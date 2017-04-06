package com.example.mein.meinmein;

import android.os.Build;
import android.os.Handler;
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
        final TextView ManaAmount = (TextView) findViewById(R.id.mana);
        final Handler handler = new Handler();
        class Player {
            private int gold;
            private float mana;
            public void SetGold(){
                gold=0;
                GoldAmount.setText("Gold: 0");
            }
            public void ReceiveGold(){
                gold += 10 + (int)(Math.random() * 21);
                GoldAmount.setText("Gold: " + gold);
            }
            public void Attack() {
                int hpEnemyCurrent = Hp_Enemy.getProgress();
                int randomDmgPlayer = 5 + (int)(Math.random() * 16);
                Hp_Enemy.setProgress(hpEnemyCurrent - randomDmgPlayer);
            }
            public void ReceiveMana(){
                mana += 0.5;
                ManaAmount.setText("Mana: " + (int)mana);
            }
            public void SetMana(){
                mana = 0;
                ManaAmount.setText("Mana: 0" );
            }
            public void SpendMana(int a){
                mana -= a;
                ManaAmount.setText("Mana: " + (int) mana);
            }
            public float GetMana(){return mana;}
        }
        final Player player = new Player();
        class Enemy {
            public void Attack() {
                int hpPlayerCurrent = Hp_Player.getProgress();
                int randomDmgEnemy = 1 + (int) (Math.random() * 6);
                Hp_Player.setProgress(hpPlayerCurrent - randomDmgEnemy);
            }
        }
        final Enemy enemy = new Enemy();
        class System {
            private int enemyStatus;
            private Runnable handlerTask = new Runnable() {
                @Override
                public void run() {
                    if(Hp_Enemy.getProgress() != 0){
                        enemy.Attack();
                    }
                    if(Hp_Player.getProgress() == 0){
                        Status.setText("YOU DIED");
                        enemyStop();
                    }
                    handler.postDelayed(this, 1000);
                }
            };
            public void enemyStop(){
                handler.removeCallbacksAndMessages(null);
                enemyStatus = 0;
            }
            public void enemyStart(){
                enemyStatus = 1;
                handlerTask.run();
            }
            public int getEnemyStatus(){
                return enemyStatus;
            }
            public void start(){
                Status.setText("Kill as many enemies before you die!");
                player.SetGold();
                player.SetMana();
                enemyStop();
            }
        }
        final System system = new System();
        system.start();
        Status.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                if(system.getEnemyStatus() == 0){
                    system.enemyStart();
                }
                if((Hp_Enemy.getProgress() != 0) && (Hp_Player.getProgress() != 0)){
                    player.Attack();
                } else {
                    if(Hp_Enemy.getProgress() == 0){
                        Hp_Enemy.setProgress(100);
                        player.ReceiveGold();
                        player.ReceiveMana();
                    }
                    if(Hp_Player.getProgress() == 0){
                        Status.setText("Kill as many enemies before you die!");
                        Hp_Enemy.setProgress(100);
                        Hp_Player.setProgress(100);
                        player.SetGold();
                        player.SetMana();
                        system.enemyStop();
                    }
                }
            }
        });
        Magic.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(player.GetMana() >= 5){
                    Hp_Player.incrementProgressBy(50);
                    player.SpendMana(5);
                }

            }
        });


    }
}