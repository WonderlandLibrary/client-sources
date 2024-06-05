package alphentus.utils;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class TimeUtil {

    private long ms;

    public final boolean isDelayComplete(double delay) {
        return System.currentTimeMillis() - ms >= delay;
    }

    public final void reset() {
        ms = System.currentTimeMillis();
    }
}