package net.minecraft.client.gui;

import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.a6I;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.IScoreObjectiveCriteria$EnumRenderType;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings$GameType;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class GuiPlayerTabOverlay extends Gui {
   public static String j;
   private static final Ordering o = Ordering.from(new a6I());
   public static boolean l = false;
   private final Minecraft mc;
   private final GuiIngame guiIngame;
   private IChatComponent footer;
   private IChatComponent header;
   private int h = 20;
   private int n = 80;
   private boolean m = false;
   private long lastTimeOpened;
   private boolean isBeingRendered;

   public GuiPlayerTabOverlay(Minecraft var1, GuiIngame var2) {
      this.mc = var1;
      this.guiIngame = var2;
   }

   public String getPlayerName(@NotNull NetworkPlayerInfo var1) {
      String var2 = var1.getDisplayName() != null?var1.getDisplayName().getFormattedText():ScorePlayerTeam.formatPlayerName(var1.getPlayerTeam(), var1.getGameProfile().getName());
      return var2;
   }

   public void updatePlayerList(boolean var1) {
      if(!this.isBeingRendered) {
         this.lastTimeOpened = Minecraft.getSystemTime();
      }

      this.isBeingRendered = var1;
   }

   public List c() {
      return o.sortedCopy(this.mc.getNetHandler().getPlayerInfoMap());
   }

   public List b() {
      return (List)o.sortedCopy(this.mc.getNetHandler().getPlayerInfoMap()).stream().map(this::lambda$getListPlayers$0).collect(Collectors.toList());
   }

   public void renderPlayerlist(int var1, Scoreboard var2, ScoreObjective var3) {
      List var4 = this.c();
      int var5 = 0;
      int var6 = 0;

      for(NetworkPlayerInfo var8 : var4) {
         int var9 = this.mc.fontRendererObj.d(this.getPlayerName(var8));
         var5 = Math.max(var5, var9);
         if(var3.getRenderType() != IScoreObjectiveCriteria$EnumRenderType.HEARTS) {
            var9 = this.mc.fontRendererObj.d(" " + var2.getValueFromObjective(var8.getGameProfile().getName(), var3).getScorePoints());
            var6 = Math.max(var6, var9);
         }
      }

      int var36 = Math.max(this.n, 5);
      var4 = var4.subList(0, Math.min(var4.size(), var36));
      int var37;
      int var39 = var37 = var4.size();
      int var10 = Math.max(this.h, 5);

      int var11;
      for(var11 = 1; var37 > var10; var37 = (var39 + var11 - 1) / var11) {
         ++var11;
      }

      boolean var12 = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
      int var13;
      if(var3.getRenderType() == IScoreObjectiveCriteria$EnumRenderType.HEARTS) {
         var13 = 90;
      } else {
         var13 = var6;
      }

      int var14 = Math.min(var11 * (9 + var5 + var13 + 13), var1 - 50) / var11;
      int var15 = var1 / 2 - (var14 * var11 + (var11 - 1) * 5) / 2;
      int var16 = 10;
      int var17 = var14 * var11 + (var11 - 1) * 5;
      List var18 = null;
      List var19 = null;
      if(this.header != null) {
         var18 = this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), var1 - 50);

         for(String var21 : var18) {
            var17 = Math.max(var17, this.mc.fontRendererObj.d(var21));
         }
      }

      if(this.footer != null) {
         var19 = this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), var1 - 50);

         for(String var46 : var19) {
            var17 = Math.max(var17, this.mc.fontRendererObj.d(var46));
         }
      }

      drawRect(var1 / 2 - var17 / 2 - 1, var16 - 1, var1 / 2 + var17 / 2 + 1, var16 + var18.size() * this.mc.fontRendererObj.f(), this.m?855638016:Integer.MIN_VALUE);

      for(String var47 : var18) {
         int var22 = this.mc.fontRendererObj.d(var47);
         this.mc.fontRendererObj.drawStringWithShadow(var47, (float)(var1 / 2 - var22 / 2), (float)var16, this.m?1308622847:-1);
         var16 += this.mc.fontRendererObj.f();
      }

      ++var16;
      drawRect(var1 / 2 - var17 / 2 - 1, var16 - 1, var1 / 2 + var17 / 2 + 1, var16 + var37 * 9, this.m?855638016:Integer.MIN_VALUE);

      for(int var44 = 0; var44 < var39; ++var44) {
         int var48 = var44 / var37;
         int var50 = var44 % var37;
         int var23 = var15 + var48 * var14 + var48 * 5;
         int var24 = var16 + var50 * 9;
         drawRect(var23, var24, var23 + var14, var24 + 8, 553648127);
         GlStateManager.color(1.0F, 1.0F, 1.0F, this.m?0.3F:1.0F);
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if(var44 < var4.size()) {
            NetworkPlayerInfo var25 = (NetworkPlayerInfo)var4.get(var44);
            String var26 = this.getPlayerName(var25);
            GameProfile var27 = var25.getGameProfile();
            EntityPlayer var28 = this.mc.theWorld.getPlayerEntityByUUID(var27.getId());
            if(!var28.isWearing(EnumPlayerModelParts.CAPE) || !var27.getName().equals("Dinnerbone") && !var27.getName().equals("Grumm")) {
               boolean var55 = false;
            } else {
               boolean var10000 = true;
            }

            this.mc.ab().a(var25.getLocationSkin());
            boolean var56 = true;
            boolean var10001 = true;
            byte var30 = 16;
            var10001 = true;
            boolean var10002 = true;
            byte var31 = -8;
            Gui.a(var23, var24, 8.0F, (float)var30, 8, var31, 8, 8, 64.0F, 64.0F);
            if(var28.isWearing(EnumPlayerModelParts.HAT)) {
               var10002 = true;
               boolean var10003 = true;
               byte var32 = 16;
               var10003 = true;
               boolean var10004 = true;
               byte var33 = -8;
               Gui.a(var23, var24, 40.0F, (float)var32, 8, var33, 8, 8, 64.0F, 64.0F);
            }

            var23 = var23 + 9;
            if(var25.getGameType() == WorldSettings$GameType.SPECTATOR) {
               var26 = EnumChatFormatting.ITALIC + var26;
               this.mc.fontRendererObj.drawStringWithShadow(var26, (float)var23, (float)var24, this.m?872415231:-1862270977);
            } else {
               this.mc.fontRendererObj.drawStringWithShadow(var26, (float)var23, (float)var24, this.m?1895825407:-1);
            }

            if(var25.getGameType() != WorldSettings$GameType.SPECTATOR) {
               int var54 = var23 + var5 + 1;
               int var29 = var54 + var13;
               if(var29 - var54 > 5) {
                  GuiPlayerTabOverlay var59 = this;
                  ScoreObjective var61 = var3;
                  int var62 = var24;
                  GameProfile var10005 = var27;

                  try {
                     var59.drawScoreboardValues(var61, var62, var10005.getName(), var54, var29, var25);
                  } catch (Exception var34) {
                     var34.printStackTrace();
                  }
               }
            }

            this.drawPing(var14, var23 - 9, var24, var25);
         }
      }

      var16 = var16 + var37 * 9 + 1;
      drawRect(var1 / 2 - var17 / 2 - 1, var16 - 1, var1 / 2 + var17 / 2 + 1, var16 + var19.size() * this.mc.fontRendererObj.f(), this.m?855638016:Integer.MIN_VALUE);

      for(String var49 : var19) {
         int var51 = this.mc.fontRendererObj.d(var49);
         this.mc.fontRendererObj.drawStringWithShadow(var49, (float)(var1 / 2 - var51 / 2), (float)var16, this.m?1308622847:-1);
         var16 += this.mc.fontRendererObj.f();
      }

   }

   protected void drawPing(int var1, int var2, int var3, NetworkPlayerInfo var4) {
      int var5 = var4.getResponseTime();
      ServerData var6 = this.mc.getCurrentServerData();
      this.zLevel += 100.0F;
      if(l) {
         int var7;
         if(var5 > 500) {
            var7 = 11141120;
         } else if(var5 > 300) {
            var7 = 11184640;
         } else if(var5 > 200) {
            var7 = 11193344;
         } else if(var5 > 135) {
            var7 = 2128640;
         } else if(var5 > 70) {
            var7 = '餀';
         } else {
            var7 = '묀';
         }

         if(var5 < 10000) {
            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            int var8 = var2 + var1 - (this.mc.fontRendererObj.d(String.valueOf(var5)) >> 1) - 2;
            int var9 = var3 + (this.mc.fontRendererObj.f() >> 2);
            this.mc.fontRendererObj.drawStringWithShadow(Integer.toString(var5), (float)(2 * var8), (float)(2 * var9), var7);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glPopMatrix();
         }
      } else {
         GlStateManager.color(1.0F, 1.0F, 1.0F, this.m?0.3F:1.0F);
         this.mc.ab().a(icons);
         byte var10 = 5;
         this.drawTexturedModalRect(var2 + var1 - 11, var3, 0, 176 + var10 * 8, 10, 8);
      }

      this.zLevel -= 100.0F;
   }

   private void drawScoreboardValues(@NotNull ScoreObjective var1, int var2, String var3, int var4, int var5, NetworkPlayerInfo var6) {
      int var7 = var1.getScoreboard().getValueFromObjective(var3, var1).getScorePoints();
      if(var1.getRenderType() == IScoreObjectiveCriteria$EnumRenderType.HEARTS) {
         this.mc.ab().a(icons);
         if(this.lastTimeOpened == var6.k()) {
            if(var7 < var6.h()) {
               var6.c(Minecraft.getSystemTime());
               var6.a((long)(this.guiIngame.getUpdateCounter() + 20));
            } else if(var7 > var6.h()) {
               var6.c(Minecraft.getSystemTime());
               var6.a((long)(this.guiIngame.getUpdateCounter() + 10));
            }
         }

         if(Minecraft.getSystemTime() - var6.g() > 1000L || this.lastTimeOpened != var6.k()) {
            var6.b(var7);
            var6.c(var7);
            var6.c(Minecraft.getSystemTime());
         }

         var6.b(this.lastTimeOpened);
         var6.b(var7);
         int var8 = MathHelper.ceiling_float_int((float)Math.max(var7, var6.e()) / 2.0F);
         int var9 = Math.max(MathHelper.ceiling_float_int((float)(var7 / 2)), Math.max(MathHelper.ceiling_float_int((float)(var6.e() / 2)), 10));
         if(var6.o() > (long)this.guiIngame.getUpdateCounter() && (var6.o() - (long)this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L) {
            boolean var18 = true;
         } else {
            boolean var10000 = false;
         }

         float var11 = Math.min((float)(var5 - var4 - 4) / (float)var9, 9.0F);
         if(var11 > 3.0F) {
            for(int var12 = var8; var12 < var9; ++var12) {
               this.drawTexturedModalRect((float)var4 + (float)var12 * var11, (float)var2, 25, 0, 9, 9);
            }

            for(int var16 = 0; var16 < var8; ++var16) {
               this.drawTexturedModalRect((float)var4 + (float)var16 * var11, (float)var2, 25, 0, 9, 9);
               if(var16 * 2 + 1 < var6.e()) {
                  this.drawTexturedModalRect((float)var4 + (float)var16 * var11, (float)var2, 70, 0, 9, 9);
               }

               if(var16 * 2 + 1 == var6.e()) {
                  this.drawTexturedModalRect((float)var4 + (float)var16 * var11, (float)var2, 79, 0, 9, 9);
               }

               if(var16 * 2 + 1 < var7) {
                  this.drawTexturedModalRect((float)var4 + (float)var16 * var11, (float)var2, var16 >= 10?160:52, 0, 9, 9);
               }

               if(var16 * 2 + 1 == var7) {
                  this.drawTexturedModalRect((float)var4 + (float)var16 * var11, (float)var2, var16 >= 10?169:61, 0, 9, 9);
               }
            }
         } else {
            float var17 = MathHelper.a((float)var7 / 20.0F, 0.0F, 1.0F);
            int var13 = (int)((1.0F - var17) * 255.0F) << 16 | (int)(var17 * 255.0F) << 8;
            String var14 = "" + (float)var7 / 2.0F;
            if(var5 - this.mc.fontRendererObj.d(var14 + "hp") >= var4) {
               var14 = var14 + "hp";
            }

            this.mc.fontRendererObj.drawStringWithShadow(var14, (float)((var5 + var4) / 2 - this.mc.fontRendererObj.d(var14) / 2), (float)var2, var13);
         }
      } else {
         String var15 = EnumChatFormatting.YELLOW + "" + var7;
         this.mc.fontRendererObj.drawStringWithShadow(var15, (float)(var5 - this.mc.fontRendererObj.d(var15)), (float)var2, 16777215);
      }

   }

   public void setFooter(IChatComponent var1) {
      this.footer = var1;
   }

   public void setHeader(IChatComponent var1) {
      this.header = var1;
   }

   public void func_181030_a() {
      this.header = null;
      this.footer = null;
   }

   private EntityPlayer lambda$getListPlayers$0(NetworkPlayerInfo var1) {
      return this.mc.theWorld.getPlayerEntityByUUID(var1.getGameProfile().getId());
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
