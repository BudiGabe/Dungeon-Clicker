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
    ProgressBar Hp_Enemy;
    ProgressBar Hp_Player;
    TextView Status;
    TextView Armor;
    TextView MinDmg;
    TextView MaxDmg;
    TextView KillCount;
    TextView Income;
    Player player;
    Enemy enemy;
    Handler handler;
    Shop shop;
    System system;
    Button addMinDmg;
    Button addMaxDmg;
    Button AddArmor;
    Button Magic;
    Button AddIncome;
    TextView GoldAmount;
    TextView ManaAmount;
    class Enemy { //ask about add armor
        private int minDmg = 1;
        private int maxDmg = 5;
        public int getHp(){return Hp_Enemy.getProgress();}
        public void resetHp(){Hp_Enemy.setProgress(100);}
        public void damage(int value){
            Hp_Enemy.setProgress(getHp() - value);
        }
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
    class System {
        final Timer t = new Timer(false);
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
                handler.postDelayed(this, 2000);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hp_Enemy = (ProgressBar) findViewById(R.id.Hp_Enemy);
        Hp_Player = (ProgressBar) findViewById(R.id.Hp_Player);
        Status = (TextView) findViewById(R.id.Status);
        Armor = (TextView) findViewById(R.id.Armor);
        MinDmg = (TextView) findViewById(R.id.CurrentMinDmg);
        MaxDmg = (TextView) findViewById(R.id.CurrentMaxDmg);
        KillCount = (TextView) findViewById(R.id.KillCount);
        Income = (TextView) findViewById(R.id.Income) ;
        Magic = (Button) findViewById(R.id.magic);
        addMinDmg = (Button) findViewById(R.id.buttonMinDmg);
        addMaxDmg = (Button) findViewById(R.id.buttonMaxDmg);
        AddArmor = (Button) findViewById(R.id.buttonArmor);
        AddIncome = (Button) findViewById(R.id.buttonIncome);
        GoldAmount = (TextView) findViewById(R.id.goldAmount);
        ManaAmount = (TextView) findViewById(R.id.mana);
        handler = new Handler();
        player = new Player( Hp_Player,
                Armor,
                MinDmg,
                MaxDmg,
                KillCount,
                ManaAmount,
                GoldAmount,
                Income);

        enemy = new Enemy();
        shop = new Shop();
        system = new System();
        system.start();
        Status.setOnClickListener(new Status(enemy, player, system, shop, Status));
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