package com.example.dicegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android .widget.Button;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int overallUserScore = 0;
    int currentUserScore= 0;

    int currentComputerScore = 0;
    int overallComputerScore = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the button View
        Button roll = findViewById(R.id.button_Roll);
        Button hold = findViewById(R.id.button_Hold);
        Button reset = findViewById(R.id.button_Reset);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Anything that is inside of here will execute when we click the button
                //Toast.makeText(MainActivity.this, "Clicked" , Toast.LENGTH_SHORT).show();

                //Random number generator
                Random dice = new Random();

                int randomNumberGenerated = dice.nextInt(6) + 1;

                Toast.makeText(MainActivity.this, "" + randomNumberGenerated, Toast.LENGTH_SHORT).show();

                ImageView diceFace = findViewById(R.id.imageView_dice);


                if (randomNumberGenerated == 1) {
                    //update the ui show the 1 day
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice1));

                }

                if (randomNumberGenerated == 2) {
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice2));
                }

                if (randomNumberGenerated == 3) {
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice3));
                }
                if (randomNumberGenerated == 4) {
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice4));
                }
                if (randomNumberGenerated == 5) {
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice5));
                }
                if (randomNumberGenerated == 6) {
                    diceFace.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice6));

                }


                TextView userScoreView = findViewById(R.id.textView_Player);


                if(randomNumberGenerated== 1) {
                    currentUserScore = 0;
                    userScoreView.setText("Player Score" + overallUserScore);
                    //let the computer take turn
                    startComputerTurn();
                }else{
                    //old view     plus randomNumber that was rolled
                       currentUserScore = currentUserScore + randomNumberGenerated;
                       userScoreView.setText("Player Score "+currentUserScore);

                    }
                }
        });
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the points they got during this turn into their overall score
                overallUserScore = overallUserScore + currentUserScore;
                //display overall score
                TextView userScoreView = findViewById(R.id.textView_Player);
                userScoreView.setText("Player Score" + overallUserScore);

                if(overallUserScore >= 100) {
                    userScoreView.setText("Player won with score of " + overallUserScore);
                    currentUserScore = 0;
                }else{
                    startComputerTurn();
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private int rollDice() {
        //Create a Random object instance - this will help us generate a random number
        Random random = new Random();

        //This generates a random number between 0-6 excluding 6
        int num = random.nextInt(6);
        num = num + 1; //We add one so that we get numbers between 1 - 6

        return num;
    }

    private void displayUI(int num) {
        //get the imageView
        final ImageView image = findViewById(R.id.imageView_dice);
        if (num == 1) {
            //if the number rolled is one display the first dice
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice1));
        } else if (num == 2) {
            //if the number rolled is one display the 2nd dice
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice2));
        } else if (num == 3) {
            //if the number rolled is one display the 3rd dice
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice3));
        } else if (num == 4) {
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice4));
        } else if (num == 5) {
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice5));
        } else if (num == 6) {
            image.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dice6));
        }

    }

    //This method displays Toast message and starts computerTurn
    private void startComputerTurn() {
        Toast.makeText(MainActivity.this, "Computer is going", Toast.LENGTH_SHORT).show();

        //start the computer turn and roll the Dice
        computerTurn(rollDice());
    }

    //This method disables the buttons
    //Creates a new Thread
    //Rolls the Dice for the computer
    //and calls the method recusively
    private void computerTurn(final int numRolled) {

        //Find the Views
        final TextView computerScoreView = findViewById(R.id.textView_Computer);
        final TextView userScoreView = findViewById(R.id.textView_Player);
        final Button roll = findViewById(R.id.button_Roll);
        final Button hold = findViewById(R.id.button_Hold);
        final Button reset = findViewById(R.id.button_Reset);

        //Disable the buttons
        roll.setEnabled(false);
        hold.setEnabled(false);
        reset.setEnabled(false);

        //Create a new Thread
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Call Display UI to show the correct image
                displayUI(numRolled);
                Toast.makeText(getApplicationContext(), "rolled" + numRolled, Toast.LENGTH_SHORT).show(); //show what number was rolled

                //Add the rolled number to the current score
                currentComputerScore = currentComputerScore + numRolled;

                //Determine if the computer rolled a 1
                if (numRolled == 1) {

                    //if the computer rolled a one
                    currentComputerScore = 0; //set the current score to zero
                    computerScoreView.setText("Overall Comp Score " + overallComputerScore); //display the overall score

                    //Enable the buttons
                    roll.setEnabled(true);
                    hold.setEnabled(true);
                    reset.setEnabled(true);

                } else if (currentComputerScore > 5) {

                    //if the currentComputerScore is greater than 5
                    computerScoreView.setText("Current score " + currentComputerScore); //show the current score

                    //Enable the buttons
                    roll.setEnabled(true);
                    hold.setEnabled(true);
                    reset.setEnabled(true);

                    //update the overall score
                    overallComputerScore = overallComputerScore + currentComputerScore;
                    currentComputerScore = 0;

                    //check if the score is 1000
                    if (overallComputerScore >= 100) {
                        //if the score is 100 or greater then tell the use they lost
                        computerScoreView.setText("Computer Won with " + overallComputerScore);
                        userScoreView.setText("You lost");
                    }

                    //show the overall score of the computer
                    computerScoreView.setText("Overall Comp Score " + overallComputerScore);


                } else {
                    //show the current score of the computer
                    computerScoreView.setText("Current score " + currentComputerScore);
                    //re roll for the computer
                    computerTurn(rollDice());

                }

            }
        }, 2000);

    }

    private void reset() {
        //reset all the values to zero
        overallComputerScore = 0;
        overallUserScore = 0;
        currentComputerScore = 0;
        currentUserScore = 0;

        //get views that will be used
        TextView computerScoreView = findViewById(R.id.textView_Computer);
        TextView userScoreView = findViewById(R.id.textView_Player);

        //reset the text for the views
        computerScoreView.setText("Computer Score : 0");
        userScoreView.setText("Player Score: 0");

    }


}

