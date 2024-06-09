package com.client.glowclient.sponge.mixin;

import net.minecraft.command.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.network.datasync.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import com.client.glowclient.sponge.mixinutils.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements ICommandSender, ICapabilitySerializable<NBTTagCompound>
{
    @Shadow
    public boolean field_70156_m;
    @Shadow
    protected int field_184245_j;
    @Shadow
    private Entity field_184239_as;
    @Shadow
    public boolean field_98038_p;
    @Shadow
    public double field_70169_q;
    @Shadow
    public double field_70167_r;
    @Shadow
    public double field_70166_s;
    @Shadow
    public float field_70177_z;
    @Shadow
    public float field_70125_A;
    @Shadow
    public float field_70126_B;
    @Shadow
    public float field_70127_C;
    @Shadow
    public boolean field_70122_E;
    @Shadow
    public boolean field_70123_F;
    @Shadow
    public boolean field_70124_G;
    @Shadow
    public boolean field_70132_H;
    @Shadow
    public boolean field_70133_I;
    @Shadow
    protected boolean field_70134_J;
    @Shadow
    private boolean field_174835_g;
    @Shadow
    public boolean field_70128_L;
    @Shadow
    public float field_70130_N;
    @Shadow
    public float field_70131_O;
    @Shadow
    public float field_70141_P;
    @Shadow
    public float field_70140_Q;
    @Shadow
    public float field_82151_R;
    @Shadow
    public float field_70143_R;
    @Shadow
    private int field_70150_b;
    @Shadow
    private float field_191959_ay;
    @Shadow
    public double field_70142_S;
    @Shadow
    public double field_70137_T;
    @Shadow
    public double field_70136_U;
    @Shadow
    public float field_70138_W;
    @Shadow
    protected Random field_70146_Z;
    @Shadow
    public int field_70173_aa;
    @Shadow
    private int field_190534_ay;
    @Shadow
    protected boolean field_70171_ac;
    @Shadow
    public boolean field_70158_ak;
    @Shadow
    public boolean field_70160_al;
    @Shadow
    public int field_71088_bW;
    @Shadow
    protected boolean field_71087_bX;
    @Shadow
    protected int field_82153_h;
    @Shadow
    public int field_71093_bK;
    @Shadow
    protected BlockPos field_181016_an;
    @Shadow
    protected Vec3d field_181017_ao;
    @Shadow
    protected EnumFacing field_181018_ap;
    @Shadow
    private boolean field_83001_bt;
    @Shadow
    protected UUID field_96093_i;
    @Shadow
    protected String field_189513_ar;
    @Shadow
    protected boolean field_184238_ar;
    @Shadow
    private double[] field_191505_aI;
    @Shadow
    private long field_191506_aJ;
    @Shadow
    private AxisAlignedBB field_70121_D;
    @Shadow
    public World field_70170_p;
    @Shadow
    public double field_70165_t;
    @Shadow
    public double field_70163_u;
    @Shadow
    public double field_70161_v;
    @Shadow
    public double field_70159_w;
    @Shadow
    public double field_70181_x;
    @Shadow
    public double field_70179_y;
    @Shadow
    private int field_145783_c;
    @Shadow
    protected boolean field_70178_ae;
    @Shadow
    public float field_70144_Y;
    @Shadow
    private List<Entity> field_184244_h;
    @Shadow
    public boolean field_70145_X;
    @Shadow
    protected EntityDataManager field_70180_af;
    
    public MixinEntity() {
        super();
    }
    
    @Shadow
    protected abstract void setFlag(final int p0, final boolean p1);
    
    @Shadow
    protected abstract void doBlockCollisions();
    
    @Shadow
    public abstract void resetPositionToBB();
    
    @Shadow
    public abstract void setEntityBoundingBox(final AxisAlignedBB p0);
    
    @Shadow
    public abstract AxisAlignedBB getEntityBoundingBox();
    
    @Shadow
    public abstract boolean isSneaking();
    
    @Shadow
    protected abstract void updateFallState(final double p0, final boolean p1, final IBlockState p2, final BlockPos p3);
    
    @Shadow
    protected abstract boolean canTriggerWalking();
    
    @Shadow
    public abstract boolean isBeingRidden();
    
    @Shadow
    public abstract boolean isInWater();
    
    @Shadow
    @Nullable
    public abstract Entity getControllingPassenger();
    
    @Shadow
    public abstract void playSound(final SoundEvent p0, final float p1, final float p2);
    
    @Shadow
    protected abstract SoundEvent getSwimSound();
    
    @Shadow
    protected abstract void playStepSound(final BlockPos p0, final Block p1);
    
    @Shadow
    protected abstract float playFlySound(final float p0);
    
    @Shadow
    protected abstract boolean makeFlySound();
    
    @Shadow
    public abstract void addEntityCrashInfo(final CrashReportCategory p0);
    
    @Shadow
    protected abstract void dealFireDamage(final int p0);
    
    @Shadow
    public abstract boolean isWet();
    
    @Shadow
    public abstract void setFire(final int p0);
    
    @Shadow
    protected abstract int getFireImmuneTicks();
    
    @Shadow
    public abstract boolean isBurning();
    
    @Shadow
    public abstract boolean isRiding();
    
    @Inject(method = { "isPushedByWater" }, at = { @At("HEAD") }, cancellable = true)
    public void preIsPushedByWater(final CallbackInfoReturnable callbackInfoReturnable) {
        if (HookTranslator.v16) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
    
    @Inject(method = { "applyEntityCollision" }, at = { @At("HEAD") }, cancellable = true)
    public void preApplyEntityCollision(final CallbackInfo callbackInfo) {
        if (HookTranslator.v15) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "doBlockCollisions" }, at = { @At("HEAD") }, cancellable = true)
    public void preDoBlockCollisions(final CallbackInfo callbackInfo) {
        if (HookTranslator.v18) {
            callbackInfo.cancel();
        }
    }
    
    @Overwrite
    public void move(final MoverType moverType, double calculateXOffset, double n, double calculateZOffset) {
        if (this.noClip) {
            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, n, calculateZOffset));
            this.resetPositionToBB();
        }
        else {
            if (moverType == MoverType.PISTON) {
                final long totalWorldTime = this.world.getTotalWorldTime();
                if (totalWorldTime != this.pistonDeltasGameTime) {
                    Arrays.fill(this.pistonDeltas, 0.0);
                    this.pistonDeltasGameTime = totalWorldTime;
                }
                if (calculateXOffset != 0.0) {
                    final int ordinal = EnumFacing.Axis.X.ordinal();
                    final double clamp = MathHelper.clamp(calculateXOffset + this.pistonDeltas[ordinal], -0.51, 0.51);
                    calculateXOffset = clamp - this.pistonDeltas[ordinal];
                    this.pistonDeltas[ordinal] = clamp;
                    if (Math.abs(calculateXOffset) <= 9.999999747378752E-6) {
                        return;
                    }
                }
                else if (n != 0.0) {
                    final int ordinal2 = EnumFacing.Axis.Y.ordinal();
                    final double clamp2 = MathHelper.clamp(n + this.pistonDeltas[ordinal2], -0.51, 0.51);
                    n = clamp2 - this.pistonDeltas[ordinal2];
                    this.pistonDeltas[ordinal2] = clamp2;
                    if (Math.abs(n) <= 9.999999747378752E-6) {
                        return;
                    }
                }
                else {
                    if (calculateZOffset == 0.0) {
                        return;
                    }
                    final int ordinal3 = EnumFacing.Axis.Z.ordinal();
                    final double clamp3 = MathHelper.clamp(calculateZOffset + this.pistonDeltas[ordinal3], -0.51, 0.51);
                    calculateZOffset = clamp3 - this.pistonDeltas[ordinal3];
                    this.pistonDeltas[ordinal3] = clamp3;
                    if (Math.abs(calculateZOffset) <= 9.999999747378752E-6) {
                        return;
                    }
                }
            }
            this.world.profiler.startSection("move");
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            if (this.isInWeb) {
                this.isInWeb = false;
                calculateXOffset *= 0.25;
                n *= 0.05000000074505806;
                calculateZOffset *= 0.25;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
            double n2 = calculateXOffset;
            final double n3 = n;
            double n4 = calculateZOffset;
            if ((moverType == MoverType.SELF || moverType == MoverType.PLAYER) && this.onGround && (this.isSneaking() || HookTranslator.v19) && (Entity.class.cast(this) instanceof EntityPlayer || Entity.class.cast(this) instanceof AbstractHorse || Entity.class.cast(this) instanceof EntityPig)) {
                while (calculateXOffset != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(calculateXOffset, (double)(-this.stepHeight), 0.0)).isEmpty()) {
                    if (calculateXOffset < 0.05 && calculateXOffset >= -0.05) {
                        calculateXOffset = 0.0;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= 0.05;
                    }
                    else {
                        calculateXOffset += 0.05;
                    }
                    n2 = calculateXOffset;
                }
                while (calculateZOffset != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(0.0, (double)(-this.stepHeight), calculateZOffset)).isEmpty()) {
                    if (calculateZOffset < 0.05 && calculateZOffset >= -0.05) {
                        calculateZOffset = 0.0;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= 0.05;
                    }
                    else {
                        calculateZOffset += 0.05;
                    }
                    n4 = calculateZOffset;
                }
                while (calculateXOffset != 0.0 && calculateZOffset != 0.0 && this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().offset(calculateXOffset, (double)(-this.stepHeight), calculateZOffset)).isEmpty()) {
                    if (calculateXOffset < 0.05 && calculateXOffset >= -0.05) {
                        calculateXOffset = 0.0;
                    }
                    else if (calculateXOffset > 0.0) {
                        calculateXOffset -= 0.05;
                    }
                    else {
                        calculateXOffset += 0.05;
                    }
                    n2 = calculateXOffset;
                    if (calculateZOffset < 0.05 && calculateZOffset >= -0.05) {
                        calculateZOffset = 0.0;
                    }
                    else if (calculateZOffset > 0.0) {
                        calculateZOffset -= 0.05;
                    }
                    else {
                        calculateZOffset += 0.05;
                    }
                    n4 = calculateZOffset;
                }
            }
            final List collisionBoxes = this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().expand(calculateXOffset, n, calculateZOffset));
            final AxisAlignedBB entityBoundingBox = this.getEntityBoundingBox();
            if (n != 0.0) {
                for (int i = 0; i < collisionBoxes.size(); ++i) {
                    n = collisionBoxes.get(i).calculateYOffset(this.getEntityBoundingBox(), n);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
            }
            if (calculateXOffset != 0.0) {
                for (int j = 0; j < collisionBoxes.size(); ++j) {
                    calculateXOffset = collisionBoxes.get(j).calculateXOffset(this.getEntityBoundingBox(), calculateXOffset);
                }
                if (calculateXOffset != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(calculateXOffset, 0.0, 0.0));
                }
            }
            if (calculateZOffset != 0.0) {
                for (int k = 0; k < collisionBoxes.size(); ++k) {
                    calculateZOffset = collisionBoxes.get(k).calculateZOffset(this.getEntityBoundingBox(), calculateZOffset);
                }
                if (calculateZOffset != 0.0) {
                    this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, calculateZOffset));
                }
            }
            final boolean b = this.onGround || (n3 != n && n3 < 0.0);
            if (this.stepHeight > 0.0f && b && (n2 != calculateXOffset || n4 != calculateZOffset)) {
                final double n5 = calculateXOffset;
                final double n6 = n;
                final double n7 = calculateZOffset;
                final AxisAlignedBB entityBoundingBox2 = this.getEntityBoundingBox();
                this.setEntityBoundingBox(entityBoundingBox);
                n = this.stepHeight;
                final List collisionBoxes2 = this.world.getCollisionBoxes((Entity)Entity.class.cast(this), this.getEntityBoundingBox().expand(n2, n, n4));
                final AxisAlignedBB entityBoundingBox3 = this.getEntityBoundingBox();
                final AxisAlignedBB expand = entityBoundingBox3.expand(n2, 0.0, n4);
                double calculateYOffset = n;
                for (int l = 0; l < collisionBoxes2.size(); ++l) {
                    calculateYOffset = collisionBoxes2.get(l).calculateYOffset(expand, calculateYOffset);
                }
                final AxisAlignedBB offset = entityBoundingBox3.offset(0.0, calculateYOffset, 0.0);
                double calculateXOffset2 = n2;
                for (int n8 = 0; n8 < collisionBoxes2.size(); ++n8) {
                    calculateXOffset2 = collisionBoxes2.get(n8).calculateXOffset(offset, calculateXOffset2);
                }
                final AxisAlignedBB offset2 = offset.offset(calculateXOffset2, 0.0, 0.0);
                double calculateZOffset2 = n4;
                for (int n9 = 0; n9 < collisionBoxes2.size(); ++n9) {
                    calculateZOffset2 = collisionBoxes2.get(n9).calculateZOffset(offset2, calculateZOffset2);
                }
                final AxisAlignedBB offset3 = offset2.offset(0.0, 0.0, calculateZOffset2);
                final AxisAlignedBB entityBoundingBox4 = this.getEntityBoundingBox();
                double calculateYOffset2 = n;
                for (int n10 = 0; n10 < collisionBoxes2.size(); ++n10) {
                    calculateYOffset2 = collisionBoxes2.get(n10).calculateYOffset(entityBoundingBox4, calculateYOffset2);
                }
                final AxisAlignedBB offset4 = entityBoundingBox4.offset(0.0, calculateYOffset2, 0.0);
                double calculateXOffset3 = n2;
                for (int n11 = 0; n11 < collisionBoxes2.size(); ++n11) {
                    calculateXOffset3 = collisionBoxes2.get(n11).calculateXOffset(offset4, calculateXOffset3);
                }
                final AxisAlignedBB offset5 = offset4.offset(calculateXOffset3, 0.0, 0.0);
                double calculateZOffset3 = n4;
                for (int n12 = 0; n12 < collisionBoxes2.size(); ++n12) {
                    calculateZOffset3 = collisionBoxes2.get(n12).calculateZOffset(offset5, calculateZOffset3);
                }
                final AxisAlignedBB offset6 = offset5.offset(0.0, 0.0, calculateZOffset3);
                if (calculateXOffset2 * calculateXOffset2 + calculateZOffset2 * calculateZOffset2 > calculateXOffset3 * calculateXOffset3 + calculateZOffset3 * calculateZOffset3) {
                    calculateXOffset = calculateXOffset2;
                    calculateZOffset = calculateZOffset2;
                    n = -calculateYOffset;
                    this.setEntityBoundingBox(offset3);
                }
                else {
                    calculateXOffset = calculateXOffset3;
                    calculateZOffset = calculateZOffset3;
                    n = -calculateYOffset2;
                    this.setEntityBoundingBox(offset6);
                }
                for (int n13 = 0; n13 < collisionBoxes2.size(); ++n13) {
                    n = collisionBoxes2.get(n13).calculateYOffset(this.getEntityBoundingBox(), n);
                }
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, n, 0.0));
                if (n5 * n5 + n7 * n7 >= calculateXOffset * calculateXOffset + calculateZOffset * calculateZOffset) {
                    calculateXOffset = n5;
                    n = n6;
                    calculateZOffset = n7;
                    this.setEntityBoundingBox(entityBoundingBox2);
                }
            }
            this.world.profiler.endSection();
            this.world.profiler.startSection("rest");
            this.resetPositionToBB();
            this.collidedHorizontally = (n2 != calculateXOffset || n4 != calculateZOffset);
            this.collidedVertically = (n3 != n);
            this.onGround = (this.collidedVertically && n3 < 0.0);
            this.collided = (this.collidedHorizontally || this.collidedVertically);
            BlockPos blockPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY - 0.20000000298023224), MathHelper.floor(this.posZ));
            IBlockState blockState = this.world.getBlockState(blockPos);
            if (blockState.getMaterial() == Material.AIR) {
                final BlockPos down = blockPos.down();
                final IBlockState blockState2 = this.world.getBlockState(down);
                final Block block = blockState2.getBlock();
                if (block instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate) {
                    blockState = blockState2;
                    blockPos = down;
                }
            }
            this.updateFallState(n, this.onGround, blockState, blockPos);
            if (n2 != calculateXOffset) {
                this.motionX = 0.0;
            }
            if (n4 != calculateZOffset) {
                this.motionZ = 0.0;
            }
            final Block block2 = blockState.getBlock();
            if (n3 != n) {
                block2.onLanded(this.world, (Entity)Entity.class.cast(this));
            }
            if (this.canTriggerWalking() && (!this.onGround || !this.isSneaking() || !(Entity.class.cast(this) instanceof EntityPlayer)) && !this.isRiding()) {
                final double n14 = this.posX - posX;
                double n15 = this.posY - posY;
                final double n16 = this.posZ - posZ;
                if (block2 != Blocks.LADDER) {
                    n15 = 0.0;
                }
                if (block2 != null && this.onGround) {
                    block2.onEntityWalk(this.world, blockPos, (Entity)Entity.class.cast(this));
                }
                this.distanceWalkedModified += (float)(MathHelper.sqrt(n14 * n14 + n16 * n16) * 0.6);
                this.distanceWalkedOnStepModified += (float)(MathHelper.sqrt(n14 * n14 + n15 * n15 + n16 * n16) * 0.6);
                if (this.distanceWalkedOnStepModified > this.nextStepDistance && blockState.getMaterial() != Material.AIR) {
                    this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
                    if (this.isInWater()) {
                        final Entity entity = (this.isBeingRidden() && this.getControllingPassenger() != null) ? this.getControllingPassenger() : Entity.class.cast(this);
                        float n17 = MathHelper.sqrt(entity.motionX * entity.motionX * 0.20000000298023224 + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224) * ((entity == Entity.class.cast(this)) ? 0.35f : 0.4f);
                        if (n17 > 1.0f) {
                            n17 = 1.0f;
                        }
                        this.playSound(this.getSwimSound(), n17, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                    }
                    else {
                        this.playStepSound(blockPos, block2);
                    }
                }
                else if (this.distanceWalkedOnStepModified > this.nextFlap && this.makeFlySound() && blockState.getMaterial() == Material.AIR) {
                    this.nextFlap = this.playFlySound(this.distanceWalkedOnStepModified);
                }
            }
            try {
                this.doBlockCollisions();
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, "Checking entity block collision");
                this.addEntityCrashInfo(crashReport.makeCategory("Entity being checked for collision"));
                throw new ReportedException(crashReport);
            }
            final boolean wet = this.isWet();
            if (this.world.isFlammableWithin(this.getEntityBoundingBox().shrink(0.001))) {
                this.dealFireDamage(1);
                if (!wet) {
                    ++this.fire;
                    if (this.fire == 0) {
                        this.setFire(8);
                    }
                }
            }
            else if (this.fire <= 0) {
                this.fire = -this.getFireImmuneTicks();
            }
            if (wet && this.isBurning()) {
                this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7f, 1.6f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                this.fire = -this.getFireImmuneTicks();
            }
            this.world.profiler.endSection();
        }
    }
}
