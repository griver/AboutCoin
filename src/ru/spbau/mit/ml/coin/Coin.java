package ru.spbau.mit.ml.coin;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 03.01.13
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public class Coin {
    private double probability = 0.5;
    private static Random RANDOM = new Random();

    public Coin() {}

    public Coin(double probability) {
        if(probability >= 0 && probability <= 1)
            this.probability = probability;
    }

    public boolean flip() {
        return RANDOM.nextDouble() <= probability;
    }
}
