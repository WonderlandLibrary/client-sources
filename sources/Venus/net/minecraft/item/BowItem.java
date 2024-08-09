/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import java.util.function.Predicate;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class BowItem
extends ShootableItem
implements IVanishable {
    public BowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int n) {
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)livingEntity;
            boolean bl = playerEntity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemStack) > 0;
            ItemStack itemStack2 = playerEntity.findAmmo(itemStack);
            if (!itemStack2.isEmpty() || bl) {
                int n2;
                float f;
                if (itemStack2.isEmpty()) {
                    itemStack2 = new ItemStack(Items.ARROW);
                }
                if (!((double)(f = BowItem.getArrowVelocity(n2 = this.getUseDuration(itemStack) - n)) < 0.1)) {
                    boolean bl2;
                    boolean bl3 = bl2 = bl && itemStack2.getItem() == Items.ARROW;
                    if (!world.isRemote) {
                        int n3;
                        int n4;
                        ArrowItem arrowItem = (ArrowItem)(itemStack2.getItem() instanceof ArrowItem ? itemStack2.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, itemStack2, playerEntity);
                        abstractArrowEntity.func_234612_a_(playerEntity, playerEntity.rotationPitch, playerEntity.rotationYaw, 0.0f, f * 3.0f, 1.0f);
                        if (f == 1.0f) {
                            abstractArrowEntity.setIsCritical(false);
                        }
                        if ((n4 = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemStack)) > 0) {
                            abstractArrowEntity.setDamage(abstractArrowEntity.getDamage() + (double)n4 * 0.5 + 0.5);
                        }
                        if ((n3 = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemStack)) > 0) {
                            abstractArrowEntity.setKnockbackStrength(n3);
                        }
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemStack) > 0) {
                            abstractArrowEntity.setFire(100);
                        }
                        itemStack.damageItem(1, playerEntity, arg_0 -> BowItem.lambda$onPlayerStoppedUsing$0(playerEntity, arg_0));
                        if (bl2 || playerEntity.abilities.isCreativeMode && (itemStack2.getItem() == Items.SPECTRAL_ARROW || itemStack2.getItem() == Items.TIPPED_ARROW)) {
                            abstractArrowEntity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }
                        world.addEntity(abstractArrowEntity);
                    }
                    world.playSound(null, playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 1.2f) + f * 0.5f);
                    if (!bl2 && !playerEntity.abilities.isCreativeMode) {
                        itemStack2.shrink(1);
                        if (itemStack2.isEmpty()) {
                            playerEntity.inventory.deleteStack(itemStack2);
                        }
                    }
                    playerEntity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public static float getArrowVelocity(int n) {
        float f = (float)n / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 1;
    }

    @Override
    public UseAction getUseAction(ItemStack itemStack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        boolean bl;
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        boolean bl2 = bl = !playerEntity.findAmmo(itemStack).isEmpty();
        if (!playerEntity.abilities.isCreativeMode && !bl) {
            return ActionResult.resultFail(itemStack);
        }
        playerEntity.setActiveHand(hand);
        return ActionResult.resultConsume(itemStack);
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return ARROWS;
    }

    @Override
    public int func_230305_d_() {
        return 0;
    }

    private static void lambda$onPlayerStoppedUsing$0(PlayerEntity playerEntity, PlayerEntity playerEntity2) {
        playerEntity2.sendBreakAnimation(playerEntity.getActiveHand());
    }
}

