package com.example.editme.util.config;

import com.example.editme.EditmeMod;
import com.example.editme.settings.Setting;
import com.example.editme.util.converters.Convertable;
import com.example.editme.util.setting.SettingsRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map.Entry;

public class ConfigurationProducer {
   public static void loadConfiguration(InputStream var0) {
      try {
         loadConfiguration((new JsonParser()).parse(new InputStreamReader(var0)).getAsJsonObject());
      } catch (IllegalStateException var2) {
         EditmeMod.log.error("Invalid Config, Re-Generating");
         loadConfiguration(new JsonObject());
      }

   }

   public static void loadConfiguration(Path var0) throws IOException {
      InputStream var1 = Files.newInputStream(var0);
      loadConfiguration(var1);
      var1.close();
   }

   private static void loadConfiguration(SettingsRegister var0, JsonObject var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         String var4 = (String)var3.getKey();
         JsonElement var5 = (JsonElement)var3.getValue();
         if (var0.registerHashMap.containsKey(var4)) {
            loadConfiguration(var0.subregister(var4), var5.getAsJsonObject());
         } else {
            Setting var6 = var0.getSetting(var4);
            if (var6 != null) {
               var6.setValue(var6.converter().reverse().convert(var5));
            }
         }
      }

   }

   public static JsonObject produceConfig() {
      return produceConfig(SettingsRegister.ROOT);
   }

   private static JsonObject produceConfig(SettingsRegister var0) {
      JsonObject var1 = new JsonObject();
      Iterator var2 = var0.registerHashMap.entrySet().iterator();

      Entry var3;
      while(var2.hasNext()) {
         var3 = (Entry)var2.next();
         var1.add((String)var3.getKey(), produceConfig((SettingsRegister)var3.getValue()));
      }

      var2 = var0.settingHashMap.entrySet().iterator();

      while(var2.hasNext()) {
         var3 = (Entry)var2.next();
         Setting var4 = (Setting)var3.getValue();
         if (var4 instanceof Convertable) {
            var1.add((String)var3.getKey(), (JsonElement)var4.converter().convert(var4.getValue()));
         }
      }

      return var1;
   }

   public static void saveConfiguration(Path var0) throws IOException {
      saveConfiguration(Files.newOutputStream(var0));
   }

   public static void saveConfiguration(OutputStream var0) throws IOException {
      Gson var1 = (new GsonBuilder()).setPrettyPrinting().create();
      String var2 = var1.toJson(produceConfig());
      BufferedWriter var3 = new BufferedWriter(new OutputStreamWriter(var0));
      var3.write(var2);
      var3.close();
   }

   public static void loadConfiguration(JsonObject var0) {
      loadConfiguration(SettingsRegister.ROOT, var0);
   }
}
