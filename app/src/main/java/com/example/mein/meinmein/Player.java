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
    Upgrade upgrade;
    private int xpTillLvl;
    private int gold;
    private int mana;
    private int minDmg = 5;
    private int maxDmg = 10;
    private int armor;
    private int kills;
    private int income;
    private int upgradePoints;
    private int lvl = 1;
    private int leech;
    private int dmgPlayer;
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
                  TextView currLvl,
                  Upgrade upgrade){
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
        this.upgrade = upgrade;
        xpTillLvl = getMaxXp() - getXp();
    }
    public void addDmg(int value){
        maxDmg += value;
        minDmg += value;
    }
    public void addArmor(int value){armor += value;}
    public void addIncome(int value){income += value;}
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
    public void recoverHp(int value){Hp_Player.incrementProgressBy(value);}
    public void resetHp(){Hp_Player.setProgress(100);}
    public int getKills() {return kills;}
    public int getIncome(){return income;}
    public void damage(int value){Hp_Player.setProgress(getHp() - value);}
    public void calculateDmgDealtTo(Enemy enemy){
        int randomDmgPlayer = minDmg + (int)(Math.random() * (maxDmg + 1));
        dmgPlayer = randomDmgPlayer - enemy.getArmor();
        if(dmgPlayer < 0){
            dmgPlayer = 0;
        }
    }
    public void attack(Enemy enemy) {
        calculateDmgDealtTo(enemy);
        enemy.damage(dmgPlayer);
        if(leech > 0){Hp_Player.incrementProgressBy(leech);}
    }
    public void addLeech(int valueOfLeech){leech += dmgPlayer*(valueOfLeech/100);}
    public void setGold(){
        gold=0;
        GoldAmount.setText("Gold: 0");
    }
    public void receiveGold(){
        gold += 20 + income;
        GoldAmount.setText("Gold: " + gold);
    }
    public int getGold(){return gold;}
    public void spendGold(int value){
        gold -= value;
        GoldAmount.setText("Gold: " + gold);
    }
    public int getPoints(){return upgradePoints;}
    public void spendPoints(){upgradePoints--;}
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

    public void resetLvl(){
        lvl = 1;
        currLvl.setText("1");
    }
    public void refreshXpTillLvl(){xpTillLvl = getMaxXp() - getXp(); }
    public void addRemainingXp(Enemy enemy){
        int remainingXp = enemy.getXpPerKill() - xpTillLvl;
        expBar.setProgress(remainingXp);
    }
    public void lvlUpAfterKill(Enemy enemy){
        lvl++;
        expBar.setMax(getMaxXp() + lvl*10);
        addRemainingXp(enemy);
        refreshXpTillLvl();
        upgradePoints++;
        currLvl.setText("" + lvl);
        upgrade.enable();
    }
    public void receiveXpAfterKill(Enemy enemy){ //WHY DO I GET x2 XP #FirstWorldProblem
        expBar.setProgress(getXp() + enemy.getXpPerKill());
        refreshXpTillLvl();
    }
    public boolean shouldLvlAfterKill(Enemy enemy){
        return enemy.getXpPerKill() >= xpTillLvl;
    }
    public void kill(Enemy enemy){
        enemy.resetHp();
        if(getKills() % 10 == 0){
            enemy.evolve();
        }
        addKills();
        receiveGold();
        receiveMana();
        if(shouldLvlAfterKill(enemy)){
            lvlUpAfterKill(enemy);
        }else{
            receiveXpAfterKill(enemy);
        }
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