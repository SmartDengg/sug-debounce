package com.smartdengg.sugdebounce;

/**
 * 创建时间:  2017/03/20 16:57 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public interface DebounceSubscription {

  DebounceSubscription NONE = new DebounceSubscription() {

    @Override public boolean isUnsubscribed() {
      return true;
    }

    @Override public void unsubscribe() {

    }
  };

  boolean isUnsubscribed();

  void unsubscribe();
}
