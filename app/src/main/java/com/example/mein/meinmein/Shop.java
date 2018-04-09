package com.example.mein.meinmein;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Shop extends AppCompatActivity{
    Button leechSword;
    TextView goldAmountShop;
    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        leechSword = (Button) findViewById(R.id.leechSword);
        goldAmountShop = (TextView) findViewById(R.id.goldAmountShop);
        goldAmountShop.setText("Gold: ");
        leechSword.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                player.spendGold(200);
                player.addDmg(2);
                player.addLeech(50);
            }
        });
    }
}
