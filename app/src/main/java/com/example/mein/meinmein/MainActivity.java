package com.example.mein.meinmein;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar Hp_Enemy = (ProgressBar) findViewById(R.id.Hp_Enemy);
        final ProgressBar Hp_Player = (ProgressBar) findViewById(R.id.Hp_Player);
        final TextView Status = (TextView) findViewById(R.id.Status);
        Button defend = (Button) findViewById(R.id.defend);
        final Button attack = ((Button) findViewById(R.id.attack));
        class Player {
            public void Attack() {
                int hpEnemyCurrent = Hp_Enemy.getProgress();
                int randomDmgPlayer = 5 + (int)(Math.random() * 16);
                Hp_Enemy.setProgress(hpEnemyCurrent - randomDmgPlayer);
            }
        }
        class Enemy {
            public void Attack() {
                int hpPlayerCurrent = Hp_Player.getProgress();
                int randomDmgEnemy = 5 + (int) (Math.random() * 16);
                Hp_Player.setProgress(hpPlayerCurrent - randomDmgEnemy);
            }
        }
        final Player player= new Player();
        final Enemy enemy= new Enemy();
        Status.setText("Choose an action");
        attack.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                player.Attack();
                enemy.Attack();
                if(Hp_Enemy.getProgress() == 0){
                    Status.setText("You Win!!");
                    attack.setEnabled(false);
                    Status.setOnClickListener(new TextView.OnClickListener(){
                        public void onClick(View v){
                            Hp_Enemy.setProgress(100);
                            Hp_Player.setProgress(100);
                            Status.setText("Choose an action");
                            attack.setEnabled(true);
                        }
                    });
                }
                if(Hp_Player.getProgress() == 0){
                    Status.setText("You lose! :(");
                    attack.setEnabled(false);
                    Status.setOnClickListener(new TextView.OnClickListener() {
                        public void onClick(View v) {
                            Hp_Enemy.setProgress(100);
                            Hp_Player.setProgress(100);
                            Status.setText("Choose an action");
                            attack.setEnabled(true);
                        }
                    });
                }
            }
        });

    }
}