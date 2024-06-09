/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.base.Charsets;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.io.Files;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.JsonUtils;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ResourceIndex
/*    */ {
/* 23 */   private static final Logger logger = LogManager.getLogger();
/* 24 */   private final Map<String, File> resourceMap = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public ResourceIndex(File p_i1047_1_, String p_i1047_2_) {
/* 28 */     if (p_i1047_2_ != null) {
/*    */       
/* 30 */       File file1 = new File(p_i1047_1_, "objects");
/* 31 */       File file2 = new File(p_i1047_1_, "indexes/" + p_i1047_2_ + ".json");
/* 32 */       BufferedReader bufferedreader = null;
/*    */ 
/*    */       
/*    */       try {
/* 36 */         bufferedreader = Files.newReader(file2, Charsets.UTF_8);
/* 37 */         JsonObject jsonobject = (new JsonParser()).parse(bufferedreader).getAsJsonObject();
/* 38 */         JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonobject, "objects", null);
/*    */         
/* 40 */         if (jsonobject1 != null)
/*    */         {
/* 42 */           for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet())
/*    */           {
/* 44 */             JsonObject jsonobject2 = (JsonObject)entry.getValue();
/* 45 */             String s = entry.getKey();
/* 46 */             String[] astring = s.split("/", 2);
/* 47 */             String s1 = (astring.length == 1) ? astring[0] : (String.valueOf(astring[0]) + ":" + astring[1]);
/* 48 */             String s2 = JsonUtils.getString(jsonobject2, "hash");
/* 49 */             File file3 = new File(file1, String.valueOf(s2.substring(0, 2)) + "/" + s2);
/* 50 */             this.resourceMap.put(s1, file3);
/*    */           }
/*    */         
/*    */         }
/* 54 */       } catch (JsonParseException var20) {
/*    */         
/* 56 */         logger.error("Unable to parse resource index file: " + file2);
/*    */       }
/* 58 */       catch (FileNotFoundException var21) {
/*    */         
/* 60 */         logger.error("Can't find the resource index file: " + file2);
/*    */       }
/*    */       finally {
/*    */         
/* 64 */         IOUtils.closeQuietly(bufferedreader);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, File> getResourceMap() {
/* 71 */     return this.resourceMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\resources\ResourceIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */