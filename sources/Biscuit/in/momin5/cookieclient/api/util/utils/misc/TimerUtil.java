package in.momin5.cookieclient.api.util.utils.misc;

public class TimerUtil {
    private long time = -1L;

    public boolean passed(double ms) {
        return System.currentTimeMillis() - this.time >= ms;
    }


    public void resetTimeSkipTo(long p_MS) {
        this.time = System.currentTimeMillis() + p_MS;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }


    public boolean passedS(double s) {
        return this.passedMs((long)s * 1000L);
    }

    public boolean passedDms(double dms) {
        return this.passedMs((long)dms * 10L);
    }

    public boolean passedDs(double ds) {
        return this.passedMs((long)ds * 100L);
    }

    public boolean passedMs(long ms) {
        return this.passedNS(this.convertToNS(ms));
    }

    public void setMs(long ms) {
        this.time = System.nanoTime() - this.convertToNS(ms);
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }


    public long getMs(long time) {
        return time / 1000000L;
    }

    public long convertToNS(long time) {
        return time * 1000000L;
    }

    private long currentMS = 0L;
    private long lastMS = -1L;

    public void setCurrentMS() {
        currentMS = System.nanoTime() / 1000000;
    }

    public boolean hasDelayRun(long time) {
        return (currentMS - lastMS) >= time;
    }

    public void setLastMS() {
        lastMS = System.nanoTime() / 1000000;
    }

    public void reset() {
        currentMS = System.nanoTime() / 1000000;
    }
}
