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
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 09.08.2020.
 */
public class InventoryManager extends Mod {

    public Setting delay = new Setting("Delay", 0, 200, 120, true, this);
    public Setting openInv = new Setting("Opened Inventory", true, this);
    public Setting clean = new Setting("Clean", true, this);
    public Setting sort = new Setting("Sort", true, this);
    RandomUtil randomUtil = new RandomUtil();
    TimeUtil timer = new TimeUtil();
    TimeUtil openInvTimer = new TimeUtil();

    public InventoryManager() {
        super("Inventory Manager", Keyboard.KEY_NONE, true, ModCategory.PLAYER);

        Init.getInstance().settingManager.addSetting(delay);
        Init.getInstance().settingManager.addSetting(clean);
        Init.getInstance().settingManager.addSetting(sort);
    }

    @EventTarget
    public void event(Event event) {
        if (!getState())
            return;
        if (event.getType() != Type.TICKUPDATE)
            return;
        if (!(mc.currentScreen instanceof GuiInventory)) {
            openInvTimer.reset();
        }
        if (!(mc.currentScreen instanceof GuiInventory) && openInv.isState())
            return;
        if (mc.thePlayer.isMoving())
            return;

        if (!openInvTimer.isDelayComplete(Init.getInstance().modManager.getModuleByClass(AutoArmor.class).startDelay.getCurrent() + Init.getInstance().modManager.getModuleByClass(AutoArmor.class).delay.getCurrent() * 4))
            return;

        for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (timer.isDelayComplete(delay.getCurrent() + randomUtil.randomInt(-25, 25))) {

                    if (sort.isState() && itemStack.getItem() instanceof ItemSword && itemStack == InventoryUtil.bestSword() && mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtil.bestSword()) && mc.thePlayer.inventoryContainer.getSlot(36).getStack() != InventoryUtil.bestSword()) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 2, mc.thePlayer);
                    } else if (sort.isState() && itemStack.getItem() instanceof ItemBow && itemStack == InventoryUtil.bestBow() && mc.thePlayer.inventoryContainer.getInventory().contains(InventoryUtil.bestBow()) && mc.thePlayer.inventoryContainer.getSlot(37).getStack() != InventoryUtil.bestBow()) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 1, 2, mc.thePlayer);
                    } else if (clean.isState() && (InventoryUtil.isTrash(itemStack) || InventoryUtil.isBadTool(itemStack))) {
                        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 1, 4, mc.thePlayer);
                        timer.reset();
                        break;
                    }
                }
            }
        }
    }
}