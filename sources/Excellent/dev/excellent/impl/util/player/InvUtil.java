package dev.excellent.impl.util.player;

import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.impl.util.time.TimerUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class InvUtil implements IMinecraft {

    private static final int OFFHAND_SLOT_NUM = 40;

    public enum EnumSlotFindType {
        HOTBAR(0, 8),
        INVENTORY(9, 35),
        ARMOR(36, 39),
        OFFHAND(40, 40),
        FULL_INVENTORY(0, 40),
        DROP(-999, -999);
        private final int minSlot, maxSlot;

        EnumSlotFindType(int minSlot, int maxSlot) {
            this.minSlot = minSlot;
            this.maxSlot = maxSlot;
        }

        int getMin() {
            return this.minSlot;
        }

        int getMax() {
            return this.maxSlot;
        }

        boolean isIn(int key) {
            return key >= getMin() && key <= getMax();
        }
    }

    public static EnumSlotFindType[] ALL_SLOT_FIND_TYPES = new EnumSlotFindType[]{EnumSlotFindType.HOTBAR, EnumSlotFindType.ARMOR, EnumSlotFindType.DROP};
    public static EnumSlotFindType[] DEFAULT_SLOT_FIND_TYPES = new EnumSlotFindType[]{EnumSlotFindType.HOTBAR, EnumSlotFindType.INVENTORY};
    public static EnumSlotFindType[] DEFAULT_SLOT_FIND_TYPES_WITH_OFFHAND = new EnumSlotFindType[]{EnumSlotFindType.HOTBAR, EnumSlotFindType.INVENTORY, EnumSlotFindType.OFFHAND};
    private static final List<Integer> numbers = new ArrayList<>();

    public static int[] getSlotFindsNumbers(EnumSlotFindType... enumSlotFindTypes) {
        numbers.clear();
        for (EnumSlotFindType enumSlotFindType : enumSlotFindTypes) {
            for (int num = enumSlotFindType.getMin(); num < enumSlotFindType.getMax(); ++num) {
                int finalNum = num;
                if (numbers.stream().noneMatch(obj -> obj == finalNum)) numbers.add(num);
            }
        }
        return numbers.stream().mapToInt(i -> i).toArray();
    }

    @Getter
    public static class ItemStackWithSlot {
        private final ItemStack itemStack;
        private final int slotNumber;

        private ItemStackWithSlot(ItemStack itemStack, int slotNumber) {
            this.itemStack = itemStack;
            this.slotNumber = slotNumber;
        }

        public static ItemStackWithSlot gen(ItemStack itemStack, int slotNumber) {
            return new ItemStackWithSlot(itemStack, slotNumber);
        }

        public static ItemStackWithSlot getDefault() {
            return new ItemStackWithSlot(new ItemStack(Items.AIR, 1), -1);
        }
    }

    private static List<ItemStackWithSlot> stacksWithSlot;

    private static void addStackWithSlotToTepmoraryList(ItemStackWithSlot itemStackWithSlot) {
        if (stacksWithSlot.stream().noneMatch(stackWithSlot -> stackWithSlot.getItemStack() == itemStackWithSlot.getItemStack() && stackWithSlot.getSlotNumber() == itemStackWithSlot.getSlotNumber()))
            stacksWithSlot.add(itemStackWithSlot);
    }

    public static List<ItemStackWithSlot> getInventorySlots(int[] whiteSlotNumbers) {
        stacksWithSlot.clear();
        for (Integer slotNum : whiteSlotNumbers) {
            ItemStack stack = mc.player.inventory.mainInventory.get(slotNum);
            addStackWithSlotToTepmoraryList(ItemStackWithSlot.gen(stack, slotNum));
        }
        return stacksWithSlot;
    }

    public int findBestSlotInHotBar() {
        int emptySlot = findEmptySlot();
        if (emptySlot != -1) {
            return emptySlot;
        } else {
            return findNonSwordSlot();
        }
    }

    private int findEmptySlot() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).isEmpty() && mc.player.inventory.currentItem != i) {
                return i;
            }
        }
        return -1;
    }

    private int findNonSwordSlot() {
        for (int i = 0; i < 9; i++) {
            if (!(mc.player.inventory.getStackInSlot(i).getItem() instanceof SwordItem) && !(mc.player.inventory.getStackInSlot(i).getItem() instanceof ElytraItem) && mc.player.inventory.currentItem != i) {
                return i;
            }
        }
        return -1;
    }

    public List<ItemStackWithSlot> findItemsFromTypes(Item[] items, EnumSlotFindType... enumSlotFindTypes) {
        if (items == null || items.length == 0 || enumSlotFindTypes == null || enumSlotFindTypes.length == 0)
            return null;
        stacksWithSlot.clear();
        for (ItemStackWithSlot itemStackWithSlot : getInventorySlots(getSlotFindsNumbers(enumSlotFindTypes))) {
            for (Item item : items) {
                if (itemStackWithSlot.getItemStack().getItem() != item) continue;
                addStackWithSlotToTepmoraryList(itemStackWithSlot);
            }
        }
        return stacksWithSlot;
    }

    public List<ItemStackWithSlot> findItemFromTypes(Item items, EnumSlotFindType... enumSlotFindTypes) {
        return findItemsFromTypes(new Item[]{items}, enumSlotFindTypes);
    }

    public enum EnumWhiteInvActions {
        HOTBAR_SWAP(0), TRIPLE_CLICK(1), SWAP_CLICK(2), INV_HOTBAR_MOVE_SWAP(3);
        private final int actionKey;

        EnumWhiteInvActions(int actionKey) {
            this.actionKey = actionKey;
        }

        int getActionKey() {
            return this.actionKey;
        }
    }

    private static boolean hasSlotInWhiteSlotFindTypes(int checkSlot, EnumSlotFindType... enumSlotFindTypes) {
        return Arrays.stream(enumSlotFindTypes).noneMatch(slotFindType -> slotFindType.isIn(checkSlot));
    }

    private static final long[] swapDelays = new long[]{
            //HOTBAR_SWAP
            150L,
            //TRIPLE_CLICK
            250L,
            //SWAP_CLICK
            50L,
            //INV_HOTBAR_MOVE_SWAP
            200L
    };

    public static int findItemStackSlot(ItemStack toFindStack) {
        int slotNum = -1;
        for (int num = 0; num < mc.player.inventory.mainInventory.size(); ++num) {
            ItemStack itemStack = mc.player.inventory.mainInventory.get(num);
            if (itemStack == toFindStack) slotNum = num;
        }
        return slotNum;
    }

    public static ItemStackWithSlot findItemWithSlotFromTypes(Item item, EnumSlotFindType... enumSlotFindTypes) {
        return getInventorySlots(getSlotFindsNumbers(enumSlotFindTypes)).stream().filter(enumSlotFindType -> enumSlotFindType.getItemStack().getItem() == item).findFirst().orElse(ItemStackWithSlot.getDefault());
    }

    public static ItemStack getStackFromSlot(int slot) {
        return mc.player.inventory.mainInventory.get(slot);
    }

    private static EnumWhiteInvActions getBestInvSwapAction(int slotAt, int slotTo) {
        EnumWhiteInvActions enumWhiteInvAction = EnumWhiteInvActions.TRIPLE_CLICK;
        boolean slotAtInHotbar = slotAt >= 0 && slotAt <= 8, slotToInHotbar = slotTo >= 0 && slotTo <= 8;
        if (slotAtInHotbar && slotTo == OFFHAND_SLOT_NUM) {
            enumWhiteInvAction = EnumWhiteInvActions.HOTBAR_SWAP;
        }
        if (slotAtInHotbar && slotToInHotbar) {
            enumWhiteInvAction = EnumWhiteInvActions.SWAP_CLICK;
        }
        if (slotAtInHotbar != slotToInHotbar && slotTo == OFFHAND_SLOT_NUM) {
            enumWhiteInvAction = EnumWhiteInvActions.INV_HOTBAR_MOVE_SWAP;
        }
        return enumWhiteInvAction;
    }

    public static void winClickInv(int slot, int mouseButton, ClickType clickType) {
        mc.playerController.windowClick(0, slot, mouseButton, clickType, mc.player);
    }

    public static void moveInventoryItemStackToOffHand(int slot) {
        EnumWhiteInvActions invAction = getBestInvSwapAction(slot, OFFHAND_SLOT_NUM);
        ItemStack prevOffHandStack = mc.player.getHeldItemOffhand();
        boolean offhandNotStacked = prevOffHandStack.count == 1 || prevOffHandStack.getItem() == Items.AIR;
        int prevCurrentHandSlot = mc.player.inventory.currentItem;
        boolean canHeldDopping = (invAction == EnumWhiteInvActions.HOTBAR_SWAP || invAction == EnumWhiteInvActions.INV_HOTBAR_MOVE_SWAP || invAction == EnumWhiteInvActions.SWAP_CLICK) && prevOffHandStack.getItem() != Items.AIR;
        switch (invAction) {
            case HOTBAR_SWAP:
                mc.player.inventory.currentItem = slot;
                mc.playerController.syncCurrentPlayItem();
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
                mc.player.inventory.currentItem = prevCurrentHandSlot;
                mc.playerController.syncCurrentPlayItem();
                break;
            case TRIPLE_CLICK:
                boolean sendRC = getStackFromSlot(slot).count == 1 && offhandNotStacked;
                winClickInv(slot, sendRC ? 1 : 0, ClickType.PICKUP);
                winClickInv(OFFHAND_SLOT_NUM, offhandNotStacked ? 1 : 0, ClickType.PICKUP);
                winClickInv(slot, sendRC ? 1 : 0, ClickType.PICKUP);
                break;
            case INV_HOTBAR_MOVE_SWAP:
                winClickInv(slot, prevCurrentHandSlot, ClickType.SWAP);
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
                winClickInv(slot, prevCurrentHandSlot, ClickType.SWAP);
                break;
            case SWAP_CLICK:
                winClickInv(slot, prevCurrentHandSlot, ClickType.SWAP);
                break;
        }
        if (canHeldDopping) mc.player.setHeldItem(net.minecraft.util.Hand.OFF_HAND, mc.player.getHeldItemOffhand());
    }

    private void swapSlotToSlotProcess(int slotAt, int slotTo, TimerUtil timerUtil, EnumWhiteInvActions... whiteInvActions) {
        long delay = swapDelays[0];
        boolean hasTimeReach = timerUtil == null || timerUtil.hasReached(delay);
        if (!hasTimeReach || slotAt == -1 || hasSlotInWhiteSlotFindTypes(slotAt, DEFAULT_SLOT_FIND_TYPES) || hasSlotInWhiteSlotFindTypes(slotTo, DEFAULT_SLOT_FIND_TYPES_WITH_OFFHAND))
            return;


        if (timerUtil != null) timerUtil.reset();
    }

    public static void moveItem(int from, int to) {
        if (from == to)
            return;
        pickupItem(from, 0);
        pickupItem(to, 0);
        pickupItem(from, 0);
    }

    public static void pickupItem(int slot, int button) {
        mc.playerController.windowClick(0, slot, button, ClickType.PICKUP, mc.player);
    }

    public static void moveItem(int from, int to, boolean air) {

        if (from == to)
            return;
        pickupItem(from, 0);
        pickupItem(to, 0);
        if (air)
            pickupItem(from, 0);
    }


    public static int findEmptySlot(boolean inHotBar) {
        int start = inHotBar ? 0 : 9;
        int end = inHotBar ? 9 : 45;

        for (int i = start; i < end; ++i) {
            if (!mc.player.inventory.getStackInSlot(i).isEmpty()) {
                continue;
            }

            return i;
        }
        return -1;
    }

    public int getSlotInInventory(Item item) {
        int finalSlot = -1;
        for (int i = 0; i < 36; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                finalSlot = i;
            }
        }

        return finalSlot;
    }

    public int getSlotInInventoryOrHotbar(Item item, boolean inHotBar) {
        int firstSlot = inHotBar ? 0 : 9;
        int lastSlot = inHotBar ? 9 : 36;
        int finalSlot = -1;
        for (int i = firstSlot; i < lastSlot; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                finalSlot = i;
            }
        }

        return finalSlot;
    }

    public static class Hand {
        public static boolean isEnabled;
        private boolean isChangingItem;
        @Setter
        private int originalSlot = -1;

        public void onEventPacket(PacketEvent eventPacket) {
            if (!eventPacket.isReceive()) {
                return;
            }
            if (eventPacket.getPacket() instanceof SHeldItemChangePacket) {
                this.isChangingItem = true;
            }
        }

        public void handleItemChange(boolean resetItem) {
            if (this.isChangingItem && this.originalSlot != -1) {
                isEnabled = true;
                mc.player.inventory.currentItem = this.originalSlot;
                if (resetItem) {
                    this.isChangingItem = false;
                    this.originalSlot = -1;
                    isEnabled = false;
                }
            }
        }

    }
}
