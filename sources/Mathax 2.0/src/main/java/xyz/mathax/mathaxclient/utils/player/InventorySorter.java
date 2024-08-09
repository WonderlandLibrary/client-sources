package xyz.mathax.mathaxclient.utils.player;

import xyz.mathax.mathaxclient.mixininterface.ISlot;
import xyz.mathax.mathaxclient.utils.render.PeekScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InventorySorter {
    private final HandledScreen<?> screen;
    private final InventoryPart originInvPart;

    private boolean invalid;
    private List<Action> actions;
    private int timer, currentActionI;

    public InventorySorter(HandledScreen<?> screen, Slot originSlot) {
        this.screen = screen;

        this.originInvPart = getInvPart(originSlot);
        if (originInvPart == InventoryPart.Invalid || originInvPart == InventoryPart.Hotbar || screen instanceof PeekScreen) {
            invalid = true;
            return;
        }

        this.actions = new ArrayList<>();

        generateActions();
    }

    public boolean tick(int delay) {
        if (invalid) {
            return true;
        }

        if (currentActionI >= actions.size()) {
            return true;
        }

        if (timer >= delay) {
            timer = 0;
        } else {
            timer++;
            return false;
        }

        Action action = actions.get(currentActionI);
        InvUtils.move().fromId(action.from).toId(action.to);

        currentActionI++;
        return false;
    }

    private void generateActions() {
        List<MySlot> slots = new ArrayList<>();
        for (Slot slot : screen.getScreenHandler().slots) {
            if (getInvPart(slot) == originInvPart) {
                slots.add(new MySlot(((ISlot) slot).getId(), slot.getStack()));
            }
        }

        slots.sort(Comparator.comparingInt(value -> value.id));

        generateStackingActions(slots);
        generateSortingActions(slots);
    }

    private void generateStackingActions(List<MySlot> slots) {
        SlotMap slotMap = new SlotMap();
        for (MySlot slot : slots) {
            if (slot.itemStack.isEmpty() || !slot.itemStack.isStackable() || slot.itemStack.getCount() >= slot.itemStack.getMaxCount()) {
                continue;
            }

            slotMap.get(slot.itemStack).add(slot);
        }

        for (var entry : slotMap.map) {
            List<MySlot> slotsToStack = entry.getRight();
            MySlot slotToStackTo = null;
            for (int i = 0; i < slotsToStack.size(); i++) {
                MySlot slot = slotsToStack.get(i);
                if (slotToStackTo == null) {
                    slotToStackTo = slot;
                    continue;
                }

                actions.add(new Action(slot.id, slotToStackTo.id));

                if (slotToStackTo.itemStack.getCount() + slot.itemStack.getCount() <= slotToStackTo.itemStack.getMaxCount()) {
                    slotToStackTo.itemStack = new ItemStack(slotToStackTo.itemStack.getItem(), slotToStackTo.itemStack.getCount() + slot.itemStack.getCount());
                    slot.itemStack = ItemStack.EMPTY;

                    if (slotToStackTo.itemStack.getCount() >= slotToStackTo.itemStack.getMaxCount()) {
                        slotToStackTo = null;
                    }
                } else {
                    int needed = slotToStackTo.itemStack.getMaxCount() - slotToStackTo.itemStack.getCount();
                    slotToStackTo.itemStack = new ItemStack(slotToStackTo.itemStack.getItem(), slotToStackTo.itemStack.getMaxCount());
                    slot.itemStack = new ItemStack(slot.itemStack.getItem(), slot.itemStack.getCount() - needed);

                    slotToStackTo = null;
                    i--;
                }
            }
        }
    }

    private void generateSortingActions(List<MySlot> slots) {
        for (int i = 0; i < slots.size(); i++) {
            MySlot bestSlot = null;
            for (int j = i; j < slots.size(); j++) {
                MySlot slot = slots.get(j);
                if (bestSlot == null) {
                    bestSlot = slot;
                    continue;
                }

                if (isSlotBetter(bestSlot, slot)) {
                    bestSlot = slot;
                }
            }

            if (!bestSlot.itemStack.isEmpty()) {
                MySlot toSlot = slots.get(i);
                int from = bestSlot.id;
                int to = toSlot.id;
                if (from != to) {
                    ItemStack temp = bestSlot.itemStack;
                    bestSlot.itemStack = toSlot.itemStack;
                    toSlot.itemStack = temp;

                    actions.add(new Action(from, to));
                }
            }
        }
    }

    private boolean isSlotBetter(MySlot best, MySlot slot) {
        ItemStack bestI = best.itemStack;
        ItemStack slotI = slot.itemStack;
        if (bestI.isEmpty() && !slotI.isEmpty()) {
            return true;
        } else if (!bestI.isEmpty() && slotI.isEmpty()) {
            return false;
        }

        int id = Registries.ITEM.getId(bestI.getItem()).compareTo(Registries.ITEM.getId(slotI.getItem()));
        if (id == 0) {
            return slotI.getCount() > bestI.getCount();
        }

        return id > 0;
    }

    private InventoryPart getInvPart(Slot slot) {
        int i = ((ISlot) slot).getIndex();
        if (slot.inventory instanceof PlayerInventory && (!(screen instanceof CreativeInventoryScreen) || ((ISlot) slot).getId() > 8)) {
            if (SlotUtils.isHotbar(i)) {
                return InventoryPart.Hotbar;
            } else if (SlotUtils.isMain(i)) {
                return InventoryPart.Player;
            }
        } else if ((screen instanceof GenericContainerScreen || screen instanceof ShulkerBoxScreen) && slot.inventory instanceof SimpleInventory) {
            return InventoryPart.Main;
        }

        return InventoryPart.Invalid;
    }

    private enum InventoryPart {
        Hotbar,
        Player,
        Main,
        Invalid
    }

    private static class MySlot {
        public final int id;
        public ItemStack itemStack;

        public MySlot(int id, ItemStack itemStack) {
            this.id = id;
            this.itemStack = itemStack;
        }
    }

    private static class SlotMap {
        private final List<Pair<ItemStack, List<MySlot>>> map = new ArrayList<>();

        public List<MySlot> get(ItemStack itemStack) {
            for (var entry : map) {
                if (areEqual(itemStack, entry.getLeft())) {
                    return entry.getRight();
                }
            }

            List<MySlot> list = new ArrayList<>();
            map.add(new Pair<>(itemStack, list));
            return list;
        }

        private boolean areEqual(ItemStack itemStack1, ItemStack itemStack2) {
            if (!itemStack1.isOf(itemStack2.getItem()) || (itemStack1.getNbt() == null && itemStack2.getNbt() != null)) {
                return false;
            }

            return itemStack1.getNbt() == null || itemStack1.getNbt().equals(itemStack2.getNbt());
        }
    }

    private record Action(int from, int to) {}
}
