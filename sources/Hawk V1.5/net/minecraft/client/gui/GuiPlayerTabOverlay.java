package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

public class GuiPlayerTabOverlay extends Gui {
   private IChatComponent header;
   private final GuiIngame field_175251_g;
   private IChatComponent footer;
   private final Minecraft field_175250_f;
   private static final Ordering field_175252_a = Ordering.from(new GuiPlayerTabOverlay.PlayerComparator((Object)null));
   private long field_175253_j;
   private static final String __OBFID = "CL_00001943";
   private boolean field_175254_k;

   public void setHeader(IChatComponent var1) {
      this.header = var1;
   }

   public void setFooter(IChatComponent var1) {
      this.footer = var1;
   }

   private void func_175247_a(ScoreObjective var1, int var2, String var3, int var4, int var5, NetworkPlayerInfo var6) {
      int var7 = var1.getScoreboard().getValueFromObjective(var3, var1).getScorePoints();
      if (var1.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
         this.field_175250_f.getTextureManager().bindTexture(icons);
         if (this.field_175253_j == var6.func_178855_p()) {
            if (var7 < var6.func_178835_l()) {
               var6.func_178846_a(Minecraft.getSystemTime());
               var6.func_178844_b((long)(this.field_175251_g.getUpdateCounter() + 20));
            } else if (var7 > var6.func_178835_l()) {
               var6.func_178846_a(Minecraft.getSystemTime());
               var6.func_178844_b((long)(this.field_175251_g.getUpdateCounter() + 10));
            }
         }

         if (Minecraft.getSystemTime() - var6.func_178847_n() > 1000L || this.field_175253_j != var6.func_178855_p()) {
            var6.func_178836_b(var7);
            var6.func_178857_c(var7);
            var6.func_178846_a(Minecraft.getSystemTime());
         }

         var6.func_178843_c(this.field_175253_j);
         var6.func_178836_b(var7);
         int var8 = MathHelper.ceiling_float_int((float)Math.max(var7, var6.func_178860_m()) / 2.0F);
         int var9 = Math.max(MathHelper.ceiling_float_int((float)(var7 / 2)), Math.max(MathHelper.ceiling_float_int((float)(var6.func_178860_m() / 2)), 10));
         boolean var10 = var6.func_178858_o() > (long)this.field_175251_g.getUpdateCounter() && (var6.func_178858_o() - (long)this.field_175251_g.getUpdateCounter()) / 3L % 2L == 1L;
         if (var8 > 0) {
            float var11 = Math.min((float)(var5 - var4 - 4) / (float)var9, 9.0F);
            if (var11 > 3.0F) {
               int var12;
               for(var12 = var8; var12 < var9; ++var12) {
                  this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, var10 ? 25 : 16, 0, 9, 9);
               }

               for(var12 = 0; var12 < var8; ++var12) {
                  this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, var10 ? 25 : 16, 0, 9, 9);
                  if (var10) {
                     if (var12 * 2 + 1 < var6.func_178860_m()) {
                        this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, 70, 0, 9, 9);
                     }

                     if (var12 * 2 + 1 == var6.func_178860_m()) {
                        this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, 79, 0, 9, 9);
                     }
                  }

                  if (var12 * 2 + 1 < var7) {
                     this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, var12 >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (var12 * 2 + 1 == var7) {
                     this.func_175174_a((float)var4 + (float)var12 * var11, (float)var2, var12 >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float var16 = MathHelper.clamp_float((float)var7 / 20.0F, 0.0F, 1.0F);
               int var13 = (int)((1.0F - var16) * 255.0F) << 16 | (int)(var16 * 255.0F) << 8;
               String var14 = String.valueOf((new StringBuilder()).append((float)var7 / 2.0F));
               if (var5 - this.field_175250_f.fontRendererObj.getStringWidth(String.valueOf((new StringBuilder(String.valueOf(var14))).append("hp"))) >= var4) {
                  var14 = String.valueOf((new StringBuilder(String.valueOf(var14))).append("hp"));
               }

               this.field_175250_f.fontRendererObj.drawStringWithShadow(var14, (double)((float)((var5 + var4) / 2 - this.field_175250_f.fontRendererObj.getStringWidth(var14) / 2)), (double)((float)var2), var13);
            }
         }
      } else {
         String var15 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.YELLOW).append(var7));
         this.field_175250_f.fontRendererObj.drawStringWithShadow(var15, (double)((float)(var5 - this.field_175250_f.fontRendererObj.getStringWidth(var15))), (double)((float)var2), 16777215);
      }

   }

   public void func_175246_a(boolean var1) {
      if (var1 && !this.field_175254_k) {
         this.field_175253_j = Minecraft.getSystemTime();
      }

      this.field_175254_k = var1;
   }

   public void func_175249_a(int var1, Scoreboard var2, ScoreObjective var3) {
      NetHandlerPlayClient var4 = this.field_175250_f.thePlayer.sendQueue;
      List var5 = field_175252_a.sortedCopy(var4.func_175106_d());
      int var6 = 0;
      int var7 = 0;
      Iterator var8 = var5.iterator();

      int var9;
      while(var8.hasNext()) {
         NetworkPlayerInfo var10 = (NetworkPlayerInfo)var8.next();
         var9 = this.field_175250_f.fontRendererObj.getStringWidth(this.func_175243_a(var10));
         var6 = Math.max(var6, var9);
         if (var3 != null && var3.func_178766_e() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            var9 = this.field_175250_f.fontRendererObj.getStringWidth(String.valueOf((new StringBuilder(" ")).append(var2.getValueFromObjective(var10.func_178845_a().getName(), var3).getScorePoints())));
            var7 = Math.max(var7, var9);
         }
      }

      var5 = var5.subList(0, Math.min(var5.size(), 80));
      int var31 = var5.size();
      int var11 = var31;

      for(var9 = 1; var11 > 20; var11 = (var31 + var9 - 1) / var9) {
         ++var9;
      }

      boolean var12 = this.field_175250_f.isIntegratedServerRunning() || this.field_175250_f.getNetHandler().getNetworkManager().func_179292_f();
      int var13;
      if (var3 != null) {
         if (var3.func_178766_e() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            var13 = 90;
         } else {
            var13 = var7;
         }
      } else {
         var13 = 0;
      }

      int var14 = Math.min(var9 * ((var12 ? 9 : 0) + var6 + var13 + 13), var1 - 50) / var9;
      int var15 = var1 / 2 - (var14 * var9 + (var9 - 1) * 5) / 2;
      int var16 = 10;
      int var17 = var14 * var9 + (var9 - 1) * 5;
      List var18 = null;
      List var19 = null;
      Iterator var20;
      String var21;
      if (this.header != null) {
         var18 = this.field_175250_f.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), var1 - 50);

         for(var20 = var18.iterator(); var20.hasNext(); var17 = Math.max(var17, this.field_175250_f.fontRendererObj.getStringWidth(var21))) {
            var21 = (String)var20.next();
         }
      }

      if (this.footer != null) {
         var19 = this.field_175250_f.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), var1 - 50);

         for(var20 = var19.iterator(); var20.hasNext(); var17 = Math.max(var17, this.field_175250_f.fontRendererObj.getStringWidth(var21))) {
            var21 = (String)var20.next();
         }
      }

      int var22;
      if (var18 != null) {
         drawRect((double)(var1 / 2 - var17 / 2 - 1), (double)(var16 - 1), (double)(var1 / 2 + var17 / 2 + 1), (double)(var16 + var18.size() * this.field_175250_f.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);

         for(var20 = var18.iterator(); var20.hasNext(); var16 += this.field_175250_f.fontRendererObj.FONT_HEIGHT) {
            var21 = (String)var20.next();
            var22 = this.field_175250_f.fontRendererObj.getStringWidth(var21);
            this.field_175250_f.fontRendererObj.drawStringWithShadow(var21, (double)((float)(var1 / 2 - var22 / 2)), (double)((float)var16), -1);
         }

         ++var16;
      }

      drawRect((double)(var1 / 2 - var17 / 2 - 1), (double)(var16 - 1), (double)(var1 / 2 + var17 / 2 + 1), (double)(var16 + var11 * 9), Integer.MIN_VALUE);

      for(int var23 = 0; var23 < var31; ++var23) {
         int var24 = var23 / var11;
         var22 = var23 % var11;
         int var25 = var15 + var24 * var14 + var24 * 5;
         int var26 = var16 + var22 * 9;
         drawRect((double)var25, (double)var26, (double)(var25 + var14), (double)(var26 + 8), 553648127);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (var23 < var5.size()) {
            NetworkPlayerInfo var27 = (NetworkPlayerInfo)var5.get(var23);
            String var28 = this.func_175243_a(var27);
            if (var12) {
               this.field_175250_f.getTextureManager().bindTexture(var27.func_178837_g());
               Gui.drawScaledCustomSizeModalRect(var25, var26, 8.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
               EntityPlayer var29 = this.field_175250_f.theWorld.getPlayerEntityByUUID(var27.func_178845_a().getId());
               if (var29 != null && var29.func_175148_a(EnumPlayerModelParts.HAT)) {
                  Gui.drawScaledCustomSizeModalRect(var25, var26, 40.0F, 8.0F, 8, 8, 8, 8, 64.0F, 64.0F);
               }

               var25 += 9;
            }

            if (var27.getGameType() == WorldSettings.GameType.SPECTATOR) {
               var28 = String.valueOf((new StringBuilder()).append(EnumChatFormatting.ITALIC).append(var28));
               this.field_175250_f.fontRendererObj.drawStringWithShadow(var28, (double)((float)var25), (double)((float)var26), -1862270977);
            } else {
               this.field_175250_f.fontRendererObj.drawStringWithShadow(var28, (double)((float)var25), (double)((float)var26), -1);
            }

            if (var3 != null && var27.getGameType() != WorldSettings.GameType.SPECTATOR) {
               int var32 = var25 + var6 + 1;
               int var30 = var32 + var13;
               if (var30 - var32 > 5) {
                  this.func_175247_a(var3, var26, var27.func_178845_a().getName(), var32, var30, var27);
               }
            }

            this.func_175245_a(var14, var25 - (var12 ? 9 : 0), var26, var27);
         }
      }

      if (var19 != null) {
         var16 += var11 * 9 + 1;
         drawRect((double)(var1 / 2 - var17 / 2 - 1), (double)(var16 - 1), (double)(var1 / 2 + var17 / 2 + 1), (double)(var16 + var19.size() * this.field_175250_f.fontRendererObj.FONT_HEIGHT), Integer.MIN_VALUE);

         for(var20 = var19.iterator(); var20.hasNext(); var16 += this.field_175250_f.fontRendererObj.FONT_HEIGHT) {
            var21 = (String)var20.next();
            var22 = this.field_175250_f.fontRendererObj.getStringWidth(var21);
            this.field_175250_f.fontRendererObj.drawStringWithShadow(var21, (double)((float)(var1 / 2 - var22 / 2)), (double)((float)var16), -1);
         }
      }

   }

   public GuiPlayerTabOverlay(Minecraft var1, GuiIngame var2) {
      this.field_175250_f = var1;
      this.field_175251_g = var2;
   }

   protected void func_175245_a(int var1, int var2, int var3, NetworkPlayerInfo var4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.field_175250_f.getTextureManager().bindTexture(icons);
      byte var5 = 0;
      boolean var6 = false;
      byte var7;
      if (var4.getResponseTime() < 0) {
         var7 = 5;
      } else if (var4.getResponseTime() < 150) {
         var7 = 0;
      } else if (var4.getResponseTime() < 300) {
         var7 = 1;
      } else if (var4.getResponseTime() < 600) {
         var7 = 2;
      } else if (var4.getResponseTime() < 1000) {
         var7 = 3;
      } else {
         var7 = 4;
      }

      this.zLevel += 100.0F;
      this.drawTexturedModalRect(var2 + var1 - 11, var3, 0 + var5 * 10, 176 + var7 * 8, 10, 8);
      this.zLevel -= 100.0F;
   }

   public String func_175243_a(NetworkPlayerInfo var1) {
      return var1.func_178854_k() != null ? var1.func_178854_k().getFormattedText() : ScorePlayerTeam.formatPlayerName(var1.func_178850_i(), var1.func_178845_a().getName());
   }

   static class PlayerComparator implements Comparator {
      private static final String __OBFID = "CL_00001941";

      PlayerComparator(Object var1) {
         this();
      }

      public int compare(Object var1, Object var2) {
         return this.func_178952_a((NetworkPlayerInfo)var1, (NetworkPlayerInfo)var2);
      }

      private PlayerComparator() {
      }

      public int func_178952_a(NetworkPlayerInfo var1, NetworkPlayerInfo var2) {
         ScorePlayerTeam var3 = var1.func_178850_i();
         ScorePlayerTeam var4 = var2.func_178850_i();
         return ComparisonChain.start().compareTrueFirst(var1.getGameType() != WorldSettings.GameType.SPECTATOR, var2.getGameType() != WorldSettings.GameType.SPECTATOR).compare(var3 != null ? var3.getRegisteredName() : "", var4 != null ? var4.getRegisteredName() : "").compare(var1.func_178845_a().getName(), var2.func_178845_a().getName()).result();
      }
   }
}
