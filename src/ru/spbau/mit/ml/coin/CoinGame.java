package ru.spbau.mit.ml.coin;

/**
 * Created with IntelliJ IDEA.
 * User: griver
 * Date: 03.01.13
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class CoinGame {
    private Player player;
    private Coin coin;

    public CoinGame(Coin coin, Player player) {
        this.coin = coin;
        this.player = player;
    }

    void play(Player player){

    }

    void setCoin(Coin coin) {
        this.coin = coin;
    }
}
