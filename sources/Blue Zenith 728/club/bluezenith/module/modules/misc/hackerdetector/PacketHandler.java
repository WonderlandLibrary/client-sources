package club.bluezenith.module.modules.misc.hackerdetector;

import club.bluezenith.module.modules.misc.HackerDetector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S14PacketEntity.S15PacketEntityRelMove;
import net.minecraft.network.play.server.S14PacketEntity.S17PacketEntityLookMove;

import static club.bluezenith.util.MinecraftInstance.mc;

public class PacketHandler {

    private final HackerDetector hackerDetector;

    public PacketHandler(HackerDetector hackerDetector) {
        this.hackerDetector = hackerDetector;
    }

    private boolean doesPlayerExist(int entityID) {
        final Entity player = mc.theWorld.getEntityByID(entityID);
        return (player instanceof EntityPlayer) && hackerDetector.getPlayerWatcher().getPlayerByIdOrNull(player.getEntityId()) != null;
    }

    public void onMovement(S14PacketEntity packet) {
        if(packet instanceof S15PacketEntityRelMove || packet instanceof S17PacketEntityLookMove) {
            if (doesPlayerExist(packet.entityId)) {
                final PlayerInfo playerInfo = hackerDetector.getPlayerWatcher().getPlayerByIdOrNull(packet.entityId);
                playerInfo.handleMovement(packet, packet.posX, packet.posY, packet.posZ, packet.onGround);
            }
        }
    }

}
