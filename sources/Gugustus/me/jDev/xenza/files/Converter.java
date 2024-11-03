package me.jDev.xenza.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import me.jDev.xenza.files.parts.SettingPart;
import net.augustus.Augustus;
import net.augustus.clickgui.buttons.CategoryButton;
import net.augustus.modules.Module;
import me.jDev.xenza.files.parts.ClickGuiPart;
import me.jDev.xenza.files.parts.ConfigPart;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.augustus.utils.shader.ShaderUtil;

public class Converter implements MC, MM, SM {
   private final String path = "gugustus";
   public static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
   public static final Gson NORMALGSON = new GsonBuilder().setPrettyPrinting().create();

   public void moduleSaver(List<Module> moduleList) {
      FileManager<Module> fileManager = new FileManager<>();
      fileManager.saveFile(this.path + "/settings", "module.json", moduleList);
   }

   public void moduleReader(List<Module> moduleList) {
      File file = new File(this.path + "/settings/module.json");
      if (!file.exists()) {
         this.moduleSaver(moduleList);
      }

      ArrayList<Module> loadedModules = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/module.json"));
         loadedModules = GSON.fromJson(reader, (new TypeToken<List<Module>>() {}).getType());
         reader.close();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      for(Module module : moduleList) {
         for(Module loadedModule : loadedModules) {
            if (loadedModule != null && module != null && loadedModule.getName().equalsIgnoreCase(module.getName())) {
               module.readModule(loadedModule);
            }
         }
      }
   }

   public void saveBackground(ShaderUtil shaderUtil) {
      FileManager<String> fileManager = new FileManager<>();
      fileManager.saveFile(this.path + "/settings", "background.json", shaderUtil.getName());
   }

   public String readBackground() {
      File file = new File(this.path + "/settings/background.json");
      if (!file.exists()) {
         ShaderUtil shaderUtil = new ShaderUtil();
         shaderUtil.setName("Trinity");
         shaderUtil.createBackgroundShader(this.path + "/shaders/trinity.frag");
         this.saveBackground(shaderUtil);
      }

      String shaderName = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/background.json"));
         shaderName = new Gson().fromJson(reader, (new TypeToken<String>() {
         }).getType());
         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return shaderName;
   }

   public void saveLastAlts(List<String> lastAlts) {
      FileManager<List<String>> fileManager = new FileManager<>();
      fileManager.saveFile(this.path + "/alts", "lastAlts.json", lastAlts);
   }

   public void readLastAlts() {
      File file = new File(this.path + "/alts/lastAlts.json");
      if (!file.exists()) {
         this.saveLastAlts(new ArrayList<>());
      }

      ArrayList<String> altList = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/alts/lastAlts.json"));
         altList = new Gson().fromJson(reader, (new TypeToken<List<String>>() {
         }).getType());
         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      if (!altList.isEmpty()) {
         Augustus.getInstance().setLastAlts(altList);
      } else {
         System.err.println("No last alts found!");
      }
   }

   public void settingSaver(ArrayList<Setting> settingList) {
      FileManager<Setting> fileManager = new FileManager<>();
      fileManager.saveFile(this.path + "/settings", "settings.json", settingList);
   }

