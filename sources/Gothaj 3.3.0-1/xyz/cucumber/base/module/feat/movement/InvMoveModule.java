package xyz.cucumber.base.module.feat.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventUpdate;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to move while inv is opened",
   name = "Inv Move",
   key = 0
)
public class InvMoveModule extends Mod {
   KeyBinding[] moveKeys = new KeyBinding[]{
      this.mc.gameSettings.keyBindForward,
      this.mc.gameSettings.keyBindBack,
      this.mc.gameSettings.keyBindLeft,
      this.mc.gameSettings.keyBindRight,
      this.mc.gameSettings.keyBindJump
   };

   @Override
   public void onDisable() {
      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindForward) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindForward.pressed = false;
      }

      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindBack) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindBack.pressed = false;
      }

      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindRight) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindRight.pressed = false;
      }

      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindLeft) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindLeft.pressed = false;
      }

      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindJump) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindJump.pressed = false;
      }

      if (!GameSettings.isKeyDown(this.mc.gameSettings.keyBindSprint) || this.mc.currentScreen != null) {
         this.mc.gameSettings.keyBindSprint.pressed = false;
      }
   }

   @EventListener
   public void onUpdate(EventUpdate e) {
      if (!(this.mc.currentScreen instanceof GuiChat)) {
         for (KeyBinding bind : this.moveKeys) {
            KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
         }
      }
   }
}
