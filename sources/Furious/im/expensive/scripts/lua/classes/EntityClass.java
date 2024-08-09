package im.expensive.scripts.lua.classes;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityClass {

    private final Entity entity;

    public EntityClass(Entity entity) {
        this.entity = entity;
    }

    public boolean isOnGround() {
        return this.entity.isOnGround();
    }

    public VectorClass getPosition() {
        return new VectorClass(entity.getPositionVec());
    }

    public int getHurtTime() {
        if (entity instanceof LivingEntity e)
            return e.hurtTime;
        return 0;
    }

    public double getHealth() {
        if (entity instanceof LivingEntity e)
            return e.getHealth();
        return 0;
    }

    public double getMaxHealth() {
        if (entity instanceof LivingEntity e)
            return e.getMaxHealth();
        return 0;
    }

    public int getEntityID() {
        return entity.getEntityId();
    }

    public void jump() {
        if (entity instanceof ClientPlayerEntity e)
            e.jump();
    }

    public boolean isInWater() {
        return entity.isInWater();
    }

    public boolean isAlive() {
        return entity.isAlive();
    }

    public double getDistance(EntityClass e) {
        return entity.getDistance(e.entity);
    }

    public double getYaw() {
        return entity.rotationYaw;
    }

    public double getPitch() {
        return entity.rotationPitch;
    }

    public boolean isSneaking() {
        if (entity instanceof PlayerEntity e)
            return e.isCrouching();
        return false;
    }

}
