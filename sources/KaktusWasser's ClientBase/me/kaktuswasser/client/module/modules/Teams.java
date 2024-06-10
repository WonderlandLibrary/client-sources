// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.util.Iterator;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.module.Module;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class Teams extends Module
{
    public Teams() {
        super("Teams", -17473, Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
    }
    
    public String getTabName(final EntityPlayer player) {
        String realName = "";
        for (final Object o : Teams.mc.ingameGUI.getTabList().getPlayerList()) {
            final NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)o;
            final String mcName = Teams.mc.ingameGUI.getTabList().func_175243_a(playerInfo);
            if (mcName.contains(player.getName()) && player.getName() != mcName) {
                realName = mcName;
            }
        }
        return realName;
    }
}
