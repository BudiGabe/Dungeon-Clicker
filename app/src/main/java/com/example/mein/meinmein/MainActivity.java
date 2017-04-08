package com.example.mein.meinmein;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar Hp_Enemy = (ProgressBar) findViewById(R.id.Hp_Enemy);
        final ProgressBar Hp_Player = (ProgressBar) findViewById(R.id.Hp_Player);
        final TextView Status = (TextView) findViewById(R.id.Status);
        final TextView Hp = (TextView) findViewById(R.id.CurrentHp);
        final TextView MinDmg = (TextView) findViewById(R.id.CurrentMinDmg);
        final TextView MaxDmg = (TextView) findViewById(R.id.CurrentMaxDmg);
        Button Magic = (Button) findViewById(R.id.magic);
        final Button AddMinDmg = (Button) findViewById(R.id.buttonMinDmg);
        Button AddMaxDmg = (Button) findViewById(R.id.buttonMaxDmg);
        Button AddMaxHp = (Button) findViewById(R.id.buttonHp);
        final TextView GoldAmount = (TextView) findViewById(R.id.goldAmount);
        final TextView ManaAmount = (TextView) findViewById(R.id.mana);
        final Handler handler = new Handler();
        final Timer t = new Timer(false);
        class Player {
            private int gold;
            private int mana;
            private int minDmg = 5;
            private int maxDmg = 10;
            public void AddMinDmg(int value){minDmg += value;}
            public void AddMaxDmg(int value){maxDmg += value;}
            public void AddMaxHp(int value){Hp_Player.setMax(Hp_Player.getMax() + value);}
            public int GetMinDmg(){return minDmg;}
            public int GetMaxDmg(){return maxDmg;}
            public void Attack() {
                int hpEnemyCurrent = Hp_Enemy.getProgress();
                int randomDmgPlayer = minDmg + (int)(Math.random() * (maxDmg + 1));
                Hp_Enemy.setProgress(hpEnemyCurrent - randomDmgPlayer);
            }
            public void SetGold(){
                gold=0;
                GoldAmount.setText("Gold: 0");
            }
            public void ReceiveGold(){
                gold += 10 + (int)(Math.random() * 21);
                GoldAmount.setText("Gold: " + gold);
            }
            public int GetGold(){return gold;}
            public void SpendGold(int value){
                gold -= value;
                GoldAmount.setText("Gold: " + gold);
            }
            public void ReceiveMana(){
                mana += 1;
                ManaAmount.setText("Mana: " + mana);
            }
            public void SetMana(){
                mana = 0;
                ManaAmount.setText("Mana: 0" );
            }
            public void SpendMana(int value){
                mana -= value;
                ManaAmount.setText("Mana: " + mana);
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
        class Shop{
            private int priceHp = 100;
            private int priceMinDmg = 100;
            private int priceMaxDmg = 100;
            public int getPriceHp(){return priceHp;}
            public int getPriceMinDmg(){return priceMinDmg;}
            public int getPriceMaxDmg(){return priceMaxDmg;}
            public void buyHP(){
                player.AddMaxHp(20);
                player.SpendGold(priceHp);
                priceHp += 50;
                Hp.setText("HP: " + Hp_Player.getMax());
            }
            public void buyMaxDmg(){
                player.AddMaxDmg(2);
                player.SpendGold(priceMaxDmg);
                priceMaxDmg += 50;
                MaxDmg.setText("MaxDmg: " + player.GetMaxDmg());
            }
            public void buyMinDmg(){
                player.AddMinDmg(2);
                player.SpendGold(priceMinDmg);
                priceMinDmg += 50;
                MinDmg.setText("MinDmg: " + player.GetMinDmg());
            }
        }
        final Shop shop = new Shop();
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
            public void warningGold(){
                Status.setText("Not enough gold");
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Status.setText("Kill as many enemies before you die!");
                            }
                        });
                    }
                }, 2000);
            }
            public void warningMana(){
                Status.setText("Not enough mana");
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Status.setText("Kill as many enemies before you die!");
                            }
                        });
                    }
                }, 2000);
            }
            public void start(){
                Status.setText("Kill as many enemies before you die!");
                Hp.setText("HP: " + Hp_Player.getMax());
                MinDmg.setText("MinDmg: " + player.GetMinDmg());
                MaxDmg.setText("MaxDmg: " + player.GetMaxDmg());
                player.SetGold();
                player.SetMana();
                AddMinDmg.setEnabled(false);
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
                if(player.GetMana() >= 10){
                    Hp_Player.incrementProgressBy(50);
                    player.SpendMana(10);
                }
                else{
                    system.warningMana();
                }


            }
        });
        AddMaxHp.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.GetGold() >= shop.getPriceHp()){
                    shop.buyHP();
                }else{
                    system.warningGold();
                }
            }
        });
        AddMaxDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.GetGold() >= shop.getPriceMaxDmg()){
                    shop.buyMaxDmg();
                }else{
                    system.warningGold();
                }
                if(player.GetMaxDmg() > player.GetMinDmg() + 5)
                    AddMinDmg.setEnabled(true);
            }
        });
        AddMinDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.GetGold() >= shop.getPriceMinDmg()){
                   shop.buyMinDmg();
                }else{
                    system.warningGold();
                }
                if(player.GetMinDmg() > player.GetMaxDmg() - 5){
                    AddMinDmg.setEnabled(false);
                }
            }
        });

    }
}