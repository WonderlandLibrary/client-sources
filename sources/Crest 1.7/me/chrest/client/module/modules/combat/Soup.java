// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import me.chrest.event.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.utils.Timer;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Soup")
public class Soup extends Module
{
    @Option.Op(min = 0.5, max = 10.0, increment = 0.5, name = "Health")
    private double health;
    @Option.Op(min = 50.0, max = 1000.0, increment = 25.0, name = "Delay")
    private double delay;
    private Timer timer;
    
    public Soup() {
        this.health = 4.5;
        this.delay = 250.0;
        this.timer = new Timer();
    }
    
    @EventTarget(3)
    private void onUpdate(final UpdateEvent event) {
        if (event.getState().equals(Event.State.PRE)) {
            final int soupSlot = this.getSoup();
            if (soupSlot != -1 && ClientUtils.player().getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay) && ClientUtils.player().isCollidedVertically && this.doesHotbarHaveSoup()) {
                final int currentItem = ClientUtils.player().inventory.currentItem;
                ClientUtils.packet(new C09PacketHeldItemChange(soupSlot));
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(ClientUtils.player().inventory.getCurrentItem()));
                ClientUtils.packet(new C09PacketHeldItemChange(currentItem));
                for (int i = 0; i < 80; ++i) {
                    ClientUtils.packet(new C03PacketPlayer(true));
                }
                this.timer.reset();
            }
            if (!this.doesHotbarHaveSoup()) {
                this.grabSoup();
                this.timer.reset();
            }
        }
        event.getState().equals(Event.State.PRE);
    }
    
    private void grabSoup() {
        for (int i = 9; i < 36; ++i) {
            final ItemStack stack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.mushroom_stew)) {
                ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.openContainer.windowId, i, 1, 2, ClientUtils.mc().thePlayer);
                break;
            }
        }
    }
    
    private boolean doesHotbarHaveSoup() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.mushroom_stew)) {
                return true;
            }
        }
        return false;
    }
    
    private int getSoup() {
        for (int i = 0; i <= 8; ++i) {
            final ItemStack stack = ClientUtils.player().inventory.getStackInSlot(i);
            if (stack != null && Item.getIdFromItem(stack.getItem()) == Item.getIdFromItem(Items.mushroom_stew)) {
                return i;
            }
        }
        return -1;
    }
}
