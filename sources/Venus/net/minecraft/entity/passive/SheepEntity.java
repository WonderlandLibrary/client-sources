/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IShearable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SheepEntity
extends AnimalEntity
implements IShearable {
    private static final DataParameter<Byte> DYE_COLOR = EntityDataManager.createKey(SheepEntity.class, DataSerializers.BYTE);
    private static final Map<DyeColor, IItemProvider> WOOL_BY_COLOR = Util.make(Maps.newEnumMap(DyeColor.class), SheepEntity::lambda$static$0);
    private static final Map<DyeColor, float[]> DYE_TO_RGB = Maps.newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap(SheepEntity::lambda$static$1, SheepEntity::createSheepColor)));
    private int sheepTimer;
    private EatGrassGoal eatGrassGoal;

    private static float[] createSheepColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return new float[]{0.9019608f, 0.9019608f, 0.9019608f};
        }
        float[] fArray = dyeColor.getColorComponentValues();
        float f = 0.75f;
        return new float[]{fArray[0] * 0.75f, fArray[1] * 0.75f, fArray[2] * 0.75f};
    }

    public static float[] getDyeRgb(DyeColor dyeColor) {
        return DYE_TO_RGB.get(dyeColor);
    }

    public SheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal((CreatureEntity)this, 1.1, Ingredient.fromItems(Items.WHEAT), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, this.eatGrassGoal);
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.eatGrassGoal.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.livingTick();
    }

    public static AttributeModifierMap.MutableAttribute func_234225_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.23f);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(DYE_COLOR, (byte)0);
    }

    @Override
    public ResourceLocation getLootTable() {
        if (this.getSheared()) {
            return this.getType().getLootTable();
        }
        switch (2.$SwitchMap$net$minecraft$item$DyeColor[this.getFleeceColor().ordinal()]) {
            default: {
                return LootTables.ENTITIES_SHEEP_WHITE;
            }
            case 2: {
                return LootTables.ENTITIES_SHEEP_ORANGE;
            }
            case 3: {
                return LootTables.ENTITIES_SHEEP_MAGENTA;
            }
            case 4: {
                return LootTables.ENTITIES_SHEEP_LIGHT_BLUE;
            }
            case 5: {
                return LootTables.ENTITIES_SHEEP_YELLOW;
            }
            case 6: {
                return LootTables.ENTITIES_SHEEP_LIME;
            }
            case 7: {
                return LootTables.ENTITIES_SHEEP_PINK;
            }
            case 8: {
                return LootTables.ENTITIES_SHEEP_GRAY;
            }
            case 9: {
                return LootTables.ENTITIES_SHEEP_LIGHT_GRAY;
            }
            case 10: {
                return LootTables.ENTITIES_SHEEP_CYAN;
            }
            case 11: {
                return LootTables.ENTITIES_SHEEP_PURPLE;
            }
            case 12: {
                return LootTables.ENTITIES_SHEEP_BLUE;
            }
            case 13: {
                return LootTables.ENTITIES_SHEEP_BROWN;
            }
            case 14: {
                return LootTables.ENTITIES_SHEEP_GREEN;
            }
            case 15: {
                return LootTables.ENTITIES_SHEEP_RED;
            }
            case 16: 
        }
        return LootTables.ENTITIES_SHEEP_BLACK;
    }

    @Override
    public void handleStatusUpdate(byte by) {
        if (by == 10) {
            this.sheepTimer = 40;
        } else {
            super.handleStatusUpdate(by);
        }
    }

    public float getHeadRotationPointY(float f) {
        if (this.sheepTimer <= 0) {
            return 0.0f;
        }
        if (this.sheepTimer >= 4 && this.sheepTimer <= 36) {
            return 1.0f;
        }
        return this.sheepTimer < 4 ? ((float)this.sheepTimer - f) / 4.0f : -((float)(this.sheepTimer - 40) - f) / 4.0f;
    }

    public float getHeadRotationAngleX(float f) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f2 = ((float)(this.sheepTimer - 4) - f) / 32.0f;
            return 0.62831855f + 0.2199115f * MathHelper.sin(f2 * 28.7f);
        }
        return this.sheepTimer > 0 ? 0.62831855f : this.rotationPitch * ((float)Math.PI / 180);
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!this.world.isRemote && this.isShearable()) {
                this.shear(SoundCategory.PLAYERS);
                itemStack.damageItem(1, playerEntity, arg_0 -> SheepEntity.lambda$func_230254_b_$2(hand, arg_0));
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.CONSUME;
        }
        return super.func_230254_b_(playerEntity, hand);
    }

    @Override
    public void shear(SoundCategory soundCategory) {
        this.world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, soundCategory, 1.0f, 1.0f);
        this.setSheared(false);
        int n = 1 + this.rand.nextInt(3);
        for (int i = 0; i < n; ++i) {
            ItemEntity itemEntity = this.entityDropItem(WOOL_BY_COLOR.get(this.getFleeceColor()), 1);
            if (itemEntity == null) continue;
            itemEntity.setMotion(itemEntity.getMotion().add((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f, this.rand.nextFloat() * 0.05f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f));
        }
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.getSheared() && !this.isChild();
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putBoolean("Sheared", this.getSheared());
        compoundNBT.putByte("Color", (byte)this.getFleeceColor().getId());
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        this.setSheared(compoundNBT.getBoolean("Sheared"));
        this.setFleeceColor(DyeColor.byId(compoundNBT.getByte("Color")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15f, 1.0f);
    }

    public DyeColor getFleeceColor() {
        return DyeColor.byId(this.dataManager.get(DYE_COLOR) & 0xF);
    }

    public void setFleeceColor(DyeColor dyeColor) {
        byte by = this.dataManager.get(DYE_COLOR);
        this.dataManager.set(DYE_COLOR, (byte)(by & 0xF0 | dyeColor.getId() & 0xF));
    }

    public boolean getSheared() {
        return (this.dataManager.get(DYE_COLOR) & 0x10) != 0;
    }

    public void setSheared(boolean bl) {
        byte by = this.dataManager.get(DYE_COLOR);
        if (bl) {
            this.dataManager.set(DYE_COLOR, (byte)(by | 0x10));
        } else {
            this.dataManager.set(DYE_COLOR, (byte)(by & 0xFFFFFFEF));
        }
    }

    public static DyeColor getRandomSheepColor(Random random2) {
        int n = random2.nextInt(100);
        if (n < 5) {
            return DyeColor.BLACK;
        }
        if (n < 10) {
            return DyeColor.GRAY;
        }
        if (n < 15) {
            return DyeColor.LIGHT_GRAY;
        }
        if (n < 18) {
            return DyeColor.BROWN;
        }
        return random2.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
    }

    @Override
    public SheepEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        SheepEntity sheepEntity = (SheepEntity)ageableEntity;
        SheepEntity sheepEntity2 = EntityType.SHEEP.create(serverWorld);
        sheepEntity2.setFleeceColor(this.getDyeColorMixFromParents(this, sheepEntity));
        return sheepEntity2;
    }

    @Override
    public void eatGrassBonus() {
        this.setSheared(true);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }

    @Override
    @Nullable
    public ILivingEntityData onInitialSpawn(IServerWorld iServerWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        this.setFleeceColor(SheepEntity.getRandomSheepColor(iServerWorld.getRandom()));
        return super.onInitialSpawn(iServerWorld, difficultyInstance, spawnReason, iLivingEntityData, compoundNBT);
    }

    private DyeColor getDyeColorMixFromParents(AnimalEntity animalEntity, AnimalEntity animalEntity2) {
        DyeColor dyeColor = ((SheepEntity)animalEntity).getFleeceColor();
        DyeColor dyeColor2 = ((SheepEntity)animalEntity2).getFleeceColor();
        CraftingInventory craftingInventory = SheepEntity.createDyeColorCraftingInventory(dyeColor, dyeColor2);
        Optional<Item> optional = this.world.getRecipeManager().getRecipe(IRecipeType.CRAFTING, craftingInventory, this.world).map(arg_0 -> SheepEntity.lambda$getDyeColorMixFromParents$3(craftingInventory, arg_0)).map(ItemStack::getItem);
        Objects.requireNonNull(DyeItem.class);
        Optional<Item> optional2 = optional.filter(DyeItem.class::isInstance);
        Objects.requireNonNull(DyeItem.class);
        return optional2.map(DyeItem.class::cast).map(DyeItem::getDyeColor).orElseGet(() -> this.lambda$getDyeColorMixFromParents$4(dyeColor, dyeColor2));
    }

    private static CraftingInventory createDyeColorCraftingInventory(DyeColor dyeColor, DyeColor dyeColor2) {
        CraftingInventory craftingInventory = new CraftingInventory(new Container((ContainerType)null, -1){

            @Override
            public boolean canInteractWith(PlayerEntity playerEntity) {
                return true;
            }
        }, 2, 1);
        craftingInventory.setInventorySlotContents(0, new ItemStack(DyeItem.getItem(dyeColor)));
        craftingInventory.setInventorySlotContents(1, new ItemStack(DyeItem.getItem(dyeColor2)));
        return craftingInventory;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize entitySize) {
        return 0.95f * entitySize.height;
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        return this.func_241840_a(serverWorld, ageableEntity);
    }

    private DyeColor lambda$getDyeColorMixFromParents$4(DyeColor dyeColor, DyeColor dyeColor2) {
        return this.world.rand.nextBoolean() ? dyeColor : dyeColor2;
    }

    private static ItemStack lambda$getDyeColorMixFromParents$3(CraftingInventory craftingInventory, ICraftingRecipe iCraftingRecipe) {
        return iCraftingRecipe.getCraftingResult(craftingInventory);
    }

    private static void lambda$func_230254_b_$2(Hand hand, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(hand);
    }

    private static DyeColor lambda$static$1(DyeColor dyeColor) {
        return dyeColor;
    }

    private static void lambda$static$0(EnumMap enumMap) {
        enumMap.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
        enumMap.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
        enumMap.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
        enumMap.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        enumMap.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
        enumMap.put(DyeColor.LIME, Blocks.LIME_WOOL);
        enumMap.put(DyeColor.PINK, Blocks.PINK_WOOL);
        enumMap.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
        enumMap.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        enumMap.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
        enumMap.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
        enumMap.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
        enumMap.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
        enumMap.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
        enumMap.put(DyeColor.RED, Blocks.RED_WOOL);
        enumMap.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
    }
}

