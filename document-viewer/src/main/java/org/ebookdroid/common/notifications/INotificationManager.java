package org.ebookdroid.common.notifications;

public interface INotificationManager {

    INotificationManager instance = new ModernNotificationManager();

//    int notify(final CharSequence title, final CharSequence message, final Intent intent);

//    int notify(final CharSequence message);

//    int notify(final int titleId, final CharSequence message, final Intent intent);
    int notify(final int titleId, final CharSequence message);

//    int notify(final int titleId, final int messageId);

//    int notify(final int messageId);

}