   public void settingReader(ArrayList<Setting> settingList) {
      File file = new File(this.path + "/settings/settings.json");
      if (!file.exists()) {
         this.settingSaver(settingList);
      }

      ArrayList<SettingPart> settings = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/settings.json"));
         settings = GSON.fromJson(reader, (new TypeToken<ArrayList<SettingPart>>() {
         }).getType());
         reader.close();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      for(SettingPart loadedSetting : settings) {
         for(Setting setting : settingList) {
            if (setting != null
               && loadedSetting != null
               && loadedSetting.getId() == setting.getId()
               && loadedSetting.getName().equalsIgnoreCase(setting.getName())
               && loadedSetting.getParentName().equalsIgnoreCase(setting.getParentName())) {
               if (setting instanceof BooleanValue) {
                  BooleanValue booleanValue = (BooleanValue)setting;
                  booleanValue.readSetting(loadedSetting);
               } else if (setting instanceof ColorSetting) {
                  ColorSetting booleanValue = (ColorSetting)setting;
                  booleanValue.readSetting(loadedSetting);
               } else if (setting instanceof DoubleValue) {
                  DoubleValue booleanValue = (DoubleValue)setting;
                  booleanValue.readSetting(loadedSetting);
               } else if (setting instanceof StringValue) {
                  StringValue booleanValue = (StringValue)setting;
                  booleanValue.readSetting(loadedSetting);
               }
            }
         }
      }
   }

   public void clickGuiSaver(List<CategoryButton> categoryButtons) {
      List<ClickGuiPart> clickGuiParts = new ArrayList<>();

      for(CategoryButton categoryButton : categoryButtons) {
         clickGuiParts.add(new ClickGuiPart(categoryButton.xPosition, categoryButton.yPosition, categoryButton.isUnfolded(), categoryButton.getCategory()));
      }

      FileManager<ClickGuiPart> fileManager = new FileManager<>();
      fileManager.saveAllFile(this.path + "/settings", "clickGui.json", clickGuiParts);
   }

   public void clickGuiLoader(List<CategoryButton> categoryButtons) {
      File file = new File(this.path + "/settings/clickGui.json");
      if (!file.exists()) {
         this.clickGuiSaver(categoryButtons);
      }

      ArrayList<ClickGuiPart> clickGuiParts = new ArrayList<>();

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/clickGui.json"));
         clickGuiParts = new Gson().fromJson(reader, (new TypeToken<List<ClickGuiPart>>() {
         }).getType());
         reader.close();
      } catch (IOException var8) {
         var8.printStackTrace();
      }

