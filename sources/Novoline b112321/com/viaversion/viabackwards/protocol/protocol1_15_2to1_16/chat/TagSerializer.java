package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.ListTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.kF;

public class TagSerializer {
   private static final Pattern PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");

   public static String a(JsonObject var0) {
      StringBuilder var2 = new StringBuilder("{");
      kF.b();
      Iterator var3 = var0.entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         Preconditions.checkArgument(((JsonElement)var4.getValue()).isJsonPrimitive());
         if(var2.length() != 1) {
            var2.append(',');
         }

         String var5 = escape(((JsonElement)var4.getValue()).getAsString());
         var2.append((String)var4.getKey()).append(':').append(var5);
      }

      return var2.append('}').toString();
   }

   public static JsonObject a(CompoundTag var0) {
      kF.b();
      JsonObject var2 = new JsonObject();
      Iterator var3 = var0.getValue().entrySet().iterator();
      if(var3.hasNext()) {
         Entry var4 = (Entry)var3.next();
         var2.add((String)var4.getKey(), a((Tag)var4.getValue()));
      }

      if(PacketRemapper.b() == null) {
         kF.b(new int[1]);
      }

      return var2;
   }

   private static JsonElement a(Tag var0) {
      int[] var1 = kF.b();
      if(var0 instanceof CompoundTag) {
         return a((CompoundTag)var0);
      } else if(var0 instanceof ListTag) {
         ListTag var2 = (ListTag)var0;
         JsonArray var3 = new JsonArray();
         Iterator var4 = var2.iterator();
         if(var4.hasNext()) {
            Tag var5 = (Tag)var4.next();
            var3.add(a(var5));
         }

         return var3;
      } else {
         return new JsonPrimitive(var0.getValue().toString());
      }
   }

   public static String escape(String var0) {
      int[] var1 = kF.b();
      if(PLAIN_TEXT.matcher(var0).matches()) {
         return var0;
      } else {
         StringBuilder var2 = new StringBuilder(" ");
         int var3 = 0;
         int var4 = 0;
         if(var4 < var0.length()) {
            char var5 = var0.charAt(var4);
            if(var5 == 92) {
               var2.append('\\');
            }

            if(var5 == 34 || var5 == 39) {
               if(var3 == 0) {
                  var3 = var5 == 34?39:34;
               }

               if(var3 == var5) {
                  var2.append('\\');
               }
            }

            var2.append(var5);
            ++var4;
         }

         if(var3 == 0) {
            var3 = 34;
         }

         var2.setCharAt(0, (char)var3);
         var2.append((char)var3);
         return var2.toString();
      }
   }
}
