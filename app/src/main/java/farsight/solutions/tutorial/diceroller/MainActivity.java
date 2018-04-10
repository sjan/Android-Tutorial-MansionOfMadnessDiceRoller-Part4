package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static farsight.solutions.tutorial.diceroller.Dice.Face.BLANK;
import static farsight.solutions.tutorial.diceroller.Dice.Face.MAGNIFY;
import static farsight.solutions.tutorial.diceroller.Dice.Face.STAR;

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

        updateDiceCount();

    }

    public void rollDice(View view) {
        //roll all dice that are not being held
        for(Dice dice : diceList) {
            if(!dice.hold)
                dice.roll();
        }

        //notify adapter to update view
        diceAdapter.notifyDataSetChanged();
        updateDiceCount();
    }

    public void addDice(View view) {
        if(diceList.size()< MAX_DICE_COUNT) {
            diceAdapter.add(new Dice());
        }
        updateDiceCount();
    }

    public void removeDice(View view) {
        if(!diceList.isEmpty()) {
            int lastIndex = diceList.size() - 1;
            diceAdapter.remove(diceAdapter.getItem(lastIndex));
        }
        updateDiceCount();
    }

    private void updateDiceCount() {
        TextView totalCount = findViewById(R.id.total_count);
        totalCount.setText(Integer.toString(diceList.size()));

        TextView blankCount = findViewById(R.id.blank_count);
        blankCount.setText(Integer.toString(countDice(BLANK)));

        TextView magCount = findViewById(R.id.mag_count);
        magCount.setText(Integer.toString(countDice(MAGNIFY)));

        TextView starCount = findViewById(R.id.star_count);
        starCount.setText(Integer.toString(countDice(STAR)));

    }

    private int countDice(Dice.Face type) {
        int count =0;
        for(Dice dice : diceList) {
            if(dice.diceVal == type) {
                count++;
            }
        }
        return count;
    }
}
