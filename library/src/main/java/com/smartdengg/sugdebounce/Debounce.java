package com.smartdengg.sugdebounce;

import android.widget.TextView;
import com.smartdengg.sugdebounce.internal.Preconditions;
import com.smartdengg.sugdebounce.internal.RxPlatform;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间:  2017/03/20 14:57 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public class Debounce {

  private static boolean hasRx1XClasspath =
      RxPlatform.HAS_RX1X_OBSERVABLE || RxPlatform.HAS_RX1X_ANDROID;

  private static boolean hasRx2XClasspath =
      RxPlatform.HAS_RX2X_OBSERVABLE || RxPlatform.HAS_RX2X_ANDROID;

  private Debounce() {
    throw new AssertionError("No instances!");
  }

  /**
   * Listen to the TextView content changes and return a {@link DebounceSubscription}. If a new
   * content change happens within timeout, the timeout will be dropped until the next content
   * changes. when the timeout complete over, trigger the {@link DebounceObserver}.
   * <p>
   * <em>Note:</em>  If the current thread is not the UI thread, you will receive an default {@link
   * DebounceSubscription#NONE instance}
   * <p>
   *
   * @param textView textView for listening to content changes
   * @param timeout the debounce time interval, the most recent timeout will be effective, the
   * former will always be dropped
   * @param unit the {@link TimeUnit} for the timeout
   * @param observer the content change callback, will receive the textView current content
   * @return the DebounceSubscription, you can remove the content listener by calling
   * DebounceSubscription.unsubscribe()
   * @see <a href="#DebounceSubscription#NONE">DebounceSubscription#NONE</a>
   */
  public static DebounceSubscription onAfterTextChangedAction(TextView textView, long timeout,
      TimeUnit unit, final DebounceObserver<TextViewAfterTextChangeEvent> observer) {

    Preconditions.requireNonNull(observer, "observer = null");

    if (!Preconditions.checkMainThread(observer)) return DebounceSubscription.NONE;

    if (hasRx2XClasspath) {
      return Rx2xObservable.subscribeAfterTextChangeEvent(textView, timeout, unit, observer);
    } else if (hasRx1XClasspath) {
      return Rx1xObservable.subscribeAfterTextChangeEvent(textView, timeout, unit, observer);
    } else {
      return LocalHandlerObservable.subscribeAfterTextChangeEvent(textView, timeout, unit,
          observer);
    }
  }

  /**
   * Listen to the TextView content changes and return a {@link DebounceSubscription}. If a new
   * content change happens within timeout, the timeout will be dropped until the next content
   * changes. when the timeout complete over, trigger the {@link DebounceObserver}.
   * <p>
   * <em>Note:</em>  If the current thread is not the UI thread, you will receive an default {@link
   * DebounceSubscription#NONE instance}
   * <p>
   *
   * @param textView textView for listening to content changes
   * @param timeoutInMillis the debounce time interval, the most recent timeout will be effective,
   * the former will always be dropped, in Milliseconds unit
   * @param observer the content change callback, will receive the textView current content
   * @return the DebounceSubscription, you can remove the content listener by calling
   * DebounceSubscription.unsubscribe()
   * @see <a href="#DebounceSubscription#NONE">DebounceSubscription#NONE</a>
   * @see #onAfterTextChangedAction(TextView, long, TimeUnit, DebounceObserver)
   */
  public static DebounceSubscription onAfterTextChangedAction(TextView textView,
      long timeoutInMillis, final DebounceObserver<TextViewAfterTextChangeEvent> observer) {
    return onAfterTextChangedAction(textView, timeoutInMillis, TimeUnit.MILLISECONDS, observer);
  }
}
