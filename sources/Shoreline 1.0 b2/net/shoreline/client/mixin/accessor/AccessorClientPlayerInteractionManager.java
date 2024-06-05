package net.shoreline.client.mixin.accessor;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ClientPlayerInteractionManager
 */
@Mixin(ClientPlayerInteractionManager.class)
public interface AccessorClientPlayerInteractionManager
{
    /**
     *
     */
    @Invoker("syncSelectedSlot")
    void hookSyncSelectedSlot();
}
