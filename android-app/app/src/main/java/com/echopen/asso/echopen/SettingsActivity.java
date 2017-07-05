package com.echopen.asso.echopen;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button start_button = (Button) findViewById(R.id.scan_start_button);
        start_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scan_start_button:
                startActivity(new Intent( this, ScanActivity.class));
                break;
        }
    }
}
