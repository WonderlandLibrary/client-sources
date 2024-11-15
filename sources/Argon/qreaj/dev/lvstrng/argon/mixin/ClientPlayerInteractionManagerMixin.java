// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.modules.impl.NoBreakDelay;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ClientPlayerInteractionManager.class})
public class ClientPlayerInteractionManagerMixin {
    @Shadow
    private int blockBreakingCooldown;

    @Redirect(method = {"updateBlockBreakingProgress"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;blockBreakingCooldown:I", opcode = 180, ordinal = 0))
    public int updateBlockBreakingProgress(final ClientPlayerInteractionManager clientPlayerInteractionManager) {
        final int blockBreakingCooldown = this.blockBreakingCooldown;
        return Argon.INSTANCE.getModuleManager().getModuleByClass(NoBreakDelay.class).isEnabled() ? 0 : blockBreakingCooldown;
    }
}
