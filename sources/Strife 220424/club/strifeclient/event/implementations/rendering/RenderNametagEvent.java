package club.strifeclient.event.implementations.rendering;

import club.strifeclient.event.Event;
import lombok.AllArgsConstructor;
import net.minecraft.entity.EntityLivingBase;

@AllArgsConstructor
public class RenderNametagEvent extends Event {
    private final EntityLivingBase entity;
}
