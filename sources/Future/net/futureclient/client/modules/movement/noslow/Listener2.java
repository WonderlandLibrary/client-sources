package net.futureclient.client.modules.movement.noslow;

import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiOptions;
import net.futureclient.client.KC;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.NoSlow;
import net.futureclient.client.SE;
import net.futureclient.client.n;

public class Listener2 extends n<SE>
{
    public final NoSlow k;
    
    public Listener2(final NoSlow k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((SE)event);
    }
    
    @Override
    public void M(final SE se) {
        if (this.k.inventoryMove.M() && (NoSlow.getMinecraft9().currentScreen instanceof GuiInventory || NoSlow.getMinecraft24().currentScreen instanceof KC || NoSlow.getMinecraft21().currentScreen instanceof GuiOptions || NoSlow.getMinecraft11().currentScreen instanceof GuiVideoSettings || NoSlow.getMinecraft22().currentScreen instanceof GuiScreenOptionsSounds || NoSlow.getMinecraft16().currentScreen instanceof GuiContainer || NoSlow.getMinecraft19().currentScreen instanceof GuiIngameMenu)) {
            se.M(true);
        }
    }
}
