package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.vector.Matrix4f;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public final class Render3DPosedLastEvent extends Event {
    private final MatrixStack matrix;
    private final Matrix4f projectionMatrix;
    private final ActiveRenderInfo activeRenderInfo;
    private final WorldRenderer context;
    private final float partialTicks;
    private final long finishTimeNano;
    private double cx, cy, cz;
}
