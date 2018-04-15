package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int ONE_SECOND = 1000;
    private static final int TWO_SECONDS = 2000;
    private static final int FULL_REVOLUTION = 360;
    private static final int THREE_REVOLUTION = 1080;
    private static final float CENTER = 0.5f;

    Random random = new Random();

    MainPresenter presenter;
    Unbinder unbinder;

    @BindView(R.id.hold_button)
    Button holdButton;

    @BindView(R.id.change_button)
    Button changeButton;

    @BindView(R.id.total_count)
    TextView totalCount;

    @BindView(R.id.blank_count)
    TextView blankCount;

    @BindView(R.id.mag_count)
    TextView magCount;

    @BindView(R.id.star_count)
    TextView starCount;

    @BindView(R.id.dice_canvas)
    DiceRollLayout diceRollLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting activity to layout
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        //Presenter
        presenter = new MainPresenter();
        presenter.setView(this);

        diceRollLayout.setPresenter(presenter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void rollDice(int diceIndex) {
        //roll all dice that are not being held
        View diceView = diceRollLayout.getChildAt(diceIndex);

        if (diceView != null) {
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

    @Override
    public void refreshDiceLayout() {
        diceRollLayout.render();
    }

    @Override
    public void highlightDice(int diceIndex) {
        DiceView diceView = (DiceView) diceRollLayout.getChildAt(diceIndex);
        diceView.highlight();
    }

    @Override
    public void unhighlightAllDice() {
        for(int i=0;i<diceRollLayout.getChildCount();i++) {
            DiceView diceView = (DiceView) diceRollLayout.getChildAt(i);
            diceView.unhighlight();
        }
    }

    @Override
    public void disableChangeButton() {
        changeButton.setEnabled(false);
    }

    @Override
    public void enableChangeButton() {
        changeButton.setEnabled(true);
    }

    @Override
    public void enableHoldButton(boolean hold) {
        holdButton.setEnabled(true);
        if(hold) {
            holdButton.setText(R.string.hold_button_unhold_label);
        } else {
            holdButton.setText(R.string.hold_button_hold_label);
        }
    }

    @Override
    public void disableHoldButton() {
        holdButton.setEnabled(false);
    }

    @Override
    public void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice) {
        totalCount.setText(Integer.toString(totalDice));
        blankCount.setText(Integer.toString(blankDice));
        magCount.setText(Integer.toString(magDice));
        starCount.setText(Integer.toString(starDice));
    }

    public void onClickAddButton(View view) {
        presenter.addButtonClicked();
    }

    public void onClickRemoveButton(View view) {
        presenter.removeButtonClicked();
    }

    public void onClickRollButton(View view) {
        presenter.rollButtonClicked();
    }

    public void onClickChangeButton(View view) {
        presenter.changeButtonClicked();
    }

    public void onClickHoldButton(View view) {
        presenter.holdButtonClick();
    }

    private int randomDuration() {
        return ONE_SECOND + random.nextInt(TWO_SECONDS);
    }

    private int randomRotation() {
        return FULL_REVOLUTION + random.nextInt(THREE_REVOLUTION);
    }
}