package farsight.solutions.tutorial.diceroller;

public interface MainView {
    void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice);
    void rollDice(int diceIndex);
    void refreshDiceLayout();
    void highlightDice(int diceIndex);
    void unhighlightAllDice();

    void disableChangeButton();
    void enableChangeButton();

    void disableHoldButton();
    void enableHoldButton(boolean hold);

}
