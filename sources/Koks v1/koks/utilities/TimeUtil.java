package koks.utilities;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 21:06
 */
public class TimeUtil {

    public long lastMS;

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasReached(long time) {
        return System.currentTimeMillis() - lastMS >= time;
    }

}