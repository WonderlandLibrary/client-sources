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
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import org.lwjgl.input.Keyboard;

/**
 * @author avox | lmao
 * @since on 30.07.2020.
 */
public class ChestStealer extends Mod {

    public Setting delay = new Setting("Steal Delay", 0, 200, 100, true, this);
    public Setting delayRandom = new Setting("Random Factor", 0, 100, 50, true, this);
    public Setting intelligent = new Setting("Intelligent", true, this);
    public Setting instant = new Setting("Steal Instant", true, this);
    TimeUtil timer = new TimeUtil();
    RandomUtil random = new RandomUtil();
    boolean empty;

    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_NONE, true, ModCategory.PLAYER);

        Init.getInstance().settingManager.addSetting(delay);
        Init.getInstance().settingManager.addSetting(delayRandom);
        Init.getInstance().settingManager.addSetting(intelligent);
        Init.getInstance().settingManager.addSetting(instant);
    }

    @EventTarget
    public void event(Event event) {
        if (event.getType() == Type.RENDER2D) {
            if (instant.isState()) {
                delay.setVisible(false);
                delayRandom.setVisible(false);
            } else {
                delay.setVisible(true);
                delayRandom.setVisible(true);
            }

        }

        if (getState() && event.getType() == Type.TICKUPDATE) {

            if (!getInfoName().equals("" + delayRandom.getCurrent()))
                this.setInfoName("" + Math.round(delay.getCurrent()));

            if (mc.currentScreen instanceof GuiChest) {
                ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
                empty = true;
                if (mc.thePlayer.isMoving())
                    return;
                double finalDelay = instant.isState() ? 0 : delay.getCurrent() + random.randomDouble(-delayRandom.getCurrent() / 2 - 5, delayRandom.getCurrent() / 2 + 5);
                for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
                    if ((chest.getSlot(i).getHasStack() && chest.getSlot(i).getStack() != null) && (!InventoryUtil.isTrash(chest.getSlot(i).getStack()) || !intelligent.isState())) {
                        if (timer.isDelayComplete(finalDelay)) {
                            mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
                            timer.reset();
                        }
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    mc.thePlayer.closeScreen();
                }
            }
        }
    }

}