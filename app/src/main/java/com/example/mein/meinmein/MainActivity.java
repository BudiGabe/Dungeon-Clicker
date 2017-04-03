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
        Button Magic = (Button) findViewById(R.id.magic);
        final TextView GoldAmount = (TextView) findViewById(R.id.goldAmount);
        class Player {
            private int gold;
            public void SetGold(){
                gold=0;
                GoldAmount.setText("Gold: " + gold);
            }
            public void GetGold(){
                gold += 10 + (int)(Math.random() * 21);
                GoldAmount.setText("Gold: " + gold);
            }
            public void Attack() {
                int hpEnemyCurrent = Hp_Enemy.getProgress();
                int randomDmgPlayer = 5 + (int)(Math.random() * 16);
                Hp_Enemy.setProgress(hpEnemyCurrent - randomDmgPlayer);
            }
        }
        class Enemy {
            public void Attack() {
                int hpPlayerCurrent = Hp_Player.getProgress();
                int randomDmgEnemy = 1 + (int) (Math.random() * 6);
                Hp_Player.setProgress(hpPlayerCurrent - randomDmgEnemy);
            }
        }
        final Player player= new Player();
        final Enemy enemy= new Enemy();
        Status.setText("Kill as many enemies before you die!");
        player.SetGold();
        Status.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                if((Hp_Enemy.getProgress() != 0) && (Hp_Player.getProgress() != 0)){
                    player.Attack();
                    enemy.Attack();
                    if(Hp_Player.getProgress() == 0){
                        Status.setText("YOU DIED");
                    }
                } else {
                    if(Hp_Enemy.getProgress()==0){
                        Hp_Enemy.setProgress(100);
                        player.GetGold();
                    }
                    if(Hp_Player.getProgress() == 0){
                        Status.setText("Kill as many enemies before you die!");
                        Hp_Enemy.setProgress(100);
                        Hp_Player.setProgress(100);
                        player.SetGold();

                    }
                }
            }
        });
    }
}