package in.momin5.cookieclient.api.event;


import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;


public class Event extends Cancellable {

    private Era era = Era.PRE;
    private final float partialTicks;

    public Event() {
        partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
    }
    public Event(Era p_Era) {
        partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        era = p_Era;
    }
    public Era getEra() {
        return era;
    }
    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE,
        PERI,
        POST
    }
}
