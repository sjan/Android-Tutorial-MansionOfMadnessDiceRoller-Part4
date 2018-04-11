package farsight.solutions.tutorial.diceroller;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    private static final int MAX_DICE_COUNT = 25;

    List<Dice> diceList = new ArrayList<>();
    private MainView mainView;

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
    }

    public void addButtonClicked() {
        if(diceList.size()<MAX_DICE_COUNT) {
            diceList.add(new Dice());
            mainView.refreshAdapter();
            mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
        }
    }

    public void removeButtonClicked() {
        if(!diceList.isEmpty()) {
            diceList.remove(diceList.size() - 1);
            mainView.refreshAdapter();
            mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
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
        mainView.refreshAdapter();
    }

    public void changeButtonClicked(int diceIndex) {
        Dice dice = diceList.get(diceIndex);
        dice.nextValue();
        mainView.refreshAdapter();
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }

    public void holdButtonClick(int diceIndex) {
        Dice dice = diceList.get(diceIndex);
        dice.toggleHold();
        mainView.refreshAdapter();
        mainView.updateDiceCount(diceList.size(), countDice(Dice.Face.BLANK), countDice(Dice.Face.MAGNIFY), countDice(Dice.Face.STAR));
    }

    public List<Dice> getDiceList() {
        return diceList;
    }
}
