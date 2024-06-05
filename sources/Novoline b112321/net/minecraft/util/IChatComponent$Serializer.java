package net.minecraft.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map.Entry;
import net.t5;
import net.minecraft.util.ChatComponentScore;
import net.minecraft.util.ChatComponentSelector;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonUtils;

public class IChatComponent$Serializer implements JsonDeserializer, JsonSerializer {
   private static final Gson GSON;

   public IChatComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      if(var1.isJsonPrimitive()) {
         return new ChatComponentText(var1.getAsString());
      } else if(!var1.isJsonObject()) {
         if(!var1.isJsonArray()) {
            throw new JsonParseException("Don\'t know how to turn " + var1.toString() + " into a Component");
         } else {
            JsonArray var11 = var1.getAsJsonArray();
            IChatComponent var12 = null;

            for(JsonElement var17 : var11) {
               IChatComponent var18 = this.deserialize(var17, var17.getClass(), var3);
               var12 = var18;
            }

            return var12;
         }
      } else {
         JsonObject var4 = var1.getAsJsonObject();
         Object var5;
         if(var4.has("text")) {
            var5 = new ChatComponentText(var4.get("text").getAsString());
         } else if(var4.has("translate")) {
            String var6 = var4.get("translate").getAsString();
            if(var4.has("with")) {
               JsonArray var7 = var4.getAsJsonArray("with");
               Object[] var8 = new Object[var7.size()];

               for(int var9 = 0; var9 < var8.length; ++var9) {
                  var8[var9] = this.deserialize(var7.get(var9), var2, var3);
                  if(var8[var9] instanceof ChatComponentText) {
                     ChatComponentText var10 = (ChatComponentText)var8[var9];
                     if(var10.getChatStyle().isEmpty() && var10.getSiblings().isEmpty()) {
                        var8[var9] = var10.getChatComponentText_TextValue();
                     }
                  }
               }

               var5 = new ChatComponentTranslation(var6, var8);
            } else {
               var5 = new ChatComponentTranslation(var6, new Object[0]);
            }
         } else if(var4.has("score")) {
            JsonObject var13 = var4.getAsJsonObject("score");
            if(!var13.has("name") || !var13.has("objective")) {
               throw new JsonParseException("A score component needs a least a name and an objective");
            }

            var5 = new ChatComponentScore(JsonUtils.getString(var13, "name"), JsonUtils.getString(var13, "objective"));
            if(var13.has("value")) {
               ((ChatComponentScore)var5).setValue(JsonUtils.getString(var13, "value"));
            }
         } else {
            if(!var4.has("selector")) {
               throw new JsonParseException("Don\'t know how to turn " + var1.toString() + " into a Component");
            }

            var5 = new ChatComponentSelector(JsonUtils.getString(var4, "selector"));
         }

         if(var4.has("extra")) {
            JsonArray var14 = var4.getAsJsonArray("extra");
            if(var14.size() <= 0) {
               throw new JsonParseException("Unexpected empty array of components");
            }

            for(int var16 = 0; var16 < var14.size(); ++var16) {
               ((IChatComponent)var5).appendSibling(this.deserialize(var14.get(var16), var2, var3));
            }
         }

         ((IChatComponent)var5).setChatStyle((ChatStyle)var3.deserialize(var1, ChatStyle.class));
         return (IChatComponent)var5;
      }
   }

   private void serializeChatStyle(ChatStyle var1, JsonObject var2, JsonSerializationContext var3) {
      JsonElement var4 = var3.serialize(var1);
      if(var4.isJsonObject()) {
         JsonObject var5 = (JsonObject)var4;

         for(Entry var7 : var5.entrySet()) {
            var2.add((String)var7.getKey(), (JsonElement)var7.getValue());
         }
      }

   }

   public JsonElement serialize(IChatComponent var1, Type var2, JsonSerializationContext var3) {
      if(var1 instanceof ChatComponentText && var1.getChatStyle().isEmpty() && var1.getSiblings().isEmpty()) {
         return new JsonPrimitive(((ChatComponentText)var1).getChatComponentText_TextValue());
      } else {
         JsonObject var4 = new JsonObject();
         if(!var1.getChatStyle().isEmpty()) {
            this.serializeChatStyle(var1.getChatStyle(), var4, var3);
         }

         if(!var1.getSiblings().isEmpty()) {
            JsonArray var5 = new JsonArray();

            for(IChatComponent var7 : var1.getSiblings()) {
               var5.add(this.serialize((IChatComponent)var7, var7.getClass(), var3));
            }

            var4.add("extra", var5);
         }

         if(var1 instanceof ChatComponentText) {
            var4.addProperty("text", ((ChatComponentText)var1).getChatComponentText_TextValue());
         } else if(var1 instanceof ChatComponentTranslation) {
            ChatComponentTranslation var11 = (ChatComponentTranslation)var1;
            var4.addProperty("translate", var11.getKey());
            if(var11.getFormatArgs() != null && var11.getFormatArgs().length > 0) {
               JsonArray var14 = new JsonArray();

               for(Object var10 : var11.getFormatArgs()) {
                  if(var10 instanceof IChatComponent) {
                     var14.add(this.serialize((IChatComponent)((IChatComponent)var10), var10.getClass(), var3));
                  } else {
                     var14.add(new JsonPrimitive(String.valueOf(var10)));
                  }
               }

               var4.add("with", var14);
            }
         } else if(var1 instanceof ChatComponentScore) {
            ChatComponentScore var12 = (ChatComponentScore)var1;
            JsonObject var15 = new JsonObject();
            var15.addProperty("name", var12.getName());
            var15.addProperty("objective", var12.getObjective());
            var15.addProperty("value", var12.getUnformattedTextForChat());
            var4.add("score", var15);
         } else {
            if(!(var1 instanceof ChatComponentSelector)) {
               throw new IllegalArgumentException("Don\'t know how to serialize " + var1 + " as a Component");
            }

            ChatComponentSelector var13 = (ChatComponentSelector)var1;
            var4.addProperty("selector", var13.getSelector());
         }

         return var4;
      }
   }

   public static String componentToJson(IChatComponent var0) {
      return GSON.toJson(var0);
   }

   public static IChatComponent jsonToComponent(String var0) {
      return (IChatComponent)GSON.fromJson(var0, IChatComponent.class);
   }

   static {
      GsonBuilder var7 = new GsonBuilder();
      var7.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent$Serializer());
      var7.registerTypeHierarchyAdapter(ChatStyle.class, new t5());
      var7.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
      GSON = var7.create();
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
