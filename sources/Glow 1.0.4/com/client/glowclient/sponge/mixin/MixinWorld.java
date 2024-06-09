package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ World.class })
public abstract class MixinWorld implements IBlockAccess
{
    public MixinWorld() {
        super();
    }
    
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    public void preCheckLightFor(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!HookTranslator.mc.isIntegratedServerRunning() && HookTranslator.v17) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
