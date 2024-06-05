    package client.metaware.impl.utils.util.other;

    import net.minecraft.client.Minecraft;

    public class FrictionUtil {

        public static Minecraft mc = Minecraft.getMinecraft();

        public static float applyFriction(float speed) {


            float value;
            float percent = 0;

            if(mc.thePlayer.isMoving() && mc.thePlayer.onGround) {
                percent = 99.5F;
            }

            if(mc.thePlayer.isMoving() && !mc.thePlayer.onGround) {
                percent = 98F;
            }

            if(mc.thePlayer.isMoving() && mc.thePlayer.isInWater()) {
                percent = 80.3F;
            }

            value = speed / 100 * percent;


            return value;

        }

        public static float applyCustomFriction(float speed, float friction) {


            float value;
            float percent = friction;
            value = speed / 100 * percent;


            return value;

        }

    }
