package me.travis.wurstplus.event;

import me.zero.alpine.type.Cancellable;
import me.travis.wurstplus.util.Wrapper;

public class wurstplusEvent extends Cancellable {

    private Era era = Era.PRE;
    private final float partialTicks;

    public wurstplusEvent() {
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
