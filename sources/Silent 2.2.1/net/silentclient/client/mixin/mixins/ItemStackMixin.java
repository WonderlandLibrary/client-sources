package net.silentclient.client.mixin.mixins;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    private String silent$cachedDisplayName;

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void silent$returnCachedDisplayName(CallbackInfoReturnable<String> cir) {
        if (silent$cachedDisplayName != null) {
            cir.setReturnValue(silent$cachedDisplayName);
        }
    }

    @Inject(method = "getDisplayName", at = @At("RETURN"))
    private void silent$cacheDisplayName(CallbackInfoReturnable<String> cir) {
        silent$cachedDisplayName = cir.getReturnValue();
    }

    @Inject(method = "setStackDisplayName", at = @At("HEAD"))
    private void silent$resetCachedDisplayName(String displayName, CallbackInfoReturnable<ItemStack> cir) {
        silent$cachedDisplayName = null;
    }

    //#if MC==10809
    @Redirect(
            method = "getTooltip",
            at = @At(value = "INVOKE", target = "Ljava/lang/Integer;toHexString(I)Ljava/lang/String;")
    )
    private String silent$fixHexColorString(int i) {
        return String.format("%06X", i);
    }
    //#endif
}
