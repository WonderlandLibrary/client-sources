package dev.excellent.api.event.impl.funs;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.monster.CreeperEntity;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public final class CreeperResizerPopEvent extends Event {
    @Setter
    public float resize;
    private final CreeperEntity creeperEntity;
    private final MatrixStack matrix;

}
