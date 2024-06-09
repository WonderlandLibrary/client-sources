package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;

@SideOnly(Side.CLIENT)
@Mixin({ ItemStack.class })
public abstract class MixinItemStack
{
    public MixinItemStack() {
        super();
    }
    
    @Inject(method = { "getMaxStackSize" }, at = { @At("HEAD") }, cancellable = true)
    public void getItemStackLimit(final CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (HookTranslator.mc.player != null && HookTranslator.mc.player.isCreative()) {
            callbackInfoReturnable.setReturnValue(64);
        }
    }
    
    @Overwrite
    public void setItemDamage(final int itemDamage) {
        ItemStack.class.cast(this).itemDamage = itemDamage;
    }
}
