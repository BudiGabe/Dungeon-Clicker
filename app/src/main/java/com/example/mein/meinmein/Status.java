package com.example.mein.meinmein;

import android.view.View;
import android.widget.TextView;

/**
 * Created by MEIN on 14.04.2017.
 */

public class Status implements View.OnClickListener {
    MainActivity.Enemy enemy;
    Player player;
    MainActivity.System system;
    MainActivity.Shop shop;
    TextView Status;
    public Status(MainActivity.Enemy enemy, Player player, MainActivity.System system, MainActivity.Shop shop, TextView Status){
        this.enemy = enemy;
        this.shop = shop;
        this.player = player;
        this.system = system;
        this.Status = Status;
    }
    @Override
    public void onClick(View v) {
        if(system.getEnemyStatus() == 0){
            system.enemyStart();
        }
        if((enemy.getHp() != 0) && (player.getHp() != 0)){
            player.attack(enemy);
        } else {
            if(enemy.getHp() == 0){
                enemy.resetHp();
                if(player.getKills() % 10 == 0){
                    enemy.evolve();
                }
                player.addKills();
                player.receiveGold();
                player.receiveMana();
            }
            if(player.getHp() == 0){
                Status.setText(R.string.instructions);
                enemy.reset();
                player.reset();
                shop.reset();
                system.enemyStop();
            }
        }
    }

}


