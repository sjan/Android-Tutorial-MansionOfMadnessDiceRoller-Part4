package farsight.solutions.tutorial.diceroller;

import java.util.List;

public interface MainView {
    void updateDiceCount(int totalDice, int blankDice, int magDice, int starDice);
    void redrawDice(List<Dice> diceList);
    void spinDice(int diceIndex);
}
