package com.smartdengg.sugdebounce;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间:  2017/03/20 16:48 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
class LocalHandlerObservable {

  private static final String DATA = "data";

  static final int AFTER_TEXT_CHANGE_EVENT = 1;
  static final Handler HANDLER;

  static {
    HANDLER = new Handler(Looper.getMainLooper()) {
      @Override public void handleMessage(Message msg) {

        switch (msg.what) {

          case AFTER_TEXT_CHANGE_EVENT:

            final TextViewAfterTextChangeEventWrapper eventWrapper =
                (TextViewAfterTextChangeEventWrapper) msg.obj;
            TextViewAfterTextChangeEvent event = eventWrapper.event;
            eventWrapper.observer.onSuccess(event);

            break;

          default:
            throw new IllegalStateException("Unknown handler message received: " + msg.what);
        }
      }
    };
  }

  private LocalHandlerObservable() {
    throw new AssertionError("no instance");
  }

  static DebounceSubscription subscribeAfterTextChangeEvent(final TextView textView,
      final long timeout, final TimeUnit unit,
      final DebounceObserver<TextViewAfterTextChangeEvent> observer) {

    final TextWatcher textWatcher = new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override public void afterTextChanged(Editable editable) {

        final TextViewAfterTextChangeEventWrapper eventWrapper =
            new TextViewAfterTextChangeEventWrapper();

        eventWrapper.event = TextViewAfterTextChangeEvent.create(textView, editable);
        eventWrapper.observer = observer;

        final Message message = Message.obtain();
        message.what = AFTER_TEXT_CHANGE_EVENT;
        message.obj = eventWrapper;

        HANDLER.removeMessages(AFTER_TEXT_CHANGE_EVENT);
        HANDLER.sendMessageDelayed(message, unit.toMillis(timeout));
      }
    };

    textView.addTextChangedListener(textWatcher);

    return LocalSubscription.wrap(textView, textWatcher);
  }

  private static class TextViewAfterTextChangeEventWrapper {
    TextViewAfterTextChangeEvent event;
    DebounceObserver<TextViewAfterTextChangeEvent> observer;
  }
}
