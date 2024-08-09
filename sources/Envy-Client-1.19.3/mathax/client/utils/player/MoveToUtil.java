package mathax.client.utils.player;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.util.math.Vec3d;

import static mathax.client.MatHax.mc;

public class MoveToUtil {

    public static void moveTo(double xpos, double ypos, double zpos, boolean  preserve, boolean onground) {
        Vec3d newPos = new Vec3d(xpos, ypos, zpos);
        double dist = PlayerUtils.distanceTo(newPos);
        int packetsRequired = (int) Math.ceil(dist / 10.0);
        sendpackets(packetsRequired, preserve, onground);
        moveplayer(xpos, ypos, zpos, preserve, onground);
    }

    public static void sendpackets(int packetsRequired, boolean preserve, boolean onground) {
        if (mc.player.hasVehicle()) {
            for (int packetNumber = 0; packetNumber < (packetsRequired); packetNumber++) {
                mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
            }
        } else {
            for (int packetNumber = 0; packetNumber < (packetsRequired); packetNumber++) {
                if (preserve) {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), onground));
                } else {
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(onground));
                }
            }
        }
    }
    public static void moveplayer(double xpos, double ypos, double zpos, boolean preserve, boolean onground) {
        if (mc.player.hasVehicle()) {
            mc.player.getVehicle().setPosition(xpos, ypos, zpos);
            mc.player.networkHandler.sendPacket(new VehicleMoveC2SPacket(mc.player.getVehicle()));
        } else {
            if (preserve) {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(xpos, ypos, zpos, mc.player.getYaw(), mc.player.getPitch(), onground));
            } else {
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(xpos, ypos, zpos, onground));
            }
            mc.player.setPosition(xpos, ypos, zpos);
        }
    }


}
