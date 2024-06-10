/*   1:    */ package net.minecraft.stats;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Maps;
/*   4:    */ import com.google.common.collect.Sets;
/*   5:    */ import com.google.gson.JsonElement;
/*   6:    */ import com.google.gson.JsonObject;
/*   7:    */ import com.google.gson.JsonParseException;
/*   8:    */ import com.google.gson.JsonParser;
/*   9:    */ import com.google.gson.JsonPrimitive;
/*  10:    */ import java.io.File;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.lang.reflect.Constructor;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.HashSet;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import java.util.Map.Entry;
/*  19:    */ import java.util.Set;
/*  20:    */ import net.minecraft.entity.player.EntityPlayer;
/*  21:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  22:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  23:    */ import net.minecraft.network.play.server.S37PacketStatistics;
/*  24:    */ import net.minecraft.server.MinecraftServer;
/*  25:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  26:    */ import net.minecraft.util.ChatComponentTranslation;
/*  27:    */ import net.minecraft.util.IJsonSerializable;
/*  28:    */ import net.minecraft.util.TupleIntJsonSerializable;
/*  29:    */ import org.apache.commons.io.FileUtils;
/*  30:    */ import org.apache.logging.log4j.LogManager;
/*  31:    */ import org.apache.logging.log4j.Logger;
/*  32:    */ 
/*  33:    */ public class StatisticsFile
/*  34:    */   extends StatFileWriter
/*  35:    */ {
/*  36: 31 */   private static final Logger logger = ;
/*  37:    */   private final MinecraftServer field_150890_c;
/*  38:    */   private final File field_150887_d;
/*  39: 34 */   private final Set field_150888_e = Sets.newHashSet();
/*  40: 35 */   private int field_150885_f = -300;
/*  41: 36 */   private boolean field_150886_g = false;
/*  42:    */   private static final String __OBFID = "CL_00001471";
/*  43:    */   
/*  44:    */   public StatisticsFile(MinecraftServer p_i45306_1_, File p_i45306_2_)
/*  45:    */   {
/*  46: 41 */     this.field_150890_c = p_i45306_1_;
/*  47: 42 */     this.field_150887_d = p_i45306_2_;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void func_150882_a()
/*  51:    */   {
/*  52: 47 */     if (this.field_150887_d.isFile()) {
/*  53:    */       try
/*  54:    */       {
/*  55: 51 */         this.field_150875_a.clear();
/*  56: 52 */         this.field_150875_a.putAll(func_150881_a(FileUtils.readFileToString(this.field_150887_d)));
/*  57:    */       }
/*  58:    */       catch (IOException var2)
/*  59:    */       {
/*  60: 56 */         logger.error("Couldn't read statistics file " + this.field_150887_d, var2);
/*  61:    */       }
/*  62:    */       catch (JsonParseException var3)
/*  63:    */       {
/*  64: 60 */         logger.error("Couldn't parse statistics file " + this.field_150887_d, var3);
/*  65:    */       }
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void func_150883_b()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 69 */       FileUtils.writeStringToFile(this.field_150887_d, func_150880_a(this.field_150875_a));
/*  74:    */     }
/*  75:    */     catch (IOException var2)
/*  76:    */     {
/*  77: 73 */       logger.error("Couldn't save stats", var2);
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_150873_a(EntityPlayer p_150873_1_, StatBase p_150873_2_, int p_150873_3_)
/*  82:    */   {
/*  83: 79 */     int var4 = p_150873_2_.isAchievement() ? writeStat(p_150873_2_) : 0;
/*  84: 80 */     super.func_150873_a(p_150873_1_, p_150873_2_, p_150873_3_);
/*  85: 81 */     this.field_150888_e.add(p_150873_2_);
/*  86: 83 */     if ((p_150873_2_.isAchievement()) && (var4 == 0) && (p_150873_3_ > 0))
/*  87:    */     {
/*  88: 85 */       this.field_150886_g = true;
/*  89: 87 */       if (this.field_150890_c.func_147136_ar()) {
/*  90: 89 */         this.field_150890_c.getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.achievement", new Object[] { p_150873_1_.func_145748_c_(), p_150873_2_.func_150955_j() }));
/*  91:    */       }
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Set func_150878_c()
/*  96:    */   {
/*  97: 96 */     HashSet var1 = Sets.newHashSet(this.field_150888_e);
/*  98: 97 */     this.field_150888_e.clear();
/*  99: 98 */     this.field_150886_g = false;
/* 100: 99 */     return var1;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Map func_150881_a(String p_150881_1_)
/* 104:    */   {
/* 105:104 */     JsonElement var2 = new JsonParser().parse(p_150881_1_);
/* 106:106 */     if (!var2.isJsonObject()) {
/* 107:108 */       return Maps.newHashMap();
/* 108:    */     }
/* 109:112 */     JsonObject var3 = var2.getAsJsonObject();
/* 110:113 */     HashMap var4 = Maps.newHashMap();
/* 111:114 */     Iterator var5 = var3.entrySet().iterator();
/* 112:116 */     while (var5.hasNext())
/* 113:    */     {
/* 114:118 */       Map.Entry var6 = (Map.Entry)var5.next();
/* 115:119 */       StatBase var7 = StatList.func_151177_a((String)var6.getKey());
/* 116:121 */       if (var7 != null)
/* 117:    */       {
/* 118:123 */         TupleIntJsonSerializable var8 = new TupleIntJsonSerializable();
/* 119:125 */         if ((((JsonElement)var6.getValue()).isJsonPrimitive()) && (((JsonElement)var6.getValue()).getAsJsonPrimitive().isNumber()))
/* 120:    */         {
/* 121:127 */           var8.setIntegerValue(((JsonElement)var6.getValue()).getAsInt());
/* 122:    */         }
/* 123:129 */         else if (((JsonElement)var6.getValue()).isJsonObject())
/* 124:    */         {
/* 125:131 */           JsonObject var9 = ((JsonElement)var6.getValue()).getAsJsonObject();
/* 126:133 */           if ((var9.has("value")) && (var9.get("value").isJsonPrimitive()) && (var9.get("value").getAsJsonPrimitive().isNumber())) {
/* 127:135 */             var8.setIntegerValue(var9.getAsJsonPrimitive("value").getAsInt());
/* 128:    */           }
/* 129:138 */           if ((var9.has("progress")) && (var7.func_150954_l() != null)) {
/* 130:    */             try
/* 131:    */             {
/* 132:142 */               Constructor var10 = var7.func_150954_l().getConstructor(new Class[0]);
/* 133:143 */               IJsonSerializable var11 = (IJsonSerializable)var10.newInstance(new Object[0]);
/* 134:144 */               var8.setJsonSerializableValue(var11);
/* 135:    */             }
/* 136:    */             catch (Throwable var12)
/* 137:    */             {
/* 138:148 */               logger.warn("Invalid statistic progress in " + this.field_150887_d, var12);
/* 139:    */             }
/* 140:    */           }
/* 141:    */         }
/* 142:153 */         var4.put(var7, var8);
/* 143:    */       }
/* 144:    */       else
/* 145:    */       {
/* 146:157 */         logger.warn("Invalid statistic in " + this.field_150887_d + ": Don't know what " + (String)var6.getKey() + " is");
/* 147:    */       }
/* 148:    */     }
/* 149:161 */     return var4;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static String func_150880_a(Map p_150880_0_)
/* 153:    */   {
/* 154:167 */     JsonObject var1 = new JsonObject();
/* 155:168 */     Iterator var2 = p_150880_0_.entrySet().iterator();
/* 156:170 */     while (var2.hasNext())
/* 157:    */     {
/* 158:172 */       Map.Entry var3 = (Map.Entry)var2.next();
/* 159:174 */       if (((TupleIntJsonSerializable)var3.getValue()).getJsonSerializableValue() != null)
/* 160:    */       {
/* 161:176 */         JsonObject var4 = new JsonObject();
/* 162:177 */         var4.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)var3.getValue()).getIntegerValue()));
/* 163:    */         try
/* 164:    */         {
/* 165:181 */           var4.add("progress", ((TupleIntJsonSerializable)var3.getValue()).getJsonSerializableValue().getSerializableElement());
/* 166:    */         }
/* 167:    */         catch (Throwable var6)
/* 168:    */         {
/* 169:185 */           logger.warn("Couldn't save statistic " + ((StatBase)var3.getKey()).func_150951_e() + ": error serializing progress", var6);
/* 170:    */         }
/* 171:188 */         var1.add(((StatBase)var3.getKey()).statId, var4);
/* 172:    */       }
/* 173:    */       else
/* 174:    */       {
/* 175:192 */         var1.addProperty(((StatBase)var3.getKey()).statId, Integer.valueOf(((TupleIntJsonSerializable)var3.getValue()).getIntegerValue()));
/* 176:    */       }
/* 177:    */     }
/* 178:196 */     return var1.toString();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void func_150877_d()
/* 182:    */   {
/* 183:201 */     Iterator var1 = this.field_150875_a.keySet().iterator();
/* 184:203 */     while (var1.hasNext())
/* 185:    */     {
/* 186:205 */       StatBase var2 = (StatBase)var1.next();
/* 187:206 */       this.field_150888_e.add(var2);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void func_150876_a(EntityPlayerMP p_150876_1_)
/* 192:    */   {
/* 193:212 */     int var2 = this.field_150890_c.getTickCounter();
/* 194:213 */     HashMap var3 = Maps.newHashMap();
/* 195:215 */     if ((this.field_150886_g) || (var2 - this.field_150885_f > 300))
/* 196:    */     {
/* 197:217 */       this.field_150885_f = var2;
/* 198:218 */       Iterator var4 = func_150878_c().iterator();
/* 199:220 */       while (var4.hasNext())
/* 200:    */       {
/* 201:222 */         StatBase var5 = (StatBase)var4.next();
/* 202:223 */         var3.put(var5, Integer.valueOf(writeStat(var5)));
/* 203:    */       }
/* 204:    */     }
/* 205:227 */     p_150876_1_.playerNetServerHandler.sendPacket(new S37PacketStatistics(var3));
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void func_150884_b(EntityPlayerMP p_150884_1_)
/* 209:    */   {
/* 210:232 */     HashMap var2 = Maps.newHashMap();
/* 211:233 */     Iterator var3 = AchievementList.achievementList.iterator();
/* 212:235 */     while (var3.hasNext())
/* 213:    */     {
/* 214:237 */       Achievement var4 = (Achievement)var3.next();
/* 215:239 */       if (hasAchievementUnlocked(var4))
/* 216:    */       {
/* 217:241 */         var2.put(var4, Integer.valueOf(writeStat(var4)));
/* 218:242 */         this.field_150888_e.remove(var4);
/* 219:    */       }
/* 220:    */     }
/* 221:246 */     p_150884_1_.playerNetServerHandler.sendPacket(new S37PacketStatistics(var2));
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean func_150879_e()
/* 225:    */   {
/* 226:251 */     return this.field_150886_g;
/* 227:    */   }
/* 228:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatisticsFile
 * JD-Core Version:    0.7.0.1
 */