package wtf.diablo.client.util.mc.entity;

import net.minecraft.client.Minecraft;

public final class Rotation {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static final Rotation ZERO = new Rotation(0.0F, 0.0F);

    private final float yaw;

    private final float pitch;

    public Rotation(final float yaw, final float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Rotation() {
        this.yaw = mc.thePlayer.rotationYaw;
        this.pitch = mc.thePlayer.rotationPitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}