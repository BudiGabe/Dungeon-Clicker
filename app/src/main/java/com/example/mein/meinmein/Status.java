package com.example.mein.meinmein;

import android.view.View;
import android.widget.TextView;

/**
 * Created by MEIN on 14.04.2017.
 */

public class Status implements View.OnClickListener {
    Enemy enemy;
    Player player;
    System system;
    Upgrade upgrade;
    TextView statusText;
    public Status(Enemy enemy,
                  Player player,
                  System system,
                  Upgrade upgrade,
                  TextView statusText){
        this.enemy = enemy;
        this.player = player;
        this.system = system;
        this.upgrade = upgrade;
        this.statusText = statusText;
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
                player.kill(enemy);
            }
            if(player.getHp() == 0){
                statusText.setText(R.string.instructions);
                enemy.reset();
                player.reset();
                upgrade.reset();
                system.enemyStop();
            }
        }
    }

}


