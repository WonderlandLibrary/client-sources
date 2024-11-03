package net.silentclient.client.mixin.accessors;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SimpleReloadableResourceManager.class)
public interface SimpleReloadableResourceManagerAccessor {
    @Accessor
    Map<String, FallbackResourceManager> getDomainResourceManagers();
}
