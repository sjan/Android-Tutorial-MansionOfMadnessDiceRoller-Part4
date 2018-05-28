package farsight.solutions.tutorial.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final String TAG = MainActivity.class.getName();
    MainPresenter presenter;
    Unbinder unbinder;

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

        //Presenter Binding
        bindPresenter(new MainPresenter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void redrawDice(List<Dice> list) {
        diceRollLayout.render(list);
    }

    @Override
    public void spinDice(int diceIndex) {
        diceRollLayout.spinDice(diceIndex);
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

    private void bindPresenter(MainPresenter mainPresenter) {
        presenter = mainPresenter;
        diceRollLayout.setPresenter(presenter);
        presenter.setView(this);
    }
}