package me.zeroeightsix.kami.mixin.client;

import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.util.CapeManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Created 23 November 2019 by hub
 * Updated 16 December 2019 by hub
 */
@Mixin({AbstractClientPlayer.class})
public abstract class MixinAbstractClientPlayer {

    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = ("getLocationCape"), at = {@At("HEAD")}, cancellable = true)
    public void getLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {

        if (ModuleManager.isModuleEnabled("Capes")) {

            final NetworkPlayerInfo info = this.getPlayerInfo();

            UUID uuid = null;

            if (info != null) {
                uuid = this.getPlayerInfo().getGameProfile().getId();
            }

            if (uuid != null && CapeManager.hasCape(uuid)) {
                if (CapeManager.isOg(uuid)) {
                    callbackInfoReturnable.setReturnValue(new ResourceLocation("textures/cape_og.png"));
                } else {
                    callbackInfoReturnable.setReturnValue(new ResourceLocation("textures/cape.png"));
                }
            }

        }

    }

}
