package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigChild;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.config.ConfigParentId;
import com.shroomclient.shroomclientnextgen.events.EventBus;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ClientTickEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.InvManagerUtil;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.screen.slot.SlotActionType;

@RegisterModule(
    // made name shorter to fit in noti...
    name = "Inventory Manager",
    uniqueId = "invcleaner",
    description = "Manages Your Inventory",
    category = ModuleCategory.Player
)
public class InventoryManager extends Module {

    // prob more we should throw out, batman help!! (i was gonna add bows but ehh)
    public static final Item[] dropSkywars = {
        Items.EGG,
        Items.SNOWBALL,
        Items.PLAYER_HEAD,
        Items.LAVA_BUCKET,
        Items.WATER_BUCKET,
        Items.BUCKET,
        Items.COOKED_BEEF,
        Items.ENCHANTED_BOOK,
        Items.EXPERIENCE_BOTTLE,
        Items.DIRT,
        Items.STICK,
        Items.TNT,
        Items.SKELETON_SPAWN_EGG,
        Items.CREEPER_SPAWN_EGG,
        Items.ZOMBIE_SPAWN_EGG,
        Items.FISHING_ROD,
        Items.FLINT_AND_STEEL,
        Items.BLAZE_SPAWN_EGG,
    };
    public static final Potion[] dropPotions = { Potions.POISON };
    // might add this
    public int bowSlot = 4;

    @ConfigOption(
        name = "Delay Between Items",
        description = "Delay Sorting Each Item",
        min = 0,
        max = 500,
        order = 1
    )
    public Integer DelayBetweenItems = 0;

    @ConfigOption(
        name = "Delay Before Open",
        description = "Delay After Opening Your Inventory",
        min = 0,
        max = 500,
        order = 3
    )
    public Integer DelayBeforeOpen = 0;

    @ConfigParentId("close")
    @ConfigOption(
        name = "Auto Close",
        description = "Automatically Closes Your Inventory When Done",
        order = 2
    )
    public Boolean autoclose = true;

    @ConfigChild("close")
    @ConfigOption(
        name = "Delay Before Close",
        description = "Delay Before Closing Gui, In Ticks",
        min = 0,
        max = 500,
        order = 4
    )
    public Integer DelayBeforeClose = 0;

    @ConfigOption(
        name = "Drop Junk",
        description = "Drops Useless Items In Skywars",
        order = 5
    )
    public Boolean dropJunk = true;

    @ConfigOption(
        name = "Sword Slot",
        description = "Slot The Manager Will Put Your Sword In",
        min = 1,
        max = 9,
        order = 6
    )
    public Integer swordSlot = 1;

    @ConfigOption(
        name = "Block Slot",
        description = "Slot The Manager Will Put Your Blocks In",
        min = 1,
        max = 9,
        order = 7
    )
    public Integer blockSlot = 3;

    @ConfigOption(
        name = "Gapple Slot",
        description = "Slot The Manager Will Put Your Gapples In",
        min = 1,
        max = 9,
        order = 8
    )
    public Integer gappleSlot = 2;

    @ConfigOption(
        name = "Pearl Slot",
        description = "Slot The Manager Will Put Your Enderpearls In",
        min = 1,
        max = 9,
        order = 9
    )
    public Integer pearlSlot = 8;

    @ConfigOption(
        name = "Pickaxe Slot",
        description = "Slot The Manager Will Put Your Pickaxe In",
        min = 1,
        max = 9,
        order = 10
    )
    public Integer pickaxeSlot = 6;

    @ConfigOption(
        name = "Axe Slot",
        description = "Slot The Manager Will Put Your Axe In",
        min = 1,
        max = 9,
        order = 11
    )
    public Integer axeSlot = 5;

    @ConfigOption(
        name = "Shovel Slot",
        description = "Slot The Manager Will Put Your Shovel In",
        min = 1,
        max = 9,
        order = 12
    )
    public Integer shovelSlot = 7;

    @ConfigOption(
        name = "Junk Slot",
        description = "Slot That You Won't Use",
        min = 1,
        max = 9,
        order = 13
    )
    public Integer junkSlot = 4;

