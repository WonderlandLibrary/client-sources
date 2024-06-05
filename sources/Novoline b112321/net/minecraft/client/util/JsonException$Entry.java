package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.util.JsonException$1;
import org.apache.commons.lang3.StringUtils;

public class JsonException$Entry {
   private String field_151376_a;
   private final List field_151375_b;

   private JsonException$Entry() {
      this.field_151376_a = null;
      this.field_151375_b = Lists.newArrayList();
   }

   private void func_151373_a(String var1) {
      this.field_151375_b.add(0, var1);
   }

   public String func_151372_b() {
      return StringUtils.join(this.field_151375_b, "->");
   }

   public String toString() {
      return this.field_151376_a != null?(!this.field_151375_b.isEmpty()?this.field_151376_a + " " + this.func_151372_b():this.field_151376_a):(!this.field_151375_b.isEmpty()?"(Unknown file) " + this.func_151372_b():"(Unknown file)");
   }

   JsonException$Entry(JsonException$1 var1) {
      this();
   }

   static void access$100(JsonException$Entry var0, String var1) {
      var0.func_151373_a(var1);
   }

   static String access$202(JsonException$Entry var0, String var1) {
      return var0.field_151376_a = var1;
   }
}
