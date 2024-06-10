package ac.sapphire.client.mixin.entity;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    private int fire;

    @Shadow
    private int nextStepDistance;

    @Shadow public abstract void setFire(int seconds);

    @Shadow public int fireResistance;

    @Shadow public World worldObj;

    @Shadow public boolean noClip;

    @Shadow public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow public abstract AxisAlignedBB getEntityBoundingBox();

    @Shadow public double posX;

    @Shadow public double posY;

    @Shadow public double posZ;

    @Shadow protected boolean isInWeb;

    @Shadow public double motionX;

    @Shadow public double motionY;

    @Shadow public double motionZ;

    @Shadow public boolean onGround;

    @Shadow public float stepHeight;

    @Shadow public boolean isCollidedHorizontally;

    @Shadow public boolean isCollidedVertically;

    @Shadow public boolean isCollided;

    @Shadow protected abstract void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos);

    @Shadow protected abstract boolean canTriggerWalking();

    @Shadow public Entity ridingEntity;

    @Shadow public float distanceWalkedModified;

    @Shadow public float distanceWalkedOnStepModified;

    @Shadow public abstract boolean isInWater();

    @Shadow protected abstract String getSwimSound();

    @Shadow protected Random rand;

    @Shadow protected abstract void playStepSound(BlockPos pos, Block blockIn);

    @Shadow protected abstract void doBlockCollisions();

    @Shadow public abstract void addEntityCrashInfo(CrashReportCategory category);

    @Shadow public abstract boolean isWet();

    @Shadow protected abstract void dealFireDamage(int amount);

    public int getFire() {
        return fire;
    }

    public float getNextStepDistance() {
        return nextStepDistance;
    }

    public void setNextStepDistance(int s) {
        nextStepDistance = s;
    }

    public void incFire() {
        fire++;
    }

    @Shadow
    public abstract void moveEntity(double x, double y, double z);
}
