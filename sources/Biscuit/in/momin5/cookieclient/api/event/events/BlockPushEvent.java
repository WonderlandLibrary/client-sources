package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;

public class BlockPushEvent extends Event {
    public double var1;
    public double var2;
    public double var3;

    public BlockPushEvent(double var1, double var2, double var3) {
        this.isCancelled();
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
    }
}
