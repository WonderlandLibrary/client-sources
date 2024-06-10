/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.io.FileNotFoundException;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Properties;
/*   8:    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.client.settings.GameSettings;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import net.minecraft.world.World;
/*  13:    */ import net.minecraft.world.WorldProvider;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class CustomSky
/*  17:    */ {
/*  18: 16 */   private static CustomSkyLayer[][] worldSkyLayers = null;
/*  19:    */   
/*  20:    */   public static void reset()
/*  21:    */   {
/*  22: 20 */     worldSkyLayers = null;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static void update()
/*  26:    */   {
/*  27:    */     
/*  28: 27 */     if (Config.isCustomSky()) {
/*  29: 29 */       worldSkyLayers = readCustomSkies();
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   private static CustomSkyLayer[][] readCustomSkies()
/*  34:    */   {
/*  35: 35 */     CustomSkyLayer[][] wsls = new CustomSkyLayer[10][0];
/*  36: 36 */     String prefix = "mcpatcher/sky/world";
/*  37: 37 */     int lastWorldId = -1;
/*  38: 38 */     int worldCount = 0;
/*  39: 40 */     while (worldCount < wsls.length)
/*  40:    */     {
/*  41: 42 */       String wslsTrim = prefix + worldCount + "/sky";
/*  42: 43 */       ArrayList i = new ArrayList();
/*  43: 44 */       int sls = 1;
/*  44: 48 */       while (sls < 1000)
/*  45:    */       {
/*  46: 52 */         String path = wslsTrim + sls + ".properties";
/*  47:    */         try
/*  48:    */         {
/*  49: 56 */           ResourceLocation e = new ResourceLocation(path);
/*  50: 57 */           InputStream in = Config.getResourceStream(e);
/*  51: 59 */           if (in == null) {
/*  52:    */             break;
/*  53:    */           }
/*  54: 64 */           Properties props = new Properties();
/*  55: 65 */           props.load(in);
/*  56: 66 */           Config.dbg("CustomSky properties: " + path);
/*  57: 67 */           String defSource = wslsTrim + sls + ".png";
/*  58: 68 */           CustomSkyLayer sl = new CustomSkyLayer(props, defSource);
/*  59: 70 */           if (sl.isValid(path))
/*  60:    */           {
/*  61: 72 */             ResourceLocation locSource = new ResourceLocation(sl.source);
/*  62: 73 */             ITextureObject tex = TextureUtils.getTexture(locSource);
/*  63: 75 */             if (tex == null)
/*  64:    */             {
/*  65: 77 */               Config.log("CustomSky: Texture not found: " + locSource);
/*  66:    */             }
/*  67:    */             else
/*  68:    */             {
/*  69: 81 */               sl.textureId = tex.getGlTextureId();
/*  70: 82 */               i.add(sl);
/*  71: 83 */               in.close();
/*  72:    */             }
/*  73:    */           }
/*  74:    */         }
/*  75:    */         catch (FileNotFoundException var15)
/*  76:    */         {
/*  77:    */           break;
/*  78:    */         }
/*  79:    */         catch (IOException var16)
/*  80:    */         {
/*  81: 93 */           var16.printStackTrace();
/*  82:    */         }
/*  83: 96 */         sls++;
/*  84:    */       }
/*  85:101 */       if (i.size() > 0)
/*  86:    */       {
/*  87:103 */         CustomSkyLayer[] var19 = (CustomSkyLayer[])i.toArray(new CustomSkyLayer[i.size()]);
/*  88:104 */         wsls[worldCount] = var19;
/*  89:105 */         lastWorldId = worldCount;
/*  90:    */       }
/*  91:108 */       worldCount++;
/*  92:    */     }
/*  93:113 */     if (lastWorldId < 0) {
/*  94:115 */       return null;
/*  95:    */     }
/*  96:119 */     worldCount = lastWorldId + 1;
/*  97:120 */     CustomSkyLayer[][] var17 = new CustomSkyLayer[worldCount][0];
/*  98:122 */     for (int var18 = 0; var18 < var17.length; var18++) {
/*  99:124 */       var17[var18] = wsls[var18];
/* 100:    */     }
/* 101:127 */     return var17;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static void renderSky(World world, TextureManager re, float celestialAngle, float rainBrightness)
/* 105:    */   {
/* 106:133 */     if (worldSkyLayers != null) {
/* 107:135 */       if (Config.getGameSettings().renderDistanceChunks >= 8)
/* 108:    */       {
/* 109:137 */         int dimId = world.provider.dimensionId;
/* 110:139 */         if ((dimId >= 0) && (dimId < worldSkyLayers.length))
/* 111:    */         {
/* 112:141 */           CustomSkyLayer[] sls = worldSkyLayers[dimId];
/* 113:143 */           if (sls != null)
/* 114:    */           {
/* 115:145 */             long time = world.getWorldTime();
/* 116:146 */             int timeOfDay = (int)(time % 24000L);
/* 117:148 */             for (int i = 0; i < sls.length; i++)
/* 118:    */             {
/* 119:150 */               CustomSkyLayer sl = sls[i];
/* 120:152 */               if (sl.isActive(timeOfDay)) {
/* 121:154 */                 sl.render(timeOfDay, celestialAngle, rainBrightness);
/* 122:    */               }
/* 123:    */             }
/* 124:158 */             clearBlend(rainBrightness);
/* 125:    */           }
/* 126:    */         }
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static boolean hasSkyLayers(World world)
/* 132:    */   {
/* 133:167 */     if (worldSkyLayers == null) {
/* 134:169 */       return false;
/* 135:    */     }
/* 136:171 */     if (Config.getGameSettings().renderDistanceChunks < 8) {
/* 137:173 */       return false;
/* 138:    */     }
/* 139:177 */     int dimId = world.provider.dimensionId;
/* 140:179 */     if ((dimId >= 0) && (dimId < worldSkyLayers.length))
/* 141:    */     {
/* 142:181 */       CustomSkyLayer[] sls = worldSkyLayers[dimId];
/* 143:182 */       return sls != null;
/* 144:    */     }
/* 145:186 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   private static void clearBlend(float rainBrightness)
/* 149:    */   {
/* 150:193 */     GL11.glDisable(3008);
/* 151:194 */     GL11.glEnable(3042);
/* 152:195 */     GL11.glBlendFunc(770, 1);
/* 153:196 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, rainBrightness);
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.CustomSky
 * JD-Core Version:    0.7.0.1
 */