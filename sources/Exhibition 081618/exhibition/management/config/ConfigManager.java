package exhibition.management.config;

import exhibition.Client;
import exhibition.event.EventSystem;
import exhibition.management.SubFolder;
import exhibition.module.Module;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.StringConversions;
import exhibition.util.misc.ChatUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigManager {
   public ConfigManager() {
      String basePath = Client.getDataDir().getAbsolutePath();
      String newPath = basePath + (basePath.endsWith(File.separator) ? SubFolder.Configs.getFolderName() : File.separator + SubFolder.Configs.getFolderName());
      File test = new File(newPath);
      if (!test.exists()) {
         test.mkdirs();
      }

   }

   public void createConfig(String configName) {
      File configDir = this.getFolder();
      if (configDir.isDirectory()) {
         File[] var3 = configDir.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            if (file.getAbsolutePath().endsWith(".cfg") && file.getAbsolutePath().contains(configName + ".cfg")) {
               ChatUtil.printChat("§4[§cE§4]§8 Config already exists!");
               return;
            }
         }
      }

      File file = new File(configDir + "/" + configName + ".cfg");

      try {
         if (file.createNewFile()) {
            this.save(configName);
            ChatUtil.printChat("§4[§cE§4]§8 Successfully created config file!");
         }
      } catch (IOException var7) {
         ChatUtil.printChat("§4[§cE§4]§8 An internal error occurred: §c" + var7.getMessage());
      }

   }

   public void deleteConfig(String configName) {
      File configDir = this.getFolder();
      if (configDir.isDirectory()) {
         File[] var3 = configDir.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            if (file.getAbsolutePath().endsWith(".cfg") && file.getAbsolutePath().toLowerCase().contains(configName.toLowerCase() + ".cfg")) {
               if (file.delete()) {
                  ChatUtil.printChat("§4[§cE§4]§8 Config deleted successfully!");
               } else {
                  ChatUtil.printChat("§4[§cE§4]§8 An error occurred deleting config.!");
               }

               return;
            }
         }

         ChatUtil.printChat("§4[§cE§4]§8 No configs with the name \"§7" + configName + "§8\" exist! Try §a.config list§8!");
      }

   }

   public List getConfigs() {
      File configDir = this.getFolder();
      List configs = new ArrayList();
      if (configDir.isDirectory()) {
         File[] var3 = configDir.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            if (file.getAbsolutePath().endsWith(".cfg")) {
               configs.add(file.getName().replaceAll(".cfg", ""));
            }
         }
      }

      return configs;
   }

   public void load(String configName) {
      File configDir = this.getFolder();
      if (configDir.isDirectory()) {
         File[] var3 = configDir.listFiles();
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               ChatUtil.printChat("§4[§cE§4]§8 No configs with the name \"§7" + configName + "§8\" exist! Try §a.config list§8!");
               break;
            }

            File file = var3[var5];
            if (file.getAbsolutePath().endsWith(".cfg") && file.getAbsolutePath().toLowerCase().contains(configName.toLowerCase() + ".cfg")) {
               ArrayList readContent = new ArrayList();

               try {
                  BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));

                  String str;
                  while((str = in.readLine()) != null) {
                     readContent.add(str);
                  }

                  in.close();
                  if (!readContent.isEmpty()) {
                     this.loadStatus(readContent);
                     this.loadSettings(readContent);
                  }

                  ChatUtil.printChat("§4[§cE§4]§8 Config loaded successfully!");
                  return;
               } catch (Exception var10) {
                  ChatUtil.printChat("§4[§cE§4]§8 Error loading config!");
               }
            }

            ++var5;
         }
      }

   }

   public void save(String configName) {
      File configDir = this.getFolder();
      if (configDir.isDirectory()) {
         File[] var3 = configDir.listFiles();
         int var4 = var3.length;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               ChatUtil.printChat("§4[§cE§4]§8 No configs with the name \"§7" + configName + "§8\" exist! Try §a.config list§8!");
               break;
            }

            File file = var3[var5];
            if (file.getAbsolutePath().endsWith(".cfg") && file.getAbsolutePath().toLowerCase().contains(configName.toLowerCase() + ".cfg")) {
               List newList = (List)Stream.concat(this.saveStatus().stream(), this.saveSettings().stream()).collect(Collectors.toList());

               try {
                  Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
                  Iterator var9 = newList.iterator();

                  while(var9.hasNext()) {
                     String outputLine = (String)var9.next();
                     out.write(outputLine + System.getProperty("line.separator"));
                  }

                  out.close();
                  ChatUtil.printChat("§4[§cE§4]§8 Config saved successfully!");
                  return;
               } catch (Exception var11) {
                  ChatUtil.printChat("§4[§cE§4]§8 An error occurred while saving config: §c" + var11.getMessage());
               }
            }

            ++var5;
         }
      }

   }

   private File getFolder() {
      File folder = new File(Client.getDataDir().getAbsolutePath() + File.separator + SubFolder.Configs.getFolderName());
      if (!folder.exists()) {
         folder.mkdirs();
      }

      return folder;
   }

   private List saveStatus() {
      List fileContent = new ArrayList();
      Module[] var2 = (Module[])Client.getModuleManager().getArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         String displayName = module.getName();
         String enabled = Boolean.toString(module.isEnabled());
         fileContent.add(String.format("%s:%s:%s", "MOD", displayName, enabled));
      }

      return fileContent;
   }

   private List saveSettings() {
      List fileContent = new ArrayList();
      Module[] var2 = (Module[])Client.getModuleManager().getArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         Iterator var6 = module.getSettings().values().iterator();

         while(var6.hasNext()) {
            Setting setting = (Setting)var6.next();
            String displayName;
            String settingName;
            String settingValue;
            if (setting.getValue() instanceof Options) {
               displayName = module.getName();
               settingName = setting.getName();
               settingValue = ((Options)setting.getValue()).getSelected();
               fileContent.add(String.format("%s:%s:%s:%s", "SET", displayName, settingName, settingValue));
            } else if (setting.getValue() instanceof MultiBool) {
               displayName = module.getName();
               settingName = setting.getName();
               List enabled = new ArrayList();
               ((MultiBool)setting.getValue()).getBooleans().forEach((set) -> {
                  enabled.add(((Module) set).getName() + "=" + ((Setting) set).getValue());
               });
               settingValue = enabled.toString();
               fileContent.add(String.format("%s:%s:%s:%s", "SET", displayName, settingName, settingValue));
            } else {
               displayName = module.getName();
               settingName = setting.getName();
               settingValue = setting.getValue().toString();
               fileContent.add(String.format("%s:%s:%s:%s", "SET", displayName, settingName, settingValue));
            }
         }
      }

      return fileContent;
   }

   private void loadStatus(List fileContent) {
      Iterator var2 = fileContent.iterator();

      while(true) {
         while(true) {
            while(true) {
               String[] split;
               do {
                  if (!var2.hasNext()) {
                     return;
                  }

                  String line = (String)var2.next();
                  split = line.split(":");
               } while(!split[0].equalsIgnoreCase("MOD"));

               String displayName = split[1];
               Module[] var6 = (Module[])Client.getModuleManager().getArray();
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  Module module = var6[var8];
                  if (module.getName().equalsIgnoreCase(displayName)) {
                     boolean enabled = Boolean.parseBoolean(split[2]);
                     if (enabled && !module.isEnabled()) {
                        module.setEnabled(true);
                        EventSystem.register(module);
                        module.onEnable();
                        break;
                     }

                     if (!enabled && module.isEnabled()) {
                        module.setEnabled(false);
                        EventSystem.unregister(module);
                        module.onDisable();
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   private void loadSettings(List fileContent) {
      Iterator var2 = fileContent.iterator();

      while(true) {
         String[] split;
         do {
            if (!var2.hasNext()) {
               return;
            }

            String line = (String)var2.next();
            split = line.split(":");
         } while(!split[0].equalsIgnoreCase("SET"));

         Module[] var5 = (Module[])Client.getModuleManager().getArray();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Module module = var5[var7];
            if (module.getName().equalsIgnoreCase(split[1])) {
               Setting setting = Module.getSetting(module.getSettings(), split[2]);
               if (setting != null) {
                  String settingValue = split[3];
                  if (setting.getValue() instanceof Number) {
                     Object newValue = StringConversions.castNumber(settingValue, setting.getValue());
                     if (newValue != null) {
                        setting.setValue(newValue);
                     }
                  } else if (setting.getValue().getClass().equals(String.class)) {
                     String parsed = settingValue.replaceAll("_", " ");
                     setting.setValue(parsed);
                  } else if (setting.getValue().getClass().equals(Boolean.class)) {
                     setting.setValue(Boolean.parseBoolean(settingValue));
                  } else if (setting.getValue().getClass().equals(Options.class)) {
                     ((Options)setting.getValue()).setSelected(settingValue);
                  } else if (setting.getValue().getClass().equals(MultiBool.class)) {
                     MultiBool multiBool = (MultiBool)setting.getValue();
                     List items = Arrays.asList(split[3].replace("[", "").replace("]", "").split("\\s*,\\s*"));
                     items.forEach((o) -> {
                        multiBool.getBooleans().forEach((bool) -> {
                           if (((String) o).contains(((Module) bool).getName())) {
                              ((Setting) bool).setValue(((String) o).split("=")[1].equalsIgnoreCase("true"));
                           }
                        });
                     });
                  }
               }
            }
         }
      }
   }
}
