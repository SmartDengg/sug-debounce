package com.smartdengg.sugdebounce.internal;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 创建时间:  2017/03/20 16:36 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public class Composition {

  private Composition() {
    throw new AssertionError("no instance");
  }

  @SuppressWarnings("unchecked")
  public static <T> Observable.Transformer<T, T> applyDebounceCompose1X(long time, TimeUnit unit) {
    return (Observable.Transformer<T, T>) DebounceTransformer1X.create(time, unit);
  }

  @SuppressWarnings("unchecked")
  public static <T> ObservableTransformer<T, T> applyDebounceCompose2X(long time, TimeUnit unit) {
    return (ObservableTransformer<T, T>) DebounceTransformer2X.create(time, unit);
  }

  private static class DebounceTransformer1X implements Observable.Transformer {

    private long time;
    private TimeUnit unit;

    static Observable.Transformer create(long time, TimeUnit unit) {
      return new DebounceTransformer1X(time, unit);
    }

    private DebounceTransformer1X(long time, TimeUnit unit) {
      this.time = time;
      this.unit = unit;
    }

    @Override public Object call(Object observable) {
      return ((Observable) observable).debounce(time, unit)
          .observeOn(AndroidSchedulers.mainThread());
    }
  }

  private static class DebounceTransformer2X implements ObservableTransformer {

    private long time;
    private TimeUnit unit;

    static ObservableTransformer create(long time, TimeUnit unit) {
      return new DebounceTransformer2X(time, unit);
    }

    private DebounceTransformer2X(long time, TimeUnit unit) {
      this.time = time;
      this.unit = unit;
    }

    @Override public ObservableSource apply(io.reactivex.Observable upstream) {
      return upstream.debounce(time, unit)
          .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread());
    }
  }
}
