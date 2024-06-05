package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class Invmove extends Module {
   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         if (this.mc.currentScreen instanceof GuiScreen) {
            EntityPlayerSP var10000;
            if (Keyboard.isKeyDown(205) && !(this.mc.currentScreen instanceof GuiChat)) {
               var10000 = this.mc.thePlayer;
               var10000.rotationYaw += 8.0F;
            }

            if (Keyboard.isKeyDown(203) && !(this.mc.currentScreen instanceof GuiChat)) {
               var10000 = this.mc.thePlayer;
               var10000.rotationYaw -= 8.0F;
            }

            if (Keyboard.isKeyDown(200) && !(this.mc.currentScreen instanceof GuiChat)) {
               var10000 = this.mc.thePlayer;
               var10000.rotationPitch -= 8.0F;
            }

            if (Keyboard.isKeyDown(208) && !(this.mc.currentScreen instanceof GuiChat)) {
               var10000 = this.mc.thePlayer;
               var10000.rotationPitch += 8.0F;
            }
         }

         KeyBinding[] var2 = new KeyBinding[]{this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindJump, this.mc.gameSettings.keyBindSprint};
         KeyBinding var3;
         int var4;
         int var5;
         KeyBinding[] var6;
         if (this.mc.currentScreen instanceof GuiScreen && !(this.mc.currentScreen instanceof GuiChat)) {
            var6 = var2;
            var5 = var2.length;

            for(var4 = 0; var4 < var5; ++var4) {
               var3 = var6[var4];
               var3.pressed = Keyboard.isKeyDown(var3.getKeyCode());
            }
         } else {
            var6 = var2;
            var5 = var2.length;

            for(var4 = 0; var4 < var5; ++var4) {
               var3 = var6[var4];
               if (!Keyboard.isKeyDown(var3.getKeyCode())) {
                  KeyBinding.setKeyBindState(var3.getKeyCode(), false);
               }
            }
         }
      }

   }

   public Invmove() {
      super("Invmove", 0, Module.Category.MOVEMENT);
   }
}
