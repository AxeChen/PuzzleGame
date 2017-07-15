package com.mg.axe.puzzlegame.game;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.mg.axe.puzzlegame.ui.PuzzleLayout;

/**
 * @Author Zaifeng
 * @Create 2017/7/13 0013
 * @Description Content
 */

public class PuzzleGame implements Game, PuzzleLayout.SuccessListener {

    private static int DEFAULT_TIME = 300;
    private static final int TIME_CHANGE = 1;

    private PuzzleLayout puzzleLayou;
    private GameStateListener stateListener;
    private Context context;


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void addGameStateListener(GameStateListener stateListener) {
        this.stateListener = stateListener;
    }

    public PuzzleGame(@NonNull Context context, @NonNull PuzzleLayout puzzleLayout) {
        this.context = context.getApplicationContext();
        this.puzzleLayou = puzzleLayout;
        puzzleLayou.addSuccessListener(this);
    }

    private boolean checkNull(){
        return puzzleLayou==null;
    }

    @Override
    public void addLevel() {
        if(checkNull()){
            return;
        }
        if (!puzzleLayou.addCount()) {
            Toast.makeText(context, "已经是最高等级", Toast.LENGTH_SHORT).show();
        }
        if (stateListener != null) {
            stateListener.setLevel(getLevel());
        }
    }

    @Override
    public void reduceLevel() {
        if(checkNull()){
            return;
        }
        if (!puzzleLayou.reduceCount()) {
            Toast.makeText(context, "已经是最低等级", Toast.LENGTH_SHORT).show();
        }
        if (stateListener != null) {
            stateListener.setLevel(getLevel());
        }
    }

    public void changeRes(int res){
        puzzleLayou.changeRes(res);
    }

    @Override
    public void win() {
        //胜利的回调
        mHandler.removeMessages(TIME_CHANGE);
    }

    public int getLevel() {
        if(checkNull()){
            return 0;
        }
        int count = puzzleLayou.getCount();
        return count - 3 + 1;
    }

    @Override
    public void success() {
        if (stateListener != null) {
            stateListener.gameSuccess(getLevel());
        }
    }

    public interface GameStateListener {
        public void setLevel(int level);
        public void gameSuccess(int level);
    }
}
