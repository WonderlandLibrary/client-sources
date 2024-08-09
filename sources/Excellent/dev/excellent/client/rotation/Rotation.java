package dev.excellent.client.rotation;

import dev.excellent.api.interfaces.game.IMinecraft;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rotation implements IMinecraft {

    private float yaw, pitch;

    public Rotation(Entity entity) {
        yaw = entity.rotationYaw;
        pitch = entity.rotationPitch;
    }

    public double getDelta(Rotation targetRotation) {
        double yawDelta = MathHelper.wrapDegrees(targetRotation.getYaw() - yaw);
        double pitchDelta = MathHelper.wrapDegrees(targetRotation.getPitch() - pitch);

        return Math.hypot(Math.abs(yawDelta), Math.abs(pitchDelta));
    }

    public static Rotation getReal() {
        return new Rotation(FreeLookHandler.getFreeYaw(), FreeLookHandler.getFreePitch());
    }


}