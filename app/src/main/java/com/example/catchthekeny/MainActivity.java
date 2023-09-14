package com.example.catchthekeny;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView textTime;
    TextView textScore;
    TextView bestScoreText;
    int score;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    SharedPreferences sharedPreferences;
    int bestScore=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize

        textTime = findViewById(R.id.timeText);

        textScore = findViewById(R.id.scoreText);

        bestScoreText = findViewById(R.id.bestScoreText);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);

        imageArray=new ImageView[] {imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};




        sharedPreferences = this.getSharedPreferences("com.example.catchthekeny", Context.MODE_PRIVATE);


        int writeBestScore = sharedPreferences.getInt("BestScore",0);

        if(writeBestScore == 0){
            bestScoreText.setText("Best Score: ");
        }else{
            bestScoreText.setText("Best Score: " + writeBestScore);
        }

       score=0;

        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {
                textTime.setText("Time: " + l/1000);
            }

            @Override
            public void onFinish() {
                textTime.setText("Time off!");



                handler.removeCallbacks(runnable);
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }


                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you want to play again?");

                alert.setCancelable(false);

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //restart
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Game is finished!", Toast.LENGTH_SHORT).show();
                        System.exit(0);
                    }
                });
                alert.show();





                //Son skoru kaydetme.

                int bestScore=sharedPreferences.getInt("BestScore",0);
                if(bestScore == 0 || bestScore < score){
                    bestScore = score;
                    sharedPreferences.edit().putInt("BestScore",bestScore).apply();
                    Toast.makeText(MainActivity.this, "New Best Score is" + bestScore, Toast.LENGTH_SHORT).show();
                    bestScoreText.setText("Best score: " + bestScore);
                }
            }
        }.start();


        hideImages();
    }
    public void increaseScore(View view){
        score++;
        textScore.setText("Score: " + score);
    }
    public void hideImages(){

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for(ImageView image : imageArray){
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility((View.VISIBLE));
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);


    }
}