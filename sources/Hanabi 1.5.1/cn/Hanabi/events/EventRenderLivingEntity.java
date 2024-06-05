package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.*;
import net.minecraft.entity.*;

public class EventRenderLivingEntity implements Event, Cancellable
{
    private EntityLivingBase entity;
    private boolean pre;
    private float limbSwing;
    private float limbSwingAmount;
    private float ageInTicks;
    private float rotationYawHead;
    private float rotationPitch;
    private float chestRot;
    private float offset;
    public boolean cancelled;
    
    public EventRenderLivingEntity(final EntityLivingBase entity, final boolean pre, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float rotationYawHead, final float rotationPitch, final float chestRot, final float offset) {
        this.entity = entity;
        this.pre = pre;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.rotationYawHead = rotationYawHead;
        this.rotationPitch = rotationPitch;
        this.chestRot = chestRot;
        this.offset = offset;
        this.cancelled = false;
    }
    
    public EventRenderLivingEntity(final EntityLivingBase entity, final boolean pre) {
        this.entity = entity;
        this.pre = pre;
    }
    
    public EntityLivingBase getEntity() {
        return this.entity;
    }
    
    public boolean isPre() {
        return this.pre;
    }
    
    public boolean isPost() {
        return !this.pre;
    }
    
    public float getLimbSwing() {
        return this.limbSwing;
    }
    
    public void setLimbSwing(final float limbSwing) {
        this.limbSwing = limbSwing;
    }
    
    public float getLimbSwingAmount() {
        return this.limbSwingAmount;
    }
    
    public void setLimbSwingAmount(final float limbSwingAmount) {
        this.limbSwingAmount = limbSwingAmount;
    }
    
    public float getAgeInTicks() {
        return this.ageInTicks;
    }
    
    public void setAgeInTicks(final float ageInTicks) {
        this.ageInTicks = ageInTicks;
    }
    
    public float getRotationYawHead() {
        return this.rotationYawHead;
    }
    
    public void setRotationYawHead(final float rotationYawHead) {
        this.rotationYawHead = rotationYawHead;
    }
    
    public float getRotationPitch() {
        return this.rotationPitch;
    }
    
    public void setRotationPitch(final float rotationPitch) {
        this.rotationPitch = rotationPitch;
    }
    
    public float getOffset() {
        return this.offset;
    }
    
    public void setOffset(final float offset) {
        this.offset = offset;
    }
    
    public float getRotationChest() {
        return this.chestRot;
    }
    
    public void setRotationChest(final float rotationChest) {
        this.chestRot = rotationChest;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean state) {
        this.cancelled = state;
    }
}
