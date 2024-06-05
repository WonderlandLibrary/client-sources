package net.minecraft.client.gui;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.gui.screen.dropdown.DropdownGUI;
import cc.novoline.modules.misc.Streamer;
import cc.novoline.modules.visual.Crosshair;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_18;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_20;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.aE6;
import net.aHg;
import net.aZ5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiOverlayDebug;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.GuiStreamIndicator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import net.optifine.Config;
import net.optifine.CustomColors;
import viaversion.viafabric.ViaFabric;

public class GuiIngame extends Gui {
   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
   private static final String s = "CL_00000661";
   private final Random rand = new Random();
   private final Minecraft mc;
   private final RenderItem itemRenderer;
   private List E;
   private final aZ5 B;
   private final GuiStreamIndicator streamIndicator;
   private final GuiOverlayDebug overlayDebug;
   private final GuiSpectator spectatorGui;
   private final aHg t;
   public float prevVignetteBrightness = 1.0F;
   private int updateCounter;
   private String recordPlaying = "";
   private int recordPlayingUpFor;
   private boolean recordIsPlaying;
   private int remainingHighlightTicks;
   private ItemStack highlightingItemStack;
   private int field_175195_w;
   private String field_175201_x = "";
   private String field_175200_y = "";
   private int f;
   private int H;
   private int p;
   private int playerHealth = 0;
   private int lastPlayerHealth = 0;
   private long lastSystemTime = 0L;
   private long healthUpdateCounter = 0L;

   public GuiIngame(Minecraft var1) {
      this.mc = var1;
      this.itemRenderer = var1.getRenderItem();
      this.overlayDebug = new GuiOverlayDebug(var1);
      this.spectatorGui = new GuiSpectator(var1);
      this.B = new aZ5(var1);
      this.streamIndicator = new GuiStreamIndicator(var1);
      this.t = new aHg(var1, this);
      this.func_175177_a();
   }

   public void func_175177_a() {
      this.f = 10;
      this.H = 70;
      this.p = 20;
   }

