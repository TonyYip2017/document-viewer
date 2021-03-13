package org.ebookdroid.core.models;

import org.ebookdroid.core.events.DecodingProgressListener;

import java.util.concurrent.atomic.AtomicInteger;

import org.emdev.utils.listeners.ListenerProxy;

public class DecodingProgressModel extends ListenerProxy {

    private final AtomicInteger currentlyDecoding = new AtomicInteger();

    public DecodingProgressModel() {
        super(DecodingProgressListener.class);
    }

// --Commented out by Inspection START (3/10/21 4:44 PM):
//    public void increase() {
//        this.<DecodingProgressListener> getListener().decodingProgressChanged(currentlyDecoding.incrementAndGet());
//    }
// --Commented out by Inspection STOP (3/10/21 4:44 PM)

    public void increase(int increment) {
        this.<DecodingProgressListener> getListener().decodingProgressChanged(currentlyDecoding.addAndGet(increment));
    }

    public void decrease() {
        this.<DecodingProgressListener> getListener().decodingProgressChanged(currentlyDecoding.decrementAndGet());
    }
}
