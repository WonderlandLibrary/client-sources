package xyz.cucumber.base.utils.cfgs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.feat.other.NotificationsModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.module.settings.StringSettings;
import xyz.cucumber.base.utils.position.PositionUtils;

public class ConfigFileUtils {
   public static File directory = new File("Gothaj/configs");
   public static File file = new File(directory, "default.json");

   public static String getTimeDifference(String lastTime) {
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
      String dateTimeStr2 = now.format(formatter);
      LocalDateTime dateTime1 = LocalDateTime.parse(lastTime, formatter);
      LocalDateTime dateTime2 = LocalDateTime.parse(dateTimeStr2, formatter);
      long minutesDifference = ChronoUnit.MINUTES.between(dateTime1, dateTime2);
      String end = (int)minutesDifference + " minutes old";
      if (minutesDifference >= 60L) {
         end = (int)(minutesDifference / 60L) + " hours old";
      }

      if (minutesDifference >= 1440L) {
         end = (int)(minutesDifference / 1440L) + " days old";
      }

      if (minutesDifference >= 10080L) {
         end = (int)(minutesDifference / 10080L) + " weeks old";
      }

      if (minutesDifference >= 43200L) {
         end = (int)(minutesDifference / 43200L) + " months old";
      }

      if (minutesDifference >= 525600L) {
         end = (int)(minutesDifference / 525600L) + " years old";
      }

      return end;
   }

   public static String getConfigDate(JsonObject obj) {
      return obj.get("last-update").getAsString();
   }

