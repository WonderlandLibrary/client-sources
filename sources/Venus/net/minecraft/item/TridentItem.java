/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class TridentItem
extends Item
implements IVanishable {
    private final Multimap<Attribute, AttributeModifier> tridentAttributes;

    public TridentItem(Item.Properties properties) {
        super(properties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", 8.0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)-2.9f, AttributeModifier.Operation.ADDITION));
        this.tridentAttributes = builder.build();
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity) {
        return !playerEntity.isCreative();
    }

    @Override
    public UseAction getUseAction(ItemStack itemStack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 1;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int n) {
        if (livingEntity instanceof PlayerEntity) {
            int n2;
            PlayerEntity playerEntity = (PlayerEntity)livingEntity;
            int n3 = this.getUseDuration(itemStack) - n;
            if (n3 >= 10 && ((n2 = EnchantmentHelper.getRiptideModifier(itemStack)) <= 0 || playerEntity.isWet())) {
                if (!world.isRemote) {
                    itemStack.damageItem(1, playerEntity, arg_0 -> TridentItem.lambda$onPlayerStoppedUsing$0(livingEntity, arg_0));
                    if (n2 == 0) {
                        TridentEntity tridentEntity = new TridentEntity(world, (LivingEntity)playerEntity, itemStack);
                        tridentEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0f, 2.5f + (float)n2 * 0.5f, 1.0f);
                        if (playerEntity.abilities.isCreativeMode) {
                            tridentEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }
                        world.addEntity(tridentEntity);
                        world.playMovingSound(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        if (!playerEntity.abilities.isCreativeMode) {
                            playerEntity.inventory.deleteStack(itemStack);
                        }
                    }
                }
                playerEntity.addStat(Stats.ITEM_USED.get(this));
                if (n2 > 0) {
                    float f = playerEntity.rotationYaw;
                    float f2 = playerEntity.rotationPitch;
                    float f3 = -MathHelper.sin(f * ((float)Math.PI / 180)) * MathHelper.cos(f2 * ((float)Math.PI / 180));
                    float f4 = -MathHelper.sin(f2 * ((float)Math.PI / 180));
                    float f5 = MathHelper.cos(f * ((float)Math.PI / 180)) * MathHelper.cos(f2 * ((float)Math.PI / 180));
                    float f6 = MathHelper.sqrt(f3 * f3 + f4 * f4 + f5 * f5);
                    float f7 = 3.0f * ((1.0f + (float)n2) / 4.0f);
                    playerEntity.addVelocity(f3 *= f7 / f6, f4 *= f7 / f6, f5 *= f7 / f6);
                    playerEntity.startSpinAttack(20);
                    if (playerEntity.isOnGround()) {
                        float f8 = 1.1999999f;
                        playerEntity.move(MoverType.SELF, new Vector3d(0.0, 1.1999999284744263, 0.0));
                    }
                    SoundEvent soundEvent = n2 >= 3 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_3 : (n2 == 2 ? SoundEvents.ITEM_TRIDENT_RIPTIDE_2 : SoundEvents.ITEM_TRIDENT_RIPTIDE_1);
                    world.playMovingSound(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return ActionResult.resultFail(itemStack);
        }
        if (EnchantmentHelper.getRiptideModifier(itemStack) > 0 && !playerEntity.isWet()) {
            return ActionResult.resultFail(itemStack);
        }
        playerEntity.setActiveHand(hand);
        return ActionResult.resultConsume(itemStack);
    }

    @Override
    public boolean hitEntity(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.damageItem(1, livingEntity2, TridentItem::lambda$hitEntity$1);
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        if ((double)blockState.getBlockHardness(world, blockPos) != 0.0) {
            itemStack.damageItem(2, livingEntity, TridentItem::lambda$onBlockDestroyed$2);
        }
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == EquipmentSlotType.MAINHAND ? this.tridentAttributes : super.getAttributeModifiers(equipmentSlotType);
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    private static void lambda$onBlockDestroyed$2(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }

    private static void lambda$hitEntity$1(LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND);
    }

    private static void lambda$onPlayerStoppedUsing$0(LivingEntity livingEntity, PlayerEntity playerEntity) {
        playerEntity.sendBreakAnimation(livingEntity.getActiveHand());
    }
}

