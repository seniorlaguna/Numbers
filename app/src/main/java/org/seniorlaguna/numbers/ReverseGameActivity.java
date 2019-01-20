package org.seniorlaguna.numbers;


import android.content.Intent;
import android.os.Bundle;

import org.seniorlaguna.engine.utils.ViewUtils;

public class ReverseGameActivity extends OriginalGameActivity {

    @Override
    protected void initRightOrder() {
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            rightOrder.add(25 - i);
        }
    }

    @Override
    protected void initInstruction() {
        ViewUtils.fillTextView(this, R.id.instructionTextView, getString(R.string.reverse_instruction));
    }

    @Override
    protected void endGame(float myScore) {
        Intent intent = new Intent(this, GameoverActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.GAME_CODE, Constants.REVERSE_GAME);
        bundle.putInt(Constants.ORDER_CODE, Constants.ASCENDING);
        bundle.putFloat(Constants.MY_SCORE_CODE, myScore);
        intent.putExtras(bundle);
        startActivityWithMusic(intent);
    }
}
