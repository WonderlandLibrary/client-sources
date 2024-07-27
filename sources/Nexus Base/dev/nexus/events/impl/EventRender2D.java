package dev.nexus.events.impl;

import dev.nexus.events.types.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public class EventRender2D implements Event {
    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
