package me.zeroeightsix.kami.module.modules.dev;

import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.setting.Setting;
import me.zeroeightsix.kami.setting.Settings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.util.MovementInput;

/**
 * Created by TimG on 16 June 2019
 * Last Updated 16 June 2019 by hub
 */
@Module.Info(name = "GMEntitySpeed", category = Module.Category.HIDDEN, description = "Godmode EntitySpeed")
public class GMEntitySpeed extends Module {

    private Setting<Double> gmentityspeed = register(Settings.doubleBuilder("Speed").withRange(0.1, 10.0).withValue(1.0).build());

    private static void speedEntity(Entity entity, Double speed) {
        if (entity instanceof EntityLlama) {
            entity.rotationYaw = mc.player.rotationYaw;
            ((EntityLlama) entity).rotationYawHead = mc.player.rotationYawHead;
        }
        MovementInput movementInput = mc.player.movementInput;
        double forward = movementInput.moveForward;
        double strafe = movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            entity.motionX = 0.0D;
            entity.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }
            entity.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
            entity.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
            if (entity instanceof EntityMinecart) {
                EntityMinecart em = (EntityMinecart) entity;
                em.setVelocity((forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F))), em.motionY, (forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F))));
            }
        }
    }

    @Override
    public void onUpdate() {
        try {
            if (mc.player.getRidingEntity() != null) {
                speedEntity(mc.player.getRidingEntity(), gmentityspeed.getValue());
            }
        } catch (Exception e) {
            System.out.println("ERROR: Dude we kinda have a problem here:");
            e.printStackTrace();
        }
    }

}
