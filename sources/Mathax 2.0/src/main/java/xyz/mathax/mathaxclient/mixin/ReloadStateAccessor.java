package xyz.mathax.mathaxclient.mixin;

import net.minecraft.client.resource.ResourceReloadLogger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ResourceReloadLogger.ReloadState.class)
public interface ReloadStateAccessor {
    @Accessor("finished")
    boolean isFinished();
}
