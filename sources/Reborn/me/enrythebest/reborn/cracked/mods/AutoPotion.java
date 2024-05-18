package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;
import net.minecraft.src.*;

public final class AutoPotion extends ModBase
{
    private byte slot;
    
    public AutoPotion() {
        super("AutoPotion", "0", true, ".t potion");
        this.slot = -1;
        this.setDescription("Vedi attraverso i blocchi");
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.slot = -1;
    }
    
    @Override
    public void onRenderHand() {
        this.onHealthUpdate();
        Minecraft.getMinecraft();
        if (Minecraft.currentScreen == null) {
            for (byte var1 = 36; var1 <= 44; ++var1) {
                Minecraft.getMinecraft();
                if (Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack() == null) {
                    if (this.getPot()) {
                        break;
                    }
                }
                else {
                    Minecraft.getMinecraft();
                    if (Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack().getItem() instanceof ItemGlassBottle) {
                        Minecraft.getMinecraft();
                        final int var2 = Minecraft.thePlayer.inventory.currentItem;
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.inventory.currentItem = var1 - 36;
                        Minecraft.getMinecraft().playerController.syncCurrentPlayItem();
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.dropOneItem(true);
                        Minecraft.getMinecraft();
                        Minecraft.thePlayer.inventory.currentItem = var2;
                        Minecraft.getMinecraft().playerController.syncCurrentPlayItem();
                    }
                }
            }
        }
    }
    
    private void onHealthUpdate() {
        if (MorbidWrapper.getPlayer().getHealth() <= 10.0f) {
            for (byte var1 = 36; var1 <= 44; ++var1) {
                Minecraft.getMinecraft();
                final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
                if (var2 != null) {
                    final Item var3 = var2.getItem();
                    if (var3 instanceof ItemPotion) {
                        final ItemPotion var4 = (ItemPotion)var3;
                        if (ItemPotion.isSplash(var2.getItemDamage())) {
                            final Object var5 = null;
                            for (final Object var7 : var4.getEffects(var2)) {
                                final PotionEffect var8 = (PotionEffect)var7;
                                if (var8.getPotionID() == Potion.heal.id) {
                                    this.slot = var1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void onPreUpdate() {
        if (this.slot != -1) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.rotationPitch = 90.0f;
        }
    }
    
    private void onPostUpdate() {
        if (this.slot != -1) {
            Minecraft.getMinecraft();
            final int var1 = Minecraft.thePlayer.inventory.currentItem;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.inventory.currentItem = this.slot - 36;
            Minecraft.getMinecraft().playerController.syncCurrentPlayItem();
            Minecraft.getMinecraft();
            final NetClientHandler sendQueue = Minecraft.thePlayer.sendQueue;
            final int par1 = -1;
            final int par2 = -1;
            final int par3 = -1;
            final int par4 = 255;
            Minecraft.getMinecraft();
            sendQueue.addToSendQueue(new Packet15Place(par1, par2, par3, par4, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            Minecraft.getMinecraft();
            Minecraft.thePlayer.inventory.currentItem = var1;
            Minecraft.getMinecraft().playerController.syncCurrentPlayItem();
            this.slot = -1;
        }
    }
    
    private boolean getPot() {
        for (byte var1 = 9; var1 <= 35; ++var1) {
            Minecraft.getMinecraft();
            final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
            if (var2 != null) {
                final Item var3 = var2.getItem();
                if (var3 instanceof ItemPotion) {
                    clickSlot(var1, 0, true);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void clickSlot(final int var0, final int var1, final boolean var2) {
        final PlayerControllerMP playerController = Minecraft.getMinecraft().playerController;
        Minecraft.getMinecraft();
        final int windowId = Minecraft.thePlayer.inventoryContainer.windowId;
        final int par4 = var2 ? 1 : 0;
        Minecraft.getMinecraft();
        playerController.windowClick(windowId, var0, var1, par4, Minecraft.thePlayer);
    }
}
