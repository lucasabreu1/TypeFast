package com.example.lucas_abreu.mainactivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //addFragment();
        TextView logoText = (TextView) findViewById(R.id.logoText);
        Button startButton = (Button) findViewById(R.id.startButton);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/carter_one.ttf");
        logoText.setTypeface(customFont);
        startButton.setTypeface(customFont);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startGame();

            }
        });
    }

    public void startGame(){
        Intent intent = new Intent(this, GameScreen.class);
        startActivity(intent);
    }
}


