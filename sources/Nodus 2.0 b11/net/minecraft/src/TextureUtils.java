/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.awt.Graphics2D;
/*   4:    */ import java.awt.RenderingHints;
/*   5:    */ import java.awt.image.BufferedImage;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.nio.IntBuffer;
/*   8:    */ import net.minecraft.client.renderer.GLAllocation;
/*   9:    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*  10:    */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*  11:    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  15:    */ import net.minecraft.client.resources.IReloadableResourceManager;
/*  16:    */ import net.minecraft.client.resources.IResourceManager;
/*  17:    */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*  18:    */ import net.minecraft.util.IIcon;
/*  19:    */ import net.minecraft.util.ResourceLocation;
/*  20:    */ 
/*  21:    */ public class TextureUtils
/*  22:    */ {
/*  23:    */   public static final String texGrassTop = "grass_top";
/*  24:    */   public static final String texStone = "stone";
/*  25:    */   public static final String texDirt = "dirt";
/*  26:    */   public static final String texGrassSide = "grass_side";
/*  27:    */   public static final String texStoneslabSide = "stone_slab_side";
/*  28:    */   public static final String texStoneslabTop = "stone_slab_top";
/*  29:    */   public static final String texBedrock = "bedrock";
/*  30:    */   public static final String texSand = "sand";
/*  31:    */   public static final String texGravel = "gravel";
/*  32:    */   public static final String texLogOak = "log_oak";
/*  33:    */   public static final String texLogOakTop = "log_oak_top";
/*  34:    */   public static final String texGoldOre = "gold_ore";
/*  35:    */   public static final String texIronOre = "iron_ore";
/*  36:    */   public static final String texCoalOre = "coal_ore";
/*  37:    */   public static final String texObsidian = "obsidian";
/*  38:    */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*  39:    */   public static final String texSnow = "snow";
/*  40:    */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*  41:    */   public static final String texMyceliumSide = "mycelium_side";
/*  42:    */   public static final String texMyceliumTop = "mycelium_top";
/*  43:    */   public static final String texDiamondOre = "diamond_ore";
/*  44:    */   public static final String texRedstoneOre = "redstone_ore";
/*  45:    */   public static final String texLapisOre = "lapis_ore";
/*  46:    */   public static final String texLeavesOak = "leaves_oak";
/*  47:    */   public static final String texLeavesOakOpaque = "leaves_oak_opaque";
/*  48:    */   public static final String texLeavesJungle = "leaves_jungle";
/*  49:    */   public static final String texLeavesJungleOpaque = "leaves_jungle_opaque";
/*  50:    */   public static final String texCactusSide = "cactus_side";
/*  51:    */   public static final String texClay = "clay";
/*  52:    */   public static final String texFarmlandWet = "farmland_wet";
/*  53:    */   public static final String texFarmlandDry = "farmland_dry";
/*  54:    */   public static final String texNetherrack = "netherrack";
/*  55:    */   public static final String texSoulSand = "soul_sand";
/*  56:    */   public static final String texGlowstone = "glowstone";
/*  57:    */   public static final String texLogSpruce = "log_spruce";
/*  58:    */   public static final String texLogBirch = "log_birch";
/*  59:    */   public static final String texLeavesSpruce = "leaves_spruce";
/*  60:    */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*  61:    */   public static final String texLogJungle = "log_jungle";
/*  62:    */   public static final String texEndStone = "end_stone";
/*  63:    */   public static final String texSandstoneTop = "sandstone_top";
/*  64:    */   public static final String texSandstoneBottom = "sandstone_bottom";
/*  65:    */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*  66:    */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*  67:    */   public static final String texWaterStill = "water_still";
/*  68:    */   public static final String texWaterFlow = "water_flow";
/*  69:    */   public static final String texLavaStill = "lava_still";
/*  70:    */   public static final String texLavaFlow = "lava_flow";
/*  71:    */   public static final String texFireLayer0 = "fire_layer_0";
/*  72:    */   public static final String texFireLayer1 = "fire_layer_1";
/*  73:    */   public static final String texPortal = "portal";
/*  74:    */   public static IIcon iconGrassTop;
/*  75:    */   public static IIcon iconGrassSide;
/*  76:    */   public static IIcon iconGrassSideOverlay;
/*  77:    */   public static IIcon iconSnow;
/*  78:    */   public static IIcon iconGrassSideSnowed;
/*  79:    */   public static IIcon iconMyceliumSide;
/*  80:    */   public static IIcon iconMyceliumTop;
/*  81:    */   public static IIcon iconWaterStill;
/*  82:    */   public static IIcon iconWaterFlow;
/*  83:    */   public static IIcon iconLavaStill;
/*  84:    */   public static IIcon iconLavaFlow;
/*  85:    */   public static IIcon iconPortal;
/*  86:    */   public static IIcon iconFireLayer0;
/*  87:    */   public static IIcon iconFireLayer1;
/*  88: 88 */   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);
/*  89:    */   
/*  90:    */   public static void update()
/*  91:    */   {
/*  92: 92 */     TextureMap mapBlocks = TextureMap.textureMapBlocks;
/*  93: 94 */     if (mapBlocks != null)
/*  94:    */     {
/*  95: 96 */       iconGrassTop = mapBlocks.getIconSafe("grass_top");
/*  96: 97 */       iconGrassSide = mapBlocks.getIconSafe("grass_side");
/*  97: 98 */       iconGrassSideOverlay = mapBlocks.getIconSafe("grass_side_overlay");
/*  98: 99 */       iconSnow = mapBlocks.getIconSafe("snow");
/*  99:100 */       iconGrassSideSnowed = mapBlocks.getIconSafe("grass_side_snowed");
/* 100:101 */       iconMyceliumSide = mapBlocks.getIconSafe("mycelium_side");
/* 101:102 */       iconMyceliumTop = mapBlocks.getIconSafe("mycelium_top");
/* 102:103 */       iconWaterStill = mapBlocks.getIconSafe("water_still");
/* 103:104 */       iconWaterFlow = mapBlocks.getIconSafe("water_flow");
/* 104:105 */       iconLavaStill = mapBlocks.getIconSafe("lava_still");
/* 105:106 */       iconLavaFlow = mapBlocks.getIconSafe("lava_flow");
/* 106:107 */       iconFireLayer0 = mapBlocks.getIconSafe("fire_layer_0");
/* 107:108 */       iconFireLayer1 = mapBlocks.getIconSafe("fire_layer_1");
/* 108:109 */       iconPortal = mapBlocks.getIconSafe("portal");
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi)
/* 113:    */   {
/* 114:115 */     if ((name.startsWith("/mob/zombie")) || (name.startsWith("/mob/pigzombie")))
/* 115:    */     {
/* 116:117 */       int width = bi.getWidth();
/* 117:118 */       int height = bi.getHeight();
/* 118:120 */       if (width == height * 2)
/* 119:    */       {
/* 120:122 */         BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
/* 121:123 */         Graphics2D gr = scaledImage.createGraphics();
/* 122:124 */         gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 123:125 */         gr.drawImage(bi, 0, 0, width, height, null);
/* 124:126 */         return scaledImage;
/* 125:    */       }
/* 126:    */     }
/* 127:130 */     return bi;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static TextureAtlasSprite getTextureAtlasSprite(IIcon icon)
/* 131:    */   {
/* 132:135 */     return (icon instanceof TextureAtlasSprite) ? (TextureAtlasSprite)icon : null;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static int ceilPowerOfTwo(int val)
/* 136:    */   {
/* 137:142 */     for (int i = 1; i < val; i *= 2) {}
/* 138:147 */     return i;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static int getPowerOfTwo(int val)
/* 142:    */   {
/* 143:152 */     int i = 1;
/* 144:155 */     for (int po2 = 0; i < val; po2++) {
/* 145:157 */       i *= 2;
/* 146:    */     }
/* 147:160 */     return po2;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static int twoToPower(int power)
/* 151:    */   {
/* 152:165 */     int val = 1;
/* 153:167 */     for (int i = 0; i < power; i++) {
/* 154:169 */       val *= 2;
/* 155:    */     }
/* 156:172 */     return val;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static void refreshBlockTextures()
/* 160:    */   {
/* 161:177 */     Config.dbg("*** Reloading block textures ***");
/* 162:178 */     WrUpdates.finishCurrentUpdate();
/* 163:179 */     TextureMap.textureMapBlocks.loadTextureSafe(Config.getResourceManager());
/* 164:180 */     update();
/* 165:181 */     NaturalTextures.update();
/* 166:182 */     TextureMap.textureMapBlocks.updateAnimations();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static ITextureObject getTexture(String path)
/* 170:    */   {
/* 171:187 */     return getTexture(new ResourceLocation(path));
/* 172:    */   }
/* 173:    */   
/* 174:    */   public static ITextureObject getTexture(ResourceLocation loc)
/* 175:    */   {
/* 176:192 */     ITextureObject tex = Config.getTextureManager().getTexture(loc);
/* 177:194 */     if (tex != null) {
/* 178:196 */       return tex;
/* 179:    */     }
/* 180:198 */     if (!Config.hasResource(loc)) {
/* 181:200 */       return null;
/* 182:    */     }
/* 183:204 */     SimpleTexture tex1 = new SimpleTexture(loc);
/* 184:205 */     Config.getTextureManager().loadTexture(loc, tex1);
/* 185:206 */     return tex1;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static void resourcesReloaded(IResourceManager rm)
/* 189:    */   {
/* 190:212 */     if (TextureMap.textureMapBlocks != null)
/* 191:    */     {
/* 192:214 */       Config.dbg("*** Reloading custom textures ***");
/* 193:215 */       CustomSky.reset();
/* 194:216 */       TextureAnimations.reset();
/* 195:217 */       WrUpdates.finishCurrentUpdate();
/* 196:218 */       update();
/* 197:219 */       NaturalTextures.update();
/* 198:220 */       TextureAnimations.update();
/* 199:221 */       CustomColorizer.update();
/* 200:222 */       CustomSky.update();
/* 201:223 */       RandomMobs.resetTextures();
/* 202:224 */       Config.updateTexturePackClouds();
/* 203:225 */       Config.getTextureManager().tick();
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static void refreshTextureMaps(IResourceManager rm)
/* 208:    */   {
/* 209:231 */     TextureMap.textureMapBlocks.loadTextureSafe(rm);
/* 210:232 */     TextureMap.textureMapItems.loadTextureSafe(rm);
/* 211:233 */     update();
/* 212:234 */     NaturalTextures.update();
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static void registerResourceListener()
/* 216:    */   {
/* 217:239 */     IResourceManager rm = Config.getResourceManager();
/* 218:241 */     if ((rm instanceof IReloadableResourceManager))
/* 219:    */     {
/* 220:243 */       IReloadableResourceManager tto = (IReloadableResourceManager)rm;
/* 221:244 */       IResourceManagerReloadListener ttol = new IResourceManagerReloadListener()
/* 222:    */       {
/* 223:    */         public void onResourceManagerReload(IResourceManager var1)
/* 224:    */         {
/* 225:248 */           TextureUtils.resourcesReloaded(var1);
/* 226:    */         }
/* 227:250 */       };
/* 228:251 */       tto.registerReloadListener(ttol);
/* 229:    */     }
/* 230:254 */     ITickableTextureObject tto1 = new ITickableTextureObject()
/* 231:    */     {
/* 232:    */       public void tick() {}
/* 233:    */       
/* 234:    */       public void loadTexture(IResourceManager var1)
/* 235:    */         throws IOException
/* 236:    */       {}
/* 237:    */       
/* 238:    */       public int getGlTextureId()
/* 239:    */       {
/* 240:263 */         return 0;
/* 241:    */       }
/* 242:265 */     };
/* 243:266 */     ResourceLocation ttol1 = new ResourceLocation("optifine/TickableTextures");
/* 244:267 */     Config.getTextureManager().loadTickableTexture(ttol1, tto1);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static String fixResourcePath(String path, String basePath)
/* 248:    */   {
/* 249:272 */     String strAssMc = "assets/minecraft/";
/* 250:274 */     if (path.startsWith(strAssMc))
/* 251:    */     {
/* 252:276 */       path = path.substring(strAssMc.length());
/* 253:277 */       return path;
/* 254:    */     }
/* 255:279 */     if (path.startsWith("./"))
/* 256:    */     {
/* 257:281 */       path = path.substring(2);
/* 258:283 */       if (!basePath.endsWith("/")) {
/* 259:285 */         basePath = basePath + "/";
/* 260:    */       }
/* 261:288 */       path = basePath + path;
/* 262:289 */       return path;
/* 263:    */     }
/* 264:293 */     String strMcpatcher = "mcpatcher/";
/* 265:295 */     if (path.startsWith("~/"))
/* 266:    */     {
/* 267:297 */       path = path.substring(2);
/* 268:298 */       path = strMcpatcher + path;
/* 269:299 */       return path;
/* 270:    */     }
/* 271:301 */     if (path.startsWith("/"))
/* 272:    */     {
/* 273:303 */       path = strMcpatcher + path.substring(1);
/* 274:304 */       return path;
/* 275:    */     }
/* 276:308 */     return path;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public static String getBasePath(String path)
/* 280:    */   {
/* 281:315 */     int pos = path.lastIndexOf('/');
/* 282:316 */     return pos < 0 ? "" : path.substring(0, pos);
/* 283:    */   }
/* 284:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.TextureUtils
 * JD-Core Version:    0.7.0.1
 */