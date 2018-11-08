package com.smartdengg.sugdebounce.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import com.smartdengg.sugdebounce.Debounce;
import com.smartdengg.sugdebounce.DebounceObserver;
import com.smartdengg.sugdebounce.TextViewAfterTextChangeEvent;

/**
 * 创建时间:  2017/12/21 19:23 <br>
 * 作者:  dengwei <br>
 * 描述:
 */
public class EditTextActivity extends AppCompatActivity {

  static final int NO_DEBOUNCE = 0;
  static final int DEBOUNCE = 1;
  private static final String KEY_TYPE = "TYPE";

  @IntDef({ NO_DEBOUNCE, DEBOUNCE }) @interface type {
  }

  static void start(Context context, @type int type) {

    Intent intent = new Intent(context, EditTextActivity.class);
    intent.putExtra(KEY_TYPE, type);
    context.startActivity(intent);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edittext);

    final int type = getIntent().getIntExtra(KEY_TYPE, NO_DEBOUNCE);

    EditText editText = findViewById(R.id.edittext);
    final TextView logTv = findViewById(R.id.log_tv);

    switch (type) {

      case NO_DEBOUNCE:
        setTitle("no-debounce");

        editText.addTextChangedListener(new TextWatcher() {
          @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override public void onTextChanged(CharSequence s, int start, int before, int count) {

          }

          @Override public void afterTextChanged(Editable editable) {
            logTv.append("\n" + editable.toString());
          }
        });

        break;

      case DEBOUNCE:
        setTitle("debounce");

        Debounce.onAfterTextChangedAction(editText, 300,
            new DebounceObserver<TextViewAfterTextChangeEvent>() {
              @Override public void onError(Throwable throwable) {

              }

              @Override public void onSuccess(TextViewAfterTextChangeEvent event) {
                logTv.append("\n" + event.editable().toString());
              }
            });

        break;
    }
  }
}
