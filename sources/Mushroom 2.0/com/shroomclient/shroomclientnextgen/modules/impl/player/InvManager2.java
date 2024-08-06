package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.google.common.collect.Lists;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.InventoryMove;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.InvManagerUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Vec3d;

// short name looks gooder in arraylist + notification
/*@RegisterModule(
        name = "Inv Cleaner2",
        uniqueId = "invcleaner2",
        description = "Batman's Inventory Manager",
        category = ModuleCategory.Player
)*/
public class InvManager2 extends Module {

    private final ConcurrentLinkedQueue<Action> actionQueue =
        new ConcurrentLinkedQueue<>();

    @ConfigOption(
        name = "Sword Slot",
        description = "",
        min = 1,
        max = 9,
        order = 1
    )
    public Integer swordSlot = 1;

    @ConfigOption(
        name = "Gapple Slot",
        description = "",
        min = 1,
        max = 9,
        order = 2
    )
    public Integer gappleSlot = 2;

    @ConfigOption(
        name = "Blocks Slot",
        description = "",
        min = 1,
        max = 9,
        order = 3
    )
    public Integer blockSlot = 3;

    @ConfigOption(
        name = "Junk Slot",
        description = "",
        min = 1,
        max = 9,
        order = 4
    )
    public Integer junkSlot = 4;

    @ConfigOption(
        name = "Pearl Slot",
        description = "",
        min = 1,
        max = 9,
        order = 5
    )
    public Integer pearlSlot = 5;

    @ConfigOption(
        name = "Pickaxe Slot",
        description = "",
        min = 1,
        max = 9,
        order = 6
    )
    public Integer pickaxeSlot = 6;

    @ConfigOption(
        name = "Axe Slot",
        description = "",
        min = 1,
        max = 9,
        order = 7
    )
    public Integer axeSlot = 7;

    @ConfigOption(
        name = "Bow Slot",
        description = "",
        min = 1,
        max = 9,
        order = 8
    )
    public Integer bowSlot = 8;

    @ConfigOption(
        name = "Potion Slot",
        description = "",
        min = 1,
        max = 9,
        order = 9
    )
    public Integer potionSlot = 9;

    @ConfigOption(
        name = "Before clean delay",
        description = "",
        min = 0,
        max = 500,
        order = 10
    )
    public Integer preDelay = 150;

    @ConfigOption(name = "Auto close", description = "", order = 11)
    public Boolean autoClose = false;

    @ConfigOption(
        name = "After clean delay",
        description = "",
        min = 0,
        max = 500,
        order = 12
    )
    public Integer postDelay = 150;

    @ConfigOption(
        name = "Action delay",
        description = "",
        max = 500,
        order = 13
    )
    public Integer actionDelay = 30;

    Vec3d motion;
    private long lastActionAt = 0;
    private boolean wantsToClose = false;
    private long invCloseAt = 0;
    private boolean didRun = false;
    private boolean didDrop = false;
    private boolean wasOpen = false;
    private long invOpenAt = 0;
    private boolean didProc = false;
    private ArrayList<Integer> keepSlots;
    private ArrayList<ItemStack> junkStacks;

    public static int localSlotToPacketSlot(int slot) {
        if (slot <= 8) return slot + 36;
        if (slot < 36) return slot;
        if (slot == 36) return 8;
        if (slot == 37) return 7;
        if (slot == 38) return 6;
        if (slot == 39) return 5;
        if (slot == 40) return 45;
        return slot;
    }

    private int configHotBarSlotToPacketSlot(int configSlot) {
        return configSlot + 35;
    }

    @SubscribeEvent
    public void onRenderTick(MotionEvent.Pre e) {
        if (motion != null) {
            C.p().setVelocity(motion);
            motion = null;
        }

        if (!actionQueue.isEmpty()) {
            if (
                ModuleManager.isEnabled(InventoryMove.class) &&
                InventoryMove.pauseMotion
            ) {
                C.p().setVelocity(0, C.p().getVelocity().y, 0);
            }

            if (Instant.now().toEpochMilli() - lastActionAt >= actionDelay) {
                Action act = actionQueue.poll();
                C.mc.interactionManager.clickSlot(
                    C.p().currentScreenHandler.syncId,
                    act.slotId,
                    act.button,
                    act.actionType,
                    C.p()
                );
                lastActionAt = Instant.now().toEpochMilli();
            }
        } else {
            if (!didDrop) {
                didDrop = true;
                procDrop();
            } else {
                if (!didRun) return;
                didRun = false;
                if (autoClose && !wantsToClose) {
                    wantsToClose = true;
                    invCloseAt = Instant.now().toEpochMilli();
                }
            }
        }
    }

    private void reset() {
        wasOpen = false;
        actionQueue.clear();
        lastActionAt = 0;
        didProc = false;
        wantsToClose = false;
        didRun = false;
        didDrop = false;
    }

    @Override
    protected void onEnable() {
        reset();
    }

    @Override
    protected void onDisable() {}

