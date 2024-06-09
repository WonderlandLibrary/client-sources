// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.util.Iterator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

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
