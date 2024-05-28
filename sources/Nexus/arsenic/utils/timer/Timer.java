package arsenic.utils.timer;

public class Timer {
    private long startTime;
    private long coolDownTime;
    private boolean finishChecked;

    public Timer() {
        this(1000);
    }

    public Timer(long coolDownTime) {
        this.coolDownTime = coolDownTime;
        startTime = Long.MAX_VALUE - coolDownTime;
    }


    public void start() {
        startTime = System.currentTimeMillis();
        finishChecked = false;
    }

    public boolean hasFinished() {
        return System.currentTimeMillis() >= (startTime + coolDownTime);
    }

    public boolean firstFinish() {
        if (hasFinished() && !finishChecked) {
            finishChecked = true;
            return true;
        }
        return false;
    }


    public void setCooldown(long coolDownTime) { this.coolDownTime = coolDownTime; }

    public long getCooldownTime() { return coolDownTime; }

    public long getElapsedTime() {
        long et = System.currentTimeMillis() - startTime;
        return Math.min(et, coolDownTime);
    }

    public long getElapsedTimeAsPercent() {
        return (getElapsedTime()/coolDownTime);
    }

    public long getTimeLeft() {
        long tl = coolDownTime - (System.currentTimeMillis() - startTime);
        return tl < 0 ? 0 : tl;
    }
}