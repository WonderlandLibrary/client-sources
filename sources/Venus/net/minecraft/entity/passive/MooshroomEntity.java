/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DrinkHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MooshroomEntity
extends CowEntity
implements IShearable {
    private static final DataParameter<String> MOOSHROOM_TYPE = EntityDataManager.createKey(MooshroomEntity.class, DataSerializers.STRING);
    private Effect hasStewEffect;
    private int effectDuration;
    private UUID lightningUUID;

    public MooshroomEntity(EntityType<? extends MooshroomEntity> entityType, World world) {
        super((EntityType<? extends CowEntity>)entityType, world);
    }

    @Override
    public float getBlockPathWeight(BlockPos blockPos, IWorldReader iWorldReader) {
        return iWorldReader.getBlockState(blockPos.down()).isIn(Blocks.MYCELIUM) ? 10.0f : iWorldReader.getBrightness(blockPos) - 0.5f;
    }

    public static boolean func_223318_c(EntityType<MooshroomEntity> entityType, IWorld iWorld, SpawnReason spawnReason, BlockPos blockPos, Random random2) {
        return iWorld.getBlockState(blockPos.down()).isIn(Blocks.MYCELIUM) && iWorld.getLightSubtracted(blockPos, 0) > 8;
    }

    @Override
    public void func_241841_a(ServerWorld serverWorld, LightningBoltEntity lightningBoltEntity) {
        UUID uUID = lightningBoltEntity.getUniqueID();
        if (!uUID.equals(this.lightningUUID)) {
            this.setMooshroomType(this.getMooshroomType() == Type.RED ? Type.BROWN : Type.RED);
            this.lightningUUID = uUID;
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0f, 1.0f);
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOOSHROOM_TYPE, Type.RED.name);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.BOWL && !this.isChild()) {
            ItemStack itemStack2;
            boolean bl = false;
            if (this.hasStewEffect != null) {
                bl = true;
                itemStack2 = new ItemStack(Items.SUSPICIOUS_STEW);
                SuspiciousStewItem.addEffect(itemStack2, this.hasStewEffect, this.effectDuration);
                this.hasStewEffect = null;
                this.effectDuration = 0;
            } else {
                itemStack2 = new ItemStack(Items.MUSHROOM_STEW);
            }
            ItemStack itemStack3 = DrinkHelper.fill(itemStack, playerEntity, itemStack2, false);
            playerEntity.setHeldItem(hand, itemStack3);
            SoundEvent soundEvent = bl ? SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK : SoundEvents.ENTITY_MOOSHROOM_MILK;
            this.playSound(soundEvent, 1.0f, 1.0f);
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (itemStack.getItem() == Items.SHEARS && this.isShearable()) {
            this.shear(SoundCategory.PLAYERS);
            if (!this.world.isRemote) {
                itemStack.damageItem(1, playerEntity, arg_0 -> MooshroomEntity.lambda$func_230254_b_$0(hand, arg_0));
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        if (this.getMooshroomType() == Type.BROWN && itemStack.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
            if (this.hasStewEffect != null) {
                for (int i = 0; i < 2; ++i) {
                    this.world.addParticle(ParticleTypes.SMOKE, this.getPosX() + this.rand.nextDouble() / 2.0, this.getPosYHeight(0.5), this.getPosZ() + this.rand.nextDouble() / 2.0, 0.0, this.rand.nextDouble() / 5.0, 0.0);
                }
            } else {
                Optional<Pair<Effect, Integer>> optional = this.getStewEffect(itemStack);
                if (!optional.isPresent()) {
                    return ActionResultType.PASS;
                }
                Pair<Effect, Integer> pair = optional.get();
                if (!playerEntity.abilities.isCreativeMode) {
                    itemStack.shrink(1);
                }
                for (int i = 0; i < 4; ++i) {
                    this.world.addParticle(ParticleTypes.EFFECT, this.getPosX() + this.rand.nextDouble() / 2.0, this.getPosYHeight(0.5), this.getPosZ() + this.rand.nextDouble() / 2.0, 0.0, this.rand.nextDouble() / 5.0, 0.0);
                }
                this.hasStewEffect = pair.getLeft();
                this.effectDuration = pair.getRight();
                this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0f, 1.0f);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    public void shear(SoundCategory soundCategory) {
        this.world.playMovingSound(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, soundCategory, 1.0f, 1.0f);
        if (!this.world.isRemote()) {
            ((ServerWorld)this.world).spawnParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosYHeight(0.5), this.getPosZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.remove();
            CowEntity cowEntity = EntityType.COW.create(this.world);
            cowEntity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
            cowEntity.setHealth(this.getHealth());
            cowEntity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
                cowEntity.setCustomName(this.getCustomName());
                cowEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (this.isNoDespawnRequired()) {
                cowEntity.enablePersistence();
            }
            cowEntity.setInvulnerable(this.isInvulnerable());
            this.world.addEntity(cowEntity);
            for (int i = 0; i < 5; ++i) {
                this.world.addEntity(new ItemEntity(this.world, this.getPosX(), this.getPosYHeight(1.0), this.getPosZ(), new ItemStack(this.getMooshroomType().renderState.getBlock())));
            }
        }
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isChild();
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putString("Type", this.getMooshroomType().name);
        if (this.hasStewEffect != null) {
            compoundNBT.putByte("EffectId", (byte)Effect.getId(this.hasStewEffect));
            compoundNBT.putInt("EffectDuration", this.effectDuration);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setMooshroomType(Type.getTypeByName(compoundNBT.getString("Type")));
        if (compoundNBT.contains("EffectId", 0)) {
            this.hasStewEffect = Effect.get(compoundNBT.getByte("EffectId"));
        }
        if (compoundNBT.contains("EffectDuration", 0)) {
            this.effectDuration = compoundNBT.getInt("EffectDuration");
        }
    }

    private Optional<Pair<Effect, Integer>> getStewEffect(ItemStack itemStack) {
        Block block;
        Item item = itemStack.getItem();
        if (item instanceof BlockItem && (block = ((BlockItem)item).getBlock()) instanceof FlowerBlock) {
            FlowerBlock flowerBlock = (FlowerBlock)block;
            return Optional.of(Pair.of(flowerBlock.getStewEffect(), flowerBlock.getStewEffectDuration()));
        }
        return Optional.empty();
    }

    private void setMooshroomType(Type type) {
        this.dataManager.set(MOOSHROOM_TYPE, type.name);
    }

    public Type getMooshroomType() {
        return Type.getTypeByName(this.dataManager.get(MOOSHROOM_TYPE));
    }

    @Override
    public MooshroomEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        MooshroomEntity mooshroomEntity = EntityType.MOOSHROOM.create(serverWorld);
        mooshroomEntity.setMooshroomType(this.func_213445_a((MooshroomEntity)ageableEntity));
        return mooshroomEntity;
    }

    private Type func_213445_a(MooshroomEntity mooshroomEntity) {
        Type type;
        Type type2 = this.getMooshroomType();
        Type type3 = type2 == (type = mooshroomEntity.getMooshroomType()) && this.rand.nextInt(1024) == 0 ? (type2 == Type.BROWN ? Type.RED : Type.BROWN) : (this.rand.nextBoolean() ? type2 : type);
        return type3;
    }

    @Override
    public CowEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    private static void lambda$func_230254_b_$0(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }

    public static enum Type {
        RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
        BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

        private final String name;
        private final BlockState renderState;

        private Type(String string2, BlockState blockState) {
            this.name = string2;
            this.renderState = blockState;
        }

        public BlockState getRenderState() {
            return this.renderState;
        }

        private static Type getTypeByName(String string) {
            for (Type type : Type.values()) {
                if (!type.name.equals(string)) continue;
                return type;
            }
            return RED;
        }
    }
}

