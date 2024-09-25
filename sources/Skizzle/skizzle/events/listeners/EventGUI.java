/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import skizzle.events.Event;

public class EventGUI
extends Event<EventGUI> {
    public Minecraft mc = Minecraft.getMinecraft();
    public boolean chestGUI = !Nigga.chestGUI;
    public ItemStack item;

    public static {
        throw throwable;
    }

    public EventGUI() {
        EventGUI Nigga;
        Nigga.item = Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem();
    }
}

