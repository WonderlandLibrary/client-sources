package wwe.modules.player;

import wwe.*;
import wwe.modules.*;
import wwe.utils.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.item.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.inventory.*;

public class ElytraReplace extends qMIe
{
    public ElytraReplace() {
        super("Elytra Replace", "Automatically replaces your elytra", 0, Category.PLAYER);
    }
    
    @Override
    public void hPRw() {
        if (!this.IuiN()) {
            return;
        }
        final ItemStack itemStackFromSlot = Wrapper.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (itemStackFromSlot.getItem() != Items.ELYTRA) {
            int i = 0;
            while (i < Wrapper.mc.player.inventory.mainInventory.size()) {
                final ItemStack stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(i);
                if (stackInSlot.getItem() == Items.ELYTRA && Wrapper.mc.currentScreen == null && stackInSlot.getItemDamage() < stackInSlot.getMaxDamage() - 1) {
                    final PlayerControllerMP playerController = Wrapper.mc.playerController;
                    final int n = 0;
                    int n2;
                    if (i < 9) {
                        n2 = i + 36;
                        if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n2 = i;
                    }
                    playerController.windowClick(n, n2, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    Wrapper.mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++i;
                    if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        if (itemStackFromSlot.getItem() == Items.ELYTRA && itemStackFromSlot.getItemDamage() >= itemStackFromSlot.getMaxDamage() - 1) {
            int j = 0;
            while (j <= 36) {
                final ItemStack stackInSlot2 = Wrapper.mc.player.inventory.getStackInSlot(j);
                if (stackInSlot2.getItem() == Items.ELYTRA && Wrapper.mc.currentScreen == null && stackInSlot2.getItemDamage() < stackInSlot2.getMaxDamage() - 1) {
                    Wrapper.mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    final PlayerControllerMP playerController2 = Wrapper.mc.playerController;
                    final int n3 = 0;
                    int n4;
                    if (j < 9) {
                        n4 = j + 36;
                        if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                            throw null;
                        }
                    }
                    else {
                        n4 = j;
                    }
                    playerController2.windowClick(n3, n4, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
                    Wrapper.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Wrapper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                    if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                        throw null;
                    }
                    continue;
                }
            }
        }
    }
    
    public void PQPT(final int n) {
        if (Wrapper.mc.player.openContainer instanceof ContainerPlayer) {
            final PlayerControllerMP playerController = Wrapper.mc.playerController;
            final int n2 = 0;
            int n3;
            if (n < 9) {
                n3 = n + 36;
                if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                    throw null;
                }
            }
            else {
                n3 = n;
            }
            playerController.windowClick(n2, n3, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
            Wrapper.mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
            final PlayerControllerMP playerController2 = Wrapper.mc.playerController;
            final int n4 = 0;
            int n5;
            if (n < 9) {
                n5 = n + 36;
                if (ElytraReplace.\u200a\u2009\u200b\u200f\u200a\u2009) {
                    throw null;
                }
            }
            else {
                n5 = n;
            }
            playerController2.windowClick(n4, n5, 0, ClickType.PICKUP, (EntityPlayer)Wrapper.mc.player);
        }
    }
}
