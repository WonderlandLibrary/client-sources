package net.minecraft.client.gui.achievement;

import java.io.IOException;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiAchievements extends GuiScreen implements IProgressMeter {
   private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
   private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
   private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
   private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
   private static final ResourceLocation ACHIEVEMENT_BACKGROUND = new ResourceLocation("textures/gui/achievement/achievement_background.png");
   protected GuiScreen parentScreen;
   protected int field_146555_f = 256;
   protected int field_146557_g = 202;
   protected int field_146563_h;
   protected int field_146564_i;
   protected float field_146570_r = 1.0F;
   protected double field_146569_s;
   protected double field_146568_t;
   protected double field_146567_u;
   protected double field_146566_v;
   protected double field_146565_w;
   protected double field_146573_x;
   private int field_146554_D;
   private StatFileWriter statFileWriter;
   private boolean loadingAchievements = true;

   public GuiAchievements(GuiScreen var1, StatFileWriter var2) {
      this.parentScreen = var1;
      this.statFileWriter = var2;
      short var3 = 141;
      short var4 = 141;
      this.field_146569_s = this.field_146567_u = this.field_146565_w = (double)(AchievementList.openInventory.displayColumn * 24 - var3 / 2 - 12);
      this.field_146568_t = this.field_146566_v = this.field_146573_x = (double)(AchievementList.openInventory.displayRow * 24 - var4 / 2);
   }

   public void initGui() {
      this.mc.getNetHandler().b(new C16PacketClientStatus(C16PacketClientStatus$EnumState.REQUEST_STATS));
      this.buttonList.clear();
      this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(!this.loadingAchievements && var1.id == 1) {
         this.mc.displayGuiScreen(this.parentScreen);
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(var2 == this.mc.gameSettings.keyBindInventory.getKeyCode()) {
         this.mc.displayGuiScreen((GuiScreen)null);
         this.mc.setIngameFocus();
      } else {
         super.keyTyped(var1, var2);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      if(this.loadingAchievements) {
         this.drawDefaultBackground();
         this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), this.width / 2, this.height / 2, 16777215);
         this.drawCenteredString(this.fontRendererObj, lanSearchStates[(int)(Minecraft.getSystemTime() / 150L % (long)lanSearchStates.length)], this.width / 2, this.height / 2 + this.fontRendererObj.getHeight() * 2, 16777215);
      } else {
         if(Mouse.isButtonDown(0)) {
            int var4 = (this.width - this.field_146555_f) / 2;
            int var5 = (this.height - this.field_146557_g) / 2;
            int var6 = var4 + 8;
            int var7 = var5 + 17;
            if((this.field_146554_D == 0 || this.field_146554_D == 1) && var1 >= var6 && var1 < var6 + 224 && var2 >= var7 && var2 < var7 + 155) {
               if(this.field_146554_D == 0) {
                  this.field_146554_D = 1;
               } else {
                  this.field_146567_u -= (double)((float)(var1 - this.field_146563_h) * this.field_146570_r);
                  this.field_146566_v -= (double)((float)(var2 - this.field_146564_i) * this.field_146570_r);
                  this.field_146565_w = this.field_146569_s = this.field_146567_u;
                  this.field_146573_x = this.field_146568_t = this.field_146566_v;
               }

               this.field_146563_h = var1;
               this.field_146564_i = var2;
            }
         } else {
            this.field_146554_D = 0;
         }

         int var11 = Mouse.getDWheel();
         float var12 = this.field_146570_r;
         this.field_146570_r += 0.25F;
         this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
         if(this.field_146570_r != var12) {
            float var10000 = var12 - this.field_146570_r;
            float var13 = var12 * (float)this.field_146555_f;
            float var8 = var12 * (float)this.field_146557_g;
            float var9 = this.field_146570_r * (float)this.field_146555_f;
            float var10 = this.field_146570_r * (float)this.field_146557_g;
            this.field_146567_u -= (double)((var9 - var13) * 0.5F);
            this.field_146566_v -= (double)((var10 - var8) * 0.5F);
            this.field_146565_w = this.field_146569_s = this.field_146567_u;
            this.field_146573_x = this.field_146568_t = this.field_146566_v;
         }

         if(this.field_146565_w < (double)field_146572_y) {
            this.field_146565_w = (double)field_146572_y;
         }

         if(this.field_146573_x < (double)field_146571_z) {
            this.field_146573_x = (double)field_146571_z;
         }

         if(this.field_146565_w >= (double)field_146559_A) {
            this.field_146565_w = (double)(field_146559_A - 1);
         }

         if(this.field_146573_x >= (double)field_146560_B) {
            this.field_146573_x = (double)(field_146560_B - 1);
         }

         this.drawDefaultBackground();
         this.drawAchievementScreen(var1, var2, var3);
         GlStateManager.disableLighting();
         GlStateManager.disableDepth();
         this.drawTitle();
         GlStateManager.enableLighting();
         GlStateManager.enableDepth();
      }

   }

   public void doneLoading() {
      if(this.loadingAchievements) {
         this.loadingAchievements = false;
      }

   }

   public void updateScreen() {
      if(!this.loadingAchievements) {
         this.field_146569_s = this.field_146567_u;
         this.field_146568_t = this.field_146566_v;
         double var1 = this.field_146565_w - this.field_146567_u;
         double var3 = this.field_146573_x - this.field_146566_v;
         if(var1 * var1 + var3 * var3 < 4.0D) {
            this.field_146567_u += var1;
            this.field_146566_v += var3;
         } else {
            this.field_146567_u += var1 * 0.85D;
            this.field_146566_v += var3 * 0.85D;
         }
      }

   }

   protected void drawTitle() {
      int var1 = (this.width - this.field_146555_f) / 2;
      int var2 = (this.height - this.field_146557_g) / 2;
      this.fontRendererObj.drawString(I18n.format("gui.achievements", new Object[0]), (float)(var1 + 15), (float)(var2 + 5), 4210752);
   }

   protected void drawAchievementScreen(int var1, int var2, float var3) {
      int var4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * (double)var3);
      int var5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * (double)var3);
      if(var4 < field_146572_y) {
         var4 = field_146572_y;
      }

      if(var5 < field_146571_z) {
         var5 = field_146571_z;
      }

      if(var4 >= field_146559_A) {
         var4 = field_146559_A - 1;
      }

      if(var5 >= field_146560_B) {
         var5 = field_146560_B - 1;
      }

      int var6 = (this.width - this.field_146555_f) / 2;
      int var7 = (this.height - this.field_146557_g) / 2;
      int var8 = var6 + 16;
      int var9 = var7 + 17;
      this.zLevel = 0.0F;
      GlStateManager.depthFunc(518);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var8, (float)var9, -200.0F);
      GlStateManager.scale(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();
      int var10 = var4 + 288 >> 4;
      int var11 = var5 + 288 >> 4;
      int var12 = (var4 + 288) % 16;
      int var13 = (var5 + 288) % 16;
      boolean var14 = true;
      boolean var15 = true;
      boolean var16 = true;
      boolean var17 = true;
      boolean var18 = true;
      Random var19 = new Random();
      float var20 = 16.0F / this.field_146570_r;
      float var21 = 16.0F / this.field_146570_r;

      for(int var22 = 0; (float)var22 * var20 - (float)var13 < 155.0F; ++var22) {
         float var23 = 0.6F - (float)(var11 + var22) / 25.0F * 0.3F;
         GlStateManager.color(var23, var23, var23, 1.0F);

         for(int var24 = 0; (float)var24 * var21 - (float)var12 < 224.0F; ++var24) {
            var19.setSeed((long)(this.mc.getSession().getPlayerID().hashCode() + var10 + var24 + (var11 + var22) * 16));
            int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
            TextureAtlasSprite var26 = this.func_175371_a(Blocks.sand);
            if(var25 <= 37 && var11 + var22 != 35) {
               if(var25 == 22) {
                  if(var19.nextInt(2) == 0) {
                     var26 = this.func_175371_a(Blocks.diamond_ore);
                  } else {
                     var26 = this.func_175371_a(Blocks.redstone_ore);
                  }
               } else if(var25 == 10) {
                  var26 = this.func_175371_a(Blocks.iron_ore);
               } else if(var25 == 8) {
                  var26 = this.func_175371_a(Blocks.coal_ore);
               } else if(var25 > 4) {
                  var26 = this.func_175371_a(Blocks.stone);
               } else {
                  var26 = this.func_175371_a(Blocks.dirt);
               }
            } else {
               Block var27 = Blocks.bedrock;
               var26 = this.func_175371_a(var27);
            }

            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.drawTexturedModalRect(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
         }
      }

      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);

      for(int var33 = 0; var33 < AchievementList.achievementList.size(); ++var33) {
         Achievement var35 = (Achievement)AchievementList.achievementList.get(var33);
         if(var35.parentAchievement != null) {
            int var37 = var35.displayColumn * 24 - var4 + 11;
            int var39 = var35.displayRow * 24 - var5 + 11;
            int var43 = var35.parentAchievement.displayColumn * 24 - var4 + 11;
            int var46 = var35.parentAchievement.displayRow * 24 - var5 + 11;
            boolean var28 = this.statFileWriter.hasAchievementUnlocked(var35);
            this.statFileWriter.canUnlockAchievement(var35);
            int var30 = this.statFileWriter.func_150874_c(var35);
            if(var30 <= 4) {
               int var31 = -16777216;
               var31 = -6250336;
               this.drawHorizontalLine(var37, var43, var39, var31);
               this.drawVerticalLine(var43, var39, var46, var31);
               if(var37 > var43) {
                  this.drawTexturedModalRect(var37 - 11 - 7, var39 - 5, 114, 234, 7, 11);
               } else if(var37 < var43) {
                  this.drawTexturedModalRect(var37 + 11, var39 - 5, 107, 234, 7, 11);
               } else if(var39 > var46) {
                  this.drawTexturedModalRect(var37 - 5, var39 - 11 - 7, 96, 234, 11, 7);
               } else if(var39 < var46) {
                  this.drawTexturedModalRect(var37 - 5, var39 + 11, 96, 241, 11, 7);
               }
            }
         }
      }

      Achievement var34 = null;
      float var36 = (float)(var1 - var8) * this.field_146570_r;
      float var38 = (float)(var2 - var9) * this.field_146570_r;
      RenderHelper.enableGUIStandardItemLighting();
      GlStateManager.disableLighting();
      GlStateManager.enableRescaleNormal();
      GlStateManager.enableColorMaterial();

      for(int var40 = 0; var40 < AchievementList.achievementList.size(); ++var40) {
         Achievement var44 = (Achievement)AchievementList.achievementList.get(var40);
         int var47 = var44.displayColumn * 24 - var4;
         int var49 = var44.displayRow * 24 - var5;
         if(var47 >= -24 && var49 >= -24 && (float)var47 <= 224.0F * this.field_146570_r && (float)var49 <= 155.0F * this.field_146570_r) {
            int var29 = this.statFileWriter.func_150874_c(var44);
            if(this.statFileWriter.hasAchievementUnlocked(var44)) {
               float var52 = 0.75F;
               GlStateManager.color(var52, var52, var52, 1.0F);
            } else if(this.statFileWriter.canUnlockAchievement(var44)) {
               float var53 = 1.0F;
               GlStateManager.color(var53, var53, var53, 1.0F);
            } else if(var29 < 3) {
               float var54 = 0.3F;
               GlStateManager.color(var54, var54, var54, 1.0F);
            } else if(var29 == 3) {
               float var55 = 0.2F;
               GlStateManager.color(var55, var55, var55, 1.0F);
            } else {
               if(var29 != 4) {
                  continue;
               }

               float var56 = 0.1F;
               GlStateManager.color(var56, var56, var56, 1.0F);
            }

            this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
            if(var44.getSpecial()) {
               this.drawTexturedModalRect(var47 - 2, var49 - 2, 26, 202, 26, 26);
            } else {
               this.drawTexturedModalRect(var47 - 2, var49 - 2, 0, 202, 26, 26);
            }

            if(!this.statFileWriter.canUnlockAchievement(var44)) {
               float var57 = 0.1F;
               GlStateManager.color(var57, var57, var57, 1.0F);
               this.itemRender.func_175039_a(false);
            }

            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            this.itemRender.renderItemAndEffectIntoGUI(var44.theItemStack, (float)(var47 + 3), (float)(var49 + 3));
            GlStateManager.blendFunc(770, 771);
            GlStateManager.disableLighting();
            if(!this.statFileWriter.canUnlockAchievement(var44)) {
               this.itemRender.func_175039_a(true);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if(var36 >= (float)var47 && var36 <= (float)(var47 + 22) && var38 >= (float)var49 && var38 <= (float)(var49 + 22)) {
               var34 = var44;
            }
         }
      }

      GlStateManager.disableDepth();
      GlStateManager.enableBlend();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(ACHIEVEMENT_BACKGROUND);
      this.drawTexturedModalRect(var6, var7, 0, 0, this.field_146555_f, this.field_146557_g);
      this.zLevel = 0.0F;
      GlStateManager.depthFunc(515);
      GlStateManager.disableDepth();
      GlStateManager.enableTexture2D();
      super.drawScreen(var1, var2, var3);
      String var41 = var34.getStatName().getUnformattedText();
      String var45 = var34.getDescription();
      int var48 = var1 + 12;
      int var50 = var2 - 4;
      int var51 = this.statFileWriter.func_150874_c(var34);
      if(this.statFileWriter.canUnlockAchievement(var34)) {
         int var58 = Math.max(this.fontRendererObj.d(var41), 120);
         int var62 = this.fontRendererObj.splitStringWidth(var45, var58);
         if(this.statFileWriter.hasAchievementUnlocked(var34)) {
            var62 += 12;
         }

         this.drawGradientRect((float)(var48 - 3), (float)(var50 - 3), (float)(var48 + var58 + 3), (float)(var50 + var62 + 3 + 12), -1073741824, -1073741824);
         this.fontRendererObj.drawSplitString(var45, var48, var50 + 12, var58, -6250336);
         if(this.statFileWriter.hasAchievementUnlocked(var34)) {
            this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), (float)var48, (float)(var50 + var62 + 4), -7302913);
         }
      } else if(var51 == 3) {
         var41 = I18n.format("achievement.unknown", new Object[0]);
         int var59 = Math.max(this.fontRendererObj.d(var41), 120);
         String var63 = (new ChatComponentTranslation("achievement.requires", new Object[]{var34.parentAchievement.getStatName()})).getUnformattedText();
         int var32 = this.fontRendererObj.splitStringWidth(var63, var59);
         this.drawGradientRect((float)(var48 - 3), (float)(var50 - 3), (float)(var48 + var59 + 3), (float)(var50 + var32 + 12 + 3), -1073741824, -1073741824);
         this.fontRendererObj.drawSplitString(var63, var48, var50 + 12, var59, -9416624);
      } else if(var51 < 3) {
         int var60 = Math.max(this.fontRendererObj.d(var41), 120);
         String var64 = (new ChatComponentTranslation("achievement.requires", new Object[]{var34.parentAchievement.getStatName()})).getUnformattedText();
         int var65 = this.fontRendererObj.splitStringWidth(var64, var60);
         this.drawGradientRect((float)(var48 - 3), (float)(var50 - 3), (float)(var48 + var60 + 3), (float)(var50 + var65 + 12 + 3), -1073741824, -1073741824);
         this.fontRendererObj.drawSplitString(var64, var48, var50 + 12, var60, -9416624);
      } else {
         var41 = null;
      }

      this.fontRendererObj.drawStringWithShadow(var41, (float)var48, (float)var50, this.statFileWriter.canUnlockAchievement(var34)?(var34.getSpecial()?-128:-1):(var34.getSpecial()?-8355776:-8355712));
      GlStateManager.enableDepth();
      GlStateManager.enableLighting();
      RenderHelper.disableStandardItemLighting();
   }

   private TextureAtlasSprite func_175371_a(Block var1) {
      return Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(var1.getDefaultState());
   }

   public boolean doesGuiPauseGame() {
      return !this.loadingAchievements;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
