package com.shroomclient.shroomclientnextgen.mixin;

import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.KeyPressEvent;
import com.shroomclient.shroomclientnextgen.events.impl.KeyReleaseEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.client.ClickGUI;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.InventoryMove;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.ClickGUIScreen;
import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(at = @At("RETURN"), method = "onKey")
    private void onKey(
        long window,
        int key,
        int scancode,
        int action,
        int modifiers,
        CallbackInfo info
    ) {
        if (
            (C.mc.currentScreen instanceof ClickGUIScreen &&
                !ClickGUI.search.isFocused()) ||
            (ModuleManager.isEnabled(InventoryMove.class) &&
                (C.mc.currentScreen instanceof InventoryScreen ||
                    C.mc.currentScreen instanceof GenericContainerScreen)) ||
            C.mc.currentScreen == null
        ) {
            if (action == 0) {
                Bus.post(new KeyReleaseEvent(key, scancode));
            }
            if (action == 1) {
                Bus.post(new KeyPressEvent(key, scancode));
            }
        }
    }
}
