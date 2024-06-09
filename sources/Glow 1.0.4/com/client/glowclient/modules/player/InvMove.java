package com.client.glowclient.modules.player;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.client.settings.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.gui.*;
import com.client.glowclient.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class InvMove extends ModuleContainer
{
    public InvMove() {
        super(Category.PLAYER, "InvMove", true, -1, "Move around while in your inventory");
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!ModuleManager.M("AutoWalk").k()) {
            final KeyBinding[] array = { Wrapper.mc.gameSettings.keyBindForward, Wrapper.mc.gameSettings.keyBindBack, Wrapper.mc.gameSettings.keyBindLeft, Wrapper.mc.gameSettings.keyBindRight, Wrapper.mc.gameSettings.keyBindJump, Wrapper.mc.gameSettings.keyBindSprint };
            if (Wrapper.mc.currentScreen instanceof GuiOptions || Wrapper.mc.currentScreen instanceof GuiVideoSettings || Wrapper.mc.currentScreen instanceof GuiScreenOptionsSounds || Wrapper.mc.currentScreen instanceof GuiContainer || Wrapper.mc.currentScreen instanceof GuiIngameMenu || Wrapper.mc.currentScreen instanceof iD || Wrapper.mc.currentScreen instanceof YD || Wrapper.mc.currentScreen instanceof jD) {
                final KeyBinding[] array2;
                final int length = (array2 = array).length;
                int n;
                int i = n = 0;
                while (i < length) {
                    final KeyBinding keyBinding;
                    final int keyCode = (keyBinding = array2[n]).getKeyCode();
                    final KeyBinding keyBinding2 = keyBinding;
                    ++n;
                    KeyBinding.setKeyBindState(keyCode, Keyboard.isKeyDown(keyBinding2.getKeyCode()));
                    i = n;
                }
            }
            else if (Wrapper.mc.currentScreen == null) {
                final KeyBinding[] array3;
                final int length2 = (array3 = array).length;
                int n2;
                int j = n2 = 0;
                while (j < length2) {
                    final KeyBinding keyBinding3;
                    if (!Keyboard.isKeyDown((keyBinding3 = array3[n2]).getKeyCode())) {
                        KeyBinding.setKeyBindState(keyBinding3.getKeyCode(), false);
                    }
                    j = ++n2;
                }
            }
        }
    }
}
