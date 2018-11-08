package com.smartdengg.sugdebounce;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import com.smartdengg.sugdebounce.internal.Composition;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;
import io.reactivex.observers.DisposableObserver;
import java.util.concurrent.TimeUnit;

/**
 * 创建时间:  2017/03/20 16:46 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
class Rx2xObservable {

  private Rx2xObservable() {
    throw new AssertionError("no instance");
  }

  static DebounceSubscription subscribeAfterTextChangeEvent(TextView textView, long timeout,
      TimeUnit unit, final DebounceObserver<TextViewAfterTextChangeEvent> observer) {

    final DisposableObserver<TextViewAfterTextChangeEvent> disposable =
        Observable.create(new RxAfterTextChangedEmitter(textView))
            .compose(
                Composition.<TextViewAfterTextChangeEvent>applyDebounceCompose2X(timeout, unit))
            .subscribeWith(new DisposableObserver<TextViewAfterTextChangeEvent>() {
              @Override public void onNext(TextViewAfterTextChangeEvent event) {
                observer.onSuccess(event);
              }

              @Override public void onError(Throwable throwable) {
                observer.onError(throwable);
              }

              @Override public void onComplete() {
                /*no-op*/
              }
            });

    return RxSubscription.wrap(disposable);
  }

  private static class RxAfterTextChangedEmitter
      implements ObservableOnSubscribe<TextViewAfterTextChangeEvent> {

    private TextView textView;

    RxAfterTextChangedEmitter(TextView textView) {
      this.textView = textView;
    }

    @Override public void subscribe(final ObservableEmitter<TextViewAfterTextChangeEvent> emitter)
        throws Exception {

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

      emitter.setCancellable(new Cancellable() {
        @Override public void cancel() throws Exception {
          textView.removeTextChangedListener(watcher);
        }
      });
    }
  }
}
