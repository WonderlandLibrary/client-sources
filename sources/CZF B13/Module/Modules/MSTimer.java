package gq.vapu.czfclient.Module.Modules;

public final class MSTimer {
    private long time = -1L;

    public final boolean hasTimePassed(long MS) {
        return System.currentTimeMillis() >= this.time + MS;
    }

    public final long hasTimeLeft(long MS) {
        return MS + this.time - System.currentTimeMillis();
    }

    public final long timePassed() {
        return System.currentTimeMillis() - this.time;
    }

    public final void reset() {
        this.time = System.currentTimeMillis();
    }
}
