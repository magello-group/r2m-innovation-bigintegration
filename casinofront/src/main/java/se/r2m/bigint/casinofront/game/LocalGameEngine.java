package se.r2m.bigint.casinofront.game;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

public class LocalGameEngine implements GameEngine {

    private static final Integer INITIAL_AMOUNT = 50000;
    Map<String, Integer> playerBalance = new ConcurrentHashMap<String, Integer>();

    Random rm = new Random();

    public GameOutput doPlay(GameInput input) {

        Assert.notNull(input);
        Assert.notNull(input.getPlayerId());

        String playerId = input.getPlayerId();
        Integer balance = playerBalance.get(playerId);
        if (balance == null) {
            balance = createPlayer(playerId);
        }
        if (balance - input.getPlayedAmount() < 0){
            //Can't place bet
            return new GameOutput(-1, 0, 0, balance);
        }
        // Winning number
        int winNumber = rm.nextInt(37);
        int newBalance;
        int gameWinnings = 0;
        if(winNumber == input.getPlayedNumber()){
            // WON
            gameWinnings = 36 * input.getPlayedAmount();
            newBalance = balance + gameWinnings;
        } else {
            // Loose
            newBalance = balance - input.getPlayedAmount();
        }
        playerBalance.put(playerId, newBalance);


        return new GameOutput(winNumber, gameWinnings, 0, newBalance);
    }

    private Integer createPlayer(String playerId) {
        playerBalance.put(playerId, INITIAL_AMOUNT);
        return INITIAL_AMOUNT;
    }

}
