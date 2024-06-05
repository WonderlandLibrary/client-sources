package zamorozka.ui;

public class Rotation2 implements MCUtil {

    float yaw;
    float pitch;

    /**
     * Patch gcd exploit in aim
     *
     * @see net.minecraft.client.renderer.EntityRenderer.updateCameraAndRender
     */
    private void fixedSensitivity(float sensitivity) {
        float f = sensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 1.2F;

        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
    }
}