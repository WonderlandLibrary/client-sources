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
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public interface IChatComponent extends Iterable {
   ChatStyle getChatStyle();

   String getFormattedText();

   IChatComponent appendText(String var1);

   List getSiblings();

   IChatComponent createCopy();

   String getUnformattedTextForChat();

   IChatComponent setChatStyle(ChatStyle var1);

   IChatComponent appendSibling(IChatComponent var1);

   String getUnformattedText();

   public static class Serializer implements JsonSerializer, JsonDeserializer {
      private static final String __OBFID = "CL_00001263";
      private static final Gson GSON;

      private void serializeChatStyle(ChatStyle var1, JsonObject var2, JsonSerializationContext var3) {
         JsonElement var4 = var3.serialize(var1);
         if (var4.isJsonObject()) {
            JsonObject var5 = (JsonObject)var4;
            Iterator var6 = var5.entrySet().iterator();

            while(var6.hasNext()) {
               Entry var7 = (Entry)var6.next();
               var2.add((String)var7.getKey(), (JsonElement)var7.getValue());
            }
         }

      }

      public JsonElement serialize(Object var1, Type var2, JsonSerializationContext var3) {
         return this.serialize((IChatComponent)var1, var2, var3);
      }

      public static String componentToJson(IChatComponent var0) {
         return GSON.toJson(var0);
      }

      public static IChatComponent jsonToComponent(String var0) {
         return (IChatComponent)GSON.fromJson(var0, IChatComponent.class);
      }

      public IChatComponent deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) {
         if (var1.isJsonPrimitive()) {
            return new ChatComponentText(var1.getAsString());
         } else if (!var1.isJsonObject()) {
            if (var1.isJsonArray()) {
               JsonArray var11 = var1.getAsJsonArray();
               IChatComponent var15 = null;
               Iterator var14 = var11.iterator();

               while(var14.hasNext()) {
                  JsonElement var17 = (JsonElement)var14.next();
                  IChatComponent var18 = this.deserialize(var17, var17.getClass(), var3);
                  if (var15 == null) {
                     var15 = var18;
                  } else {
                     var15.appendSibling(var18);
                  }
               }

               return var15;
            } else {
               throw new JsonParseException(String.valueOf((new StringBuilder("Don't know how to turn ")).append(var1.toString()).append(" into a Component")));
            }
         } else {
            JsonObject var4 = var1.getAsJsonObject();
            Object var5;
            if (var4.has("text")) {
               var5 = new ChatComponentText(var4.get("text").getAsString());
            } else if (var4.has("translate")) {
               String var12 = var4.get("translate").getAsString();
               if (var4.has("with")) {
                  JsonArray var7 = var4.getAsJsonArray("with");
                  Object[] var8 = new Object[var7.size()];

                  for(int var9 = 0; var9 < var8.length; ++var9) {
                     var8[var9] = this.deserialize(var7.get(var9), var2, var3);
                     if (var8[var9] instanceof ChatComponentText) {
                        ChatComponentText var10 = (ChatComponentText)var8[var9];
                        if (var10.getChatStyle().isEmpty() && var10.getSiblings().isEmpty()) {
                           var8[var9] = var10.getChatComponentText_TextValue();
                        }
                     }
                  }

                  var5 = new ChatComponentTranslation(var12, var8);
               } else {
                  var5 = new ChatComponentTranslation(var12, new Object[0]);
               }
            } else if (var4.has("score")) {
               JsonObject var6 = var4.getAsJsonObject("score");
               if (!var6.has("name") || !var6.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               var5 = new ChatComponentScore(JsonUtils.getJsonObjectStringFieldValue(var6, "name"), JsonUtils.getJsonObjectStringFieldValue(var6, "objective"));
               if (var6.has("value")) {
                  ((ChatComponentScore)var5).func_179997_b(JsonUtils.getJsonObjectStringFieldValue(var6, "value"));
               }
            } else {
               if (!var4.has("selector")) {
                  throw new JsonParseException(String.valueOf((new StringBuilder("Don't know how to turn ")).append(var1.toString()).append(" into a Component")));
               }

               var5 = new ChatComponentSelector(JsonUtils.getJsonObjectStringFieldValue(var4, "selector"));
            }

            if (var4.has("extra")) {
               JsonArray var13 = var4.getAsJsonArray("extra");
               if (var13.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for(int var16 = 0; var16 < var13.size(); ++var16) {
                  ((IChatComponent)var5).appendSibling(this.deserialize(var13.get(var16), var2, var3));
               }
            }

            ((IChatComponent)var5).setChatStyle((ChatStyle)var3.deserialize(var1, ChatStyle.class));
            return (IChatComponent)var5;
         }
      }

      public JsonElement serialize(IChatComponent var1, Type var2, JsonSerializationContext var3) {
         if (var1 instanceof ChatComponentText && var1.getChatStyle().isEmpty() && var1.getSiblings().isEmpty()) {
            return new JsonPrimitive(((ChatComponentText)var1).getChatComponentText_TextValue());
         } else {
            JsonObject var4 = new JsonObject();
            if (!var1.getChatStyle().isEmpty()) {
               this.serializeChatStyle(var1.getChatStyle(), var4, var3);
            }

            if (!var1.getSiblings().isEmpty()) {
               JsonArray var5 = new JsonArray();
               Iterator var6 = var1.getSiblings().iterator();

               while(var6.hasNext()) {
                  IChatComponent var7 = (IChatComponent)var6.next();
                  var5.add(this.serialize((IChatComponent)var7, var7.getClass(), var3));
               }

               var4.add("extra", var5);
            }

            if (var1 instanceof ChatComponentText) {
               var4.addProperty("text", ((ChatComponentText)var1).getChatComponentText_TextValue());
            } else if (var1 instanceof ChatComponentTranslation) {
               ChatComponentTranslation var11 = (ChatComponentTranslation)var1;
               var4.addProperty("translate", var11.getKey());
               if (var11.getFormatArgs() != null && var11.getFormatArgs().length > 0) {
                  JsonArray var14 = new JsonArray();
                  Object[] var16 = var11.getFormatArgs();
                  int var8 = var16.length;

                  for(int var9 = 0; var9 < var8; ++var9) {
                     Object var10 = var16[var9];
                     if (var10 instanceof IChatComponent) {
                        var14.add(this.serialize((IChatComponent)((IChatComponent)var10), var10.getClass(), var3));
                     } else {
                        var14.add(new JsonPrimitive(String.valueOf(var10)));
                     }
                  }

                  var4.add("with", var14);
               }
            } else if (var1 instanceof ChatComponentScore) {
               ChatComponentScore var12 = (ChatComponentScore)var1;
               JsonObject var15 = new JsonObject();
               var15.addProperty("name", var12.func_179995_g());
               var15.addProperty("objective", var12.func_179994_h());
               var15.addProperty("value", var12.getUnformattedTextForChat());
               var4.add("score", var15);
            } else {
               if (!(var1 instanceof ChatComponentSelector)) {
                  throw new IllegalArgumentException(String.valueOf((new StringBuilder("Don't know how to serialize ")).append(var1).append(" as a Component")));
               }

               ChatComponentSelector var13 = (ChatComponentSelector)var1;
               var4.addProperty("selector", var13.func_179992_g());
            }

            return var4;
         }
      }

      static {
         GsonBuilder var0 = new GsonBuilder();
         var0.registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer());
         var0.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
         var0.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
         GSON = var0.create();
      }

      public Object deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return this.deserialize(var1, var2, var3);
      }
   }
}
