package com.echopen.asso.echopen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.echopen.asso.echopen.echography_image_streaming.EchographyImageStreamingService;
import com.echopen.asso.echopen.echography_image_streaming.modes.EchographyImageStreamingMode;
import com.echopen.asso.echopen.echography_image_streaming.modes.EchographyImageStreamingTCPMode;
import com.echopen.asso.echopen.echography_image_visualisation.EchographyImageVisualisationContract;
import com.echopen.asso.echopen.echography_image_visualisation.EchographyImageVisualisationPresenter;
import com.echopen.asso.echopen.ui.RenderingContextController;
import com.echopen.asso.echopen.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static com.echopen.asso.echopen.utils.Constants.Http.REDPITAYA_PORT;

public class ScanActivity extends Activity implements EchographyImageVisualisationContract.View, View.OnClickListener {

    Bitmap current_image;
    String i1;
    /**
     * This method calls all the UI methods and then gives hand to  UDPToBitmapDisplayer class.
     * UDPToBitmapDisplayer listens to UDP data, processes them with the help of ScanConversion,
     * and then displays them.
     * Also, this method uses the Config singleton class that provides device-specific constants
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scan);
        super.onCreate(savedInstanceState);


        ImageButton screenShot_Button = (ImageButton) findViewById(R.id.sreenshot_button) ;
        screenShot_Button.setOnClickListener(this);
        i1 = UUID.randomUUID().toString();

        ImageView type_image = (ImageView) findViewById(R.id.type_image);
        type_image.setImageResource(R.drawable.picto_estomac_off);

        RenderingContextController rdController = new RenderingContextController();
        EchographyImageStreamingService serviceEcho =  new EchographyImageStreamingService(rdController);
        EchographyImageVisualisationPresenter presenter = new EchographyImageVisualisationPresenter(serviceEcho, this);
        EchographyImageStreamingMode mode = new EchographyImageStreamingTCPMode(Constants.Http.REDPITAYA_IP, REDPITAYA_PORT);
        serviceEcho.connect(mode, this);
        HashMap info = (HashMap) getIntent().getSerializableExtra("info");
        Log.d("IIIICCCCCCCCCCCIIIIIII", ""+info.size());
        presenter.listenEchographyImageStreaming();

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    /**
     * Following the doc https://developer.android.com/intl/ko/training/basics/intents/result.html,
     * onActivityResult is “Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.”,
     * See more here : https://stackoverflow.com/questions/20114485/use-onactivityresult-android
     *
     * @param requestCode, integer argument that identifies your request
     * @param resultCode, to get its values, check RESULT_CANCELED, RESULT_OK here https://developer.android.com/reference/android/app/Activity.html#RESULT_OK
     * @param data,       Intent instance
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshImage(final Bitmap iBitmap) {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("hey", "yo");
                    ImageView echoImage = (ImageView) findViewById(R.id.echo_view);
                    echoImage.setImageBitmap(iBitmap);
                    current_image = iBitmap;
                }

            });

        }
        catch (Exception e){
            System.out.print("Eurreur");
            e.printStackTrace();
        }
    }

    @Override
    public void setPresenter(EchographyImageVisualisationContract.Presenter presenter) {
        presenter.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sreenshot_button:

                FileOutputStream out = null;
                try {
                    out = openFileOutput( i1+"-"+System.currentTimeMillis() + ".jpeg", MODE_PRIVATE);
                    current_image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Eurrrrrrr", " !!!!!!!!!");
                }
                break;
        }
    }

}


