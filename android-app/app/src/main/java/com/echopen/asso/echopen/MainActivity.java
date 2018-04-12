package com.echopen.asso.echopen;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.MotionEvent;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.os.Handler;


import com.echopen.asso.echopen.echography_image_streaming.EchographyImageStreamingService;
import com.echopen.asso.echopen.echography_image_streaming.modes.EchographyImageStreamingTCPMode;
import com.echopen.asso.echopen.echography_image_visualisation.EchographyImageVisualisationContract;
import com.echopen.asso.echopen.echography_image_visualisation.EchographyImageVisualisationPresenter;
import com.echopen.asso.echopen.filters.RenderingContext;
import com.echopen.asso.echopen.utils.Constants;

/**
 * MainActivity class handles the main screen of the app.
 * Tools are called in the following order :
 * - initSwipeViews() handles the gesture tricks via GestureDetector class
 * - initViewComponents() mainly sets the clickable elements
 * - initActionController() and setupContainer() : in order to separate concerns, View parts are handled by the initActionController()
 * method which calls the MainActionController class that deals with MainActivity Views,
 * especially handles the display of the main screen picture
 * These two methods should be refactored into one
 */

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EchographyImageStreamingService mEchographyImageStreamingService;
    private EchographyImageVisualisationContract.Presenter mEchographyImageVisualisationPresenter;
    private ImageView mCaptureButton;
    private ImageView mPregnantWomanButton;
    private ImageView mEndExamButton;
    private ImageView mBatteryButton;
    private ImageView mSelectButton;
    private ImageView mCaptureShadow;
    private Handler handler;

    private Long then;
    private RotateAnimation rotate_animation_capture;

    private final static float IMAGE_ZOOM_FACTOR = 1.75f;
    private final static float IMAGE_ROTATION_FACTOR = 90.f;
    /**
     * This method calls all the UI methods and then gives hand to  UDPToBitmapDisplayer class.
     * UDPToBitmapDisplayer listens to UDP data, processes them with the help of ScanConversion,
     * and then displays them.
     * Also, this method uses the Config singleton class that provides device-specific constants
     */
    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mEchographyImageStreamingService = ((EchOpenApplication) getApplication()).getEchographyImageStreamingService();
        mEchographyImageStreamingService.connect(new EchographyImageStreamingTCPMode(Constants.Http.REDPITAYA_IP, Constants.Http.REDPITAYA_PORT), this);

        this.setPresenter(new EchographyImageVisualisationPresenter(mEchographyImageStreamingService, this));

        setContentView(R.layout.activity_main);




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




    }