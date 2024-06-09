package me.travis.wurstplus.module.modules.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.travis.wurstplus.util.Friends;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.client.network.NetworkPlayerInfo;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;

@Module.Info(name = "TabFriends", category = Module.Category.RENDER)
public class TabFriends extends Module
{
    public static TabFriends INSTANCE;
    
    public TabFriends() {
        TabFriends.INSTANCE = this;
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        final String dname = (networkPlayerInfoIn.getDisplayName() != null) ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName((Team)networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        if (Friends.isFriend(dname)) {
            return ChatFormatting.GOLD.toString() + dname;
        }
        return dname;
    }
}
