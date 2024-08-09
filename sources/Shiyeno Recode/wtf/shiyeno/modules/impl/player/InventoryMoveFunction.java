package wtf.shiyeno.modules.impl.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "InventoryMove",
        type = Type.Player
)
public class InventoryMoveFunction extends Function {
    private final TimerUtil timerUtil = new TimerUtil();

    public InventoryMoveFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            this.handleEventUpdate();
        }
    }

    private void handleEventUpdate() {
        KeyBinding[] keys = new KeyBinding[]{mc.gameSettings.keyBindForward, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump, mc.gameSettings.keyBindSprint};
        if (!(mc.currentScreen instanceof ChatScreen) && !(mc.currentScreen instanceof EditSignScreen) && mc.currentScreen != null) {
            KeyBinding[] var2 = keys;
            int var3 = keys.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                KeyBinding keyBinding = var2[var4];
                keyBinding.setPressed(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), keyBinding.getDefault().getKeyCode()));
            }

        }
    }
}