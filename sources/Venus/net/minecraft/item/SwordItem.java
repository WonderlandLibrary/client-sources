/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SwordItem
extends TieredItem
implements IVanishable {
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public SwordItem(IItemTier iItemTier, int n, float f, Item.Properties properties) {
        super(iItemTier, properties);
        this.attackDamage = (float)n + iItemTier.getAttackDamage();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)f, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return !playerEntity.isCreative();
    }

    @Override
    public float getDestroySpeed(ItemStack itemStack, BlockState blockState) {
        if (blockState.isIn(Blocks.COBWEB)) {
            return 15.0f;
        }
        Material material = blockState.getMaterial();
        return material != Material.PLANTS && material != Material.TALL_PLANTS && material != Material.CORAL && !blockState.isIn(BlockTags.LEAVES) && material != Material.GOURD ? 1.0f : 1.5f;
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.damageItem(1, livingEntity2, SwordItem::lambda$hitEntity$0);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if (blockState.getBlockHardness(world, blockPos) != 0.0f) {
            itemStack.damageItem(2, livingEntity, SwordItem::lambda$onBlockDestroyed$1);
        }
        return false;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockState) {
        return blockState.isIn(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == EquipmentSlotType.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(equipmentSlotType);
    }

    private static void lambda$onBlockDestroyed$1(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }

    private static void lambda$hitEntity$0(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }
}

