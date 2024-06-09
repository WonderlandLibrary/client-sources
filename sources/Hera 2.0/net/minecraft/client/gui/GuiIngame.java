/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.awt.Color;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Random;
/*      */ import me.eagler.Client;
/*      */ import me.eagler.font.FontHelper;
/*      */ import me.eagler.module.Module;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.boss.BossStatus;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import optfine.Config;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class GuiIngame
/*      */   extends Gui
/*      */ {
/*   62 */   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
/*   63 */   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
/*   64 */   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
/*   65 */   private final Random rand = new Random();
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final RenderItem itemRenderer;
/*      */   
/*      */   private final GuiNewChat persistantChatGUI;
/*      */   
/*      */   private final GuiStreamIndicator streamIndicator;
/*      */   private int updateCounter;
/*   75 */   private String recordPlaying = "";
/*      */ 
/*      */   
/*      */   private int recordPlayingUpFor;
/*      */   
/*      */   private boolean recordIsPlaying;
/*      */   
/*   82 */   public float prevVignetteBrightness = 1.0F;
/*      */   
/*      */   private int remainingHighlightTicks;
/*      */   
/*      */   private ItemStack highlightingItemStack;
/*      */   
/*      */   private final GuiOverlayDebug overlayDebug;
/*      */   
/*      */   private final GuiSpectator spectatorGui;
/*      */   
/*      */   private final GuiPlayerTabOverlay overlayPlayerList;
/*      */   
/*      */   private int field_175195_w;
/*   95 */   private String field_175201_x = "";
/*   96 */   private String field_175200_y = "";
/*      */   private int field_175199_z;
/*      */   private int field_175192_A;
/*      */   private int field_175193_B;
/*  100 */   private int playerHealth = 0;
/*  101 */   private int lastPlayerHealth = 0;
/*      */ 
/*      */   
/*  104 */   private long lastSystemTime = 0L;
/*      */ 
/*      */   
/*  107 */   private long healthUpdateCounter = 0L;
/*      */ 
/*      */   
/*      */   private static final String __OBFID = "CL_00000661";
/*      */ 
/*      */   
/*      */   private ArrayList<Module> activemodules;
/*      */ 
/*      */   
/*      */   float oppacity;
/*      */   
/*      */   int blocks;
/*      */ 
/*      */   
/*      */   public void func_175177_a() {
/*  122 */     this.field_175199_z = 10;
/*  123 */     this.field_175192_A = 70;
/*  124 */     this.field_175193_B = 20;
/*      */   }
/*      */   
/*      */   public void renderGameOverlay(float partialTicks) {
/*  128 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  129 */     int i = scaledresolution.getScaledWidth();
/*  130 */     int j = scaledresolution.getScaledHeight();
/*  131 */     this.mc.entityRenderer.setupOverlayRendering();
/*  132 */     GlStateManager.enableBlend();
/*      */     
/*  134 */     if (Config.isVignetteEnabled()) {
/*  135 */       renderVignette(this.mc.thePlayer.getBrightness(partialTicks), scaledresolution);
/*      */     } else {
/*  137 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */     
/*  140 */     ItemStack itemstack = this.mc.thePlayer.inventory.armorItemInSlot(3);
/*      */     
/*  142 */     if (this.mc.gameSettings.thirdPersonView == 0 && itemstack != null && 
/*  143 */       itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
/*  144 */       renderPumpkinOverlay(scaledresolution);
/*      */     }
/*      */     
/*  147 */     if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
/*  148 */       float f = this.mc.thePlayer.prevTimeInPortal + (
/*  149 */         this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * partialTicks;
/*      */       
/*  151 */       if (f > 0.0F) {
/*  152 */         func_180474_b(f, scaledresolution);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  161 */     renderTooltip(scaledresolution, partialTicks);
/*      */ 
/*      */     
/*  164 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  165 */     this.mc.getTextureManager().bindTexture(icons);
/*  166 */     GlStateManager.enableBlend();
/*      */     
/*  168 */     if (showCrosshair()) {
/*  169 */       GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
/*  170 */       GlStateManager.enableAlpha();
/*  171 */       drawTexturedModalRect(i / 2 - 7, j / 2 - 7, 0, 0, 16, 16);
/*      */     } 
/*      */     
/*  174 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  175 */     this.mc.mcProfiler.startSection("bossHealth");
/*  176 */     renderBossHealth();
/*  177 */     this.mc.mcProfiler.endSection();
/*      */     
/*  179 */     if (this.mc.playerController.shouldDrawHUD()) {
/*  180 */       renderPlayerStats(scaledresolution);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  185 */     renderLogo();
/*  186 */     renderArraylist();
/*  187 */     renderBlocks(i, j);
/*  188 */     otherThings();
/*  189 */     renderHutcam();
/*      */     
/*  191 */     GlStateManager.disableBlend();
/*      */     
/*  193 */     if (this.mc.thePlayer.getSleepTimer() > 0) {
/*  194 */       this.mc.mcProfiler.startSection("sleep");
/*  195 */       GlStateManager.disableDepth();
/*  196 */       GlStateManager.disableAlpha();
/*  197 */       int l = this.mc.thePlayer.getSleepTimer();
/*  198 */       float f2 = l / 100.0F;
/*      */       
/*  200 */       if (f2 > 1.0F) {
/*  201 */         f2 = 1.0F - (l - 100) / 10.0F;
/*      */       }
/*      */       
/*  204 */       int k = (int)(220.0F * f2) << 24 | 0x101020;
/*  205 */       drawRect(0, 0, i, j, k);
/*  206 */       GlStateManager.enableAlpha();
/*  207 */       GlStateManager.enableDepth();
/*  208 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  211 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  212 */     int i2 = i / 2 - 91;
/*      */     
/*  214 */     if (this.mc.thePlayer.isRidingHorse()) {
/*  215 */       renderHorseJumpBar(scaledresolution, i2);
/*  216 */     } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
/*  217 */       renderExpBar(scaledresolution, i2);
/*      */     } 
/*      */     
/*  220 */     if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
/*  221 */       func_181551_a(scaledresolution);
/*  222 */     } else if (this.mc.thePlayer.isSpectator()) {
/*  223 */       this.spectatorGui.func_175263_a(scaledresolution);
/*      */     } 
/*      */     
/*  226 */     if (this.mc.isDemo()) {
/*  227 */       renderDemo(scaledresolution);
/*      */     }
/*      */     
/*  230 */     if (this.mc.gameSettings.showDebugInfo) {
/*  231 */       this.overlayDebug.renderDebugInfo(scaledresolution);
/*      */     }
/*      */     
/*  234 */     if (this.recordPlayingUpFor > 0) {
/*  235 */       this.mc.mcProfiler.startSection("overlayMessage");
/*  236 */       float f3 = this.recordPlayingUpFor - partialTicks;
/*  237 */       int k1 = (int)(f3 * 255.0F / 20.0F);
/*      */       
/*  239 */       if (k1 > 255) {
/*  240 */         k1 = 255;
/*      */       }
/*      */       
/*  243 */       if (k1 > 8) {
/*  244 */         GlStateManager.pushMatrix();
/*  245 */         GlStateManager.translate((i / 2), (j - 68), 0.0F);
/*  246 */         GlStateManager.enableBlend();
/*  247 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  248 */         int i1 = 16777215;
/*      */         
/*  250 */         if (this.recordIsPlaying) {
/*  251 */           i1 = MathHelper.func_181758_c(f3 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
/*      */         }
/*      */         
/*  254 */         getFontRenderer().drawString(this.recordPlaying, 
/*  255 */             -getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4, 
/*  256 */             i1 + (k1 << 24 & 0xFF000000));
/*  257 */         GlStateManager.disableBlend();
/*  258 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  261 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  264 */     if (this.field_175195_w > 0) {
/*  265 */       this.mc.mcProfiler.startSection("titleAndSubtitle");
/*  266 */       float f4 = this.field_175195_w - partialTicks;
/*  267 */       int l1 = 255;
/*      */       
/*  269 */       if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
/*  270 */         float f1 = (this.field_175199_z + this.field_175192_A + this.field_175193_B) - f4;
/*  271 */         l1 = (int)(f1 * 255.0F / this.field_175199_z);
/*      */       } 
/*      */       
/*  274 */       if (this.field_175195_w <= this.field_175193_B) {
/*  275 */         l1 = (int)(f4 * 255.0F / this.field_175193_B);
/*      */       }
/*      */       
/*  278 */       l1 = MathHelper.clamp_int(l1, 0, 255);
/*      */       
/*  280 */       if (l1 > 8) {
/*  281 */         GlStateManager.pushMatrix();
/*  282 */         GlStateManager.translate((i / 2), (j / 2), 0.0F);
/*  283 */         GlStateManager.enableBlend();
/*  284 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  285 */         GlStateManager.pushMatrix();
/*  286 */         GlStateManager.scale(4.0F, 4.0F, 4.0F);
/*  287 */         int j2 = l1 << 24 & 0xFF000000;
/*  288 */         getFontRenderer().drawString(this.field_175201_x, (
/*  289 */             -getFontRenderer().getStringWidth(this.field_175201_x) / 2), -10.0F, 
/*  290 */             0xFFFFFF | j2, true);
/*  291 */         GlStateManager.popMatrix();
/*  292 */         GlStateManager.pushMatrix();
/*  293 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  294 */         getFontRenderer().drawString(this.field_175200_y, (
/*  295 */             -getFontRenderer().getStringWidth(this.field_175200_y) / 2), 5.0F, 0xFFFFFF | j2, 
/*  296 */             true);
/*  297 */         GlStateManager.popMatrix();
/*  298 */         GlStateManager.disableBlend();
/*  299 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  302 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/*  305 */     Scoreboard scoreboard = this.mc.theWorld.getScoreboard();
/*  306 */     ScoreObjective scoreobjective = null;
/*  307 */     ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.thePlayer.getName());
/*      */     
/*  309 */     if (scoreplayerteam != null) {
/*  310 */       int j1 = scoreplayerteam.getChatFormat().getColorIndex();
/*      */       
/*  312 */       if (j1 >= 0) {
/*  313 */         scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j1);
/*      */       }
/*      */     } 
/*      */     
/*  317 */     ScoreObjective scoreobjective1 = (scoreobjective != null) ? scoreobjective : 
/*  318 */       scoreboard.getObjectiveInDisplaySlot(1);
/*      */     
/*  320 */     if (!Client.instance.getModuleManager().getModuleByName("NoScoreboard").isEnabled())
/*      */     {
/*  322 */       if (scoreobjective1 != null) {
/*  323 */         renderScoreboard(scoreobjective1, scaledresolution);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  328 */     GlStateManager.enableBlend();
/*  329 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  330 */     GlStateManager.disableAlpha();
/*  331 */     GlStateManager.pushMatrix();
/*  332 */     GlStateManager.translate(0.0F, (j - 48), 0.0F);
/*  333 */     this.mc.mcProfiler.startSection("chat");
/*  334 */     this.persistantChatGUI.drawChat(this.updateCounter);
/*  335 */     this.mc.mcProfiler.endSection();
/*  336 */     GlStateManager.popMatrix();
/*  337 */     scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
/*      */     
/*  339 */     if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || (this.mc.isIntegratedServerRunning() && 
/*  340 */       this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null)) {
/*  341 */       this.overlayPlayerList.updatePlayerList(false);
/*      */     } else {
/*  343 */       this.overlayPlayerList.updatePlayerList(true);
/*  344 */       this.overlayPlayerList.renderPlayerlist(i, scoreboard, scoreobjective1);
/*      */     } 
/*      */     
/*  347 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  348 */     GlStateManager.disableLighting();
/*  349 */     GlStateManager.enableAlpha();
/*      */   } private void renderArraylist() { int y = 10; for (Module module : Client.instance.getModuleManager().getModules()) { if (module.isEnabled()) if (!this.activemodules.contains(module)) this.activemodules.add(module);   }  Collections.sort(Client.instance.getModuleManager().getModules(), new Comparator<Module>() { public int compare(Module m1, Module m2) { if (FontHelper.cfArrayList.getStringWidth(m1.getTag()) > FontHelper.cfArrayList.getStringWidth(m2.getTag())) return -1;  if (FontHelper.cfArrayList.getStringWidth(m1.getTag()) < FontHelper.cfArrayList.getStringWidth(m2.getTag())) return 1;  return 0; } }
/*      */       ); ScaledResolution sr = new ScaledResolution(this.mc); for (int i = 0; i < this.activemodules.size(); i++) { Color c = new Color(Color.DARK_GRAY.getRed(), Color.DARK_GRAY.getGreen(), Color.DARK_GRAY.getBlue(), 255 - i - 1); double rectX = (sr.getScaledWidth() - 5 - FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag())); double rectX2 = rectX + FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag()) + 3.0D; double rectY = (y * i + 2); double rectY2 = rectY + FontHelper.cfArrayList.getStringHeight(((Module)this.activemodules.get(i)).getTag()) + 1.0D; int color = Color.black.getRGB(); drawRect(rectX - 1.0D, rectY, rectX, rectY2, color); drawRect(rectX2, rectY, rectX2 + 1.0D, rectY2, color); if (i == 0) drawRect(rectX - 1.0D, rectY - 1.0D, rectX2 + 1.0D, rectY, color);  if (i == this.activemodules.size() - 1)
/*  352 */         drawRect(rectX - 1.0D, rectY2, rectX2 + 1.0D, rectY2 + 1.0D, color);  if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Dark")) { drawRect((sr.getScaledWidth() - 5 - FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag())), (y * i + 2), (FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag()) + 3), (FontHelper.cfArrayList.getStringHeight(((Module)this.activemodules.get(i)).getTag()) + 1), c); FontHelper.cfArrayList.drawCenteredString2(((Module)this.activemodules.get(i)).getTag(), (sr.getScaledWidth() - 3 - FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag())), (y * i + 1), Color.white.getRGB(), false); } else if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Bright")) { Color c2 = new Color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), 255 - i - 1); drawRect((sr.getScaledWidth() - 5 - FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag())), (y * i + 2), (FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag()) + 3), (FontHelper.cfArrayList.getStringHeight(((Module)this.activemodules.get(i)).getTag()) + 1), c2); FontHelper.cfArrayList.drawCenteredString2("§0" + ((Module)this.activemodules.get(i)).getTag(), (sr.getScaledWidth() - 3 - FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag())), (y * i + 1), Color.white.getRGB(), false); }  if (i != this.activemodules.size() - 1) { double mwidth = (FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i)).getTag()) + 3); double mwidthNext = (FontHelper.cfArrayList.getStringWidth(((Module)this.activemodules.get(i + 1)).getTag()) + 3); double difference = mwidth - mwidthNext; drawRect(rectX, rectY2 - 1.0D, rectX + 1.0D + difference - 2.0D, rectY2, color); }  }  this.activemodules.clear(); } public GuiIngame(Minecraft mcIn) { this.activemodules = new ArrayList<Module>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  504 */     this.oppacity = 0.0F;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  566 */     this.blocks = 0; this.mc = mcIn; this.itemRenderer = mcIn.getRenderItem();
/*      */     this.overlayDebug = new GuiOverlayDebug(mcIn);
/*      */     this.spectatorGui = new GuiSpectator(mcIn);
/*      */     this.persistantChatGUI = new GuiNewChat(mcIn);
/*      */     this.streamIndicator = new GuiStreamIndicator(mcIn);
/*      */     this.overlayPlayerList = new GuiPlayerTabOverlay(mcIn, this);
/*  572 */     func_175177_a(); } private void renderBlocks(double i, double j) { if (Client.instance.getModuleManager().getModuleByName("ScaffoldWalk").isEnabled()) {
/*      */       
/*  574 */       double width = i / 2.0D - 7.0D + 20.0D;
/*      */       
/*  576 */       double height = j / 2.0D - 7.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  581 */       if (Client.instance.getUtils().isTheme("Dark")) {
/*      */         
/*  583 */         FontHelper.cfClickGui.drawStringWithBG(this.blocks, width, height, Color.white);
/*      */       }
/*  585 */       else if (Client.instance.getUtils().isTheme("Bright")) {
/*      */         
/*  587 */         FontHelper.cfClickGui.drawStringWithBGColor("§0" + this.blocks, width, height, Color.white, Color.white);
/*      */       } 
/*      */ 
/*      */       
/*  591 */       this.blocks = 0;
/*      */ 
/*      */       
/*      */       try {
/*  595 */         for (int a = 0; a < this.mc.thePlayer.inventory.getSizeInventory(); a++)
/*      */         {
/*  597 */           if (this.mc.thePlayer.inventory.getStackInSlot(a) != null)
/*      */           {
/*  599 */             if (this.mc.thePlayer.inventory.getStackInSlot(a)
/*  600 */               .getItem() instanceof net.minecraft.item.ItemBlock)
/*      */             {
/*  602 */               this.blocks += (this.mc.thePlayer.inventory.getStackInSlot(a)).stackSize;
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */         }
/*      */       
/*      */       }
/*  610 */       catch (Exception exception) {}
/*      */     }  } private void renderLogo() { String name = "Hera v" + Client.instance.VERSION; if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Dark")) {
/*      */       drawRectWithShadow(2.0D, 2.0D, (FontHelper.cfArrayList.getStringWidth(name) + 4), (FontHelper.cfArrayList.getStringHeight(name) + 4), "right", "bottom", Color.DARK_GRAY); FontHelper.cfArrayList.drawCenteredString2(name, 5.0D, 3.0D, Color.white.getRGB(), false);
/*      */       FontHelper.cfSmall.drawStringWithBGShadow("FPS: " + Minecraft.getDebugFPS(), 5.0D, (3 + FontHelper.cfArrayList.getStringHeight(name) + 8), Color.white);
/*      */     } else if (Client.instance.getSettingManager().getSettingByName("ThemeMode").getMode().equalsIgnoreCase("Bright")) {
/*      */       drawRectWithShadow(2.0D, 2.0D, (FontHelper.cfArrayList.getStringWidth(name) + 4), (FontHelper.cfArrayList.getStringHeight(name) + 4), "right", "bottom", Color.white);
/*      */       FontHelper.cfArrayList.drawCenteredString2("§0" + name, 5.0D, 3.0D, Color.white.getRGB(), false);
/*      */       FontHelper.cfSmall.drawStringWithBGShadowColor("§0FPS: " + Minecraft.getDebugFPS(), 5.0D, (3 + FontHelper.cfArrayList.getStringHeight(name) + 8), Color.white, Color.white);
/*      */     }  } private void renderHutcam() { if (this.mc.thePlayer.hurtTime >= 0.5D)
/*      */       if (Client.instance.getModuleManager().getModuleByName("Hurtcam").isEnabled())
/*      */         if (Client.instance.getSettingManager().getSettingByName("RedHurtcam").getBoolean()) {
/*      */           Color c = new Color(255, 0, 0, 12 * this.mc.thePlayer.hurtTime);
/*      */           drawRedHurt(c);
/*      */         }    }
/*      */   private void otherThings() {}
/*  625 */   protected void renderTooltip(ScaledResolution sr, float partialTicks) { if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  626 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  627 */       this.mc.getTextureManager().bindTexture(widgetsTexPath);
/*  628 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  629 */       int i = sr.getScaledWidth() / 2;
/*  630 */       float f = this.zLevel;
/*  631 */       this.zLevel = -90.0F;
/*      */ 
/*      */ 
/*      */       
/*  635 */       drawRect((i - 92), (sr.getScaledHeight() - 23), 184.0D, 1.0D, Color.black);
/*      */       
/*  637 */       drawRect((i - 92), (sr.getScaledHeight() - 22), 1.0D, 20.0D, Color.black);
/*      */       
/*  639 */       drawRect((i - 91 + 182), (sr.getScaledHeight() - 22), 1.0D, 20.0D, Color.black);
/*      */       
/*  641 */       drawRect((i - 92), (sr.getScaledHeight() - 2), 184.0D, 1.0D, Color.black);
/*      */       
/*  643 */       if (Client.instance.getUtils().isTheme("Dark")) {
/*      */         
/*  645 */         drawRect((i - 91), (sr.getScaledHeight() - 22), 182.0D, 20.0D, Color.DARK_GRAY);
/*      */         
/*  647 */         drawRect((i - 91 + entityplayer.inventory.currentItem * 20), (sr.getScaledHeight() - 22), 22.0D, 20.0D, Color.gray);
/*      */       }
/*  649 */       else if (Client.instance.getUtils().isTheme("Bright")) {
/*      */         
/*  651 */         drawRect((i - 91), (sr.getScaledHeight() - 22), 182.0D, 20.0D, Color.white);
/*      */         
/*  653 */         Color c = new Color(150, 150, 150, 255);
/*      */         
/*  655 */         drawRect((i - 91 + entityplayer.inventory.currentItem * 20), (sr.getScaledHeight() - 22), 22.0D, 20.0D, c);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  664 */       this.zLevel = f;
/*  665 */       GlStateManager.enableRescaleNormal();
/*  666 */       GlStateManager.enableBlend();
/*  667 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  668 */       RenderHelper.enableGUIStandardItemLighting();
/*      */       
/*  670 */       for (int j = 0; j < 9; j++) {
/*  671 */         int k = sr.getScaledWidth() / 2 - 90 + j * 20 + 2;
/*  672 */         int l = sr.getScaledHeight() - 16 - 3;
/*  673 */         renderHotbarItem(j, k, l - 1, partialTicks, entityplayer);
/*      */       } 
/*      */       
/*  676 */       RenderHelper.disableStandardItemLighting();
/*  677 */       GlStateManager.disableRescaleNormal();
/*  678 */       GlStateManager.disableBlend();
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void renderHorseJumpBar(ScaledResolution p_175186_1_, int p_175186_2_) {
/*  683 */     this.mc.mcProfiler.startSection("jumpBar");
/*  684 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  685 */     float f = this.mc.thePlayer.getHorseJumpPower();
/*  686 */     short short1 = 182;
/*  687 */     int i = (int)(f * (short1 + 1));
/*  688 */     int j = p_175186_1_.getScaledHeight() - 32 + 3;
/*  689 */     drawTexturedModalRect(p_175186_2_, j, 0, 84, short1, 5);
/*      */     
/*  691 */     if (i > 0) {
/*  692 */       drawTexturedModalRect(p_175186_2_, j, 0, 89, i, 5);
/*      */     }
/*      */     
/*  695 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderExpBar(ScaledResolution p_175176_1_, int p_175176_2_) {
/*  699 */     this.mc.mcProfiler.startSection("expBar");
/*  700 */     this.mc.getTextureManager().bindTexture(Gui.icons);
/*  701 */     int i = this.mc.thePlayer.xpBarCap();
/*      */     
/*  703 */     if (i > 0) {
/*  704 */       short short1 = 182;
/*  705 */       int k = (int)(this.mc.thePlayer.experience * (short1 + 1));
/*  706 */       int j = p_175176_1_.getScaledHeight() - 32 + 3;
/*  707 */       drawTexturedModalRect(p_175176_2_, j, 0, 64, short1, 5);
/*      */       
/*  709 */       if (k > 0) {
/*  710 */         drawTexturedModalRect(p_175176_2_, j, 0, 69, k, 5);
/*      */       }
/*      */     } 
/*      */     
/*  714 */     this.mc.mcProfiler.endSection();
/*      */     
/*  716 */     if (this.mc.thePlayer.experienceLevel > 0) {
/*  717 */       this.mc.mcProfiler.startSection("expLevel");
/*  718 */       int j1 = 8453920;
/*  719 */       int j = this.mc.thePlayer.experienceLevel;
/*  720 */       int i1 = (p_175176_1_.getScaledWidth() - getFontRenderer().getStringWidth(j)) / 2;
/*  721 */       int l = p_175176_1_.getScaledHeight() - 31 - 4;
/*  722 */       boolean flag = false;
/*  723 */       getFontRenderer().drawString(j, i1 + 1, l, 0);
/*  724 */       getFontRenderer().drawString(j, i1 - 1, l, 0);
/*  725 */       getFontRenderer().drawString(j, i1, l + 1, 0);
/*  726 */       getFontRenderer().drawString(j, i1, l - 1, 0);
/*  727 */       getFontRenderer().drawString(j, i1, l, j1);
/*  728 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void func_181551_a(ScaledResolution p_181551_1_) {
/*  733 */     this.mc.mcProfiler.startSection("selectedItemName");
/*      */     
/*  735 */     if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
/*  736 */       String s = this.highlightingItemStack.getDisplayName();
/*      */       
/*  738 */       if (this.highlightingItemStack.hasDisplayName()) {
/*  739 */         s = EnumChatFormatting.ITALIC + s;
/*      */       }
/*      */       
/*  742 */       int i = (p_181551_1_.getScaledWidth() - getFontRenderer().getStringWidth(s)) / 2;
/*  743 */       int j = p_181551_1_.getScaledHeight() - 59;
/*      */       
/*  745 */       if (!this.mc.playerController.shouldDrawHUD()) {
/*  746 */         j += 14;
/*      */       }
/*      */       
/*  749 */       int k = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
/*      */       
/*  751 */       if (k > 255) {
/*  752 */         k = 255;
/*      */       }
/*      */       
/*  755 */       if (k > 0) {
/*  756 */         GlStateManager.pushMatrix();
/*  757 */         GlStateManager.enableBlend();
/*  758 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  759 */         getFontRenderer().drawStringWithShadow(s, i, j, 16777215 + (k << 24));
/*  760 */         GlStateManager.disableBlend();
/*  761 */         GlStateManager.popMatrix();
/*      */       } 
/*      */     } 
/*      */     
/*  765 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   public void renderDemo(ScaledResolution p_175185_1_) {
/*  769 */     this.mc.mcProfiler.startSection("demo");
/*  770 */     String s = "";
/*      */     
/*  772 */     if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
/*  773 */       s = I18n.format("demo.demoExpired", new Object[0]);
/*      */     } else {
/*  775 */       s = I18n.format("demo.remainingTime", new Object[] {
/*  776 */             StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime()))
/*      */           });
/*      */     } 
/*  779 */     int i = getFontRenderer().getStringWidth(s);
/*  780 */     getFontRenderer().drawStringWithShadow(s, (p_175185_1_.getScaledWidth() - i - 10), 5.0F, 16777215);
/*  781 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   protected boolean showCrosshair() {
/*  785 */     if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && 
/*  786 */       !this.mc.gameSettings.reducedDebugInfo)
/*  787 */       return false; 
/*  788 */     if (this.mc.playerController.isSpectator()) {
/*  789 */       if (this.mc.pointedEntity != null) {
/*  790 */         return true;
/*      */       }
/*  792 */       if (this.mc.objectMouseOver != null && 
/*  793 */         this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/*  794 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/*      */         
/*  796 */         if (this.mc.theWorld.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory) {
/*  797 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  801 */       return false;
/*      */     } 
/*      */     
/*  804 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderStreamIndicator(ScaledResolution p_180478_1_) {
/*  809 */     this.streamIndicator.render(p_180478_1_.getScaledWidth() - 10, 10);
/*      */   }
/*      */   private void renderScoreboard(ScoreObjective p_180475_1_, ScaledResolution p_180475_2_) {
/*      */     ArrayList arraylist1;
/*  813 */     Scoreboard scoreboard = p_180475_1_.getScoreboard();
/*  814 */     Collection collection = scoreboard.getSortedScores(p_180475_1_);
/*  815 */     ArrayList arraylist = Lists.newArrayList(Iterables.filter(collection, new Predicate() {
/*      */             private static final String __OBFID = "CL_00001958";
/*      */             
/*      */             public boolean apply(Score p_apply_1_) {
/*  819 */               return (p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#"));
/*      */             }
/*      */             
/*      */             public boolean apply(Object p_apply_1_) {
/*  823 */               return apply((Score)p_apply_1_);
/*      */             }
/*      */           }));
/*      */ 
/*      */     
/*  828 */     if (arraylist.size() > 15) {
/*  829 */       arraylist1 = Lists.newArrayList(Iterables.skip(arraylist, collection.size() - 15));
/*      */     } else {
/*  831 */       arraylist1 = arraylist;
/*      */     } 
/*      */     
/*  834 */     int i = getFontRenderer().getStringWidth(p_180475_1_.getDisplayName());
/*      */     
/*  836 */     for (Object score0 : arraylist1) {
/*  837 */       Score score = (Score)score0;
/*  838 */       ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(score.getPlayerName());
/*  839 */       String s = String.valueOf(ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam, score.getPlayerName())) + ": " + 
/*  840 */         EnumChatFormatting.RED + score.getScorePoints();
/*  841 */       i = Math.max(i, getFontRenderer().getStringWidth(s));
/*      */     } 
/*      */     
/*  844 */     int j1 = arraylist1.size() * (getFontRenderer()).FONT_HEIGHT;
/*  845 */     int k1 = p_180475_2_.getScaledHeight() / 2 + j1 / 3;
/*  846 */     byte b0 = 3;
/*  847 */     int j = p_180475_2_.getScaledWidth() - i - b0;
/*  848 */     int k = 0;
/*      */     
/*  850 */     for (Object score10 : arraylist1) {
/*  851 */       Score score1 = (Score)score10;
/*  852 */       k++;
/*  853 */       ScorePlayerTeam scoreplayerteam1 = scoreboard.getPlayersTeam(score1.getPlayerName());
/*  854 */       String s1 = ScorePlayerTeam.formatPlayerName((Team)scoreplayerteam1, score1.getPlayerName());
/*  855 */       String s2 = EnumChatFormatting.RED + score1.getScorePoints();
/*  856 */       int l = k1 - k * (getFontRenderer()).FONT_HEIGHT;
/*  857 */       int i1 = p_180475_2_.getScaledWidth() - b0 + 2;
/*  858 */       drawRect(j - 2, l, i1, l + (getFontRenderer()).FONT_HEIGHT, 1342177280);
/*  859 */       getFontRenderer().drawString(s1, j, l, 553648127);
/*  860 */       getFontRenderer().drawString(s2, i1 - getFontRenderer().getStringWidth(s2), l, 553648127);
/*      */       
/*  862 */       if (k == arraylist1.size()) {
/*  863 */         String s3 = p_180475_1_.getDisplayName();
/*  864 */         drawRect(j - 2, l - (getFontRenderer()).FONT_HEIGHT - 1, i1, l - 1, 1610612736);
/*  865 */         drawRect(j - 2, l - 1, i1, l, 1342177280);
/*  866 */         getFontRenderer().drawString(s3, j + i / 2 - getFontRenderer().getStringWidth(s3) / 2, 
/*  867 */             l - (getFontRenderer()).FONT_HEIGHT, 553648127);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPlayerStats(ScaledResolution p_180477_1_) {
/*  873 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  874 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  875 */       int i = MathHelper.ceiling_float_int(entityplayer.getHealth());
/*  876 */       boolean flag = (this.healthUpdateCounter > this.updateCounter && (
/*  877 */         this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L);
/*      */       
/*  879 */       if (i < this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*  880 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  881 */         this.healthUpdateCounter = (this.updateCounter + 20);
/*  882 */       } else if (i > this.playerHealth && entityplayer.hurtResistantTime > 0) {
/*  883 */         this.lastSystemTime = Minecraft.getSystemTime();
/*  884 */         this.healthUpdateCounter = (this.updateCounter + 10);
/*      */       } 
/*      */       
/*  887 */       if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
/*  888 */         this.playerHealth = i;
/*  889 */         this.lastPlayerHealth = i;
/*  890 */         this.lastSystemTime = Minecraft.getSystemTime();
/*      */       } 
/*      */       
/*  893 */       this.playerHealth = i;
/*  894 */       int j = this.lastPlayerHealth;
/*  895 */       this.rand.setSeed((this.updateCounter * 312871));
/*  896 */       boolean flag1 = false;
/*  897 */       FoodStats foodstats = entityplayer.getFoodStats();
/*  898 */       int k = foodstats.getFoodLevel();
/*  899 */       int l = foodstats.getPrevFoodLevel();
/*  900 */       IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
/*  901 */       int i1 = p_180477_1_.getScaledWidth() / 2 - 91;
/*  902 */       int j1 = p_180477_1_.getScaledWidth() / 2 + 91;
/*  903 */       int k1 = p_180477_1_.getScaledHeight() - 39;
/*  904 */       float f = (float)iattributeinstance.getAttributeValue();
/*  905 */       float f1 = entityplayer.getAbsorptionAmount();
/*  906 */       int l1 = MathHelper.ceiling_float_int((f + f1) / 2.0F / 10.0F);
/*  907 */       int i2 = Math.max(10 - l1 - 2, 3);
/*  908 */       int j2 = k1 - (l1 - 1) * i2 - 10;
/*  909 */       float f2 = f1;
/*  910 */       int k2 = entityplayer.getTotalArmorValue();
/*  911 */       int l2 = -1;
/*      */       
/*  913 */       if (entityplayer.isPotionActive(Potion.regeneration)) {
/*  914 */         l2 = this.updateCounter % MathHelper.ceiling_float_int(f + 5.0F);
/*      */       }
/*      */       
/*  917 */       this.mc.mcProfiler.startSection("armor");
/*      */       
/*  919 */       for (int i3 = 0; i3 < 10; i3++) {
/*  920 */         if (k2 > 0) {
/*  921 */           int j3 = i1 + i3 * 8;
/*      */           
/*  923 */           if (i3 * 2 + 1 < k2) {
/*  924 */             drawTexturedModalRect(j3, j2, 34, 9, 9, 9);
/*      */           }
/*      */           
/*  927 */           if (i3 * 2 + 1 == k2) {
/*  928 */             drawTexturedModalRect(j3, j2, 25, 9, 9, 9);
/*      */           }
/*      */           
/*  931 */           if (i3 * 2 + 1 > k2) {
/*  932 */             drawTexturedModalRect(j3, j2, 16, 9, 9, 9);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  937 */       this.mc.mcProfiler.endStartSection("health");
/*      */       
/*  939 */       for (int j5 = MathHelper.ceiling_float_int((f + f1) / 2.0F) - 1; j5 >= 0; j5--) {
/*  940 */         int k5 = 16;
/*      */         
/*  942 */         if (entityplayer.isPotionActive(Potion.poison)) {
/*  943 */           k5 += 36;
/*  944 */         } else if (entityplayer.isPotionActive(Potion.wither)) {
/*  945 */           k5 += 72;
/*      */         } 
/*      */         
/*  948 */         byte b0 = 0;
/*      */         
/*  950 */         if (flag) {
/*  951 */           b0 = 1;
/*      */         }
/*      */         
/*  954 */         int k3 = MathHelper.ceiling_float_int((j5 + 1) / 10.0F) - 1;
/*  955 */         int l3 = i1 + j5 % 10 * 8;
/*  956 */         int i4 = k1 - k3 * i2;
/*      */         
/*  958 */         if (i <= 4) {
/*  959 */           i4 += this.rand.nextInt(2);
/*      */         }
/*      */         
/*  962 */         if (j5 == l2) {
/*  963 */           i4 -= 2;
/*      */         }
/*      */         
/*  966 */         byte b1 = 0;
/*      */         
/*  968 */         if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
/*  969 */           b1 = 5;
/*      */         }
/*      */         
/*  972 */         drawTexturedModalRect(l3, i4, 16 + b0 * 9, 9 * b1, 9, 9);
/*      */         
/*  974 */         if (flag) {
/*  975 */           if (j5 * 2 + 1 < j) {
/*  976 */             drawTexturedModalRect(l3, i4, k5 + 54, 9 * b1, 9, 9);
/*      */           }
/*      */           
/*  979 */           if (j5 * 2 + 1 == j) {
/*  980 */             drawTexturedModalRect(l3, i4, k5 + 63, 9 * b1, 9, 9);
/*      */           }
/*      */         } 
/*      */         
/*  984 */         if (f2 <= 0.0F) {
/*  985 */           if (j5 * 2 + 1 < i) {
/*  986 */             drawTexturedModalRect(l3, i4, k5 + 36, 9 * b1, 9, 9);
/*      */           }
/*      */           
/*  989 */           if (j5 * 2 + 1 == i) {
/*  990 */             drawTexturedModalRect(l3, i4, k5 + 45, 9 * b1, 9, 9);
/*      */           }
/*      */         } else {
/*  993 */           if (f2 == f1 && f1 % 2.0F == 1.0F) {
/*  994 */             drawTexturedModalRect(l3, i4, k5 + 153, 9 * b1, 9, 9);
/*      */           } else {
/*  996 */             drawTexturedModalRect(l3, i4, k5 + 144, 9 * b1, 9, 9);
/*      */           } 
/*      */           
/*  999 */           f2 -= 2.0F;
/*      */         } 
/*      */       } 
/*      */       
/* 1003 */       Entity entity = entityplayer.ridingEntity;
/*      */       
/* 1005 */       if (entity == null) {
/* 1006 */         this.mc.mcProfiler.endStartSection("food");
/*      */         
/* 1008 */         for (int l5 = 0; l5 < 10; l5++) {
/* 1009 */           int i8 = k1;
/* 1010 */           int j6 = 16;
/* 1011 */           byte b4 = 0;
/*      */           
/* 1013 */           if (entityplayer.isPotionActive(Potion.hunger)) {
/* 1014 */             j6 += 36;
/* 1015 */             b4 = 13;
/*      */           } 
/*      */           
/* 1018 */           if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && 
/* 1019 */             this.updateCounter % (k * 3 + 1) == 0) {
/* 1020 */             i8 = k1 + this.rand.nextInt(3) - 1;
/*      */           }
/*      */           
/* 1023 */           if (flag1) {
/* 1024 */             b4 = 1;
/*      */           }
/*      */           
/* 1027 */           int k7 = j1 - l5 * 8 - 9;
/* 1028 */           drawTexturedModalRect(k7, i8, 16 + b4 * 9, 27, 9, 9);
/*      */           
/* 1030 */           if (flag1) {
/* 1031 */             if (l5 * 2 + 1 < l) {
/* 1032 */               drawTexturedModalRect(k7, i8, j6 + 54, 27, 9, 9);
/*      */             }
/*      */             
/* 1035 */             if (l5 * 2 + 1 == l) {
/* 1036 */               drawTexturedModalRect(k7, i8, j6 + 63, 27, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/* 1040 */           if (l5 * 2 + 1 < k) {
/* 1041 */             drawTexturedModalRect(k7, i8, j6 + 36, 27, 9, 9);
/*      */           }
/*      */           
/* 1044 */           if (l5 * 2 + 1 == k) {
/* 1045 */             drawTexturedModalRect(k7, i8, j6 + 45, 27, 9, 9);
/*      */           }
/*      */         } 
/* 1048 */       } else if (entity instanceof EntityLivingBase) {
/* 1049 */         this.mc.mcProfiler.endStartSection("mountHealth");
/* 1050 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/* 1051 */         int l7 = (int)Math.ceil(entitylivingbase.getHealth());
/* 1052 */         float f3 = entitylivingbase.getMaxHealth();
/* 1053 */         int l6 = (int)(f3 + 0.5F) / 2;
/*      */         
/* 1055 */         if (l6 > 30) {
/* 1056 */           l6 = 30;
/*      */         }
/*      */         
/* 1059 */         int j7 = k1;
/*      */         
/* 1061 */         for (int j4 = 0; l6 > 0; j4 += 20) {
/* 1062 */           int k4 = Math.min(l6, 10);
/* 1063 */           l6 -= k4;
/*      */           
/* 1065 */           for (int l4 = 0; l4 < k4; l4++) {
/* 1066 */             byte b2 = 52;
/* 1067 */             byte b3 = 0;
/*      */             
/* 1069 */             if (flag1) {
/* 1070 */               b3 = 1;
/*      */             }
/*      */             
/* 1073 */             int i5 = j1 - l4 * 8 - 9;
/* 1074 */             drawTexturedModalRect(i5, j7, b2 + b3 * 9, 9, 9, 9);
/*      */             
/* 1076 */             if (l4 * 2 + 1 + j4 < l7) {
/* 1077 */               drawTexturedModalRect(i5, j7, b2 + 36, 9, 9, 9);
/*      */             }
/*      */             
/* 1080 */             if (l4 * 2 + 1 + j4 == l7) {
/* 1081 */               drawTexturedModalRect(i5, j7, b2 + 45, 9, 9, 9);
/*      */             }
/*      */           } 
/*      */           
/* 1085 */           j7 -= 10;
/*      */         } 
/*      */       } 
/*      */       
/* 1089 */       this.mc.mcProfiler.endStartSection("air");
/*      */       
/* 1091 */       if (entityplayer.isInsideOfMaterial(Material.water)) {
/* 1092 */         int i6 = this.mc.thePlayer.getAir();
/* 1093 */         int j8 = MathHelper.ceiling_double_int((i6 - 2) * 10.0D / 300.0D);
/* 1094 */         int k6 = MathHelper.ceiling_double_int(i6 * 10.0D / 300.0D) - j8;
/*      */         
/* 1096 */         for (int i7 = 0; i7 < j8 + k6; i7++) {
/* 1097 */           if (i7 < j8) {
/* 1098 */             drawTexturedModalRect(j1 - i7 * 8 - 9, j2, 16, 18, 9, 9);
/*      */           } else {
/* 1100 */             drawTexturedModalRect(j1 - i7 * 8 - 9, j2, 25, 18, 9, 9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1105 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBossHealth() {
/* 1113 */     if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
/* 1114 */       BossStatus.statusBarTime--;
/* 1115 */       FontRenderer fontrenderer = this.mc.fontRendererObj;
/* 1116 */       ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1117 */       int i = scaledresolution.getScaledWidth();
/* 1118 */       short short1 = 182;
/* 1119 */       int j = i / 2 - short1 / 2;
/* 1120 */       int k = (int)(BossStatus.healthScale * (short1 + 1));
/* 1121 */       byte b0 = 12;
/* 1122 */       drawTexturedModalRect(j, b0, 0, 74, short1, 5);
/* 1123 */       drawTexturedModalRect(j, b0, 0, 74, short1, 5);
/*      */       
/* 1125 */       if (k > 0) {
/* 1126 */         drawTexturedModalRect(j, b0, 0, 79, k, 5);
/*      */       }
/*      */       
/* 1129 */       String s = BossStatus.bossName;
/* 1130 */       getFontRenderer().drawStringWithShadow(s, (
/* 1131 */           i / 2 - getFontRenderer().getStringWidth(s) / 2), (b0 - 10), 16777215);
/* 1132 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1133 */       this.mc.getTextureManager().bindTexture(icons);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderPumpkinOverlay(ScaledResolution p_180476_1_) {
/* 1138 */     GlStateManager.disableDepth();
/* 1139 */     GlStateManager.depthMask(false);
/* 1140 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1141 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1142 */     GlStateManager.disableAlpha();
/* 1143 */     this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
/* 1144 */     Tessellator tessellator = Tessellator.getInstance();
/* 1145 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1146 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1147 */     worldrenderer.pos(0.0D, p_180476_1_.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1148 */     worldrenderer.pos(p_180476_1_.getScaledWidth(), p_180476_1_.getScaledHeight(), -90.0D)
/* 1149 */       .tex(1.0D, 1.0D).endVertex();
/* 1150 */     worldrenderer.pos(p_180476_1_.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1151 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1152 */     tessellator.draw();
/* 1153 */     GlStateManager.depthMask(true);
/* 1154 */     GlStateManager.enableDepth();
/* 1155 */     GlStateManager.enableAlpha();
/* 1156 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderVignette(float p_180480_1_, ScaledResolution p_180480_2_) {
/* 1163 */     if (Config.isVignetteEnabled()) {
/* 1164 */       p_180480_1_ = 1.0F - p_180480_1_;
/* 1165 */       p_180480_1_ = MathHelper.clamp_float(p_180480_1_, 0.0F, 1.0F);
/* 1166 */       WorldBorder worldborder = this.mc.theWorld.getWorldBorder();
/* 1167 */       float f = (float)worldborder.getClosestDistance((Entity)this.mc.thePlayer);
/* 1168 */       double d0 = Math.min(worldborder.getResizeSpeed() * worldborder.getWarningTime() * 1000.0D, 
/* 1169 */           Math.abs(worldborder.getTargetSize() - worldborder.getDiameter()));
/* 1170 */       double d1 = Math.max(worldborder.getWarningDistance(), d0);
/*      */       
/* 1172 */       if (f < d1) {
/* 1173 */         f = 1.0F - (float)(f / d1);
/*      */       } else {
/* 1175 */         f = 0.0F;
/*      */       } 
/*      */       
/* 1178 */       this.prevVignetteBrightness = 
/* 1179 */         (float)(this.prevVignetteBrightness + (p_180480_1_ - this.prevVignetteBrightness) * 0.01D);
/* 1180 */       GlStateManager.disableDepth();
/* 1181 */       GlStateManager.depthMask(false);
/* 1182 */       GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
/*      */       
/* 1184 */       if (f > 0.0F) {
/* 1185 */         GlStateManager.color(0.0F, f, f, 1.0F);
/*      */       } else {
/* 1187 */         GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, 
/* 1188 */             this.prevVignetteBrightness, 1.0F);
/*      */       } 
/*      */       
/* 1191 */       this.mc.getTextureManager().bindTexture(vignetteTexPath);
/* 1192 */       Tessellator tessellator = Tessellator.getInstance();
/* 1193 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1194 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1195 */       worldrenderer.pos(0.0D, p_180480_2_.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
/* 1196 */       worldrenderer.pos(p_180480_2_.getScaledWidth(), p_180480_2_.getScaledHeight(), -90.0D)
/* 1197 */         .tex(1.0D, 1.0D).endVertex();
/* 1198 */       worldrenderer.pos(p_180480_2_.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
/* 1199 */       worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
/* 1200 */       tessellator.draw();
/* 1201 */       GlStateManager.depthMask(true);
/* 1202 */       GlStateManager.enableDepth();
/* 1203 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1204 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void func_180474_b(float p_180474_1_, ScaledResolution p_180474_2_) {
/* 1209 */     if (p_180474_1_ < 1.0F) {
/* 1210 */       p_180474_1_ *= p_180474_1_;
/* 1211 */       p_180474_1_ *= p_180474_1_;
/* 1212 */       p_180474_1_ = p_180474_1_ * 0.8F + 0.2F;
/*      */     } 
/*      */     
/* 1215 */     GlStateManager.disableAlpha();
/* 1216 */     GlStateManager.disableDepth();
/* 1217 */     GlStateManager.depthMask(false);
/* 1218 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1219 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_180474_1_);
/* 1220 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 1221 */     TextureAtlasSprite textureatlassprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes()
/* 1222 */       .getTexture(Blocks.portal.getDefaultState());
/* 1223 */     float f = textureatlassprite.getMinU();
/* 1224 */     float f1 = textureatlassprite.getMinV();
/* 1225 */     float f2 = textureatlassprite.getMaxU();
/* 1226 */     float f3 = textureatlassprite.getMaxV();
/* 1227 */     Tessellator tessellator = Tessellator.getInstance();
/* 1228 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 1229 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1230 */     worldrenderer.pos(0.0D, p_180474_2_.getScaledHeight(), -90.0D).tex(f, f3)
/* 1231 */       .endVertex();
/* 1232 */     worldrenderer.pos(p_180474_2_.getScaledWidth(), p_180474_2_.getScaledHeight(), -90.0D)
/* 1233 */       .tex(f2, f3).endVertex();
/* 1234 */     worldrenderer.pos(p_180474_2_.getScaledWidth(), 0.0D, -90.0D).tex(f2, f1)
/* 1235 */       .endVertex();
/* 1236 */     worldrenderer.pos(0.0D, 0.0D, -90.0D).tex(f, f1).endVertex();
/* 1237 */     tessellator.draw();
/* 1238 */     GlStateManager.depthMask(true);
/* 1239 */     GlStateManager.enableDepth();
/* 1240 */     GlStateManager.enableAlpha();
/* 1241 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   private void renderHotbarItem(int index, int xPos, int yPos, float partialTicks, EntityPlayer p_175184_5_) {
/* 1245 */     ItemStack itemstack = p_175184_5_.inventory.mainInventory[index];
/*      */     
/* 1247 */     if (itemstack != null) {
/* 1248 */       float f = itemstack.animationsToGo - partialTicks;
/*      */       
/* 1250 */       if (f > 0.0F) {
/* 1251 */         GlStateManager.pushMatrix();
/* 1252 */         float f1 = 1.0F + f / 5.0F;
/* 1253 */         GlStateManager.translate((xPos + 8), (yPos + 12), 0.0F);
/* 1254 */         GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
/* 1255 */         GlStateManager.translate(-(xPos + 8), -(yPos + 12), 0.0F);
/*      */       } 
/*      */       
/* 1258 */       this.itemRenderer.renderItemAndEffectIntoGUI(itemstack, xPos, yPos);
/*      */       
/* 1260 */       if (f > 0.0F) {
/* 1261 */         GlStateManager.popMatrix();
/*      */       }
/*      */       
/* 1264 */       this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, itemstack, xPos, yPos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick() {
/* 1272 */     if (this.recordPlayingUpFor > 0) {
/* 1273 */       this.recordPlayingUpFor--;
/*      */     }
/*      */     
/* 1276 */     if (this.field_175195_w > 0) {
/* 1277 */       this.field_175195_w--;
/*      */       
/* 1279 */       if (this.field_175195_w <= 0) {
/* 1280 */         this.field_175201_x = "";
/* 1281 */         this.field_175200_y = "";
/*      */       } 
/*      */     } 
/*      */     
/* 1285 */     this.updateCounter++;
/* 1286 */     this.streamIndicator.func_152439_a();
/*      */     
/* 1288 */     if (this.mc.thePlayer != null) {
/* 1289 */       ItemStack itemstack = this.mc.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1291 */       if (itemstack == null) {
/* 1292 */         this.remainingHighlightTicks = 0;
/* 1293 */       } else if (this.highlightingItemStack != null && itemstack.getItem() == this.highlightingItemStack.getItem() && 
/* 1294 */         ItemStack.areItemStackTagsEqual(itemstack, this.highlightingItemStack) && (
/* 1295 */         itemstack.isItemStackDamageable() || 
/* 1296 */         itemstack.getMetadata() == this.highlightingItemStack.getMetadata())) {
/* 1297 */         if (this.remainingHighlightTicks > 0) {
/* 1298 */           this.remainingHighlightTicks--;
/*      */         }
/*      */       } else {
/* 1301 */         this.remainingHighlightTicks = 40;
/*      */       } 
/*      */       
/* 1304 */       this.highlightingItemStack = itemstack;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlayingMessage(String p_73833_1_) {
/* 1309 */     setRecordPlaying(I18n.format("record.nowPlaying", new Object[] { p_73833_1_ }), true);
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(String p_110326_1_, boolean p_110326_2_) {
/* 1313 */     this.recordPlaying = p_110326_1_;
/* 1314 */     this.recordPlayingUpFor = 60;
/* 1315 */     this.recordIsPlaying = p_110326_2_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayTitle(String p_175178_1_, String p_175178_2_, int p_175178_3_, int p_175178_4_, int p_175178_5_) {
/* 1320 */     if (p_175178_1_ == null && p_175178_2_ == null && p_175178_3_ < 0 && p_175178_4_ < 0 && p_175178_5_ < 0) {
/* 1321 */       this.field_175201_x = "";
/* 1322 */       this.field_175200_y = "";
/* 1323 */       this.field_175195_w = 0;
/* 1324 */     } else if (p_175178_1_ != null) {
/* 1325 */       this.field_175201_x = p_175178_1_;
/* 1326 */       this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
/* 1327 */     } else if (p_175178_2_ != null) {
/* 1328 */       this.field_175200_y = p_175178_2_;
/*      */     } else {
/* 1330 */       if (p_175178_3_ >= 0) {
/* 1331 */         this.field_175199_z = p_175178_3_;
/*      */       }
/*      */       
/* 1334 */       if (p_175178_4_ >= 0) {
/* 1335 */         this.field_175192_A = p_175178_4_;
/*      */       }
/*      */       
/* 1338 */       if (p_175178_5_ >= 0) {
/* 1339 */         this.field_175193_B = p_175178_5_;
/*      */       }
/*      */       
/* 1342 */       if (this.field_175195_w > 0) {
/* 1343 */         this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setRecordPlaying(IChatComponent p_175188_1_, boolean p_175188_2_) {
/* 1349 */     setRecordPlaying(p_175188_1_.getUnformattedText(), p_175188_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GuiNewChat getChatGUI() {
/* 1357 */     return this.persistantChatGUI;
/*      */   }
/*      */   
/*      */   public int getUpdateCounter() {
/* 1361 */     return this.updateCounter;
/*      */   }
/*      */   
/*      */   public FontRenderer getFontRenderer() {
/* 1365 */     return this.mc.fontRendererObj;
/*      */   }
/*      */   
/*      */   public GuiSpectator getSpectatorGui() {
/* 1369 */     return this.spectatorGui;
/*      */   }
/*      */   
/*      */   public GuiPlayerTabOverlay getTabList() {
/* 1373 */     return this.overlayPlayerList;
/*      */   }
/*      */   
/*      */   public void func_181029_i() {
/* 1377 */     this.overlayPlayerList.func_181030_a();
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiIngame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */