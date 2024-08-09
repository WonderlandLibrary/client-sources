package mathax.client.utils;


import static mathax.client.MatHax.mc;

public class AntiWallUtil {
    public static void WallUpdate1() {
        if (mc.player.isInsideWall()) {
            mc.player.updatePosition(mc.player.getX(),mc.player.getY() + 1,mc.player.getZ());
        }
    }
    public static void WallUpdate2() {
        if (mc.player.isInsideWall()) {
            mc.player.updatePosition(mc.player.getX(),mc.player.getY() + 2,mc.player.getZ());
        }
    }
    public static void WallUpdate3() {
        if (mc.player.isInsideWall()) {
            mc.player.noClip = true;
            mc.player.setVelocity(mc.player.getVelocity().getX(),0.3,mc.player.getVelocity().getZ());
        }
        else {
            mc.player.noClip = false;
        }
    }
}
