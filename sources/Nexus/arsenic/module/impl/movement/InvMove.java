package arsenic.module.impl.movement;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.*;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "InvMove",category = ModuleCategory.Movement)
public class InvMove extends Module {

    @EventLink
    public final Listener<EventGameLoop> listener  = event -> {
        if(!(mc.currentScreen instanceof GuiContainer))
            return;

        while (Keyboard.next()) {
            int k = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
            KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());

            if (Keyboard.getEventKeyState()) {
                KeyBinding.onTick(k);
            }
        }
    };
}
