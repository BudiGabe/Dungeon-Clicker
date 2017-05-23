package com.example.mein.meinmein;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;





public class MainActivity extends AppCompatActivity {
    ProgressBar Hp_Enemy;
    ProgressBar Hp_Player;
    ProgressBar expBar;
    TextView statusText;
    TextView Armor;
    TextView MinDmg;
    TextView MaxDmg;
    TextView KillCount;
    TextView Income;
    TextView currLvl;
    Player player;
    Enemy enemy;
    Handler handler;
    Shop shop;
    Upgrade upgrade;
    Status status;
    System system;
    Button addMinDmg;
    Button addMaxDmg;
    Button AddArmor;
    Button Magic;
    Button AddIncome;
    TextView GoldAmount;
    TextView ManaAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hp_Enemy = (ProgressBar) findViewById(R.id.Hp_Enemy);
        Hp_Player = (ProgressBar) findViewById(R.id.Hp_Player);
        expBar = (ProgressBar) findViewById(R.id.expBar);
        statusText = (TextView) findViewById(R.id.Status);
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
        currLvl = (TextView) findViewById(R.id.lvl);
        handler = new Handler();
        player = new Player( Hp_Player,
                Hp_Enemy,
                expBar,
                Armor,
                MinDmg,
                MaxDmg,
                KillCount,
                ManaAmount,
                GoldAmount,
                Income,
                currLvl,
                upgrade);
        enemy = new Enemy(Hp_Enemy,
                Hp_Player);
        shop = new Shop();
        upgrade = new Upgrade(Armor,
                AddArmor,
                MaxDmg,
                addMaxDmg,
                MinDmg,
                addMinDmg,
                Income,
                AddIncome,
                player,
                system);
        system = new System( Armor,
                AddArmor,
                MaxDmg,
                addMaxDmg,
                MinDmg,
                addMinDmg,
                Income,
                AddIncome,
                handler,
                player,
                enemy,
                statusText,
                upgrade,
                this);
        status = new Status( enemy,
                player,
                system,
                upgrade,
                statusText);
        system.start();
        statusText.setOnClickListener(new Status(enemy, player, system, upgrade, statusText));
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
                upgrade.upArmor();
                if(player.getPoints() == 0){
                    upgrade.disable();
                }
            }
        });
        addMaxDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                upgrade.upMaxDmg();
                if(player.getPoints() == 0){
                    upgrade.disable();
                }
            }
        });
        addMinDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                upgrade.upMinDmg();
                if(player.getPoints() == 0){
                    upgrade.disable();
                }
            }
        });
        AddIncome.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                upgrade.upIncome();
                if(player.getPoints() == 0){
                    upgrade.disable();
                }
            }
        });

    }
}