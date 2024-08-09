/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToolItem
extends TieredItem
implements IVanishable {
    private final Set<Block> effectiveBlocks;
    protected final float efficiency;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> toolAttributes;

    protected ToolItem(float f, float f2, IItemTier iItemTier, Set<Block> set, Item.Properties properties) {
        super(iItemTier, properties);
        this.effectiveBlocks = set;
        this.efficiency = iItemTier.getEfficiency();
        this.attackDamage = f + iItemTier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)f2, AttributeModifier.Operation.ADDITION));
        this.toolAttributes = builder.build();
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        return this.effectiveBlocks.contains(blockState.getBlock()) ? this.efficiency : 1.0f;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.damageItem(2, livingEntity2, ToolItem::lambda$hitEntity$0);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (!world.isRemote && blockState.getBlockHardness(world, blockPos) != 0.0f) {
            itemStack.damageItem(1, livingEntity, ToolItem::lambda$onBlockDestroyed$1);
        }
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == EquipmentSlotType.MAINHAND ? this.toolAttributes : super.getAttributeModifiers(equipmentSlotType);
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    private static void lambda$onBlockDestroyed$1(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }

    private static void lambda$hitEntity$0(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }
}

