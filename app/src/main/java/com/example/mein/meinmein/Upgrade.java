package com.example.mein.meinmein;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MEIN on 11.05.2017.
 */

public class Upgrade {
    TextView Armor;
    Button AddArmor;
    TextView MaxDmg;
    Button addMaxDmg;
    TextView MinDmg;
    Button addMinDmg;
    TextView Income;
    Button AddIncome;
    Player player;
    System system;
    public Upgrade(TextView Armor,
                Button AddArmor,
                TextView MaxDmg,
                Button addMaxDmg,
                TextView MinDmg,
                Button addMinDmg,
                TextView Income,
                Button AddIncome,
                   Player player,
                   System system){
        this.Armor = Armor;
        this.AddArmor = AddArmor;
        this.MaxDmg = MaxDmg;
        this.addMaxDmg = addMaxDmg;
        this.MinDmg = MinDmg;
        this.addMinDmg = addMinDmg;
        this.Income = Income;
        this.AddIncome = AddIncome;
        this.player = player;
        this.system = system;
    }

    public void upArmor(){
        player.addArmor();
        player.spendPoints();
        Armor.setText("Armor: " + player.getArmor());
    }
    public void upMaxDmg(){
        player.addMaxDmg(2);
        player.spendPoints();
        MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
    }
    public void upMinDmg(){
        player.addMinDmg(2);
        player.spendPoints();
        MinDmg.setText("MinDmg: " + player.getMinDmg());
    }
    public void upIncome(){
        player.addIncome();
        player.spendPoints();
        Income.setText("Income: " + player.getIncome());
    }
    public void reset(){
        Armor.setText("Armor: " + player.getArmor());
        Income.setText("Income: " + player.getIncome());
        MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
        MinDmg.setText("MinDmg: " + player.getMinDmg());

    }
    public void disable(){
        addMinDmg.setEnabled(false);
        addMaxDmg.setEnabled(false);
        AddArmor.setEnabled(false);
        AddIncome.setEnabled(false);
    }
    public void enable(){
        addMinDmg.setEnabled(true);
        addMaxDmg.setEnabled(true);
        AddArmor.setEnabled(true);
        AddIncome.setEnabled(true);
    }

}
