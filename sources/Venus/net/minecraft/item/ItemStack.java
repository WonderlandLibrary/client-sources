/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.functions.impl.player.ItemCooldown;
import mpp.venusfr.venusfr;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.arguments.BlockPredicateArgument;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.UnbreakingEnchantment;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ItemStack {
    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create(ItemStack::lambda$static$3);
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ItemStack EMPTY = new ItemStack((IItemProvider)null);
    public static final DecimalFormat DECIMALFORMAT = Util.make(new DecimalFormat("#.##"), ItemStack::lambda$static$4);
    private static final Style LORE_STYLE = Style.EMPTY.setFormatting(TextFormatting.DARK_PURPLE).setItalic(true);
    private int count;
    private int animationsToGo;
    @Deprecated
    private final Item item;
    private CompoundNBT tag;
    private boolean isEmpty;
    private Entity attachedEntity;
    private CachedBlockInfo canDestroyCacheBlock;
    private boolean canDestroyCacheResult;
    private CachedBlockInfo canPlaceOnCacheBlock;
    private boolean canPlaceOnCacheResult;

    public ItemStack(IItemProvider iItemProvider) {
        this(iItemProvider, 1);
    }

    private ItemStack(IItemProvider iItemProvider, int n, Optional<CompoundNBT> optional) {
        this(iItemProvider, n);
        optional.ifPresent(this::setTag);
    }

    public ItemStack(IItemProvider iItemProvider, int n) {
        this.item = iItemProvider == null ? null : iItemProvider.asItem();
        this.count = n;
        if (this.item != null && this.item.isDamageable()) {
            this.setDamage(this.getDamage());
        }
        this.updateEmptyState();
    }

    private void updateEmptyState() {
        this.isEmpty = false;
        this.isEmpty = this.isEmpty();
    }

    private ItemStack(CompoundNBT compoundNBT) {
        this.item = Registry.ITEM.getOrDefault(new ResourceLocation(compoundNBT.getString("id")));
        this.count = compoundNBT.getByte("Count");
        if (compoundNBT.contains("tag", 1)) {
            this.tag = compoundNBT.getCompound("tag");
            this.getItem().updateItemStackNBT(compoundNBT);
        }
        if (this.getItem().isDamageable()) {
            this.setDamage(this.getDamage());
        }
        this.updateEmptyState();
    }

    public static ItemStack read(CompoundNBT compoundNBT) {
        try {
            return new ItemStack(compoundNBT);
        } catch (RuntimeException runtimeException) {
            LOGGER.debug("Tried to load invalid item: {}", (Object)compoundNBT, (Object)runtimeException);
            return EMPTY;
        }
    }

    public boolean isEmpty() {
        if (this == EMPTY) {
            return false;
        }
        if (this.getItem() != null && this.getItem() != Items.AIR) {
            return this.count <= 0;
        }
        return false;
    }

    public ItemStack split(int n) {
        int n2 = Math.min(n, this.count);
        ItemStack itemStack = this.copy();
        itemStack.setCount(n2);
        this.shrink(n2);
        return itemStack;
    }

    public Item getItem() {
        return this.isEmpty ? Items.AIR : this.item;
    }

    public ActionResultType onItemUse(ItemUseContext itemUseContext) {
        PlayerEntity playerEntity = itemUseContext.getPlayer();
        BlockPos blockPos = itemUseContext.getPos();
        CachedBlockInfo cachedBlockInfo = new CachedBlockInfo(itemUseContext.getWorld(), blockPos, false);
        if (playerEntity != null && !playerEntity.abilities.allowEdit && !this.canPlaceOn(itemUseContext.getWorld().getTags(), cachedBlockInfo)) {
            return ActionResultType.PASS;
        }
        Item item = this.getItem();
        ActionResultType actionResultType = item.onItemUse(itemUseContext);
        if (playerEntity != null && actionResultType.isSuccessOrConsume()) {
            playerEntity.addStat(Stats.ITEM_USED.get(item));
        }
        return actionResultType;
    }

    public float getDestroySpeed(BlockState blockState) {
        return this.getItem().getDestroySpeed(this, blockState);
    }

    public ActionResult<ItemStack> useItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        return this.getItem().onItemRightClick(world, playerEntity, hand);
    }

    public ItemStack onItemUseFinish(World world, LivingEntity livingEntity) {
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        ItemCooldown itemCooldown = functionRegistry.getItemCooldown();
        ItemCooldown.ItemEnum itemEnum = ItemCooldown.ItemEnum.getItemEnum(this.getItem());
        if (itemCooldown.isState() && itemEnum != null && itemCooldown.isCurrentItem(itemEnum)) {
            itemCooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
        }
        return this.getItem().onItemUseFinish(this, world, livingEntity);
    }

    public CompoundNBT write(CompoundNBT compoundNBT) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(this.getItem());
        compoundNBT.putString("id", resourceLocation == null ? "minecraft:air" : resourceLocation.toString());
        compoundNBT.putByte("Count", (byte)this.count);
        if (this.tag != null) {
            compoundNBT.put("tag", this.tag.copy());
        }
        return compoundNBT;
    }

    public int getMaxStackSize() {
        return this.getItem().getMaxStackSize();
    }

    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isDamageable() || !this.isDamaged());
    }

    public boolean isDamageable() {
        if (!this.isEmpty && this.getItem().getMaxDamage() > 0) {
            CompoundNBT compoundNBT = this.getTag();
            return compoundNBT == null || !compoundNBT.getBoolean("Unbreakable");
        }
        return true;
    }

    public boolean isDamaged() {
        return this.isDamageable() && this.getDamage() > 0;
    }

    public int getDamage() {
        return this.tag == null ? 0 : this.tag.getInt("Damage");
    }

    public void setDamage(int n) {
        this.getOrCreateTag().putInt("Damage", Math.max(0, n));
    }

    public int getMaxDamage() {
        return this.getItem().getMaxDamage();
    }

    public boolean attemptDamageItem(int n, Random random2, @Nullable ServerPlayerEntity serverPlayerEntity) {
        int n2;
        if (!this.isDamageable()) {
            return true;
        }
        if (n > 0) {
            n2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, this);
            int n3 = 0;
            for (int i = 0; n2 > 0 && i < n; ++i) {
                if (!UnbreakingEnchantment.negateDamage(this, n2, random2)) continue;
                ++n3;
            }
            if ((n -= n3) <= 0) {
                return true;
            }
        }
        if (serverPlayerEntity != null && n != 0) {
            CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger(serverPlayerEntity, this, this.getDamage() + n);
        }
        n2 = this.getDamage() + n;
        this.setDamage(n2);
        return n2 >= this.getMaxDamage();
    }

    public <T extends LivingEntity> void damageItem(int n, T t, Consumer<T> consumer) {
        if (!(t.world.isRemote || t instanceof PlayerEntity && ((PlayerEntity)t).abilities.isCreativeMode || !this.isDamageable() || !this.attemptDamageItem(n, t.getRNG(), t instanceof ServerPlayerEntity ? (ServerPlayerEntity)t : null))) {
            consumer.accept(t);
            Item item = this.getItem();
            this.shrink(1);
            if (t instanceof PlayerEntity) {
                ((PlayerEntity)t).addStat(Stats.ITEM_BROKEN.get(item));
            }
            this.setDamage(0);
        }
    }

    public void hitEntity(LivingEntity livingEntity, PlayerEntity playerEntity) {
        Item item = this.getItem();
        if (item.hitEntity(this, livingEntity, playerEntity)) {
            playerEntity.addStat(Stats.ITEM_USED.get(item));
        }
    }

    public void onBlockDestroyed(World world, BlockState blockState, BlockPos blockPos, PlayerEntity playerEntity) {
        Item item = this.getItem();
        if (item.onBlockDestroyed(this, world, blockState, blockPos, playerEntity)) {
            playerEntity.addStat(Stats.ITEM_USED.get(item));
        }
    }

    public boolean canHarvestBlock(BlockState blockState) {
        return this.getItem().canHarvestBlock(blockState);
    }

    public ActionResultType interactWithEntity(PlayerEntity playerEntity, LivingEntity livingEntity, Hand hand) {
        return this.getItem().itemInteractionForEntity(this, playerEntity, livingEntity, hand);
    }

    public ItemStack copy() {
        if (this.isEmpty()) {
            return EMPTY;
        }
        ItemStack itemStack = new ItemStack(this.getItem(), this.count);
        itemStack.setAnimationsToGo(this.getAnimationsToGo());
        if (this.tag != null) {
            itemStack.tag = this.tag.copy();
        }
        return itemStack;
    }

    public static boolean areItemStackTagsEqual(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.isEmpty() && itemStack2.isEmpty()) {
            return false;
        }
        if (!itemStack.isEmpty() && !itemStack2.isEmpty()) {
            if (itemStack.tag == null && itemStack2.tag != null) {
                return true;
            }
            return itemStack.tag == null || itemStack.tag.equals(itemStack2.tag);
        }
        return true;
    }

    public static boolean areItemStacksEqual(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.isEmpty() && itemStack2.isEmpty()) {
            return false;
        }
        return !itemStack.isEmpty() && !itemStack2.isEmpty() ? itemStack.isItemStackEqual(itemStack2) : false;
    }

    private boolean isItemStackEqual(ItemStack itemStack) {
        if (this.count != itemStack.count) {
            return true;
        }
        if (this.getItem() != itemStack.getItem()) {
            return true;
        }
        if (this.tag == null && itemStack.tag != null) {
            return true;
        }
        return this.tag == null || this.tag.equals(itemStack.tag);
    }

    public static boolean areItemsEqual(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack == itemStack2) {
            return false;
        }
        return !itemStack.isEmpty() && !itemStack2.isEmpty() ? itemStack.isItemEqual(itemStack2) : false;
    }

    public static boolean areItemsEqualIgnoreDurability(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack == itemStack2) {
            return false;
        }
        return !itemStack.isEmpty() && !itemStack2.isEmpty() ? itemStack.isItemEqualIgnoreDurability(itemStack2) : false;
    }

    public boolean isItemEqual(ItemStack itemStack) {
        return !itemStack.isEmpty() && this.getItem() == itemStack.getItem();
    }

    public boolean isItemEqualIgnoreDurability(ItemStack itemStack) {
        if (!this.isDamageable()) {
            return this.isItemEqual(itemStack);
        }
        return !itemStack.isEmpty() && this.getItem() == itemStack.getItem();
    }

    public String getTranslationKey() {
        return this.getItem().getTranslationKey(this);
    }

    public String toString() {
        return this.count + " " + this.getItem();
    }

    public void inventoryTick(World world, Entity entity2, int n, boolean bl) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        if (this.getItem() != null) {
            this.getItem().inventoryTick(this, world, entity2, n, bl);
        }
    }

    public void onCrafting(World world, PlayerEntity playerEntity, int n) {
        playerEntity.addStat(Stats.ITEM_CRAFTED.get(this.getItem()), n);
        this.getItem().onCreated(this, world, playerEntity);
    }

    public int getUseDuration() {
        return this.getItem().getUseDuration(this);
    }

    public UseAction getUseAction() {
        return this.getItem().getUseAction(this);
    }

    public void onPlayerStoppedUsing(World world, LivingEntity livingEntity, int n) {
        this.getItem().onPlayerStoppedUsing(this, world, livingEntity, n);
    }

    public boolean isCrossbowStack() {
        return this.getItem().isCrossbow(this);
    }

    public boolean hasTag() {
        return !this.isEmpty && this.tag != null && !this.tag.isEmpty();
    }

    @Nullable
    public CompoundNBT getTag() {
        return this.tag;
    }

    public CompoundNBT getOrCreateTag() {
        if (this.tag == null) {
            this.setTag(new CompoundNBT());
        }
        return this.tag;
    }

    public CompoundNBT getOrCreateChildTag(String string) {
        if (this.tag != null && this.tag.contains(string, 1)) {
            return this.tag.getCompound(string);
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        this.setTagInfo(string, compoundNBT);
        return compoundNBT;
    }

    @Nullable
    public CompoundNBT getChildTag(String string) {
        return this.tag != null && this.tag.contains(string, 1) ? this.tag.getCompound(string) : null;
    }

    public void removeChildTag(String string) {
        if (this.tag != null && this.tag.contains(string)) {
            this.tag.remove(string);
            if (this.tag.isEmpty()) {
                this.tag = null;
            }
        }
    }

    public ListNBT getEnchantmentTagList() {
        return this.tag != null ? this.tag.getList("Enchantments", 10) : new ListNBT();
    }

    public void setTag(@Nullable CompoundNBT compoundNBT) {
        this.tag = compoundNBT;
        if (this.getItem().isDamageable()) {
            this.setDamage(this.getDamage());
        }
    }

    public ITextComponent getDisplayName() {
        CompoundNBT compoundNBT = this.getChildTag("display");
        if (compoundNBT != null && compoundNBT.contains("Name", 1)) {
            try {
                IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(compoundNBT.getString("Name"));
                if (iFormattableTextComponent != null) {
                    return iFormattableTextComponent;
                }
                compoundNBT.remove("Name");
            } catch (JsonParseException jsonParseException) {
                compoundNBT.remove("Name");
            }
        }
        return this.getItem().getDisplayName(this);
    }

    public ItemStack setDisplayName(@Nullable ITextComponent iTextComponent) {
        CompoundNBT compoundNBT = this.getOrCreateChildTag("display");
        if (iTextComponent != null) {
            compoundNBT.putString("Name", ITextComponent.Serializer.toJson(iTextComponent));
        } else {
            compoundNBT.remove("Name");
        }
        return this;
    }

    public void clearCustomName() {
        CompoundNBT compoundNBT = this.getChildTag("display");
        if (compoundNBT != null) {
            compoundNBT.remove("Name");
            if (compoundNBT.isEmpty()) {
                this.removeChildTag("display");
            }
        }
        if (this.tag != null && this.tag.isEmpty()) {
            this.tag = null;
        }
    }

    public boolean hasDisplayName() {
        CompoundNBT compoundNBT = this.getChildTag("display");
        return compoundNBT != null && compoundNBT.contains("Name", 1);
    }

    public List<ITextComponent> getTooltip(@Nullable PlayerEntity playerEntity, ITooltipFlag iTooltipFlag) {
        Multimap<Attribute, AttributeModifier> multimap;
        Object object;
        int n;
        Object object2;
        int n2;
        ArrayList<ITextComponent> arrayList = Lists.newArrayList();
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").append(this.getDisplayName()).mergeStyle(this.getRarity().color);
        if (this.hasDisplayName()) {
            iFormattableTextComponent.mergeStyle(TextFormatting.ITALIC);
        }
        arrayList.add(iFormattableTextComponent);
        if (!iTooltipFlag.isAdvanced() && !this.hasDisplayName() && this.getItem() == Items.FILLED_MAP) {
            arrayList.add(new StringTextComponent("#" + FilledMapItem.getMapId(this)).mergeStyle(TextFormatting.GRAY));
        }
        if (ItemStack.func_242394_a(n2 = this.func_242393_J(), TooltipDisplayFlags.ADDITIONAL)) {
            this.getItem().addInformation(this, playerEntity == null ? null : playerEntity.world, arrayList, iTooltipFlag);
        }
        if (this.hasTag()) {
            if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.ENCHANTMENTS)) {
                ItemStack.addEnchantmentTooltips(arrayList, this.getEnchantmentTagList());
            }
            if (this.tag.contains("display", 1)) {
                object2 = this.tag.getCompound("display");
                if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.DYE) && ((CompoundNBT)object2).contains("color", 0)) {
                    if (iTooltipFlag.isAdvanced()) {
                        arrayList.add(new TranslationTextComponent("item.color", String.format("#%06X", ((CompoundNBT)object2).getInt("color"))).mergeStyle(TextFormatting.GRAY));
                    } else {
                        arrayList.add(new TranslationTextComponent("item.dyed").mergeStyle(TextFormatting.GRAY, TextFormatting.ITALIC));
                    }
                }
                if (((CompoundNBT)object2).getTagId("Lore") == 9) {
                    ListNBT listNBT = ((CompoundNBT)object2).getList("Lore", 8);
                    for (n = 0; n < listNBT.size(); ++n) {
                        object = listNBT.getString(n);
                        try {
                            multimap = ITextComponent.Serializer.getComponentFromJson((String)object);
                            if (multimap == null) continue;
                            arrayList.add(TextComponentUtils.func_240648_a_((IFormattableTextComponent)((Object)multimap), LORE_STYLE));
                            continue;
                        } catch (JsonParseException jsonParseException) {
                            ((CompoundNBT)object2).remove("Lore");
                        }
                    }
                }
            }
        }
        if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.MODIFIERS)) {
            object2 = EquipmentSlotType.values();
            int n3 = ((EquipmentSlotType[])object2).length;
            for (n = 0; n < n3; ++n) {
                object = object2[n];
                multimap = this.getAttributeModifiers((EquipmentSlotType)((Object)object));
                if (multimap.isEmpty()) continue;
                arrayList.add(StringTextComponent.EMPTY);
                arrayList.add(new TranslationTextComponent("item.modifiers." + object.getName()).mergeStyle(TextFormatting.GRAY));
                for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                    AttributeModifier attributeModifier = entry.getValue();
                    double d = attributeModifier.getAmount();
                    boolean bl = false;
                    if (playerEntity != null) {
                        if (attributeModifier.getID() == Item.ATTACK_DAMAGE_MODIFIER) {
                            d += playerEntity.getBaseAttributeValue(Attributes.ATTACK_DAMAGE);
                            d += (double)EnchantmentHelper.getModifierForCreature(this, CreatureAttribute.UNDEFINED);
                            bl = true;
                        } else if (attributeModifier.getID() == Item.ATTACK_SPEED_MODIFIER) {
                            d += playerEntity.getBaseAttributeValue(Attributes.ATTACK_SPEED);
                            bl = true;
                        }
                    }
                    double d2 = attributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL ? (entry.getKey().equals(Attributes.KNOCKBACK_RESISTANCE) ? d * 10.0 : d) : d * 100.0;
                    if (bl) {
                        arrayList.add(new StringTextComponent(" ").append(new TranslationTextComponent("attribute.modifier.equals." + attributeModifier.getOperation().getId(), DECIMALFORMAT.format(d2), new TranslationTextComponent(entry.getKey().getAttributeName()))).mergeStyle(TextFormatting.DARK_GREEN));
                        continue;
                    }
                    if (d > 0.0) {
                        arrayList.add(new TranslationTextComponent("attribute.modifier.plus." + attributeModifier.getOperation().getId(), DECIMALFORMAT.format(d2), new TranslationTextComponent(entry.getKey().getAttributeName())).mergeStyle(TextFormatting.BLUE));
                        continue;
                    }
                    if (!(d < 0.0)) continue;
                    arrayList.add(new TranslationTextComponent("attribute.modifier.take." + attributeModifier.getOperation().getId(), DECIMALFORMAT.format(d2 *= -1.0), new TranslationTextComponent(entry.getKey().getAttributeName())).mergeStyle(TextFormatting.RED));
                }
            }
        }
        if (this.hasTag()) {
            if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
                arrayList.add(new TranslationTextComponent("item.unbreakable").mergeStyle(TextFormatting.BLUE));
            }
            if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.CAN_DESTROY) && this.tag.contains("CanDestroy", 0) && !((ListNBT)(object2 = this.tag.getList("CanDestroy", 8))).isEmpty()) {
                arrayList.add(StringTextComponent.EMPTY);
                arrayList.add(new TranslationTextComponent("item.canBreak").mergeStyle(TextFormatting.GRAY));
                for (int i = 0; i < ((ListNBT)object2).size(); ++i) {
                    arrayList.addAll(ItemStack.getPlacementTooltip(((ListNBT)object2).getString(i)));
                }
            }
            if (ItemStack.func_242394_a(n2, TooltipDisplayFlags.CAN_PLACE) && this.tag.contains("CanPlaceOn", 0) && !((ListNBT)(object2 = this.tag.getList("CanPlaceOn", 8))).isEmpty()) {
                arrayList.add(StringTextComponent.EMPTY);
                arrayList.add(new TranslationTextComponent("item.canPlace").mergeStyle(TextFormatting.GRAY));
                for (int i = 0; i < ((ListNBT)object2).size(); ++i) {
                    arrayList.addAll(ItemStack.getPlacementTooltip(((ListNBT)object2).getString(i)));
                }
            }
        }
        if (iTooltipFlag.isAdvanced()) {
            if (this.isDamaged()) {
                arrayList.add(new TranslationTextComponent("item.durability", this.getMaxDamage() - this.getDamage(), this.getMaxDamage()));
            }
            arrayList.add(new StringTextComponent(Registry.ITEM.getKey(this.getItem()).toString()).mergeStyle(TextFormatting.DARK_GRAY));
            if (this.hasTag()) {
                arrayList.add(new TranslationTextComponent("item.nbt_tags", this.tag.keySet().size()).mergeStyle(TextFormatting.DARK_GRAY));
            }
        }
        return arrayList;
    }

    private static boolean func_242394_a(int n, TooltipDisplayFlags tooltipDisplayFlags) {
        return (n & tooltipDisplayFlags.func_242397_a()) == 0;
    }

    private int func_242393_J() {
        return this.hasTag() && this.tag.contains("HideFlags", 0) ? this.tag.getInt("HideFlags") : 0;
    }

    public void func_242395_a(TooltipDisplayFlags tooltipDisplayFlags) {
        CompoundNBT compoundNBT = this.getOrCreateTag();
        compoundNBT.putInt("HideFlags", compoundNBT.getInt("HideFlags") | tooltipDisplayFlags.func_242397_a());
    }

    public static void addEnchantmentTooltips(List<ITextComponent> list, ListNBT listNBT) {
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            Registry.ENCHANTMENT.getOptional(ResourceLocation.tryCreate(compoundNBT.getString("id"))).ifPresent(arg_0 -> ItemStack.lambda$addEnchantmentTooltips$5(list, compoundNBT, arg_0));
        }
    }

    private static Collection<ITextComponent> getPlacementTooltip(String string) {
        try {
            boolean bl;
            BlockStateParser blockStateParser = new BlockStateParser(new StringReader(string), true).parse(false);
            BlockState blockState = blockStateParser.getState();
            ResourceLocation resourceLocation = blockStateParser.getTag();
            boolean bl2 = blockState != null;
            boolean bl3 = bl = resourceLocation != null;
            if (bl2 || bl) {
                List<Block> list;
                if (bl2) {
                    return Lists.newArrayList(blockState.getBlock().getTranslatedName().mergeStyle(TextFormatting.DARK_GRAY));
                }
                ITag<Block> iTag = BlockTags.getCollection().get(resourceLocation);
                if (iTag != null && !(list = iTag.getAllElements()).isEmpty()) {
                    return list.stream().map(Block::getTranslatedName).map(ItemStack::lambda$getPlacementTooltip$6).collect(Collectors.toList());
                }
            }
        } catch (CommandSyntaxException commandSyntaxException) {
            // empty catch block
        }
        return Lists.newArrayList(new StringTextComponent("missingno").mergeStyle(TextFormatting.DARK_GRAY));
    }

    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }

    public Rarity getRarity() {
        return this.getItem().getRarity(this);
    }

    public boolean isEnchantable() {
        if (!this.getItem().isEnchantable(this)) {
            return true;
        }
        return !this.isEnchanted();
    }

    public void addEnchantment(Enchantment enchantment, int n) {
        this.getOrCreateTag();
        if (!this.tag.contains("Enchantments", 0)) {
            this.tag.put("Enchantments", new ListNBT());
        }
        ListNBT listNBT = this.tag.getList("Enchantments", 10);
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("id", String.valueOf(Registry.ENCHANTMENT.getKey(enchantment)));
        compoundNBT.putShort("lvl", (byte)n);
        listNBT.add(compoundNBT);
    }

    public boolean isEnchanted() {
        if (this.tag != null && this.tag.contains("Enchantments", 0)) {
            return !this.tag.getList("Enchantments", 10).isEmpty();
        }
        return true;
    }

    public void setTagInfo(String string, INBT iNBT) {
        this.getOrCreateTag().put(string, iNBT);
    }

    public boolean isOnItemFrame() {
        return this.attachedEntity instanceof ItemFrameEntity;
    }

    public void setAttachedEntity(@Nullable Entity entity2) {
        this.attachedEntity = entity2;
    }

    @Nullable
    public ItemFrameEntity getItemFrame() {
        return this.attachedEntity instanceof ItemFrameEntity ? (ItemFrameEntity)this.getAttachedEntity() : null;
    }

    @Nullable
    public Entity getAttachedEntity() {
        return !this.isEmpty ? this.attachedEntity : null;
    }

    public int getRepairCost() {
        return this.hasTag() && this.tag.contains("RepairCost", 0) ? this.tag.getInt("RepairCost") : 0;
    }

    public void setRepairCost(int n) {
        this.getOrCreateTag().putInt("RepairCost", n);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        Multimap<Attribute, AttributeModifier> multimap;
        if (this.hasTag() && this.tag.contains("AttributeModifiers", 0)) {
            multimap = HashMultimap.create();
            ListNBT listNBT = this.tag.getList("AttributeModifiers", 10);
            for (int i = 0; i < listNBT.size(); ++i) {
                AttributeModifier attributeModifier;
                Optional<Attribute> optional;
                CompoundNBT compoundNBT = listNBT.getCompound(i);
                if (compoundNBT.contains("Slot", 1) && !compoundNBT.getString("Slot").equals(equipmentSlotType.getName()) || !(optional = Registry.ATTRIBUTE.getOptional(ResourceLocation.tryCreate(compoundNBT.getString("AttributeName")))).isPresent() || (attributeModifier = AttributeModifier.read(compoundNBT)) == null || attributeModifier.getID().getLeastSignificantBits() == 0L || attributeModifier.getID().getMostSignificantBits() == 0L) continue;
                multimap.put(optional.get(), attributeModifier);
            }
        } else {
            multimap = this.getItem().getAttributeModifiers(equipmentSlotType);
        }
        return multimap;
    }

    public void addAttributeModifier(Attribute attribute, AttributeModifier attributeModifier, @Nullable EquipmentSlotType equipmentSlotType) {
        this.getOrCreateTag();
        if (!this.tag.contains("AttributeModifiers", 0)) {
            this.tag.put("AttributeModifiers", new ListNBT());
        }
        ListNBT listNBT = this.tag.getList("AttributeModifiers", 10);
        CompoundNBT compoundNBT = attributeModifier.write();
        compoundNBT.putString("AttributeName", Registry.ATTRIBUTE.getKey(attribute).toString());
        if (equipmentSlotType != null) {
            compoundNBT.putString("Slot", equipmentSlotType.getName());
        }
        listNBT.add(compoundNBT);
    }

    public ITextComponent getTextComponent() {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").append(this.getDisplayName());
        if (this.hasDisplayName()) {
            iFormattableTextComponent.mergeStyle(TextFormatting.ITALIC);
        }
        IFormattableTextComponent iFormattableTextComponent2 = TextComponentUtils.wrapWithSquareBrackets(iFormattableTextComponent);
        if (!this.isEmpty) {
            iFormattableTextComponent2.mergeStyle(this.getRarity().color).modifyStyle(this::lambda$getTextComponent$7);
        }
        return iFormattableTextComponent2;
    }

    private static boolean isStateAndTileEntityEqual(CachedBlockInfo cachedBlockInfo, @Nullable CachedBlockInfo cachedBlockInfo2) {
        if (cachedBlockInfo2 != null && cachedBlockInfo.getBlockState() == cachedBlockInfo2.getBlockState()) {
            if (cachedBlockInfo.getTileEntity() == null && cachedBlockInfo2.getTileEntity() == null) {
                return false;
            }
            return cachedBlockInfo.getTileEntity() != null && cachedBlockInfo2.getTileEntity() != null ? Objects.equals(cachedBlockInfo.getTileEntity().write(new CompoundNBT()), cachedBlockInfo2.getTileEntity().write(new CompoundNBT())) : false;
        }
        return true;
    }

    public boolean canDestroy(ITagCollectionSupplier iTagCollectionSupplier, CachedBlockInfo cachedBlockInfo) {
        if (ItemStack.isStateAndTileEntityEqual(cachedBlockInfo, this.canDestroyCacheBlock)) {
            return this.canDestroyCacheResult;
        }
        this.canDestroyCacheBlock = cachedBlockInfo;
        if (this.hasTag() && this.tag.contains("CanDestroy", 0)) {
            ListNBT listNBT = this.tag.getList("CanDestroy", 8);
            for (int i = 0; i < listNBT.size(); ++i) {
                String string = listNBT.getString(i);
                try {
                    Predicate<CachedBlockInfo> predicate = BlockPredicateArgument.blockPredicate().parse(new StringReader(string)).create(iTagCollectionSupplier);
                    if (predicate.test(cachedBlockInfo)) {
                        this.canDestroyCacheResult = true;
                        return true;
                    }
                    continue;
                } catch (CommandSyntaxException commandSyntaxException) {
                    // empty catch block
                }
            }
        }
        this.canDestroyCacheResult = false;
        return true;
    }

    public boolean canPlaceOn(ITagCollectionSupplier iTagCollectionSupplier, CachedBlockInfo cachedBlockInfo) {
        if (ItemStack.isStateAndTileEntityEqual(cachedBlockInfo, this.canPlaceOnCacheBlock)) {
            return this.canPlaceOnCacheResult;
        }
        this.canPlaceOnCacheBlock = cachedBlockInfo;
        if (this.hasTag() && this.tag.contains("CanPlaceOn", 0)) {
            ListNBT listNBT = this.tag.getList("CanPlaceOn", 8);
            for (int i = 0; i < listNBT.size(); ++i) {
                String string = listNBT.getString(i);
                try {
                    Predicate<CachedBlockInfo> predicate = BlockPredicateArgument.blockPredicate().parse(new StringReader(string)).create(iTagCollectionSupplier);
                    if (predicate.test(cachedBlockInfo)) {
                        this.canPlaceOnCacheResult = true;
                        return true;
                    }
                    continue;
                } catch (CommandSyntaxException commandSyntaxException) {
                    // empty catch block
                }
            }
        }
        this.canPlaceOnCacheResult = false;
        return true;
    }

    public int getAnimationsToGo() {
        return this.animationsToGo;
    }

    public void setAnimationsToGo(int n) {
        this.animationsToGo = n;
    }

    public int getCount() {
        return this.isEmpty ? 0 : this.count;
    }

    public void setCount(int n) {
        this.count = n;
        this.updateEmptyState();
    }

    public void grow(int n) {
        this.setCount(this.count + n);
    }

    public void shrink(int n) {
        this.grow(-n);
    }

    public void onItemUsed(World world, LivingEntity livingEntity, int n) {
        this.getItem().onUse(world, livingEntity, this, n);
    }

    public boolean isFood() {
        return this.getItem().isFood();
    }

    public SoundEvent getDrinkSound() {
        return this.getItem().getDrinkSound();
    }

    public SoundEvent getEatSound() {
        return this.getItem().getEatSound();
    }

    private Style lambda$getTextComponent$7(Style style) {
        return style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemHover(this)));
    }

    private static IFormattableTextComponent lambda$getPlacementTooltip$6(IFormattableTextComponent iFormattableTextComponent) {
        return iFormattableTextComponent.mergeStyle(TextFormatting.DARK_GRAY);
    }

    private static void lambda$addEnchantmentTooltips$5(List list, CompoundNBT compoundNBT, Enchantment enchantment) {
        list.add(enchantment.getDisplayName(compoundNBT.getInt("lvl")));
    }

    private static void lambda$static$4(DecimalFormat decimalFormat) {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }

    private static App lambda$static$3(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)Registry.ITEM.fieldOf("id")).forGetter(ItemStack::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("Count")).forGetter(ItemStack::lambda$static$1), CompoundNBT.CODEC.optionalFieldOf("tag").forGetter(ItemStack::lambda$static$2)).apply(instance, ItemStack::new);
    }

    private static Optional lambda$static$2(ItemStack itemStack) {
        return Optional.ofNullable(itemStack.tag);
    }

    private static Integer lambda$static$1(ItemStack itemStack) {
        return itemStack.count;
    }

    private static Item lambda$static$0(ItemStack itemStack) {
        return itemStack.item;
    }

    public static enum TooltipDisplayFlags {
        ENCHANTMENTS,
        MODIFIERS,
        UNBREAKABLE,
        CAN_DESTROY,
        CAN_PLACE,
        ADDITIONAL,
        DYE;

        private final int field_242396_h = 1 << this.ordinal();

        public int func_242397_a() {
            return this.field_242396_h;
        }
    }
}

