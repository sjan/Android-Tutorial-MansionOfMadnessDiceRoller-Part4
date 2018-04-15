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

    }

    public void rollButtonClicked() {
        for(int index=0;index<diceList.size();index++) {
            Dice dice = diceList.get(index);
            if(!dice.hold) {
                dice.roll();
                mainView.spinDice(index);
            }
        }
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }

    public void addButtonClicked() {
        if(diceList.size()<MAX_DICE_COUNT) {
            diceList.add(new Dice());
            mainView.addDiceToView();
            mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
        }
    }

    public void removeButtonClicked() {
        if(!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);

            mainView.removeDiceFromView();
            mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
        }
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

        mainView.removeDiceFromView();
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));

    }

    public void changeButtonClicked() {
        Dice dice = diceList.get(selectIndex);
        dice.nextValue();

        mainView.flipDiceView(selectIndex);
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }

    public void holdButtonClick() {
        Dice dice = diceList.get(selectIndex);
        dice.toggleHold();

        mainView.holdDiceView(selectIndex);
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }

    public void unselectIndex() {
        selectIndex = null;

        mainView.unhighlightAllDice();
        mainView.disableChangeButton();
        mainView.disableHoldButton();
    }
}
