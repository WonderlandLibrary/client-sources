/*    1:     */ package net.minecraft.client.renderer;
/*    2:     */ 
/*    3:     */ import net.minecraft.block.Block;
/*    4:     */ import net.minecraft.block.BlockAnvil;
/*    5:     */ import net.minecraft.block.BlockBeacon;
/*    6:     */ import net.minecraft.block.BlockBed;
/*    7:     */ import net.minecraft.block.BlockBrewingStand;
/*    8:     */ import net.minecraft.block.BlockCauldron;
/*    9:     */ import net.minecraft.block.BlockCocoa;
/*   10:     */ import net.minecraft.block.BlockDirectional;
/*   11:     */ import net.minecraft.block.BlockDoublePlant;
/*   12:     */ import net.minecraft.block.BlockDragonEgg;
/*   13:     */ import net.minecraft.block.BlockEndPortalFrame;
/*   14:     */ import net.minecraft.block.BlockFence;
/*   15:     */ import net.minecraft.block.BlockFenceGate;
/*   16:     */ import net.minecraft.block.BlockFire;
/*   17:     */ import net.minecraft.block.BlockFlowerPot;
/*   18:     */ import net.minecraft.block.BlockGrass;
/*   19:     */ import net.minecraft.block.BlockHopper;
/*   20:     */ import net.minecraft.block.BlockLiquid;
/*   21:     */ import net.minecraft.block.BlockPane;
/*   22:     */ import net.minecraft.block.BlockPistonBase;
/*   23:     */ import net.minecraft.block.BlockPistonExtension;
/*   24:     */ import net.minecraft.block.BlockRailBase;
/*   25:     */ import net.minecraft.block.BlockRedstoneComparator;
/*   26:     */ import net.minecraft.block.BlockRedstoneDiode;
/*   27:     */ import net.minecraft.block.BlockRedstoneRepeater;
/*   28:     */ import net.minecraft.block.BlockRedstoneWire;
/*   29:     */ import net.minecraft.block.BlockStainedGlassPane;
/*   30:     */ import net.minecraft.block.BlockStairs;
/*   31:     */ import net.minecraft.block.BlockStem;
/*   32:     */ import net.minecraft.block.BlockTripWire;
/*   33:     */ import net.minecraft.block.BlockWall;
/*   34:     */ import net.minecraft.block.material.Material;
/*   35:     */ import net.minecraft.client.Minecraft;
/*   36:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   37:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   38:     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
/*   39:     */ import net.minecraft.client.settings.GameSettings;
/*   40:     */ import net.minecraft.init.Blocks;
/*   41:     */ import net.minecraft.item.Item;
/*   42:     */ import net.minecraft.item.ItemBlock;
/*   43:     */ import net.minecraft.src.Config;
/*   44:     */ import net.minecraft.src.ConnectedProperties;
/*   45:     */ import net.minecraft.src.ConnectedTextures;
/*   46:     */ import net.minecraft.src.CustomColorizer;
/*   47:     */ import net.minecraft.src.NaturalProperties;
/*   48:     */ import net.minecraft.src.NaturalTextures;
/*   49:     */ import net.minecraft.src.Reflector;
/*   50:     */ import net.minecraft.src.ReflectorClass;
/*   51:     */ import net.minecraft.src.ReflectorMethod;
/*   52:     */ import net.minecraft.src.TextureUtils;
/*   53:     */ import net.minecraft.tileentity.TileEntity;
/*   54:     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*   55:     */ import net.minecraft.util.IIcon;
/*   56:     */ import net.minecraft.util.MathHelper;
/*   57:     */ import net.minecraft.util.Vec3;
/*   58:     */ import net.minecraft.util.Vec3Pool;
/*   59:     */ import net.minecraft.world.IBlockAccess;
/*   60:     */ import net.minecraft.world.World;
/*   61:     */ import org.lwjgl.opengl.GL11;
/*   62:     */ 
/*   63:     */ public class RenderBlocks
/*   64:     */ {
/*   65:     */   public IBlockAccess blockAccess;
/*   66:     */   public IIcon overrideBlockTexture;
/*   67:     */   public boolean flipTexture;
/*   68:     */   public boolean renderAllFaces;
/*   69:  72 */   public static boolean fancyGrass = true;
/*   70:  73 */   public static boolean cfgGrassFix = true;
/*   71:  74 */   public boolean useInventoryTint = true;
/*   72:  75 */   public boolean renderFromInside = false;
/*   73:     */   public double renderMinX;
/*   74:     */   public double renderMaxX;
/*   75:     */   public double renderMinY;
/*   76:     */   public double renderMaxY;
/*   77:     */   public double renderMinZ;
/*   78:     */   public double renderMaxZ;
/*   79:     */   public boolean lockBlockBounds;
/*   80:     */   public boolean partialRenderBounds;
/*   81:     */   public final Minecraft minecraftRB;
/*   82:     */   public int uvRotateEast;
/*   83:     */   public int uvRotateWest;
/*   84:     */   public int uvRotateSouth;
/*   85:     */   public int uvRotateNorth;
/*   86:     */   public int uvRotateTop;
/*   87:     */   public int uvRotateBottom;
/*   88:     */   public boolean enableAO;
/*   89:     */   public float aoLightValueScratchXYZNNN;
/*   90:     */   public float aoLightValueScratchXYNN;
/*   91:     */   public float aoLightValueScratchXYZNNP;
/*   92:     */   public float aoLightValueScratchYZNN;
/*   93:     */   public float aoLightValueScratchYZNP;
/*   94:     */   public float aoLightValueScratchXYZPNN;
/*   95:     */   public float aoLightValueScratchXYPN;
/*   96:     */   public float aoLightValueScratchXYZPNP;
/*   97:     */   public float aoLightValueScratchXYZNPN;
/*   98:     */   public float aoLightValueScratchXYNP;
/*   99:     */   public float aoLightValueScratchXYZNPP;
/*  100:     */   public float aoLightValueScratchYZPN;
/*  101:     */   public float aoLightValueScratchXYZPPN;
/*  102:     */   public float aoLightValueScratchXYPP;
/*  103:     */   public float aoLightValueScratchYZPP;
/*  104:     */   public float aoLightValueScratchXYZPPP;
/*  105:     */   public float aoLightValueScratchXZNN;
/*  106:     */   public float aoLightValueScratchXZPN;
/*  107:     */   public float aoLightValueScratchXZNP;
/*  108:     */   public float aoLightValueScratchXZPP;
/*  109:     */   public int aoBrightnessXYZNNN;
/*  110:     */   public int aoBrightnessXYNN;
/*  111:     */   public int aoBrightnessXYZNNP;
/*  112:     */   public int aoBrightnessYZNN;
/*  113:     */   public int aoBrightnessYZNP;
/*  114:     */   public int aoBrightnessXYZPNN;
/*  115:     */   public int aoBrightnessXYPN;
/*  116:     */   public int aoBrightnessXYZPNP;
/*  117:     */   public int aoBrightnessXYZNPN;
/*  118:     */   public int aoBrightnessXYNP;
/*  119:     */   public int aoBrightnessXYZNPP;
/*  120:     */   public int aoBrightnessYZPN;
/*  121:     */   public int aoBrightnessXYZPPN;
/*  122:     */   public int aoBrightnessXYPP;
/*  123:     */   public int aoBrightnessYZPP;
/*  124:     */   public int aoBrightnessXYZPPP;
/*  125:     */   public int aoBrightnessXZNN;
/*  126:     */   public int aoBrightnessXZPN;
/*  127:     */   public int aoBrightnessXZNP;
/*  128:     */   public int aoBrightnessXZPP;
/*  129:     */   public int brightnessTopLeft;
/*  130:     */   public int brightnessBottomLeft;
/*  131:     */   public int brightnessBottomRight;
/*  132:     */   public int brightnessTopRight;
/*  133:     */   public float colorRedTopLeft;
/*  134:     */   public float colorRedBottomLeft;
/*  135:     */   public float colorRedBottomRight;
/*  136:     */   public float colorRedTopRight;
/*  137:     */   public float colorGreenTopLeft;
/*  138:     */   public float colorGreenBottomLeft;
/*  139:     */   public float colorGreenBottomRight;
/*  140:     */   public float colorGreenTopRight;
/*  141:     */   public float colorBlueTopLeft;
/*  142:     */   public float colorBlueBottomLeft;
/*  143:     */   public float colorBlueBottomRight;
/*  144:     */   public float colorBlueTopRight;
/*  145:     */   public boolean aoLightValuesCalculated;
/*  146: 161 */   public float aoLightValueOpaque = 0.2F;
/*  147: 162 */   public boolean betterSnowEnabled = true;
/*  148:     */   private static final String __OBFID = "CL_00000940";
/*  149:     */   
/*  150:     */   public RenderBlocks(IBlockAccess par1IBlockAccess)
/*  151:     */   {
/*  152: 167 */     this.blockAccess = par1IBlockAccess;
/*  153: 168 */     this.minecraftRB = Minecraft.getMinecraft();
/*  154: 169 */     this.aoLightValueOpaque = (1.0F - Config.getAmbientOcclusionLevel() * 0.8F);
/*  155:     */   }
/*  156:     */   
/*  157:     */   public RenderBlocks()
/*  158:     */   {
/*  159: 174 */     this.minecraftRB = Minecraft.getMinecraft();
/*  160:     */   }
/*  161:     */   
/*  162:     */   public void setOverrideBlockTexture(IIcon p_147757_1_)
/*  163:     */   {
/*  164: 182 */     this.overrideBlockTexture = p_147757_1_;
/*  165:     */   }
/*  166:     */   
/*  167:     */   public void clearOverrideBlockTexture()
/*  168:     */   {
/*  169: 190 */     this.overrideBlockTexture = null;
/*  170:     */   }
/*  171:     */   
/*  172:     */   public boolean hasOverrideBlockTexture()
/*  173:     */   {
/*  174: 195 */     return this.overrideBlockTexture != null;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public void setRenderFromInside(boolean p_147786_1_)
/*  178:     */   {
/*  179: 200 */     this.renderFromInside = p_147786_1_;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setRenderAllFaces(boolean p_147753_1_)
/*  183:     */   {
/*  184: 205 */     this.renderAllFaces = p_147753_1_;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void setRenderBounds(double p_147782_1_, double p_147782_3_, double p_147782_5_, double p_147782_7_, double p_147782_9_, double p_147782_11_)
/*  188:     */   {
/*  189: 210 */     if (!this.lockBlockBounds)
/*  190:     */     {
/*  191: 212 */       this.renderMinX = p_147782_1_;
/*  192: 213 */       this.renderMaxX = p_147782_7_;
/*  193: 214 */       this.renderMinY = p_147782_3_;
/*  194: 215 */       this.renderMaxY = p_147782_9_;
/*  195: 216 */       this.renderMinZ = p_147782_5_;
/*  196: 217 */       this.renderMaxZ = p_147782_11_;
/*  197: 218 */       this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*  198:     */     }
/*  199:     */   }
/*  200:     */   
/*  201:     */   public void setRenderBoundsFromBlock(Block p_147775_1_)
/*  202:     */   {
/*  203: 224 */     if (!this.lockBlockBounds)
/*  204:     */     {
/*  205: 226 */       this.renderMinX = p_147775_1_.getBlockBoundsMinX();
/*  206: 227 */       this.renderMaxX = p_147775_1_.getBlockBoundsMaxX();
/*  207: 228 */       this.renderMinY = p_147775_1_.getBlockBoundsMinY();
/*  208: 229 */       this.renderMaxY = p_147775_1_.getBlockBoundsMaxY();
/*  209: 230 */       this.renderMinZ = p_147775_1_.getBlockBoundsMinZ();
/*  210: 231 */       this.renderMaxZ = p_147775_1_.getBlockBoundsMaxZ();
/*  211: 232 */       this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*  212:     */     }
/*  213:     */   }
/*  214:     */   
/*  215:     */   public void overrideBlockBounds(double p_147770_1_, double p_147770_3_, double p_147770_5_, double p_147770_7_, double p_147770_9_, double p_147770_11_)
/*  216:     */   {
/*  217: 238 */     this.renderMinX = p_147770_1_;
/*  218: 239 */     this.renderMaxX = p_147770_7_;
/*  219: 240 */     this.renderMinY = p_147770_3_;
/*  220: 241 */     this.renderMaxY = p_147770_9_;
/*  221: 242 */     this.renderMinZ = p_147770_5_;
/*  222: 243 */     this.renderMaxZ = p_147770_11_;
/*  223: 244 */     this.lockBlockBounds = true;
/*  224: 245 */     this.partialRenderBounds = ((this.minecraftRB.gameSettings.ambientOcclusion >= 2) && ((this.renderMinX > 0.0D) || (this.renderMaxX < 1.0D) || (this.renderMinY > 0.0D) || (this.renderMaxY < 1.0D) || (this.renderMinZ > 0.0D) || (this.renderMaxZ < 1.0D)));
/*  225:     */   }
/*  226:     */   
/*  227:     */   public void unlockBlockBounds()
/*  228:     */   {
/*  229: 253 */     this.lockBlockBounds = false;
/*  230:     */   }
/*  231:     */   
/*  232:     */   public void renderBlockUsingTexture(Block p_147792_1_, int p_147792_2_, int p_147792_3_, int p_147792_4_, IIcon p_147792_5_)
/*  233:     */   {
/*  234: 261 */     setOverrideBlockTexture(p_147792_5_);
/*  235: 262 */     renderBlockByRenderType(p_147792_1_, p_147792_2_, p_147792_3_, p_147792_4_);
/*  236: 263 */     clearOverrideBlockTexture();
/*  237:     */   }
/*  238:     */   
/*  239:     */   public void renderBlockAllFaces(Block p_147769_1_, int p_147769_2_, int p_147769_3_, int p_147769_4_)
/*  240:     */   {
/*  241: 271 */     this.renderAllFaces = true;
/*  242: 272 */     renderBlockByRenderType(p_147769_1_, p_147769_2_, p_147769_3_, p_147769_4_);
/*  243: 273 */     this.renderAllFaces = false;
/*  244:     */   }
/*  245:     */   
/*  246:     */   public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4)
/*  247:     */   {
/*  248: 278 */     int i = par1Block.getRenderType();
/*  249: 280 */     if (i == -1) {
/*  250: 282 */       return false;
/*  251:     */     }
/*  252: 286 */     par1Block.setBlockBoundsBasedOnState(this.blockAccess, par2, par3, par4);
/*  253: 288 */     if ((Config.isBetterSnow()) && (par1Block == Blocks.standing_sign) && (hasSnowNeighbours(par2, par3, par4))) {
/*  254: 290 */       renderSnow(par2, par3, par4, Blocks.snow_layer.getBlockBoundsMaxY());
/*  255:     */     }
/*  256: 293 */     setRenderBoundsFromBlock(par1Block);
/*  257: 295 */     switch (i)
/*  258:     */     {
/*  259:     */     case 0: 
/*  260: 298 */       return renderStandardBlock(par1Block, par2, par3, par4);
/*  261:     */     case 1: 
/*  262: 301 */       return renderCrossedSquares(par1Block, par2, par3, par4);
/*  263:     */     case 2: 
/*  264: 304 */       return renderBlockTorch(par1Block, par2, par3, par4);
/*  265:     */     case 3: 
/*  266: 307 */       return renderBlockFire((BlockFire)par1Block, par2, par3, par4);
/*  267:     */     case 4: 
/*  268: 310 */       return renderBlockFluids(par1Block, par2, par3, par4);
/*  269:     */     case 5: 
/*  270: 313 */       return renderBlockRedstoneWire(par1Block, par2, par3, par4);
/*  271:     */     case 6: 
/*  272: 316 */       return renderBlockCrops(par1Block, par2, par3, par4);
/*  273:     */     case 7: 
/*  274: 319 */       return renderBlockDoor(par1Block, par2, par3, par4);
/*  275:     */     case 8: 
/*  276: 322 */       return renderBlockLadder(par1Block, par2, par3, par4);
/*  277:     */     case 9: 
/*  278: 325 */       return renderBlockMinecartTrack((BlockRailBase)par1Block, par2, par3, par4);
/*  279:     */     case 10: 
/*  280: 328 */       return renderBlockStairs((BlockStairs)par1Block, par2, par3, par4);
/*  281:     */     case 11: 
/*  282: 331 */       return renderBlockFence((BlockFence)par1Block, par2, par3, par4);
/*  283:     */     case 12: 
/*  284: 334 */       return renderBlockLever(par1Block, par2, par3, par4);
/*  285:     */     case 13: 
/*  286: 337 */       return renderBlockCactus(par1Block, par2, par3, par4);
/*  287:     */     case 14: 
/*  288: 340 */       return renderBlockBed(par1Block, par2, par3, par4);
/*  289:     */     case 15: 
/*  290: 343 */       return renderBlockRepeater((BlockRedstoneRepeater)par1Block, par2, par3, par4);
/*  291:     */     case 16: 
/*  292: 346 */       return renderPistonBase(par1Block, par2, par3, par4, false);
/*  293:     */     case 17: 
/*  294: 349 */       return renderPistonExtension(par1Block, par2, par3, par4, true);
/*  295:     */     case 18: 
/*  296: 352 */       return renderBlockPane((BlockPane)par1Block, par2, par3, par4);
/*  297:     */     case 19: 
/*  298: 355 */       return renderBlockStem(par1Block, par2, par3, par4);
/*  299:     */     case 20: 
/*  300: 358 */       return renderBlockVine(par1Block, par2, par3, par4);
/*  301:     */     case 21: 
/*  302: 361 */       return renderBlockFenceGate((BlockFenceGate)par1Block, par2, par3, par4);
/*  303:     */     case 22: 
/*  304:     */     default: 
/*  305: 365 */       if (Reflector.ModLoader.exists()) {
/*  306: 367 */         return Reflector.callBoolean(Reflector.ModLoader_renderWorldBlock, new Object[] { this, this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4), par1Block, Integer.valueOf(i) });
/*  307:     */       }
/*  308: 371 */       if (Reflector.FMLRenderAccessLibrary.exists()) {
/*  309: 373 */         return Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderWorldBlock, new Object[] { this, this.blockAccess, Integer.valueOf(par2), Integer.valueOf(par3), Integer.valueOf(par4), par1Block, Integer.valueOf(i) });
/*  310:     */       }
/*  311: 376 */       return false;
/*  312:     */     case 23: 
/*  313: 380 */       return renderBlockLilyPad(par1Block, par2, par3, par4);
/*  314:     */     case 24: 
/*  315: 383 */       return renderBlockCauldron((BlockCauldron)par1Block, par2, par3, par4);
/*  316:     */     case 25: 
/*  317: 386 */       return renderBlockBrewingStand((BlockBrewingStand)par1Block, par2, par3, par4);
/*  318:     */     case 26: 
/*  319: 389 */       return renderBlockEndPortalFrame((BlockEndPortalFrame)par1Block, par2, par3, par4);
/*  320:     */     case 27: 
/*  321: 392 */       return renderBlockDragonEgg((BlockDragonEgg)par1Block, par2, par3, par4);
/*  322:     */     case 28: 
/*  323: 395 */       return renderBlockCocoa((BlockCocoa)par1Block, par2, par3, par4);
/*  324:     */     case 29: 
/*  325: 398 */       return renderBlockTripWireSource(par1Block, par2, par3, par4);
/*  326:     */     case 30: 
/*  327: 401 */       return renderBlockTripWire(par1Block, par2, par3, par4);
/*  328:     */     case 31: 
/*  329: 404 */       return renderBlockLog(par1Block, par2, par3, par4);
/*  330:     */     case 32: 
/*  331: 407 */       return renderBlockWall((BlockWall)par1Block, par2, par3, par4);
/*  332:     */     case 33: 
/*  333: 410 */       return renderBlockFlowerpot((BlockFlowerPot)par1Block, par2, par3, par4);
/*  334:     */     case 34: 
/*  335: 413 */       return renderBlockBeacon((BlockBeacon)par1Block, par2, par3, par4);
/*  336:     */     case 35: 
/*  337: 416 */       return renderBlockAnvil((BlockAnvil)par1Block, par2, par3, par4);
/*  338:     */     case 36: 
/*  339: 419 */       return renderBlockRedstoneDiode((BlockRedstoneDiode)par1Block, par2, par3, par4);
/*  340:     */     case 37: 
/*  341: 422 */       return renderBlockRedstoneComparator((BlockRedstoneComparator)par1Block, par2, par3, par4);
/*  342:     */     case 38: 
/*  343: 425 */       return renderBlockHopper((BlockHopper)par1Block, par2, par3, par4);
/*  344:     */     case 39: 
/*  345: 428 */       return renderBlockQuartz(par1Block, par2, par3, par4);
/*  346:     */     case 40: 
/*  347: 431 */       return renderBlockDoublePlant((BlockDoublePlant)par1Block, par2, par3, par4);
/*  348:     */     }
/*  349: 434 */     return renderBlockStainedGlassPane(par1Block, par2, par3, par4);
/*  350:     */   }
/*  351:     */   
/*  352:     */   public boolean renderBlockEndPortalFrame(BlockEndPortalFrame p_147743_1_, int p_147743_2_, int p_147743_3_, int p_147743_4_)
/*  353:     */   {
/*  354: 444 */     int var5 = this.blockAccess.getBlockMetadata(p_147743_2_, p_147743_3_, p_147743_4_);
/*  355: 445 */     int var6 = var5 & 0x3;
/*  356: 447 */     if (var6 == 0) {
/*  357: 449 */       this.uvRotateTop = 3;
/*  358: 451 */     } else if (var6 == 3) {
/*  359: 453 */       this.uvRotateTop = 1;
/*  360: 455 */     } else if (var6 == 1) {
/*  361: 457 */       this.uvRotateTop = 2;
/*  362:     */     }
/*  363: 460 */     if (!BlockEndPortalFrame.func_150020_b(var5))
/*  364:     */     {
/*  365: 462 */       setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
/*  366: 463 */       renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
/*  367: 464 */       this.uvRotateTop = 0;
/*  368: 465 */       return true;
/*  369:     */     }
/*  370: 469 */     this.renderAllFaces = true;
/*  371: 470 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
/*  372: 471 */     renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
/*  373: 472 */     setOverrideBlockTexture(p_147743_1_.func_150021_e());
/*  374: 473 */     setRenderBounds(0.25D, 0.8125D, 0.25D, 0.75D, 1.0D, 0.75D);
/*  375: 474 */     renderStandardBlock(p_147743_1_, p_147743_2_, p_147743_3_, p_147743_4_);
/*  376: 475 */     this.renderAllFaces = false;
/*  377: 476 */     clearOverrideBlockTexture();
/*  378: 477 */     this.uvRotateTop = 0;
/*  379: 478 */     return true;
/*  380:     */   }
/*  381:     */   
/*  382:     */   public boolean renderBlockBed(Block p_147773_1_, int p_147773_2_, int p_147773_3_, int p_147773_4_)
/*  383:     */   {
/*  384: 487 */     Tessellator var5 = Tessellator.instance;
/*  385: 488 */     int var6 = this.blockAccess.getBlockMetadata(p_147773_2_, p_147773_3_, p_147773_4_);
/*  386: 489 */     int var7 = BlockBed.func_149895_l(var6);
/*  387: 490 */     boolean var8 = BlockBed.func_149975_b(var6);
/*  388: 492 */     if (Reflector.ForgeBlock_getBedDirection.exists()) {
/*  389: 494 */       var7 = Reflector.callInt(p_147773_1_, Reflector.ForgeBlock_getBedDirection, new Object[] { this.blockAccess, Integer.valueOf(p_147773_2_), Integer.valueOf(p_147773_3_), Integer.valueOf(p_147773_4_) });
/*  390:     */     }
/*  391: 497 */     if (Reflector.ForgeBlock_isBedFoot.exists()) {
/*  392: 499 */       var8 = Reflector.callBoolean(p_147773_1_, Reflector.ForgeBlock_isBedFoot, new Object[] { this.blockAccess, Integer.valueOf(p_147773_2_), Integer.valueOf(p_147773_3_), Integer.valueOf(p_147773_4_) });
/*  393:     */     }
/*  394: 502 */     float var9 = 0.5F;
/*  395: 503 */     float var10 = 1.0F;
/*  396: 504 */     float var11 = 0.8F;
/*  397: 505 */     float var12 = 0.6F;
/*  398: 506 */     int var25 = p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_);
/*  399: 507 */     var5.setBrightness(var25);
/*  400: 508 */     var5.setColorOpaque_F(var9, var9, var9);
/*  401: 509 */     IIcon var26 = getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 0);
/*  402: 511 */     if (this.overrideBlockTexture != null) {
/*  403: 513 */       var26 = this.overrideBlockTexture;
/*  404:     */     }
/*  405: 516 */     double var27 = var26.getMinU();
/*  406: 517 */     double var29 = var26.getMaxU();
/*  407: 518 */     double var31 = var26.getMinV();
/*  408: 519 */     double var33 = var26.getMaxV();
/*  409: 520 */     double var35 = p_147773_2_ + this.renderMinX;
/*  410: 521 */     double var37 = p_147773_2_ + this.renderMaxX;
/*  411: 522 */     double var39 = p_147773_3_ + this.renderMinY + 0.1875D;
/*  412: 523 */     double var41 = p_147773_4_ + this.renderMinZ;
/*  413: 524 */     double var43 = p_147773_4_ + this.renderMaxZ;
/*  414: 525 */     var5.addVertexWithUV(var35, var39, var43, var27, var33);
/*  415: 526 */     var5.addVertexWithUV(var35, var39, var41, var27, var31);
/*  416: 527 */     var5.addVertexWithUV(var37, var39, var41, var29, var31);
/*  417: 528 */     var5.addVertexWithUV(var37, var39, var43, var29, var33);
/*  418: 529 */     var5.setBrightness(p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_ + 1, p_147773_4_));
/*  419: 530 */     var5.setColorOpaque_F(var10, var10, var10);
/*  420: 531 */     var26 = getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 1);
/*  421: 533 */     if (this.overrideBlockTexture != null) {
/*  422: 535 */       var26 = this.overrideBlockTexture;
/*  423:     */     }
/*  424: 538 */     var27 = var26.getMinU();
/*  425: 539 */     var29 = var26.getMaxU();
/*  426: 540 */     var31 = var26.getMinV();
/*  427: 541 */     var33 = var26.getMaxV();
/*  428: 542 */     var35 = var27;
/*  429: 543 */     var37 = var29;
/*  430: 544 */     var39 = var31;
/*  431: 545 */     var41 = var31;
/*  432: 546 */     var43 = var27;
/*  433: 547 */     double var45 = var29;
/*  434: 548 */     double var47 = var33;
/*  435: 549 */     double var49 = var33;
/*  436: 551 */     if (var7 == 0)
/*  437:     */     {
/*  438: 553 */       var37 = var27;
/*  439: 554 */       var39 = var33;
/*  440: 555 */       var43 = var29;
/*  441: 556 */       var49 = var31;
/*  442:     */     }
/*  443: 558 */     else if (var7 == 2)
/*  444:     */     {
/*  445: 560 */       var35 = var29;
/*  446: 561 */       var41 = var33;
/*  447: 562 */       var45 = var27;
/*  448: 563 */       var47 = var31;
/*  449:     */     }
/*  450: 565 */     else if (var7 == 3)
/*  451:     */     {
/*  452: 567 */       var35 = var29;
/*  453: 568 */       var41 = var33;
/*  454: 569 */       var45 = var27;
/*  455: 570 */       var47 = var31;
/*  456: 571 */       var37 = var27;
/*  457: 572 */       var39 = var33;
/*  458: 573 */       var43 = var29;
/*  459: 574 */       var49 = var31;
/*  460:     */     }
/*  461: 577 */     double var51 = p_147773_2_ + this.renderMinX;
/*  462: 578 */     double var53 = p_147773_2_ + this.renderMaxX;
/*  463: 579 */     double var55 = p_147773_3_ + this.renderMaxY;
/*  464: 580 */     double var57 = p_147773_4_ + this.renderMinZ;
/*  465: 581 */     double var59 = p_147773_4_ + this.renderMaxZ;
/*  466: 582 */     var5.addVertexWithUV(var53, var55, var59, var43, var47);
/*  467: 583 */     var5.addVertexWithUV(var53, var55, var57, var35, var39);
/*  468: 584 */     var5.addVertexWithUV(var51, var55, var57, var37, var41);
/*  469: 585 */     var5.addVertexWithUV(var51, var55, var59, var45, var49);
/*  470: 586 */     int var61 = net.minecraft.util.Direction.directionToFacing[var7];
/*  471: 588 */     if (var8) {
/*  472: 590 */       var61 = net.minecraft.util.Direction.directionToFacing[net.minecraft.util.Direction.rotateOpposite[var7]];
/*  473:     */     }
/*  474: 593 */     byte var62 = 4;
/*  475: 595 */     switch (var7)
/*  476:     */     {
/*  477:     */     case 0: 
/*  478: 598 */       var62 = 5;
/*  479: 599 */       break;
/*  480:     */     case 1: 
/*  481: 602 */       var62 = 3;
/*  482:     */     case 2: 
/*  483:     */     default: 
/*  484:     */       break;
/*  485:     */     case 3: 
/*  486: 609 */       var62 = 2;
/*  487:     */     }
/*  488: 612 */     if ((var61 != 2) && ((this.renderAllFaces) || (p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1, 2))))
/*  489:     */     {
/*  490: 614 */       var5.setBrightness(this.renderMinZ > 0.0D ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ - 1));
/*  491: 615 */       var5.setColorOpaque_F(var11, var11, var11);
/*  492: 616 */       this.flipTexture = (var62 == 2);
/*  493: 617 */       renderFaceZNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 2));
/*  494:     */     }
/*  495: 620 */     if ((var61 != 3) && ((this.renderAllFaces) || (p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1, 3))))
/*  496:     */     {
/*  497: 622 */       var5.setBrightness(this.renderMaxZ < 1.0D ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_ + 1));
/*  498: 623 */       var5.setColorOpaque_F(var11, var11, var11);
/*  499: 624 */       this.flipTexture = (var62 == 3);
/*  500: 625 */       renderFaceZPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 3));
/*  501:     */     }
/*  502: 628 */     if ((var61 != 4) && ((this.renderAllFaces) || (p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_, 4))))
/*  503:     */     {
/*  504: 630 */       var5.setBrightness(this.renderMinZ > 0.0D ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ - 1, p_147773_3_, p_147773_4_));
/*  505: 631 */       var5.setColorOpaque_F(var12, var12, var12);
/*  506: 632 */       this.flipTexture = (var62 == 4);
/*  507: 633 */       renderFaceXNeg(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 4));
/*  508:     */     }
/*  509: 636 */     if ((var61 != 5) && ((this.renderAllFaces) || (p_147773_1_.shouldSideBeRendered(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_, 5))))
/*  510:     */     {
/*  511: 638 */       var5.setBrightness(this.renderMaxZ < 1.0D ? var25 : p_147773_1_.getBlockBrightness(this.blockAccess, p_147773_2_ + 1, p_147773_3_, p_147773_4_));
/*  512: 639 */       var5.setColorOpaque_F(var12, var12, var12);
/*  513: 640 */       this.flipTexture = (var62 == 5);
/*  514: 641 */       renderFaceXPos(p_147773_1_, p_147773_2_, p_147773_3_, p_147773_4_, getBlockIcon(p_147773_1_, this.blockAccess, p_147773_2_, p_147773_3_, p_147773_4_, 5));
/*  515:     */     }
/*  516: 644 */     this.flipTexture = false;
/*  517: 645 */     return true;
/*  518:     */   }
/*  519:     */   
/*  520:     */   public boolean renderBlockBrewingStand(BlockBrewingStand p_147741_1_, int p_147741_2_, int p_147741_3_, int p_147741_4_)
/*  521:     */   {
/*  522: 653 */     setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
/*  523: 654 */     renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
/*  524: 655 */     setOverrideBlockTexture(p_147741_1_.func_149959_e());
/*  525: 656 */     this.renderAllFaces = true;
/*  526: 657 */     setRenderBounds(0.5625D, 0.0D, 0.3125D, 0.9375D, 0.125D, 0.6875D);
/*  527: 658 */     renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
/*  528: 659 */     setRenderBounds(0.125D, 0.0D, 0.0625D, 0.5D, 0.125D, 0.4375D);
/*  529: 660 */     renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
/*  530: 661 */     setRenderBounds(0.125D, 0.0D, 0.5625D, 0.5D, 0.125D, 0.9375D);
/*  531: 662 */     renderStandardBlock(p_147741_1_, p_147741_2_, p_147741_3_, p_147741_4_);
/*  532: 663 */     this.renderAllFaces = false;
/*  533: 664 */     clearOverrideBlockTexture();
/*  534: 665 */     Tessellator var5 = Tessellator.instance;
/*  535: 666 */     var5.setBrightness(p_147741_1_.getBlockBrightness(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_));
/*  536: 667 */     int var6 = p_147741_1_.colorMultiplier(this.blockAccess, p_147741_2_, p_147741_3_, p_147741_4_);
/*  537: 668 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/*  538: 669 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/*  539: 670 */     float var9 = (var6 & 0xFF) / 255.0F;
/*  540: 672 */     if (EntityRenderer.anaglyphEnable)
/*  541:     */     {
/*  542: 674 */       float var32 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
/*  543: 675 */       float var31 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
/*  544: 676 */       float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
/*  545: 677 */       var7 = var32;
/*  546: 678 */       var8 = var31;
/*  547: 679 */       var9 = var12;
/*  548:     */     }
/*  549: 682 */     var5.setColorOpaque_F(var7, var8, var9);
/*  550: 683 */     IIcon var321 = getBlockIconFromSideAndMetadata(p_147741_1_, 0, 0);
/*  551: 685 */     if (hasOverrideBlockTexture()) {
/*  552: 687 */       var321 = this.overrideBlockTexture;
/*  553:     */     }
/*  554: 690 */     double var311 = var321.getMinV();
/*  555: 691 */     double var13 = var321.getMaxV();
/*  556: 692 */     int var15 = this.blockAccess.getBlockMetadata(p_147741_2_, p_147741_3_, p_147741_4_);
/*  557: 694 */     for (int var16 = 0; var16 < 3; var16++)
/*  558:     */     {
/*  559: 696 */       double var17 = var16 * 3.141592653589793D * 2.0D / 3.0D + 1.570796326794897D;
/*  560: 697 */       double var19 = var321.getInterpolatedU(8.0D);
/*  561: 698 */       double var21 = var321.getMaxU();
/*  562: 700 */       if ((var15 & 1 << var16) != 0) {
/*  563: 702 */         var21 = var321.getMinU();
/*  564:     */       }
/*  565: 705 */       double var23 = p_147741_2_ + 0.5D;
/*  566: 706 */       double var25 = p_147741_2_ + 0.5D + Math.sin(var17) * 8.0D / 16.0D;
/*  567: 707 */       double var27 = p_147741_4_ + 0.5D;
/*  568: 708 */       double var29 = p_147741_4_ + 0.5D + Math.cos(var17) * 8.0D / 16.0D;
/*  569: 709 */       var5.addVertexWithUV(var23, p_147741_3_ + 1, var27, var19, var311);
/*  570: 710 */       var5.addVertexWithUV(var23, p_147741_3_ + 0, var27, var19, var13);
/*  571: 711 */       var5.addVertexWithUV(var25, p_147741_3_ + 0, var29, var21, var13);
/*  572: 712 */       var5.addVertexWithUV(var25, p_147741_3_ + 1, var29, var21, var311);
/*  573: 713 */       var5.addVertexWithUV(var25, p_147741_3_ + 1, var29, var21, var311);
/*  574: 714 */       var5.addVertexWithUV(var25, p_147741_3_ + 0, var29, var21, var13);
/*  575: 715 */       var5.addVertexWithUV(var23, p_147741_3_ + 0, var27, var19, var13);
/*  576: 716 */       var5.addVertexWithUV(var23, p_147741_3_ + 1, var27, var19, var311);
/*  577:     */     }
/*  578: 719 */     p_147741_1_.setBlockBoundsForItemRender();
/*  579: 720 */     return true;
/*  580:     */   }
/*  581:     */   
/*  582:     */   public boolean renderBlockCauldron(BlockCauldron p_147785_1_, int p_147785_2_, int p_147785_3_, int p_147785_4_)
/*  583:     */   {
/*  584: 728 */     renderStandardBlock(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_);
/*  585: 729 */     Tessellator var5 = Tessellator.instance;
/*  586: 730 */     var5.setBrightness(p_147785_1_.getBlockBrightness(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_));
/*  587: 731 */     int var6 = p_147785_1_.colorMultiplier(this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
/*  588: 732 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/*  589: 733 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/*  590: 734 */     float var9 = (var6 & 0xFF) / 255.0F;
/*  591: 737 */     if (EntityRenderer.anaglyphEnable)
/*  592:     */     {
/*  593: 739 */       float var15 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
/*  594: 740 */       float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
/*  595: 741 */       float var16 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
/*  596: 742 */       var7 = var15;
/*  597: 743 */       var8 = var11;
/*  598: 744 */       var9 = var16;
/*  599:     */     }
/*  600: 747 */     var5.setColorOpaque_F(var7, var8, var9);
/*  601: 748 */     IIcon var151 = p_147785_1_.getBlockTextureFromSide(2);
/*  602: 749 */     float var11 = 0.125F;
/*  603: 750 */     renderFaceXPos(p_147785_1_, p_147785_2_ - 1.0F + var11, p_147785_3_, p_147785_4_, var151);
/*  604: 751 */     renderFaceXNeg(p_147785_1_, p_147785_2_ + 1.0F - var11, p_147785_3_, p_147785_4_, var151);
/*  605: 752 */     renderFaceZPos(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_ - 1.0F + var11, var151);
/*  606: 753 */     renderFaceZNeg(p_147785_1_, p_147785_2_, p_147785_3_, p_147785_4_ + 1.0F - var11, var151);
/*  607: 754 */     IIcon var161 = BlockCauldron.func_150026_e("inner");
/*  608: 755 */     renderFaceYPos(p_147785_1_, p_147785_2_, p_147785_3_ - 1.0F + 0.25F, p_147785_4_, var161);
/*  609: 756 */     renderFaceYNeg(p_147785_1_, p_147785_2_, p_147785_3_ + 1.0F - 0.75F, p_147785_4_, var161);
/*  610: 757 */     int var13 = this.blockAccess.getBlockMetadata(p_147785_2_, p_147785_3_, p_147785_4_);
/*  611: 759 */     if (var13 > 0)
/*  612:     */     {
/*  613: 761 */       IIcon var14 = BlockLiquid.func_149803_e("water_still");
/*  614: 762 */       int wc = CustomColorizer.getFluidColor(Blocks.water, this.blockAccess, p_147785_2_, p_147785_3_, p_147785_4_);
/*  615: 763 */       float wr = (wc >> 16 & 0xFF) / 255.0F;
/*  616: 764 */       float wg = (wc >> 8 & 0xFF) / 255.0F;
/*  617: 765 */       float wb = (wc & 0xFF) / 255.0F;
/*  618: 766 */       var5.setColorOpaque_F(wr, wg, wb);
/*  619: 767 */       renderFaceYPos(p_147785_1_, p_147785_2_, p_147785_3_ - 1.0F + BlockCauldron.func_150025_c(var13), p_147785_4_, var14);
/*  620:     */     }
/*  621: 770 */     return true;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public boolean renderBlockFlowerpot(BlockFlowerPot p_147752_1_, int p_147752_2_, int p_147752_3_, int p_147752_4_)
/*  625:     */   {
/*  626: 778 */     renderStandardBlock(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_);
/*  627: 779 */     Tessellator var5 = Tessellator.instance;
/*  628: 780 */     var5.setBrightness(p_147752_1_.getBlockBrightness(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_));
/*  629: 781 */     int var6 = p_147752_1_.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
/*  630: 782 */     IIcon var7 = getBlockIconFromSide(p_147752_1_, 0);
/*  631: 783 */     float var8 = (var6 >> 16 & 0xFF) / 255.0F;
/*  632: 784 */     float var9 = (var6 >> 8 & 0xFF) / 255.0F;
/*  633: 785 */     float var10 = (var6 & 0xFF) / 255.0F;
/*  634: 788 */     if (EntityRenderer.anaglyphEnable)
/*  635:     */     {
/*  636: 790 */       float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/*  637: 791 */       float var21 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/*  638: 792 */       float var22 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/*  639: 793 */       var8 = var11;
/*  640: 794 */       var9 = var21;
/*  641: 795 */       var10 = var22;
/*  642:     */     }
/*  643: 798 */     var5.setColorOpaque_F(var8, var9, var10);
/*  644: 799 */     float var11 = 0.1865F;
/*  645: 800 */     renderFaceXPos(p_147752_1_, p_147752_2_ - 0.5F + var11, p_147752_3_, p_147752_4_, var7);
/*  646: 801 */     renderFaceXNeg(p_147752_1_, p_147752_2_ + 0.5F - var11, p_147752_3_, p_147752_4_, var7);
/*  647: 802 */     renderFaceZPos(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_ - 0.5F + var11, var7);
/*  648: 803 */     renderFaceZNeg(p_147752_1_, p_147752_2_, p_147752_3_, p_147752_4_ + 0.5F - var11, var7);
/*  649: 804 */     renderFaceYPos(p_147752_1_, p_147752_2_, p_147752_3_ - 0.5F + var11 + 0.1875F, p_147752_4_, getBlockIcon(Blocks.dirt));
/*  650: 805 */     TileEntity var211 = this.blockAccess.getTileEntity(p_147752_2_, p_147752_3_, p_147752_4_);
/*  651: 807 */     if ((var211 != null) && ((var211 instanceof TileEntityFlowerPot)))
/*  652:     */     {
/*  653: 809 */       Item var221 = ((TileEntityFlowerPot)var211).func_145965_a();
/*  654: 810 */       int var14 = ((TileEntityFlowerPot)var211).func_145966_b();
/*  655: 812 */       if ((var221 instanceof ItemBlock))
/*  656:     */       {
/*  657: 814 */         Block var15 = Block.getBlockFromItem(var221);
/*  658: 815 */         int var16 = var15.getRenderType();
/*  659: 816 */         float var17 = 0.0F;
/*  660: 817 */         float var18 = 4.0F;
/*  661: 818 */         float var19 = 0.0F;
/*  662: 819 */         var5.addTranslation(var17 / 16.0F, var18 / 16.0F, var19 / 16.0F);
/*  663: 820 */         var6 = var15.colorMultiplier(this.blockAccess, p_147752_2_, p_147752_3_, p_147752_4_);
/*  664: 822 */         if (var6 != 16777215)
/*  665:     */         {
/*  666: 824 */           var8 = (var6 >> 16 & 0xFF) / 255.0F;
/*  667: 825 */           var9 = (var6 >> 8 & 0xFF) / 255.0F;
/*  668: 826 */           var10 = (var6 & 0xFF) / 255.0F;
/*  669: 827 */           var5.setColorOpaque_F(var8, var9, var10);
/*  670:     */         }
/*  671: 830 */         if (var16 == 1)
/*  672:     */         {
/*  673: 832 */           drawCrossedSquares(getBlockIconFromSideAndMetadata(var15, 0, var14), p_147752_2_, p_147752_3_, p_147752_4_, 0.75F);
/*  674:     */         }
/*  675: 834 */         else if (var16 == 13)
/*  676:     */         {
/*  677: 836 */           this.renderAllFaces = true;
/*  678: 837 */           float var20 = 0.125F;
/*  679: 838 */           setRenderBounds(0.5F - var20, 0.0D, 0.5F - var20, 0.5F + var20, 0.25D, 0.5F + var20);
/*  680: 839 */           renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
/*  681: 840 */           setRenderBounds(0.5F - var20, 0.25D, 0.5F - var20, 0.5F + var20, 0.5D, 0.5F + var20);
/*  682: 841 */           renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
/*  683: 842 */           setRenderBounds(0.5F - var20, 0.5D, 0.5F - var20, 0.5F + var20, 0.75D, 0.5F + var20);
/*  684: 843 */           renderStandardBlock(var15, p_147752_2_, p_147752_3_, p_147752_4_);
/*  685: 844 */           this.renderAllFaces = false;
/*  686: 845 */           setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  687:     */         }
/*  688: 848 */         var5.addTranslation(-var17 / 16.0F, -var18 / 16.0F, -var19 / 16.0F);
/*  689:     */       }
/*  690:     */     }
/*  691: 852 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147752_2_, p_147752_3_, p_147752_4_))) {
/*  692: 854 */       renderSnow(p_147752_2_, p_147752_3_, p_147752_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/*  693:     */     }
/*  694: 857 */     return true;
/*  695:     */   }
/*  696:     */   
/*  697:     */   public boolean renderBlockAnvil(BlockAnvil p_147725_1_, int p_147725_2_, int p_147725_3_, int p_147725_4_)
/*  698:     */   {
/*  699: 865 */     return renderBlockAnvilMetadata(p_147725_1_, p_147725_2_, p_147725_3_, p_147725_4_, this.blockAccess.getBlockMetadata(p_147725_2_, p_147725_3_, p_147725_4_));
/*  700:     */   }
/*  701:     */   
/*  702:     */   public boolean renderBlockAnvilMetadata(BlockAnvil p_147780_1_, int p_147780_2_, int p_147780_3_, int p_147780_4_, int p_147780_5_)
/*  703:     */   {
/*  704: 873 */     Tessellator var6 = Tessellator.instance;
/*  705: 874 */     var6.setBrightness(p_147780_1_.getBlockBrightness(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_));
/*  706: 875 */     int var7 = p_147780_1_.colorMultiplier(this.blockAccess, p_147780_2_, p_147780_3_, p_147780_4_);
/*  707: 876 */     float var8 = (var7 >> 16 & 0xFF) / 255.0F;
/*  708: 877 */     float var9 = (var7 >> 8 & 0xFF) / 255.0F;
/*  709: 878 */     float var10 = (var7 & 0xFF) / 255.0F;
/*  710: 880 */     if (EntityRenderer.anaglyphEnable)
/*  711:     */     {
/*  712: 882 */       float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/*  713: 883 */       float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/*  714: 884 */       float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/*  715: 885 */       var8 = var11;
/*  716: 886 */       var9 = var12;
/*  717: 887 */       var10 = var13;
/*  718:     */     }
/*  719: 890 */     var6.setColorOpaque_F(var8, var9, var10);
/*  720: 891 */     return renderBlockAnvilOrient(p_147780_1_, p_147780_2_, p_147780_3_, p_147780_4_, p_147780_5_, false);
/*  721:     */   }
/*  722:     */   
/*  723:     */   public boolean renderBlockAnvilOrient(BlockAnvil p_147728_1_, int p_147728_2_, int p_147728_3_, int p_147728_4_, int p_147728_5_, boolean p_147728_6_)
/*  724:     */   {
/*  725: 899 */     int var7 = p_147728_6_ ? 0 : p_147728_5_ & 0x3;
/*  726: 900 */     boolean var8 = false;
/*  727: 901 */     float var9 = 0.0F;
/*  728: 903 */     switch (var7)
/*  729:     */     {
/*  730:     */     case 0: 
/*  731: 906 */       this.uvRotateSouth = 2;
/*  732: 907 */       this.uvRotateNorth = 1;
/*  733: 908 */       this.uvRotateTop = 3;
/*  734: 909 */       this.uvRotateBottom = 3;
/*  735: 910 */       break;
/*  736:     */     case 1: 
/*  737: 913 */       this.uvRotateEast = 1;
/*  738: 914 */       this.uvRotateWest = 2;
/*  739: 915 */       this.uvRotateTop = 2;
/*  740: 916 */       this.uvRotateBottom = 1;
/*  741: 917 */       var8 = true;
/*  742: 918 */       break;
/*  743:     */     case 2: 
/*  744: 921 */       this.uvRotateSouth = 1;
/*  745: 922 */       this.uvRotateNorth = 2;
/*  746: 923 */       break;
/*  747:     */     case 3: 
/*  748: 926 */       this.uvRotateEast = 2;
/*  749: 927 */       this.uvRotateWest = 1;
/*  750: 928 */       this.uvRotateTop = 1;
/*  751: 929 */       this.uvRotateBottom = 2;
/*  752: 930 */       var8 = true;
/*  753:     */     }
/*  754: 933 */     var9 = renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 0, var9, 0.75F, 0.25F, 0.75F, var8, p_147728_6_, p_147728_5_);
/*  755: 934 */     var9 = renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 1, var9, 0.5F, 0.0625F, 0.625F, var8, p_147728_6_, p_147728_5_);
/*  756: 935 */     var9 = renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 2, var9, 0.25F, 0.3125F, 0.5F, var8, p_147728_6_, p_147728_5_);
/*  757: 936 */     renderBlockAnvilRotate(p_147728_1_, p_147728_2_, p_147728_3_, p_147728_4_, 3, var9, 0.625F, 0.375F, 1.0F, var8, p_147728_6_, p_147728_5_);
/*  758: 937 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  759: 938 */     this.uvRotateEast = 0;
/*  760: 939 */     this.uvRotateWest = 0;
/*  761: 940 */     this.uvRotateSouth = 0;
/*  762: 941 */     this.uvRotateNorth = 0;
/*  763: 942 */     this.uvRotateTop = 0;
/*  764: 943 */     this.uvRotateBottom = 0;
/*  765: 944 */     return true;
/*  766:     */   }
/*  767:     */   
/*  768:     */   public float renderBlockAnvilRotate(BlockAnvil p_147737_1_, int p_147737_2_, int p_147737_3_, int p_147737_4_, int p_147737_5_, float p_147737_6_, float p_147737_7_, float p_147737_8_, float p_147737_9_, boolean p_147737_10_, boolean p_147737_11_, int p_147737_12_)
/*  769:     */   {
/*  770: 952 */     if (p_147737_10_)
/*  771:     */     {
/*  772: 954 */       float var14 = p_147737_7_;
/*  773: 955 */       p_147737_7_ = p_147737_9_;
/*  774: 956 */       p_147737_9_ = var14;
/*  775:     */     }
/*  776: 959 */     p_147737_7_ /= 2.0F;
/*  777: 960 */     p_147737_9_ /= 2.0F;
/*  778: 961 */     p_147737_1_.field_149833_b = p_147737_5_;
/*  779: 962 */     setRenderBounds(0.5F - p_147737_7_, p_147737_6_, 0.5F - p_147737_9_, 0.5F + p_147737_7_, p_147737_6_ + p_147737_8_, 0.5F + p_147737_9_);
/*  780: 964 */     if (p_147737_11_)
/*  781:     */     {
/*  782: 966 */       Tessellator var141 = Tessellator.instance;
/*  783: 967 */       var141.startDrawingQuads();
/*  784: 968 */       var141.setNormal(0.0F, -1.0F, 0.0F);
/*  785: 969 */       renderFaceYNeg(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 0, p_147737_12_));
/*  786: 970 */       var141.draw();
/*  787: 971 */       var141.startDrawingQuads();
/*  788: 972 */       var141.setNormal(0.0F, 1.0F, 0.0F);
/*  789: 973 */       renderFaceYPos(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 1, p_147737_12_));
/*  790: 974 */       var141.draw();
/*  791: 975 */       var141.startDrawingQuads();
/*  792: 976 */       var141.setNormal(0.0F, 0.0F, -1.0F);
/*  793: 977 */       renderFaceZNeg(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 2, p_147737_12_));
/*  794: 978 */       var141.draw();
/*  795: 979 */       var141.startDrawingQuads();
/*  796: 980 */       var141.setNormal(0.0F, 0.0F, 1.0F);
/*  797: 981 */       renderFaceZPos(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 3, p_147737_12_));
/*  798: 982 */       var141.draw();
/*  799: 983 */       var141.startDrawingQuads();
/*  800: 984 */       var141.setNormal(-1.0F, 0.0F, 0.0F);
/*  801: 985 */       renderFaceXNeg(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 4, p_147737_12_));
/*  802: 986 */       var141.draw();
/*  803: 987 */       var141.startDrawingQuads();
/*  804: 988 */       var141.setNormal(1.0F, 0.0F, 0.0F);
/*  805: 989 */       renderFaceXPos(p_147737_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147737_1_, 5, p_147737_12_));
/*  806: 990 */       var141.draw();
/*  807:     */     }
/*  808:     */     else
/*  809:     */     {
/*  810: 994 */       renderStandardBlock(p_147737_1_, p_147737_2_, p_147737_3_, p_147737_4_);
/*  811:     */     }
/*  812: 997 */     return p_147737_6_ + p_147737_8_;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public boolean renderBlockTorch(Block p_147791_1_, int p_147791_2_, int p_147791_3_, int p_147791_4_)
/*  816:     */   {
/*  817:1005 */     int var5 = this.blockAccess.getBlockMetadata(p_147791_2_, p_147791_3_, p_147791_4_);
/*  818:1006 */     Tessellator var6 = Tessellator.instance;
/*  819:1007 */     var6.setBrightness(p_147791_1_.getBlockBrightness(this.blockAccess, p_147791_2_, p_147791_3_, p_147791_4_));
/*  820:1008 */     var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  821:1009 */     double var7 = 0.4000000059604645D;
/*  822:1010 */     double var9 = 0.5D - var7;
/*  823:1011 */     double var11 = 0.2000000029802322D;
/*  824:1013 */     if (var5 == 1)
/*  825:     */     {
/*  826:1015 */       renderTorchAtAngle(p_147791_1_, p_147791_2_ - var9, p_147791_3_ + var11, p_147791_4_, -var7, 0.0D, 0);
/*  827:     */     }
/*  828:1017 */     else if (var5 == 2)
/*  829:     */     {
/*  830:1019 */       renderTorchAtAngle(p_147791_1_, p_147791_2_ + var9, p_147791_3_ + var11, p_147791_4_, var7, 0.0D, 0);
/*  831:     */     }
/*  832:1021 */     else if (var5 == 3)
/*  833:     */     {
/*  834:1023 */       renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_ + var11, p_147791_4_ - var9, 0.0D, -var7, 0);
/*  835:     */     }
/*  836:1025 */     else if (var5 == 4)
/*  837:     */     {
/*  838:1027 */       renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_ + var11, p_147791_4_ + var9, 0.0D, var7, 0);
/*  839:     */     }
/*  840:     */     else
/*  841:     */     {
/*  842:1031 */       renderTorchAtAngle(p_147791_1_, p_147791_2_, p_147791_3_, p_147791_4_, 0.0D, 0.0D, 0);
/*  843:1033 */       if ((p_147791_1_ != Blocks.torch) && (Config.isBetterSnow()) && (hasSnowNeighbours(p_147791_2_, p_147791_3_, p_147791_4_))) {
/*  844:1035 */         renderSnow(p_147791_2_, p_147791_3_, p_147791_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/*  845:     */       }
/*  846:     */     }
/*  847:1039 */     return true;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public boolean renderBlockRepeater(BlockRedstoneRepeater p_147759_1_, int p_147759_2_, int p_147759_3_, int p_147759_4_)
/*  851:     */   {
/*  852:1047 */     int var5 = this.blockAccess.getBlockMetadata(p_147759_2_, p_147759_3_, p_147759_4_);
/*  853:1048 */     int var6 = var5 & 0x3;
/*  854:1049 */     int var7 = (var5 & 0xC) >> 2;
/*  855:1050 */     Tessellator var8 = Tessellator.instance;
/*  856:1051 */     var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
/*  857:1052 */     var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  858:1053 */     double var9 = -0.1875D;
/*  859:1054 */     boolean var11 = p_147759_1_.func_149910_g(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_, var5);
/*  860:1055 */     double var12 = 0.0D;
/*  861:1056 */     double var14 = 0.0D;
/*  862:1057 */     double var16 = 0.0D;
/*  863:1058 */     double var18 = 0.0D;
/*  864:1060 */     switch (var6)
/*  865:     */     {
/*  866:     */     case 0: 
/*  867:1063 */       var18 = -0.3125D;
/*  868:1064 */       var14 = BlockRedstoneRepeater.field_149973_b[var7];
/*  869:1065 */       break;
/*  870:     */     case 1: 
/*  871:1068 */       var16 = 0.3125D;
/*  872:1069 */       var12 = -BlockRedstoneRepeater.field_149973_b[var7];
/*  873:1070 */       break;
/*  874:     */     case 2: 
/*  875:1073 */       var18 = 0.3125D;
/*  876:1074 */       var14 = -BlockRedstoneRepeater.field_149973_b[var7];
/*  877:1075 */       break;
/*  878:     */     case 3: 
/*  879:1078 */       var16 = -0.3125D;
/*  880:1079 */       var12 = BlockRedstoneRepeater.field_149973_b[var7];
/*  881:     */     }
/*  882:1082 */     if (!var11)
/*  883:     */     {
/*  884:1084 */       renderTorchAtAngle(p_147759_1_, p_147759_2_ + var12, p_147759_3_ + var9, p_147759_4_ + var14, 0.0D, 0.0D, 0);
/*  885:     */     }
/*  886:     */     else
/*  887:     */     {
/*  888:1088 */       IIcon var20 = getBlockIcon(Blocks.bedrock);
/*  889:1089 */       setOverrideBlockTexture(var20);
/*  890:1090 */       float var21 = 2.0F;
/*  891:1091 */       float var22 = 14.0F;
/*  892:1092 */       float var23 = 7.0F;
/*  893:1093 */       float var24 = 9.0F;
/*  894:1095 */       switch (var6)
/*  895:     */       {
/*  896:     */       case 1: 
/*  897:     */       case 3: 
/*  898:1099 */         var21 = 7.0F;
/*  899:1100 */         var22 = 9.0F;
/*  900:1101 */         var23 = 2.0F;
/*  901:1102 */         var24 = 14.0F;
/*  902:     */       }
/*  903:1107 */       setRenderBounds(var21 / 16.0F + (float)var12, 0.125D, var23 / 16.0F + (float)var14, var22 / 16.0F + (float)var12, 0.25D, var24 / 16.0F + (float)var14);
/*  904:1108 */       double var25 = var20.getInterpolatedU(var21);
/*  905:1109 */       double var27 = var20.getInterpolatedV(var23);
/*  906:1110 */       double var29 = var20.getInterpolatedU(var22);
/*  907:1111 */       double var31 = var20.getInterpolatedV(var24);
/*  908:1112 */       var8.addVertexWithUV(p_147759_2_ + var21 / 16.0F + var12, p_147759_3_ + 0.25F, p_147759_4_ + var23 / 16.0F + var14, var25, var27);
/*  909:1113 */       var8.addVertexWithUV(p_147759_2_ + var21 / 16.0F + var12, p_147759_3_ + 0.25F, p_147759_4_ + var24 / 16.0F + var14, var25, var31);
/*  910:1114 */       var8.addVertexWithUV(p_147759_2_ + var22 / 16.0F + var12, p_147759_3_ + 0.25F, p_147759_4_ + var24 / 16.0F + var14, var29, var31);
/*  911:1115 */       var8.addVertexWithUV(p_147759_2_ + var22 / 16.0F + var12, p_147759_3_ + 0.25F, p_147759_4_ + var23 / 16.0F + var14, var29, var27);
/*  912:1116 */       renderStandardBlock(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
/*  913:1117 */       setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
/*  914:1118 */       clearOverrideBlockTexture();
/*  915:     */     }
/*  916:1122 */     var8.setBrightness(p_147759_1_.getBlockBrightness(this.blockAccess, p_147759_2_, p_147759_3_, p_147759_4_));
/*  917:1123 */     var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  918:1124 */     renderTorchAtAngle(p_147759_1_, p_147759_2_ + var16, p_147759_3_ + var9, p_147759_4_ + var18, 0.0D, 0.0D, 0);
/*  919:1125 */     renderBlockRedstoneDiode(p_147759_1_, p_147759_2_, p_147759_3_, p_147759_4_);
/*  920:1126 */     return true;
/*  921:     */   }
/*  922:     */   
/*  923:     */   public boolean renderBlockRedstoneComparator(BlockRedstoneComparator p_147781_1_, int p_147781_2_, int p_147781_3_, int p_147781_4_)
/*  924:     */   {
/*  925:1131 */     Tessellator var5 = Tessellator.instance;
/*  926:1132 */     var5.setBrightness(p_147781_1_.getBlockBrightness(this.blockAccess, p_147781_2_, p_147781_3_, p_147781_4_));
/*  927:1133 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  928:1134 */     int var6 = this.blockAccess.getBlockMetadata(p_147781_2_, p_147781_3_, p_147781_4_);
/*  929:1135 */     int var7 = var6 & 0x3;
/*  930:1136 */     double var8 = 0.0D;
/*  931:1137 */     double var10 = -0.1875D;
/*  932:1138 */     double var12 = 0.0D;
/*  933:1139 */     double var14 = 0.0D;
/*  934:1140 */     double var16 = 0.0D;
/*  935:     */     IIcon var18;
/*  936:     */     IIcon var18;
/*  937:1143 */     if (p_147781_1_.func_149969_d(var6))
/*  938:     */     {
/*  939:1145 */       var18 = Blocks.redstone_torch.getBlockTextureFromSide(0);
/*  940:     */     }
/*  941:     */     else
/*  942:     */     {
/*  943:1149 */       var10 -= 0.1875D;
/*  944:1150 */       var18 = Blocks.unlit_redstone_torch.getBlockTextureFromSide(0);
/*  945:     */     }
/*  946:1153 */     switch (var7)
/*  947:     */     {
/*  948:     */     case 0: 
/*  949:1156 */       var12 = -0.3125D;
/*  950:1157 */       var16 = 1.0D;
/*  951:1158 */       break;
/*  952:     */     case 1: 
/*  953:1161 */       var8 = 0.3125D;
/*  954:1162 */       var14 = -1.0D;
/*  955:1163 */       break;
/*  956:     */     case 2: 
/*  957:1166 */       var12 = 0.3125D;
/*  958:1167 */       var16 = -1.0D;
/*  959:1168 */       break;
/*  960:     */     case 3: 
/*  961:1171 */       var8 = -0.3125D;
/*  962:1172 */       var14 = 1.0D;
/*  963:     */     }
/*  964:1175 */     renderTorchAtAngle(p_147781_1_, p_147781_2_ + 0.25D * var14 + 0.1875D * var16, p_147781_3_ - 0.1875F, p_147781_4_ + 0.25D * var16 + 0.1875D * var14, 0.0D, 0.0D, var6);
/*  965:1176 */     renderTorchAtAngle(p_147781_1_, p_147781_2_ + 0.25D * var14 + -0.1875D * var16, p_147781_3_ - 0.1875F, p_147781_4_ + 0.25D * var16 + -0.1875D * var14, 0.0D, 0.0D, var6);
/*  966:1177 */     setOverrideBlockTexture(var18);
/*  967:1178 */     renderTorchAtAngle(p_147781_1_, p_147781_2_ + var8, p_147781_3_ + var10, p_147781_4_ + var12, 0.0D, 0.0D, var6);
/*  968:1179 */     clearOverrideBlockTexture();
/*  969:1180 */     renderBlockRedstoneDiodeMetadata(p_147781_1_, p_147781_2_, p_147781_3_, p_147781_4_, var7);
/*  970:1181 */     return true;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public boolean renderBlockRedstoneDiode(BlockRedstoneDiode p_147748_1_, int p_147748_2_, int p_147748_3_, int p_147748_4_)
/*  974:     */   {
/*  975:1186 */     Tessellator var5 = Tessellator.instance;
/*  976:1187 */     renderBlockRedstoneDiodeMetadata(p_147748_1_, p_147748_2_, p_147748_3_, p_147748_4_, this.blockAccess.getBlockMetadata(p_147748_2_, p_147748_3_, p_147748_4_) & 0x3);
/*  977:1188 */     return true;
/*  978:     */   }
/*  979:     */   
/*  980:     */   public void renderBlockRedstoneDiodeMetadata(BlockRedstoneDiode p_147732_1_, int p_147732_2_, int p_147732_3_, int p_147732_4_, int p_147732_5_)
/*  981:     */   {
/*  982:1193 */     renderStandardBlock(p_147732_1_, p_147732_2_, p_147732_3_, p_147732_4_);
/*  983:1194 */     Tessellator var6 = Tessellator.instance;
/*  984:1195 */     var6.setBrightness(p_147732_1_.getBlockBrightness(this.blockAccess, p_147732_2_, p_147732_3_, p_147732_4_));
/*  985:1196 */     var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/*  986:1197 */     int var7 = this.blockAccess.getBlockMetadata(p_147732_2_, p_147732_3_, p_147732_4_);
/*  987:1198 */     IIcon var8 = getBlockIconFromSideAndMetadata(p_147732_1_, 1, var7);
/*  988:1199 */     double var9 = var8.getMinU();
/*  989:1200 */     double var11 = var8.getMaxU();
/*  990:1201 */     double var13 = var8.getMinV();
/*  991:1202 */     double var15 = var8.getMaxV();
/*  992:1203 */     double var17 = 0.125D;
/*  993:1204 */     double var19 = p_147732_2_ + 1;
/*  994:1205 */     double var21 = p_147732_2_ + 1;
/*  995:1206 */     double var23 = p_147732_2_ + 0;
/*  996:1207 */     double var25 = p_147732_2_ + 0;
/*  997:1208 */     double var27 = p_147732_4_ + 0;
/*  998:1209 */     double var29 = p_147732_4_ + 1;
/*  999:1210 */     double var31 = p_147732_4_ + 1;
/* 1000:1211 */     double var33 = p_147732_4_ + 0;
/* 1001:1212 */     double var35 = p_147732_3_ + var17;
/* 1002:1214 */     if (p_147732_5_ == 2)
/* 1003:     */     {
/* 1004:1216 */       var19 = var21 = p_147732_2_ + 0;
/* 1005:1217 */       var23 = var25 = p_147732_2_ + 1;
/* 1006:1218 */       var27 = var33 = p_147732_4_ + 1;
/* 1007:1219 */       var29 = var31 = p_147732_4_ + 0;
/* 1008:     */     }
/* 1009:1221 */     else if (p_147732_5_ == 3)
/* 1010:     */     {
/* 1011:1223 */       var19 = var25 = p_147732_2_ + 0;
/* 1012:1224 */       var21 = var23 = p_147732_2_ + 1;
/* 1013:1225 */       var27 = var29 = p_147732_4_ + 0;
/* 1014:1226 */       var31 = var33 = p_147732_4_ + 1;
/* 1015:     */     }
/* 1016:1228 */     else if (p_147732_5_ == 1)
/* 1017:     */     {
/* 1018:1230 */       var19 = var25 = p_147732_2_ + 1;
/* 1019:1231 */       var21 = var23 = p_147732_2_ + 0;
/* 1020:1232 */       var27 = var29 = p_147732_4_ + 1;
/* 1021:1233 */       var31 = var33 = p_147732_4_ + 0;
/* 1022:     */     }
/* 1023:1236 */     var6.addVertexWithUV(var25, var35, var33, var9, var13);
/* 1024:1237 */     var6.addVertexWithUV(var23, var35, var31, var9, var15);
/* 1025:1238 */     var6.addVertexWithUV(var21, var35, var29, var11, var15);
/* 1026:1239 */     var6.addVertexWithUV(var19, var35, var27, var11, var13);
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public void renderPistonBaseAllFaces(Block p_147804_1_, int p_147804_2_, int p_147804_3_, int p_147804_4_)
/* 1030:     */   {
/* 1031:1244 */     this.renderAllFaces = true;
/* 1032:1245 */     renderPistonBase(p_147804_1_, p_147804_2_, p_147804_3_, p_147804_4_, true);
/* 1033:1246 */     this.renderAllFaces = false;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public boolean renderPistonBase(Block p_147731_1_, int p_147731_2_, int p_147731_3_, int p_147731_4_, boolean p_147731_5_)
/* 1037:     */   {
/* 1038:1251 */     int var6 = this.blockAccess.getBlockMetadata(p_147731_2_, p_147731_3_, p_147731_4_);
/* 1039:1252 */     boolean var7 = (p_147731_5_) || ((var6 & 0x8) != 0);
/* 1040:1253 */     int var8 = BlockPistonBase.func_150076_b(var6);
/* 1041:1254 */     float var9 = 0.25F;
/* 1042:1256 */     if (var7)
/* 1043:     */     {
/* 1044:1258 */       switch (var8)
/* 1045:     */       {
/* 1046:     */       case 0: 
/* 1047:1261 */         this.uvRotateEast = 3;
/* 1048:1262 */         this.uvRotateWest = 3;
/* 1049:1263 */         this.uvRotateSouth = 3;
/* 1050:1264 */         this.uvRotateNorth = 3;
/* 1051:1265 */         setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1052:1266 */         break;
/* 1053:     */       case 1: 
/* 1054:1269 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
/* 1055:1270 */         break;
/* 1056:     */       case 2: 
/* 1057:1273 */         this.uvRotateSouth = 1;
/* 1058:1274 */         this.uvRotateNorth = 2;
/* 1059:1275 */         setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
/* 1060:1276 */         break;
/* 1061:     */       case 3: 
/* 1062:1279 */         this.uvRotateSouth = 2;
/* 1063:1280 */         this.uvRotateNorth = 1;
/* 1064:1281 */         this.uvRotateTop = 3;
/* 1065:1282 */         this.uvRotateBottom = 3;
/* 1066:1283 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
/* 1067:1284 */         break;
/* 1068:     */       case 4: 
/* 1069:1287 */         this.uvRotateEast = 1;
/* 1070:1288 */         this.uvRotateWest = 2;
/* 1071:1289 */         this.uvRotateTop = 2;
/* 1072:1290 */         this.uvRotateBottom = 1;
/* 1073:1291 */         setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1074:1292 */         break;
/* 1075:     */       case 5: 
/* 1076:1295 */         this.uvRotateEast = 2;
/* 1077:1296 */         this.uvRotateWest = 1;
/* 1078:1297 */         this.uvRotateTop = 1;
/* 1079:1298 */         this.uvRotateBottom = 2;
/* 1080:1299 */         setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
/* 1081:     */       }
/* 1082:1302 */       ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
/* 1083:1303 */       renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
/* 1084:1304 */       this.uvRotateEast = 0;
/* 1085:1305 */       this.uvRotateWest = 0;
/* 1086:1306 */       this.uvRotateSouth = 0;
/* 1087:1307 */       this.uvRotateNorth = 0;
/* 1088:1308 */       this.uvRotateTop = 0;
/* 1089:1309 */       this.uvRotateBottom = 0;
/* 1090:1310 */       setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1091:1311 */       ((BlockPistonBase)p_147731_1_).func_150070_b((float)this.renderMinX, (float)this.renderMinY, (float)this.renderMinZ, (float)this.renderMaxX, (float)this.renderMaxY, (float)this.renderMaxZ);
/* 1092:     */     }
/* 1093:     */     else
/* 1094:     */     {
/* 1095:1315 */       switch (var8)
/* 1096:     */       {
/* 1097:     */       case 0: 
/* 1098:1318 */         this.uvRotateEast = 3;
/* 1099:1319 */         this.uvRotateWest = 3;
/* 1100:1320 */         this.uvRotateSouth = 3;
/* 1101:1321 */         this.uvRotateNorth = 3;
/* 1102:     */       case 1: 
/* 1103:     */       default: 
/* 1104:     */         break;
/* 1105:     */       case 2: 
/* 1106:1328 */         this.uvRotateSouth = 1;
/* 1107:1329 */         this.uvRotateNorth = 2;
/* 1108:1330 */         break;
/* 1109:     */       case 3: 
/* 1110:1333 */         this.uvRotateSouth = 2;
/* 1111:1334 */         this.uvRotateNorth = 1;
/* 1112:1335 */         this.uvRotateTop = 3;
/* 1113:1336 */         this.uvRotateBottom = 3;
/* 1114:1337 */         break;
/* 1115:     */       case 4: 
/* 1116:1340 */         this.uvRotateEast = 1;
/* 1117:1341 */         this.uvRotateWest = 2;
/* 1118:1342 */         this.uvRotateTop = 2;
/* 1119:1343 */         this.uvRotateBottom = 1;
/* 1120:1344 */         break;
/* 1121:     */       case 5: 
/* 1122:1347 */         this.uvRotateEast = 2;
/* 1123:1348 */         this.uvRotateWest = 1;
/* 1124:1349 */         this.uvRotateTop = 1;
/* 1125:1350 */         this.uvRotateBottom = 2;
/* 1126:     */       }
/* 1127:1353 */       renderStandardBlock(p_147731_1_, p_147731_2_, p_147731_3_, p_147731_4_);
/* 1128:1354 */       this.uvRotateEast = 0;
/* 1129:1355 */       this.uvRotateWest = 0;
/* 1130:1356 */       this.uvRotateSouth = 0;
/* 1131:1357 */       this.uvRotateNorth = 0;
/* 1132:1358 */       this.uvRotateTop = 0;
/* 1133:1359 */       this.uvRotateBottom = 0;
/* 1134:     */     }
/* 1135:1362 */     return true;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public void renderPistonRodUD(double p_147763_1_, double p_147763_3_, double p_147763_5_, double p_147763_7_, double p_147763_9_, double p_147763_11_, float p_147763_13_, double p_147763_14_)
/* 1139:     */   {
/* 1140:1367 */     IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
/* 1141:1369 */     if (hasOverrideBlockTexture()) {
/* 1142:1371 */       var16 = this.overrideBlockTexture;
/* 1143:     */     }
/* 1144:1374 */     Tessellator var17 = Tessellator.instance;
/* 1145:1375 */     double var18 = var16.getMinU();
/* 1146:1376 */     double var20 = var16.getMinV();
/* 1147:1377 */     double var22 = var16.getInterpolatedU(p_147763_14_);
/* 1148:1378 */     double var24 = var16.getInterpolatedV(4.0D);
/* 1149:1379 */     var17.setColorOpaque_F(p_147763_13_, p_147763_13_, p_147763_13_);
/* 1150:1380 */     var17.addVertexWithUV(p_147763_1_, p_147763_7_, p_147763_9_, var22, var20);
/* 1151:1381 */     var17.addVertexWithUV(p_147763_1_, p_147763_5_, p_147763_9_, var18, var20);
/* 1152:1382 */     var17.addVertexWithUV(p_147763_3_, p_147763_5_, p_147763_11_, var18, var24);
/* 1153:1383 */     var17.addVertexWithUV(p_147763_3_, p_147763_7_, p_147763_11_, var22, var24);
/* 1154:     */   }
/* 1155:     */   
/* 1156:     */   public void renderPistonRodSN(double p_147789_1_, double p_147789_3_, double p_147789_5_, double p_147789_7_, double p_147789_9_, double p_147789_11_, float p_147789_13_, double p_147789_14_)
/* 1157:     */   {
/* 1158:1388 */     IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
/* 1159:1390 */     if (hasOverrideBlockTexture()) {
/* 1160:1392 */       var16 = this.overrideBlockTexture;
/* 1161:     */     }
/* 1162:1395 */     Tessellator var17 = Tessellator.instance;
/* 1163:1396 */     double var18 = var16.getMinU();
/* 1164:1397 */     double var20 = var16.getMinV();
/* 1165:1398 */     double var22 = var16.getInterpolatedU(p_147789_14_);
/* 1166:1399 */     double var24 = var16.getInterpolatedV(4.0D);
/* 1167:1400 */     var17.setColorOpaque_F(p_147789_13_, p_147789_13_, p_147789_13_);
/* 1168:1401 */     var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_11_, var22, var20);
/* 1169:1402 */     var17.addVertexWithUV(p_147789_1_, p_147789_5_, p_147789_9_, var18, var20);
/* 1170:1403 */     var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_9_, var18, var24);
/* 1171:1404 */     var17.addVertexWithUV(p_147789_3_, p_147789_7_, p_147789_11_, var22, var24);
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void renderPistonRodEW(double p_147738_1_, double p_147738_3_, double p_147738_5_, double p_147738_7_, double p_147738_9_, double p_147738_11_, float p_147738_13_, double p_147738_14_)
/* 1175:     */   {
/* 1176:1409 */     IIcon var16 = BlockPistonBase.func_150074_e("piston_side");
/* 1177:1411 */     if (hasOverrideBlockTexture()) {
/* 1178:1413 */       var16 = this.overrideBlockTexture;
/* 1179:     */     }
/* 1180:1416 */     Tessellator var17 = Tessellator.instance;
/* 1181:1417 */     double var18 = var16.getMinU();
/* 1182:1418 */     double var20 = var16.getMinV();
/* 1183:1419 */     double var22 = var16.getInterpolatedU(p_147738_14_);
/* 1184:1420 */     double var24 = var16.getInterpolatedV(4.0D);
/* 1185:1421 */     var17.setColorOpaque_F(p_147738_13_, p_147738_13_, p_147738_13_);
/* 1186:1422 */     var17.addVertexWithUV(p_147738_3_, p_147738_5_, p_147738_9_, var22, var20);
/* 1187:1423 */     var17.addVertexWithUV(p_147738_1_, p_147738_5_, p_147738_9_, var18, var20);
/* 1188:1424 */     var17.addVertexWithUV(p_147738_1_, p_147738_7_, p_147738_11_, var18, var24);
/* 1189:1425 */     var17.addVertexWithUV(p_147738_3_, p_147738_7_, p_147738_11_, var22, var24);
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   public void renderPistonExtensionAllFaces(Block p_147750_1_, int p_147750_2_, int p_147750_3_, int p_147750_4_, boolean p_147750_5_)
/* 1193:     */   {
/* 1194:1430 */     this.renderAllFaces = true;
/* 1195:1431 */     renderPistonExtension(p_147750_1_, p_147750_2_, p_147750_3_, p_147750_4_, p_147750_5_);
/* 1196:1432 */     this.renderAllFaces = false;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public boolean renderPistonExtension(Block p_147809_1_, int p_147809_2_, int p_147809_3_, int p_147809_4_, boolean p_147809_5_)
/* 1200:     */   {
/* 1201:1437 */     int var6 = this.blockAccess.getBlockMetadata(p_147809_2_, p_147809_3_, p_147809_4_);
/* 1202:1438 */     int var7 = BlockPistonExtension.func_150085_b(var6);
/* 1203:1439 */     float var8 = 0.25F;
/* 1204:1440 */     float var9 = 0.375F;
/* 1205:1441 */     float var10 = 0.625F;
/* 1206:1442 */     float var11 = p_147809_5_ ? 1.0F : 0.5F;
/* 1207:1443 */     double var12 = p_147809_5_ ? 16.0D : 8.0D;
/* 1208:1445 */     switch (var7)
/* 1209:     */     {
/* 1210:     */     case 0: 
/* 1211:1448 */       this.uvRotateEast = 3;
/* 1212:1449 */       this.uvRotateWest = 3;
/* 1213:1450 */       this.uvRotateSouth = 3;
/* 1214:1451 */       this.uvRotateNorth = 3;
/* 1215:1452 */       setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
/* 1216:1453 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1217:1454 */       renderPistonRodUD(p_147809_2_ + 0.375F, p_147809_2_ + 0.625F, p_147809_3_ + 0.25F, p_147809_3_ + 0.25F + var11, p_147809_4_ + 0.625F, p_147809_4_ + 0.625F, 0.8F, var12);
/* 1218:1455 */       renderPistonRodUD(p_147809_2_ + 0.625F, p_147809_2_ + 0.375F, p_147809_3_ + 0.25F, p_147809_3_ + 0.25F + var11, p_147809_4_ + 0.375F, p_147809_4_ + 0.375F, 0.8F, var12);
/* 1219:1456 */       renderPistonRodUD(p_147809_2_ + 0.375F, p_147809_2_ + 0.375F, p_147809_3_ + 0.25F, p_147809_3_ + 0.25F + var11, p_147809_4_ + 0.375F, p_147809_4_ + 0.625F, 0.6F, var12);
/* 1220:1457 */       renderPistonRodUD(p_147809_2_ + 0.625F, p_147809_2_ + 0.625F, p_147809_3_ + 0.25F, p_147809_3_ + 0.25F + var11, p_147809_4_ + 0.625F, p_147809_4_ + 0.375F, 0.6F, var12);
/* 1221:1458 */       break;
/* 1222:     */     case 1: 
/* 1223:1461 */       setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1224:1462 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1225:1463 */       renderPistonRodUD(p_147809_2_ + 0.375F, p_147809_2_ + 0.625F, p_147809_3_ - 0.25F + 1.0F - var11, p_147809_3_ - 0.25F + 1.0F, p_147809_4_ + 0.625F, p_147809_4_ + 0.625F, 0.8F, var12);
/* 1226:1464 */       renderPistonRodUD(p_147809_2_ + 0.625F, p_147809_2_ + 0.375F, p_147809_3_ - 0.25F + 1.0F - var11, p_147809_3_ - 0.25F + 1.0F, p_147809_4_ + 0.375F, p_147809_4_ + 0.375F, 0.8F, var12);
/* 1227:1465 */       renderPistonRodUD(p_147809_2_ + 0.375F, p_147809_2_ + 0.375F, p_147809_3_ - 0.25F + 1.0F - var11, p_147809_3_ - 0.25F + 1.0F, p_147809_4_ + 0.375F, p_147809_4_ + 0.625F, 0.6F, var12);
/* 1228:1466 */       renderPistonRodUD(p_147809_2_ + 0.625F, p_147809_2_ + 0.625F, p_147809_3_ - 0.25F + 1.0F - var11, p_147809_3_ - 0.25F + 1.0F, p_147809_4_ + 0.625F, p_147809_4_ + 0.375F, 0.6F, var12);
/* 1229:1467 */       break;
/* 1230:     */     case 2: 
/* 1231:1470 */       this.uvRotateSouth = 1;
/* 1232:1471 */       this.uvRotateNorth = 2;
/* 1233:1472 */       setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
/* 1234:1473 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1235:1474 */       renderPistonRodSN(p_147809_2_ + 0.375F, p_147809_2_ + 0.375F, p_147809_3_ + 0.625F, p_147809_3_ + 0.375F, p_147809_4_ + 0.25F, p_147809_4_ + 0.25F + var11, 0.6F, var12);
/* 1236:1475 */       renderPistonRodSN(p_147809_2_ + 0.625F, p_147809_2_ + 0.625F, p_147809_3_ + 0.375F, p_147809_3_ + 0.625F, p_147809_4_ + 0.25F, p_147809_4_ + 0.25F + var11, 0.6F, var12);
/* 1237:1476 */       renderPistonRodSN(p_147809_2_ + 0.375F, p_147809_2_ + 0.625F, p_147809_3_ + 0.375F, p_147809_3_ + 0.375F, p_147809_4_ + 0.25F, p_147809_4_ + 0.25F + var11, 0.5F, var12);
/* 1238:1477 */       renderPistonRodSN(p_147809_2_ + 0.625F, p_147809_2_ + 0.375F, p_147809_3_ + 0.625F, p_147809_3_ + 0.625F, p_147809_4_ + 0.25F, p_147809_4_ + 0.25F + var11, 1.0F, var12);
/* 1239:1478 */       break;
/* 1240:     */     case 3: 
/* 1241:1481 */       this.uvRotateSouth = 2;
/* 1242:1482 */       this.uvRotateNorth = 1;
/* 1243:1483 */       this.uvRotateTop = 3;
/* 1244:1484 */       this.uvRotateBottom = 3;
/* 1245:1485 */       setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
/* 1246:1486 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1247:1487 */       renderPistonRodSN(p_147809_2_ + 0.375F, p_147809_2_ + 0.375F, p_147809_3_ + 0.625F, p_147809_3_ + 0.375F, p_147809_4_ - 0.25F + 1.0F - var11, p_147809_4_ - 0.25F + 1.0F, 0.6F, var12);
/* 1248:1488 */       renderPistonRodSN(p_147809_2_ + 0.625F, p_147809_2_ + 0.625F, p_147809_3_ + 0.375F, p_147809_3_ + 0.625F, p_147809_4_ - 0.25F + 1.0F - var11, p_147809_4_ - 0.25F + 1.0F, 0.6F, var12);
/* 1249:1489 */       renderPistonRodSN(p_147809_2_ + 0.375F, p_147809_2_ + 0.625F, p_147809_3_ + 0.375F, p_147809_3_ + 0.375F, p_147809_4_ - 0.25F + 1.0F - var11, p_147809_4_ - 0.25F + 1.0F, 0.5F, var12);
/* 1250:1490 */       renderPistonRodSN(p_147809_2_ + 0.625F, p_147809_2_ + 0.375F, p_147809_3_ + 0.625F, p_147809_3_ + 0.625F, p_147809_4_ - 0.25F + 1.0F - var11, p_147809_4_ - 0.25F + 1.0F, 1.0F, var12);
/* 1251:1491 */       break;
/* 1252:     */     case 4: 
/* 1253:1494 */       this.uvRotateEast = 1;
/* 1254:1495 */       this.uvRotateWest = 2;
/* 1255:1496 */       this.uvRotateTop = 2;
/* 1256:1497 */       this.uvRotateBottom = 1;
/* 1257:1498 */       setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
/* 1258:1499 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1259:1500 */       renderPistonRodEW(p_147809_2_ + 0.25F, p_147809_2_ + 0.25F + var11, p_147809_3_ + 0.375F, p_147809_3_ + 0.375F, p_147809_4_ + 0.625F, p_147809_4_ + 0.375F, 0.5F, var12);
/* 1260:1501 */       renderPistonRodEW(p_147809_2_ + 0.25F, p_147809_2_ + 0.25F + var11, p_147809_3_ + 0.625F, p_147809_3_ + 0.625F, p_147809_4_ + 0.375F, p_147809_4_ + 0.625F, 1.0F, var12);
/* 1261:1502 */       renderPistonRodEW(p_147809_2_ + 0.25F, p_147809_2_ + 0.25F + var11, p_147809_3_ + 0.375F, p_147809_3_ + 0.625F, p_147809_4_ + 0.375F, p_147809_4_ + 0.375F, 0.6F, var12);
/* 1262:1503 */       renderPistonRodEW(p_147809_2_ + 0.25F, p_147809_2_ + 0.25F + var11, p_147809_3_ + 0.625F, p_147809_3_ + 0.375F, p_147809_4_ + 0.625F, p_147809_4_ + 0.625F, 0.6F, var12);
/* 1263:1504 */       break;
/* 1264:     */     case 5: 
/* 1265:1507 */       this.uvRotateEast = 2;
/* 1266:1508 */       this.uvRotateWest = 1;
/* 1267:1509 */       this.uvRotateTop = 1;
/* 1268:1510 */       this.uvRotateBottom = 2;
/* 1269:1511 */       setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1270:1512 */       renderStandardBlock(p_147809_1_, p_147809_2_, p_147809_3_, p_147809_4_);
/* 1271:1513 */       renderPistonRodEW(p_147809_2_ - 0.25F + 1.0F - var11, p_147809_2_ - 0.25F + 1.0F, p_147809_3_ + 0.375F, p_147809_3_ + 0.375F, p_147809_4_ + 0.625F, p_147809_4_ + 0.375F, 0.5F, var12);
/* 1272:1514 */       renderPistonRodEW(p_147809_2_ - 0.25F + 1.0F - var11, p_147809_2_ - 0.25F + 1.0F, p_147809_3_ + 0.625F, p_147809_3_ + 0.625F, p_147809_4_ + 0.375F, p_147809_4_ + 0.625F, 1.0F, var12);
/* 1273:1515 */       renderPistonRodEW(p_147809_2_ - 0.25F + 1.0F - var11, p_147809_2_ - 0.25F + 1.0F, p_147809_3_ + 0.375F, p_147809_3_ + 0.625F, p_147809_4_ + 0.375F, p_147809_4_ + 0.375F, 0.6F, var12);
/* 1274:1516 */       renderPistonRodEW(p_147809_2_ - 0.25F + 1.0F - var11, p_147809_2_ - 0.25F + 1.0F, p_147809_3_ + 0.625F, p_147809_3_ + 0.375F, p_147809_4_ + 0.625F, p_147809_4_ + 0.625F, 0.6F, var12);
/* 1275:     */     }
/* 1276:1519 */     this.uvRotateEast = 0;
/* 1277:1520 */     this.uvRotateWest = 0;
/* 1278:1521 */     this.uvRotateSouth = 0;
/* 1279:1522 */     this.uvRotateNorth = 0;
/* 1280:1523 */     this.uvRotateTop = 0;
/* 1281:1524 */     this.uvRotateBottom = 0;
/* 1282:1525 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 1283:1526 */     return true;
/* 1284:     */   }
/* 1285:     */   
/* 1286:     */   public boolean renderBlockLever(Block p_147790_1_, int p_147790_2_, int p_147790_3_, int p_147790_4_)
/* 1287:     */   {
/* 1288:1531 */     int var5 = this.blockAccess.getBlockMetadata(p_147790_2_, p_147790_3_, p_147790_4_);
/* 1289:1532 */     int var6 = var5 & 0x7;
/* 1290:1533 */     boolean var7 = (var5 & 0x8) > 0;
/* 1291:1534 */     Tessellator var8 = Tessellator.instance;
/* 1292:1535 */     boolean var9 = hasOverrideBlockTexture();
/* 1293:1537 */     if (!var9) {
/* 1294:1539 */       setOverrideBlockTexture(getBlockIcon(Blocks.cobblestone));
/* 1295:     */     }
/* 1296:1542 */     float var10 = 0.25F;
/* 1297:1543 */     float var11 = 0.1875F;
/* 1298:1544 */     float var12 = 0.1875F;
/* 1299:1546 */     if (var6 == 5) {
/* 1300:1548 */       setRenderBounds(0.5F - var11, 0.0D, 0.5F - var10, 0.5F + var11, var12, 0.5F + var10);
/* 1301:1550 */     } else if (var6 == 6) {
/* 1302:1552 */       setRenderBounds(0.5F - var10, 0.0D, 0.5F - var11, 0.5F + var10, var12, 0.5F + var11);
/* 1303:1554 */     } else if (var6 == 4) {
/* 1304:1556 */       setRenderBounds(0.5F - var11, 0.5F - var10, 1.0F - var12, 0.5F + var11, 0.5F + var10, 1.0D);
/* 1305:1558 */     } else if (var6 == 3) {
/* 1306:1560 */       setRenderBounds(0.5F - var11, 0.5F - var10, 0.0D, 0.5F + var11, 0.5F + var10, var12);
/* 1307:1562 */     } else if (var6 == 2) {
/* 1308:1564 */       setRenderBounds(1.0F - var12, 0.5F - var10, 0.5F - var11, 1.0D, 0.5F + var10, 0.5F + var11);
/* 1309:1566 */     } else if (var6 == 1) {
/* 1310:1568 */       setRenderBounds(0.0D, 0.5F - var10, 0.5F - var11, var12, 0.5F + var10, 0.5F + var11);
/* 1311:1570 */     } else if (var6 == 0) {
/* 1312:1572 */       setRenderBounds(0.5F - var10, 1.0F - var12, 0.5F - var11, 0.5F + var10, 1.0D, 0.5F + var11);
/* 1313:1574 */     } else if (var6 == 7) {
/* 1314:1576 */       setRenderBounds(0.5F - var11, 1.0F - var12, 0.5F - var10, 0.5F + var11, 1.0D, 0.5F + var10);
/* 1315:     */     }
/* 1316:1579 */     renderStandardBlock(p_147790_1_, p_147790_2_, p_147790_3_, p_147790_4_);
/* 1317:1581 */     if (!var9) {
/* 1318:1583 */       clearOverrideBlockTexture();
/* 1319:     */     }
/* 1320:1586 */     var8.setBrightness(p_147790_1_.getBlockBrightness(this.blockAccess, p_147790_2_, p_147790_3_, p_147790_4_));
/* 1321:1587 */     var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 1322:1588 */     IIcon var13 = getBlockIconFromSide(p_147790_1_, 0);
/* 1323:1590 */     if (hasOverrideBlockTexture()) {
/* 1324:1592 */       var13 = this.overrideBlockTexture;
/* 1325:     */     }
/* 1326:1595 */     double var14 = var13.getMinU();
/* 1327:1596 */     double var16 = var13.getMinV();
/* 1328:1597 */     double var18 = var13.getMaxU();
/* 1329:1598 */     double var20 = var13.getMaxV();
/* 1330:1599 */     Vec3[] var22 = new Vec3[8];
/* 1331:1600 */     float var23 = 0.0625F;
/* 1332:1601 */     float var24 = 0.0625F;
/* 1333:1602 */     float var25 = 0.625F;
/* 1334:1603 */     var22[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var23, 0.0D, -var24);
/* 1335:1604 */     var22[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, 0.0D, -var24);
/* 1336:1605 */     var22[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, 0.0D, var24);
/* 1337:1606 */     var22[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var23, 0.0D, var24);
/* 1338:1607 */     var22[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var23, var25, -var24);
/* 1339:1608 */     var22[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, var25, -var24);
/* 1340:1609 */     var22[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var23, var25, var24);
/* 1341:1610 */     var22[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var23, var25, var24);
/* 1342:1612 */     for (int var31 = 0; var31 < 8; var31++)
/* 1343:     */     {
/* 1344:1614 */       if (var7)
/* 1345:     */       {
/* 1346:1616 */         var22[var31].zCoord -= 0.0625D;
/* 1347:1617 */         var22[var31].rotateAroundX(0.6981317F);
/* 1348:     */       }
/* 1349:     */       else
/* 1350:     */       {
/* 1351:1621 */         var22[var31].zCoord += 0.0625D;
/* 1352:1622 */         var22[var31].rotateAroundX(-0.6981317F);
/* 1353:     */       }
/* 1354:1625 */       if ((var6 == 0) || (var6 == 7)) {
/* 1355:1627 */         var22[var31].rotateAroundZ(3.141593F);
/* 1356:     */       }
/* 1357:1630 */       if ((var6 == 6) || (var6 == 0)) {
/* 1358:1632 */         var22[var31].rotateAroundY(1.570796F);
/* 1359:     */       }
/* 1360:1635 */       if ((var6 > 0) && (var6 < 5))
/* 1361:     */       {
/* 1362:1637 */         var22[var31].yCoord -= 0.375D;
/* 1363:1638 */         var22[var31].rotateAroundX(1.570796F);
/* 1364:1640 */         if (var6 == 4) {
/* 1365:1642 */           var22[var31].rotateAroundY(0.0F);
/* 1366:     */         }
/* 1367:1645 */         if (var6 == 3) {
/* 1368:1647 */           var22[var31].rotateAroundY(3.141593F);
/* 1369:     */         }
/* 1370:1650 */         if (var6 == 2) {
/* 1371:1652 */           var22[var31].rotateAroundY(1.570796F);
/* 1372:     */         }
/* 1373:1655 */         if (var6 == 1) {
/* 1374:1657 */           var22[var31].rotateAroundY(-1.570796F);
/* 1375:     */         }
/* 1376:1660 */         var22[var31].xCoord += p_147790_2_ + 0.5D;
/* 1377:1661 */         var22[var31].yCoord += p_147790_3_ + 0.5F;
/* 1378:1662 */         var22[var31].zCoord += p_147790_4_ + 0.5D;
/* 1379:     */       }
/* 1380:1664 */       else if ((var6 != 0) && (var6 != 7))
/* 1381:     */       {
/* 1382:1666 */         var22[var31].xCoord += p_147790_2_ + 0.5D;
/* 1383:1667 */         var22[var31].yCoord += p_147790_3_ + 0.125F;
/* 1384:1668 */         var22[var31].zCoord += p_147790_4_ + 0.5D;
/* 1385:     */       }
/* 1386:     */       else
/* 1387:     */       {
/* 1388:1672 */         var22[var31].xCoord += p_147790_2_ + 0.5D;
/* 1389:1673 */         var22[var31].yCoord += p_147790_3_ + 0.875F;
/* 1390:1674 */         var22[var31].zCoord += p_147790_4_ + 0.5D;
/* 1391:     */       }
/* 1392:     */     }
/* 1393:1678 */     Vec3 var311 = null;
/* 1394:1679 */     Vec3 var27 = null;
/* 1395:1680 */     Vec3 var28 = null;
/* 1396:1681 */     Vec3 var29 = null;
/* 1397:1683 */     for (int var30 = 0; var30 < 6; var30++)
/* 1398:     */     {
/* 1399:1685 */       if (var30 == 0)
/* 1400:     */       {
/* 1401:1687 */         var14 = var13.getInterpolatedU(7.0D);
/* 1402:1688 */         var16 = var13.getInterpolatedV(6.0D);
/* 1403:1689 */         var18 = var13.getInterpolatedU(9.0D);
/* 1404:1690 */         var20 = var13.getInterpolatedV(8.0D);
/* 1405:     */       }
/* 1406:1692 */       else if (var30 == 2)
/* 1407:     */       {
/* 1408:1694 */         var14 = var13.getInterpolatedU(7.0D);
/* 1409:1695 */         var16 = var13.getInterpolatedV(6.0D);
/* 1410:1696 */         var18 = var13.getInterpolatedU(9.0D);
/* 1411:1697 */         var20 = var13.getMaxV();
/* 1412:     */       }
/* 1413:1700 */       if (var30 == 0)
/* 1414:     */       {
/* 1415:1702 */         var311 = var22[0];
/* 1416:1703 */         var27 = var22[1];
/* 1417:1704 */         var28 = var22[2];
/* 1418:1705 */         var29 = var22[3];
/* 1419:     */       }
/* 1420:1707 */       else if (var30 == 1)
/* 1421:     */       {
/* 1422:1709 */         var311 = var22[7];
/* 1423:1710 */         var27 = var22[6];
/* 1424:1711 */         var28 = var22[5];
/* 1425:1712 */         var29 = var22[4];
/* 1426:     */       }
/* 1427:1714 */       else if (var30 == 2)
/* 1428:     */       {
/* 1429:1716 */         var311 = var22[1];
/* 1430:1717 */         var27 = var22[0];
/* 1431:1718 */         var28 = var22[4];
/* 1432:1719 */         var29 = var22[5];
/* 1433:     */       }
/* 1434:1721 */       else if (var30 == 3)
/* 1435:     */       {
/* 1436:1723 */         var311 = var22[2];
/* 1437:1724 */         var27 = var22[1];
/* 1438:1725 */         var28 = var22[5];
/* 1439:1726 */         var29 = var22[6];
/* 1440:     */       }
/* 1441:1728 */       else if (var30 == 4)
/* 1442:     */       {
/* 1443:1730 */         var311 = var22[3];
/* 1444:1731 */         var27 = var22[2];
/* 1445:1732 */         var28 = var22[6];
/* 1446:1733 */         var29 = var22[7];
/* 1447:     */       }
/* 1448:1735 */       else if (var30 == 5)
/* 1449:     */       {
/* 1450:1737 */         var311 = var22[0];
/* 1451:1738 */         var27 = var22[3];
/* 1452:1739 */         var28 = var22[7];
/* 1453:1740 */         var29 = var22[4];
/* 1454:     */       }
/* 1455:1743 */       var8.addVertexWithUV(var311.xCoord, var311.yCoord, var311.zCoord, var14, var20);
/* 1456:1744 */       var8.addVertexWithUV(var27.xCoord, var27.yCoord, var27.zCoord, var18, var20);
/* 1457:1745 */       var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var18, var16);
/* 1458:1746 */       var8.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var14, var16);
/* 1459:     */     }
/* 1460:1749 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147790_2_, p_147790_3_, p_147790_4_))) {
/* 1461:1751 */       renderSnow(p_147790_2_, p_147790_3_, p_147790_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 1462:     */     }
/* 1463:1754 */     return true;
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public boolean renderBlockTripWireSource(Block p_147723_1_, int p_147723_2_, int p_147723_3_, int p_147723_4_)
/* 1467:     */   {
/* 1468:1759 */     Tessellator var5 = Tessellator.instance;
/* 1469:1760 */     int var6 = this.blockAccess.getBlockMetadata(p_147723_2_, p_147723_3_, p_147723_4_);
/* 1470:1761 */     int var7 = var6 & 0x3;
/* 1471:1762 */     boolean var8 = (var6 & 0x4) == 4;
/* 1472:1763 */     boolean var9 = (var6 & 0x8) == 8;
/* 1473:1764 */     boolean var10 = !World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147723_2_, p_147723_3_ - 1, p_147723_4_);
/* 1474:1765 */     boolean var11 = hasOverrideBlockTexture();
/* 1475:1767 */     if (!var11) {
/* 1476:1769 */       setOverrideBlockTexture(getBlockIcon(Blocks.planks));
/* 1477:     */     }
/* 1478:1772 */     float var12 = 0.25F;
/* 1479:1773 */     float var13 = 0.125F;
/* 1480:1774 */     float var14 = 0.125F;
/* 1481:1775 */     float var15 = 0.3F - var12;
/* 1482:1776 */     float var16 = 0.3F + var12;
/* 1483:1778 */     if (var7 == 2) {
/* 1484:1780 */       setRenderBounds(0.5F - var13, var15, 1.0F - var14, 0.5F + var13, var16, 1.0D);
/* 1485:1782 */     } else if (var7 == 0) {
/* 1486:1784 */       setRenderBounds(0.5F - var13, var15, 0.0D, 0.5F + var13, var16, var14);
/* 1487:1786 */     } else if (var7 == 1) {
/* 1488:1788 */       setRenderBounds(1.0F - var14, var15, 0.5F - var13, 1.0D, var16, 0.5F + var13);
/* 1489:1790 */     } else if (var7 == 3) {
/* 1490:1792 */       setRenderBounds(0.0D, var15, 0.5F - var13, var14, var16, 0.5F + var13);
/* 1491:     */     }
/* 1492:1795 */     renderStandardBlock(p_147723_1_, p_147723_2_, p_147723_3_, p_147723_4_);
/* 1493:1797 */     if (!var11) {
/* 1494:1799 */       clearOverrideBlockTexture();
/* 1495:     */     }
/* 1496:1802 */     var5.setBrightness(p_147723_1_.getBlockBrightness(this.blockAccess, p_147723_2_, p_147723_3_, p_147723_4_));
/* 1497:1803 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 1498:1804 */     IIcon var17 = getBlockIconFromSide(p_147723_1_, 0);
/* 1499:1806 */     if (hasOverrideBlockTexture()) {
/* 1500:1808 */       var17 = this.overrideBlockTexture;
/* 1501:     */     }
/* 1502:1811 */     double var18 = var17.getMinU();
/* 1503:1812 */     double var20 = var17.getMinV();
/* 1504:1813 */     double var22 = var17.getMaxU();
/* 1505:1814 */     double var24 = var17.getMaxV();
/* 1506:1815 */     Vec3[] var26 = new Vec3[8];
/* 1507:1816 */     float var27 = 0.046875F;
/* 1508:1817 */     float var28 = 0.046875F;
/* 1509:1818 */     float var29 = 0.3125F;
/* 1510:1819 */     var26[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var27, 0.0D, -var28);
/* 1511:1820 */     var26[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, 0.0D, -var28);
/* 1512:1821 */     var26[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, 0.0D, var28);
/* 1513:1822 */     var26[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var27, 0.0D, var28);
/* 1514:1823 */     var26[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var27, var29, -var28);
/* 1515:1824 */     var26[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, var29, -var28);
/* 1516:1825 */     var26[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var27, var29, var28);
/* 1517:1826 */     var26[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var27, var29, var28);
/* 1518:1828 */     for (int var60 = 0; var60 < 8; var60++)
/* 1519:     */     {
/* 1520:1830 */       var26[var60].zCoord += 0.0625D;
/* 1521:1832 */       if (var9)
/* 1522:     */       {
/* 1523:1834 */         var26[var60].rotateAroundX(0.5235988F);
/* 1524:1835 */         var26[var60].yCoord -= 0.4375D;
/* 1525:     */       }
/* 1526:1837 */       else if (var8)
/* 1527:     */       {
/* 1528:1839 */         var26[var60].rotateAroundX(0.08726647F);
/* 1529:1840 */         var26[var60].yCoord -= 0.4375D;
/* 1530:     */       }
/* 1531:     */       else
/* 1532:     */       {
/* 1533:1844 */         var26[var60].rotateAroundX(-0.6981317F);
/* 1534:1845 */         var26[var60].yCoord -= 0.375D;
/* 1535:     */       }
/* 1536:1848 */       var26[var60].rotateAroundX(1.570796F);
/* 1537:1850 */       if (var7 == 2) {
/* 1538:1852 */         var26[var60].rotateAroundY(0.0F);
/* 1539:     */       }
/* 1540:1855 */       if (var7 == 0) {
/* 1541:1857 */         var26[var60].rotateAroundY(3.141593F);
/* 1542:     */       }
/* 1543:1860 */       if (var7 == 1) {
/* 1544:1862 */         var26[var60].rotateAroundY(1.570796F);
/* 1545:     */       }
/* 1546:1865 */       if (var7 == 3) {
/* 1547:1867 */         var26[var60].rotateAroundY(-1.570796F);
/* 1548:     */       }
/* 1549:1870 */       var26[var60].xCoord += p_147723_2_ + 0.5D;
/* 1550:1871 */       var26[var60].yCoord += p_147723_3_ + 0.3125F;
/* 1551:1872 */       var26[var60].zCoord += p_147723_4_ + 0.5D;
/* 1552:     */     }
/* 1553:1875 */     Vec3 var601 = null;
/* 1554:1876 */     Vec3 var31 = null;
/* 1555:1877 */     Vec3 var32 = null;
/* 1556:1878 */     Vec3 var33 = null;
/* 1557:1879 */     byte var34 = 7;
/* 1558:1880 */     byte var35 = 9;
/* 1559:1881 */     byte var36 = 9;
/* 1560:1882 */     byte var37 = 16;
/* 1561:1884 */     for (int var61 = 0; var61 < 6; var61++)
/* 1562:     */     {
/* 1563:1886 */       if (var61 == 0)
/* 1564:     */       {
/* 1565:1888 */         var601 = var26[0];
/* 1566:1889 */         var31 = var26[1];
/* 1567:1890 */         var32 = var26[2];
/* 1568:1891 */         var33 = var26[3];
/* 1569:1892 */         var18 = var17.getInterpolatedU(var34);
/* 1570:1893 */         var20 = var17.getInterpolatedV(var36);
/* 1571:1894 */         var22 = var17.getInterpolatedU(var35);
/* 1572:1895 */         var24 = var17.getInterpolatedV(var36 + 2);
/* 1573:     */       }
/* 1574:1897 */       else if (var61 == 1)
/* 1575:     */       {
/* 1576:1899 */         var601 = var26[7];
/* 1577:1900 */         var31 = var26[6];
/* 1578:1901 */         var32 = var26[5];
/* 1579:1902 */         var33 = var26[4];
/* 1580:     */       }
/* 1581:1904 */       else if (var61 == 2)
/* 1582:     */       {
/* 1583:1906 */         var601 = var26[1];
/* 1584:1907 */         var31 = var26[0];
/* 1585:1908 */         var32 = var26[4];
/* 1586:1909 */         var33 = var26[5];
/* 1587:1910 */         var18 = var17.getInterpolatedU(var34);
/* 1588:1911 */         var20 = var17.getInterpolatedV(var36);
/* 1589:1912 */         var22 = var17.getInterpolatedU(var35);
/* 1590:1913 */         var24 = var17.getInterpolatedV(var37);
/* 1591:     */       }
/* 1592:1915 */       else if (var61 == 3)
/* 1593:     */       {
/* 1594:1917 */         var601 = var26[2];
/* 1595:1918 */         var31 = var26[1];
/* 1596:1919 */         var32 = var26[5];
/* 1597:1920 */         var33 = var26[6];
/* 1598:     */       }
/* 1599:1922 */       else if (var61 == 4)
/* 1600:     */       {
/* 1601:1924 */         var601 = var26[3];
/* 1602:1925 */         var31 = var26[2];
/* 1603:1926 */         var32 = var26[6];
/* 1604:1927 */         var33 = var26[7];
/* 1605:     */       }
/* 1606:1929 */       else if (var61 == 5)
/* 1607:     */       {
/* 1608:1931 */         var601 = var26[0];
/* 1609:1932 */         var31 = var26[3];
/* 1610:1933 */         var32 = var26[7];
/* 1611:1934 */         var33 = var26[4];
/* 1612:     */       }
/* 1613:1937 */       var5.addVertexWithUV(var601.xCoord, var601.yCoord, var601.zCoord, var18, var24);
/* 1614:1938 */       var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var22, var24);
/* 1615:1939 */       var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var22, var20);
/* 1616:1940 */       var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var18, var20);
/* 1617:     */     }
/* 1618:1943 */     float var611 = 0.09375F;
/* 1619:1944 */     float var39 = 0.09375F;
/* 1620:1945 */     float var40 = 0.03125F;
/* 1621:1946 */     var26[0] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var611, 0.0D, -var39);
/* 1622:1947 */     var26[1] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, 0.0D, -var39);
/* 1623:1948 */     var26[2] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, 0.0D, var39);
/* 1624:1949 */     var26[3] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var611, 0.0D, var39);
/* 1625:1950 */     var26[4] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var611, var40, -var39);
/* 1626:1951 */     var26[5] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, var40, -var39);
/* 1627:1952 */     var26[6] = this.blockAccess.getWorldVec3Pool().getVecFromPool(var611, var40, var39);
/* 1628:1953 */     var26[7] = this.blockAccess.getWorldVec3Pool().getVecFromPool(-var611, var40, var39);
/* 1629:1955 */     for (int var62 = 0; var62 < 8; var62++)
/* 1630:     */     {
/* 1631:1957 */       var26[var62].zCoord += 0.21875D;
/* 1632:1959 */       if (var9)
/* 1633:     */       {
/* 1634:1961 */         var26[var62].yCoord -= 0.09375D;
/* 1635:1962 */         var26[var62].zCoord -= 0.1625D;
/* 1636:1963 */         var26[var62].rotateAroundX(0.0F);
/* 1637:     */       }
/* 1638:1965 */       else if (var8)
/* 1639:     */       {
/* 1640:1967 */         var26[var62].yCoord += 0.015625D;
/* 1641:1968 */         var26[var62].zCoord -= 0.171875D;
/* 1642:1969 */         var26[var62].rotateAroundX(0.1745329F);
/* 1643:     */       }
/* 1644:     */       else
/* 1645:     */       {
/* 1646:1973 */         var26[var62].rotateAroundX(0.8726646F);
/* 1647:     */       }
/* 1648:1976 */       if (var7 == 2) {
/* 1649:1978 */         var26[var62].rotateAroundY(0.0F);
/* 1650:     */       }
/* 1651:1981 */       if (var7 == 0) {
/* 1652:1983 */         var26[var62].rotateAroundY(3.141593F);
/* 1653:     */       }
/* 1654:1986 */       if (var7 == 1) {
/* 1655:1988 */         var26[var62].rotateAroundY(1.570796F);
/* 1656:     */       }
/* 1657:1991 */       if (var7 == 3) {
/* 1658:1993 */         var26[var62].rotateAroundY(-1.570796F);
/* 1659:     */       }
/* 1660:1996 */       var26[var62].xCoord += p_147723_2_ + 0.5D;
/* 1661:1997 */       var26[var62].yCoord += p_147723_3_ + 0.3125F;
/* 1662:1998 */       var26[var62].zCoord += p_147723_4_ + 0.5D;
/* 1663:     */     }
/* 1664:2001 */     byte var621 = 5;
/* 1665:2002 */     byte var42 = 11;
/* 1666:2003 */     byte var43 = 3;
/* 1667:2004 */     byte var44 = 9;
/* 1668:2006 */     for (int var63 = 0; var63 < 6; var63++)
/* 1669:     */     {
/* 1670:2008 */       if (var63 == 0)
/* 1671:     */       {
/* 1672:2010 */         var601 = var26[0];
/* 1673:2011 */         var31 = var26[1];
/* 1674:2012 */         var32 = var26[2];
/* 1675:2013 */         var33 = var26[3];
/* 1676:2014 */         var18 = var17.getInterpolatedU(var621);
/* 1677:2015 */         var20 = var17.getInterpolatedV(var43);
/* 1678:2016 */         var22 = var17.getInterpolatedU(var42);
/* 1679:2017 */         var24 = var17.getInterpolatedV(var44);
/* 1680:     */       }
/* 1681:2019 */       else if (var63 == 1)
/* 1682:     */       {
/* 1683:2021 */         var601 = var26[7];
/* 1684:2022 */         var31 = var26[6];
/* 1685:2023 */         var32 = var26[5];
/* 1686:2024 */         var33 = var26[4];
/* 1687:     */       }
/* 1688:2026 */       else if (var63 == 2)
/* 1689:     */       {
/* 1690:2028 */         var601 = var26[1];
/* 1691:2029 */         var31 = var26[0];
/* 1692:2030 */         var32 = var26[4];
/* 1693:2031 */         var33 = var26[5];
/* 1694:2032 */         var18 = var17.getInterpolatedU(var621);
/* 1695:2033 */         var20 = var17.getInterpolatedV(var43);
/* 1696:2034 */         var22 = var17.getInterpolatedU(var42);
/* 1697:2035 */         var24 = var17.getInterpolatedV(var43 + 2);
/* 1698:     */       }
/* 1699:2037 */       else if (var63 == 3)
/* 1700:     */       {
/* 1701:2039 */         var601 = var26[2];
/* 1702:2040 */         var31 = var26[1];
/* 1703:2041 */         var32 = var26[5];
/* 1704:2042 */         var33 = var26[6];
/* 1705:     */       }
/* 1706:2044 */       else if (var63 == 4)
/* 1707:     */       {
/* 1708:2046 */         var601 = var26[3];
/* 1709:2047 */         var31 = var26[2];
/* 1710:2048 */         var32 = var26[6];
/* 1711:2049 */         var33 = var26[7];
/* 1712:     */       }
/* 1713:2051 */       else if (var63 == 5)
/* 1714:     */       {
/* 1715:2053 */         var601 = var26[0];
/* 1716:2054 */         var31 = var26[3];
/* 1717:2055 */         var32 = var26[7];
/* 1718:2056 */         var33 = var26[4];
/* 1719:     */       }
/* 1720:2059 */       var5.addVertexWithUV(var601.xCoord, var601.yCoord, var601.zCoord, var18, var24);
/* 1721:2060 */       var5.addVertexWithUV(var31.xCoord, var31.yCoord, var31.zCoord, var22, var24);
/* 1722:2061 */       var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var22, var20);
/* 1723:2062 */       var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var18, var20);
/* 1724:     */     }
/* 1725:2065 */     if (var8)
/* 1726:     */     {
/* 1727:2067 */       double var631 = var26[0].yCoord;
/* 1728:2068 */       float var47 = 0.03125F;
/* 1729:2069 */       float var48 = 0.5F - var47 / 2.0F;
/* 1730:2070 */       float var49 = var48 + var47;
/* 1731:2071 */       double var50 = var17.getMinU();
/* 1732:2072 */       double var52 = var17.getInterpolatedV(var8 ? 2.0D : 0.0D);
/* 1733:2073 */       double var54 = var17.getMaxU();
/* 1734:2074 */       double var56 = var17.getInterpolatedV(var8 ? 4.0D : 2.0D);
/* 1735:2075 */       double var58 = (var10 ? 3.5F : 1.5F) / 16.0D;
/* 1736:2076 */       var5.setColorOpaque_F(0.75F, 0.75F, 0.75F);
/* 1737:2078 */       if (var7 == 2)
/* 1738:     */       {
/* 1739:2080 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_ + 0.25D, var50, var52);
/* 1740:2081 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_ + 0.25D, var50, var56);
/* 1741:2082 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_, var54, var56);
/* 1742:2083 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_, var54, var52);
/* 1743:2084 */         var5.addVertexWithUV(p_147723_2_ + var48, var631, p_147723_4_ + 0.5D, var50, var52);
/* 1744:2085 */         var5.addVertexWithUV(p_147723_2_ + var49, var631, p_147723_4_ + 0.5D, var50, var56);
/* 1745:2086 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_ + 0.25D, var54, var56);
/* 1746:2087 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_ + 0.25D, var54, var52);
/* 1747:     */       }
/* 1748:2089 */       else if (var7 == 0)
/* 1749:     */       {
/* 1750:2091 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_ + 0.75D, var50, var52);
/* 1751:2092 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_ + 0.75D, var50, var56);
/* 1752:2093 */         var5.addVertexWithUV(p_147723_2_ + var49, var631, p_147723_4_ + 0.5D, var54, var56);
/* 1753:2094 */         var5.addVertexWithUV(p_147723_2_ + var48, var631, p_147723_4_ + 0.5D, var54, var52);
/* 1754:2095 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_ + 1, var50, var52);
/* 1755:2096 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_ + 1, var50, var56);
/* 1756:2097 */         var5.addVertexWithUV(p_147723_2_ + var49, p_147723_3_ + var58, p_147723_4_ + 0.75D, var54, var56);
/* 1757:2098 */         var5.addVertexWithUV(p_147723_2_ + var48, p_147723_3_ + var58, p_147723_4_ + 0.75D, var54, var52);
/* 1758:     */       }
/* 1759:2100 */       else if (var7 == 1)
/* 1760:     */       {
/* 1761:2102 */         var5.addVertexWithUV(p_147723_2_, p_147723_3_ + var58, p_147723_4_ + var49, var50, var56);
/* 1762:2103 */         var5.addVertexWithUV(p_147723_2_ + 0.25D, p_147723_3_ + var58, p_147723_4_ + var49, var54, var56);
/* 1763:2104 */         var5.addVertexWithUV(p_147723_2_ + 0.25D, p_147723_3_ + var58, p_147723_4_ + var48, var54, var52);
/* 1764:2105 */         var5.addVertexWithUV(p_147723_2_, p_147723_3_ + var58, p_147723_4_ + var48, var50, var52);
/* 1765:2106 */         var5.addVertexWithUV(p_147723_2_ + 0.25D, p_147723_3_ + var58, p_147723_4_ + var49, var50, var56);
/* 1766:2107 */         var5.addVertexWithUV(p_147723_2_ + 0.5D, var631, p_147723_4_ + var49, var54, var56);
/* 1767:2108 */         var5.addVertexWithUV(p_147723_2_ + 0.5D, var631, p_147723_4_ + var48, var54, var52);
/* 1768:2109 */         var5.addVertexWithUV(p_147723_2_ + 0.25D, p_147723_3_ + var58, p_147723_4_ + var48, var50, var52);
/* 1769:     */       }
/* 1770:     */       else
/* 1771:     */       {
/* 1772:2113 */         var5.addVertexWithUV(p_147723_2_ + 0.5D, var631, p_147723_4_ + var49, var50, var56);
/* 1773:2114 */         var5.addVertexWithUV(p_147723_2_ + 0.75D, p_147723_3_ + var58, p_147723_4_ + var49, var54, var56);
/* 1774:2115 */         var5.addVertexWithUV(p_147723_2_ + 0.75D, p_147723_3_ + var58, p_147723_4_ + var48, var54, var52);
/* 1775:2116 */         var5.addVertexWithUV(p_147723_2_ + 0.5D, var631, p_147723_4_ + var48, var50, var52);
/* 1776:2117 */         var5.addVertexWithUV(p_147723_2_ + 0.75D, p_147723_3_ + var58, p_147723_4_ + var49, var50, var56);
/* 1777:2118 */         var5.addVertexWithUV(p_147723_2_ + 1, p_147723_3_ + var58, p_147723_4_ + var49, var54, var56);
/* 1778:2119 */         var5.addVertexWithUV(p_147723_2_ + 1, p_147723_3_ + var58, p_147723_4_ + var48, var54, var52);
/* 1779:2120 */         var5.addVertexWithUV(p_147723_2_ + 0.75D, p_147723_3_ + var58, p_147723_4_ + var48, var50, var52);
/* 1780:     */       }
/* 1781:     */     }
/* 1782:2124 */     return true;
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   public boolean renderBlockTripWire(Block p_147756_1_, int p_147756_2_, int p_147756_3_, int p_147756_4_)
/* 1786:     */   {
/* 1787:2129 */     Tessellator var5 = Tessellator.instance;
/* 1788:2130 */     IIcon var6 = getBlockIconFromSide(p_147756_1_, 0);
/* 1789:2131 */     int var7 = this.blockAccess.getBlockMetadata(p_147756_2_, p_147756_3_, p_147756_4_);
/* 1790:2132 */     boolean var8 = (var7 & 0x4) == 4;
/* 1791:2133 */     boolean var9 = (var7 & 0x2) == 2;
/* 1792:2135 */     if (hasOverrideBlockTexture()) {
/* 1793:2137 */       var6 = this.overrideBlockTexture;
/* 1794:     */     }
/* 1795:2140 */     var5.setBrightness(p_147756_1_.getBlockBrightness(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_));
/* 1796:2141 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 1797:2142 */     double var10 = var6.getMinU();
/* 1798:2143 */     double var12 = var6.getInterpolatedV(var8 ? 2.0D : 0.0D);
/* 1799:2144 */     double var14 = var6.getMaxU();
/* 1800:2145 */     double var16 = var6.getInterpolatedV(var8 ? 4.0D : 2.0D);
/* 1801:2146 */     double var18 = (var9 ? 3.5F : 1.5F) / 16.0D;
/* 1802:2147 */     boolean var20 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 1);
/* 1803:2148 */     boolean var21 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 3);
/* 1804:2149 */     boolean var22 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 2);
/* 1805:2150 */     boolean var23 = BlockTripWire.func_150139_a(this.blockAccess, p_147756_2_, p_147756_3_, p_147756_4_, var7, 0);
/* 1806:2151 */     float var24 = 0.03125F;
/* 1807:2152 */     float var25 = 0.5F - var24 / 2.0F;
/* 1808:2153 */     float var26 = var25 + var24;
/* 1809:2155 */     if ((!var22) && (!var21) && (!var23) && (!var20))
/* 1810:     */     {
/* 1811:2157 */       var22 = true;
/* 1812:2158 */       var23 = true;
/* 1813:     */     }
/* 1814:2161 */     if (var22)
/* 1815:     */     {
/* 1816:2163 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.25D, var10, var12);
/* 1817:2164 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.25D, var10, var16);
/* 1818:2165 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_, var14, var16);
/* 1819:2166 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_, var14, var12);
/* 1820:2167 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_, var14, var12);
/* 1821:2168 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_, var14, var16);
/* 1822:2169 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.25D, var10, var16);
/* 1823:2170 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.25D, var10, var12);
/* 1824:     */     }
/* 1825:2173 */     if ((var22) || ((var23) && (!var21) && (!var20)))
/* 1826:     */     {
/* 1827:2175 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.5D, var10, var12);
/* 1828:2176 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.5D, var10, var16);
/* 1829:2177 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.25D, var14, var16);
/* 1830:2178 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.25D, var14, var12);
/* 1831:2179 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.25D, var14, var12);
/* 1832:2180 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.25D, var14, var16);
/* 1833:2181 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.5D, var10, var16);
/* 1834:2182 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.5D, var10, var12);
/* 1835:     */     }
/* 1836:2185 */     if ((var23) || ((var22) && (!var21) && (!var20)))
/* 1837:     */     {
/* 1838:2187 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.75D, var10, var12);
/* 1839:2188 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.75D, var10, var16);
/* 1840:2189 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.5D, var14, var16);
/* 1841:2190 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.5D, var14, var12);
/* 1842:2191 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.5D, var14, var12);
/* 1843:2192 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.5D, var14, var16);
/* 1844:2193 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.75D, var10, var16);
/* 1845:2194 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.75D, var10, var12);
/* 1846:     */     }
/* 1847:2197 */     if (var23)
/* 1848:     */     {
/* 1849:2199 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 1, var10, var12);
/* 1850:2200 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 1, var10, var16);
/* 1851:2201 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.75D, var14, var16);
/* 1852:2202 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.75D, var14, var12);
/* 1853:2203 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 0.75D, var14, var12);
/* 1854:2204 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 0.75D, var14, var16);
/* 1855:2205 */       var5.addVertexWithUV(p_147756_2_ + var26, p_147756_3_ + var18, p_147756_4_ + 1, var10, var16);
/* 1856:2206 */       var5.addVertexWithUV(p_147756_2_ + var25, p_147756_3_ + var18, p_147756_4_ + 1, var10, var12);
/* 1857:     */     }
/* 1858:2209 */     if (var20)
/* 1859:     */     {
/* 1860:2211 */       var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1861:2212 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1862:2213 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1863:2214 */       var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1864:2215 */       var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1865:2216 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1866:2217 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1867:2218 */       var5.addVertexWithUV(p_147756_2_, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1868:     */     }
/* 1869:2221 */     if ((var20) || ((var21) && (!var22) && (!var23)))
/* 1870:     */     {
/* 1871:2223 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1872:2224 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1873:2225 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1874:2226 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1875:2227 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1876:2228 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1877:2229 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1878:2230 */       var5.addVertexWithUV(p_147756_2_ + 0.25D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1879:     */     }
/* 1880:2233 */     if ((var21) || ((var20) && (!var22) && (!var23)))
/* 1881:     */     {
/* 1882:2235 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1883:2236 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1884:2237 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1885:2238 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1886:2239 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1887:2240 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1888:2241 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1889:2242 */       var5.addVertexWithUV(p_147756_2_ + 0.5D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1890:     */     }
/* 1891:2245 */     if (var21)
/* 1892:     */     {
/* 1893:2247 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1894:2248 */       var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1895:2249 */       var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1896:2250 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1897:2251 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var25, var10, var12);
/* 1898:2252 */       var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var18, p_147756_4_ + var25, var14, var12);
/* 1899:2253 */       var5.addVertexWithUV(p_147756_2_ + 1, p_147756_3_ + var18, p_147756_4_ + var26, var14, var16);
/* 1900:2254 */       var5.addVertexWithUV(p_147756_2_ + 0.75D, p_147756_3_ + var18, p_147756_4_ + var26, var10, var16);
/* 1901:     */     }
/* 1902:2257 */     return true;
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public boolean renderBlockFire(BlockFire p_147801_1_, int p_147801_2_, int p_147801_3_, int p_147801_4_)
/* 1906:     */   {
/* 1907:2262 */     Tessellator var5 = Tessellator.instance;
/* 1908:2263 */     IIcon var6 = p_147801_1_.func_149840_c(0);
/* 1909:2264 */     IIcon var7 = p_147801_1_.func_149840_c(1);
/* 1910:2265 */     IIcon var8 = var6;
/* 1911:2267 */     if (hasOverrideBlockTexture()) {
/* 1912:2269 */       var8 = this.overrideBlockTexture;
/* 1913:     */     }
/* 1914:2272 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 1915:2273 */     var5.setBrightness(p_147801_1_.getBlockBrightness(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_));
/* 1916:2274 */     double var9 = var8.getMinU();
/* 1917:2275 */     double var11 = var8.getMinV();
/* 1918:2276 */     double var13 = var8.getMaxU();
/* 1919:2277 */     double var15 = var8.getMaxV();
/* 1920:2278 */     float var17 = 1.4F;
/* 1921:2287 */     if ((!World.doesBlockHaveSolidTopSurface(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_)) && (!Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ - 1, p_147801_4_)))
/* 1922:     */     {
/* 1923:2289 */       float var36 = 0.2F;
/* 1924:2290 */       float var19 = 0.0625F;
/* 1925:2292 */       if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 0x1) == 1)
/* 1926:     */       {
/* 1927:2294 */         var9 = var7.getMinU();
/* 1928:2295 */         var11 = var7.getMinV();
/* 1929:2296 */         var13 = var7.getMaxU();
/* 1930:2297 */         var15 = var7.getMaxV();
/* 1931:     */       }
/* 1932:2300 */       if ((p_147801_2_ / 2 + p_147801_3_ / 2 + p_147801_4_ / 2 & 0x1) == 1)
/* 1933:     */       {
/* 1934:2302 */         double var20 = var13;
/* 1935:2303 */         var13 = var9;
/* 1936:2304 */         var9 = var20;
/* 1937:     */       }
/* 1938:2307 */       if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ - 1, p_147801_3_, p_147801_4_))
/* 1939:     */       {
/* 1940:2309 */         var5.addVertexWithUV(p_147801_2_ + var36, p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
/* 1941:2310 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1, var13, var15);
/* 1942:2311 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1943:2312 */         var5.addVertexWithUV(p_147801_2_ + var36, p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
/* 1944:2313 */         var5.addVertexWithUV(p_147801_2_ + var36, p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
/* 1945:2314 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1946:2315 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1, var13, var15);
/* 1947:2316 */         var5.addVertexWithUV(p_147801_2_ + var36, p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
/* 1948:     */       }
/* 1949:2319 */       if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_ + 1, p_147801_3_, p_147801_4_))
/* 1950:     */       {
/* 1951:2321 */         var5.addVertexWithUV(p_147801_2_ + 1 - var36, p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
/* 1952:2322 */         var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1953:2323 */         var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1, var13, var15);
/* 1954:2324 */         var5.addVertexWithUV(p_147801_2_ + 1 - var36, p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
/* 1955:2325 */         var5.addVertexWithUV(p_147801_2_ + 1 - var36, p_147801_3_ + var17 + var19, p_147801_4_ + 1, var13, var11);
/* 1956:2326 */         var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1, var13, var15);
/* 1957:2327 */         var5.addVertexWithUV(p_147801_2_ + 1 - 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1958:2328 */         var5.addVertexWithUV(p_147801_2_ + 1 - var36, p_147801_3_ + var17 + var19, p_147801_4_ + 0, var9, var11);
/* 1959:     */       }
/* 1960:2331 */       if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ - 1))
/* 1961:     */       {
/* 1962:2333 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17 + var19, p_147801_4_ + var36, var13, var11);
/* 1963:2334 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var13, var15);
/* 1964:2335 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1965:2336 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17 + var19, p_147801_4_ + var36, var9, var11);
/* 1966:2337 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17 + var19, p_147801_4_ + var36, var9, var11);
/* 1967:2338 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var9, var15);
/* 1968:2339 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 0, var13, var15);
/* 1969:2340 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17 + var19, p_147801_4_ + var36, var13, var11);
/* 1970:     */       }
/* 1971:2343 */       if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_, p_147801_4_ + 1))
/* 1972:     */       {
/* 1973:2345 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17 + var19, p_147801_4_ + 1 - var36, var9, var11);
/* 1974:2346 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var19, p_147801_4_ + 1 - 0, var9, var15);
/* 1975:2347 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1 - 0, var13, var15);
/* 1976:2348 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17 + var19, p_147801_4_ + 1 - var36, var13, var11);
/* 1977:2349 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17 + var19, p_147801_4_ + 1 - var36, var13, var11);
/* 1978:2350 */         var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0 + var19, p_147801_4_ + 1 - 0, var13, var15);
/* 1979:2351 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0 + var19, p_147801_4_ + 1 - 0, var9, var15);
/* 1980:2352 */         var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17 + var19, p_147801_4_ + 1 - var36, var9, var11);
/* 1981:     */       }
/* 1982:2355 */       if (Blocks.fire.func_149844_e(this.blockAccess, p_147801_2_, p_147801_3_ + 1, p_147801_4_))
/* 1983:     */       {
/* 1984:2357 */         double var20 = p_147801_2_ + 0.5D + 0.5D;
/* 1985:2358 */         double var22 = p_147801_2_ + 0.5D - 0.5D;
/* 1986:2359 */         double var24 = p_147801_4_ + 0.5D + 0.5D;
/* 1987:2360 */         double var26 = p_147801_4_ + 0.5D - 0.5D;
/* 1988:2361 */         double var28 = p_147801_2_ + 0.5D - 0.5D;
/* 1989:2362 */         double var30 = p_147801_2_ + 0.5D + 0.5D;
/* 1990:2363 */         double var32 = p_147801_4_ + 0.5D - 0.5D;
/* 1991:2364 */         double var34 = p_147801_4_ + 0.5D + 0.5D;
/* 1992:2365 */         var9 = var6.getMinU();
/* 1993:2366 */         var11 = var6.getMinV();
/* 1994:2367 */         var13 = var6.getMaxU();
/* 1995:2368 */         var15 = var6.getMaxV();
/* 1996:2369 */         p_147801_3_++;
/* 1997:2370 */         var17 = -0.2F;
/* 1998:2372 */         if ((p_147801_2_ + p_147801_3_ + p_147801_4_ & 0x1) == 0)
/* 1999:     */         {
/* 2000:2374 */           var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
/* 2001:2375 */           var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
/* 2002:2376 */           var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
/* 2003:2377 */           var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
/* 2004:2378 */           var9 = var7.getMinU();
/* 2005:2379 */           var11 = var7.getMinV();
/* 2006:2380 */           var13 = var7.getMaxU();
/* 2007:2381 */           var15 = var7.getMaxV();
/* 2008:2382 */           var5.addVertexWithUV(var30, p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
/* 2009:2383 */           var5.addVertexWithUV(var22, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
/* 2010:2384 */           var5.addVertexWithUV(var22, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
/* 2011:2385 */           var5.addVertexWithUV(var30, p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
/* 2012:     */         }
/* 2013:     */         else
/* 2014:     */         {
/* 2015:2389 */           var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var34, var13, var11);
/* 2016:2390 */           var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var26, var13, var15);
/* 2017:2391 */           var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var26, var9, var15);
/* 2018:2392 */           var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var34, var9, var11);
/* 2019:2393 */           var9 = var7.getMinU();
/* 2020:2394 */           var11 = var7.getMinV();
/* 2021:2395 */           var13 = var7.getMaxU();
/* 2022:2396 */           var15 = var7.getMaxV();
/* 2023:2397 */           var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var32, var13, var11);
/* 2024:2398 */           var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
/* 2025:2399 */           var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
/* 2026:2400 */           var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var32, var9, var11);
/* 2027:     */         }
/* 2028:     */       }
/* 2029:     */     }
/* 2030:     */     else
/* 2031:     */     {
/* 2032:2406 */       double var18 = p_147801_2_ + 0.5D + 0.2D;
/* 2033:2407 */       double var20 = p_147801_2_ + 0.5D - 0.2D;
/* 2034:2408 */       double var22 = p_147801_4_ + 0.5D + 0.2D;
/* 2035:2409 */       double var24 = p_147801_4_ + 0.5D - 0.2D;
/* 2036:2410 */       double var26 = p_147801_2_ + 0.5D - 0.3D;
/* 2037:2411 */       double var28 = p_147801_2_ + 0.5D + 0.3D;
/* 2038:2412 */       double var30 = p_147801_4_ + 0.5D - 0.3D;
/* 2039:2413 */       double var32 = p_147801_4_ + 0.5D + 0.3D;
/* 2040:2414 */       var5.addVertexWithUV(var26, p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
/* 2041:2415 */       var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
/* 2042:2416 */       var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
/* 2043:2417 */       var5.addVertexWithUV(var26, p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
/* 2044:2418 */       var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
/* 2045:2419 */       var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
/* 2046:2420 */       var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
/* 2047:2421 */       var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
/* 2048:2422 */       var9 = var7.getMinU();
/* 2049:2423 */       var11 = var7.getMinV();
/* 2050:2424 */       var13 = var7.getMaxU();
/* 2051:2425 */       var15 = var7.getMaxV();
/* 2052:2426 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var32, var13, var11);
/* 2053:2427 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
/* 2054:2428 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
/* 2055:2429 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var32, var9, var11);
/* 2056:2430 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var30, var13, var11);
/* 2057:2431 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var22, var13, var15);
/* 2058:2432 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var22, var9, var15);
/* 2059:2433 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var30, var9, var11);
/* 2060:2434 */       var18 = p_147801_2_ + 0.5D - 0.5D;
/* 2061:2435 */       var20 = p_147801_2_ + 0.5D + 0.5D;
/* 2062:2436 */       var22 = p_147801_4_ + 0.5D - 0.5D;
/* 2063:2437 */       var24 = p_147801_4_ + 0.5D + 0.5D;
/* 2064:2438 */       var26 = p_147801_2_ + 0.5D - 0.4D;
/* 2065:2439 */       var28 = p_147801_2_ + 0.5D + 0.4D;
/* 2066:2440 */       var30 = p_147801_4_ + 0.5D - 0.4D;
/* 2067:2441 */       var32 = p_147801_4_ + 0.5D + 0.4D;
/* 2068:2442 */       var5.addVertexWithUV(var26, p_147801_3_ + var17, p_147801_4_ + 0, var9, var11);
/* 2069:2443 */       var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 0, var9, var15);
/* 2070:2444 */       var5.addVertexWithUV(var18, p_147801_3_ + 0, p_147801_4_ + 1, var13, var15);
/* 2071:2445 */       var5.addVertexWithUV(var26, p_147801_3_ + var17, p_147801_4_ + 1, var13, var11);
/* 2072:2446 */       var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 1, var9, var11);
/* 2073:2447 */       var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 1, var9, var15);
/* 2074:2448 */       var5.addVertexWithUV(var20, p_147801_3_ + 0, p_147801_4_ + 0, var13, var15);
/* 2075:2449 */       var5.addVertexWithUV(var28, p_147801_3_ + var17, p_147801_4_ + 0, var13, var11);
/* 2076:2450 */       var9 = var6.getMinU();
/* 2077:2451 */       var11 = var6.getMinV();
/* 2078:2452 */       var13 = var6.getMaxU();
/* 2079:2453 */       var15 = var6.getMaxV();
/* 2080:2454 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var32, var9, var11);
/* 2081:2455 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var24, var9, var15);
/* 2082:2456 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var24, var13, var15);
/* 2083:2457 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var32, var13, var11);
/* 2084:2458 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + var17, var30, var9, var11);
/* 2085:2459 */       var5.addVertexWithUV(p_147801_2_ + 1, p_147801_3_ + 0, var22, var9, var15);
/* 2086:2460 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + 0, var22, var13, var15);
/* 2087:2461 */       var5.addVertexWithUV(p_147801_2_ + 0, p_147801_3_ + var17, var30, var13, var11);
/* 2088:     */     }
/* 2089:2464 */     return true;
/* 2090:     */   }
/* 2091:     */   
/* 2092:     */   public boolean renderBlockRedstoneWire(Block p_147788_1_, int p_147788_2_, int p_147788_3_, int p_147788_4_)
/* 2093:     */   {
/* 2094:2469 */     Tessellator var5 = Tessellator.instance;
/* 2095:2470 */     int var6 = this.blockAccess.getBlockMetadata(p_147788_2_, p_147788_3_, p_147788_4_);
/* 2096:2471 */     IIcon var7 = BlockRedstoneWire.func_150173_e("cross");
/* 2097:2472 */     IIcon var8 = BlockRedstoneWire.func_150173_e("line");
/* 2098:2473 */     IIcon var9 = BlockRedstoneWire.func_150173_e("cross_overlay");
/* 2099:2474 */     IIcon var10 = BlockRedstoneWire.func_150173_e("line_overlay");
/* 2100:2475 */     var5.setBrightness(p_147788_1_.getBlockBrightness(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_));
/* 2101:2476 */     float var11 = var6 / 15.0F;
/* 2102:2477 */     float var12 = var11 * 0.6F + 0.4F;
/* 2103:2479 */     if (var6 == 0) {
/* 2104:2481 */       var12 = 0.3F;
/* 2105:     */     }
/* 2106:2484 */     float var13 = var11 * var11 * 0.7F - 0.5F;
/* 2107:2485 */     float var14 = var11 * var11 * 0.6F - 0.7F;
/* 2108:2487 */     if (var13 < 0.0F) {
/* 2109:2489 */       var13 = 0.0F;
/* 2110:     */     }
/* 2111:2492 */     if (var14 < 0.0F) {
/* 2112:2494 */       var14 = 0.0F;
/* 2113:     */     }
/* 2114:2497 */     int rsColor = CustomColorizer.getRedstoneColor(var6);
/* 2115:2499 */     if (rsColor != -1)
/* 2116:     */     {
/* 2117:2501 */       int var15 = rsColor >> 16 & 0xFF;
/* 2118:2502 */       int green = rsColor >> 8 & 0xFF;
/* 2119:2503 */       int var17 = rsColor & 0xFF;
/* 2120:2504 */       var12 = var15 / 255.0F;
/* 2121:2505 */       var13 = green / 255.0F;
/* 2122:2506 */       var14 = var17 / 255.0F;
/* 2123:     */     }
/* 2124:2509 */     var5.setColorOpaque_F(var12, var13, var14);
/* 2125:2510 */     double var151 = 0.015625D;
/* 2126:2511 */     double var171 = 0.015625D;
/* 2127:2512 */     boolean var19 = (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_, p_147788_4_, 1)) || ((!this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ - 1, p_147788_4_, -1)));
/* 2128:2513 */     boolean var20 = (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_, p_147788_4_, 3)) || ((!this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ - 1, p_147788_4_, -1)));
/* 2129:2514 */     boolean var21 = (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ - 1, 2)) || ((!this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ - 1, -1)));
/* 2130:2515 */     boolean var22 = (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_, p_147788_4_ + 1, 0)) || ((!this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ - 1, p_147788_4_ + 1, -1)));
/* 2131:2517 */     if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube())
/* 2132:     */     {
/* 2133:2519 */       if ((this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_, -1))) {
/* 2134:2521 */         var19 = true;
/* 2135:     */       }
/* 2136:2524 */       if ((this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_, -1))) {
/* 2137:2526 */         var20 = true;
/* 2138:     */       }
/* 2139:2529 */       if ((this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1, -1))) {
/* 2140:2531 */         var21 = true;
/* 2141:     */       }
/* 2142:2534 */       if ((this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube()) && (BlockRedstoneWire.func_150174_f(this.blockAccess, p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1, -1))) {
/* 2143:2536 */         var22 = true;
/* 2144:     */       }
/* 2145:     */     }
/* 2146:2540 */     float var23 = p_147788_2_ + 0;
/* 2147:2541 */     float var24 = p_147788_2_ + 1;
/* 2148:2542 */     float var25 = p_147788_4_ + 0;
/* 2149:2543 */     float var26 = p_147788_4_ + 1;
/* 2150:2544 */     boolean var27 = false;
/* 2151:2546 */     if (((var19) || (var20)) && (!var21) && (!var22)) {
/* 2152:2548 */       var27 = true;
/* 2153:     */     }
/* 2154:2551 */     if (((var21) || (var22)) && (!var20) && (!var19)) {
/* 2155:2553 */       var27 = true;
/* 2156:     */     }
/* 2157:2556 */     if (!var27)
/* 2158:     */     {
/* 2159:2558 */       int var33 = 0;
/* 2160:2559 */       int var29 = 0;
/* 2161:2560 */       int var30 = 16;
/* 2162:2561 */       int var31 = 16;
/* 2163:2562 */       boolean var32 = true;
/* 2164:2564 */       if (!var19) {
/* 2165:2566 */         var23 += 0.3125F;
/* 2166:     */       }
/* 2167:2569 */       if (!var19) {
/* 2168:2571 */         var33 += 5;
/* 2169:     */       }
/* 2170:2574 */       if (!var20) {
/* 2171:2576 */         var24 -= 0.3125F;
/* 2172:     */       }
/* 2173:2579 */       if (!var20) {
/* 2174:2581 */         var30 -= 5;
/* 2175:     */       }
/* 2176:2584 */       if (!var21) {
/* 2177:2586 */         var25 += 0.3125F;
/* 2178:     */       }
/* 2179:2589 */       if (!var21) {
/* 2180:2591 */         var29 += 5;
/* 2181:     */       }
/* 2182:2594 */       if (!var22) {
/* 2183:2596 */         var26 -= 0.3125F;
/* 2184:     */       }
/* 2185:2599 */       if (!var22) {
/* 2186:2601 */         var31 -= 5;
/* 2187:     */       }
/* 2188:2604 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var7.getInterpolatedU(var30), var7.getInterpolatedV(var31));
/* 2189:2605 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var7.getInterpolatedU(var30), var7.getInterpolatedV(var29));
/* 2190:2606 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var7.getInterpolatedU(var33), var7.getInterpolatedV(var29));
/* 2191:2607 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var7.getInterpolatedU(var33), var7.getInterpolatedV(var31));
/* 2192:2608 */       var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2193:2609 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var9.getInterpolatedU(var30), var9.getInterpolatedV(var31));
/* 2194:2610 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var9.getInterpolatedU(var30), var9.getInterpolatedV(var29));
/* 2195:2611 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var9.getInterpolatedU(var33), var9.getInterpolatedV(var29));
/* 2196:2612 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var9.getInterpolatedU(var33), var9.getInterpolatedV(var31));
/* 2197:     */     }
/* 2198:2614 */     else if (var27)
/* 2199:     */     {
/* 2200:2616 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var8.getMaxU(), var8.getMaxV());
/* 2201:2617 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var8.getMaxU(), var8.getMinV());
/* 2202:2618 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var8.getMinU(), var8.getMinV());
/* 2203:2619 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var8.getMinU(), var8.getMaxV());
/* 2204:2620 */       var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2205:2621 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var10.getMaxU(), var10.getMaxV());
/* 2206:2622 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var10.getMaxU(), var10.getMinV());
/* 2207:2623 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var10.getMinU(), var10.getMinV());
/* 2208:2624 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var10.getMinU(), var10.getMaxV());
/* 2209:     */     }
/* 2210:     */     else
/* 2211:     */     {
/* 2212:2628 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var8.getMaxU(), var8.getMaxV());
/* 2213:2629 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var8.getMinU(), var8.getMaxV());
/* 2214:2630 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var8.getMinU(), var8.getMinV());
/* 2215:2631 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var8.getMaxU(), var8.getMinV());
/* 2216:2632 */       var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2217:2633 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var26, var10.getMaxU(), var10.getMaxV());
/* 2218:2634 */       var5.addVertexWithUV(var24, p_147788_3_ + 0.015625D, var25, var10.getMinU(), var10.getMaxV());
/* 2219:2635 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var25, var10.getMinU(), var10.getMinV());
/* 2220:2636 */       var5.addVertexWithUV(var23, p_147788_3_ + 0.015625D, var26, var10.getMaxU(), var10.getMinV());
/* 2221:     */     }
/* 2222:2639 */     if (!this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_).isBlockNormalCube())
/* 2223:     */     {
/* 2224:2641 */       float var331 = 0.021875F;
/* 2225:2643 */       if ((this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (this.blockAccess.getBlock(p_147788_2_ - 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire))
/* 2226:     */       {
/* 2227:2645 */         var5.setColorOpaque_F(var12, var13, var14);
/* 2228:2646 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1, var8.getMaxU(), var8.getMinV());
/* 2229:2647 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMinV());
/* 2230:2648 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMaxV());
/* 2231:2649 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0, var8.getMaxU(), var8.getMaxV());
/* 2232:2650 */         var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2233:2651 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1, var10.getMaxU(), var10.getMinV());
/* 2234:2652 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMinV());
/* 2235:2653 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMaxV());
/* 2236:2654 */         var5.addVertexWithUV(p_147788_2_ + 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0, var10.getMaxU(), var10.getMaxV());
/* 2237:     */       }
/* 2238:2657 */       if ((this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_, p_147788_4_).isBlockNormalCube()) && (this.blockAccess.getBlock(p_147788_2_ + 1, p_147788_3_ + 1, p_147788_4_) == Blocks.redstone_wire))
/* 2239:     */       {
/* 2240:2659 */         var5.setColorOpaque_F(var12, var13, var14);
/* 2241:2660 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 0, p_147788_4_ + 1, var8.getMinU(), var8.getMaxV());
/* 2242:2661 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1, var8.getMaxU(), var8.getMaxV());
/* 2243:2662 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0, var8.getMaxU(), var8.getMinV());
/* 2244:2663 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 0, p_147788_4_ + 0, var8.getMinU(), var8.getMinV());
/* 2245:2664 */         var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2246:2665 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 0, p_147788_4_ + 1, var10.getMinU(), var10.getMaxV());
/* 2247:2666 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1, var10.getMaxU(), var10.getMaxV());
/* 2248:2667 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0, var10.getMaxU(), var10.getMinV());
/* 2249:2668 */         var5.addVertexWithUV(p_147788_2_ + 1 - 0.015625D, p_147788_3_ + 0, p_147788_4_ + 0, var10.getMinU(), var10.getMinV());
/* 2250:     */       }
/* 2251:2671 */       if ((this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ - 1).isBlockNormalCube()) && (this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ - 1) == Blocks.redstone_wire))
/* 2252:     */       {
/* 2253:2673 */         var5.setColorOpaque_F(var12, var13, var14);
/* 2254:2674 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 0.015625D, var8.getMinU(), var8.getMaxV());
/* 2255:2675 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0.015625D, var8.getMaxU(), var8.getMaxV());
/* 2256:2676 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0.015625D, var8.getMaxU(), var8.getMinV());
/* 2257:2677 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 0.015625D, var8.getMinU(), var8.getMinV());
/* 2258:2678 */         var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2259:2679 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 0.015625D, var10.getMinU(), var10.getMaxV());
/* 2260:2680 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0.015625D, var10.getMaxU(), var10.getMaxV());
/* 2261:2681 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 0.015625D, var10.getMaxU(), var10.getMinV());
/* 2262:2682 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 0.015625D, var10.getMinU(), var10.getMinV());
/* 2263:     */       }
/* 2264:2685 */       if ((this.blockAccess.getBlock(p_147788_2_, p_147788_3_, p_147788_4_ + 1).isBlockNormalCube()) && (this.blockAccess.getBlock(p_147788_2_, p_147788_3_ + 1, p_147788_4_ + 1) == Blocks.redstone_wire))
/* 2265:     */       {
/* 2266:2687 */         var5.setColorOpaque_F(var12, var13, var14);
/* 2267:2688 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1 - 0.015625D, var8.getMaxU(), var8.getMinV());
/* 2268:2689 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625D, var8.getMinU(), var8.getMinV());
/* 2269:2690 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625D, var8.getMinU(), var8.getMaxV());
/* 2270:2691 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1 - 0.015625D, var8.getMaxU(), var8.getMaxV());
/* 2271:2692 */         var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2272:2693 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1 - 0.015625D, var10.getMaxU(), var10.getMinV());
/* 2273:2694 */         var5.addVertexWithUV(p_147788_2_ + 1, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625D, var10.getMinU(), var10.getMinV());
/* 2274:2695 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 0, p_147788_4_ + 1 - 0.015625D, var10.getMinU(), var10.getMaxV());
/* 2275:2696 */         var5.addVertexWithUV(p_147788_2_ + 0, p_147788_3_ + 1 + 0.021875F, p_147788_4_ + 1 - 0.015625D, var10.getMaxU(), var10.getMaxV());
/* 2276:     */       }
/* 2277:     */     }
/* 2278:2700 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147788_2_, p_147788_3_, p_147788_4_))) {
/* 2279:2702 */       renderSnow(p_147788_2_, p_147788_3_, p_147788_4_, 0.01D);
/* 2280:     */     }
/* 2281:2705 */     return true;
/* 2282:     */   }
/* 2283:     */   
/* 2284:     */   public boolean renderBlockMinecartTrack(BlockRailBase p_147766_1_, int p_147766_2_, int p_147766_3_, int p_147766_4_)
/* 2285:     */   {
/* 2286:2710 */     Tessellator var5 = Tessellator.instance;
/* 2287:2711 */     int var6 = this.blockAccess.getBlockMetadata(p_147766_2_, p_147766_3_, p_147766_4_);
/* 2288:2712 */     IIcon var7 = getBlockIconFromSideAndMetadata(p_147766_1_, 0, var6);
/* 2289:2714 */     if (hasOverrideBlockTexture()) {
/* 2290:2716 */       var7 = this.overrideBlockTexture;
/* 2291:     */     }
/* 2292:2719 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null)) {
/* 2293:2721 */       var7 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147766_1_, p_147766_2_, p_147766_3_, p_147766_4_, 1, var7);
/* 2294:     */     }
/* 2295:2724 */     if (p_147766_1_.func_150050_e()) {
/* 2296:2726 */       var6 &= 0x7;
/* 2297:     */     }
/* 2298:2729 */     var5.setBrightness(p_147766_1_.getBlockBrightness(this.blockAccess, p_147766_2_, p_147766_3_, p_147766_4_));
/* 2299:2730 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2300:2731 */     double var8 = var7.getMinU();
/* 2301:2732 */     double var10 = var7.getMinV();
/* 2302:2733 */     double var12 = var7.getMaxU();
/* 2303:2734 */     double var14 = var7.getMaxV();
/* 2304:2735 */     double var16 = 0.0625D;
/* 2305:2736 */     double var18 = p_147766_2_ + 1;
/* 2306:2737 */     double var20 = p_147766_2_ + 1;
/* 2307:2738 */     double var22 = p_147766_2_ + 0;
/* 2308:2739 */     double var24 = p_147766_2_ + 0;
/* 2309:2740 */     double var26 = p_147766_4_ + 0;
/* 2310:2741 */     double var28 = p_147766_4_ + 1;
/* 2311:2742 */     double var30 = p_147766_4_ + 1;
/* 2312:2743 */     double var32 = p_147766_4_ + 0;
/* 2313:2744 */     double var34 = p_147766_3_ + var16;
/* 2314:2745 */     double var36 = p_147766_3_ + var16;
/* 2315:2746 */     double var38 = p_147766_3_ + var16;
/* 2316:2747 */     double var40 = p_147766_3_ + var16;
/* 2317:2749 */     if ((var6 != 1) && (var6 != 2) && (var6 != 3) && (var6 != 7))
/* 2318:     */     {
/* 2319:2751 */       if (var6 == 8)
/* 2320:     */       {
/* 2321:2753 */         var18 = var20 = p_147766_2_ + 0;
/* 2322:2754 */         var22 = var24 = p_147766_2_ + 1;
/* 2323:2755 */         var26 = var32 = p_147766_4_ + 1;
/* 2324:2756 */         var28 = var30 = p_147766_4_ + 0;
/* 2325:     */       }
/* 2326:2758 */       else if (var6 == 9)
/* 2327:     */       {
/* 2328:2760 */         var18 = var24 = p_147766_2_ + 0;
/* 2329:2761 */         var20 = var22 = p_147766_2_ + 1;
/* 2330:2762 */         var26 = var28 = p_147766_4_ + 0;
/* 2331:2763 */         var30 = var32 = p_147766_4_ + 1;
/* 2332:     */       }
/* 2333:     */     }
/* 2334:     */     else
/* 2335:     */     {
/* 2336:2768 */       var18 = var24 = p_147766_2_ + 1;
/* 2337:2769 */       var20 = var22 = p_147766_2_ + 0;
/* 2338:2770 */       var26 = var28 = p_147766_4_ + 1;
/* 2339:2771 */       var30 = var32 = p_147766_4_ + 0;
/* 2340:     */     }
/* 2341:2774 */     if ((var6 != 2) && (var6 != 4))
/* 2342:     */     {
/* 2343:2776 */       if ((var6 == 3) || (var6 == 5))
/* 2344:     */       {
/* 2345:2778 */         var36 += 1.0D;
/* 2346:2779 */         var38 += 1.0D;
/* 2347:     */       }
/* 2348:     */     }
/* 2349:     */     else
/* 2350:     */     {
/* 2351:2784 */       var34 += 1.0D;
/* 2352:2785 */       var40 += 1.0D;
/* 2353:     */     }
/* 2354:2788 */     var5.addVertexWithUV(var18, var34, var26, var12, var10);
/* 2355:2789 */     var5.addVertexWithUV(var20, var36, var28, var12, var14);
/* 2356:2790 */     var5.addVertexWithUV(var22, var38, var30, var8, var14);
/* 2357:2791 */     var5.addVertexWithUV(var24, var40, var32, var8, var10);
/* 2358:2792 */     var5.addVertexWithUV(var24, var40, var32, var8, var10);
/* 2359:2793 */     var5.addVertexWithUV(var22, var38, var30, var8, var14);
/* 2360:2794 */     var5.addVertexWithUV(var20, var36, var28, var12, var14);
/* 2361:2795 */     var5.addVertexWithUV(var18, var34, var26, var12, var10);
/* 2362:2797 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147766_2_, p_147766_3_, p_147766_4_))) {
/* 2363:2799 */       renderSnow(p_147766_2_, p_147766_3_, p_147766_4_, 0.05D);
/* 2364:     */     }
/* 2365:2802 */     return true;
/* 2366:     */   }
/* 2367:     */   
/* 2368:     */   public boolean renderBlockLadder(Block p_147794_1_, int p_147794_2_, int p_147794_3_, int p_147794_4_)
/* 2369:     */   {
/* 2370:2807 */     Tessellator var5 = Tessellator.instance;
/* 2371:2808 */     IIcon var6 = getBlockIconFromSide(p_147794_1_, 0);
/* 2372:2810 */     if (hasOverrideBlockTexture()) {
/* 2373:2812 */       var6 = this.overrideBlockTexture;
/* 2374:     */     }
/* 2375:2815 */     int var15 = this.blockAccess.getBlockMetadata(p_147794_2_, p_147794_3_, p_147794_4_);
/* 2376:2817 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null)) {
/* 2377:2819 */       var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147794_1_, p_147794_2_, p_147794_3_, p_147794_4_, var15, var6);
/* 2378:     */     }
/* 2379:2822 */     var5.setBrightness(p_147794_1_.getBlockBrightness(this.blockAccess, p_147794_2_, p_147794_3_, p_147794_4_));
/* 2380:2823 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 2381:2824 */     double var7 = var6.getMinU();
/* 2382:2825 */     double var9 = var6.getMinV();
/* 2383:2826 */     double var11 = var6.getMaxU();
/* 2384:2827 */     double var13 = var6.getMaxV();
/* 2385:2828 */     double var16 = 0.0D;
/* 2386:2829 */     double var18 = 0.0500000007450581D;
/* 2387:2831 */     if (var15 == 5)
/* 2388:     */     {
/* 2389:2833 */       var5.addVertexWithUV(p_147794_2_ + var18, p_147794_3_ + 1 + var16, p_147794_4_ + 1 + var16, var7, var9);
/* 2390:2834 */       var5.addVertexWithUV(p_147794_2_ + var18, p_147794_3_ + 0 - var16, p_147794_4_ + 1 + var16, var7, var13);
/* 2391:2835 */       var5.addVertexWithUV(p_147794_2_ + var18, p_147794_3_ + 0 - var16, p_147794_4_ + 0 - var16, var11, var13);
/* 2392:2836 */       var5.addVertexWithUV(p_147794_2_ + var18, p_147794_3_ + 1 + var16, p_147794_4_ + 0 - var16, var11, var9);
/* 2393:     */     }
/* 2394:2839 */     if (var15 == 4)
/* 2395:     */     {
/* 2396:2841 */       var5.addVertexWithUV(p_147794_2_ + 1 - var18, p_147794_3_ + 0 - var16, p_147794_4_ + 1 + var16, var11, var13);
/* 2397:2842 */       var5.addVertexWithUV(p_147794_2_ + 1 - var18, p_147794_3_ + 1 + var16, p_147794_4_ + 1 + var16, var11, var9);
/* 2398:2843 */       var5.addVertexWithUV(p_147794_2_ + 1 - var18, p_147794_3_ + 1 + var16, p_147794_4_ + 0 - var16, var7, var9);
/* 2399:2844 */       var5.addVertexWithUV(p_147794_2_ + 1 - var18, p_147794_3_ + 0 - var16, p_147794_4_ + 0 - var16, var7, var13);
/* 2400:     */     }
/* 2401:2847 */     if (var15 == 3)
/* 2402:     */     {
/* 2403:2849 */       var5.addVertexWithUV(p_147794_2_ + 1 + var16, p_147794_3_ + 0 - var16, p_147794_4_ + var18, var11, var13);
/* 2404:2850 */       var5.addVertexWithUV(p_147794_2_ + 1 + var16, p_147794_3_ + 1 + var16, p_147794_4_ + var18, var11, var9);
/* 2405:2851 */       var5.addVertexWithUV(p_147794_2_ + 0 - var16, p_147794_3_ + 1 + var16, p_147794_4_ + var18, var7, var9);
/* 2406:2852 */       var5.addVertexWithUV(p_147794_2_ + 0 - var16, p_147794_3_ + 0 - var16, p_147794_4_ + var18, var7, var13);
/* 2407:     */     }
/* 2408:2855 */     if (var15 == 2)
/* 2409:     */     {
/* 2410:2857 */       var5.addVertexWithUV(p_147794_2_ + 1 + var16, p_147794_3_ + 1 + var16, p_147794_4_ + 1 - var18, var7, var9);
/* 2411:2858 */       var5.addVertexWithUV(p_147794_2_ + 1 + var16, p_147794_3_ + 0 - var16, p_147794_4_ + 1 - var18, var7, var13);
/* 2412:2859 */       var5.addVertexWithUV(p_147794_2_ + 0 - var16, p_147794_3_ + 0 - var16, p_147794_4_ + 1 - var18, var11, var13);
/* 2413:2860 */       var5.addVertexWithUV(p_147794_2_ + 0 - var16, p_147794_3_ + 1 + var16, p_147794_4_ + 1 - var18, var11, var9);
/* 2414:     */     }
/* 2415:2863 */     return true;
/* 2416:     */   }
/* 2417:     */   
/* 2418:     */   public boolean renderBlockVine(Block p_147726_1_, int p_147726_2_, int p_147726_3_, int p_147726_4_)
/* 2419:     */   {
/* 2420:2868 */     Tessellator var5 = Tessellator.instance;
/* 2421:2869 */     IIcon var6 = getBlockIconFromSide(p_147726_1_, 0);
/* 2422:2871 */     if (hasOverrideBlockTexture()) {
/* 2423:2873 */       var6 = this.overrideBlockTexture;
/* 2424:     */     }
/* 2425:2876 */     int var17 = this.blockAccess.getBlockMetadata(p_147726_2_, p_147726_3_, p_147726_4_);
/* 2426:2878 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null))
/* 2427:     */     {
/* 2428:2880 */       byte var7 = 0;
/* 2429:2882 */       if ((var17 & 0x1) != 0) {
/* 2430:2884 */         var7 = 2;
/* 2431:2886 */       } else if ((var17 & 0x2) != 0) {
/* 2432:2888 */         var7 = 5;
/* 2433:2890 */       } else if ((var17 & 0x4) != 0) {
/* 2434:2892 */         var7 = 3;
/* 2435:2894 */       } else if ((var17 & 0x8) != 0) {
/* 2436:2896 */         var7 = 4;
/* 2437:     */       }
/* 2438:2899 */       var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147726_1_, p_147726_2_, p_147726_3_, p_147726_4_, var7, var6);
/* 2439:     */     }
/* 2440:2902 */     var5.setBrightness(p_147726_1_.getBlockBrightness(this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_));
/* 2441:2903 */     int var71 = CustomColorizer.getColorMultiplier(p_147726_1_, this.blockAccess, p_147726_2_, p_147726_3_, p_147726_4_);
/* 2442:2904 */     float var8 = (var71 >> 16 & 0xFF) / 255.0F;
/* 2443:2905 */     float var9 = (var71 >> 8 & 0xFF) / 255.0F;
/* 2444:2906 */     float var10 = (var71 & 0xFF) / 255.0F;
/* 2445:2907 */     var5.setColorOpaque_F(var8, var9, var10);
/* 2446:2908 */     double var18 = var6.getMinU();
/* 2447:2909 */     double var19 = var6.getMinV();
/* 2448:2910 */     double var11 = var6.getMaxU();
/* 2449:2911 */     double var13 = var6.getMaxV();
/* 2450:2912 */     double var15 = 0.0500000007450581D;
/* 2451:2914 */     if ((var17 & 0x2) != 0)
/* 2452:     */     {
/* 2453:2916 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 1, var18, var19);
/* 2454:2917 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 1, var18, var13);
/* 2455:2918 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 0, var11, var13);
/* 2456:2919 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 0, var11, var19);
/* 2457:2920 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 0, var11, var19);
/* 2458:2921 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 0, var11, var13);
/* 2459:2922 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 0, p_147726_4_ + 1, var18, var13);
/* 2460:2923 */       var5.addVertexWithUV(p_147726_2_ + var15, p_147726_3_ + 1, p_147726_4_ + 1, var18, var19);
/* 2461:     */     }
/* 2462:2926 */     if ((var17 & 0x8) != 0)
/* 2463:     */     {
/* 2464:2928 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 0, p_147726_4_ + 1, var11, var13);
/* 2465:2929 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 1, p_147726_4_ + 1, var11, var19);
/* 2466:2930 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 1, p_147726_4_ + 0, var18, var19);
/* 2467:2931 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 0, p_147726_4_ + 0, var18, var13);
/* 2468:2932 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 0, p_147726_4_ + 0, var18, var13);
/* 2469:2933 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 1, p_147726_4_ + 0, var18, var19);
/* 2470:2934 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 1, p_147726_4_ + 1, var11, var19);
/* 2471:2935 */       var5.addVertexWithUV(p_147726_2_ + 1 - var15, p_147726_3_ + 0, p_147726_4_ + 1, var11, var13);
/* 2472:     */     }
/* 2473:2938 */     if ((var17 & 0x4) != 0)
/* 2474:     */     {
/* 2475:2940 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + var15, var11, var13);
/* 2476:2941 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + var15, var11, var19);
/* 2477:2942 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + var15, var18, var19);
/* 2478:2943 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + var15, var18, var13);
/* 2479:2944 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + var15, var18, var13);
/* 2480:2945 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + var15, var18, var19);
/* 2481:2946 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + var15, var11, var19);
/* 2482:2947 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + var15, var11, var13);
/* 2483:     */     }
/* 2484:2950 */     if ((var17 & 0x1) != 0)
/* 2485:     */     {
/* 2486:2952 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + 1 - var15, var18, var19);
/* 2487:2953 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + 1 - var15, var18, var13);
/* 2488:2954 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + 1 - var15, var11, var13);
/* 2489:2955 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + 1 - var15, var11, var19);
/* 2490:2956 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1, p_147726_4_ + 1 - var15, var11, var19);
/* 2491:2957 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 0, p_147726_4_ + 1 - var15, var11, var13);
/* 2492:2958 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 0, p_147726_4_ + 1 - var15, var18, var13);
/* 2493:2959 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1, p_147726_4_ + 1 - var15, var18, var19);
/* 2494:     */     }
/* 2495:2962 */     if (this.blockAccess.getBlock(p_147726_2_, p_147726_3_ + 1, p_147726_4_).isBlockNormalCube())
/* 2496:     */     {
/* 2497:2964 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1 - var15, p_147726_4_ + 0, var18, var19);
/* 2498:2965 */       var5.addVertexWithUV(p_147726_2_ + 1, p_147726_3_ + 1 - var15, p_147726_4_ + 1, var18, var13);
/* 2499:2966 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1 - var15, p_147726_4_ + 1, var11, var13);
/* 2500:2967 */       var5.addVertexWithUV(p_147726_2_ + 0, p_147726_3_ + 1 - var15, p_147726_4_ + 0, var11, var19);
/* 2501:     */     }
/* 2502:2970 */     return true;
/* 2503:     */   }
/* 2504:     */   
/* 2505:     */   public boolean renderBlockStainedGlassPane(Block block, int x, int y, int z)
/* 2506:     */   {
/* 2507:2975 */     int var5 = this.blockAccess.getHeight();
/* 2508:2976 */     Tessellator var6 = Tessellator.instance;
/* 2509:2977 */     var6.setBrightness(block.getBlockBrightness(this.blockAccess, x, y, z));
/* 2510:2978 */     int var7 = block.colorMultiplier(this.blockAccess, x, y, z);
/* 2511:2979 */     float var8 = (var7 >> 16 & 0xFF) / 255.0F;
/* 2512:2980 */     float var9 = (var7 >> 8 & 0xFF) / 255.0F;
/* 2513:2981 */     float var10 = (var7 & 0xFF) / 255.0F;
/* 2514:2983 */     if (EntityRenderer.anaglyphEnable)
/* 2515:     */     {
/* 2516:2985 */       float isStainedGlass = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/* 2517:2986 */       float iconGlass = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/* 2518:2987 */       float iconGlassPaneTop = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/* 2519:2988 */       var8 = isStainedGlass;
/* 2520:2989 */       var9 = iconGlass;
/* 2521:2990 */       var10 = iconGlassPaneTop;
/* 2522:     */     }
/* 2523:2993 */     var6.setColorOpaque_F(var8, var9, var10);
/* 2524:2994 */     boolean isStainedGlass1 = block instanceof BlockStainedGlassPane;
/* 2525:2995 */     int metadata = 0;
/* 2526:     */     IIcon iconGlassPaneTop1;
/* 2527:     */     IIcon iconGlass1;
/* 2528:     */     IIcon iconGlassPaneTop1;
/* 2529:2999 */     if (hasOverrideBlockTexture())
/* 2530:     */     {
/* 2531:3001 */       IIcon iconGlass1 = this.overrideBlockTexture;
/* 2532:3002 */       iconGlassPaneTop1 = this.overrideBlockTexture;
/* 2533:     */     }
/* 2534:     */     else
/* 2535:     */     {
/* 2536:3006 */       metadata = this.blockAccess.getBlockMetadata(x, y, z);
/* 2537:3007 */       iconGlass1 = getBlockIconFromSideAndMetadata(block, 0, metadata);
/* 2538:3008 */       iconGlassPaneTop1 = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150104_b(metadata) : ((BlockPane)block).func_150097_e();
/* 2539:     */     }
/* 2540:3011 */     IIcon iconZ = iconGlass1;
/* 2541:3012 */     boolean drawTop = true;
/* 2542:3013 */     boolean drawBottom = true;
/* 2543:3015 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null))
/* 2544:     */     {
/* 2545:3017 */       IIcon gMinU = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 4, iconGlass1);
/* 2546:3018 */       IIcon iz = ConnectedTextures.getConnectedTexture(this.blockAccess, block, x, y, z, 2, iconGlass1);
/* 2547:3020 */       if ((gMinU != iconGlass1) || (iz != iconGlass1))
/* 2548:     */       {
/* 2549:3022 */         BlockPane gHalf7U = (BlockPane)block;
/* 2550:3023 */         drawTop = (this.blockAccess.getBlock(x, y + 1, z) != block) || (this.blockAccess.getBlockMetadata(x, y + 1, z) != metadata);
/* 2551:3024 */         drawBottom = (this.blockAccess.getBlock(x, y - 1, z) != block) || (this.blockAccess.getBlockMetadata(x, y - 1, z) != metadata);
/* 2552:     */       }
/* 2553:3027 */       iconGlass1 = gMinU;
/* 2554:3028 */       iconZ = iz;
/* 2555:     */     }
/* 2556:3031 */     double gMinU1 = iconGlass1.getMinU();
/* 2557:3032 */     double gHalf7U1 = iconGlass1.getInterpolatedU(7.0D);
/* 2558:3033 */     double gHalf9U = iconGlass1.getInterpolatedU(9.0D);
/* 2559:3034 */     double gMaxU = iconGlass1.getMaxU();
/* 2560:3035 */     double gMinV = iconGlass1.getMinV();
/* 2561:3036 */     double gMaxV = iconGlass1.getMaxV();
/* 2562:3037 */     double gMinUz = iconZ.getMinU();
/* 2563:3038 */     double gHalf7Uz = iconZ.getInterpolatedU(7.0D);
/* 2564:3039 */     double gHalf9Uz = iconZ.getInterpolatedU(9.0D);
/* 2565:3040 */     double gMaxUz = iconZ.getMaxU();
/* 2566:3041 */     double gMinVz = iconZ.getMinV();
/* 2567:3042 */     double gMaxVz = iconZ.getMaxV();
/* 2568:3043 */     double var26 = iconGlassPaneTop1.getInterpolatedU(7.0D);
/* 2569:3044 */     double var28 = iconGlassPaneTop1.getInterpolatedU(9.0D);
/* 2570:3045 */     double var30 = iconGlassPaneTop1.getMinV();
/* 2571:3046 */     double var32 = iconGlassPaneTop1.getMaxV();
/* 2572:3047 */     double var34 = iconGlassPaneTop1.getInterpolatedV(7.0D);
/* 2573:3048 */     double var36 = iconGlassPaneTop1.getInterpolatedV(9.0D);
/* 2574:3049 */     double x0 = x;
/* 2575:3050 */     double x1 = x + 1;
/* 2576:3051 */     double z0 = z;
/* 2577:3052 */     double z1 = z + 1;
/* 2578:3053 */     double xHalfMin = x + 0.5D - 0.0625D;
/* 2579:3054 */     double xHalfMax = x + 0.5D + 0.0625D;
/* 2580:3055 */     double zHalfMin = z + 0.5D - 0.0625D;
/* 2581:3056 */     double zHalfMax = z + 0.5D + 0.0625D;
/* 2582:3057 */     boolean connZNeg = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z - 1));
/* 2583:3058 */     boolean connZPos = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x, y, z + 1));
/* 2584:3059 */     boolean connXNeg = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x - 1, y, z));
/* 2585:3060 */     boolean connXPos = isStainedGlass1 ? ((BlockStainedGlassPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z)) : ((BlockPane)block).func_150098_a(this.blockAccess.getBlock(x + 1, y, z));
/* 2586:3061 */     double var58 = 0.001D;
/* 2587:3062 */     double var60 = 0.999D;
/* 2588:3063 */     double var62 = 0.001D;
/* 2589:3064 */     boolean disconnected = (!connZNeg) && (!connZPos) && (!connXNeg) && (!connXPos);
/* 2590:3065 */     double yTop = y + 0.999D;
/* 2591:3066 */     double yBottom = y + 0.001D;
/* 2592:3068 */     if (!drawTop) {
/* 2593:3070 */       yTop = y + 1;
/* 2594:     */     }
/* 2595:3073 */     if (!drawBottom) {
/* 2596:3075 */       yBottom = y;
/* 2597:     */     }
/* 2598:3078 */     if ((!connXNeg) && (!disconnected))
/* 2599:     */     {
/* 2600:3080 */       if ((!connZNeg) && (!connZPos))
/* 2601:     */       {
/* 2602:3082 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2603:3083 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2604:3084 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2605:3085 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
/* 2606:     */       }
/* 2607:     */     }
/* 2608:3088 */     else if ((connXNeg) && (connXPos))
/* 2609:     */     {
/* 2610:3090 */       if (!connZNeg)
/* 2611:     */       {
/* 2612:3092 */         var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
/* 2613:3093 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
/* 2614:3094 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
/* 2615:3095 */         var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
/* 2616:     */       }
/* 2617:     */       else
/* 2618:     */       {
/* 2619:3099 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
/* 2620:3100 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
/* 2621:3101 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
/* 2622:3102 */         var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
/* 2623:3103 */         var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
/* 2624:3104 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
/* 2625:3105 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
/* 2626:3106 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
/* 2627:     */       }
/* 2628:3109 */       if (!connZPos)
/* 2629:     */       {
/* 2630:3111 */         var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
/* 2631:3112 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
/* 2632:3113 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
/* 2633:3114 */         var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
/* 2634:     */       }
/* 2635:     */       else
/* 2636:     */       {
/* 2637:3118 */         var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
/* 2638:3119 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
/* 2639:3120 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
/* 2640:3121 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
/* 2641:3122 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
/* 2642:3123 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
/* 2643:3124 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
/* 2644:3125 */         var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
/* 2645:     */       }
/* 2646:3128 */       if (drawTop)
/* 2647:     */       {
/* 2648:3130 */         var6.addVertexWithUV(x0, yTop, zHalfMax, var28, var30);
/* 2649:3131 */         var6.addVertexWithUV(x1, yTop, zHalfMax, var28, var32);
/* 2650:3132 */         var6.addVertexWithUV(x1, yTop, zHalfMin, var26, var32);
/* 2651:3133 */         var6.addVertexWithUV(x0, yTop, zHalfMin, var26, var30);
/* 2652:     */       }
/* 2653:3136 */       if (drawBottom)
/* 2654:     */       {
/* 2655:3138 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, var26, var32);
/* 2656:3139 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, var26, var30);
/* 2657:3140 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, var28, var30);
/* 2658:3141 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, var28, var32);
/* 2659:     */       }
/* 2660:     */     }
/* 2661:     */     else
/* 2662:     */     {
/* 2663:3146 */       if ((!connZNeg) && (!disconnected))
/* 2664:     */       {
/* 2665:3148 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
/* 2666:3149 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
/* 2667:3150 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
/* 2668:3151 */         var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
/* 2669:     */       }
/* 2670:     */       else
/* 2671:     */       {
/* 2672:3155 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
/* 2673:3156 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
/* 2674:3157 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, gMinUz, gMaxVz);
/* 2675:3158 */         var6.addVertexWithUV(x0, yTop, zHalfMin, gMinUz, gMinVz);
/* 2676:     */       }
/* 2677:3161 */       if ((!connZPos) && (!disconnected))
/* 2678:     */       {
/* 2679:3163 */         var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
/* 2680:3164 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
/* 2681:3165 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
/* 2682:3166 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
/* 2683:     */       }
/* 2684:     */       else
/* 2685:     */       {
/* 2686:3170 */         var6.addVertexWithUV(x0, yTop, zHalfMax, gMinUz, gMinVz);
/* 2687:3171 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, gMinUz, gMaxVz);
/* 2688:3172 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
/* 2689:3173 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
/* 2690:     */       }
/* 2691:3176 */       if (drawTop)
/* 2692:     */       {
/* 2693:3178 */         var6.addVertexWithUV(x0, yTop, zHalfMax, var28, var30);
/* 2694:3179 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var28, var34);
/* 2695:3180 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
/* 2696:3181 */         var6.addVertexWithUV(x0, yTop, zHalfMin, var26, var30);
/* 2697:     */       }
/* 2698:3184 */       if (drawBottom)
/* 2699:     */       {
/* 2700:3186 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var34);
/* 2701:3187 */         var6.addVertexWithUV(x0, yBottom, zHalfMax, var26, var30);
/* 2702:3188 */         var6.addVertexWithUV(x0, yBottom, zHalfMin, var28, var30);
/* 2703:3189 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var28, var34);
/* 2704:     */       }
/* 2705:     */     }
/* 2706:3193 */     if (((connXPos) || (disconnected)) && (!connXNeg))
/* 2707:     */     {
/* 2708:3195 */       if ((!connZPos) && (!disconnected))
/* 2709:     */       {
/* 2710:3197 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
/* 2711:3198 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
/* 2712:3199 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
/* 2713:3200 */         var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
/* 2714:     */       }
/* 2715:     */       else
/* 2716:     */       {
/* 2717:3204 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
/* 2718:3205 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
/* 2719:3206 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, gMaxUz, gMaxVz);
/* 2720:3207 */         var6.addVertexWithUV(x1, yTop, zHalfMax, gMaxUz, gMinVz);
/* 2721:     */       }
/* 2722:3210 */       if ((!connZNeg) && (!disconnected))
/* 2723:     */       {
/* 2724:3212 */         var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
/* 2725:3213 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
/* 2726:3214 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
/* 2727:3215 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
/* 2728:     */       }
/* 2729:     */       else
/* 2730:     */       {
/* 2731:3219 */         var6.addVertexWithUV(x1, yTop, zHalfMin, gMaxUz, gMinVz);
/* 2732:3220 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, gMaxUz, gMaxVz);
/* 2733:3221 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
/* 2734:3222 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
/* 2735:     */       }
/* 2736:3225 */       if (drawTop)
/* 2737:     */       {
/* 2738:3227 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
/* 2739:3228 */         var6.addVertexWithUV(x1, yTop, zHalfMax, var28, var30);
/* 2740:3229 */         var6.addVertexWithUV(x1, yTop, zHalfMin, var26, var30);
/* 2741:3230 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var26, var36);
/* 2742:     */       }
/* 2743:3233 */       if (drawBottom)
/* 2744:     */       {
/* 2745:3235 */         var6.addVertexWithUV(x1, yBottom, zHalfMax, var26, var32);
/* 2746:3236 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var26, var36);
/* 2747:3237 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var36);
/* 2748:3238 */         var6.addVertexWithUV(x1, yBottom, zHalfMin, var28, var32);
/* 2749:     */       }
/* 2750:     */     }
/* 2751:3241 */     else if ((!connXPos) && (!connZNeg) && (!connZPos))
/* 2752:     */     {
/* 2753:3243 */       var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf7U1, gMinV);
/* 2754:3244 */       var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf7U1, gMaxV);
/* 2755:3245 */       var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9U, gMaxV);
/* 2756:3246 */       var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9U, gMinV);
/* 2757:     */     }
/* 2758:3249 */     if ((!connZNeg) && (!disconnected))
/* 2759:     */     {
/* 2760:3251 */       if ((!connXPos) && (!connXNeg))
/* 2761:     */       {
/* 2762:3253 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf9Uz, gMinVz);
/* 2763:3254 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf9Uz, gMaxVz);
/* 2764:3255 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7Uz, gMaxVz);
/* 2765:3256 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7Uz, gMinVz);
/* 2766:     */       }
/* 2767:     */     }
/* 2768:3259 */     else if ((connZNeg) && (connZPos))
/* 2769:     */     {
/* 2770:3261 */       if (!connXNeg)
/* 2771:     */       {
/* 2772:3263 */         var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
/* 2773:3264 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
/* 2774:3265 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
/* 2775:3266 */         var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
/* 2776:     */       }
/* 2777:     */       else
/* 2778:     */       {
/* 2779:3270 */         var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
/* 2780:3271 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
/* 2781:3272 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2782:3273 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2783:3274 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
/* 2784:3275 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2785:3276 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
/* 2786:3277 */         var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
/* 2787:     */       }
/* 2788:3280 */       if (!connXPos)
/* 2789:     */       {
/* 2790:3282 */         var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
/* 2791:3283 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
/* 2792:3284 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
/* 2793:3285 */         var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
/* 2794:     */       }
/* 2795:     */       else
/* 2796:     */       {
/* 2797:3289 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2798:3290 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2799:3291 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
/* 2800:3292 */         var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
/* 2801:3293 */         var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
/* 2802:3294 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
/* 2803:3295 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2804:3296 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
/* 2805:     */       }
/* 2806:3299 */       if (drawTop)
/* 2807:     */       {
/* 2808:3301 */         var6.addVertexWithUV(xHalfMax, yTop, z0, var28, var30);
/* 2809:3302 */         var6.addVertexWithUV(xHalfMin, yTop, z0, var26, var30);
/* 2810:3303 */         var6.addVertexWithUV(xHalfMin, yTop, z1, var26, var32);
/* 2811:3304 */         var6.addVertexWithUV(xHalfMax, yTop, z1, var28, var32);
/* 2812:     */       }
/* 2813:3307 */       if (drawBottom)
/* 2814:     */       {
/* 2815:3309 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, var26, var30);
/* 2816:3310 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, var28, var30);
/* 2817:3311 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, var28, var32);
/* 2818:3312 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, var26, var32);
/* 2819:     */       }
/* 2820:     */     }
/* 2821:     */     else
/* 2822:     */     {
/* 2823:3317 */       if ((!connXNeg) && (!disconnected))
/* 2824:     */       {
/* 2825:3319 */         var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
/* 2826:3320 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
/* 2827:3321 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2828:3322 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
/* 2829:     */       }
/* 2830:     */       else
/* 2831:     */       {
/* 2832:3326 */         var6.addVertexWithUV(xHalfMin, yTop, z0, gMinU1, gMinV);
/* 2833:3327 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, gMinU1, gMaxV);
/* 2834:3328 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2835:3329 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2836:     */       }
/* 2837:3332 */       if ((!connXPos) && (!disconnected))
/* 2838:     */       {
/* 2839:3334 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
/* 2840:3335 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2841:3336 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
/* 2842:3337 */         var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
/* 2843:     */       }
/* 2844:     */       else
/* 2845:     */       {
/* 2846:3341 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2847:3342 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2848:3343 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, gMinU1, gMaxV);
/* 2849:3344 */         var6.addVertexWithUV(xHalfMax, yTop, z0, gMinU1, gMinV);
/* 2850:     */       }
/* 2851:3347 */       if (drawTop)
/* 2852:     */       {
/* 2853:3349 */         var6.addVertexWithUV(xHalfMax, yTop, z0, var28, var30);
/* 2854:3350 */         var6.addVertexWithUV(xHalfMin, yTop, z0, var26, var30);
/* 2855:3351 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
/* 2856:3352 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var28, var34);
/* 2857:     */       }
/* 2858:3355 */       if (drawBottom)
/* 2859:     */       {
/* 2860:3357 */         var6.addVertexWithUV(xHalfMin, yBottom, z0, var26, var30);
/* 2861:3358 */         var6.addVertexWithUV(xHalfMax, yBottom, z0, var28, var30);
/* 2862:3359 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var34);
/* 2863:3360 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var26, var34);
/* 2864:     */       }
/* 2865:     */     }
/* 2866:3364 */     if (((connZPos) || (disconnected)) && (!connZNeg))
/* 2867:     */     {
/* 2868:3366 */       if ((!connXNeg) && (!disconnected))
/* 2869:     */       {
/* 2870:3368 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2871:3369 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2872:3370 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
/* 2873:3371 */         var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
/* 2874:     */       }
/* 2875:     */       else
/* 2876:     */       {
/* 2877:3375 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf9U, gMinV);
/* 2878:3376 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2879:3377 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, gMaxU, gMaxV);
/* 2880:3378 */         var6.addVertexWithUV(xHalfMin, yTop, z1, gMaxU, gMinV);
/* 2881:     */       }
/* 2882:3381 */       if ((!connXPos) && (!disconnected))
/* 2883:     */       {
/* 2884:3383 */         var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
/* 2885:3384 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
/* 2886:3385 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2887:3386 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2888:     */       }
/* 2889:     */       else
/* 2890:     */       {
/* 2891:3390 */         var6.addVertexWithUV(xHalfMax, yTop, z1, gMaxU, gMinV);
/* 2892:3391 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, gMaxU, gMaxV);
/* 2893:3392 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2894:3393 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9U, gMinV);
/* 2895:     */       }
/* 2896:3396 */       if (drawTop)
/* 2897:     */       {
/* 2898:3398 */         var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
/* 2899:3399 */         var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var26, var36);
/* 2900:3400 */         var6.addVertexWithUV(xHalfMin, yTop, z1, var26, var32);
/* 2901:3401 */         var6.addVertexWithUV(xHalfMax, yTop, z1, var28, var32);
/* 2902:     */       }
/* 2903:3404 */       if (drawBottom)
/* 2904:     */       {
/* 2905:3406 */         var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var36);
/* 2906:3407 */         var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var28, var36);
/* 2907:3408 */         var6.addVertexWithUV(xHalfMax, yBottom, z1, var28, var32);
/* 2908:3409 */         var6.addVertexWithUV(xHalfMin, yBottom, z1, var26, var32);
/* 2909:     */       }
/* 2910:     */     }
/* 2911:3412 */     else if ((!connZPos) && (!connXPos) && (!connXNeg))
/* 2912:     */     {
/* 2913:3414 */       var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, gHalf7Uz, gMinVz);
/* 2914:3415 */       var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, gHalf7Uz, gMaxVz);
/* 2915:3416 */       var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, gHalf9Uz, gMaxVz);
/* 2916:3417 */       var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, gHalf9Uz, gMinVz);
/* 2917:     */     }
/* 2918:3420 */     if (drawTop)
/* 2919:     */     {
/* 2920:3422 */       var6.addVertexWithUV(xHalfMax, yTop, zHalfMin, var28, var34);
/* 2921:3423 */       var6.addVertexWithUV(xHalfMin, yTop, zHalfMin, var26, var34);
/* 2922:3424 */       var6.addVertexWithUV(xHalfMin, yTop, zHalfMax, var26, var36);
/* 2923:3425 */       var6.addVertexWithUV(xHalfMax, yTop, zHalfMax, var28, var36);
/* 2924:     */     }
/* 2925:3428 */     if (drawBottom)
/* 2926:     */     {
/* 2927:3430 */       var6.addVertexWithUV(xHalfMin, yBottom, zHalfMin, var26, var34);
/* 2928:3431 */       var6.addVertexWithUV(xHalfMax, yBottom, zHalfMin, var28, var34);
/* 2929:3432 */       var6.addVertexWithUV(xHalfMax, yBottom, zHalfMax, var28, var36);
/* 2930:3433 */       var6.addVertexWithUV(xHalfMin, yBottom, zHalfMax, var26, var36);
/* 2931:     */     }
/* 2932:3436 */     if (disconnected)
/* 2933:     */     {
/* 2934:3438 */       var6.addVertexWithUV(x0, yTop, zHalfMin, gHalf7U1, gMinV);
/* 2935:3439 */       var6.addVertexWithUV(x0, yBottom, zHalfMin, gHalf7U1, gMaxV);
/* 2936:3440 */       var6.addVertexWithUV(x0, yBottom, zHalfMax, gHalf9U, gMaxV);
/* 2937:3441 */       var6.addVertexWithUV(x0, yTop, zHalfMax, gHalf9U, gMinV);
/* 2938:3442 */       var6.addVertexWithUV(x1, yTop, zHalfMax, gHalf7U1, gMinV);
/* 2939:3443 */       var6.addVertexWithUV(x1, yBottom, zHalfMax, gHalf7U1, gMaxV);
/* 2940:3444 */       var6.addVertexWithUV(x1, yBottom, zHalfMin, gHalf9U, gMaxV);
/* 2941:3445 */       var6.addVertexWithUV(x1, yTop, zHalfMin, gHalf9U, gMinV);
/* 2942:3446 */       var6.addVertexWithUV(xHalfMax, yTop, z0, gHalf9Uz, gMinVz);
/* 2943:3447 */       var6.addVertexWithUV(xHalfMax, yBottom, z0, gHalf9Uz, gMaxVz);
/* 2944:3448 */       var6.addVertexWithUV(xHalfMin, yBottom, z0, gHalf7Uz, gMaxVz);
/* 2945:3449 */       var6.addVertexWithUV(xHalfMin, yTop, z0, gHalf7Uz, gMinVz);
/* 2946:3450 */       var6.addVertexWithUV(xHalfMin, yTop, z1, gHalf7Uz, gMinVz);
/* 2947:3451 */       var6.addVertexWithUV(xHalfMin, yBottom, z1, gHalf7Uz, gMaxVz);
/* 2948:3452 */       var6.addVertexWithUV(xHalfMax, yBottom, z1, gHalf9Uz, gMaxVz);
/* 2949:3453 */       var6.addVertexWithUV(xHalfMax, yTop, z1, gHalf9Uz, gMinVz);
/* 2950:     */     }
/* 2951:3456 */     return true;
/* 2952:     */   }
/* 2953:     */   
/* 2954:     */   public boolean renderBlockPane(BlockPane p_147767_1_, int p_147767_2_, int p_147767_3_, int p_147767_4_)
/* 2955:     */   {
/* 2956:3461 */     int var5 = this.blockAccess.getHeight();
/* 2957:3462 */     Tessellator var6 = Tessellator.instance;
/* 2958:3463 */     var6.setBrightness(p_147767_1_.getBlockBrightness(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_));
/* 2959:3464 */     int var7 = p_147767_1_.colorMultiplier(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_);
/* 2960:3465 */     float var8 = (var7 >> 16 & 0xFF) / 255.0F;
/* 2961:3466 */     float var9 = (var7 >> 8 & 0xFF) / 255.0F;
/* 2962:3467 */     float var10 = (var7 & 0xFF) / 255.0F;
/* 2963:3469 */     if (EntityRenderer.anaglyphEnable)
/* 2964:     */     {
/* 2965:3471 */       float var64 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/* 2966:3472 */       float var63 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/* 2967:3473 */       float cp = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/* 2968:3474 */       var8 = var64;
/* 2969:3475 */       var9 = var63;
/* 2970:3476 */       var10 = cp;
/* 2971:     */     }
/* 2972:3479 */     var6.setColorOpaque_F(var8, var9, var10);
/* 2973:3480 */     ConnectedProperties cp1 = null;
/* 2974:     */     IIcon var641;
/* 2975:     */     IIcon var631;
/* 2976:     */     IIcon var641;
/* 2977:3484 */     if (hasOverrideBlockTexture())
/* 2978:     */     {
/* 2979:3486 */       IIcon var631 = this.overrideBlockTexture;
/* 2980:3487 */       var641 = this.overrideBlockTexture;
/* 2981:     */     }
/* 2982:     */     else
/* 2983:     */     {
/* 2984:3491 */       int kr = this.blockAccess.getBlockMetadata(p_147767_2_, p_147767_3_, p_147767_4_);
/* 2985:3492 */       var631 = getBlockIconFromSideAndMetadata(p_147767_1_, 0, kr);
/* 2986:3493 */       var641 = p_147767_1_.func_150097_e();
/* 2987:3495 */       if (Config.isConnectedTextures()) {
/* 2988:3497 */         cp1 = ConnectedTextures.getConnectedProperties(this.blockAccess, p_147767_1_, p_147767_2_, p_147767_3_, p_147767_4_, -1, var631);
/* 2989:     */       }
/* 2990:     */     }
/* 2991:3501 */     IIcon kr1 = var631;
/* 2992:3502 */     IIcon kz = var631;
/* 2993:3503 */     IIcon kzr = var631;
/* 2994:3505 */     if (cp1 != null)
/* 2995:     */     {
/* 2996:3507 */       Block blockIdXp = this.blockAccess.getBlock(p_147767_2_ + 1, p_147767_3_, p_147767_4_);
/* 2997:3508 */       Block var15 = this.blockAccess.getBlock(p_147767_2_ - 1, p_147767_3_, p_147767_4_);
/* 2998:3509 */       Block blockIdYp = this.blockAccess.getBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_);
/* 2999:3510 */       Block var17 = this.blockAccess.getBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_);
/* 3000:3511 */       Block blockIdZp = this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ + 1);
/* 3001:3512 */       Block var19 = this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ - 1);
/* 3002:3513 */       boolean linkXp = blockIdXp == p_147767_1_;
/* 3003:3514 */       boolean var21 = var15 == p_147767_1_;
/* 3004:3515 */       boolean linkYp = blockIdYp == p_147767_1_;
/* 3005:3516 */       boolean dr = var17 == p_147767_1_;
/* 3006:3517 */       boolean linkZp = blockIdZp == p_147767_1_;
/* 3007:3518 */       boolean d1r = var19 == p_147767_1_;
/* 3008:3519 */       int var64i = ConnectedTextures.getPaneTextureIndex(linkXp, var21, linkYp, dr);
/* 3009:3520 */       int d2r = ConnectedTextures.getReversePaneTextureIndex(var64i);
/* 3010:3521 */       int kzi = ConnectedTextures.getPaneTextureIndex(linkZp, d1r, linkYp, dr);
/* 3011:3522 */       int d3r = ConnectedTextures.getReversePaneTextureIndex(kzi);
/* 3012:3523 */       var631 = ConnectedTextures.getCtmTexture(cp1, var64i, var631);
/* 3013:3524 */       kr1 = ConnectedTextures.getCtmTexture(cp1, d2r, kr1);
/* 3014:3525 */       kz = ConnectedTextures.getCtmTexture(cp1, kzi, kz);
/* 3015:3526 */       kzr = ConnectedTextures.getCtmTexture(cp1, d3r, kzr);
/* 3016:     */     }
/* 3017:3529 */     double var65 = var631.getMinU();
/* 3018:3530 */     double var151 = var631.getInterpolatedU(8.0D);
/* 3019:3531 */     double var171 = var631.getMaxU();
/* 3020:3532 */     double var191 = var631.getMinV();
/* 3021:3533 */     double var211 = var631.getMaxV();
/* 3022:3534 */     double dr1 = kr1.getMinU();
/* 3023:3535 */     double d1r1 = kr1.getInterpolatedU(8.0D);
/* 3024:3536 */     double d2r1 = kr1.getMaxU();
/* 3025:3537 */     double d3r1 = kr1.getMinV();
/* 3026:3538 */     double d4r = kr1.getMaxV();
/* 3027:3539 */     double dz = kz.getMinU();
/* 3028:3540 */     double d1z = kz.getInterpolatedU(8.0D);
/* 3029:3541 */     double d2z = kz.getMaxU();
/* 3030:3542 */     double d3z = kz.getMinV();
/* 3031:3543 */     double d4z = kz.getMaxV();
/* 3032:3544 */     double dzr = kzr.getMinU();
/* 3033:3545 */     double d1zr = kzr.getInterpolatedU(8.0D);
/* 3034:3546 */     double d2zr = kzr.getMaxU();
/* 3035:3547 */     double d3zr = kzr.getMinV();
/* 3036:3548 */     double d4zr = kzr.getMaxV();
/* 3037:3549 */     double var23 = var641.getInterpolatedU(7.0D);
/* 3038:3550 */     double var25 = var641.getInterpolatedU(9.0D);
/* 3039:3551 */     double var27 = var641.getMinV();
/* 3040:3552 */     double var29 = var641.getInterpolatedV(8.0D);
/* 3041:3553 */     double var31 = var641.getMaxV();
/* 3042:3554 */     double var33 = p_147767_2_;
/* 3043:3555 */     double var35 = p_147767_2_ + 0.5D;
/* 3044:3556 */     double var37 = p_147767_2_ + 1;
/* 3045:3557 */     double var39 = p_147767_4_;
/* 3046:3558 */     double var41 = p_147767_4_ + 0.5D;
/* 3047:3559 */     double var43 = p_147767_4_ + 1;
/* 3048:3560 */     double var45 = p_147767_2_ + 0.5D - 0.0625D;
/* 3049:3561 */     double var47 = p_147767_2_ + 0.5D + 0.0625D;
/* 3050:3562 */     double var49 = p_147767_4_ + 0.5D - 0.0625D;
/* 3051:3563 */     double var51 = p_147767_4_ + 0.5D + 0.0625D;
/* 3052:3564 */     boolean var53 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ - 1));
/* 3053:3565 */     boolean var54 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_, p_147767_3_, p_147767_4_ + 1));
/* 3054:3566 */     boolean var55 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ - 1, p_147767_3_, p_147767_4_));
/* 3055:3567 */     boolean var56 = p_147767_1_.func_150098_a(this.blockAccess.getBlock(p_147767_2_ + 1, p_147767_3_, p_147767_4_));
/* 3056:3568 */     boolean var57 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ + 1, p_147767_4_, 1);
/* 3057:3569 */     boolean var58 = p_147767_1_.shouldSideBeRendered(this.blockAccess, p_147767_2_, p_147767_3_ - 1, p_147767_4_, 0);
/* 3058:3570 */     double var59 = 0.01D;
/* 3059:3571 */     double var61 = 0.005D;
/* 3060:3573 */     if (((!var55) || (!var56)) && ((var55) || (var56) || (var53) || (var54)))
/* 3061:     */     {
/* 3062:3575 */       if ((var55) && (!var56))
/* 3063:     */       {
/* 3064:3577 */         var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, var65, var191);
/* 3065:3578 */         var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, var65, var211);
/* 3066:3579 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, var151, var211);
/* 3067:3580 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, var151, var191);
/* 3068:3581 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1r1, d3r1);
/* 3069:3582 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1r1, d4r);
/* 3070:3583 */         var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, d2r1, d4r);
/* 3071:3584 */         var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, d2r1, d3r1);
/* 3072:3586 */         if ((!var54) && (!var53))
/* 3073:     */         {
/* 3074:3588 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var23, var27);
/* 3075:3589 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var23, var31);
/* 3076:3590 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var25, var31);
/* 3077:3591 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var25, var27);
/* 3078:3592 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var23, var27);
/* 3079:3593 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var23, var31);
/* 3080:3594 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var25, var31);
/* 3081:3595 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var25, var27);
/* 3082:     */         }
/* 3083:3598 */         if ((var57) || ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_))))
/* 3084:     */         {
/* 3085:3600 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3086:3601 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3087:3602 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3088:3603 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3089:3604 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3090:3605 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3091:3606 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3092:3607 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3093:     */         }
/* 3094:3610 */         if ((var58) || ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_))))
/* 3095:     */         {
/* 3096:3612 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3097:3613 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3098:3614 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3099:3615 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3100:3616 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3101:3617 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3102:3618 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3103:3619 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3104:     */         }
/* 3105:     */       }
/* 3106:3622 */       else if ((!var55) && (var56))
/* 3107:     */       {
/* 3108:3624 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, var151, var191);
/* 3109:3625 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, var151, var211);
/* 3110:3626 */         var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, var171, var211);
/* 3111:3627 */         var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, var171, var191);
/* 3112:3628 */         var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, dr1, d3r1);
/* 3113:3629 */         var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, dr1, d4r);
/* 3114:3630 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1r1, d4r);
/* 3115:3631 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1r1, d3r1);
/* 3116:3633 */         if ((!var54) && (!var53))
/* 3117:     */         {
/* 3118:3635 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var23, var27);
/* 3119:3636 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var23, var31);
/* 3120:3637 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var25, var31);
/* 3121:3638 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var25, var27);
/* 3122:3639 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var51, var23, var27);
/* 3123:3640 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var51, var23, var31);
/* 3124:3641 */           var6.addVertexWithUV(var35, p_147767_3_ + 0, var49, var25, var31);
/* 3125:3642 */           var6.addVertexWithUV(var35, p_147767_3_ + 1, var49, var25, var27);
/* 3126:     */         }
/* 3127:3645 */         if ((var57) || ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_))))
/* 3128:     */         {
/* 3129:3647 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3130:3648 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3131:3649 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3132:3650 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3133:3651 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3134:3652 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3135:3653 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3136:3654 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3137:     */         }
/* 3138:3657 */         if ((var58) || ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_))))
/* 3139:     */         {
/* 3140:3659 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3141:3660 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3142:3661 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3143:3662 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3144:3663 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3145:3664 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3146:3665 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3147:3666 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3148:     */         }
/* 3149:     */       }
/* 3150:     */     }
/* 3151:     */     else
/* 3152:     */     {
/* 3153:3672 */       var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, var65, var191);
/* 3154:3673 */       var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, var65, var211);
/* 3155:3674 */       var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, var171, var211);
/* 3156:3675 */       var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, var171, var191);
/* 3157:3676 */       var6.addVertexWithUV(var37, p_147767_3_ + 1, var41, dr1, d3r1);
/* 3158:3677 */       var6.addVertexWithUV(var37, p_147767_3_ + 0, var41, dr1, d4r);
/* 3159:3678 */       var6.addVertexWithUV(var33, p_147767_3_ + 0, var41, d2r1, d4r);
/* 3160:3679 */       var6.addVertexWithUV(var33, p_147767_3_ + 1, var41, d2r1, d3r1);
/* 3161:3681 */       if (var57)
/* 3162:     */       {
/* 3163:3683 */         var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3164:3684 */         var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3165:3685 */         var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3166:3686 */         var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3167:3687 */         var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3168:3688 */         var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3169:3689 */         var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3170:3690 */         var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3171:     */       }
/* 3172:     */       else
/* 3173:     */       {
/* 3174:3694 */         if ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ + 1, p_147767_4_)))
/* 3175:     */         {
/* 3176:3696 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3177:3697 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3178:3698 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3179:3699 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3180:3700 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3181:3701 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var51, var25, var31);
/* 3182:3702 */           var6.addVertexWithUV(var33, p_147767_3_ + 1 + 0.01D, var49, var23, var31);
/* 3183:3703 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3184:     */         }
/* 3185:3706 */         if ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ + 1, p_147767_4_)))
/* 3186:     */         {
/* 3187:3708 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3188:3709 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3189:3710 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3190:3711 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3191:3712 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var51, var25, var27);
/* 3192:3713 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var51, var25, var29);
/* 3193:3714 */           var6.addVertexWithUV(var35, p_147767_3_ + 1 + 0.01D, var49, var23, var29);
/* 3194:3715 */           var6.addVertexWithUV(var37, p_147767_3_ + 1 + 0.01D, var49, var23, var27);
/* 3195:     */         }
/* 3196:     */       }
/* 3197:3719 */       if (var58)
/* 3198:     */       {
/* 3199:3721 */         var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3200:3722 */         var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3201:3723 */         var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3202:3724 */         var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3203:3725 */         var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3204:3726 */         var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3205:3727 */         var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3206:3728 */         var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3207:     */       }
/* 3208:     */       else
/* 3209:     */       {
/* 3210:3732 */         if ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_ - 1, p_147767_3_ - 1, p_147767_4_)))
/* 3211:     */         {
/* 3212:3734 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3213:3735 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3214:3736 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3215:3737 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3216:3738 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3217:3739 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var51, var25, var31);
/* 3218:3740 */           var6.addVertexWithUV(var33, p_147767_3_ - 0.01D, var49, var23, var31);
/* 3219:3741 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3220:     */         }
/* 3221:3744 */         if ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_ + 1, p_147767_3_ - 1, p_147767_4_)))
/* 3222:     */         {
/* 3223:3746 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3224:3747 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3225:3748 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3226:3749 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3227:3750 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var51, var25, var27);
/* 3228:3751 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var51, var25, var29);
/* 3229:3752 */           var6.addVertexWithUV(var35, p_147767_3_ - 0.01D, var49, var23, var29);
/* 3230:3753 */           var6.addVertexWithUV(var37, p_147767_3_ - 0.01D, var49, var23, var27);
/* 3231:     */         }
/* 3232:     */       }
/* 3233:     */     }
/* 3234:3758 */     if (((!var53) || (!var54)) && ((var55) || (var56) || (var53) || (var54)))
/* 3235:     */     {
/* 3236:3760 */       if ((var53) && (!var54))
/* 3237:     */       {
/* 3238:3762 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, dz, d3z);
/* 3239:3763 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, dz, d4z);
/* 3240:3764 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1z, d4z);
/* 3241:3765 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1z, d3z);
/* 3242:3766 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1zr, d3zr);
/* 3243:3767 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1zr, d4zr);
/* 3244:3768 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, d2zr, d4zr);
/* 3245:3769 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, d2zr, d3zr);
/* 3246:3771 */         if ((!var56) && (!var55))
/* 3247:     */         {
/* 3248:3773 */           var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var23, var27);
/* 3249:3774 */           var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var23, var31);
/* 3250:3775 */           var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var25, var31);
/* 3251:3776 */           var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var25, var27);
/* 3252:3777 */           var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var23, var27);
/* 3253:3778 */           var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var23, var31);
/* 3254:3779 */           var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var25, var31);
/* 3255:3780 */           var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var25, var27);
/* 3256:     */         }
/* 3257:3783 */         if ((var57) || ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1))))
/* 3258:     */         {
/* 3259:3785 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var25, var27);
/* 3260:3786 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var25, var29);
/* 3261:3787 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var23, var29);
/* 3262:3788 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var23, var27);
/* 3263:3789 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var25, var27);
/* 3264:3790 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var25, var29);
/* 3265:3791 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var23, var29);
/* 3266:3792 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var23, var27);
/* 3267:     */         }
/* 3268:3795 */         if ((var58) || ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1))))
/* 3269:     */         {
/* 3270:3797 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var25, var27);
/* 3271:3798 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var25, var29);
/* 3272:3799 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var23, var29);
/* 3273:3800 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var23, var27);
/* 3274:3801 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var25, var27);
/* 3275:3802 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var25, var29);
/* 3276:3803 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var23, var29);
/* 3277:3804 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var23, var27);
/* 3278:     */         }
/* 3279:     */       }
/* 3280:3807 */       else if ((!var53) && (var54))
/* 3281:     */       {
/* 3282:3809 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1z, d3z);
/* 3283:3810 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1z, d4z);
/* 3284:3811 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, d2z, d4z);
/* 3285:3812 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, d2z, d3z);
/* 3286:3813 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, dzr, d3zr);
/* 3287:3814 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, dzr, d4zr);
/* 3288:3815 */         var6.addVertexWithUV(var35, p_147767_3_ + 0, var41, d1zr, d4zr);
/* 3289:3816 */         var6.addVertexWithUV(var35, p_147767_3_ + 1, var41, d1zr, d3zr);
/* 3290:3818 */         if ((!var56) && (!var55))
/* 3291:     */         {
/* 3292:3820 */           var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var23, var27);
/* 3293:3821 */           var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var23, var31);
/* 3294:3822 */           var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var25, var31);
/* 3295:3823 */           var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var25, var27);
/* 3296:3824 */           var6.addVertexWithUV(var45, p_147767_3_ + 1, var41, var23, var27);
/* 3297:3825 */           var6.addVertexWithUV(var45, p_147767_3_ + 0, var41, var23, var31);
/* 3298:3826 */           var6.addVertexWithUV(var47, p_147767_3_ + 0, var41, var25, var31);
/* 3299:3827 */           var6.addVertexWithUV(var47, p_147767_3_ + 1, var41, var25, var27);
/* 3300:     */         }
/* 3301:3830 */         if ((var57) || ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1))))
/* 3302:     */         {
/* 3303:3832 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var23, var29);
/* 3304:3833 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var31);
/* 3305:3834 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var31);
/* 3306:3835 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var25, var29);
/* 3307:3836 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var29);
/* 3308:3837 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var23, var31);
/* 3309:3838 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var25, var31);
/* 3310:3839 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var29);
/* 3311:     */         }
/* 3312:3842 */         if ((var58) || ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1))))
/* 3313:     */         {
/* 3314:3844 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var23, var29);
/* 3315:3845 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var31);
/* 3316:3846 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var31);
/* 3317:3847 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var25, var29);
/* 3318:3848 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var29);
/* 3319:3849 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var23, var31);
/* 3320:3850 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var25, var31);
/* 3321:3851 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var29);
/* 3322:     */         }
/* 3323:     */       }
/* 3324:     */     }
/* 3325:     */     else
/* 3326:     */     {
/* 3327:3857 */       var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, dzr, d3zr);
/* 3328:3858 */       var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, dzr, d4zr);
/* 3329:3859 */       var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, d2zr, d4zr);
/* 3330:3860 */       var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, d2zr, d3zr);
/* 3331:3861 */       var6.addVertexWithUV(var35, p_147767_3_ + 1, var39, dz, d3z);
/* 3332:3862 */       var6.addVertexWithUV(var35, p_147767_3_ + 0, var39, dz, d4z);
/* 3333:3863 */       var6.addVertexWithUV(var35, p_147767_3_ + 0, var43, d2z, d4z);
/* 3334:3864 */       var6.addVertexWithUV(var35, p_147767_3_ + 1, var43, d2z, d3z);
/* 3335:3866 */       if (var57)
/* 3336:     */       {
/* 3337:3868 */         var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var31);
/* 3338:3869 */         var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var25, var27);
/* 3339:3870 */         var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var23, var27);
/* 3340:3871 */         var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var31);
/* 3341:3872 */         var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var25, var31);
/* 3342:3873 */         var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var27);
/* 3343:3874 */         var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var27);
/* 3344:3875 */         var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var23, var31);
/* 3345:     */       }
/* 3346:     */       else
/* 3347:     */       {
/* 3348:3879 */         if ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ - 1)))
/* 3349:     */         {
/* 3350:3881 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var25, var27);
/* 3351:3882 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var25, var29);
/* 3352:3883 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var23, var29);
/* 3353:3884 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var23, var27);
/* 3354:3885 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var25, var27);
/* 3355:3886 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var39, var25, var29);
/* 3356:3887 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var39, var23, var29);
/* 3357:3888 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var23, var27);
/* 3358:     */         }
/* 3359:3891 */         if ((p_147767_3_ < var5 - 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ + 1, p_147767_4_ + 1)))
/* 3360:     */         {
/* 3361:3893 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var23, var29);
/* 3362:3894 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var31);
/* 3363:3895 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var31);
/* 3364:3896 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var25, var29);
/* 3365:3897 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var43, var23, var29);
/* 3366:3898 */           var6.addVertexWithUV(var45, p_147767_3_ + 1 + 0.005D, var41, var23, var31);
/* 3367:3899 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var41, var25, var31);
/* 3368:3900 */           var6.addVertexWithUV(var47, p_147767_3_ + 1 + 0.005D, var43, var25, var29);
/* 3369:     */         }
/* 3370:     */       }
/* 3371:3904 */       if (var58)
/* 3372:     */       {
/* 3373:3906 */         var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var31);
/* 3374:3907 */         var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var25, var27);
/* 3375:3908 */         var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var23, var27);
/* 3376:3909 */         var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var31);
/* 3377:3910 */         var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var25, var31);
/* 3378:3911 */         var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var27);
/* 3379:3912 */         var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var27);
/* 3380:3913 */         var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var23, var31);
/* 3381:     */       }
/* 3382:     */       else
/* 3383:     */       {
/* 3384:3917 */         if ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ - 1)))
/* 3385:     */         {
/* 3386:3919 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var25, var27);
/* 3387:3920 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var25, var29);
/* 3388:3921 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var23, var29);
/* 3389:3922 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var23, var27);
/* 3390:3923 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var25, var27);
/* 3391:3924 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var39, var25, var29);
/* 3392:3925 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var39, var23, var29);
/* 3393:3926 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var23, var27);
/* 3394:     */         }
/* 3395:3929 */         if ((p_147767_3_ > 1) && (this.blockAccess.isAirBlock(p_147767_2_, p_147767_3_ - 1, p_147767_4_ + 1)))
/* 3396:     */         {
/* 3397:3931 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var23, var29);
/* 3398:3932 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var31);
/* 3399:3933 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var31);
/* 3400:3934 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var25, var29);
/* 3401:3935 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var43, var23, var29);
/* 3402:3936 */           var6.addVertexWithUV(var45, p_147767_3_ - 0.005D, var41, var23, var31);
/* 3403:3937 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var41, var25, var31);
/* 3404:3938 */           var6.addVertexWithUV(var47, p_147767_3_ - 0.005D, var43, var25, var29);
/* 3405:     */         }
/* 3406:     */       }
/* 3407:     */     }
/* 3408:3943 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147767_2_, p_147767_3_, p_147767_4_))) {
/* 3409:3945 */       renderSnow(p_147767_2_, p_147767_3_, p_147767_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 3410:     */     }
/* 3411:3948 */     return true;
/* 3412:     */   }
/* 3413:     */   
/* 3414:     */   public boolean renderCrossedSquares(Block p_147746_1_, int p_147746_2_, int p_147746_3_, int p_147746_4_)
/* 3415:     */   {
/* 3416:3953 */     Tessellator var5 = Tessellator.instance;
/* 3417:3954 */     var5.setBrightness(p_147746_1_.getBlockBrightness(this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_));
/* 3418:3955 */     int var6 = CustomColorizer.getColorMultiplier(p_147746_1_, this.blockAccess, p_147746_2_, p_147746_3_, p_147746_4_);
/* 3419:3956 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/* 3420:3957 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/* 3421:3958 */     float var9 = (var6 & 0xFF) / 255.0F;
/* 3422:3960 */     if (EntityRenderer.anaglyphEnable)
/* 3423:     */     {
/* 3424:3962 */       float var18 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
/* 3425:3963 */       float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
/* 3426:3964 */       float var19 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
/* 3427:3965 */       var7 = var18;
/* 3428:3966 */       var8 = var11;
/* 3429:3967 */       var9 = var19;
/* 3430:     */     }
/* 3431:3970 */     var5.setColorOpaque_F(var7, var8, var9);
/* 3432:3971 */     double var181 = p_147746_2_;
/* 3433:3972 */     double var191 = p_147746_3_;
/* 3434:3973 */     double var14 = p_147746_4_;
/* 3435:3976 */     if (p_147746_1_ == Blocks.tallgrass)
/* 3436:     */     {
/* 3437:3978 */       long var16 = p_147746_2_ * 3129871 ^ p_147746_4_ * 116129781L ^ p_147746_3_;
/* 3438:3979 */       var16 = var16 * var16 * 42317861L + var16 * 11L;
/* 3439:3980 */       var181 += ((float)(var16 >> 16 & 0xF) / 15.0F - 0.5D) * 0.5D;
/* 3440:3981 */       var191 += ((float)(var16 >> 20 & 0xF) / 15.0F - 1.0D) * 0.2D;
/* 3441:3982 */       var14 += ((float)(var16 >> 24 & 0xF) / 15.0F - 0.5D) * 0.5D;
/* 3442:     */     }
/* 3443:3984 */     else if ((p_147746_1_ == Blocks.red_flower) || (p_147746_1_ == Blocks.yellow_flower))
/* 3444:     */     {
/* 3445:3986 */       long var16 = p_147746_2_ * 3129871 ^ p_147746_4_ * 116129781L ^ p_147746_3_;
/* 3446:3987 */       var16 = var16 * var16 * 42317861L + var16 * 11L;
/* 3447:3988 */       var181 += ((float)(var16 >> 16 & 0xF) / 15.0F - 0.5D) * 0.3D;
/* 3448:3989 */       var14 += ((float)(var16 >> 24 & 0xF) / 15.0F - 0.5D) * 0.3D;
/* 3449:     */     }
/* 3450:3992 */     IIcon var20 = getBlockIconFromSideAndMetadata(p_147746_1_, 0, this.blockAccess.getBlockMetadata(p_147746_2_, p_147746_3_, p_147746_4_));
/* 3451:3994 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null)) {
/* 3452:3996 */       var20 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147746_1_, p_147746_2_, p_147746_3_, p_147746_4_, 2, var20);
/* 3453:     */     }
/* 3454:3999 */     drawCrossedSquares(var20, var181, var191, var14, 1.0F);
/* 3455:4001 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147746_2_, p_147746_3_, p_147746_4_))) {
/* 3456:4003 */       renderSnow(p_147746_2_, p_147746_3_, p_147746_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 3457:     */     }
/* 3458:4006 */     return true;
/* 3459:     */   }
/* 3460:     */   
/* 3461:     */   public boolean renderBlockDoublePlant(BlockDoublePlant p_147774_1_, int p_147774_2_, int p_147774_3_, int p_147774_4_)
/* 3462:     */   {
/* 3463:4011 */     Tessellator var5 = Tessellator.instance;
/* 3464:4012 */     var5.setBrightness(p_147774_1_.getBlockBrightness(this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_));
/* 3465:4013 */     int var6 = CustomColorizer.getColorMultiplier(p_147774_1_, this.blockAccess, p_147774_2_, p_147774_3_, p_147774_4_);
/* 3466:4014 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/* 3467:4015 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/* 3468:4016 */     float var9 = (var6 & 0xFF) / 255.0F;
/* 3469:4018 */     if (EntityRenderer.anaglyphEnable)
/* 3470:     */     {
/* 3471:4020 */       float var58 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
/* 3472:4021 */       float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
/* 3473:4022 */       float var59 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
/* 3474:4023 */       var7 = var58;
/* 3475:4024 */       var8 = var11;
/* 3476:4025 */       var9 = var59;
/* 3477:     */     }
/* 3478:4028 */     var5.setColorOpaque_F(var7, var8, var9);
/* 3479:4029 */     long var581 = p_147774_2_ * 3129871 ^ p_147774_4_ * 116129781L;
/* 3480:4030 */     var581 = var581 * var581 * 42317861L + var581 * 11L;
/* 3481:4031 */     double var591 = p_147774_2_;
/* 3482:4032 */     double var14 = p_147774_3_;
/* 3483:4033 */     double var16 = p_147774_4_;
/* 3484:4034 */     var591 += ((float)(var581 >> 16 & 0xF) / 15.0F - 0.5D) * 0.3D;
/* 3485:4035 */     var16 += ((float)(var581 >> 24 & 0xF) / 15.0F - 0.5D) * 0.3D;
/* 3486:4036 */     int var18 = this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_, p_147774_4_);
/* 3487:4037 */     boolean var19 = false;
/* 3488:4038 */     boolean var20 = BlockDoublePlant.func_149887_c(var18);
/* 3489:     */     int var60;
/* 3490:     */     int var60;
/* 3491:4041 */     if (var20)
/* 3492:     */     {
/* 3493:4043 */       if (this.blockAccess.getBlock(p_147774_2_, p_147774_3_ - 1, p_147774_4_) != p_147774_1_) {
/* 3494:4045 */         return false;
/* 3495:     */       }
/* 3496:4048 */       var60 = BlockDoublePlant.func_149890_d(this.blockAccess.getBlockMetadata(p_147774_2_, p_147774_3_ - 1, p_147774_4_));
/* 3497:     */     }
/* 3498:     */     else
/* 3499:     */     {
/* 3500:4052 */       var60 = BlockDoublePlant.func_149890_d(var18);
/* 3501:     */     }
/* 3502:4055 */     IIcon var21 = p_147774_1_.func_149888_a(var20, var60);
/* 3503:4056 */     drawCrossedSquares(var21, var591, var14, var16, 1.0F);
/* 3504:4058 */     if ((var20) && (var60 == 0))
/* 3505:     */     {
/* 3506:4060 */       IIcon var22 = p_147774_1_.field_149891_b[0];
/* 3507:4061 */       double var23 = Math.cos(var581 * 0.8D) * 3.141592653589793D * 0.1D;
/* 3508:4062 */       double var25 = Math.cos(var23);
/* 3509:4063 */       double var27 = Math.sin(var23);
/* 3510:4064 */       double var29 = var22.getMinU();
/* 3511:4065 */       double var31 = var22.getMinV();
/* 3512:4066 */       double var33 = var22.getMaxU();
/* 3513:4067 */       double var35 = var22.getMaxV();
/* 3514:4068 */       double var37 = 0.3D;
/* 3515:4069 */       double var39 = -0.05D;
/* 3516:4070 */       double var41 = 0.5D + 0.3D * var25 - 0.5D * var27;
/* 3517:4071 */       double var43 = 0.5D + 0.5D * var25 + 0.3D * var27;
/* 3518:4072 */       double var45 = 0.5D + 0.3D * var25 + 0.5D * var27;
/* 3519:4073 */       double var47 = 0.5D + -0.5D * var25 + 0.3D * var27;
/* 3520:4074 */       double var49 = 0.5D + -0.05D * var25 + 0.5D * var27;
/* 3521:4075 */       double var51 = 0.5D + -0.5D * var25 + -0.05D * var27;
/* 3522:4076 */       double var53 = 0.5D + -0.05D * var25 - 0.5D * var27;
/* 3523:4077 */       double var55 = 0.5D + 0.5D * var25 + -0.05D * var27;
/* 3524:4078 */       var5.addVertexWithUV(var591 + var49, var14 + 1.0D, var16 + var51, var29, var35);
/* 3525:4079 */       var5.addVertexWithUV(var591 + var53, var14 + 1.0D, var16 + var55, var33, var35);
/* 3526:4080 */       var5.addVertexWithUV(var591 + var41, var14 + 0.0D, var16 + var43, var33, var31);
/* 3527:4081 */       var5.addVertexWithUV(var591 + var45, var14 + 0.0D, var16 + var47, var29, var31);
/* 3528:4082 */       IIcon var57 = p_147774_1_.field_149891_b[1];
/* 3529:4083 */       var29 = var57.getMinU();
/* 3530:4084 */       var31 = var57.getMinV();
/* 3531:4085 */       var33 = var57.getMaxU();
/* 3532:4086 */       var35 = var57.getMaxV();
/* 3533:4087 */       var5.addVertexWithUV(var591 + var53, var14 + 1.0D, var16 + var55, var29, var35);
/* 3534:4088 */       var5.addVertexWithUV(var591 + var49, var14 + 1.0D, var16 + var51, var33, var35);
/* 3535:4089 */       var5.addVertexWithUV(var591 + var45, var14 + 0.0D, var16 + var47, var33, var31);
/* 3536:4090 */       var5.addVertexWithUV(var591 + var41, var14 + 0.0D, var16 + var43, var29, var31);
/* 3537:     */     }
/* 3538:4093 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147774_2_, p_147774_3_, p_147774_4_))) {
/* 3539:4095 */       renderSnow(p_147774_2_, p_147774_3_, p_147774_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 3540:     */     }
/* 3541:4098 */     return true;
/* 3542:     */   }
/* 3543:     */   
/* 3544:     */   public boolean renderBlockStem(Block p_147724_1_, int p_147724_2_, int p_147724_3_, int p_147724_4_)
/* 3545:     */   {
/* 3546:4103 */     BlockStem var5 = (BlockStem)p_147724_1_;
/* 3547:4104 */     Tessellator var6 = Tessellator.instance;
/* 3548:4105 */     var6.setBrightness(var5.getBlockBrightness(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_));
/* 3549:4106 */     int var7 = CustomColorizer.getStemColorMultiplier(var5, this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
/* 3550:4107 */     float var8 = (var7 >> 16 & 0xFF) / 255.0F;
/* 3551:4108 */     float var9 = (var7 >> 8 & 0xFF) / 255.0F;
/* 3552:4109 */     float var10 = (var7 & 0xFF) / 255.0F;
/* 3553:4111 */     if (EntityRenderer.anaglyphEnable)
/* 3554:     */     {
/* 3555:4113 */       float var14 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
/* 3556:4114 */       float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
/* 3557:4115 */       float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
/* 3558:4116 */       var8 = var14;
/* 3559:4117 */       var9 = var12;
/* 3560:4118 */       var10 = var13;
/* 3561:     */     }
/* 3562:4121 */     var6.setColorOpaque_F(var8, var9, var10);
/* 3563:4122 */     var5.setBlockBoundsBasedOnState(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
/* 3564:4123 */     int var141 = var5.func_149873_e(this.blockAccess, p_147724_2_, p_147724_3_, p_147724_4_);
/* 3565:4125 */     if (var141 < 0)
/* 3566:     */     {
/* 3567:4127 */       renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), this.renderMaxY, p_147724_2_, p_147724_3_ - 0.0625F, p_147724_4_);
/* 3568:     */     }
/* 3569:     */     else
/* 3570:     */     {
/* 3571:4131 */       renderBlockStemSmall(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), 0.5D, p_147724_2_, p_147724_3_ - 0.0625F, p_147724_4_);
/* 3572:4132 */       renderBlockStemBig(var5, this.blockAccess.getBlockMetadata(p_147724_2_, p_147724_3_, p_147724_4_), var141, this.renderMaxY, p_147724_2_, p_147724_3_ - 0.0625F, p_147724_4_);
/* 3573:     */     }
/* 3574:4135 */     return true;
/* 3575:     */   }
/* 3576:     */   
/* 3577:     */   public boolean renderBlockCrops(Block p_147796_1_, int p_147796_2_, int p_147796_3_, int p_147796_4_)
/* 3578:     */   {
/* 3579:4140 */     Tessellator var5 = Tessellator.instance;
/* 3580:4141 */     var5.setBrightness(p_147796_1_.getBlockBrightness(this.blockAccess, p_147796_2_, p_147796_3_, p_147796_4_));
/* 3581:4142 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 3582:4143 */     renderBlockCropsImpl(p_147796_1_, this.blockAccess.getBlockMetadata(p_147796_2_, p_147796_3_, p_147796_4_), p_147796_2_, p_147796_3_ - 0.0625F, p_147796_4_);
/* 3583:4144 */     return true;
/* 3584:     */   }
/* 3585:     */   
/* 3586:     */   public void renderTorchAtAngle(Block p_147747_1_, double p_147747_2_, double p_147747_4_, double p_147747_6_, double p_147747_8_, double p_147747_10_, int p_147747_12_)
/* 3587:     */   {
/* 3588:4149 */     Tessellator var13 = Tessellator.instance;
/* 3589:4150 */     IIcon var14 = getBlockIconFromSideAndMetadata(p_147747_1_, 0, p_147747_12_);
/* 3590:4152 */     if (hasOverrideBlockTexture()) {
/* 3591:4154 */       var14 = this.overrideBlockTexture;
/* 3592:     */     }
/* 3593:4157 */     double var15 = var14.getMinU();
/* 3594:4158 */     double var17 = var14.getMinV();
/* 3595:4159 */     double var19 = var14.getMaxU();
/* 3596:4160 */     double var21 = var14.getMaxV();
/* 3597:4161 */     double var23 = var14.getInterpolatedU(7.0D);
/* 3598:4162 */     double var25 = var14.getInterpolatedV(6.0D);
/* 3599:4163 */     double var27 = var14.getInterpolatedU(9.0D);
/* 3600:4164 */     double var29 = var14.getInterpolatedV(8.0D);
/* 3601:4165 */     double var31 = var14.getInterpolatedU(7.0D);
/* 3602:4166 */     double var33 = var14.getInterpolatedV(13.0D);
/* 3603:4167 */     double var35 = var14.getInterpolatedU(9.0D);
/* 3604:4168 */     double var37 = var14.getInterpolatedV(15.0D);
/* 3605:4169 */     p_147747_2_ += 0.5D;
/* 3606:4170 */     p_147747_6_ += 0.5D;
/* 3607:4171 */     double var39 = p_147747_2_ - 0.5D;
/* 3608:4172 */     double var41 = p_147747_2_ + 0.5D;
/* 3609:4173 */     double var43 = p_147747_6_ - 0.5D;
/* 3610:4174 */     double var45 = p_147747_6_ + 0.5D;
/* 3611:4175 */     double var47 = 0.0625D;
/* 3612:4176 */     double var49 = 0.625D;
/* 3613:4177 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0D - var49) - var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0D - var49) - var47, var23, var25);
/* 3614:4178 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0D - var49) - var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0D - var49) + var47, var23, var29);
/* 3615:4179 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0D - var49) + var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0D - var49) + var47, var27, var29);
/* 3616:4180 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ * (1.0D - var49) + var47, p_147747_4_ + var49, p_147747_6_ + p_147747_10_ * (1.0D - var49) - var47, var27, var25);
/* 3617:4181 */     var13.addVertexWithUV(p_147747_2_ + var47 + p_147747_8_, p_147747_4_, p_147747_6_ - var47 + p_147747_10_, var35, var33);
/* 3618:4182 */     var13.addVertexWithUV(p_147747_2_ + var47 + p_147747_8_, p_147747_4_, p_147747_6_ + var47 + p_147747_10_, var35, var37);
/* 3619:4183 */     var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_, p_147747_6_ + var47 + p_147747_10_, var31, var37);
/* 3620:4184 */     var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_, p_147747_6_ - var47 + p_147747_10_, var31, var33);
/* 3621:4185 */     var13.addVertexWithUV(p_147747_2_ - var47, p_147747_4_ + 1.0D, var43, var15, var17);
/* 3622:4186 */     var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_ + 0.0D, var43 + p_147747_10_, var15, var21);
/* 3623:4187 */     var13.addVertexWithUV(p_147747_2_ - var47 + p_147747_8_, p_147747_4_ + 0.0D, var45 + p_147747_10_, var19, var21);
/* 3624:4188 */     var13.addVertexWithUV(p_147747_2_ - var47, p_147747_4_ + 1.0D, var45, var19, var17);
/* 3625:4189 */     var13.addVertexWithUV(p_147747_2_ + var47, p_147747_4_ + 1.0D, var45, var15, var17);
/* 3626:4190 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var47, p_147747_4_ + 0.0D, var45 + p_147747_10_, var15, var21);
/* 3627:4191 */     var13.addVertexWithUV(p_147747_2_ + p_147747_8_ + var47, p_147747_4_ + 0.0D, var43 + p_147747_10_, var19, var21);
/* 3628:4192 */     var13.addVertexWithUV(p_147747_2_ + var47, p_147747_4_ + 1.0D, var43, var19, var17);
/* 3629:4193 */     var13.addVertexWithUV(var39, p_147747_4_ + 1.0D, p_147747_6_ + var47, var15, var17);
/* 3630:4194 */     var13.addVertexWithUV(var39 + p_147747_8_, p_147747_4_ + 0.0D, p_147747_6_ + var47 + p_147747_10_, var15, var21);
/* 3631:4195 */     var13.addVertexWithUV(var41 + p_147747_8_, p_147747_4_ + 0.0D, p_147747_6_ + var47 + p_147747_10_, var19, var21);
/* 3632:4196 */     var13.addVertexWithUV(var41, p_147747_4_ + 1.0D, p_147747_6_ + var47, var19, var17);
/* 3633:4197 */     var13.addVertexWithUV(var41, p_147747_4_ + 1.0D, p_147747_6_ - var47, var15, var17);
/* 3634:4198 */     var13.addVertexWithUV(var41 + p_147747_8_, p_147747_4_ + 0.0D, p_147747_6_ - var47 + p_147747_10_, var15, var21);
/* 3635:4199 */     var13.addVertexWithUV(var39 + p_147747_8_, p_147747_4_ + 0.0D, p_147747_6_ - var47 + p_147747_10_, var19, var21);
/* 3636:4200 */     var13.addVertexWithUV(var39, p_147747_4_ + 1.0D, p_147747_6_ - var47, var19, var17);
/* 3637:     */   }
/* 3638:     */   
/* 3639:     */   public void drawCrossedSquares(IIcon p_147765_1_, double p_147765_2_, double p_147765_4_, double p_147765_6_, float p_147765_8_)
/* 3640:     */   {
/* 3641:4205 */     Tessellator var9 = Tessellator.instance;
/* 3642:4207 */     if (hasOverrideBlockTexture()) {
/* 3643:4209 */       p_147765_1_ = this.overrideBlockTexture;
/* 3644:     */     }
/* 3645:4212 */     double var10 = p_147765_1_.getMinU();
/* 3646:4213 */     double var12 = p_147765_1_.getMinV();
/* 3647:4214 */     double var14 = p_147765_1_.getMaxU();
/* 3648:4215 */     double var16 = p_147765_1_.getMaxV();
/* 3649:4216 */     double var18 = 0.45D * p_147765_8_;
/* 3650:4217 */     double var20 = p_147765_2_ + 0.5D - var18;
/* 3651:4218 */     double var22 = p_147765_2_ + 0.5D + var18;
/* 3652:4219 */     double var24 = p_147765_6_ + 0.5D - var18;
/* 3653:4220 */     double var26 = p_147765_6_ + 0.5D + var18;
/* 3654:4221 */     var9.addVertexWithUV(var20, p_147765_4_ + p_147765_8_, var24, var10, var12);
/* 3655:4222 */     var9.addVertexWithUV(var20, p_147765_4_ + 0.0D, var24, var10, var16);
/* 3656:4223 */     var9.addVertexWithUV(var22, p_147765_4_ + 0.0D, var26, var14, var16);
/* 3657:4224 */     var9.addVertexWithUV(var22, p_147765_4_ + p_147765_8_, var26, var14, var12);
/* 3658:4225 */     var9.addVertexWithUV(var22, p_147765_4_ + p_147765_8_, var26, var10, var12);
/* 3659:4226 */     var9.addVertexWithUV(var22, p_147765_4_ + 0.0D, var26, var10, var16);
/* 3660:4227 */     var9.addVertexWithUV(var20, p_147765_4_ + 0.0D, var24, var14, var16);
/* 3661:4228 */     var9.addVertexWithUV(var20, p_147765_4_ + p_147765_8_, var24, var14, var12);
/* 3662:4229 */     var9.addVertexWithUV(var20, p_147765_4_ + p_147765_8_, var26, var10, var12);
/* 3663:4230 */     var9.addVertexWithUV(var20, p_147765_4_ + 0.0D, var26, var10, var16);
/* 3664:4231 */     var9.addVertexWithUV(var22, p_147765_4_ + 0.0D, var24, var14, var16);
/* 3665:4232 */     var9.addVertexWithUV(var22, p_147765_4_ + p_147765_8_, var24, var14, var12);
/* 3666:4233 */     var9.addVertexWithUV(var22, p_147765_4_ + p_147765_8_, var24, var10, var12);
/* 3667:4234 */     var9.addVertexWithUV(var22, p_147765_4_ + 0.0D, var24, var10, var16);
/* 3668:4235 */     var9.addVertexWithUV(var20, p_147765_4_ + 0.0D, var26, var14, var16);
/* 3669:4236 */     var9.addVertexWithUV(var20, p_147765_4_ + p_147765_8_, var26, var14, var12);
/* 3670:     */   }
/* 3671:     */   
/* 3672:     */   public void renderBlockStemSmall(Block p_147730_1_, int p_147730_2_, double p_147730_3_, double p_147730_5_, double p_147730_7_, double p_147730_9_)
/* 3673:     */   {
/* 3674:4241 */     Tessellator var11 = Tessellator.instance;
/* 3675:4242 */     IIcon var12 = getBlockIconFromSideAndMetadata(p_147730_1_, 0, p_147730_2_);
/* 3676:4244 */     if (hasOverrideBlockTexture()) {
/* 3677:4246 */       var12 = this.overrideBlockTexture;
/* 3678:     */     }
/* 3679:4249 */     double var13 = var12.getMinU();
/* 3680:4250 */     double var15 = var12.getMinV();
/* 3681:4251 */     double var17 = var12.getMaxU();
/* 3682:4252 */     double var19 = var12.getInterpolatedV(p_147730_3_ * 16.0D);
/* 3683:4253 */     double var21 = p_147730_5_ + 0.5D - 0.449999988079071D;
/* 3684:4254 */     double var23 = p_147730_5_ + 0.5D + 0.449999988079071D;
/* 3685:4255 */     double var25 = p_147730_9_ + 0.5D - 0.449999988079071D;
/* 3686:4256 */     double var27 = p_147730_9_ + 0.5D + 0.449999988079071D;
/* 3687:4257 */     var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var25, var13, var15);
/* 3688:4258 */     var11.addVertexWithUV(var21, p_147730_7_ + 0.0D, var25, var13, var19);
/* 3689:4259 */     var11.addVertexWithUV(var23, p_147730_7_ + 0.0D, var27, var17, var19);
/* 3690:4260 */     var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var27, var17, var15);
/* 3691:4261 */     var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var27, var17, var15);
/* 3692:4262 */     var11.addVertexWithUV(var23, p_147730_7_ + 0.0D, var27, var17, var19);
/* 3693:4263 */     var11.addVertexWithUV(var21, p_147730_7_ + 0.0D, var25, var13, var19);
/* 3694:4264 */     var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var25, var13, var15);
/* 3695:4265 */     var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var27, var13, var15);
/* 3696:4266 */     var11.addVertexWithUV(var21, p_147730_7_ + 0.0D, var27, var13, var19);
/* 3697:4267 */     var11.addVertexWithUV(var23, p_147730_7_ + 0.0D, var25, var17, var19);
/* 3698:4268 */     var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var25, var17, var15);
/* 3699:4269 */     var11.addVertexWithUV(var23, p_147730_7_ + p_147730_3_, var25, var17, var15);
/* 3700:4270 */     var11.addVertexWithUV(var23, p_147730_7_ + 0.0D, var25, var17, var19);
/* 3701:4271 */     var11.addVertexWithUV(var21, p_147730_7_ + 0.0D, var27, var13, var19);
/* 3702:4272 */     var11.addVertexWithUV(var21, p_147730_7_ + p_147730_3_, var27, var13, var15);
/* 3703:     */   }
/* 3704:     */   
/* 3705:     */   public boolean renderBlockLilyPad(Block p_147783_1_, int p_147783_2_, int p_147783_3_, int p_147783_4_)
/* 3706:     */   {
/* 3707:4277 */     Tessellator var5 = Tessellator.instance;
/* 3708:4278 */     IIcon var6 = getBlockIconFromSide(p_147783_1_, 1);
/* 3709:4280 */     if (hasOverrideBlockTexture()) {
/* 3710:4282 */       var6 = this.overrideBlockTexture;
/* 3711:     */     }
/* 3712:4285 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null)) {
/* 3713:4287 */       var6 = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147783_1_, p_147783_2_, p_147783_3_, p_147783_4_, 1, var6);
/* 3714:     */     }
/* 3715:4290 */     float var7 = 0.015625F;
/* 3716:4291 */     double var8 = var6.getMinU();
/* 3717:4292 */     double var10 = var6.getMinV();
/* 3718:4293 */     double var12 = var6.getMaxU();
/* 3719:4294 */     double var14 = var6.getMaxV();
/* 3720:4295 */     long var16 = p_147783_2_ * 3129871 ^ p_147783_4_ * 116129781L ^ p_147783_3_;
/* 3721:4296 */     var16 = var16 * var16 * 42317861L + var16 * 11L;
/* 3722:4297 */     int var18 = (int)(var16 >> 16 & 0x3);
/* 3723:4298 */     var5.setBrightness(p_147783_1_.getBlockBrightness(this.blockAccess, p_147783_2_, p_147783_3_, p_147783_4_));
/* 3724:4299 */     float var19 = p_147783_2_ + 0.5F;
/* 3725:4300 */     float var20 = p_147783_4_ + 0.5F;
/* 3726:4301 */     float var21 = (var18 & 0x1) * 0.5F * (1 - var18 / 2 % 2 * 2);
/* 3727:4302 */     float var22 = (var18 + 1 & 0x1) * 0.5F * (1 - (var18 + 1) / 2 % 2 * 2);
/* 3728:4303 */     int col = CustomColorizer.getLilypadColor();
/* 3729:4304 */     var5.setColorOpaque_I(col);
/* 3730:4305 */     var5.addVertexWithUV(var19 + var21 - var22, p_147783_3_ + var7, var20 + var21 + var22, var8, var10);
/* 3731:4306 */     var5.addVertexWithUV(var19 + var21 + var22, p_147783_3_ + var7, var20 - var21 + var22, var12, var10);
/* 3732:4307 */     var5.addVertexWithUV(var19 - var21 + var22, p_147783_3_ + var7, var20 - var21 - var22, var12, var14);
/* 3733:4308 */     var5.addVertexWithUV(var19 - var21 - var22, p_147783_3_ + var7, var20 + var21 - var22, var8, var14);
/* 3734:4309 */     var5.setColorOpaque_I((col & 0xFEFEFE) >> 1);
/* 3735:4310 */     var5.addVertexWithUV(var19 - var21 - var22, p_147783_3_ + var7, var20 + var21 - var22, var8, var14);
/* 3736:4311 */     var5.addVertexWithUV(var19 - var21 + var22, p_147783_3_ + var7, var20 - var21 - var22, var12, var14);
/* 3737:4312 */     var5.addVertexWithUV(var19 + var21 + var22, p_147783_3_ + var7, var20 - var21 + var22, var12, var10);
/* 3738:4313 */     var5.addVertexWithUV(var19 + var21 - var22, p_147783_3_ + var7, var20 + var21 + var22, var8, var10);
/* 3739:4314 */     return true;
/* 3740:     */   }
/* 3741:     */   
/* 3742:     */   public void renderBlockStemBig(BlockStem p_147740_1_, int p_147740_2_, int p_147740_3_, double p_147740_4_, double p_147740_6_, double p_147740_8_, double p_147740_10_)
/* 3743:     */   {
/* 3744:4319 */     Tessellator var12 = Tessellator.instance;
/* 3745:4320 */     IIcon var13 = p_147740_1_.func_149872_i();
/* 3746:4322 */     if (hasOverrideBlockTexture()) {
/* 3747:4324 */       var13 = this.overrideBlockTexture;
/* 3748:     */     }
/* 3749:4327 */     double var14 = var13.getMinU();
/* 3750:4328 */     double var16 = var13.getMinV();
/* 3751:4329 */     double var18 = var13.getMaxU();
/* 3752:4330 */     double var20 = var13.getMaxV();
/* 3753:4331 */     double var22 = p_147740_6_ + 0.5D - 0.5D;
/* 3754:4332 */     double var24 = p_147740_6_ + 0.5D + 0.5D;
/* 3755:4333 */     double var26 = p_147740_10_ + 0.5D - 0.5D;
/* 3756:4334 */     double var28 = p_147740_10_ + 0.5D + 0.5D;
/* 3757:4335 */     double var30 = p_147740_6_ + 0.5D;
/* 3758:4336 */     double var32 = p_147740_10_ + 0.5D;
/* 3759:4338 */     if ((p_147740_3_ + 1) / 2 % 2 == 1)
/* 3760:     */     {
/* 3761:4340 */       double var34 = var18;
/* 3762:4341 */       var18 = var14;
/* 3763:4342 */       var14 = var34;
/* 3764:     */     }
/* 3765:4345 */     if (p_147740_3_ < 2)
/* 3766:     */     {
/* 3767:4347 */       var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var32, var14, var16);
/* 3768:4348 */       var12.addVertexWithUV(var22, p_147740_8_ + 0.0D, var32, var14, var20);
/* 3769:4349 */       var12.addVertexWithUV(var24, p_147740_8_ + 0.0D, var32, var18, var20);
/* 3770:4350 */       var12.addVertexWithUV(var24, p_147740_8_ + p_147740_4_, var32, var18, var16);
/* 3771:4351 */       var12.addVertexWithUV(var24, p_147740_8_ + p_147740_4_, var32, var18, var16);
/* 3772:4352 */       var12.addVertexWithUV(var24, p_147740_8_ + 0.0D, var32, var18, var20);
/* 3773:4353 */       var12.addVertexWithUV(var22, p_147740_8_ + 0.0D, var32, var14, var20);
/* 3774:4354 */       var12.addVertexWithUV(var22, p_147740_8_ + p_147740_4_, var32, var14, var16);
/* 3775:     */     }
/* 3776:     */     else
/* 3777:     */     {
/* 3778:4358 */       var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var28, var14, var16);
/* 3779:4359 */       var12.addVertexWithUV(var30, p_147740_8_ + 0.0D, var28, var14, var20);
/* 3780:4360 */       var12.addVertexWithUV(var30, p_147740_8_ + 0.0D, var26, var18, var20);
/* 3781:4361 */       var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var26, var18, var16);
/* 3782:4362 */       var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var26, var18, var16);
/* 3783:4363 */       var12.addVertexWithUV(var30, p_147740_8_ + 0.0D, var26, var18, var20);
/* 3784:4364 */       var12.addVertexWithUV(var30, p_147740_8_ + 0.0D, var28, var14, var20);
/* 3785:4365 */       var12.addVertexWithUV(var30, p_147740_8_ + p_147740_4_, var28, var14, var16);
/* 3786:     */     }
/* 3787:     */   }
/* 3788:     */   
/* 3789:     */   public void renderBlockCropsImpl(Block p_147795_1_, int p_147795_2_, double p_147795_3_, double p_147795_5_, double p_147795_7_)
/* 3790:     */   {
/* 3791:4371 */     Tessellator var9 = Tessellator.instance;
/* 3792:4372 */     IIcon var10 = getBlockIconFromSideAndMetadata(p_147795_1_, 0, p_147795_2_);
/* 3793:4374 */     if (hasOverrideBlockTexture()) {
/* 3794:4376 */       var10 = this.overrideBlockTexture;
/* 3795:     */     }
/* 3796:4379 */     double var11 = var10.getMinU();
/* 3797:4380 */     double var13 = var10.getMinV();
/* 3798:4381 */     double var15 = var10.getMaxU();
/* 3799:4382 */     double var17 = var10.getMaxV();
/* 3800:4383 */     double var19 = p_147795_3_ + 0.5D - 0.25D;
/* 3801:4384 */     double var21 = p_147795_3_ + 0.5D + 0.25D;
/* 3802:4385 */     double var23 = p_147795_7_ + 0.5D - 0.5D;
/* 3803:4386 */     double var25 = p_147795_7_ + 0.5D + 0.5D;
/* 3804:4387 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var23, var11, var13);
/* 3805:4388 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var23, var11, var17);
/* 3806:4389 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var25, var15, var17);
/* 3807:4390 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var25, var15, var13);
/* 3808:4391 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var25, var11, var13);
/* 3809:4392 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var25, var11, var17);
/* 3810:4393 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var23, var15, var17);
/* 3811:4394 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var23, var15, var13);
/* 3812:4395 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var25, var11, var13);
/* 3813:4396 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var25, var11, var17);
/* 3814:4397 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var23, var15, var17);
/* 3815:4398 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var23, var15, var13);
/* 3816:4399 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var23, var11, var13);
/* 3817:4400 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var23, var11, var17);
/* 3818:4401 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var25, var15, var17);
/* 3819:4402 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var25, var15, var13);
/* 3820:4403 */     var19 = p_147795_3_ + 0.5D - 0.5D;
/* 3821:4404 */     var21 = p_147795_3_ + 0.5D + 0.5D;
/* 3822:4405 */     var23 = p_147795_7_ + 0.5D - 0.25D;
/* 3823:4406 */     var25 = p_147795_7_ + 0.5D + 0.25D;
/* 3824:4407 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var23, var11, var13);
/* 3825:4408 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var23, var11, var17);
/* 3826:4409 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var23, var15, var17);
/* 3827:4410 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var23, var15, var13);
/* 3828:4411 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var23, var11, var13);
/* 3829:4412 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var23, var11, var17);
/* 3830:4413 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var23, var15, var17);
/* 3831:4414 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var23, var15, var13);
/* 3832:4415 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var25, var11, var13);
/* 3833:4416 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var25, var11, var17);
/* 3834:4417 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var25, var15, var17);
/* 3835:4418 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var25, var15, var13);
/* 3836:4419 */     var9.addVertexWithUV(var19, p_147795_5_ + 1.0D, var25, var11, var13);
/* 3837:4420 */     var9.addVertexWithUV(var19, p_147795_5_ + 0.0D, var25, var11, var17);
/* 3838:4421 */     var9.addVertexWithUV(var21, p_147795_5_ + 0.0D, var25, var15, var17);
/* 3839:4422 */     var9.addVertexWithUV(var21, p_147795_5_ + 1.0D, var25, var15, var13);
/* 3840:     */   }
/* 3841:     */   
/* 3842:     */   public boolean renderBlockFluids(Block p_147721_1_, int p_147721_2_, int p_147721_3_, int p_147721_4_)
/* 3843:     */   {
/* 3844:4427 */     Tessellator var5 = Tessellator.instance;
/* 3845:4428 */     int var6 = CustomColorizer.getFluidColor(p_147721_1_, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_);
/* 3846:4429 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/* 3847:4430 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/* 3848:4431 */     float var9 = (var6 & 0xFF) / 255.0F;
/* 3849:4432 */     boolean var10 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ + 1, p_147721_4_, 1);
/* 3850:4433 */     boolean var11 = p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_, 0);
/* 3851:4434 */     boolean[] var12 = { p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ - 1, 2), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ + 1, 3), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ - 1, p_147721_3_, p_147721_4_, 4), p_147721_1_.shouldSideBeRendered(this.blockAccess, p_147721_2_ + 1, p_147721_3_, p_147721_4_, 5) };
/* 3852:4436 */     if ((!var10) && (!var11) && (var12[0] == 0) && (var12[1] == 0) && (var12[2] == 0) && (var12[3] == 0)) {
/* 3853:4438 */       return false;
/* 3854:     */     }
/* 3855:4442 */     boolean var13 = false;
/* 3856:4443 */     float var14 = 0.5F;
/* 3857:4444 */     float var15 = 1.0F;
/* 3858:4445 */     float var16 = 0.8F;
/* 3859:4446 */     float var17 = 0.6F;
/* 3860:4447 */     double var18 = 0.0D;
/* 3861:4448 */     double var20 = 1.0D;
/* 3862:4449 */     Material var22 = p_147721_1_.getMaterial();
/* 3863:4450 */     int var23 = this.blockAccess.getBlockMetadata(p_147721_2_, p_147721_3_, p_147721_4_);
/* 3864:4451 */     double var24 = getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_, var22);
/* 3865:4452 */     double var26 = getFluidHeight(p_147721_2_, p_147721_3_, p_147721_4_ + 1, var22);
/* 3866:4453 */     double var28 = getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_ + 1, var22);
/* 3867:4454 */     double var30 = getFluidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_, var22);
/* 3868:4455 */     double var32 = 0.001000000047497451D;
/* 3869:4466 */     if ((this.renderAllFaces) || (var10))
/* 3870:     */     {
/* 3871:4468 */       var13 = true;
/* 3872:4469 */       IIcon var57 = getBlockIconFromSideAndMetadata(p_147721_1_, 1, var23);
/* 3873:4470 */       float var58 = (float)BlockLiquid.func_149802_a(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, var22);
/* 3874:4472 */       if (var58 > -999.0F) {
/* 3875:4474 */         var57 = getBlockIconFromSideAndMetadata(p_147721_1_, 2, var23);
/* 3876:     */       }
/* 3877:4477 */       var24 -= var32;
/* 3878:4478 */       var26 -= var32;
/* 3879:4479 */       var28 -= var32;
/* 3880:4480 */       var30 -= var32;
/* 3881:     */       double var49;
/* 3882:     */       double var39;
/* 3883:     */       double var45;
/* 3884:     */       double var37;
/* 3885:     */       double var47;
/* 3886:     */       double var41;
/* 3887:     */       double var51;
/* 3888:     */       double var43;
/* 3889:     */       double var49;
/* 3890:4484 */       if (var58 < -999.0F)
/* 3891:     */       {
/* 3892:4486 */         double var39 = var57.getInterpolatedU(0.0D);
/* 3893:4487 */         double var45 = var57.getInterpolatedV(0.0D);
/* 3894:4488 */         double var37 = var39;
/* 3895:4489 */         double var47 = var57.getInterpolatedV(16.0D);
/* 3896:4490 */         double var41 = var57.getInterpolatedU(16.0D);
/* 3897:4491 */         double var51 = var47;
/* 3898:4492 */         double var43 = var41;
/* 3899:4493 */         var49 = var45;
/* 3900:     */       }
/* 3901:     */       else
/* 3902:     */       {
/* 3903:4497 */         float var52 = MathHelper.sin(var58) * 0.25F;
/* 3904:4498 */         float var53 = MathHelper.cos(var58) * 0.25F;
/* 3905:4499 */         float var54 = 8.0F;
/* 3906:4500 */         var39 = var57.getInterpolatedU(8.0F + (-var53 - var52) * 16.0F);
/* 3907:4501 */         var45 = var57.getInterpolatedV(8.0F + (-var53 + var52) * 16.0F);
/* 3908:4502 */         var37 = var57.getInterpolatedU(8.0F + (-var53 + var52) * 16.0F);
/* 3909:4503 */         var47 = var57.getInterpolatedV(8.0F + (var53 + var52) * 16.0F);
/* 3910:4504 */         var41 = var57.getInterpolatedU(8.0F + (var53 + var52) * 16.0F);
/* 3911:4505 */         var51 = var57.getInterpolatedV(8.0F + (var53 - var52) * 16.0F);
/* 3912:4506 */         var43 = var57.getInterpolatedU(8.0F + (var53 - var52) * 16.0F);
/* 3913:4507 */         var49 = var57.getInterpolatedV(8.0F + (-var53 - var52) * 16.0F);
/* 3914:     */       }
/* 3915:4510 */       var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_));
/* 3916:4511 */       var5.setColorOpaque_F(var15 * var7, var15 * var8, var15 * var9);
/* 3917:4512 */       double var56 = 3.90625E-005D;
/* 3918:4513 */       var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var24, p_147721_4_ + 0, var39 + var56, var45 + var56);
/* 3919:4514 */       var5.addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + var26, p_147721_4_ + 1, var37 + var56, var47 - var56);
/* 3920:4515 */       var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var28, p_147721_4_ + 1, var41 - var56, var51 - var56);
/* 3921:4516 */       var5.addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + var30, p_147721_4_ + 0, var43 - var56, var49 + var56);
/* 3922:     */     }
/* 3923:4519 */     if ((this.renderAllFaces) || (var11))
/* 3924:     */     {
/* 3925:4521 */       var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_));
/* 3926:4522 */       var5.setColorOpaque_F(var14 * var7, var14 * var8, var14 * var9);
/* 3927:4523 */       renderFaceYNeg(p_147721_1_, p_147721_2_, p_147721_3_ + var32, p_147721_4_, getBlockIconFromSide(p_147721_1_, 0));
/* 3928:4524 */       var13 = true;
/* 3929:     */     }
/* 3930:4527 */     for (int var571 = 0; var571 < 4; var571++)
/* 3931:     */     {
/* 3932:4529 */       int var581 = p_147721_2_;
/* 3933:4530 */       int var591 = p_147721_4_;
/* 3934:4532 */       if (var571 == 0) {
/* 3935:4534 */         var591 = p_147721_4_ - 1;
/* 3936:     */       }
/* 3937:4537 */       if (var571 == 1) {
/* 3938:4539 */         var591++;
/* 3939:     */       }
/* 3940:4542 */       if (var571 == 2) {
/* 3941:4544 */         var581 = p_147721_2_ - 1;
/* 3942:     */       }
/* 3943:4547 */       if (var571 == 3) {
/* 3944:4549 */         var581++;
/* 3945:     */       }
/* 3946:4552 */       IIcon var59 = getBlockIconFromSideAndMetadata(p_147721_1_, var571 + 2, var23);
/* 3947:4554 */       if ((this.renderAllFaces) || (var12[var571] != 0))
/* 3948:     */       {
/* 3949:     */         double var49;
/* 3950:     */         double var39;
/* 3951:     */         double var41;
/* 3952:     */         double var43;
/* 3953:     */         double var47;
/* 3954:     */         double var45;
/* 3955:     */         double var49;
/* 3956:4556 */         if (var571 == 0)
/* 3957:     */         {
/* 3958:4558 */           double var39 = var24;
/* 3959:4559 */           double var41 = var30;
/* 3960:4560 */           double var43 = p_147721_2_;
/* 3961:4561 */           double var47 = p_147721_2_ + 1;
/* 3962:4562 */           double var45 = p_147721_4_ + var32;
/* 3963:4563 */           var49 = p_147721_4_ + var32;
/* 3964:     */         }
/* 3965:     */         else
/* 3966:     */         {
/* 3967:     */           double var49;
/* 3968:4565 */           if (var571 == 1)
/* 3969:     */           {
/* 3970:4567 */             double var39 = var28;
/* 3971:4568 */             double var41 = var26;
/* 3972:4569 */             double var43 = p_147721_2_ + 1;
/* 3973:4570 */             double var47 = p_147721_2_;
/* 3974:4571 */             double var45 = p_147721_4_ + 1 - var32;
/* 3975:4572 */             var49 = p_147721_4_ + 1 - var32;
/* 3976:     */           }
/* 3977:     */           else
/* 3978:     */           {
/* 3979:     */             double var49;
/* 3980:4574 */             if (var571 == 2)
/* 3981:     */             {
/* 3982:4576 */               double var39 = var26;
/* 3983:4577 */               double var41 = var24;
/* 3984:4578 */               double var43 = p_147721_2_ + var32;
/* 3985:4579 */               double var47 = p_147721_2_ + var32;
/* 3986:4580 */               double var45 = p_147721_4_ + 1;
/* 3987:4581 */               var49 = p_147721_4_;
/* 3988:     */             }
/* 3989:     */             else
/* 3990:     */             {
/* 3991:4585 */               var39 = var30;
/* 3992:4586 */               var41 = var28;
/* 3993:4587 */               var43 = p_147721_2_ + 1 - var32;
/* 3994:4588 */               var47 = p_147721_2_ + 1 - var32;
/* 3995:4589 */               var45 = p_147721_4_;
/* 3996:4590 */               var49 = p_147721_4_ + 1;
/* 3997:     */             }
/* 3998:     */           }
/* 3999:     */         }
/* 4000:4593 */         var13 = true;
/* 4001:4594 */         float var60 = var59.getInterpolatedU(0.0D);
/* 4002:4595 */         float var52 = var59.getInterpolatedU(8.0D);
/* 4003:4596 */         float var53 = var59.getInterpolatedV((1.0D - var39) * 16.0D * 0.5D);
/* 4004:4597 */         float var54 = var59.getInterpolatedV((1.0D - var41) * 16.0D * 0.5D);
/* 4005:4598 */         float var55 = var59.getInterpolatedV(8.0D);
/* 4006:4599 */         var5.setBrightness(p_147721_1_.getBlockBrightness(this.blockAccess, var581, p_147721_3_, var591));
/* 4007:4600 */         float var61 = 1.0F;
/* 4008:4601 */         var61 *= (var571 < 2 ? var16 : var17);
/* 4009:4602 */         var5.setColorOpaque_F(var15 * var61 * var7, var15 * var61 * var8, var15 * var61 * var9);
/* 4010:4603 */         var5.addVertexWithUV(var43, p_147721_3_ + var39, var45, var60, var53);
/* 4011:4604 */         var5.addVertexWithUV(var47, p_147721_3_ + var41, var49, var52, var54);
/* 4012:4605 */         var5.addVertexWithUV(var47, p_147721_3_ + 0, var49, var52, var55);
/* 4013:4606 */         var5.addVertexWithUV(var43, p_147721_3_ + 0, var45, var60, var55);
/* 4014:     */       }
/* 4015:     */     }
/* 4016:4610 */     this.renderMinY = var18;
/* 4017:4611 */     this.renderMaxY = var20;
/* 4018:4612 */     return var13;
/* 4019:     */   }
/* 4020:     */   
/* 4021:     */   public float getFluidHeight(int p_147729_1_, int p_147729_2_, int p_147729_3_, Material p_147729_4_)
/* 4022:     */   {
/* 4023:4618 */     int var5 = 0;
/* 4024:4619 */     float var6 = 0.0F;
/* 4025:4621 */     for (int var7 = 0; var7 < 4; var7++)
/* 4026:     */     {
/* 4027:4623 */       int var8 = p_147729_1_ - (var7 & 0x1);
/* 4028:4624 */       int var10 = p_147729_3_ - (var7 >> 1 & 0x1);
/* 4029:4626 */       if (this.blockAccess.getBlock(var8, p_147729_2_ + 1, var10).getMaterial() == p_147729_4_) {
/* 4030:4628 */         return 1.0F;
/* 4031:     */       }
/* 4032:4631 */       Material var11 = this.blockAccess.getBlock(var8, p_147729_2_, var10).getMaterial();
/* 4033:4633 */       if (var11 == p_147729_4_)
/* 4034:     */       {
/* 4035:4635 */         int var12 = this.blockAccess.getBlockMetadata(var8, p_147729_2_, var10);
/* 4036:4637 */         if ((var12 >= 8) || (var12 == 0))
/* 4037:     */         {
/* 4038:4639 */           var6 += BlockLiquid.func_149801_b(var12) * 10.0F;
/* 4039:4640 */           var5 += 10;
/* 4040:     */         }
/* 4041:4643 */         var6 += BlockLiquid.func_149801_b(var12);
/* 4042:4644 */         var5++;
/* 4043:     */       }
/* 4044:4646 */       else if (!var11.isSolid())
/* 4045:     */       {
/* 4046:4648 */         var6 += 1.0F;
/* 4047:4649 */         var5++;
/* 4048:     */       }
/* 4049:     */     }
/* 4050:4653 */     return 1.0F - var6 / var5;
/* 4051:     */   }
/* 4052:     */   
/* 4053:     */   public void renderBlockSandFalling(Block p_147749_1_, World p_147749_2_, int p_147749_3_, int p_147749_4_, int p_147749_5_, int p_147749_6_)
/* 4054:     */   {
/* 4055:4658 */     float var7 = 0.5F;
/* 4056:4659 */     float var8 = 1.0F;
/* 4057:4660 */     float var9 = 0.8F;
/* 4058:4661 */     float var10 = 0.6F;
/* 4059:4662 */     Tessellator var11 = Tessellator.instance;
/* 4060:4663 */     var11.startDrawingQuads();
/* 4061:4664 */     var11.setBrightness(p_147749_1_.getBlockBrightness(p_147749_2_, p_147749_3_, p_147749_4_, p_147749_5_));
/* 4062:4665 */     var11.setColorOpaque_F(var7, var7, var7);
/* 4063:4666 */     renderFaceYNeg(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 0, p_147749_6_));
/* 4064:4667 */     var11.setColorOpaque_F(var8, var8, var8);
/* 4065:4668 */     renderFaceYPos(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 1, p_147749_6_));
/* 4066:4669 */     var11.setColorOpaque_F(var9, var9, var9);
/* 4067:4670 */     renderFaceZNeg(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 2, p_147749_6_));
/* 4068:4671 */     var11.setColorOpaque_F(var9, var9, var9);
/* 4069:4672 */     renderFaceZPos(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 3, p_147749_6_));
/* 4070:4673 */     var11.setColorOpaque_F(var10, var10, var10);
/* 4071:4674 */     renderFaceXNeg(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 4, p_147749_6_));
/* 4072:4675 */     var11.setColorOpaque_F(var10, var10, var10);
/* 4073:4676 */     renderFaceXPos(p_147749_1_, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(p_147749_1_, 5, p_147749_6_));
/* 4074:4677 */     var11.draw();
/* 4075:     */   }
/* 4076:     */   
/* 4077:     */   public boolean renderStandardBlock(Block p_147784_1_, int p_147784_2_, int p_147784_3_, int p_147784_4_)
/* 4078:     */   {
/* 4079:4685 */     int var5 = CustomColorizer.getColorMultiplier(p_147784_1_, this.blockAccess, p_147784_2_, p_147784_3_, p_147784_4_);
/* 4080:4686 */     float var6 = (var5 >> 16 & 0xFF) / 255.0F;
/* 4081:4687 */     float var7 = (var5 >> 8 & 0xFF) / 255.0F;
/* 4082:4688 */     float var8 = (var5 & 0xFF) / 255.0F;
/* 4083:4690 */     if (EntityRenderer.anaglyphEnable)
/* 4084:     */     {
/* 4085:4692 */       float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
/* 4086:4693 */       float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
/* 4087:4694 */       float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
/* 4088:4695 */       var6 = var9;
/* 4089:4696 */       var7 = var10;
/* 4090:4697 */       var8 = var11;
/* 4091:     */     }
/* 4092:4700 */     return (Minecraft.isAmbientOcclusionEnabled()) && (p_147784_1_.getLightValue() == 0) ? renderStandardBlockWithAmbientOcclusion(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8) : this.partialRenderBounds ? renderStandardBlockWithAmbientOcclusionPartial(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8) : renderStandardBlockWithColorMultiplier(p_147784_1_, p_147784_2_, p_147784_3_, p_147784_4_, var6, var7, var8);
/* 4093:     */   }
/* 4094:     */   
/* 4095:     */   public boolean renderBlockLog(Block p_147742_1_, int p_147742_2_, int p_147742_3_, int p_147742_4_)
/* 4096:     */   {
/* 4097:4705 */     int var5 = this.blockAccess.getBlockMetadata(p_147742_2_, p_147742_3_, p_147742_4_);
/* 4098:4706 */     int var6 = var5 & 0xC;
/* 4099:4708 */     if (var6 == 4)
/* 4100:     */     {
/* 4101:4710 */       this.uvRotateEast = 1;
/* 4102:4711 */       this.uvRotateWest = 1;
/* 4103:4712 */       this.uvRotateTop = 1;
/* 4104:4713 */       this.uvRotateBottom = 1;
/* 4105:     */     }
/* 4106:4715 */     else if (var6 == 8)
/* 4107:     */     {
/* 4108:4717 */       this.uvRotateSouth = 1;
/* 4109:4718 */       this.uvRotateNorth = 1;
/* 4110:     */     }
/* 4111:4721 */     boolean var7 = renderStandardBlock(p_147742_1_, p_147742_2_, p_147742_3_, p_147742_4_);
/* 4112:4722 */     this.uvRotateSouth = 0;
/* 4113:4723 */     this.uvRotateEast = 0;
/* 4114:4724 */     this.uvRotateWest = 0;
/* 4115:4725 */     this.uvRotateNorth = 0;
/* 4116:4726 */     this.uvRotateTop = 0;
/* 4117:4727 */     this.uvRotateBottom = 0;
/* 4118:4728 */     return var7;
/* 4119:     */   }
/* 4120:     */   
/* 4121:     */   public boolean renderBlockQuartz(Block p_147779_1_, int p_147779_2_, int p_147779_3_, int p_147779_4_)
/* 4122:     */   {
/* 4123:4733 */     int var5 = this.blockAccess.getBlockMetadata(p_147779_2_, p_147779_3_, p_147779_4_);
/* 4124:4735 */     if (var5 == 3)
/* 4125:     */     {
/* 4126:4737 */       this.uvRotateEast = 1;
/* 4127:4738 */       this.uvRotateWest = 1;
/* 4128:4739 */       this.uvRotateTop = 1;
/* 4129:4740 */       this.uvRotateBottom = 1;
/* 4130:     */     }
/* 4131:4742 */     else if (var5 == 4)
/* 4132:     */     {
/* 4133:4744 */       this.uvRotateSouth = 1;
/* 4134:4745 */       this.uvRotateNorth = 1;
/* 4135:     */     }
/* 4136:4748 */     boolean var6 = renderStandardBlock(p_147779_1_, p_147779_2_, p_147779_3_, p_147779_4_);
/* 4137:4749 */     this.uvRotateSouth = 0;
/* 4138:4750 */     this.uvRotateEast = 0;
/* 4139:4751 */     this.uvRotateWest = 0;
/* 4140:4752 */     this.uvRotateNorth = 0;
/* 4141:4753 */     this.uvRotateTop = 0;
/* 4142:4754 */     this.uvRotateBottom = 0;
/* 4143:4755 */     return var6;
/* 4144:     */   }
/* 4145:     */   
/* 4146:     */   public boolean renderStandardBlockWithAmbientOcclusion(Block p_147751_1_, int p_147751_2_, int p_147751_3_, int p_147751_4_, float p_147751_5_, float p_147751_6_, float p_147751_7_)
/* 4147:     */   {
/* 4148:4760 */     this.enableAO = true;
/* 4149:4761 */     boolean defaultTexture = Tessellator.instance.defaultTexture;
/* 4150:4762 */     boolean betterGrass = (Config.isBetterGrass()) && (defaultTexture);
/* 4151:4763 */     boolean simpleAO = p_147751_1_ == Blocks.glass;
/* 4152:4764 */     boolean var8 = false;
/* 4153:4765 */     float var9 = 0.0F;
/* 4154:4766 */     float var10 = 0.0F;
/* 4155:4767 */     float var11 = 0.0F;
/* 4156:4768 */     float var12 = 0.0F;
/* 4157:4769 */     boolean var13 = true;
/* 4158:4770 */     int var14 = -1;
/* 4159:4771 */     Tessellator var15 = Tessellator.instance;
/* 4160:4772 */     var15.setBrightness(983055);
/* 4161:4774 */     if (p_147751_1_ == Blocks.grass) {
/* 4162:4776 */       var13 = false;
/* 4163:4778 */     } else if (hasOverrideBlockTexture()) {
/* 4164:4780 */       var13 = false;
/* 4165:     */     }
/* 4166:4790 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_, 0)))
/* 4167:     */     {
/* 4168:4792 */       if (this.renderMinY <= 0.0D) {
/* 4169:4794 */         p_147751_3_--;
/* 4170:     */       }
/* 4171:4797 */       this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4172:4798 */       this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4173:4799 */       this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4174:4800 */       this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4175:4801 */       this.aoLightValueScratchXYNN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4176:4802 */       this.aoLightValueScratchYZNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4177:4803 */       this.aoLightValueScratchYZNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4178:4804 */       this.aoLightValueScratchXYPN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4179:4805 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
/* 4180:4806 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
/* 4181:4807 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
/* 4182:4808 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
/* 4183:4810 */       if ((!var19) && (!var17))
/* 4184:     */       {
/* 4185:4812 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
/* 4186:4813 */         this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
/* 4187:     */       }
/* 4188:     */       else
/* 4189:     */       {
/* 4190:4817 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
/* 4191:4818 */         this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
/* 4192:     */       }
/* 4193:4821 */       if ((!var18) && (!var17))
/* 4194:     */       {
/* 4195:4823 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
/* 4196:4824 */         this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
/* 4197:     */       }
/* 4198:     */       else
/* 4199:     */       {
/* 4200:4828 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
/* 4201:4829 */         this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
/* 4202:     */       }
/* 4203:4832 */       if ((!var19) && (!var16))
/* 4204:     */       {
/* 4205:4834 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
/* 4206:4835 */         this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
/* 4207:     */       }
/* 4208:     */       else
/* 4209:     */       {
/* 4210:4839 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
/* 4211:4840 */         this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
/* 4212:     */       }
/* 4213:4843 */       if ((!var18) && (!var16))
/* 4214:     */       {
/* 4215:4845 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
/* 4216:4846 */         this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
/* 4217:     */       }
/* 4218:     */       else
/* 4219:     */       {
/* 4220:4850 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
/* 4221:4851 */         this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
/* 4222:     */       }
/* 4223:4854 */       if (this.renderMinY <= 0.0D) {
/* 4224:4856 */         p_147751_3_++;
/* 4225:     */       }
/* 4226:4859 */       if (var14 < 0) {
/* 4227:4861 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4228:     */       }
/* 4229:4864 */       int var20 = var14;
/* 4230:4866 */       if ((this.renderMinY <= 0.0D) || (!this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_).isOpaqueCube())) {
/* 4231:4868 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4232:     */       }
/* 4233:4871 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4234:4872 */       var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0F;
/* 4235:4873 */       var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
/* 4236:4874 */       var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
/* 4237:4875 */       var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0F;
/* 4238:4876 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
/* 4239:4877 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
/* 4240:4878 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
/* 4241:4879 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
/* 4242:4881 */       if (simpleAO)
/* 4243:     */       {
/* 4244:4883 */         var10 = var21;
/* 4245:4884 */         var11 = var21;
/* 4246:4885 */         var12 = var21;
/* 4247:4886 */         var9 = var21;
/* 4248:4887 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4249:     */       }
/* 4250:4890 */       if (var13)
/* 4251:     */       {
/* 4252:4892 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.5F);
/* 4253:4893 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.5F);
/* 4254:4894 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.5F);
/* 4255:     */       }
/* 4256:     */       else
/* 4257:     */       {
/* 4258:4898 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F);
/* 4259:4899 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F);
/* 4260:4900 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F);
/* 4261:     */       }
/* 4262:4903 */       this.colorRedTopLeft *= var9;
/* 4263:4904 */       this.colorGreenTopLeft *= var9;
/* 4264:4905 */       this.colorBlueTopLeft *= var9;
/* 4265:4906 */       this.colorRedBottomLeft *= var10;
/* 4266:4907 */       this.colorGreenBottomLeft *= var10;
/* 4267:4908 */       this.colorBlueBottomLeft *= var10;
/* 4268:4909 */       this.colorRedBottomRight *= var11;
/* 4269:4910 */       this.colorGreenBottomRight *= var11;
/* 4270:4911 */       this.colorBlueBottomRight *= var11;
/* 4271:4912 */       this.colorRedTopRight *= var12;
/* 4272:4913 */       this.colorGreenTopRight *= var12;
/* 4273:4914 */       this.colorBlueTopRight *= var12;
/* 4274:4915 */       renderFaceYNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 0));
/* 4275:4916 */       var8 = true;
/* 4276:     */     }
/* 4277:4919 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_, 1)))
/* 4278:     */     {
/* 4279:4921 */       if (this.renderMaxY >= 1.0D) {
/* 4280:4923 */         p_147751_3_++;
/* 4281:     */       }
/* 4282:4926 */       this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4283:4927 */       this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4284:4928 */       this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4285:4929 */       this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4286:4930 */       this.aoLightValueScratchXYNP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4287:4931 */       this.aoLightValueScratchXYPP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4288:4932 */       this.aoLightValueScratchYZPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4289:4933 */       this.aoLightValueScratchYZPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4290:4934 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
/* 4291:4935 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
/* 4292:4936 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
/* 4293:4937 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
/* 4294:4939 */       if ((!var19) && (!var17))
/* 4295:     */       {
/* 4296:4941 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
/* 4297:4942 */         this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
/* 4298:     */       }
/* 4299:     */       else
/* 4300:     */       {
/* 4301:4946 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
/* 4302:4947 */         this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1);
/* 4303:     */       }
/* 4304:4950 */       if ((!var19) && (!var16))
/* 4305:     */       {
/* 4306:4952 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
/* 4307:4953 */         this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
/* 4308:     */       }
/* 4309:     */       else
/* 4310:     */       {
/* 4311:4957 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
/* 4312:4958 */         this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1);
/* 4313:     */       }
/* 4314:4961 */       if ((!var18) && (!var17))
/* 4315:     */       {
/* 4316:4963 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
/* 4317:4964 */         this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
/* 4318:     */       }
/* 4319:     */       else
/* 4320:     */       {
/* 4321:4968 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
/* 4322:4969 */         this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1);
/* 4323:     */       }
/* 4324:4972 */       if ((!var18) && (!var16))
/* 4325:     */       {
/* 4326:4974 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
/* 4327:4975 */         this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
/* 4328:     */       }
/* 4329:     */       else
/* 4330:     */       {
/* 4331:4979 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
/* 4332:4980 */         this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1);
/* 4333:     */       }
/* 4334:4983 */       if (this.renderMaxY >= 1.0D) {
/* 4335:4985 */         p_147751_3_--;
/* 4336:     */       }
/* 4337:4988 */       if (var14 < 0) {
/* 4338:4990 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4339:     */       }
/* 4340:4993 */       int var20 = var14;
/* 4341:4995 */       if ((this.renderMaxY >= 1.0D) || (!this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_).isOpaqueCube())) {
/* 4342:4997 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4343:     */       }
/* 4344:5000 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4345:5001 */       var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0F;
/* 4346:5002 */       var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
/* 4347:5003 */       var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
/* 4348:5004 */       var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0F;
/* 4349:5005 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
/* 4350:5006 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
/* 4351:5007 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
/* 4352:5008 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
/* 4353:5010 */       if (simpleAO)
/* 4354:     */       {
/* 4355:5012 */         var10 = var21;
/* 4356:5013 */         var11 = var21;
/* 4357:5014 */         var12 = var21;
/* 4358:5015 */         var9 = var21;
/* 4359:5016 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4360:     */       }
/* 4361:5019 */       this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_);
/* 4362:5020 */       this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_);
/* 4363:5021 */       this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_);
/* 4364:5022 */       this.colorRedTopLeft *= var9;
/* 4365:5023 */       this.colorGreenTopLeft *= var9;
/* 4366:5024 */       this.colorBlueTopLeft *= var9;
/* 4367:5025 */       this.colorRedBottomLeft *= var10;
/* 4368:5026 */       this.colorGreenBottomLeft *= var10;
/* 4369:5027 */       this.colorBlueBottomLeft *= var10;
/* 4370:5028 */       this.colorRedBottomRight *= var11;
/* 4371:5029 */       this.colorGreenBottomRight *= var11;
/* 4372:5030 */       this.colorBlueBottomRight *= var11;
/* 4373:5031 */       this.colorRedTopRight *= var12;
/* 4374:5032 */       this.colorGreenTopRight *= var12;
/* 4375:5033 */       this.colorBlueTopRight *= var12;
/* 4376:5034 */       renderFaceYPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 1));
/* 4377:5035 */       var8 = true;
/* 4378:     */     }
/* 4379:5040 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1, 2)))
/* 4380:     */     {
/* 4381:5042 */       if (this.renderMinZ <= 0.0D) {
/* 4382:5044 */         p_147751_4_--;
/* 4383:     */       }
/* 4384:5047 */       this.aoLightValueScratchXZNN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4385:5048 */       this.aoLightValueScratchYZNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4386:5049 */       this.aoLightValueScratchYZPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4387:5050 */       this.aoLightValueScratchXZPN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4388:5051 */       this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4389:5052 */       this.aoBrightnessYZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4390:5053 */       this.aoBrightnessYZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4391:5054 */       this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4392:5055 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
/* 4393:5056 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
/* 4394:5057 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1).getCanBlockGrass();
/* 4395:5058 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1).getCanBlockGrass();
/* 4396:5060 */       if ((!var17) && (!var19))
/* 4397:     */       {
/* 4398:5062 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
/* 4399:5063 */         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
/* 4400:     */       }
/* 4401:     */       else
/* 4402:     */       {
/* 4403:5067 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
/* 4404:5068 */         this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
/* 4405:     */       }
/* 4406:5071 */       if ((!var17) && (!var18))
/* 4407:     */       {
/* 4408:5073 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
/* 4409:5074 */         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
/* 4410:     */       }
/* 4411:     */       else
/* 4412:     */       {
/* 4413:5078 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
/* 4414:5079 */         this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
/* 4415:     */       }
/* 4416:5082 */       if ((!var16) && (!var19))
/* 4417:     */       {
/* 4418:5084 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
/* 4419:5085 */         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
/* 4420:     */       }
/* 4421:     */       else
/* 4422:     */       {
/* 4423:5089 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
/* 4424:5090 */         this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
/* 4425:     */       }
/* 4426:5093 */       if ((!var16) && (!var18))
/* 4427:     */       {
/* 4428:5095 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
/* 4429:5096 */         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
/* 4430:     */       }
/* 4431:     */       else
/* 4432:     */       {
/* 4433:5100 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
/* 4434:5101 */         this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
/* 4435:     */       }
/* 4436:5104 */       if (this.renderMinZ <= 0.0D) {
/* 4437:5106 */         p_147751_4_++;
/* 4438:     */       }
/* 4439:5109 */       if (var14 < 0) {
/* 4440:5111 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4441:     */       }
/* 4442:5114 */       int var20 = var14;
/* 4443:5116 */       if ((this.renderMinZ <= 0.0D) || (!this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ - 1).isOpaqueCube())) {
/* 4444:5118 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4445:     */       }
/* 4446:5121 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4447:5122 */       var9 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0F;
/* 4448:5123 */       var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
/* 4449:5124 */       var11 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
/* 4450:5125 */       var12 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0F;
/* 4451:5126 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
/* 4452:5127 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
/* 4453:5128 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
/* 4454:5129 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
/* 4455:5131 */       if (simpleAO)
/* 4456:     */       {
/* 4457:5133 */         var10 = var21;
/* 4458:5134 */         var11 = var21;
/* 4459:5135 */         var12 = var21;
/* 4460:5136 */         var9 = var21;
/* 4461:5137 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4462:     */       }
/* 4463:5140 */       if (var13)
/* 4464:     */       {
/* 4465:5142 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.8F);
/* 4466:5143 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.8F);
/* 4467:5144 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.8F);
/* 4468:     */       }
/* 4469:     */       else
/* 4470:     */       {
/* 4471:5148 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F);
/* 4472:5149 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F);
/* 4473:5150 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F);
/* 4474:     */       }
/* 4475:5153 */       this.colorRedTopLeft *= var9;
/* 4476:5154 */       this.colorGreenTopLeft *= var9;
/* 4477:5155 */       this.colorBlueTopLeft *= var9;
/* 4478:5156 */       this.colorRedBottomLeft *= var10;
/* 4479:5157 */       this.colorGreenBottomLeft *= var10;
/* 4480:5158 */       this.colorBlueBottomLeft *= var10;
/* 4481:5159 */       this.colorRedBottomRight *= var11;
/* 4482:5160 */       this.colorGreenBottomRight *= var11;
/* 4483:5161 */       this.colorBlueBottomRight *= var11;
/* 4484:5162 */       this.colorRedTopRight *= var12;
/* 4485:5163 */       this.colorGreenTopRight *= var12;
/* 4486:5164 */       this.colorBlueTopRight *= var12;
/* 4487:5165 */       IIcon var22 = getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 2);
/* 4488:5167 */       if (betterGrass) {
/* 4489:5169 */         var22 = fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 2, p_147751_5_, p_147751_6_, p_147751_7_);
/* 4490:     */       }
/* 4491:5172 */       renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
/* 4492:5174 */       if ((defaultTexture) && (fancyGrass) && (var22 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 4493:     */       {
/* 4494:5176 */         this.colorRedTopLeft *= p_147751_5_;
/* 4495:5177 */         this.colorRedBottomLeft *= p_147751_5_;
/* 4496:5178 */         this.colorRedBottomRight *= p_147751_5_;
/* 4497:5179 */         this.colorRedTopRight *= p_147751_5_;
/* 4498:5180 */         this.colorGreenTopLeft *= p_147751_6_;
/* 4499:5181 */         this.colorGreenBottomLeft *= p_147751_6_;
/* 4500:5182 */         this.colorGreenBottomRight *= p_147751_6_;
/* 4501:5183 */         this.colorGreenTopRight *= p_147751_6_;
/* 4502:5184 */         this.colorBlueTopLeft *= p_147751_7_;
/* 4503:5185 */         this.colorBlueBottomLeft *= p_147751_7_;
/* 4504:5186 */         this.colorBlueBottomRight *= p_147751_7_;
/* 4505:5187 */         this.colorBlueTopRight *= p_147751_7_;
/* 4506:5188 */         renderFaceZNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
/* 4507:     */       }
/* 4508:5191 */       var8 = true;
/* 4509:     */     }
/* 4510:5194 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1, 3)))
/* 4511:     */     {
/* 4512:5196 */       if (this.renderMaxZ >= 1.0D) {
/* 4513:5198 */         p_147751_4_++;
/* 4514:     */       }
/* 4515:5201 */       this.aoLightValueScratchXZNP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4516:5202 */       this.aoLightValueScratchXZPP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4517:5203 */       this.aoLightValueScratchYZNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4518:5204 */       this.aoLightValueScratchYZPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4519:5205 */       this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4520:5206 */       this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4521:5207 */       this.aoBrightnessYZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4522:5208 */       this.aoBrightnessYZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4523:5209 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
/* 4524:5210 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
/* 4525:5211 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1).getCanBlockGrass();
/* 4526:5212 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1).getCanBlockGrass();
/* 4527:5214 */       if ((!var17) && (!var19))
/* 4528:     */       {
/* 4529:5216 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
/* 4530:5217 */         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
/* 4531:     */       }
/* 4532:     */       else
/* 4533:     */       {
/* 4534:5221 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
/* 4535:5222 */         this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_);
/* 4536:     */       }
/* 4537:5225 */       if ((!var17) && (!var18))
/* 4538:     */       {
/* 4539:5227 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
/* 4540:5228 */         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
/* 4541:     */       }
/* 4542:     */       else
/* 4543:     */       {
/* 4544:5232 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
/* 4545:5233 */         this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_);
/* 4546:     */       }
/* 4547:5236 */       if ((!var16) && (!var19))
/* 4548:     */       {
/* 4549:5238 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
/* 4550:5239 */         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
/* 4551:     */       }
/* 4552:     */       else
/* 4553:     */       {
/* 4554:5243 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
/* 4555:5244 */         this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_);
/* 4556:     */       }
/* 4557:5247 */       if ((!var16) && (!var18))
/* 4558:     */       {
/* 4559:5249 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
/* 4560:5250 */         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
/* 4561:     */       }
/* 4562:     */       else
/* 4563:     */       {
/* 4564:5254 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
/* 4565:5255 */         this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_);
/* 4566:     */       }
/* 4567:5258 */       if (this.renderMaxZ >= 1.0D) {
/* 4568:5260 */         p_147751_4_--;
/* 4569:     */       }
/* 4570:5263 */       if (var14 < 0) {
/* 4571:5265 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4572:     */       }
/* 4573:5268 */       int var20 = var14;
/* 4574:5270 */       if ((this.renderMaxZ >= 1.0D) || (!this.blockAccess.getBlock(p_147751_2_, p_147751_3_, p_147751_4_ + 1).isOpaqueCube())) {
/* 4575:5272 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4576:     */       }
/* 4577:5275 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4578:5276 */       var9 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0F;
/* 4579:5277 */       var12 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
/* 4580:5278 */       var11 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
/* 4581:5279 */       var10 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0F;
/* 4582:5280 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
/* 4583:5281 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
/* 4584:5282 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
/* 4585:5283 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
/* 4586:5285 */       if (simpleAO)
/* 4587:     */       {
/* 4588:5287 */         var10 = var21;
/* 4589:5288 */         var11 = var21;
/* 4590:5289 */         var12 = var21;
/* 4591:5290 */         var9 = var21;
/* 4592:5291 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4593:     */       }
/* 4594:5294 */       if (var13)
/* 4595:     */       {
/* 4596:5296 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.8F);
/* 4597:5297 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.8F);
/* 4598:5298 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.8F);
/* 4599:     */       }
/* 4600:     */       else
/* 4601:     */       {
/* 4602:5302 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F);
/* 4603:5303 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F);
/* 4604:5304 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F);
/* 4605:     */       }
/* 4606:5307 */       this.colorRedTopLeft *= var9;
/* 4607:5308 */       this.colorGreenTopLeft *= var9;
/* 4608:5309 */       this.colorBlueTopLeft *= var9;
/* 4609:5310 */       this.colorRedBottomLeft *= var10;
/* 4610:5311 */       this.colorGreenBottomLeft *= var10;
/* 4611:5312 */       this.colorBlueBottomLeft *= var10;
/* 4612:5313 */       this.colorRedBottomRight *= var11;
/* 4613:5314 */       this.colorGreenBottomRight *= var11;
/* 4614:5315 */       this.colorBlueBottomRight *= var11;
/* 4615:5316 */       this.colorRedTopRight *= var12;
/* 4616:5317 */       this.colorGreenTopRight *= var12;
/* 4617:5318 */       this.colorBlueTopRight *= var12;
/* 4618:5319 */       IIcon var22 = getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 3);
/* 4619:5321 */       if (betterGrass) {
/* 4620:5323 */         var22 = fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 3, p_147751_5_, p_147751_6_, p_147751_7_);
/* 4621:     */       }
/* 4622:5326 */       renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
/* 4623:5328 */       if ((defaultTexture) && (fancyGrass) && (var22 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 4624:     */       {
/* 4625:5330 */         this.colorRedTopLeft *= p_147751_5_;
/* 4626:5331 */         this.colorRedBottomLeft *= p_147751_5_;
/* 4627:5332 */         this.colorRedBottomRight *= p_147751_5_;
/* 4628:5333 */         this.colorRedTopRight *= p_147751_5_;
/* 4629:5334 */         this.colorGreenTopLeft *= p_147751_6_;
/* 4630:5335 */         this.colorGreenBottomLeft *= p_147751_6_;
/* 4631:5336 */         this.colorGreenBottomRight *= p_147751_6_;
/* 4632:5337 */         this.colorGreenTopRight *= p_147751_6_;
/* 4633:5338 */         this.colorBlueTopLeft *= p_147751_7_;
/* 4634:5339 */         this.colorBlueBottomLeft *= p_147751_7_;
/* 4635:5340 */         this.colorBlueBottomRight *= p_147751_7_;
/* 4636:5341 */         this.colorBlueTopRight *= p_147751_7_;
/* 4637:5342 */         renderFaceZPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
/* 4638:     */       }
/* 4639:5345 */       var8 = true;
/* 4640:     */     }
/* 4641:5348 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_, 4)))
/* 4642:     */     {
/* 4643:5350 */       if (this.renderMinX <= 0.0D) {
/* 4644:5352 */         p_147751_2_--;
/* 4645:     */       }
/* 4646:5355 */       this.aoLightValueScratchXYNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4647:5356 */       this.aoLightValueScratchXZNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4648:5357 */       this.aoLightValueScratchXZNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4649:5358 */       this.aoLightValueScratchXYNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4650:5359 */       this.aoBrightnessXYNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4651:5360 */       this.aoBrightnessXZNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4652:5361 */       this.aoBrightnessXZNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4653:5362 */       this.aoBrightnessXYNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4654:5363 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
/* 4655:5364 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
/* 4656:5365 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
/* 4657:5366 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
/* 4658:5368 */       if ((!var18) && (!var17))
/* 4659:     */       {
/* 4660:5370 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
/* 4661:5371 */         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
/* 4662:     */       }
/* 4663:     */       else
/* 4664:     */       {
/* 4665:5375 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
/* 4666:5376 */         this.aoBrightnessXYZNNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
/* 4667:     */       }
/* 4668:5379 */       if ((!var19) && (!var17))
/* 4669:     */       {
/* 4670:5381 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
/* 4671:5382 */         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
/* 4672:     */       }
/* 4673:     */       else
/* 4674:     */       {
/* 4675:5386 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
/* 4676:5387 */         this.aoBrightnessXYZNNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
/* 4677:     */       }
/* 4678:5390 */       if ((!var18) && (!var16))
/* 4679:     */       {
/* 4680:5392 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
/* 4681:5393 */         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
/* 4682:     */       }
/* 4683:     */       else
/* 4684:     */       {
/* 4685:5397 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
/* 4686:5398 */         this.aoBrightnessXYZNPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
/* 4687:     */       }
/* 4688:5401 */       if ((!var19) && (!var16))
/* 4689:     */       {
/* 4690:5403 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
/* 4691:5404 */         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
/* 4692:     */       }
/* 4693:     */       else
/* 4694:     */       {
/* 4695:5408 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
/* 4696:5409 */         this.aoBrightnessXYZNPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
/* 4697:     */       }
/* 4698:5412 */       if (this.renderMinX <= 0.0D) {
/* 4699:5414 */         p_147751_2_++;
/* 4700:     */       }
/* 4701:5417 */       if (var14 < 0) {
/* 4702:5419 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4703:     */       }
/* 4704:5422 */       int var20 = var14;
/* 4705:5424 */       if ((this.renderMinX <= 0.0D) || (!this.blockAccess.getBlock(p_147751_2_ - 1, p_147751_3_, p_147751_4_).isOpaqueCube())) {
/* 4706:5426 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4707:     */       }
/* 4708:5429 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_ - 1, p_147751_3_, p_147751_4_);
/* 4709:5430 */       var12 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0F;
/* 4710:5431 */       var9 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
/* 4711:5432 */       var10 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
/* 4712:5433 */       var11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0F;
/* 4713:5434 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
/* 4714:5435 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
/* 4715:5436 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
/* 4716:5437 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
/* 4717:5439 */       if (simpleAO)
/* 4718:     */       {
/* 4719:5441 */         var10 = var21;
/* 4720:5442 */         var11 = var21;
/* 4721:5443 */         var12 = var21;
/* 4722:5444 */         var9 = var21;
/* 4723:5445 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4724:     */       }
/* 4725:5448 */       if (var13)
/* 4726:     */       {
/* 4727:5450 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.6F);
/* 4728:5451 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.6F);
/* 4729:5452 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.6F);
/* 4730:     */       }
/* 4731:     */       else
/* 4732:     */       {
/* 4733:5456 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F);
/* 4734:5457 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F);
/* 4735:5458 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F);
/* 4736:     */       }
/* 4737:5461 */       this.colorRedTopLeft *= var9;
/* 4738:5462 */       this.colorGreenTopLeft *= var9;
/* 4739:5463 */       this.colorBlueTopLeft *= var9;
/* 4740:5464 */       this.colorRedBottomLeft *= var10;
/* 4741:5465 */       this.colorGreenBottomLeft *= var10;
/* 4742:5466 */       this.colorBlueBottomLeft *= var10;
/* 4743:5467 */       this.colorRedBottomRight *= var11;
/* 4744:5468 */       this.colorGreenBottomRight *= var11;
/* 4745:5469 */       this.colorBlueBottomRight *= var11;
/* 4746:5470 */       this.colorRedTopRight *= var12;
/* 4747:5471 */       this.colorGreenTopRight *= var12;
/* 4748:5472 */       this.colorBlueTopRight *= var12;
/* 4749:5473 */       IIcon var22 = getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 4);
/* 4750:5475 */       if (betterGrass) {
/* 4751:5477 */         var22 = fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 4, p_147751_5_, p_147751_6_, p_147751_7_);
/* 4752:     */       }
/* 4753:5480 */       renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
/* 4754:5482 */       if ((defaultTexture) && (fancyGrass) && (var22 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 4755:     */       {
/* 4756:5484 */         this.colorRedTopLeft *= p_147751_5_;
/* 4757:5485 */         this.colorRedBottomLeft *= p_147751_5_;
/* 4758:5486 */         this.colorRedBottomRight *= p_147751_5_;
/* 4759:5487 */         this.colorRedTopRight *= p_147751_5_;
/* 4760:5488 */         this.colorGreenTopLeft *= p_147751_6_;
/* 4761:5489 */         this.colorGreenBottomLeft *= p_147751_6_;
/* 4762:5490 */         this.colorGreenBottomRight *= p_147751_6_;
/* 4763:5491 */         this.colorGreenTopRight *= p_147751_6_;
/* 4764:5492 */         this.colorBlueTopLeft *= p_147751_7_;
/* 4765:5493 */         this.colorBlueBottomLeft *= p_147751_7_;
/* 4766:5494 */         this.colorBlueBottomRight *= p_147751_7_;
/* 4767:5495 */         this.colorBlueTopRight *= p_147751_7_;
/* 4768:5496 */         renderFaceXNeg(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
/* 4769:     */       }
/* 4770:5499 */       var8 = true;
/* 4771:     */     }
/* 4772:5502 */     if ((this.renderAllFaces) || (p_147751_1_.shouldSideBeRendered(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_, 5)))
/* 4773:     */     {
/* 4774:5504 */       if (this.renderMaxX >= 1.0D) {
/* 4775:5506 */         p_147751_2_++;
/* 4776:     */       }
/* 4777:5509 */       this.aoLightValueScratchXYPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4778:5510 */       this.aoLightValueScratchXZPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4779:5511 */       this.aoLightValueScratchXZPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4780:5512 */       this.aoLightValueScratchXYPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4781:5513 */       this.aoBrightnessXYPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_);
/* 4782:5514 */       this.aoBrightnessXZPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ - 1);
/* 4783:5515 */       this.aoBrightnessXZPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_ + 1);
/* 4784:5516 */       this.aoBrightnessXYPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_);
/* 4785:5517 */       boolean var16 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ + 1, p_147751_4_).getCanBlockGrass();
/* 4786:5518 */       boolean var17 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_ - 1, p_147751_4_).getCanBlockGrass();
/* 4787:5519 */       boolean var18 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ + 1).getCanBlockGrass();
/* 4788:5520 */       boolean var19 = this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_ - 1).getCanBlockGrass();
/* 4789:5522 */       if ((!var17) && (!var19))
/* 4790:     */       {
/* 4791:5524 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
/* 4792:5525 */         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
/* 4793:     */       }
/* 4794:     */       else
/* 4795:     */       {
/* 4796:5529 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
/* 4797:5530 */         this.aoBrightnessXYZPNN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ - 1);
/* 4798:     */       }
/* 4799:5533 */       if ((!var17) && (!var18))
/* 4800:     */       {
/* 4801:5535 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
/* 4802:5536 */         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
/* 4803:     */       }
/* 4804:     */       else
/* 4805:     */       {
/* 4806:5540 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
/* 4807:5541 */         this.aoBrightnessXYZPNP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ - 1, p_147751_4_ + 1);
/* 4808:     */       }
/* 4809:5544 */       if ((!var16) && (!var19))
/* 4810:     */       {
/* 4811:5546 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
/* 4812:5547 */         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
/* 4813:     */       }
/* 4814:     */       else
/* 4815:     */       {
/* 4816:5551 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
/* 4817:5552 */         this.aoBrightnessXYZPPN = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ - 1);
/* 4818:     */       }
/* 4819:5555 */       if ((!var16) && (!var18))
/* 4820:     */       {
/* 4821:5557 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
/* 4822:5558 */         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
/* 4823:     */       }
/* 4824:     */       else
/* 4825:     */       {
/* 4826:5562 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
/* 4827:5563 */         this.aoBrightnessXYZPPP = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_ + 1, p_147751_4_ + 1);
/* 4828:     */       }
/* 4829:5566 */       if (this.renderMaxX >= 1.0D) {
/* 4830:5568 */         p_147751_2_--;
/* 4831:     */       }
/* 4832:5571 */       if (var14 < 0) {
/* 4833:5573 */         var14 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_);
/* 4834:     */       }
/* 4835:5576 */       int var20 = var14;
/* 4836:5578 */       if ((this.renderMaxX >= 1.0D) || (!this.blockAccess.getBlock(p_147751_2_ + 1, p_147751_3_, p_147751_4_).isOpaqueCube())) {
/* 4837:5580 */         var20 = p_147751_1_.getBlockBrightness(this.blockAccess, p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4838:     */       }
/* 4839:5583 */       float var21 = getAmbientOcclusionLightValue(p_147751_2_ + 1, p_147751_3_, p_147751_4_);
/* 4840:5584 */       var9 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0F;
/* 4841:5585 */       var10 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0F;
/* 4842:5586 */       var11 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
/* 4843:5587 */       var12 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
/* 4844:5588 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
/* 4845:5589 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
/* 4846:5590 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
/* 4847:5591 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
/* 4848:5593 */       if (simpleAO)
/* 4849:     */       {
/* 4850:5595 */         var10 = var21;
/* 4851:5596 */         var11 = var21;
/* 4852:5597 */         var12 = var21;
/* 4853:5598 */         var9 = var21;
/* 4854:5599 */         this.brightnessTopLeft = (this.brightnessTopRight = this.brightnessBottomRight = this.brightnessBottomLeft = var20);
/* 4855:     */       }
/* 4856:5602 */       if (var13)
/* 4857:     */       {
/* 4858:5604 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147751_5_ * 0.6F);
/* 4859:5605 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147751_6_ * 0.6F);
/* 4860:5606 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147751_7_ * 0.6F);
/* 4861:     */       }
/* 4862:     */       else
/* 4863:     */       {
/* 4864:5610 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F);
/* 4865:5611 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F);
/* 4866:5612 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F);
/* 4867:     */       }
/* 4868:5615 */       this.colorRedTopLeft *= var9;
/* 4869:5616 */       this.colorGreenTopLeft *= var9;
/* 4870:5617 */       this.colorBlueTopLeft *= var9;
/* 4871:5618 */       this.colorRedBottomLeft *= var10;
/* 4872:5619 */       this.colorGreenBottomLeft *= var10;
/* 4873:5620 */       this.colorBlueBottomLeft *= var10;
/* 4874:5621 */       this.colorRedBottomRight *= var11;
/* 4875:5622 */       this.colorGreenBottomRight *= var11;
/* 4876:5623 */       this.colorBlueBottomRight *= var11;
/* 4877:5624 */       this.colorRedTopRight *= var12;
/* 4878:5625 */       this.colorGreenTopRight *= var12;
/* 4879:5626 */       this.colorBlueTopRight *= var12;
/* 4880:5627 */       IIcon var22 = getBlockIcon(p_147751_1_, this.blockAccess, p_147751_2_, p_147751_3_, p_147751_4_, 5);
/* 4881:5629 */       if (betterGrass) {
/* 4882:5631 */         var22 = fixAoSideGrassTexture(var22, p_147751_2_, p_147751_3_, p_147751_4_, 5, p_147751_5_, p_147751_6_, p_147751_7_);
/* 4883:     */       }
/* 4884:5634 */       renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, var22);
/* 4885:5636 */       if ((defaultTexture) && (fancyGrass) && (var22 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 4886:     */       {
/* 4887:5638 */         this.colorRedTopLeft *= p_147751_5_;
/* 4888:5639 */         this.colorRedBottomLeft *= p_147751_5_;
/* 4889:5640 */         this.colorRedBottomRight *= p_147751_5_;
/* 4890:5641 */         this.colorRedTopRight *= p_147751_5_;
/* 4891:5642 */         this.colorGreenTopLeft *= p_147751_6_;
/* 4892:5643 */         this.colorGreenBottomLeft *= p_147751_6_;
/* 4893:5644 */         this.colorGreenBottomRight *= p_147751_6_;
/* 4894:5645 */         this.colorGreenTopRight *= p_147751_6_;
/* 4895:5646 */         this.colorBlueTopLeft *= p_147751_7_;
/* 4896:5647 */         this.colorBlueBottomLeft *= p_147751_7_;
/* 4897:5648 */         this.colorBlueBottomRight *= p_147751_7_;
/* 4898:5649 */         this.colorBlueTopRight *= p_147751_7_;
/* 4899:5650 */         renderFaceXPos(p_147751_1_, p_147751_2_, p_147751_3_, p_147751_4_, BlockGrass.func_149990_e());
/* 4900:     */       }
/* 4901:5653 */       var8 = true;
/* 4902:     */     }
/* 4903:5656 */     this.enableAO = false;
/* 4904:5657 */     return var8;
/* 4905:     */   }
/* 4906:     */   
/* 4907:     */   public boolean renderStandardBlockWithAmbientOcclusionPartial(Block p_147808_1_, int p_147808_2_, int p_147808_3_, int p_147808_4_, float p_147808_5_, float p_147808_6_, float p_147808_7_)
/* 4908:     */   {
/* 4909:5662 */     this.enableAO = true;
/* 4910:5663 */     boolean var8 = false;
/* 4911:5664 */     float var9 = 0.0F;
/* 4912:5665 */     float var10 = 0.0F;
/* 4913:5666 */     float var11 = 0.0F;
/* 4914:5667 */     float var12 = 0.0F;
/* 4915:5668 */     boolean var13 = true;
/* 4916:5669 */     int var14 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_);
/* 4917:5670 */     Tessellator var15 = Tessellator.instance;
/* 4918:5671 */     var15.setBrightness(983055);
/* 4919:5673 */     if (p_147808_1_ == Blocks.grass) {
/* 4920:5675 */       var13 = false;
/* 4921:5677 */     } else if (hasOverrideBlockTexture()) {
/* 4922:5679 */       var13 = false;
/* 4923:     */     }
/* 4924:5689 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_, 0)))
/* 4925:     */     {
/* 4926:5691 */       if (this.renderMinY <= 0.0D) {
/* 4927:5693 */         p_147808_3_--;
/* 4928:     */       }
/* 4929:5696 */       this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 4930:5697 */       this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 4931:5698 */       this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 4932:5699 */       this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 4933:5700 */       this.aoLightValueScratchXYNN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 4934:5701 */       this.aoLightValueScratchYZNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 4935:5702 */       this.aoLightValueScratchYZNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 4936:5703 */       this.aoLightValueScratchXYPN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 4937:5704 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
/* 4938:5705 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
/* 4939:5706 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
/* 4940:5707 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
/* 4941:5709 */       if ((!var19) && (!var17))
/* 4942:     */       {
/* 4943:5711 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
/* 4944:5712 */         this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
/* 4945:     */       }
/* 4946:     */       else
/* 4947:     */       {
/* 4948:5716 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
/* 4949:5717 */         this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
/* 4950:     */       }
/* 4951:5720 */       if ((!var18) && (!var17))
/* 4952:     */       {
/* 4953:5722 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
/* 4954:5723 */         this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
/* 4955:     */       }
/* 4956:     */       else
/* 4957:     */       {
/* 4958:5727 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
/* 4959:5728 */         this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
/* 4960:     */       }
/* 4961:5731 */       if ((!var19) && (!var16))
/* 4962:     */       {
/* 4963:5733 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
/* 4964:5734 */         this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
/* 4965:     */       }
/* 4966:     */       else
/* 4967:     */       {
/* 4968:5738 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
/* 4969:5739 */         this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
/* 4970:     */       }
/* 4971:5742 */       if ((!var18) && (!var16))
/* 4972:     */       {
/* 4973:5744 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
/* 4974:5745 */         this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
/* 4975:     */       }
/* 4976:     */       else
/* 4977:     */       {
/* 4978:5749 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
/* 4979:5750 */         this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
/* 4980:     */       }
/* 4981:5753 */       if (this.renderMinY <= 0.0D) {
/* 4982:5755 */         p_147808_3_++;
/* 4983:     */       }
/* 4984:5758 */       int var20 = var14;
/* 4985:5760 */       if ((this.renderMinY <= 0.0D) || (!this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_).isOpaqueCube())) {
/* 4986:5762 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 4987:     */       }
/* 4988:5765 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 4989:5766 */       var9 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + var21) / 4.0F;
/* 4990:5767 */       var12 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN) / 4.0F;
/* 4991:5768 */       var11 = (var21 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN) / 4.0F;
/* 4992:5769 */       var10 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + var21 + this.aoLightValueScratchYZNN) / 4.0F;
/* 4993:5770 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, var20);
/* 4994:5771 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, var20);
/* 4995:5772 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, var20);
/* 4996:5773 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, var20);
/* 4997:5775 */       if (var13)
/* 4998:     */       {
/* 4999:5777 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.5F);
/* 5000:5778 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.5F);
/* 5001:5779 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.5F);
/* 5002:     */       }
/* 5003:     */       else
/* 5004:     */       {
/* 5005:5783 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F);
/* 5006:5784 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F);
/* 5007:5785 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F);
/* 5008:     */       }
/* 5009:5788 */       this.colorRedTopLeft *= var9;
/* 5010:5789 */       this.colorGreenTopLeft *= var9;
/* 5011:5790 */       this.colorBlueTopLeft *= var9;
/* 5012:5791 */       this.colorRedBottomLeft *= var10;
/* 5013:5792 */       this.colorGreenBottomLeft *= var10;
/* 5014:5793 */       this.colorBlueBottomLeft *= var10;
/* 5015:5794 */       this.colorRedBottomRight *= var11;
/* 5016:5795 */       this.colorGreenBottomRight *= var11;
/* 5017:5796 */       this.colorBlueBottomRight *= var11;
/* 5018:5797 */       this.colorRedTopRight *= var12;
/* 5019:5798 */       this.colorGreenTopRight *= var12;
/* 5020:5799 */       this.colorBlueTopRight *= var12;
/* 5021:5800 */       renderFaceYNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 0));
/* 5022:5801 */       var8 = true;
/* 5023:     */     }
/* 5024:5804 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_, 1)))
/* 5025:     */     {
/* 5026:5806 */       if (this.renderMaxY >= 1.0D) {
/* 5027:5808 */         p_147808_3_++;
/* 5028:     */       }
/* 5029:5811 */       this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5030:5812 */       this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5031:5813 */       this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5032:5814 */       this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5033:5815 */       this.aoLightValueScratchXYNP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5034:5816 */       this.aoLightValueScratchXYPP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5035:5817 */       this.aoLightValueScratchYZPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5036:5818 */       this.aoLightValueScratchYZPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5037:5819 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
/* 5038:5820 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
/* 5039:5821 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
/* 5040:5822 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
/* 5041:5824 */       if ((!var19) && (!var17))
/* 5042:     */       {
/* 5043:5826 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
/* 5044:5827 */         this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
/* 5045:     */       }
/* 5046:     */       else
/* 5047:     */       {
/* 5048:5831 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
/* 5049:5832 */         this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1);
/* 5050:     */       }
/* 5051:5835 */       if ((!var19) && (!var16))
/* 5052:     */       {
/* 5053:5837 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
/* 5054:5838 */         this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
/* 5055:     */       }
/* 5056:     */       else
/* 5057:     */       {
/* 5058:5842 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
/* 5059:5843 */         this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1);
/* 5060:     */       }
/* 5061:5846 */       if ((!var18) && (!var17))
/* 5062:     */       {
/* 5063:5848 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
/* 5064:5849 */         this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
/* 5065:     */       }
/* 5066:     */       else
/* 5067:     */       {
/* 5068:5853 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
/* 5069:5854 */         this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1);
/* 5070:     */       }
/* 5071:5857 */       if ((!var18) && (!var16))
/* 5072:     */       {
/* 5073:5859 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
/* 5074:5860 */         this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
/* 5075:     */       }
/* 5076:     */       else
/* 5077:     */       {
/* 5078:5864 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
/* 5079:5865 */         this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1);
/* 5080:     */       }
/* 5081:5868 */       if (this.renderMaxY >= 1.0D) {
/* 5082:5870 */         p_147808_3_--;
/* 5083:     */       }
/* 5084:5873 */       int var20 = var14;
/* 5085:5875 */       if ((this.renderMaxY >= 1.0D) || (!this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_).isOpaqueCube())) {
/* 5086:5877 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5087:     */       }
/* 5088:5880 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5089:5881 */       var12 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + var21) / 4.0F;
/* 5090:5882 */       var9 = (this.aoLightValueScratchYZPP + var21 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP) / 4.0F;
/* 5091:5883 */       var10 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN) / 4.0F;
/* 5092:5884 */       var11 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0F;
/* 5093:5885 */       this.brightnessTopRight = getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, var20);
/* 5094:5886 */       this.brightnessTopLeft = getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, var20);
/* 5095:5887 */       this.brightnessBottomLeft = getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, var20);
/* 5096:5888 */       this.brightnessBottomRight = getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
/* 5097:5889 */       this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_);
/* 5098:5890 */       this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_);
/* 5099:5891 */       this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_);
/* 5100:5892 */       this.colorRedTopLeft *= var9;
/* 5101:5893 */       this.colorGreenTopLeft *= var9;
/* 5102:5894 */       this.colorBlueTopLeft *= var9;
/* 5103:5895 */       this.colorRedBottomLeft *= var10;
/* 5104:5896 */       this.colorGreenBottomLeft *= var10;
/* 5105:5897 */       this.colorBlueBottomLeft *= var10;
/* 5106:5898 */       this.colorRedBottomRight *= var11;
/* 5107:5899 */       this.colorGreenBottomRight *= var11;
/* 5108:5900 */       this.colorBlueBottomRight *= var11;
/* 5109:5901 */       this.colorRedTopRight *= var12;
/* 5110:5902 */       this.colorGreenTopRight *= var12;
/* 5111:5903 */       this.colorBlueTopRight *= var12;
/* 5112:5904 */       renderFaceYPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 1));
/* 5113:5905 */       var8 = true;
/* 5114:     */     }
/* 5115:5918 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1, 2)))
/* 5116:     */     {
/* 5117:5920 */       if (this.renderMinZ <= 0.0D) {
/* 5118:5922 */         p_147808_4_--;
/* 5119:     */       }
/* 5120:5925 */       this.aoLightValueScratchXZNN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5121:5926 */       this.aoLightValueScratchYZNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5122:5927 */       this.aoLightValueScratchYZPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5123:5928 */       this.aoLightValueScratchXZPN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5124:5929 */       this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5125:5930 */       this.aoBrightnessYZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5126:5931 */       this.aoBrightnessYZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5127:5932 */       this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5128:5933 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
/* 5129:5934 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
/* 5130:5935 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1).getCanBlockGrass();
/* 5131:5936 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1).getCanBlockGrass();
/* 5132:5938 */       if ((!var17) && (!var19))
/* 5133:     */       {
/* 5134:5940 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
/* 5135:5941 */         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
/* 5136:     */       }
/* 5137:     */       else
/* 5138:     */       {
/* 5139:5945 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
/* 5140:5946 */         this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
/* 5141:     */       }
/* 5142:5949 */       if ((!var17) && (!var18))
/* 5143:     */       {
/* 5144:5951 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
/* 5145:5952 */         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
/* 5146:     */       }
/* 5147:     */       else
/* 5148:     */       {
/* 5149:5956 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
/* 5150:5957 */         this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
/* 5151:     */       }
/* 5152:5960 */       if ((!var16) && (!var19))
/* 5153:     */       {
/* 5154:5962 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
/* 5155:5963 */         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
/* 5156:     */       }
/* 5157:     */       else
/* 5158:     */       {
/* 5159:5967 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
/* 5160:5968 */         this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
/* 5161:     */       }
/* 5162:5971 */       if ((!var16) && (!var18))
/* 5163:     */       {
/* 5164:5973 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
/* 5165:5974 */         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
/* 5166:     */       }
/* 5167:     */       else
/* 5168:     */       {
/* 5169:5978 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
/* 5170:5979 */         this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
/* 5171:     */       }
/* 5172:5982 */       if (this.renderMinZ <= 0.0D) {
/* 5173:5984 */         p_147808_4_++;
/* 5174:     */       }
/* 5175:5987 */       int var20 = var14;
/* 5176:5989 */       if ((this.renderMinZ <= 0.0D) || (!this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ - 1).isOpaqueCube())) {
/* 5177:5991 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5178:     */       }
/* 5179:5994 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5180:5995 */       float var22 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + var21 + this.aoLightValueScratchYZPN) / 4.0F;
/* 5181:5996 */       float var23 = (var21 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN) / 4.0F;
/* 5182:5997 */       float var24 = (this.aoLightValueScratchYZNN + var21 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN) / 4.0F;
/* 5183:5998 */       float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + var21) / 4.0F;
/* 5184:5999 */       var9 = (float)(var22 * this.renderMaxY * (1.0D - this.renderMinX) + var23 * this.renderMaxY * this.renderMinX + var24 * (1.0D - this.renderMaxY) * this.renderMinX + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
/* 5185:6000 */       var10 = (float)(var22 * this.renderMaxY * (1.0D - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
/* 5186:6001 */       var11 = (float)(var22 * this.renderMinY * (1.0D - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0D - this.renderMinY) * this.renderMaxX + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
/* 5187:6002 */       var12 = (float)(var22 * this.renderMinY * (1.0D - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0D - this.renderMinY) * this.renderMinX + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
/* 5188:6003 */       int var26 = getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, var20);
/* 5189:6004 */       int var27 = getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, var20);
/* 5190:6005 */       int var28 = getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, var20);
/* 5191:6006 */       int var29 = getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, var20);
/* 5192:6007 */       this.brightnessTopLeft = mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0D - this.renderMinX), this.renderMaxY * this.renderMinX, (1.0D - this.renderMaxY) * this.renderMinX, (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
/* 5193:6008 */       this.brightnessBottomLeft = mixAoBrightness(var26, var27, var28, var29, this.renderMaxY * (1.0D - this.renderMaxX), this.renderMaxY * this.renderMaxX, (1.0D - this.renderMaxY) * this.renderMaxX, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
/* 5194:6009 */       this.brightnessBottomRight = mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0D - this.renderMaxX), this.renderMinY * this.renderMaxX, (1.0D - this.renderMinY) * this.renderMaxX, (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
/* 5195:6010 */       this.brightnessTopRight = mixAoBrightness(var26, var27, var28, var29, this.renderMinY * (1.0D - this.renderMinX), this.renderMinY * this.renderMinX, (1.0D - this.renderMinY) * this.renderMinX, (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
/* 5196:6012 */       if (var13)
/* 5197:     */       {
/* 5198:6014 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8F);
/* 5199:6015 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8F);
/* 5200:6016 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8F);
/* 5201:     */       }
/* 5202:     */       else
/* 5203:     */       {
/* 5204:6020 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F);
/* 5205:6021 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F);
/* 5206:6022 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F);
/* 5207:     */       }
/* 5208:6025 */       this.colorRedTopLeft *= var9;
/* 5209:6026 */       this.colorGreenTopLeft *= var9;
/* 5210:6027 */       this.colorBlueTopLeft *= var9;
/* 5211:6028 */       this.colorRedBottomLeft *= var10;
/* 5212:6029 */       this.colorGreenBottomLeft *= var10;
/* 5213:6030 */       this.colorBlueBottomLeft *= var10;
/* 5214:6031 */       this.colorRedBottomRight *= var11;
/* 5215:6032 */       this.colorGreenBottomRight *= var11;
/* 5216:6033 */       this.colorBlueBottomRight *= var11;
/* 5217:6034 */       this.colorRedTopRight *= var12;
/* 5218:6035 */       this.colorGreenTopRight *= var12;
/* 5219:6036 */       this.colorBlueTopRight *= var12;
/* 5220:6037 */       IIcon var30 = getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 2);
/* 5221:6038 */       renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
/* 5222:6040 */       if ((fancyGrass) && (var30.getIconName().equals("grass_side")) && (!hasOverrideBlockTexture()))
/* 5223:     */       {
/* 5224:6042 */         this.colorRedTopLeft *= p_147808_5_;
/* 5225:6043 */         this.colorRedBottomLeft *= p_147808_5_;
/* 5226:6044 */         this.colorRedBottomRight *= p_147808_5_;
/* 5227:6045 */         this.colorRedTopRight *= p_147808_5_;
/* 5228:6046 */         this.colorGreenTopLeft *= p_147808_6_;
/* 5229:6047 */         this.colorGreenBottomLeft *= p_147808_6_;
/* 5230:6048 */         this.colorGreenBottomRight *= p_147808_6_;
/* 5231:6049 */         this.colorGreenTopRight *= p_147808_6_;
/* 5232:6050 */         this.colorBlueTopLeft *= p_147808_7_;
/* 5233:6051 */         this.colorBlueBottomLeft *= p_147808_7_;
/* 5234:6052 */         this.colorBlueBottomRight *= p_147808_7_;
/* 5235:6053 */         this.colorBlueTopRight *= p_147808_7_;
/* 5236:6054 */         renderFaceZNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
/* 5237:     */       }
/* 5238:6057 */       var8 = true;
/* 5239:     */     }
/* 5240:6060 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1, 3)))
/* 5241:     */     {
/* 5242:6062 */       if (this.renderMaxZ >= 1.0D) {
/* 5243:6064 */         p_147808_4_++;
/* 5244:     */       }
/* 5245:6067 */       this.aoLightValueScratchXZNP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5246:6068 */       this.aoLightValueScratchXZPP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5247:6069 */       this.aoLightValueScratchYZNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5248:6070 */       this.aoLightValueScratchYZPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5249:6071 */       this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5250:6072 */       this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5251:6073 */       this.aoBrightnessYZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5252:6074 */       this.aoBrightnessYZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5253:6075 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
/* 5254:6076 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
/* 5255:6077 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1).getCanBlockGrass();
/* 5256:6078 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1).getCanBlockGrass();
/* 5257:6080 */       if ((!var17) && (!var19))
/* 5258:     */       {
/* 5259:6082 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
/* 5260:6083 */         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
/* 5261:     */       }
/* 5262:     */       else
/* 5263:     */       {
/* 5264:6087 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
/* 5265:6088 */         this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_);
/* 5266:     */       }
/* 5267:6091 */       if ((!var17) && (!var18))
/* 5268:     */       {
/* 5269:6093 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
/* 5270:6094 */         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
/* 5271:     */       }
/* 5272:     */       else
/* 5273:     */       {
/* 5274:6098 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
/* 5275:6099 */         this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_);
/* 5276:     */       }
/* 5277:6102 */       if ((!var16) && (!var19))
/* 5278:     */       {
/* 5279:6104 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
/* 5280:6105 */         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
/* 5281:     */       }
/* 5282:     */       else
/* 5283:     */       {
/* 5284:6109 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
/* 5285:6110 */         this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_);
/* 5286:     */       }
/* 5287:6113 */       if ((!var16) && (!var18))
/* 5288:     */       {
/* 5289:6115 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
/* 5290:6116 */         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
/* 5291:     */       }
/* 5292:     */       else
/* 5293:     */       {
/* 5294:6120 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
/* 5295:6121 */         this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_);
/* 5296:     */       }
/* 5297:6124 */       if (this.renderMaxZ >= 1.0D) {
/* 5298:6126 */         p_147808_4_--;
/* 5299:     */       }
/* 5300:6129 */       int var20 = var14;
/* 5301:6131 */       if ((this.renderMaxZ >= 1.0D) || (!this.blockAccess.getBlock(p_147808_2_, p_147808_3_, p_147808_4_ + 1).isOpaqueCube())) {
/* 5302:6133 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5303:     */       }
/* 5304:6136 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5305:6137 */       float var22 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + var21 + this.aoLightValueScratchYZPP) / 4.0F;
/* 5306:6138 */       float var23 = (var21 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP) / 4.0F;
/* 5307:6139 */       float var24 = (this.aoLightValueScratchYZNP + var21 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP) / 4.0F;
/* 5308:6140 */       float var25 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + var21) / 4.0F;
/* 5309:6141 */       var9 = (float)(var22 * this.renderMaxY * (1.0D - this.renderMinX) + var23 * this.renderMaxY * this.renderMinX + var24 * (1.0D - this.renderMaxY) * this.renderMinX + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
/* 5310:6142 */       var10 = (float)(var22 * this.renderMinY * (1.0D - this.renderMinX) + var23 * this.renderMinY * this.renderMinX + var24 * (1.0D - this.renderMinY) * this.renderMinX + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
/* 5311:6143 */       var11 = (float)(var22 * this.renderMinY * (1.0D - this.renderMaxX) + var23 * this.renderMinY * this.renderMaxX + var24 * (1.0D - this.renderMinY) * this.renderMaxX + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
/* 5312:6144 */       var12 = (float)(var22 * this.renderMaxY * (1.0D - this.renderMaxX) + var23 * this.renderMaxY * this.renderMaxX + var24 * (1.0D - this.renderMaxY) * this.renderMaxX + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
/* 5313:6145 */       int var26 = getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, var20);
/* 5314:6146 */       int var27 = getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, var20);
/* 5315:6147 */       int var28 = getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
/* 5316:6148 */       int var29 = getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, var20);
/* 5317:6149 */       this.brightnessTopLeft = mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * (1.0D - this.renderMinX), (1.0D - this.renderMaxY) * this.renderMinX, this.renderMaxY * this.renderMinX);
/* 5318:6150 */       this.brightnessBottomLeft = mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * (1.0D - this.renderMinX), (1.0D - this.renderMinY) * this.renderMinX, this.renderMinY * this.renderMinX);
/* 5319:6151 */       this.brightnessBottomRight = mixAoBrightness(var26, var29, var28, var27, this.renderMinY * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * (1.0D - this.renderMaxX), (1.0D - this.renderMinY) * this.renderMaxX, this.renderMinY * this.renderMaxX);
/* 5320:6152 */       this.brightnessTopRight = mixAoBrightness(var26, var29, var28, var27, this.renderMaxY * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX), (1.0D - this.renderMaxY) * this.renderMaxX, this.renderMaxY * this.renderMaxX);
/* 5321:6154 */       if (var13)
/* 5322:     */       {
/* 5323:6156 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.8F);
/* 5324:6157 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.8F);
/* 5325:6158 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.8F);
/* 5326:     */       }
/* 5327:     */       else
/* 5328:     */       {
/* 5329:6162 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F);
/* 5330:6163 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F);
/* 5331:6164 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F);
/* 5332:     */       }
/* 5333:6167 */       this.colorRedTopLeft *= var9;
/* 5334:6168 */       this.colorGreenTopLeft *= var9;
/* 5335:6169 */       this.colorBlueTopLeft *= var9;
/* 5336:6170 */       this.colorRedBottomLeft *= var10;
/* 5337:6171 */       this.colorGreenBottomLeft *= var10;
/* 5338:6172 */       this.colorBlueBottomLeft *= var10;
/* 5339:6173 */       this.colorRedBottomRight *= var11;
/* 5340:6174 */       this.colorGreenBottomRight *= var11;
/* 5341:6175 */       this.colorBlueBottomRight *= var11;
/* 5342:6176 */       this.colorRedTopRight *= var12;
/* 5343:6177 */       this.colorGreenTopRight *= var12;
/* 5344:6178 */       this.colorBlueTopRight *= var12;
/* 5345:6179 */       IIcon var30 = getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 3);
/* 5346:6180 */       renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
/* 5347:6182 */       if ((fancyGrass) && (var30.getIconName().equals("grass_side")) && (!hasOverrideBlockTexture()))
/* 5348:     */       {
/* 5349:6184 */         this.colorRedTopLeft *= p_147808_5_;
/* 5350:6185 */         this.colorRedBottomLeft *= p_147808_5_;
/* 5351:6186 */         this.colorRedBottomRight *= p_147808_5_;
/* 5352:6187 */         this.colorRedTopRight *= p_147808_5_;
/* 5353:6188 */         this.colorGreenTopLeft *= p_147808_6_;
/* 5354:6189 */         this.colorGreenBottomLeft *= p_147808_6_;
/* 5355:6190 */         this.colorGreenBottomRight *= p_147808_6_;
/* 5356:6191 */         this.colorGreenTopRight *= p_147808_6_;
/* 5357:6192 */         this.colorBlueTopLeft *= p_147808_7_;
/* 5358:6193 */         this.colorBlueBottomLeft *= p_147808_7_;
/* 5359:6194 */         this.colorBlueBottomRight *= p_147808_7_;
/* 5360:6195 */         this.colorBlueTopRight *= p_147808_7_;
/* 5361:6196 */         renderFaceZPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
/* 5362:     */       }
/* 5363:6199 */       var8 = true;
/* 5364:     */     }
/* 5365:6202 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_, 4)))
/* 5366:     */     {
/* 5367:6204 */       if (this.renderMinX <= 0.0D) {
/* 5368:6206 */         p_147808_2_--;
/* 5369:     */       }
/* 5370:6209 */       this.aoLightValueScratchXYNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5371:6210 */       this.aoLightValueScratchXZNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5372:6211 */       this.aoLightValueScratchXZNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5373:6212 */       this.aoLightValueScratchXYNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5374:6213 */       this.aoBrightnessXYNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5375:6214 */       this.aoBrightnessXZNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5376:6215 */       this.aoBrightnessXZNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5377:6216 */       this.aoBrightnessXYNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5378:6217 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
/* 5379:6218 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
/* 5380:6219 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
/* 5381:6220 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
/* 5382:6222 */       if ((!var18) && (!var17))
/* 5383:     */       {
/* 5384:6224 */         this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
/* 5385:6225 */         this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
/* 5386:     */       }
/* 5387:     */       else
/* 5388:     */       {
/* 5389:6229 */         this.aoLightValueScratchXYZNNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
/* 5390:6230 */         this.aoBrightnessXYZNNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
/* 5391:     */       }
/* 5392:6233 */       if ((!var19) && (!var17))
/* 5393:     */       {
/* 5394:6235 */         this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
/* 5395:6236 */         this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
/* 5396:     */       }
/* 5397:     */       else
/* 5398:     */       {
/* 5399:6240 */         this.aoLightValueScratchXYZNNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
/* 5400:6241 */         this.aoBrightnessXYZNNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
/* 5401:     */       }
/* 5402:6244 */       if ((!var18) && (!var16))
/* 5403:     */       {
/* 5404:6246 */         this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
/* 5405:6247 */         this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
/* 5406:     */       }
/* 5407:     */       else
/* 5408:     */       {
/* 5409:6251 */         this.aoLightValueScratchXYZNPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
/* 5410:6252 */         this.aoBrightnessXYZNPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
/* 5411:     */       }
/* 5412:6255 */       if ((!var19) && (!var16))
/* 5413:     */       {
/* 5414:6257 */         this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
/* 5415:6258 */         this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
/* 5416:     */       }
/* 5417:     */       else
/* 5418:     */       {
/* 5419:6262 */         this.aoLightValueScratchXYZNPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
/* 5420:6263 */         this.aoBrightnessXYZNPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
/* 5421:     */       }
/* 5422:6266 */       if (this.renderMinX <= 0.0D) {
/* 5423:6268 */         p_147808_2_++;
/* 5424:     */       }
/* 5425:6271 */       int var20 = var14;
/* 5426:6273 */       if ((this.renderMinX <= 0.0D) || (!this.blockAccess.getBlock(p_147808_2_ - 1, p_147808_3_, p_147808_4_).isOpaqueCube())) {
/* 5427:6275 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5428:     */       }
/* 5429:6278 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_ - 1, p_147808_3_, p_147808_4_);
/* 5430:6279 */       float var22 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + var21 + this.aoLightValueScratchXZNP) / 4.0F;
/* 5431:6280 */       float var23 = (var21 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP) / 4.0F;
/* 5432:6281 */       float var24 = (this.aoLightValueScratchXZNN + var21 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP) / 4.0F;
/* 5433:6282 */       float var25 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + var21) / 4.0F;
/* 5434:6283 */       var9 = (float)(var23 * this.renderMaxY * this.renderMaxZ + var24 * this.renderMaxY * (1.0D - this.renderMaxZ) + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var22 * (1.0D - this.renderMaxY) * this.renderMaxZ);
/* 5435:6284 */       var10 = (float)(var23 * this.renderMaxY * this.renderMinZ + var24 * this.renderMaxY * (1.0D - this.renderMinZ) + var25 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var22 * (1.0D - this.renderMaxY) * this.renderMinZ);
/* 5436:6285 */       var11 = (float)(var23 * this.renderMinY * this.renderMinZ + var24 * this.renderMinY * (1.0D - this.renderMinZ) + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var22 * (1.0D - this.renderMinY) * this.renderMinZ);
/* 5437:6286 */       var12 = (float)(var23 * this.renderMinY * this.renderMaxZ + var24 * this.renderMinY * (1.0D - this.renderMaxZ) + var25 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var22 * (1.0D - this.renderMinY) * this.renderMaxZ);
/* 5438:6287 */       int var26 = getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, var20);
/* 5439:6288 */       int var27 = getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, var20);
/* 5440:6289 */       int var28 = getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, var20);
/* 5441:6290 */       int var29 = getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, var20);
/* 5442:6291 */       this.brightnessTopLeft = mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMaxZ, this.renderMaxY * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMaxY) * this.renderMaxZ);
/* 5443:6292 */       this.brightnessBottomLeft = mixAoBrightness(var27, var28, var29, var26, this.renderMaxY * this.renderMinZ, this.renderMaxY * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), (1.0D - this.renderMaxY) * this.renderMinZ);
/* 5444:6293 */       this.brightnessBottomRight = mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMinZ, this.renderMinY * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), (1.0D - this.renderMinY) * this.renderMinZ);
/* 5445:6294 */       this.brightnessTopRight = mixAoBrightness(var27, var28, var29, var26, this.renderMinY * this.renderMaxZ, this.renderMinY * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), (1.0D - this.renderMinY) * this.renderMaxZ);
/* 5446:6296 */       if (var13)
/* 5447:     */       {
/* 5448:6298 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6F);
/* 5449:6299 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6F);
/* 5450:6300 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6F);
/* 5451:     */       }
/* 5452:     */       else
/* 5453:     */       {
/* 5454:6304 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F);
/* 5455:6305 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F);
/* 5456:6306 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F);
/* 5457:     */       }
/* 5458:6309 */       this.colorRedTopLeft *= var9;
/* 5459:6310 */       this.colorGreenTopLeft *= var9;
/* 5460:6311 */       this.colorBlueTopLeft *= var9;
/* 5461:6312 */       this.colorRedBottomLeft *= var10;
/* 5462:6313 */       this.colorGreenBottomLeft *= var10;
/* 5463:6314 */       this.colorBlueBottomLeft *= var10;
/* 5464:6315 */       this.colorRedBottomRight *= var11;
/* 5465:6316 */       this.colorGreenBottomRight *= var11;
/* 5466:6317 */       this.colorBlueBottomRight *= var11;
/* 5467:6318 */       this.colorRedTopRight *= var12;
/* 5468:6319 */       this.colorGreenTopRight *= var12;
/* 5469:6320 */       this.colorBlueTopRight *= var12;
/* 5470:6321 */       IIcon var30 = getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 4);
/* 5471:6322 */       renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
/* 5472:6324 */       if ((fancyGrass) && (var30.getIconName().equals("grass_side")) && (!hasOverrideBlockTexture()))
/* 5473:     */       {
/* 5474:6326 */         this.colorRedTopLeft *= p_147808_5_;
/* 5475:6327 */         this.colorRedBottomLeft *= p_147808_5_;
/* 5476:6328 */         this.colorRedBottomRight *= p_147808_5_;
/* 5477:6329 */         this.colorRedTopRight *= p_147808_5_;
/* 5478:6330 */         this.colorGreenTopLeft *= p_147808_6_;
/* 5479:6331 */         this.colorGreenBottomLeft *= p_147808_6_;
/* 5480:6332 */         this.colorGreenBottomRight *= p_147808_6_;
/* 5481:6333 */         this.colorGreenTopRight *= p_147808_6_;
/* 5482:6334 */         this.colorBlueTopLeft *= p_147808_7_;
/* 5483:6335 */         this.colorBlueBottomLeft *= p_147808_7_;
/* 5484:6336 */         this.colorBlueBottomRight *= p_147808_7_;
/* 5485:6337 */         this.colorBlueTopRight *= p_147808_7_;
/* 5486:6338 */         renderFaceXNeg(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
/* 5487:     */       }
/* 5488:6341 */       var8 = true;
/* 5489:     */     }
/* 5490:6344 */     if ((this.renderAllFaces) || (p_147808_1_.shouldSideBeRendered(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_, 5)))
/* 5491:     */     {
/* 5492:6346 */       if (this.renderMaxX >= 1.0D) {
/* 5493:6348 */         p_147808_2_++;
/* 5494:     */       }
/* 5495:6351 */       this.aoLightValueScratchXYPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5496:6352 */       this.aoLightValueScratchXZPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5497:6353 */       this.aoLightValueScratchXZPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5498:6354 */       this.aoLightValueScratchXYPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5499:6355 */       this.aoBrightnessXYPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_);
/* 5500:6356 */       this.aoBrightnessXZPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ - 1);
/* 5501:6357 */       this.aoBrightnessXZPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_ + 1);
/* 5502:6358 */       this.aoBrightnessXYPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_);
/* 5503:6359 */       boolean var16 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ + 1, p_147808_4_).getCanBlockGrass();
/* 5504:6360 */       boolean var17 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_ - 1, p_147808_4_).getCanBlockGrass();
/* 5505:6361 */       boolean var18 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ + 1).getCanBlockGrass();
/* 5506:6362 */       boolean var19 = this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_ - 1).getCanBlockGrass();
/* 5507:6364 */       if ((!var17) && (!var19))
/* 5508:     */       {
/* 5509:6366 */         this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
/* 5510:6367 */         this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
/* 5511:     */       }
/* 5512:     */       else
/* 5513:     */       {
/* 5514:6371 */         this.aoLightValueScratchXYZPNN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
/* 5515:6372 */         this.aoBrightnessXYZPNN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ - 1);
/* 5516:     */       }
/* 5517:6375 */       if ((!var17) && (!var18))
/* 5518:     */       {
/* 5519:6377 */         this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
/* 5520:6378 */         this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
/* 5521:     */       }
/* 5522:     */       else
/* 5523:     */       {
/* 5524:6382 */         this.aoLightValueScratchXYZPNP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
/* 5525:6383 */         this.aoBrightnessXYZPNP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ - 1, p_147808_4_ + 1);
/* 5526:     */       }
/* 5527:6386 */       if ((!var16) && (!var19))
/* 5528:     */       {
/* 5529:6388 */         this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
/* 5530:6389 */         this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
/* 5531:     */       }
/* 5532:     */       else
/* 5533:     */       {
/* 5534:6393 */         this.aoLightValueScratchXYZPPN = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
/* 5535:6394 */         this.aoBrightnessXYZPPN = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ - 1);
/* 5536:     */       }
/* 5537:6397 */       if ((!var16) && (!var18))
/* 5538:     */       {
/* 5539:6399 */         this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
/* 5540:6400 */         this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
/* 5541:     */       }
/* 5542:     */       else
/* 5543:     */       {
/* 5544:6404 */         this.aoLightValueScratchXYZPPP = getAmbientOcclusionLightValue(p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
/* 5545:6405 */         this.aoBrightnessXYZPPP = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_, p_147808_3_ + 1, p_147808_4_ + 1);
/* 5546:     */       }
/* 5547:6408 */       if (this.renderMaxX >= 1.0D) {
/* 5548:6410 */         p_147808_2_--;
/* 5549:     */       }
/* 5550:6413 */       int var20 = var14;
/* 5551:6415 */       if ((this.renderMaxX >= 1.0D) || (!this.blockAccess.getBlock(p_147808_2_ + 1, p_147808_3_, p_147808_4_).isOpaqueCube())) {
/* 5552:6417 */         var20 = p_147808_1_.getBlockBrightness(this.blockAccess, p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5553:     */       }
/* 5554:6420 */       float var21 = getAmbientOcclusionLightValue(p_147808_2_ + 1, p_147808_3_, p_147808_4_);
/* 5555:6421 */       float var22 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + var21 + this.aoLightValueScratchXZPP) / 4.0F;
/* 5556:6422 */       float var23 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + var21) / 4.0F;
/* 5557:6423 */       float var24 = (this.aoLightValueScratchXZPN + var21 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP) / 4.0F;
/* 5558:6424 */       float var25 = (var21 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP) / 4.0F;
/* 5559:6425 */       var9 = (float)(var22 * (1.0D - this.renderMinY) * this.renderMaxZ + var23 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ) + var24 * this.renderMinY * (1.0D - this.renderMaxZ) + var25 * this.renderMinY * this.renderMaxZ);
/* 5560:6426 */       var10 = (float)(var22 * (1.0D - this.renderMinY) * this.renderMinZ + var23 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ) + var24 * this.renderMinY * (1.0D - this.renderMinZ) + var25 * this.renderMinY * this.renderMinZ);
/* 5561:6427 */       var11 = (float)(var22 * (1.0D - this.renderMaxY) * this.renderMinZ + var23 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ) + var24 * this.renderMaxY * (1.0D - this.renderMinZ) + var25 * this.renderMaxY * this.renderMinZ);
/* 5562:6428 */       var12 = (float)(var22 * (1.0D - this.renderMaxY) * this.renderMaxZ + var23 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ) + var24 * this.renderMaxY * (1.0D - this.renderMaxZ) + var25 * this.renderMaxY * this.renderMaxZ);
/* 5563:6429 */       int var26 = getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, var20);
/* 5564:6430 */       int var27 = getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, var20);
/* 5565:6431 */       int var28 = getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, var20);
/* 5566:6432 */       int var29 = getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, var20);
/* 5567:6433 */       this.brightnessTopLeft = mixAoBrightness(var26, var29, var28, var27, (1.0D - this.renderMinY) * this.renderMaxZ, (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ), this.renderMinY * (1.0D - this.renderMaxZ), this.renderMinY * this.renderMaxZ);
/* 5568:6434 */       this.brightnessBottomLeft = mixAoBrightness(var26, var29, var28, var27, (1.0D - this.renderMinY) * this.renderMinZ, (1.0D - this.renderMinY) * (1.0D - this.renderMinZ), this.renderMinY * (1.0D - this.renderMinZ), this.renderMinY * this.renderMinZ);
/* 5569:6435 */       this.brightnessBottomRight = mixAoBrightness(var26, var29, var28, var27, (1.0D - this.renderMaxY) * this.renderMinZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ), this.renderMaxY * (1.0D - this.renderMinZ), this.renderMaxY * this.renderMinZ);
/* 5570:6436 */       this.brightnessTopRight = mixAoBrightness(var26, var29, var28, var27, (1.0D - this.renderMaxY) * this.renderMaxZ, (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ), this.renderMaxY * (1.0D - this.renderMaxZ), this.renderMaxY * this.renderMaxZ);
/* 5571:6438 */       if (var13)
/* 5572:     */       {
/* 5573:6440 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = p_147808_5_ * 0.6F);
/* 5574:6441 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = p_147808_6_ * 0.6F);
/* 5575:6442 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = p_147808_7_ * 0.6F);
/* 5576:     */       }
/* 5577:     */       else
/* 5578:     */       {
/* 5579:6446 */         this.colorRedTopLeft = (this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F);
/* 5580:6447 */         this.colorGreenTopLeft = (this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F);
/* 5581:6448 */         this.colorBlueTopLeft = (this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F);
/* 5582:     */       }
/* 5583:6451 */       this.colorRedTopLeft *= var9;
/* 5584:6452 */       this.colorGreenTopLeft *= var9;
/* 5585:6453 */       this.colorBlueTopLeft *= var9;
/* 5586:6454 */       this.colorRedBottomLeft *= var10;
/* 5587:6455 */       this.colorGreenBottomLeft *= var10;
/* 5588:6456 */       this.colorBlueBottomLeft *= var10;
/* 5589:6457 */       this.colorRedBottomRight *= var11;
/* 5590:6458 */       this.colorGreenBottomRight *= var11;
/* 5591:6459 */       this.colorBlueBottomRight *= var11;
/* 5592:6460 */       this.colorRedTopRight *= var12;
/* 5593:6461 */       this.colorGreenTopRight *= var12;
/* 5594:6462 */       this.colorBlueTopRight *= var12;
/* 5595:6463 */       IIcon var30 = getBlockIcon(p_147808_1_, this.blockAccess, p_147808_2_, p_147808_3_, p_147808_4_, 5);
/* 5596:6464 */       renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, var30);
/* 5597:6466 */       if ((fancyGrass) && (var30.getIconName().equals("grass_side")) && (!hasOverrideBlockTexture()))
/* 5598:     */       {
/* 5599:6468 */         this.colorRedTopLeft *= p_147808_5_;
/* 5600:6469 */         this.colorRedBottomLeft *= p_147808_5_;
/* 5601:6470 */         this.colorRedBottomRight *= p_147808_5_;
/* 5602:6471 */         this.colorRedTopRight *= p_147808_5_;
/* 5603:6472 */         this.colorGreenTopLeft *= p_147808_6_;
/* 5604:6473 */         this.colorGreenBottomLeft *= p_147808_6_;
/* 5605:6474 */         this.colorGreenBottomRight *= p_147808_6_;
/* 5606:6475 */         this.colorGreenTopRight *= p_147808_6_;
/* 5607:6476 */         this.colorBlueTopLeft *= p_147808_7_;
/* 5608:6477 */         this.colorBlueBottomLeft *= p_147808_7_;
/* 5609:6478 */         this.colorBlueBottomRight *= p_147808_7_;
/* 5610:6479 */         this.colorBlueTopRight *= p_147808_7_;
/* 5611:6480 */         renderFaceXPos(p_147808_1_, p_147808_2_, p_147808_3_, p_147808_4_, BlockGrass.func_149990_e());
/* 5612:     */       }
/* 5613:6483 */       var8 = true;
/* 5614:     */     }
/* 5615:6486 */     this.enableAO = false;
/* 5616:6487 */     return var8;
/* 5617:     */   }
/* 5618:     */   
/* 5619:     */   public int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_)
/* 5620:     */   {
/* 5621:6492 */     if (p_147778_1_ == 0) {
/* 5622:6494 */       p_147778_1_ = p_147778_4_;
/* 5623:     */     }
/* 5624:6497 */     if (p_147778_2_ == 0) {
/* 5625:6499 */       p_147778_2_ = p_147778_4_;
/* 5626:     */     }
/* 5627:6502 */     if (p_147778_3_ == 0) {
/* 5628:6504 */       p_147778_3_ = p_147778_4_;
/* 5629:     */     }
/* 5630:6507 */     return p_147778_1_ + p_147778_2_ + p_147778_3_ + p_147778_4_ >> 2 & 0xFF00FF;
/* 5631:     */   }
/* 5632:     */   
/* 5633:     */   public int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_, double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_)
/* 5634:     */   {
/* 5635:6512 */     int var13 = (int)((p_147727_1_ >> 16 & 0xFF) * p_147727_5_ + (p_147727_2_ >> 16 & 0xFF) * p_147727_7_ + (p_147727_3_ >> 16 & 0xFF) * p_147727_9_ + (p_147727_4_ >> 16 & 0xFF) * p_147727_11_) & 0xFF;
/* 5636:6513 */     int var14 = (int)((p_147727_1_ & 0xFF) * p_147727_5_ + (p_147727_2_ & 0xFF) * p_147727_7_ + (p_147727_3_ & 0xFF) * p_147727_9_ + (p_147727_4_ & 0xFF) * p_147727_11_) & 0xFF;
/* 5637:6514 */     return var13 << 16 | var14;
/* 5638:     */   }
/* 5639:     */   
/* 5640:     */   public boolean renderStandardBlockWithColorMultiplier(Block p_147736_1_, int p_147736_2_, int p_147736_3_, int p_147736_4_, float p_147736_5_, float p_147736_6_, float p_147736_7_)
/* 5641:     */   {
/* 5642:6519 */     this.enableAO = false;
/* 5643:6520 */     boolean defaultTexture = Tessellator.instance.defaultTexture;
/* 5644:6521 */     boolean betterGrass = (Config.isBetterGrass()) && (defaultTexture);
/* 5645:6522 */     Tessellator var8 = Tessellator.instance;
/* 5646:6523 */     boolean var9 = false;
/* 5647:6524 */     int var26 = -1;
/* 5648:6530 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_, 0)))
/* 5649:     */     {
/* 5650:6532 */       if (var26 < 0) {
/* 5651:6534 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5652:     */       }
/* 5653:6537 */       float var27 = 0.5F;
/* 5654:6538 */       float var13 = var27;
/* 5655:6539 */       float var19 = var27;
/* 5656:6540 */       float var22 = var27;
/* 5657:6542 */       if (p_147736_1_ != Blocks.grass)
/* 5658:     */       {
/* 5659:6544 */         var13 = var27 * p_147736_5_;
/* 5660:6545 */         var19 = var27 * p_147736_6_;
/* 5661:6546 */         var22 = var27 * p_147736_7_;
/* 5662:     */       }
/* 5663:6549 */       var8.setBrightness(this.renderMinY > 0.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ - 1, p_147736_4_));
/* 5664:6550 */       var8.setColorOpaque_F(var13, var19, var22);
/* 5665:6551 */       renderFaceYNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 0));
/* 5666:6552 */       var9 = true;
/* 5667:     */     }
/* 5668:6555 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_, 1)))
/* 5669:     */     {
/* 5670:6557 */       if (var26 < 0) {
/* 5671:6559 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5672:     */       }
/* 5673:6562 */       float var27 = 1.0F;
/* 5674:6563 */       float var13 = var27 * p_147736_5_;
/* 5675:6564 */       float var19 = var27 * p_147736_6_;
/* 5676:6565 */       float var22 = var27 * p_147736_7_;
/* 5677:6566 */       var8.setBrightness(this.renderMaxY < 1.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_ + 1, p_147736_4_));
/* 5678:6567 */       var8.setColorOpaque_F(var13, var19, var22);
/* 5679:6568 */       renderFaceYPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 1));
/* 5680:6569 */       var9 = true;
/* 5681:     */     }
/* 5682:6575 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1, 2)))
/* 5683:     */     {
/* 5684:6577 */       if (var26 < 0) {
/* 5685:6579 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5686:     */       }
/* 5687:6582 */       float var13 = 0.8F;
/* 5688:6583 */       float var19 = var13;
/* 5689:6584 */       float var22 = var13;
/* 5690:6585 */       float var25 = var13;
/* 5691:6587 */       if (p_147736_1_ != Blocks.grass)
/* 5692:     */       {
/* 5693:6589 */         var19 = var13 * p_147736_5_;
/* 5694:6590 */         var22 = var13 * p_147736_6_;
/* 5695:6591 */         var25 = var13 * p_147736_7_;
/* 5696:     */       }
/* 5697:6594 */       var8.setBrightness(this.renderMinZ > 0.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ - 1));
/* 5698:6595 */       var8.setColorOpaque_F(var19, var22, var25);
/* 5699:6596 */       IIcon var271 = getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
/* 5700:6598 */       if (betterGrass)
/* 5701:     */       {
/* 5702:6600 */         if ((var271 == TextureUtils.iconGrassSide) || (var271 == TextureUtils.iconMyceliumSide))
/* 5703:     */         {
/* 5704:6602 */           var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2, var271);
/* 5705:6604 */           if (var271 == TextureUtils.iconGrassTop) {
/* 5706:6606 */             var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5707:     */           }
/* 5708:     */         }
/* 5709:6610 */         if (var271 == TextureUtils.iconGrassSideSnowed) {
/* 5710:6612 */           var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 2);
/* 5711:     */         }
/* 5712:     */       }
/* 5713:6616 */       renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
/* 5714:6618 */       if ((defaultTexture) && (fancyGrass) && (var271 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 5715:     */       {
/* 5716:6620 */         var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5717:6621 */         renderFaceZNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
/* 5718:     */       }
/* 5719:6624 */       var9 = true;
/* 5720:     */     }
/* 5721:6627 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1, 3)))
/* 5722:     */     {
/* 5723:6629 */       if (var26 < 0) {
/* 5724:6631 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5725:     */       }
/* 5726:6634 */       float var13 = 0.8F;
/* 5727:6635 */       float var19 = var13;
/* 5728:6636 */       float var22 = var13;
/* 5729:6637 */       float var25 = var13;
/* 5730:6639 */       if (p_147736_1_ != Blocks.grass)
/* 5731:     */       {
/* 5732:6641 */         var19 = var13 * p_147736_5_;
/* 5733:6642 */         var22 = var13 * p_147736_6_;
/* 5734:6643 */         var25 = var13 * p_147736_7_;
/* 5735:     */       }
/* 5736:6646 */       var8.setBrightness(this.renderMaxZ < 1.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_ + 1));
/* 5737:6647 */       var8.setColorOpaque_F(var19, var22, var25);
/* 5738:6648 */       IIcon var271 = getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
/* 5739:6650 */       if (betterGrass)
/* 5740:     */       {
/* 5741:6652 */         if ((var271 == TextureUtils.iconGrassSide) || (var271 == TextureUtils.iconMyceliumSide))
/* 5742:     */         {
/* 5743:6654 */           var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3, var271);
/* 5744:6656 */           if (var271 == TextureUtils.iconGrassTop) {
/* 5745:6658 */             var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5746:     */           }
/* 5747:     */         }
/* 5748:6662 */         if (var271 == TextureUtils.iconGrassSideSnowed) {
/* 5749:6664 */           var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 3);
/* 5750:     */         }
/* 5751:     */       }
/* 5752:6668 */       renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
/* 5753:6670 */       if ((defaultTexture) && (fancyGrass) && (var271 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 5754:     */       {
/* 5755:6672 */         var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5756:6673 */         renderFaceZPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
/* 5757:     */       }
/* 5758:6676 */       var9 = true;
/* 5759:     */     }
/* 5760:6679 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_, 4)))
/* 5761:     */     {
/* 5762:6681 */       if (var26 < 0) {
/* 5763:6683 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5764:     */       }
/* 5765:6686 */       float var13 = 0.6F;
/* 5766:6687 */       float var19 = var13;
/* 5767:6688 */       float var22 = var13;
/* 5768:6689 */       float var25 = var13;
/* 5769:6691 */       if (p_147736_1_ != Blocks.grass)
/* 5770:     */       {
/* 5771:6693 */         var19 = var13 * p_147736_5_;
/* 5772:6694 */         var22 = var13 * p_147736_6_;
/* 5773:6695 */         var25 = var13 * p_147736_7_;
/* 5774:     */       }
/* 5775:6698 */       var8.setBrightness(this.renderMinX > 0.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ - 1, p_147736_3_, p_147736_4_));
/* 5776:6699 */       var8.setColorOpaque_F(var19, var22, var25);
/* 5777:6700 */       IIcon var271 = getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
/* 5778:6702 */       if (betterGrass)
/* 5779:     */       {
/* 5780:6704 */         if ((var271 == TextureUtils.iconGrassSide) || (var271 == TextureUtils.iconMyceliumSide))
/* 5781:     */         {
/* 5782:6706 */           var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4, var271);
/* 5783:6708 */           if (var271 == TextureUtils.iconGrassTop) {
/* 5784:6710 */             var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5785:     */           }
/* 5786:     */         }
/* 5787:6714 */         if (var271 == TextureUtils.iconGrassSideSnowed) {
/* 5788:6716 */           var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 4);
/* 5789:     */         }
/* 5790:     */       }
/* 5791:6720 */       renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
/* 5792:6722 */       if ((defaultTexture) && (fancyGrass) && (var271 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 5793:     */       {
/* 5794:6724 */         var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5795:6725 */         renderFaceXNeg(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
/* 5796:     */       }
/* 5797:6728 */       var9 = true;
/* 5798:     */     }
/* 5799:6731 */     if ((this.renderAllFaces) || (p_147736_1_.shouldSideBeRendered(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_, 5)))
/* 5800:     */     {
/* 5801:6733 */       if (var26 < 0) {
/* 5802:6735 */         var26 = p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_);
/* 5803:     */       }
/* 5804:6738 */       float var13 = 0.6F;
/* 5805:6739 */       float var19 = var13;
/* 5806:6740 */       float var22 = var13;
/* 5807:6741 */       float var25 = var13;
/* 5808:6743 */       if (p_147736_1_ != Blocks.grass)
/* 5809:     */       {
/* 5810:6745 */         var19 = var13 * p_147736_5_;
/* 5811:6746 */         var22 = var13 * p_147736_6_;
/* 5812:6747 */         var25 = var13 * p_147736_7_;
/* 5813:     */       }
/* 5814:6750 */       var8.setBrightness(this.renderMaxX < 1.0D ? var26 : p_147736_1_.getBlockBrightness(this.blockAccess, p_147736_2_ + 1, p_147736_3_, p_147736_4_));
/* 5815:6751 */       var8.setColorOpaque_F(var19, var22, var25);
/* 5816:6752 */       IIcon var271 = getBlockIcon(p_147736_1_, this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
/* 5817:6754 */       if (betterGrass)
/* 5818:     */       {
/* 5819:6756 */         if ((var271 == TextureUtils.iconGrassSide) || (var271 == TextureUtils.iconMyceliumSide))
/* 5820:     */         {
/* 5821:6758 */           var271 = Config.getSideGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5, var271);
/* 5822:6760 */           if (var271 == TextureUtils.iconGrassTop) {
/* 5823:6762 */             var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5824:     */           }
/* 5825:     */         }
/* 5826:6766 */         if (var271 == TextureUtils.iconGrassSideSnowed) {
/* 5827:6768 */           var271 = Config.getSideSnowGrassTexture(this.blockAccess, p_147736_2_, p_147736_3_, p_147736_4_, 5);
/* 5828:     */         }
/* 5829:     */       }
/* 5830:6772 */       renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, var271);
/* 5831:6774 */       if ((defaultTexture) && (fancyGrass) && (var271 == TextureUtils.iconGrassSide) && (!hasOverrideBlockTexture()))
/* 5832:     */       {
/* 5833:6776 */         var8.setColorOpaque_F(var19 * p_147736_5_, var22 * p_147736_6_, var25 * p_147736_7_);
/* 5834:6777 */         renderFaceXPos(p_147736_1_, p_147736_2_, p_147736_3_, p_147736_4_, BlockGrass.func_149990_e());
/* 5835:     */       }
/* 5836:6780 */       var9 = true;
/* 5837:     */     }
/* 5838:6783 */     return var9;
/* 5839:     */   }
/* 5840:     */   
/* 5841:     */   public boolean renderBlockCocoa(BlockCocoa p_147772_1_, int p_147772_2_, int p_147772_3_, int p_147772_4_)
/* 5842:     */   {
/* 5843:6788 */     Tessellator var5 = Tessellator.instance;
/* 5844:6789 */     var5.setBrightness(p_147772_1_.getBlockBrightness(this.blockAccess, p_147772_2_, p_147772_3_, p_147772_4_));
/* 5845:6790 */     var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
/* 5846:6791 */     int var6 = this.blockAccess.getBlockMetadata(p_147772_2_, p_147772_3_, p_147772_4_);
/* 5847:6792 */     int var7 = BlockDirectional.func_149895_l(var6);
/* 5848:6793 */     int var8 = BlockCocoa.func_149987_c(var6);
/* 5849:6794 */     IIcon var9 = p_147772_1_.func_149988_b(var8);
/* 5850:6795 */     int var10 = 4 + var8 * 2;
/* 5851:6796 */     int var11 = 5 + var8 * 2;
/* 5852:6797 */     double var12 = 15.0D - var10;
/* 5853:6798 */     double var14 = 15.0D;
/* 5854:6799 */     double var16 = 4.0D;
/* 5855:6800 */     double var18 = 4.0D + var11;
/* 5856:6801 */     double var20 = var9.getInterpolatedU(var12);
/* 5857:6802 */     double var22 = var9.getInterpolatedU(var14);
/* 5858:6803 */     double var24 = var9.getInterpolatedV(var16);
/* 5859:6804 */     double var26 = var9.getInterpolatedV(var18);
/* 5860:6805 */     double var28 = 0.0D;
/* 5861:6806 */     double var30 = 0.0D;
/* 5862:6808 */     switch (var7)
/* 5863:     */     {
/* 5864:     */     case 0: 
/* 5865:6811 */       var28 = 8.0D - var10 / 2;
/* 5866:6812 */       var30 = 15.0D - var10;
/* 5867:6813 */       break;
/* 5868:     */     case 1: 
/* 5869:6816 */       var28 = 1.0D;
/* 5870:6817 */       var30 = 8.0D - var10 / 2;
/* 5871:6818 */       break;
/* 5872:     */     case 2: 
/* 5873:6821 */       var28 = 8.0D - var10 / 2;
/* 5874:6822 */       var30 = 1.0D;
/* 5875:6823 */       break;
/* 5876:     */     case 3: 
/* 5877:6826 */       var28 = 15.0D - var10;
/* 5878:6827 */       var30 = 8.0D - var10 / 2;
/* 5879:     */     }
/* 5880:6830 */     double var32 = p_147772_2_ + var28 / 16.0D;
/* 5881:6831 */     double var34 = p_147772_2_ + (var28 + var10) / 16.0D;
/* 5882:6832 */     double var36 = p_147772_3_ + (12.0D - var11) / 16.0D;
/* 5883:6833 */     double var38 = p_147772_3_ + 0.75D;
/* 5884:6834 */     double var40 = p_147772_4_ + var30 / 16.0D;
/* 5885:6835 */     double var42 = p_147772_4_ + (var30 + var10) / 16.0D;
/* 5886:6836 */     var5.addVertexWithUV(var32, var36, var40, var20, var26);
/* 5887:6837 */     var5.addVertexWithUV(var32, var36, var42, var22, var26);
/* 5888:6838 */     var5.addVertexWithUV(var32, var38, var42, var22, var24);
/* 5889:6839 */     var5.addVertexWithUV(var32, var38, var40, var20, var24);
/* 5890:6840 */     var5.addVertexWithUV(var34, var36, var42, var20, var26);
/* 5891:6841 */     var5.addVertexWithUV(var34, var36, var40, var22, var26);
/* 5892:6842 */     var5.addVertexWithUV(var34, var38, var40, var22, var24);
/* 5893:6843 */     var5.addVertexWithUV(var34, var38, var42, var20, var24);
/* 5894:6844 */     var5.addVertexWithUV(var34, var36, var40, var20, var26);
/* 5895:6845 */     var5.addVertexWithUV(var32, var36, var40, var22, var26);
/* 5896:6846 */     var5.addVertexWithUV(var32, var38, var40, var22, var24);
/* 5897:6847 */     var5.addVertexWithUV(var34, var38, var40, var20, var24);
/* 5898:6848 */     var5.addVertexWithUV(var32, var36, var42, var20, var26);
/* 5899:6849 */     var5.addVertexWithUV(var34, var36, var42, var22, var26);
/* 5900:6850 */     var5.addVertexWithUV(var34, var38, var42, var22, var24);
/* 5901:6851 */     var5.addVertexWithUV(var32, var38, var42, var20, var24);
/* 5902:6852 */     int var44 = var10;
/* 5903:6854 */     if (var8 >= 2) {
/* 5904:6856 */       var44 = var10 - 1;
/* 5905:     */     }
/* 5906:6859 */     var20 = var9.getMinU();
/* 5907:6860 */     var22 = var9.getInterpolatedU(var44);
/* 5908:6861 */     var24 = var9.getMinV();
/* 5909:6862 */     var26 = var9.getInterpolatedV(var44);
/* 5910:6863 */     var5.addVertexWithUV(var32, var38, var42, var20, var26);
/* 5911:6864 */     var5.addVertexWithUV(var34, var38, var42, var22, var26);
/* 5912:6865 */     var5.addVertexWithUV(var34, var38, var40, var22, var24);
/* 5913:6866 */     var5.addVertexWithUV(var32, var38, var40, var20, var24);
/* 5914:6867 */     var5.addVertexWithUV(var32, var36, var40, var20, var24);
/* 5915:6868 */     var5.addVertexWithUV(var34, var36, var40, var22, var24);
/* 5916:6869 */     var5.addVertexWithUV(var34, var36, var42, var22, var26);
/* 5917:6870 */     var5.addVertexWithUV(var32, var36, var42, var20, var26);
/* 5918:6871 */     var20 = var9.getInterpolatedU(12.0D);
/* 5919:6872 */     var22 = var9.getMaxU();
/* 5920:6873 */     var24 = var9.getMinV();
/* 5921:6874 */     var26 = var9.getInterpolatedV(4.0D);
/* 5922:6875 */     var28 = 8.0D;
/* 5923:6876 */     var30 = 0.0D;
/* 5924:6879 */     switch (var7)
/* 5925:     */     {
/* 5926:     */     case 0: 
/* 5927:6882 */       var28 = 8.0D;
/* 5928:6883 */       var30 = 12.0D;
/* 5929:6884 */       double var45 = var20;
/* 5930:6885 */       var20 = var22;
/* 5931:6886 */       var22 = var45;
/* 5932:6887 */       break;
/* 5933:     */     case 1: 
/* 5934:6890 */       var28 = 0.0D;
/* 5935:6891 */       var30 = 8.0D;
/* 5936:6892 */       break;
/* 5937:     */     case 2: 
/* 5938:6895 */       var28 = 8.0D;
/* 5939:6896 */       var30 = 0.0D;
/* 5940:6897 */       break;
/* 5941:     */     case 3: 
/* 5942:6900 */       var28 = 12.0D;
/* 5943:6901 */       var30 = 8.0D;
/* 5944:6902 */       double var45 = var20;
/* 5945:6903 */       var20 = var22;
/* 5946:6904 */       var22 = var45;
/* 5947:     */     }
/* 5948:6907 */     var32 = p_147772_2_ + var28 / 16.0D;
/* 5949:6908 */     var34 = p_147772_2_ + (var28 + 4.0D) / 16.0D;
/* 5950:6909 */     var36 = p_147772_3_ + 0.75D;
/* 5951:6910 */     var38 = p_147772_3_ + 1.0D;
/* 5952:6911 */     var40 = p_147772_4_ + var30 / 16.0D;
/* 5953:6912 */     var42 = p_147772_4_ + (var30 + 4.0D) / 16.0D;
/* 5954:6914 */     if ((var7 != 2) && (var7 != 0))
/* 5955:     */     {
/* 5956:6916 */       if ((var7 == 1) || (var7 == 3))
/* 5957:     */       {
/* 5958:6918 */         var5.addVertexWithUV(var34, var36, var40, var20, var26);
/* 5959:6919 */         var5.addVertexWithUV(var32, var36, var40, var22, var26);
/* 5960:6920 */         var5.addVertexWithUV(var32, var38, var40, var22, var24);
/* 5961:6921 */         var5.addVertexWithUV(var34, var38, var40, var20, var24);
/* 5962:6922 */         var5.addVertexWithUV(var32, var36, var40, var22, var26);
/* 5963:6923 */         var5.addVertexWithUV(var34, var36, var40, var20, var26);
/* 5964:6924 */         var5.addVertexWithUV(var34, var38, var40, var20, var24);
/* 5965:6925 */         var5.addVertexWithUV(var32, var38, var40, var22, var24);
/* 5966:     */       }
/* 5967:     */     }
/* 5968:     */     else
/* 5969:     */     {
/* 5970:6930 */       var5.addVertexWithUV(var32, var36, var40, var22, var26);
/* 5971:6931 */       var5.addVertexWithUV(var32, var36, var42, var20, var26);
/* 5972:6932 */       var5.addVertexWithUV(var32, var38, var42, var20, var24);
/* 5973:6933 */       var5.addVertexWithUV(var32, var38, var40, var22, var24);
/* 5974:6934 */       var5.addVertexWithUV(var32, var36, var42, var20, var26);
/* 5975:6935 */       var5.addVertexWithUV(var32, var36, var40, var22, var26);
/* 5976:6936 */       var5.addVertexWithUV(var32, var38, var40, var22, var24);
/* 5977:6937 */       var5.addVertexWithUV(var32, var38, var42, var20, var24);
/* 5978:     */     }
/* 5979:6940 */     return true;
/* 5980:     */   }
/* 5981:     */   
/* 5982:     */   public boolean renderBlockBeacon(BlockBeacon p_147797_1_, int p_147797_2_, int p_147797_3_, int p_147797_4_)
/* 5983:     */   {
/* 5984:6945 */     float var5 = 0.1875F;
/* 5985:6946 */     setOverrideBlockTexture(getBlockIcon(Blocks.glass));
/* 5986:6947 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 5987:6948 */     renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
/* 5988:6949 */     this.renderAllFaces = true;
/* 5989:6950 */     setOverrideBlockTexture(getBlockIcon(Blocks.obsidian));
/* 5990:6951 */     setRenderBounds(0.125D, 0.006250000093132258D, 0.125D, 0.875D, var5, 0.875D);
/* 5991:6952 */     renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
/* 5992:6953 */     setOverrideBlockTexture(getBlockIcon(Blocks.beacon));
/* 5993:6954 */     setRenderBounds(0.1875D, var5, 0.1875D, 0.8125D, 0.875D, 0.8125D);
/* 5994:6955 */     renderStandardBlock(p_147797_1_, p_147797_2_, p_147797_3_, p_147797_4_);
/* 5995:6956 */     this.renderAllFaces = false;
/* 5996:6957 */     clearOverrideBlockTexture();
/* 5997:6958 */     return true;
/* 5998:     */   }
/* 5999:     */   
/* 6000:     */   public boolean renderBlockCactus(Block p_147755_1_, int p_147755_2_, int p_147755_3_, int p_147755_4_)
/* 6001:     */   {
/* 6002:6963 */     int var5 = p_147755_1_.colorMultiplier(this.blockAccess, p_147755_2_, p_147755_3_, p_147755_4_);
/* 6003:6964 */     float var6 = (var5 >> 16 & 0xFF) / 255.0F;
/* 6004:6965 */     float var7 = (var5 >> 8 & 0xFF) / 255.0F;
/* 6005:6966 */     float var8 = (var5 & 0xFF) / 255.0F;
/* 6006:6968 */     if (EntityRenderer.anaglyphEnable)
/* 6007:     */     {
/* 6008:6970 */       float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
/* 6009:6971 */       float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
/* 6010:6972 */       float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
/* 6011:6973 */       var6 = var9;
/* 6012:6974 */       var7 = var10;
/* 6013:6975 */       var8 = var11;
/* 6014:     */     }
/* 6015:6978 */     return renderBlockCactusImpl(p_147755_1_, p_147755_2_, p_147755_3_, p_147755_4_, var6, var7, var8);
/* 6016:     */   }
/* 6017:     */   
/* 6018:     */   public boolean renderBlockCactusImpl(Block p_147754_1_, int p_147754_2_, int p_147754_3_, int p_147754_4_, float p_147754_5_, float p_147754_6_, float p_147754_7_)
/* 6019:     */   {
/* 6020:6983 */     Tessellator var8 = Tessellator.instance;
/* 6021:6984 */     boolean var9 = false;
/* 6022:6985 */     float var10 = 0.5F;
/* 6023:6986 */     float var11 = 1.0F;
/* 6024:6987 */     float var12 = 0.8F;
/* 6025:6988 */     float var13 = 0.6F;
/* 6026:6989 */     float var14 = var10 * p_147754_5_;
/* 6027:6990 */     float var15 = var11 * p_147754_5_;
/* 6028:6991 */     float var16 = var12 * p_147754_5_;
/* 6029:6992 */     float var17 = var13 * p_147754_5_;
/* 6030:6993 */     float var18 = var10 * p_147754_6_;
/* 6031:6994 */     float var19 = var11 * p_147754_6_;
/* 6032:6995 */     float var20 = var12 * p_147754_6_;
/* 6033:6996 */     float var21 = var13 * p_147754_6_;
/* 6034:6997 */     float var22 = var10 * p_147754_7_;
/* 6035:6998 */     float var23 = var11 * p_147754_7_;
/* 6036:6999 */     float var24 = var12 * p_147754_7_;
/* 6037:7000 */     float var25 = var13 * p_147754_7_;
/* 6038:7001 */     float var26 = 0.0625F;
/* 6039:7002 */     int var27 = p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_);
/* 6040:7004 */     if ((this.renderAllFaces) || (p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_, 0)))
/* 6041:     */     {
/* 6042:7006 */       var8.setBrightness(this.renderMinY > 0.0D ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ - 1, p_147754_4_));
/* 6043:7007 */       var8.setColorOpaque_F(var14, var18, var22);
/* 6044:7008 */       renderFaceYNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 0));
/* 6045:     */     }
/* 6046:7011 */     if ((this.renderAllFaces) || (p_147754_1_.shouldSideBeRendered(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_, 1)))
/* 6047:     */     {
/* 6048:7013 */       var8.setBrightness(this.renderMaxY < 1.0D ? var27 : p_147754_1_.getBlockBrightness(this.blockAccess, p_147754_2_, p_147754_3_ + 1, p_147754_4_));
/* 6049:7014 */       var8.setColorOpaque_F(var15, var19, var23);
/* 6050:7015 */       renderFaceYPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 1));
/* 6051:     */     }
/* 6052:7018 */     var8.setBrightness(var27);
/* 6053:7019 */     var8.setColorOpaque_F(var16, var20, var24);
/* 6054:7020 */     var8.addTranslation(0.0F, 0.0F, var26);
/* 6055:7021 */     renderFaceZNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 2));
/* 6056:7022 */     var8.addTranslation(0.0F, 0.0F, -var26);
/* 6057:7023 */     var8.addTranslation(0.0F, 0.0F, -var26);
/* 6058:7024 */     renderFaceZPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 3));
/* 6059:7025 */     var8.addTranslation(0.0F, 0.0F, var26);
/* 6060:7026 */     var8.setColorOpaque_F(var17, var21, var25);
/* 6061:7027 */     var8.addTranslation(var26, 0.0F, 0.0F);
/* 6062:7028 */     renderFaceXNeg(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 4));
/* 6063:7029 */     var8.addTranslation(-var26, 0.0F, 0.0F);
/* 6064:7030 */     var8.addTranslation(-var26, 0.0F, 0.0F);
/* 6065:7031 */     renderFaceXPos(p_147754_1_, p_147754_2_, p_147754_3_, p_147754_4_, getBlockIcon(p_147754_1_, this.blockAccess, p_147754_2_, p_147754_3_, p_147754_4_, 5));
/* 6066:7032 */     var8.addTranslation(var26, 0.0F, 0.0F);
/* 6067:7033 */     return true;
/* 6068:     */   }
/* 6069:     */   
/* 6070:     */   public boolean renderBlockFence(BlockFence p_147735_1_, int p_147735_2_, int p_147735_3_, int p_147735_4_)
/* 6071:     */   {
/* 6072:7038 */     boolean var5 = false;
/* 6073:7039 */     float var6 = 0.375F;
/* 6074:7040 */     float var7 = 0.625F;
/* 6075:7041 */     setRenderBounds(var6, 0.0D, var6, var7, 1.0D, var7);
/* 6076:7042 */     renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6077:7043 */     var5 = true;
/* 6078:7044 */     boolean var8 = false;
/* 6079:7045 */     boolean var9 = false;
/* 6080:7047 */     if ((p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_)) || (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_))) {
/* 6081:7049 */       var8 = true;
/* 6082:     */     }
/* 6083:7052 */     if ((p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1)) || (p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1))) {
/* 6084:7054 */       var9 = true;
/* 6085:     */     }
/* 6086:7057 */     boolean var10 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ - 1, p_147735_3_, p_147735_4_);
/* 6087:7058 */     boolean var11 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_ + 1, p_147735_3_, p_147735_4_);
/* 6088:7059 */     boolean var12 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ - 1);
/* 6089:7060 */     boolean var13 = p_147735_1_.func_149826_e(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_ + 1);
/* 6090:7062 */     if ((!var8) && (!var9)) {
/* 6091:7064 */       var8 = true;
/* 6092:     */     }
/* 6093:7067 */     var6 = 0.4375F;
/* 6094:7068 */     var7 = 0.5625F;
/* 6095:7069 */     float var14 = 0.75F;
/* 6096:7070 */     float var15 = 0.9375F;
/* 6097:7071 */     float var16 = var10 ? 0.0F : var6;
/* 6098:7072 */     float var17 = var11 ? 1.0F : var7;
/* 6099:7073 */     float var18 = var12 ? 0.0F : var6;
/* 6100:7074 */     float var19 = var13 ? 1.0F : var7;
/* 6101:7076 */     if (var8)
/* 6102:     */     {
/* 6103:7078 */       setRenderBounds(var16, var14, var6, var17, var15, var7);
/* 6104:7079 */       renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6105:7080 */       var5 = true;
/* 6106:     */     }
/* 6107:7083 */     if (var9)
/* 6108:     */     {
/* 6109:7085 */       setRenderBounds(var6, var14, var18, var7, var15, var19);
/* 6110:7086 */       renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6111:7087 */       var5 = true;
/* 6112:     */     }
/* 6113:7090 */     var14 = 0.375F;
/* 6114:7091 */     var15 = 0.5625F;
/* 6115:7093 */     if (var8)
/* 6116:     */     {
/* 6117:7095 */       setRenderBounds(var16, var14, var6, var17, var15, var7);
/* 6118:7096 */       renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6119:7097 */       var5 = true;
/* 6120:     */     }
/* 6121:7100 */     if (var9)
/* 6122:     */     {
/* 6123:7102 */       setRenderBounds(var6, var14, var18, var7, var15, var19);
/* 6124:7103 */       renderStandardBlock(p_147735_1_, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6125:7104 */       var5 = true;
/* 6126:     */     }
/* 6127:7107 */     p_147735_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147735_2_, p_147735_3_, p_147735_4_);
/* 6128:7109 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147735_2_, p_147735_3_, p_147735_4_))) {
/* 6129:7111 */       renderSnow(p_147735_2_, p_147735_3_, p_147735_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 6130:     */     }
/* 6131:7114 */     return var5;
/* 6132:     */   }
/* 6133:     */   
/* 6134:     */   public boolean renderBlockWall(BlockWall p_147807_1_, int p_147807_2_, int p_147807_3_, int p_147807_4_)
/* 6135:     */   {
/* 6136:7119 */     boolean var5 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ - 1, p_147807_3_, p_147807_4_);
/* 6137:7120 */     boolean var6 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_ + 1, p_147807_3_, p_147807_4_);
/* 6138:7121 */     boolean var7 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ - 1);
/* 6139:7122 */     boolean var8 = p_147807_1_.func_150091_e(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_ + 1);
/* 6140:7123 */     boolean var9 = (var7) && (var8) && (!var5) && (!var6);
/* 6141:7124 */     boolean var10 = (!var7) && (!var8) && (var5) && (var6);
/* 6142:7125 */     boolean var11 = this.blockAccess.isAirBlock(p_147807_2_, p_147807_3_ + 1, p_147807_4_);
/* 6143:7127 */     if (((var9) || (var10)) && (var11))
/* 6144:     */     {
/* 6145:7129 */       if (var9)
/* 6146:     */       {
/* 6147:7131 */         setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
/* 6148:7132 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6149:     */       }
/* 6150:     */       else
/* 6151:     */       {
/* 6152:7136 */         setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
/* 6153:7137 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6154:     */       }
/* 6155:     */     }
/* 6156:     */     else
/* 6157:     */     {
/* 6158:7142 */       setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
/* 6159:7143 */       renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6160:7145 */       if (var5)
/* 6161:     */       {
/* 6162:7147 */         setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
/* 6163:7148 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6164:     */       }
/* 6165:7151 */       if (var6)
/* 6166:     */       {
/* 6167:7153 */         setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
/* 6168:7154 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6169:     */       }
/* 6170:7157 */       if (var7)
/* 6171:     */       {
/* 6172:7159 */         setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
/* 6173:7160 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6174:     */       }
/* 6175:7163 */       if (var8)
/* 6176:     */       {
/* 6177:7165 */         setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
/* 6178:7166 */         renderStandardBlock(p_147807_1_, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6179:     */       }
/* 6180:     */     }
/* 6181:7170 */     p_147807_1_.setBlockBoundsBasedOnState(this.blockAccess, p_147807_2_, p_147807_3_, p_147807_4_);
/* 6182:7172 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147807_2_, p_147807_3_, p_147807_4_))) {
/* 6183:7174 */       renderSnow(p_147807_2_, p_147807_3_, p_147807_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 6184:     */     }
/* 6185:7177 */     return true;
/* 6186:     */   }
/* 6187:     */   
/* 6188:     */   public boolean renderBlockDragonEgg(BlockDragonEgg p_147802_1_, int p_147802_2_, int p_147802_3_, int p_147802_4_)
/* 6189:     */   {
/* 6190:7182 */     boolean var5 = false;
/* 6191:7183 */     int var6 = 0;
/* 6192:7185 */     for (int var7 = 0; var7 < 8; var7++)
/* 6193:     */     {
/* 6194:7187 */       byte var8 = 0;
/* 6195:7188 */       byte var9 = 1;
/* 6196:7190 */       if (var7 == 0) {
/* 6197:7192 */         var8 = 2;
/* 6198:     */       }
/* 6199:7195 */       if (var7 == 1) {
/* 6200:7197 */         var8 = 3;
/* 6201:     */       }
/* 6202:7200 */       if (var7 == 2) {
/* 6203:7202 */         var8 = 4;
/* 6204:     */       }
/* 6205:7205 */       if (var7 == 3)
/* 6206:     */       {
/* 6207:7207 */         var8 = 5;
/* 6208:7208 */         var9 = 2;
/* 6209:     */       }
/* 6210:7211 */       if (var7 == 4)
/* 6211:     */       {
/* 6212:7213 */         var8 = 6;
/* 6213:7214 */         var9 = 3;
/* 6214:     */       }
/* 6215:7217 */       if (var7 == 5)
/* 6216:     */       {
/* 6217:7219 */         var8 = 7;
/* 6218:7220 */         var9 = 5;
/* 6219:     */       }
/* 6220:7223 */       if (var7 == 6)
/* 6221:     */       {
/* 6222:7225 */         var8 = 6;
/* 6223:7226 */         var9 = 2;
/* 6224:     */       }
/* 6225:7229 */       if (var7 == 7) {
/* 6226:7231 */         var8 = 3;
/* 6227:     */       }
/* 6228:7234 */       float var10 = var8 / 16.0F;
/* 6229:7235 */       float var11 = 1.0F - var6 / 16.0F;
/* 6230:7236 */       float var12 = 1.0F - (var6 + var9) / 16.0F;
/* 6231:7237 */       var6 += var9;
/* 6232:7238 */       setRenderBounds(0.5F - var10, var12, 0.5F - var10, 0.5F + var10, var11, 0.5F + var10);
/* 6233:7239 */       renderStandardBlock(p_147802_1_, p_147802_2_, p_147802_3_, p_147802_4_);
/* 6234:     */     }
/* 6235:7242 */     var5 = true;
/* 6236:7243 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 6237:7244 */     return var5;
/* 6238:     */   }
/* 6239:     */   
/* 6240:     */   public boolean renderBlockFenceGate(BlockFenceGate p_147776_1_, int p_147776_2_, int p_147776_3_, int p_147776_4_)
/* 6241:     */   {
/* 6242:7249 */     boolean var5 = true;
/* 6243:7250 */     int var6 = this.blockAccess.getBlockMetadata(p_147776_2_, p_147776_3_, p_147776_4_);
/* 6244:7251 */     boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
/* 6245:7252 */     int var8 = BlockDirectional.func_149895_l(var6);
/* 6246:7253 */     float var9 = 0.375F;
/* 6247:7254 */     float var10 = 0.5625F;
/* 6248:7255 */     float var11 = 0.75F;
/* 6249:7256 */     float var12 = 0.9375F;
/* 6250:7257 */     float var13 = 0.3125F;
/* 6251:7258 */     float var14 = 1.0F;
/* 6252:7260 */     if (((var8 != 2) && (var8 != 0)) || (((this.blockAccess.getBlock(p_147776_2_ - 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall) && (this.blockAccess.getBlock(p_147776_2_ + 1, p_147776_3_, p_147776_4_) == Blocks.cobblestone_wall)) || (((var8 == 3) || (var8 == 1)) && (this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ - 1) == Blocks.cobblestone_wall) && (this.blockAccess.getBlock(p_147776_2_, p_147776_3_, p_147776_4_ + 1) == Blocks.cobblestone_wall))))
/* 6253:     */     {
/* 6254:7262 */       var9 -= 0.1875F;
/* 6255:7263 */       var10 -= 0.1875F;
/* 6256:7264 */       var11 -= 0.1875F;
/* 6257:7265 */       var12 -= 0.1875F;
/* 6258:7266 */       var13 -= 0.1875F;
/* 6259:7267 */       var14 -= 0.1875F;
/* 6260:     */     }
/* 6261:7270 */     this.renderAllFaces = true;
/* 6262:     */     float var15;
/* 6263:     */     float var16;
/* 6264:     */     float var17;
/* 6265:     */     float var18;
/* 6266:7276 */     if ((var8 != 3) && (var8 != 1))
/* 6267:     */     {
/* 6268:7278 */       float var15 = 0.0F;
/* 6269:7279 */       float var16 = 0.125F;
/* 6270:7280 */       float var17 = 0.4375F;
/* 6271:7281 */       float var18 = 0.5625F;
/* 6272:7282 */       setRenderBounds(var15, var13, var17, var16, var14, var18);
/* 6273:7283 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6274:7284 */       var15 = 0.875F;
/* 6275:7285 */       var16 = 1.0F;
/* 6276:7286 */       setRenderBounds(var15, var13, var17, var16, var14, var18);
/* 6277:7287 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6278:     */     }
/* 6279:     */     else
/* 6280:     */     {
/* 6281:7291 */       this.uvRotateTop = 1;
/* 6282:7292 */       var15 = 0.4375F;
/* 6283:7293 */       var16 = 0.5625F;
/* 6284:7294 */       var17 = 0.0F;
/* 6285:7295 */       var18 = 0.125F;
/* 6286:7296 */       setRenderBounds(var15, var13, var17, var16, var14, var18);
/* 6287:7297 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6288:7298 */       var17 = 0.875F;
/* 6289:7299 */       var18 = 1.0F;
/* 6290:7300 */       setRenderBounds(var15, var13, var17, var16, var14, var18);
/* 6291:7301 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6292:7302 */       this.uvRotateTop = 0;
/* 6293:     */     }
/* 6294:7305 */     if (var7)
/* 6295:     */     {
/* 6296:7307 */       if ((var8 == 2) || (var8 == 0)) {
/* 6297:7309 */         this.uvRotateTop = 1;
/* 6298:     */       }
/* 6299:7316 */       if (var8 == 3)
/* 6300:     */       {
/* 6301:7318 */         var15 = 0.0F;
/* 6302:7319 */         var16 = 0.125F;
/* 6303:7320 */         var17 = 0.875F;
/* 6304:7321 */         var18 = 1.0F;
/* 6305:7322 */         float var19 = 0.5625F;
/* 6306:7323 */         float var20 = 0.8125F;
/* 6307:7324 */         float var21 = 0.9375F;
/* 6308:7325 */         setRenderBounds(0.8125D, var9, 0.0D, 0.9375D, var12, 0.125D);
/* 6309:7326 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6310:7327 */         setRenderBounds(0.8125D, var9, 0.875D, 0.9375D, var12, 1.0D);
/* 6311:7328 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6312:7329 */         setRenderBounds(0.5625D, var9, 0.0D, 0.8125D, var10, 0.125D);
/* 6313:7330 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6314:7331 */         setRenderBounds(0.5625D, var9, 0.875D, 0.8125D, var10, 1.0D);
/* 6315:7332 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6316:7333 */         setRenderBounds(0.5625D, var11, 0.0D, 0.8125D, var12, 0.125D);
/* 6317:7334 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6318:7335 */         setRenderBounds(0.5625D, var11, 0.875D, 0.8125D, var12, 1.0D);
/* 6319:7336 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6320:     */       }
/* 6321:7338 */       else if (var8 == 1)
/* 6322:     */       {
/* 6323:7340 */         var15 = 0.0F;
/* 6324:7341 */         var16 = 0.125F;
/* 6325:7342 */         var17 = 0.875F;
/* 6326:7343 */         var18 = 1.0F;
/* 6327:7344 */         float var19 = 0.0625F;
/* 6328:7345 */         float var20 = 0.1875F;
/* 6329:7346 */         float var21 = 0.4375F;
/* 6330:7347 */         setRenderBounds(0.0625D, var9, 0.0D, 0.1875D, var12, 0.125D);
/* 6331:7348 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6332:7349 */         setRenderBounds(0.0625D, var9, 0.875D, 0.1875D, var12, 1.0D);
/* 6333:7350 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6334:7351 */         setRenderBounds(0.1875D, var9, 0.0D, 0.4375D, var10, 0.125D);
/* 6335:7352 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6336:7353 */         setRenderBounds(0.1875D, var9, 0.875D, 0.4375D, var10, 1.0D);
/* 6337:7354 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6338:7355 */         setRenderBounds(0.1875D, var11, 0.0D, 0.4375D, var12, 0.125D);
/* 6339:7356 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6340:7357 */         setRenderBounds(0.1875D, var11, 0.875D, 0.4375D, var12, 1.0D);
/* 6341:7358 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6342:     */       }
/* 6343:7360 */       else if (var8 == 0)
/* 6344:     */       {
/* 6345:7362 */         var15 = 0.0F;
/* 6346:7363 */         var16 = 0.125F;
/* 6347:7364 */         var17 = 0.875F;
/* 6348:7365 */         var18 = 1.0F;
/* 6349:7366 */         float var19 = 0.5625F;
/* 6350:7367 */         float var20 = 0.8125F;
/* 6351:7368 */         float var21 = 0.9375F;
/* 6352:7369 */         setRenderBounds(0.0D, var9, 0.8125D, 0.125D, var12, 0.9375D);
/* 6353:7370 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6354:7371 */         setRenderBounds(0.875D, var9, 0.8125D, 1.0D, var12, 0.9375D);
/* 6355:7372 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6356:7373 */         setRenderBounds(0.0D, var9, 0.5625D, 0.125D, var10, 0.8125D);
/* 6357:7374 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6358:7375 */         setRenderBounds(0.875D, var9, 0.5625D, 1.0D, var10, 0.8125D);
/* 6359:7376 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6360:7377 */         setRenderBounds(0.0D, var11, 0.5625D, 0.125D, var12, 0.8125D);
/* 6361:7378 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6362:7379 */         setRenderBounds(0.875D, var11, 0.5625D, 1.0D, var12, 0.8125D);
/* 6363:7380 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6364:     */       }
/* 6365:7382 */       else if (var8 == 2)
/* 6366:     */       {
/* 6367:7384 */         var15 = 0.0F;
/* 6368:7385 */         var16 = 0.125F;
/* 6369:7386 */         var17 = 0.875F;
/* 6370:7387 */         var18 = 1.0F;
/* 6371:7388 */         float var19 = 0.0625F;
/* 6372:7389 */         float var20 = 0.1875F;
/* 6373:7390 */         float var21 = 0.4375F;
/* 6374:7391 */         setRenderBounds(0.0D, var9, 0.0625D, 0.125D, var12, 0.1875D);
/* 6375:7392 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6376:7393 */         setRenderBounds(0.875D, var9, 0.0625D, 1.0D, var12, 0.1875D);
/* 6377:7394 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6378:7395 */         setRenderBounds(0.0D, var9, 0.1875D, 0.125D, var10, 0.4375D);
/* 6379:7396 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6380:7397 */         setRenderBounds(0.875D, var9, 0.1875D, 1.0D, var10, 0.4375D);
/* 6381:7398 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6382:7399 */         setRenderBounds(0.0D, var11, 0.1875D, 0.125D, var12, 0.4375D);
/* 6383:7400 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6384:7401 */         setRenderBounds(0.875D, var11, 0.1875D, 1.0D, var12, 0.4375D);
/* 6385:7402 */         renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6386:     */       }
/* 6387:     */     }
/* 6388:7405 */     else if ((var8 != 3) && (var8 != 1))
/* 6389:     */     {
/* 6390:7407 */       var15 = 0.375F;
/* 6391:7408 */       var16 = 0.5F;
/* 6392:7409 */       var17 = 0.4375F;
/* 6393:7410 */       var18 = 0.5625F;
/* 6394:7411 */       setRenderBounds(var15, var9, var17, var16, var12, var18);
/* 6395:7412 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6396:7413 */       var15 = 0.5F;
/* 6397:7414 */       var16 = 0.625F;
/* 6398:7415 */       setRenderBounds(var15, var9, var17, var16, var12, var18);
/* 6399:7416 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6400:7417 */       var15 = 0.625F;
/* 6401:7418 */       var16 = 0.875F;
/* 6402:7419 */       setRenderBounds(var15, var9, var17, var16, var10, var18);
/* 6403:7420 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6404:7421 */       setRenderBounds(var15, var11, var17, var16, var12, var18);
/* 6405:7422 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6406:7423 */       var15 = 0.125F;
/* 6407:7424 */       var16 = 0.375F;
/* 6408:7425 */       setRenderBounds(var15, var9, var17, var16, var10, var18);
/* 6409:7426 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6410:7427 */       setRenderBounds(var15, var11, var17, var16, var12, var18);
/* 6411:7428 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6412:     */     }
/* 6413:     */     else
/* 6414:     */     {
/* 6415:7432 */       this.uvRotateTop = 1;
/* 6416:7433 */       var15 = 0.4375F;
/* 6417:7434 */       var16 = 0.5625F;
/* 6418:7435 */       var17 = 0.375F;
/* 6419:7436 */       var18 = 0.5F;
/* 6420:7437 */       setRenderBounds(var15, var9, var17, var16, var12, var18);
/* 6421:7438 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6422:7439 */       var17 = 0.5F;
/* 6423:7440 */       var18 = 0.625F;
/* 6424:7441 */       setRenderBounds(var15, var9, var17, var16, var12, var18);
/* 6425:7442 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6426:7443 */       var17 = 0.625F;
/* 6427:7444 */       var18 = 0.875F;
/* 6428:7445 */       setRenderBounds(var15, var9, var17, var16, var10, var18);
/* 6429:7446 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6430:7447 */       setRenderBounds(var15, var11, var17, var16, var12, var18);
/* 6431:7448 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6432:7449 */       var17 = 0.125F;
/* 6433:7450 */       var18 = 0.375F;
/* 6434:7451 */       setRenderBounds(var15, var9, var17, var16, var10, var18);
/* 6435:7452 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6436:7453 */       setRenderBounds(var15, var11, var17, var16, var12, var18);
/* 6437:7454 */       renderStandardBlock(p_147776_1_, p_147776_2_, p_147776_3_, p_147776_4_);
/* 6438:     */     }
/* 6439:7457 */     if ((Config.isBetterSnow()) && (hasSnowNeighbours(p_147776_2_, p_147776_3_, p_147776_4_))) {
/* 6440:7459 */       renderSnow(p_147776_2_, p_147776_3_, p_147776_4_, Blocks.snow_layer.getBlockBoundsMaxY());
/* 6441:     */     }
/* 6442:7462 */     this.renderAllFaces = false;
/* 6443:7463 */     this.uvRotateTop = 0;
/* 6444:7464 */     setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 6445:7465 */     return var5;
/* 6446:     */   }
/* 6447:     */   
/* 6448:     */   public boolean renderBlockHopper(BlockHopper p_147803_1_, int p_147803_2_, int p_147803_3_, int p_147803_4_)
/* 6449:     */   {
/* 6450:7470 */     Tessellator var5 = Tessellator.instance;
/* 6451:7471 */     var5.setBrightness(p_147803_1_.getBlockBrightness(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_));
/* 6452:7472 */     int var6 = p_147803_1_.colorMultiplier(this.blockAccess, p_147803_2_, p_147803_3_, p_147803_4_);
/* 6453:7473 */     float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/* 6454:7474 */     float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/* 6455:7475 */     float var9 = (var6 & 0xFF) / 255.0F;
/* 6456:7477 */     if (EntityRenderer.anaglyphEnable)
/* 6457:     */     {
/* 6458:7479 */       float var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
/* 6459:7480 */       float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
/* 6460:7481 */       float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
/* 6461:7482 */       var7 = var10;
/* 6462:7483 */       var8 = var11;
/* 6463:7484 */       var9 = var12;
/* 6464:     */     }
/* 6465:7487 */     var5.setColorOpaque_F(var7, var8, var9);
/* 6466:7488 */     return renderBlockHopperMetadata(p_147803_1_, p_147803_2_, p_147803_3_, p_147803_4_, this.blockAccess.getBlockMetadata(p_147803_2_, p_147803_3_, p_147803_4_), false);
/* 6467:     */   }
/* 6468:     */   
/* 6469:     */   public boolean renderBlockHopperMetadata(BlockHopper p_147799_1_, int p_147799_2_, int p_147799_3_, int p_147799_4_, int p_147799_5_, boolean p_147799_6_)
/* 6470:     */   {
/* 6471:7493 */     Tessellator var7 = Tessellator.instance;
/* 6472:7494 */     int var8 = BlockHopper.func_149918_b(p_147799_5_);
/* 6473:7495 */     double var9 = 0.625D;
/* 6474:7496 */     setRenderBounds(0.0D, var9, 0.0D, 1.0D, 1.0D, 1.0D);
/* 6475:7498 */     if (p_147799_6_)
/* 6476:     */     {
/* 6477:7500 */       var7.startDrawingQuads();
/* 6478:7501 */       var7.setNormal(0.0F, -1.0F, 0.0F);
/* 6479:7502 */       renderFaceYNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 0, p_147799_5_));
/* 6480:7503 */       var7.draw();
/* 6481:7504 */       var7.startDrawingQuads();
/* 6482:7505 */       var7.setNormal(0.0F, 1.0F, 0.0F);
/* 6483:7506 */       renderFaceYPos(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 1, p_147799_5_));
/* 6484:7507 */       var7.draw();
/* 6485:7508 */       var7.startDrawingQuads();
/* 6486:7509 */       var7.setNormal(0.0F, 0.0F, -1.0F);
/* 6487:7510 */       renderFaceZNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 2, p_147799_5_));
/* 6488:7511 */       var7.draw();
/* 6489:7512 */       var7.startDrawingQuads();
/* 6490:7513 */       var7.setNormal(0.0F, 0.0F, 1.0F);
/* 6491:7514 */       renderFaceZPos(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 3, p_147799_5_));
/* 6492:7515 */       var7.draw();
/* 6493:7516 */       var7.startDrawingQuads();
/* 6494:7517 */       var7.setNormal(-1.0F, 0.0F, 0.0F);
/* 6495:7518 */       renderFaceXNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 4, p_147799_5_));
/* 6496:7519 */       var7.draw();
/* 6497:7520 */       var7.startDrawingQuads();
/* 6498:7521 */       var7.setNormal(1.0F, 0.0F, 0.0F);
/* 6499:7522 */       renderFaceXPos(p_147799_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147799_1_, 5, p_147799_5_));
/* 6500:7523 */       var7.draw();
/* 6501:     */     }
/* 6502:     */     else
/* 6503:     */     {
/* 6504:7527 */       renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6505:     */     }
/* 6506:7532 */     if (!p_147799_6_)
/* 6507:     */     {
/* 6508:7534 */       var7.setBrightness(p_147799_1_.getBlockBrightness(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_));
/* 6509:7535 */       int var24 = p_147799_1_.colorMultiplier(this.blockAccess, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6510:7536 */       float var25 = (var24 >> 16 & 0xFF) / 255.0F;
/* 6511:7537 */       float var13 = (var24 >> 8 & 0xFF) / 255.0F;
/* 6512:7538 */       float var26 = (var24 & 0xFF) / 255.0F;
/* 6513:7540 */       if (EntityRenderer.anaglyphEnable)
/* 6514:     */       {
/* 6515:7542 */         float var15 = (var25 * 30.0F + var13 * 59.0F + var26 * 11.0F) / 100.0F;
/* 6516:7543 */         float var27 = (var25 * 30.0F + var13 * 70.0F) / 100.0F;
/* 6517:7544 */         float var17 = (var25 * 30.0F + var26 * 70.0F) / 100.0F;
/* 6518:7545 */         var25 = var15;
/* 6519:7546 */         var13 = var27;
/* 6520:7547 */         var26 = var17;
/* 6521:     */       }
/* 6522:7550 */       var7.setColorOpaque_F(var25, var13, var26);
/* 6523:     */     }
/* 6524:7553 */     IIcon var241 = BlockHopper.func_149916_e("hopper_outside");
/* 6525:7554 */     IIcon var251 = BlockHopper.func_149916_e("hopper_inside");
/* 6526:7555 */     float var13 = 0.125F;
/* 6527:7557 */     if (p_147799_6_)
/* 6528:     */     {
/* 6529:7559 */       var7.startDrawingQuads();
/* 6530:7560 */       var7.setNormal(1.0F, 0.0F, 0.0F);
/* 6531:7561 */       renderFaceXPos(p_147799_1_, -1.0F + var13, 0.0D, 0.0D, var241);
/* 6532:7562 */       var7.draw();
/* 6533:7563 */       var7.startDrawingQuads();
/* 6534:7564 */       var7.setNormal(-1.0F, 0.0F, 0.0F);
/* 6535:7565 */       renderFaceXNeg(p_147799_1_, 1.0F - var13, 0.0D, 0.0D, var241);
/* 6536:7566 */       var7.draw();
/* 6537:7567 */       var7.startDrawingQuads();
/* 6538:7568 */       var7.setNormal(0.0F, 0.0F, 1.0F);
/* 6539:7569 */       renderFaceZPos(p_147799_1_, 0.0D, 0.0D, -1.0F + var13, var241);
/* 6540:7570 */       var7.draw();
/* 6541:7571 */       var7.startDrawingQuads();
/* 6542:7572 */       var7.setNormal(0.0F, 0.0F, -1.0F);
/* 6543:7573 */       renderFaceZNeg(p_147799_1_, 0.0D, 0.0D, 1.0F - var13, var241);
/* 6544:7574 */       var7.draw();
/* 6545:7575 */       var7.startDrawingQuads();
/* 6546:7576 */       var7.setNormal(0.0F, 1.0F, 0.0F);
/* 6547:7577 */       renderFaceYPos(p_147799_1_, 0.0D, -1.0D + var9, 0.0D, var251);
/* 6548:7578 */       var7.draw();
/* 6549:     */     }
/* 6550:     */     else
/* 6551:     */     {
/* 6552:7582 */       renderFaceXPos(p_147799_1_, p_147799_2_ - 1.0F + var13, p_147799_3_, p_147799_4_, var241);
/* 6553:7583 */       renderFaceXNeg(p_147799_1_, p_147799_2_ + 1.0F - var13, p_147799_3_, p_147799_4_, var241);
/* 6554:7584 */       renderFaceZPos(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_ - 1.0F + var13, var241);
/* 6555:7585 */       renderFaceZNeg(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_ + 1.0F - var13, var241);
/* 6556:7586 */       renderFaceYPos(p_147799_1_, p_147799_2_, p_147799_3_ - 1.0F + var9, p_147799_4_, var251);
/* 6557:     */     }
/* 6558:7589 */     setOverrideBlockTexture(var241);
/* 6559:7590 */     double var261 = 0.25D;
/* 6560:7591 */     double var271 = 0.25D;
/* 6561:7592 */     setRenderBounds(var261, var271, var261, 1.0D - var261, var9 - 0.002D, 1.0D - var261);
/* 6562:7594 */     if (p_147799_6_)
/* 6563:     */     {
/* 6564:7596 */       var7.startDrawingQuads();
/* 6565:7597 */       var7.setNormal(1.0F, 0.0F, 0.0F);
/* 6566:7598 */       renderFaceXPos(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6567:7599 */       var7.draw();
/* 6568:7600 */       var7.startDrawingQuads();
/* 6569:7601 */       var7.setNormal(-1.0F, 0.0F, 0.0F);
/* 6570:7602 */       renderFaceXNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6571:7603 */       var7.draw();
/* 6572:7604 */       var7.startDrawingQuads();
/* 6573:7605 */       var7.setNormal(0.0F, 0.0F, 1.0F);
/* 6574:7606 */       renderFaceZPos(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6575:7607 */       var7.draw();
/* 6576:7608 */       var7.startDrawingQuads();
/* 6577:7609 */       var7.setNormal(0.0F, 0.0F, -1.0F);
/* 6578:7610 */       renderFaceZNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6579:7611 */       var7.draw();
/* 6580:7612 */       var7.startDrawingQuads();
/* 6581:7613 */       var7.setNormal(0.0F, 1.0F, 0.0F);
/* 6582:7614 */       renderFaceYPos(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6583:7615 */       var7.draw();
/* 6584:7616 */       var7.startDrawingQuads();
/* 6585:7617 */       var7.setNormal(0.0F, -1.0F, 0.0F);
/* 6586:7618 */       renderFaceYNeg(p_147799_1_, 0.0D, 0.0D, 0.0D, var241);
/* 6587:7619 */       var7.draw();
/* 6588:     */     }
/* 6589:     */     else
/* 6590:     */     {
/* 6591:7623 */       renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6592:     */     }
/* 6593:7626 */     if (!p_147799_6_)
/* 6594:     */     {
/* 6595:7628 */       double var20 = 0.375D;
/* 6596:7629 */       double var22 = 0.25D;
/* 6597:7630 */       setOverrideBlockTexture(var241);
/* 6598:7632 */       if (var8 == 0)
/* 6599:     */       {
/* 6600:7634 */         setRenderBounds(var20, 0.0D, var20, 1.0D - var20, 0.25D, 1.0D - var20);
/* 6601:7635 */         renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6602:     */       }
/* 6603:7638 */       if (var8 == 2)
/* 6604:     */       {
/* 6605:7640 */         setRenderBounds(var20, var271, 0.0D, 1.0D - var20, var271 + var22, var261);
/* 6606:7641 */         renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6607:     */       }
/* 6608:7644 */       if (var8 == 3)
/* 6609:     */       {
/* 6610:7646 */         setRenderBounds(var20, var271, 1.0D - var261, 1.0D - var20, var271 + var22, 1.0D);
/* 6611:7647 */         renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6612:     */       }
/* 6613:7650 */       if (var8 == 4)
/* 6614:     */       {
/* 6615:7652 */         setRenderBounds(0.0D, var271, var20, var261, var271 + var22, 1.0D - var20);
/* 6616:7653 */         renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6617:     */       }
/* 6618:7656 */       if (var8 == 5)
/* 6619:     */       {
/* 6620:7658 */         setRenderBounds(1.0D - var261, var271, var20, 1.0D, var271 + var22, 1.0D - var20);
/* 6621:7659 */         renderStandardBlock(p_147799_1_, p_147799_2_, p_147799_3_, p_147799_4_);
/* 6622:     */       }
/* 6623:     */     }
/* 6624:7663 */     clearOverrideBlockTexture();
/* 6625:7664 */     return true;
/* 6626:     */   }
/* 6627:     */   
/* 6628:     */   public boolean renderBlockStairs(BlockStairs p_147722_1_, int p_147722_2_, int p_147722_3_, int p_147722_4_)
/* 6629:     */   {
/* 6630:7669 */     p_147722_1_.func_150147_e(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
/* 6631:7670 */     setRenderBoundsFromBlock(p_147722_1_);
/* 6632:7671 */     renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
/* 6633:7672 */     boolean var5 = p_147722_1_.func_150145_f(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_);
/* 6634:7673 */     setRenderBoundsFromBlock(p_147722_1_);
/* 6635:7674 */     renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
/* 6636:7676 */     if ((var5) && (p_147722_1_.func_150144_g(this.blockAccess, p_147722_2_, p_147722_3_, p_147722_4_)))
/* 6637:     */     {
/* 6638:7678 */       setRenderBoundsFromBlock(p_147722_1_);
/* 6639:7679 */       renderStandardBlock(p_147722_1_, p_147722_2_, p_147722_3_, p_147722_4_);
/* 6640:     */     }
/* 6641:7682 */     return true;
/* 6642:     */   }
/* 6643:     */   
/* 6644:     */   public boolean renderBlockDoor(Block p_147760_1_, int p_147760_2_, int p_147760_3_, int p_147760_4_)
/* 6645:     */   {
/* 6646:7687 */     Tessellator var5 = Tessellator.instance;
/* 6647:7688 */     int var6 = this.blockAccess.getBlockMetadata(p_147760_2_, p_147760_3_, p_147760_4_);
/* 6648:7690 */     if ((var6 & 0x8) != 0)
/* 6649:     */     {
/* 6650:7692 */       if (this.blockAccess.getBlock(p_147760_2_, p_147760_3_ - 1, p_147760_4_) != p_147760_1_) {
/* 6651:7694 */         return false;
/* 6652:     */       }
/* 6653:     */     }
/* 6654:7697 */     else if (this.blockAccess.getBlock(p_147760_2_, p_147760_3_ + 1, p_147760_4_) != p_147760_1_) {
/* 6655:7699 */       return false;
/* 6656:     */     }
/* 6657:7702 */     boolean var7 = false;
/* 6658:7703 */     float var8 = 0.5F;
/* 6659:7704 */     float var9 = 1.0F;
/* 6660:7705 */     float var10 = 0.8F;
/* 6661:7706 */     float var11 = 0.6F;
/* 6662:7707 */     int var12 = p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_);
/* 6663:7708 */     var5.setBrightness(this.renderMinY > 0.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ - 1, p_147760_4_));
/* 6664:7709 */     var5.setColorOpaque_F(var8, var8, var8);
/* 6665:7710 */     renderFaceYNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 0));
/* 6666:7711 */     var7 = true;
/* 6667:7712 */     var5.setBrightness(this.renderMaxY < 1.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_ + 1, p_147760_4_));
/* 6668:7713 */     var5.setColorOpaque_F(var9, var9, var9);
/* 6669:7714 */     renderFaceYPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 1));
/* 6670:7715 */     var7 = true;
/* 6671:7716 */     var5.setBrightness(this.renderMinZ > 0.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ - 1));
/* 6672:7717 */     var5.setColorOpaque_F(var10, var10, var10);
/* 6673:7718 */     IIcon var13 = getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 2);
/* 6674:7719 */     renderFaceZNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
/* 6675:7720 */     var7 = true;
/* 6676:7721 */     this.flipTexture = false;
/* 6677:7722 */     var5.setBrightness(this.renderMaxZ < 1.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_ + 1));
/* 6678:7723 */     var5.setColorOpaque_F(var10, var10, var10);
/* 6679:7724 */     var13 = getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 3);
/* 6680:7725 */     renderFaceZPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
/* 6681:7726 */     var7 = true;
/* 6682:7727 */     this.flipTexture = false;
/* 6683:7728 */     var5.setBrightness(this.renderMinX > 0.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ - 1, p_147760_3_, p_147760_4_));
/* 6684:7729 */     var5.setColorOpaque_F(var11, var11, var11);
/* 6685:7730 */     var13 = getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 4);
/* 6686:7731 */     renderFaceXNeg(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
/* 6687:7732 */     var7 = true;
/* 6688:7733 */     this.flipTexture = false;
/* 6689:7734 */     var5.setBrightness(this.renderMaxX < 1.0D ? var12 : p_147760_1_.getBlockBrightness(this.blockAccess, p_147760_2_ + 1, p_147760_3_, p_147760_4_));
/* 6690:7735 */     var5.setColorOpaque_F(var11, var11, var11);
/* 6691:7736 */     var13 = getBlockIcon(p_147760_1_, this.blockAccess, p_147760_2_, p_147760_3_, p_147760_4_, 5);
/* 6692:7737 */     renderFaceXPos(p_147760_1_, p_147760_2_, p_147760_3_, p_147760_4_, var13);
/* 6693:7738 */     var7 = true;
/* 6694:7739 */     this.flipTexture = false;
/* 6695:7740 */     return var7;
/* 6696:     */   }
/* 6697:     */   
/* 6698:     */   public void renderFaceYNeg(Block p_147768_1_, double p_147768_2_, double p_147768_4_, double p_147768_6_, IIcon p_147768_8_)
/* 6699:     */   {
/* 6700:7745 */     Tessellator var9 = Tessellator.instance;
/* 6701:7747 */     if (hasOverrideBlockTexture()) {
/* 6702:7749 */       p_147768_8_ = this.overrideBlockTexture;
/* 6703:     */     }
/* 6704:7752 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateBottom == 0)) {
/* 6705:7754 */       p_147768_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147768_1_, (int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0, p_147768_8_);
/* 6706:     */     }
/* 6707:7757 */     boolean uvRotateSet = false;
/* 6708:7759 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateBottom == 0))
/* 6709:     */     {
/* 6710:7761 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147768_8_);
/* 6711:7763 */       if (var10 != null)
/* 6712:     */       {
/* 6713:7765 */         int rand = Config.getRandom((int)p_147768_2_, (int)p_147768_4_, (int)p_147768_6_, 0);
/* 6714:7767 */         if (var10.rotation > 1) {
/* 6715:7769 */           this.uvRotateBottom = (rand & 0x3);
/* 6716:     */         }
/* 6717:7772 */         if (var10.rotation == 2) {
/* 6718:7774 */           this.uvRotateBottom = (this.uvRotateBottom / 2 * 3);
/* 6719:     */         }
/* 6720:7777 */         if (var10.flip) {
/* 6721:7779 */           this.flipTexture = ((rand & 0x4) != 0);
/* 6722:     */         }
/* 6723:7782 */         uvRotateSet = true;
/* 6724:     */       }
/* 6725:     */     }
/* 6726:7786 */     double var101 = p_147768_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 6727:7787 */     double var12 = p_147768_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 6728:7788 */     double var14 = p_147768_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 6729:7789 */     double var16 = p_147768_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 6730:7791 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/* 6731:     */     {
/* 6732:7793 */       var101 = p_147768_8_.getMinU();
/* 6733:7794 */       var12 = p_147768_8_.getMaxU();
/* 6734:     */     }
/* 6735:7797 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/* 6736:     */     {
/* 6737:7799 */       var14 = p_147768_8_.getMinV();
/* 6738:7800 */       var16 = p_147768_8_.getMaxV();
/* 6739:     */     }
/* 6740:7805 */     if (this.flipTexture)
/* 6741:     */     {
/* 6742:7807 */       double var18 = var101;
/* 6743:7808 */       var101 = var12;
/* 6744:7809 */       var12 = var18;
/* 6745:     */     }
/* 6746:7812 */     double var18 = var12;
/* 6747:7813 */     double var20 = var101;
/* 6748:7814 */     double var22 = var14;
/* 6749:7815 */     double var24 = var16;
/* 6750:7818 */     if (this.uvRotateBottom == 2)
/* 6751:     */     {
/* 6752:7820 */       var101 = p_147768_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 6753:7821 */       var14 = p_147768_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 6754:7822 */       var12 = p_147768_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 6755:7823 */       var16 = p_147768_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 6756:7825 */       if (this.flipTexture)
/* 6757:     */       {
/* 6758:7827 */         double var26 = var101;
/* 6759:7828 */         var101 = var12;
/* 6760:7829 */         var12 = var26;
/* 6761:     */       }
/* 6762:7832 */       var22 = var14;
/* 6763:7833 */       var24 = var16;
/* 6764:7834 */       var18 = var101;
/* 6765:7835 */       var20 = var12;
/* 6766:7836 */       var14 = var16;
/* 6767:7837 */       var16 = var22;
/* 6768:     */     }
/* 6769:7839 */     else if (this.uvRotateBottom == 1)
/* 6770:     */     {
/* 6771:7841 */       var101 = p_147768_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 6772:7842 */       var14 = p_147768_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 6773:7843 */       var12 = p_147768_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 6774:7844 */       var16 = p_147768_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 6775:7846 */       if (this.flipTexture)
/* 6776:     */       {
/* 6777:7848 */         double var26 = var101;
/* 6778:7849 */         var101 = var12;
/* 6779:7850 */         var12 = var26;
/* 6780:     */       }
/* 6781:7853 */       var18 = var12;
/* 6782:7854 */       var20 = var101;
/* 6783:7855 */       var101 = var12;
/* 6784:7856 */       var12 = var20;
/* 6785:7857 */       var22 = var16;
/* 6786:7858 */       var24 = var14;
/* 6787:     */     }
/* 6788:7860 */     else if (this.uvRotateBottom == 3)
/* 6789:     */     {
/* 6790:7862 */       var101 = p_147768_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 6791:7863 */       var12 = p_147768_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 6792:7864 */       var14 = p_147768_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 6793:7865 */       var16 = p_147768_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 6794:7867 */       if (this.flipTexture)
/* 6795:     */       {
/* 6796:7869 */         double var26 = var101;
/* 6797:7870 */         var101 = var12;
/* 6798:7871 */         var12 = var26;
/* 6799:     */       }
/* 6800:7874 */       var18 = var12;
/* 6801:7875 */       var20 = var101;
/* 6802:7876 */       var22 = var14;
/* 6803:7877 */       var24 = var16;
/* 6804:     */     }
/* 6805:7880 */     if (uvRotateSet)
/* 6806:     */     {
/* 6807:7882 */       this.uvRotateBottom = 0;
/* 6808:7883 */       this.flipTexture = false;
/* 6809:     */     }
/* 6810:7886 */     double var26 = p_147768_2_ + this.renderMinX;
/* 6811:7887 */     double var28 = p_147768_2_ + this.renderMaxX;
/* 6812:7888 */     double var30 = p_147768_4_ + this.renderMinY;
/* 6813:7889 */     double var32 = p_147768_6_ + this.renderMinZ;
/* 6814:7890 */     double var34 = p_147768_6_ + this.renderMaxZ;
/* 6815:7892 */     if (this.renderFromInside)
/* 6816:     */     {
/* 6817:7894 */       var26 = p_147768_2_ + this.renderMaxX;
/* 6818:7895 */       var28 = p_147768_2_ + this.renderMinX;
/* 6819:     */     }
/* 6820:7898 */     if (this.enableAO)
/* 6821:     */     {
/* 6822:7900 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 6823:7901 */       var9.setBrightness(this.brightnessTopLeft);
/* 6824:7902 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 6825:7903 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 6826:7904 */       var9.setBrightness(this.brightnessBottomLeft);
/* 6827:7905 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 6828:7906 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 6829:7907 */       var9.setBrightness(this.brightnessBottomRight);
/* 6830:7908 */       var9.addVertexWithUV(var28, var30, var32, var18, var22);
/* 6831:7909 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 6832:7910 */       var9.setBrightness(this.brightnessTopRight);
/* 6833:7911 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 6834:     */     }
/* 6835:     */     else
/* 6836:     */     {
/* 6837:7915 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 6838:7916 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 6839:7917 */       var9.addVertexWithUV(var28, var30, var32, var18, var22);
/* 6840:7918 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 6841:     */     }
/* 6842:     */   }
/* 6843:     */   
/* 6844:     */   public void renderFaceYPos(Block p_147806_1_, double p_147806_2_, double p_147806_4_, double p_147806_6_, IIcon p_147806_8_)
/* 6845:     */   {
/* 6846:7924 */     Tessellator var9 = Tessellator.instance;
/* 6847:7926 */     if (hasOverrideBlockTexture()) {
/* 6848:7928 */       p_147806_8_ = this.overrideBlockTexture;
/* 6849:     */     }
/* 6850:7931 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateTop == 0)) {
/* 6851:7933 */       p_147806_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147806_1_, (int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1, p_147806_8_);
/* 6852:     */     }
/* 6853:7936 */     boolean uvRotateSet = false;
/* 6854:7938 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateTop == 0))
/* 6855:     */     {
/* 6856:7940 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147806_8_);
/* 6857:7942 */       if (var10 != null)
/* 6858:     */       {
/* 6859:7944 */         int rand = Config.getRandom((int)p_147806_2_, (int)p_147806_4_, (int)p_147806_6_, 1);
/* 6860:7946 */         if (var10.rotation > 1) {
/* 6861:7948 */           this.uvRotateTop = (rand & 0x3);
/* 6862:     */         }
/* 6863:7951 */         if (var10.rotation == 2) {
/* 6864:7953 */           this.uvRotateTop = (this.uvRotateTop / 2 * 3);
/* 6865:     */         }
/* 6866:7956 */         if (var10.flip) {
/* 6867:7958 */           this.flipTexture = ((rand & 0x4) != 0);
/* 6868:     */         }
/* 6869:7961 */         uvRotateSet = true;
/* 6870:     */       }
/* 6871:     */     }
/* 6872:7965 */     double var101 = p_147806_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 6873:7966 */     double var12 = p_147806_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 6874:7967 */     double var14 = p_147806_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 6875:7968 */     double var16 = p_147806_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 6876:7971 */     if (this.flipTexture)
/* 6877:     */     {
/* 6878:7973 */       double var18 = var101;
/* 6879:7974 */       var101 = var12;
/* 6880:7975 */       var12 = var18;
/* 6881:     */     }
/* 6882:7978 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/* 6883:     */     {
/* 6884:7980 */       var101 = p_147806_8_.getMinU();
/* 6885:7981 */       var12 = p_147806_8_.getMaxU();
/* 6886:     */     }
/* 6887:7984 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/* 6888:     */     {
/* 6889:7986 */       var14 = p_147806_8_.getMinV();
/* 6890:7987 */       var16 = p_147806_8_.getMaxV();
/* 6891:     */     }
/* 6892:7990 */     double var18 = var12;
/* 6893:7991 */     double var20 = var101;
/* 6894:7992 */     double var22 = var14;
/* 6895:7993 */     double var24 = var16;
/* 6896:7996 */     if (this.uvRotateTop == 1)
/* 6897:     */     {
/* 6898:7998 */       var101 = p_147806_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 6899:7999 */       var14 = p_147806_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 6900:8000 */       var12 = p_147806_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 6901:8001 */       var16 = p_147806_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 6902:8003 */       if (this.flipTexture)
/* 6903:     */       {
/* 6904:8005 */         double var26 = var101;
/* 6905:8006 */         var101 = var12;
/* 6906:8007 */         var12 = var26;
/* 6907:     */       }
/* 6908:8010 */       var22 = var14;
/* 6909:8011 */       var24 = var16;
/* 6910:8012 */       var18 = var101;
/* 6911:8013 */       var20 = var12;
/* 6912:8014 */       var14 = var16;
/* 6913:8015 */       var16 = var22;
/* 6914:     */     }
/* 6915:8017 */     else if (this.uvRotateTop == 2)
/* 6916:     */     {
/* 6917:8019 */       var101 = p_147806_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 6918:8020 */       var14 = p_147806_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 6919:8021 */       var12 = p_147806_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 6920:8022 */       var16 = p_147806_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 6921:8024 */       if (this.flipTexture)
/* 6922:     */       {
/* 6923:8026 */         double var26 = var101;
/* 6924:8027 */         var101 = var12;
/* 6925:8028 */         var12 = var26;
/* 6926:     */       }
/* 6927:8031 */       var18 = var12;
/* 6928:8032 */       var20 = var101;
/* 6929:8033 */       var101 = var12;
/* 6930:8034 */       var12 = var20;
/* 6931:8035 */       var22 = var16;
/* 6932:8036 */       var24 = var14;
/* 6933:     */     }
/* 6934:8038 */     else if (this.uvRotateTop == 3)
/* 6935:     */     {
/* 6936:8040 */       var101 = p_147806_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 6937:8041 */       var12 = p_147806_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 6938:8042 */       var14 = p_147806_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 6939:8043 */       var16 = p_147806_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 6940:8045 */       if (this.flipTexture)
/* 6941:     */       {
/* 6942:8047 */         double var26 = var101;
/* 6943:8048 */         var101 = var12;
/* 6944:8049 */         var12 = var26;
/* 6945:     */       }
/* 6946:8052 */       var18 = var12;
/* 6947:8053 */       var20 = var101;
/* 6948:8054 */       var22 = var14;
/* 6949:8055 */       var24 = var16;
/* 6950:     */     }
/* 6951:8058 */     if (uvRotateSet)
/* 6952:     */     {
/* 6953:8060 */       this.uvRotateTop = 0;
/* 6954:8061 */       this.flipTexture = false;
/* 6955:     */     }
/* 6956:8064 */     double var26 = p_147806_2_ + this.renderMinX;
/* 6957:8065 */     double var28 = p_147806_2_ + this.renderMaxX;
/* 6958:8066 */     double var30 = p_147806_4_ + this.renderMaxY;
/* 6959:8067 */     double var32 = p_147806_6_ + this.renderMinZ;
/* 6960:8068 */     double var34 = p_147806_6_ + this.renderMaxZ;
/* 6961:8070 */     if (this.renderFromInside)
/* 6962:     */     {
/* 6963:8072 */       var26 = p_147806_2_ + this.renderMaxX;
/* 6964:8073 */       var28 = p_147806_2_ + this.renderMinX;
/* 6965:     */     }
/* 6966:8076 */     if (this.enableAO)
/* 6967:     */     {
/* 6968:8078 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 6969:8079 */       var9.setBrightness(this.brightnessTopLeft);
/* 6970:8080 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 6971:8081 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 6972:8082 */       var9.setBrightness(this.brightnessBottomLeft);
/* 6973:8083 */       var9.addVertexWithUV(var28, var30, var32, var18, var22);
/* 6974:8084 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 6975:8085 */       var9.setBrightness(this.brightnessBottomRight);
/* 6976:8086 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 6977:8087 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 6978:8088 */       var9.setBrightness(this.brightnessTopRight);
/* 6979:8089 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 6980:     */     }
/* 6981:     */     else
/* 6982:     */     {
/* 6983:8093 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 6984:8094 */       var9.addVertexWithUV(var28, var30, var32, var18, var22);
/* 6985:8095 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 6986:8096 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 6987:     */     }
/* 6988:     */   }
/* 6989:     */   
/* 6990:     */   public void renderFaceZNeg(Block p_147761_1_, double p_147761_2_, double p_147761_4_, double p_147761_6_, IIcon p_147761_8_)
/* 6991:     */   {
/* 6992:8102 */     Tessellator var9 = Tessellator.instance;
/* 6993:8104 */     if (hasOverrideBlockTexture()) {
/* 6994:8106 */       p_147761_8_ = this.overrideBlockTexture;
/* 6995:     */     }
/* 6996:8109 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateEast == 0)) {
/* 6997:8111 */       p_147761_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147761_1_, (int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2, p_147761_8_);
/* 6998:     */     }
/* 6999:8114 */     boolean uvRotateSet = false;
/* 7000:8116 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateEast == 0))
/* 7001:     */     {
/* 7002:8118 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147761_8_);
/* 7003:8120 */       if (var10 != null)
/* 7004:     */       {
/* 7005:8122 */         int rand = Config.getRandom((int)p_147761_2_, (int)p_147761_4_, (int)p_147761_6_, 2);
/* 7006:8124 */         if (var10.rotation > 1) {
/* 7007:8126 */           this.uvRotateEast = (rand & 0x3);
/* 7008:     */         }
/* 7009:8129 */         if (var10.rotation == 2) {
/* 7010:8131 */           this.uvRotateEast = (this.uvRotateEast / 2 * 3);
/* 7011:     */         }
/* 7012:8134 */         if (var10.flip) {
/* 7013:8136 */           this.flipTexture = ((rand & 0x4) != 0);
/* 7014:     */         }
/* 7015:8139 */         uvRotateSet = true;
/* 7016:     */       }
/* 7017:     */     }
/* 7018:8143 */     double var101 = p_147761_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 7019:8144 */     double var12 = p_147761_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 7020:8145 */     double var14 = p_147761_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 7021:8146 */     double var16 = p_147761_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/* 7022:8149 */     if (this.flipTexture)
/* 7023:     */     {
/* 7024:8151 */       double var18 = var101;
/* 7025:8152 */       var101 = var12;
/* 7026:8153 */       var12 = var18;
/* 7027:     */     }
/* 7028:8156 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/* 7029:     */     {
/* 7030:8158 */       var101 = p_147761_8_.getMinU();
/* 7031:8159 */       var12 = p_147761_8_.getMaxU();
/* 7032:     */     }
/* 7033:8162 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/* 7034:     */     {
/* 7035:8164 */       var14 = p_147761_8_.getMinV();
/* 7036:8165 */       var16 = p_147761_8_.getMaxV();
/* 7037:     */     }
/* 7038:8168 */     double var18 = var12;
/* 7039:8169 */     double var20 = var101;
/* 7040:8170 */     double var22 = var14;
/* 7041:8171 */     double var24 = var16;
/* 7042:8174 */     if (this.uvRotateEast == 2)
/* 7043:     */     {
/* 7044:8176 */       var101 = p_147761_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 7045:8177 */       var14 = p_147761_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 7046:8178 */       var12 = p_147761_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 7047:8179 */       var16 = p_147761_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 7048:8181 */       if (this.flipTexture)
/* 7049:     */       {
/* 7050:8183 */         double var26 = var101;
/* 7051:8184 */         var101 = var12;
/* 7052:8185 */         var12 = var26;
/* 7053:     */       }
/* 7054:8188 */       var22 = var14;
/* 7055:8189 */       var24 = var16;
/* 7056:8190 */       var18 = var101;
/* 7057:8191 */       var20 = var12;
/* 7058:8192 */       var14 = var16;
/* 7059:8193 */       var16 = var22;
/* 7060:     */     }
/* 7061:8195 */     else if (this.uvRotateEast == 1)
/* 7062:     */     {
/* 7063:8197 */       var101 = p_147761_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 7064:8198 */       var14 = p_147761_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 7065:8199 */       var12 = p_147761_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 7066:8200 */       var16 = p_147761_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 7067:8202 */       if (this.flipTexture)
/* 7068:     */       {
/* 7069:8204 */         double var26 = var101;
/* 7070:8205 */         var101 = var12;
/* 7071:8206 */         var12 = var26;
/* 7072:     */       }
/* 7073:8209 */       var18 = var12;
/* 7074:8210 */       var20 = var101;
/* 7075:8211 */       var101 = var12;
/* 7076:8212 */       var12 = var20;
/* 7077:8213 */       var22 = var16;
/* 7078:8214 */       var24 = var14;
/* 7079:     */     }
/* 7080:8216 */     else if (this.uvRotateEast == 3)
/* 7081:     */     {
/* 7082:8218 */       var101 = p_147761_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 7083:8219 */       var12 = p_147761_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 7084:8220 */       var14 = p_147761_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 7085:8221 */       var16 = p_147761_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 7086:8223 */       if (this.flipTexture)
/* 7087:     */       {
/* 7088:8225 */         double var26 = var101;
/* 7089:8226 */         var101 = var12;
/* 7090:8227 */         var12 = var26;
/* 7091:     */       }
/* 7092:8230 */       var18 = var12;
/* 7093:8231 */       var20 = var101;
/* 7094:8232 */       var22 = var14;
/* 7095:8233 */       var24 = var16;
/* 7096:     */     }
/* 7097:8236 */     if (uvRotateSet)
/* 7098:     */     {
/* 7099:8238 */       this.uvRotateEast = 0;
/* 7100:8239 */       this.flipTexture = false;
/* 7101:     */     }
/* 7102:8242 */     double var26 = p_147761_2_ + this.renderMinX;
/* 7103:8243 */     double var28 = p_147761_2_ + this.renderMaxX;
/* 7104:8244 */     double var30 = p_147761_4_ + this.renderMinY;
/* 7105:8245 */     double var32 = p_147761_4_ + this.renderMaxY;
/* 7106:8246 */     double var34 = p_147761_6_ + this.renderMinZ;
/* 7107:8248 */     if (this.renderFromInside)
/* 7108:     */     {
/* 7109:8250 */       var26 = p_147761_2_ + this.renderMaxX;
/* 7110:8251 */       var28 = p_147761_2_ + this.renderMinX;
/* 7111:     */     }
/* 7112:8254 */     if (this.enableAO)
/* 7113:     */     {
/* 7114:8256 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 7115:8257 */       var9.setBrightness(this.brightnessTopLeft);
/* 7116:8258 */       var9.addVertexWithUV(var26, var32, var34, var18, var22);
/* 7117:8259 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 7118:8260 */       var9.setBrightness(this.brightnessBottomLeft);
/* 7119:8261 */       var9.addVertexWithUV(var28, var32, var34, var101, var14);
/* 7120:8262 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 7121:8263 */       var9.setBrightness(this.brightnessBottomRight);
/* 7122:8264 */       var9.addVertexWithUV(var28, var30, var34, var20, var24);
/* 7123:8265 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 7124:8266 */       var9.setBrightness(this.brightnessTopRight);
/* 7125:8267 */       var9.addVertexWithUV(var26, var30, var34, var12, var16);
/* 7126:     */     }
/* 7127:     */     else
/* 7128:     */     {
/* 7129:8271 */       var9.addVertexWithUV(var26, var32, var34, var18, var22);
/* 7130:8272 */       var9.addVertexWithUV(var28, var32, var34, var101, var14);
/* 7131:8273 */       var9.addVertexWithUV(var28, var30, var34, var20, var24);
/* 7132:8274 */       var9.addVertexWithUV(var26, var30, var34, var12, var16);
/* 7133:     */     }
/* 7134:     */   }
/* 7135:     */   
/* 7136:     */   public void renderFaceZPos(Block p_147734_1_, double p_147734_2_, double p_147734_4_, double p_147734_6_, IIcon p_147734_8_)
/* 7137:     */   {
/* 7138:8280 */     Tessellator var9 = Tessellator.instance;
/* 7139:8282 */     if (hasOverrideBlockTexture()) {
/* 7140:8284 */       p_147734_8_ = this.overrideBlockTexture;
/* 7141:     */     }
/* 7142:8287 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateWest == 0)) {
/* 7143:8289 */       p_147734_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147734_1_, (int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3, p_147734_8_);
/* 7144:     */     }
/* 7145:8292 */     boolean uvRotateSet = false;
/* 7146:8294 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateWest == 0))
/* 7147:     */     {
/* 7148:8296 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147734_8_);
/* 7149:8298 */       if (var10 != null)
/* 7150:     */       {
/* 7151:8300 */         int rand = Config.getRandom((int)p_147734_2_, (int)p_147734_4_, (int)p_147734_6_, 3);
/* 7152:8302 */         if (var10.rotation > 1) {
/* 7153:8304 */           this.uvRotateWest = (rand & 0x3);
/* 7154:     */         }
/* 7155:8307 */         if (var10.rotation == 2) {
/* 7156:8309 */           this.uvRotateWest = (this.uvRotateWest / 2 * 3);
/* 7157:     */         }
/* 7158:8312 */         if (var10.flip) {
/* 7159:8314 */           this.flipTexture = ((rand & 0x4) != 0);
/* 7160:     */         }
/* 7161:8317 */         uvRotateSet = true;
/* 7162:     */       }
/* 7163:     */     }
/* 7164:8321 */     double var101 = p_147734_8_.getInterpolatedU(this.renderMinX * 16.0D);
/* 7165:8322 */     double var12 = p_147734_8_.getInterpolatedU(this.renderMaxX * 16.0D);
/* 7166:8323 */     double var14 = p_147734_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 7167:8324 */     double var16 = p_147734_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/* 7168:8327 */     if (this.flipTexture)
/* 7169:     */     {
/* 7170:8329 */       double var18 = var101;
/* 7171:8330 */       var101 = var12;
/* 7172:8331 */       var12 = var18;
/* 7173:     */     }
/* 7174:8334 */     if ((this.renderMinX < 0.0D) || (this.renderMaxX > 1.0D))
/* 7175:     */     {
/* 7176:8336 */       var101 = p_147734_8_.getMinU();
/* 7177:8337 */       var12 = p_147734_8_.getMaxU();
/* 7178:     */     }
/* 7179:8340 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/* 7180:     */     {
/* 7181:8342 */       var14 = p_147734_8_.getMinV();
/* 7182:8343 */       var16 = p_147734_8_.getMaxV();
/* 7183:     */     }
/* 7184:8346 */     double var18 = var12;
/* 7185:8347 */     double var20 = var101;
/* 7186:8348 */     double var22 = var14;
/* 7187:8349 */     double var24 = var16;
/* 7188:8352 */     if (this.uvRotateWest == 1)
/* 7189:     */     {
/* 7190:8354 */       var101 = p_147734_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 7191:8355 */       var16 = p_147734_8_.getInterpolatedV(16.0D - this.renderMinX * 16.0D);
/* 7192:8356 */       var12 = p_147734_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 7193:8357 */       var14 = p_147734_8_.getInterpolatedV(16.0D - this.renderMaxX * 16.0D);
/* 7194:8359 */       if (this.flipTexture)
/* 7195:     */       {
/* 7196:8361 */         double var26 = var101;
/* 7197:8362 */         var101 = var12;
/* 7198:8363 */         var12 = var26;
/* 7199:     */       }
/* 7200:8366 */       var22 = var14;
/* 7201:8367 */       var24 = var16;
/* 7202:8368 */       var18 = var101;
/* 7203:8369 */       var20 = var12;
/* 7204:8370 */       var14 = var16;
/* 7205:8371 */       var16 = var22;
/* 7206:     */     }
/* 7207:8373 */     else if (this.uvRotateWest == 2)
/* 7208:     */     {
/* 7209:8375 */       var101 = p_147734_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 7210:8376 */       var14 = p_147734_8_.getInterpolatedV(this.renderMinX * 16.0D);
/* 7211:8377 */       var12 = p_147734_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 7212:8378 */       var16 = p_147734_8_.getInterpolatedV(this.renderMaxX * 16.0D);
/* 7213:8380 */       if (this.flipTexture)
/* 7214:     */       {
/* 7215:8382 */         double var26 = var101;
/* 7216:8383 */         var101 = var12;
/* 7217:8384 */         var12 = var26;
/* 7218:     */       }
/* 7219:8387 */       var18 = var12;
/* 7220:8388 */       var20 = var101;
/* 7221:8389 */       var101 = var12;
/* 7222:8390 */       var12 = var20;
/* 7223:8391 */       var22 = var16;
/* 7224:8392 */       var24 = var14;
/* 7225:     */     }
/* 7226:8394 */     else if (this.uvRotateWest == 3)
/* 7227:     */     {
/* 7228:8396 */       var101 = p_147734_8_.getInterpolatedU(16.0D - this.renderMinX * 16.0D);
/* 7229:8397 */       var12 = p_147734_8_.getInterpolatedU(16.0D - this.renderMaxX * 16.0D);
/* 7230:8398 */       var14 = p_147734_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 7231:8399 */       var16 = p_147734_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 7232:8401 */       if (this.flipTexture)
/* 7233:     */       {
/* 7234:8403 */         double var26 = var101;
/* 7235:8404 */         var101 = var12;
/* 7236:8405 */         var12 = var26;
/* 7237:     */       }
/* 7238:8408 */       var18 = var12;
/* 7239:8409 */       var20 = var101;
/* 7240:8410 */       var22 = var14;
/* 7241:8411 */       var24 = var16;
/* 7242:     */     }
/* 7243:8414 */     if (uvRotateSet)
/* 7244:     */     {
/* 7245:8416 */       this.uvRotateWest = 0;
/* 7246:8417 */       this.flipTexture = false;
/* 7247:     */     }
/* 7248:8420 */     double var26 = p_147734_2_ + this.renderMinX;
/* 7249:8421 */     double var28 = p_147734_2_ + this.renderMaxX;
/* 7250:8422 */     double var30 = p_147734_4_ + this.renderMinY;
/* 7251:8423 */     double var32 = p_147734_4_ + this.renderMaxY;
/* 7252:8424 */     double var34 = p_147734_6_ + this.renderMaxZ;
/* 7253:8426 */     if (this.renderFromInside)
/* 7254:     */     {
/* 7255:8428 */       var26 = p_147734_2_ + this.renderMaxX;
/* 7256:8429 */       var28 = p_147734_2_ + this.renderMinX;
/* 7257:     */     }
/* 7258:8432 */     if (this.enableAO)
/* 7259:     */     {
/* 7260:8434 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 7261:8435 */       var9.setBrightness(this.brightnessTopLeft);
/* 7262:8436 */       var9.addVertexWithUV(var26, var32, var34, var101, var14);
/* 7263:8437 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 7264:8438 */       var9.setBrightness(this.brightnessBottomLeft);
/* 7265:8439 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 7266:8440 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 7267:8441 */       var9.setBrightness(this.brightnessBottomRight);
/* 7268:8442 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 7269:8443 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 7270:8444 */       var9.setBrightness(this.brightnessTopRight);
/* 7271:8445 */       var9.addVertexWithUV(var28, var32, var34, var18, var22);
/* 7272:     */     }
/* 7273:     */     else
/* 7274:     */     {
/* 7275:8449 */       var9.addVertexWithUV(var26, var32, var34, var101, var14);
/* 7276:8450 */       var9.addVertexWithUV(var26, var30, var34, var20, var24);
/* 7277:8451 */       var9.addVertexWithUV(var28, var30, var34, var12, var16);
/* 7278:8452 */       var9.addVertexWithUV(var28, var32, var34, var18, var22);
/* 7279:     */     }
/* 7280:     */   }
/* 7281:     */   
/* 7282:     */   public void renderFaceXNeg(Block p_147798_1_, double p_147798_2_, double p_147798_4_, double p_147798_6_, IIcon p_147798_8_)
/* 7283:     */   {
/* 7284:8458 */     Tessellator var9 = Tessellator.instance;
/* 7285:8460 */     if (hasOverrideBlockTexture()) {
/* 7286:8462 */       p_147798_8_ = this.overrideBlockTexture;
/* 7287:     */     }
/* 7288:8465 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateNorth == 0)) {
/* 7289:8467 */       p_147798_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147798_1_, (int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4, p_147798_8_);
/* 7290:     */     }
/* 7291:8470 */     boolean uvRotateSet = false;
/* 7292:8472 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateNorth == 0))
/* 7293:     */     {
/* 7294:8474 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147798_8_);
/* 7295:8476 */       if (var10 != null)
/* 7296:     */       {
/* 7297:8478 */         int rand = Config.getRandom((int)p_147798_2_, (int)p_147798_4_, (int)p_147798_6_, 4);
/* 7298:8480 */         if (var10.rotation > 1) {
/* 7299:8482 */           this.uvRotateNorth = (rand & 0x3);
/* 7300:     */         }
/* 7301:8485 */         if (var10.rotation == 2) {
/* 7302:8487 */           this.uvRotateNorth = (this.uvRotateNorth / 2 * 3);
/* 7303:     */         }
/* 7304:8490 */         if (var10.flip) {
/* 7305:8492 */           this.flipTexture = ((rand & 0x4) != 0);
/* 7306:     */         }
/* 7307:8495 */         uvRotateSet = true;
/* 7308:     */       }
/* 7309:     */     }
/* 7310:8499 */     double var101 = p_147798_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 7311:8500 */     double var12 = p_147798_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 7312:8501 */     double var14 = p_147798_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 7313:8502 */     double var16 = p_147798_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/* 7314:8505 */     if (this.flipTexture)
/* 7315:     */     {
/* 7316:8507 */       double var18 = var101;
/* 7317:8508 */       var101 = var12;
/* 7318:8509 */       var12 = var18;
/* 7319:     */     }
/* 7320:8512 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/* 7321:     */     {
/* 7322:8514 */       var101 = p_147798_8_.getMinU();
/* 7323:8515 */       var12 = p_147798_8_.getMaxU();
/* 7324:     */     }
/* 7325:8518 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/* 7326:     */     {
/* 7327:8520 */       var14 = p_147798_8_.getMinV();
/* 7328:8521 */       var16 = p_147798_8_.getMaxV();
/* 7329:     */     }
/* 7330:8524 */     double var18 = var12;
/* 7331:8525 */     double var20 = var101;
/* 7332:8526 */     double var22 = var14;
/* 7333:8527 */     double var24 = var16;
/* 7334:8530 */     if (this.uvRotateNorth == 1)
/* 7335:     */     {
/* 7336:8532 */       var101 = p_147798_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 7337:8533 */       var14 = p_147798_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 7338:8534 */       var12 = p_147798_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 7339:8535 */       var16 = p_147798_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 7340:8537 */       if (this.flipTexture)
/* 7341:     */       {
/* 7342:8539 */         double var26 = var101;
/* 7343:8540 */         var101 = var12;
/* 7344:8541 */         var12 = var26;
/* 7345:     */       }
/* 7346:8544 */       var22 = var14;
/* 7347:8545 */       var24 = var16;
/* 7348:8546 */       var18 = var101;
/* 7349:8547 */       var20 = var12;
/* 7350:8548 */       var14 = var16;
/* 7351:8549 */       var16 = var22;
/* 7352:     */     }
/* 7353:8551 */     else if (this.uvRotateNorth == 2)
/* 7354:     */     {
/* 7355:8553 */       var101 = p_147798_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 7356:8554 */       var14 = p_147798_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 7357:8555 */       var12 = p_147798_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 7358:8556 */       var16 = p_147798_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 7359:8558 */       if (this.flipTexture)
/* 7360:     */       {
/* 7361:8560 */         double var26 = var101;
/* 7362:8561 */         var101 = var12;
/* 7363:8562 */         var12 = var26;
/* 7364:     */       }
/* 7365:8565 */       var18 = var12;
/* 7366:8566 */       var20 = var101;
/* 7367:8567 */       var101 = var12;
/* 7368:8568 */       var12 = var20;
/* 7369:8569 */       var22 = var16;
/* 7370:8570 */       var24 = var14;
/* 7371:     */     }
/* 7372:8572 */     else if (this.uvRotateNorth == 3)
/* 7373:     */     {
/* 7374:8574 */       var101 = p_147798_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 7375:8575 */       var12 = p_147798_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 7376:8576 */       var14 = p_147798_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 7377:8577 */       var16 = p_147798_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 7378:8579 */       if (this.flipTexture)
/* 7379:     */       {
/* 7380:8581 */         double var26 = var101;
/* 7381:8582 */         var101 = var12;
/* 7382:8583 */         var12 = var26;
/* 7383:     */       }
/* 7384:8586 */       var18 = var12;
/* 7385:8587 */       var20 = var101;
/* 7386:8588 */       var22 = var14;
/* 7387:8589 */       var24 = var16;
/* 7388:     */     }
/* 7389:8592 */     if (uvRotateSet)
/* 7390:     */     {
/* 7391:8594 */       this.uvRotateNorth = 0;
/* 7392:8595 */       this.flipTexture = false;
/* 7393:     */     }
/* 7394:8598 */     double var26 = p_147798_2_ + this.renderMinX;
/* 7395:8599 */     double var28 = p_147798_4_ + this.renderMinY;
/* 7396:8600 */     double var30 = p_147798_4_ + this.renderMaxY;
/* 7397:8601 */     double var32 = p_147798_6_ + this.renderMinZ;
/* 7398:8602 */     double var34 = p_147798_6_ + this.renderMaxZ;
/* 7399:8604 */     if (this.renderFromInside)
/* 7400:     */     {
/* 7401:8606 */       var32 = p_147798_6_ + this.renderMaxZ;
/* 7402:8607 */       var34 = p_147798_6_ + this.renderMinZ;
/* 7403:     */     }
/* 7404:8610 */     if (this.enableAO)
/* 7405:     */     {
/* 7406:8612 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 7407:8613 */       var9.setBrightness(this.brightnessTopLeft);
/* 7408:8614 */       var9.addVertexWithUV(var26, var30, var34, var18, var22);
/* 7409:8615 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 7410:8616 */       var9.setBrightness(this.brightnessBottomLeft);
/* 7411:8617 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 7412:8618 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 7413:8619 */       var9.setBrightness(this.brightnessBottomRight);
/* 7414:8620 */       var9.addVertexWithUV(var26, var28, var32, var20, var24);
/* 7415:8621 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 7416:8622 */       var9.setBrightness(this.brightnessTopRight);
/* 7417:8623 */       var9.addVertexWithUV(var26, var28, var34, var12, var16);
/* 7418:     */     }
/* 7419:     */     else
/* 7420:     */     {
/* 7421:8627 */       var9.addVertexWithUV(var26, var30, var34, var18, var22);
/* 7422:8628 */       var9.addVertexWithUV(var26, var30, var32, var101, var14);
/* 7423:8629 */       var9.addVertexWithUV(var26, var28, var32, var20, var24);
/* 7424:8630 */       var9.addVertexWithUV(var26, var28, var34, var12, var16);
/* 7425:     */     }
/* 7426:     */   }
/* 7427:     */   
/* 7428:     */   public void renderFaceXPos(Block p_147764_1_, double p_147764_2_, double p_147764_4_, double p_147764_6_, IIcon p_147764_8_)
/* 7429:     */   {
/* 7430:8636 */     Tessellator var9 = Tessellator.instance;
/* 7431:8638 */     if (hasOverrideBlockTexture()) {
/* 7432:8640 */       p_147764_8_ = this.overrideBlockTexture;
/* 7433:     */     }
/* 7434:8643 */     if ((Config.isConnectedTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateSouth == 0)) {
/* 7435:8645 */       p_147764_8_ = ConnectedTextures.getConnectedTexture(this.blockAccess, p_147764_1_, (int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5, p_147764_8_);
/* 7436:     */     }
/* 7437:8648 */     boolean uvRotateSet = false;
/* 7438:8650 */     if ((Config.isNaturalTextures()) && (this.overrideBlockTexture == null) && (this.uvRotateSouth == 0))
/* 7439:     */     {
/* 7440:8652 */       NaturalProperties var10 = NaturalTextures.getNaturalProperties(p_147764_8_);
/* 7441:8654 */       if (var10 != null)
/* 7442:     */       {
/* 7443:8656 */         int rand = Config.getRandom((int)p_147764_2_, (int)p_147764_4_, (int)p_147764_6_, 5);
/* 7444:8658 */         if (var10.rotation > 1) {
/* 7445:8660 */           this.uvRotateSouth = (rand & 0x3);
/* 7446:     */         }
/* 7447:8663 */         if (var10.rotation == 2) {
/* 7448:8665 */           this.uvRotateSouth = (this.uvRotateSouth / 2 * 3);
/* 7449:     */         }
/* 7450:8668 */         if (var10.flip) {
/* 7451:8670 */           this.flipTexture = ((rand & 0x4) != 0);
/* 7452:     */         }
/* 7453:8673 */         uvRotateSet = true;
/* 7454:     */       }
/* 7455:     */     }
/* 7456:8677 */     double var101 = p_147764_8_.getInterpolatedU(this.renderMaxZ * 16.0D);
/* 7457:8678 */     double var12 = p_147764_8_.getInterpolatedU(this.renderMinZ * 16.0D);
/* 7458:8679 */     double var14 = p_147764_8_.getInterpolatedV(16.0D - this.renderMaxY * 16.0D);
/* 7459:8680 */     double var16 = p_147764_8_.getInterpolatedV(16.0D - this.renderMinY * 16.0D);
/* 7460:8683 */     if (this.flipTexture)
/* 7461:     */     {
/* 7462:8685 */       double var18 = var101;
/* 7463:8686 */       var101 = var12;
/* 7464:8687 */       var12 = var18;
/* 7465:     */     }
/* 7466:8690 */     if ((this.renderMinZ < 0.0D) || (this.renderMaxZ > 1.0D))
/* 7467:     */     {
/* 7468:8692 */       var101 = p_147764_8_.getMinU();
/* 7469:8693 */       var12 = p_147764_8_.getMaxU();
/* 7470:     */     }
/* 7471:8696 */     if ((this.renderMinY < 0.0D) || (this.renderMaxY > 1.0D))
/* 7472:     */     {
/* 7473:8698 */       var14 = p_147764_8_.getMinV();
/* 7474:8699 */       var16 = p_147764_8_.getMaxV();
/* 7475:     */     }
/* 7476:8702 */     double var18 = var12;
/* 7477:8703 */     double var20 = var101;
/* 7478:8704 */     double var22 = var14;
/* 7479:8705 */     double var24 = var16;
/* 7480:8708 */     if (this.uvRotateSouth == 2)
/* 7481:     */     {
/* 7482:8710 */       var101 = p_147764_8_.getInterpolatedU(this.renderMinY * 16.0D);
/* 7483:8711 */       var14 = p_147764_8_.getInterpolatedV(16.0D - this.renderMinZ * 16.0D);
/* 7484:8712 */       var12 = p_147764_8_.getInterpolatedU(this.renderMaxY * 16.0D);
/* 7485:8713 */       var16 = p_147764_8_.getInterpolatedV(16.0D - this.renderMaxZ * 16.0D);
/* 7486:8715 */       if (this.flipTexture)
/* 7487:     */       {
/* 7488:8717 */         double var26 = var101;
/* 7489:8718 */         var101 = var12;
/* 7490:8719 */         var12 = var26;
/* 7491:     */       }
/* 7492:8722 */       var22 = var14;
/* 7493:8723 */       var24 = var16;
/* 7494:8724 */       var18 = var101;
/* 7495:8725 */       var20 = var12;
/* 7496:8726 */       var14 = var16;
/* 7497:8727 */       var16 = var22;
/* 7498:     */     }
/* 7499:8729 */     else if (this.uvRotateSouth == 1)
/* 7500:     */     {
/* 7501:8731 */       var101 = p_147764_8_.getInterpolatedU(16.0D - this.renderMaxY * 16.0D);
/* 7502:8732 */       var14 = p_147764_8_.getInterpolatedV(this.renderMaxZ * 16.0D);
/* 7503:8733 */       var12 = p_147764_8_.getInterpolatedU(16.0D - this.renderMinY * 16.0D);
/* 7504:8734 */       var16 = p_147764_8_.getInterpolatedV(this.renderMinZ * 16.0D);
/* 7505:8736 */       if (this.flipTexture)
/* 7506:     */       {
/* 7507:8738 */         double var26 = var101;
/* 7508:8739 */         var101 = var12;
/* 7509:8740 */         var12 = var26;
/* 7510:     */       }
/* 7511:8743 */       var18 = var12;
/* 7512:8744 */       var20 = var101;
/* 7513:8745 */       var101 = var12;
/* 7514:8746 */       var12 = var20;
/* 7515:8747 */       var22 = var16;
/* 7516:8748 */       var24 = var14;
/* 7517:     */     }
/* 7518:8750 */     else if (this.uvRotateSouth == 3)
/* 7519:     */     {
/* 7520:8752 */       var101 = p_147764_8_.getInterpolatedU(16.0D - this.renderMinZ * 16.0D);
/* 7521:8753 */       var12 = p_147764_8_.getInterpolatedU(16.0D - this.renderMaxZ * 16.0D);
/* 7522:8754 */       var14 = p_147764_8_.getInterpolatedV(this.renderMaxY * 16.0D);
/* 7523:8755 */       var16 = p_147764_8_.getInterpolatedV(this.renderMinY * 16.0D);
/* 7524:8757 */       if (this.flipTexture)
/* 7525:     */       {
/* 7526:8759 */         double var26 = var101;
/* 7527:8760 */         var101 = var12;
/* 7528:8761 */         var12 = var26;
/* 7529:     */       }
/* 7530:8764 */       var18 = var12;
/* 7531:8765 */       var20 = var101;
/* 7532:8766 */       var22 = var14;
/* 7533:8767 */       var24 = var16;
/* 7534:     */     }
/* 7535:8770 */     if (uvRotateSet)
/* 7536:     */     {
/* 7537:8772 */       this.uvRotateSouth = 0;
/* 7538:8773 */       this.flipTexture = false;
/* 7539:     */     }
/* 7540:8776 */     double var26 = p_147764_2_ + this.renderMaxX;
/* 7541:8777 */     double var28 = p_147764_4_ + this.renderMinY;
/* 7542:8778 */     double var30 = p_147764_4_ + this.renderMaxY;
/* 7543:8779 */     double var32 = p_147764_6_ + this.renderMinZ;
/* 7544:8780 */     double var34 = p_147764_6_ + this.renderMaxZ;
/* 7545:8782 */     if (this.renderFromInside)
/* 7546:     */     {
/* 7547:8784 */       var32 = p_147764_6_ + this.renderMaxZ;
/* 7548:8785 */       var34 = p_147764_6_ + this.renderMinZ;
/* 7549:     */     }
/* 7550:8788 */     if (this.enableAO)
/* 7551:     */     {
/* 7552:8790 */       var9.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
/* 7553:8791 */       var9.setBrightness(this.brightnessTopLeft);
/* 7554:8792 */       var9.addVertexWithUV(var26, var28, var34, var20, var24);
/* 7555:8793 */       var9.setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
/* 7556:8794 */       var9.setBrightness(this.brightnessBottomLeft);
/* 7557:8795 */       var9.addVertexWithUV(var26, var28, var32, var12, var16);
/* 7558:8796 */       var9.setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
/* 7559:8797 */       var9.setBrightness(this.brightnessBottomRight);
/* 7560:8798 */       var9.addVertexWithUV(var26, var30, var32, var18, var22);
/* 7561:8799 */       var9.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
/* 7562:8800 */       var9.setBrightness(this.brightnessTopRight);
/* 7563:8801 */       var9.addVertexWithUV(var26, var30, var34, var101, var14);
/* 7564:     */     }
/* 7565:     */     else
/* 7566:     */     {
/* 7567:8805 */       var9.addVertexWithUV(var26, var28, var34, var20, var24);
/* 7568:8806 */       var9.addVertexWithUV(var26, var28, var32, var12, var16);
/* 7569:8807 */       var9.addVertexWithUV(var26, var30, var32, var18, var22);
/* 7570:8808 */       var9.addVertexWithUV(var26, var30, var34, var101, var14);
/* 7571:     */     }
/* 7572:     */   }
/* 7573:     */   
/* 7574:     */   public void renderBlockAsItem(Block p_147800_1_, int p_147800_2_, float p_147800_3_)
/* 7575:     */   {
/* 7576:8814 */     Tessellator var4 = Tessellator.instance;
/* 7577:8815 */     boolean var5 = p_147800_1_ == Blocks.grass;
/* 7578:8817 */     if ((p_147800_1_ == Blocks.dispenser) || (p_147800_1_ == Blocks.dropper) || (p_147800_1_ == Blocks.furnace)) {
/* 7579:8819 */       p_147800_2_ = 3;
/* 7580:     */     }
/* 7581:8827 */     if (this.useInventoryTint)
/* 7582:     */     {
/* 7583:8829 */       int var6 = p_147800_1_.getRenderColor(p_147800_2_);
/* 7584:8831 */       if (var5) {
/* 7585:8833 */         var6 = 16777215;
/* 7586:     */       }
/* 7587:8836 */       float var7 = (var6 >> 16 & 0xFF) / 255.0F;
/* 7588:8837 */       float var8 = (var6 >> 8 & 0xFF) / 255.0F;
/* 7589:8838 */       float var9 = (var6 & 0xFF) / 255.0F;
/* 7590:8839 */       GL11.glColor4f(var7 * p_147800_3_, var8 * p_147800_3_, var9 * p_147800_3_, 1.0F);
/* 7591:     */     }
/* 7592:8842 */     int var6 = p_147800_1_.getRenderType();
/* 7593:8843 */     setRenderBoundsFromBlock(p_147800_1_);
/* 7594:8846 */     if ((var6 != 0) && (var6 != 31) && (var6 != 39) && (var6 != 16) && (var6 != 26))
/* 7595:     */     {
/* 7596:8848 */       if (var6 == 1)
/* 7597:     */       {
/* 7598:8850 */         var4.startDrawingQuads();
/* 7599:8851 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7600:8852 */         IIcon var171 = getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_);
/* 7601:8853 */         drawCrossedSquares(var171, -0.5D, -0.5D, -0.5D, 1.0F);
/* 7602:8854 */         var4.draw();
/* 7603:     */       }
/* 7604:8856 */       else if (var6 == 19)
/* 7605:     */       {
/* 7606:8858 */         var4.startDrawingQuads();
/* 7607:8859 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7608:8860 */         p_147800_1_.setBlockBoundsForItemRender();
/* 7609:8861 */         renderBlockStemSmall(p_147800_1_, p_147800_2_, this.renderMaxY, -0.5D, -0.5D, -0.5D);
/* 7610:8862 */         var4.draw();
/* 7611:     */       }
/* 7612:8864 */       else if (var6 == 23)
/* 7613:     */       {
/* 7614:8866 */         var4.startDrawingQuads();
/* 7615:8867 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7616:8868 */         p_147800_1_.setBlockBoundsForItemRender();
/* 7617:8869 */         var4.draw();
/* 7618:     */       }
/* 7619:8871 */       else if (var6 == 13)
/* 7620:     */       {
/* 7621:8873 */         p_147800_1_.setBlockBoundsForItemRender();
/* 7622:8874 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7623:8875 */         float var7 = 0.0625F;
/* 7624:8876 */         var4.startDrawingQuads();
/* 7625:8877 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7626:8878 */         renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 0));
/* 7627:8879 */         var4.draw();
/* 7628:8880 */         var4.startDrawingQuads();
/* 7629:8881 */         var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7630:8882 */         renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 1));
/* 7631:8883 */         var4.draw();
/* 7632:8884 */         var4.startDrawingQuads();
/* 7633:8885 */         var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7634:8886 */         var4.addTranslation(0.0F, 0.0F, var7);
/* 7635:8887 */         renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 2));
/* 7636:8888 */         var4.addTranslation(0.0F, 0.0F, -var7);
/* 7637:8889 */         var4.draw();
/* 7638:8890 */         var4.startDrawingQuads();
/* 7639:8891 */         var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7640:8892 */         var4.addTranslation(0.0F, 0.0F, -var7);
/* 7641:8893 */         renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 3));
/* 7642:8894 */         var4.addTranslation(0.0F, 0.0F, var7);
/* 7643:8895 */         var4.draw();
/* 7644:8896 */         var4.startDrawingQuads();
/* 7645:8897 */         var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7646:8898 */         var4.addTranslation(var7, 0.0F, 0.0F);
/* 7647:8899 */         renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 4));
/* 7648:8900 */         var4.addTranslation(-var7, 0.0F, 0.0F);
/* 7649:8901 */         var4.draw();
/* 7650:8902 */         var4.startDrawingQuads();
/* 7651:8903 */         var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7652:8904 */         var4.addTranslation(-var7, 0.0F, 0.0F);
/* 7653:8905 */         renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 5));
/* 7654:8906 */         var4.addTranslation(var7, 0.0F, 0.0F);
/* 7655:8907 */         var4.draw();
/* 7656:8908 */         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7657:     */       }
/* 7658:8910 */       else if (var6 == 22)
/* 7659:     */       {
/* 7660:8912 */         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 7661:8913 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7662:8914 */         TileEntityRendererChestHelper.instance.func_147715_a(p_147800_1_, p_147800_2_, p_147800_3_);
/* 7663:8915 */         GL11.glEnable(32826);
/* 7664:     */       }
/* 7665:8917 */       else if (var6 == 6)
/* 7666:     */       {
/* 7667:8919 */         var4.startDrawingQuads();
/* 7668:8920 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7669:8921 */         renderBlockCropsImpl(p_147800_1_, p_147800_2_, -0.5D, -0.5D, -0.5D);
/* 7670:8922 */         var4.draw();
/* 7671:     */       }
/* 7672:8924 */       else if (var6 == 2)
/* 7673:     */       {
/* 7674:8926 */         var4.startDrawingQuads();
/* 7675:8927 */         var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7676:8928 */         renderTorchAtAngle(p_147800_1_, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
/* 7677:8929 */         var4.draw();
/* 7678:     */       }
/* 7679:8931 */       else if (var6 == 10)
/* 7680:     */       {
/* 7681:8933 */         for (int var14 = 0; var14 < 2; var14++)
/* 7682:     */         {
/* 7683:8935 */           if (var14 == 0) {
/* 7684:8937 */             setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
/* 7685:     */           }
/* 7686:8940 */           if (var14 == 1) {
/* 7687:8942 */             setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
/* 7688:     */           }
/* 7689:8945 */           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7690:8946 */           var4.startDrawingQuads();
/* 7691:8947 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7692:8948 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 0));
/* 7693:8949 */           var4.draw();
/* 7694:8950 */           var4.startDrawingQuads();
/* 7695:8951 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7696:8952 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 1));
/* 7697:8953 */           var4.draw();
/* 7698:8954 */           var4.startDrawingQuads();
/* 7699:8955 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7700:8956 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 2));
/* 7701:8957 */           var4.draw();
/* 7702:8958 */           var4.startDrawingQuads();
/* 7703:8959 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7704:8960 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 3));
/* 7705:8961 */           var4.draw();
/* 7706:8962 */           var4.startDrawingQuads();
/* 7707:8963 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7708:8964 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 4));
/* 7709:8965 */           var4.draw();
/* 7710:8966 */           var4.startDrawingQuads();
/* 7711:8967 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7712:8968 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 5));
/* 7713:8969 */           var4.draw();
/* 7714:8970 */           GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7715:     */         }
/* 7716:     */       }
/* 7717:8973 */       else if (var6 == 27)
/* 7718:     */       {
/* 7719:8975 */         int var14 = 0;
/* 7720:8976 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7721:8977 */         var4.startDrawingQuads();
/* 7722:8979 */         for (int var181 = 0; var181 < 8; var181++)
/* 7723:     */         {
/* 7724:8981 */           byte var17 = 0;
/* 7725:8982 */           byte var18 = 1;
/* 7726:8984 */           if (var181 == 0) {
/* 7727:8986 */             var17 = 2;
/* 7728:     */           }
/* 7729:8989 */           if (var181 == 1) {
/* 7730:8991 */             var17 = 3;
/* 7731:     */           }
/* 7732:8994 */           if (var181 == 2) {
/* 7733:8996 */             var17 = 4;
/* 7734:     */           }
/* 7735:8999 */           if (var181 == 3)
/* 7736:     */           {
/* 7737:9001 */             var17 = 5;
/* 7738:9002 */             var18 = 2;
/* 7739:     */           }
/* 7740:9005 */           if (var181 == 4)
/* 7741:     */           {
/* 7742:9007 */             var17 = 6;
/* 7743:9008 */             var18 = 3;
/* 7744:     */           }
/* 7745:9011 */           if (var181 == 5)
/* 7746:     */           {
/* 7747:9013 */             var17 = 7;
/* 7748:9014 */             var18 = 5;
/* 7749:     */           }
/* 7750:9017 */           if (var181 == 6)
/* 7751:     */           {
/* 7752:9019 */             var17 = 6;
/* 7753:9020 */             var18 = 2;
/* 7754:     */           }
/* 7755:9023 */           if (var181 == 7) {
/* 7756:9025 */             var17 = 3;
/* 7757:     */           }
/* 7758:9028 */           float var11 = var17 / 16.0F;
/* 7759:9029 */           float var12 = 1.0F - var14 / 16.0F;
/* 7760:9030 */           float var13 = 1.0F - (var14 + var18) / 16.0F;
/* 7761:9031 */           var14 += var18;
/* 7762:9032 */           setRenderBounds(0.5F - var11, var13, 0.5F - var11, 0.5F + var11, var12, 0.5F + var11);
/* 7763:9033 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7764:9034 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 0));
/* 7765:9035 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7766:9036 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 1));
/* 7767:9037 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7768:9038 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 2));
/* 7769:9039 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7770:9040 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 3));
/* 7771:9041 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7772:9042 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 4));
/* 7773:9043 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7774:9044 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 5));
/* 7775:     */         }
/* 7776:9047 */         var4.draw();
/* 7777:9048 */         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7778:9049 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 7779:     */       }
/* 7780:9051 */       else if (var6 == 11)
/* 7781:     */       {
/* 7782:9053 */         for (int var14 = 0; var14 < 4; var14++)
/* 7783:     */         {
/* 7784:9055 */           float var8 = 0.125F;
/* 7785:9057 */           if (var14 == 0) {
/* 7786:9059 */             setRenderBounds(0.5F - var8, 0.0D, 0.0D, 0.5F + var8, 1.0D, var8 * 2.0F);
/* 7787:     */           }
/* 7788:9062 */           if (var14 == 1) {
/* 7789:9064 */             setRenderBounds(0.5F - var8, 0.0D, 1.0F - var8 * 2.0F, 0.5F + var8, 1.0D, 1.0D);
/* 7790:     */           }
/* 7791:9067 */           var8 = 0.0625F;
/* 7792:9069 */           if (var14 == 2) {
/* 7793:9071 */             setRenderBounds(0.5F - var8, 1.0F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 1.0F - var8, 1.0F + var8 * 2.0F);
/* 7794:     */           }
/* 7795:9074 */           if (var14 == 3) {
/* 7796:9076 */             setRenderBounds(0.5F - var8, 0.5F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 0.5F - var8, 1.0F + var8 * 2.0F);
/* 7797:     */           }
/* 7798:9079 */           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7799:9080 */           var4.startDrawingQuads();
/* 7800:9081 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7801:9082 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 0));
/* 7802:9083 */           var4.draw();
/* 7803:9084 */           var4.startDrawingQuads();
/* 7804:9085 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7805:9086 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 1));
/* 7806:9087 */           var4.draw();
/* 7807:9088 */           var4.startDrawingQuads();
/* 7808:9089 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7809:9090 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 2));
/* 7810:9091 */           var4.draw();
/* 7811:9092 */           var4.startDrawingQuads();
/* 7812:9093 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7813:9094 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 3));
/* 7814:9095 */           var4.draw();
/* 7815:9096 */           var4.startDrawingQuads();
/* 7816:9097 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7817:9098 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 4));
/* 7818:9099 */           var4.draw();
/* 7819:9100 */           var4.startDrawingQuads();
/* 7820:9101 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7821:9102 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 5));
/* 7822:9103 */           var4.draw();
/* 7823:9104 */           GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7824:     */         }
/* 7825:9107 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 7826:     */       }
/* 7827:9109 */       else if (var6 == 21)
/* 7828:     */       {
/* 7829:9111 */         for (int var14 = 0; var14 < 3; var14++)
/* 7830:     */         {
/* 7831:9113 */           float var8 = 0.0625F;
/* 7832:9115 */           if (var14 == 0) {
/* 7833:9117 */             setRenderBounds(0.5F - var8, 0.300000011920929D, 0.0D, 0.5F + var8, 1.0D, var8 * 2.0F);
/* 7834:     */           }
/* 7835:9120 */           if (var14 == 1) {
/* 7836:9122 */             setRenderBounds(0.5F - var8, 0.300000011920929D, 1.0F - var8 * 2.0F, 0.5F + var8, 1.0D, 1.0D);
/* 7837:     */           }
/* 7838:9125 */           var8 = 0.0625F;
/* 7839:9127 */           if (var14 == 2) {
/* 7840:9129 */             setRenderBounds(0.5F - var8, 0.5D, 0.0D, 0.5F + var8, 1.0F - var8, 1.0D);
/* 7841:     */           }
/* 7842:9132 */           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7843:9133 */           var4.startDrawingQuads();
/* 7844:9134 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7845:9135 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 0));
/* 7846:9136 */           var4.draw();
/* 7847:9137 */           var4.startDrawingQuads();
/* 7848:9138 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7849:9139 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 1));
/* 7850:9140 */           var4.draw();
/* 7851:9141 */           var4.startDrawingQuads();
/* 7852:9142 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7853:9143 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 2));
/* 7854:9144 */           var4.draw();
/* 7855:9145 */           var4.startDrawingQuads();
/* 7856:9146 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7857:9147 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 3));
/* 7858:9148 */           var4.draw();
/* 7859:9149 */           var4.startDrawingQuads();
/* 7860:9150 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7861:9151 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 4));
/* 7862:9152 */           var4.draw();
/* 7863:9153 */           var4.startDrawingQuads();
/* 7864:9154 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7865:9155 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(p_147800_1_, 5));
/* 7866:9156 */           var4.draw();
/* 7867:9157 */           GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7868:     */         }
/* 7869:     */       }
/* 7870:9160 */       else if (var6 == 32)
/* 7871:     */       {
/* 7872:9162 */         for (int var14 = 0; var14 < 2; var14++)
/* 7873:     */         {
/* 7874:9164 */           if (var14 == 0) {
/* 7875:9166 */             setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
/* 7876:     */           }
/* 7877:9169 */           if (var14 == 1) {
/* 7878:9171 */             setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
/* 7879:     */           }
/* 7880:9174 */           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7881:9175 */           var4.startDrawingQuads();
/* 7882:9176 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7883:9177 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
/* 7884:9178 */           var4.draw();
/* 7885:9179 */           var4.startDrawingQuads();
/* 7886:9180 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7887:9181 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
/* 7888:9182 */           var4.draw();
/* 7889:9183 */           var4.startDrawingQuads();
/* 7890:9184 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7891:9185 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
/* 7892:9186 */           var4.draw();
/* 7893:9187 */           var4.startDrawingQuads();
/* 7894:9188 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7895:9189 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
/* 7896:9190 */           var4.draw();
/* 7897:9191 */           var4.startDrawingQuads();
/* 7898:9192 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7899:9193 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
/* 7900:9194 */           var4.draw();
/* 7901:9195 */           var4.startDrawingQuads();
/* 7902:9196 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7903:9197 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
/* 7904:9198 */           var4.draw();
/* 7905:9199 */           GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7906:     */         }
/* 7907:9202 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 7908:     */       }
/* 7909:9204 */       else if (var6 == 35)
/* 7910:     */       {
/* 7911:9206 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7912:9207 */         renderBlockAnvilOrient((BlockAnvil)p_147800_1_, 0, 0, 0, p_147800_2_ << 2, true);
/* 7913:9208 */         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7914:     */       }
/* 7915:9210 */       else if (var6 == 34)
/* 7916:     */       {
/* 7917:9212 */         for (int var14 = 0; var14 < 3; var14++)
/* 7918:     */         {
/* 7919:9214 */           if (var14 == 0)
/* 7920:     */           {
/* 7921:9216 */             setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
/* 7922:9217 */             setOverrideBlockTexture(getBlockIcon(Blocks.obsidian));
/* 7923:     */           }
/* 7924:9219 */           else if (var14 == 1)
/* 7925:     */           {
/* 7926:9221 */             setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
/* 7927:9222 */             setOverrideBlockTexture(getBlockIcon(Blocks.beacon));
/* 7928:     */           }
/* 7929:9224 */           else if (var14 == 2)
/* 7930:     */           {
/* 7931:9226 */             setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 7932:9227 */             setOverrideBlockTexture(getBlockIcon(Blocks.glass));
/* 7933:     */           }
/* 7934:9230 */           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7935:9231 */           var4.startDrawingQuads();
/* 7936:9232 */           var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7937:9233 */           renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
/* 7938:9234 */           var4.draw();
/* 7939:9235 */           var4.startDrawingQuads();
/* 7940:9236 */           var4.setNormal(0.0F, 1.0F, 0.0F);
/* 7941:9237 */           renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
/* 7942:9238 */           var4.draw();
/* 7943:9239 */           var4.startDrawingQuads();
/* 7944:9240 */           var4.setNormal(0.0F, 0.0F, -1.0F);
/* 7945:9241 */           renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
/* 7946:9242 */           var4.draw();
/* 7947:9243 */           var4.startDrawingQuads();
/* 7948:9244 */           var4.setNormal(0.0F, 0.0F, 1.0F);
/* 7949:9245 */           renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
/* 7950:9246 */           var4.draw();
/* 7951:9247 */           var4.startDrawingQuads();
/* 7952:9248 */           var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 7953:9249 */           renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
/* 7954:9250 */           var4.draw();
/* 7955:9251 */           var4.startDrawingQuads();
/* 7956:9252 */           var4.setNormal(1.0F, 0.0F, 0.0F);
/* 7957:9253 */           renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
/* 7958:9254 */           var4.draw();
/* 7959:9255 */           GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7960:     */         }
/* 7961:9258 */         setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/* 7962:9259 */         clearOverrideBlockTexture();
/* 7963:     */       }
/* 7964:9261 */       else if (var6 == 38)
/* 7965:     */       {
/* 7966:9263 */         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7967:9264 */         renderBlockHopperMetadata((BlockHopper)p_147800_1_, 0, 0, 0, 0, true);
/* 7968:9265 */         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 7969:     */       }
/* 7970:9267 */       else if (Reflector.ModLoader.exists())
/* 7971:     */       {
/* 7972:9269 */         Reflector.callVoid(Reflector.ModLoader_renderInvBlock, new Object[] { this, p_147800_1_, Integer.valueOf(p_147800_2_), Integer.valueOf(var6) });
/* 7973:     */       }
/* 7974:9271 */       else if (Reflector.FMLRenderAccessLibrary.exists())
/* 7975:     */       {
/* 7976:9273 */         Reflector.callVoid(Reflector.FMLRenderAccessLibrary_renderInventoryBlock, new Object[] { this, p_147800_1_, Integer.valueOf(p_147800_2_), Integer.valueOf(var6) });
/* 7977:     */       }
/* 7978:     */     }
/* 7979:     */     else
/* 7980:     */     {
/* 7981:9278 */       if (var6 == 16) {
/* 7982:9280 */         p_147800_2_ = 1;
/* 7983:     */       }
/* 7984:9283 */       p_147800_1_.setBlockBoundsForItemRender();
/* 7985:9284 */       setRenderBoundsFromBlock(p_147800_1_);
/* 7986:9285 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 7987:9286 */       GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
/* 7988:9287 */       var4.startDrawingQuads();
/* 7989:9288 */       var4.setNormal(0.0F, -1.0F, 0.0F);
/* 7990:9289 */       renderFaceYNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 0, p_147800_2_));
/* 7991:9290 */       var4.draw();
/* 7992:9292 */       if ((var5) && (this.useInventoryTint))
/* 7993:     */       {
/* 7994:9294 */         int var14 = p_147800_1_.getRenderColor(p_147800_2_);
/* 7995:9295 */         float var8 = (var14 >> 16 & 0xFF) / 255.0F;
/* 7996:9296 */         float var9 = (var14 >> 8 & 0xFF) / 255.0F;
/* 7997:9297 */         float var10 = (var14 & 0xFF) / 255.0F;
/* 7998:9298 */         GL11.glColor4f(var8 * p_147800_3_, var9 * p_147800_3_, var10 * p_147800_3_, 1.0F);
/* 7999:     */       }
/* 8000:9301 */       var4.startDrawingQuads();
/* 8001:9302 */       var4.setNormal(0.0F, 1.0F, 0.0F);
/* 8002:9303 */       renderFaceYPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 1, p_147800_2_));
/* 8003:9304 */       var4.draw();
/* 8004:9306 */       if ((var5) && (this.useInventoryTint)) {
/* 8005:9308 */         GL11.glColor4f(p_147800_3_, p_147800_3_, p_147800_3_, 1.0F);
/* 8006:     */       }
/* 8007:9311 */       var4.startDrawingQuads();
/* 8008:9312 */       var4.setNormal(0.0F, 0.0F, -1.0F);
/* 8009:9313 */       renderFaceZNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 2, p_147800_2_));
/* 8010:9314 */       var4.draw();
/* 8011:9315 */       var4.startDrawingQuads();
/* 8012:9316 */       var4.setNormal(0.0F, 0.0F, 1.0F);
/* 8013:9317 */       renderFaceZPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 3, p_147800_2_));
/* 8014:9318 */       var4.draw();
/* 8015:9319 */       var4.startDrawingQuads();
/* 8016:9320 */       var4.setNormal(-1.0F, 0.0F, 0.0F);
/* 8017:9321 */       renderFaceXNeg(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 4, p_147800_2_));
/* 8018:9322 */       var4.draw();
/* 8019:9323 */       var4.startDrawingQuads();
/* 8020:9324 */       var4.setNormal(1.0F, 0.0F, 0.0F);
/* 8021:9325 */       renderFaceXPos(p_147800_1_, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(p_147800_1_, 5, p_147800_2_));
/* 8022:9326 */       var4.draw();
/* 8023:9327 */       GL11.glTranslatef(0.5F, 0.5F, 0.5F);
/* 8024:     */     }
/* 8025:     */   }
/* 8026:     */   
/* 8027:     */   public static boolean renderItemIn3d(int par0)
/* 8028:     */   {
/* 8029:9333 */     switch (par0)
/* 8030:     */     {
/* 8031:     */     case -1: 
/* 8032:9336 */       return false;
/* 8033:     */     case 0: 
/* 8034:     */     case 10: 
/* 8035:     */     case 11: 
/* 8036:     */     case 13: 
/* 8037:     */     case 16: 
/* 8038:     */     case 21: 
/* 8039:     */     case 22: 
/* 8040:     */     case 26: 
/* 8041:     */     case 27: 
/* 8042:     */     case 31: 
/* 8043:     */     case 32: 
/* 8044:     */     case 34: 
/* 8045:     */     case 35: 
/* 8046:     */     case 39: 
/* 8047:9352 */       return true;
/* 8048:     */     }
/* 8049:9381 */     return Reflector.FMLRenderAccessLibrary.exists() ? Reflector.callBoolean(Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock, new Object[] { Integer.valueOf(par0) }) : Reflector.ModLoader.exists() ? Reflector.callBoolean(Reflector.ModLoader_renderBlockIsItemFull3D, new Object[] { Integer.valueOf(par0) }) : false;
/* 8050:     */   }
/* 8051:     */   
/* 8052:     */   public IIcon getBlockIcon(Block p_147793_1_, IBlockAccess p_147793_2_, int p_147793_3_, int p_147793_4_, int p_147793_5_, int p_147793_6_)
/* 8053:     */   {
/* 8054:9387 */     return getIconSafe(p_147793_1_.getIcon(p_147793_2_, p_147793_3_, p_147793_4_, p_147793_5_, p_147793_6_));
/* 8055:     */   }
/* 8056:     */   
/* 8057:     */   public IIcon getBlockIconFromSideAndMetadata(Block p_147787_1_, int p_147787_2_, int p_147787_3_)
/* 8058:     */   {
/* 8059:9392 */     return getIconSafe(p_147787_1_.getIcon(p_147787_2_, p_147787_3_));
/* 8060:     */   }
/* 8061:     */   
/* 8062:     */   public IIcon getBlockIconFromSide(Block p_147777_1_, int p_147777_2_)
/* 8063:     */   {
/* 8064:9397 */     return getIconSafe(p_147777_1_.getBlockTextureFromSide(p_147777_2_));
/* 8065:     */   }
/* 8066:     */   
/* 8067:     */   public IIcon getBlockIcon(Block p_147745_1_)
/* 8068:     */   {
/* 8069:9402 */     return getIconSafe(p_147745_1_.getBlockTextureFromSide(1));
/* 8070:     */   }
/* 8071:     */   
/* 8072:     */   public IIcon getIconSafe(IIcon p_147758_1_)
/* 8073:     */   {
/* 8074:9407 */     if (p_147758_1_ == null) {
/* 8075:9409 */       p_147758_1_ = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
/* 8076:     */     }
/* 8077:9412 */     return p_147758_1_;
/* 8078:     */   }
/* 8079:     */   
/* 8080:     */   private float getAmbientOcclusionLightValue(int i, int j, int k)
/* 8081:     */   {
/* 8082:9417 */     Block block = this.blockAccess.getBlock(i, j, k);
/* 8083:9418 */     return block.isBlockNormalCube() ? this.aoLightValueOpaque : 1.0F;
/* 8084:     */   }
/* 8085:     */   
/* 8086:     */   private IIcon fixAoSideGrassTexture(IIcon tex, int x, int y, int z, int side, float f, float f1, float f2)
/* 8087:     */   {
/* 8088:9423 */     if ((tex == TextureUtils.iconGrassSide) || (tex == TextureUtils.iconMyceliumSide))
/* 8089:     */     {
/* 8090:9425 */       tex = Config.getSideGrassTexture(this.blockAccess, x, y, z, side, tex);
/* 8091:9427 */       if (tex == TextureUtils.iconGrassTop)
/* 8092:     */       {
/* 8093:9429 */         this.colorRedTopLeft *= f;
/* 8094:9430 */         this.colorRedBottomLeft *= f;
/* 8095:9431 */         this.colorRedBottomRight *= f;
/* 8096:9432 */         this.colorRedTopRight *= f;
/* 8097:9433 */         this.colorGreenTopLeft *= f1;
/* 8098:9434 */         this.colorGreenBottomLeft *= f1;
/* 8099:9435 */         this.colorGreenBottomRight *= f1;
/* 8100:9436 */         this.colorGreenTopRight *= f1;
/* 8101:9437 */         this.colorBlueTopLeft *= f2;
/* 8102:9438 */         this.colorBlueBottomLeft *= f2;
/* 8103:9439 */         this.colorBlueBottomRight *= f2;
/* 8104:9440 */         this.colorBlueTopRight *= f2;
/* 8105:     */       }
/* 8106:     */     }
/* 8107:9444 */     if (tex == TextureUtils.iconGrassSideSnowed) {
/* 8108:9446 */       tex = Config.getSideSnowGrassTexture(this.blockAccess, x, y, z, side);
/* 8109:     */     }
/* 8110:9449 */     return tex;
/* 8111:     */   }
/* 8112:     */   
/* 8113:     */   private boolean hasSnowNeighbours(int x, int y, int z)
/* 8114:     */   {
/* 8115:9454 */     Block blockSnow = Blocks.snow_layer;
/* 8116:9455 */     return (this.blockAccess.getBlock(x - 1, y, z) != blockSnow) && (this.blockAccess.getBlock(x + 1, y, z) != blockSnow) && (this.blockAccess.getBlock(x, y, z - 1) != blockSnow) && (this.blockAccess.getBlock(x, y, z + 1) != blockSnow) ? false : this.blockAccess.getBlock(x, y - 1, z).isOpaqueCube();
/* 8117:     */   }
/* 8118:     */   
/* 8119:     */   private void renderSnow(int x, int y, int z, double maxY)
/* 8120:     */   {
/* 8121:9460 */     if (this.betterSnowEnabled)
/* 8122:     */     {
/* 8123:9462 */       setRenderBoundsFromBlock(Blocks.snow_layer);
/* 8124:9463 */       this.renderMaxY = maxY;
/* 8125:9464 */       renderStandardBlock(Blocks.snow_layer, x, y, z);
/* 8126:     */     }
/* 8127:     */   }
/* 8128:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.RenderBlocks
 * JD-Core Version:    0.7.0.1
 */