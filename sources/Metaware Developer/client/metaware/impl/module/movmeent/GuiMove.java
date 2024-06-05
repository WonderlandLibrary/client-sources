package client.metaware.impl.module.movmeent;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

@ModuleInfo(renderName = "GUIMove", name = "GUIMove", category = Category.MOVEMENT)
public class GuiMove extends Module {

    @EventHandler
    private Listener<MovePlayerEvent> eventMoveListener = event -> {
        if(mc.currentScreen instanceof net.minecraft.client.gui.GuiChat || mc.currentScreen == null)
            return;
        keyset(mc.gameSettings.keyBindForward);
        keyset(mc.gameSettings.keyBindLeft);
        keyset(mc.gameSettings.keyBindRight);
        keyset(mc.gameSettings.keyBindBack);
        keyset(mc.gameSettings.keyBindJump);
    };

    private void keyset(KeyBinding key){
        key.pressed = GameSettings.isKeyDown(key);
    }
}
