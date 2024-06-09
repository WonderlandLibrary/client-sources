package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by myche on 2/25/2017.
 */
public class AutoArmor extends Module {

    private final int[] helmet;
    private final int[] chestplate;
    private final int[] leggings;
    private final int[] boots;
    int delay;

    public AutoArmor() {
        super("AutoArmor", 0, Category.COMBAT);
        boots = new int[]{313, 309, 317, 305, 301};
        chestplate = new int[]{311, 307, 315, 303, 299};
        helmet = new int[]{310, 306, 314, 302, 298};
        leggings = new int[]{312, 308, 316, 304, 300};
        delay = 0;
    }

    private int getItem(final int id) {
        for (int index = 9; index < 45; ++index) {
            final ItemStack item = mc.thePlayer.inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return index;
            }
        }
        return -1;
    }

    public void doBestAutoarmor() {
        ++delay;
        if (delay >= 1.5 && (mc.thePlayer.openContainer == null || mc.thePlayer.openContainer.windowId == 0)) {
            boolean dropkek = false;
            int item = -1;
            if (mc.thePlayer.inventory.armorInventory[0] == null) {
                for (final int id : boots) {
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (armorIsBetter(0, boots)) {
                item = 8;
                dropkek = true;
            }
            if (mc.thePlayer.inventory.armorInventory[3] == null) {
                for (final int id : helmet) {
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (armorIsBetter(3, helmet)) {
                item = 5;
                dropkek = true;
            }
            if (mc.thePlayer.inventory.armorInventory[1] == null) {
                for (final int id : leggings) {
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (armorIsBetter(1, leggings)) {
                item = 7;
                dropkek = true;
            }
            if (mc.thePlayer.inventory.armorInventory[2] == null) {
                for (final int id : chestplate) {
                    if (getItem(id) != -1) {
                        item = getItem(id);
                        break;
                    }
                }
            }
            if (armorIsBetter(2, chestplate)) {
                item = 6;
                dropkek = true;
            }
            boolean var7 = false;
            for (final ItemStack stack : mc.thePlayer.inventory.mainInventory) {
                if (stack == null) {
                    var7 = true;
                    break;
                }
            }
            dropkek = (dropkek && !var7);
            if (item != -1) {
                mc.playerController.windowClick(0, item, 0, dropkek ? 4 : 1, mc.thePlayer);
                delay = 0;
            }
        }
    }

    @SubscribeEvent
    public void onPre(UpdateEvent event) {
        if (event.type == EventType.PRE) {
            doBestAutoarmor();
        }
    }

    public boolean armorIsBetter(final int slot, final int[] armortype) {
        if (mc.thePlayer.inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int invIndex = 0;
            int finalCurrentIndex = -1;
            int finalInvIndex = -1;
            for (final int armor : armortype) {
                if (Item.getIdFromItem(mc.thePlayer.inventory.armorInventory[slot].getItem()) == armor) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
                ++currentIndex;
            }
            for (final int armor : armortype) {
                if (getItem(armor) != -1) {
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
}
