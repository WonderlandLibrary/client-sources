// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod.Collection.Combat;

import java.util.Iterator;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemPotion;
import net.minecraft.client.network.badlion.Events.EventMove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.client.network.badlion.Utils.RotationUtils;
import net.minecraft.client.network.badlion.Utils.ClientUtils;
import net.minecraft.client.network.badlion.Events.EventState;
import net.minecraft.client.network.badlion.Events.EventUpdate;
import net.minecraft.client.network.badlion.memes.EventTarget;
import net.minecraft.client.network.badlion.Utils.InvUtils;
import net.minecraft.client.network.badlion.Events.EventTick;
import net.minecraft.client.network.badlion.memes.EventManager;
import org.darkstorm.minecraft.gui.component.BoundedRangeComponent;
import net.minecraft.client.network.badlion.Mod.Category;
import net.minecraft.item.ItemStack;
import net.minecraft.client.network.badlion.Utils.TimeMeme;
import net.minecraft.client.network.badlion.Mod.NumValue;
import net.minecraft.client.network.badlion.Mod.Mod;

public class AutoPot extends Mod
{
    private NumValue autoPot;
    private TimeMeme timer;
    private int lockedTicks;
    private boolean motion;
    private static int potSlot;
    private static ItemStack originalStack;
    private static int currentItem;
    private static int expectedPackets;
    
    public AutoPot() {
        super("AutoPotion", Category.COMBAT);
        this.autoPot = new NumValue("Autopot HP", 4.0, 1.0, 10.0, BoundedRangeComponent.ValueDisplay.DECIMAL);
        this.setName("AutoPotion");
    }
    
    @Override
    public void onEnable() {
        this.timer = new TimeMeme();
        this.lockedTicks = -1;
        EventManager.register(this);
    }
    
    @Override
    public void onDisable() {
        AutoPot.expectedPackets = 0;
        this.lockedTicks = -1;
        EventManager.unregister(this);
    }
    
    @EventTarget
    public void onTick(final EventTick event) {
        this.setRenderName(String.format("%s§7 %s", this.getModName(), InvUtils.getPotsInInventory()));
    }
    
    @EventTarget(3)
    private void onUpdate(final EventUpdate event) {
        final EventState state = event.state;
        if (state == EventState.PRE) {
            if (this.lockedTicks >= 0) {
                if (this.lockedTicks == 0) {
                    event.y = 1.3;
                }
                else {
                    event.setCancelled(true);
                }
                --this.lockedTicks;
            }
            else {
                final int potSlot = this.getPotionFromInventory();
                if (potSlot != -1 && ClientUtils.mc().thePlayer.getHealth() <= this.autoPot.getValue() * 2.0 && this.timer.hasPassed(300.0)) {
                    event.pitch = 98.0f;
                    if (this.motion) {
                        final double movedPosX = ClientUtils.mc().thePlayer.posX + ClientUtils.mc().thePlayer.motionX * 16.0;
                        final double movedPosY = ClientUtils.mc().thePlayer.boundingBox.minY - 3.6;
                        final double movedPosZ = ClientUtils.mc().thePlayer.posZ + ClientUtils.mc().thePlayer.motionZ * 16.0;
                        final float yaw = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY)[0];
                        final float pitch = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY)[1];
                        event.yaw = yaw;
                        event.pitch = pitch;
                    }
                }
            }
        }
        else {
            final EventState state2 = event.state;
            if (state2 == EventState.POST && this.lockedTicks < 0) {
                final int potSlot2 = this.getPotionFromInventory();
                if (potSlot2 != -1 && ClientUtils.mc().thePlayer.getHealth() <= this.autoPot.getValue() * 2.0 && this.timer.hasPassed(300.0)) {
                    if (this.hotBarHasPots()) {
                        for (int i = 36; i < 45; ++i) {
                            final ItemStack stack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack();
                            if (stack != null && this.isSplashPot(stack)) {
                                final int oldSlot = ClientUtils.mc().thePlayer.inventory.currentItem;
                                ClientUtils.player().sendQueue.addToSendQueue(new C09PacketHeldItemChange(i - 36));
                                ClientUtils.player().sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(stack));
                                ClientUtils.player().sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                                break;
                            }
                        }
                        this.timer.reset();
                    }
                    else {
                        this.grabPot();
                    }
                }
            }
        }
    }
    
    private void grabPot() {
        for (int i = 9; i < 36; ++i) {
            final ItemStack stack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && this.isSplashPot(stack)) {
                ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.openContainer.windowId, i, 1, 2, ClientUtils.mc().thePlayer);
                break;
            }
        }
    }
    
    private boolean hotBarHasPots() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && this.isSplashPot(stack)) {
                return true;
            }
        }
        return false;
    }
    
    @EventTarget
    private void onMove(final EventMove event) {
        if (this.lockedTicks >= 0) {
            event.setX(0.0);
            event.setZ(0.0);
            event.setY(0.0);
        }
    }
    
    public static void resetItems() {
        if (AutoPot.expectedPackets > 0) {
            --AutoPot.expectedPackets;
            ClientUtils.player().inventoryContainer.putStackInSlot(AutoPot.potSlot, null);
            ClientUtils.player().inventoryContainer.putStackInSlot(36 + AutoPot.currentItem, AutoPot.originalStack);
        }
    }
    
    protected void swap(final int slot, final int hotbarNum, final boolean packet) {
        ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.mc().thePlayer);
    }
    
    private int getPotionFromInventory() {
        int pot = -1;
        int counter = 0;
        for (int i = 1; i < 45; ++i) {
            if (ClientUtils.player().inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = ClientUtils.player().inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemPotion) {
                    final ItemPotion potion = (ItemPotion)item;
                    if (potion.getEffects(is) != null) {
                        for (final Object o : potion.getEffects(is)) {
                            final PotionEffect effect = (PotionEffect)o;
                            if (effect.getPotionID() == Potion.heal.id && ItemPotion.isSplash(is.getItemDamage())) {
                                ++counter;
                                pot = i;
                            }
                        }
                    }
                }
            }
        }
        return pot;
    }
    
    private boolean isSplashPot(final ItemStack stack) {
        if (stack != null && stack.getItem() instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion)stack.getItem();
            if (ItemPotion.isSplash(stack.getItemDamage())) {
                for (final Object o : potion.getEffects(stack)) {
                    final PotionEffect effect = (PotionEffect)o;
                    if (effect.getPotionID() == Potion.heal.getId()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
