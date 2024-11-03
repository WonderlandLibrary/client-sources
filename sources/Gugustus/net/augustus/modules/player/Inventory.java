package net.augustus.modules.player;

import java.awt.Color;

import net.augustus.events.EventSilentMove;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Inventory extends Module {
   public Inventory() {
      super("Inventory", new Color(11, 143, 125), Categorys.PLAYER);
   }

   @Override
   public void onEnable() {

   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      if (!(mc.currentScreen instanceof GuiChat)) {
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()));
         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode()));
         if (mm.sprint.isToggled()
            /*&& !mm.scaffoldWalk.isToggled()
            && !mm.newScaffold.isToggled()*/
            && (!mm.blockFly.isToggled() || mm.blockFly.sprint.getBoolean())) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
         }
      }
   }
}
