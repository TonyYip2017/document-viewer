package org.ebookdroid.core;

public class ViewPages {

    public int currentIndex;
    public int firstVisible;
    public int lastVisible;

    public int firstCached;
    public int lastCached;

    ViewPages() {
    }

// --Commented out by Inspection START (3/10/21 5:17 PM):
//    public void update(final ViewPages p) {
//        currentIndex = p.currentIndex;
//        firstVisible = p.firstVisible;
//        lastVisible = p.firstVisible;
//        firstCached = p.firstCached;
//        lastCached = p.lastCached;
//    }
// --Commented out by Inspection STOP (3/10/21 5:17 PM)

    @Override
    public String toString() {
        final StringBuilder buf = new StringBuilder(this.getClass().getSimpleName());
        buf.append("[");
        toString(buf);
        buf.append("]");
        return buf.toString();
    }

    StringBuilder toString(final StringBuilder buf) {
        buf.append("visible: ").append("[");
        buf.append(firstVisible).append(", ").append(currentIndex).append(", ").append(lastVisible);
        buf.append("]");
        buf.append(" ");
        buf.append("cached: ").append("[");
        buf.append(firstCached).append(", ").append(lastCached);
        buf.append("]");

        return buf;
    }
}
