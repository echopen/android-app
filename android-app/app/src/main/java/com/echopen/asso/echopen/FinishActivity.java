package com.echopen.asso.echopen;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class FinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        TextView title = (TextView) findViewById(R.id.title);
        TextView text_finish = (TextView) findViewById(R.id.text_finish);
        TextView id_patient = (TextView) findViewById(R.id.id_patient);

        setFont(title,"Moderat-Bold.ttf");
        setFont(text_finish,"Avenir-Book.ttf");
        setFont(id_patient,"Moderat-Bold.ttf");
    }

    public void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }
}
