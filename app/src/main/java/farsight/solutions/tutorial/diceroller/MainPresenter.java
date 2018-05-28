package farsight.solutions.tutorial.diceroller;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    private static final int MAX_DICE_COUNT = 25;
    private static final String TAG = MainPresenter.class.getName();

    private List<Dice> diceList = new ArrayList<>();
    private MainView mainView;

    private int countDice(Dice.Face type) {
        int count = 0;
        for (Dice dice : diceList) {
            if (dice.diceVal == type) {
                count++;
            }
        }
        return count;
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;
        diceList.add(new Dice());

        mainView.redrawDice(diceList);
        updateDiceCount();
    }

    public void rollButtonClicked() {
        for (int index = 0; index < diceList.size(); index++) {
            Dice dice = diceList.get(index);
            if (!dice.hold) {
                dice.roll();
                mainView.spinDice(index);
            }
        }

        updateDiceCount();
    }

    public void addButtonClicked() {
        if (diceList.size() < MAX_DICE_COUNT) {
            diceList.add(new Dice());

            mainView.redrawDice(diceList);
            updateDiceCount();
        }
    }

    public void removeButtonClicked() {
        if (!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);

            mainView.redrawDice(diceList);
            updateDiceCount();
        }
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    public void diceInZone(int index, Zone type) {
        Dice dice = diceList.get(index);

        switch(type) {
            case HOLD:
                mainView.redrawDice(diceList);
                dice.setHold(true);
                break;
            case ROLL:
                mainView.redrawDice(diceList);
                dice.setHold(false);
                break;
            case SWITCH_MAGNIFY:
                dice.diceVal = Dice.Face.MAGNIFY;
                break;
            case SWITCH_BLANK:
                dice.diceVal = Dice.Face.BLANK;
                break;
            case SWITCH_STAR:
                dice.diceVal = Dice.Face.STAR;
                break;
        }
        updateDiceCount();

    }

    private void updateDiceCount() {
        mainView.updateDiceCount(
                diceList.size(),
                countDice(Dice.Face.BLANK),
                countDice(Dice.Face.MAGNIFY),
                countDice(Dice.Face.STAR));
    }
}
