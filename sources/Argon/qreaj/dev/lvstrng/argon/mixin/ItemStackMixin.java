// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.impl.NoBounce;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ItemStack.class})
public class ItemStackMixin {
    @Inject(method = {"getBobbingAnimationTime"}, at = {@At("HEAD")}, cancellable = true)
    private void removeBounceAnimation(final CallbackInfoReturnable cir) {
        if (Argon.mc.player == null) {
            return;
        }
        final NoBounce noBounce = (NoBounce) Argon.INSTANCE.getModuleManager().getModuleByClass(NoBounce.class);
        if (Argon.INSTANCE != null && Argon.mc.player != null && noBounce.isEnabled() && Argon.mc.player.getMainHandStack().isOf(Items.END_CRYSTAL)) {
            cir.setReturnValue(0);
        }
    }
}
