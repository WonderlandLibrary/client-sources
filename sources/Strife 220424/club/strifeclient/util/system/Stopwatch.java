package club.strifeclient.util.system;

public final class Stopwatch {
    private long start;
    public boolean hasElapsed(long time) {
        return hasElapsed(time, false);
    }
    public boolean hasElapsed(long time, boolean reset) {
        boolean reached = getElapsedTime() > time;
        if(reached && reset)
            reset();
        return reached;
    }
    public long getElapsedTime() {
        return System.currentTimeMillis() - start;
    }
    public void reset() {
        start = System.currentTimeMillis();
    }
}
