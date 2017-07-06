package com.echopen.asso.echopen;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageButton button_exam = (ImageButton) findViewById(R.id.button_exam);
        button_exam.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_exam:
                startActivity(new Intent( this, SettingsActivity.class));
                break;
        }
    }
}
