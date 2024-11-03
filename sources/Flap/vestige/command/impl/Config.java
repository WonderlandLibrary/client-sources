package vestige.command.impl;

import java.io.File;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import vestige.Flap;
import vestige.command.Command;
import vestige.util.misc.LogUtil;

public class Config extends Command {
   public Config() {
      super("Config", "Loads or saves a config.");
   }

   public void onCommand(String[] args) {
      if (args.length < 2) {
         LogUtil.addChatMessage("&9&l[FLAP]&r Invalid usage. Correct usage: .config <load|save|list> [configName]");
      } else {
         String action = args[1].toLowerCase();
         byte var4 = -1;
         switch(action.hashCode()) {
         case 3322014:
            if (action.equals("list")) {
               var4 = 2;
            }
            break;
         case 3327206:
            if (action.equals("load")) {
               var4 = 0;
            }
            break;
         case 3522941:
            if (action.equals("save")) {
               var4 = 1;
            }
         }

         switch(var4) {
         case 0:
         case 1:
            if (args.length < 3) {
               LogUtil.addChatMessage("&9&l[FLAP]&r Please specify a config name.");
               return;
            }

            String configName = args[2];
            if ("load".equals(action)) {
               boolean success = Flap.instance.getFileSystem().loadConfig(configName, false);
               if (success) {
                  LogUtil.addChatMessage("&9&l[FLAP]&r Loaded config " + configName);
               } else {
                  LogUtil.addChatMessage("&9&l[FLAP]&r Config not found.");
               }
            } else {
               Flap.instance.getFileSystem().saveConfig(configName);
               LogUtil.addChatMessage("&9&l[FLAP]&r Saved config " + configName);
            }
            break;
         case 2:
            File mcDir = Minecraft.getMinecraft().mcDataDir;
            File vestigeDir = new File(mcDir, "Flap");
            File configDir = new File(vestigeDir, "configs");
            if (configDir.exists() && configDir.isDirectory()) {
               String[] configFiles = configDir.list((dir, name) -> {
                  return name.endsWith(".txt");
               });
               if (configFiles != null && configFiles.length > 0) {
                  StringBuilder configList = new StringBuilder("&9&l[FLAP]&r Available configs: ");
                  Arrays.stream(configFiles).map((fileName) -> {
                     return fileName.substring(0, fileName.length() - 4);
                  }).forEach((nameWithoutExtension) -> {
                     configList.append(nameWithoutExtension).append(", ");
                  });
                  if (configList.length() > 0) {
                     configList.setLength(configList.length() - 2);
                  }

                  LogUtil.addChatMessage(configList.toString());
               } else {
                  LogUtil.addChatMessage("&9&l[FLAP]&r No configs found.");
               }
            } else {
               LogUtil.addChatMessage("&9&l[FLAP]&r Config directory not found.");
            }
            break;
         default:
            LogUtil.addChatMessage("&9&l[FLAP]&r Invalid action. Use load, save, or list.");
         }

      }
   }
}
