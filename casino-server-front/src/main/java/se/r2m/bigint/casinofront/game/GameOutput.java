package se.r2m.bigint.casinofront.game;

public class GameOutput {

    private final int resultNumber;
    private final int gameWinnings;
    private final int jackpotWinnings;
    private final int remainingBalance;

    public GameOutput(int resultNumber, int gameWinnings, int jackpotWinnings, int remainingBalance) {
        super();
        this.resultNumber = resultNumber;
        this.gameWinnings = gameWinnings;
        this.jackpotWinnings = jackpotWinnings;
        this.remainingBalance = remainingBalance;
    }

    public int getResultNumber() {
        return resultNumber;
    }

    public int getGameWinnings() {
        return gameWinnings;
    }

    public int getJackpotWinnings() {
        return jackpotWinnings;
    }

    public int getRemainingBalance() {
        return remainingBalance;
    }

}
