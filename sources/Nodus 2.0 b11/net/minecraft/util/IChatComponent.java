/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.gson.Gson;
/*   4:    */ import com.google.gson.GsonBuilder;
/*   5:    */ import com.google.gson.JsonArray;
/*   6:    */ import com.google.gson.JsonDeserializationContext;
/*   7:    */ import com.google.gson.JsonDeserializer;
/*   8:    */ import com.google.gson.JsonElement;
/*   9:    */ import com.google.gson.JsonObject;
/*  10:    */ import com.google.gson.JsonParseException;
/*  11:    */ import com.google.gson.JsonPrimitive;
/*  12:    */ import com.google.gson.JsonSerializationContext;
/*  13:    */ import com.google.gson.JsonSerializer;
/*  14:    */ import java.lang.reflect.Type;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map.Entry;
/*  18:    */ import java.util.Set;
/*  19:    */ 
/*  20:    */ public abstract interface IChatComponent
/*  21:    */   extends Iterable
/*  22:    */ {
/*  23:    */   public abstract IChatComponent setChatStyle(ChatStyle paramChatStyle);
/*  24:    */   
/*  25:    */   public abstract ChatStyle getChatStyle();
/*  26:    */   
/*  27:    */   public abstract IChatComponent appendText(String paramString);
/*  28:    */   
/*  29:    */   public abstract IChatComponent appendSibling(IChatComponent paramIChatComponent);
/*  30:    */   
/*  31:    */   public abstract String getUnformattedTextForChat();
/*  32:    */   
/*  33:    */   public abstract String getUnformattedText();
/*  34:    */   
/*  35:    */   public abstract String getFormattedText();
/*  36:    */   
/*  37:    */   public abstract List getSiblings();
/*  38:    */   
/*  39:    */   public abstract IChatComponent createCopy();
/*  40:    */   
/*  41:    */   public static class Serializer
/*  42:    */     implements JsonDeserializer, JsonSerializer
/*  43:    */   {
/*  44:    */     private static final Gson field_150700_a;
/*  45:    */     private static final String __OBFID = "CL_00001263";
/*  46:    */     
/*  47:    */     public IChatComponent deserialize(JsonElement p_150698_1_, Type p_150698_2_, JsonDeserializationContext p_150698_3_)
/*  48:    */     {
/*  49: 69 */       if (p_150698_1_.isJsonPrimitive()) {
/*  50: 71 */         return new ChatComponentText(p_150698_1_.getAsString());
/*  51:    */       }
/*  52: 73 */       if (!p_150698_1_.isJsonObject())
/*  53:    */       {
/*  54: 75 */         if (p_150698_1_.isJsonArray())
/*  55:    */         {
/*  56: 77 */           JsonArray var11 = p_150698_1_.getAsJsonArray();
/*  57: 78 */           IChatComponent var12 = null;
/*  58: 79 */           Iterator var13 = var11.iterator();
/*  59: 81 */           while (var13.hasNext())
/*  60:    */           {
/*  61: 83 */             JsonElement var16 = (JsonElement)var13.next();
/*  62: 84 */             IChatComponent var17 = deserialize(var16, var16.getClass(), p_150698_3_);
/*  63: 86 */             if (var12 == null) {
/*  64: 88 */               var12 = var17;
/*  65:    */             } else {
/*  66: 92 */               var12.appendSibling(var17);
/*  67:    */             }
/*  68:    */           }
/*  69: 96 */           return var12;
/*  70:    */         }
/*  71:100 */         throw new JsonParseException("Don't know how to turn " + p_150698_1_.toString() + " into a Component");
/*  72:    */       }
/*  73:105 */       JsonObject var4 = p_150698_1_.getAsJsonObject();
/*  74:    */       Object var5;
/*  75:    */       Object var5;
/*  76:108 */       if (var4.has("text"))
/*  77:    */       {
/*  78:110 */         var5 = new ChatComponentText(var4.get("text").getAsString());
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82:114 */         if (!var4.has("translate")) {
/*  83:116 */           throw new JsonParseException("Don't know how to turn " + p_150698_1_.toString() + " into a Component");
/*  84:    */         }
/*  85:119 */         String var6 = var4.get("translate").getAsString();
/*  86:    */         Object var5;
/*  87:121 */         if (var4.has("with"))
/*  88:    */         {
/*  89:123 */           JsonArray var7 = var4.getAsJsonArray("with");
/*  90:124 */           Object[] var8 = new Object[var7.size()];
/*  91:126 */           for (int var9 = 0; var9 < var8.length; var9++)
/*  92:    */           {
/*  93:128 */             var8[var9] = deserialize(var7.get(var9), p_150698_2_, p_150698_3_);
/*  94:130 */             if ((var8[var9] instanceof ChatComponentText))
/*  95:    */             {
/*  96:132 */               ChatComponentText var10 = (ChatComponentText)var8[var9];
/*  97:134 */               if ((var10.getChatStyle().isEmpty()) && (var10.getSiblings().isEmpty())) {
/*  98:136 */                 var8[var9] = var10.getChatComponentText_TextValue();
/*  99:    */               }
/* 100:    */             }
/* 101:    */           }
/* 102:141 */           var5 = new ChatComponentTranslation(var6, var8);
/* 103:    */         }
/* 104:    */         else
/* 105:    */         {
/* 106:145 */           var5 = new ChatComponentTranslation(var6, new Object[0]);
/* 107:    */         }
/* 108:    */       }
/* 109:149 */       if (var4.has("extra"))
/* 110:    */       {
/* 111:151 */         JsonArray var14 = var4.getAsJsonArray("extra");
/* 112:153 */         if (var14.size() <= 0) {
/* 113:155 */           throw new JsonParseException("Unexpected empty array of components");
/* 114:    */         }
/* 115:158 */         for (int var15 = 0; var15 < var14.size(); var15++) {
/* 116:160 */           ((IChatComponent)var5).appendSibling(deserialize(var14.get(var15), p_150698_2_, p_150698_3_));
/* 117:    */         }
/* 118:    */       }
/* 119:164 */       ((IChatComponent)var5).setChatStyle((ChatStyle)p_150698_3_.deserialize(p_150698_1_, ChatStyle.class));
/* 120:165 */       return (IChatComponent)var5;
/* 121:    */     }
/* 122:    */     
/* 123:    */     private void func_150695_a(ChatStyle p_150695_1_, JsonObject p_150695_2_, JsonSerializationContext p_150695_3_)
/* 124:    */     {
/* 125:171 */       JsonElement var4 = p_150695_3_.serialize(p_150695_1_);
/* 126:173 */       if (var4.isJsonObject())
/* 127:    */       {
/* 128:175 */         JsonObject var5 = (JsonObject)var4;
/* 129:176 */         Iterator var6 = var5.entrySet().iterator();
/* 130:178 */         while (var6.hasNext())
/* 131:    */         {
/* 132:180 */           Map.Entry var7 = (Map.Entry)var6.next();
/* 133:181 */           p_150695_2_.add((String)var7.getKey(), (JsonElement)var7.getValue());
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:    */     
/* 138:    */     public JsonElement serialize(IChatComponent p_150697_1_, Type p_150697_2_, JsonSerializationContext p_150697_3_)
/* 139:    */     {
/* 140:188 */       if (((p_150697_1_ instanceof ChatComponentText)) && (p_150697_1_.getChatStyle().isEmpty()) && (p_150697_1_.getSiblings().isEmpty())) {
/* 141:190 */         return new JsonPrimitive(((ChatComponentText)p_150697_1_).getChatComponentText_TextValue());
/* 142:    */       }
/* 143:194 */       JsonObject var4 = new JsonObject();
/* 144:196 */       if (!p_150697_1_.getChatStyle().isEmpty()) {
/* 145:198 */         func_150695_a(p_150697_1_.getChatStyle(), var4, p_150697_3_);
/* 146:    */       }
/* 147:201 */       if (!p_150697_1_.getSiblings().isEmpty())
/* 148:    */       {
/* 149:203 */         JsonArray var5 = new JsonArray();
/* 150:204 */         Iterator var6 = p_150697_1_.getSiblings().iterator();
/* 151:206 */         while (var6.hasNext())
/* 152:    */         {
/* 153:208 */           IChatComponent var7 = (IChatComponent)var6.next();
/* 154:209 */           var5.add(serialize(var7, var7.getClass(), p_150697_3_));
/* 155:    */         }
/* 156:212 */         var4.add("extra", var5);
/* 157:    */       }
/* 158:215 */       if ((p_150697_1_ instanceof ChatComponentText))
/* 159:    */       {
/* 160:217 */         var4.addProperty("text", ((ChatComponentText)p_150697_1_).getChatComponentText_TextValue());
/* 161:    */       }
/* 162:    */       else
/* 163:    */       {
/* 164:221 */         if (!(p_150697_1_ instanceof ChatComponentTranslation)) {
/* 165:223 */           throw new IllegalArgumentException("Don't know how to serialize " + p_150697_1_ + " as a Component");
/* 166:    */         }
/* 167:226 */         ChatComponentTranslation var11 = (ChatComponentTranslation)p_150697_1_;
/* 168:227 */         var4.addProperty("translate", var11.getKey());
/* 169:229 */         if ((var11.getFormatArgs() != null) && (var11.getFormatArgs().length > 0))
/* 170:    */         {
/* 171:231 */           JsonArray var12 = new JsonArray();
/* 172:232 */           Object[] var13 = var11.getFormatArgs();
/* 173:233 */           int var8 = var13.length;
/* 174:235 */           for (int var9 = 0; var9 < var8; var9++)
/* 175:    */           {
/* 176:237 */             Object var10 = var13[var9];
/* 177:239 */             if ((var10 instanceof IChatComponent)) {
/* 178:241 */               var12.add(serialize((IChatComponent)var10, var10.getClass(), p_150697_3_));
/* 179:    */             } else {
/* 180:245 */               var12.add(new JsonPrimitive(String.valueOf(var10)));
/* 181:    */             }
/* 182:    */           }
/* 183:249 */           var4.add("with", var12);
/* 184:    */         }
/* 185:    */       }
/* 186:253 */       return var4;
/* 187:    */     }
/* 188:    */     
/* 189:    */     public static String func_150696_a(IChatComponent p_150696_0_)
/* 190:    */     {
/* 191:259 */       return field_150700_a.toJson(p_150696_0_);
/* 192:    */     }
/* 193:    */     
/* 194:    */     public static IChatComponent func_150699_a(String p_150699_0_)
/* 195:    */     {
/* 196:264 */       return (IChatComponent)field_150700_a.fromJson(p_150699_0_, IChatComponent.class);
/* 197:    */     }
/* 198:    */     
/* 199:    */     public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 200:    */     {
/* 201:269 */       return serialize((IChatComponent)par1Obj, par2Type, par3JsonSerializationContext);
/* 202:    */     }
/* 203:    */     
/* 204:    */     static
/* 205:    */     {
/* 206:274 */       GsonBuilder var0 = new GsonBuilder();
/* 207:275 */       var0.registerTypeHierarchyAdapter(IChatComponent.class, new Serializer());
/* 208:276 */       var0.registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer());
/* 209:277 */       var0.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
/* 210:278 */       field_150700_a = var0.create();
/* 211:    */     }
/* 212:    */   }
/* 213:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.IChatComponent
 * JD-Core Version:    0.7.0.1
 */