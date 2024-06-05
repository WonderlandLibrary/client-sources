// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.misc;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C02PacketUseEntity;
import gg.childtrafficking.smokex.SmokeXClient;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.entity.player.EntityPlayer;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "FriendProtect", renderName = "Friend Protect", description = "Prevents you from attacking friends.", category = ModuleCategory.VISUALS)
public final class FriendProtectModule extends Module
{
    private final EventListener<EventSendPacket> sendPacketEventListener;
    
    public FriendProtectModule() {
        this.sendPacketEventListener = (event -> {
            final MovingObjectPosition target = this.mc.objectMouseOver;
            if (target.entityHit instanceof EntityPlayer && PlayerUtils.checkPing((EntityPlayer)target.entityHit) && SmokeXClient.getInstance().getPlayerManager().isFriend(target.entityHit.getName())) {
                if (event.getPacket() instanceof C02PacketUseEntity) {
                    final C02PacketUseEntity c02PacketUseEntity = (C02PacketUseEntity)event.getPacket();
                    if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                        event.cancel();
                    }
                }
                if (event.getPacket() instanceof C0APacketAnimation) {
                    event.cancel();
                }
            }
        });
    }
}
