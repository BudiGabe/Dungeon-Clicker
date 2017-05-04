package com.example.mein.meinmein;

import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by MEIN on 04.05.2017.
 */

public class Shop {
    TextView Armor;
    Button AddArmor;
    TextView MaxDmg;
    Button addMaxDmg;
    TextView MinDmg;
    Button addMinDmg;
    TextView Income;
    Button AddIncome;
    private int priceArmor = 100;
    private int priceMinDmg = 100;
    private int priceMaxDmg = 100;
    private int priceIncome = 100;
    public Shop(TextView Armor,
            Button AddArmor,
            TextView MaxDmg,
            Button addMaxDmg,
            TextView MinDmg,
            Button addMinDmg,
            TextView Income,
            Button AddIncome){
        this.Armor = Armor;
        this.AddArmor = AddArmor;
        this.MaxDmg = MaxDmg;
        this.addMaxDmg = addMaxDmg;
        this.MinDmg = MinDmg;
        this.addMinDmg = addMinDmg;
        this.Income = Income;
        this.AddIncome = AddIncome;
    }
    public int getPriceArmor(){return priceArmor;}
    public int getPriceMinDmg(){return priceMinDmg;}
    public int getPriceMaxDmg(){return priceMaxDmg;}
    public int getPriceIncome(){return priceIncome;}
    public void buyArmor(Player player){
        player.addArmor();
        player.SpendGold(priceArmor);
        priceArmor += 50;
        Armor.setText("Armor: " + player.getArmor());
        AddArmor.setText("Armor\n" + priceArmor);
    }
    public void buyMaxDmg(Player player){
        player.addMaxDmg(2);
        player.SpendGold(priceMaxDmg);
        priceMaxDmg += 50;
        MaxDmg.setText("MaxDmg: " + player.getMaxDmg());
        addMaxDmg.setText("Max Dmg\n" + priceMaxDmg);
    }
    public void buyMinDmg(Player player){
        player.addMinDmg(2);
        player.SpendGold(priceMinDmg);
        priceMinDmg += 50;
        MinDmg.setText("MinDmg: " + player.getMinDmg());
        addMinDmg.setText("Min Dmg\n" + priceMinDmg);
    }
    public void buyIncome(Player player){
        player.addIncome();
        player.SpendGold(priceIncome);
        priceIncome += 50;
        Income.setText("Income: " + player.getIncome());
        AddIncome.setText("Income\n" + priceIncome);
    }
    public void reset(Player player){
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
