package mathax.client.utils.player;

import static mathax.client.MatHax.mc;

public class DamageBoostUtil {

    private static double BoostType1Speed = 0.7;

    //Used to set the Boosting Status
    public static boolean isBoosting() {
        return false;
    }

    //Only Runs if you are outside hurttime
    public static boolean isHurtTime() {
        return mc.player.hurtTime > 0;
    }

    //AirStrafeBoost
    public static void BoostType1() {
        if (isBoosting() == true && !mc.player.isOnGround()) {
            mc.player.airStrafingSpeed = (float) BoostType1Speed;
        }
    }
}
