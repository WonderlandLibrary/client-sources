package me.mees.remix.modules.world.antibot;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import java.util.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;

public class Mineplex extends Mode<AntiBot>
{
    public Mineplex(final AntiBot parent) {
        super(parent, "Mineplex");
    }
    
    public boolean isReal(final EntityPlayer player) {
        for (final NetworkPlayerInfo npi : this.mc.thePlayer.sendQueue.getPlayerInfoMap()) {
            if (npi != null && npi.getGameProfile() != null && player.getGameProfile() != null && npi.getGameProfile().getId().toString().equals(player.getGameProfile().getId().toString()) && player.getEntityId() <= 1000000000) {
                if (player.getName().startsWith("§c")) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
    
    @Subscriber
    public void onPacket(final EventPacketReceive event) {
        if (event.getPacket() instanceof S0CPacketSpawnPlayer) {
            final S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
            final int entityX = packet.getX() / 32;
            final int entityY = packet.getY() / 32;
            final int entityZ = packet.getZ() / 32;
            if (this.mc.thePlayer.ticksExisted != 0 && entityY > this.mc.thePlayer.posY && this.mc.thePlayer.getDistance(entityX, entityY, entityZ) < 20.0 && !this.mc.theWorld.getSpawnPoint().equals(new BlockPos(entityX, this.mc.theWorld.getSpawnPoint().getY(), entityZ))) {
                event.setCancelled(true);
            }
        }
    }
}
