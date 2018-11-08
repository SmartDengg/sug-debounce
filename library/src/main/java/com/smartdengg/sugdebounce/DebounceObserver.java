package com.smartdengg.sugdebounce;

/**
 * 创建时间:  2017/03/20 14:55 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public interface DebounceObserver<T> {

  void onError(Throwable throwable);

  void onSuccess(T t);
}
