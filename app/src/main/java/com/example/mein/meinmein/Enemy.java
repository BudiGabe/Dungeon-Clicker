package com.example.mein.meinmein;

import android.widget.ProgressBar;

/**
 * Created by MEIN on 03.05.2017.
 */

public class Enemy {
    ProgressBar Hp_Enemy;
    ProgressBar Hp_Player;
    private int minDmg = 1;
    private int maxDmg = 5;
    private int armor;
    private int xpPerKill = 20;
    private int lvl = 1;
    public Enemy(ProgressBar Hp_Enemy,
                 ProgressBar Hp_Player){
        this.Hp_Enemy = Hp_Enemy;
        this.Hp_Player = Hp_Player;
    }
    public int getHp(){return Hp_Enemy.getProgress();}
    public int getArmor(){return armor;}
    public void resetHp(){Hp_Enemy.setProgress(100);}
    public void damage(int value){Hp_Enemy.setProgress(getHp() - value);}
    public void attack(Player player) {
        int randomDmgEnemy = minDmg + (int) (Math.random() * (maxDmg + 1));
        int dmgEnemy = randomDmgEnemy - player.getArmor();
        if(dmgEnemy < 0){
            dmgEnemy = 0;
        }
       player.damage(dmgEnemy);
    }
    public int getXpPerKill(){return xpPerKill;}
    public void evolve() {
        lvl++;
        minDmg++;
        maxDmg++;
        armor++;
        xpPerKill += lvl*5;
    }
    public void reset() {
        resetHp();
        minDmg = 1;
        maxDmg = 5;
        armor = 0;
        xpPerKill = 20;
        lvl = 1;
    }
}
