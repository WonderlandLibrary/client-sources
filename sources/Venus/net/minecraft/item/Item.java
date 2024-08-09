/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class Item
implements IItemProvider {
    public static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
    protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    protected static final Random random = new Random();
    protected final ItemGroup group;
    private final Rarity rarity;
    private final int maxStackSize;
    private final int maxDamage;
    private final boolean burnable;
    private final Item containerItem;
    @Nullable
    private String translationKey;
    @Nullable
    private final Food food;

    public static int getIdFromItem(Item item) {
        return item == null ? 0 : Registry.ITEM.getId(item);
    }

    public static Item getItemById(int n) {
        return Registry.ITEM.getByValue(n);
    }

    @Deprecated
    public static Item getItemFromBlock(Block block) {
        return BLOCK_TO_ITEM.getOrDefault(block, Items.AIR);
    }

    public Item(Properties properties) {
        this.group = properties.group;
        this.rarity = properties.rarity;
        this.containerItem = properties.containerItem;
        this.maxDamage = properties.maxDamage;
        this.maxStackSize = properties.maxStackSize;
        this.food = properties.food;
        this.burnable = properties.immuneToFire;
    }

    public void onUse(World world, LivingEntity livingEntity, ItemStack itemStack, int n) {
    }

    public boolean updateItemStackNBT(CompoundNBT compoundNBT) {
        return true;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return false;
    }

    @Override
    public Item asItem() {
        return this;
    }

    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        return ActionResultType.PASS;
    }

    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return 1.0f;
    }

    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        if (this.isFood()) {
            ItemStack itemStack = playerEntity.getHeldItem(hand);
            if (playerEntity.canEat(this.getFood().canEatWhenFull())) {
                playerEntity.setActiveHand(hand);
                return ActionResult.resultConsume(itemStack);
            }
            return ActionResult.resultFail(itemStack);
        }
        return ActionResult.resultPass(playerEntity.getHeldItem(hand));
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, LivingEntity livingEntity) {
        return this.isFood() ? livingEntity.onFoodEaten(world, itemStack) : itemStack;
    }

    public final int getMaxStackSize() {
        return this.maxStackSize;
    }

    public final int getMaxDamage() {
        return this.maxDamage;
    }

    public boolean isDamageable() {
        return this.maxDamage > 0;
    }

    public boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        return true;
    }

    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        return true;
    }

    public boolean canHarvestBlock(BlockState blockState) {
        return true;
    }

    public ActionResultType itemInteractionForEntity(ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
        return ActionResultType.PASS;
    }

    public ITextComponent getName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    public String toString() {
        return Registry.ITEM.getKey(this).getPath();
    }

    protected String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("item", Registry.ITEM.getKey(this));
        }
        return this.translationKey;
    }

    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }

    public String getTranslationKey(ItemStack itemStack) {
        return this.getTranslationKey();
    }

    public boolean shouldSyncTag() {
        return false;
    }

    @Nullable
    public final Item getContainerItem() {
        return this.containerItem;
    }

    public boolean hasContainerItem() {
        return this.containerItem != null;
    }

    public void inventoryTick(ItemStack itemStack, World world, Entity entity2, int n, boolean bl) {
    }

    public void onCreated(ItemStack itemStack, World world, PlayerEntity playerEntity) {
    }

    public boolean isComplex() {
        return true;
    }

    public UseAction getUseAction(ItemStack itemStack) {
        return itemStack.getItem().isFood() ? UseAction.EAT : UseAction.NONE;
    }

    public int getUseDuration(ItemStack itemStack) {
        if (itemStack.getItem().isFood()) {
            return this.getFood().isFastEating() ? 16 : 32;
        }
        return 1;
    }

    public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int n) {
    }

    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
    }

    public ITextComponent getDisplayName(ItemStack itemStack) {
        return new TranslationTextComponent(this.getTranslationKey(itemStack));
    }

    public boolean hasEffect(ItemStack itemStack) {
        return itemStack.isEnchanted();
    }

    public Rarity getRarity(ItemStack itemStack) {
        if (!itemStack.isEnchanted()) {
            return this.rarity;
        }
        switch (1.$SwitchMap$net$minecraft$item$Rarity[this.rarity.ordinal()]) {
            case 1: 
            case 2: {
                return Rarity.RARE;
            }
            case 3: {
                return Rarity.EPIC;
            }
        }
        return this.rarity;
    }

    public boolean isEnchantable(ItemStack itemStack) {
        return this.getMaxStackSize() == 1 && this.isDamageable();
    }

    protected static BlockRayTraceResult rayTrace(World world, PlayerEntity playerEntity, RayTraceContext.FluidMode fluidMode) {
        float f = playerEntity.rotationPitch;
        float f2 = playerEntity.rotationYaw;
        Vector3d vector3d = playerEntity.getEyePosition(1.0f);
        float f3 = MathHelper.cos(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * ((float)Math.PI / 180) - (float)Math.PI);
        float f5 = -MathHelper.cos(-f * ((float)Math.PI / 180));
        float f6 = MathHelper.sin(-f * ((float)Math.PI / 180));
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d = 5.0;
        Vector3d vector3d2 = vector3d.add((double)f7 * 5.0, (double)f6 * 5.0, (double)f8 * 5.0);
        return world.rayTraceBlocks(new RayTraceContext(vector3d, vector3d2, RayTraceContext.BlockMode.OUTLINE, fluidMode, playerEntity));
    }

    public int getItemEnchantability() {
        return 1;
    }

    public void fillItemGroup(ItemGroup itemGroup, NonNullList<ItemStack> nonNullList) {
        if (this.isInGroup(itemGroup)) {
            nonNullList.add(new ItemStack(this));
        }
    }

    protected boolean isInGroup(ItemGroup itemGroup) {
        ItemGroup itemGroup2 = this.getGroup();
        return itemGroup2 != null && (itemGroup == ItemGroup.SEARCH || itemGroup == itemGroup2);
    }

    @Nullable
    public final ItemGroup getGroup() {
        return this.group;
    }

    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2) {
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        return ImmutableMultimap.of();
    }

    public boolean isCrossbow(ItemStack itemStack) {
        return itemStack.getItem() == Items.CROSSBOW;
    }

    public ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }

    public boolean isIn(ITag<Item> iTag) {
        return iTag.contains(this);
    }

    public boolean isFood() {
        return this.food != null;
    }

    @Nullable
    public Food getFood() {
        return this.food;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ENTITY_GENERIC_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ENTITY_GENERIC_EAT;
    }

    public boolean isImmuneToFire() {
        return this.burnable;
    }

    public boolean isDamageable(DamageSource damageSource) {
        return !this.burnable || !damageSource.isFireDamage();
    }

    public static class Properties {
        private int maxStackSize = 64;
        private int maxDamage;
        private Item containerItem;
        private ItemGroup group;
        private Rarity rarity = Rarity.COMMON;
        private Food food;
        private boolean immuneToFire;

        public Properties food(Food food) {
            this.food = food;
            return this;
        }

        public Properties maxStackSize(int n) {
            if (this.maxDamage > 0) {
                throw new RuntimeException("Unable to have damage AND stack.");
            }
            this.maxStackSize = n;
            return this;
        }

        public Properties defaultMaxDamage(int n) {
            return this.maxDamage == 0 ? this.maxDamage(n) : this;
        }

        public Properties maxDamage(int n) {
            this.maxDamage = n;
            this.maxStackSize = 1;
            return this;
        }

        public Properties containerItem(Item item) {
            this.containerItem = item;
            return this;
        }

        public Properties group(ItemGroup itemGroup) {
            this.group = itemGroup;
            return this;
        }

        public Properties rarity(Rarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Properties isImmuneToFire() {
            this.immuneToFire = true;
            return this;
        }
    }
}

