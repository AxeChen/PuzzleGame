package com.mg.axe.puzzlegame;

/**
 * @Author Zaifeng
 * @Create 2017/7/13 0013
 * @Description Content
 */

public interface Game {

    /**
     * 增加难度
     */
    public void addLevel();

    /**
     * 减少难度
     */
    public void reduceLevel();

    /**
     * 游戏赢得比赛
     */
    public void win();

}
