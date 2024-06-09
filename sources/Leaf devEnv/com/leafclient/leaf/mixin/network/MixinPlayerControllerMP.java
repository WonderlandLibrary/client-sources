package com.leafclient.leaf.mixin.network;

import com.leafclient.leaf.extension.ExtensionPlayerController;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP implements ExtensionPlayerController {

    @Shadow protected abstract void syncCurrentPlayItem();

    @Override
    public void syncCurrentItem() {
        syncCurrentPlayItem();
    }
}
