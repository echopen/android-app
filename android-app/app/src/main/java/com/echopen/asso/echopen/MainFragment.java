package com.echopen.asso.echopen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements EchographyImageVisualisationContract.View {
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




    public MainFragment() {
        mCaptureShadow.setOnTouchListener(new View.OnTouchListener() {
            @Override

            public boolean onTouch(View v,final MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    then = System.currentTimeMillis();
                    mEchographyImageVisualisationPresenter.toggleFreeze();

                    rotate_animation_capture = new RotateAnimation(0,144 ,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.6f);

                    rotate_animation_capture.setDuration(5000);
                    mCaptureShadow.clearAnimation();
                    mCaptureShadow.setAnimation(rotate_animation_capture);

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("mcaptureButton", "Long Press");

                        }
                    }, 5000);

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    mEchographyImageVisualisationPresenter.toggleFreeze();
                    if(((Long) System.currentTimeMillis() - then) > 5000){
                        Log.d("mcaptureButton", "Long Press");
                        return false;
                    }
                    if(((Long) System.currentTimeMillis() - then) < 200){
                        Log.d("mcaptureButton", "Short Press");
                        rotate_animation_capture.cancel();
                        rotate_animation_capture= null;
                        handler.removeCallbacksAndMessages(null);
                        handler = null;
                        return false;
                    }
                    else {
                        handler.removeCallbacksAndMessages(null);
                        handler = null;
                        rotate_animation_capture.cancel();
                        rotate_animation_capture= null;
                        Log.d("mcaptureButton", "noContent");
                    }
                }
                return true ;
            }
        });


        mPregnantWomanButton = (ImageView) getView().findViewById(R.id.main_button_jauge);
        mPregnantWomanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pregnantButton", "PregnantWomanButton Pressed");
            }
        });
        mCaptureButton = (ImageView) getView().findViewById(R.id.main_button_capture);

        mCaptureShadow = (ImageView) getView().findViewById(R.id.main_button_shadow);
        mCaptureShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("captureButton", "Short Press"); }
        });

        mSelectButton = (ImageView) getView().findViewById(R.id.main_button_select);
        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("selectButton", "SelectButton Pressed");
            }
        });

        mEndExamButton = (ImageView) getView().findViewById(R.id.main_button_end_exam);
        mEndExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("endExamButton", "EndExamButton Pressed");
            }
        });

        mBatteryButton = (ImageView) getView().findViewById(R.id.main_button_battery);
        mBatteryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("batteryButton", "BatteryButton Pressed");
            }
        });


        mEchographyImageStreamingService.getRenderingContextController().setLinearLutSlope(RenderingContext.DEFAULT_LUT_SLOPE);
        mEchographyImageStreamingService.getRenderingContextController().setLinearLutOffset(RenderingContext.DEFAULT_LUT_OFFSET);
    }
    }
    @Override
    public void refreshImage(final Bitmap iBitmap) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "refreshImage");
                ImageView lEchOpenImage = (ImageView) getView().findViewById(R.id.echopenImage);
                lEchOpenImage.setRotation(IMAGE_ROTATION_FACTOR);
                lEchOpenImage.setScaleX(IMAGE_ZOOM_FACTOR);
                lEchOpenImage.setScaleY(IMAGE_ZOOM_FACTOR);
                lEchOpenImage.setImageBitmap(iBitmap);
            }
        });
    }

    @Override
    public void displayFreezeButton() {
        mCaptureShadow.setImageResource(R.drawable.icon_arc_shadow);
        mCaptureButton.setImageResource(R.drawable.button_jauge);

    }

    @Override
    public void displayUnfreezeButton() {
        mCaptureShadow.setImageResource(R.drawable.icon_save_image);


    }

    @Override
    public void setPresenter(EchographyImageVisualisationContract.Presenter iPresenter) {
        mEchographyImageVisualisationPresenter = iPresenter;
        mEchographyImageVisualisationPresenter.start();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main2, container, false);
    }

}
