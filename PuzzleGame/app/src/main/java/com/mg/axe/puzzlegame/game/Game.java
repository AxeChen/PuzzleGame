package com.mg.axe.puzzlegame.game;

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
     * 修改游戏模式
     */
    public void changeMode(String gameMode);

    /**
     * 修改图片
     *
     * @param res
     */
    public void changeImage(int res);

}
