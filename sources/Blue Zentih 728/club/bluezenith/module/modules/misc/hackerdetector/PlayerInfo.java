package club.bluezenith.module.modules.misc.hackerdetector;

import club.bluezenith.module.modules.misc.hackerdetector.checks.NoSlowA;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
    private final List<Check> checks = new ArrayList<>();

    private EntityPlayer playerEntity;
    private final GameProfile playerProfile;

    private double serverX, serverY, serverZ, delta, deltaY;
    private Vec3 current, prev;

    private boolean hasTeleported;
    private Vec3 teleportFrom, teleportTo;

    private int ticksSinceTeleport, ticksSinceVelocity;

    private boolean packetGround, isBlocking, isInVehicle;


    public PlayerInfo(EntityPlayer player) {
        this.playerProfile = player.getGameProfile();
        this.playerEntity = player;

        this.current = this.prev = player.getPositionVector();

        this.serverX = player.serverPosX;
        this.serverY = player.serverPosY;
        this.serverZ = player.serverPosZ;

        checks.add(new NoSlowA(this));
    }

    public void handleMovement(S14PacketEntity packet, byte x, byte y, byte z, boolean packetGround) {
        serverX += x;
        serverY += y;
        serverZ += z;

        this.prev = this.current;

        this.current = new Vec3(serverX / 32D, serverY / 32D, serverZ / 32D);

        this.delta = new Vec3(current.xCoord, 0, current.zCoord).distanceTo(new Vec3(prev.xCoord, 0, prev.zCoord));
        this.deltaY = current.yCoord - prev.yCoord;
        this.packetGround = packetGround;

        this.ticksSinceTeleport++;
        this.ticksSinceVelocity++;
        this.checks.forEach(check -> check.process(packet));
    }

    public Vec3 getCurrent() {
        return this.current;
    }

    public Vec3 getPrev() {
        return this.prev;
    }

    public double getDelta() {
        return this.delta;
    }

    public int getTicksSinceVelocity() {
        return this.ticksSinceVelocity;
    }

    public int getTicksSinceTeleport() {
        return this.ticksSinceTeleport;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean hasTeleported() {
        return hasTeleported;
    }

    public boolean isInVehicle() {
        return isInVehicle;
    }

    public boolean isPacketGround() {
        return packetGround;
    }

    public EntityPlayer getPlayerEntity() {
        return playerEntity;
    }

    public EntityPlayer updatePlayerEntity() {
        this.playerEntity = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(this.playerEntity.getUniqueID());
        return this.playerEntity;
    }

    public GameProfile getPlayerProfile() {
        return playerProfile;
    }
}
