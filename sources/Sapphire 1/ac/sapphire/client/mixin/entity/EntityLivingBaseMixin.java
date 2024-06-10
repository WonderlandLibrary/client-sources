package ac.sapphire.client.mixin.entity;

import ac.sapphire.client.Sapphire;
import ac.sapphire.client.module.impl.movement.QuickAccelModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseMixin extends Entity {
    @Shadow
    public float moveForward;
    @Shadow
    public float moveStrafing;
    @Shadow
    protected float randomYawVelocity;
    @Shadow
    protected int newPosRotationIncrements;
    @Shadow
    protected double newPosX;
    @Shadow
    protected double newPosY;
    @Shadow
    protected double newPosZ;
    @Shadow
    protected double newRotationYaw;
    @Shadow
    protected double newRotationPitch;
    @Shadow
    protected boolean isJumping;
    @Shadow
    private int jumpTicks;

    public EntityLivingBaseMixin(World worldIn) {
        super(worldIn);
    }

    @Shadow
    public abstract void moveEntityWithHeading(float strafe, float forward);

    @Shadow
    protected abstract void collideWithNearbyEntities();

    @Shadow
    protected abstract void jump();

    @Shadow
    public abstract boolean isServerWorld();

    @Shadow
    protected abstract boolean isMovementBlocked();

    @Shadow
    protected abstract void updateEntityActionState();

    @Shadow
    protected abstract void updateAITick();

    @Shadow
    protected abstract void handleJumpLava();

    /**
     * @author
     */
    @Overwrite
    public void onLivingUpdate() {
        if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }

        if (this.newPosRotationIncrements > 0) {
            double d0 = this.posX + (this.newPosX - this.posX) / (double) this.newPosRotationIncrements;
            double d1 = this.posY + (this.newPosY - this.posY) / (double) this.newPosRotationIncrements;
            double d2 = this.posZ + (this.newPosZ - this.posZ) / (double) this.newPosRotationIncrements;
            double d3 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.newPosRotationIncrements);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.newRotationPitch - (double) this.rotationPitch) / (double) this.newPosRotationIncrements);
            --this.newPosRotationIncrements;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else if (!this.isServerWorld()) {
            this.motionX *= 0.98D;
            this.motionY *= 0.98D;
            this.motionZ *= 0.98D;
        }

        if (Math.abs(this.motionX) < 0.005D) {
            this.motionX = 0.0D;
        }

        if (Math.abs(this.motionY) < 0.005D) {
            this.motionY = 0.0D;
        }

        if (Math.abs(this.motionZ) < 0.005D) {
            this.motionZ = 0.0D;
        }

        this.worldObj.theProfiler.startSection("ai");

        if (this.isMovementBlocked()) {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        } else if (this.isServerWorld()) {
            this.worldObj.theProfiler.startSection("newAi");
            this.updateEntityActionState();
            this.worldObj.theProfiler.endSection();
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("jump");

        if (this.isJumping) {
            if (this.isInWater()) {
                this.updateAITick();
            } else if (this.isInLava()) {
                this.handleJumpLava();
            } else if (this.onGround && this.jumpTicks == 0) {
                this.jump();
                this.jumpTicks = 10;
            }
        } else {
            this.jumpTicks = 0;
        }

        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("travel");
        if(!Sapphire.INSTANCE.getModuleManager().get(QuickAccelModule.class).isToggled()) {
            this.moveStrafing *= 0.98F;
            this.moveForward *= 0.98F;
        }
        this.randomYawVelocity *= 0.9F;
        this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("push");

        if (!this.worldObj.isRemote) {
            this.collideWithNearbyEntities();
        }

        this.worldObj.theProfiler.endSection();
    }
}
