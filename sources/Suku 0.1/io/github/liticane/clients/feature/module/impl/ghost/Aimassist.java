package io.github.liticane.clients.feature.module.impl.ghost;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.render.Render2DEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.module.impl.combat.Teams;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

@Module.Info(name = "AimAssist", category = Module.Category.GHOST)
public class Aimassist extends Module {
    public final NumberProperty range = new NumberProperty("Range", this, 3, 3, 6, 0.1);
    public final NumberProperty speed = new NumberProperty("Speed", this, 1, 1, 25, 0.50);
    public final BooleanProperty vertical = new BooleanProperty("Vertical", this, false);
    public EntityLivingBase target;

    public TimerUtil timer = new TimerUtil();

    @SubscribeEvent
    private final EventListener<Render2DEvent> onRender2D = e -> {
        //shit
        setSuffix("" +speed.getValue());
        if (target == null || !canAttack(target)) {
            target = getClosest(range.getValue());
        }

        if (target == null) {
            timer.reset();
            return;
        }

        if (mc.settings.keyBindAttack.isKeyDown()) {
            this.faceEntity(target, Math.min(2.0 * this.speed.getValue(), this.speed.getValue() * 2.0 * Math.max(0.2, this.getDistanceFromMouse(target) / mc.settings.fovSetting)));
            //mc.player.rotationYaw += Math.random() * 0.5 - 0.25;
            // mc.player.rotationYaw = Math.round(mc.player.rotationYaw * 5000) / 5000.0F;
            mc.player.rotationYaw += 0.5 - 0.25;
            // mc.player.rotationYaw = mc.player.rotationYaw;
            if (vertical.isToggled()) {
                mc.player.rotationPitch += Math.random() * 0.2 - 0.1;
                mc.player.rotationPitch = Math.max(mc.player.rotationPitch, -90);
                mc.player.rotationPitch = Math.min(mc.player.rotationPitch, 90);
            }
        }

        timer.reset();

    };

    public boolean canAttack(EntityLivingBase target) {
        try {
            if (!(target instanceof EntityPlayer)) {
                return false;
            } else if (!mc.player.canEntityBeSeen(target)) {
                return false;
            } else if(target.isDead) {
                return  false;
            }

            // Use Teams.isTeammate with proper checks
            if (Client.INSTANCE.getModuleManager().get(Teams.class).isToggled()) {
                Teams teamsModule = Client.INSTANCE.getModuleManager().get(Teams.class);
                if (teamsModule.isTeammate(target)) {
                    return false;
                }
            }

            return target != mc.player && mc.player.isEntityAlive() && mc.player.getDistanceToEntity(target) <= range.getValue() && mc.player.ticksExisted > 100;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return false; // Handle the exception, return false, or log the error as needed
        }
    }

    public EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target1 = null;
        for (Object object : mc.world.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) object;
                if (canAttack(player)) {
                    double currentDist = mc.player.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target1 = player;
                    }
                }
            }
        }
        return target1;
    }

    public static float wrapAngleTo180_float(float value)
    {
        return MathHelper.wrapAngleTo180_float(value);
    }

    private int getDistanceFromMouse(EntityLivingBase entity) {
        float[] neededRotations = this.getRotationsNeeded(entity);
        if (neededRotations != null) {
            float distanceFromMouse = MathHelper.sqrt_float(mc.player.rotationYaw - neededRotations[0] * mc.player.rotationYaw - neededRotations[0] + mc.player.rotationPitch - neededRotations[1] * mc.player.rotationPitch - neededRotations[1]);
            return (int) distanceFromMouse;
        }
        return -1;
    }

    private float[] getRotationsNeeded(EntityLivingBase entity) {
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - mc.player.posX;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = entity;
            diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9 - (mc.player.posY + mc.player.getEyeHeight());
        } else {
            diffY = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (mc.player.posY + mc.player.getEyeHeight());
        }
        double diffZ = entity.posZ - mc.player.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { mc.player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.player.rotationPitch) };
    }

    private void faceEntity(EntityLivingBase entity, double speed) {
        //speed += Math.random() * 4 - 3;
        //speed = Math.round(speed * 5000) / 5000.0D;

        float[] rotations = this.getRotationsNeeded(entity);
        if (rotations != null) {
            mc.player.rotationYaw = (float) this.limitAngleChange(mc.player.prevRotationYaw, rotations[0], speed);
        }
    }

    private double limitAngleChange(double current, double intended, double speed) {
        double change = intended - current;
        if (change > speed) {
            change = speed;
        } else if (change < -speed) {
            change = -speed;
        }
        return current + change;
    }

}
