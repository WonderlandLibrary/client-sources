package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public class RenderHotbarEvent extends CancellableEvent {
    private final MatrixStack matrix;
    private final float partialTicks;
}
