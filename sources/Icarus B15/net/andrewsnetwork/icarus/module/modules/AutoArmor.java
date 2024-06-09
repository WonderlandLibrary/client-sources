// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.module.Module;

public class AutoArmor extends Module
{
    private final int[] boots;
    private final int[] chestplate;
    private final int[] helmet;
    private final int[] leggings;
    private final TimeHelper time;
    
    public AutoArmor() {
        super("AutoArmor", -16728065, Category.PLAYER);
        this.boots = new int[] { 313, 309, 317, 305, 301 };
        this.chestplate = new int[] { 311, 307, 315, 303, 299 };
        this.helmet = new int[] { 310, 306, 314, 302, 298 };
        this.leggings = new int[] { 312, 308, 316, 304, 300 };
        this.time = new TimeHelper();
        this.setTag("Auto Armor");
    }
    
    public boolean armourIsBetter(final int slot, final int[] armourtype) {
        if (AutoArmor.mc.thePlayer.inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int finalCurrentIndex = -1;
            int invIndex = 0;
            int finalInvIndex = -1;
            for (final int armour : armourtype) {
                if (Item.getIdFromItem(AutoArmor.mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                ++currentIndex;
            }
            for (final int armour : armourtype) {
                if (this.findItem(armour) != -1) {
                    finalInvIndex = invIndex;
                    break;
                }
                ++invIndex;
            }
            if (finalInvIndex > -1) {
                return finalInvIndex < finalCurrentIndex;
            }
        }
        return false;
    }
    
    private int findItem(final int id) {
        for (int index = 9; index < 45; ++index) {
            final ItemStack item = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return index;
            }
        }
        return -1;
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (event instanceof PreMotion) {
            if (!this.time.hasReached(65L)) {
                return;
            }
            if (AutoArmor.mc.thePlayer.openContainer != null && AutoArmor.mc.thePlayer.openContainer.windowId != 0) {
                return;
            }
            int item = -1;
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[0] == null) {
                int[] arrayOfInt;
                for (int j = (arrayOfInt = this.boots).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                }
            }
            if (this.armourIsBetter(0, this.boots)) {
                item = 8;
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[3] == null) {
                int[] arrayOfInt;
                for (int j = (arrayOfInt = this.helmet).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                }
            }
            if (this.armourIsBetter(3, this.helmet)) {
                item = 5;
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[1] == null) {
                int[] arrayOfInt;
                for (int j = (arrayOfInt = this.leggings).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                }
            }
            if (this.armourIsBetter(1, this.leggings)) {
                item = 7;
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[2] == null) {
                int[] arrayOfInt;
                for (int j = (arrayOfInt = this.chestplate).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                }
            }
            if (this.armourIsBetter(2, this.chestplate)) {
                item = 6;
            }
            if (item != -1) {
                AutoArmor.mc.playerController.windowClick(0, item, 0, 1, AutoArmor.mc.thePlayer);
                AutoArmor.mc.playerController.updateController();
                this.time.setLastMS(this.time.getCurrentMS());
            }
        }
    }
}
