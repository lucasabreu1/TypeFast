package com.example.lucas_abreu.mainactivity;

import android.app.Fragment;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TimerFragment extends Fragment {

    private static final String TAG = TimerFragment.class.getSimpleName();

    RemoveTimerFragment removeListener;
    Thread timerThread;
    Handler handler;
    TextView counter;
    int i;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof RemoveTimerFragment){
            removeListener  = (RemoveTimerFragment) context;
        }else{
            throw new ClassCastException(context.toString() + " must implement RemoveFragmentListener");
        }
        Log.d(TAG, "onAttach() called");
    }



    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() called");
        return inflater.inflate(R.layout.timer_fragment, container, false);
    }

    @Nullable
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() called");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        counter = (TextView) getView().findViewById(R.id.counter);
        handler = new Handler();
        timerThread = new Thread(new TimerThread());
        timerThread.start();
    }

    class TimerThread implements Runnable{
        @Override
        public void run() {

            for(i=3; i>=1; i--)
            {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(""+i);
                    }
                });
            }
            removeListener.removeTimerFragment();

        }
    }

    interface RemoveTimerFragment
    {
        void removeTimerFragment();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
}
