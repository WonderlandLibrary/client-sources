// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public final class InventoryUtil {
    static final boolean field462;

    static {
        field462 = !InventoryUtil.class.desiredAssertionStatus();
    }

    public static void setSlot(final int slot) {
        Argon.mc.player.getInventory().selectedSlot = slot;
        ((ClientPlayerInteractionManagerAccessor) Argon.mc.interactionManager).syncSlot();
    }

    public static boolean method308(final Predicate item) {
        final int n = 0;
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n2 = n;
        int i = 0;
        while (i < 9) {
            final boolean test;
            final boolean b = test = item.test(getInventory.getStack(i).getItem());
            if (n2 == 0) {
                if (n2 == 0) {
                    if (!b) {
                        ++i;
                        if (n2 != 0) {
                            break;
                        }
                        continue;
                    } else {
                        getInventory.selectedSlot = i;
                    }
                }
                return b;
            }
            return test;
        }
        return false;
    }

    public static boolean method309(final Item item) {
        return method308(a -> InventoryUtil.equals(item, (Item) a));
    }

    public static boolean method310(final Predicate item) {
        final int n = 0;
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n2 = n;
        int i = 0;
        boolean test = false;
        while (i < 9) {
            final ItemStack getAbilities = getInventory.getStack(i);
            if (n2 == 0) {
                test = item.test(getAbilities.getItem());
                if (n2 != 0) {
                    return test;
                }
                if (test) {
                    return true;
                }
                ++i;
            }
            if (n2 != 0) {
                break;
            }
        }
        return test;
    }

    public static int method311(final Predicate item) {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        int n = 0;
        for (int i = 0; i < 36; ++i) {
            final ItemStack getAbilities = getInventory.getStack(i);
            if (item.test(getAbilities.getItem())) {
                n += getAbilities.getCount();
            }
        }
        return n;
    }

    public static int method312(final Predicate item) {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        int n = 0;
        for (int i = 9; i < 36; ++i) {
            final ItemStack getAbilities = getInventory.getStack(i);
            if (item.test(getAbilities.getItem())) {
                n += getAbilities.getCount();
            }
        }
        return n;
    }

    public static int method313(final StatusEffect type, final int duration, final int amplifier) {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n = 0;
        final StatusEffectInstance statusEffectInstance = new StatusEffectInstance(type, duration, amplifier);
        int i = 0;
        final int n2 = n;
        while (i < 9) {
            final ItemStack getAbilities = getInventory.getStack(i);
            {
                Label_0106:
                {
                    final int n3;
                    final boolean b = (n3 = ((getAbilities.getItem() instanceof SplashPotionItem) ? 1 : 0)) != 0;
                    if (!b) {
                        break Label_0106;
                    }
                    final String string = PotionUtil.getPotion(getAbilities).getEffects().toString();
                    if (string.contains(statusEffectInstance.toString())) {
                        return i;
                    }
                }
                ++i;
            }
        }
        return -1;
    }

    public static boolean hasPotion(final StatusEffect type, final int duration, final int amplifier, final ItemStack itemStack) {
        final StatusEffectInstance statusEffectInstance = new StatusEffectInstance(type, duration, amplifier);
        return PotionUtil.getPotion(itemStack).getEffects().toString().contains(statusEffectInstance.toString());
    }

    public static int method315() {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        for (int i = 9; i < 36; ++i) {
            if (getInventory.main.get(i).getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }
        return -1;
    }

    public static boolean holdAxe() {
        final int axeSlot = getAxe();

        if (axeSlot != -1) {
            Argon.mc.player.getInventory().selectedSlot = axeSlot;
            return true;
        }

        return false;
    }

    public static int method317() {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n = 0;
        final Random random = new Random();
        final int n2 = n;
        final int n3 = random.nextInt(27) + 9;
        int i = 0;
        int n4 = 0;
        while (i < 27) {
            n4 = (n3 + i) % 36;
            if (n2 != 0) {
                return n4;
            }
            final int n5 = n4;
            final ItemStack itemStack = getInventory.main.get(n5);
            if (n2 == 0) {
                Label_0134:
                {
                    if (itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                        final int n6 = n5;
                        if (n2 == 0) {
                            if (n6 == 36) {
                                final int n7 = n5;
                                if (n2 == 0) {
                                    if (n7 == 37) {
                                        final int n8 = n5;
                                        if (n2 == 0) {
                                            if (n8 == 38) {
                                                final int n9 = n5;
                                                if (n2 == 0) {
                                                    if (n9 == 39) {
                                                        break Label_0134;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return n6;
                    }
                }
                ++i;
            }
            if (n2 != 0) {
                break;
            }
        }
        return n4;
    }

    public static int method318(final String potion) {
        final int n = 0;
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n2 = new Random().nextInt(27) + 9;
        int i = 0;
        while (i < 27) {
            final int n4 = (n2 + i) % 36;
            final ItemStack itemStack = getInventory.main.get(n4);
            final boolean b = ((itemStack.getItem() instanceof SplashPotionItem) ? 1 : 0) != 0;
            {
                if (b) {
                    int n8 = 0;
                    {
                        PotionUtil.getPotion(itemStack).getEffects().toString().contains(potion);
                    }
                    if (n4 == 0) {
                        return -1;
                    }
                    return n8;
                }
            }
            ++i;
        }
        return -1;
    }

    public static int method319(final StatusEffect effect, final int duration, final int amplifier) {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final StatusEffectInstance statusEffectInstance = new StatusEffectInstance(effect, duration, amplifier);
        for (int i = 0; i < 34; ++i)
            if (getInventory.main.get(i).getItem() instanceof SplashPotionItem && PotionUtil.getPotion(getInventory.main.get(i)).getEffects().toString().contains(statusEffectInstance.toString()))
                return i;

        return -1;
    }

    public static List method320() {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        final int n = 0;
        final ArrayList list = new ArrayList();
        int i = 0;
        final int n2 = n;
        while (i < 9) {
            boolean b2;
            final boolean b = b2 = getInventory.main.get(i).isEmpty();
            Label_0121:
            {
                if (n2 == 0) {
                    if (b) {
                        list.add(i);
                        if (n2 == 0) {
                            break Label_0121;
                        }
                    }
                    final boolean contains;
                    b2 = (contains = list.contains(i));
                }
                if (n2 == 0) {
                    if (!b) {
                        break Label_0121;
                    }
                    final ItemStack itemStack = getInventory.main.get(i);
                    if (n2 != 0) {
                        break Label_0121;
                    }
                    b2 = itemStack.isEmpty();
                }
                if (!b2) {
                    list.remove(i);
                }
            }
            ++i;
            if (n2 != 0) {
                break;
            }
        }
        return list;
    }

    public static int getAxe() {
        final PlayerInventory getInventory = Argon.mc.player.getInventory();
        for (int i = 0; i < 9; ++i) {
            if (((Inventory) getInventory).getStack(i).getItem() instanceof AxeItem) {
                return i;
            }
        }
        return -1;
    }


    private static boolean method323(final Item item, final Item item2) {
        return item2 == item;
    }

    private static boolean equals(final Item item, final Item item2) {
        return item.equals(item2);
    }
}
