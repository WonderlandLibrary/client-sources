package net.augustus.ui;

import static net.augustus.utils.interfaces.MC.mc;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.sun.javafx.geom.Vec2d;

import net.augustus.Augustus;
import net.augustus.Augustus.ClientBrand;
import net.augustus.font.CustomFontUtil;
import net.augustus.font.UnicodeFontRenderer;
import net.augustus.font.testfontbase.FontUtil;
import net.augustus.modules.Module;
import net.augustus.modules.combat.KillAura;
import net.augustus.notify.reborn.CleanNotificationManager;
import net.augustus.notify.rise5.Notification;
import net.augustus.notify.rise5.NotificationManager;
import net.augustus.notify.xenza.NotificationsManager;
import net.augustus.utils.GuiUtils;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.RainbowUtil;
import net.augustus.utils.RenderUtil;
import net.augustus.utils.ResourceUtil;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.lorious.RectUtils;
import net.augustus.utils.skid.lorious.anims.Easings;
import net.augustus.utils.skid.ohare.font.Fonts;
import net.augustus.utils.skid.vestige.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class GuiIngameHook extends GuiIngame implements MM, MC {
   private final boolean leftSide = false;
   private final int lastWidth = 0;
   private float yAdd = 0.0F;
   public static float lastArrayListY;
   private final RainbowUtil rainbowUtil = new RainbowUtil();
   public static CustomFontUtil customFont;
   private ResourceLocation augustusResourceLocation = null;
   private ResourceLocation amogusResourceLocation = null;
   private static UnicodeFontRenderer ryuFontRenderer;
   private static UnicodeFontRenderer oldFontRenderer;
   private static UnicodeFontRenderer vegaFontRenderer;
   public float alpha = 4.0F;
   public float rectSize = 1.0F;
   public float saturationDelay = 1F;
   public boolean underLine = true;


   static {
      try {
         ryuFontRenderer = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/psr.ttf")).deriveFont(20F));
      } catch (Exception e) {
         e.printStackTrace();
      }
      try {
         oldFontRenderer = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/Comfortaa-Bold.ttf")).deriveFont(56F));
      } catch (Exception e) {
         e.printStackTrace();
      }
      try {
         vegaFontRenderer = new UnicodeFontRenderer(Font.createFont(0, GuiIngameHook.class.getResourceAsStream("/ressources/roboto.ttf")).deriveFont(18F));
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private int yOff;


   public static Color getChroma() {
      Color color = new Color(Color.HSBtoRGB(((float)System.nanoTime() + -0.0F) / 2.0F / 1.0E9F, 1.0F, 1.0F));
      return new Color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
   }
   public GuiIngameHook(Minecraft client) {
      super(client);
      augustusResourceLocation = ResourceUtil.loadResourceLocation("pictures/augustus.png", "augustus");
      amogusResourceLocation = ResourceUtil.loadResourceLocation("pictures/amogus.png", "amogus");
      customFont = new CustomFontUtil("Verdana", 16);
   }

   @Override
   public void renderGameOverlay(float partialTicks) {
      super.renderGameOverlay(partialTicks);
      ScaledResolution sr = new ScaledResolution(mc);
      if (mm.notifications.isToggled()) {
         switch (mm.notifications.mode.getSelected()) {
            case "Xenza": {
               NotificationsManager.renderNotifications();
               NotificationsManager.update();
               break;
            }
            case "New": {
               CleanNotificationManager.renderCleanNotifications();
               CleanNotificationManager.update();
               break;
            }
            case "Rise5": {
               if (!NotificationManager.notifications.isEmpty()) {
                  if (NotificationManager.notifications.getFirst().getEnd() > System.currentTimeMillis()) {
                     NotificationManager.notifications.getFirst().y = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 50;
                     NotificationManager.notifications.getFirst().render();
                  } else {
                     NotificationManager.notifications.removeFirst();
                  }
               }

               if (NotificationManager.notifications.size() > 0) {
                  int i = 0;
                  try {
                     for (final Notification notification : NotificationManager.notifications) {
                        if (i == 0) {
                           i++;
                           continue;
                        }

                        notification.y = (new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 18) - (35 * (i + 1));
                        notification.render();
                        i++;
                     }
                  } catch (final
                  ConcurrentModificationException ignored) {
                  }
               }
               break;
            }
         }
      }
      if (mm.arrayList.isToggled()) {
         switch (mm.arrayList.mode.getSelected()) {
            case "New": {
               double y = 0.0;
               mm.getModules().sort(Comparator.comparingDouble(module -> -Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module.getDisplayName())));
               int index = 0;
               for (Module module2 : mm.getModules()) {
                  module2.getXPos().updateAnimation();
                  module2.getYPos().updateAnimation();
                  module2.updatePos();
                  if (module2.getYPos().getTarget() != 5.0 + y) {
                     module2.getYPos().animate(5.0 + y, 350.0, Easings.QUAD_BOTH);
                  }
                  if (module2.getXPos().getValue() <= -5.0) continue;
                  Color clr = ColorUtils.getGradientOffset(new Color(255, 255, 255), ColorUtils.getRainbow(4.0f, 0.5f, 1.0f), (double)Math.abs(System.currentTimeMillis() / 16L) / 50.0 + (double)index * 0.25 - 0.25);
                  Color clr1 = ColorUtils.getGradientOffset(new Color(255, 255, 255), ColorUtils.getRainbow(4.0f, 0.5f, 1.0f), (double)Math.abs(System.currentTimeMillis() / 16L) / 50.0 + (double)index * 0.25);
                  if(mm.arrayList.blur.getBoolean())
                	  BlurUtil.blur((float)(sr.getScaledWidth() - module2.getXPos().getValue() - 8.0), (float)module2.getYPos().getValue() + 0.1f, (float)(sr.getScaledWidth() + Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module2.getDisplayName()) - module2.getXPos().getValue()), (float)(module2.getYPos().getValue() + 4.0 + Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight()) + 2.5f);
                  RectUtils.drawRect((double)sr.getScaledWidth() - module2.getXPos().getValue() - 8.0, module2.getYPos().getValue(), (double)sr.getScaledWidth() + Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module2.getDisplayName()) - module2.getXPos().getValue(), module2.getYPos().getValue() + 4.0 + (double)Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() + 2.0, new Color(0, 0, 0, 125).getRGB());
                  RectUtils.drawGradientRect((double)sr.getScaledWidth() + Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module2.getDisplayName()) - module2.getXPos().getValue() - 2.0, module2.getYPos().getValue(), (double)sr.getScaledWidth() + Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module2.getDisplayName()) - module2.getXPos().getValue(), module2.getYPos().getValue() + 4.0 + (double)Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() + 2.0, clr.getRGB(), clr1.getRGB());
                  Augustus.getInstance().getLoriousFontService().getComfortaa18().drawStringWithShadow(module2.getName(), (double)(sr.getScaledWidth() - 5) - module2.getXPos().getValue(), (float)(module2.getYPos().getValue() + 2.0 + (double)(Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() / 2)), -1, true, clr, clr1);
                  Augustus.getInstance().getLoriousFontService().getComfortaa18().drawStringWithShadow(module2.getSuffix(), (double)(sr.getScaledWidth() - 5) - module2.getXPos().getValue() + Augustus.getInstance().getLoriousFontService().getComfortaa18().getStringWidth(module2.getName()) + 2.0, (float)(module2.getYPos().getValue() + 2.0 + (double)(Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() / 2)), new Color(200, 200, 200).getRGB(), false, clr, clr1);
                  y += Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() + 6;
                  ++index;
               }

               break;
            }
            case "Default": {
               mm.arrayList.renderArrayList();
               break;
            }
            case "Vega": {
               byte b1 = 0;
               if(mm.arrayList.sideOption.getSelected().equals("Left")) {
                  switch (mm.hud.mode.getSelected()) {
                     case "Ryu": {
                        b1 = (byte) (vegaFontRenderer.getStringHeight("ALLAH") + 5F);
                        break;
                     }
                     case "Old": {
                        b1 = (byte) 48F;
                        break;
                     }
                     case "Other": {
                        b1 = (byte) ((double) ((float) vegaFontRenderer.getStringHeight("ALLAH") * 1.75F + 4.0F + 1.0F) * 1.5);
                        break;
                     }
                     case "Basic": {
                        b1 = (byte) (vegaFontRenderer.getStringHeight("ALLAH") + 1);
                        break;
                     }
                  }
               }
               byte b2;

               //ArrayList<Module> arrayList = (ArrayList<Module>) mm.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingDouble(paramModule -> -vegaFontRenderer.getStringWidth(paramModule.getDisplayName()))).collect(Collectors.toList());
               ArrayList<Module> arrayList = (ArrayList<Module>) mm.getModules().stream().filter(Module::isToggled).sorted(Comparator.comparingDouble(paramModule -> -vegaFontRenderer.getStringWidth(paramModule.getName()))).collect(Collectors.toList());
               for (b2 = 0; b2 < arrayList.size(); b2++) {
                  Module module1 = arrayList.get(b2);
                  Module module2 = arrayList.get(b2 + ((b2 == arrayList.size() - 1) ? 0 : 1));
                  //int i = Augustus.getInstance().getColorUtil().getSaturationFadeColor(color, (int)(b1 * this.saturationDelay), 100.0F).getRGB();
                  int i = ColorUtil.getColor(mm.arrayList.vegaColor1.getColor(), mm.arrayList.vegaColor2.getColor(), 1000, 100*b2);
                  Gui.drawRect(sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module1.getName()) - 5, b1 * 13, sr.getScaledWidth(), b1 * 13 + 13, (new Color(0.0F, 0.0F, 0.0F, this.alpha / 10.0F)).getRGB());
                  //BlurUtil.blur(sr.getScaledWidth() - (int)vegaFontRenderer.getStringWidth(module1.getName()) - 5, b1 * 13, sr.getScaledWidth(), b1 * 13 + 13);
                  Gui.drawRect((sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module1.getName()) - 6), (this.underLine) ? (b1 * 13 + 1) : (b1 * 13), (sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module1.getName()) - 5), b1 * 13 + 13, i);
                  Gui.drawRect(sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module1.getName()) - 6, b1 * 13 + 13, (module2 != module1) ? (sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module2.getName()) - 5) : sr.getScaledWidth(), b1 * 13 + 14, i);
                  vegaFontRenderer.drawString(module1.getName(), sr.getScaledWidth() - vegaFontRenderer.getStringWidth(module1.getName()) - (1), (1 + b1 * 13), i);
                  b1++;
               }
               break;
            }
         }
      }

      if (mm.hud.isToggled()) {
         if(mm.hud.targethud.getBoolean()) {
            drawTargetHud();
         }
         hud();
      } else {
         yAdd = 0.0F;
      }

      if (mm.hud.isToggled() && mm.hud.armor.getBoolean()) {
         drawArmorHud();
      }

      if(mm.hud.blockdisplay.getBoolean()) {
    	  if(mm.blockFly.isToggled()) {
    		  this.drawBlockDisplay();
    	  }
      }
      
      if(mm.resourcesdisplay.isToggled()) {
    	  this.drawResourcesDisplay();
      }
      
      if(Augustus.getInstance().getBrand() == ClientBrand.DEV) {
    	  Augustus.getInstance().getLoriousFontService().getArialbold24().drawString("DEV - " + Augustus.getInstance().getBuild(), sr.getScaledWidth() / 2 - (Augustus.getInstance().getLoriousFontService().getArialbold24().getStringWidth("DEV - " + Augustus.getInstance().getBuild()) / 2), 50, 0x70ffffff, false, null, null);
      }
   }

   @Override
   public void updateTick() {
      super.updateTick();
      if (mm.arrayList.isToggled()) {
      }
   }

   private void hud() {
      ScaledResolution sr = new ScaledResolution(mc);
      String var2 = mm.hud.mode.getSelected();
      switch(var2) {
         case "CSGO": {
            switch (mm.hud.side.getSelected()) {
               case "Left": {
                  Augustus.getInstance().getLoriousFontService().getArialbold24().drawString("G", 5D, 5D, Color.BLACK.getRGB(), true, Color.BLUE, Color.GREEN);
//                  oldFontRenderer.drawString("ENZA", 5.0F + oldFontRenderer.getStringWidth("X"), 5.0F, -1);
                  break;
               }
               case "Right":
                  oldFontRenderer.drawString("G", sr.getScaledWidth() - 5.0F - oldFontRenderer.getStringWidth("Gugustus"), 5.0F, getChroma().getRGB());
                  oldFontRenderer.drawString("ugustus", sr.getScaledWidth() - 5.0F - oldFontRenderer.getStringWidth("Gugustus"), 5.0F, -1);
                  break;
            }
            break;
         }
         case "Old": {
            String var11 = mm.hud.side.getSelected();
            switch (var11) {
               case "Left": {
                  oldFontRenderer.drawString("G", 5.0F, 5.0F, getChroma().getRGB());
                  oldFontRenderer.drawString("ugustus", 5.0F + oldFontRenderer.getStringWidth("G"), 5.0F, -1);
                  break;
               }
               case "Right":
                  oldFontRenderer.drawString("G", sr.getScaledWidth() - 5.0F - oldFontRenderer.getStringWidth("Gugustus"), 5.0F, getChroma().getRGB());
                  oldFontRenderer.drawString("ugustus", sr.getScaledWidth() - 5.0F - oldFontRenderer.getStringWidth("ugustus"), 5.0F, -1);
                  break;
            }
            break;
         }
         case "Ryu": {

            String name = "Ryu  ";
            float width = (float)getFontRenderer().getStringWidth(name);
            yAdd = (float)(getFontRenderer().FONT_HEIGHT + 1);
            String var11 = mm.hud.side.getSelected();
            switch(var11) {
               case "Left":
                  width = (float)ryuFontRenderer.getStringWidth(name);
                  ryuFontRenderer.drawStringWithShadow(name, 1.0F, 1.0F, mm.hud.color.getColor().getRGB());
                  ryuFontRenderer.drawStringWithShadow("# ", width, 1.0F, new Color(162, 162, 162 ,200).getRGB());
                  //System.out.println(getFontRenderer().FONT_HEIGHT);
                  ryuFontRenderer.drawStringWithShadow(Minecraft.getDebugFPS() + "fps", width + ryuFontRenderer.getStringWidth("# "), 1.0F, Color.white.getRGB());
                  break;
               case "Right":
                  PlayerUtil.sendChat("Ryu == Left");
                  mm.hud.side.setString("Left");
            }
            break;
         }
         case "Other":
            GL11.glPushMatrix();
            GL11.glScaled(1.5, 1.5, 0.0);
            yAdd = (float)((int)((double)((float)getFontRenderer().FONT_HEIGHT * 1.75F + 4.0F + 1.0F) * 1.5));
            String var13 = mm.hud.side.getSelected();
            switch(var13) {
               case "Left":
                  if (mm.hud.backGround.getBoolean()) {
                     drawRect(
                        0,
                        0,
                        (int)((float)getFontRenderer().getStringWidth("G") * 1.75F + (float)getFontRenderer().getStringWidth("ugustus") + 4.0F),
                        (int)((float)getFontRenderer().FONT_HEIGHT * 1.75F + 3.0F),
                        mm.hud.backGroundColor.getColor().getRGB()
                     );
                  }

                  GL11.glPushMatrix();
                  GL11.glScaled(1.75, 1.75, 0.0);
                  getFontRenderer().drawString("G", 1.0F, 1.0F, mm.hud.color.getColor().getRGB(), true, 0.5F);
                  GL11.glScaled(1.0, 1.0, 0.0);
                  GL11.glPopMatrix();
                  getFontRenderer()
                     .drawString(
                        "ugustus",
                        (float)getFontRenderer().getStringWidth("G") * 1.75F + 2.0F,
                        (float)(2 + getFontRenderer().FONT_HEIGHT) - 4.0F,
                        mm.hud.color.getColor().getRGB(),
                        true,
                        0.5F
                     );
                  break;
               case "Right":
                  if (mm.hud.backGround.getBoolean()) {
                     drawRect(
                        (int)(
                           (float)sr.getScaledWidth() / 1.5F
                              - (float)(
                                 (int)(
                                    (float)getFontRenderer().getStringWidth("G") * 1.75F + (float)getFontRenderer().getStringWidth("ugustus") + 4.0F
                                 )
                              )
                        ),
                        0,
                        (int)((float)sr.getScaledWidth() / 1.5F) + 1,
                        (int)((float)getFontRenderer().FONT_HEIGHT * 1.75F + 3.0F),
                        mm.hud.backGroundColor.getColor().getRGB()
                     );
                  }

                  GL11.glPushMatrix();
                  GL11.glScaled(1.75, 1.75, 0.0);
                  getFontRenderer()
                     .drawString(
                        "G",
                        (float)sr.getScaledWidth() / 1.5F / 1.75F
                           - (float)getFontRenderer().getStringWidth("G")
                           - (float)getFontRenderer().getStringWidth("ugustus") / 1.75F
                           - 0.25F,
                        1.0F,
                        mm.hud.color.getColor().getRGB(),
                        true,
                        0.5F
                     );
                  GL11.glScaled(1.0, 1.0, 0.0);
                  GL11.glPopMatrix();
                  getFontRenderer()
                     .drawString(
                        "ugustus",
                        (float)sr.getScaledWidth() / 1.5F
                           - 1.75F
                           - (float)getFontRenderer().getStringWidth("G") * 1.5F * 1.75F
                           - (float)getFontRenderer().getStringWidth("ugustus") / 1.5F
                           + 4.0F,
                        (float)(2 + getFontRenderer().FONT_HEIGHT) - 4.0F,
                        mm.hud.color.getColor().getRGB(),
                        true,
                        0.5F
                     );
            }

            GL11.glScaled(1.0, 1.0, 0.0);
            GL11.glPopMatrix();
            break;
         case "Basic":
            GregorianCalendar cal = (GregorianCalendar)GregorianCalendar.getInstance();
            Date time = new Date();
            cal.setTime(time);
            int h = mm.protector.isToggled() && mm.protector.protectTime.getBoolean() ? mm.protector.getRandomHour() : cal.get(10);
            String m = cal.get(12) <= 9 ? "0" + cal.get(12) : String.valueOf(cal.get(12));
            m = mm.protector.isToggled() && mm.protector.protectTime.getBoolean() ? String.valueOf(mm.protector.getRandomMinute()) : m;
            String printTime;
            if (cal.get(9) == 0) {
               printTime = h < 9 ? " (0" + h + ":" + m + " AM)" : " (" + h + ":" + m + " AM)";
            } else {
               printTime = h < 9 ? " (0" + h + ":" + m + " PM)" : " (" + h + ":" + m + " PM)";
            }

            String name = Augustus.getInstance().getName() + " " + Augustus.getInstance().getVersion();
            float width = (float)getFontRenderer().getStringWidth(name);
            yAdd = (float)(getFontRenderer().FONT_HEIGHT + 1);
            String var11 = mm.hud.side.getSelected();
            switch(var11) {
               case "Left":
                  if (mm.hud.backGround.getBoolean()) {
                     RenderUtil.drawFloatRect(
                        0.0F,
                        0.0F,
                        (float)(getFontRenderer().getStringWidth(printTime) + 3) + width,
                        (float)(getFontRenderer().FONT_HEIGHT + 1),
                        mm.hud.backGroundColor.getColor().getRGB()
                     );
                  }

                  getFontRenderer().drawString(name, 1.0F, 1.0F, mm.hud.color.getColor().getRGB(), true, 0.5F);
                  getFontRenderer().drawString(printTime, width, 1.0F, new Color(182, 186, 189).getRGB(), true, 0.5F);
                  break;
               case "Right":
                  if (mm.hud.backGround.getBoolean()) {
                     RenderUtil.drawFloatRect(
                        (float)(sr.getScaledWidth() - getFontRenderer().getStringWidth(printTime) - 3) - width,
                        0.0F,
                        (float)sr.getScaledWidth(),
                        (float)(getFontRenderer().FONT_HEIGHT + 1),
                        mm.hud.backGroundColor.getColor().getRGB()
                     );
                  }


                  getFontRenderer()
                     .drawString(
                        name,
                        (float)sr.getScaledWidth() - width - (float)getFontRenderer().getStringWidth(printTime),
                        1.0F,
                        mm.hud.color.getColor().getRGB(),
                        true,
                        0.5F
                     );
                  getFontRenderer()
                     .drawString(
                        printTime,
                        (float)(sr.getScaledWidth() - getFontRenderer().getStringWidth(printTime) - 1),
                        1.0F,
                        new Color(182, 186, 189).getRGB(),
                        true,
                        0.5F
                     );
               case "SigmaJello": {
              	 GlStateManager.pushMatrix();
              	 GlStateManager.scale(2.5, 2.5, 2.5);
              	 Augustus.getInstance().getLoriousFontService().getArialbold24().drawString("Sigma", 4, 4, 0x70ffffff, false, null, null);
              	 GlStateManager.popMatrix();
              	 GlStateManager.pushMatrix();
              	 GlStateManager.scale(1.3, 1.3, 1.3);
              	 Augustus.getInstance().getLoriousFontService().getArialbold24().drawString("Jello", 9, 30, 0x70ffffff, false, null, null);
              	 GlStateManager.popMatrix();
              	 break;
               }
               default:
                  break;
            }
         default:
            yAdd = 0.0F;
      }

      if (!mm.arrayList.sideOption.getSelected().equals(mm.hud.side.getSelected())) {
         yAdd = 0.0F;
      }
   }
   private static float animated;
   public static Color color = new Color(0, 0, 0, 100);
   public static void drawTargetHud() {
      if(KillAura.target instanceof EntityPlayer) {
         int x = (int)mm.hud.targethud_x.getValue();
         int y = (int)mm.hud.targethud_y.getValue();
         switch (mm.hud.targetMode.getSelected().toLowerCase()) {
            case "other": {
               GuiUtils.drawRect1(x, y, 200, 150, color.getRGB());
               break;
            }
            case "basic": {
               Color healthColor;
               String playerName;
               int distance;
               float f1;
               float f2;
               EntityPlayer currentTarget = (EntityPlayer) KillAura.target;
               healthColor = new Color(getHealthColor(currentTarget));
               playerName = "Name: " + StringUtils.stripControlCodes(currentTarget.getName());
               distance = (int) mc.thePlayer.getDistanceToEntity(currentTarget);
               f1 = 133.0F / Minecraft.getDebugFPS() * 1.05F;
               f2 = 140.0F / currentTarget.getMaxHealth() * Math.min(currentTarget.getHealth(), currentTarget.getMaxHealth());
               GuiUtils.drawRect1((x - 1.0F), (y - 1.0F), 142.0D, 44.0D, (new Color(0, 0, 0, 100)).getRGB());
               GuiUtils.drawRect1(x, y, 140.0D, 40.0D, (new Color(0, 0, 0, 75)).getRGB());
               GuiUtils.drawRect1(x, (y + 40.0F), 140.0D, 2.0D, (new Color(0, 0, 0)).getRGB());
               mc.fontRendererObj.drawString(playerName, (int) (x + 25.0F), (int) (y + 4.0F), -1);
               mc.fontRendererObj.drawString("Distance: " + distance + "m", (int) (x + 25.0F), (int) (y + 15.0F), -1);
               mc.fontRendererObj.drawString("Armor: " + Math.round(currentTarget.getTotalArmorValue()), (int) (x + 25.0F), (int) (y + 25.0F), -1);
               if (mc.currentScreen == null)
                  GuiInventory.drawEntityOnScreen(x + 12, y + 31, 13, currentTarget.rotationYaw, -currentTarget.rotationPitch, currentTarget);
               if (f2 < animated || f2 > animated)
                  if (Math.abs(f2 - animated) <= f1) {
                     animated = f2;
                  } else {
                     animated += (animated < f2) ? (f1 * 3.0F) : -f1;
                  }
               GuiUtils.drawRect1(x, (y + 40.0F), animated, 2.0D, healthColor.getRGB());
               break;
            }
	        case "ryder": {
				Gui.drawRect(x, y, x + 134, y + 46, 0xff303030);
				EntityLivingBase target = KillAura.target;
				GlStateManager.color(256, 256, 256);
				
				double posX = x + 2;
				double posY = y + 2;
				int scale = 42;
				drawPlayerHead(posX, posY, scale, KillAura.target, new Color(255, 255, 255, 255));
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 50 / 1.5, y + 1 / 1.5, 1);
				GlStateManager.scale(1.5, 1.5, 1);
				GlStateManager.translate(-(x + 50), -(y + 1), 1);
				mc.fontRendererObj.drawString(target.getName(), x + 59, y + 3, -1);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				mc.fontRendererObj.drawString((Math.round(target.getDistanceToEntity(mc.thePlayer) * 10) / 10.0) + " block(s) away", x + 47.5, y + 18, -1);
				GlStateManager.popMatrix();
				Gui.drawRect(x + 46, y + 31, (int)(x + 132), y + 44, 0xff808080);
				if(target.getHealth() <= 0)
					Gui.drawRect(x + 46, y + 31, (int)(x + 132), y + 44, 0xffff0000);
				if(target.getHealth() > 20)
					Gui.drawRect(x + 46, y + 31, (int)(x + 132), y + 44, 0xffff0000);
				else
					Gui.drawRect(x + 46, y + 31, (int)(x + 45 + target.getHealth() * 4.35), y + 44, 0xffff0000);
				GlStateManager.pushMatrix();
				GlStateManager.translate(x + 50 / 1.5, y + 1 / 1.5, 1);
				GlStateManager.scale(1.25, 1.25, 1);
				GlStateManager.translate(-(x + 50), -(y + 1), 1);
				mc.fontRendererObj.drawString((int)target.getHealth() + " / " + (int)target.getMaxHealth(), x + 76.5, y + 26.5, -1);
				GlStateManager.popMatrix();
	        	break;
	        }
         }
      }
   }

   public static void drawPlayerHead(final double d, final double e, final int width, final EntityLivingBase player, final Color color) {
   	Minecraft mc = Minecraft.getMinecraft();
       final NetworkPlayerInfo playerInfo = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
       if (playerInfo != null) {
           mc.getTextureManager().bindTexture(playerInfo.getLocationSkin());
       }
       else {
           mc.getTextureManager().bindTexture(DefaultPlayerSkin.getDefaultSkin(player.getUniqueID()));
       }
       Gui.drawScaledCustomSizeModalRect((int)d, (int)e, 8.0f, 8.0f, 8, 8, width, width, 64.0f, 64.0f);
   }
   
   public static int getHealthColor(EntityLivingBase entityLivingBase) {
      float percentage = 100.0F * entityLivingBase.getHealth() / 2.0F / entityLivingBase.getMaxHealth() / 2.0F;
      return (percentage > 75.0F) ? 1703705 : ((percentage > 50.0F) ? 16776960 : ((percentage > 25.0F) ? 16733440 : 16713984));
   }
   public static void drawCustomHotBar() {
      ScaledResolution sr = new ScaledResolution(mc);
      FontRenderer fr = mc.fontRendererObj;
      int i = mc.getNetHandler() != null && mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()) != null
         ? mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime()
         : 0;
      int x =(int)mc.thePlayer.posX;
      int y = (int)mc.thePlayer.posY;
      int z = (int)mc.thePlayer.posZ;
      fr.drawString(
         "FPS: " + Minecraft.getDebugFPS() + "   Ping: " + i,
         (int)(4.0F + FontUtil.espHotbar.getStringWidth("A")),
         sr.getScaledHeight() - 20,
         Color.white.getRGB()
      );
      fr.drawString(
         "X: " + x + "  Y:" + y + "  Z: " + z,
         (int)(4.0F + FontUtil.espHotbar.getStringWidth("X")),
         sr.getScaledHeight() - 9,
         Color.white.getRGB()
      );
      FontUtil.espHotbar.drawStringWithShadow("G", 1.5, sr.getScaledHeight() - 19, Color.white.getRGB());
      if (!mm.protector.isToggled() || !mm.protector.protectTime.getBoolean()) {
         GregorianCalendar now = new GregorianCalendar();
         DateFormat df = DateFormat.getDateInstance(2);
         fr.drawString(
            df.format(now.getTime()),
            (float)(sr.getScaledWidth() - 10 - fr.getStringWidth(df.format(now.getTime()))),
            (float)(sr.getScaledHeight() - 1 - fr.FONT_HEIGHT),
            new Color(255, 255, 255, 221).getRGB(),
            true,
            0.5F
         );
         DateFormat df2 = DateFormat.getTimeInstance(3);
         fr.drawString(
            df2.format(now.getTime()),
            (float)(
               (double)(sr.getScaledWidth() - 10)
                  - (double)fr.getStringWidth(df2.format(now.getTime())) * 0.5
                  - (double)fr.getStringWidth(df.format(now.getTime())) * 0.5
            ),
            (float)(sr.getScaledHeight() - 11 - fr.FONT_HEIGHT),
            new Color(255, 255, 255, 221).getRGB(),
            true,
            0.5F
         );
      }
   }

   public static void drawCustomCrossHair() {
      ScaledResolution sr = new ScaledResolution(mc);
      double screenWidth = sr.getScaledWidth_double();
      double screenHeight = sr.getScaledHeight_double();
      Vec2d middle = new Vec2d(screenWidth / 2.0, screenHeight / 2.0);
      double crossHairWidth = mm.crossHair.width.getValue() / 2.0;
      double crossHairLength = mm.crossHair.length.getValue();
      double crossHairMargin = mm.crossHair.margin.getValue();
      int color = mm.crossHair.color.getColor().getRGB();
      if (mm.crossHair.rainbow.getBoolean()) {
         mm.crossHair
            .rainbowUtil
            .updateRainbow(
               mm.crossHair.rainbowSpeed.getValue() == 1000.0
                  ? (float)(mm.crossHair.rainbowSpeed.getValue() * 1.0E-5F)
                  : (float)(mm.crossHair.rainbowSpeed.getValue() * 1.0E-6F),
               255
            );
         color = mm.crossHair.rainbowUtil.getColor().getRGB();
      }

      RenderUtil.drawRect(middle.x - crossHairWidth, middle.y + crossHairMargin + crossHairLength, middle.x + crossHairWidth, middle.y + crossHairMargin, color);
      if (!mm.crossHair.tStyle.getBoolean()) {
         RenderUtil.drawRect(
            middle.x - crossHairWidth, middle.y - crossHairMargin - crossHairLength, middle.x + crossHairWidth, middle.y - crossHairMargin, color
         );
      }

      RenderUtil.drawRect(middle.x + crossHairMargin, middle.y + crossHairWidth, middle.x + crossHairMargin + crossHairLength, middle.y - crossHairWidth, color);
      RenderUtil.drawRect(middle.x - crossHairMargin - crossHairLength, middle.y + crossHairWidth, middle.x - crossHairMargin, middle.y - crossHairWidth, color);
      if (mm.crossHair.dot.getBoolean()) {
         RenderUtil.drawRect(middle.x - crossHairWidth, middle.y + crossHairWidth, middle.x + crossHairWidth, middle.y - crossHairWidth, color);
      }
   }

   private void drawArmorHud() {
      ScaledResolution sr = new ScaledResolution(mc);
      int height = sr.getScaledHeight() - 56;
      int width = sr.getScaledWidth() / 2 + 91;
      mc.getRenderItem().zLevel = -100.0F;
      if (mc.thePlayer.getCurrentArmor(3) != null) {
         mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getCurrentArmor(3), width - 81, height);
      }

      if (mc.thePlayer.getCurrentArmor(2) != null) {
         mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getCurrentArmor(2), width - 61, height);
      }

      if (mc.thePlayer.getCurrentArmor(1) != null) {
         mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getCurrentArmor(1), width - 41, height);
      }

      if (mc.thePlayer.getCurrentArmor(0) != null) {
         mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.getCurrentArmor(0), width - 21, height);
      }
   }
   
   private void drawBlockDisplay() {
	   ScaledResolution sr = new ScaledResolution(mc);
	   String mode = mm.hud.displaymode.getSelected();
	   if(mc.thePlayer.inventory.getStackInSlot(mm.blockFly.getSlotID()) == null)
		   return;
	   switch(mode) {
		   case "Augustus": {
			   if(mm.hud.backGround2.getBoolean()) {
				   RectUtils.drawRoundedRect((sr.getScaledWidth() / 2) - 18, (sr.getScaledHeight() / 2) + 12, (sr.getScaledWidth() / 2) + 18, (sr.getScaledHeight() / 2) + 32, 3, mm.hud.backGroundColor2.getColor().getRGB());
			   }
			   Fonts.hudfont.drawString(mc.thePlayer.inventory.getStackInSlot(mm.blockFly.getSlotID()).stackSize + "", (sr.getScaledWidth() / 2) + 2, sr.getScaledHeight() / 2 + 19, -1);
		       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		       GlStateManager.enableRescaleNormal();
		       GlStateManager.enableBlend();
		       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		       RenderHelper.enableGUIStandardItemLighting();
		       mc.getRenderItem().renderItemIntoGUI(mc.thePlayer.inventory.getStackInSlot(mm.blockFly.getSlotID()), (sr.getScaledWidth() / 2) - 16, (sr.getScaledHeight() / 2) + 14);
		       RenderHelper.disableStandardItemLighting();
		       GlStateManager.disableRescaleNormal();
		       GlStateManager.disableBlend();
			   break;
		   }
		   case "Rise": {
			   
			   break;
		   }
		   case "Smiple": {
			   
			   break;
		   }
	
	   }
   }
   
   private void drawResourcesDisplay() {
	   double x = mm.resourcesdisplay.x.getValue();
	   double y = mm.resourcesdisplay.y.getValue();
	   int iron = 0, gold = 0, dia = 0, emerald = 0;
	   for(int i = 0; i < mc.thePlayer.inventory.getSizeInventory(); i++) {
		   ItemStack is = mc.thePlayer.inventory.getStackInSlot(i);
		   if(is != null) {
			   int itemID = Item.getIdFromItem(is.getItem());
			   if(itemID == 265) {
				   iron += is.stackSize;
			   }
			   if(itemID == 266) {
				   gold += is.stackSize;
			   }
			   if(itemID == 264) {
				   dia += is.stackSize;
			   }
			   if(itemID == 388) {
				   emerald += is.stackSize;
			   }
		   }
	   }
	   Gui.drawRect(x, y, x + 120, y + 78, 0x70000000);
       mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.iron_ingot), (int)x + 3, (int)y + 0);
       mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.gold_ingot), (int)x + 3, (int)y + 20);
       mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.diamond), (int)x + 3, (int)y + 40);
       mc.getRenderItem().renderItemIntoGUI(new ItemStack(Items.emerald), (int)x + 3, (int)y + 60);
       mc.fontRendererObj.drawStringWithShadow("Iron x" + iron, (float)x + 23, (float)y + (float)4, -1);
       mc.fontRendererObj.drawStringWithShadow("Gold x" + gold, (float)x + 23, (float)y + (float)24, -1);
       mc.fontRendererObj.drawStringWithShadow("Diamond x" + dia, (float)x + 23, (float)y + (float)44, -1);
       mc.fontRendererObj.drawStringWithShadow("Emerald x" + emerald, (float)x + 23, (float)y + (float)64, -1);
   }

}
