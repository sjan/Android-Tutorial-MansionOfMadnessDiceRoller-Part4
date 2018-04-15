package farsight.solutions.tutorial.diceroller;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    private static final int MAX_DICE_COUNT = 25;
    private static final String TAG = MainPresenter.class.getName();

    private List<Dice> diceList = new ArrayList<>();
    private MainView mainView;
    private Integer selectIndex = null;

    private int countDice(Dice.Face type) {
        int count =0;
        for(Dice dice : diceList) {
            if(dice.diceVal == type) {
                count++;
            }
        }
        return count;
    }

    public void setView(MainView mainView) {
        this.mainView = mainView;
        mainView.disableChangeButton();
        mainView.disableHoldButton();
        updateUI();
    }

    public void addButtonClicked() {
        if(diceList.size()<MAX_DICE_COUNT) {
            diceList.add(new Dice());
            updateUI();
        }
    }

    public void removeButtonClicked() {
        if(!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);
            updateUI();
        }
    }

    public void rollButtonClicked() {
        for(int index=0;index<diceList.size();index++) {
            Dice dice = diceList.get(index);
            if(!dice.hold) {
                dice.roll();
                mainView.rollDice(index);
            }
        }
        updateUI();
    }

    public List<Dice> getDiceList() {
        return diceList;
    }

    public void selectIndex(int diceIndex) {
        selectIndex = diceIndex;

        mainView.unhighlightAllDice();
        mainView.highlightDice(diceIndex);
        mainView.enableChangeButton();
        mainView.enableHoldButton(diceList.get(diceIndex).hold);
    }

    public void unselectIndex() {
        selectIndex = null;

        mainView.unhighlightAllDice();
        mainView.disableChangeButton();
        mainView.disableHoldButton();
    }

    public void changeButtonClicked() {
        if(selectIndex!=null) {
            diceList.get(selectIndex).nextValue();
            updateUI();
        }
    }

    public void holdButtonClick() {
        if(selectIndex!=null) {
            Dice dice = diceList.get(selectIndex);
            dice.toggleHold();
            mainView.enableHoldButton(dice.hold);
            updateUI();
        }
    }

    private void updateUI() {
        mainView.refreshDiceLayout();
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }
}
