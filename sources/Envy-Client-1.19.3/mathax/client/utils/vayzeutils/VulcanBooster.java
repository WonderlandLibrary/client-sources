package mathax.client.utils.vayzeutils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class VulcanBooster {
    public static void VulcanBoost(double Boost) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player != null) {
            if (player.isTouchingWater()) {
                Vec3d lookVector = player.getRotationVector();

                double x = Math.cos(Math.toRadians(player.getYaw() + 90.0F));
                double z = Math.sin(Math.toRadians(player.getYaw() + 90.0F));

                Vec3d motionVector = new Vec3d(x, 0.3, z).normalize().multiply(Boost); // Set the desired velocity
                player.setVelocity(motionVector);
            }
        }
    }
}
