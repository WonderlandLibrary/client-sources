package wtf.diablo.client.util.math.time;

public class TimerUtil {
    private static long lastMS = System.currentTimeMillis();

    public void reset()
    {
        lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(final long time)
    {
        return System.currentTimeMillis() - lastMS > time;
    }

    public boolean hasTimeElapsed(final double time)
    {
        return System.currentTimeMillis() - lastMS > time;
    }

    public long getElapsedTime()
    {
        return System.currentTimeMillis() - lastMS;
    }
}
