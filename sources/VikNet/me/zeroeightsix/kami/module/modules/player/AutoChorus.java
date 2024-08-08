package me.zeroeightsix.kami.module.modules.player;

import me.zeroeightsix.kami.module.Module;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;

//made by viktisen
//see NoCrystalWaste under player category for more

@Module.Info(name = "AutoChorus", description = "Holds right click when holding chorus fruit", category = Module.Category.PLAYER)
public class AutoChorus extends Module {

    private boolean eat = false;
    private int lastSlot = -1;

    @Override
    public void onUpdate() {
        if (eat && !mc.player.isHandActive()) {
            if (lastSlot != -1) {
                mc.player.inventory.currentItem = lastSlot;
                lastSlot = -1;
            }
            eat = false;
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            return;

        }
        if (mc.player.getHeldItemMainhand().getItem() == Items.CHORUS_FRUIT) {
            eat = true;

            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.rightClickMouse();
        }
    }
}
