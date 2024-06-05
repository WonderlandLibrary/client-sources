package cn.Hanabi.injection.mixins;

import cn.Hanabi.injection.interfaces.*;
import net.minecraft.client.gui.*;
import com.google.common.collect.*;
import net.minecraft.client.network.*;
import cn.Hanabi.modules.Player.*;
import net.minecraft.scoreboard.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiPlayerTabOverlay.class })
public class MixinGuiPlayerTabOverlay implements IGuiPlayerTabOverlay
{
    @Shadow
    @Final
    private static Ordering<NetworkPlayerInfo> field_175252_a;
    
    @Override
    public Ordering<NetworkPlayerInfo> getField() {
        return MixinGuiPlayerTabOverlay.field_175252_a;
    }
    
    @Overwrite
    public String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        if (Teams.isClientFriend(networkPlayerInfoIn.getGameProfile().getName())) {
            return (networkPlayerInfoIn.getDisplayName() != null) ? ("§a[ClientFriend]§r" + networkPlayerInfoIn.getDisplayName().getFormattedText()) : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), "§a[ClientFriend]§r" + networkPlayerInfoIn.getGameProfile().getName());
        }
        if (Teams.isMod(networkPlayerInfoIn.getGameProfile().getName())) {
            return (networkPlayerInfoIn.getDisplayName() != null) ? ("§b[MOD]§r" + networkPlayerInfoIn.getDisplayName().getFormattedText()) : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), "§d[MOD]§r" + networkPlayerInfoIn.getGameProfile().getName());
        }
        return (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }
}
