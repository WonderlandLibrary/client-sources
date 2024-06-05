package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import hawk.Client;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import optifine.Config;
import optifine.CustomColors;

public class GuiIngame extends Gui {
   public float prevVignetteBrightness = 1.0F;
   private int updateCounter;
   private ItemStack highlightingItemStack;
   private int field_175189_D = 0;
   private int remainingHighlightTicks;
   private static final String __OBFID = "CL_00000661";
   private long field_175191_F = 0L;
   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
   private int field_175193_B;
   private final GuiSpectator field_175197_u;
   private String field_175200_y = "";
   private int field_175194_C = 0;
   private final GuiPlayerTabOverlay overlayPlayerList;
   private final RenderItem itemRenderer;
   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
   private final GuiOverlayDebug overlayDebug;
   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
   private final GuiStreamIndicator streamIndicator;
   private final GuiNewChat persistantChatGUI;
   private String field_175201_x = "";
   private String recordPlaying = "";
   private int field_175199_z;
   private boolean recordIsPlaying;
   private int field_175195_w;
   private final Random rand = new Random();
   private int field_175192_A;
   private int recordPlayingUpFor;
   private final Minecraft mc;
   private long field_175190_E = 0L;

   public void updateTick() {
      if (this.recordPlayingUpFor > 0) {
         --this.recordPlayingUpFor;
      }

      if (this.field_175195_w > 0) {
         --this.field_175195_w;
         if (this.field_175195_w <= 0) {
            this.field_175201_x = "";
            this.field_175200_y = "";
         }
      }

      ++this.updateCounter;
      this.streamIndicator.func_152439_a();
      if (this.mc.thePlayer != null) {
         ItemStack var1 = this.mc.thePlayer.inventory.getCurrentItem();
         if (var1 == null) {
            this.remainingHighlightTicks = 0;
         } else if (this.highlightingItemStack != null && var1.getItem() == this.highlightingItemStack.getItem() && ItemStack.areItemStackTagsEqual(var1, this.highlightingItemStack) && (var1.isItemStackDamageable() || var1.getMetadata() == this.highlightingItemStack.getMetadata())) {
            if (this.remainingHighlightTicks > 0) {
               --this.remainingHighlightTicks;
            }
         } else {
            this.remainingHighlightTicks = 40;
         }

         this.highlightingItemStack = var1;
      }

   }

