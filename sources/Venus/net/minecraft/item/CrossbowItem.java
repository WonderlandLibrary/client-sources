/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShootableItem;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CrossbowItem
extends ShootableItem
implements IVanishable {
    private boolean isLoadingStart = false;
    private boolean isLoadingMiddle = false;

    public CrossbowItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAmmoPredicate() {
        return ARROWS_OR_FIREWORKS;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return ARROWS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        if (CrossbowItem.isCharged(itemStack)) {
            CrossbowItem.fireProjectiles(world, playerEntity, hand, itemStack, CrossbowItem.func_220013_l(itemStack), 1.0f);
            CrossbowItem.setCharged(itemStack, false);
            return ActionResult.resultConsume(itemStack);
        }
        if (!playerEntity.findAmmo(itemStack).isEmpty()) {
            if (!CrossbowItem.isCharged(itemStack)) {
                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
                playerEntity.setActiveHand(hand);
            }
            return ActionResult.resultConsume(itemStack);
        }
        return ActionResult.resultFail(itemStack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int n) {
        int n2 = this.getUseDuration(itemStack) - n;
        float f = CrossbowItem.getCharge(n2, itemStack);
        if (f >= 1.0f && !CrossbowItem.isCharged(itemStack) && CrossbowItem.hasAmmo(livingEntity, itemStack)) {
            CrossbowItem.setCharged(itemStack, true);
            SoundCategory soundCategory = livingEntity instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
            world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, soundCategory, 1.0f, 1.0f / (random.nextFloat() * 0.5f + 1.0f) + 0.2f);
        }
    }

    private static boolean hasAmmo(LivingEntity livingEntity, ItemStack itemStack) {
        int n = EnchantmentHelper.getEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
        int n2 = n == 0 ? 1 : 3;
        boolean bl = livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.isCreativeMode;
        ItemStack itemStack2 = livingEntity.findAmmo(itemStack);
        ItemStack itemStack3 = itemStack2.copy();
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                itemStack2 = itemStack3.copy();
            }
            if (itemStack2.isEmpty() && bl) {
                itemStack2 = new ItemStack(Items.ARROW);
                itemStack3 = itemStack2.copy();
            }
            if (CrossbowItem.func_220023_a(livingEntity, itemStack, itemStack2, i > 0, bl)) continue;
            return true;
        }
        return false;
    }

    private static boolean func_220023_a(LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2, boolean bl, boolean bl2) {
        ItemStack itemStack3;
        boolean bl3;
        if (itemStack2.isEmpty()) {
            return true;
        }
        boolean bl4 = bl3 = bl2 && itemStack2.getItem() instanceof ArrowItem;
        if (!(bl3 || bl2 || bl)) {
            itemStack3 = itemStack2.split(1);
            if (itemStack2.isEmpty() && livingEntity instanceof PlayerEntity) {
                ((PlayerEntity)livingEntity).inventory.deleteStack(itemStack2);
            }
        } else {
            itemStack3 = itemStack2.copy();
        }
        CrossbowItem.addChargedProjectile(itemStack, itemStack3);
        return false;
    }

    public static boolean isCharged(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        return compoundNBT != null && compoundNBT.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean bl) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        compoundNBT.putBoolean("Charged", bl);
    }

    private static void addChargedProjectile(ItemStack itemStack, ItemStack itemStack2) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        ListNBT listNBT = compoundNBT.contains("ChargedProjectiles", 0) ? compoundNBT.getList("ChargedProjectiles", 10) : new ListNBT();
        CompoundNBT compoundNBT2 = new CompoundNBT();
        itemStack2.write(compoundNBT2);
        listNBT.add(compoundNBT2);
        compoundNBT.put("ChargedProjectiles", listNBT);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack itemStack) {
        ListNBT listNBT;
        ArrayList<ItemStack> arrayList = Lists.newArrayList();
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null && compoundNBT.contains("ChargedProjectiles", 0) && (listNBT = compoundNBT.getList("ChargedProjectiles", 10)) != null) {
            for (int i = 0; i < listNBT.size(); ++i) {
                CompoundNBT compoundNBT2 = listNBT.getCompound(i);
                arrayList.add(ItemStack.read(compoundNBT2));
            }
        }
        return arrayList;
    }

    private static void clearProjectiles(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT != null) {
            ListNBT listNBT = compoundNBT.getList("ChargedProjectiles", 9);
            listNBT.clear();
            compoundNBT.put("ChargedProjectiles", listNBT);
        }
    }

    public static boolean hasChargedProjectile(ItemStack itemStack, Item item) {
        return CrossbowItem.getChargedProjectiles(itemStack).stream().anyMatch(arg_0 -> CrossbowItem.lambda$hasChargedProjectile$0(item, arg_0));
    }

    private static void fireProjectile(World world, LivingEntity livingEntity, Hand hand, ItemStack itemStack, ItemStack itemStack2, float f, boolean bl, float f2, float f3, float f4) {
        if (!world.isRemote) {
            ProjectileEntity projectileEntity;
            boolean bl2;
            boolean bl3 = bl2 = itemStack2.getItem() == Items.FIREWORK_ROCKET;
            if (bl2) {
                projectileEntity = new FireworkRocketEntity(world, itemStack2, livingEntity, livingEntity.getPosX(), livingEntity.getPosYEye() - (double)0.15f, livingEntity.getPosZ(), true);
            } else {
                projectileEntity = CrossbowItem.createArrow(world, livingEntity, itemStack, itemStack2);
                if (bl || f4 != 0.0f) {
                    ((AbstractArrowEntity)projectileEntity).pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                }
            }
            if (livingEntity instanceof ICrossbowUser) {
                ICrossbowUser iCrossbowUser = (ICrossbowUser)((Object)livingEntity);
                iCrossbowUser.func_230284_a_(iCrossbowUser.getAttackTarget(), itemStack, projectileEntity, f4);
            } else {
                Vector3d vector3d = livingEntity.getUpVector(1.0f);
                Quaternion quaternion = new Quaternion(new Vector3f(vector3d), f4, true);
                Vector3d vector3d2 = livingEntity.getLook(1.0f);
                Vector3f vector3f = new Vector3f(vector3d2);
                vector3f.transform(quaternion);
                projectileEntity.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), f2, f3);
            }
            itemStack.damageItem(bl2 ? 3 : 1, livingEntity, arg_0 -> CrossbowItem.lambda$fireProjectile$1(hand, arg_0));
            world.addEntity(projectileEntity);
            world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0f, f);
        }
    }

    private static AbstractArrowEntity createArrow(World world, LivingEntity livingEntity, ItemStack itemStack, ItemStack itemStack2) {
        ArrowItem arrowItem = (ArrowItem)(itemStack2.getItem() instanceof ArrowItem ? itemStack2.getItem() : Items.ARROW);
        AbstractArrowEntity abstractArrowEntity = arrowItem.createArrow(world, itemStack2, livingEntity);
        if (livingEntity instanceof PlayerEntity) {
            abstractArrowEntity.setIsCritical(false);
        }
        abstractArrowEntity.setHitSound(SoundEvents.ITEM_CROSSBOW_HIT);
        abstractArrowEntity.setShotFromCrossbow(false);
        int n = EnchantmentHelper.getEnchantmentLevel(Enchantments.PIERCING, itemStack);
        if (n > 0) {
            abstractArrowEntity.setPierceLevel((byte)n);
        }
        return abstractArrowEntity;
    }

    public static void fireProjectiles(World world, LivingEntity livingEntity, Hand hand, ItemStack itemStack, float f, float f2) {
        List<ItemStack> list = CrossbowItem.getChargedProjectiles(itemStack);
        float[] fArray = CrossbowItem.getRandomSoundPitches(livingEntity.getRNG());
        for (int i = 0; i < list.size(); ++i) {
            boolean bl;
            ItemStack itemStack2 = list.get(i);
            boolean bl2 = bl = livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.isCreativeMode;
            if (itemStack2.isEmpty()) continue;
            if (i == 0) {
                CrossbowItem.fireProjectile(world, livingEntity, hand, itemStack, itemStack2, fArray[i], bl, f, f2, 0.0f);
                continue;
            }
            if (i == 1) {
                CrossbowItem.fireProjectile(world, livingEntity, hand, itemStack, itemStack2, fArray[i], bl, f, f2, -10.0f);
                continue;
            }
            if (i != 2) continue;
            CrossbowItem.fireProjectile(world, livingEntity, hand, itemStack, itemStack2, fArray[i], bl, f, f2, 10.0f);
        }
        CrossbowItem.fireProjectilesAfter(world, livingEntity, itemStack);
    }

    private static float[] getRandomSoundPitches(Random random2) {
        boolean bl = random2.nextBoolean();
        return new float[]{1.0f, CrossbowItem.getRandomSoundPitch(bl), CrossbowItem.getRandomSoundPitch(!bl)};
    }

    private static float getRandomSoundPitch(boolean bl) {
        float f = bl ? 0.63f : 0.43f;
        return 1.0f / (random.nextFloat() * 0.5f + 1.8f) + f;
    }

    private static void fireProjectilesAfter(World world, LivingEntity livingEntity, ItemStack itemStack) {
        if (livingEntity instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)livingEntity;
            if (!world.isRemote) {
                CriteriaTriggers.SHOT_CROSSBOW.test(serverPlayerEntity, itemStack);
            }
            serverPlayerEntity.addStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }
        CrossbowItem.clearProjectiles(itemStack);
    }

    @Override
    public void onUse(World world, LivingEntity livingEntity, ItemStack itemStack, int n) {
        if (!world.isRemote) {
            int n2 = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
            SoundEvent soundEvent = this.getSoundEvent(n2);
            SoundEvent soundEvent2 = n2 == 0 ? SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(itemStack.getUseDuration() - n) / (float)CrossbowItem.getChargeTime(itemStack);
            if (f < 0.2f) {
                this.isLoadingStart = false;
                this.isLoadingMiddle = false;
            }
            if (f >= 0.2f && !this.isLoadingStart) {
                this.isLoadingStart = true;
                world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), soundEvent, SoundCategory.PLAYERS, 0.5f, 1.0f);
            }
            if (f >= 0.5f && soundEvent2 != null && !this.isLoadingMiddle) {
                this.isLoadingMiddle = true;
                world.playSound(null, livingEntity.getPosX(), livingEntity.getPosY(), livingEntity.getPosZ(), soundEvent2, SoundCategory.PLAYERS, 0.5f, 1.0f);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return CrossbowItem.getChargeTime(itemStack) + 3;
    }

    public static int getChargeTime(ItemStack itemStack) {
        int n = EnchantmentHelper.getEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
        return n == 0 ? 25 : 25 - 5 * n;
    }

    @Override
    public UseAction getUseAction(ItemStack itemStack) {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getSoundEvent(int n) {
        switch (n) {
            case 1: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1;
            }
            case 2: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2;
            }
            case 3: {
                return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3;
            }
        }
        return SoundEvents.ITEM_CROSSBOW_LOADING_START;
    }

    private static float getCharge(int n, ItemStack itemStack) {
        float f = (float)n / (float)CrossbowItem.getChargeTime(itemStack);
        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> list, ITooltipFlag iTooltipFlag) {
        List<ItemStack> list2 = CrossbowItem.getChargedProjectiles(itemStack);
        if (CrossbowItem.isCharged(itemStack) && !list2.isEmpty()) {
            ItemStack itemStack2 = list2.get(0);
            list.add(new TranslationTextComponent("item.minecraft.crossbow.projectile").appendString(" ").append(itemStack2.getTextComponent()));
            if (iTooltipFlag.isAdvanced() && itemStack2.getItem() == Items.FIREWORK_ROCKET) {
                ArrayList<ITextComponent> arrayList = Lists.newArrayList();
                Items.FIREWORK_ROCKET.addInformation(itemStack2, world, arrayList, iTooltipFlag);
                if (!arrayList.isEmpty()) {
                    for (int i = 0; i < arrayList.size(); ++i) {
                        arrayList.set(i, new StringTextComponent("  ").append((ITextComponent)arrayList.get(i)).mergeStyle(TextFormatting.GRAY));
                    }
                    list.addAll(arrayList);
                }
            }
        }
    }

    private static float func_220013_l(ItemStack itemStack) {
        return itemStack.getItem() == Items.CROSSBOW && CrossbowItem.hasChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? 1.6f : 3.15f;
    }

    @Override
    public int func_230305_d_() {
        return 1;
    }

    private static void lambda$fireProjectile$1(Hand hand, LivingEntity livingEntity) {
        livingEntity.sendBreakAnimation(hand);
    }

    private static boolean lambda$hasChargedProjectile$0(Item item, ItemStack itemStack) {
        return itemStack.getItem() == item;
    }
}

