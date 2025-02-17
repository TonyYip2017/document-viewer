package org.ebookdroid.common.settings.types;

import android.content.pm.ActivityInfo;
import android.os.Build;

import org.ebookdroid.EBookDroidApp;
import org.emdev.utils.enums.ResourceConstant;
import org.sufficientlysecure.viewer.R;

public enum RotationType implements ResourceConstant {

    /**
    *
    */
    UNSPECIFIED(R.string.pref_rotation_unspecified, ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED, 3),
    /**
    *
    */
    LANDSCAPE(R.string.pref_rotation_land, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, 3),
    /**
    *
    */
    PORTRAIT(R.string.pref_rotation_port, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, 3),
    /**
    *
    */
    USER(R.string.pref_rotation_user, ActivityInfo.SCREEN_ORIENTATION_USER, 3),
    /**
    *
    */
    BEHIND(R.string.pref_rotation_behind, ActivityInfo.SCREEN_ORIENTATION_BEHIND, 3),
    /**
    *
    */
    AUTOMATIC(R.string.pref_rotation_auto, ActivityInfo.SCREEN_ORIENTATION_SENSOR, 3),
    /**
    *
    */
    NOSENSOR(R.string.pref_rotation_nosensor, ActivityInfo.SCREEN_ORIENTATION_NOSENSOR, 3),
    /**
    * Not compatible with of versions
    */
    SENSOR_LANDSCAPE(R.string.pref_rotation_sensor_landscape, 6 /* ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE */, 10),
    /**
    *
    */
    SENSOR_PORTRAIT(R.string.pref_rotation_sensor_portrait, 7 /* ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT */, 10),
    /**
    *
    */
    REVERSE_LANDSCAPE(R.string.pref_rotation_reverse_landscape, 8 /* ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE */, 10),
    /**
    *
    */
    REVERSE_PORTRAIT(R.string.pref_rotation_reverse_portrait, 9 /* ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT */, 10),
    /**
    *
    */
    FULL_SENSOR(R.string.pref_rotation_full_sensor, 10 /* ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR */, 10);

    private final String resValue;

    private final int orientation;

    private final int version;

    RotationType(final int resId, final int orientation, final int version) {
        this.resValue = EBookDroidApp.context.getString(resId);
        this.orientation = orientation;
        this.version = version;
    }

    public String getResValue() {
        return resValue;
    }

    public int getOrientation() {
        return this.version <= Build.VERSION.SDK_INT ? orientation : ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

// --Commented out by Inspection START (3/10/21 5:27 PM):
//    public int getVersion() {
//        return version;
//    }
// --Commented out by Inspection STOP (3/10/21 5:27 PM)
}
