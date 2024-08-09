package mathax.client.utils.vayzeutils;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import static mathax.client.utils.vayzeutils.AntiWallImport.mc;

public class AntiWallUtil {
    public static void WallUpdate1() {
        assert mc.player != null;
        if (mc.player.isInsideWall()) {
            mc.player.updatePosition(mc.player.getX(),mc.player.getY() + 1,mc.player.getZ());
        }
    }
    public static void WallUpdate2() {
        assert mc.player != null;
        if (mc.player.isInsideWall()) {
            mc.player.updatePosition(mc.player.getX(),mc.player.getY() + 2,mc.player.getZ());
        }
    }
    public static void WallUpdate3() {
        assert mc.player != null;
        if (mc.player.isInsideWall()) {
            mc.player.noClip = true;
            mc.player.setVelocity(mc.player.getVelocity().getX(),0.3,mc.player.getVelocity().getZ());
        }
        else {
            mc.player.noClip = false;
        }
    }
    public static void WallUpdate4() {
        assert mc.player != null;
        if (mc.player.isInsideWall()) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), true));
            mc.player.updatePosition(mc.player.getX(),mc.player.getY() + 1.5,mc.player.getZ());
        }
    }
}
