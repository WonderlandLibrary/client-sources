package lol.point.returnclient.util.system;

public class TimerUtil {

    public long lastMS = System.currentTimeMillis();

    public long getTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean elapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) {
                reset();
            }

            return true;
        }

        return false;
    }

    public boolean elapsed(long time) {
        return System.currentTimeMillis() - lastMS > time;
    }

}
