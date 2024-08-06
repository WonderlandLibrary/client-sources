package com.shroomclient.shroomclientnextgen.util;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.*;

public class InvManagerUtil {

    // Doesn't contain equipment as these get filtered at an earlier stage
    private static final List<Item> goodItems = List.of(
        Items.STONE,
        Items.OAK_PLANKS,
        Items.OAK_LOG,
        Items.GOLDEN_APPLE,
        Items.ENDER_PEARL,
        Items.ARROW
    );

    private static float getSwordDamage(ItemStack stack) {
        int sharp = EnchantmentHelper.getLevel(Enchantments.SHARPNESS, stack);
        float sharpDmg = 0;
        if (sharp > 0) {
            sharpDmg = 0.5f * ((float) sharp) + 0.5f;
        }

        if (stack.getItem() instanceof SwordItem s) return (
            s.getAttackDamage() + sharpDmg
        );

        return -1;
    }

    private static float getBowDamage(ItemStack stack) {
        int power = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
        float dmgMulti = 1f;
        if (power > 0) {
            dmgMulti += 0.25f * ((float) (1 + power));
        }
        return dmgMulti;
    }

    // Lower is better
    private static float getArmorMulti(ItemStack stack) {
        int prot = EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack);
        float protMulti = 1;
        if (prot > 0) {
            protMulti = 1f - (0.04f * ((float) prot));
        }
        protMulti -= (((ArmorItem) stack.getItem()).getProtection() * 0.04f);
        return protMulti;
    }

    // Higher is better
    private static float getInverseArmorMulti(ItemStack stack) {
        return 1f - getArmorMulti(stack);
    }

    private static int getToolSpeed(ItemStack stack) {
        int eff = EnchantmentHelper.getLevel(Enchantments.EFFICIENCY, stack);
        ToolMaterial mat = ((ToolItem) stack.getItem()).getMaterial();
        int speed;
        if (mat == ToolMaterials.WOOD) {
            speed = 2;
        } else if (mat == ToolMaterials.STONE) {
            speed = 4;
        } else if (mat == ToolMaterials.IRON) {
            speed = 6;
        } else if (mat == ToolMaterials.DIAMOND) {
            speed = 8;
        } else if (mat == ToolMaterials.NETHERITE) {
            speed = 9;
        } else if (mat == ToolMaterials.GOLD) {
            speed = 12;
        } else {
            speed = 1;
        }

        if (eff > 0) {
            speed += 1;
            speed += (int) Math.pow(eff, 2);
        }

        return speed;
    }

    private static boolean stackEquals(ItemStack a, ItemStack b) {
        if (a.getItem() != b.getItem()) return false;
        if (a.getCount() != b.getCount()) return false;
        if (a.getNbt() == null && b.getNbt() == null) return true;
        if (a.getNbt() == null || b.getNbt() == null) return false;
        return a.getNbt().equals(b.getNbt());
    }

    private static boolean isSameArmorPiece(ItemStack a, ItemStack b) {
        return (
            ((ArmorItem) a.getItem()).getSlotType() ==
            ((ArmorItem) b.getItem()).getSlotType()
        );
    }

    private static ToolType getToolType(ItemStack stack) {
        Item i = stack.getItem();
        if (i instanceof PickaxeItem) {
            return ToolType.PICKAXE;
        } else if (i instanceof AxeItem) {
            return ToolType.AXE;
        } else if (i instanceof ShovelItem) {
            return ToolType.SHOVEL;
        }
        return null;
    }

    private static boolean isSameTool(ItemStack a, ItemStack b) {
        return getToolType(a) == getToolType(b);
    }

    public static boolean isSame(@Nullable ItemStack a, ItemStack b) {
        if (a == null && b == null) return true;
        if (a == null) return false;
        if (b == null) return false;
        return stackEquals(a, b);
    }

    // If this is true it's the best item
    public static boolean isBetter(
        @Nullable ItemStack current,
        ItemStack stack,
        List<ItemStack> inventory
    ) {
        if (current != null && stackEquals(current, stack)) return false;

        if (
            (double) stack.getDamage() / stack.getMaxDamage() > 0.75d
        ) return false;

        List<ItemStack> otherStacks = inventory
            .stream()
            .filter(
                s ->
                    !stackEquals(s, stack) &&
                    (current == null || !stackEquals(s, current))
            )
            .toList();

        if (stack.getItem() instanceof SwordItem) {
            if (
                current != null &&
                current.getItem() instanceof SwordItem &&
                getSwordDamage(stack) < getSwordDamage(current)
            ) return false;

            Optional<ItemStack> optBest = otherStacks
                .stream()
                .max(
                    Comparator.comparingDouble(it -> {
                        if (it.getItem() instanceof SwordItem) {
                            return getSwordDamage(it);
                        } else {
                            return -1d;
                        }
                    })
                );
            if (
                optBest.isPresent() &&
                optBest.get().getItem() instanceof SwordItem &&
                getSwordDamage(optBest.get()) > getSwordDamage(stack)
            ) return false;
        } else if (stack.getItem() instanceof ArmorItem) {
            if (
                current != null &&
                current.getItem() instanceof ArmorItem &&
                isSameArmorPiece(stack, current) &&
                getInverseArmorMulti(stack) < getInverseArmorMulti(current)
            ) return false;

            Optional<ItemStack> optBest = otherStacks
                .stream()
                .max(
                    Comparator.comparingDouble(it -> {
                        if (
                            it.getItem() instanceof ArmorItem &&
                            isSameArmorPiece(stack, it)
                        ) {
                            return getInverseArmorMulti(it);
                        } else {
                            return -1d;
                        }
                    })
                );
            if (
                optBest.isPresent() &&
                optBest.get().getItem() instanceof ArmorItem &&
                getInverseArmorMulti(optBest.get()) >
                    getInverseArmorMulti(stack)
            ) return false;
        } else if (stack.getItem() instanceof ToolItem) {
            if (
                current != null &&
                current.getItem() instanceof ToolItem &&
                isSameTool(stack, current) &&
                getToolSpeed(stack) < getToolSpeed(current)
            ) return false;

            Optional<ItemStack> optBest = otherStacks
                .stream()
                .max(
                    Comparator.comparingDouble(it -> {
                        if (getToolType(it) == null) return -1d;
                        if (
                            it.getItem() instanceof ToolItem &&
                            isSameTool(stack, it)
                        ) {
                            return getToolSpeed(it);
                        } else {
                            return -1d;
                        }
                    })
                );
            if (
                optBest.isPresent() &&
                optBest.get().getItem() instanceof ToolItem &&
                getToolSpeed(optBest.get()) > getToolSpeed(stack)
            ) return false;
        }

        return true;
    }

    public static ItemType getType(ItemStack stack) {
        Item i = stack.getItem();
        if (i instanceof SwordItem) return ItemType.SWORD;
        if (i instanceof BowItem) return ItemType.BOW;
        if (i instanceof ArmorItem ti) {
            switch (ti.getSlotType()) {
                case HEAD -> {
                    return ItemType.HELMET;
                }
                case CHEST -> {
                    return ItemType.CHESTPLATE;
                }
                case LEGS -> {
                    return ItemType.LEGGINGS;
                }
                case FEET -> {
                    return ItemType.BOOTS;
                }
            }
        }
        if (i instanceof PickaxeItem) return ItemType.PICKAXE;
        if (i instanceof AxeItem) return ItemType.AXE;
        return ItemType.OTHER;
    }

    private static float getValue(ItemStack stack) {
        ItemType type = getType(stack);
        switch (type) {
            case SWORD -> {
                return getSwordDamage(stack);
            }
            case BOW -> {
                return getBowDamage(stack);
            }
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS -> {
                return getInverseArmorMulti(stack);
            }
            case PICKAXE, AXE -> {
                return getToolSpeed(stack);
            }
            case OTHER -> throw new RuntimeException("Unsupported type");
        }
        throw new RuntimeException("Won't happen");
    }

    public static boolean didIncreaseInValue(
        @Nullable ItemStack old,
        ItemStack newS
    ) {
        if (old == null || old.getItem() == Items.AIR) return true;
        if (getType(old) != getType(newS)) return true;
        return getValue(newS) > getValue(old);
    }

    public static @Nullable ItemStack getBest(
        List<ItemStack> inv,
        ItemType type
    ) {
        Stream<ItemStack> filtered = inv
            .stream()
            .filter(stack -> getType(stack) == type);
        switch (type) {
            case SWORD -> {
                return filtered
                    .max(
                        Comparator.comparingDouble(
                            InvManagerUtil::getSwordDamage
                        )
                    )
                    .orElse(null);
            }
            case BOW -> {
                return filtered
                    .max(
                        Comparator.comparingDouble(InvManagerUtil::getBowDamage)
                    )
                    .orElse(null);
            }
            case HELMET, CHESTPLATE, LEGGINGS, BOOTS -> {
                return filtered
                    .max(
                        Comparator.comparingDouble(
                            InvManagerUtil::getInverseArmorMulti
                        )
                    )
                    .orElse(null);
            }
            case PICKAXE, AXE -> {
                return filtered
                    .max(
                        Comparator.comparingDouble(InvManagerUtil::getToolSpeed)
                    )
                    .orElse(null);
            }
            case OTHER -> throw new RuntimeException("Unsupported type");
        }
        throw new RuntimeException("Won't happen");
    }

    private static boolean isGood(ItemStack stack) {
        return goodItems.contains(stack.getItem());
    }

    public static boolean isJunk(@Nullable ItemStack stack) {
        if (stack == null) return true;
        return !isGood(stack);
    }

    public static int getBestSwordSlotHotbar() {
        int bestSlot = 0;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = C.p().getInventory().getStack(i);
            if (
                stack != null &&
                getSwordDamage(stack) >
                    getSwordDamage(C.p().getInventory().getStack(bestSlot))
            ) {
                bestSlot = i;
            }
        }

        return bestSlot;
    }

    private enum ToolType {
        PICKAXE,
        AXE,
        SHOVEL,
    }

    public enum ItemType {
        SWORD,
        BOW,
        HELMET,
        CHESTPLATE,
        LEGGINGS,
        BOOTS,
        PICKAXE,
        AXE,
        OTHER,
    }
}
