package net.shoreline.client.mixin.accessor;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * @author linus
 * @see ClientPlayerInteractionManager
 * @since 1.0
 */
@Mixin(ClientPlayerInteractionManager.class)
public interface AccessorClientPlayerInteractionManager {
    /**
     *
     */
    @Invoker("syncSelectedSlot")
    void hookSyncSelectedSlot();

    /**
     * @return
     */
    @Accessor("currentBreakingProgress")
    float hookGetCurrentBreakingProgress();

    /**
     * @param currentBreakingProgress
     */
    @Accessor("currentBreakingProgress")
    void hookSetCurrentBreakingProgress(float currentBreakingProgress);
}
