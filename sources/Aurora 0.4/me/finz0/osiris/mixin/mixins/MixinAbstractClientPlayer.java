package me.finz0.osiris.mixin.mixins;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.ModuleManager;
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

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer {
    @Shadow @Nullable protected abstract NetworkPlayerInfo getPlayerInfo();

    @Inject(method = "getLocationCape", at = @At("HEAD"), cancellable = true)
    public void getLocationCape(CallbackInfoReturnable<ResourceLocation> cir){
        UUID uuid = getPlayerInfo().getGameProfile().getId();

        if(ModuleManager.isModuleEnabled("Capes") && AuroraMod.getInstance().capeUtils.hasCape(uuid)) {
            cir.setReturnValue(new ResourceLocation("osiris:textures/cape.png"));
        }
    }
}
