package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList {
   private final Map values = Maps.newHashMap();
   protected static final Logger logger = LogManager.getLogger();
   protected final Gson gson;
   private boolean lanServer = true;
   private static final ParameterizedType saveFileFormat = new ParameterizedType() {
      private static final String __OBFID = "CL_00001875";

      public Type[] getActualTypeArguments() {
         return new Type[]{UserListEntry.class};
      }

      public Type getRawType() {
         return List.class;
      }

      public Type getOwnerType() {
         return null;
      }
   };
   private final File saveFile;
   private static final String __OBFID = "CL_00001876";

   protected String getObjectKey(Object var1) {
      return var1.toString();
   }

   protected boolean hasEntry(Object var1) {
      return this.values.containsKey(this.getObjectKey(var1));
   }

   private void removeExpired() {
      ArrayList var1 = Lists.newArrayList();
      Iterator var2 = this.values.values().iterator();

      while(var2.hasNext()) {
         UserListEntry var3 = (UserListEntry)var2.next();
         if (var3.hasBanExpired()) {
            var1.add(var3.getValue());
         }
      }

      var2 = var1.iterator();

      while(var2.hasNext()) {
         Object var4 = var2.next();
         this.values.remove(var4);
      }

   }

   public UserListEntry getEntry(Object var1) {
      this.removeExpired();
      return (UserListEntry)this.values.get(this.getObjectKey(var1));
   }

   public void writeChanges() throws IOException {
      Collection var1 = this.values.values();
      String var2 = this.gson.toJson(var1);
      BufferedWriter var3 = null;

      try {
         var3 = Files.newWriter(this.saveFile, Charsets.UTF_8);
         var3.write(var2);
         IOUtils.closeQuietly(var3);
      } finally {
         IOUtils.closeQuietly(var3);
      }
   }

   public String[] getKeys() {
      return (String[])this.values.keySet().toArray(new String[this.values.size()]);
   }

   protected Map getValues() {
      return this.values;
   }

   public boolean isLanServer() {
      return this.lanServer;
   }

   public void setLanServer(boolean var1) {
      this.lanServer = var1;
   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListEntry((Object)null, var1);
   }

   public void removeEntry(Object var1) {
      this.values.remove(this.getObjectKey(var1));

      try {
         this.writeChanges();
      } catch (IOException var3) {
         logger.warn("Could not save the list after removing a user.", var3);
      }

   }

   public UserList(File var1) {
      this.saveFile = var1;
      GsonBuilder var2 = (new GsonBuilder()).setPrettyPrinting();
      var2.registerTypeHierarchyAdapter(UserListEntry.class, new UserList.Serializer(this, (Object)null));
      this.gson = var2.create();
   }

   public void addEntry(UserListEntry var1) {
      this.values.put(this.getObjectKey(var1.getValue()), var1);

      try {
         this.writeChanges();
      } catch (IOException var3) {
         logger.warn("Could not save the list after adding a user.", var3);
      }

   }

   class Serializer implements JsonSerializer, JsonDeserializer {
      private static final String __OBFID = "CL_00001874";
      final UserList this$0;

      private Serializer(UserList var1) {
         this.this$0 = var1;
      }

      Serializer(UserList var1, Object var2) {
         this(var1);
      }

      public JsonElement serializeEntry(UserListEntry var1, Type var2, JsonSerializationContext var3) {
         JsonObject var4 = new JsonObject();
         var1.onSerialization(var4);
         return var4;
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         return this.deserializeEntry(var1, var2, var3);
      }

      public UserListEntry deserializeEntry(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         if (var1.isJsonObject()) {
            JsonObject var4 = var1.getAsJsonObject();
            UserListEntry var5 = this.this$0.createEntry(var4);
            return var5;
         } else {
            return null;
         }
      }

      public JsonElement serialize(Object var1, Type var2, JsonSerializationContext var3) {
         return this.serializeEntry((UserListEntry)var1, var2, var3);
      }
   }
}
