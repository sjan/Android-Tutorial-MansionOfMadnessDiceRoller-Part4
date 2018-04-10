package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_DICE_COUNT = 25;
    DiceAdapter diceAdapter;
    List <Dice> diceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting activity to layout
        setContentView(R.layout.activity_main);

        //Setup ListView and Adapter
        ListView listView = findViewById(R.id.dice_list);
        diceAdapter = new DiceAdapter(this, R.layout.dice_row, diceList);
        listView.setAdapter(diceAdapter);

        //Initialize Data
        diceAdapter.add(new Dice());
    }

    public void rollDice(View view) {
        //roll all dice that are not being held
        for(Dice dice : diceList) {
            if(!dice.hold)
                dice.roll();
        }

        //notify adapter to update view
        diceAdapter.notifyDataSetChanged();
    }

    public void addDice(View view) {
        if(diceList.size()< MAX_DICE_COUNT) {
            diceAdapter.add(new Dice());
        }
    }

    public void removeDice(View view) {
        if(!diceList.isEmpty()) {
            int lastIndex = diceList.size() - 1;
            diceAdapter.remove(diceAdapter.getItem(lastIndex));
        }
    }


}
