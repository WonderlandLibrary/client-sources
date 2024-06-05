// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import net.minecraft.inventory.IInventory;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.inventory.GuiChest;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import java.util.List;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "ChestStealer", renderName = "Chest Stealer", category = ModuleCategory.PLAYER)
public final class ChestStealerModule extends Module
{
    private final List<Integer> lootedChests;
    public final NumberProperty<Long> clickDelayProperty;
    public final NumberProperty<Long> closeDelayProperty;
    public final BooleanProperty nameCheckProperty;
    public final BooleanProperty archeryProperty;
    public final BooleanProperty silentProperty;
    private boolean containerOpen;
    private final TimerUtil timer;
    private final EventListener<EventUpdate> eventEventCallback;
    private final EventListener<EventReceivePacket> onPacketReceiveEvent;
    private final EventListener<EventSendPacket> sendPacketEventListener;
    
    public ChestStealerModule() {
        this.lootedChests = new ArrayList<Integer>();
        this.clickDelayProperty = new NumberProperty<Long>("Click Delay", 100L, 10L, 500L, 10L);
        this.closeDelayProperty = new NumberProperty<Long>("Close Delay", 100L, 10L, 500L, 10L);
        this.nameCheckProperty = new BooleanProperty("Name Check", true);
        this.archeryProperty = new BooleanProperty("Archery", false);
        this.silentProperty = new BooleanProperty("Silent", false);
        this.timer = new TimerUtil();
        this.eventEventCallback = (e -> {
            if (e.isPre() && this.mc.currentScreen instanceof GuiChest) {
                final GuiChest chest = (GuiChest)this.mc.currentScreen;
                final IInventory lowerChestInv = chest.lowerChestInventory;
                if (lowerChestInv.getDisplayName().getUnformattedText().contains("Chest") || !this.nameCheckProperty.getValue()) {
                    this.containerOpen = true;
                    if (!Mouse.isGrabbed() && this.silentProperty.getValue()) {
                        this.mc.inGameHasFocus = true;
                        this.mc.mouseHelper.grabMouseCursor();
                    }
                    if (this.isInventoryFull() || PlayerUtils.isInventoryEmpty(lowerChestInv, this.archeryProperty.getValue())) {
                        if (this.timer.hasElapsed(this.closeDelayProperty.getValue())) {
                            this.mc.thePlayer.closeScreen();
                        }
                    }
                    else {
                        int i = 0;
                        while (i < lowerChestInv.getSizeInventory()) {
                            if (this.timer.hasElapsed(this.clickDelayProperty.getValue()) && PlayerUtils.isValid(lowerChestInv.getStackInSlot(i), this.archeryProperty.getValue())) {
                                PlayerUtils.windowClick(chest.inventorySlots.windowId, i, 0, PlayerUtils.ClickType.SHIFT_CLICK);
                                this.timer.reset();
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                }
                else {
                    this.containerOpen = false;
                }
            }
            return;
        });
        this.onPacketReceiveEvent = (event -> {});
        this.sendPacketEventListener = (event -> {});
    }
    
    private boolean isInventoryFull() {
        for (int i = 9; i < 45; ++i) {
            if (!this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                return false;
            }
        }
        return true;
    }
}
