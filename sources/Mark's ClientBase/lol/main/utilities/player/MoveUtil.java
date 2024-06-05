package lol.main.utilities.player;

import lol.main.IMinecraft;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MoveUtil implements IMinecraft {

    public static boolean isMoving(){
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

}
