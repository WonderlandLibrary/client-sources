package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.management.keybinding.KeyMask;
import exhibition.management.keybinding.Keybind;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.util.misc.ChatUtil;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
   public Bind(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         Module module = null;
         if (args.length > 0) {
            module = Client.getModuleManager().get(args[0]);
         }

         if (module == null) {
            this.printUsage();
         } else {
            if (args.length == 1) {
               Keybind key = module.getKeybind();
               ChatUtil.printChat("§4[§cE§4]§8 " + module.getName() + ": " + (key.getMask() == KeyMask.None ? "" : key.getMask().name() + " + ") + key.getKeyStr());
            } else {
               Keybind keybind;
               int keyIndex;
               if (args.length == 2) {
                  keyIndex = Keyboard.getKeyIndex(args[1].toUpperCase());
                  keybind = new Keybind(module, keyIndex);
                  module.setKeybind(keybind);
                  keybind = module.getKeybind();
                  ChatUtil.printChat("§4[§cE§4]§8 Set " + module.getName() + " to " + (keybind.getMask() == KeyMask.None ? "" : keybind.getMask().name() + " + ") + keybind.getKeyStr());
               } else if (args.length == 3) {
                  keyIndex = Keyboard.getKeyIndex(args[1].toUpperCase());
                  KeyMask mask = KeyMask.getMask(args[2]);
                  keybind = new Keybind(module, keyIndex, mask);
                  module.setKeybind(keybind);
                  Keybind key = module.getKeybind();
                  ChatUtil.printChat("§4[§cE§4]§8 Set " + module.getName() + " to " + (key.getMask() == KeyMask.None ? "" : key.getMask().name() + " + ") + key.getKeyStr());
               }
            }

            ModuleManager.saveStatus();
         }
      }
   }

   public String getUsage() {
      return "bind <Module> " + EnumChatFormatting.ITALIC + "[optional]" + EnumChatFormatting.RESET + "<Key> " + EnumChatFormatting.ITALIC + "[optional]" + EnumChatFormatting.RESET + "<Mask>";
   }
}
