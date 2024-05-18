package me.THEREALWWEFAN231.wwefan.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.*;
import wwe.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { AbstractClientPlayer.class }, priority = 9999)
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    public MixinAbstractClientPlayer() {
        super();
    }
    
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo func_175155_b();
    
    @Inject(method = { "getLocationCape" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetLocationCape(final CallbackInfoReturnable<ResourceLocation> callbackInfoReturnable) {
        if (this.getPlayerInfo() != null && PKGo.UBRO(this.getGameProfile().getId())) {
            callbackInfoReturnable.setReturnValue(new ResourceLocation("textures/wwe/cape.png"));
        }
    }
}
