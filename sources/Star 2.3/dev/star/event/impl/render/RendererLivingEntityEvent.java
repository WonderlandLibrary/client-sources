package dev.star.event.impl.render;

import dev.star.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;


@Getter
@AllArgsConstructor
public class RendererLivingEntityEvent extends Event.StateEvent {
    private final EntityLivingBase entity;
    private final RendererLivingEntity<?> renderer;
    private final float partialTicks;
    private final double x, y, z;
}
