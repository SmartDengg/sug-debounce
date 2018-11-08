package com.smartdengg.sugdebounce;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.smartdengg.sugdebounce.internal.Composition;
import java.util.concurrent.TimeUnit;
import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Cancellable;

/**
 * 创建时间:  2017/03/20 16:46 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
class Rx1xObservable {

  private Rx1xObservable() {
    throw new AssertionError("no instance");
  }

  static DebounceSubscription subscribeAfterTextChangeEvent(TextView textView, long timeout,
      TimeUnit unit, final DebounceObserver<TextViewAfterTextChangeEvent> observer) {

    Subscription subscription = Observable.create(RxAfterTextChangedEmitter.create(textView),
        Emitter.BackpressureMode.LATEST)
        .compose(Composition.<TextViewAfterTextChangeEvent>applyDebounceCompose1X(timeout, unit))
        .subscribe(new Subscriber<TextViewAfterTextChangeEvent>() {
          @Override public void onCompleted() {
            /*no-op*/
          }

          @Override public void onError(Throwable throwable) {
            observer.onError(throwable);
          }

          @Override public void onNext(TextViewAfterTextChangeEvent event) {
            observer.onSuccess(event);
          }
        });

    return RxSubscription.wrap(subscription);
  }

  private static class RxAfterTextChangedEmitter
      implements Action1<Emitter<TextViewAfterTextChangeEvent>> {

    private TextView textView;

    @SuppressWarnings("unchecked")
    static RxAfterTextChangedEmitter create(final TextView textView) {
      return new RxAfterTextChangedEmitter(textView);
    }

    private RxAfterTextChangedEmitter(TextView textView) {
      this.textView = textView;
    }

    @Override public void call(final Emitter<TextViewAfterTextChangeEvent> emitter) {
      final TextWatcher watcher = new TextWatcher() {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override public void afterTextChanged(Editable editable) {
          emitter.onNext(TextViewAfterTextChangeEvent.create(textView, editable));
        }
      };
      textView.addTextChangedListener(watcher);
      emitter.setCancellation(new Cancellable() {
        @Override public void cancel() throws Exception {
          textView.removeTextChangedListener(watcher);
        }
      });
    }
  }
}
