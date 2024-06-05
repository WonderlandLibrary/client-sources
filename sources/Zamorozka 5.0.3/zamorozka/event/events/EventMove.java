package zamorozka.event.events;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import zamorozka.event.Event;

public class EventMove extends Event {
    private double x;
    private double y;
    private double z;
    public float strafe;
    public float forward;
    public float friction;
    public float yaw;
    public boolean canceled;

    public EventMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getMovementSpeed() {
        double baseSpeed = 0.2873D;
        if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public double getMovementSpeed(double baseSpeed) { 
    	double speed = baseSpeed;
        if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            return speed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return speed;
    }

    public void setZ(final double z) {
        this.z = z;
    }

    public double getLegitMotion() {
    	return 0.41999998688697815D;
    }

    public double getMotionY(double mY) {
		if (Minecraft.getMinecraft().player.isPotionActive(Potion.getPotionById(8))) {
			mY += (Minecraft.getMinecraft().player.getActivePotionEffect(Potion.getPotionById(8)).getAmplifier() + 1) * 0.1;
		}
		return mY;
    }
}