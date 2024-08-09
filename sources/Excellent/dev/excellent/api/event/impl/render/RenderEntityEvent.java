package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public final class RenderEntityEvent extends CancellableEvent {
   private final Entity entity;
   private final Runnable renderEntity;
   private final MatrixStack matrixStack;
   private final float partialTicks;
}