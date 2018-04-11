package farsight.solutions.tutorial.diceroller;

public interface MainView {
    void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice);
    void rollDice(int diceIndex);
    void refreshAdapter();
}
