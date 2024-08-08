package in.momin5.cookieclient.api.util.utils.misc;

import com.lukflug.panelstudio.settings.Toggleable;

public class Timer {
    /**
     * OMG WHY ARE THERE 2 TIMER CLASSES OMG OMG SKIDDDDD
     * ok so lemme explain, i used that timer in a LOT of classes, but i have no idea how that one works(probs cause i pasted it??)
     * this one i wrote, i dont wanna delete that one cause a lot of modules use that timer and im too lazy to rewrite that code
     */
    private long time;

    public Timer() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDelta() {
        return time - System.currentTimeMillis();
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public boolean hasPassed(int millis) {
        return (System.currentTimeMillis() - this.time) >= millis;
    }

    public boolean hasPassAndReset(int millis) {
        if (System.currentTimeMillis() - this.time >= millis) {
            reset();
            return true;
        } else return false;
    }
}
