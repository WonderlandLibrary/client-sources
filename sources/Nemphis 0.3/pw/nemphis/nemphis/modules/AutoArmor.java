/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.management.TimeHelper;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.COMBAT, color=-1871285, name="AutoArmor")
public class AutoArmor
extends ToggleableModule {
    private TimeHelper time = new TimeHelper();
    private int[] boots = new int[]{301, 317, 305, 309, 313};
    private int[] chestplate = new int[]{311, 307, 315, 303, 299};
    private int[] helmet = new int[]{310, 306, 314, 302, 298};
    private int[] leggings = new int[]{312, 308, 316, 304, 300};

    private int findItem(int id) {
        int index = 9;
        while (index < 45) {
            ItemStack item = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    @EventListener
    private void onTick(UpdateEvent event) {
        int item = -1;
        if (this.time.hasReached(15.0f)) {
            int id;
            int j;
            int[] arrayOfInt;
            int i;
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[0] == null) {
                arrayOfInt = this.boots;
                j = arrayOfInt.length;
                i = 0;
                while (i < j) {
                    id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                    ++i;
                }
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[1] == null) {
                arrayOfInt = this.leggings;
                j = arrayOfInt.length;
                i = 0;
                while (i < j) {
                    id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                    ++i;
                }
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[2] == null) {
                arrayOfInt = this.chestplate;
                j = arrayOfInt.length;
                i = 0;
                while (i < j) {
                    id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                    ++i;
                }
            }
            if (AutoArmor.mc.thePlayer.inventory.armorInventory[3] == null) {
                arrayOfInt = this.helmet;
                j = arrayOfInt.length;
                i = 0;
                while (i < j) {
                    id = arrayOfInt[i];
                    if (this.findItem(id) != -1) {
                        item = this.findItem(id);
                        break;
                    }
                    ++i;
                }
            }
            if (item != -1) {
                AutoArmor.mc.playerController.windowClick(0, item, 0, 1, AutoArmor.mc.thePlayer);
                this.time.resetTimer();
            }
        }
    }
}

