package com.mg.axe.puzzlegame;

/**
 * @Author Zaifeng
 * @Create 2017/7/13 0013
 * @Description Content
 */

public interface Game {

    /**
     * 游戏开始
     */
    public void start();

    /**
     * 游戏暂停
     */
    public void pause();

    /**
     * 游戏停止
     */
    public void stop();

    /**
     * 游戏失败
     */
    public void over();

    /**
     * 游戏赢得比赛
     */
    public void win();

}
