package us.dev.direkt.module.internal.player;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.player.update.EventPreMotionUpdate;
import us.dev.direkt.module.ModCategory;
import us.dev.direkt.module.annotations.ModData;
import us.dev.direkt.module.ToggleableModule;
import us.dev.dvent.Listener;
import us.dev.dvent.Link;

/**
 * Created by Meckimp on 1/18/2016.
 */
@ModData(label = "Auto Armor", aliases = "autoarmour", category = ModCategory.PLAYER)
public class AutoArmor extends ToggleableModule {

    private final Timer timer =  new Timer();

    @Listener
    protected Link<EventPreMotionUpdate> onPreMotionUpdate = new Link<>(event -> {
        if ((!this.timer.hasReach(150)) || (Wrapper.getPlayer().capabilities.isCreativeMode) || ((Wrapper.getMinecraft().currentScreen != null) && (!(Wrapper.getMinecraft().currentScreen instanceof GuiChat)))) {
            return;
        }
        for (byte b = 5; b <= 8; b = (byte) (b + 1)) {
            if (equipArmor(b)) {
                this.timer.reset();
                break;
            }
        }
    });

    private boolean equipArmor(byte b) {
        int currentProt = -1;
        byte slot = -1;
        ItemArmor current = null;
        if ((Wrapper.getPlayer().inventoryContainer.getSlot(b).getStack() != null) &&
                ((Wrapper.getPlayer().inventoryContainer.getSlot(b).getStack().getItem() instanceof ItemArmor))) {
            current = (ItemArmor) Wrapper.getPlayer().inventoryContainer.getSlot(b).getStack().getItem();
            currentProt = current.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("protection"),
                    Wrapper.getPlayer().inventoryContainer.getSlot(b).getStack());
        }
        for (byte i = 9; i <= 44; i = (byte) (i + 1)) {
            ItemStack stack = Wrapper.getPlayer().inventoryContainer.getSlot(i).getStack();
            if ((stack != null) && ((stack.getItem() instanceof ItemArmor))) {
                ItemArmor armor = (ItemArmor) stack.getItem();
                int armorProt = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("protection"), stack);
                if ((checkArmor(armor, b)) && ((current == null) || (currentProt < armorProt))) {
                    currentProt = armorProt;
                    current = armor;
                    slot = i;
                }
            }
        }
        if (slot != -1) {
            boolean isNull = Wrapper.getPlayer().inventoryContainer.getSlot(b).getStack() == null;
            if (!isNull) {
                clickSlot(b, 0, false);
            }
            clickSlot(slot, 0, true);
            if (!isNull) {
                clickSlot(slot, 0, false);
            }
            return true;
        }
        return false;
    }

    private void clickSlot(int slot, int mouseButton, boolean shiftClick) {
        Wrapper.getMinecraft().playerController.windowClick(Wrapper.getPlayer().inventoryContainer.windowId, slot, mouseButton, shiftClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, Wrapper.getPlayer());
    }

    private boolean checkArmor(ItemArmor item, byte b) {
        return ((b == 5) && (isHelmet(item))) || ((b == 6) && (isChest(item))) || ((b == 7) && (isLeggings(item))) || ((b == 8) && (isBoots(item)));
    }

    private boolean isHelmet(ItemArmor item) {
        return item.armorType == EntityEquipmentSlot.HEAD;
    }

    private boolean isChest(ItemArmor item) {
        return item.armorType == EntityEquipmentSlot.CHEST;
    }

    private boolean isLeggings(ItemArmor item) {
        return item.armorType == EntityEquipmentSlot.LEGS;
    }

    private boolean isBoots(ItemArmor item) {
        return item.armorType == EntityEquipmentSlot.FEET;
    }
	
}
