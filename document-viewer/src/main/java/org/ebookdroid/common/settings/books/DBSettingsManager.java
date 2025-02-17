package org.ebookdroid.common.settings.books;

import org.ebookdroid.common.settings.BackupSettings;
import org.ebookdroid.common.settings.SettingsManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.emdev.common.backup.BackupManager;
import org.emdev.common.backup.IBackupAgent;
import org.emdev.common.log.LogContext;
import org.emdev.common.log.LogManager;
import org.emdev.utils.LengthUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBSettingsManager extends SQLiteOpenHelper implements IBackupAgent {

    private static final LogContext LCTX = LogManager.root().lctx("DBSettingsManager");

    public static final String BACKUP_KEY = "recent-books";

    public static final int DB_VERSION = DBAdapterV9.VERSION;

    private final IDBAdapter adapter;

    private SQLiteDatabase upgragingInstance;

    private SQLiteDatabase m_db;

    public DBSettingsManager(final Context context) {
        this(context, context.getPackageName() + ".settings");
    }

    DBSettingsManager(final Context context, String fileName) {
        this(context, fileName, DB_VERSION);
    }

    /**
     * Used in DBSettingsManagerTest
     *
     * @param context
     * @param fileName
     */
    DBSettingsManager(final Context context, String fileName, int version) {
        super(context, fileName, null, version);
        adapter = createAdapter(version);
        try {
            m_db = getWritableDatabase();
        } catch (final Exception ex) {
            LCTX.e("Unexpected DB error: ", ex);
        }
        BackupManager.addAgent(this);
    }

    public void close() {
        if (m_db != null) {
            try {
                m_db.close();
            } catch (Exception ignored) {
            }
            m_db = null;
        }
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        adapter.onCreate(db);
    }

    protected IDBAdapter createAdapter(final int version) {
        switch (version) {
            case DBAdapterV1.VERSION:
                return new DBAdapterV1(this);
            case DBAdapterV2.VERSION:
                return new DBAdapterV2(this);
            case DBAdapterV3.VERSION:
                return new DBAdapterV3(this);
            case DBAdapterV4.VERSION:
                return new DBAdapterV4(this);
            case DBAdapterV5.VERSION:
                return new DBAdapterV5(this);
            case DBAdapterV6.VERSION:
                return new DBAdapterV6(this);
            case DBAdapterV7.VERSION:
                return new DBAdapterV7(this);
            case DBAdapterV8.VERSION:
                return new DBAdapterV8(this);
            case DBAdapterV9.VERSION:
            default:
                return new DBAdapterV9(this);
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        upgragingInstance = db;
        LCTX.i("Upgrading from version " + oldVersion + " to version " + newVersion);
        try {
            final IDBAdapter oldAdapter = createAdapter(oldVersion);
            final IDBAdapter newAdapter = createAdapter(newVersion);
            switchAdapter(db, oldAdapter, newAdapter);
        } finally {
            upgragingInstance = null;
            LCTX.i("Upgrade finished");
        }
    }

    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        upgragingInstance = db;
        LCTX.i("Downgrading from version " + oldVersion + " to version " + newVersion);
        try {
            final IDBAdapter oldAdapter = createAdapter(oldVersion);
            final IDBAdapter newAdapter = createAdapter(newVersion);
            switchAdapter(db, newAdapter, oldAdapter);
        } finally {
            upgragingInstance = null;
            LCTX.i("Downgrade finished");
        }
    }

    public void switchAdapter(final SQLiteDatabase db, final IDBAdapter oldAdapter, final IDBAdapter newAdapter) {
        final Map<String, BookSettings> bookSettings = oldAdapter.getAllBooks();
        oldAdapter.deleteAll();
        oldAdapter.onDestroy(db);
        newAdapter.onCreate(db);
        newAdapter.restoreBookSettings(bookSettings.values());
    }

    /**
     * {@inheritDoc}
     *
     * @see android.database.sqlite.SQLiteOpenHelper#getWritableDatabase()
     */
    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        if (upgragingInstance != null) {
            return upgragingInstance;
        }

        if (m_db != null && m_db.isOpen()) {
            return m_db;
        }
        LCTX.d("New DB connection created: " + m_db);
        m_db = super.getWritableDatabase();
        return m_db;
    }

    /**
     * {@inheritDoc}
     *
     * @see android.database.sqlite.SQLiteOpenHelper#getReadableDatabase()
     */
    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        if (upgragingInstance != null) {
            return upgragingInstance;
        }

        if (m_db != null && m_db.isOpen()) {
            return m_db;
        }
        return super.getReadableDatabase();
    }

    synchronized void closeDatabase(final SQLiteDatabase db) {
        if (db != upgragingInstance && db != m_db) {
            try {
                db.close();
            } catch (final Exception ignored) {
            }
            LCTX.d("DB connection closed: " + m_db);
        }
    }

    public Map<String, BookSettings> getAllBooks() {
        return adapter.getAllBooks();
    }

    public Map<String, BookSettings> getRecentBooks(final boolean all) {
        return adapter.getRecentBooks(all);
    }

    public BookSettings getBookSettings(final String fileName) {
        return adapter.getBookSettings(fileName);
    }

    public boolean storeBookSettings(final BookSettings bs) {
        return bs.persistent && adapter.storeBookSettings(Collections.singletonList(bs));
    }

    public boolean storeBookSettings(final List<BookSettings> list) {
        return adapter.storeBookSettings(list);
    }

    public boolean restoreBookSettings(Collection<BookSettings> c) {
        return adapter.restoreBookSettings(c);
    }

    public boolean clearRecent() {
        return adapter.clearRecent();
    }

    public boolean removeBookFromRecents(final BookSettings bs) {
        return adapter.removeBookFromRecents(bs);
    }

    public void delete(final BookSettings current) {
        adapter.delete(current);
    }

    public boolean deleteAll() {
        return adapter.deleteAll();
    }

    public boolean deleteAllBookmarks() {
        return adapter.deleteAllBookmarks();
    }

    @Override
    public String key() {
        return BACKUP_KEY;
    }

    @Override
    public JSONObject backup() {
        final BookBackupType backupType = BackupSettings.current().bookBackup;
        final JSONObject root = new JSONObject();
        if (backupType == BookBackupType.NONE) {
            return root;
        }
        try {
            final JSONArray books = new JSONArray();
            root.put("books", books);
            final Map<String, BookSettings> m = backupType == BookBackupType.RECENT ? getRecentBooks(true)
                    : getAllBooks();
            for (final BookSettings bs : m.values()) {
                final JSONObject obj = bs.toJSON();
                books.put(obj);
            }
        } catch (final JSONException ex) {
            SettingsManager.LCTX.e("Error on recent book backup: " + ex.getMessage());
        }
        return root;
    }

    @Override
    public void restore(final JSONObject backup) {
        try {
            final List<BookSettings> list = new ArrayList<>();
            final JSONArray books = backup.getJSONArray("books");
            if (LengthUtils.isNotEmpty(books)) {
                for (int i = 0, n = books.length(); i < n; i++) {
                    final JSONObject obj = books.getJSONObject(i);
                    list.add(new BookSettings(obj));
                }
            }

            if (deleteAll()) {
                restoreBookSettings(list);
            }
            SettingsManager.onRecentBooksChanged();
        } catch (final JSONException ex) {
            SettingsManager.LCTX.e("Error on recent book restoring: " + ex.getMessage());
        }
    }
}
