package com.viaversion.viabackwards.api.data;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viaversion.libs.gson.JsonIOException;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.libs.opennbt.tag.io.NBTIO;
import com.viaversion.viaversion.libs.opennbt.tag.io.TagReader;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class VBMappingDataLoader {
   private static final TagReader<CompoundTag> TAG_READER = NBTIO.reader(CompoundTag.class).named();

   @Nullable
   public static CompoundTag loadNBT(String name) {
      InputStream resource = getResource(name);
      if (resource == null) {
         return null;
      } else {
         try {
            InputStream stream = resource;

            CompoundTag var3;
            try {
               var3 = TAG_READER.read(stream);
            } catch (Throwable var6) {
               if (resource != null) {
                  try {
                     stream.close();
                  } catch (Throwable var5) {
                     var6.addSuppressed(var5);
                  }
               }

               throw var6;
            }

            if (resource != null) {
               resource.close();
            }

            return var3;
         } catch (IOException var7) {
            throw new RuntimeException(var7);
         }
      }
   }

   @Nullable
   public static CompoundTag loadNBTFromDir(String name) {
      CompoundTag packedData = loadNBT(name);
      File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
      if (!file.exists()) {
         return packedData;
      } else {
         ViaBackwards.getPlatform().getLogger().info("Loading " + name + " from plugin folder");

         try {
            CompoundTag fileData = TAG_READER.read(file.toPath(), false);
            return mergeTags(packedData, fileData);
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }
   }

   private static CompoundTag mergeTags(CompoundTag original, CompoundTag extra) {
      for (Entry<String, Tag> entry : extra.entrySet()) {
         if (entry.getValue() instanceof CompoundTag) {
            CompoundTag originalEntry = original.get(entry.getKey());
            if (originalEntry != null) {
               mergeTags(originalEntry, (CompoundTag)entry.getValue());
               continue;
            }
         }

         original.put(entry.getKey(), entry.getValue());
      }

      return original;
   }

   public static JsonObject loadData(String name) {
      try {
         InputStream stream = getResource(name);

         JsonObject var7;
         label48: {
            try {
               if (stream == null) {
                  var7 = null;
                  break label48;
               }

               var7 = GsonUtil.getGson().fromJson(new InputStreamReader(stream), JsonObject.class);
            } catch (Throwable var5) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable var4) {
                     var5.addSuppressed(var4);
                  }
               }

               throw var5;
            }

            if (stream != null) {
               stream.close();
            }

            return var7;
         }

         if (stream != null) {
            stream.close();
         }

         return var7;
      } catch (IOException var6) {
         throw new RuntimeException(var6);
      }
   }

   public static JsonObject loadFromDataDir(String name) {
      File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
      if (!file.exists()) {
         return loadData(name);
      } else {
         try {
            FileReader reader = new FileReader(file);

            JsonObject var3;
            try {
               var3 = GsonUtil.getGson().fromJson(reader, JsonObject.class);
            } catch (Throwable var6) {
               try {
                  reader.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }

               throw var6;
            }

            reader.close();
            return var3;
         } catch (JsonSyntaxException var7) {
            ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
            var7.printStackTrace();
            ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
            return loadData(name);
         } catch (JsonIOException | IOException var8) {
            var8.printStackTrace();
            return null;
         }
      }
   }

   @Nullable
   public static InputStream getResource(String name) {
      return VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);
   }
}
