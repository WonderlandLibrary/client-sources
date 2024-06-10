/*   1:    */ package net.minecraft.client.audio;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.gson.Gson;
/*   5:    */ import com.google.gson.GsonBuilder;
/*   6:    */ import java.io.FileNotFoundException;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.io.InputStreamReader;
/*   9:    */ import java.lang.reflect.ParameterizedType;
/*  10:    */ import java.lang.reflect.Type;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.Random;
/*  17:    */ import java.util.Set;
/*  18:    */ import net.minecraft.client.resources.IResource;
/*  19:    */ import net.minecraft.client.resources.IResourceManager;
/*  20:    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*  21:    */ import net.minecraft.client.settings.GameSettings;
/*  22:    */ import net.minecraft.entity.player.EntityPlayer;
/*  23:    */ import net.minecraft.server.gui.IUpdatePlayerListBox;
/*  24:    */ import net.minecraft.util.ResourceLocation;
/*  25:    */ import org.apache.commons.lang3.ArrayUtils;
/*  26:    */ import org.apache.logging.log4j.LogManager;
/*  27:    */ import org.apache.logging.log4j.Logger;
/*  28:    */ 
/*  29:    */ public class SoundHandler
/*  30:    */   implements IResourceManagerReloadListener, IUpdatePlayerListBox
/*  31:    */ {
/*  32: 30 */   private static final Logger logger = ;
/*  33: 31 */   private static final Gson field_147699_c = new GsonBuilder().registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
/*  34: 32 */   private static final ParameterizedType field_147696_d = new ParameterizedType()
/*  35:    */   {
/*  36:    */     private static final String __OBFID = "CL_00001148";
/*  37:    */     
/*  38:    */     public Type[] getActualTypeArguments()
/*  39:    */     {
/*  40: 37 */       return new Type[] { String.class, SoundList.class };
/*  41:    */     }
/*  42:    */     
/*  43:    */     public Type getRawType()
/*  44:    */     {
/*  45: 41 */       return Map.class;
/*  46:    */     }
/*  47:    */     
/*  48:    */     public Type getOwnerType()
/*  49:    */     {
/*  50: 45 */       return null;
/*  51:    */     }
/*  52:    */   };
/*  53: 48 */   public static final SoundPoolEntry field_147700_a = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0D, 0.0D, false);
/*  54: 49 */   private final SoundRegistry field_147697_e = new SoundRegistry();
/*  55:    */   private final SoundManager field_147694_f;
/*  56:    */   private final IResourceManager field_147695_g;
/*  57:    */   private static final String __OBFID = "CL_00001147";
/*  58:    */   
/*  59:    */   public SoundHandler(IResourceManager p_i45122_1_, GameSettings p_i45122_2_)
/*  60:    */   {
/*  61: 56 */     this.field_147695_g = p_i45122_1_;
/*  62: 57 */     this.field_147694_f = new SoundManager(this, p_i45122_2_);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/*  66:    */   {
/*  67: 62 */     this.field_147694_f.func_148596_a();
/*  68: 63 */     this.field_147697_e.func_148763_c();
/*  69: 64 */     Iterator var2 = par1ResourceManager.getResourceDomains().iterator();
/*  70: 66 */     while (var2.hasNext())
/*  71:    */     {
/*  72: 68 */       String var3 = (String)var2.next();
/*  73:    */       try
/*  74:    */       {
/*  75: 72 */         List var4 = par1ResourceManager.getAllResources(new ResourceLocation(var3, "sounds.json"));
/*  76: 74 */         for (int var5 = var4.size() - 1; var5 >= 0; var5--)
/*  77:    */         {
/*  78: 76 */           IResource var6 = (IResource)var4.get(var5);
/*  79:    */           try
/*  80:    */           {
/*  81: 80 */             Map var7 = (Map)field_147699_c.fromJson(new InputStreamReader(var6.getInputStream()), field_147696_d);
/*  82: 81 */             Iterator var8 = var7.entrySet().iterator();
/*  83: 83 */             while (var8.hasNext())
/*  84:    */             {
/*  85: 85 */               Map.Entry var9 = (Map.Entry)var8.next();
/*  86: 86 */               func_147693_a(new ResourceLocation(var3, (String)var9.getKey()), (SoundList)var9.getValue());
/*  87:    */             }
/*  88:    */           }
/*  89:    */           catch (RuntimeException var10)
/*  90:    */           {
/*  91: 91 */             logger.warn("Invalid sounds.json", var10);
/*  92:    */           }
/*  93:    */         }
/*  94:    */       }
/*  95:    */       catch (IOException localIOException) {}
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void func_147693_a(ResourceLocation p_147693_1_, SoundList p_147693_2_)
/* 100:    */   {
/* 101:    */     SoundEventAccessorComposite var3;
/* 102:    */     SoundEventAccessorComposite var3;
/* 103:106 */     if ((this.field_147697_e.containsKey(p_147693_1_)) && (!p_147693_2_.func_148574_b()))
/* 104:    */     {
/* 105:108 */       var3 = (SoundEventAccessorComposite)this.field_147697_e.getObject(p_147693_1_);
/* 106:    */     }
/* 107:    */     else
/* 108:    */     {
/* 109:112 */       var3 = new SoundEventAccessorComposite(p_147693_1_, 1.0D, 1.0D, p_147693_2_.func_148573_c());
/* 110:113 */       this.field_147697_e.func_148762_a(var3);
/* 111:    */     }
/* 112:116 */     Iterator var4 = p_147693_2_.func_148570_a().iterator();
/* 113:118 */     while (var4.hasNext())
/* 114:    */     {
/* 115:120 */       SoundList.SoundEntry var5 = (SoundList.SoundEntry)var4.next();
/* 116:121 */       String var6 = var5.func_148556_a();
/* 117:122 */       ResourceLocation var7 = new ResourceLocation(var6);
/* 118:123 */       String var8 = var6.contains(":") ? var7.getResourceDomain() : p_147693_1_.getResourceDomain();
/* 119:    */       Object var9;
/* 120:    */       Object var9;
/* 121:126 */       switch (SwitchType.field_148765_a[var5.func_148563_e().ordinal()])
/* 122:    */       {
/* 123:    */       case 1: 
/* 124:129 */         ResourceLocation var10 = new ResourceLocation(var8, "sounds/" + var7.getResourcePath() + ".ogg");
/* 125:    */         try
/* 126:    */         {
/* 127:133 */           this.field_147695_g.getResource(var10);
/* 128:    */         }
/* 129:    */         catch (FileNotFoundException var12)
/* 130:    */         {
/* 131:137 */           logger.warn("File {} does not exist, cannot add it to event {}", new Object[] { var10, p_147693_1_ });
/* 132:138 */           continue;
/* 133:    */         }
/* 134:    */         catch (IOException var13)
/* 135:    */         {
/* 136:142 */           logger.warn("Could not load sound file " + var10 + ", cannot add it to event " + p_147693_1_, var13);
/* 137:143 */           continue;
/* 138:    */         }
/* 139:146 */         var9 = new SoundEventAccessor(new SoundPoolEntry(var10, var5.func_148560_c(), var5.func_148558_b(), var5.func_148552_f()), var5.func_148555_d());
/* 140:147 */         break;
/* 141:    */       case 2: 
/* 142:150 */         var9 = new ISoundEventAccessor()
/* 143:    */         {
/* 144:    */           final ResourceLocation field_148726_a;
/* 145:    */           private static final String __OBFID = "CL_00001149";
/* 146:    */           
/* 147:    */           public int func_148721_a()
/* 148:    */           {
/* 149:156 */             SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.field_147697_e.getObject(this.field_148726_a);
/* 150:157 */             return var1 == null ? 0 : var1.func_148721_a();
/* 151:    */           }
/* 152:    */           
/* 153:    */           public SoundPoolEntry func_148720_g()
/* 154:    */           {
/* 155:161 */             SoundEventAccessorComposite var1 = (SoundEventAccessorComposite)SoundHandler.this.field_147697_e.getObject(this.field_148726_a);
/* 156:162 */             return var1 == null ? SoundHandler.field_147700_a : var1.func_148720_g();
/* 157:    */           }
/* 158:165 */         };
/* 159:166 */         break;
/* 160:    */       default: 
/* 161:168 */         throw new IllegalStateException("IN YOU FACE");
/* 162:    */       }
/* 163:    */       Object var9;
/* 164:171 */       var3.func_148727_a((ISoundEventAccessor)var9);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public SoundEventAccessorComposite func_147680_a(ResourceLocation p_147680_1_)
/* 169:    */   {
/* 170:177 */     return (SoundEventAccessorComposite)this.field_147697_e.getObject(p_147680_1_);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void playSound(ISound p_147682_1_)
/* 174:    */   {
/* 175:185 */     this.field_147694_f.func_148611_c(p_147682_1_);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void playDelayedSound(ISound p_147681_1_, int p_147681_2_)
/* 179:    */   {
/* 180:193 */     this.field_147694_f.func_148599_a(p_147681_1_, p_147681_2_);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void func_147691_a(EntityPlayer p_147691_1_, float p_147691_2_)
/* 184:    */   {
/* 185:198 */     this.field_147694_f.func_148615_a(p_147691_1_, p_147691_2_);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void func_147689_b()
/* 189:    */   {
/* 190:203 */     this.field_147694_f.func_148610_e();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void func_147690_c()
/* 194:    */   {
/* 195:208 */     this.field_147694_f.func_148614_c();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void func_147685_d()
/* 199:    */   {
/* 200:213 */     this.field_147694_f.func_148613_b();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void update()
/* 204:    */   {
/* 205:221 */     this.field_147694_f.func_148605_d();
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void func_147687_e()
/* 209:    */   {
/* 210:226 */     this.field_147694_f.func_148604_f();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setSoundLevel(SoundCategory p_147684_1_, float p_147684_2_)
/* 214:    */   {
/* 215:231 */     if ((p_147684_1_ == SoundCategory.MASTER) && (p_147684_2_ <= 0.0F)) {
/* 216:233 */       func_147690_c();
/* 217:    */     }
/* 218:236 */     this.field_147694_f.func_148601_a(p_147684_1_, p_147684_2_);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void func_147683_b(ISound p_147683_1_)
/* 222:    */   {
/* 223:241 */     this.field_147694_f.func_148602_b(p_147683_1_);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public SoundEventAccessorComposite func_147686_a(SoundCategory... p_147686_1_)
/* 227:    */   {
/* 228:246 */     ArrayList var2 = Lists.newArrayList();
/* 229:247 */     Iterator var3 = this.field_147697_e.getKeys().iterator();
/* 230:249 */     while (var3.hasNext())
/* 231:    */     {
/* 232:251 */       ResourceLocation var4 = (ResourceLocation)var3.next();
/* 233:252 */       SoundEventAccessorComposite var5 = (SoundEventAccessorComposite)this.field_147697_e.getObject(var4);
/* 234:254 */       if (ArrayUtils.contains(p_147686_1_, var5.func_148728_d())) {
/* 235:256 */         var2.add(var5);
/* 236:    */       }
/* 237:    */     }
/* 238:260 */     if (var2.isEmpty()) {
/* 239:262 */       return null;
/* 240:    */     }
/* 241:266 */     return (SoundEventAccessorComposite)var2.get(new Random().nextInt(var2.size()));
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean func_147692_c(ISound p_147692_1_)
/* 245:    */   {
/* 246:272 */     return this.field_147694_f.func_148597_a(p_147692_1_);
/* 247:    */   }
/* 248:    */   
/* 249:    */   static final class SwitchType
/* 250:    */   {
/* 251:277 */     static final int[] field_148765_a = new int[SoundList.SoundEntry.Type.values().length];
/* 252:    */     private static final String __OBFID = "CL_00001150";
/* 253:    */     
/* 254:    */     static
/* 255:    */     {
/* 256:    */       try
/* 257:    */       {
/* 258:284 */         field_148765_a[SoundList.SoundEntry.Type.FILE.ordinal()] = 1;
/* 259:    */       }
/* 260:    */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/* 261:    */       try
/* 262:    */       {
/* 263:293 */         field_148765_a[SoundList.SoundEntry.Type.SOUND_EVENT.ordinal()] = 2;
/* 264:    */       }
/* 265:    */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/* 266:    */     }
/* 267:    */   }
/* 268:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundHandler
 * JD-Core Version:    0.7.0.1
 */