   private void func_180477_d(ScaledResolution var1) {
      if (this.mc.func_175606_aa() instanceof EntityPlayer) {
         EntityPlayer var2 = (EntityPlayer)this.mc.func_175606_aa();
         int var3 = MathHelper.ceiling_float_int(var2.getHealth());
         boolean var4 = this.field_175191_F > (long)this.updateCounter && (this.field_175191_F - (long)this.updateCounter) / 3L % 2L == 1L;
         if (var3 < this.field_175194_C && var2.hurtResistantTime > 0) {
            this.field_175190_E = Minecraft.getSystemTime();
            this.field_175191_F = (long)(this.updateCounter + 20);
         } else if (var3 > this.field_175194_C && var2.hurtResistantTime > 0) {
            this.field_175190_E = Minecraft.getSystemTime();
            this.field_175191_F = (long)(this.updateCounter + 10);
         }

         if (Minecraft.getSystemTime() - this.field_175190_E > 1000L) {
            this.field_175194_C = var3;
            this.field_175189_D = var3;
            this.field_175190_E = Minecraft.getSystemTime();
         }

         this.field_175194_C = var3;
         int var5 = this.field_175189_D;
         this.rand.setSeed((long)(this.updateCounter * 312871));
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
         if (var2.isPotionActive(Potion.regeneration)) {
            var21 = this.updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
         }

         this.mc.mcProfiler.startSection("armor");

         int var22;
         int var23;
         for(var22 = 0; var22 < 10; ++var22) {
            if (var20 > 0) {
               var23 = var11 + var22 * 8;
               if (var22 * 2 + 1 < var20) {
                  this.drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
               }

               if (var22 * 2 + 1 == var20) {
                  this.drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
               }

               if (var22 * 2 + 1 > var20) {
                  this.drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
               }
            }
         }

         this.mc.mcProfiler.endStartSection("health");

         int var24;
         int var25;
         int var26;
         for(var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1; var22 >= 0; --var22) {
            var23 = 16;
            if (var2.isPotionActive(Potion.poison)) {
               var23 += 36;
            } else if (var2.isPotionActive(Potion.wither)) {
               var23 += 72;
            }

            byte var27 = 0;
            if (var4) {
               var27 = 1;
            }

            var24 = MathHelper.ceiling_float_int((float)(var22 + 1) / 10.0F) - 1;
            var25 = var11 + var22 % 10 * 8;
            var26 = var13 - var24 * var17;
            if (var3 <= 4) {
               var26 += this.rand.nextInt(2);
            }

            if (var22 == var21) {
               var26 -= 2;
            }

            byte var28 = 0;
            if (var2.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
               var28 = 5;
            }

            this.drawTexturedModalRect(var25, var26, 16 + var27 * 9, 9 * var28, 9, 9);
            if (var4) {
               if (var22 * 2 + 1 < var5) {
                  this.drawTexturedModalRect(var25, var26, var23 + 54, 9 * var28, 9, 9);
               }

               if (var22 * 2 + 1 == var5) {
                  this.drawTexturedModalRect(var25, var26, var23 + 63, 9 * var28, 9, 9);
               }
            }

            if (!(var19 > 0.0F)) {
               if (var22 * 2 + 1 < var3) {
                  this.drawTexturedModalRect(var25, var26, var23 + 36, 9 * var28, 9, 9);
               }

               if (var22 * 2 + 1 == var3) {
                  this.drawTexturedModalRect(var25, var26, var23 + 45, 9 * var28, 9, 9);
               }
            } else {
               if (var19 == var15 && var15 % 2.0F == 1.0F) {
                  this.drawTexturedModalRect(var25, var26, var23 + 153, 9 * var28, 9, 9);
               } else {
                  this.drawTexturedModalRect(var25, var26, var23 + 144, 9 * var28, 9, 9);
               }

               var19 -= 2.0F;
            }
         }

         Entity var37 = var2.ridingEntity;
         int var38;
         if (var37 == null) {
            this.mc.mcProfiler.endStartSection("food");

            for(var23 = 0; var23 < 10; ++var23) {
               var38 = var13;
               var24 = 16;
               byte var39 = 0;
               if (var2.isPotionActive(Potion.hunger)) {
                  var24 += 36;
                  var39 = 13;
               }

               if (var2.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var8 * 3 + 1) == 0) {
                  var38 = var13 + (this.rand.nextInt(3) - 1);
               }

               if (var6) {
                  var39 = 1;
               }

               var26 = var12 - var23 * 8 - 9;
               this.drawTexturedModalRect(var26, var38, 16 + var39 * 9, 27, 9, 9);
               if (var6) {
                  if (var23 * 2 + 1 < var9) {
                     this.drawTexturedModalRect(var26, var38, var24 + 54, 27, 9, 9);
                  }

                  if (var23 * 2 + 1 == var9) {
                     this.drawTexturedModalRect(var26, var38, var24 + 63, 27, 9, 9);
                  }
               }

               if (var23 * 2 + 1 < var8) {
                  this.drawTexturedModalRect(var26, var38, var24 + 36, 27, 9, 9);
               }

               if (var23 * 2 + 1 == var8) {
                  this.drawTexturedModalRect(var26, var38, var24 + 45, 27, 9, 9);
               }
            }
         } else if (var37 instanceof EntityLivingBase) {
            this.mc.mcProfiler.endStartSection("mountHealth");
            EntityLivingBase var29 = (EntityLivingBase)var37;
            var38 = (int)Math.ceil((double)var29.getHealth());
            float var30 = var29.getMaxHealth();
            var25 = (int)(var30 + 0.5F) / 2;
            if (var25 > 30) {
               var25 = 30;
            }

            var26 = var13;

            for(int var31 = 0; var25 > 0; var31 += 20) {
               int var32 = Math.min(var25, 10);
               var25 -= var32;

               for(int var33 = 0; var33 < var32; ++var33) {
                  byte var34 = 52;
                  byte var35 = 0;
                  if (var6) {
                     var35 = 1;
                  }

                  int var36 = var12 - var33 * 8 - 9;
                  this.drawTexturedModalRect(var36, var26, var34 + var35 * 9, 9, 9, 9);
                  if (var33 * 2 + 1 + var31 < var38) {
                     this.drawTexturedModalRect(var36, var26, var34 + 36, 9, 9, 9);
                  }

                  if (var33 * 2 + 1 + var31 == var38) {
                     this.drawTexturedModalRect(var36, var26, var34 + 45, 9, 9, 9);
                  }
               }

               var26 -= 10;
            }
         }

         this.mc.mcProfiler.endStartSection("air");
         if (var2.isInsideOfMaterial(Material.water)) {
            var23 = this.mc.thePlayer.getAir();
            var38 = MathHelper.ceiling_double_int((double)(var23 - 2) * 10.0D / 300.0D);
            var24 = MathHelper.ceiling_double_int((double)var23 * 10.0D / 300.0D) - var38;

            for(var25 = 0; var25 < var38 + var24; ++var25) {
               if (var25 < var38) {
                  this.drawTexturedModalRect(var12 - var25 * 8 - 9, var18, 16, 18, 9, 9);
               } else {
                  this.drawTexturedModalRect(var12 - var25 * 8 - 9, var18, 25, 18, 9, 9);
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }

   }

   public void setRecordPlaying(String var1, boolean var2) {
      this.recordPlaying = var1;
      this.recordPlayingUpFor = 60;
      this.recordIsPlaying = var2;
   }

   private void func_180475_a(ScoreObjective var1, ScaledResolution var2) {
      Scoreboard var3 = var1.getScoreboard();
      Collection var4 = var3.getSortedScores(var1);
      ArrayList var5 = Lists.newArrayList(Iterables.filter(var4, new Predicate(this) {
         private static final String __OBFID = "CL_00001958";
         final GuiIngame this$0;

         public boolean func_178903_a(Score var1) {
            return var1.getPlayerName() != null && !var1.getPlayerName().startsWith("#");
         }

         public boolean apply(Object var1) {
            return this.func_178903_a((Score)var1);
         }

         {
            this.this$0 = var1;
         }
      }));
      ArrayList var6;
      if (var5.size() > 15) {
         var6 = Lists.newArrayList(Iterables.skip(var5, var4.size() - 15));
      } else {
         var6 = var5;
      }

      int var7 = this.func_175179_f().getStringWidth(var1.getDisplayName());

      String var8;
      for(Iterator var9 = var6.iterator(); var9.hasNext(); var7 = Math.max(var7, this.func_175179_f().getStringWidth(var8))) {
         Score var10 = (Score)var9.next();
         ScorePlayerTeam var11 = var3.getPlayersTeam(var10.getPlayerName());
         var8 = String.valueOf((new StringBuilder(String.valueOf(ScorePlayerTeam.formatPlayerName(var11, var10.getPlayerName())))).append(": ").append(EnumChatFormatting.RED).append(var10.getScorePoints()));
      }

      int var22 = var6.size() * this.func_175179_f().FONT_HEIGHT;
      int var23 = var2.getScaledHeight() / 2 + var22 / 3;
      byte var24 = 3;
      int var12 = var2.getScaledWidth() - var7 - var24;
      int var13 = 0;
      Iterator var14 = var6.iterator();

      while(var14.hasNext()) {
         Score var15 = (Score)var14.next();
         ++var13;
         ScorePlayerTeam var16 = var3.getPlayersTeam(var15.getPlayerName());
         String var17 = ScorePlayerTeam.formatPlayerName(var16, var15.getPlayerName());
         String var18 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.RED).append(var15.getScorePoints()));
         int var19 = var23 - var13 * this.func_175179_f().FONT_HEIGHT;
         int var20 = var2.getScaledWidth() - var24 + 2;
         drawRect((double)(var12 - 2), (double)var19, (double)var20, (double)(var19 + this.func_175179_f().FONT_HEIGHT), 1342177280);
         this.func_175179_f().drawString(var17, (double)var12, (double)var19, 553648127);
         this.func_175179_f().drawString(var18, (double)(var20 - this.func_175179_f().getStringWidth(var18)), (double)var19, 553648127);
         if (var13 == var6.size()) {
            String var21 = var1.getDisplayName();
            drawRect((double)(var12 - 2), (double)(var19 - this.func_175179_f().FONT_HEIGHT - 1), (double)var20, (double)(var19 - 1), 1610612736);
            drawRect((double)(var12 - 2), (double)(var19 - 1), (double)var20, (double)var19, 1342177280);
            this.func_175179_f().drawString(var21, (double)(var12 + var7 / 2 - this.func_175179_f().getStringWidth(var21) / 2), (double)(var19 - this.func_175179_f().FONT_HEIGHT), 553648127);
         }
      }

   }

