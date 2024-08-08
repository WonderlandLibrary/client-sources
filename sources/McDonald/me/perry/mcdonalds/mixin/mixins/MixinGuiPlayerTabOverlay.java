// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin.mixins;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import me.perry.mcdonalds.features.modules.misc.ExtraTab;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.List;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.Gui;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay extends Gui
{
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/util/List;subList(II)Ljava/util/List;"))
    public List<NetworkPlayerInfo> subListHook(final List<NetworkPlayerInfo> list, final int fromIndex, final int toIndex) {
        return list.subList(fromIndex, ExtraTab.getINSTANCE().isEnabled() ? Math.min(ExtraTab.getINSTANCE().size.getValue(), list.size()) : toIndex);
    }
    
    @Inject(method = { "getPlayerName" }, at = { @At("HEAD") }, cancellable = true)
    public void getPlayerNameHook(final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfoReturnable<String> info) {
        if (ExtraTab.getINSTANCE().isEnabled()) {
            info.setReturnValue(ExtraTab.getPlayerName(networkPlayerInfoIn));
        }
    }
}
