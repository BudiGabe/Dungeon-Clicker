package com.example.mein.meinmein;

import android.widget.ProgressBar;
import android.widget.TextView;



/**
 * Created by MEIN on 14.04.2017.
 */

public class Player {
    ProgressBar Hp_Player;
    ProgressBar Hp_Enemy;
    ProgressBar expBar;
    TextView Armor;
    TextView MinDmg;
    TextView MaxDmg;
    TextView KillCount;
    TextView ManaAmount;
    TextView GoldAmount;
    TextView Income;
    TextView currLvl;
    private int gold;
    private int mana;
    private int minDmg = 5;
    private int maxDmg = 10;
    private int armor;
    private int kills;
    private int income;
    private int upgradePoints;
    private int lvl = 1;
    public Player(ProgressBar Hp_Player,
                  ProgressBar Hp_Enemy,
                  ProgressBar expBar,
                  TextView Armor,
                  TextView MinDmg,
                  TextView MaxDmg,
                  TextView KillCount,
                  TextView ManaAmount,
                  TextView GoldAmount,
                  TextView Income,
                  TextView currLvl){
        this.Hp_Player = Hp_Player;
        this.Hp_Enemy = Hp_Enemy;
        this.expBar = expBar;
        this.Armor = Armor;
        this.MinDmg = MinDmg;
        this.MaxDmg = MaxDmg;
        this.KillCount = KillCount;
        this.ManaAmount = ManaAmount;
        this.GoldAmount = GoldAmount;
        this.Income = Income;
        this.currLvl = currLvl;

    }
    public void addMinDmg(int value){minDmg += value;}
    public void addMaxDmg(int value){maxDmg += value;}
    public void addArmor(){armor++;}
    public void addIncome(){income += 20;}
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
    public int getHp(){return Hp_Player.getProgress();}
    public void resetHp(){Hp_Player.setProgress(100);}
    public int getKills() {return kills;}
    public int getIncome(){return income;}
    public void damage(int value){Hp_Player.setProgress(getHp() - value);}
    public void attack(Enemy enemy) {
        int randomDmgPlayer = minDmg + (int)(Math.random() * (maxDmg + 1));
        int dmgPlayer = randomDmgPlayer - enemy.getArmor();
        if(dmgPlayer < 0){
            dmgPlayer = 0;
        }
        enemy.damage(dmgPlayer);
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
    public int getXp(){
        return expBar.getProgress();
    }
    public int getMaxXp(){
        return expBar.getMax();
    }
    public void resetXp(){
        expBar.setProgress(0);
        expBar.setMax(100);
    }
    public void lvlUp(){
        expBar.setProgress(0);
        lvl++;
        expBar.setMax(expBar.getMax() + lvl*10);
        upgradePoints++;
        currLvl.setText("" + lvl);
    }
    public void resetLvl(){
        lvl = 1;
        currLvl.setText("1");
    }
    public void receiveXp(Enemy enemy){
        int xpTillLvl = getMaxXp() - getXp();
        if(enemy.getXpPerKill() >= xpTillLvl){
            lvlUp();
            int remainingXp = enemy.getXpPerKill() - xpTillLvl;
            expBar.setProgress(getXp() + remainingXp);
        }else {expBar.setProgress(getXp() + enemy.getXpPerKill());}
    }

    public void reset() {
        resetHp();
        setGold();
        setMana();
        resetKills();
        resetIncome();
        resetXp();
        resetLvl();
        minDmg = 5;
        maxDmg = 10;
        armor = 0;
        Armor.setText("Armor: " + armor);
        MaxDmg.setText("MaxDmg: " + maxDmg);
        MinDmg.setText("MinDmg: " + minDmg);
    }
}