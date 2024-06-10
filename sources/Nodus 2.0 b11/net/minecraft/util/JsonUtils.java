/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import com.google.gson.JsonArray;
/*   4:    */ import com.google.gson.JsonElement;
/*   5:    */ import com.google.gson.JsonObject;
/*   6:    */ import com.google.gson.JsonPrimitive;
/*   7:    */ import com.google.gson.JsonSyntaxException;
/*   8:    */ import org.apache.commons.lang3.StringUtils;
/*   9:    */ 
/*  10:    */ public class JsonUtils
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00001484";
/*  13:    */   
/*  14:    */   public static boolean jsonObjectFieldTypeIsString(JsonObject p_151205_0_, String p_151205_1_)
/*  15:    */   {
/*  16: 18 */     return !jsonObjectFieldTypeIsPrimitive(p_151205_0_, p_151205_1_) ? false : p_151205_0_.getAsJsonPrimitive(p_151205_1_).isString();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static boolean jsonElementTypeIsString(JsonElement p_151211_0_)
/*  20:    */   {
/*  21: 26 */     return !p_151211_0_.isJsonPrimitive() ? false : p_151211_0_.getAsJsonPrimitive().isString();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public static boolean jsonObjectFieldTypeIsArray(JsonObject p_151202_0_, String p_151202_1_)
/*  25:    */   {
/*  26: 34 */     return !jsonObjectHasNamedField(p_151202_0_, p_151202_1_) ? false : p_151202_0_.get(p_151202_1_).isJsonArray();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static boolean jsonObjectFieldTypeIsPrimitive(JsonObject p_151201_0_, String p_151201_1_)
/*  30:    */   {
/*  31: 43 */     return !jsonObjectHasNamedField(p_151201_0_, p_151201_1_) ? false : p_151201_0_.get(p_151201_1_).isJsonPrimitive();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static boolean jsonObjectHasNamedField(JsonObject p_151204_0_, String p_151204_1_)
/*  35:    */   {
/*  36: 51 */     return p_151204_0_ != null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static String getJsonElementStringValue(JsonElement p_151206_0_, String p_151206_1_)
/*  40:    */   {
/*  41: 60 */     if (p_151206_0_.isJsonPrimitive()) {
/*  42: 62 */       return p_151206_0_.getAsString();
/*  43:    */     }
/*  44: 66 */     throw new JsonSyntaxException("Expected " + p_151206_1_ + " to be a string, was " + getJsonElementTypeDescription(p_151206_0_));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String getJsonObjectStringFieldValue(JsonObject p_151200_0_, String p_151200_1_)
/*  48:    */   {
/*  49: 75 */     if (p_151200_0_.has(p_151200_1_)) {
/*  50: 77 */       return getJsonElementStringValue(p_151200_0_.get(p_151200_1_), p_151200_1_);
/*  51:    */     }
/*  52: 81 */     throw new JsonSyntaxException("Missing " + p_151200_1_ + ", expected to find a string");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static String getJsonObjectStringFieldValueOrDefault(JsonObject p_151219_0_, String p_151219_1_, String p_151219_2_)
/*  56:    */   {
/*  57: 91 */     return p_151219_0_.has(p_151219_1_) ? getJsonElementStringValue(p_151219_0_.get(p_151219_1_), p_151219_1_) : p_151219_2_;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static boolean getJsonElementBooleanValue(JsonElement p_151216_0_, String p_151216_1_)
/*  61:    */   {
/*  62:100 */     if (p_151216_0_.isJsonPrimitive()) {
/*  63:102 */       return p_151216_0_.getAsBoolean();
/*  64:    */     }
/*  65:106 */     throw new JsonSyntaxException("Expected " + p_151216_1_ + " to be a Boolean, was " + getJsonElementTypeDescription(p_151216_0_));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean getJsonObjectBooleanFieldValue(JsonObject p_151212_0_, String p_151212_1_)
/*  69:    */   {
/*  70:115 */     if (p_151212_0_.has(p_151212_1_)) {
/*  71:117 */       return getJsonElementBooleanValue(p_151212_0_.get(p_151212_1_), p_151212_1_);
/*  72:    */     }
/*  73:121 */     throw new JsonSyntaxException("Missing " + p_151212_1_ + ", expected to find a Boolean");
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static boolean getJsonObjectBooleanFieldValueOrDefault(JsonObject p_151209_0_, String p_151209_1_, boolean p_151209_2_)
/*  77:    */   {
/*  78:131 */     return p_151209_0_.has(p_151209_1_) ? getJsonElementBooleanValue(p_151209_0_.get(p_151209_1_), p_151209_1_) : p_151209_2_;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static float getJsonElementFloatValue(JsonElement p_151220_0_, String p_151220_1_)
/*  82:    */   {
/*  83:140 */     if ((p_151220_0_.isJsonPrimitive()) && (p_151220_0_.getAsJsonPrimitive().isNumber())) {
/*  84:142 */       return p_151220_0_.getAsFloat();
/*  85:    */     }
/*  86:146 */     throw new JsonSyntaxException("Expected " + p_151220_1_ + " to be a Float, was " + getJsonElementTypeDescription(p_151220_0_));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static float getJsonObjectFloatFieldValue(JsonObject p_151217_0_, String p_151217_1_)
/*  90:    */   {
/*  91:155 */     if (p_151217_0_.has(p_151217_1_)) {
/*  92:157 */       return getJsonElementFloatValue(p_151217_0_.get(p_151217_1_), p_151217_1_);
/*  93:    */     }
/*  94:161 */     throw new JsonSyntaxException("Missing " + p_151217_1_ + ", expected to find a Float");
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static float getJsonObjectFloatFieldValueOrDefault(JsonObject p_151221_0_, String p_151221_1_, float p_151221_2_)
/*  98:    */   {
/*  99:171 */     return p_151221_0_.has(p_151221_1_) ? getJsonElementFloatValue(p_151221_0_.get(p_151221_1_), p_151221_1_) : p_151221_2_;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static int getJsonElementIntegerValue(JsonElement p_151215_0_, String p_151215_1_)
/* 103:    */   {
/* 104:180 */     if ((p_151215_0_.isJsonPrimitive()) && (p_151215_0_.getAsJsonPrimitive().isNumber())) {
/* 105:182 */       return p_151215_0_.getAsInt();
/* 106:    */     }
/* 107:186 */     throw new JsonSyntaxException("Expected " + p_151215_1_ + " to be a Int, was " + getJsonElementTypeDescription(p_151215_0_));
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static int getJsonObjectIntegerFieldValue(JsonObject p_151203_0_, String p_151203_1_)
/* 111:    */   {
/* 112:195 */     if (p_151203_0_.has(p_151203_1_)) {
/* 113:197 */       return getJsonElementIntegerValue(p_151203_0_.get(p_151203_1_), p_151203_1_);
/* 114:    */     }
/* 115:201 */     throw new JsonSyntaxException("Missing " + p_151203_1_ + ", expected to find a Int");
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static int getJsonObjectIntegerFieldValueOrDefault(JsonObject p_151208_0_, String p_151208_1_, int p_151208_2_)
/* 119:    */   {
/* 120:211 */     return p_151208_0_.has(p_151208_1_) ? getJsonElementIntegerValue(p_151208_0_.get(p_151208_1_), p_151208_1_) : p_151208_2_;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static JsonObject getJsonElementAsJsonObject(JsonElement p_151210_0_, String p_151210_1_)
/* 124:    */   {
/* 125:220 */     if (p_151210_0_.isJsonObject()) {
/* 126:222 */       return p_151210_0_.getAsJsonObject();
/* 127:    */     }
/* 128:226 */     throw new JsonSyntaxException("Expected " + p_151210_1_ + " to be a JsonObject, was " + getJsonElementTypeDescription(p_151210_0_));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static JsonObject getJsonObjectFieldOrDefault(JsonObject p_151218_0_, String p_151218_1_, JsonObject p_151218_2_)
/* 132:    */   {
/* 133:236 */     return p_151218_0_.has(p_151218_1_) ? getJsonElementAsJsonObject(p_151218_0_.get(p_151218_1_), p_151218_1_) : p_151218_2_;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static JsonArray getJsonElementAsJsonArray(JsonElement p_151207_0_, String p_151207_1_)
/* 137:    */   {
/* 138:245 */     if (p_151207_0_.isJsonArray()) {
/* 139:247 */       return p_151207_0_.getAsJsonArray();
/* 140:    */     }
/* 141:251 */     throw new JsonSyntaxException("Expected " + p_151207_1_ + " to be a JsonArray, was " + getJsonElementTypeDescription(p_151207_0_));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static JsonArray getJsonObjectJsonArrayField(JsonObject p_151214_0_, String p_151214_1_)
/* 145:    */   {
/* 146:260 */     if (p_151214_0_.has(p_151214_1_)) {
/* 147:262 */       return getJsonElementAsJsonArray(p_151214_0_.get(p_151214_1_), p_151214_1_);
/* 148:    */     }
/* 149:266 */     throw new JsonSyntaxException("Missing " + p_151214_1_ + ", expected to find a JsonArray");
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static JsonArray getJsonObjectJsonArrayFieldOrDefault(JsonObject p_151213_0_, String p_151213_1_, JsonArray p_151213_2_)
/* 153:    */   {
/* 154:276 */     return p_151213_0_.has(p_151213_1_) ? getJsonElementAsJsonArray(p_151213_0_.get(p_151213_1_), p_151213_1_) : p_151213_2_;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static String getJsonElementTypeDescription(JsonElement p_151222_0_)
/* 158:    */   {
/* 159:284 */     String var1 = StringUtils.abbreviateMiddle(String.valueOf(p_151222_0_), "...", 10);
/* 160:286 */     if (p_151222_0_ == null) {
/* 161:288 */       return "null (missing)";
/* 162:    */     }
/* 163:290 */     if (p_151222_0_.isJsonNull()) {
/* 164:292 */       return "null (json)";
/* 165:    */     }
/* 166:294 */     if (p_151222_0_.isJsonArray()) {
/* 167:296 */       return "an array (" + var1 + ")";
/* 168:    */     }
/* 169:298 */     if (p_151222_0_.isJsonObject()) {
/* 170:300 */       return "an object (" + var1 + ")";
/* 171:    */     }
/* 172:304 */     if (p_151222_0_.isJsonPrimitive())
/* 173:    */     {
/* 174:306 */       JsonPrimitive var2 = p_151222_0_.getAsJsonPrimitive();
/* 175:308 */       if (var2.isNumber()) {
/* 176:310 */         return "a number (" + var1 + ")";
/* 177:    */       }
/* 178:313 */       if (var2.isBoolean()) {
/* 179:315 */         return "a boolean (" + var1 + ")";
/* 180:    */       }
/* 181:    */     }
/* 182:319 */     return var1;
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.JsonUtils
 * JD-Core Version:    0.7.0.1
 */