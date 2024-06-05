package zamorozka.ui;

public class TimerrUtil {
    private long time;

    public boolean passedS(double s) {
        return getMs(System.nanoTime() - this.time) >= ((long) (s * 1000.0));
    }

    public boolean passedM(double m) {
        return getMs(System.nanoTime() - this.time) >= ((long) (m * 1000.0 * 60));
    }

    public boolean passedDms(double dms) {
        return getMs(System.nanoTime() - this.time) >= ((long) (dms * 10.0));
    }

    public boolean passedDs(double ds) {
        return getMs(System.nanoTime() - this.time) >= ((long) (ds * 100.0));
    }

    public boolean passedMs(long ms) {
        return getMs(System.nanoTime() - this.time) >= ms;
    }

    public boolean passedNS(long ns) {
        return System.nanoTime() - this.time >= ns;
    }

    public void setMs(long ms) {
        this.time = System.nanoTime() - ms * 1000000;
    }

    public long getPassedTimeMs() {
        return getMs(System.nanoTime() - this.time);
    }

    public void reset() {
        this.time = System.nanoTime();
    }

    public long getMs(long time) {
        return time / 1000000;
    }

}
