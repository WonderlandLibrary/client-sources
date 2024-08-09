package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.MainWindow;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public final class Render2DEvent extends Event {
    private final MatrixStack matrix;
    private final ActiveRenderInfo activeRenderInfo;
    private final MainWindow mainWindow;
    private final float partialTicks;
}