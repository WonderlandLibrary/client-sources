package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ PlayerControllerMP.class })
public abstract class MixinPlayerControllerMP
{
    @Shadow
    private GameType field_78779_k;
    
    public MixinPlayerControllerMP() {
        super();
        this.currentGameType = GameType.SURVIVAL;
    }
    
    @Inject(method = { "getBlockReachDistance" }, at = { @At("HEAD") }, cancellable = true)
    public void preGetBlockReachDistance(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v6) {
            callbackInfoReturnable.setReturnValue(this.currentGameType.isCreative() ? ((float)HookTranslator.m43() + 0.5f) : ((float)HookTranslator.m43()));
        }
    }
}
