package net.shoreline.client.mixin.gui.hud;

import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.hud.PlayerListEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(PlayerListHud.class)
public class MixinPlayerListHud
{
    //
    @Shadow
    @Final
    private MinecraftClient client;
    //
    @Shadow
    @Final
    private static Comparator<PlayerListEntry> ENTRY_ORDERING;

    /**
     *
     * @param cir
     */
    @Inject(method = "collectPlayerEntries", at = @At(value = "HEAD"),
            cancellable = true)
    private void hookCollectPlayerEntries(CallbackInfoReturnable<List<PlayerListEntry>> cir)
    {
        PlayerListEvent playerListEvent = new PlayerListEvent();
        Shoreline.EVENT_HANDLER.dispatch(playerListEvent);
        if (playerListEvent.isCanceled())
        {
            cir.cancel();
            cir.setReturnValue(client.player.networkHandler.getListedPlayerListEntries()
                    .stream().sorted(ENTRY_ORDERING).toList());
        }
    }
}
