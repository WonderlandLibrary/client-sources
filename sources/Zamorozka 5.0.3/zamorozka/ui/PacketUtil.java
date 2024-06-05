package zamorozka.ui;

import net.minecraft.network.play.client.CPacketPlayer;

public class PacketUtil extends Wrapper {
    public static void sendPlayerPosPacket(double x, double y, double z, boolean ground) {
        getPlayer().connection.sendPacket(new CPacketPlayer.Position(x, y, z, ground));
    }

    public static void sendPlayerLookPacket(double x, double y, double z, float yaw, float pitch, boolean ground) {
        getPlayer().connection.sendPacket(new CPacketPlayer.Position(x, y, z, ground));
    }

    public static void sendLookPacket(float yaw, float pitch, boolean b) {
        sendPlayerLookPacket(getPlayer().posX, getPlayer().posY, getPlayer().posZ, yaw, pitch, getPlayer().onGround);
    }

    public static void addPlayerPacket(boolean onGround) {
        addPlayerLookPacket(getPlayer().posX, getPlayer().posY, getPlayer().posZ, getPlayer().rotationYaw, getPlayer().rotationPitch, onGround);
    }

    public static void sendPlayerPacket(boolean onGround) {
        sendPlayerLookPacket(getPlayer().posX, getPlayer().posY, getPlayer().posZ, getPlayer().rotationYaw, getPlayer().rotationPitch, onGround);
    }

    public static void addPlayerOffsetPacket(double x, double y, double z, boolean ground) {
        addPlayerLookPacket(getPlayer().posX + x, getPlayer().posY + y, getPlayer().posZ + z, getPlayer().rotationYaw,
                getPlayer().rotationPitch, ground);
    }

    public static void addPlayerLookPacket(double posX, double posY, double posZ, float rotyaw, float rotpitch, boolean ground) {
        getPlayer().connection.sendPacket(new CPacketPlayer.Position(posX, posY, posZ, ground));
    }

    public static void sendPlayerOffsetPacket(double x, double y, double z, boolean ground) {
        sendPlayerLookPacket(getPlayer().posX + x, getPlayer().posY + y, getPlayer().posZ + z, getPlayer().rotationYaw,
                getPlayer().rotationPitch, ground);
    }

}