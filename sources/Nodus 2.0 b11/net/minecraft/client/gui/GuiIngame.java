/*    1:     */ package net.minecraft.client.gui;
/*    2:     */ 
/*    3:     */ import java.awt.Color;
/*    4:     */ import java.util.Collection;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.Random;
/*    8:     */ import me.connorm.Nodus.ui.UIRenderer;
/*    9:     */ import net.minecraft.block.BlockPortal;
/*   10:     */ import net.minecraft.block.material.Material;
/*   11:     */ import net.minecraft.client.Minecraft;
/*   12:     */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   13:     */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*   14:     */ import net.minecraft.client.multiplayer.WorldClient;
/*   15:     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   16:     */ import net.minecraft.client.renderer.EntityRenderer;
/*   17:     */ import net.minecraft.client.renderer.OpenGlHelper;
/*   18:     */ import net.minecraft.client.renderer.RenderHelper;
/*   19:     */ import net.minecraft.client.renderer.Tessellator;
/*   20:     */ import net.minecraft.client.renderer.entity.RenderItem;
/*   21:     */ import net.minecraft.client.renderer.texture.TextureManager;
/*   22:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   23:     */ import net.minecraft.client.resources.I18n;
/*   24:     */ import net.minecraft.client.settings.GameSettings;
/*   25:     */ import net.minecraft.client.settings.KeyBinding;
/*   26:     */ import net.minecraft.client.shader.ShaderGroup;
/*   27:     */ import net.minecraft.entity.Entity;
/*   28:     */ import net.minecraft.entity.EntityLivingBase;
/*   29:     */ import net.minecraft.entity.SharedMonsterAttributes;
/*   30:     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*   31:     */ import net.minecraft.entity.boss.BossStatus;
/*   32:     */ import net.minecraft.entity.player.InventoryPlayer;
/*   33:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   34:     */ import net.minecraft.init.Blocks;
/*   35:     */ import net.minecraft.item.Item;
/*   36:     */ import net.minecraft.item.ItemStack;
/*   37:     */ import net.minecraft.potion.Potion;
/*   38:     */ import net.minecraft.profiler.Profiler;
/*   39:     */ import net.minecraft.scoreboard.Score;
/*   40:     */ import net.minecraft.scoreboard.ScoreObjective;
/*   41:     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*   42:     */ import net.minecraft.scoreboard.Scoreboard;
/*   43:     */ import net.minecraft.util.AxisAlignedBB;
/*   44:     */ import net.minecraft.util.EnumChatFormatting;
/*   45:     */ import net.minecraft.util.FoodStats;
/*   46:     */ import net.minecraft.util.IIcon;
/*   47:     */ import net.minecraft.util.MathHelper;
/*   48:     */ import net.minecraft.util.ResourceLocation;
/*   49:     */ import net.minecraft.util.StringUtils;
/*   50:     */ import net.minecraft.world.EnumSkyBlock;
/*   51:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   52:     */ import net.minecraft.world.chunk.Chunk;
/*   53:     */ import net.minecraft.world.storage.WorldInfo;
/*   54:     */ import org.lwjgl.opengl.GL11;
/*   55:     */ 
/*   56:     */ public class GuiIngame
/*   57:     */   extends Gui
/*   58:     */ {
/*   59:  48 */   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
/*   60:  49 */   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
/*   61:  50 */   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
/*   62:  51 */   private static final RenderItem itemRenderer = new RenderItem();
/*   63:  52 */   private final Random rand = new Random();
/*   64:     */   private final Minecraft mc;
/*   65:     */   public GuiNewChat persistantChatGUI;
/*   66:     */   private int updateCounter;
/*   67:  60 */   private String recordPlaying = "";
/*   68:     */   private int recordPlayingUpFor;
/*   69:     */   private boolean recordIsPlaying;
/*   70:  67 */   public float prevVignetteBrightness = 1.0F;
/*   71:     */   private int remainingHighlightTicks;
/*   72:     */   private ItemStack highlightingItemStack;
/*   73:     */   private static final String __OBFID = "CL_00000661";
/*   74:     */   
/*   75:     */   public GuiIngame(Minecraft par1Minecraft)
/*   76:     */   {
/*   77:  78 */     this.mc = par1Minecraft;
/*   78:  79 */     this.persistantChatGUI = new GuiNewChat(par1Minecraft);
/*   79:     */   }
/*   80:     */   
/*   81:     */   public void renderGameOverlay(float par1, boolean par2, int par3, int par4)
/*   82:     */   {
/*   83:  87 */     ScaledResolution var5 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*   84:  88 */     int var6 = var5.getScaledWidth();
/*   85:  89 */     int var7 = var5.getScaledHeight();
/*   86:  90 */     FontRenderer var8 = this.mc.fontRenderer;
/*   87:  91 */     this.mc.entityRenderer.setupOverlayRendering();
/*   88:  92 */     GL11.glEnable(3042);
/*   89:  94 */     if (Minecraft.isFancyGraphicsEnabled()) {
/*   90:  96 */       renderVignette(this.mc.thePlayer.getBrightness(par1), var6, var7);
/*   91:     */     } else {
/*   92: 100 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*   93:     */     }
/*   94: 103 */     ItemStack var9 = this.mc.thePlayer.inventory.armorItemInSlot(3);
/*   95: 105 */     if ((this.mc.gameSettings.thirdPersonView == 0) && (var9 != null) && (var9.getItem() == Item.getItemFromBlock(Blocks.pumpkin))) {
/*   96: 107 */       renderPumpkinBlur(var6, var7);
/*   97:     */     }
/*   98: 110 */     if (!this.mc.thePlayer.isPotionActive(Potion.confusion))
/*   99:     */     {
/*  100: 112 */       float var10 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * par1;
/*  101: 114 */       if (var10 > 0.0F) {
/*  102: 116 */         func_130015_b(var10, var6, var7);
/*  103:     */       }
/*  104:     */     }
/*  105: 124 */     if (!this.mc.playerController.enableEverythingIsScrewedUpMode())
/*  106:     */     {
/*  107: 126 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  108: 127 */       this.mc.getTextureManager().bindTexture(widgetsTexPath);
/*  109: 128 */       InventoryPlayer var31 = this.mc.thePlayer.inventory;
/*  110: 129 */       zLevel = -90.0F;
/*  111: 130 */       drawTexturedModalRect(var6 / 2 - 91, var7 - 22, 0, 0, 182, 22);
/*  112: 131 */       drawTexturedModalRect(var6 / 2 - 91 - 1 + var31.currentItem * 20, var7 - 22 - 1, 0, 22, 24, 22);
/*  113: 132 */       this.mc.getTextureManager().bindTexture(icons);
/*  114: 133 */       GL11.glEnable(3042);
/*  115: 134 */       OpenGlHelper.glBlendFunc(775, 769, 1, 0);
/*  116: 135 */       drawTexturedModalRect(var6 / 2 - 7, var7 / 2 - 7, 0, 0, 16, 16);
/*  117: 136 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  118: 137 */       this.mc.mcProfiler.startSection("bossHealth");
/*  119: 138 */       renderBossHealth();
/*  120: 139 */       this.mc.mcProfiler.endSection();
/*  121: 141 */       if (this.mc.playerController.shouldDrawHUD()) {
/*  122: 143 */         func_110327_a(var6, var7);
/*  123:     */       }
/*  124: 146 */       this.mc.mcProfiler.startSection("actionBar");
/*  125: 147 */       GL11.glEnable(32826);
/*  126: 148 */       RenderHelper.enableGUIStandardItemLighting();
/*  127: 150 */       for (int var11 = 0; var11 < 9; var11++)
/*  128:     */       {
/*  129: 152 */         int var12 = var6 / 2 - 90 + var11 * 20 + 2;
/*  130: 153 */         int var13 = var7 - 16 - 3;
/*  131: 154 */         renderInventorySlot(var11, var12, var13, par1);
/*  132:     */       }
/*  133: 157 */       RenderHelper.disableStandardItemLighting();
/*  134: 158 */       GL11.glDisable(32826);
/*  135: 159 */       this.mc.mcProfiler.endSection();
/*  136: 160 */       GL11.glDisable(3042);
/*  137:     */     }
/*  138: 165 */     if (this.mc.thePlayer.getSleepTimer() > 0)
/*  139:     */     {
/*  140: 167 */       this.mc.mcProfiler.startSection("sleep");
/*  141: 168 */       GL11.glDisable(2929);
/*  142: 169 */       GL11.glDisable(3008);
/*  143: 170 */       int var32 = this.mc.thePlayer.getSleepTimer();
/*  144: 171 */       float var34 = var32 / 100.0F;
/*  145: 173 */       if (var34 > 1.0F) {
/*  146: 175 */         var34 = 1.0F - (var32 - 100) / 10.0F;
/*  147:     */       }
/*  148: 178 */       int var12 = (int)(220.0F * var34) << 24 | 0x101020;
/*  149: 179 */       drawRect(0.0F, 0, var6, var7, var12);
/*  150: 180 */       GL11.glEnable(3008);
/*  151: 181 */       GL11.glEnable(2929);
/*  152: 182 */       this.mc.mcProfiler.endSection();
/*  153:     */     }
/*  154: 185 */     int var32 = 16777215;
/*  155: 186 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  156: 187 */     int var11 = var6 / 2 - 91;
/*  157: 195 */     if (this.mc.thePlayer.isRidingHorse())
/*  158:     */     {
/*  159: 197 */       this.mc.mcProfiler.startSection("jumpBar");
/*  160: 198 */       this.mc.getTextureManager().bindTexture(Gui.icons);
/*  161: 199 */       float var33 = this.mc.thePlayer.getHorseJumpPower();
/*  162: 200 */       short var37 = 182;
/*  163: 201 */       int var14 = (int)(var33 * (var37 + 1));
/*  164: 202 */       int var15 = var7 - 32 + 3;
/*  165: 203 */       drawTexturedModalRect(var11, var15, 0, 84, var37, 5);
/*  166: 205 */       if (var14 > 0) {
/*  167: 207 */         drawTexturedModalRect(var11, var15, 0, 89, var14, 5);
/*  168:     */       }
/*  169: 210 */       this.mc.mcProfiler.endSection();
/*  170:     */     }
/*  171: 212 */     else if (this.mc.playerController.gameIsSurvivalOrAdventure())
/*  172:     */     {
/*  173: 214 */       this.mc.mcProfiler.startSection("expBar");
/*  174: 215 */       this.mc.getTextureManager().bindTexture(Gui.icons);
/*  175: 216 */       int var12 = this.mc.thePlayer.xpBarCap();
/*  176: 218 */       if (var12 > 0)
/*  177:     */       {
/*  178: 220 */         short var37 = 182;
/*  179: 221 */         int var14 = (int)(this.mc.thePlayer.experience * (var37 + 1));
/*  180: 222 */         int var15 = var7 - 32 + 3;
/*  181: 223 */         drawTexturedModalRect(var11, var15, 0, 64, var37, 5);
/*  182: 225 */         if (var14 > 0) {
/*  183: 227 */           drawTexturedModalRect(var11, var15, 0, 69, var14, 5);
/*  184:     */         }
/*  185:     */       }
/*  186: 231 */       this.mc.mcProfiler.endSection();
/*  187: 233 */       if (this.mc.thePlayer.experienceLevel > 0)
/*  188:     */       {
/*  189: 235 */         this.mc.mcProfiler.startSection("expLevel");
/*  190: 236 */         boolean var35 = false;
/*  191: 237 */         int var14 = var35 ? 16777215 : 8453920;
/*  192: 238 */         String var42 = this.mc.thePlayer.experienceLevel;
/*  193: 239 */         int var16 = (var6 - var8.getStringWidth(var42)) / 2;
/*  194: 240 */         int var17 = var7 - 31 - 4;
/*  195: 241 */         boolean var18 = false;
/*  196: 242 */         var8.drawString(var42, var16 + 1, var17, 0);
/*  197: 243 */         var8.drawString(var42, var16 - 1, var17, 0);
/*  198: 244 */         var8.drawString(var42, var16, var17 + 1, 0);
/*  199: 245 */         var8.drawString(var42, var16, var17 - 1, 0);
/*  200: 246 */         var8.drawString(var42, var16, var17, var14);
/*  201: 247 */         this.mc.mcProfiler.endSection();
/*  202:     */       }
/*  203:     */     }
/*  204: 253 */     if (this.mc.gameSettings.heldItemTooltips)
/*  205:     */     {
/*  206: 255 */       this.mc.mcProfiler.startSection("toolHighlight");
/*  207: 257 */       if ((this.remainingHighlightTicks > 0) && (this.highlightingItemStack != null))
/*  208:     */       {
/*  209: 259 */         String var36 = this.highlightingItemStack.getDisplayName();
/*  210: 260 */         int var13 = (var6 - var8.getStringWidth(var36)) / 2;
/*  211: 261 */         int var14 = var7 - 59;
/*  212: 263 */         if (!this.mc.playerController.shouldDrawHUD()) {
/*  213: 265 */           var14 += 14;
/*  214:     */         }
/*  215: 268 */         int var15 = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/*  216: 270 */         if (var15 > 255) {
/*  217: 272 */           var15 = 255;
/*  218:     */         }
/*  219: 275 */         if (var15 > 0)
/*  220:     */         {
/*  221: 277 */           GL11.glPushMatrix();
/*  222: 278 */           GL11.glEnable(3042);
/*  223: 279 */           OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  224: 280 */           var8.drawStringWithShadow(var36, var13, var14, 16777215 + (var15 << 24));
/*  225: 281 */           GL11.glDisable(3042);
/*  226: 282 */           GL11.glPopMatrix();
/*  227:     */         }
/*  228:     */       }
/*  229: 286 */       this.mc.mcProfiler.endSection();
/*  230:     */     }
/*  231: 289 */     if (this.mc.isDemo())
/*  232:     */     {
/*  233: 291 */       this.mc.mcProfiler.startSection("demo");
/*  234: 292 */       String var36 = "";
/*  235: 294 */       if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
/*  236: 296 */         var36 = I18n.format("demo.demoExpired", new Object[0]);
/*  237:     */       } else {
/*  238: 300 */         var36 = I18n.format("demo.remainingTime", new Object[] { StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())) });
/*  239:     */       }
/*  240: 303 */       int var13 = var8.getStringWidth(var36);
/*  241: 304 */       var8.drawStringWithShadow(var36, var6 - var13 - 10, 5, 16777215);
/*  242: 305 */       this.mc.mcProfiler.endSection();
/*  243:     */     }
/*  244: 313 */     GL11.glPushMatrix();
/*  245: 314 */     UIRenderer.renderUI();
/*  246: 315 */     GL11.glPopMatrix();
/*  247: 317 */     if (this.mc.gameSettings.showDebugInfo)
/*  248:     */     {
/*  249: 319 */       this.mc.mcProfiler.startSection("debug");
/*  250: 320 */       GL11.glPushMatrix();
/*  251: 321 */       var8.drawStringWithShadow("Minecraft 1.7.2 (" + this.mc.debug + ")", 2, 2, 16777215);
/*  252: 322 */       var8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 12, 16777215);
/*  253: 323 */       var8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 22, 16777215);
/*  254: 324 */       var8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 32, 16777215);
/*  255: 325 */       var8.drawStringWithShadow(this.mc.getWorldProviderName(), 2, 42, 16777215);
/*  256: 326 */       long var38 = Runtime.getRuntime().maxMemory();
/*  257: 327 */       long var40 = Runtime.getRuntime().totalMemory();
/*  258: 328 */       long var39 = Runtime.getRuntime().freeMemory();
/*  259: 329 */       long var46 = var40 - var39;
/*  260: 330 */       String var20 = "Used memory: " + var46 * 100L / var38 + "% (" + var46 / 1024L / 1024L + "MB) of " + var38 / 1024L / 1024L + "MB";
/*  261: 331 */       int var21 = 14737632;
/*  262: 332 */       drawString(var8, var20, var6 - var8.getStringWidth(var20) - 2, 2, 14737632);
/*  263: 333 */       var20 = "Allocated memory: " + var40 * 100L / var38 + "% (" + var40 / 1024L / 1024L + "MB)";
/*  264: 334 */       drawString(var8, var20, var6 - var8.getStringWidth(var20) - 2, 12, 14737632);
/*  265: 335 */       int var22 = MathHelper.floor_double(this.mc.thePlayer.posX);
/*  266: 336 */       int var23 = MathHelper.floor_double(this.mc.thePlayer.posY);
/*  267: 337 */       int var24 = MathHelper.floor_double(this.mc.thePlayer.posZ);
/*  268: 338 */       drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[] { Double.valueOf(this.mc.thePlayer.posX), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 0xF) }), 2, 64, 14737632);
/*  269: 339 */       drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[] { Double.valueOf(this.mc.thePlayer.boundingBox.minY), Double.valueOf(this.mc.thePlayer.posY) }), 2, 72, 14737632);
/*  270: 340 */       drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[] { Double.valueOf(this.mc.thePlayer.posZ), Integer.valueOf(var24), Integer.valueOf(var24 >> 4), Integer.valueOf(var24 & 0xF) }), 2, 80, 14737632);
/*  271: 341 */       int var25 = MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
/*  272: 342 */       drawString(var8, "f: " + var25 + " (" + net.minecraft.util.Direction.directions[var25] + ") / " + MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw), 2, 88, 14737632);
/*  273: 344 */       if ((this.mc.theWorld != null) && (this.mc.theWorld.blockExists(var22, var23, var24)))
/*  274:     */       {
/*  275: 346 */         Chunk var26 = this.mc.theWorld.getChunkFromBlockCoords(var22, var24);
/*  276: 347 */         drawString(var8, "lc: " + (var26.getTopFilledSegment() + 15) + " b: " + var26.getBiomeGenForWorldCoords(var22 & 0xF, var24 & 0xF, this.mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var26.getSavedLightValue(EnumSkyBlock.Block, var22 & 0xF, var23, var24 & 0xF) + " sl: " + var26.getSavedLightValue(EnumSkyBlock.Sky, var22 & 0xF, var23, var24 & 0xF) + " rl: " + var26.getBlockLightValue(var22 & 0xF, var23, var24 & 0xF, 0), 2, 96, 14737632);
/*  277:     */       }
/*  278: 350 */       drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[] { Float.valueOf(this.mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(this.mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(this.mc.thePlayer.onGround), Integer.valueOf(this.mc.theWorld.getHeightValue(var22, var24)) }), 2, 104, 14737632);
/*  279: 352 */       if ((this.mc.entityRenderer != null) && (this.mc.entityRenderer.isShaderActive())) {
/*  280: 354 */         drawString(var8, String.format("shader: %s", new Object[] { this.mc.entityRenderer.getShaderGroup().getShaderGroupName() }), 2, 112, 14737632);
/*  281:     */       }
/*  282: 357 */       GL11.glPopMatrix();
/*  283: 358 */       this.mc.mcProfiler.endSection();
/*  284:     */     }
/*  285: 361 */     if (this.recordPlayingUpFor > 0)
/*  286:     */     {
/*  287: 363 */       this.mc.mcProfiler.startSection("overlayMessage");
/*  288: 364 */       float var33 = this.recordPlayingUpFor - par1;
/*  289: 365 */       int var13 = (int)(var33 * 255.0F / 20.0F);
/*  290: 367 */       if (var13 > 255) {
/*  291: 369 */         var13 = 255;
/*  292:     */       }
/*  293: 372 */       if (var13 > 8)
/*  294:     */       {
/*  295: 374 */         GL11.glPushMatrix();
/*  296: 375 */         GL11.glTranslatef(var6 / 2, var7 - 68, 0.0F);
/*  297: 376 */         GL11.glEnable(3042);
/*  298: 377 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  299: 378 */         int var14 = 16777215;
/*  300: 380 */         if (this.recordIsPlaying) {
/*  301: 382 */           var14 = Color.HSBtoRGB(var33 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*  302:     */         }
/*  303: 385 */         var8.drawString(this.recordPlaying, -var8.getStringWidth(this.recordPlaying) / 2, -4, var14 + (var13 << 24 & 0xFF000000));
/*  304: 386 */         GL11.glDisable(3042);
/*  305: 387 */         GL11.glPopMatrix();
/*  306:     */       }
/*  307: 390 */       this.mc.mcProfiler.endSection();
/*  308:     */     }
/*  309: 393 */     ScoreObjective var43 = this.mc.theWorld.getScoreboard().func_96539_a(1);
/*  310: 395 */     if (var43 != null) {
/*  311: 397 */       func_96136_a(var43, var7, var6, var8);
/*  312:     */     }
/*  313: 400 */     GL11.glEnable(3042);
/*  314: 401 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  315: 402 */     GL11.glDisable(3008);
/*  316: 403 */     GL11.glPushMatrix();
/*  317: 404 */     GL11.glTranslatef(0.0F, var7 - 48, 0.0F);
/*  318: 405 */     this.mc.mcProfiler.startSection("chat");
/*  319: 406 */     this.persistantChatGUI.func_146230_a(this.updateCounter);
/*  320: 407 */     this.mc.mcProfiler.endSection();
/*  321: 408 */     GL11.glPopMatrix();
/*  322:     */     
/*  323:     */ 
/*  324:     */ 
/*  325: 412 */     var43 = this.mc.theWorld.getScoreboard().func_96539_a(0);
/*  326: 414 */     if ((this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed()) && ((!this.mc.isIntegratedServerRunning()) || (this.mc.thePlayer.sendQueue.playerInfoList.size() > 1) || (var43 != null)))
/*  327:     */     {
/*  328: 416 */       this.mc.mcProfiler.startSection("playerList");
/*  329: 417 */       NetHandlerPlayClient var41 = this.mc.thePlayer.sendQueue;
/*  330: 418 */       List var44 = var41.playerInfoList;
/*  331: 419 */       int var15 = var41.currentServerMaxPlayers;
/*  332: 420 */       int var16 = var15;
/*  333: 422 */       for (int var17 = 1; var16 > 20; var16 = (var15 + var17 - 1) / var17) {
/*  334: 424 */         var17++;
/*  335:     */       }
/*  336: 427 */       int var45 = 300 / var17;
/*  337: 429 */       if (var45 > 150) {
/*  338: 431 */         var45 = 150;
/*  339:     */       }
/*  340: 434 */       int var19 = (var6 - var17 * var45) / 2;
/*  341: 435 */       byte var47 = 10;
/*  342: 436 */       drawRect(var19 - 1, var47 - 1, var19 + var45 * var17, var47 + 9 * var16, -2147483648);
/*  343: 438 */       for (int var21 = 0; var21 < var15; var21++)
/*  344:     */       {
/*  345: 440 */         int var22 = var19 + var21 % var17 * var45;
/*  346: 441 */         int var23 = var47 + var21 / var17 * 9;
/*  347: 442 */         drawRect(var22, var23, var22 + var45 - 1, var23 + 8, 553648127);
/*  348: 443 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  349: 444 */         GL11.glEnable(3008);
/*  350: 446 */         if (var21 < var44.size())
/*  351:     */         {
/*  352: 448 */           GuiPlayerInfo var49 = (GuiPlayerInfo)var44.get(var21);
/*  353: 449 */           ScorePlayerTeam var48 = this.mc.theWorld.getScoreboard().getPlayersTeam(var49.name);
/*  354: 450 */           String var52 = ScorePlayerTeam.formatPlayerName(var48, var49.name);
/*  355: 451 */           var8.drawStringWithShadow(var52, var22, var23, 16777215);
/*  356: 453 */           if (var43 != null)
/*  357:     */           {
/*  358: 455 */             int var27 = var22 + var8.getStringWidth(var52) + 5;
/*  359: 456 */             int var28 = var22 + var45 - 12 - 5;
/*  360: 458 */             if (var28 - var27 > 5)
/*  361:     */             {
/*  362: 460 */               Score var29 = var43.getScoreboard().func_96529_a(var49.name, var43);
/*  363: 461 */               String var30 = EnumChatFormatting.YELLOW + var29.getScorePoints();
/*  364: 462 */               var8.drawStringWithShadow(var30, var28 - var8.getStringWidth(var30), var23, 16777215);
/*  365:     */             }
/*  366:     */           }
/*  367: 466 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  368: 467 */           this.mc.getTextureManager().bindTexture(icons);
/*  369: 468 */           byte var53 = 0;
/*  370: 469 */           boolean var51 = false;
/*  371:     */           byte var50;
/*  372:     */           byte var50;
/*  373: 472 */           if (var49.responseTime < 0)
/*  374:     */           {
/*  375: 474 */             var50 = 5;
/*  376:     */           }
/*  377:     */           else
/*  378:     */           {
/*  379:     */             byte var50;
/*  380: 476 */             if (var49.responseTime < 150)
/*  381:     */             {
/*  382: 478 */               var50 = 0;
/*  383:     */             }
/*  384:     */             else
/*  385:     */             {
/*  386:     */               byte var50;
/*  387: 480 */               if (var49.responseTime < 300)
/*  388:     */               {
/*  389: 482 */                 var50 = 1;
/*  390:     */               }
/*  391:     */               else
/*  392:     */               {
/*  393:     */                 byte var50;
/*  394: 484 */                 if (var49.responseTime < 600)
/*  395:     */                 {
/*  396: 486 */                   var50 = 2;
/*  397:     */                 }
/*  398:     */                 else
/*  399:     */                 {
/*  400:     */                   byte var50;
/*  401: 488 */                   if (var49.responseTime < 1000) {
/*  402: 490 */                     var50 = 3;
/*  403:     */                   } else {
/*  404: 494 */                     var50 = 4;
/*  405:     */                   }
/*  406:     */                 }
/*  407:     */               }
/*  408:     */             }
/*  409:     */           }
/*  410: 497 */           zLevel += 100.0F;
/*  411: 498 */           drawTexturedModalRect(var22 + var45 - 12, var23, 0 + var53 * 10, 176 + var50 * 8, 10, 8);
/*  412: 499 */           zLevel -= 100.0F;
/*  413:     */         }
/*  414:     */       }
/*  415:     */     }
/*  416: 504 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  417: 505 */     GL11.glDisable(2896);
/*  418: 506 */     GL11.glEnable(3008);
/*  419:     */   }
/*  420:     */   
/*  421:     */   private void func_96136_a(ScoreObjective par1ScoreObjective, int par2, int par3, FontRenderer par4FontRenderer)
/*  422:     */   {
/*  423: 511 */     Scoreboard var5 = par1ScoreObjective.getScoreboard();
/*  424: 512 */     Collection var6 = var5.func_96534_i(par1ScoreObjective);
/*  425: 514 */     if (var6.size() <= 15)
/*  426:     */     {
/*  427: 516 */       int var7 = par4FontRenderer.getStringWidth(par1ScoreObjective.getDisplayName());
/*  428:     */       String var11;
/*  429: 519 */       for (Iterator var8 = var6.iterator(); var8.hasNext(); var7 = Math.max(var7, par4FontRenderer.getStringWidth(var11)))
/*  430:     */       {
/*  431: 521 */         Score var9 = (Score)var8.next();
/*  432: 522 */         ScorePlayerTeam var10 = var5.getPlayersTeam(var9.getPlayerName());
/*  433: 523 */         var11 = ScorePlayerTeam.formatPlayerName(var10, var9.getPlayerName()) + ": " + EnumChatFormatting.RED + var9.getScorePoints();
/*  434:     */       }
/*  435: 526 */       int var22 = var6.size() * par4FontRenderer.FONT_HEIGHT;
/*  436: 527 */       int var23 = par2 / 2 + var22 / 3;
/*  437: 528 */       byte var25 = 3;
/*  438: 529 */       int var24 = par3 - var7 - var25;
/*  439: 530 */       int var12 = 0;
/*  440: 531 */       Iterator var13 = var6.iterator();
/*  441: 533 */       while (var13.hasNext())
/*  442:     */       {
/*  443: 535 */         Score var14 = (Score)var13.next();
/*  444: 536 */         var12++;
/*  445: 537 */         ScorePlayerTeam var15 = var5.getPlayersTeam(var14.getPlayerName());
/*  446: 538 */         String var16 = ScorePlayerTeam.formatPlayerName(var15, var14.getPlayerName());
/*  447: 539 */         String var17 = EnumChatFormatting.RED + var14.getScorePoints();
/*  448: 540 */         int var19 = var23 - var12 * par4FontRenderer.FONT_HEIGHT;
/*  449: 541 */         int var20 = par3 - var25 + 2;
/*  450: 542 */         drawRect(var24 - 2, var19, var20, var19 + par4FontRenderer.FONT_HEIGHT, 1342177280);
/*  451: 543 */         par4FontRenderer.drawString(var16, var24, var19, 553648127);
/*  452: 544 */         par4FontRenderer.drawString(var17, var20 - par4FontRenderer.getStringWidth(var17), var19, 553648127);
/*  453: 546 */         if (var12 == var6.size())
/*  454:     */         {
/*  455: 548 */           String var21 = par1ScoreObjective.getDisplayName();
/*  456: 549 */           drawRect(var24 - 2, var19 - par4FontRenderer.FONT_HEIGHT - 1, var20, var19 - 1, 1610612736);
/*  457: 550 */           drawRect(var24 - 2, var19 - 1, var20, var19, 1342177280);
/*  458: 551 */           par4FontRenderer.drawString(var21, var24 + var7 / 2 - par4FontRenderer.getStringWidth(var21) / 2, var19 - par4FontRenderer.FONT_HEIGHT, 553648127);
/*  459:     */         }
/*  460:     */       }
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   private void func_110327_a(int par1, int par2)
/*  465:     */   {
/*  466: 559 */     boolean var3 = this.mc.thePlayer.hurtResistantTime / 3 % 2 == 1;
/*  467: 561 */     if (this.mc.thePlayer.hurtResistantTime < 10) {
/*  468: 563 */       var3 = false;
/*  469:     */     }
/*  470: 566 */     int var4 = MathHelper.ceiling_float_int(this.mc.thePlayer.getHealth());
/*  471: 567 */     int var5 = MathHelper.ceiling_float_int(this.mc.thePlayer.prevHealth);
/*  472: 568 */     this.rand.setSeed(this.updateCounter * 312871);
/*  473: 569 */     boolean var6 = false;
/*  474: 570 */     FoodStats var7 = this.mc.thePlayer.getFoodStats();
/*  475: 571 */     int var8 = var7.getFoodLevel();
/*  476: 572 */     int var9 = var7.getPrevFoodLevel();
/*  477: 573 */     IAttributeInstance var10 = this.mc.thePlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/*  478: 574 */     int var11 = par1 / 2 - 91;
/*  479: 575 */     int var12 = par1 / 2 + 91;
/*  480: 576 */     int var13 = par2 - 39;
/*  481: 577 */     float var14 = (float)var10.getAttributeValue();
/*  482: 578 */     float var15 = this.mc.thePlayer.getAbsorptionAmount();
/*  483: 579 */     int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F / 10.0F);
/*  484: 580 */     int var17 = Math.max(10 - (var16 - 2), 3);
/*  485: 581 */     int var18 = var13 - (var16 - 1) * var17 - 10;
/*  486: 582 */     float var19 = var15;
/*  487: 583 */     int var20 = this.mc.thePlayer.getTotalArmorValue();
/*  488: 584 */     int var21 = -1;
/*  489: 586 */     if (this.mc.thePlayer.isPotionActive(Potion.regeneration)) {
/*  490: 588 */       var21 = this.updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
/*  491:     */     }
/*  492: 591 */     this.mc.mcProfiler.startSection("armor");
/*  493: 595 */     for (int var22 = 0; var22 < 10; var22++) {
/*  494: 597 */       if (var20 > 0)
/*  495:     */       {
/*  496: 599 */         int var23 = var11 + var22 * 8;
/*  497: 601 */         if (var22 * 2 + 1 < var20) {
/*  498: 603 */           drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
/*  499:     */         }
/*  500: 606 */         if (var22 * 2 + 1 == var20) {
/*  501: 608 */           drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
/*  502:     */         }
/*  503: 611 */         if (var22 * 2 + 1 > var20) {
/*  504: 613 */           drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
/*  505:     */         }
/*  506:     */       }
/*  507:     */     }
/*  508: 618 */     this.mc.mcProfiler.endStartSection("health");
/*  509: 623 */     for (var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1; var22 >= 0; var22--)
/*  510:     */     {
/*  511: 625 */       int var23 = 16;
/*  512: 627 */       if (this.mc.thePlayer.isPotionActive(Potion.poison)) {
/*  513: 629 */         var23 += 36;
/*  514: 631 */       } else if (this.mc.thePlayer.isPotionActive(Potion.wither)) {
/*  515: 633 */         var23 += 72;
/*  516:     */       }
/*  517: 636 */       byte var24 = 0;
/*  518: 638 */       if (var3) {
/*  519: 640 */         var24 = 1;
/*  520:     */       }
/*  521: 643 */       int var25 = MathHelper.ceiling_float_int((var22 + 1) / 10.0F) - 1;
/*  522: 644 */       int var26 = var11 + var22 % 10 * 8;
/*  523: 645 */       int var27 = var13 - var25 * var17;
/*  524: 647 */       if (var4 <= 4) {
/*  525: 649 */         var27 += this.rand.nextInt(2);
/*  526:     */       }
/*  527: 652 */       if (var22 == var21) {
/*  528: 654 */         var27 -= 2;
/*  529:     */       }
/*  530: 657 */       byte var28 = 0;
/*  531: 659 */       if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*  532: 661 */         var28 = 5;
/*  533:     */       }
/*  534: 664 */       drawTexturedModalRect(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
/*  535: 666 */       if (var3)
/*  536:     */       {
/*  537: 668 */         if (var22 * 2 + 1 < var5) {
/*  538: 670 */           drawTexturedModalRect(var26, var27, var23 + 54, 9 * var28, 9, 9);
/*  539:     */         }
/*  540: 673 */         if (var22 * 2 + 1 == var5) {
/*  541: 675 */           drawTexturedModalRect(var26, var27, var23 + 63, 9 * var28, 9, 9);
/*  542:     */         }
/*  543:     */       }
/*  544: 679 */       if (var19 > 0.0F)
/*  545:     */       {
/*  546: 681 */         if ((var19 == var15) && (var15 % 2.0F == 1.0F)) {
/*  547: 683 */           drawTexturedModalRect(var26, var27, var23 + 153, 9 * var28, 9, 9);
/*  548:     */         } else {
/*  549: 687 */           drawTexturedModalRect(var26, var27, var23 + 144, 9 * var28, 9, 9);
/*  550:     */         }
/*  551: 690 */         var19 -= 2.0F;
/*  552:     */       }
/*  553:     */       else
/*  554:     */       {
/*  555: 694 */         if (var22 * 2 + 1 < var4) {
/*  556: 696 */           drawTexturedModalRect(var26, var27, var23 + 36, 9 * var28, 9, 9);
/*  557:     */         }
/*  558: 699 */         if (var22 * 2 + 1 == var4) {
/*  559: 701 */           drawTexturedModalRect(var26, var27, var23 + 45, 9 * var28, 9, 9);
/*  560:     */         }
/*  561:     */       }
/*  562:     */     }
/*  563: 706 */     Entity var34 = this.mc.thePlayer.ridingEntity;
/*  564: 709 */     if (var34 == null)
/*  565:     */     {
/*  566: 711 */       this.mc.mcProfiler.endStartSection("food");
/*  567: 713 */       for (int var23 = 0; var23 < 10; var23++)
/*  568:     */       {
/*  569: 715 */         int var35 = var13;
/*  570: 716 */         int var25 = 16;
/*  571: 717 */         byte var36 = 0;
/*  572: 719 */         if (this.mc.thePlayer.isPotionActive(Potion.hunger))
/*  573:     */         {
/*  574: 721 */           var25 += 36;
/*  575: 722 */           var36 = 13;
/*  576:     */         }
/*  577: 725 */         if ((this.mc.thePlayer.getFoodStats().getSaturationLevel() <= 0.0F) && (this.updateCounter % (var8 * 3 + 1) == 0)) {
/*  578: 727 */           var35 = var13 + (this.rand.nextInt(3) - 1);
/*  579:     */         }
/*  580: 730 */         if (var6) {
/*  581: 732 */           var36 = 1;
/*  582:     */         }
/*  583: 735 */         int var27 = var12 - var23 * 8 - 9;
/*  584: 736 */         drawTexturedModalRect(var27, var35, 16 + var36 * 9, 27, 9, 9);
/*  585: 738 */         if (var6)
/*  586:     */         {
/*  587: 740 */           if (var23 * 2 + 1 < var9) {
/*  588: 742 */             drawTexturedModalRect(var27, var35, var25 + 54, 27, 9, 9);
/*  589:     */           }
/*  590: 745 */           if (var23 * 2 + 1 == var9) {
/*  591: 747 */             drawTexturedModalRect(var27, var35, var25 + 63, 27, 9, 9);
/*  592:     */           }
/*  593:     */         }
/*  594: 751 */         if (var23 * 2 + 1 < var8) {
/*  595: 753 */           drawTexturedModalRect(var27, var35, var25 + 36, 27, 9, 9);
/*  596:     */         }
/*  597: 756 */         if (var23 * 2 + 1 == var8) {
/*  598: 758 */           drawTexturedModalRect(var27, var35, var25 + 45, 27, 9, 9);
/*  599:     */         }
/*  600:     */       }
/*  601:     */     }
/*  602: 762 */     else if ((var34 instanceof EntityLivingBase))
/*  603:     */     {
/*  604: 764 */       this.mc.mcProfiler.endStartSection("mountHealth");
/*  605: 765 */       EntityLivingBase var38 = (EntityLivingBase)var34;
/*  606: 766 */       int var35 = (int)Math.ceil(var38.getHealth());
/*  607: 767 */       float var37 = var38.getMaxHealth();
/*  608: 768 */       int var26 = (int)(var37 + 0.5F) / 2;
/*  609: 770 */       if (var26 > 30) {
/*  610: 772 */         var26 = 30;
/*  611:     */       }
/*  612: 775 */       int var27 = var13;
/*  613: 777 */       for (int var39 = 0; var26 > 0; var39 += 20)
/*  614:     */       {
/*  615: 779 */         int var29 = Math.min(var26, 10);
/*  616: 780 */         var26 -= var29;
/*  617: 782 */         for (int var30 = 0; var30 < var29; var30++)
/*  618:     */         {
/*  619: 784 */           byte var31 = 52;
/*  620: 785 */           byte var32 = 0;
/*  621: 787 */           if (var6) {
/*  622: 789 */             var32 = 1;
/*  623:     */           }
/*  624: 792 */           int var33 = var12 - var30 * 8 - 9;
/*  625: 793 */           drawTexturedModalRect(var33, var27, var31 + var32 * 9, 9, 9, 9);
/*  626: 795 */           if (var30 * 2 + 1 + var39 < var35) {
/*  627: 797 */             drawTexturedModalRect(var33, var27, var31 + 36, 9, 9, 9);
/*  628:     */           }
/*  629: 800 */           if (var30 * 2 + 1 + var39 == var35) {
/*  630: 802 */             drawTexturedModalRect(var33, var27, var31 + 45, 9, 9, 9);
/*  631:     */           }
/*  632:     */         }
/*  633: 806 */         var27 -= 10;
/*  634:     */       }
/*  635:     */     }
/*  636: 810 */     this.mc.mcProfiler.endStartSection("air");
/*  637: 812 */     if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
/*  638:     */     {
/*  639: 814 */       int var23 = this.mc.thePlayer.getAir();
/*  640: 815 */       int var35 = MathHelper.ceiling_double_int((var23 - 2) * 10.0D / 300.0D);
/*  641: 816 */       int var25 = MathHelper.ceiling_double_int(var23 * 10.0D / 300.0D) - var35;
/*  642: 818 */       for (int var26 = 0; var26 < var35 + var25; var26++) {
/*  643: 820 */         if (var26 < var35) {
/*  644: 822 */           drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 16, 18, 9, 9);
/*  645:     */         } else {
/*  646: 826 */           drawTexturedModalRect(var12 - var26 * 8 - 9, var18, 25, 18, 9, 9);
/*  647:     */         }
/*  648:     */       }
/*  649:     */     }
/*  650: 831 */     this.mc.mcProfiler.endSection();
/*  651:     */   }
/*  652:     */   
/*  653:     */   private void renderBossHealth()
/*  654:     */   {
/*  655: 839 */     if ((BossStatus.bossName != null) && (BossStatus.statusBarTime > 0))
/*  656:     */     {
/*  657: 841 */       BossStatus.statusBarTime -= 1;
/*  658: 842 */       FontRenderer var1 = this.mc.fontRenderer;
/*  659: 843 */       ScaledResolution var2 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  660: 844 */       int var3 = var2.getScaledWidth();
/*  661: 845 */       short var4 = 182;
/*  662: 846 */       int var5 = var3 / 2 - var4 / 2;
/*  663: 847 */       int var6 = (int)(BossStatus.healthScale * (var4 + 1));
/*  664: 848 */       byte var7 = 12;
/*  665: 849 */       drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
/*  666: 850 */       drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
/*  667: 852 */       if (var6 > 0) {
/*  668: 854 */         drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
/*  669:     */       }
/*  670: 857 */       String var8 = BossStatus.bossName;
/*  671: 858 */       var1.drawStringWithShadow(var8, var3 / 2 - var1.getStringWidth(var8) / 2, var7 - 10, 16777215);
/*  672: 859 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  673: 860 */       this.mc.getTextureManager().bindTexture(icons);
/*  674:     */     }
/*  675:     */   }
/*  676:     */   
/*  677:     */   private void renderPumpkinBlur(int par1, int par2)
/*  678:     */   {
/*  679: 866 */     GL11.glDisable(2929);
/*  680: 867 */     GL11.glDepthMask(false);
/*  681: 868 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  682: 869 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  683: 870 */     GL11.glDisable(3008);
/*  684: 871 */     this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
/*  685: 872 */     Tessellator var3 = Tessellator.instance;
/*  686: 873 */     var3.startDrawingQuads();
/*  687: 874 */     var3.addVertexWithUV(0.0D, par2, -90.0D, 0.0D, 1.0D);
/*  688: 875 */     var3.addVertexWithUV(par1, par2, -90.0D, 1.0D, 1.0D);
/*  689: 876 */     var3.addVertexWithUV(par1, 0.0D, -90.0D, 1.0D, 0.0D);
/*  690: 877 */     var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
/*  691: 878 */     var3.draw();
/*  692: 879 */     GL11.glDepthMask(true);
/*  693: 880 */     GL11.glEnable(2929);
/*  694: 881 */     GL11.glEnable(3008);
/*  695: 882 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  696:     */   }
/*  697:     */   
/*  698:     */   private void renderVignette(float par1, int par2, int par3)
/*  699:     */   {
/*  700: 890 */     par1 = 1.0F - par1;
/*  701: 892 */     if (par1 < 0.0F) {
/*  702: 894 */       par1 = 0.0F;
/*  703:     */     }
/*  704: 897 */     if (par1 > 1.0F) {
/*  705: 899 */       par1 = 1.0F;
/*  706:     */     }
/*  707: 902 */     this.prevVignetteBrightness = ((float)(this.prevVignetteBrightness + (par1 - this.prevVignetteBrightness) * 0.01D));
/*  708: 903 */     GL11.glDisable(2929);
/*  709: 904 */     GL11.glDepthMask(false);
/*  710: 905 */     OpenGlHelper.glBlendFunc(0, 769, 1, 0);
/*  711: 906 */     GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
/*  712: 907 */     this.mc.getTextureManager().bindTexture(vignetteTexPath);
/*  713: 908 */     Tessellator var4 = Tessellator.instance;
/*  714: 909 */     var4.startDrawingQuads();
/*  715: 910 */     var4.addVertexWithUV(0.0D, par3, -90.0D, 0.0D, 1.0D);
/*  716: 911 */     var4.addVertexWithUV(par2, par3, -90.0D, 1.0D, 1.0D);
/*  717: 912 */     var4.addVertexWithUV(par2, 0.0D, -90.0D, 1.0D, 0.0D);
/*  718: 913 */     var4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
/*  719: 914 */     var4.draw();
/*  720: 915 */     GL11.glDepthMask(true);
/*  721: 916 */     GL11.glEnable(2929);
/*  722: 917 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  723: 918 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  724:     */   }
/*  725:     */   
/*  726:     */   private void func_130015_b(float par1, int par2, int par3)
/*  727:     */   {
/*  728: 923 */     if (par1 < 1.0F)
/*  729:     */     {
/*  730: 925 */       par1 *= par1;
/*  731: 926 */       par1 *= par1;
/*  732: 927 */       par1 = par1 * 0.8F + 0.2F;
/*  733:     */     }
/*  734: 930 */     GL11.glDisable(3008);
/*  735: 931 */     GL11.glDisable(2929);
/*  736: 932 */     GL11.glDepthMask(false);
/*  737: 933 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  738: 934 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, par1);
/*  739: 935 */     IIcon var4 = Blocks.portal.getBlockTextureFromSide(1);
/*  740: 936 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*  741: 937 */     float var5 = var4.getMinU();
/*  742: 938 */     float var6 = var4.getMinV();
/*  743: 939 */     float var7 = var4.getMaxU();
/*  744: 940 */     float var8 = var4.getMaxV();
/*  745: 941 */     Tessellator var9 = Tessellator.instance;
/*  746: 942 */     var9.startDrawingQuads();
/*  747: 943 */     var9.addVertexWithUV(0.0D, par3, -90.0D, var5, var8);
/*  748: 944 */     var9.addVertexWithUV(par2, par3, -90.0D, var7, var8);
/*  749: 945 */     var9.addVertexWithUV(par2, 0.0D, -90.0D, var7, var6);
/*  750: 946 */     var9.addVertexWithUV(0.0D, 0.0D, -90.0D, var5, var6);
/*  751: 947 */     var9.draw();
/*  752: 948 */     GL11.glDepthMask(true);
/*  753: 949 */     GL11.glEnable(2929);
/*  754: 950 */     GL11.glEnable(3008);
/*  755: 951 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  756:     */   }
/*  757:     */   
/*  758:     */   private void renderInventorySlot(int par1, int par2, int par3, float par4)
/*  759:     */   {
/*  760: 959 */     ItemStack var5 = this.mc.thePlayer.inventory.mainInventory[par1];
/*  761: 961 */     if (var5 != null)
/*  762:     */     {
/*  763: 963 */       float var6 = var5.animationsToGo - par4;
/*  764: 965 */       if (var6 > 0.0F)
/*  765:     */       {
/*  766: 967 */         GL11.glPushMatrix();
/*  767: 968 */         float var7 = 1.0F + var6 / 5.0F;
/*  768: 969 */         GL11.glTranslatef(par2 + 8, par3 + 12, 0.0F);
/*  769: 970 */         GL11.glScalef(1.0F / var7, (var7 + 1.0F) / 2.0F, 1.0F);
/*  770: 971 */         GL11.glTranslatef(-(par2 + 8), -(par3 + 12), 0.0F);
/*  771:     */       }
/*  772: 974 */       itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var5, par2, par3);
/*  773: 976 */       if (var6 > 0.0F) {
/*  774: 978 */         GL11.glPopMatrix();
/*  775:     */       }
/*  776: 981 */       itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var5, par2, par3);
/*  777:     */     }
/*  778:     */   }
/*  779:     */   
/*  780:     */   public void updateTick()
/*  781:     */   {
/*  782: 990 */     if (this.recordPlayingUpFor > 0) {
/*  783: 992 */       this.recordPlayingUpFor -= 1;
/*  784:     */     }
/*  785: 995 */     this.updateCounter += 1;
/*  786: 997 */     if (this.mc.thePlayer != null)
/*  787:     */     {
/*  788: 999 */       ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();
/*  789:1001 */       if (var1 == null) {
/*  790:1003 */         this.remainingHighlightTicks = 0;
/*  791:1005 */       } else if ((this.highlightingItemStack != null) && (var1.getItem() == this.highlightingItemStack.getItem()) && (ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack)) && ((var1.isItemStackDamageable()) || (var1.getItemDamage() == this.highlightingItemStack.getItemDamage())))
/*  792:     */       {
/*  793:1007 */         if (this.remainingHighlightTicks > 0) {
/*  794:1009 */           this.remainingHighlightTicks -= 1;
/*  795:     */         }
/*  796:     */       }
/*  797:     */       else {
/*  798:1014 */         this.remainingHighlightTicks = 40;
/*  799:     */       }
/*  800:1017 */       this.highlightingItemStack = var1;
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void setRecordPlayingMessage(String par1Str)
/*  805:     */   {
/*  806:1023 */     func_110326_a("Now playing: " + par1Str, true);
/*  807:     */   }
/*  808:     */   
/*  809:     */   public void func_110326_a(String par1Str, boolean par2)
/*  810:     */   {
/*  811:1028 */     this.recordPlaying = par1Str;
/*  812:1029 */     this.recordPlayingUpFor = 60;
/*  813:1030 */     this.recordIsPlaying = par2;
/*  814:     */   }
/*  815:     */   
/*  816:     */   public GuiNewChat getChatGUI()
/*  817:     */   {
/*  818:1035 */     return this.persistantChatGUI;
/*  819:     */   }
/*  820:     */   
/*  821:     */   public int getUpdateCounter()
/*  822:     */   {
/*  823:1040 */     return this.updateCounter;
/*  824:     */   }
/*  825:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiIngame
 * JD-Core Version:    0.7.0.1
 */