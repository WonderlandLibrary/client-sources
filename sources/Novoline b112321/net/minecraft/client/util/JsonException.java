package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.util.JsonException$Entry;

public class JsonException extends IOException {
   private final List field_151383_a = Lists.newArrayList();
   private final String field_151382_b;

   public JsonException(String var1) {
      this.field_151383_a.add(new JsonException$Entry());
      this.field_151382_b = var1;
   }

   public JsonException(String var1, Throwable var2) {
      super(var2);
      this.field_151383_a.add(new JsonException$Entry());
      this.field_151382_b = var1;
   }

   public void func_151380_a(String var1) {
      JsonException$Entry.access$100((JsonException$Entry)this.field_151383_a.get(0), var1);
   }

   public void func_151381_b(String var1) {
      JsonException$Entry.access$202((JsonException$Entry)this.field_151383_a.get(0), var1);
      this.field_151383_a.add(0, new JsonException$Entry());
   }

   public String getMessage() {
      return "Invalid " + ((JsonException$Entry)this.field_151383_a.get(this.field_151383_a.size() - 1)).toString() + ": " + this.field_151382_b;
   }

   public static JsonException func_151379_a(Exception var0) {
      if(var0 instanceof JsonException) {
         return (JsonException)var0;
      } else {
         String var1 = var0.getMessage();
         if(var0 instanceof FileNotFoundException) {
            var1 = "File not found";
         }

         return new JsonException(var1, var0);
      }
   }
}
