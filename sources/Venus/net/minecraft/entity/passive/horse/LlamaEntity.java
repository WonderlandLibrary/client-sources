/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive.horse;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarpetBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LlamaFollowCaravanGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LlamaEntity
extends AbstractChestedHorseEntity
implements IRangedAttackMob {
    private static final Ingredient field_234243_bC_ = Ingredient.fromItems(Items.WHEAT, Blocks.HAY_BLOCK.asItem());
    private static final DataParameter<Integer> DATA_STRENGTH_ID = EntityDataManager.createKey(LlamaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(LlamaEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.createKey(LlamaEntity.class, DataSerializers.VARINT);
    private boolean didSpit;
    @Nullable
    private LlamaEntity caravanHead;
    @Nullable
    private LlamaEntity caravanTail;

    public LlamaEntity(EntityType<? extends LlamaEntity> entityType, World world) {
        super((EntityType<? extends AbstractChestedHorseEntity>)entityType, world);
    }

    public boolean isTraderLlama() {
        return true;
    }

    private void setStrength(int n) {
        this.dataManager.set(DATA_STRENGTH_ID, Math.max(1, Math.min(5, n)));
    }

    private void setRandomStrength() {
        int n = this.rand.nextFloat() < 0.04f ? 5 : 3;
        this.setStrength(1 + this.rand.nextInt(n));
    }

    public int getStrength() {
        return this.dataManager.get(DATA_STRENGTH_ID);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("Variant", this.getVariant());
        compoundNBT.putInt("Strength", this.getStrength());
        if (!this.horseChest.getStackInSlot(1).isEmpty()) {
            compoundNBT.put("DecorItem", this.horseChest.getStackInSlot(1).write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.setStrength(compoundNBT.getInt("Strength"));
        super.readAdditional(compoundNBT);
        this.setVariant(compoundNBT.getInt("Variant"));
        if (compoundNBT.contains("DecorItem", 1)) {
            this.horseChest.setInventorySlotContents(1, ItemStack.read(compoundNBT.getCompound("DecorItem")));
        }
        this.func_230275_fc_();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(2, new LlamaFollowCaravanGoal(this, 2.1f));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.25, 40, 20.0f));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 0.7));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new DefendTargetGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute func_234244_fu_() {
        return LlamaEntity.func_234234_eJ_().createMutableAttribute(Attributes.FOLLOW_RANGE, 40.0);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DATA_STRENGTH_ID, 0);
        this.dataManager.register(DATA_COLOR_ID, -1);
        this.dataManager.register(DATA_VARIANT_ID, 0);
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(DATA_VARIANT_ID), 0, 3);
    }

    public void setVariant(int n) {
        this.dataManager.set(DATA_VARIANT_ID, n);
    }

    @Override
    protected int getInventorySize() {
        return this.hasChest() ? 2 + 3 * this.getInventoryColumns() : super.getInventorySize();
    }

    @Override
    public void updatePassenger(Entity entity2) {
        if (this.isPassenger(entity2)) {
            float f = MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180));
            float f2 = MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180));
            float f3 = 0.3f;
            entity2.setPosition(this.getPosX() + (double)(0.3f * f2), this.getPosY() + this.getMountedYOffset() + entity2.getYOffset(), this.getPosZ() - (double)(0.3f * f));
        }
    }

    @Override
    public double getMountedYOffset() {
        return (double)this.getHeight() * 0.67;
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public boolean isBreedingItem(ItemStack itemStack) {
        return field_234243_bC_.test(itemStack);
    }

    @Override
    protected boolean handleEating(PlayerEntity playerEntity, ItemStack itemStack) {
        SoundEvent soundEvent;
        int n = 0;
        int n2 = 0;
        float f = 0.0f;
        boolean bl = false;
        Item item = itemStack.getItem();
        if (item == Items.WHEAT) {
            n = 10;
            n2 = 3;
            f = 2.0f;
        } else if (item == Blocks.HAY_BLOCK.asItem()) {
            n = 90;
            n2 = 6;
            f = 10.0f;
            if (this.isTame() && this.getGrowingAge() == 0 && this.canFallInLove()) {
                bl = true;
                this.setInLove(playerEntity);
            }
        }
        if (this.getHealth() < this.getMaxHealth() && f > 0.0f) {
            this.heal(f);
            bl = true;
        }
        if (this.isChild() && n > 0) {
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0), this.getPosYRandom() + 0.5, this.getPosZRandom(1.0), 0.0, 0.0, 0.0);
            if (!this.world.isRemote) {
                this.addGrowth(n);
            }
            bl = true;
        }
        if (n2 > 0 && (bl || !this.isTame()) && this.getTemper() < this.getMaxTemper()) {
            bl = true;
            if (!this.world.isRemote) {
                this.increaseTemper(n2);
            }
        }
        if (bl && !this.isSilent() && (soundEvent = this.func_230274_fe_()) != null) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), this.func_230274_fe_(), this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
        return bl;
    }

    @Override
    protected boolean isMovementBlocked() {
        return this.getShouldBeDead() || this.isEatingHaystack();
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        int n;
        this.setRandomStrength();
        if (iLivingEntityData instanceof LlamaData) {
            n = ((LlamaData)iLivingEntityData).variant;
        } else {
            n = this.rand.nextInt(4);
            iLivingEntityData = new LlamaData(n);
        }
        this.setVariant(n);
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_LLAMA_ANGRY;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_LLAMA_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_LLAMA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_LLAMA_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent func_230274_fe_() {
        return SoundEvents.ENTITY_LLAMA_EAT;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15f, 1.0f);
    }

    @Override
    protected void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_LLAMA_CHEST, 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
    }

    @Override
    public void makeMad() {
        SoundEvent soundEvent = this.getAngrySound();
        if (soundEvent != null) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public int getInventoryColumns() {
        return this.getStrength();
    }

    @Override
    public boolean func_230276_fq_() {
        return false;
    }

    @Override
    public boolean func_230277_fr_() {
        return !this.horseChest.getStackInSlot(1).isEmpty();
    }

    @Override
    public boolean isArmor(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return ItemTags.CARPETS.contains(item);
    }

    @Override
    public boolean func_230264_L__() {
        return true;
    }

    @Override
    public void onInventoryChanged(IInventory iInventory) {
        DyeColor dyeColor = this.getColor();
        super.onInventoryChanged(iInventory);
        DyeColor dyeColor2 = this.getColor();
        if (this.ticksExisted > 20 && dyeColor2 != null && dyeColor2 != dyeColor) {
            this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5f, 1.0f);
        }
    }

    @Override
    protected void func_230275_fc_() {
        if (!this.world.isRemote) {
            super.func_230275_fc_();
            this.setColor(LlamaEntity.getCarpetColor(this.horseChest.getStackInSlot(1)));
        }
    }

    private void setColor(@Nullable DyeColor dyeColor) {
        this.dataManager.set(DATA_COLOR_ID, dyeColor == null ? -1 : dyeColor.getId());
    }

    @Nullable
    private static DyeColor getCarpetColor(ItemStack itemStack) {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        return block instanceof CarpetBlock ? ((CarpetBlock)block).getColor() : null;
    }

    @Nullable
    public DyeColor getColor() {
        int n = this.dataManager.get(DATA_COLOR_ID);
        return n == -1 ? null : DyeColor.byId(n);
    }

    @Override
    public int getMaxTemper() {
        return 1;
    }

    @Override
    public boolean canMateWith(AnimalEntity animalEntity) {
        return animalEntity != this && animalEntity instanceof LlamaEntity && this.canMate() && ((LlamaEntity)animalEntity).canMate();
    }

    @Override
    public LlamaEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        LlamaEntity llamaEntity = this.createChild();
        this.setOffspringAttributes(ageableEntity, llamaEntity);
        LlamaEntity llamaEntity2 = (LlamaEntity)ageableEntity;
        int n = this.rand.nextInt(Math.max(this.getStrength(), llamaEntity2.getStrength())) + 1;
        if (this.rand.nextFloat() < 0.03f) {
            ++n;
        }
        llamaEntity.setStrength(n);
        llamaEntity.setVariant(this.rand.nextBoolean() ? this.getVariant() : llamaEntity2.getVariant());
        return llamaEntity;
    }

    protected LlamaEntity createChild() {
        return EntityType.LLAMA.create(this.world);
    }

    private void spit(LivingEntity livingEntity) {
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(this.world, this);
        double d = livingEntity.getPosX() - this.getPosX();
        double d2 = livingEntity.getPosYHeight(0.3333333333333333) - llamaSpitEntity.getPosY();
        double d3 = livingEntity.getPosZ() - this.getPosZ();
        float f = MathHelper.sqrt(d * d + d3 * d3) * 0.2f;
        llamaSpitEntity.shoot(d, d2 + (double)f, d3, 1.5f, 10.0f);
        if (!this.isSilent()) {
            this.world.playSound(null, this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LLAMA_SPIT, this.getSoundCategory(), 1.0f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
        }
        this.world.addEntity(llamaSpitEntity);
        this.didSpit = true;
    }

    private void setDidSpit(boolean bl) {
        this.didSpit = bl;
    }

    @Override
    public boolean onLivingFall(float f, float f2) {
        int n = this.calculateFallDamage(f, f2);
        if (n <= 0) {
            return true;
        }
        if (f >= 6.0f) {
            this.attackEntityFrom(DamageSource.FALL, n);
            if (this.isBeingRidden()) {
                for (Entity entity2 : this.getRecursivePassengers()) {
                    entity2.attackEntityFrom(DamageSource.FALL, n);
                }
            }
        }
        this.playFallSound();
        return false;
    }

    public void leaveCaravan() {
        if (this.caravanHead != null) {
            this.caravanHead.caravanTail = null;
        }
        this.caravanHead = null;
    }

    public void joinCaravan(LlamaEntity llamaEntity) {
        this.caravanHead = llamaEntity;
        this.caravanHead.caravanTail = this;
    }

    public boolean hasCaravanTrail() {
        return this.caravanTail != null;
    }

    public boolean inCaravan() {
        return this.caravanHead != null;
    }

    @Nullable
    public LlamaEntity getCaravanHead() {
        return this.caravanHead;
    }

    @Override
    protected double followLeashSpeed() {
        return 2.0;
    }

    @Override
    protected void followMother() {
        if (!this.inCaravan() && this.isChild()) {
            super.followMother();
        }
    }

    @Override
    public boolean canEatGrass() {
        return true;
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f) {
        this.spit(livingEntity);
    }

    @Override
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0, 0.75 * (double)this.getEyeHeight(), (double)this.getWidth() * 0.5);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    static class HurtByTargetGoal
    extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
        public HurtByTargetGoal(LlamaEntity llamaEntity) {
            super(llamaEntity, new Class[0]);
        }

        @Override
        public boolean shouldContinueExecuting() {
            if (this.goalOwner instanceof LlamaEntity) {
                LlamaEntity llamaEntity = (LlamaEntity)this.goalOwner;
                if (llamaEntity.didSpit) {
                    llamaEntity.setDidSpit(true);
                    return true;
                }
            }
            return super.shouldContinueExecuting();
        }
    }

    static class DefendTargetGoal
    extends NearestAttackableTargetGoal<WolfEntity> {
        public DefendTargetGoal(LlamaEntity llamaEntity) {
            super(llamaEntity, WolfEntity.class, 16, false, true, DefendTargetGoal::lambda$new$0);
        }

        @Override
        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.25;
        }

        private static boolean lambda$new$0(LivingEntity livingEntity) {
            return !((WolfEntity)livingEntity).isTamed();
        }
    }

    static class LlamaData
    extends AgeableEntity.AgeableData {
        public final int variant;

        private LlamaData(int n) {
            super(true);
            this.variant = n;
        }
    }
}

