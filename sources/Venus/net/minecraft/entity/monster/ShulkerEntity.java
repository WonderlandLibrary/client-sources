/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.BodyController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.ShulkerAABBHelper;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ShulkerEntity
extends GolemEntity
implements IMob {
    private static final UUID COVERED_ARMOR_BONUS_ID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
    private static final AttributeModifier COVERED_ARMOR_BONUS_MODIFIER = new AttributeModifier(COVERED_ARMOR_BONUS_ID, "Covered armor bonus", 20.0, AttributeModifier.Operation.ADDITION);
    protected static final DataParameter<Direction> ATTACHED_FACE = EntityDataManager.createKey(ShulkerEntity.class, DataSerializers.DIRECTION);
    protected static final DataParameter<Optional<BlockPos>> ATTACHED_BLOCK_POS = EntityDataManager.createKey(ShulkerEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    protected static final DataParameter<Byte> PEEK_TICK = EntityDataManager.createKey(ShulkerEntity.class, DataSerializers.BYTE);
    protected static final DataParameter<Byte> COLOR = EntityDataManager.createKey(ShulkerEntity.class, DataSerializers.BYTE);
    private float prevPeekAmount;
    private float peekAmount;
    private BlockPos currentAttachmentPosition = null;
    private int clientSideTeleportInterpolation;

    public ShulkerEntity(EntityType<? extends ShulkerEntity> entityType, World world) {
        super((EntityType<? extends GolemEntity>)entityType, world);
        this.experienceValue = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(7, new PeekGoal(this));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]).setCallsForHelp(new Class[0]));
        this.targetSelector.addGoal(2, new AttackNearestGoal(this, this));
        this.targetSelector.addGoal(3, new DefenseAttackGoal(this));
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHULKER_AMBIENT;
    }

    @Override
    public void playAmbientSound() {
        if (!this.isClosed()) {
            super.playAmbientSound();
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHULKER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isClosed() ? SoundEvents.ENTITY_SHULKER_HURT_CLOSED : SoundEvents.ENTITY_SHULKER_HURT;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ATTACHED_FACE, Direction.DOWN);
        this.dataManager.register(ATTACHED_BLOCK_POS, Optional.empty());
        this.dataManager.register(PEEK_TICK, (byte)0);
        this.dataManager.register(COLOR, (byte)16);
    }

    public static AttributeModifierMap.MutableAttribute func_234300_m_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 30.0);
    }

    @Override
    protected BodyController createBodyController() {
        return new BodyHelperController(this, this);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.dataManager.set(ATTACHED_FACE, Direction.byIndex(compoundNBT.getByte("AttachFace")));
        this.dataManager.set(PEEK_TICK, compoundNBT.getByte("Peek"));
        this.dataManager.set(COLOR, compoundNBT.getByte("Color"));
        if (compoundNBT.contains("APX")) {
            int n = compoundNBT.getInt("APX");
            int n2 = compoundNBT.getInt("APY");
            int n3 = compoundNBT.getInt("APZ");
            this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(new BlockPos(n, n2, n3)));
        } else {
            this.dataManager.set(ATTACHED_BLOCK_POS, Optional.empty());
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putByte("AttachFace", (byte)this.dataManager.get(ATTACHED_FACE).getIndex());
        compoundNBT.putByte("Peek", this.dataManager.get(PEEK_TICK));
        compoundNBT.putByte("Color", this.dataManager.get(COLOR));
        BlockPos blockPos = this.getAttachmentPos();
        if (blockPos != null) {
            compoundNBT.putInt("APX", blockPos.getX());
            compoundNBT.putInt("APY", blockPos.getY());
            compoundNBT.putInt("APZ", blockPos.getZ());
        }
    }

    @Override
    public void tick() {
        float f;
        super.tick();
        BlockPos blockPos = this.dataManager.get(ATTACHED_BLOCK_POS).orElse(null);
        if (blockPos == null && !this.world.isRemote) {
            blockPos = this.getPosition();
            this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockPos));
        }
        if (this.isPassenger()) {
            blockPos = null;
            this.rotationYaw = f = this.getRidingEntity().rotationYaw;
            this.renderYawOffset = f;
            this.prevRenderYawOffset = f;
            this.clientSideTeleportInterpolation = 0;
        } else if (!this.world.isRemote) {
            Direction direction;
            BlockState blockState = this.world.getBlockState(blockPos);
            if (!blockState.isAir()) {
                if (blockState.isIn(Blocks.MOVING_PISTON)) {
                    direction = blockState.get(PistonBlock.FACING);
                    if (this.world.isAirBlock(blockPos.offset(direction))) {
                        blockPos = blockPos.offset(direction);
                        this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockPos));
                    } else {
                        this.tryTeleportToNewPosition();
                    }
                } else if (blockState.isIn(Blocks.PISTON_HEAD)) {
                    direction = blockState.get(PistonHeadBlock.FACING);
                    if (this.world.isAirBlock(blockPos.offset(direction))) {
                        blockPos = blockPos.offset(direction);
                        this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockPos));
                    } else {
                        this.tryTeleportToNewPosition();
                    }
                } else {
                    this.tryTeleportToNewPosition();
                }
            }
            if (!this.func_234298_a_(blockPos, direction = this.getAttachmentFacing())) {
                Direction direction2 = this.func_234299_g_(blockPos);
                if (direction2 != null) {
                    this.dataManager.set(ATTACHED_FACE, direction2);
                } else {
                    this.tryTeleportToNewPosition();
                }
            }
        }
        f = (float)this.getPeekTick() * 0.01f;
        this.prevPeekAmount = this.peekAmount;
        if (this.peekAmount > f) {
            this.peekAmount = MathHelper.clamp(this.peekAmount - 0.05f, f, 1.0f);
        } else if (this.peekAmount < f) {
            this.peekAmount = MathHelper.clamp(this.peekAmount + 0.05f, 0.0f, f);
        }
        if (blockPos != null) {
            List<Entity> list;
            if (this.world.isRemote) {
                if (this.clientSideTeleportInterpolation > 0 && this.currentAttachmentPosition != null) {
                    --this.clientSideTeleportInterpolation;
                } else {
                    this.currentAttachmentPosition = blockPos;
                }
            }
            this.forceSetPosition((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5);
            double d = 0.5 - (double)MathHelper.sin((0.5f + this.peekAmount) * (float)Math.PI) * 0.5;
            double d2 = 0.5 - (double)MathHelper.sin((0.5f + this.prevPeekAmount) * (float)Math.PI) * 0.5;
            Direction direction = this.getAttachmentFacing().getOpposite();
            this.setBoundingBox(new AxisAlignedBB(this.getPosX() - 0.5, this.getPosY(), this.getPosZ() - 0.5, this.getPosX() + 0.5, this.getPosY() + 1.0, this.getPosZ() + 0.5).expand((double)direction.getXOffset() * d, (double)direction.getYOffset() * d, (double)direction.getZOffset() * d));
            double d3 = d - d2;
            if (d3 > 0.0 && !(list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox())).isEmpty()) {
                for (Entity entity2 : list) {
                    if (entity2 instanceof ShulkerEntity || entity2.noClip) continue;
                    entity2.move(MoverType.SHULKER, new Vector3d(d3 * (double)direction.getXOffset(), d3 * (double)direction.getYOffset(), d3 * (double)direction.getZOffset()));
                }
            }
        }
    }

    @Override
    public void move(MoverType moverType, Vector3d vector3d) {
        if (moverType == MoverType.SHULKER_BOX) {
            this.tryTeleportToNewPosition();
        } else {
            super.move(moverType, vector3d);
        }
    }

    @Override
    public void setPosition(double d, double d2, double d3) {
        super.setPosition(d, d2, d3);
        if (this.dataManager != null && this.ticksExisted != 0) {
            Optional<BlockPos> optional = this.dataManager.get(ATTACHED_BLOCK_POS);
            Optional<BlockPos> optional2 = Optional.of(new BlockPos(d, d2, d3));
            if (!optional2.equals(optional)) {
                this.dataManager.set(ATTACHED_BLOCK_POS, optional2);
                this.dataManager.set(PEEK_TICK, (byte)0);
                this.isAirBorne = true;
            }
        }
    }

    @Nullable
    protected Direction func_234299_g_(BlockPos blockPos) {
        for (Direction direction : Direction.values()) {
            if (!this.func_234298_a_(blockPos, direction)) continue;
            return direction;
        }
        return null;
    }

    private boolean func_234298_a_(BlockPos blockPos, Direction direction) {
        return this.world.isDirectionSolid(blockPos.offset(direction), this, direction.getOpposite()) && this.world.hasNoCollisions(this, ShulkerAABBHelper.getOpenedCollisionBox(blockPos, direction.getOpposite()));
    }

    protected boolean tryTeleportToNewPosition() {
        if (!this.isAIDisabled() && this.isAlive()) {
            BlockPos blockPos = this.getPosition();
            for (int i = 0; i < 5; ++i) {
                Direction direction;
                BlockPos blockPos2 = blockPos.add(8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17), 8 - this.rand.nextInt(17));
                if (blockPos2.getY() <= 0 || !this.world.isAirBlock(blockPos2) || !this.world.getWorldBorder().contains(blockPos2) || !this.world.hasNoCollisions(this, new AxisAlignedBB(blockPos2)) || (direction = this.func_234299_g_(blockPos2)) == null) continue;
                this.dataManager.set(ATTACHED_FACE, direction);
                this.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0f, 1.0f);
                this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(blockPos2));
                this.dataManager.set(PEEK_TICK, (byte)0);
                this.setAttackTarget(null);
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.setMotion(Vector3d.ZERO);
        if (!this.isAIDisabled()) {
            this.prevRenderYawOffset = 0.0f;
            this.renderYawOffset = 0.0f;
        }
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> dataParameter) {
        BlockPos blockPos;
        if (ATTACHED_BLOCK_POS.equals(dataParameter) && this.world.isRemote && !this.isPassenger() && (blockPos = this.getAttachmentPos()) != null) {
            if (this.currentAttachmentPosition == null) {
                this.currentAttachmentPosition = blockPos;
            } else {
                this.clientSideTeleportInterpolation = 6;
            }
            this.forceSetPosition((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5);
        }
        super.notifyDataManagerChange(dataParameter);
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        this.newPosRotationIncrements = 0;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        Entity entity2;
        if (this.isClosed() && (entity2 = damageSource.getImmediateSource()) instanceof AbstractArrowEntity) {
            return true;
        }
        if (super.attackEntityFrom(damageSource, f)) {
            if ((double)this.getHealth() < (double)this.getMaxHealth() * 0.5 && this.rand.nextInt(4) == 0) {
                this.tryTeleportToNewPosition();
            }
            return false;
        }
        return true;
    }

    private boolean isClosed() {
        return this.getPeekTick() == 0;
    }

    @Override
    public boolean func_241845_aY() {
        return this.isAlive();
    }

    public Direction getAttachmentFacing() {
        return this.dataManager.get(ATTACHED_FACE);
    }

    @Nullable
    public BlockPos getAttachmentPos() {
        return this.dataManager.get(ATTACHED_BLOCK_POS).orElse(null);
    }

    public void setAttachmentPos(@Nullable BlockPos blockPos) {
        this.dataManager.set(ATTACHED_BLOCK_POS, Optional.ofNullable(blockPos));
    }

    public int getPeekTick() {
        return this.dataManager.get(PEEK_TICK).byteValue();
    }

    public void updateArmorModifier(int n) {
        if (!this.world.isRemote) {
            this.getAttribute(Attributes.ARMOR).removeModifier(COVERED_ARMOR_BONUS_MODIFIER);
            if (n == 0) {
                this.getAttribute(Attributes.ARMOR).applyPersistentModifier(COVERED_ARMOR_BONUS_MODIFIER);
                this.playSound(SoundEvents.ENTITY_SHULKER_CLOSE, 1.0f, 1.0f);
            } else {
                this.playSound(SoundEvents.ENTITY_SHULKER_OPEN, 1.0f, 1.0f);
            }
        }
        this.dataManager.set(PEEK_TICK, (byte)n);
    }

    public float getClientPeekAmount(float f) {
        return MathHelper.lerp(f, this.prevPeekAmount, this.peekAmount);
    }

    public int getClientTeleportInterp() {
        return this.clientSideTeleportInterpolation;
    }

    public BlockPos getOldAttachPos() {
        return this.currentAttachmentPosition;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.5f;
    }

    @Override
    public int getVerticalFaceSpeed() {
        return 1;
    }

    @Override
    public int getHorizontalFaceSpeed() {
        return 1;
    }

    @Override
    public void applyEntityCollision(Entity entity2) {
    }

    @Override
    public float getCollisionBorderSize() {
        return 0.0f;
    }

    public boolean isAttachedToBlock() {
        return this.currentAttachmentPosition != null && this.getAttachmentPos() != null;
    }

    @Nullable
    public DyeColor getColor() {
        Byte by = this.dataManager.get(COLOR);
        return by != 16 && by <= 15 ? DyeColor.byId(by.byteValue()) : null;
    }

    static Random access$000(ShulkerEntity shulkerEntity) {
        return shulkerEntity.rand;
    }

    static Random access$100(ShulkerEntity shulkerEntity) {
        return shulkerEntity.rand;
    }

    static Random access$200(ShulkerEntity shulkerEntity) {
        return shulkerEntity.rand;
    }

    static Random access$300(ShulkerEntity shulkerEntity) {
        return shulkerEntity.rand;
    }

    static Random access$400(ShulkerEntity shulkerEntity) {
        return shulkerEntity.rand;
    }

    class AttackGoal
    extends Goal {
        private int attackTime;
        final ShulkerEntity this$0;

        public AttackGoal(ShulkerEntity shulkerEntity) {
            this.this$0 = shulkerEntity;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity livingEntity = this.this$0.getAttackTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                return this.this$0.world.getDifficulty() != Difficulty.PEACEFUL;
            }
            return true;
        }

        @Override
        public void startExecuting() {
            this.attackTime = 20;
            this.this$0.updateArmorModifier(100);
        }

        @Override
        public void resetTask() {
            this.this$0.updateArmorModifier(0);
        }

        @Override
        public void tick() {
            if (this.this$0.world.getDifficulty() != Difficulty.PEACEFUL) {
                --this.attackTime;
                LivingEntity livingEntity = this.this$0.getAttackTarget();
                this.this$0.getLookController().setLookPositionWithEntity(livingEntity, 180.0f, 180.0f);
                double d = this.this$0.getDistanceSq(livingEntity);
                if (d < 400.0) {
                    if (this.attackTime <= 0) {
                        this.attackTime = 20 + ShulkerEntity.access$000(this.this$0).nextInt(10) * 20 / 2;
                        this.this$0.world.addEntity(new ShulkerBulletEntity(this.this$0.world, this.this$0, livingEntity, this.this$0.getAttachmentFacing().getAxis()));
                        this.this$0.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0f, (ShulkerEntity.access$100(this.this$0).nextFloat() - ShulkerEntity.access$200(this.this$0).nextFloat()) * 0.2f + 1.0f);
                    }
                } else {
                    this.this$0.setAttackTarget(null);
                }
                super.tick();
            }
        }
    }

    class PeekGoal
    extends Goal {
        private int peekTime;
        final ShulkerEntity this$0;

        private PeekGoal(ShulkerEntity shulkerEntity) {
            this.this$0 = shulkerEntity;
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.getAttackTarget() == null && ShulkerEntity.access$300(this.this$0).nextInt(40) == 0;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return this.this$0.getAttackTarget() == null && this.peekTime > 0;
        }

        @Override
        public void startExecuting() {
            this.peekTime = 20 * (1 + ShulkerEntity.access$400(this.this$0).nextInt(3));
            this.this$0.updateArmorModifier(30);
        }

        @Override
        public void resetTask() {
            if (this.this$0.getAttackTarget() == null) {
                this.this$0.updateArmorModifier(0);
            }
        }

        @Override
        public void tick() {
            --this.peekTime;
        }
    }

    class AttackNearestGoal
    extends NearestAttackableTargetGoal<PlayerEntity> {
        final ShulkerEntity this$0;

        public AttackNearestGoal(ShulkerEntity shulkerEntity, ShulkerEntity shulkerEntity2) {
            this.this$0 = shulkerEntity;
            super((MobEntity)shulkerEntity2, PlayerEntity.class, true);
        }

        @Override
        public boolean shouldExecute() {
            return this.this$0.world.getDifficulty() == Difficulty.PEACEFUL ? false : super.shouldExecute();
        }

        @Override
        protected AxisAlignedBB getTargetableArea(double d) {
            Direction direction = ((ShulkerEntity)this.goalOwner).getAttachmentFacing();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.goalOwner.getBoundingBox().grow(4.0, d, d);
            }
            return direction.getAxis() == Direction.Axis.Z ? this.goalOwner.getBoundingBox().grow(d, d, 4.0) : this.goalOwner.getBoundingBox().grow(d, 4.0, d);
        }
    }

    static class DefenseAttackGoal
    extends NearestAttackableTargetGoal<LivingEntity> {
        public DefenseAttackGoal(ShulkerEntity shulkerEntity) {
            super(shulkerEntity, LivingEntity.class, 10, true, false, DefenseAttackGoal::lambda$new$0);
        }

        @Override
        public boolean shouldExecute() {
            return this.goalOwner.getTeam() == null ? false : super.shouldExecute();
        }

        @Override
        protected AxisAlignedBB getTargetableArea(double d) {
            Direction direction = ((ShulkerEntity)this.goalOwner).getAttachmentFacing();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.goalOwner.getBoundingBox().grow(4.0, d, d);
            }
            return direction.getAxis() == Direction.Axis.Z ? this.goalOwner.getBoundingBox().grow(d, d, 4.0) : this.goalOwner.getBoundingBox().grow(d, 4.0, d);
        }

        private static boolean lambda$new$0(LivingEntity livingEntity) {
            return livingEntity instanceof IMob;
        }
    }

    class BodyHelperController
    extends BodyController {
        final ShulkerEntity this$0;

        public BodyHelperController(ShulkerEntity shulkerEntity, MobEntity mobEntity) {
            this.this$0 = shulkerEntity;
            super(mobEntity);
        }

        @Override
        public void updateRenderAngles() {
        }
    }
}

