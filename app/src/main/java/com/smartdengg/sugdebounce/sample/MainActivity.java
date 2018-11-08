package com.smartdengg.sugdebounce.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.no_debounce_btn).setOnClickListener(this);
    findViewById(R.id.debounce_btn).setOnClickListener(this);
  }

  @Override public void onClick(View v) {

    switch (v.getId()) {
      case R.id.no_debounce_btn:
        EditTextActivity.start(MainActivity.this, EditTextActivity.NO_DEBOUNCE);
        break;

      case R.id.debounce_btn:
        EditTextActivity.start(MainActivity.this, EditTextActivity.DEBOUNCE);
        break;
    }
  }
}
