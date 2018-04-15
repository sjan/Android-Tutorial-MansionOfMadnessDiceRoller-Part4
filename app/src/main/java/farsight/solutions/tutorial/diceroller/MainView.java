package farsight.solutions.tutorial.diceroller;

public interface MainView {
    void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice);
    void highlightDice(int diceIndex);
    void unhighlightAllDice();

    void disableChangeButton();
    void enableChangeButton();

    void disableHoldButton();
    void enableHoldButton(boolean hold);

    void addDiceToView();
    void removeDiceFromView();
    void spinDice(int index);
    void flipDiceView(int diceIndex);
    void holdDiceView(int diceIndex);

}
