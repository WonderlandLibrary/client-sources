package dev.excellent.api.event.impl.funs;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public final class EntityRenderMatrixEvent extends Event {
    private final MatrixStack matrix;
    private final Entity entity;
}
