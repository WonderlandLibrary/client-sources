package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GCDFix implements MCUtil {
    public float yaw;
    public float pitch;

    public GCDFix(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Final method for getting sexual rotations
     *
     * @return fixed rotation (float)
     * @author koloslolya
     */
    public static float getFixedRotation(float rot) {
        return getDeltaMouse(rot) * getGCDValue();
    }

    /**
     * Returns the actual value of the GCD
     * (taking into account the sensitivity of the mouse and
     * the multipliers of the minecraft setAngles method)
     *
     * @return (float) GCD Factor
     * @author koloslolya
     * @see net.minecraft.entity.Entity
     * Method: setAngles
     */
    public static float getGCDValue() {
        return (float) (getGCD() * .15);
    }

    /**
     * Generates a GCD value
     *
     * @author koloslolya
     * @see net.minecraft.client.renderer.EntityRenderer
     * Method: updateCameraAndRender
     */
    public static float getGCD() {
        float f1;
        return (f1 = (float) (mc.gameSettings.mouseSensitivity * .6 + .2)) * f1 * f1 * 8;
    }

    /**
     * It needs for quality GCD fix
     * This method selects the closest value that the mouse can make
     * (Simulates deltaMouse values)
     * <p>
     * How to use: RotationUtils.getDeltaMouse(20.0F) * RotationUtils.getGCDValue()
     *
     * @param delta (float), delta yaw/pitch
     * @return (float) Closest value to mouse delta
     * @author koloslolya
     */
    public static float getDeltaMouse(float delta) {
        return Math.round(delta / getGCDValue());
    }

    public static GCDFix copy$default(GCDFix var0, float var1, float var2, int var3, Object var4) {
        if ((var3 & 1) != 0) {
            var1 = var0.yaw;
        }
        if ((var3 & 2) != 0) {
            var2 = var0.pitch;
        }
        return var0.copy(var1, var2);
    }

    public final void toPlayer(EntityPlayer player) {
        float var2 = this.yaw;
        boolean var3 = false;
        if (!Float.isNaN(var2)) {
            var2 = this.pitch;
            var3 = false;
            if (!Float.isNaN(var2)) {
                this.fixedSensitivity(Minecraft.getMinecraft().gameSettings.mouseSensitivity);
                player.rotationYaw = this.yaw;
                player.rotationPitch = this.pitch;
                return;
            }
        }
    }

    public void fixedSensitivity(float sensitivity) {
        float f = sensitivity * 0.6f + 0.2f;
        float gcd = f * f * f * 1.2f;
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;
    }

    public final float getYaw() {
        return this.yaw;
    }

    public final void setYaw(float var1) {
        this.yaw = var1;
    }

    public final float getPitch() {
        return this.pitch;
    }

    public final void setPitch(float var1) {
        this.pitch = var1;
    }

    public final float component1() {
        return this.yaw;
    }

    public final float component2() {
        return this.pitch;
    }

    public final GCDFix copy(float yaw, float pitch) {
        return new GCDFix(yaw, pitch);
    }

    public String toString() {
        return "Rotation(yaw=" + this.yaw + ", pitch=" + this.pitch + ")";
    }

    public int hashCode() {
        return Float.hashCode(this.yaw) * 31 + Float.hashCode(this.pitch);
    }

    public boolean equals(Object var1) {
        if (this != var1) {
            if (var1 instanceof GCDFix) {
                GCDFix var2 = (GCDFix) var1;
                return Float.compare(this.yaw, var2.yaw) == 0 && Float.compare(this.pitch, var2.pitch) == 0;
            }
            return false;
        }
        return true;
    }
}