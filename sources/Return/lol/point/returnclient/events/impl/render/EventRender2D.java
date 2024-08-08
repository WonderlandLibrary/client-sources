package lol.point.returnclient.events.impl.render;

import lol.point.returnclient.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.ScaledResolution;

@AllArgsConstructor
public class EventRender2D extends Event {
    public ScaledResolution scaledResolution;
    public float partialTicks;
}
