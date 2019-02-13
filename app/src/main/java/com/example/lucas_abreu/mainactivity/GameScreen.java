package com.example.lucas_abreu.mainactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameScreen extends Activity implements TimerFragment.RemoveTimerFragment {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TimerFragment timerFragment;
    RetryFragment retryFragment;
    EditText gameEditText;
    TextView gameTextView;
    TextView timerTextView;
    Thread phraseCountDown;
    Phrase phrase;
    int totalSeconds;
    int i;
    Handler handler;
    private static final String TAG = GameScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen);
        gameTextView = (TextView) findViewById(R.id.game_text_view);
        gameEditText = (EditText) findViewById(R.id.game_edit_text);
        timerTextView = (TextView) findViewById(R.id.timer_text_view);
        handler = new Handler();
        addTimerFragment();

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://typefastapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        PhraseClient phraseClient = retrofit.create(PhraseClient.class);
        Call<Phrase> call = phraseClient.getRandomRhrase();

        call.enqueue(new Callback<Phrase>() {
            @Override
            public void onResponse(Call<Phrase> call, Response<Phrase> response) {
                phrase = response.body();
                timerTextView.setText(String.valueOf(phrase.conteudo.length()));
                gameTextView.setText(phrase.conteudo);
            }

            @Override
            public void onFailure(Call<Phrase> call, Throwable t) {
                gameEditText.setText("uhull");
            }
        });
    }



    private void addTimerFragment(){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        timerFragment = new TimerFragment();
        fragmentTransaction.add(R.id.fragmentContainer, timerFragment);
        fragmentTransaction.commit();
    }

    private void addRetryFragment(){
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        retryFragment = new RetryFragment();
    }

    @Override
    public void removeTimerFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        TimerFragment timerFragment = (TimerFragment) fragmentManager.findFragmentById(R.id.fragmentContainer);
        if( timerFragment != null)
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(timerFragment).commit();
        }

        startPhraseCountDown();;

    }

    public void startPhraseCountDown(){
        phraseCountDown = new Thread(new PhraseCountDownTimer());
        phraseCountDown.start();

    }

    class PhraseCountDownTimer implements Runnable
    {
        @Override
        public void run() {

            Log.d(TAG, ""+phrase.conteudo.length());

            for(i=10; i>1; i--)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        timerTextView.setText(""+i);
                        if(i==1)
                            Log.d(TAG, "ACABOU O TEMPO!");
                    }
                });
            }

        }
    }



    public void print_lala(){

        Log.d(TAG, "LALA");
    }
}
