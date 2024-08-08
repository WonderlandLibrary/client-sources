package in.momin5.cookieclient.api.event.events;

import in.momin5.cookieclient.api.event.Event;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class RenderFirstPersonEvent extends Event {

    private EnumHandSide enumHand;

    public RenderFirstPersonEvent(EnumHandSide enumHand) {
        this.enumHand = enumHand;
    }

    public EnumHandSide getHandSide() {
        return enumHand;
    }
}