    int i = 0;
    boolean going = false;
    int gone = 0;
    long closeTime = 0;
    boolean stop = false;
    boolean wasOpen = false;
    long openTime = 0;
    long time;

    public static boolean isJunk(ItemStack stack) {
        if (stack == null) return true;

        for (Item dropSkywar : dropSkywars) {
            if (stack.getItem() == dropSkywar) {
                return true;
            }

            if (stack.getItem() == Items.POTION) {
                boolean isBadPotion = PotionUtil.getPotionEffects(stack)
                    .stream()
                    .anyMatch(
                        p ->
                            Arrays.stream(dropPotions).anyMatch(
                                d ->
                                    p.getEffectType() ==
                                    d.getEffects().get(0).getEffectType()
                            )
                    );
                if (isBadPotion) return true;
            }
        }

        return false;
    }

    // this is SO dumb, i prob missed smth idk
    public static int stupidNumberConversion(int num) {
        if (num <= 8) return num + 36;
        if (num < 36) return num;
        if (num == 36) return 8;
        if (num == 37) return 7;
        if (num == 38) return 6;
        if (num == 39) return 5;
        if (num == 40) return 45;
        return num;
    }

    // if u wanna add bows!
    public static int getBowDamage(ItemStack bow) {
        return (
            EnchantmentHelper.getLevel(Enchantments.POWER, bow) +
            EnchantmentHelper.getLevel(Enchantments.FLAME, bow) * 2
        );
    }

    public static float getToolDamage(ItemStack tool) {
        float damage = 0.0f;
        if (tool != null && tool.getItem() instanceof SwordItem h) {
            damage += h.getAttackDamage();
            damage +=
            (float) (1.25 *
                EnchantmentHelper.getLevel(Enchantments.SHARPNESS, tool));
            damage +=
            (float) (EnchantmentHelper.getLevel(Enchantments.SHARPNESS, tool) *
                0.5);
        }
        return damage;
    }

