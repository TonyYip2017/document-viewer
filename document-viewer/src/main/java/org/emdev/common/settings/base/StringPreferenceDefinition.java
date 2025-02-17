package org.emdev.common.settings.base;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;

public class StringPreferenceDefinition extends BasePreferenceDefinition<String> {

    private final String defValue;

    public StringPreferenceDefinition(final int keyRes) {
        super(keyRes);
        defValue = "";
    }

    @Override
    public String getPreferenceValue(final SharedPreferences prefs) {
        return getPreferenceValue(prefs, defValue);
    }

    public String getPreferenceValue(final SharedPreferences prefs, final String defValue) {
        if (!prefs.contains(key)) {
            prefs.edit().putString(key, defValue).commit();
        }
        return prefs.getString(key, defValue);
    }

    public void setPreferenceValue(final Editor edit, final String value) {
        edit.putString(key, value);
    }

    @Override
    public void restore(final JSONObject root, final Editor edit) {
        final String value = root.optString(key);
        setPreferenceValue(edit, value != null ? value : defValue);
    }
}