   public static String getString(File f) {
      try {
         return FileUtils.readFileToString(f, StandardCharsets.UTF_8);
      } catch (IOException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String getConfigDate(File f) {
      try {
         BufferedReader load = new BufferedReader(new FileReader(f));
         JsonObject json = (JsonObject)xyz.cucumber.base.utils.FileUtils.jsonParser.parse(load);
         return getConfigDate(json);
      } catch (Exception var3) {
         return "";
      }
   }

   public static String getConfigDate(String text) {
      JsonParser parser = new JsonParser();
      JsonObject json = (JsonObject)parser.parse(text);
      return getConfigDate(json);
   }

   public static void newLoad(String name, JsonObject json, boolean b) {
      for (Entry<String, JsonElement> entry : json.entrySet()) {
         JsonObject obj = (JsonObject)entry.getValue();
         JsonObject modules = (JsonObject)obj.get("modules");

         for (Entry<String, JsonElement> ent : modules.entrySet()) {
            Mod mod = Client.INSTANCE.getModuleManager().getModule(ent.getKey());
            if (mod != null) {
               JsonObject module = (JsonObject)obj.get("modules");
               if (module.get("enabled").getAsBoolean() && !mod.isEnabled()) {
                  mod.toggle();
               }

               if (!module.get("enabled").getAsBoolean() && mod.isEnabled()) {
                  mod.toggle();
               }

               for (ModuleSettings settings : mod.getSettings()) {
                  JsonElement s = module.get(settings.getName());
                  if (s != null) {
                     if (settings instanceof ModeSettings) {
                        ((ModeSettings)settings).setMode(s.getAsString());
                     }

                     if (settings instanceof BooleanSettings) {
                        ((BooleanSettings)settings).setEnabled(s.getAsBoolean());
                     }

                     if (settings instanceof StringSettings) {
                        ((StringSettings)settings).setString(s.getAsString());
                     }

                     if (settings instanceof NumberSettings) {
                        ((NumberSettings)settings).setValue(s.getAsDouble());
                     }

                     if (settings instanceof ColorSettings) {
                        ColorSettings set = (ColorSettings)settings;
                        JsonObject o = (JsonObject)s;
                        set.setMainColor(o.get("Main color").getAsInt());
                        set.setSecondaryColor(o.get("Secondary color").getAsInt());
                        set.setAlpha(o.get("Alpha").getAsInt());
                        set.setMode(o.get("Mode").getAsString());
                     }
                  }
               }

               JsonObject clientSettings = (JsonObject)obj.get("Client");

               for (Entry var17 : modules.entrySet()) {
                  ;
               }
            }
         }
      }
   }

   public static void load(String name, String text, boolean b) {
      try {
         JsonParser parser = new JsonParser();
         JsonObject json = (JsonObject)parser.parse(text);

         for (Entry<String, JsonElement> entry : json.entrySet()) {
            Mod mod = Client.INSTANCE.getModuleManager().getModule(entry.getKey());
            if (mod != null) {
               JsonObject module = (JsonObject)entry.getValue();
               if (module.get("enabled").getAsBoolean() && !mod.isEnabled()) {
                  mod.toggle();
               }

               if (!module.get("enabled").getAsBoolean() && mod.isEnabled()) {
                  mod.toggle();
               }

               for (ModuleSettings settings : mod.getSettings()) {
                  JsonElement s = module.get(settings.getName());
                  if (s != null) {
                     if (settings instanceof ModeSettings) {
                        ((ModeSettings)settings).setMode(s.getAsString());
                     }

                     if (settings instanceof BooleanSettings) {
                        ((BooleanSettings)settings).setEnabled(s.getAsBoolean());
                     }

                     if (settings instanceof StringSettings) {
                        ((StringSettings)settings).setString(s.getAsString());
                     }

                     if (settings instanceof NumberSettings) {
                        ((NumberSettings)settings).setValue(s.getAsDouble());
                     }

                     if (settings instanceof ColorSettings) {
                        ColorSettings set = (ColorSettings)settings;
                        JsonObject obj = (JsonObject)s;
                        set.setMainColor(obj.get("Main color").getAsInt());
                        set.setSecondaryColor(obj.get("Secondary color").getAsInt());
                        set.setAlpha(obj.get("Alpha").getAsInt());
                        set.setMode(obj.get("Mode").getAsString());
                     }
                  }
               }

               NotificationsModule not = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
               if (not.isEnabled() && b) {
                  try {
                     not.notifications
                        .add(
                           new NotificationsModule.Notification(
                              name, "Config was successfully loaded", NotificationsModule.Type.ENABLED, new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                           )
                        );
                  } catch (Exception var15) {
                  }
               }
            }
         }
      } catch (Exception var16) {
         if (!b) {
            return;
         }

         NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
         if (mod.isEnabled() && b) {
            try {
               mod.notifications
                  .add(
                     new NotificationsModule.Notification(
                        name, "Online config failed to load!", NotificationsModule.Type.DISABLED, new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                     )
                  );
            } catch (Exception var14) {
            }
         }
      }
   }

   public static void save(File f, boolean b) {
      if (!directory.exists()) {
         directory.mkdirs();
      }

      if (!file.exists()) {
         try {
            file.createNewFile();
         } catch (IOException var14) {
            var14.printStackTrace();
         }
      }

      try {
         JsonObject json = new JsonObject();
         LocalDateTime myDateObj = LocalDateTime.now();
         DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
         String formattedDate = myDateObj.format(myFormatObj);
         file = f;
         json.addProperty("last-update", formattedDate);

         for (Mod m : Client.INSTANCE.getModuleManager().getModules()) {
            JsonObject jsonMod = new JsonObject();
            jsonMod.addProperty("enabled", m.isEnabled());

            for (ModuleSettings settings : m.getSettings()) {
               if (settings instanceof ModeSettings) {
                  jsonMod.addProperty(settings.getName(), ((ModeSettings)settings).getMode());
               }

               if (settings instanceof BooleanSettings) {
                  jsonMod.addProperty(settings.getName(), ((BooleanSettings)settings).isEnabled());
               }

               if (settings instanceof StringSettings) {
                  jsonMod.addProperty(settings.getName(), ((StringSettings)settings).getString());
               }

               if (settings instanceof NumberSettings) {
                  jsonMod.addProperty(settings.getName(), ((NumberSettings)settings).getValue());
               }

               if (settings instanceof ColorSettings) {
                  ColorSettings set = (ColorSettings)settings;
                  JsonObject jsonColor = new JsonObject();
                  jsonColor.addProperty("Main color", set.getMainColor());
                  jsonColor.addProperty("Secondary color", set.getSecondaryColor());
                  jsonColor.addProperty("Alpha", set.getAlpha());
                  jsonColor.addProperty("Mode", set.getMode());
                  jsonMod.add(settings.getName(), jsonColor);
               }
            }

            json.add(m.getName(), jsonMod);
         }

         PrintWriter save = new PrintWriter(new FileWriter(f));
         save.println(xyz.cucumber.base.utils.FileUtils.prettyGson.toJson(json));
         save.close();
         if (!b) {
            return;
         }

         NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
         if (mod.isEnabled()) {
            mod.notifications
               .add(
                  new NotificationsModule.Notification(
                     f.getName().substring(0, f.getName().lastIndexOf(".")),
                     "Config was successfully saved!",
                     NotificationsModule.Type.ENABLED,
                     new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                  )
               );
         }
      } catch (Exception var15) {
         if (!b) {
            return;
         }

         NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
         if (mod.isEnabled()) {
            try {
               mod.notifications
                  .add(
                     new NotificationsModule.Notification(
                        f.getName().substring(0, f.getName().lastIndexOf(".")),
                        "Config failed to load!",
                        NotificationsModule.Type.DISABLED,
                        new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                     )
                  );
            } catch (Exception var13) {
            }
         }
      }
   }

   public static void load(File f, boolean b, boolean startup) {
      if (!directory.exists()) {
         directory.mkdirs();
      }

      if (!file.exists()) {
         save(f, false);
      } else {
         try {
            BufferedReader load = new BufferedReader(new FileReader(f));
            JsonObject json = (JsonObject)xyz.cucumber.base.utils.FileUtils.jsonParser.parse(load);
            file = f;
            load.close();

            for (Entry<String, JsonElement> entry : json.entrySet()) {
               Mod mod = Client.INSTANCE.getModuleManager().getModule(entry.getKey());
               if (mod != null) {
                  JsonObject module = (JsonObject)entry.getValue();
                  if (module.get("enabled").getAsBoolean() && !mod.isEnabled()) {
                     mod.toggle();
                  }

                  if (!module.get("enabled").getAsBoolean() && mod.isEnabled()) {
                     mod.toggle();
                  }

                  for (ModuleSettings settings : mod.getSettings()) {
                     JsonElement s = module.get(settings.getName());
                     if (s != null) {
                        if (settings instanceof ModeSettings) {
                           ((ModeSettings)settings).setMode(s.getAsString());
                        }

                        if (settings instanceof BooleanSettings) {
                           ((BooleanSettings)settings).setEnabled(s.getAsBoolean());
                        }

                        if (settings instanceof StringSettings) {
                           ((StringSettings)settings).setString(s.getAsString());
                        }

                        if (settings instanceof NumberSettings) {
                           ((NumberSettings)settings).setValue(s.getAsDouble());
                        }

                        if (settings instanceof ColorSettings) {
                           ColorSettings set = (ColorSettings)settings;
                           JsonObject obj = (JsonObject)s;
                           set.setMainColor(obj.get("Main color").getAsInt());
                           set.setSecondaryColor(obj.get("Secondary color").getAsInt());
                           set.setAlpha(obj.get("Alpha").getAsInt());
                           set.setMode(obj.get("Mode").getAsString());
                        }
                     }
                  }
               }
            }

            NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
            if (mod.isEnabled() && b) {
               try {
                  mod.notifications
                     .add(
                        new NotificationsModule.Notification(
                           f.getName().substring(0, f.getName().lastIndexOf(".")),
                           "Config was successfully loaded",
                           NotificationsModule.Type.ENABLED,
                           new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                        )
                     );
               } catch (Exception var15) {
               }
            }
         } catch (Exception var16) {
            if (!b) {
               return;
            }

            NotificationsModule mod = (NotificationsModule)Client.INSTANCE.getModuleManager().getModule(NotificationsModule.class);
            if (mod.isEnabled() && b) {
               try {
                  mod.notifications
                     .add(
                        new NotificationsModule.Notification(
                           f.getName().substring(0, f.getName().lastIndexOf(".")),
                           "Config failed to load!",
                           NotificationsModule.Type.DISABLED,
                           new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F)
                        )
                     );
               } catch (Exception var14) {
               }
            }
         }
      }
   }
}
