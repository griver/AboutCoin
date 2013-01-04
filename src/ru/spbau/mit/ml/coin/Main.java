package ru.spbau.mit.ml.coin;

import java.io.*;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 03.01.13
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    static public int SCORE = 100;
    static public int GAMES_NUMBER = 1000;
    static public double DELTA = 0.1;

    static public Integer[] createInitRates() {
        Integer[] init = new Integer[SCORE + 1];
        init[0] = init[SCORE] = 0;
        for(int i = 1; i < init.length; ++i)
            init[i] = Math.min(i, SCORE - i);
        return init;
    }

    static public void main(String[] argv) throws IOException {

        Player player = new Player(SCORE);
        Double[] winProbability = new Double[SCORE + 1];
        Double[] previosBestProb = new Double[SCORE + 1];
        Integer[] winCases = new Integer[SCORE + 1];
        Integer[] allCases = new Integer[SCORE + 1];
        Integer[] rates = createInitRates();
        Arrays.fill(previosBestProb, 0.0);
        boolean haveNewStrategy;

        for (double prob  = 0.4; prob <= 0.6; prob +=0.1 ){
            do{
                Arrays.fill(winCases, 0);
                Arrays.fill(allCases, 0);
                Coin coin = new Coin(prob);

                player.setRates(rates);

                for(int i = 0; i < GAMES_NUMBER; ++i){
                    for(int start = 1; start < SCORE; ++start) {
                        player.play(coin, start);

                        for(int value : player.getVisitedStates()){
                            if(player.isWon()) ++winCases[value];
                            ++allCases[value];
                        }
                    }
                }

                for(int i = 1; i < SCORE; ++i)
                    winProbability[i] = (double) winCases[i] / (double) allCases[i];

                winProbability[0] = 0.0;
                winProbability[SCORE] = 1.0;

                haveNewStrategy = calculateNewStrategy(rates, winProbability, previosBestProb,  prob);
            } while (haveNewStrategy);

            try {
                System.out.println("закончили подсчеты для вероятности: " + prob);

                printRates("rates for " + prob + " probability", rates);
            } catch (FileNotFoundException e) {
                System.err.println("can't save rates in file");
                e.printStackTrace(System.err);
            }

        }


    }

    static public void printRates(String filename, Integer[] rates) throws FileNotFoundException {
        PrintWriter printer = new PrintWriter(filename);
        for(int i = 1; i < SCORE; ++i)
            printer.println(i + " : " + rates[i]);
        printer.flush();
        printer.close();
    }

    static public boolean calculateNewStrategy(Integer[] rates, Double[] winProbability, Double[] oldBestProb, double coinProb) {
        boolean isRealyNew = false;

        Integer[] newRates = rates.clone();
        int bestRate;
        double bestProb, currProb;
        for(int money = 1 ; money < SCORE; ++money) {
            bestRate = rates[money];
            bestProb = 0;
            for(int rate = 1; rate <= money && rate + money <= SCORE; ++rate) {
                currProb = coinProb * winProbability[money + rate] + (1.0 - coinProb) * winProbability[money - rate];
                if(currProb > bestProb){
                    bestProb = currProb;
                    bestRate = rate;
                }
            }

            isRealyNew = Math.abs(bestProb - oldBestProb[money]) > DELTA ? true : isRealyNew;
            oldBestProb[money] = bestProb;
            rates[money] = bestRate;
        }

        return isRealyNew;
    }

    static public void checkCoin(Coin coin, int count) {
        int successNumber = 0;

        for(int i =0; i < count; ++i)
            if(coin.flip()) ++successNumber;

        System.out.println("heads: " + successNumber + "   talls: " + (count - successNumber));
        System.out.println("success probability = " + (double) successNumber / (double) count);
    }
}
