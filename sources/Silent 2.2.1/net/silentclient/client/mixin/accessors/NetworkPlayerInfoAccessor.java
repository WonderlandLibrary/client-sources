package net.silentclient.client.mixin.accessors;

import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(NetworkPlayerInfo.class)
public interface NetworkPlayerInfoAccessor {
    @Invoker("loadPlayerTextures") void silent$loadPlayerTextures();
}
