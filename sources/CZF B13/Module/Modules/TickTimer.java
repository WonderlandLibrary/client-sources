package gq.vapu.czfclient.Module.Modules;

public final class TickTimer {
    private int tick;

    public final void update() {
        int var10001 = this.tick++;
    }

    public final void reset() {
        this.tick = 0;
    }

    public final boolean hasTimePassed(int ticks) {
        return this.tick >= ticks;
    }
}
