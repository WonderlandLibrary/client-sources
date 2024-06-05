// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.combat;

import java.util.Iterator;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.client.entity.EntityPlayerSP;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.util.StringUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.property.properties.EnumProperty;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "AntiBot", renderName = "Anti Bot", description = "Removed cheat detection bots from the game", category = ModuleCategory.COMBAT)
public final class AntiBotModule extends Module
{
    public final List<EntityPlayer> bots;
    private final EnumProperty<Mode> enumProperty;
    public EventListener<EventUpdate> eventUpdateEventListener;
    public EventListener<EventReceivePacket> eventReceivePacketEventListener;
    
    public AntiBotModule() {
        this.bots = new ArrayList<EntityPlayer>();
        this.enumProperty = new EnumProperty<Mode>("Mode", Mode.WATCHDOG);
        this.eventUpdateEventListener = (event -> {
            if (event.isPre()) {
                final ArrayList retards = new ArrayList<String>();
                this.mc.thePlayer.sendQueue.getPlayerInfoMap().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final NetworkPlayerInfo info = iterator.next();
                    retards.add(info.getGameProfile().getName());
                }
                this.setSuffix(StringUtils.upperSnakeCaseToPascal(this.enumProperty.getValueAsString()));
                this.mc.theWorld.playerEntities.iterator();
                final Iterator iterator2;
                while (iterator2.hasNext()) {
                    final EntityPlayer retard = iterator2.next();
                    if (retard.ticksExisted >= 20 && !retards.contains(retard.getName()) && !PlayerUtils.checkPing(retard) && !(retard instanceof EntityPlayerSP) && !this.bots.contains(retard)) {
                        this.bots.add(retard);
                    }
                }
            }
            return;
        });
        this.eventReceivePacketEventListener = (event -> {
            if (event.getPacket() instanceof S07PacketRespawn) {
                this.bots.clear();
            }
        });
    }
    
    private enum Mode
    {
        WATCHDOG;
    }
}
