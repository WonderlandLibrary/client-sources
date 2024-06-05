/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS;

public class simpleTimer {
    private static long timeStarted = -1L;

    public simpleTimer(boolean startOnInit) {
        if (!startOnInit) return;
        this.start();
    }

    public simpleTimer() {
        this(false);
    }

    public static void start() {
        timeStarted = System.currentTimeMillis();
    }

    public void stop() {
        timeStarted = -1L;
    }

    public static boolean isStarted() {
        if (timeStarted <= -1L) return false;
        return true;
    }

    public static boolean hasTimeElapsed(double time) {
        if (!simpleTimer.isStarted()) return false;
        if (!(time < (double)(System.currentTimeMillis() - timeStarted))) return false;
        return true;
    }

    public boolean doesTimeEqual(double time) {
        if (!simpleTimer.isStarted()) return false;
        if (time == (double)(System.currentTimeMillis() - timeStarted)) return true;
        if (time != (double)(System.currentTimeMillis() - timeStarted + 50L)) return false;
        return true;
    }
}

