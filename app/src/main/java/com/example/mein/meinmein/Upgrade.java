package com.example.mein.meinmein;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MEIN on 11.05.2017.
 */

public class Upgrade {
    TextView Armor;
    Button AddArmor;
    TextView MaxDmg;
    Button addDmg;
    TextView MinDmg;
    TextView Income;
    Button AddIncome;
    Player player;
    public Upgrade(TextView Armor,
                Button AddArmor,
                TextView MaxDmg,
                Button addDmg,
                TextView MinDmg,
                TextView Income,
                Button AddIncome){
        this.Armor = Armor;
        this.AddArmor = AddArmor;
        this.MaxDmg = MaxDmg;
        this.addDmg = addDmg;
        this.MinDmg = MinDmg;
        this.Income = Income;
        this.AddIncome = AddIncome;
    }

    public void upArmor(Player player){
        player.addArmor();
        player.spendPoints();
        Armor.setText("Armor: " + player.getArmor());
    }
    public void upDmg(Player player){
        player.addDmg(2);
        player.spendPoints();
        MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
        MinDmg.setText("MinDmg: " + player.getMinDmg());

    }

    public void upIncome(Player player){
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
        addDmg.setEnabled(false);
        AddArmor.setEnabled(false);
        AddIncome.setEnabled(false);
    }
    public void enable(){
        addDmg.setEnabled(true);
        AddArmor.setEnabled(true);
        AddIncome.setEnabled(true);
    }

}