   private void func_180476_e(ScaledResolution var1) {
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableAlpha();
      this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
      Tessellator var2 = Tessellator.getInstance();
      WorldRenderer var3 = var2.getWorldRenderer();
      var3.startDrawingQuads();
      var3.addVertexWithUV(0.0D, (double)var1.getScaledHeight(), -90.0D, 0.0D, 1.0D);
      var3.addVertexWithUV((double)var1.getScaledWidth(), (double)var1.getScaledHeight(), -90.0D, 1.0D, 1.0D);
      var3.addVertexWithUV((double)var1.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
      var3.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
      var2.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void func_180474_b(float var1, ScaledResolution var2) {
      if (var1 < 1.0F) {
         var1 *= var1;
         var1 *= var1;
         var1 = var1 * 0.8F + 0.2F;
      }

      GlStateManager.disableAlpha();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, var1);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      TextureAtlasSprite var3 = this.mc.getBlockRendererDispatcher().func_175023_a().func_178122_a(Blocks.portal.getDefaultState());
      float var4 = var3.getMinU();
      float var5 = var3.getMinV();
      float var6 = var3.getMaxU();
      float var7 = var3.getMaxV();
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      var9.startDrawingQuads();
      var9.addVertexWithUV(0.0D, (double)var2.getScaledHeight(), -90.0D, (double)var4, (double)var7);
      var9.addVertexWithUV((double)var2.getScaledWidth(), (double)var2.getScaledHeight(), -90.0D, (double)var6, (double)var7);
      var9.addVertexWithUV((double)var2.getScaledWidth(), 0.0D, -90.0D, (double)var6, (double)var5);
      var9.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)var4, (double)var5);
      var8.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public GuiPlayerTabOverlay getTabList() {
      return this.overlayPlayerList;
   }

