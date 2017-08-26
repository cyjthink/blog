package com.example.cyj.minesweep;

/**
 * Created by cyj on 2017/8/25.
 */

public class MineEntity {
    private int status = Status.MARK_INITIAL;
    private int mine_num;

    public MineEntity() {
    }

    public MineEntity(int status, int mine_num) {
        this.status = status;
        this.mine_num = mine_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMine_num() {
        return mine_num;
    }

    public void setMine_num(int mine_num) {
        this.mine_num = mine_num;
    }
}
