package com.alan.clients.script.api.wrapper.impl;

import com.alan.clients.script.api.wrapper.ScriptWrapper;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector2f;
import com.alan.clients.script.api.wrapper.impl.vector.ScriptVector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ScriptEntity extends ScriptWrapper<Entity> {

    public ScriptEntity(final Entity wrapped) {
        super(wrapped);
    }

    static ScriptEntity getById(final int id) {
        final Entity entity = MC.theWorld.getEntityByID(id);
        return tryInstantiate(entity);
    }

    static ScriptEntity getByName(final String name) {
        final Entity entity = MC.theWorld.getPlayerEntityByName(name);
        return tryInstantiate(entity);
    }

    private static ScriptEntity tryInstantiate(final Entity entity) {
        // yes yes this method was totally necessary
        return new ScriptEntity(entity);
    }

    public boolean isLiving() {
        return this.wrapped instanceof EntityLivingBase;
    }

    public void setYaw(float yaw) {
        this.wrapped.rotationYaw = yaw;
    }

    public void setPitch(float pitch) {
        this.wrapped.rotationPitch = pitch;
    }

    public ScriptVector3d getPosition() {
        return new ScriptVector3d(this.wrapped.posX, this.wrapped.posY, this.wrapped.posZ);
    }

    public ScriptVector3d getLastPosition() {
        return new ScriptVector3d(this.wrapped.lastTickPosX, this.wrapped.lastTickPosY, this.wrapped.lastTickPosZ);
    }

    public ScriptVector3d getMotion() {
        return new ScriptVector3d(this.wrapped.motionX, this.wrapped.motionY, this.wrapped.motionZ);
    }

    public void setMotion(ScriptVector3d vector3) {
        this.wrapped.motionX = vector3.getX();
        this.wrapped.motionY = vector3.getY();
        this.wrapped.motionZ = vector3.getZ();
    }

    public void setMotionX(double motionX) {
        this.wrapped.motionX = motionX;
    }

    public void setMotionY(double motionY) {
        this.wrapped.motionY = motionY;
    }

    public void setMotionZ(double motionZ) {
        this.wrapped.motionZ = motionZ;
    }

    public int getAirTicks() {
        return this.wrapped.offGroundTicks;
    }

    public int getGroundTicks() {
        return this.wrapped.onGroundTicks;
    }

    public boolean isSprinting() {
        return this.wrapped.isSprinting();
    }

    public boolean isSneaking() {
        return this.wrapped.isSneaking();
    }

    public boolean isInvisible() {
        return this.wrapped.isInvisible();
    }

    public boolean isDead() {
        return this.wrapped.isDead;
    }

    public boolean isRiding() {
        return this.wrapped.isRiding();
    }

    public boolean isEating() {
        return this.wrapped.isEating();
    }

    public boolean isBurning() {
        return this.wrapped.isBurning();
    }

    public ScriptVector2f getRotation() {
        return new ScriptVector2f(this.wrapped.rotationYaw, this.wrapped.rotationPitch);
    }

    public ScriptVector2f getLastRotation() {
        return new ScriptVector2f(this.wrapped.prevRotationYaw, this.wrapped.prevRotationPitch);
    }

    public int getTicksExisted() {
        return this.wrapped.ticksExisted;
    }

    public int getEntityId() {
        return this.wrapped.getEntityId();
    }

    public String getDisplayName() {
        return this.wrapped.getDisplayName().getUnformattedTextForChat();
    }

    public float getDistanceToEntity(final ScriptEntity entity) {
        final float f = (float) (this.wrapped.posX - entity.getPosition().getX());
        final float f1 = (float) (this.wrapped.posY - entity.getPosition().getY());
        final float f2 = (float) (this.wrapped.posZ - entity.getPosition().getZ());
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
    }

    public double getDistance(final double x, final double y, final double z) {
        final double d0 = this.wrapped.posX - x;
        final double d1 = this.wrapped.posY - y;
        final double d2 = this.wrapped.posZ - z;
        return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
    }
}