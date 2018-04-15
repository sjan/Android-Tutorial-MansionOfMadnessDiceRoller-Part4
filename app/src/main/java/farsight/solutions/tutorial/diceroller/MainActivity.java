package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getName();
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

        // specify an adapter (see also next example)
        diceRollLayout.setPresenter(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void highlightDice(int diceIndex) {
        DiceView diceView = (DiceView) diceRollLayout.getChildAt(diceIndex);
        diceView.highlight();
    }

    @Override
    public void unhighlightAllDice() {
        for (int i = 0; i < diceRollLayout.getChildCount(); i++) {
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
        if (hold) {
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
    public void addDiceToView() {
        diceRollLayout.render();
    }

    @Override
    public void removeDiceFromView() {
        diceRollLayout.render();
    }

    @Override
    public void spinDice(int diceIndex) {
        diceRollLayout.spinDice(diceIndex);
    }

    @Override
    public void flipDiceView(int diceIndex) {
        diceRollLayout.render();
    }

    @Override
    public void holdDiceView(int diceIndex) {
        diceRollLayout.render();
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

    public void onClickHoldButton(View view) {
        presenter.holdButtonClick();
    }

    public void onClickChangeButton(View view) {
        presenter.changeButtonClicked();
    }
}