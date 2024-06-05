package me.enrythebest.reborn.cracked.mods;

import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import me.enrythebest.reborn.cracked.util.*;
import net.minecraft.src.*;

public final class AutoSoup extends ModBase
{
    private final int soupID = 282;
    private final int bowlID = 281;
    private int health;
    private int count1;
    private int count2;
    public static int soups;
    
    public AutoSoup() {
        super("AutoSoup", "P", true, ".t soup");
        this.health = 14;
        this.setDescription("Eats soup for you when your health is below the treshold.");
    }
    
    @Override
    public void postMotionUpdate() {
        ++this.count1;
        ++this.count2;
        if (this.count2 >= 10 || (this.count2 >= 5 && this.isHealthLow())) {
            this.cleanHotbar();
        }
        if (this.count1 >= 10 || (this.count1 >= 5 && this.isHealthLow())) {
            this.prepareHotbar();
        }
        if (this.isHealthLow()) {
            this.eatSoup();
        }
        this.updateSoupCount();
    }
    
    @Override
    public void onCommand(final String var1) {
        final String[] var2 = var1.split(" ");
        if (var1.toLowerCase().startsWith(".sh")) {
            try {
                final int var3 = Integer.parseInt(var2[1]);
                this.health = var3;
                this.getWrapper();
                MorbidWrapper.addChat("Soup health set to: " + this.health);
            }
            catch (Exception var4) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .health [health to eat soup]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    private void eatSoup() {
        Morbid.getManager();
        final boolean var1 = ModManager.getMod("autosword").isEnabled();
        if (!var1) {
            Morbid.getManager();
            ModManager.getMod("autosword").setEnabled(false);
        }
        for (int var2 = 44; var2 >= 9; --var2) {
            this.getWrapper();
            final ItemStack var3 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var2).getStack();
            if (var3 != null) {
                if (var2 >= 36 && var2 <= 44) {
                    if (var3.itemID == 282) {
                        this.getWrapper();
                        MorbidWrapper.getPlayer().inventory.currentItem = var2 - 36;
                        this.getWrapper();
                        MorbidWrapper.getController().updateController();
                        final Packet15Place var4 = new Packet15Place(-1, -1, -1, -1, MorbidWrapper.getPlayer().inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f);
                        this.getWrapper();
                        MorbidHelper.sendPacket(var4);
                        break;
                    }
                }
                else if (var3.itemID == 282) {
                    if (!this.isHotbarFree()) {
                        this.getWrapper();
                        PlayerControllerMP var5 = MorbidWrapper.getController();
                        this.getWrapper();
                        var5.windowClick(0, 44, 0, 0, MorbidWrapper.getPlayer());
                        this.getWrapper();
                        var5 = MorbidWrapper.getController();
                        this.getWrapper();
                        var5.windowClick(0, -999, 0, 0, MorbidWrapper.getPlayer());
                    }
                    this.getWrapper();
                    PlayerControllerMP var5 = MorbidWrapper.getController();
                    this.getWrapper();
                    var5.windowClick(0, var2, 0, 1, MorbidWrapper.getPlayer());
                    break;
                }
            }
        }
        if (!var1) {
            Morbid.getManager();
            ModManager.getMod("autosword").setEnabled(true);
        }
    }
    
    private void prepareHotbar() {
        for (int var1 = 36; var1 <= 44; ++var1) {
            this.getWrapper();
            final ItemStack var2 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var1).getStack();
            if (var2 == null) {
                for (int var3 = 35; var3 >= 9; --var3) {
                    this.getWrapper();
                    final ItemStack var4 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var3).getStack();
                    if (var4 != null && var4.itemID == 282) {
                        this.getWrapper();
                        final PlayerControllerMP var5 = MorbidWrapper.getController();
                        this.getWrapper();
                        var5.windowClick(0, var3, 0, 1, MorbidWrapper.getPlayer());
                        this.count2 = 0;
                        return;
                    }
                }
            }
        }
    }
    
    private void cleanHotbar() {
        this.getWrapper();
        final ItemStack var1 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(9).getStack();
        for (int var2 = 36; var2 <= 44; ++var2) {
            this.getWrapper();
            final ItemStack var3 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var2).getStack();
            if (var3 != null && var3.itemID == 281) {
                if (var1 != null && (var1.itemID != 281 || var1.stackSize >= 64)) {
                    if (var1.itemID == 282) {
                        this.getWrapper();
                        PlayerControllerMP var4 = MorbidWrapper.getController();
                        this.getWrapper();
                        var4.windowClick(0, 9, 0, 0, MorbidWrapper.getPlayer());
                        this.getWrapper();
                        var4 = MorbidWrapper.getController();
                        this.getWrapper();
                        var4.windowClick(0, var2, 0, 0, MorbidWrapper.getPlayer());
                        this.getWrapper();
                        var4 = MorbidWrapper.getController();
                        this.getWrapper();
                        var4.windowClick(0, 9, 0, 0, MorbidWrapper.getPlayer());
                        this.count1 = 0;
                        return;
                    }
                    this.getWrapper();
                    PlayerControllerMP var4 = MorbidWrapper.getController();
                    this.getWrapper();
                    var4.windowClick(0, 9, 0, 0, MorbidWrapper.getPlayer());
                    this.getWrapper();
                    var4 = MorbidWrapper.getController();
                    this.getWrapper();
                    var4.windowClick(0, -999, 0, 0, MorbidWrapper.getPlayer());
                }
                this.getWrapper();
                PlayerControllerMP var4 = MorbidWrapper.getController();
                this.getWrapper();
                var4.windowClick(0, var2, 0, 1, MorbidWrapper.getPlayer());
                this.count1 = 0;
                return;
            }
        }
    }
    
    private void updateSoupCount() {
        AutoSoup.soups = 0;
        for (int var1 = 44; var1 >= 9; --var1) {
            this.getWrapper();
            final ItemStack var2 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var1).getStack();
            if (var2 != null && var2.itemID == 282) {
                ++AutoSoup.soups;
            }
        }
    }
    
    private boolean isHealthLow() {
        this.getWrapper();
        return MorbidWrapper.getPlayer().getHealth() <= this.health;
    }
    
    private boolean isHotbarFree() {
        for (int var1 = 36; var1 <= 44; ++var1) {
            this.getWrapper();
            final ItemStack var2 = MorbidWrapper.getPlayer().inventoryContainer.getSlot(var1).getStack();
            if (var2 == null || var2.itemID == 282) {
                return true;
            }
        }
        return false;
    }
}
