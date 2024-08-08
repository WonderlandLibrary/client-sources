package com.example.editme.modules.movement;

import com.example.editme.modules.Module;
import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;

@Module.Info(
   name = "InvLook",
   category = Module.Category.MOVEMENT
)
public class InvLook extends Module {
   public void onUpdate() {
      if (mc.field_71439_g != null && mc.field_71462_r != null && !(mc.field_71462_r instanceof GuiChat)) {
         if (Keyboard.isKeyDown(203)) {
            mc.field_71439_g.field_70177_z -= 10.0F;
         }

         if (Keyboard.isKeyDown(205)) {
            mc.field_71439_g.field_70177_z += 10.0F;
         }

         if (Keyboard.isKeyDown(200)) {
            mc.field_71439_g.field_70125_A -= 10.0F;
         }

         if (Keyboard.isKeyDown(208)) {
            mc.field_71439_g.field_70125_A += 10.0F;
         }

      }
   }
}
