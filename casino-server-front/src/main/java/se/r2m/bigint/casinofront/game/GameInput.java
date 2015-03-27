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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GameInput [playerId=");
        builder.append(playerId);
        builder.append(", playedNumber=");
        builder.append(playedNumber);
        builder.append(", playedAmount=");
        builder.append(playedAmount);
        builder.append("]");
        return builder.toString();
    }

}
