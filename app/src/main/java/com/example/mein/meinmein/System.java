package com.example.mein.meinmein;

import android.app.Activity;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by MEIN on 04.05.2017.
 */

public class System {
    TextView Armor;
    Button AddArmor;
    TextView MaxDmg;
    Button addMaxDmg;
    TextView MinDmg;
    Button addMinDmg;
    TextView Income;
    Button AddIncome;
    Handler handler;
    Player player;
    Enemy enemy;
    TextView statusText;
    Shop shop;
    Activity MainActivity;
    final Timer t = new Timer(false);
    private int enemyStatus;
    private Runnable handlerTask = new Runnable(){

        @Override
        public void run() {
            if(enemy.getHp() != 0){
                enemy.attack(player);
            }
            if(player.getHp() == 0){
                statusText.setText("YOU DIED");
                enemyStop();
            }
            handler.postDelayed(this, 2000);
        }
    };
    public System(TextView Armor,
                  Button AddArmor,
                  TextView MaxDmg,
                  Button addMaxDmg,
                  TextView MinDmg,
                  Button addMinDmg,
                  TextView Income,
                  Button AddIncome,
                  final Handler handler,
                  final Player player,
                  final Enemy enemy,
                  final TextView statusText,
                  Shop shop,
                  Activity MainActivity){
        super();
        this.Armor = Armor;
        this.AddArmor = AddArmor;
        this.MaxDmg = MaxDmg;
        this.addMaxDmg = addMaxDmg;
        this.MinDmg = MinDmg;
        this.addMinDmg = addMinDmg;
        this.Income = Income;
        this.AddIncome = AddIncome;
        this.handler = handler;
        this.player = player;
        this.enemy = enemy;
        this.statusText = statusText;
        this.shop = shop;
        this.MainActivity = MainActivity;
    }

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
        statusText.setText("Not enough gold");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        statusText.setText(R.string.instructions);
                    }
                });
            }
        }, 2000);
    }
    public void warningMana(){
        statusText.setText("Not enough mana");
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                MainActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        statusText.setText(R.string.instructions);
                    }
                });
            }
        }, 2000);
    }
    public void start(){
        statusText.setText(R.string.instructions);
        Armor.setText("Armor: " + player.getArmor());
        MinDmg.setText("MinDmg: " + player.getMinDmg());
        MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
        AddArmor.setText("Armor\n" + shop.getPriceArmor());
        addMaxDmg.setText("Max Dmg\n" + shop.getPriceMaxDmg());
        addMinDmg.setText("Min Dmg\n" + shop.getPriceMinDmg());
        AddIncome.setText("Income\n" + shop.getPriceIncome());
        player.setGold();
        player.setMana();
        player.resetKills();
        player.resetIncome();
        addMinDmg.setEnabled(false);
        enemyStop();

    }
}
