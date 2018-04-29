package farsight.solutions.tutorial.diceroller;

public interface MainView {
    void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice);

    void addDiceToView();
    void removeDiceFromView();
    void spinDice(int diceIndex);
    void holdDiceView(int diceIndex);
}
