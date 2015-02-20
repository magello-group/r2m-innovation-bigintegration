package se.r2m.bigint.casinofront.game;

public class GameInput {

    private final String playerId;
    private final int playedNumber;
    private final int playedAmount;

    public GameInput(String playerId, int playedNumber, int playedAmount) {
        super();
        this.playerId = playerId;
        this.playedNumber = playedNumber;
        this.playedAmount = playedAmount;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getPlayedNumber() {
        return playedNumber;
    }

    public int getPlayedAmount() {
        return playedAmount;
    }

}
