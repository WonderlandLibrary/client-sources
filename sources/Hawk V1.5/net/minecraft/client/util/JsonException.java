package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JsonException extends IOException {
   private static final String __OBFID = "CL_00001414";
   private final String field_151382_b;
   private final List field_151383_a = Lists.newArrayList();

   public JsonException(String var1, Throwable var2) {
      super(var2);
      this.field_151383_a.add(new JsonException.Entry((Object)null));
      this.field_151382_b = var1;
   }

   public static JsonException func_151379_a(Exception var0) {
      if (var0 instanceof JsonException) {
         return (JsonException)var0;
      } else {
         String var1 = var0.getMessage();
         if (var0 instanceof FileNotFoundException) {
            var1 = "File not found";
         }

         return new JsonException(var1, var0);
      }
   }

   public String getMessage() {
      return String.valueOf((new StringBuilder("Invalid ")).append(((JsonException.Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString()).append(": ").append(this.field_151382_b));
   }

   public void func_151380_a(String var1) {
      JsonException.Entry.access$0((JsonException.Entry)this.field_151383_a.get(0), var1);
   }

   public void func_151381_b(String var1) {
      JsonException.Entry.access$1((JsonException.Entry)this.field_151383_a.get(0), var1);
      this.field_151383_a.add(0, new JsonException.Entry((Object)null));
   }

   public JsonException(String var1) {
      this.field_151383_a.add(new JsonException.Entry((Object)null));
      this.field_151382_b = var1;
   }

   public static class Entry {
      private final List field_151375_b;
      private String field_151376_a;
      private static final String __OBFID = "CL_00001416";

      private void func_151373_a(String var1) {
         this.field_151375_b.add(0, var1);
      }

      Entry(Object var1) {
         this();
      }

      public String toString() {
         return this.field_151376_a != null ? (!this.field_151375_b.isEmpty() ? String.valueOf((new StringBuilder(String.valueOf(this.field_151376_a))).append(" ").append(this.func_151372_b())) : this.field_151376_a) : (!this.field_151375_b.isEmpty() ? String.valueOf((new StringBuilder("(Unknown file) ")).append(this.func_151372_b())) : "(Unknown file)");
      }

      static void access$1(JsonException.Entry var0, String var1) {
         var0.field_151376_a = var1;
      }

      private Entry() {
         this.field_151376_a = null;
         this.field_151375_b = Lists.newArrayList();
      }

      public String func_151372_b() {
         return StringUtils.join(this.field_151375_b, "->");
      }

      static void access$0(JsonException.Entry var0, String var1) {
         var0.func_151373_a(var1);
      }
   }
}
