package com.example.editme.commands;

import com.example.editme.util.command.syntax.ChunkBuilder;
import com.example.editme.util.command.syntax.parsers.DependantParser;
import com.example.editme.util.command.syntax.parsers.EnumParser;
import com.example.editme.util.config.ConfigurationUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigCommand extends Command {
   private void reload() {
      ConfigurationUtil.loadConfiguration();
      Command.sendChatMessage("Configuration reloaded!");
   }

   public void call(String[] var1) {
      if (var1[0] == null) {
         Command.sendChatMessage("Missing argument &bmode&r: Choose from reload, save or path");
      } else {
         String var2 = var1[0].toLowerCase();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -934641255:
            if (var2.equals("reload")) {
               var3 = 0;
            }
            break;
         case 3433509:
            if (var2.equals("path")) {
               var3 = 2;
            }
            break;
         case 3522941:
            if (var2.equals("save")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            this.reload();
            break;
         case 1:
            try {
               ConfigurationUtil.saveConfigurationUnsafe();
               Command.sendChatMessage("Saved configuration!");
            } catch (IOException var18) {
               var18.printStackTrace();
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Failed to save! ").append(var18.getMessage())));
            }
            break;
         case 2:
            if (var1[1] == null) {
               Path var4 = Paths.get(ConfigurationUtil.getConfigName());
               Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Path to configuration: &b").append(var4.toAbsolutePath().toString())));
            } else {
               String var21 = var1[1];
               if (!ConfigurationUtil.isFilenameValid(var21)) {
                  Command.sendChatMessage(String.valueOf((new StringBuilder()).append("&b").append(var21).append("&r is not a valid path")));
               } else {
                  try {
                     BufferedWriter var5 = Files.newBufferedWriter(Paths.get("EDITMELastConfig.txt"));
                     Throwable var6 = null;

                     try {
                        var5.write(var21);
                        this.reload();
                        Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Configuration path set to &b").append(var21).append("&r!")));
                     } catch (Throwable var17) {
                        var6 = var17;
                        throw var17;
                     } finally {
                        if (var5 != null) {
                           if (var6 != null) {
                              try {
                                 var5.close();
                              } catch (Throwable var16) {
                                 var6.addSuppressed(var16);
                              }
                           } else {
                              var5.close();
                           }
                        }

                     }
                  } catch (IOException var20) {
                     var20.printStackTrace();
                     Command.sendChatMessage(String.valueOf((new StringBuilder()).append("Couldn't set path: ").append(var20.getMessage())));
                  }
               }
            }
            break;
         default:
            Command.sendChatMessage("Incorrect mode, please choose from: reload, save or path");
         }

      }
   }

   public ConfigCommand() {
      super("config", (new ChunkBuilder()).append("mode", true, new EnumParser(new String[]{"reload", "save", "path"})).append("path", true, new DependantParser(0, new DependantParser.Dependency(new String[][]{{"path", "path"}}, ""))).build());
   }
}
