/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.io.Files;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class UserList<K, V extends UserListEntry<K>>
/*     */ {
/*  31 */   protected static final Logger logger = LogManager.getLogger();
/*     */   protected final Gson gson;
/*     */   private final File saveFile;
/*  34 */   private final Map<String, V> values = Maps.newHashMap();
/*     */   
/*  36 */   private static final ParameterizedType saveFileFormat = new ParameterizedType()
/*     */     {
/*     */       public Type[] getActualTypeArguments()
/*     */       {
/*  40 */         return new Type[] { UserListEntry.class };
/*     */       }
/*     */       
/*     */       public Type getRawType() {
/*  44 */         return List.class;
/*     */       }
/*     */       
/*     */       public Type getOwnerType() {
/*  48 */         return null;
/*     */       }
/*     */     };
/*     */   private boolean lanServer = true;
/*     */   
/*     */   public UserList(File saveFile) {
/*  54 */     this.saveFile = saveFile;
/*  55 */     GsonBuilder gsonbuilder = (new GsonBuilder()).setPrettyPrinting();
/*  56 */     gsonbuilder.registerTypeHierarchyAdapter(UserListEntry.class, new Serializer(null));
/*  57 */     this.gson = gsonbuilder.create();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLanServer() {
/*  62 */     return this.lanServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLanServer(boolean state) {
/*  67 */     this.lanServer = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addEntry(V entry) {
/*  75 */     this.values.put(getObjectKey(entry.getValue()), entry);
/*     */ 
/*     */     
/*     */     try {
/*  79 */       writeChanges();
/*     */     }
/*  81 */     catch (IOException ioexception) {
/*     */       
/*  83 */       logger.warn("Could not save the list after adding a user.", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public V getEntry(K obj) {
/*  89 */     removeExpired();
/*  90 */     return this.values.get(getObjectKey(obj));
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntry(K p_152684_1_) {
/*  95 */     this.values.remove(getObjectKey(p_152684_1_));
/*     */ 
/*     */     
/*     */     try {
/*  99 */       writeChanges();
/*     */     }
/* 101 */     catch (IOException ioexception) {
/*     */       
/* 103 */       logger.warn("Could not save the list after removing a user.", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getKeys() {
/* 109 */     return (String[])this.values.keySet().toArray((Object[])new String[this.values.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getObjectKey(K obj) {
/* 117 */     return obj.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean hasEntry(K entry) {
/* 122 */     return this.values.containsKey(getObjectKey(entry));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeExpired() {
/* 130 */     List<K> list = Lists.newArrayList();
/*     */     
/* 132 */     for (UserListEntry<K> userListEntry : this.values.values()) {
/*     */       
/* 134 */       if (userListEntry.hasBanExpired())
/*     */       {
/* 136 */         list.add(userListEntry.getValue());
/*     */       }
/*     */     } 
/*     */     
/* 140 */     for (K k : list)
/*     */     {
/* 142 */       this.values.remove(k);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected UserListEntry<K> createEntry(JsonObject entryData) {
/* 148 */     return new UserListEntry<K>(null, entryData);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Map<String, V> getValues() {
/* 153 */     return this.values;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChanges() throws IOException {
/* 158 */     Collection<V> collection = this.values.values();
/* 159 */     String s = this.gson.toJson(collection);
/* 160 */     BufferedWriter bufferedwriter = null;
/*     */ 
/*     */     
/*     */     try {
/* 164 */       bufferedwriter = Files.newWriter(this.saveFile, Charsets.UTF_8);
/* 165 */       bufferedwriter.write(s);
/*     */     }
/*     */     finally {
/*     */       
/* 169 */       IOUtils.closeQuietly(bufferedwriter);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class Serializer
/*     */     implements JsonDeserializer<UserListEntry<K>>, JsonSerializer<UserListEntry<K>>
/*     */   {
/*     */     private Serializer() {}
/*     */ 
/*     */     
/*     */     public JsonElement serialize(UserListEntry<K> p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 181 */       JsonObject jsonobject = new JsonObject();
/* 182 */       p_serialize_1_.onSerialization(jsonobject);
/* 183 */       return (JsonElement)jsonobject;
/*     */     }
/*     */ 
/*     */     
/*     */     public UserListEntry<K> deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 188 */       if (p_deserialize_1_.isJsonObject()) {
/*     */         
/* 190 */         JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 191 */         UserListEntry<K> userlistentry = UserList.this.createEntry(jsonobject);
/* 192 */         return userlistentry;
/*     */       } 
/*     */ 
/*     */       
/* 196 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\server\management\UserList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */