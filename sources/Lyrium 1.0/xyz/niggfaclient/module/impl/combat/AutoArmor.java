// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.combat;

import net.minecraft.item.ItemStack;
import xyz.niggfaclient.utils.player.InventoryUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AutoArmor", description = "Automatically puts armor on you", cat = Category.COMBAT)
public class AutoArmor extends Module
{
    private final DoubleProperty armorDelay;
    public final Property<Boolean> openInventory;
    private int delay;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public AutoArmor() {
        this.armorDelay = new DoubleProperty("Delay", 10.0, 0.0, 50.0, 1.0, Representation.MILLISECONDS);
        this.openInventory = new Property<Boolean>("Open Inventory", false);
        this.updateEventListener = (e -> {
            if (!this.openInventory.getValue() || this.mc.currentScreen != null) {
                if (!(this.mc.currentScreen instanceof GuiChest)) {
                    this.getBestArmor();
                }
                ++this.delay;
            }
        });
    }
    
    public void getBestArmor() {
        ++this.delay;
        if (this.delay >= this.armorDelay.getValue() && (this.mc.thePlayer.openContainer == null || this.mc.thePlayer.openContainer.windowId == 0)) {
            for (int type = 1; type < 5; ++type) {
                if (this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
                    final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                    if (InventoryUtils.isBestArmor(is, type)) {
                        continue;
                    }
                    InventoryUtils.drop(4 + type);
                }
                for (int i = 9; i < 45; ++i) {
                    if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                        final ItemStack is2 = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                        if (InventoryUtils.isBestArmor(is2, type) && InventoryUtils.getProtection(is2) > 0.0f) {
                            InventoryUtils.shiftClick(i);
                            this.delay = 0;
                            if (this.armorDelay.getValue() > 0.0) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
