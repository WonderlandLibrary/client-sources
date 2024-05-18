package rina.turok.bope.bopemod;

import net.minecraft.client.Minecraft;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.manager.BopeCommandManager;

public class BopeCommand {
   public final Minecraft mc = Minecraft.getMinecraft();
   String name;
   String description;

   public BopeCommand(String name, String description) {
      this.name = name;
      this.description = description;
   }

   public boolean get_message(String[] message) {
      return false;
   }

   public String get_name() {
      return this.name;
   }

   public String get_description() {
      return this.description;
   }

   public String current_prefix() {
      Bope.get_command_manager();
      return BopeCommandManager.get_prefix();
   }
}
