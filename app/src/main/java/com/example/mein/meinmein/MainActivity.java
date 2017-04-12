package com.example.mein.meinmein;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


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
        final TextView Armor = (TextView) findViewById(R.id.Armor);
        final TextView MinDmg = (TextView) findViewById(R.id.CurrentMinDmg);
        final TextView MaxDmg = (TextView) findViewById(R.id.CurrentMaxDmg);
        final TextView KillCount = (TextView) findViewById(R.id.KillCount);
        final TextView Income = (TextView) findViewById(R.id.Income) ;
        Button Magic = (Button) findViewById(R.id.magic);
        final Button addMinDmg = (Button) findViewById(R.id.buttonMinDmg);
        final Button addMaxDmg = (Button) findViewById(R.id.buttonMaxDmg);
        final Button AddArmor = (Button) findViewById(R.id.buttonArmor);
        final Button AddIncome = (Button) findViewById(R.id.buttonIncome);
        final TextView GoldAmount = (TextView) findViewById(R.id.goldAmount);
        final TextView ManaAmount = (TextView) findViewById(R.id.mana);
        final Handler handler = new Handler();
        final Timer t = new Timer(false);
        class Player {
            private int gold;
            private int mana;
            private int minDmg = 5;
            private int maxDmg = 10;
            private int armor;
            private int kills;
            private int income;
            private int mitigation;
            public void addMinDmg(int value){minDmg += value;}
            public void addMaxDmg(int value){maxDmg += value;}
            public void addArmor(){armor++;}
            public void addIncome(){income += 20;}
            public void addMitigation(){mitigation++;}
            public void resetMitigation(){mitigation = 0;}
            public void addKills(){
                kills++;
                KillCount.setText("Kills: " + kills);
            }
            public void resetKills(){
                kills = 0;
                KillCount.setText("Kills: 0");
            }
            public void resetIncome(){
                income = 0;
                Income.setText("Income: " + income);
            }
            public int getMinDmg(){return minDmg;}
            public int getMaxDmg(){return maxDmg;}
            public int getArmor() {return armor;}
            public int getKills() {return kills;}
            public int getIncome(){return income;}
            public void attack() {
                int hpEnemyCurrent = Hp_Enemy.getProgress();
                int randomDmgPlayer = minDmg + (int)(Math.random() * (maxDmg + 1));
                int dmgPlayer = randomDmgPlayer - mitigation;
                if(dmgPlayer < 0){
                    dmgPlayer = 0;
                }
                Hp_Enemy.setProgress(hpEnemyCurrent - randomDmgPlayer);
            }
            public void setGold(){
                gold=0;
                GoldAmount.setText("Gold: 0");
            }
            public void receiveGold(){
                gold += 20 + income;
                GoldAmount.setText("Gold: " + gold);
            }
            public int getGold(){return gold;}
            public void SpendGold(int value){
                gold -= value;
                GoldAmount.setText("Gold: " + gold);
            }
            public void receiveMana(){
                mana += 1;
                ManaAmount.setText("Mana: " + mana);
            }
            public void setMana(){
                mana = 0;
                ManaAmount.setText("Mana: 0" );
            }
            public void spendMana(int value){
                mana -= value;
                ManaAmount.setText("Mana: " + mana);
            }
            public float getMana(){return mana;}
            public void reset() {
                Hp_Player.setProgress(100);
                setGold();
                setMana();
                resetKills();
                resetIncome();
                minDmg = 5;
                maxDmg = 10;
                armor = 0;
                Armor.setText("Armor: " + armor);
                MaxDmg.setText("MaxDmg: " + maxDmg);
                MinDmg.setText("MinDmg: " + minDmg);
            }
        }
        final Player player = new Player();
        class Enemy { //ask about add armor
            private int minDmg = 1;
            private int maxDmg = 5;
            //private int armor;
            public void attack() {
                int hpPlayerCurrent = Hp_Player.getProgress();
                int randomDmgEnemy = minDmg + (int) (Math.random() * (maxDmg + 1));
                int dmgEnemy = randomDmgEnemy - player.getArmor();
                if(dmgEnemy < 0){
                    dmgEnemy = 0;
                }
                Hp_Player.setProgress(hpPlayerCurrent - dmgEnemy);
            }
            public void evolve() {
                minDmg++;
                maxDmg++;
                player.addMitigation();
            }
            //public int getArmor(){return armor;}
            public void reset() {
                Hp_Enemy.setProgress(100);
                minDmg = 1;
                maxDmg = 5;
                player.resetMitigation();
            }

        }
        final Enemy enemy = new Enemy();
        class Shop{
            private int priceArmor = 100;
            private int priceMinDmg = 100;
            private int priceMaxDmg = 100;
            private int priceIncome = 100;
            public int getPriceArmor(){return priceArmor;}
            public int getPriceMinDmg(){return priceMinDmg;}
            public int getPriceMaxDmg(){return priceMaxDmg;}
            public int getPriceIncome(){return priceIncome;}
            public void buyArmor(){
                player.addArmor();
                player.SpendGold(priceArmor);
                priceArmor += 50;
                Armor.setText("Armor: " + player.getArmor());
                AddArmor.setText("Armor\n" + priceArmor);
            }
            public void buyMaxDmg(){
                player.addMaxDmg(2);
                player.SpendGold(priceMaxDmg);
                priceMaxDmg += 50;
                MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
                addMaxDmg.setText("Max Dmg\n" + priceMaxDmg);
            }
            public void buyMinDmg(){
                player.addMinDmg(2);
                player.SpendGold(priceMinDmg);
                priceMinDmg += 50;
                MinDmg.setText("MinDmg: " + player.getMinDmg());
                addMinDmg.setText("Min Dmg\n" + priceMinDmg);
            }
            public void buyIncome(){
                player.addIncome();
                player.SpendGold(priceIncome);
                priceIncome += 50;
                Income.setText("Income: " + player.getIncome());
                AddIncome.setText("Income\n" + priceIncome);
            }
            public void reset(){
                priceArmor = 100;
                Armor.setText("Armor: " + player.getArmor());
                AddArmor.setText("Armor\n" + priceArmor);
                priceIncome = 100;
                Income.setText("Income: " + player.getIncome());
                AddIncome.setText("Income\n" + priceIncome);
                priceMaxDmg = 100;
                MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
                addMaxDmg.setText("Max Dmg\n" + priceMaxDmg);
                priceMinDmg = 100;
                MinDmg.setText("MinDmg: " + player.getMinDmg());
                addMinDmg.setText("Min Dmg\n" + priceMinDmg);
            }
        }
        final Shop shop = new Shop();
        class System {
            private int enemyStatus;
            private Runnable handlerTask = new Runnable() {
                @Override
                public void run() {
                    if(Hp_Enemy.getProgress() != 0){
                        enemy.attack();
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
                                Status.setText(R.string.instructions);
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
                                Status.setText(R.string.instructions);
                            }
                        });
                    }
                }, 2000);
            }
            public void start(){
                Status.setText(R.string.instructions);
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
        final System system = new System();
        system.start();
        Status.setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                if(system.getEnemyStatus() == 0){
                    system.enemyStart();
                }
                if((Hp_Enemy.getProgress() != 0) && (Hp_Player.getProgress() != 0)){
                    player.attack();
                } else {
                    if(Hp_Enemy.getProgress() == 0){
                        Hp_Enemy.setProgress(100);
                        if(player.getKills() % 10 == 0){
                            enemy.evolve();
                        }
                        player.addKills();
                        player.receiveGold();
                        player.receiveMana();
                    }
                    if(Hp_Player.getProgress() == 0){
                        Status.setText(R.string.instructions);
                        enemy.reset();
                        player.reset();
                        shop.reset();
                        system.enemyStop();
                    }
                }
            }
        });
        Magic.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(player.getMana() >= 10){
                    Hp_Player.incrementProgressBy(50);
                    player.spendMana(10);
                }
                else{
                    system.warningMana();
                }


            }
        });
        AddArmor.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.getGold() >= shop.getPriceArmor()){
                    shop.buyArmor();
                }else{
                    system.warningGold();
                }
            }
        });
        addMaxDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.getGold() >= shop.getPriceMaxDmg()){
                    shop.buyMaxDmg();
                }else{
                    system.warningGold();
                }
                if(player.getMaxDmg() > player.getMinDmg() + 5)
                    addMinDmg.setEnabled(true);
            }
        });
        addMinDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.getGold() >= shop.getPriceMinDmg()){
                   shop.buyMinDmg();
                }else{
                    system.warningGold();
                }
                if(player.getMinDmg() > player.getMaxDmg() - 5){
                    addMinDmg.setEnabled(false);
                }
            }
        });
        AddIncome.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(player.getGold() >= shop.getPriceIncome()){
                    shop.buyIncome();
                }else {
                    system.warningGold();
                }
            }
        });

    }
}