package club.bluezenith.module.modules.combat;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.util.client.ServerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.util.BlockPos;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class AntiBot extends Module {

    private static final BlockPos basePos = new BlockPos(0, 0, 0);

    public final List<EntityLivingBase> bots = newArrayList();
    private final BooleanValue remove = new BooleanValue("Remove bots", false).setIndex(2);
    public AntiBot() {
        super("AntiBot", ModuleCategory.COMBAT);
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(player != null && player.ticksExisted > 40 && player.ticksExisted % 10 == 0) {
            for (Entity ent : world.getLoadedEntityList()) {
                if (ent != player && ent instanceof EntityPlayer && !isInTab((EntityPlayer) ent)) {
                    if (remove.get()) {
                        world.removeEntity(ent);
                    } else {
                        bots.add((EntityLivingBase) ent);
                    }
                }
            }
        }
    }

    @Listener
    public void onPacket(PacketEvent event) {
        if(ServerUtils.hypixel) {
            if(event.packet instanceof S0CPacketSpawnPlayer) {
                final S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) event.packet;
                if(player != null && player.ticksExisted > 10 && !player.playerPosition.equals(basePos)) {
                    final Entity entity = world.getEntityByID(packet.getEntityID());
                    if(!(entity instanceof EntityPlayer) || entity == player) return;
                    if(player.getDistanceToEntity(entity) < 10) {
                        BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                                displayName,
                                "Possible bot detected: " + entity.getDisplayName().getUnformattedText(),
                                2500
                        );
                        bots.add((EntityPlayer)entity);
                    }
                }
            }
        }
    }

    @Listener
    public void refresh(SpawnPlayerEvent event) {
        bots.clear();
    }

    static boolean isInTab(EntityPlayer entity) {
        return mc.getNetHandler().getPlayerInfoMap().stream().anyMatch(info -> info.getGameProfile().getName().equals(entity.getGameProfile().getName()));
    }
}
