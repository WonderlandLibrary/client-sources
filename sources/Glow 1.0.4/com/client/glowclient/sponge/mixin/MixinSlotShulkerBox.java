package com.client.glowclient.sponge.mixin;

import net.minecraftforge.fml.relauncher.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.inventory.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;

@SideOnly(Side.CLIENT)
@Mixin({ SlotShulkerBox.class })
public abstract class MixinSlotShulkerBox extends Slot
{
    MixinSlotShulkerBox() {
        super((IInventory)null, 0, 0, 0);
    }
    
    @Inject(method = { "isItemValid(Lnet/minecraft/item/ItemStack;)Z" }, at = { @At("HEAD") }, cancellable = true)
    public void isItemValid(final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (HookTranslator.mc.player.isCreative()) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
