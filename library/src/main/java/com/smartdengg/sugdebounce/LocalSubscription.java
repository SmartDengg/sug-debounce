package com.smartdengg.sugdebounce;

import android.text.TextWatcher;
import android.widget.TextView;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 创建时间:  2017/03/20 17:08 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
class LocalSubscription extends AtomicReference<TextWatcher> implements DebounceSubscription {

  private TextView textView;

  static DebounceSubscription wrap(TextView textView, TextWatcher textWatcher) {
    return new LocalSubscription(textView, textWatcher);
  }

  private LocalSubscription(TextView textView, TextWatcher textWatcher) {
    super(textWatcher);
    this.textView = textView;
  }

  @Override public boolean isUnsubscribed() {
    return get() == null;
  }

  @Override public void unsubscribe() {
    if (get() != null) {
      TextWatcher textWatcher = getAndSet(null);
      LocalHandlerObservable.HANDLER.removeMessages(LocalHandlerObservable.AFTER_TEXT_CHANGE_EVENT);
      textView.removeTextChangedListener(textWatcher);
    }
  }
}
