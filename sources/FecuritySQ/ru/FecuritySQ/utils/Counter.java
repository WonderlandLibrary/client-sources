package ru.FecuritySQ.utils;


import java.util.TimerTask;

public class Counter {

    public long lastMS = System.currentTimeMillis();


    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) reset();
            return true;
        }

        return false;
    }
    public boolean hasReached(double milliseconds) {
        return getTimePassed() >= milliseconds;
    }
    public long getTimePassed() {
        return System.currentTimeMillis() - lastMS;
    }
    public static void sleepVoid(Runnable execute, int cooldown) {
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                execute.run();
                timer.cancel();
            }
        }, cooldown);
    }
    public boolean hasTimeElapsed(long time) {
        return System.currentTimeMillis() - lastMS > time;
    }

    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public void setTime(long time) {
        lastMS = time;
    }

}
