package xyz.mathax.mathaxclient.systems.modules.misc;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Wearable;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.DropItemsEvent;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseButtonEvent;
import xyz.mathax.mathaxclient.events.packets.InventoryEvent;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.mixin.CloseHandledScreenC2SPacketAccessor;
import xyz.mathax.mathaxclient.mixin.HandledScreenAccessor;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import xyz.mathax.mathaxclient.utils.network.Executor;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import xyz.mathax.mathaxclient.utils.player.InventorySorter;
import xyz.mathax.mathaxclient.utils.player.SlotUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InventoryTweaks extends Module {
    private InventorySorter sorter;

    private boolean inventoryOpened;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup sortingSettings = settings.createGroup("Sorting");
    private final SettingGroup autoDropSettings = settings.createGroup("Auto Drop");
    private final SettingGroup autoStealOrDumpSettings = settings.createGroup("Auto Steal or Dump");

    // General

    private final Setting<Boolean> mouseDragItemMoveSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Mouse drag item move")
            .description("Moving mouse over items while holding shift will transfer it to the other container.")
            .defaultValue(true)
            .build()
    );

    private final Setting<List<Item>> antiDropItemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Anti drop items")
            .description("Items to prevent dropping. Doesn't work in creative inventory screen.")
            .build()
    );

    private final Setting<Boolean> buttonsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("inventory-buttons")
            .description("Show steal and dump buttons in container guis.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> xCarrySetting = generalSettings.add(new BoolSetting.Builder()
            .name("XCarry")
            .description("Allows you to store four extra item stacks in your crafting grid.")
            .defaultValue(true)
            .onChanged(value -> {
                if (value || !Utils.canUpdate()) {
                    return;
                }

                mc.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(mc.player.playerScreenHandler.syncId));
                inventoryOpened = false;
            })
            .build()
    );

    private final Setting<Boolean> armorStorageSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Armor storage")
            .description("Allows you to put normal items in your armor slots.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> armorSwapSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Armor swap")
            .description("Lets you swap between pieces of armor by right clicking on it.")
            .defaultValue(true)
            .build()
    );

    // Sorting

    private final Setting<Boolean> sortingSetting = sortingSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Automatically sort stacks in inventory.")
            .defaultValue(true)
            .build()
    );

    private final Setting<KeyBind> sortingKeySetting = sortingSettings.add(new KeyBindSetting.Builder()
            .name("KeyBind")
            .description("Key to trigger the sort.")
            .visible(sortingSetting::get)
            .defaultValue(KeyBind.fromButton(GLFW.GLFW_MOUSE_BUTTON_MIDDLE))
            .build()
    );

    private final Setting<Integer> sortingDelaySetting = sortingSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("Delay in ticks between moving items when sorting.")
            .visible(sortingSetting::get)
            .defaultValue(1)
            .min(0)
            .build()
    );

    // Auto Drop

    private final Setting<List<Item>> autoDropItemsSetting = autoDropSettings.add(new ItemListSetting.Builder()
            .name("Items")
            .description("Items to drop.")
            .defaultValue(new ArrayList<>())
            .build()
    );

    private final Setting<Boolean> autoDropExcludeHotbarSetting = autoDropSettings.add(new BoolSetting.Builder()
            .name("Exclude hotbar")
            .description("Whether or not to drop items from your hotbar.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> autoDropOnlyFullStacksSetting = autoDropSettings.add(new BoolSetting.Builder()
            .name("Only full stacks")
            .description("Only drop the items if the stack is full.")
            .defaultValue(false)
            .build()
    );

    // Auto Steal or Dump

    private final Setting<AutoStealOrDumpMode> autoStealOrDumpModeSetting = autoStealOrDumpSettings.add(new EnumSetting.Builder<AutoStealOrDumpMode>()
            .name("Mode")
            .description("Determines if the module will steal or dump to containers.")
            .defaultValue(AutoStealOrDumpMode.Steal)
            .build()
    );

    private final Setting<Integer> autoStealOrDumpDelaySetting = autoStealOrDumpSettings.add(new IntSetting.Builder()
            .name("delay")
            .description("The minimum delay between stealing the next stack in milliseconds.")
            .defaultValue(20)
            .sliderRange(0, 1000)
            .build()
    );

    private final Setting<Integer> autoStealOrDumpRandomDelaySetting = autoStealOrDumpSettings.add(new IntSetting.Builder()
            .name("random")
            .description("Randomly add a delay of up to the specified time in milliseconds.")
            .min(0)
            .sliderRange(0, 1000)
            .defaultValue(50)
            .build()
    );

    public InventoryTweaks(Category category) {
        super(category, "Inventory Tweaks", "Various inventory related utilities.");
    }

    @Override
    public void onEnable() {
        inventoryOpened = false;
    }

    @Override
    public void onDisable() {
        sorter = null;

        if (inventoryOpened) {
            mc.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(mc.player.playerScreenHandler.syncId));
        }
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (event.action != KeyAction.Press) {
            return;
        }

        if (sortingKeySetting.get().matches(true, event.key)) {
            if (sort()) {
                event.cancel();
            }
        }

        if (mc.options.useKey.matchesKey(event.key, 0) && armorSwap()) {
            if (swapArmor()) {
                event.cancel();
            }
        }
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action != KeyAction.Press) {
            return;
        }

        if (sortingKeySetting.get().matches(false, event.button)) {
            if (sort()) {
                event.cancel();
            }
        }

        if (mc.options.useKey.matchesMouse(event.button) && armorSwap()) {
            if (swapArmor()) {
                event.cancel();
            }
        }
    }

    private boolean sort() {
        if (!sortingSetting.get() || !(mc.currentScreen instanceof HandledScreen<?> screen) || sorter != null) {
            return false;
        }

        if (!mc.player.currentScreenHandler.getCursorStack().isEmpty()) {
            FindItemResult empty = InvUtils.findEmpty();
            if (!empty.found()) {
                InvUtils.click().slot(-999);
            } else {
                InvUtils.click().slot(empty.slot());
            }
        }

        Slot focusedSlot = ((HandledScreenAccessor) screen).getFocusedSlot();
        if (focusedSlot == null) {
            return false;
        }

        sorter = new InventorySorter(screen, focusedSlot);
        return true;
    }

    private boolean swapArmor() {
        if (mc.currentScreen != null) {
            if (!(mc.currentScreen instanceof InventoryScreen screen)) {
                return false;
            }

            Slot focusedSlot = ((HandledScreenAccessor) screen).getFocusedSlot();
            if (focusedSlot == null || !isWearable(focusedSlot.getStack())) {
                return false;
            }

            ItemStack itemStack = focusedSlot.getStack();
            EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(itemStack);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, SlotUtils.indexToId(focusedSlot.getIndex()), SlotUtils.ARMOR_START + equipmentSlot.getEntitySlotId(), SlotActionType.SWAP, mc.player);
        } else {
            ItemStack itemStack = mc.player.getMainHandStack();
            if (!isWearable(itemStack)) {
                return false;
            }

            EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(itemStack);
            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, SlotUtils.indexToId(SlotUtils.ARMOR_START + (3 - equipmentSlot.getEntitySlotId())), mc.player.getInventory().selectedSlot, SlotActionType.SWAP, mc.player);
        }

        return true;
    }

    private boolean isWearable(ItemStack itemStack) {
        Item item = itemStack.getItem();

        if (item instanceof Wearable) {
            return true;
        }

        return item instanceof BlockItem blockItem && (blockItem.getBlock() instanceof AbstractSkullBlock || blockItem.getBlock() instanceof CarvedPumpkinBlock);
    }

    @EventHandler
    private void onOpenScreen(OpenScreenEvent event) {
        sorter = null;
    }

    @EventHandler
    private void onTickPre(TickEvent.Pre event) {
        if (sorter != null && sorter.tick(sortingDelaySetting.get())) {
            sorter = null;
        }
    }

    @EventHandler
    private void onTickPost(TickEvent.Post event) {
        if (mc.currentScreen instanceof HandledScreen<?> || autoDropItemsSetting.get().isEmpty()) {
            return;
        }

        for (int i = autoDropExcludeHotbarSetting.get() ? 9 : 0; i < mc.player.getInventory().size(); i++) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (autoDropItemsSetting.get().contains(itemStack.getItem())) {
                if (!autoDropOnlyFullStacksSetting.get() || itemStack.getCount() == itemStack.getMaxCount()) {
                    InvUtils.drop().slot(i);
                }
            }
        }
    }

    @EventHandler
    private void onDropItems(DropItemsEvent event) {
        if (antiDropItemsSetting.get().contains(event.itemStack.getItem())) {
            event.cancel();
        }
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!xCarrySetting.get() || !(event.packet instanceof CloseHandledScreenC2SPacket)) {
            return;
        }

        if (((CloseHandledScreenC2SPacketAccessor) event.packet).getSyncId() == mc.player.playerScreenHandler.syncId) {
            inventoryOpened = true;
            event.cancel();
        }
    }

    private int getSleepTime() {
        return autoStealOrDumpDelaySetting.get() + (autoStealOrDumpRandomDelaySetting.get() > 0 ? ThreadLocalRandom.current().nextInt(0, autoStealOrDumpRandomDelaySetting.get()) : 0);
    }

    private int getRows(ScreenHandler handler) {
        return (handler instanceof GenericContainerScreenHandler ? ((GenericContainerScreenHandler) handler).getRows() : 3);
    }

    private void moveSlots(ScreenHandler handler, int start, int end) {
        for (int i = start; i < end; i++) {
            if (!handler.getSlot(i).hasStack()) {
                continue;
            }

            int sleep = getSleepTime();
            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (mc.currentScreen == null) {
                break;
            }

            InvUtils.quickMove().slotId(i);
        }
    }

    public void steal(ScreenHandler handler) {
        Executor.execute(() -> moveSlots(handler, 0, getRows(handler) * 9));
    }

    public void dump(ScreenHandler handler) {
        int playerInvOffset = getRows(handler) * 9;
        Executor.execute(() -> moveSlots(handler, playerInvOffset, playerInvOffset + 4 * 9));
    }

    public boolean showButtons() {
        return isEnabled() && buttonsSetting.get();
    }

    public boolean mouseDragItemMove() {
        return isEnabled() && mouseDragItemMoveSetting.get();
    }

    public boolean armorStorage() {
        return isEnabled() && armorStorageSetting.get();
    }

    public boolean armorSwap() {
        return isEnabled() && armorSwapSetting.get();
    }

    @EventHandler
    private void onInventory(InventoryEvent event) {
        ScreenHandler handler = mc.player.currentScreenHandler;
        if (event.packet.getSyncId() == handler.syncId) {
            if (handler instanceof GenericContainerScreenHandler || handler instanceof ShulkerBoxScreenHandler) {
                if (autoStealOrDumpModeSetting.get() == AutoStealOrDumpMode.Steal) {
                    steal(handler);
                } else {
                    dump(handler);
                }
            }
        }
    }

    public enum AutoStealOrDumpMode {
        Steal("Steal"),
        Dump("Dump");

        private final String name;

        AutoStealOrDumpMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}