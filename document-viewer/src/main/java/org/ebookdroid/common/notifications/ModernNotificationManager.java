package org.ebookdroid.common.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import org.emdev.BaseDroidApp;
import org.sufficientlysecure.viewer.R;

import java.util.concurrent.atomic.AtomicInteger;

class ModernNotificationManager implements INotificationManager {

    private NotificationManager manager;
    private final AtomicInteger SEQ = new AtomicInteger();

//    private int notify(final CharSequence title, final CharSequence message) {
    @Override
    public int notify(final int titleId, final CharSequence message) {
        final CharSequence title = BaseDroidApp.context.getText(titleId);
        final Notification notification = new Notification.Builder(BaseDroidApp.context)
                .setSmallIcon(R.drawable.application_icon)
                .setAutoCancel(true)
//                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                .setContentIntent(getIntent(intent))
                .setContentTitle(title)
                .setContentText(message)
                .setTicker(message)
                .build();

        final int id = SEQ.getAndIncrement();
        if (manager == null) {
            manager = (NotificationManager) BaseDroidApp.context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        manager.notify(id, notification);
        return id;
    }

//    @Override
//    public int notify(final int messageId) {
//        return notify(BaseDroidApp.context.getText(R.string.app_name), BaseDroidApp.context.getText(messageId), null);
//    }

//    @Override
//    public int notify(final int titleId, final int messageId) {
//        return notify(BaseDroidApp.context.getText(titleId), BaseDroidApp.context.getText(messageId), null);
//    }

//    @Override
//    public int notify(final int titleId, final CharSequence message, final Intent intent) {
//        return notify(BaseDroidApp.context.getText(titleId), message, intent);
//    }

//    @Override
//    public int notify(final int titleId, final CharSequence message) {
//        return notify(BaseDroidApp.context.getText(titleId), message);
//    }

//    @Override
//    public int notify(final CharSequence message) {
//        return notify(BaseDroidApp.context.getText(R.string.app_name), message, null);
//    }

//    private PendingIntent getIntent(final Intent intent) {
//        return intent != null ? PendingIntent.getActivity(EBookDroidApp.context, 0, intent, 0) : getDefaultIntent();
//    }

//    private PendingIntent getDefaultIntent() {
//        return PendingIntent.getActivity(BaseDroidApp.context, 0, new Intent(), 0);
//    }

//    private NotificationManager getManager() {
//        if (manager == null) {
//            manager = (NotificationManager) BaseDroidApp.context.getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        return manager;
//    }
}
