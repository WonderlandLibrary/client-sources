package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;
import net.minecraft.src.*;

public final class AmericaAutoarmor extends ModBase
{
    Minecraft mc;
    private final Integer[] helmetPriority;
    private final Integer[] chestPriority;
    private final Integer[] legsPriority;
    private final Integer[] bootsPriority;
    
    public AmericaAutoarmor() {
        super("AutoArmor", "L", true, ".t armor");
        this.mc = MorbidWrapper.mcObj();
        this.helmetPriority = new Integer[] { 298, 314, 302, 306, 310 };
        this.chestPriority = new Integer[] { 299, 315, 303, 307, 311 };
        this.legsPriority = new Integer[] { 300, 316, 304, 308, 312 };
        this.bootsPriority = new Integer[] { 301, 317, 305, 309, 313 };
        this.setDescription("Sceglie e ti mette l'armor migliore.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final ItemStack var1 = MorbidWrapper.getPlayer().inventory.armorItemInSlot(3);
            final ItemStack var2 = MorbidWrapper.getPlayer().inventory.armorItemInSlot(2);
            final ItemStack var3 = MorbidWrapper.getPlayer().inventory.armorItemInSlot(1);
            final ItemStack var4 = MorbidWrapper.getPlayer().inventory.armorItemInSlot(0);
            if (var1 == null) {
                this.wearHelmet();
            }
            else {
                this.compareItem(var1, this.helmetPriority, 3);
            }
            if (var2 == null) {
                this.wearChestplate();
            }
            else {
                this.compareItem(var2, this.chestPriority, 2);
            }
            if (var3 == null) {
                this.wearLeggings();
            }
            else {
                this.compareItem(var3, this.legsPriority, 1);
            }
            if (var4 == null) {
                this.wearBoots();
            }
            else {
                this.compareItem(var4, this.bootsPriority, 0);
            }
        }
    }
    
    private void removeArmor(final int var1) {
        final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
        final int par1 = 0;
        final int par2 = 8 - var1;
        final int par3 = 0;
        final int par4 = 1;
        MorbidWrapper.mcObj();
        playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
    }
    
    private void compareItem(final ItemStack var1, final Integer[] var2, final int var3) {
        for (int var4 = 44; var4 >= 9; --var4) {
            MorbidWrapper.mcObj();
            final ItemStack var5 = Minecraft.thePlayer.inventoryContainer.getSlot(var4).getStack();
            if (var5 != null && Arrays.asList(var2).indexOf(Item.getIdFromItem(var5.getItem())) > Arrays.asList(var2).indexOf(Item.getIdFromItem(var1.getItem()))) {
                if (var4 >= 36 && var4 <= 44) {
                    this.removeArmor(var3);
                    final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
                    final int par1 = 0;
                    final int par2 = var4;
                    final int par3 = 0;
                    final int par4 = 1;
                    MorbidWrapper.mcObj();
                    playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
                }
                else {
                    this.removeArmor(var3);
                    final PlayerControllerMP playerController2 = MorbidWrapper.mcObj().playerController;
                    final int par5 = 0;
                    final int par6 = var4;
                    final int par7 = 0;
                    final int par8 = 1;
                    MorbidWrapper.mcObj();
                    playerController2.windowClick(par5, par6, par7, par8, Minecraft.thePlayer);
                }
            }
        }
    }
    
    private void wearHelmet() {
        for (int var1 = 44; var1 >= 9; --var1) {
            MorbidWrapper.mcObj();
            final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
            if (var2 != null) {
                if (var1 >= 36 && var1 <= 44) {
                    if (Arrays.asList(this.helmetPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                        final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
                        final int par1 = 0;
                        final int par2 = var1;
                        final int par3 = 0;
                        final int par4 = 1;
                        MorbidWrapper.mcObj();
                        playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
                    }
                }
                else if (Arrays.asList(this.helmetPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                    final PlayerControllerMP playerController2 = MorbidWrapper.mcObj().playerController;
                    final int par5 = 0;
                    final int par6 = var1;
                    final int par7 = 0;
                    final int par8 = 1;
                    MorbidWrapper.mcObj();
                    playerController2.windowClick(par5, par6, par7, par8, Minecraft.thePlayer);
                }
            }
        }
    }
    
    private void wearChestplate() {
        for (int var1 = 44; var1 >= 9; --var1) {
            MorbidWrapper.mcObj();
            final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
            if (var2 != null) {
                if (var1 >= 36 && var1 <= 44) {
                    if (Arrays.asList(this.chestPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                        final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
                        final int par1 = 0;
                        final int par2 = var1;
                        final int par3 = 0;
                        final int par4 = 1;
                        MorbidWrapper.mcObj();
                        playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
                    }
                }
                else if (Arrays.asList(this.chestPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                    final PlayerControllerMP playerController2 = MorbidWrapper.mcObj().playerController;
                    final int par5 = 0;
                    final int par6 = var1;
                    final int par7 = 0;
                    final int par8 = 1;
                    MorbidWrapper.mcObj();
                    playerController2.windowClick(par5, par6, par7, par8, Minecraft.thePlayer);
                }
            }
        }
    }
    
    private void wearLeggings() {
        for (int var1 = 44; var1 >= 9; --var1) {
            MorbidWrapper.mcObj();
            final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
            if (var2 != null) {
                if (var1 >= 36 && var1 <= 44) {
                    if (Arrays.asList(this.legsPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                        final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
                        final int par1 = 0;
                        final int par2 = var1;
                        final int par3 = 0;
                        final int par4 = 1;
                        MorbidWrapper.mcObj();
                        playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
                    }
                }
                else if (Arrays.asList(this.legsPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                    final PlayerControllerMP playerController2 = MorbidWrapper.mcObj().playerController;
                    final int par5 = 0;
                    final int par6 = var1;
                    final int par7 = 0;
                    final int par8 = 1;
                    MorbidWrapper.mcObj();
                    playerController2.windowClick(par5, par6, par7, par8, Minecraft.thePlayer);
                }
            }
        }
    }
    
    private void wearBoots() {
        for (int var1 = 44; var1 >= 9; --var1) {
            MorbidWrapper.mcObj();
            final ItemStack var2 = Minecraft.thePlayer.inventoryContainer.getSlot(var1).getStack();
            if (var2 != null) {
                if (var1 >= 36 && var1 <= 44) {
                    if (Arrays.asList(this.bootsPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                        final PlayerControllerMP playerController = MorbidWrapper.mcObj().playerController;
                        final int par1 = 0;
                        final int par2 = var1;
                        final int par3 = 0;
                        final int par4 = 1;
                        MorbidWrapper.mcObj();
                        playerController.windowClick(par1, par2, par3, par4, Minecraft.thePlayer);
                    }
                }
                else if (Arrays.asList(this.bootsPriority).contains(Item.getIdFromItem(var2.getItem()))) {
                    final PlayerControllerMP playerController2 = MorbidWrapper.mcObj().playerController;
                    final int par5 = 0;
                    final int par6 = var1;
                    final int par7 = 0;
                    final int par8 = 1;
                    MorbidWrapper.mcObj();
                    playerController2.windowClick(par5, par6, par7, par8, Minecraft.thePlayer);
                }
            }
        }
    }
}