    public static float getProtection(final ItemStack stack) {
        float prot = 0.0f;
        if (stack.getItem() instanceof ArmorItem armor) {
            prot +=
            (float) (armor.getProtection() +
                (100 - armor.getProtection()) *
                    EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack) *
                    0.0075);
            prot += (float) (stack.getMaxDamage() / 1000.0);
            prot -= (float) (stack.getDamage() / 1000.0);
        }
        return prot;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    // im aware this code is bad and could be replaced by functions, but it works and was easy to make
    @SubscribeEvent(EventBus.Priority.HIGHEST)
    public void onClientTick(ClientTickEvent e) {
        if (C.mc.currentScreen instanceof InventoryScreen) {
            if (!wasOpen) {
                wasOpen = true;
                openTime = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - openTime < DelayBeforeOpen) {
                return;
            }

            if (
                System.currentTimeMillis() - closeTime >= DelayBeforeClose &&
                stop
            ) {
                C.mc.currentScreen.close();
                C.mc.mouse.lockCursor();
                closeTime = 0;
            }

            if (!going) {
                i = 0;
                going = true;
            }

            while (delayPassed() && !stop) {
                while (i < 46 && isStackEmpty(i)) i++;

                if (i >= 45) {
                    // goes a few times to make sure inventory is CLEAN!
                    // doesnt really matter
                    if (gone >= 1) {
                        going = false;
                        gone = 0;

                        if (autoclose) {
                            stop = true;
                            closeTime = System.currentTimeMillis();
                        } else going = true;
                        return;
                    } else {
                        going = false;
                        gone++;
                    }
                }

                EquipmentSlot[] armerSlots = {
                    EquipmentSlot.FEET,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.HEAD,
                };

                ArrayList<ItemStack> stacks = new ArrayList<>();
                for (int ii = 0; ii < C.p().getInventory().size(); ii++) {
                    ItemStack theS = C.p().getInventory().getStack(ii);
                    if (theS != null) stacks.add(theS);
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    ArmorItem ai
                ) {
                    for (int n = 0; n < 4; n++) {
                        if (ai.getSlotType() == armerSlots[n] && i != 36 + n) {
                            ItemStack cur = C.p()
                                .getInventory()
                                .getStack(36 + n);

                            if (isStackEmpty(36 + n)) {
                                shiftClick(i);
                                //moveItem(i, 36 + n);
                            } else if (
                                InvManagerUtil.isBetter(
                                    cur,
                                    C.p().getInventory().getStack(i),
                                    stacks
                                )
                            ) {
                                drop(36 + n);
                                shiftClick(i);
                                //moveItem(i, 36 + n);
                            } else {
                                drop(i);
                            }
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    SwordItem ai
                ) {
                    if (i != swordSlot - 1) {
                        ItemStack cur = C.p()
                            .getInventory()
                            .getStack(swordSlot - 1);
                        if (isStackEmpty(swordSlot - 1)) {
                            moveItem(i, swordSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(swordSlot - 1)
                                    .getItem() instanceof
                                SwordItem)
                        ) {
                            shiftClick(swordSlot - 1);
                            moveItem(i, swordSlot - 1);
                        } else if (
                            InvManagerUtil.isBetter(
                                cur,
                                C.p().getInventory().getStack(i),
                                stacks
                            )
                        ) {
                            drop(swordSlot - 1);
                            moveItem(i, swordSlot - 1);
                        } else {
                            drop(i);
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    BlockItem ai
                ) {
                    if (i != blockSlot - 1) {
                        ItemStack cur = C.p()
                            .getInventory()
                            .getStack(blockSlot - 1);
                        if (isStackEmpty(blockSlot - 1)) {
                            moveItem(i, blockSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(blockSlot - 1)
                                    .getItem() instanceof
                                BlockItem)
                        ) {
                            shiftClick(blockSlot - 1);
                            moveItem(i, blockSlot - 1);
                        } else if (
                            C.p().getInventory().getStack(i).getCount() >
                            cur.getCount()
                        ) {
                            moveItem(i, blockSlot - 1);
                        }
                    }
                }

                if (
                    C.p()
                        .getInventory()
                        .getStack(i)
                        .toString()
                        .toLowerCase()
                        .contains("golden_ap")
                ) {
                    if (i != gappleSlot - 1) {
                        if (isStackEmpty(gappleSlot - 1)) {
                            moveItem(i, gappleSlot - 1);
                        } else if (
                            !C.p()
                                .getInventory()
                                .getStack(gappleSlot - 1)
                                .toString()
                                .toLowerCase()
                                .contains("golden_ap")
                        ) {
                            shiftClick(gappleSlot - 1);
                            moveItem(i, gappleSlot - 1);
                        } else if (
                            C.p().getInventory().getStack(i).getCount() >=
                            C.p()
                                .getInventory()
                                .getStack(gappleSlot - 1)
                                .getCount()
                        ) {
                            drop(gappleSlot - 1);
                            moveItem(i, gappleSlot - 1);
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    EnderPearlItem
                ) {
                    if (i != pearlSlot - 1) {
                        if (isStackEmpty(pearlSlot - 1)) {
                            moveItem(i, pearlSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(pearlSlot - 1)
                                    .getItem() instanceof
                                EnderPearlItem)
                        ) {
                            moveItem(i, pearlSlot - 1);
                        } else if (
                            C.p().getInventory().getStack(i).getCount() >=
                            C.p()
                                .getInventory()
                                .getStack(pearlSlot - 1)
                                .getCount()
                        ) {
                            shiftClick(pearlSlot - 1);
                            moveItem(i, pearlSlot - 1);
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    AxeItem ai
                ) {
                    if (i != axeSlot - 1) {
                        ItemStack cur = C.p()
                            .getInventory()
                            .getStack(axeSlot - 1);
                        if (isStackEmpty(axeSlot - 1)) {
                            moveItem(i, axeSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(axeSlot - 1)
                                    .getItem() instanceof
                                AxeItem)
                        ) {
                            moveItem(i, axeSlot - 1);
                        } else if (
                            InvManagerUtil.isBetter(
                                cur,
                                C.p().getInventory().getStack(i),
                                stacks
                            )
                        ) {
                            drop(axeSlot - 1);

                            moveItem(i, axeSlot - 1);
                        } else {
                            drop(i);
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    PickaxeItem ai
                ) {
                    if (i != pickaxeSlot - 1) {
                        ItemStack cur = C.p()
                            .getInventory()
                            .getStack(pickaxeSlot - 1);
                        if (isStackEmpty(pickaxeSlot - 1)) {
                            moveItem(i, pickaxeSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(pickaxeSlot - 1)
                                    .getItem() instanceof
                                PickaxeItem)
                        ) {
                            moveItem(i, pickaxeSlot - 1);
                        } else if (
                            InvManagerUtil.isBetter(
                                cur,
                                C.p().getInventory().getStack(i),
                                stacks
                            )
                        ) {
                            drop(pickaxeSlot - 1);

                            moveItem(i, pickaxeSlot - 1);
                        } else {
                            drop(i);
                        }
                    }
                }

                if (
                    C.p().getInventory().getStack(i).getItem() instanceof
                    ShovelItem ai
                ) {
                    if (i != shovelSlot - 1) {
                        ItemStack cur = C.p()
                            .getInventory()
                            .getStack(shovelSlot - 1);
                        if (isStackEmpty(shovelSlot - 1)) {
                            moveItem(i, shovelSlot - 1);
                        } else if (
                            !(C.p()
                                    .getInventory()
                                    .getStack(shovelSlot - 1)
                                    .getItem() instanceof
                                ShovelItem)
                        ) {
                            moveItem(i, shovelSlot - 1);
                        } else if (
                            InvManagerUtil.isBetter(
                                cur,
                                C.p().getInventory().getStack(i),
                                stacks
                            )
                        ) {
                            drop(shovelSlot - 1);

                            moveItem(i, shovelSlot - 1);
                        } else {
                            drop(i);
                        }
                    }
                }

                if (dropJunk) {
                    if (
                        InventoryManager.isJunk(
                            C.p().getInventory().getStack(i)
                        )
                    ) drop(i);
                }

                i++;
            }
        } else {
            going = false;
            stop = false;
            closeTime = 0;
            i = 0;
            wasOpen = false;
        }
    }

    public boolean isStackEmpty(int i) {
        return C.p().getInventory().getStack(i).toString().contains("air");
    }

    private boolean delayPassed() {
        return System.currentTimeMillis() - time >= DelayBetweenItems;
    }

    public void drop(int slot) {
        slot = stupidNumberConversion(slot);
        time = System.currentTimeMillis();
        C.mc.interactionManager.clickSlot(
            C.p().currentScreenHandler.syncId,
            slot,
            1,
            SlotActionType.THROW,
            C.p()
        );
    }

    public void shiftClick(int slot) {
        slot = stupidNumberConversion(slot);

        time = System.currentTimeMillis();
        C.mc.interactionManager.clickSlot(
            C.p().currentScreenHandler.syncId,
            slot,
            0,
            SlotActionType.QUICK_MOVE,
            C.p()
        );
    }

    public void moveItem(int startSlot, int endSlot) {
        System.out.println(startSlot + " to " + endSlot);
        startSlot = stupidNumberConversion(startSlot);
        endSlot = stupidNumberConversion(endSlot);
        int tJunkSlot = stupidNumberConversion(junkSlot);

        boolean startIsHotbar = startSlot >= 36;
        boolean endIsHotbar = endSlot >= 36;

        time = System.currentTimeMillis();

        if (endIsHotbar) {
            C.mc.interactionManager.clickSlot(
                C.p().currentScreenHandler.syncId,
                startSlot,
                endSlot - 36,
                SlotActionType.SWAP,
                C.p()
            );
        } else {
            if (startIsHotbar) {
                C.mc.interactionManager.clickSlot(
                    C.p().currentScreenHandler.syncId,
                    endSlot,
                    startSlot - 36,
                    SlotActionType.SWAP,
                    C.p()
                );
            } else {
                C.mc.interactionManager.clickSlot(
                    C.p().currentScreenHandler.syncId,
                    startSlot,
                    tJunkSlot - 36,
                    SlotActionType.SWAP,
                    C.p()
                );
                C.mc.interactionManager.clickSlot(
                    C.p().currentScreenHandler.syncId,
                    endSlot,
                    tJunkSlot - 36,
                    SlotActionType.SWAP,
                    C.p()
                );
            }
        }
    }
}