      for(CategoryButton categoryButton : Augustus.getInstance().getClickGui().getCategoryButtons()) {
         for(ClickGuiPart clickGuiPart : clickGuiParts) {
            if (categoryButton.getCategory() == clickGuiPart.getCategory()) {
               if (clickGuiPart.getX() >= 0 && clickGuiPart.getY() >= 0) {
                  categoryButton.xPosition = clickGuiPart.getX();
                  categoryButton.yPosition = clickGuiPart.getY();
               }

               categoryButton.setUnfolded(clickGuiPart.isOpen());
            }
         }
      }
   }

   public void configSaver(String name) {
      GregorianCalendar now = new GregorianCalendar();
      DateFormat df = DateFormat.getDateInstance(2);
      DateFormat df2 = DateFormat.getTimeInstance(3);
      ArrayList<SettingPart> settingParts = new ArrayList<>();

      for(Setting setting : sm.getStgs()) {
         if (setting instanceof BooleanValue) {
            settingParts.add(new SettingPart(setting.getId(), setting.getName(), setting.getParent(), ((BooleanValue)setting).getBoolean()));
         } else if (setting instanceof ColorSetting) {
            ColorSetting colorSetting = (ColorSetting)setting;
            settingParts.add(
               new SettingPart(
                  colorSetting.getId(),
                  colorSetting.getName(),
                  colorSetting.getParent(),
                  colorSetting.getColor().getRed(),
                  colorSetting.getColor().getGreen(),
                  colorSetting.getColor().getBlue(),
                  colorSetting.getColor().getAlpha()
               )
            );
         } else if (setting instanceof StringValue) {
            settingParts.add(
               new SettingPart(
                  setting.getId(), setting.getName(), setting.getParent(), ((StringValue)setting).getSelected(), ((StringValue)setting).getStringList()
               )
            );
         } else if (setting instanceof DoubleValue) {
            settingParts.add(
               new SettingPart(
                  setting.getId(),
                  setting.getName(),
                  setting.getParent(),
                  ((DoubleValue)setting).getValue(),
                  ((DoubleValue)setting).getMinValue(),
                  ((DoubleValue)setting).getMaxValue(),
                  ((DoubleValue)setting).getDecimalPlaces()
               )
            );
         }
      }

      FileManager<ConfigPart> fileManager = new FileManager<>();
      fileManager.saveFile(
         this.path + "/configs",
         name + ".json",
         new ConfigPart(name, df.format(now.getTime()), df2.format(now.getTime()), (ArrayList<Module>)mm.getModules(), settingParts)
      );
   }

   public String[] configReader(String name) {
      File file = new File(this.path + "/configs/" + name + ".json");
      if (!file.exists()) {
         this.configSaver(name);
      }

      ConfigPart configPart = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/configs/" + name + ".json"));
         configPart = GSON.fromJson(reader, ConfigPart.class);
         reader.close();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      return configPart != null ? new String[]{configPart.getName(), configPart.getDate(), configPart.getTime()} : null;
   }

   public void configLoader(String name) {
      File file = new File(this.path + "/configs/" + name + ".json");
      if (!file.exists()) {
         this.configSaver(name);
      }

      ConfigPart configPart = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/configs/" + name + ".json"));
         configPart = new Gson().fromJson(reader, (new TypeToken<ConfigPart>() {
         }).getType());
         reader.close();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      if (configPart == null) {
         System.err.println("Config wasn't loaded correctly");
      } else {
         for(Module module : mm.getModules()) {
            for(Module loadedModule : configPart.getModules()) {
               if (loadedModule.getName().equalsIgnoreCase(module.getName())) {
                  module.readConfig(loadedModule);
               }
            }
         }

         for(Setting setting : sm.getStgs()) {
            for(SettingPart loadedSetting : configPart.getSettingParts()) {
               if (loadedSetting.getId() == setting.getId()
                  && loadedSetting.getName().equalsIgnoreCase(setting.getName())
                  && loadedSetting.getParentName().equalsIgnoreCase(setting.getParent().getName())) {
                  if (setting instanceof BooleanValue) {
                     BooleanValue booleanValue = (BooleanValue)setting;
                     booleanValue.readConfigSetting(loadedSetting);
                  } else if (setting instanceof ColorSetting) {
                     ColorSetting booleanValue = (ColorSetting)setting;
                     booleanValue.readConfigSetting(loadedSetting);
                  } else if (setting instanceof DoubleValue) {
                     DoubleValue booleanValue = (DoubleValue)setting;
                     booleanValue.readConfigSetting(loadedSetting);
                  } else if (setting instanceof StringValue) {
                     StringValue booleanValue = (StringValue)setting;
                     booleanValue.readConfigSetting(loadedSetting);
                  }
               }
            }
         }
      }
   }

   public void soundSaver(String soundName) {
      FileManager<String> fileManager = new FileManager<>();
      fileManager.saveAllFile(this.path + "/settings", "sound.json", soundName);
   }

   public String readSound() {
      File file = new File(this.path + "/settings/sound.json");
      if (!file.exists()) {
         this.soundSaver("Vanilla");
      }

      String soundName = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/sound.json"));
         soundName = new Gson().fromJson(reader, (new TypeToken<String>() {
         }).getType());
         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return soundName;
   }

   public void apiSaver(String apiKey) {
      FileManager<String> fileManager = new FileManager<>();
      fileManager.saveAllFile(this.path + "/settings", "api.json", apiKey);
   }

   public String apiLoader() {
      File file = new File(this.path + "/settings/api.json");
      if (!file.exists()) {
         this.apiSaver("");
      }

      String apiKey = null;

      try {
         Reader reader = Files.newBufferedReader(Paths.get(this.path + "/settings/api.json"));
         apiKey = new Gson().fromJson(reader, (new TypeToken<String>() {
         }).getType());
         reader.close();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return apiKey;
   }
}
