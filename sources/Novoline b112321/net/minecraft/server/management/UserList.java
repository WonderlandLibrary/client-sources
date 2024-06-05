package net.minecraft.server.management;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.minecraft.server.management.UserList$1;
import net.minecraft.server.management.UserList$Serializer;
import net.minecraft.server.management.UserListEntry;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserList {
   private static final Logger LOGGER = LogManager.getLogger();
   protected final Gson gson;
   private final File saveFile;
   private final Map values = Maps.newHashMap();
   private boolean lanServer = true;
   private static final ParameterizedType saveFileFormat = new UserList$1();

   public UserList(File var1) {
      this.saveFile = var1;
      GsonBuilder var2 = (new GsonBuilder()).setPrettyPrinting();
      var2.registerTypeHierarchyAdapter(UserListEntry.class, new UserList$Serializer(this, (UserList$1)null));
      this.gson = var2.create();
   }

   public boolean isLanServer() {
      return this.lanServer;
   }

   public void setLanServer(boolean var1) {
      this.lanServer = var1;
   }

   public void addEntry(UserListEntry var1) {
      this.values.put(this.getObjectKey(var1.getValue()), var1);
      UserList var10000 = this;

      try {
         var10000.writeChanges();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after adding a user.", var3);
      }

   }

   public UserListEntry getEntry(Object var1) {
      this.removeExpired();
      return (UserListEntry)this.values.get(this.getObjectKey(var1));
   }

   public void removeEntry(Object var1) {
      this.values.remove(this.getObjectKey(var1));
      UserList var10000 = this;

      try {
         var10000.writeChanges();
      } catch (IOException var3) {
         LOGGER.warn("Could not save the list after removing a user.", var3);
      }

   }

   public String[] getKeys() {
      return (String[])this.values.keySet().toArray(new String[this.values.size()]);
   }

   protected String getObjectKey(Object var1) {
      return var1.toString();
   }

   protected boolean hasEntry(Object var1) {
      return this.values.containsKey(this.getObjectKey(var1));
   }

   private void removeExpired() {
      ArrayList var1 = Lists.newArrayList();

      for(UserListEntry var3 : this.values.values()) {
         if(var3.hasBanExpired()) {
            var1.add(var3.getValue());
         }
      }

      for(Object var5 : var1) {
         this.values.remove(var5);
      }

   }

   protected UserListEntry createEntry(JsonObject var1) {
      return new UserListEntry((Object)null, var1);
   }

   protected Map getValues() {
      return this.values;
   }

   public void writeChanges() throws IOException {
      Collection var1 = this.values.values();
      String var2 = this.gson.toJson(var1);
      BufferedWriter var3 = null;
      UserList var10000 = this;

      try {
         var3 = Files.newWriter(var10000.saveFile, Charsets.UTF_8);
         var3.write(var2);
      } finally {
         IOUtils.closeQuietly(var3);
      }

   }
}
