package rina.turok.bope.bopemod.manager;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import rina.turok.bope.bopemod.commands.BopeListCommand;

public class BopeCommandManager {
   private String tag;
   public static BopeListCommand command_list;

   public BopeCommandManager(String tag) {
      this.tag = tag;
      command_list = new BopeListCommand((new Style()).setColor(TextFormatting.BLUE));
   }

   public void init_commands() {
   }

   public static void set_prefix(String new_prefix) {
      command_list.set_prefix(new_prefix);
   }

   public static String get_prefix() {
      return command_list.get_prefix();
   }

   public String get_tag() {
      return this.tag;
   }
}
