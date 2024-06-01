package best.actinium.util.math;

import best.actinium.util.IAccess;

public class TimeUtil implements IAccess {

    public static long startTime = 0;

    public static boolean elapsed(long timeMS) {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        } else {
            if (System.currentTimeMillis() - startTime >= timeMS) {
                startTime = 0;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
