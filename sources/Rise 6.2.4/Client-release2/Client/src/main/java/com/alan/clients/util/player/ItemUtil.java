package com.alan.clients.util.player;

import com.google.common.collect.Multimap;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public final class ItemUtil {
    private static final List<Item> WHITELISTED_ITEMS = Arrays.asList(Items.fishing_rod, Items.water_bucket, Items.bucket, Items.arrow, Items.bow, Items.snowball, Items.egg, Items.ender_pearl);

    public static boolean useful(final ItemStack stack) {
        final Item item = stack.getItem();

        if (item instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) item;
            return ItemPotion.isSplash(stack.getMetadata()) && PlayerUtil.goodPotion(potion.getEffects(stack).get(0).getPotionID());
        }

        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock) item).getBlock();
            if (block instanceof BlockGlass || block instanceof BlockStainedGlass || (block.isFullBlock() && !(block instanceof ITileEntityProvider || block instanceof BlockContainer || block instanceof BlockTNT || block instanceof BlockSlime || block instanceof BlockFalling))) {
                return true;
            }
        }

        return item instanceof ItemSword ||
                item instanceof ItemTool ||
                item instanceof ItemArmor ||
                item instanceof ItemFood ||
                WHITELISTED_ITEMS.contains(item);
    }


    public static boolean isGoodItem(ItemStack stack, Container container) {
        Item item = stack.getItem();

        if (item instanceof ItemSword) {
            return isBestSword(stack, container);
        } else if (item instanceof ItemTool) {
            return isBestTool(stack, container, getToolType(item));
        } else if (item instanceof ItemBlock) {
            ItemBlock block = (ItemBlock) item;
            return block.getBlock().isFullBlock();
        } else if (item instanceof ItemBucketMilk || item instanceof ItemBucket || item instanceof ItemEnderPearl) {
            return true;
        } else if (item instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion) item;
            List<PotionEffect> effects = potion.getEffects(stack);

            if (effects != null) {
                return effects.stream().anyMatch(effect -> !Potion.potionTypes[effect.getPotionID()].isBadEffect());
            }
        } else if (item instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) item;
            return isBestArmor(stack, container, armor.armorType);
        } else if (item instanceof ItemFood) {
            return true;
        } else if (item instanceof ItemBow) {
            return isBestBow(stack, container);
        } else {
            return item == Items.arrow;
        }

        return false;
    }

    public static boolean isBestArmor(ItemStack stack, Container container, int type) {
        ItemStack bestArmor = getBestArmor(container, type);
        return bestArmor == null || stack == bestArmor || getArmorReduction(stack) > getArmorReduction(bestArmor);
    }

    public static boolean isBestTool(ItemStack stack, Container container, int type) {
        ItemStack bestTool = getBestTool(container, type);
        return bestTool == null || stack == bestTool || getToolEfficiency(stack) > getToolEfficiency(bestTool);
    }

    public static boolean isBestSword(ItemStack stack, Container container) {
        ItemStack bestSword = getBestSword(container);
        return bestSword == null || stack == bestSword || getSwordDamage(stack) > getSwordDamage(bestSword);
    }

    public static boolean isBestBow(ItemStack stack, Container container) {
        ItemStack bestBow = getBestBow(container);
        return bestBow == null || stack == bestBow || getBowDamage(stack) > getBowDamage(bestBow);
    }

    private static ItemStack getBestTool(Container container, int type) {
        return getBestItem(InventoryUtil.BEGIN, InventoryUtil.END, container, stack -> {
            if (stack.getItem() != null && stack.getItem() instanceof ItemTool) {
                ItemTool tool = (ItemTool) stack.getItem();
                return getToolType(tool) == type;
            }
            return false;
        }, Comparator.comparingDouble(ItemUtil::getToolEfficiency));
    }

    private static ItemStack getBestSword(Container container) {
        return getBestItem(InventoryUtil.BEGIN, InventoryUtil.END, container, stack -> stack.getItem() instanceof ItemSword, Comparator.comparingDouble(ItemUtil::getSwordDamage));
    }

    private static ItemStack getBestArmor(Container container, int type) {
        return getBestItem(InventoryUtil.ARMOR_BEGIN, InventoryUtil.END, container, stack -> {
            if (stack.getItem() != null && stack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor) stack.getItem();
                return armor.armorType == type;
            }
            return false;
        }, Comparator.comparingDouble(ItemUtil::getArmorReduction));
    }

    private static ItemStack getBestBow(Container container) {
        return getBestItem(InventoryUtil.BEGIN, InventoryUtil.END, container, stack -> stack.getItem() instanceof ItemBow, Comparator.comparingDouble(ItemUtil::getBowDamage));
    }

    private static ItemStack getBestItem(int begin, int end, Container container, Predicate<ItemStack> validator, Comparator<ItemStack> comparator) {
        return IntStream.range(begin, end).mapToObj(i -> container.getSlot(i).getStack()).filter(stack -> stack != null && validator.test(stack)).max(comparator.thenComparingDouble(stack -> stack.getMaxDamage() - stack.getItemDamage())).orElse(null);
    }

    private static float getToolEfficiency(ItemStack stack) {
        ItemTool tool = (ItemTool) stack.getItem();

        float f = tool.getToolMaterial().getEfficiencyOnProperMaterial();

        if (f > 1.0F) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);

            if (i > 0) {
                f += (float) (i * i + 1);
            }
        }

        return f;
    }

    private static double getSwordDamage(ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers();

        if (!multimap.isEmpty()) {
            for (Map.Entry<String, AttributeModifier> entry : multimap.entries()) {
                if (entry.getKey().equals(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                    AttributeModifier attributemodifier = entry.getValue();
                    double d0 = attributemodifier.getAmount() + EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);

                    double d1;

                    if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
                        d1 = d0;
                    } else {
                        d1 = d0 * 100.0;
                    }

                    if (d0 > 0.0) {
                        return d1;
                    } else if (d0 < 0.0) {
                        d1 = d1 * -1.0;
                        return d1;
                    }
                }
            }
        }

        return 0.0;
    }

    private static double getArmorReduction(ItemStack stack) {
        ItemArmor armor = (ItemArmor) stack.getItem();

        float damage = -1.0F;

        int i = 25 - armor.damageReduceAmount;
        float f = damage * (float) i;
        damage = f / 25.0F;

        int k = Math.min(20, EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{stack}, DamageSource.generic));

        if (k > 0) {
            int l = 25 - k;
            float f1 = damage * (float) l;
            damage = f1 / 25.0F;
        }

        return damage;
    }

    private static int getBowDamage(ItemStack stack) {
        return EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
    }

    public static int getToolType(Item tool) {
        if (tool instanceof ItemPickaxe) {
            return 0;
        } else if (tool instanceof ItemAxe) {
            return 1;
        } else if (tool instanceof ItemSpade) {
            return 2;
        }
        return 0;
    }

    public static ItemStack getCustomSkull(final String name, final String url) {
        final String gameProfileData = String.format("{\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}", url);
        final String base64Encoded = Base64.getEncoder().encodeToString(gameProfileData.getBytes());
        return getItemStack(String.format("skull 1 3 {SkullOwner:{Id:\"%s\",Name:\"%s\",Properties:{textures:[{Value:\"%s\"}]}}}", UUID.randomUUID(), name, base64Encoded));
    }

    public static ItemStack getItemStack(String command) {
        try {
            command = command.replace('&', '\u00a7');
            final String[] args;
            int i = 1;
            int j = 0;
            args = command.split(" ");
            final ResourceLocation resourcelocation = new ResourceLocation(args[0]);
            final Item item = Item.itemRegistry.getObject(resourcelocation);

            if (args.length >= 2 && args[1].matches("\\d+")) {
                i = Integer.parseInt(args[1]);
            }

            if (args.length >= 3 && args[2].matches("\\d+")) {
                j = Integer.parseInt(args[2]);
            }

            final ItemStack itemstack = new ItemStack(item, i, j);
            if (args.length >= 4) {
                final StringBuilder NBT = new StringBuilder();

                int nbtCount = 3;
                while (nbtCount < args.length) {
                    NBT.append(" ").append(args[nbtCount]);
                    nbtCount++;
                }

                itemstack.setTagCompound(JsonToNBT.getTagFromJson(NBT.toString()));
            }
            return itemstack;
        } catch (final Exception ex) {
            ex.printStackTrace();
            return new ItemStack(Blocks.barrier);
        }
    }

    public String getCustomSkullNBT(final String name, final String url) {
        final String gameProfileData = String.format("{\"textures\":{\"SKIN\":{\"url\":\"%s\"}}}", url);
        final String base64Encoded = Base64.getEncoder().encodeToString(gameProfileData.getBytes());
        return String.format("SkullOwner:{Id:\"%s\",Name:\"%s\",Properties:{textures:[{Value:\"%s\"}]}}", UUID.randomUUID(), name, base64Encoded);
    }
}
