package me.finz0.osiris.event;

import me.finz0.osiris.util.Wrapper;
import me.zero.alpine.type.Cancellable;

public class OsirisEvent extends Cancellable {

    private Era era = Era.PRE;
    private final float partialTicks;

    public OsirisEvent() {
        partialTicks = Wrapper.getMinecraft().getRenderPartialTicks();
    }

    public Era getEra() {
        return era;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public enum Era {
        PRE, PERI, POST
    }

}
