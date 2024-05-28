package dev.vertic.util.player;

import dev.vertic.Utils;

public class PlayerUtil implements Utils {

    public static boolean isInGame() {
        return mc.thePlayer != null && mc.theWorld != null;
    }

    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0.0F && mc.thePlayer.moveStrafing != 0.0F;
    }

}
