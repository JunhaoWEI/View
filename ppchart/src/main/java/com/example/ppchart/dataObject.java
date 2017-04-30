package com.example.ppchart;

/**
 * Created by weijunhao on 2017/4/30.
 */

public class dataObject {
    String happenTime;
    float num;

    public String getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "dataObject{" +
                "happenTime='" + happenTime + '\'' +
                ", num=" + num +
                '}';
    }
}
