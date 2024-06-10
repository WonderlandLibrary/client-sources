/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import java.util.concurrent.Callable;
/*  12:    */ import net.minecraft.client.resources.IResourceManager;
/*  13:    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*  14:    */ import net.minecraft.crash.CrashReport;
/*  15:    */ import net.minecraft.crash.CrashReportCategory;
/*  16:    */ import net.minecraft.src.Config;
/*  17:    */ import net.minecraft.src.RandomMobs;
/*  18:    */ import net.minecraft.util.ReportedException;
/*  19:    */ import net.minecraft.util.ResourceLocation;
/*  20:    */ import org.apache.logging.log4j.LogManager;
/*  21:    */ import org.apache.logging.log4j.Logger;
/*  22:    */ import org.lwjgl.opengl.GL11;
/*  23:    */ 
/*  24:    */ public class TextureManager
/*  25:    */   implements ITickable, IResourceManagerReloadListener
/*  26:    */ {
/*  27: 25 */   private static final Logger logger = ;
/*  28: 26 */   private final Map mapTextureObjects = Maps.newHashMap();
/*  29: 27 */   private final Map mapResourceLocations = Maps.newHashMap();
/*  30: 28 */   private final List listTickables = Lists.newArrayList();
/*  31: 29 */   private final Map mapTextureCounters = Maps.newHashMap();
/*  32:    */   private IResourceManager theResourceManager;
/*  33:    */   private static final String __OBFID = "CL_00001064";
/*  34:    */   
/*  35:    */   public TextureManager(IResourceManager par1ResourceManager)
/*  36:    */   {
/*  37: 35 */     this.theResourceManager = par1ResourceManager;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void bindTexture(ResourceLocation par1ResourceLocation)
/*  41:    */   {
/*  42: 40 */     if (Config.isRandomMobs()) {
/*  43: 42 */       par1ResourceLocation = RandomMobs.getTextureLocation(par1ResourceLocation);
/*  44:    */     }
/*  45: 45 */     Object var2 = (ITextureObject)this.mapTextureObjects.get(par1ResourceLocation);
/*  46: 47 */     if (var2 == null)
/*  47:    */     {
/*  48: 49 */       var2 = new SimpleTexture(par1ResourceLocation);
/*  49: 50 */       loadTexture(par1ResourceLocation, (ITextureObject)var2);
/*  50:    */     }
/*  51: 53 */     TextureUtil.bindTexture(((ITextureObject)var2).getGlTextureId());
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ResourceLocation getResourceLocation(int par1)
/*  55:    */   {
/*  56: 58 */     return (ResourceLocation)this.mapResourceLocations.get(Integer.valueOf(par1));
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean loadTextureMap(ResourceLocation par1ResourceLocation, TextureMap par2TextureMap)
/*  60:    */   {
/*  61: 63 */     if (loadTickableTexture(par1ResourceLocation, par2TextureMap))
/*  62:    */     {
/*  63: 65 */       this.mapResourceLocations.put(Integer.valueOf(par2TextureMap.getTextureType()), par1ResourceLocation);
/*  64: 66 */       return true;
/*  65:    */     }
/*  66: 70 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean loadTickableTexture(ResourceLocation par1ResourceLocation, ITickableTextureObject par2TickableTextureObject)
/*  70:    */   {
/*  71: 76 */     if (loadTexture(par1ResourceLocation, par2TickableTextureObject))
/*  72:    */     {
/*  73: 78 */       this.listTickables.add(par2TickableTextureObject);
/*  74: 79 */       return true;
/*  75:    */     }
/*  76: 83 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean loadTexture(ResourceLocation par1ResourceLocation, final ITextureObject par2TextureObject)
/*  80:    */   {
/*  81: 89 */     boolean var3 = true;
/*  82: 90 */     Object par2TextureObject2 = par2TextureObject;
/*  83:    */     try
/*  84:    */     {
/*  85: 94 */       par2TextureObject.loadTexture(this.theResourceManager);
/*  86:    */     }
/*  87:    */     catch (IOException var8)
/*  88:    */     {
/*  89: 98 */       logger.warn("Failed to load texture: " + par1ResourceLocation, var8);
/*  90: 99 */       par2TextureObject2 = TextureUtil.missingTexture;
/*  91:100 */       this.mapTextureObjects.put(par1ResourceLocation, par2TextureObject2);
/*  92:101 */       var3 = false;
/*  93:    */     }
/*  94:    */     catch (Throwable var9)
/*  95:    */     {
/*  96:105 */       CrashReport var5 = CrashReport.makeCrashReport(var9, "Registering texture");
/*  97:106 */       CrashReportCategory var6 = var5.makeCategory("Resource location being registered");
/*  98:107 */       var6.addCrashSection("Resource location", par1ResourceLocation);
/*  99:108 */       var6.addCrashSectionCallable("Texture object class", new Callable()
/* 100:    */       {
/* 101:    */         private static final String __OBFID = "CL_00001065";
/* 102:    */         
/* 103:    */         public String call()
/* 104:    */         {
/* 105:113 */           return par2TextureObject.getClass().getName();
/* 106:    */         }
/* 107:115 */       });
/* 108:116 */       throw new ReportedException(var5);
/* 109:    */     }
/* 110:119 */     this.mapTextureObjects.put(par1ResourceLocation, par2TextureObject2);
/* 111:120 */     return var3;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public ITextureObject getTexture(ResourceLocation par1ResourceLocation)
/* 115:    */   {
/* 116:125 */     return (ITextureObject)this.mapTextureObjects.get(par1ResourceLocation);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public ResourceLocation getDynamicTextureLocation(String par1Str, DynamicTexture par2DynamicTexture)
/* 120:    */   {
/* 121:130 */     Integer var3 = (Integer)this.mapTextureCounters.get(par1Str);
/* 122:132 */     if (var3 == null) {
/* 123:134 */       var3 = Integer.valueOf(1);
/* 124:    */     } else {
/* 125:138 */       var3 = Integer.valueOf(var3.intValue() + 1);
/* 126:    */     }
/* 127:141 */     this.mapTextureCounters.put(par1Str, var3);
/* 128:142 */     ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", new Object[] { par1Str, var3 }));
/* 129:143 */     loadTexture(var4, par2DynamicTexture);
/* 130:144 */     return var4;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void tick()
/* 134:    */   {
/* 135:149 */     Iterator var1 = this.listTickables.iterator();
/* 136:151 */     while (var1.hasNext())
/* 137:    */     {
/* 138:153 */       ITickable var2 = (ITickable)var1.next();
/* 139:154 */       var2.tick();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void func_147645_c(ResourceLocation p_147645_1_)
/* 144:    */   {
/* 145:160 */     ITextureObject var2 = getTexture(p_147645_1_);
/* 146:162 */     if (var2 != null) {
/* 147:164 */       TextureUtil.deleteTexture(var2.getGlTextureId());
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void onResourceManagerReload(IResourceManager par1ResourceManager)
/* 152:    */   {
/* 153:170 */     Config.dbg("*** Reloading textures ***");
/* 154:171 */     Config.log("Resource packs: " + Config.getResourcePackNames());
/* 155:172 */     Iterator it = this.mapTextureObjects.keySet().iterator();
/* 156:174 */     while (it.hasNext())
/* 157:    */     {
/* 158:176 */       ResourceLocation var2 = (ResourceLocation)it.next();
/* 159:178 */       if (var2.getResourcePath().startsWith("mcpatcher/"))
/* 160:    */       {
/* 161:180 */         ITextureObject var3 = (ITextureObject)this.mapTextureObjects.get(var2);
/* 162:181 */         int glTexId = var3.getGlTextureId();
/* 163:183 */         if (glTexId > 0) {
/* 164:185 */           GL11.glDeleteTextures(glTexId);
/* 165:    */         }
/* 166:188 */         it.remove();
/* 167:    */       }
/* 168:    */     }
/* 169:192 */     Iterator var21 = this.mapTextureObjects.entrySet().iterator();
/* 170:194 */     while (var21.hasNext())
/* 171:    */     {
/* 172:196 */       Map.Entry var31 = (Map.Entry)var21.next();
/* 173:197 */       loadTexture((ResourceLocation)var31.getKey(), (ITextureObject)var31.getValue());
/* 174:    */     }
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureManager
 * JD-Core Version:    0.7.0.1
 */