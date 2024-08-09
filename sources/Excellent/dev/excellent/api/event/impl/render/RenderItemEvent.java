package dev.excellent.api.event.impl.render;

import dev.excellent.api.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.mojang.blaze3d.matrix.MatrixStack;

@Getter
@AllArgsConstructor
public class RenderItemEvent extends Event {
    private MatrixStack matrix;
    private final Hand hand;
    private final HandSide handSide;
    private float swingProgress;
}
