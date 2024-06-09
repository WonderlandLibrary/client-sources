package com.client.glowclient.modules.combat;

import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import com.client.glowclient.events.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.multiplayer.*;
import com.client.glowclient.modules.*;

public class AutoTotem extends ModuleContainer
{
    private static final Minecraft A;
    private int B;
    
    @Override
    public String M() {
        int n = 0;
        String value = "";
        int n2;
        int i = n2 = 0;
        while (i < AutoTotem.A.player.inventory.getSizeInventory()) {
            final ItemStack stackInSlot;
            if ((stackInSlot = AutoTotem.A.player.inventory.getStackInSlot(n2)) != null && stackInSlot.getItem().equals(Items.TOTEM_OF_UNDYING)) {
                value = String.valueOf(n += stackInSlot.getCount());
            }
            i = ++n2;
        }
        return value;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final EntityPlayerSP player = AutoTotem.A.player;
        if (this.B > 0) {
            --this.B;
            return;
        }
        final NonNullList mainInventory = player.inventory.mainInventory;
        if (EntityEquipmentSlot.OFFHAND == null || player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() != Items.TOTEM_OF_UNDYING) {
            int n;
            int i = n = 0;
            while (true) {
                while (i < mainInventory.size()) {
                    if (mainInventory.get(n) != ItemStack.EMPTY && ((ItemStack)mainInventory.get(n)).getItem() == Items.TOTEM_OF_UNDYING) {
                        final AutoTotem autoTotem = this;
                        this.D(n);
                        autoTotem.B = 3;
                        return;
                    }
                    i = ++n;
                }
                final AutoTotem autoTotem = this;
                continue;
            }
        }
    }
    
    public void D(final int n) {
        if (AutoTotem.A.player.openContainer instanceof ContainerPlayer) {
            AutoTotem.A.playerController.windowClick(0, (n < 9) ? (n + 36) : n, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.A.player);
            final PlayerControllerMP playerController = AutoTotem.A.playerController;
            final int n2 = 45;
            final int n3 = 0;
            playerController.windowClick(n3, n2, n3, ClickType.PICKUP, (EntityPlayer)AutoTotem.A.player);
            AutoTotem.A.playerController.windowClick(0, (n < 9) ? (n + 36) : n, 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.A.player);
        }
    }
    
    public AutoTotem() {
        super(Category.COMBAT, "AutoTotem", false, -1, "Automatically places totem in offhand");
    }
    
    static {
        A = Minecraft.getMinecraft();
    }
    
    @Override
    public void D() {
        this.B = 0;
    }
}
