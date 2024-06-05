package alphentus.mod.mods.player;

import alphentus.event.Event;
import alphentus.event.Type;
import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.ModCategory;
import alphentus.settings.Setting;
import alphentus.utils.InventoryUtil;
import alphentus.utils.RandomUtil;
import alphentus.utils.TimeUtil;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class AutoArmor extends Mod {

    final InventoryUtil inventoryUtil = new InventoryUtil();

    public Setting startDelay = new Setting("Start Delay", 10, 1000, 450, true, this);
    public Setting delay = new Setting("Delay", 0, 200, 120, true, this);

    RandomUtil randomUtil = new RandomUtil();
    TimeUtil timeUtil = new TimeUtil();
    TimeUtil timeUtilOpening = new TimeUtil();

    public AutoArmor() {
        super("AutoArmor", 0, true, ModCategory.PLAYER);
        Init.getInstance().settingManager.addSetting(startDelay);
        Init.getInstance().settingManager.addSetting(delay);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.TICKUPDATE)
            return;

        if(!(mc.currentScreen instanceof GuiInventory)){
            timeUtilOpening.reset();
        }

        if (!(mc.currentScreen instanceof GuiInventory))
            return;

        if(!timeUtilOpening.isDelayComplete(startDelay.getCurrent()))
            return;

        for (int i = 1; i < 5; i++) {

            if (mc.thePlayer.inventoryContainer.getSlot(4 + i).getHasStack()) {
                ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(4 + i).getStack();
                if (isBestArmor(itemStack, i)) {
                    continue;
                }else {
                    if (timeUtil.isDelayComplete(delay.getCurrent() + randomUtil.randomInt(-25, 25))) {
                        drop(4 + i);
                    }
                }
            }


            for (int j = 9; j < 45; j++) {
                if (mc.thePlayer.inventoryContainer.getSlot(j).getHasStack()) {
                    ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(j).getStack();
                    if (getDamageReduceAmount(itemStack) > 0.0F && isBestArmor(itemStack, i)) {
                        if (timeUtil.isDelayComplete(delay.getCurrent() + randomUtil.randomInt(-25, 25))) {
                            shiftClick(j);
                            timeUtil.reset();
                        }
                    }
                }
            }
        }
    }


    public float getDamageReduceAmount(ItemStack itemStack) {
        float damageReduceAmount = 0.0F;
        if (itemStack.getItem() instanceof ItemArmor) {
            ItemArmor itemArmor = ((ItemArmor) itemStack.getItem());
            damageReduceAmount = itemArmor.damageReduceAmount;
        }
        return damageReduceAmount;
    }


    public boolean isBestArmor(ItemStack itemStack, int type) {
        float damageReduceAmount = getDamageReduceAmount(itemStack);
        String armorType = "";

        if (type == 1)
            armorType = "helmet";
        if (type == 2)
            armorType = "chestplate";
        if (type == 3)
            armorType = "leggings";
        if (type == 4)
            armorType = "boots";

        if (!(itemStack.getItem().getUnlocalizedName().contains(armorType)))
            return false;

        for (int i = 5; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemStack1 = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (getDamageReduceAmount(itemStack1) > damageReduceAmount && itemStack1.getItem().getUnlocalizedName().contains(armorType))
                    return false;
            }
        }

        return true;
    }

    public void shiftClick(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
    }

    public void drop(int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
