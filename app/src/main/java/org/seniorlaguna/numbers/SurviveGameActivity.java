package org.seniorlaguna.numbers;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import org.seniorlaguna.engine.utils.ViewUtils;

public class SurviveGameActivity extends OriginalGameActivity {

    protected final static int DELAY = 1000;
    protected final static String LEVEL_START = "Lvl 1";
    protected final static String TIME_START = "60";

    protected int gameElements[] = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
                                    R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12,
                                    R.id.button13, R.id.button14, R.id.button15, R.id.button16, R.id.button17, R.id.button18,
                                    R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24,
                                    R.id.button25, R.id.instructionTextView, R.id.levelCountTextView, R.id.timeCountTextView,
                                    R.id.pauseImageButton, R.id.gamefieldGridLayout};

    protected int time;
    protected int level;

    protected Handler handler;
    protected Runnable countdown = new Runnable() {
        @Override
        public void run() {
            time -= 1;
            ViewUtils.fillTextView(SurviveGameActivity.this, R.id.timeCountTextView, Integer.toString(time));
            if (time <= 0) {
                endGame(level);
            }
            else {
                handler.postDelayed(this, DELAY);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        scaleViews();
        initGame();
    }

    @Override
    protected void initGame() {
        time = 60;
        level = 1;
        handler = new Handler();
        currentPos = 0;

        initRightOrder();
        initInstruction();
        shuffle();
        showGameElements();
        printNumbersToGamefield();
    }

    @Override
    protected void initInstruction() {
        super.initInstruction();
        ViewUtils.fillTextView(this, R.id.levelCountTextView, LEVEL_START);
        ViewUtils.fillTextView(this, R.id.timeCountTextView, TIME_START);
    }

    @Override
    protected void showGameElements() {
        ViewUtils.showViews(this, gameElements);
    }

    @Override
    protected void rightButtonPressed(Button button) {
        ViewUtils.hideView(this, button.getId());

        if (currentPos == 0 && level == 1) {
            handler.postDelayed(countdown, DELAY);
        }
        else if (currentPos == 24) {
            nextLevel();
            return;
        }

        currentPos += 1;
    }

    private void nextLevel() {
        currentPos = 0;
        time += 10;
        level += 1;
        ViewUtils.fillTextView(this, R.id.levelCountTextView, "Lvl " + Integer.toString(level));

        shuffle();
        showGameElements();
        printNumbersToGamefield();
    }

    @Override
    protected void pause() {
        handler.removeCallbacks(countdown);
        ViewGroup root = (ViewGroup) findViewById(R.id.pauseScreenFrameLayout);
        root.removeAllViews();
        getLayoutInflater().inflate(R.layout.pause_layout, root);
    }

    @Override
    protected void resume() {
        ViewGroup root = (ViewGroup) findViewById(R.id.pauseScreenFrameLayout);
        root.removeAllViews();
        handler.postDelayed(countdown, DELAY);
    }

    @Override
    protected void restart() {
        currentPos = 0;
        level = 1;
        time = 60;
        handler.removeCallbacks(countdown);
        ViewGroup root = (ViewGroup) findViewById(R.id.pauseScreenFrameLayout);
        root.removeAllViews();

        initInstruction();
        shuffle();
        showGameElements();
        printNumbersToGamefield();
    }

    @Override
    protected void endGame(float myScore) {
        handler.removeCallbacks(countdown);
        Intent intent = new Intent(this, GameoverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.GAME_CODE, Constants.SURVIVE_GAME);
        bundle.putInt(Constants.ORDER_CODE, Constants.DESCENDING);
        bundle.putFloat(Constants.MY_SCORE_CODE, myScore);
        intent.putExtras(bundle);
        startActivityWithMusic(intent);
    }
}