   private void func_175184_a(int var1, int var2, int var3, float var4, EntityPlayer var5) {
      ItemStack var6 = var5.inventory.mainInventory[var1];
      if (var6 != null) {
         float var7 = (float)var6.animationsToGo - var4;
         if (var7 > 0.0F) {
            GlStateManager.pushMatrix();
            float var8 = 1.0F + var7 / 5.0F;
            GlStateManager.translate((float)(var2 + 8), (float)(var3 + 12), 0.0F);
            GlStateManager.scale(1.0F / var8, (var8 + 1.0F) / 2.0F, 1.0F);
            GlStateManager.translate((float)(-(var2 + 8)), (float)(-(var3 + 12)), 0.0F);
         }

         this.itemRenderer.func_180450_b(var6, var2, var3);
         if (var7 > 0.0F) {
            GlStateManager.popMatrix();
         }

         this.itemRenderer.func_175030_a(this.mc.fontRendererObj, var6, var2, var3);
      }

   }

   public void func_175185_b(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("demo");
      String var2 = "";
      if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
         var2 = I18n.format("demo.demoExpired");
      } else {
         var2 = I18n.format("demo.remainingTime", StringUtils.ticksToElapsedTime((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
      }

      int var3 = this.func_175179_f().getStringWidth(var2);
      this.func_175179_f().drawStringWithShadow(var2, (double)((float)(var1.getScaledWidth() - var3 - 10)), 5.0D, 16777215);
      this.mc.mcProfiler.endSection();
   }

   public FontRenderer func_175179_f() {
      return this.mc.fontRendererObj;
   }

   private void func_180480_a(float var1, ScaledResolution var2) {
      if (Config.isVignetteEnabled()) {
         var1 = 1.0F - var1;
         var1 = MathHelper.clamp_float(var1, 0.0F, 1.0F);
         WorldBorder var3 = this.mc.theWorld.getWorldBorder();
         float var4 = (float)var3.getClosestDistance(this.mc.thePlayer);
         double var5 = Math.min(var3.func_177749_o() * (double)var3.getWarningTime() * 1000.0D, Math.abs(var3.getTargetSize() - var3.getDiameter()));
         double var7 = Math.max((double)var3.getWarningDistance(), var5);
         if ((double)var4 < var7) {
            var4 = 1.0F - (float)((double)var4 / var7);
         } else {
            var4 = 0.0F;
         }

         this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(var1 - this.prevVignetteBrightness) * 0.01D);
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
         if (var4 > 0.0F) {
            GlStateManager.color(0.0F, var4, var4, 1.0F);
         } else {
            GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
         }

         this.mc.getTextureManager().bindTexture(vignetteTexPath);
         Tessellator var9 = Tessellator.getInstance();
         WorldRenderer var10 = var9.getWorldRenderer();
         var10.startDrawingQuads();
         var10.addVertexWithUV(0.0D, (double)var2.getScaledHeight(), -90.0D, 0.0D, 1.0D);
         var10.addVertexWithUV((double)var2.getScaledWidth(), (double)var2.getScaledHeight(), -90.0D, 1.0D, 1.0D);
         var10.addVertexWithUV((double)var2.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
         var10.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
         var9.draw();
         GlStateManager.depthMask(true);
         GlStateManager.enableDepth();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      }

   }

   public void func_175176_b(ScaledResolution var1, int var2) {
      this.mc.mcProfiler.startSection("expBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      int var3 = this.mc.thePlayer.xpBarCap();
      int var4;
      if (var3 > 0) {
         short var5 = 182;
         int var6 = (int)(this.mc.thePlayer.experience * (float)(var5 + 1));
         var4 = var1.getScaledHeight() - 32 + 3;
         this.drawTexturedModalRect(var2, var4, 0, 64, var5, 5);
         if (var6 > 0) {
            this.drawTexturedModalRect(var2, var4, 0, 69, var6, 5);
         }
      }

      this.mc.mcProfiler.endSection();
      if (this.mc.thePlayer.experienceLevel > 0) {
         this.mc.mcProfiler.startSection("expLevel");
         int var9 = 8453920;
         if (Config.isCustomColors()) {
            var9 = CustomColors.getExpBarTextColor(var9);
         }

         String var10 = String.valueOf((new StringBuilder()).append(this.mc.thePlayer.experienceLevel));
         var4 = (var1.getScaledWidth() - this.func_175179_f().getStringWidth(var10)) / 2;
         int var7 = var1.getScaledHeight() - 31 - 4;
         boolean var8 = false;
         this.func_175179_f().drawString(var10, (double)(var4 + 1), (double)var7, 0);
         this.func_175179_f().drawString(var10, (double)(var4 - 1), (double)var7, 0);
         this.func_175179_f().drawString(var10, (double)var4, (double)(var7 + 1), 0);
         this.func_175179_f().drawString(var10, (double)var4, (double)(var7 - 1), 0);
         this.func_175179_f().drawString(var10, (double)var4, (double)var7, var9);
         this.mc.mcProfiler.endSection();
      }

   }

   public GuiNewChat getChatGUI() {
      return this.persistantChatGUI;
   }

   public void func_175182_a(ScaledResolution var1) {
      this.mc.mcProfiler.startSection("toolHighlight");
      if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
         String var2 = this.highlightingItemStack.getDisplayName();
         if (this.highlightingItemStack.hasDisplayName()) {
            var2 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.ITALIC).append(var2));
         }

         int var3 = (var1.getScaledWidth() - this.func_175179_f().getStringWidth(var2)) / 2;
         int var4 = var1.getScaledHeight() - 59;
         if (!this.mc.playerController.shouldDrawHUD()) {
            var4 += 14;
         }

         int var5 = (int)((float)this.remainingHighlightTicks * 256.0F / 10.0F);
         if (var5 > 255) {
            var5 = 255;
         }

         if (var5 > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.func_175179_f().drawStringWithShadow(var2, (double)((float)var3), (double)((float)var4), 16777215 + (var5 << 24));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }
      }

      this.mc.mcProfiler.endSection();
   }

