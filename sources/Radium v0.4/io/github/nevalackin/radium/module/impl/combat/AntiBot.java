package io.github.nevalackin.radium.module.impl.combat;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.event.impl.player.DamageEntityEvent;
import io.github.nevalackin.radium.event.impl.world.WorldLoadEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ModuleInfo(label = "Anti Bot", category = ModuleCategory.COMBAT)
public final class AntiBot extends Module {

    public static final List<EntityPlayer> BOTS = new ArrayList<>();

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S0CPacketSpawnPlayer) {
            S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.getPacket();
            UUID uuid = packet.getPlayer();
            if (uuid != Wrapper.getPlayer().getUniqueID()) {
                NetworkPlayerInfo info = Wrapper.getNetHandler().getPlayerInfo(uuid);
                if (info == null || info.getResponseTime() != 1)
                    BOTS.add(Wrapper.getWorld().getPlayerEntityByUUID(uuid));
            }
        }
    }

    @Listener
    public void onWorldChange(WorldLoadEvent event) {
        BOTS.clear();
    }

    @Listener
    public void onDamageEntityEvent(DamageEntityEvent event) {
        if (event.getEntity() instanceof EntityOtherPlayerMP)
            BOTS.remove(event.getEntity());
    }
}
