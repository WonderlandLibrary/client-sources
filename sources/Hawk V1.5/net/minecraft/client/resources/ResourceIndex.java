package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   private final Map field_152784_b = Maps.newHashMap();
   private static final String __OBFID = "CL_00001831";
   private static final Logger field_152783_a = LogManager.getLogger();

   public Map func_152782_a() {
      return this.field_152784_b;
   }

   public ResourceIndex(File var1, String var2) {
      if (var2 != null) {
         File var3 = new File(var1, "objects");
         File var4 = new File(var1, String.valueOf((new StringBuilder("indexes/")).append(var2).append(".json")));
         BufferedReader var5 = null;

         try {
            var5 = Files.newReader(var4, Charsets.UTF_8);
            JsonObject var6 = (new JsonParser()).parse(var5).getAsJsonObject();
            JsonObject var7 = JsonUtils.getJsonObjectFieldOrDefault(var6, "objects", (JsonObject)null);
            if (var7 != null) {
               Iterator var8 = var7.entrySet().iterator();

               while(var8.hasNext()) {
                  Entry var9 = (Entry)var8.next();
                  JsonObject var10 = (JsonObject)var9.getValue();
                  String var11 = (String)var9.getKey();
                  String[] var12 = var11.split("/", 2);
                  String var13 = var12.length == 1 ? var12[0] : String.valueOf((new StringBuilder(String.valueOf(var12[0]))).append(":").append(var12[1]));
                  String var14 = JsonUtils.getJsonObjectStringFieldValue(var10, "hash");
                  File var15 = new File(var3, String.valueOf((new StringBuilder(String.valueOf(var14.substring(0, 2)))).append("/").append(var14)));
                  this.field_152784_b.put(var13, var15);
               }
            }
         } catch (JsonParseException var20) {
            field_152783_a.error(String.valueOf((new StringBuilder("Unable to parse resource index file: ")).append(var4)));
         } catch (FileNotFoundException var21) {
            field_152783_a.error(String.valueOf((new StringBuilder("Can't find the resource index file: ")).append(var4)));
         } finally {
            IOUtils.closeQuietly(var5);
         }
      }

   }
}
