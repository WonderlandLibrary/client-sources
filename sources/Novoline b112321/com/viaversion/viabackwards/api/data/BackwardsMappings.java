package com.viaversion.viabackwards.api.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.Map;
import net.M2;
import net.Mz;
import net.aiL;
import net.ln;
import org.jetbrains.annotations.Nullable;

public class BackwardsMappings extends aiL {
   private final Class vvProtocolClass;
   private Int2ObjectMap n;
   private Map backwardsSoundMappings;
   private static String o;

   public BackwardsMappings(String var1, String var2, @Nullable Class var3) {
      this(var1, var2, var3, false);
   }

   public BackwardsMappings(String var1, String var2, @Nullable Class var3, boolean var4) {
      super(var1, var2, var4);
      Preconditions.checkArgument(!var3.isAssignableFrom(BackwardsProtocol.class));
      this.vvProtocolClass = var3;
      this.e = false;
   }

   protected void a(JsonObject var1, JsonObject var2, @Nullable JsonObject var3) {
      String var4 = b();
      if(var3 != null) {
         JsonObject var5 = var3.getAsJsonObject("items");
         if(var5 != null) {
            this.n = VBMappingDataLoader.a(var1.getAsJsonObject("items"), var2.getAsJsonObject("items"), var5);
         }

         JsonObject var6 = var3.getAsJsonObject("sounds");
         this.backwardsSoundMappings = VBMappingDataLoader.a(var6);
      }

      if(this.vvProtocolClass != null) {
         this.g = ProtocolManagerImpl.b(this.vvProtocolClass).d().g().a();
      }

      this.a(var1, var2);
   }

   @Nullable
   protected M2 b(JsonObject var1, JsonObject var2, @Nullable JsonObject var3, String var4) {
      String var5 = b();
      if(var1.has(var4) && var2.has(var4)) {
         JsonObject var6 = var3 != null?var3.getAsJsonObject(var4):null;
         return new Mz(var1.getAsJsonArray(var4), var2.getAsJsonArray(var4), var6, this.d(var4));
      } else {
         return null;
      }
   }

   @Nullable
   protected M2 a(JsonObject var1, JsonObject var2, @Nullable JsonObject var3, String var4) {
      String var5 = b();
      if(var1.has(var4) && var2.has(var4)) {
         JsonObject var6 = var3 != null?var3.getAsJsonObject(var4):null;
         return new Mz(var1.getAsJsonObject(var4), var2.getAsJsonObject(var4), var6, this.d(var4));
      } else {
         return null;
      }
   }

   protected JsonObject e() {
      return VBMappingDataLoader.b("mapping-" + this.f + "to" + this.h + ".json");
   }

   protected void a(JsonObject var1, JsonObject var2) {
   }

   protected boolean d(String var1) {
      String var2 = b();
      return !var1.equals("blocks") && !var1.equals("statistics");
   }

   public int getNewItemId(int var1) {
      return this.g.get(var1);
   }

   public int getNewBlockId(int var1) {
      return this.i.a(var1);
   }

   public int d(int var1) {
      return this.checkValidity(var1, this.g.a().get(var1), "item");
   }

   @Nullable
   public ln a(int var1) {
      String var2 = b();
      return this.n != null?(ln)this.n.get(var1):null;
   }

   @Nullable
   public String c(String var1) {
      String var2 = b();
      return this.backwardsSoundMappings != null?(String)this.backwardsSoundMappings.get(var1):null;
   }

   @Nullable
   public Int2ObjectMap c() {
      return this.n;
   }

   @Nullable
   public Map getBackwardsSoundMappings() {
      return this.backwardsSoundMappings;
   }

   public static void b(String var0) {
      o = var0;
   }

   public static String b() {
      return o;
   }

   static {
      b((String)null);
   }
}
