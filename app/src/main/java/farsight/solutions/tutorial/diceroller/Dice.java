package farsight.solutions.tutorial.diceroller;

import java.util.Random;

public class Dice {
    public enum Face {
        BLANK,
        MAGNIFY,
        STAR
    }

    public static Random random = new Random();

    public boolean hold;
    public Face diceVal;

    Dice() {
        roll();
    }

    public void roll() {
        int num = random.nextInt(4);
        if(num == 0) { //25% magnify
            this.diceVal = Face.MAGNIFY;
        } else {
            //37.5% star, 37.5% blank
            if(random.nextBoolean()) {
                this.diceVal = Face.BLANK;
            } else {
                this.diceVal = Face.STAR;
            }
        }
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }

}
