package cc.slack.utils.other;

import cc.slack.utils.client.mc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class FileUtil extends mc {
   public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

   public static JsonObject readJsonFromFile(String path) {
      try {
         return (JsonObject)GSON.fromJson(new FileReader(path), JsonObject.class);
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static JsonObject readJsonFromResourceLocation(ResourceLocation resourceLocation) {
      try {
         InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
         Throwable var2 = null;

         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Throwable var4 = null;

            try {
               StringBuilder stringBuilder = new StringBuilder();

               String line;
               while((line = reader.readLine()) != null) {
                  stringBuilder.append(line).append('\n');
               }

               JsonObject var7 = (JsonObject)GSON.fromJson(stringBuilder.toString(), JsonObject.class);
               return var7;
            } catch (Throwable var32) {
               var4 = var32;
               throw var32;
            } finally {
               if (reader != null) {
                  if (var4 != null) {
                     try {
                        reader.close();
                     } catch (Throwable var31) {
                        var4.addSuppressed(var31);
                     }
                  } else {
                     reader.close();
                  }
               }

            }
         } catch (Throwable var34) {
            var2 = var34;
            throw var34;
         } finally {
            if (inputStream != null) {
               if (var2 != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var30) {
                     var2.addSuppressed(var30);
                  }
               } else {
                  inputStream.close();
               }
            }

         }
      } catch (IOException var36) {
         var36.printStackTrace();
         return null;
      }
   }

   public static void writeJsonToFile(JsonObject json, String path) {
      try {
         FileWriter writer = new FileWriter(path);
         GSON.toJson(json, writer);
         writer.close();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public static String readInputStream(InputStream inputStream) {
      StringBuilder stringBuilder = new StringBuilder();

      try {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

         String line;
         while((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append('\n');
         }
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return stringBuilder.toString();
   }
}
