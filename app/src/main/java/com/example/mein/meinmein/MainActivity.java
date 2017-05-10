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
    TextView statusText;
    TextView Armor;
    TextView MinDmg;
    TextView MaxDmg;
    TextView KillCount;
    TextView Income;
    Player player;
    Enemy enemy;
    Handler handler;
    Shop shop;
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
        handler = new Handler();
        player = new Player( Hp_Player,
                Hp_Enemy,
                Armor,
                MinDmg,
                MaxDmg,
                KillCount,
                ManaAmount,
                GoldAmount,
                Income);
        enemy = new Enemy(Hp_Enemy,
                Hp_Player);
        shop = new Shop(Armor,
                AddArmor,
                MaxDmg,
                addMaxDmg,
                MinDmg,
                addMinDmg,
                Income,
                AddIncome);
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
                shop,
                this);
        status = new Status( enemy,
                player,
                system,
                shop,
                statusText);
        system.start();
        statusText.setOnClickListener(new Status(enemy, player, system, shop, statusText));
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
                    shop.buyArmor(player);
                }else{
                    system.warningGold();
                }
            }
        });
        addMaxDmg.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                if(player.getGold() >= shop.getPriceMaxDmg()){
                    shop.buyMaxDmg(player);
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
                   shop.buyMinDmg(player);
                }else{
                    system.warningGold();
                }
                if(player.getMinDmg() > player.getMaxDmg() - 7){
                    addMinDmg.setEnabled(false);
                }
            }
        });
        AddIncome.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                if(player.getGold() >= shop.getPriceIncome()){
                    shop.buyIncome(player);
                }else {
                    system.warningGold();
                }
            }
        });

    }
}