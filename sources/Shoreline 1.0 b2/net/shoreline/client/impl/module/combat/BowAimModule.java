package net.shoreline.client.impl.module.combat;

import net.shoreline.client.api.config.Config;
import net.shoreline.client.api.config.setting.BooleanConfig;
import net.shoreline.client.api.event.EventStage;
import net.shoreline.client.api.event.listener.EventListener;
import net.shoreline.client.api.module.ModuleCategory;
import net.shoreline.client.api.module.ToggleModule;
import net.shoreline.client.impl.event.network.PlayerUpdateEvent;
import net.shoreline.client.init.Managers;
import net.shoreline.client.init.Modules;
import net.shoreline.client.util.world.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.math.Vec3d;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
public class BowAimModule extends ToggleModule
{
    //
    Config<Boolean> playersConfig = new BooleanConfig("Players",
            "Aims bow at players", true);
    Config<Boolean> monstersConfig = new BooleanConfig("Monsters",
            "Aims bow at monsters", false);
    Config<Boolean> neutralsConfig = new BooleanConfig("Neutrals",
            "Aims bow at neutrals", false);
    Config<Boolean> animalsConfig = new BooleanConfig("Animals",
            "Aims bow at animals", false);
    Config<Boolean> invisiblesConfig = new BooleanConfig("Invisibles",
            "Aims bow at invisible entities", false);

    /**
     *
     */
    public BowAimModule()
    {
        super("BowAim", "Automatically aims charged bow at nearby entities",
                ModuleCategory.COMBAT);
    }

    /**
     *
     * @param event
     */
    @EventListener
    public void onPlayerUpdate(PlayerUpdateEvent event)
    {
        if (event.getStage() != EventStage.PRE)
        {
            return;
        }
        if (mc.player.getMainHandStack().getItem() instanceof BowItem
                && mc.player.getItemUseTime() >= 3)
        {
            double minDist = Double.MAX_VALUE;
            Entity aimTarget = null;
            for (Entity entity : mc.world.getEntities())
            {
                if (entity == null || entity == mc.player || !entity.isAlive()
                        || !isValidAimTarget(entity)
                        || Managers.SOCIAL.isFriend(entity.getUuid())
                        || entity instanceof PlayerEntity player && Modules.ANTI_BOTS.contains(player))
                {
                    continue;
                }
                double dist = mc.player.distanceTo(entity);
                if (dist < minDist)
                {
                    minDist = dist;
                    aimTarget = entity;
                }
            }
            if (aimTarget instanceof LivingEntity target)
            {
                float[] rots = getBowRotationsTo(target);
                Managers.ROTATION.setRotationClient(rots[0], rots[1]);
            }
        }
    }

    /**
     *
     * @param target
     * @return
     */
    private float[] getBowRotationsTo(LivingEntity target)
    {
        float velocity = (72000.0f - mc.player.getItemUseTimeLeft()) / 20.0f;
        velocity = Math.min(1.0f, (velocity * velocity + velocity * 2) / 3.0f);
        Vec3d newTargetVec = target.getPos().add(target.getVelocity());
        double d = mc.player.getEyePos().distanceTo(target.getBoundingBox().offset(target.getVelocity()).getCenter());
        double x = newTargetVec.x + (newTargetVec.x - target.getX()) * d - mc.player.getX();
        double y = newTargetVec.y + (newTargetVec.y - target.getY()) * d + target.getHeight() * 0.5 - mc.player.getY() - mc.player.getEyeHeight(mc.player.getPose());
        double z = newTargetVec.z + (newTargetVec.z - target.getZ()) * d - mc.player.getZ();
        double hDistance = Math.sqrt(x * x + z * z);
        double hDistanceSq = hDistance * hDistance;
        float g = 0.006f;
        float velocitySq = velocity * velocity;
        float velocityPow4 = velocitySq * velocitySq;
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float) -Math.toDegrees(Math.atan((velocitySq - Math.sqrt(velocityPow4 - g * (g * hDistanceSq + 2.0f * y * velocitySq))) / (g * hDistance)));
        return new float[]
                {
                       yaw , pitch
                };
    }

    /**
     *
     * @param entity
     * @return
     */
    private boolean isValidAimTarget(Entity entity)
    {
        if (entity.isInvisible() && !invisiblesConfig.getValue())
        {
            return false;
        }
        return entity instanceof PlayerEntity && playersConfig.getValue()
                || EntityUtil.isMonster(entity) && monstersConfig.getValue()
                || EntityUtil.isNeutral(entity) && neutralsConfig.getValue()
                || EntityUtil.isPassive(entity) && animalsConfig.getValue();
    }
}
