package meteordevelopment.meteorclient.utils.player;

import static meteordevelopment.meteorclient.MeteorClient.mc;

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
            mc.player.setAir((int) BoostType1Speed);
        }
    }
}
