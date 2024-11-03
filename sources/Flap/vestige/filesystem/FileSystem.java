package vestige.filesystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import vestige.Flap;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;

public class FileSystem {
   private File vestigeDir;
   private File vestigeConfigDir;

   public FileSystem() {
      File mcDir = Minecraft.getMinecraft().mcDataDir;
      this.vestigeDir = new File(mcDir, "Flap");
      if (!this.vestigeDir.exists()) {
         this.vestigeDir.mkdir();
      }

      this.vestigeConfigDir = new File(this.vestigeDir, "configs");
      if (!this.vestigeConfigDir.exists()) {
         this.vestigeConfigDir.mkdir();
      }

   }

   public void saveConfig(String configName) {
      configName = configName.toLowerCase();

      try {
         File configFile = new File(this.vestigeConfigDir, configName + ".txt");
         if (!configFile.exists()) {
            configFile.createNewFile();
         }

         PrintWriter writer = new PrintWriter(configFile);
         ArrayList<String> toWrite = new ArrayList();
         Iterator var5 = Flap.instance.getModuleManager().modules.iterator();

         while(true) {
            Module m;
            do {
               if (!var5.hasNext()) {
                  var5 = toWrite.iterator();

                  while(var5.hasNext()) {
                     String s = (String)var5.next();
                     writer.println(s);
                  }

                  writer.close();
                  return;
               }

               m = (Module)var5.next();
               toWrite.add("State:" + m.getName() + ":" + m.isEnabled());
            } while(m.getSettings().isEmpty());

            Iterator var7 = m.getSettings().iterator();

            while(var7.hasNext()) {
               AbstractSetting s = (AbstractSetting)var7.next();
               if (s instanceof BooleanSetting) {
                  BooleanSetting boolSetting = (BooleanSetting)s;
                  toWrite.add("Setting:" + m.getName() + ":" + boolSetting.getName() + ":" + boolSetting.isEnabled());
               } else if (s instanceof ModeSetting) {
                  ModeSetting modeSetting = (ModeSetting)s;
                  toWrite.add("Setting:" + m.getName() + ":" + modeSetting.getName() + ":" + modeSetting.getMode());
               } else if (s instanceof DoubleSetting) {
                  DoubleSetting doubleSetting = (DoubleSetting)s;
                  toWrite.add("Setting:" + m.getName() + ":" + doubleSetting.getName() + ":" + doubleSetting.getValue());
               } else if (s instanceof IntegerSetting) {
                  IntegerSetting intSetting = (IntegerSetting)s;
                  toWrite.add("Setting:" + m.getName() + ":" + intSetting.getName() + ":" + intSetting.getValue());
               }
            }
         }
      } catch (IOException var10) {
         var10.printStackTrace();
      }
   }

   public boolean loadConfig(String configName, boolean defaultConfig) {
      try {
         File configFile = new File(this.vestigeConfigDir, configName + ".txt");
         if (configFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            ArrayList lines = new ArrayList();

            String line;
            while((line = reader.readLine()) != null) {
               lines.add(line);
            }

            reader.close();
            Iterator var7 = lines.iterator();

            while(var7.hasNext()) {
               String s = (String)var7.next();
               String[] infos = s.split(":");
               if (infos.length >= 3) {
                  String type = infos[0];
                  String moduleName = infos[1];
                  Module m = Flap.instance.getModuleManager().getModuleByName(moduleName);
                  if (m != null) {
                     byte var14 = -1;
                     switch(type.hashCode()) {
                     case -644372944:
                        if (type.equals("Setting")) {
                           var14 = 1;
                        }
                        break;
                     case 80204913:
                        if (type.equals("State")) {
                           var14 = 0;
                        }
                     }

                     switch(var14) {
                     case 0:
                        if (defaultConfig) {
                           m.setEnabledSilently(Boolean.parseBoolean(infos[2]));
                        } else {
                           m.setEnabled(Boolean.parseBoolean(infos[2]));
                        }
                        break;
                     case 1:
                        AbstractSetting setting = m.getSettingByName(infos[2]);
                        if (setting != null) {
                           if (setting instanceof BooleanSetting) {
                              BooleanSetting boolSetting = (BooleanSetting)setting;
                              boolSetting.setEnabled(Boolean.parseBoolean(infos[3]));
                           } else if (setting instanceof ModeSetting) {
                              ModeSetting modeSetting = (ModeSetting)setting;
                              modeSetting.setMode(infos[3]);
                           } else if (setting instanceof DoubleSetting) {
                              DoubleSetting doubleSetting = (DoubleSetting)setting;
                              doubleSetting.setValue(Double.parseDouble(infos[3]));
                           } else if (setting instanceof IntegerSetting) {
                              IntegerSetting intSetting = (IntegerSetting)setting;
                              intSetting.setValue(Integer.parseInt(infos[3]));
                           }
                        }
                     }
                  }
               }
            }

            return true;
         }
      } catch (IOException var17) {
         var17.printStackTrace();
      }

      return false;
   }

   public void saveKeybinds() {
      try {
         File keybindsFile = new File(this.vestigeDir, "keybinds.txt");
         if (!keybindsFile.exists()) {
            keybindsFile.createNewFile();
         }

         PrintWriter writer = new PrintWriter(keybindsFile);
         ArrayList<String> toWrite = new ArrayList();
         Iterator var4 = Flap.instance.getModuleManager().modules.iterator();

         while(var4.hasNext()) {
            Module m = (Module)var4.next();
            toWrite.add(m.getName() + ":" + m.getKey());
         }

         var4 = toWrite.iterator();

         while(var4.hasNext()) {
            String s = (String)var4.next();
            writer.println(s);
         }

         writer.close();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void loadKeybinds() {
      try {
         File keybindsFile = new File(this.vestigeDir, "keybinds.txt");
         if (keybindsFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(keybindsFile));
            ArrayList lines = new ArrayList();

            String line;
            while((line = reader.readLine()) != null) {
               lines.add(line);
            }

            reader.close();
            Iterator var5 = lines.iterator();

            while(var5.hasNext()) {
               String s = (String)var5.next();
               String[] infos = s.split(":");
               if (infos.length == 2) {
                  String moduleName = infos[0];
                  int key = Integer.parseInt(infos[1]);
                  Module m = Flap.instance.getModuleManager().getModuleByName(moduleName);
                  if (m != null) {
                     m.setKey(key);
                  }
               }
            }
         }
      } catch (IOException var11) {
         var11.printStackTrace();
      }

   }

   public void saveDefaultConfig() {
      this.saveConfig("default");
   }

   public void loadDefaultConfig() {
      this.loadConfig("default", true);
   }
}
