package dev.lvstrng.argon.modules.impl;

import dev.lvstrng.argon.event.listeners.TickListener;
import dev.lvstrng.argon.modules.Category;
import dev.lvstrng.argon.modules.Module;
import dev.lvstrng.argon.modules.setting.Setting;
import dev.lvstrng.argon.modules.setting.settings.BooleanSetting;
import dev.lvstrng.argon.modules.setting.settings.EnumSetting;
import dev.lvstrng.argon.modules.setting.settings.IntSetting;
import dev.lvstrng.argon.utils.CustomInventoryScreen;
import dev.lvstrng.argon.utils.InventoryUtil;
import dev.lvstrng.argon.utils.Mode;
import dev.lvstrng.argon.utils.Timer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public final class AutoInventoryTotem extends Module implements TickListener {
    private final EnumSetting modeSetting;
    private final IntSetting delaySetting;
    private final IntSetting totemSlotSetting;
    private final BooleanSetting autoSwitchSetting;
    private final BooleanSetting forceTotemSetting;
    private final BooleanSetting autoOpenSetting;
    private final IntSetting stayOpenForSetting;
    private final Timer delayTimer;
    private final Timer stayOpenTimer;
    private int delayCounter;
    private int stayOpenCounter;

    public AutoInventoryTotem() {
        super("Auto Inventory Totem", "Automatically equips a totem in your offhand and main hand if empty", 0, Category.COMBAT);
        this.modeSetting = new EnumSetting("Mode", Mode.RANDOM, Mode.class).setDescription("Whether to randomize the toteming pattern or no");
        this.delaySetting = new IntSetting("Delay", 0.0, 20.0, 0.0, 1.0);
        this.totemSlotSetting = new IntSetting("Totem Slot", 1.0, 9.0, 1.0, 1.0).setDescription("Your preferred totem slot");
        this.autoSwitchSetting = new BooleanSetting("Auto Switch", false).setDescription("Switches to totem slot when going inside the inventory");
        this.forceTotemSetting = new BooleanSetting("Force Totem", false).setDescription("Puts the totem in the slot, regardless if its space is taken up by something else");
        this.autoOpenSetting = new BooleanSetting("Auto Open", false).setDescription("Automatically opens and closes the inventory for you");
        this.stayOpenForSetting = new IntSetting("Stay Open For", 0.0, 20.0, 0.0, 1.0);
        this.delayCounter = -1;
        this.stayOpenCounter = -1;
        this.delayTimer = new Timer();
        this.stayOpenTimer = new Timer();
        this.addSettings(new Setting[]{
                this.modeSetting,
                this.delaySetting,
                this.totemSlotSetting,
                this.autoSwitchSetting,
                this.forceTotemSetting,
                this.autoOpenSetting,
                this.stayOpenForSetting
        });
    }

    private static boolean isTotemItem(Item item) {
        return item == Items.TOTEM_OF_UNDYING;
    }

    @Override
    public void onEnable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        this.delayCounter = -1;
        this.stayOpenCounter = -1;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.eventBus.registerPriorityListener(TickListener.class, this);
        super.onDisable();
    }

    @Override
    public void onTick() {
        if (shouldOpenInventory() && this.autoOpenSetting.getValue()) {
            this.mc.setScreen(new CustomInventoryScreen(this.mc.player));
        }

        if (!(this.mc.currentScreen instanceof InventoryScreen) && !(this.mc.currentScreen instanceof CustomInventoryScreen)) {
            resetCounters();
            return;
        }

        if (this.delayCounter == -1) {
            this.delayCounter = this.delaySetting.getValueInt();
        }

        if (this.stayOpenCounter == -1) {
            this.stayOpenCounter = this.stayOpenForSetting.getValueInt();
        }

        if (this.delayCounter > 0) {
            --this.delayCounter;
        }

        PlayerInventory playerInventory = this.mc.player.getInventory();

        if (this.autoSwitchSetting.getValue()) {
            playerInventory.selectedSlot = this.totemSlotSetting.getValueInt() - 1;
        }

        if (this.delayCounter <= 0) {
            if (!isTotemInOffHand()) {
                int totemSlot = getTotemSlot();
                if (totemSlot != -1) {
                    swapItemInSlot(totemSlot, 40);
                    return;
                }
            }

            ItemStack mainHandItem = this.mc.player.getMainHandStack();
            if (mainHandItem.isEmpty() || (this.forceTotemSetting.getValue() && mainHandItem.getItem() != Items.TOTEM_OF_UNDYING)) {
                int totemSlot = getTotemSlot();
                if (totemSlot != -1) {
                    swapItemInSlot(totemSlot, playerInventory.selectedSlot);
                    return;
                }
            }

            if (shouldCloseInventory() && this.autoOpenSetting.getValue()) {
                if (this.stayOpenCounter != 0) {
                    --this.stayOpenCounter;
                    return;
                }
                this.mc.currentScreen.close();
                this.stayOpenCounter = this.stayOpenForSetting.getValueInt();
            }
        }
    }

    private void resetCounters() {
        this.delayCounter = -1;
        this.stayOpenCounter = -1;
    }

    private boolean isTotemInOffHand() {
        return this.mc.player.getInventory().offHand.get(0).getItem() == Items.TOTEM_OF_UNDYING;
    }

    private int getTotemSlot() {
        return this.modeSetting.is(Mode.RANDOM) ? InventoryUtil.method315() : InventoryUtil.method317();
    }

    private void swapItemInSlot(int fromSlot, int toSlot) {
        this.mc.interactionManager.clickSlot(((InventoryScreen) this.mc.currentScreen).getScreenHandler().syncId, fromSlot, toSlot, SlotActionType.SWAP, this.mc.player);
    }

    private boolean shouldCloseInventory() {
        return isTotemInHand() && this.mc.currentScreen instanceof CustomInventoryScreen;
    }

    private boolean isTotemInHand() {
        return this.mc.player.getInventory().getStack(this.totemSlotSetting.getValueInt() - 1).getItem() == Items.TOTEM_OF_UNDYING
                && this.mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING;
    }

    private boolean shouldOpenInventory() {
        return (this.mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING
                || this.mc.player.getInventory().getStack(this.totemSlotSetting.getValueInt() - 1).getItem() != Items.TOTEM_OF_UNDYING)
                && !(this.mc.currentScreen instanceof CustomInventoryScreen)
                && InventoryUtil.method312(item -> item.equals(Items.TOTEM_OF_UNDYING)) != 0;
    }
}