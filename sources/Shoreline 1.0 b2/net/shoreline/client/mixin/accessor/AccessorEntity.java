package net.shoreline.client.mixin.accessor;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see Entity
 */
@Mixin(Entity.class)
public interface AccessorEntity
{
    /**
     *
     */
    @Invoker("unsetRemoved")
    void hookUnsetRemoved();
}
