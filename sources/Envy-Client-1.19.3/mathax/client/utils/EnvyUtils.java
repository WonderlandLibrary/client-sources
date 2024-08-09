package mathax.client.utils;

import static mathax.client.MatHax.mc;

public class EnvyUtils {

    public static void pos() {
        mc.player.getPos();
    }
    public static void velocity() {
        mc.player.getVelocity();
    }
    public static void jump() {
        mc.player.jump();
    }
    public static void fall() {
        mc.player.setVelocity(mc.player.getVelocity().x, -0.5, mc.player.getVelocity().z);
    }
    public static void sprint() {
        mc.player.setSprinting(true);
    }
}
