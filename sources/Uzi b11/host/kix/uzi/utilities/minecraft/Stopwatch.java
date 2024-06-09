package host.kix.uzi.utilities.minecraft;

/**
 * Created by myche on 2/3/2017.
 */
public class Stopwatch {

    private long previousMS;

    public Stopwatch() {
        reset();
    }

    public boolean hasCompleted(long milliseconds) {
        return getCurrentMS() - this.previousMS >= milliseconds;
    }

    public void reset() {
        this.previousMS = getCurrentMS();
    }

    public long getPreviousMS() {
        return this.previousMS;
    }

    public long getTimePassed(){
        return this.getCurrentMS() - this.previousMS;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

}
