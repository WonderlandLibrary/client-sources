package com.alan.clients.event.impl.render;

import com.alan.clients.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.gui.ScaledResolution;

@Getter
@AllArgsConstructor
public final class LimitedRender2DEvent implements Event {

    private final ScaledResolution scaledResolution;
    private final float partialTicks;
}
