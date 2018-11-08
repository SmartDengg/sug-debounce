package com.smartdengg.sugdebounce.internal;

/**
 * 创建时间: 2017/03/20 15:00 <br>
 * 作者: dengwei <br>
 * 描述: Determine whether rxjava is compiled dependent
 */
public class RxPlatform {

  private RxPlatform() {
    throw new AssertionError("No instance");
  }

  public static final boolean HAS_RX1X_OBSERVABLE = hasRx1XObservableOnClasspath();
  public static final boolean HAS_RX1X_ANDROID = hasRx1XAndroidOnClasspath();

  public static final boolean HAS_RX2X_OBSERVABLE = hasRx2XObservableOnClasspath();
  public static final boolean HAS_RX2X_ANDROID = hasRx2XAndroidOnClasspath();

  private static boolean hasRx1XObservableOnClasspath() {

    boolean hasObservable = false;

    try {
      final String observableClassName = rx.Observable.class.getName();
      Class.forName(observableClassName, false, getClassLoader());
      hasObservable = true;
    } catch (Exception ignored) {
      /*ClassNotFoundException*/
    }
    return hasObservable;
  }

  private static boolean hasRx1XAndroidOnClasspath() {

    boolean hasRxAndroid = false;
    try {
      final String androidSchedulersClassName =
          rx.android.schedulers.AndroidSchedulers.class.getName();
      Class.forName(androidSchedulersClassName, false, getClassLoader());
      hasRxAndroid = true;
    } catch (Exception ignored) {
      /*ClassNotFoundException*/
    }
    return hasRxAndroid;
  }

  private static boolean hasRx2XObservableOnClasspath() {

    boolean hasObservable = false;
    try {
      final String observableClassName = io.reactivex.Observable.class.getName();
      Class.forName(observableClassName, false, getClassLoader());
      hasObservable = true;
    } catch (Exception ignored) {
      /*ClassNotFoundException*/
    }
    return hasObservable;
  }

  private static boolean hasRx2XAndroidOnClasspath() {

    boolean hasRxAndroid = false;
    try {
      final String androidSchedulersClassName =
          io.reactivex.android.schedulers.AndroidSchedulers.class.getName();
      Class.forName(androidSchedulersClassName, false, getClassLoader());
      hasRxAndroid = true;
    } catch (Exception ignored) {
      /*ClassNotFoundException*/
    }
    return hasRxAndroid;
  }

  private static ClassLoader getClassLoader() {
    return RxPlatform.class.getClassLoader();
  }
}
