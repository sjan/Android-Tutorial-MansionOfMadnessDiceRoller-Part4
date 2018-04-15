package farsight.solutions.tutorial.diceroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiceView extends CircleImageView {
    Dice dice;

    public DiceView(Context context) {
        this(context, null);
    }

    public DiceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DiceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void refreshDiceFace() {
        if(dice == null)
            return;
        else if (dice.hold) {
            switch (dice.diceVal) {
                case BLANK:
                    setImageResource(R.drawable.blank_dice_grey);
                    break;
                case MAGNIFY:
                    setImageResource(R.drawable.magnifying_glass_grey);
                    break;
                case STAR:
                    setImageResource(R.drawable.star_grey);
                    break;
            }
        } else {
            switch (dice.diceVal) {
                case BLANK:
                    setImageResource(R.drawable.blank_dice);
                    break;
                case MAGNIFY:
                    setImageResource(R.drawable.magnifying_glass);
                    break;
                case STAR:
                    setImageResource(R.drawable.star);
                    break;
            }
        }
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void highlight() {
        this.setBorderWidth(6);

    }

    public void unhighlight() {
        this.setBorderWidth(0);
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
