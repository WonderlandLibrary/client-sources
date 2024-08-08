package lol.point.returnclient.util.minecraft;

import com.google.common.collect.Lists;
import lol.point.returnclient.util.MinecraftInstance;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ItemUtil implements MinecraftInstance {

    public static final List<Class<?>> BAD_ITEMS = Arrays.asList(
            Item.getByNameOrId("minecraft:sapling").getClass(),
            Item.getByNameOrId("minecraft:quartz").getClass(),
            Item.getByNameOrId("minecraft:stick").getClass(),
            Item.getByNameOrId("minecraft:rotten_flesh").getClass(),
            ItemCarrotOnAStick.class,
            ItemSkull.class,
            ItemBucketMilk.class,
            ItemBucket.class,
            ItemHoe.class,
            ItemShears.class,
            ItemSeeds.class,
            ItemReed.class,
            ItemArmorStand.class,
            ItemEditableBook.class,
            ItemEmptyMap.class,
            ItemDoublePlant.class,
            ItemFishingRod.class,
            ItemGlassBottle.class,
            ItemEnderEye.class,
            ItemExpBottle.class,
            ItemFireball.class,
            ItemFirework.class,
            ItemFireworkCharge.class,
            ItemSaddle.class,
            ItemBanner.class,
            ItemEnchantedBook.class
    );

    private static boolean isBlockValid(final Block block) {
        return (block.isFullBlock() || block == Blocks.glass) &&
                block != Blocks.sand &&
                block != Blocks.gravel &&
                block != Blocks.dispenser &&
                block != Blocks.command_block &&
                block != Blocks.noteblock &&
                block != Blocks.furnace &&
                block != Blocks.crafting_table &&
                block != Blocks.tnt &&
                block != Blocks.dropper &&
                block != Blocks.beacon;
    }

    public static boolean isTrash(Item item) {
        return BAD_ITEMS.contains(item.getClass());
    }

    public static int armorProt(final ItemArmor armor, final ItemStack item) {
        return armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{item}, DamageSource.generic);
    }

    public static int getBestBow() {
        float bestDmg = -1;
        int bestSlot = -1;
        for (int i = 0; i < 40; i++) {
            final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && itemStack.getItem() instanceof ItemBow) {
                final int powLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
                final int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
                final int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack);

                final float dmg = 1 + powLevel * 0.5f + punchLevel * 5 + flameLevel * 2;
                if (dmg > bestDmg) {
                    bestSlot = i;
                    bestDmg = dmg;

                }
            }
        }
        return bestSlot;
    }

    public static int getBestFood() {
        float bestFood = -1;
        int bestSlot = -1;
        for (int i = 0; i < 40; i++) {
            final ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
            if (item != null && item.getItem() instanceof final ItemFood food) {
                final float foodval = food.getSaturationModifier(item);
                if (bestFood < foodval) {
                    bestFood = foodval;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }

    public static int getBestArmor(final int armorType) {
        float bestProt = -1;
        int bestSlot = -1;
        for (int i = 0; i < 40; i++) {
            final ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);
            if (item == null)
                continue;
            if (item.getItem() instanceof final ItemArmor armor) {
                if (armor.armorType == armorType) {
                    final float armorProt = ItemUtil.armorProt(armor, mc.thePlayer.inventory.getStackInSlot(i));
                    if (armorProt > bestProt) {
                        bestProt = armorProt;
                        bestSlot = i;
                    }
                }
            }
        }
        return bestSlot;
    }

    public static List<Integer> getSortedByStackSize(final List<Integer> list) {
        return list.stream()
                .sorted(Comparator.comparingInt(i -> {
                    final Optional<ItemStack> optionalStack = Optional.ofNullable(mc.thePlayer.inventory.getStackInSlot(i));
                    return optionalStack.map(stack -> -stack.stackSize).orElse(0);
                }))
                .collect(Collectors.toList());
    }

    public static int getBestSword(final boolean hotbar) {
        float bestDamage = -1;
        float bestQuality = -1;
        int bestSlot = -1;

        for (int i = 0; i < (hotbar ? 9 : 36); i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack != null && stack.getItem() != null && stack.getItem() instanceof final ItemSword sword) {

                final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
                final float damage = (float) (sword.getDamageVsEntity() + level * 1.25);

                if (bestDamage < damage) {
                    bestDamage = damage;
                    bestQuality = sword.getDamageVsEntity();
                    bestSlot = i;
                }

                if ((damage == bestDamage) && (sword.getDamageVsEntity() < bestQuality)) {
                    bestQuality = sword.getDamageVsEntity();
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }

    public static int getBestToolAgainstBlock(BlockPos blockPos, boolean hotbar) {
        return getBestToolAgainstBlock(mc.theWorld.getBlockState(blockPos).getBlock(), hotbar);
    }

    public static int getBestToolAgainstBlock(Block blockPos, boolean hotbar) {
        float bestSpeed = 1F;
        int bestSlot = -1;

        for (int i = 0; i < (hotbar ? 9 : 36); i++) {
            final ItemStack item = mc.thePlayer.inventory.getStackInSlot(i);

            if (item != null) {
                final float speed = item.getStrVsBlock(blockPos);

                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
        }
        return bestSlot;
    }

    public static int getBestAxe() {
        return getBestToolAgainstBlock(Blocks.planks, false);
    }

    public static int getBestPickaxe() {
        return getBestToolAgainstBlock(Blocks.stone, false);
    }

    public static int getBestShovel() {
        return getBestToolAgainstBlock(Blocks.dirt, false);
    }

    public static int getBestBlock() {
        int bestBlockCount = -1;
        int bestSlot = -1;
        for (int i = 0; i < 40; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
            if (stack != null && stack.getItem() != null && stack.getItem() instanceof final ItemBlock block) {
                if (BlockUtil.canPlaceOnBlock(block.getBlock())) {
                    if (stack.stackSize > bestBlockCount) {
                        bestBlockCount = stack.stackSize;
                        bestSlot = i;
                    }
                }
            }
        }


        return bestSlot;
    }

    public static int getBlockSlot() {
        for (int i = 0; i < 9; i++) {
            final ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && itemStack.stackSize >= 3) {
                final ItemBlock itemBlock = (ItemBlock) itemStack.getItem();

                if (isBlockValid(itemBlock.getBlock())) {
                    return i;
                }
            }
        }

        return mc.thePlayer.inventory.currentItem;
    }
}