    private void closeInv() {
        reset();
        C.mc.currentScreen.close();
        C.mc.mouse.lockCursor();
    }

    @SubscribeEvent
    public void onUpdate(MotionEvent.Pre e) {
        if (C.mc.currentScreen instanceof InventoryScreen s) {
            if (
                wantsToClose &&
                Instant.now().toEpochMilli() - invCloseAt >= postDelay
            ) {
                closeInv();
                return;
            }

            if (wasOpen && !didProc) {
                if (Instant.now().toEpochMilli() - invOpenAt >= preDelay) {
                    didProc = true;
                    clean(C.p().getInventory());
                    didRun = true;
                }
            } else {
                invOpenAt = Instant.now().toEpochMilli();
                wasOpen = true;
            }
        } else {
            reset();
        }
    }

    private void clean(PlayerInventory inv) {
        // ------------------------------
        // Finding best items
        // ------------------------------
        ArrayList<ItemStack> invs = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (stack == null || stack.getItem() == Items.AIR) continue;
            invs.add(stack);
        }

        // Never drop armor
        keepSlots = Lists.newArrayList(36, 37, 38, 39);
        int bestSword = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.SWORD),
            inv
        );
        int bestGapple = configHotBarSlotToPacketSlot(gappleSlot) - 36;
        int bestBlock = configHotBarSlotToPacketSlot(blockSlot) - 36;
        int bestPearl = configHotBarSlotToPacketSlot(pearlSlot) - 36;
        int bestPickaxe = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.PICKAXE),
            inv
        );
        int bestAxe = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.AXE),
            inv
        );
        int bestBow = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.BOW),
            inv
        );
        int bestPotion = configHotBarSlotToPacketSlot(potionSlot) - 36;
        int bestHelmet = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.HELMET),
            inv
        );
        int bestChestplate = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.CHESTPLATE),
            inv
        );
        int bestLeggings = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.LEGGINGS),
            inv
        );
        int bestBoots = findLocalSlot(
            InvManagerUtil.getBest(invs, InvManagerUtil.ItemType.BOOTS),
            inv
        );
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (stack == null || stack.getItem() == Items.AIR) continue;
            Item it = stack.getItem();

            if (it == Items.GOLDEN_APPLE) {
                if (
                    (inv.getStack(
                                configHotBarSlotToPacketSlot(gappleSlot) - 36
                            ) ==
                            null ||
                        !(inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(gappleSlot) -
                                    36
                                )
                                .getItem() ==
                            Items.GOLDEN_APPLE) ||
                        inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(gappleSlot) -
                                    36
                                )
                                .getCount() <
                            stack.getCount())
                ) bestGapple = i;
                keepSlots.add(i);
            } else if (it instanceof BlockItem) {
                if (
                    (inv.getStack(
                                configHotBarSlotToPacketSlot(blockSlot) - 36
                            ) ==
                            null ||
                        !(inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(blockSlot) - 36
                                )
                                .getItem() instanceof
                            BlockItem) ||
                        inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(blockSlot) - 36
                                )
                                .getCount() <
                            stack.getCount())
                ) bestBlock = i;
                keepSlots.add(i);
            } else if (it == Items.ENDER_PEARL) {
                if (
                    (inv.getStack(
                                configHotBarSlotToPacketSlot(pearlSlot) - 36
                            ) ==
                            null ||
                        !(inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(pearlSlot) - 36
                                )
                                .getItem() ==
                            Items.ENDER_PEARL) ||
                        inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(pearlSlot) - 36
                                )
                                .getCount() <
                            stack.getCount())
                ) bestPearl = i;
                keepSlots.add(i);
            } else if (it instanceof PotionItem) { // TODO Potions
                if (
                    (inv.getStack(
                                configHotBarSlotToPacketSlot(potionSlot) - 36
                            ) ==
                            null ||
                        !(inv
                                .getStack(
                                    configHotBarSlotToPacketSlot(potionSlot) -
                                    36
                                )
                                .getItem() instanceof
                            PotionItem))
                ) bestPotion = i;
                keepSlots.add(i);
            }
        }

        boolean swordIsBetter =
            bestSword != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(configHotBarSlotToPacketSlot(swordSlot) - 36),
                inv.getStack(bestSword)
            );
        if (
            swordIsBetter ||
            InvManagerUtil.getType(inv.getStack(swordSlot - 1)) ==
                InvManagerUtil.ItemType.SWORD
        ) keepSlots.add(swordSlot - 1);
        boolean pickaxeIsBetter =
            bestPickaxe != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(configHotBarSlotToPacketSlot(pickaxeSlot) - 36),
                inv.getStack(bestPickaxe)
            );
        if (
            pickaxeIsBetter ||
            InvManagerUtil.getType(inv.getStack(pickaxeSlot - 1)) ==
                InvManagerUtil.ItemType.PICKAXE
        ) keepSlots.add(pickaxeSlot - 1);
        boolean axeIsBetter =
            bestAxe != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(configHotBarSlotToPacketSlot(axeSlot) - 36),
                inv.getStack(bestAxe)
            );
        if (
            axeIsBetter ||
            InvManagerUtil.getType(inv.getStack(axeSlot - 1)) ==
                InvManagerUtil.ItemType.AXE
        ) keepSlots.add(axeSlot - 1);
        boolean bowIsBetter =
            bestBow != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(configHotBarSlotToPacketSlot(bowSlot) - 36),
                inv.getStack(bestBow)
            );
        if (
            bowIsBetter ||
            InvManagerUtil.getType(inv.getStack(bowSlot - 1)) ==
                InvManagerUtil.ItemType.BOW
        ) keepSlots.add(bowSlot - 1);
        boolean helmetIsBetter =
            bestHelmet != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(39),
                inv.getStack(bestHelmet)
            );
        boolean chestplateIsBetter =
            bestChestplate != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(38),
                inv.getStack(bestChestplate)
            );
        boolean leggingsAreBetter =
            bestLeggings != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(37),
                inv.getStack(bestLeggings)
            );
        boolean bootsAreBetter =
            bestBoots != -1 &&
            InvManagerUtil.didIncreaseInValue(
                inv.getStack(36),
                inv.getStack(bestBoots)
            );

        // ------------------------------
        // Finding junk
        // ------------------------------
        junkStacks = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (stack == null || stack.getItem() == Items.AIR) continue;

            if (
                !keepSlots.contains(i) && InvManagerUtil.isJunk(stack)
            ) junkStacks.add(stack);
        }

        // ------------------------------
        // Equipping best items
        // ------------------------------
        // head --> feet, +5 (start 0)
        // First armor, sorted by (guessed) importance
        if (chestplateIsBetter) moveItem(bestChestplate, 6);
        if (leggingsAreBetter) moveItem(bestLeggings, 7);
        if (helmetIsBetter) moveItem(bestHelmet, 5);
        if (bootsAreBetter) moveItem(bestBoots, 8);
        // Then HotBar items, sorted by importance
        if (swordIsBetter) moveItem(
            bestSword,
            configHotBarSlotToPacketSlot(swordSlot)
        );
        moveItem(bestBlock, configHotBarSlotToPacketSlot(blockSlot));
        moveItem(bestGapple, configHotBarSlotToPacketSlot(gappleSlot));
        moveItem(bestPearl, configHotBarSlotToPacketSlot(pearlSlot));
        moveItem(bestPotion, configHotBarSlotToPacketSlot(potionSlot));
        if (bowIsBetter) moveItem(
            bestBow,
            configHotBarSlotToPacketSlot(bowSlot)
        );
        if (pickaxeIsBetter) moveItem(
            bestPickaxe,
            configHotBarSlotToPacketSlot(pickaxeSlot)
        );
        if (axeIsBetter) moveItem(
            bestAxe,
            configHotBarSlotToPacketSlot(axeSlot)
        );
        // ------------------------------
        // Dropping junk
        // ------------------------------
        // We do this last because it's the least important
        // This happens after all other actions finish
    }

    // Returns -1 if the stack is null
    private int findLocalSlot(ItemStack theStack, PlayerInventory inv) {
        if (theStack == null) return -1;
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (stack == null || stack.getItem() == Items.AIR) continue;

            if (InvManagerUtil.isSame(stack, theStack)) return i;
        }
        throw new RuntimeException("Item wasn't found");
    }

    private void moveItem(int startSlot, int endSlot) {
        int ss = localSlotToPacketSlot(startSlot);
        int es = endSlot;
        if (es == ss) return;
        boolean startIsHotbar = ss >= 36;
        boolean endIsHotbar = es >= 36;

        if (endIsHotbar) {
            actionQueue.add(new Action(ss, es - 36, SlotActionType.SWAP));
        } else {
            if (startIsHotbar) {
                actionQueue.add(new Action(es, ss - 36, SlotActionType.SWAP));
            } else {
                actionQueue.add(
                    new Action(
                        ss,
                        configHotBarSlotToPacketSlot(junkSlot) - 36,
                        SlotActionType.SWAP
                    )
                );
                actionQueue.add(
                    new Action(
                        es,
                        configHotBarSlotToPacketSlot(junkSlot) - 36,
                        SlotActionType.SWAP
                    )
                );
            }
        }
    }

    private void procDrop() {
        PlayerInventory inv = C.p().getInventory();
        for (ItemStack junkStack : junkStacks) {
            for (int i = 0; i < inv.size(); i++) {
                if (keepSlots.contains(i)) continue;
                ItemStack invStack = inv.getStack(i);
                if (
                    invStack == null || invStack.getItem() == Items.AIR
                ) continue;
                if (InvManagerUtil.isSame(invStack, junkStack)) drop(i);
            }
        }
    }

    private void drop(int slot) {
        int s = localSlotToPacketSlot(slot);
        actionQueue.add(new Action(s, 1, SlotActionType.THROW));
    }

    private record Action(int slotId, int button, SlotActionType actionType) {}
}
