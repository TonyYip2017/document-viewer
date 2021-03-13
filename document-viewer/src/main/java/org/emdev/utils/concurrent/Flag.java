package org.emdev.utils.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Flag {

  /**
   * Running flag.
   */
  private final AtomicBoolean flag;

  /**
   * Constructor.
   */
  public Flag() {
    this(false);
  }

  /**
   * Constructor.
   *
   * @param initial
   *          initial value
   */
  public Flag(final boolean initial) {
    flag = new AtomicBoolean(initial);
  }

  /**
   * @return the current flag value
   */
  public boolean get() {
    return flag.get();
  }

  /**
   * Sets the flag.
   */
  public synchronized void set() {
    if (flag.compareAndSet(false, true)) {
      this.notifyAll();
    }
  }

  /**
   * Clears the flag.
   */
  public synchronized void clear() {
    if (flag.compareAndSet(true, false)) {
      this.notifyAll();
    }
  }

  /**
   * Waits for flag changes.
   *
   * @param unit
   *          time unit
   * @param timeout
   *          timeout to wait
   * @return the flag value
   */
  public synchronized boolean waitFor(final TimeUnit unit, final long timeout) {
    try {
      unit.timedWait(this, timeout);
    } catch (final InterruptedException ex) {
      Thread.interrupted();
    }
    return get();
  }

// --Commented out by Inspection START (3/10/21 4:50 PM):
//  public synchronized void notifyWaiters() {
//      notifyAll();
//  }
// --Commented out by Inspection STOP (3/10/21 4:50 PM)
}
