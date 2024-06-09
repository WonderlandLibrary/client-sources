/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Module.Mod(displayName="AutoArmor")
public class AutoArmor
extends Module {
    @Option.Op(name="Rewi")
    public static boolean rewi;
    private final int[] boots = new int[]{313, 309, 317, 305, 301};
    private final int[] chestplate = new int[]{311, 307, 315, 303, 299};
    private final int[] helmet = new int[]{310, 306, 314, 302, 298};
    private final int[] leggings = new int[]{312, 308, 316, 304, 300};
    private final Timer time = new Timer();
    private Minecraft mc = Minecraft.getMinecraft();

    public boolean armourIsBetter(int slot, int[] armourtype) {
        if (Minecraft.thePlayer.inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int finalCurrentIndex = -1;
            int invIndex = 0;
            int finalInvIndex = -1;
            for (int armour : armourtype) {
                if (Item.getIdFromItem(Minecraft.thePlayer.inventory.armorInventory[slot].getItem()) == armour) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                ++currentIndex;
            }
            for (int armour : armourtype) {
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

    private int findItem(int id) {
        for (int index = 9; index < 45; ++index) {
            ItemStack item = Minecraft.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item == null || Item.getIdFromItem(item.getItem()) != id) continue;
            return index;
        }
        return -1;
    }

    @EventTarget
    public void onPreMotionUpdate(MoveEvent event) {
        if (!rewi) {
            if (!Timer.isDelayComplete(65L)) {
                return;
            }
            if (Minecraft.thePlayer.openContainer != null && Minecraft.thePlayer.openContainer.windowId != 0) {
                return;
            }
            int item = -1;
            if (Minecraft.thePlayer.inventory.armorInventory[0] == null) {
                for (int id : this.boots) {
                    if (this.findItem(id) == -1) continue;
                    item = this.findItem(id);
                    break;
                }
            }
            if (this.armourIsBetter(0, this.boots)) {
                item = 8;
            }
            if (Minecraft.thePlayer.inventory.armorInventory[3] == null) {
                for (int id : this.helmet) {
                    if (this.findItem(id) == -1) continue;
                    item = this.findItem(id);
                    break;
                }
            }
            if (this.armourIsBetter(3, this.helmet)) {
                item = 5;
            }
            if (Minecraft.thePlayer.inventory.armorInventory[1] == null) {
                for (int id : this.leggings) {
                    if (this.findItem(id) == -1) continue;
                    item = this.findItem(id);
                    break;
                }
            }
            if (this.armourIsBetter(1, this.leggings)) {
                item = 7;
            }
            if (Minecraft.thePlayer.inventory.armorInventory[2] == null) {
                for (int id : this.chestplate) {
                    if (this.findItem(id) == -1) continue;
                    item = this.findItem(id);
                    break;
                }
            }
            if (this.armourIsBetter(2, this.chestplate)) {
                item = 6;
            }
            if (item != -1) {
                Minecraft.playerController.windowClick(0, item, 0, 1, Minecraft.thePlayer);
                this.time.setLastMS();
                return;
            }
        } else {
            if (!Timer.isDelayComplete(65L)) {
                return;
            }
            if (Minecraft.thePlayer.openContainer != null && Minecraft.thePlayer.openContainer.windowId != 0) {
                return;
            }
            if (Minecraft.currentScreen instanceof GuiInventory) {
                int item = -1;
                if (Minecraft.thePlayer.inventory.armorInventory[0] == null) {
                    for (int id : this.boots) {
                        if (this.findItem(id) == -1) continue;
                        item = this.findItem(id);
                        break;
                    }
                }
                if (this.armourIsBetter(0, this.boots)) {
                    item = 8;
                }
                if (Minecraft.thePlayer.inventory.armorInventory[3] == null) {
                    for (int id : this.helmet) {
                        if (this.findItem(id) == -1) continue;
                        item = this.findItem(id);
                        break;
                    }
                }
                if (this.armourIsBetter(3, this.helmet)) {
                    item = 5;
                }
                if (Minecraft.thePlayer.inventory.armorInventory[1] == null) {
                    for (int id : this.leggings) {
                        if (this.findItem(id) == -1) continue;
                        item = this.findItem(id);
                        break;
                    }
                }
                if (this.armourIsBetter(1, this.leggings)) {
                    item = 7;
                }
                if (Minecraft.thePlayer.inventory.armorInventory[2] == null) {
                    for (int id : this.chestplate) {
                        if (this.findItem(id) == -1) continue;
                        item = this.findItem(id);
                        break;
                    }
                }
                if (this.armourIsBetter(2, this.chestplate)) {
                    item = 6;
                }
                if (item != -1) {
                    Minecraft.playerController.windowClick(0, item, 0, 1, Minecraft.thePlayer);
                    this.time.setLastMS();
                    return;
                }
            }
        }
    }
}

