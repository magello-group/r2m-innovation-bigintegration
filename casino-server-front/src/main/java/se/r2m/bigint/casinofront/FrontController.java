package se.r2m.bigint.casinofront;

import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import se.r2m.bigint.casinofront.game.GameEngine;
import se.r2m.bigint.casinofront.game.GameInput;
import se.r2m.bigint.casinofront.game.GameOutput;
import se.r2m.bigint.casinofront.model.GameResult;
import se.r2m.bigint.casinofront.processing.ProcessingTask;

@RestController
public class FrontController {

    private static Logger log = LoggerFactory.getLogger(FrontController.class);

    private static final String GAME_ID_PREFIX = UUID.randomUUID().toString();

    private static final long PROCESSING_TIME = 100;

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GameEngine engine;
    // private final GameEngine engine = new LocalGameEngine();

    private final Timer timer = new Timer();

    @RequestMapping("/")
    public String index() {

        return "This is the casino front!";
    }

    @RequestMapping("/playgameBlocking")
    public GameResult playGameBlocking(
                    @RequestParam(required=true, value="clientId") String clientId,
                    @RequestParam(required=true, value="playedNumber") Integer playedNumber,
                    @RequestParam(required = true, value = "playedAmount") Integer playedAmount
                    ) {


        Assert.isTrue(playedNumber >= 0 && playedNumber <= 36);
        GameInput gameInput = new GameInput(clientId, playedNumber, playedAmount);
        String gameId = String.format("%s-%d", GAME_ID_PREFIX, counter.incrementAndGet());

        // try {
        // Thread.sleep(PROCESSING_TIME);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }

        GameOutput go = engine.doPlay(gameInput);

        return new GameResult(gameId, go.getResultNumber(), go.getGameWinnings(), go.getJackpotWinnings(),
                        go.getRemainingBalance());
    }

    @RequestMapping("/playgame")
    public DeferredResult<GameResult> playGame(
                    @RequestParam(required=true, value="clientId") String clientId,
                    @RequestParam(required=true, value="playedNumber") Integer playedNumber,
                    @RequestParam(required = true, value = "playedAmount") Integer playedAmount
                    ) {
        
        Assert.isTrue(playedNumber >= 0 && playedNumber <= 36);
        GameInput gameInput = new GameInput(clientId, playedNumber, playedAmount);
        String gameId = String.format("%s-%d", GAME_ID_PREFIX, counter.incrementAndGet());
        
        DeferredResult<GameResult> deferredResult = new DeferredResult<GameResult>();
        ProcessingTask task = new ProcessingTask(deferredResult, engine, gameInput, gameId);
        timer.schedule(task, PROCESSING_TIME);

        return deferredResult;

    }



}
