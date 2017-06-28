package dto;

import entity.SuccessSeckill;
import enums.SeckillEnum;

/**
 * 封装秒杀执行后的结果
 * Created by LiuSitong on 2017/6/27.
 */


public class SeckillExecution {
    private long seckillid;
    private int state; //状态
    private String stateInfo;
    //秒杀成功之后
    private SuccessSeckill successSeckill;

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillid=" + seckillid +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successSeckill=" + successSeckill +
                '}';
    }

    //成功
    public SeckillExecution(long seckillid, SeckillEnum stateenum, SuccessSeckill successSeckill) {
        this.seckillid = seckillid;
        this.state = stateenum.getState();
        this.stateInfo = stateenum.getStateInfo();
        this.successSeckill = successSeckill;
    }

    //失败
    public SeckillExecution(long seckillid, SeckillEnum stateenum) {
        this.seckillid = seckillid;
        this.state = stateenum.getState();
        this.stateInfo = stateenum.getStateInfo();
    }

    public long getSeckillid() {
        return seckillid;
    }

    public void setSeckillid(long seckillid) {
        this.seckillid = seckillid;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessSeckill getSuccessSeckill() {
        return successSeckill;
    }

    public void setSuccessSeckill(SuccessSeckill successSeckill) {
        this.successSeckill = successSeckill;
    }
}