   public void func_180478_c(ScaledResolution var1) {
      this.streamIndicator.render(var1.getScaledWidth() - 10, 10);
   }

   public GuiIngame(Minecraft var1) {
      this.mc = var1;
      this.itemRenderer = var1.getRenderItem();
      this.overlayDebug = new GuiOverlayDebug(var1);
      this.field_175197_u = new GuiSpectator(var1);
      this.persistantChatGUI = new GuiNewChat(var1);
      this.streamIndicator = new GuiStreamIndicator(var1);
      this.overlayPlayerList = new GuiPlayerTabOverlay(var1, this);
      this.func_175177_a();
   }

   public GuiSpectator func_175187_g() {
      return this.field_175197_u;
   }

   public void setRecordPlayingMessage(String var1) {
      this.setRecordPlaying(I18n.format("record.nowPlaying", var1), true);
   }

   protected void func_180479_a(ScaledResolution var1, float var2) {
      if (this.mc.func_175606_aa() instanceof EntityPlayer) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(widgetsTexPath);
         EntityPlayer var3 = (EntityPlayer)this.mc.func_175606_aa();
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
            this.func_175184_a(var6, var7, var8, var2, var3);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
      }

   }

   private void renderBossHealth() {
      if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
         --BossStatus.statusBarTime;
         FontRenderer var1 = this.mc.fontRendererObj;
         ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
         int var3 = var2.getScaledWidth();
         short var4 = 182;
         int var5 = var3 / 2 - var4 / 2;
         int var6 = (int)(BossStatus.healthScale * (float)(var4 + 1));
         byte var7 = 12;
         this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
         this.drawTexturedModalRect(var5, var7, 0, 74, var4, 5);
         if (var6 > 0) {
            this.drawTexturedModalRect(var5, var7, 0, 79, var6, 5);
         }

         String var8 = BossStatus.bossName;
         int var9 = 16777215;
         if (Config.isCustomColors()) {
            var9 = CustomColors.getBossTextColor(var9);
         }

         this.func_175179_f().drawStringWithShadow(var8, (double)((float)(var3 / 2 - this.func_175179_f().getStringWidth(var8) / 2)), (double)((float)(var7 - 10)), var9);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(icons);
      }

   }

   public void func_175178_a(String var1, String var2, int var3, int var4, int var5) {
      if (var1 == null && var2 == null && var3 < 0 && var4 < 0 && var5 < 0) {
         this.field_175201_x = "";
         this.field_175200_y = "";
         this.field_175195_w = 0;
      } else if (var1 != null) {
         this.field_175201_x = var1;
         this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
      } else if (var2 != null) {
         this.field_175200_y = var2;
      } else {
         if (var3 >= 0) {
            this.field_175199_z = var3;
         }

         if (var4 >= 0) {
            this.field_175192_A = var4;
         }

         if (var5 >= 0) {
            this.field_175193_B = var5;
         }

         if (this.field_175195_w > 0) {
            this.field_175195_w = this.field_175199_z + this.field_175192_A + this.field_175193_B;
         }
      }

   }

   public void func_175177_a() {
      this.field_175199_z = 10;
      this.field_175192_A = 70;
      this.field_175193_B = 20;
   }

   public void func_175188_a(IChatComponent var1, boolean var2) {
      this.setRecordPlaying(var1.getUnformattedText(), var2);
   }

   public int getUpdateCounter() {
      return this.updateCounter;
   }

   public void func_175186_a(ScaledResolution var1, int var2) {
      this.mc.mcProfiler.startSection("jumpBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      float var3 = this.mc.thePlayer.getHorseJumpPower();
      short var4 = 182;
      int var5 = (int)(var3 * (float)(var4 + 1));
      int var6 = var1.getScaledHeight() - 32 + 3;
      this.drawTexturedModalRect(var2, var6, 0, 84, var4, 5);
      if (var5 > 0) {
         this.drawTexturedModalRect(var2, var6, 0, 89, var5, 5);
      }

      this.mc.mcProfiler.endSection();
   }

   public void func_175180_a(float var1) {
      ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int var3 = var2.getScaledWidth();
      int var4 = var2.getScaledHeight();
      this.mc.entityRenderer.setupOverlayRendering();
      GlStateManager.enableBlend();
      if (Config.isVignetteEnabled()) {
         this.func_180480_a(this.mc.thePlayer.getBrightness(var1), var2);
      } else {
         GlStateManager.enableDepth();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      }

      ItemStack var5 = this.mc.thePlayer.inventory.armorItemInSlot(3);
      if (this.mc.gameSettings.thirdPersonView == 0 && var5 != null && var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
         this.func_180476_e(var2);
      }

      float var6;
      if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
         var6 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * var1;
         if (var6 > 0.0F) {
            this.func_180474_b(var6, var2);
         }
      }

      if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
         this.field_175197_u.func_175264_a(var2, var1);
      } else {
         this.func_180479_a(var2, var1);
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(icons);
      GlStateManager.enableBlend();
      if (this.func_175183_b() && this.mc.gameSettings.thirdPersonView < 1) {
         GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
         GlStateManager.enableAlpha();
         this.drawTexturedModalRect(var3 / 2 - 7, var4 / 2 - 7, 0, 0, 16, 16);
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      this.mc.mcProfiler.startSection("bossHealth");
      this.renderBossHealth();
      this.mc.mcProfiler.endSection();
      if (this.mc.playerController.shouldDrawHUD()) {
         this.func_180477_d(var2);
      }

      GlStateManager.disableBlend();
      int var7;
      int var8;
      if (this.mc.thePlayer.getSleepTimer() > 0) {
         this.mc.mcProfiler.startSection("sleep");
         GlStateManager.disableDepth();
         GlStateManager.disableAlpha();
         var8 = this.mc.thePlayer.getSleepTimer();
         var6 = (float)var8 / 100.0F;
         if (var6 > 1.0F) {
            var6 = 1.0F - (float)(var8 - 100) / 10.0F;
         }

         var7 = (int)(220.0F * var6) << 24 | 1052704;
         drawRect(0.0D, 0.0D, (double)var3, (double)var4, var7);
         GlStateManager.enableAlpha();
         GlStateManager.enableDepth();
         this.mc.mcProfiler.endSection();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var8 = var3 / 2 - 91;
      if (this.mc.thePlayer.isRidingHorse()) {
         this.func_175186_a(var2, var8);
      } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
         this.func_175176_b(var2, var8);
      }

      if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.enableEverythingIsScrewedUpMode()) {
         this.func_175182_a(var2);
      } else if (this.mc.thePlayer.func_175149_v()) {
         this.field_175197_u.func_175263_a(var2);
      }

      if (this.mc.isDemo()) {
         this.func_175185_b(var2);
      }

      if (this.mc.gameSettings.showDebugInfo) {
         this.overlayDebug.func_175237_a(var2);
      }

      int var9;
      if (this.recordPlayingUpFor > 0) {
         this.mc.mcProfiler.startSection("overlayMessage");
         var6 = (float)this.recordPlayingUpFor - var1;
         var7 = (int)(var6 * 255.0F / 20.0F);
         if (var7 > 255) {
            var7 = 255;
         }

         if (var7 > 8) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(var3 / 2), (float)(var4 - 68), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            var9 = 16777215;
            if (this.recordIsPlaying) {
               var9 = Color.HSBtoRGB(var6 / 50.0F, 0.7F, 0.6F) & 16777215;
            }

            this.func_175179_f().drawString(this.recordPlaying, (double)(-this.func_175179_f().getStringWidth(this.recordPlaying) / 2), -4.0D, var9 + (var7 << 24 & -16777216));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }

         this.mc.mcProfiler.endSection();
      }

      if (this.field_175195_w > 0) {
         this.mc.mcProfiler.startSection("titleAndSubtitle");
         var6 = (float)this.field_175195_w - var1;
         var7 = 255;
         if (this.field_175195_w > this.field_175193_B + this.field_175192_A) {
            float var10 = (float)(this.field_175199_z + this.field_175192_A + this.field_175193_B) - var6;
            var7 = (int)(var10 * 255.0F / (float)this.field_175199_z);
         }

         if (this.field_175195_w <= this.field_175193_B) {
            var7 = (int)(var6 * 255.0F / (float)this.field_175193_B);
         }

         var7 = MathHelper.clamp_int(var7, 0, 255);
         if (var7 > 8) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)(var3 / 2), (float)(var4 / 2), 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 4.0F);
            var9 = var7 << 24 & -16777216;
            this.func_175179_f().func_175065_a(this.field_175201_x, (float)(-this.func_175179_f().getStringWidth(this.field_175201_x) / 2), -10.0F, 16777215 | var9, true);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            this.func_175179_f().func_175065_a(this.field_175200_y, (float)(-this.func_175179_f().getStringWidth(this.field_175200_y) / 2), 5.0F, 16777215 | var9, true);
            GlStateManager.popMatrix();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }

         this.mc.mcProfiler.endSection();
      }

      Scoreboard var14 = this.mc.theWorld.getScoreboard();
      ScoreObjective var11 = null;
      ScorePlayerTeam var12 = var14.getPlayersTeam(this.mc.thePlayer.getName());
      if (var12 != null) {
         int var13 = var12.func_178775_l().func_175746_b();
         if (var13 >= 0) {
            var11 = var14.getObjectiveInDisplaySlot(3 + var13);
         }
      }

      ScoreObjective var15 = var11 != null ? var11 : var14.getObjectiveInDisplaySlot(1);
      if (var15 != null) {
         this.func_180475_a(var15, var2);
      }

      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableAlpha();
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, (float)(var4 - 48), 0.0F);
      this.mc.mcProfiler.startSection("chat");
      this.persistantChatGUI.drawChat(this.updateCounter);
      this.mc.mcProfiler.endSection();
      GlStateManager.popMatrix();
      var15 = var14.getObjectiveInDisplaySlot(0);
      if (!this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() || this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.func_175106_d().size() <= 1 && var15 == null) {
         this.overlayPlayerList.func_175246_a(false);
      } else {
         this.overlayPlayerList.func_175246_a(true);
         this.overlayPlayerList.func_175249_a(var3, var14, var15);
      }

      Client.hud.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableLighting();
      GlStateManager.enableAlpha();
   }

   protected boolean func_175183_b() {
      if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.func_175140_cp() && !this.mc.gameSettings.field_178879_v) {
         return false;
      } else if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
         if (this.mc.pointedEntity != null) {
            return true;
         } else {
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
               BlockPos var1 = this.mc.objectMouseOver.func_178782_a();
               if (this.mc.theWorld.getTileEntity(var1) instanceof IInventory) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }
}
