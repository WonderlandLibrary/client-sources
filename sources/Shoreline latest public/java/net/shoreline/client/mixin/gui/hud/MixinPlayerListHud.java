package net.shoreline.client.mixin.gui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.shoreline.client.Shoreline;
import net.shoreline.client.impl.event.gui.hud.PlayerListEvent;
import net.shoreline.client.impl.event.gui.hud.PlayerListNameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.List;

/**
 * @author linus
 * @since 1.0
 */
@Mixin(PlayerListHud.class)
public abstract class MixinPlayerListHud {
    //
    @Shadow
    @Final
    private static Comparator<PlayerListEntry> ENTRY_ORDERING;
    //
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract Text applyGameModeFormatting(PlayerListEntry entry, MutableText name);

    @Inject(method = "getPlayerName", at = @At(value = "HEAD"), cancellable = true)
    private void hookGetPlayerName(PlayerListEntry entry, CallbackInfoReturnable<Text> cir) {
        Text text;
        if (entry.getDisplayName() != null) {
            text = applyGameModeFormatting(entry, entry.getDisplayName().copy());
        } else {
            text = applyGameModeFormatting(entry, Team.decorateName(entry.getScoreboardTeam(), Text.literal(entry.getProfile().getName())));
        }
        PlayerListNameEvent playerListNameEvent = new PlayerListNameEvent(text, entry.getProfile().getId());
        Shoreline.EVENT_HANDLER.dispatch(playerListNameEvent);
        if (playerListNameEvent.isCanceled()) {
            cir.cancel();
            cir.setReturnValue(playerListNameEvent.getPlayerName());
        }
    }

    /**
     * @param cir
     */
    @Inject(method = "collectPlayerEntries", at = @At(value = "HEAD"), cancellable = true)
    private void hookCollectPlayerEntries(CallbackInfoReturnable<List<PlayerListEntry>> cir) {
        PlayerListEvent playerListEvent = new PlayerListEvent();
        Shoreline.EVENT_HANDLER.dispatch(playerListEvent);
        if (playerListEvent.isCanceled()) {
            cir.cancel();
            cir.setReturnValue(client.player.networkHandler.getListedPlayerListEntries()
                    .stream().sorted(ENTRY_ORDERING).limit(playerListEvent.getSize()).toList());
        }
    }
}
