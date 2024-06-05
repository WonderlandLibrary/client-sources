package net.minecraft.client.stream;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import java.util.Map;

public class Metadata {
   private String field_152813_c;
   private static final String __OBFID = "CL_00001823";
   private final String field_152812_b;
   private static final Gson field_152811_a = new Gson();
   private Map field_152814_d;

   public void func_152807_a(String var1) {
      this.field_152813_c = var1;
   }

   public void func_152808_a(String var1, String var2) {
      if (this.field_152814_d == null) {
         this.field_152814_d = Maps.newHashMap();
      }

      if (this.field_152814_d.size() > 50) {
         throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
      } else if (var1 == null) {
         throw new IllegalArgumentException("Metadata payload key cannot be null!");
      } else if (var1.length() > 255) {
         throw new IllegalArgumentException("Metadata payload key is too long!");
      } else if (var2 == null) {
         throw new IllegalArgumentException("Metadata payload value cannot be null!");
      } else if (var2.length() > 255) {
         throw new IllegalArgumentException("Metadata payload value is too long!");
      } else {
         this.field_152814_d.put(var1, var2);
      }
   }

   public Metadata(String var1, String var2) {
      this.field_152812_b = var1;
      this.field_152813_c = var2;
   }

   public Metadata(String var1) {
      this(var1, (String)null);
   }

   public String func_152810_c() {
      return this.field_152812_b;
   }

   public String func_152806_b() {
      return this.field_152814_d != null && !this.field_152814_d.isEmpty() ? field_152811_a.toJson(this.field_152814_d) : null;
   }

   public String func_152809_a() {
      return this.field_152813_c == null ? this.field_152812_b : this.field_152813_c;
   }

   public String toString() {
      return Objects.toStringHelper(this).add("name", this.field_152812_b).add("description", this.field_152813_c).add("data", this.func_152806_b()).toString();
   }
}
