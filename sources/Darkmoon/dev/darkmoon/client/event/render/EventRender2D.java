package dev.darkmoon.client.event.render;

import com.darkmagician6.eventapi.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public class EventRender2D implements Event {
    private ScaledResolution resolution;
    public float partialTicks;
}
