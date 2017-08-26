package com.example.cyj.minesweep;

/**
 * Created by cyj on 2017/8/25.
 */

public interface Status {

    /**
     * 标记状态
     */
    // 还未显示，最初始状态
    int MARK_INITIAL = 0;

    // 没有地雷，显示为空
    int MARK_NO_MINE = 1;

    // 没有地雷，显示为数字
    int MARK_NUM_MINE = 2;

    // 存在地雷，显示为地雷图标
    int MARK_EXIST_MINE = 3;

    /**
     * 排雷操作
     */
    // 此处没有雷
    int CLEAR_MINE_SAFE = 4;

    // 成功清理掉雷
    int CLEAR_MINE_SUCCESS = 5;

    // 踩到雷，清理失败
    int CLEAR_MINE_FAIL = 6;
}
