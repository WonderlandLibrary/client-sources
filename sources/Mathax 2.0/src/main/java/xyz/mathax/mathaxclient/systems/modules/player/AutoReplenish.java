package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.ItemStackAccessor;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.combat.AutoTotem;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.SlotUtils;

import java.util.List;

public class AutoReplenish extends Module {
    private final ItemStack[] items = new ItemStack[10];

    private boolean prevHadOpenScreen;

    private int tickDelayLeft;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Integer> thresholdSetting = generalSettings.add(new IntSetting.Builder()
            .name("Threshold")
            .description("The threshold of items left this actives at.")
            .defaultValue(8)
            .min(1)
            .sliderRange(1, 63)
            .build()
    );

    private final Setting<Integer> tickDelaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The tick delay to replenish your hotbar.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    private final Setting<Boolean> offhandSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Offhand")
            .description("Whether or not to refill your offhand with items.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> unstackableSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Unstackable")
            .description("Replenish unstackable items.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> searchHotbarSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Search hotbar")
            .description("Use items in your hotbar to replenish if they are the only ones left.")
            .defaultValue(true)
            .build()
    );

    private final Setting<List<Item>> excludedItemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Excluded items")
            .description("Items that WILL NOT replenish.")
            .build()
    );

    public AutoReplenish(Category category) {
        super(category, "Auto Replenish", "Automatically refills items in your hotbar, main hand, or offhand.");

        for (int i = 0; i < items.length; i++) {
            items[i] = new ItemStack(Items.AIR);
        }
    }

    @Override
    public void onEnable() {
        fillItems();

        tickDelayLeft = tickDelaySetting.get();

        prevHadOpenScreen = mc.currentScreen != null;
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.currentScreen == null && prevHadOpenScreen) {
            fillItems();
        }

        prevHadOpenScreen = mc.currentScreen != null;
        if (mc.player.currentScreenHandler.getStacks().size() != 46 || mc.currentScreen != null) {
            return;
        }

        if (tickDelayLeft <= 0) {
            tickDelayLeft = tickDelaySetting.get();

            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);
                checkSlot(i, stack);
            }

            if (offhandSetting.get() && !Modules.get().get(AutoTotem.class).isLocked()) {
                ItemStack stack = mc.player.getOffHandStack();
                checkSlot(SlotUtils.OFFHAND, stack);
            }
        } else {
            tickDelayLeft--;
        }
    }

    private void checkSlot(int slot, ItemStack stack) {
        ItemStack prevStack = getItem(slot);
        if (!stack.isEmpty() && stack.isStackable() && !excludedItemsSetting.get().contains(stack.getItem())) {
            if (stack.getCount() <= thresholdSetting.get()) {
                addSlots(slot, findItem(stack, slot, thresholdSetting.get() - stack.getCount() + 1));
            }
        }

        if (stack.isEmpty() && !prevStack.isEmpty() && !excludedItemsSetting.get().contains(prevStack.getItem())) {
            if (prevStack.isStackable()) {
                addSlots(slot, findItem(prevStack, slot, thresholdSetting.get() - stack.getCount() + 1));
            } else {
                if (unstackableSetting.get()) {
                    addSlots(slot, findItem(prevStack, slot, 1));
                }
            }
        }

        setItem(slot, stack);
    }

    private int findItem(ItemStack itemStack, int excludedSlot, int goodEnoughCount) {
        int slot = -1;
        int count = 0;
        for (int i = mc.player.getInventory().size() - 2; i >= (searchHotbarSetting.get() ? 0 : 9); i--) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (i != excludedSlot && stack.getItem() == itemStack.getItem() && ItemStack.areNbtEqual(itemStack, stack)) {
                if (stack.getCount() > count) {
                    slot = i;
                    count = stack.getCount();

                    if (count >= goodEnoughCount) {
                        break;
                    }
                }
            }
        }

        return slot;
    }

    private void addSlots(int to, int from) {
        InvUtils.move().from(from).to(to);
    }

    private void fillItems() {
        for (int i = 0; i < 9; i++) {
            setItem(i, mc.player.getInventory().getStack(i));
        }

        setItem(SlotUtils.OFFHAND, mc.player.getOffHandStack());
    }

    private ItemStack getItem(int slot) {
        if (slot == SlotUtils.OFFHAND) {
            slot = 9;
        }

        return items[slot];
    }

    private void setItem(int slot, ItemStack stack) {
        if (slot == SlotUtils.OFFHAND) {
            slot = 9;
        }

        ItemStack itemStack = items[slot];
        ((ItemStackAccessor) (Object) itemStack).setItem(stack.getItem());
        itemStack.setCount(stack.getCount());
        itemStack.setNbt(stack.getNbt());
        ((ItemStackAccessor) (Object) itemStack).setEmpty(stack.isEmpty());
    }
}
