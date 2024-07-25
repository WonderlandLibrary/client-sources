package club.bluezenith.module.modules.fun;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class FDPFinder extends Module {

    public FDPFinder() {
        super("FDPFinder", ModuleCategory.FUN);
        this.displayName = "FDP Finder";
    }
    @Listener
    public void onSpawn(PacketEvent event) {
        final Packet<?> p = event.packet;
        if(p instanceof S0CPacketSpawnPlayer) {
            final S0CPacketSpawnPlayer s = (S0CPacketSpawnPlayer) p;
            final NetworkPlayerInfo info = mc.getNetHandler().getPlayerInfo(s.getPlayer());
            if (info != null) {
                final GameProfile profile = info.getGameProfile();
                if (profile != null) {
                    String name = profile.getName();
                    if (name.matches("F\\dD\\dP\\d_[A-z0-9]{3}")) {
                        BlueZenith.getBlueZenith().getTargetManager().addTarget(name);
                        BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                                displayName,
                                "Found a FDP user: " + name,
                                2500

                        );
                    }
                }
            }
        }
    }

    @Listener
    public void the(UpdatePlayerEvent event) {
        if(mc.thePlayer.ticksExisted % 50 == 0) {
            for (EntityPlayer ent : mc.theWorld.playerEntities) {
                final GameProfile profile = ent.getGameProfile();
                if(profile != null) {
                    String name = profile.getName();
                    if (name.matches("F\\dD\\dP\\d_[A-z0-9]{3}")) {
                        BlueZenith.getBlueZenith().getTargetManager().addTarget(name);
                        BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                                displayName,
                                "Found a FDP user: " + name,
                                2500

                        );
                    }
                }
            }
        }
    }
 }
