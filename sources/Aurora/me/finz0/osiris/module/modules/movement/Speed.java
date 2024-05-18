package me.finz0.osiris.module.modules.movement;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import net.minecraft.util.math.MathHelper;

public class Speed extends Module {
    public Speed() {
        super("Strafe", Category.MOVEMENT, "Makes you go fast");
    }

    int waitCounter;
    int forward = 1;

    Setting jump;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(jump = new Setting("Jump", this, true, "StrafeJump"));
    }


    public void onUpdate(){

        boolean boost = Math.abs(mc.player.rotationYawHead - mc.player.rotationYaw) < 90;

        if(mc.player.moveForward != 0) {
            if(!mc.player.isSprinting()) mc.player.setSprinting(true);
            float yaw = mc.player.rotationYaw;
            if(mc.player.moveForward > 0) {
                if(mc.player.movementInput.moveStrafe != 0) {
                    yaw += (mc.player.movementInput.moveStrafe > 0) ? -45 : 45;
                }
                forward = 1;
                mc.player.moveForward = 1.0f;
                mc.player.moveStrafing = 0;
            }else if(mc.player.moveForward < 0) {
                if(mc.player.movementInput.moveStrafe != 0) {
                    yaw += (mc.player.movementInput.moveStrafe > 0) ? 45 : -45;
                }
                forward = -1;
                mc.player.moveForward = -1.0f;
                mc.player.moveStrafing = 0;
            }
            if (mc.player.onGround) {
                mc.player.setJumping(false);
                if (waitCounter < 1) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
                float f = (float)Math.toRadians(yaw);
                if(jump.getValBoolean()) {
                    mc.player.motionY = 0.405;
                    mc.player.motionX -= (double) (MathHelper.sin(f) * 0.2f) * forward;
                    mc.player.motionZ += (double) (MathHelper.cos(f) * 0.2f) * forward;
                } else {
                    if(mc.gameSettings.keyBindJump.isPressed()){
                        mc.player.motionY = 0.405;
                        mc.player.motionX -= (double) (MathHelper.sin(f) * 0.2f) * forward;
                        mc.player.motionZ += (double) (MathHelper.cos(f) * 0.2f) * forward;
                    }
                }
            } else {
                if (waitCounter < 1) {
                    waitCounter++;
                    return;
                } else {
                    waitCounter = 0;
                }
                double currentSpeed = Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
                double speed = boost ? 1.0064 : 1.001;
                if(mc.player.motionY < 0) speed = 1;

                double direction = Math.toRadians(yaw);
                mc.player.motionX = (-Math.sin(direction) * speed * currentSpeed) * forward;
                mc.player.motionZ = (Math.cos(direction) * speed * currentSpeed) * forward;
            }
        }
    }

}
