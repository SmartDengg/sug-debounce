package com.smartdengg.sugdebounce;

import android.text.Editable;
import android.widget.TextView;

/**
 * 创建时间:  2017/03/20 15:19 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public class TextViewAfterTextChangeEvent {

  private TextView textView;
  private Editable editable;

  static TextViewAfterTextChangeEvent create(TextView view, Editable editable) {
    return new TextViewAfterTextChangeEvent(view, editable);
  }

  private TextViewAfterTextChangeEvent(TextView textView, Editable editable) {
    this.textView = textView;
    this.editable = editable;
  }

  public TextView view() {
    return textView;
  }

  public Editable editable() {
    return editable;
  }
}
