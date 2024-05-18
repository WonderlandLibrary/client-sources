// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Combat;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventManager;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.Mod;

public class AutoSoup extends Mod
{
    private double soupHealth;
    private double soupDelay;
    private TimeMeme timer;
    
    public AutoSoup() {
        super("AutoSoup", Category.COMBAT);
        this.setName("AutoSoup");
        this.soupHealth = 4.5;
        this.soupDelay = 700.0;
    }
    
    @Override
    public void onEnable() {
        this.timer = new TimeMeme();
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
    
    @EventTarget
    private void onPostUpdate(final EventUpdate event) {
        if (event.state == EventState.POST) {
            final int soupSlot = this.getSoupFromInventory();
            if (this.mc.thePlayer.getHealth() < this.soupHealth * 2.0 && this.timer.hasPassed((float)this.soupDelay * 1.6) && soupSlot != -1) {
                final int prevSlot = this.mc.thePlayer.inventory.currentItem;
                if (soupSlot < 9) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(soupSlot));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                    this.mc.playerController.syncCurrentPlayItem();
                    this.mc.thePlayer.inventory.currentItem = prevSlot;
                }
                else {
                    this.swap(soupSlot, this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1)));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(prevSlot));
                }
                this.timer.reset();
            }
        }
    }
    
    protected void swap(final int slot, final int hotbarNum) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, this.mc.thePlayer);
    }
    
    private int getSoupFromInventory() {
        for (int i = 0; i < 35; ++i) {
            int counter = 0;
            if (this.mc.thePlayer.inventory.mainInventory[i] != null) {
                final ItemStack is = this.mc.thePlayer.inventory.mainInventory[i];
                final Item item = is.getItem();
                if (Item.getIdFromItem(item) == 282) {
                    ++counter;
                    return i;
                }
            }
        }
        return -1;
    }
}
