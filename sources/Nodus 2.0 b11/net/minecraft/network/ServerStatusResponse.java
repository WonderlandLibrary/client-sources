/*   1:    */ package net.minecraft.network;
/*   2:    */ 
/*   3:    */ import com.google.gson.JsonArray;
/*   4:    */ import com.google.gson.JsonDeserializationContext;
/*   5:    */ import com.google.gson.JsonDeserializer;
/*   6:    */ import com.google.gson.JsonElement;
/*   7:    */ import com.google.gson.JsonObject;
/*   8:    */ import com.google.gson.JsonSerializationContext;
/*   9:    */ import com.google.gson.JsonSerializer;
/*  10:    */ import com.mojang.authlib.GameProfile;
/*  11:    */ import java.lang.reflect.Type;
/*  12:    */ import net.minecraft.util.IChatComponent;
/*  13:    */ import net.minecraft.util.JsonUtils;
/*  14:    */ 
/*  15:    */ public class ServerStatusResponse
/*  16:    */ {
/*  17:    */   private IChatComponent field_151326_a;
/*  18:    */   private PlayerCountData field_151324_b;
/*  19:    */   private MinecraftProtocolVersionIdentifier field_151325_c;
/*  20:    */   private String field_151323_d;
/*  21:    */   private static final String __OBFID = "CL_00001385";
/*  22:    */   
/*  23:    */   public IChatComponent func_151317_a()
/*  24:    */   {
/*  25: 25 */     return this.field_151326_a;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void func_151315_a(IChatComponent p_151315_1_)
/*  29:    */   {
/*  30: 30 */     this.field_151326_a = p_151315_1_;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public PlayerCountData func_151318_b()
/*  34:    */   {
/*  35: 35 */     return this.field_151324_b;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void func_151319_a(PlayerCountData p_151319_1_)
/*  39:    */   {
/*  40: 40 */     this.field_151324_b = p_151319_1_;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public MinecraftProtocolVersionIdentifier func_151322_c()
/*  44:    */   {
/*  45: 45 */     return this.field_151325_c;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void func_151321_a(MinecraftProtocolVersionIdentifier p_151321_1_)
/*  49:    */   {
/*  50: 50 */     this.field_151325_c = p_151321_1_;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void func_151320_a(String p_151320_1_)
/*  54:    */   {
/*  55: 55 */     this.field_151323_d = p_151320_1_;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String func_151316_d()
/*  59:    */   {
/*  60: 60 */     return this.field_151323_d;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static class PlayerCountData
/*  64:    */   {
/*  65:    */     private final int field_151336_a;
/*  66:    */     private final int field_151334_b;
/*  67:    */     private GameProfile[] field_151335_c;
/*  68:    */     private static final String __OBFID = "CL_00001386";
/*  69:    */     
/*  70:    */     public PlayerCountData(int p_i45274_1_, int p_i45274_2_)
/*  71:    */     {
/*  72: 72 */       this.field_151336_a = p_i45274_1_;
/*  73: 73 */       this.field_151334_b = p_i45274_2_;
/*  74:    */     }
/*  75:    */     
/*  76:    */     public int func_151332_a()
/*  77:    */     {
/*  78: 78 */       return this.field_151336_a;
/*  79:    */     }
/*  80:    */     
/*  81:    */     public int func_151333_b()
/*  82:    */     {
/*  83: 83 */       return this.field_151334_b;
/*  84:    */     }
/*  85:    */     
/*  86:    */     public GameProfile[] func_151331_c()
/*  87:    */     {
/*  88: 88 */       return this.field_151335_c;
/*  89:    */     }
/*  90:    */     
/*  91:    */     public void func_151330_a(GameProfile[] p_151330_1_)
/*  92:    */     {
/*  93: 93 */       this.field_151335_c = p_151330_1_;
/*  94:    */     }
/*  95:    */     
/*  96:    */     public static class Serializer
/*  97:    */       implements JsonDeserializer, JsonSerializer
/*  98:    */     {
/*  99:    */       private static final String __OBFID = "CL_00001387";
/* 100:    */       
/* 101:    */       public ServerStatusResponse.PlayerCountData deserialize(JsonElement p_151311_1_, Type p_151311_2_, JsonDeserializationContext p_151311_3_)
/* 102:    */       {
/* 103:102 */         JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151311_1_, "players");
/* 104:103 */         ServerStatusResponse.PlayerCountData var5 = new ServerStatusResponse.PlayerCountData(JsonUtils.getJsonObjectIntegerFieldValue(var4, "max"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "online"));
/* 105:105 */         if (JsonUtils.jsonObjectFieldTypeIsArray(var4, "sample"))
/* 106:    */         {
/* 107:107 */           JsonArray var6 = JsonUtils.getJsonObjectJsonArrayField(var4, "sample");
/* 108:109 */           if (var6.size() > 0)
/* 109:    */           {
/* 110:111 */             GameProfile[] var7 = new GameProfile[var6.size()];
/* 111:113 */             for (int var8 = 0; var8 < var7.length; var8++)
/* 112:    */             {
/* 113:115 */               JsonObject var9 = JsonUtils.getJsonElementAsJsonObject(var6.get(var8), "player[" + var8 + "]");
/* 114:116 */               var7[var8] = new GameProfile(JsonUtils.getJsonObjectStringFieldValue(var9, "id"), JsonUtils.getJsonObjectStringFieldValue(var9, "name"));
/* 115:    */             }
/* 116:119 */             var5.func_151330_a(var7);
/* 117:    */           }
/* 118:    */         }
/* 119:123 */         return var5;
/* 120:    */       }
/* 121:    */       
/* 122:    */       public JsonElement serialize(ServerStatusResponse.PlayerCountData p_151312_1_, Type p_151312_2_, JsonSerializationContext p_151312_3_)
/* 123:    */       {
/* 124:128 */         JsonObject var4 = new JsonObject();
/* 125:129 */         var4.addProperty("max", Integer.valueOf(p_151312_1_.func_151332_a()));
/* 126:130 */         var4.addProperty("online", Integer.valueOf(p_151312_1_.func_151333_b()));
/* 127:132 */         if ((p_151312_1_.func_151331_c() != null) && (p_151312_1_.func_151331_c().length > 0))
/* 128:    */         {
/* 129:134 */           JsonArray var5 = new JsonArray();
/* 130:136 */           for (int var6 = 0; var6 < p_151312_1_.func_151331_c().length; var6++)
/* 131:    */           {
/* 132:138 */             JsonObject var7 = new JsonObject();
/* 133:139 */             var7.addProperty("id", p_151312_1_.func_151331_c()[var6].getId());
/* 134:140 */             var7.addProperty("name", p_151312_1_.func_151331_c()[var6].getName());
/* 135:141 */             var5.add(var7);
/* 136:    */           }
/* 137:144 */           var4.add("sample", var5);
/* 138:    */         }
/* 139:147 */         return var4;
/* 140:    */       }
/* 141:    */       
/* 142:    */       public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 143:    */       {
/* 144:152 */         return serialize((ServerStatusResponse.PlayerCountData)par1Obj, par2Type, par3JsonSerializationContext);
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static class MinecraftProtocolVersionIdentifier
/* 150:    */   {
/* 151:    */     private final String field_151306_a;
/* 152:    */     private final int field_151305_b;
/* 153:    */     private static final String __OBFID = "CL_00001389";
/* 154:    */     
/* 155:    */     public MinecraftProtocolVersionIdentifier(String p_i45275_1_, int p_i45275_2_)
/* 156:    */     {
/* 157:165 */       this.field_151306_a = p_i45275_1_;
/* 158:166 */       this.field_151305_b = p_i45275_2_;
/* 159:    */     }
/* 160:    */     
/* 161:    */     public String func_151303_a()
/* 162:    */     {
/* 163:171 */       return this.field_151306_a;
/* 164:    */     }
/* 165:    */     
/* 166:    */     public int func_151304_b()
/* 167:    */     {
/* 168:176 */       return this.field_151305_b;
/* 169:    */     }
/* 170:    */     
/* 171:    */     public static class Serializer
/* 172:    */       implements JsonDeserializer, JsonSerializer
/* 173:    */     {
/* 174:    */       private static final String __OBFID = "CL_00001390";
/* 175:    */       
/* 176:    */       public ServerStatusResponse.MinecraftProtocolVersionIdentifier deserialize(JsonElement p_151309_1_, Type p_151309_2_, JsonDeserializationContext p_151309_3_)
/* 177:    */       {
/* 178:185 */         JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151309_1_, "version");
/* 179:186 */         return new ServerStatusResponse.MinecraftProtocolVersionIdentifier(JsonUtils.getJsonObjectStringFieldValue(var4, "name"), JsonUtils.getJsonObjectIntegerFieldValue(var4, "protocol"));
/* 180:    */       }
/* 181:    */       
/* 182:    */       public JsonElement serialize(ServerStatusResponse.MinecraftProtocolVersionIdentifier p_151310_1_, Type p_151310_2_, JsonSerializationContext p_151310_3_)
/* 183:    */       {
/* 184:191 */         JsonObject var4 = new JsonObject();
/* 185:192 */         var4.addProperty("name", p_151310_1_.func_151303_a());
/* 186:193 */         var4.addProperty("protocol", Integer.valueOf(p_151310_1_.func_151304_b()));
/* 187:194 */         return var4;
/* 188:    */       }
/* 189:    */       
/* 190:    */       public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 191:    */       {
/* 192:199 */         return serialize((ServerStatusResponse.MinecraftProtocolVersionIdentifier)par1Obj, par2Type, par3JsonSerializationContext);
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static class Serializer
/* 198:    */     implements JsonDeserializer, JsonSerializer
/* 199:    */   {
/* 200:    */     private static final String __OBFID = "CL_00001388";
/* 201:    */     
/* 202:    */     public ServerStatusResponse deserialize(JsonElement p_151314_1_, Type p_151314_2_, JsonDeserializationContext p_151314_3_)
/* 203:    */     {
/* 204:210 */       JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_151314_1_, "status");
/* 205:211 */       ServerStatusResponse var5 = new ServerStatusResponse();
/* 206:213 */       if (var4.has("description")) {
/* 207:215 */         var5.func_151315_a((IChatComponent)p_151314_3_.deserialize(var4.get("description"), IChatComponent.class));
/* 208:    */       }
/* 209:218 */       if (var4.has("players")) {
/* 210:220 */         var5.func_151319_a((ServerStatusResponse.PlayerCountData)p_151314_3_.deserialize(var4.get("players"), ServerStatusResponse.PlayerCountData.class));
/* 211:    */       }
/* 212:223 */       if (var4.has("version")) {
/* 213:225 */         var5.func_151321_a((ServerStatusResponse.MinecraftProtocolVersionIdentifier)p_151314_3_.deserialize(var4.get("version"), ServerStatusResponse.MinecraftProtocolVersionIdentifier.class));
/* 214:    */       }
/* 215:228 */       if (var4.has("favicon")) {
/* 216:230 */         var5.func_151320_a(JsonUtils.getJsonObjectStringFieldValue(var4, "favicon"));
/* 217:    */       }
/* 218:233 */       return var5;
/* 219:    */     }
/* 220:    */     
/* 221:    */     public JsonElement serialize(ServerStatusResponse p_151313_1_, Type p_151313_2_, JsonSerializationContext p_151313_3_)
/* 222:    */     {
/* 223:238 */       JsonObject var4 = new JsonObject();
/* 224:240 */       if (p_151313_1_.func_151317_a() != null) {
/* 225:242 */         var4.add("description", p_151313_3_.serialize(p_151313_1_.func_151317_a()));
/* 226:    */       }
/* 227:245 */       if (p_151313_1_.func_151318_b() != null) {
/* 228:247 */         var4.add("players", p_151313_3_.serialize(p_151313_1_.func_151318_b()));
/* 229:    */       }
/* 230:250 */       if (p_151313_1_.func_151322_c() != null) {
/* 231:252 */         var4.add("version", p_151313_3_.serialize(p_151313_1_.func_151322_c()));
/* 232:    */       }
/* 233:255 */       if (p_151313_1_.func_151316_d() != null) {
/* 234:257 */         var4.addProperty("favicon", p_151313_1_.func_151316_d());
/* 235:    */       }
/* 236:260 */       return var4;
/* 237:    */     }
/* 238:    */     
/* 239:    */     public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 240:    */     {
/* 241:265 */       return serialize((ServerStatusResponse)par1Obj, par2Type, par3JsonSerializationContext);
/* 242:    */     }
/* 243:    */   }
/* 244:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.network.ServerStatusResponse
 * JD-Core Version:    0.7.0.1
 */