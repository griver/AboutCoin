package ru.spbau.mit.ml.coin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 03.01.13
 * Time: 20:09
 * To change this template use File | Settings | File Templates.
 */
public class Player {
    Integer[] rates;
    private boolean isWon = false;
    private Set<Integer> visitedStates = new HashSet<Integer>();
    private int score;
    public Player(int score) {
        this.score = score;
    }

    public int getRate(int money) {
        return rates[money];
    }


    public void setRates(Integer[] rates) {
        if(rates != null) this.rates = rates;
    }

    public void play(Coin coin, int startingMoney) {
        if(rates == null) return;

        int  rate, money = startingMoney;
        visitedStates.clear();
        visitedStates.add(money);

        while(money > 0 && money < score) {

            rate = getRate(money);
            if(coin.flip()) money += rate;
            else money -= rate;
            visitedStates.add(money);
        }

        isWon = money == score;
    }

    public boolean isWon() {
        return isWon;
    }

    public Collection<Integer> getVisitedStates() {
        return visitedStates;
    }
}
