package me.travis.wurstplus.mixin.client;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.travis.wurstplus.module.modules.render.TabFriends;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay
{
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable returnable) {
        if (TabFriends.INSTANCE.isEnabled()) {
            returnable.cancel();
            returnable.setReturnValue(TabFriends.getPlayerName(networkPlayerInfoIn));
        }
    }
}