package com.example.android.rollthedice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    private static final SecureRandom secureRandomNumbers = new SecureRandom();

    //these are named integer constant meaning its start with 0,1,2,3 .means they are integers inside enum
    private enum Status {NOTSTARTEDYET, PROCEED, WON, LOST};

    private static final int TIGER_CLAWS = 2;
    private static final int TREE = 3;
    private static final int CEVEN = 7;
    private static final int WE_LEVEN = 11;
    private static final int PANTHER = 12;

    TextView txtCalculations;
    ImageView imgDice;;
    Button btnRestartGame;
    ImageButton btnHelp;
    TextView txtHelp;

    String oldTxtCalculationsValue = "";
    boolean firstTime = true;
    Status gameStatus = Status.NOTSTARTEDYET;
    int points ;
    boolean help = true;
    String rules = "1. For first Roll, If you get 7 or 11, you will win. " + "\n" + "2. For 2nd Roll if you get same no as before you will win and if you get 7 , then you will lose"
            +"\n" +" otherwise you will get another chance";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtCalculations = (TextView) findViewById(R.id.txtCalculations);
        imgDice = (ImageView) findViewById(R.id.imgDice);
        btnRestartGame = (Button) findViewById(R.id.btnRestartTheGame);
        final TextView txtGameStatus = (TextView) findViewById(R.id.txtGameStatus);
        btnHelp = (ImageButton) findViewById(R.id.btnHelp);
        txtHelp = (TextView) findViewById(R.id.txtHelp);


        makeBtnRestartInvisible();
        makeTxtHelpInvisible();

        txtGameStatus.setText("");
        txtCalculations.setText("");

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(help) {
                    makeTxtHelpVisible();
                    txtHelp.setText(rules);
                    help = false;
                }
                else if(!help){
                    makeTxtHelpInvisible();
                    help = true;
                }

            }
        });

        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameStatus == Status.NOTSTARTEDYET){
                    int diceSum = letsRollTheDice();
                    oldTxtCalculationsValue = txtCalculations.getText().toString();
                    points = 0;

                    switch (diceSum){

                        case CEVEN: //7
                        case WE_LEVEN: //11
                                gameStatus = Status.WON;
                                txtGameStatus.setText("You Win!");
                                makeImgDiceInvisible();;
                                makeBtnRestartVisible();
                                break;
                        case TIGER_CLAWS: //2
                        case TREE://3
                        case PANTHER: //12
                            gameStatus = Status.LOST;
                            makeImgDiceInvisible();
                            makeBtnRestartVisible();
                            break;
                        case 4:
                        case 5:
                        case 6:
                        case 8:
                        case 9:
                        case 10:
                            gameStatus = Status.PROCEED;
                            points = diceSum;
                            txtCalculations.setText((oldTxtCalculationsValue + "Your Point is: " + points + "\n"));
                            oldTxtCalculationsValue = "Your Point is " + points + "\n";
                            break;

                    }
                    return;

                }
                if(gameStatus == Status.PROCEED){
                    int diceSum = letsRollTheDice();
                    if(diceSum == points){
                        gameStatus = Status.WON;
                        txtGameStatus.setText("You Won!!");
                        makeImgDiceInvisible();
                        makeBtnRestartVisible();

                    }else if(diceSum == CEVEN){
                        gameStatus = Status.LOST;
                        txtGameStatus.setText("You Lost!!");
                        makeImgDiceInvisible();
                        makeBtnRestartVisible();
                    }
                }
            }
        });

        btnRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStatus = Status.NOTSTARTEDYET;
                txtGameStatus.setText("");
                txtCalculations.setText("");
                oldTxtCalculationsValue = "";
                makeImgDiceVisible();
                makeBtnRestartInvisible();
            }
        });



    }

    private void makeImgDiceInvisible(){
        imgDice.setVisibility(View.INVISIBLE);
    }

    private void makeBtnRestartInvisible(){
        btnRestartGame.setVisibility(View.INVISIBLE);
    }

    private void makeImgDiceVisible(){
        imgDice.setVisibility(View.VISIBLE);
    }
    private void makeBtnRestartVisible(){
        btnRestartGame.setVisibility(View.VISIBLE);
    }

    private void makeTxtHelpInvisible(){ txtHelp.setVisibility(View.INVISIBLE);}
    private void makeTxtHelpVisible(){ txtHelp.setVisibility(View.VISIBLE);}

    private int letsRollTheDice(){
        int randDie1 = 1 + secureRandomNumbers.nextInt(6);  //because of 1  it will guess a no between 1-6
        int randDie2 = 1 + secureRandomNumbers.nextInt(6);
        int sum = randDie1 + randDie2;

        txtCalculations.setText(String.format(oldTxtCalculationsValue + "you rolled %d + %d = %d%n", randDie1, randDie2 ,sum));
        return sum;
    }
}
