package rina.turok.bope.bopemod.hacks.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeInventoryWalk extends BopeModule {
   public BopeInventoryWalk() {
      super(BopeCategory.BOPE_MOVEMENT);
      this.name = "Inventory Walk";
      this.tag = "InventoryWalk";
      this.description = "Inventory walk.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      if (this.mc.player != null && this.mc.world != null) {
         if (this.mc.currentScreen instanceof GuiChat || this.mc.currentScreen == null) {
            return;
         }

         int[] var1 = new int[]{this.mc.gameSettings.keyBindForward.getKeyCode(), this.mc.gameSettings.keyBindLeft.getKeyCode(), this.mc.gameSettings.keyBindRight.getKeyCode(), this.mc.gameSettings.keyBindBack.getKeyCode(), this.mc.gameSettings.keyBindJump.getKeyCode()};
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            int keys = var1[var3];
            if (Keyboard.isKeyDown(keys)) {
               KeyBinding.setKeyBindState(keys, true);
            } else {
               KeyBinding.setKeyBindState(keys, false);
            }
         }
      }

   }
}
