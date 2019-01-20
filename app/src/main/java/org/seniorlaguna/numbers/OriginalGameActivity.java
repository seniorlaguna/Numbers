package org.seniorlaguna.numbers;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

import org.seniorlaguna.engine.custom.SoundActivity;
import org.seniorlaguna.engine.time.SimpleTimer;
import org.seniorlaguna.engine.utils.DisplayUtils;
import org.seniorlaguna.engine.utils.ViewUtils;

public class OriginalGameActivity extends SoundActivity implements View.OnClickListener {

    protected static final int ARRAY_LENGTH = 25;

    protected int gameElements[] = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
                                    R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12,
                                    R.id.button13, R.id.button14, R.id.button15, R.id.button16, R.id.button17, R.id.button18,
                                    R.id.button19, R.id.button20, R.id.button21, R.id.button22, R.id.button23, R.id.button24,
                                    R.id.button25, R.id.instructionTextView, R.id.pauseImageButton, R.id.gamefieldGridLayout};

    protected ArrayList<Integer> rightOrder = new ArrayList<>();
    protected ArrayList<Integer> randomOrder = new ArrayList<>();
    protected int currentPos;

    protected SimpleTimer timer = new SimpleTimer();

    protected boolean keepMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        scaleViews();
        initGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivityWithMusic(new Intent(this, MenuActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.pauseImageButton):
                pause();
                return;
            case (R.id.resumeImageButton):
                resume();
                return;
            case (R.id.restartImageButton):
                restart();
                return;
            case (R.id.exitImageButton):
                startActivityWithMusic(new Intent(this, MenuActivity.class));
                return;
        }

        Button button = (Button) findViewById(v.getId());
        if (button.getText().equals(Integer.toString(rightOrder.get(currentPos)))) {
            rightButtonPressed(button);
        }
    }

    protected void initGame() {
        currentPos = 0;
        timer.reset();

        initRightOrder();
        initInstruction();
        shuffle();
        showGameElements();
        printNumbersToGamefield();
    }

    protected void initRightOrder() {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            rightOrder.add(i + 1);
        }
    }

    protected void initInstruction() {
        ViewUtils.fillTextView(this, R.id.instructionTextView, getString(R.string.original_instruction));
    }

    protected void scaleViews() {
        ViewGroup gamefield = (ViewGroup) findViewById(R.id.gamefieldGridLayout);
        int size = DisplayUtils.getScreenWidth(this) / 55 * 10;
        int viewId;

        for (int i = 0; i < gamefield.getChildCount(); i++) {
            viewId = gamefield.getChildAt(i).getId();
            ViewUtils.scaleView(this, viewId, size, size);
        }
    }

    protected void shuffle() {
        randomOrder.clear();
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            randomOrder.add(rightOrder.get(i));
        }
        Collections.shuffle(randomOrder);
    }

    protected void printNumbersToGamefield() {
        ViewGroup gamefield = (ViewGroup) findViewById(R.id.gamefieldGridLayout);
        Button button;

        for (int i = 0; i < gamefield.getChildCount(); i++) {
            button = (Button) gamefield.getChildAt(i);
            button.setText(String.format("%d", randomOrder.get(i)));
        }
    }

    protected void showGameElements() {
        ViewUtils.hideView(this, R.id.levelCountTextView);
        ViewUtils.hideView(this, R.id.timeCountTextView);
        ViewUtils.showViews(this, gameElements);
    }

    protected void rightButtonPressed(Button button) {
        ViewUtils.hideView(this, button.getId());

        if (currentPos == 0) {
            timer.start();
        }
        else if (currentPos == 24) {
            timer.stop();
            endGame(timer.getTimeSeconds());
            return;
        }

        currentPos += 1;
    }

    protected void endGame(float myScore) {
        Intent intent = new Intent(this, GameoverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.GAME_CODE, Constants.ORIGINAL_GAME);
        bundle.putInt(Constants.ORDER_CODE, Constants.ASCENDING);
        bundle.putFloat(Constants.MY_SCORE_CODE, myScore);
        intent.putExtras(bundle);
        startActivityWithMusic(intent);
    }

    protected void pause() {
        timer.pause();
        ViewGroup root = (ViewGroup) findViewById(R.id.pauseScreenFrameLayout);
        root.removeAllViews();
        getLayoutInflater().inflate(R.layout.pause_layout, root);
    }

    protected void resume() {
        ViewGroup root = (ViewGroup) findViewById(R.id.pauseScreenFrameLayout);
        root.removeAllViews();
        timer.resume();
    }

    protected void restart() {
        currentPos = 0;
        timer.reset();

        shuffle();
        showGameElements();
        printNumbersToGamefield();
        resume();
    }
}
