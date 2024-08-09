package mathax.client.utils.player;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class SwimSpeedUtils {
    public static void throwPlayer(double velocityMultiplier) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player != null) {
            Vec3d lookVector = player.getRotationVector();
            Vec3d motionVector = lookVector.multiply(velocityMultiplier); // Set the desired velocity

            player.setVelocity(motionVector);
        }
    }
}