   public void renderGameOverlay(float var1) {
      ScaledResolution var2 = new ScaledResolution(this.mc);
      int var3 = var2.getScaledWidth();
      int var4 = var2.getScaledHeight();
      this.mc.entityRenderer.setupOverlayRendering();
      GlStateManager.enableBlend();
      if(Config.isVignetteEnabled()) {
         this.renderVignette(this.mc.player.getBrightness(var1), var2);
      } else {
         GlStateManager.enableDepth();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      }

      ItemStack var5 = this.mc.player.inventory.armorItemInSlot(3);
      if(this.mc.gameSettings.thirdPersonView == 0 && var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
         this.renderPumpkinOverlay(var2);
      }

      if(!this.mc.player.isPotionActive(Potion.confusion)) {
         float var6 = this.mc.player.prevTimeInPortal + (this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * var1;
         if(var6 > 0.0F) {
            this.func_180474_b(var6, var2);
         }
      }

      if(this.mc.at.k()) {
         this.spectatorGui.renderTooltip(var2, var1);
      } else {
         this.renderTooltip(var2, var1);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(icons);
      GlStateManager.enableBlend();
      if(!(this.mc.currentScreen instanceof DropdownGUI) && this.showCrosshair() && this.mc.gameSettings.thirdPersonView < 1) {
         GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
         GlStateManager.enableAlpha();
         this.drawTexturedModalRect(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      this.mc.mcProfiler.startSection("bossHealth");
      this.renderBossHealth();
      this.mc.mcProfiler.endSection();
      if(this.mc.at.b()) {
         this.renderPlayerStats(var2);
      }

      GlStateManager.disableBlend();
      if(this.mc.player.getSleepTimer() > 0) {
         this.mc.mcProfiler.startSection("sleep");
         GlStateManager.disableDepth();
         GlStateManager.disableAlpha();
         int var11 = this.mc.player.getSleepTimer();
         float var7 = (float)var11 / 100.0F;
         if(var7 > 1.0F) {
            var7 = 1.0F - (float)(var11 - 100) / 10.0F;
         }

         int var8 = (int)(220.0F * var7) << 24 | 1052704;
         drawRect(0, 0, var3, var4, var8);
         GlStateManager.enableAlpha();
         GlStateManager.enableDepth();
         this.mc.mcProfiler.endSection();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      int var12 = var3 / 2 - 91;
      if(this.mc.player.isRidingHorse()) {
         this.renderHorseJumpBar(var2, var12);
      } else if(this.mc.at.c()) {
         this.renderExpBar(var2, var12);
      }

      if(this.mc.gameSettings.heldItemTooltips && !this.mc.at.k()) {
         this.func_181551_a(var2);
      } else if(this.mc.player.isSpectator()) {
         this.spectatorGui.func_175263_a(var2);
      }

      if(this.mc.isDemo()) {
         this.renderDemo(var2);
      }

      if(this.mc.gameSettings.showDebugInfo) {
         this.overlayDebug.renderDebugInfo(var2);
      }

      if(this.recordPlayingUpFor > 0) {
         this.mc.mcProfiler.startSection("overlayMessage");
         float var13 = (float)this.recordPlayingUpFor - var1;
         int var16 = (int)(var13 * 255.0F / 20.0F);
         if(var16 > 255) {
            var16 = 255;
         }

         if(var16 > 8) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(var3 / 2), (float)(var4 - 68), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            int var9 = 16777215;
            if(this.recordIsPlaying) {
               var9 = MathHelper.func_181758_c(var13 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            this.getFontRenderer().drawString(this.recordPlaying, (float)(-this.getFontRenderer().d(this.recordPlaying) / 2), -4.0F, var9 + (var16 << 24 & -16777216));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }

         this.mc.mcProfiler.endSection();
      }

      if(this.field_175195_w > 0) {
         this.mc.mcProfiler.startSection("titleAndSubtitle");
         float var14 = (float)this.field_175195_w - var1;
         int var17 = 255;
         if(this.field_175195_w > this.p + this.H) {
            float var20 = (float)(this.f + this.H + this.p) - var14;
            var17 = (int)(var20 * 255.0F / (float)this.f);
         }

         if(this.field_175195_w <= this.p) {
            var17 = (int)(var14 * 255.0F / (float)this.p);
         }

         var17 = MathHelper.clamp_int(var17, 0, 255);
         if(var17 > 8) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(var3 / 2), (float)(var4 / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 4.0F);
            int var21 = var17 << 24 & -16777216;
            this.getFontRenderer().drawString(this.field_175201_x, (float)(-this.getFontRenderer().d(this.field_175201_x) / 2), -10.0F, 16777215 | var21, true);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            this.getFontRenderer().drawString(this.field_175200_y, (float)(-this.getFontRenderer().d(this.field_175200_y) / 2), 5.0F, 16777215 | var21, true);
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }

         this.mc.mcProfiler.endSection();
      }

      Scoreboard var15 = this.mc.world.getScoreboard();
      ScoreObjective var19 = null;
      ScorePlayerTeam var22 = var15.getPlayersTeam(this.mc.player.getName());
      if(ViaFabric.clientSideVersion == 47) {
         int var10 = var22.getChatFormat().getColorIndex();
         var19 = var15.getObjectiveInDisplaySlot(3 + var10);
      }

      this.a(var19, var2);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableAlpha();
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, (float)(var4 - 48), 0.0F);
      this.mc.mcProfiler.startSection("chat");
      this.B.c(this.updateCounter);
      this.mc.mcProfiler.endSection();
      GlStateManager.popMatrix();
      ScoreObjective var23 = var15.getObjectiveInDisplaySlot(0);
      if(this.mc.gameSettings.keyBindPlayerList.isKeyDown() && (!this.mc.isIntegratedServerRunning() || this.mc.player.connection.getPlayerInfoMap().size() > 1)) {
         this.t.a(true);
         this.t.a(var3, var15, var23);
      } else {
         this.t.a(true);
      }

      EventManager.call(new Render2DEvent(var2, var1));
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
      GlStateManager.enableAlpha();
   }

   private void renderTooltip(ScaledResolution var1, float var2) {
      if(this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(widgetsTexPath);
         EntityPlayer var3 = (EntityPlayer)this.mc.getRenderViewEntity();
         int var4 = var1.getScaledWidth() / 2;
         float var5 = this.zLevel;
         this.zLevel = -90.0F;
         this.drawTexturedModalRect(var4 - 91, var1.getScaledHeight() - 22, 0, 0, 182, 22);
         this.drawTexturedModalRect(var4 - 91 - 1 + var3.inventory.currentItem * 20, var1.getScaledHeight() - 22 - 1, 0, 22, 24, 22);
         this.zLevel = var5;
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.enableGUIStandardItemLighting();

         for(int var6 = 0; var6 < 9; ++var6) {
            int var7 = var1.getScaledWidth() / 2 - 90 + var6 * 20 + 2;
            int var8 = var1.getScaledHeight() - 16 - 3;
            this.renderHotbarItem(var6, var7, var8, var2, var3);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
      }

   }

   public void renderHorseJumpBar(ScaledResolution var1, int var2) {
      this.mc.mcProfiler.startSection("jumpBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      float var3 = this.mc.player.getHorseJumpPower();
      short var4 = 182;
      int var5 = (int)(var3 * (float)(var4 + 1));
      int var6 = var1.getScaledHeight() - 32 + 3;
      this.drawTexturedModalRect(var2, var6, 0, 84, var4, 5);
      this.drawTexturedModalRect(var2, var6, 0, 89, var5, 5);
      this.mc.mcProfiler.endSection();
   }

   public void renderExpBar(ScaledResolution var1, int var2) {
      this.mc.mcProfiler.startSection("expBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      int var3 = this.mc.player.xpBarCap();
      int var4 = 182;
      int var5 = (int)(this.mc.player.experience * (float)(var4 + 1));
      int var6 = var1.getScaledHeight() - 32 + 3;
      this.drawTexturedModalRect(var2, var6, 0, 64, var4, 5);
      this.drawTexturedModalRect(var2, var6, 0, 69, var5, 5);
      this.mc.mcProfiler.endSection();
      if(this.mc.player.experienceLevel > 0) {
         this.mc.mcProfiler.startSection("expLevel");
         var4 = 8453920;
         if(Config.isCustomColors()) {
            var4 = CustomColors.a(var4);
         }

         String var10 = "" + this.mc.player.experienceLevel;
         var6 = (var1.getScaledWidth() - this.getFontRenderer().d(var10)) / 2;
         int var7 = var1.getScaledHeight() - 31 - 4;
         boolean var8 = false;
         this.getFontRenderer().drawString(var10, (float)(var6 + 1), (float)var7, 0);
         this.getFontRenderer().drawString(var10, (float)(var6 - 1), (float)var7, 0);
         this.getFontRenderer().drawString(var10, (float)var6, (float)(var7 + 1), 0);
         this.getFontRenderer().drawString(var10, (float)var6, (float)(var7 - 1), 0);
         this.getFontRenderer().drawString(var10, (float)var6, (float)var7, var4);
         this.mc.mcProfiler.endSection();
      }

   }

   public void func_181551_a(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("selectedItemName");
      if(this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
         String var2 = this.highlightingItemStack.getDisplayName();
         if(this.highlightingItemStack.hasDisplayName()) {
            var2 = EnumChatFormatting.ITALIC + var2;
         }

         int var3 = (var1.getScaledWidth() - this.getFontRenderer().d(var2)) / 2;
         int var4 = var1.getScaledHeight() - 59;
         if(!this.mc.at.b()) {
            var4 += 14;
         }

         int var5 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);
         if(var5 > 255) {
            var5 = 255;
         }

         GlStateManager.pushMatrix();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         this.getFontRenderer().drawStringWithShadow(var2, (float)var3, (float)var4 - 12.0F, 16777215 + (var5 << 24));
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

      this.mc.mcProfiler.endSection();
   }

   public void renderDemo(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("demo");
      String var2;
      if(this.mc.world.getTotalWorldTime() >= 120500L) {
         var2 = I18n.format("demo.demoExpired", new Object[0]);
      } else {
         var2 = I18n.format("demo.remainingTime", new Object[]{StringUtils.ticksToElapsedTime((int)(120500L - this.mc.world.getTotalWorldTime()))});
      }

      int var3 = this.getFontRenderer().d(var2);
      this.getFontRenderer().drawStringWithShadow(var2, (float)(var1.getScaledWidth() - var3 - 10), 5.0F, 16777215);
      this.mc.mcProfiler.endSection();
   }

   protected boolean showCrosshair() {
      if((!this.mc.gameSettings.showDebugInfo || this.mc.player.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo) && !((Crosshair)Novoline.getInstance().getModuleManager().getModule(Crosshair.class)).isEnabled()) {
         if(this.mc.at.k()) {
            if(this.mc.pointedEntity != null) {
               return true;
            } else if(this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
               BlockPos var1 = this.mc.objectMouseOver.getBlockPos();
               return this.mc.world.getTileEntity(var1) instanceof IInventory;
            } else {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void renderStreamIndicator(ScaledResolution var1) {
      this.streamIndicator.render(var1.getScaledWidth() - 10, 10);
   }

   private void a(ScoreObjective var1, ScaledResolution var2) {
      Scoreboard var3 = var1.getScoreboard();
      Collection var4 = var3.getSortedScores(var1);
      List var5 = (List)var4.stream().filter(GuiIngame::lambda$renderScoreboard$0).collect(Collectors.toCollection(ObjectArrayList::<init>));
      if(var5.size() > 15) {
         this.E = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
      } else {
         this.E = var5;
      }

      int var6 = (int)this.getWidth(var1.getDisplayName());

      for(Object var8 : this.E) {
         ScorePlayerTeam var9 = var3.getPlayersTeam(((Score)var8).getPlayerName());
         String var10 = ScorePlayerTeam.formatPlayerName(var9, ((Score)var8).getPlayerName()) + ": " + EnumChatFormatting.RED + ((Score)var8).getScorePoints();
         var6 = (int)Math.max((float)var6, this.getWidth(var10));
      }

      int var22 = this.E.size() * this.getHeight();
      int var23 = var2.getScaledHeight() / 2 + var22 / 3 + ((Integer)((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getScoreboard().get()).intValue();
      byte var24 = 3;
      int var25 = var2.getScaledWidth() - var6 - var24 + (this.l()?6:9);
      int var11 = 0;

      for(Object var13 : this.E) {
         ++var11;
         ScorePlayerTeam var14 = var3.getPlayersTeam(((Score)var13).getPlayerName());
         String var15 = ScorePlayerTeam.formatPlayerName(var14, ((Score)var13).getPlayerName());
         EventManager.call(new aE6(var1, var11, var15));
         if(var11 == 1) {
            var15 = var15.replace("www.hypixel.ne\ud83c\udf82§et", "novoline@intent");
         }

         int var16 = var23 - var11 * this.getHeight();
         int var17 = var2.getScaledWidth() - var24 + 3;
         int var18 = this.l()?1:0;
         if(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getHudElements().contains("Scoreboard")) {
            HUD var19 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
            drawRect(var25 - 2, var16 - var18, var17, var16 + this.getHeight() - var18, 1342177280);
            if(((Streamer)Novoline.getInstance().getModuleManager().getModule(Streamer.class)).isEnabled() && ((Boolean)((Streamer)Novoline.getInstance().getModuleManager().getModule(Streamer.class)).isHideServerId().get()).booleanValue()) {
               boolean var27 = true;
            } else {
               boolean var10000 = false;
            }

            if(var11 == this.E.size()) {
               ArrayList var21 = new ArrayList(Arrays.asList(var15.split(" ")));
               if(!var21.isEmpty() && ((String)var21.get(0)).contains("/")) {
                  var15 = var15.replace(var15.substring(((String)var21.get(0)).length()), "");
               }
            }

            if(!var15.toLowerCase().contains("novoline")) {
               this.a(var15, (float)var25, (float)var16);
            } else if(this.l()) {
               var19.a(this.getClientFont(), var15.replace("§e", ""), (float)var25, (float)var16);
            } else {
               var19.a(var15.replace("§e", ""), (float)var25, (float)var16);
            }
         }

         if(var11 == this.E.size()) {
            String var26 = var1.getDisplayName();
            if(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getHudElements().contains("Scoreboard")) {
               drawRect(var25 - 2, var16 - this.getHeight() - (this.l()?2:1), var17, var16 - 1, 1610612736);
               drawRect(var25 - 2, var16 - 1 + var18, var17, var16, 1342177280);
               this.a(var26, (float)var25 + (float)var6 / 2.0F - this.getWidth(var26) / 2.0F - 3.0F, (float)(var16 - this.getHeight()));
            }
         }
      }

   }

   private void renderPlayerStats(ScaledResolution var1) {
      if(this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.mc.getRenderViewEntity();
         int var3 = MathHelper.ceiling_float_int(var2.getHealth());
         if(this.healthUpdateCounter > (long)this.updateCounter && (this.healthUpdateCounter - (long)this.updateCounter) / 3L % 2L == 1L) {
            boolean var32 = true;
         } else {
            boolean var10000 = false;
         }

         if(var3 < this.playerHealth && var2.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = (long)(this.updateCounter + 20);
         } else if(var3 > this.playerHealth && var2.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = (long)(this.updateCounter + 10);
         }

         if(Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
            this.playerHealth = var3;
            this.lastPlayerHealth = var3;
            this.lastSystemTime = Minecraft.getSystemTime();
         }

         this.playerHealth = var3;
         int var5 = this.lastPlayerHealth;
         this.rand.setSeed((long)this.updateCounter * 312871L);
         boolean var6 = false;
         FoodStats var7 = var2.getFoodStats();
         int var8 = var7.getFoodLevel();
         int var9 = var7.getPrevFoodLevel();
         IAttributeInstance var10 = var2.getEntityAttribute(SharedMonsterAttributes.maxHealth);
         int var11 = var1.getScaledWidth() / 2 - 91;
         int var12 = var1.getScaledWidth() / 2 + 91;
         int var13 = var1.getScaledHeight() - 39;
         float var14 = (float)var10.getAttributeValue();
         float var15 = var2.getAbsorptionAmount();
         int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F / 10.0F);
         int var17 = Math.max(10 - (var16 - 2), 3);
         int var18 = var13 - (var16 - 1) * var17 - 10;
         float var19 = var15;
         int var20 = var2.getTotalArmorValue();
         int var21 = -1;
         if(var2.isPotionActive(Potion.regeneration)) {
            var21 = this.updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
         }

         this.mc.mcProfiler.startSection("armor");

         for(int var22 = 0; var22 < 10; ++var22) {
            int var23 = var11 + var22 * 8;
            if(var22 * 2 + 1 < var20) {
               this.drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
            }

            if(var22 * 2 + 1 == var20) {
               this.drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
            }

            if(var22 * 2 + 1 > var20) {
               this.drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
            }
         }

         this.mc.mcProfiler.endStartSection("health");
         int var29 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1;

         while(true) {
            int var30 = 16;
            if(var2.isPotionActive(Potion.poison)) {
               var30 += 36;
            } else if(var2.isPotionActive(Potion.wither)) {
               var30 += 72;
            }

            byte var24 = 0;
            var24 = 1;
            int var25 = MathHelper.ceiling_float_int((float)(var29 + 1) / 10.0F) - 1;
            int var26 = var11 + var29 % 10 * 8;
            int var27 = var13 - var25 * var17;
            if(var3 <= 4) {
               var27 += this.rand.nextInt(2);
            }

            if(var29 == var21) {
               var27 -= 2;
            }

            byte var28 = 0;
            if(var2.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
               var28 = 5;
            }

            this.drawTexturedModalRect(var26, var27, 16 + var24 * 9, 9 * var28, 9, 9);
            if(var29 * 2 + 1 < var5) {
               this.drawTexturedModalRect(var26, var27, var30 + 54, 9 * var28, 9, 9);
            }

            if(var29 * 2 + 1 == var5) {
               this.drawTexturedModalRect(var26, var27, var30 + 63, 9 * var28, 9, 9);
            }

            if(var19 <= 0.0F) {
               if(var29 * 2 + 1 < var3) {
                  this.drawTexturedModalRect(var26, var27, var30 + 36, 9 * var28, 9, 9);
               }

               if(var29 * 2 + 1 == var3) {
                  this.drawTexturedModalRect(var26, var27, var30 + 45, 9 * var28, 9, 9);
               }
            } else {
               if(var19 == var15 && var15 % 2.0F == 1.0F) {
                  this.drawTexturedModalRect(var26, var27, var30 + 153, 9 * var28, 9, 9);
               } else {
                  this.drawTexturedModalRect(var26, var27, var30 + 144, 9 * var28, 9, 9);
               }

               var19 -= 2.0F;
            }

            --var29;
         }
      }

   }

   private void renderBossHealth() {
      if(((HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class)).getHudElements().contains("Bossbar")) {
         if(BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            FontRenderer var1 = this.mc.fontRendererObj;
            ScaledResolution var2 = new ScaledResolution(this.mc);
            int var3 = var2.getScaledWidth();
            short var4 = 182;
            int var5 = var3 / 2 - var4 / 2;
            int var6 = (int)(BossStatus.healthScale * (float)(var4 + 1));
            byte var7 = 12;
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
            this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
            String var8 = BossStatus.bossName;
            int var9 = 16777215;
            if(Config.isCustomColors()) {
               var9 = CustomColors.c(var9);
            }

            this.getFontRenderer().drawStringWithShadow(var8, (float)(var3 / 2 - this.getFontRenderer().d(var8) / 2), (float)(var7 - 10), var9);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(icons);
         }

      }
   }

   private void renderPumpkinOverlay(ScaledResolution var1) {
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableAlpha();
      this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      var3.begin(7, DefaultVertexFormats.POSITION_TEX);
      var3.pos(0.0D, (double)var1.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
      var3.pos((double)var1.getScaledWidth(), (double)var1.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
      var3.pos((double)var1.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
      var3.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
      var2.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderVignette(float var1, ScaledResolution var2) {
      if(!Config.isVignetteEnabled()) {
         GlStateManager.enableDepth();
      } else {
         var1 = 1.0F - var1;
         var1 = MathHelper.clamp_float(var1, 0.0F, 1.0F);
         WorldBorder var3 = this.mc.world.getWorldBorder();
         float var4 = (float)var3.getClosestDistance(this.mc.player);
         double var5 = Math.min(var3.getResizeSpeed() * (double)var3.getWarningTime() * 1000.0D, Math.abs(var3.getTargetSize() - var3.getDiameter()));
         double var7 = Math.max((double)var3.getWarningDistance(), var5);
         if((double)var4 < var7) {
            var4 = 1.0F - (float)((double)var4 / var7);
         } else {
            var4 = 0.0F;
         }

         this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(var1 - this.prevVignetteBrightness) * 0.01D);
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
         if(var4 > 0.0F) {
            GlStateManager.color(0.0F, var4, var4, 1.0F);
         } else {
            GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
         }

         this.mc.getTextureManager().bindTexture(vignetteTexPath);
         Tessellator var9 = Tessellator.getInstance();
         WorldRenderer var10 = var9.getWorldRenderer();
         var10.begin(7, DefaultVertexFormats.POSITION_TEX);
         var10.pos(0.0D, (double)var2.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
         var10.pos((double)var2.getScaledWidth(), (double)var2.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
         var10.pos((double)var2.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
         var10.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
         var9.draw();
         GlStateManager.depthMask(true);
         GlStateManager.enableDepth();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
   }

   private void func_180474_b(float var1, ScaledResolution var2) {
      if(var1 < 1.0F) {
         var1 = var1 * var1;
         var1 = var1 * var1;
         var1 = var1 * 0.8F + 0.2F;
      }

      GlStateManager.disableAlpha();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, var1);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      TextureAtlasSprite var3 = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
      float var4 = var3.getMinU();
      float var5 = var3.getMinV();
      float var6 = var3.getMaxU();
      float var7 = var3.getMaxV();
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      var9.begin(7, DefaultVertexFormats.POSITION_TEX);
      var9.pos(0.0D, (double)var2.getScaledHeight(), -90.0D).tex((double)var4, (double)var7).endVertex();
      var9.pos((double)var2.getScaledWidth(), (double)var2.getScaledHeight(), -90.0D).tex((double)var6, (double)var7).endVertex();
      var9.pos((double)var2.getScaledWidth(), 0.0D, -90.0D).tex((double)var6, (double)var5).endVertex();
      var9.pos(0.0D, 0.0D, -90.0D).tex((double)var4, (double)var5).endVertex();
      var8.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderHotbarItem(int var1, int var2, int var3, float var4, EntityPlayer var5) {
      ItemStack var6 = var5.inventory.mainInventory[var1];
      float var7 = (float)var6.animationsToGo - var4;
      if(var7 > 0.0F) {
         GlStateManager.pushMatrix();
         float var8 = 1.0F + var7 / 5.0F;
         GlStateManager.translate((float)(var2 + 8), (float)(var3 + 12), 0.0F);
         GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
         GlStateManager.translate((float)(-(var2 + 8)), (float)(-(var3 + 12)), 0.0F);
      }

      this.itemRenderer.renderItemAndEffectIntoGUI(var6, (float)var2, (float)var3);
      if(var7 > 0.0F) {
         GlStateManager.popMatrix();
      }

      GlStateManager.disableColorMaterial();
      this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, var6, var2, var3);
   }

   public void updateTick() {
      if(this.recordPlayingUpFor > 0) {
         --this.recordPlayingUpFor;
      }

      if(this.field_175195_w > 0) {
         --this.field_175195_w;
         if(this.field_175195_w <= 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
         }
      }

      ++this.updateCounter;
      this.streamIndicator.func_152439_a();
      if(this.mc.player != null) {
         ItemStack var1 = this.mc.player.inventory.getCurrentItem();
         this.remainingHighlightTicks = 0;
         this.highlightingItemStack = var1;
      }

   }

   public void setRecordPlayingMessage(String var1) {
      this.setRecordPlaying(I18n.format("record.nowPlaying", new Object[]{var1}), true);
   }

   public void setRecordPlaying(String var1, boolean var2) {
      this.recordPlaying = var1;
      this.recordPlayingUpFor = 60;
      this.recordIsPlaying = var2;
   }

   public void a(String var1, String var2, int var3, int var4, int var5) {
      this.field_175201_x = "";
      this.field_175200_y = "";
      this.field_175195_w = 0;
   }

   public void setRecordPlaying(IChatComponent var1, boolean var2) {
      this.setRecordPlaying(var1.getUnformattedText(), var2);
   }

   public aZ5 n() {
      return this.B;
   }

   public int getUpdateCounter() {
      return this.updateCounter;
   }

   public FontRenderer getFontRenderer() {
      return this.mc.fontRendererObj;
   }

   public GuiSpectator getSpectatorGui() {
      return this.spectatorGui;
   }

   public aHg a() {
      return this.t;
   }

   public void func_181029_i() {
      this.t.a();
   }

   private boolean l() {
      return false;
   }

   private float getWidth(String var1) {
      return this.l()?(float)Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.stringWidth(var1):(float)this.getVanillaFont().d(var1);
   }

   private int getHeight() {
      return this.l()?this.getClientFont().getHeight() + 2:this.getVanillaFont().getHeight();
   }

   private void a(String var1, float var2, float var3) {
      if(this.l()) {
         Fonts$SFTHIN$SFTHIN_20.SFTHIN_20.drawString(var1, (double)var2, (double)var3, 553648127, true);
      } else {
         this.getVanillaFont().drawString(var1, var2, var3, 553648127, true);
      }

   }

   private FontRenderer getVanillaFont() {
      return Minecraft.getInstance().fontRendererObj;
   }

   private cc.novoline.utils.fonts.api.FontRenderer getClientFont() {
      return Fonts$SF$SF_18.SF_18;
   }

   public List c() {
      return Lists.reverse(this.E);
   }

   private static boolean lambda$renderScoreboard$0(Score var0) {
      return var0.getPlayerName() != null && !var0.getPlayerName().startsWith("#");
   }
}
