package com.smartdengg.sugdebounce;

import io.reactivex.disposables.Disposable;
import rx.Subscription;

/**
 * 创建时间:  2017/03/20 17:05 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
class RxSubscription implements DebounceSubscription {

  private Subscription subscription;
  private Disposable disposable;

  static DebounceSubscription wrap(Subscription subscription) {
    return new RxSubscription(subscription);
  }

  static DebounceSubscription wrap(Disposable disposable) {
    return new RxSubscription(disposable);
  }

  private RxSubscription(Subscription subscription) {
    this.subscription = subscription;
  }

  private RxSubscription(Disposable disposable) {
    this.disposable = disposable;
  }

  @Override public boolean isUnsubscribed() {

    if (subscription != null) {
      return subscription.isUnsubscribed();
    } else if (disposable != null) {
      return disposable.isDisposed();
    }
    return true;
  }

  @Override public void unsubscribe() {
    if (subscription != null) {
      subscription.unsubscribe();
    } else if (disposable != null) {
      disposable.dispose();
    }
  }
}
