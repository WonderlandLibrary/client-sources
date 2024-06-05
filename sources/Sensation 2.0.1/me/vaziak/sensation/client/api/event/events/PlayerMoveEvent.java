package me.vaziak.sensation.client.api.event.events;

import me.vaziak.sensation.Sensation;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class PlayerMoveEvent {


    public double x;
    public double y;
    public double z;

    public PlayerMoveEvent(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double mx) {
        x = mx;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double mz) {
        z = mz;
    }
    double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward, strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe, 
			yaw = Sensation.instance.cheatManager.isModuleEnabled("Target Strafe") && !Sensation.instance.cheatManager.isModuleEnabled("Scaffold") ? Minecraft.getMinecraft().thePlayer.serverSideYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    
	public void setMoveSpeed(PlayerMoveEvent e, double moveSpeed, double strafemult, double mult, double d) {
		double speed = Math.max(moveSpeed, getBaseMoveSpeed());
		if (forward == 0.0F && strafe == 0.0F) {
			e.setX(0);
			e.setZ(0);
		}
		if (forward != 0 && strafe != 0) {
			forward = forward * Math.sin(Math.PI / d);
			strafe = strafe * Math.cos(Math.PI / d);
		}
		e.setX((forward * speed * -Math.sin(Math.toRadians(yaw)) + (strafe * strafemult) * speed * Math.cos(Math.toRadians(yaw))) * mult);
		e.setZ((forward * speed * Math.cos(Math.toRadians(yaw)) - (strafe * strafemult) * speed * -Math.sin(Math.toRadians(yaw))) * mult);
	}

	public void setMoveSpeed(double moveSpeed) {
		double speed = Math.max(moveSpeed, getBaseMoveSpeed());
		if (forward == 0.0F && strafe == 0.0F) {
			setX(0);
			setZ(0);
		}
		if (forward != 0 && strafe != 0) {
			forward = forward * Math.sin(Math.PI / 4);
			strafe = strafe * Math.cos(Math.PI / 4);
		}
		setX((forward * speed * -Math.sin(Math.toRadians(yaw)) + (strafe) * speed * Math.cos(Math.toRadians(yaw))) * .99);
		setZ((forward * speed * Math.cos(Math.toRadians(yaw)) - (strafe) * speed * -Math.sin(Math.toRadians(yaw))) * .99);
	}
	
	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}
}
