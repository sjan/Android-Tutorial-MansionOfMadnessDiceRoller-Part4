package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static farsight.solutions.tutorial.diceroller.Dice.Face.BLANK;
import static farsight.solutions.tutorial.diceroller.Dice.Face.MAGNIFY;
import static farsight.solutions.tutorial.diceroller.Dice.Face.STAR;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_DICE_COUNT = 25;
    private static final int ONE_SECOND = 1000;
    private static final int TWO_SECONDS = 2000;
    private static final int FULL_REVOLUTION = 360;
    private static final int THREE_REVOLUTION = 1080;
    private static final float CENTER = 0.5f;

    Random random = new Random();

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
        ListView listView = findViewById(R.id.dice_list);

        for(int i=0;i<diceList.size();i++) {
            Dice dice = diceList.get(i);

            if(!dice.hold) {
                dice.roll();
                View diceRowView = listView.getChildAt(i);
                if (diceRowView != null) {
                    ImageView diceView = diceRowView.findViewById(R.id.dice_icon);
                    int rotation = randomRotation();
                    int duration = randomDuration();

                    RotateAnimation rotate = new RotateAnimation(
                            0, rotation,
                            Animation.RELATIVE_TO_SELF, CENTER,
                            Animation.RELATIVE_TO_SELF, CENTER
                    );
                    rotate.setFillAfter(true);
                    rotate.setFillEnabled(true);
                    rotate.setDuration(duration);
                    rotate.setInterpolator(new DecelerateInterpolator());

                    diceView.startAnimation(rotate);
                }
            }
        }

        //notify adapter to update view
        diceAdapter.notifyDataSetChanged();
        updateDiceCount();
    }

    private int randomDuration() {
        return ONE_SECOND + random.nextInt(TWO_SECONDS);
    }

    private int randomRotation() {
        return FULL_REVOLUTION + random.nextInt(THREE_REVOLUTION);
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

    public void updateDiceCount() {
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
