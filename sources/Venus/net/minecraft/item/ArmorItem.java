/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArmorItem
extends Item
implements IArmorVanishable {
    private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
    public static final IDispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior(){

        @Override
        protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
            return ArmorItem.func_226626_a_(iBlockSource, itemStack) ? itemStack : super.dispenseStack(iBlockSource, itemStack);
        }
    };
    protected final EquipmentSlotType slot;
    private final int damageReduceAmount;
    private final float toughness;
    protected final float knockbackResistance;
    protected final IArmorMaterial material;
    private final Multimap<Attribute, AttributeModifier> field_234656_m_;

    public static boolean func_226626_a_(IBlockSource iBlockSource, ItemStack itemStack) {
        BlockPos blockPos = iBlockSource.getBlockPos().offset(iBlockSource.getBlockState().get(DispenserBlock.FACING));
        List<Entity> list = iBlockSource.getWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(blockPos), EntityPredicates.NOT_SPECTATING.and(new EntityPredicates.ArmoredMob(itemStack)));
        if (list.isEmpty()) {
            return true;
        }
        LivingEntity livingEntity = (LivingEntity)list.get(0);
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack2 = itemStack.split(1);
        livingEntity.setItemStackToSlot(equipmentSlotType, itemStack2);
        if (livingEntity instanceof MobEntity) {
            ((MobEntity)livingEntity).setDropChance(equipmentSlotType, 2.0f);
            ((MobEntity)livingEntity).enablePersistence();
        }
        return false;
    }

    public ArmorItem(IArmorMaterial iArmorMaterial, EquipmentSlotType equipmentSlotType, Item.Properties properties) {
        super(properties.defaultMaxDamage(iArmorMaterial.getDurability(equipmentSlotType)));
        this.material = iArmorMaterial;
        this.slot = equipmentSlotType;
        this.damageReduceAmount = iArmorMaterial.getDamageReductionAmount(equipmentSlotType);
        this.toughness = iArmorMaterial.getToughness();
        this.knockbackResistance = iArmorMaterial.getKnockbackResistance();
        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = ARMOR_MODIFIERS[equipmentSlotType.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uUID, "Armor modifier", (double)this.damageReduceAmount, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uUID, "Armor toughness", (double)this.toughness, AttributeModifier.Operation.ADDITION));
        if (iArmorMaterial == ArmorMaterial.NETHERITE) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uUID, "Armor knockback resistance", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }
        this.field_234656_m_ = builder.build();
    }

    public EquipmentSlotType getEquipmentSlot() {
        return this.slot;
    }

    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }

    public IArmorMaterial getArmorMaterial() {
        return this.material;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2) {
        return this.material.getRepairMaterial().test(itemStack2) || super.getIsRepairable(itemStack, itemStack2);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack2 = playerEntity.getItemStackFromSlot(equipmentSlotType);
        if (itemStack2.isEmpty()) {
            playerEntity.setItemStackToSlot(equipmentSlotType, itemStack.copy());
            itemStack.setCount(0);
            return ActionResult.func_233538_a_(itemStack, world.isRemote());
        }
        return ActionResult.resultFail(itemStack);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == this.slot ? this.field_234656_m_ : super.getAttributeModifiers(equipmentSlotType);
    }

    public int getDamageReduceAmount() {
        return this.damageReduceAmount;
    }

    public float func_234657_f_() {
        return this.toughness;
    }

    public EquipmentSlotType getSlot() {
        return this.slot;
    }

    public float getToughness() {
        return this.toughness;
    }
}

