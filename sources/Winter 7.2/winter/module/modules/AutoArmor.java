/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;

public class AutoArmor
extends Module {
    public static final String THANKS = "thx soirr";
    private final int[] boots = new int[]{313, 309, 317, 305, 301};
    private final int[] chestplate = new int[]{311, 307, 315, 303, 299};
    private final int[] helmet = new int[]{310, 306, 314, 302, 298};
    private final int[] leggings = new int[]{312, 308, 316, 304, 300};
    int delay = 0;

    public AutoArmor() {
        super("Auto Armor", Module.Category.Combat, -149045);
        this.setBind(0);
    }

    private int getItem(int id2) {
        int index = 9;
        while (index < 45) {
            ItemStack item = this.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id2) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    @EventListener
    public void onTick(TickEvent event) {
        ++this.delay;
        if (this.delay >= 1 && (this.mc.thePlayer.openContainer == null || this.mc.thePlayer.openContainer.windowId == 0)) {
            int var5;
            int var6;
            int id2;
            int[] hasInvSpace;
            boolean dropkek = false;
            int item = -1;
            if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
                hasInvSpace = this.boots;
                var5 = hasInvSpace.length;
                var6 = 0;
                while (var6 < var5) {
                    id2 = hasInvSpace[var6];
                    if (this.getItem(id2) != -1) {
                        item = this.getItem(id2);
                        break;
                    }
                    ++var6;
                }
            }
            if (this.isBetter(0, this.boots)) {
                item = 8;
                dropkek = true;
            }
            if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
                hasInvSpace = this.helmet;
                var5 = hasInvSpace.length;
                var6 = 0;
                while (var6 < var5) {
                    id2 = hasInvSpace[var6];
                    if (this.getItem(id2) != -1) {
                        item = this.getItem(id2);
                        break;
                    }
                    ++var6;
                }
            }
            if (this.isBetter(3, this.helmet)) {
                item = 5;
                dropkek = true;
            }
            if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
                hasInvSpace = this.leggings;
                var5 = hasInvSpace.length;
                var6 = 0;
                while (var6 < var5) {
                    id2 = hasInvSpace[var6];
                    if (this.getItem(id2) != -1) {
                        item = this.getItem(id2);
                        break;
                    }
                    ++var6;
                }
            }
            if (this.isBetter(1, this.leggings)) {
                item = 7;
                dropkek = true;
            }
            if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
                hasInvSpace = this.chestplate;
                var5 = hasInvSpace.length;
                var6 = 0;
                while (var6 < var5) {
                    id2 = hasInvSpace[var6];
                    if (this.getItem(id2) != -1) {
                        item = this.getItem(id2);
                        break;
                    }
                    ++var6;
                }
            }
            if (this.isBetter(2, this.chestplate)) {
                item = 6;
                dropkek = true;
            }
            boolean var9 = false;
            ItemStack[] var10 = this.mc.thePlayer.inventory.mainInventory;
            var6 = var10.length;
            id2 = 0;
            while (id2 < var6) {
                ItemStack stack = var10[id2];
                if (stack == null) {
                    var9 = true;
                    break;
                }
                ++id2;
            }
            boolean bl2 = dropkek = dropkek && !var9;
            if (item != -1) {
                this.mc.playerController.windowClick(0, item, 0, dropkek ? 4 : 1, this.mc.thePlayer);
                this.delay = 0;
            }
        }
    }

    public boolean isBetter(int slot, int[] armourtype) {
        if (this.mc.thePlayer.inventory.armorInventory[slot] != null) {
            int armour;
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            int[] var7 = armourtype;
            int var8 = armourtype.length;
            int var9 = 0;
            while (var9 < var8) {
                armour = var7[var9];
                if (Item.getIdFromItem(this.mc.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                ++currentIndex;
                ++var9;
            }
            var7 = armourtype;
            var8 = armourtype.length;
            var9 = 0;
            while (var9 < var8) {
                armour = var7[var9];
                if (this.getItem(armour) != -1) {
                    finalInvIndex = invIndex;
                    break;
                }
                ++invIndex;
                ++var9;
            }
            if (finalInvIndex > -1) {
                if (finalInvIndex < finalCurrentIndex) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}

