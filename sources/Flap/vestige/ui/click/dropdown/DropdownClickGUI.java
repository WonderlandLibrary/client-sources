package vestige.ui.click.dropdown;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.visual.ClickGuiModule;
import vestige.module.impl.visual.ClientTheme;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DescriptionSettings;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.EnumModeSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.shaders.impl.GaussianBlur;
import vestige.ui.click.dropdown.impl.CategoryHolder;
import vestige.ui.click.dropdown.impl.ModuleHolder;
import vestige.ui.click.dropdown.impl.SettingHolder;
import vestige.ui.menu.Extern.SettingsSave;
import vestige.util.render.ColorUtil2;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtil;
import vestige.util.render.RenderUtils2;

public class DropdownClickGUI extends GuiScreen {
   private final ClickGuiModule module;
   private final ArrayList<CategoryHolder> categories = new ArrayList();
   private final int categoryXOffset = 110;
   private final int categoryYOffset = 20;
   private final int moduleYOffset = 17;
   private final int settingYOffset = 16;
   private final Color moduleDisabledColor = new Color(10, 10, 10, 255);
   private final Color settingscolor = new Color(41, 40, 44, 255);
   private Color boolSettingEnabledColor = new Color(225, 225, 225);
   private final Color boolSettingBox = new Color(40, 40, 40);
   private int mouseHoverColor = 805306368;
   private Color mouseHoverColor2 = new Color(0, 0, 0, 0);
   private int lastMouseX;
   private int lastMouseY;
   private Module keyChangeModule;
   private int scrollY;
   private int scrollX;
   private ClientTheme theme;
   String categoryselected = "movement";
   private VestigeFontRenderer productSan;
   private VestigeFontRenderer productSans;
   private VestigeFontRenderer productSansBold;
   private VestigeFontRenderer productSans172;
   private VestigeFontRenderer productSansBold2;

   public DropdownClickGUI(ClickGuiModule module) {
      this.module = module;
      int x = 40;
      int y = 30;
      Category[] var4 = Category.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Category category = var4[var6];
         ArrayList<ModuleHolder> modules = new ArrayList();
         Flap.instance.getModuleManager().modules.stream().filter((m) -> {
            return m.getCategory() == category;
         }).forEach((m) -> {
            modules.add(new ModuleHolder(m));
         });
         this.categories.add(new CategoryHolder(category, modules, x, y, true));
         x += 130;
      }

   }

   public void initGui() {
      this.categories.forEach((c) -> {
         c.getModules().forEach((m) -> {
            m.updateState();
         });
      });
      this.scrollY = 0;
   }

   @NotNull
   private static Color flap$interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      if (color1 == null) {
         $$$reportNull$$$0(0);
      }

      if (color2 == null) {
         $$$reportNull$$$0(1);
      }

      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(ColorUtil2.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), ColorUtil2.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), ColorUtil2.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), ColorUtil2.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      VestigeFontRenderer fr;
      int settingColor;
      if (((ClickGuiModule)Flap.instance.getModuleManager().getModule(ClickGuiModule.class)).style.getMode() == "Drop Down") {
         GaussianBlur.startBlur();
         RenderUtils2.drawBloomShadow(0.0F, 0.0F, 1000.0F, 10000.0F, 6, 4, -1, false);
         GaussianBlur.endBlur(4.0F, 2.0F);
         this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
         fr = Flap.instance.getFontManager().getProductSans();
         VestigeFontRenderer fr2 = Flap.instance.getFontManager().getProductSan15();
         VestigeFontRenderer fr3 = Flap.instance.getFontManager().getProductSan14();
         this.productSan = Flap.instance.getFontManager().getProductSan();
         this.productSans = Flap.instance.getFontManager().getProductSans();
         VestigeFontRenderer pequenabold = Flap.instance.getFontManager().getProductSanBold();
         this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
         this.productSans172 = Flap.instance.getFontManager().getProductSans30();
         this.productSansBold2 = Flap.instance.getFontManager().getProductSansBold30();
         GL11.glTranslatef(0.0F, (float)this.scrollY, 0.0F);
         Iterator var8 = this.categories.iterator();

         label227:
         while(true) {
            CategoryHolder category;
            do {
               if (!var8.hasNext()) {
                  break label227;
               }

               category = (CategoryHolder)var8.next();
            } while(!category.isShown());

            if (category.isHolded()) {
               category.setX(category.getX() + mouseX - this.lastMouseX);
               category.setY(category.getY() + mouseY - this.lastMouseY);
            }

            int x = category.getX() + 50;
            int y = category.getY();
            Gui.drawRect(x, y + 1, x + 110 - 1, y + 20 + 1, this.moduleDisabledColor.getRGB());
            RenderUtil.lineNoGl((double)x, (double)(y + 1), (double)(x + 110 - 1), (double)(y + 1), new Color(20, 20, 20, 255));
            RenderUtil.lineNoGl((double)x, (double)(y + 1), (double)x, (double)(y + 20 + 1), new Color(20, 20, 20, 255));
            RenderUtil.lineNoGl((double)(x + 110 - 1), (double)(y + 1), (double)(x + 110 - 1), (double)(y + 20 + 1), new Color(20, 20, 20, 255));
            String categoryName = category.getCategory().toString().toLowerCase();
            String capital = categoryName.substring(0, 1).toUpperCase();
            String rest = categoryName.substring(1);
            pequenabold.drawStringWithShadow(capital + rest, (double)(x + 19), (double)y + 7.5D, -1);
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/clickgui/" + capital + rest + ".png"), x + 5, y + 5, 10, 10);
            float startX = (float)x;
            float endX = startX + 110.0F;
            y += 20;
            boolean firstModule = true;
            boolean firstModuleEnabled = false;

            label225:
            for(Iterator var19 = category.getModules().iterator(); var19.hasNext(); firstModule = false) {
               ModuleHolder holder = (ModuleHolder)var19.next();
               Module m = holder.getModule();
               float startY = (float)y;
               float endY = startY + 17.0F;
               double mult = (double)holder.getTimer().getTimeElapsed() / 200.0D;
               RenderUtil.lineNoGl((double)startX, (double)startY, (double)startX, (double)endY, new Color(20, 20, 20, 255));
               RenderUtil.lineNoGl((double)(endX - 1.0F), (double)startY, (double)(endX - 1.0F), (double)endY, new Color(20, 20, 20, 255));
               RenderUtil.lineNoGl((double)startX, (double)endY, (double)(endX - 1.0F), (double)endY, new Color(20, 20, 20, 255));
               if (m.isEnabled()) {
                  Gui.drawRect((double)startX, (double)startY, (double)(endX - 1.0F), (double)endY, this.module.getColor(1));
               } else {
                  RenderUtil.lineNoGl((double)startX, (double)startY, (double)(endX - 1.0F), (double)startY, new Color(30, 30, 30, 255));
                  Gui.drawRect((double)startX, (double)startY, (double)(endX - 1.0F), (double)endY, (new Color(25, 25, 25, 255)).getRGB());
               }

               if (!m.isEnabled()) {
                  this.productSan.drawString(m.getName(), startX + 5.0F, startY + 5.0F, (new Color(100, 100, 100)).getRGB());
               } else {
                  this.productSan.drawString(m.getName(), (double)(startX + 5.0F), (double)startY + 5.5D, -1);
               }

               y += 17;
               if (firstModule && (m.isEnabled() || mult < 1.0D)) {
                  firstModuleEnabled = true;
               }

               if (holder.isSettingsShown()) {
                  float startKeybindY = (float)y;
                  float endKeybindY = (float)(y + 16);
                  Gui.drawRect((double)startX, (double)startKeybindY, (double)(endX - 1.0F), (double)endKeybindY, (new Color(25, 25, 25, 255)).getRGB());
                  RenderUtil.lineNoGl((double)startX, (double)endY, (double)(endX - 1.0F), (double)endY, new Color(25, 25, 25, 255));
                  RenderUtil.lineNoGl((double)startX, (double)startKeybindY, (double)startX, (double)endKeybindY, new Color(20, 20, 20, 255));
                  RenderUtil.lineNoGl((double)(endX - 1.0F), (double)startKeybindY, (double)(endX - 1.0F), (double)endKeybindY, new Color(20, 20, 20, 255));
                  fr2.drawString(this.keyChangeModule == m ? "Press a key to bind..." : "Bind: " + Keyboard.getKeyName(m.getKey()), startX + 3.0F, startKeybindY + 3.5F, Color.gray.getRGB());
                  y += 16;
                  Iterator var28 = holder.getSettings().iterator();

                  while(true) {
                     SettingHolder settingHolder;
                     do {
                        if (!var28.hasNext()) {
                           continue label225;
                        }

                        settingHolder = (SettingHolder)var28.next();
                     } while(!(Boolean)settingHolder.getSetting().getVisibility().get());

                     float startSettingY = (float)y;
                     float endSettingY = (float)(y + 16);
                     RenderUtil.lineNoGl((double)startX, (double)startSettingY, (double)startX, (double)endSettingY, new Color(20, 20, 20, 255));
                     RenderUtil.lineNoGl((double)(endX - 1.0F), (double)startSettingY, (double)(endX - 1.0F), (double)endSettingY, new Color(20, 20, 20, 255));
                     Gui.drawRect((double)startX, (double)startSettingY, (double)(endX - 1.0F), (double)endSettingY, (new Color(25, 25, 25, 255)).getRGB());
                     boolean var10000;
                     if ((float)mouseX > startX + 1.0F && (float)mouseX < endX - 1.0F && (float)mouseY > startSettingY && (float)mouseY < endSettingY) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     if (settingHolder.getSetting() instanceof ModeSetting) {
                        ModeSetting setting = (ModeSetting)settingHolder.getSetting();
                        String toRender = setting.getName() + "§f §9";
                        if (fr2.getStringWidth(toRender) > 107.0D) {
                           fr2.drawStringWithShadow(setting.getName() + ":", startX + 3.0F, startSettingY + 2.5F, (new Color(200, 200, 200)).getRGB());
                           fr2.drawStringWithShadow(setting.getMode(), startX + 3.0F, startSettingY + 11.0F, (new Color(200, 200, 200)).getRGB());
                           y += 6;
                        } else {
                           fr2.drawStringWithShadow(setting.getMode(), (double)(startX + 3.0F) + fr2.getStringWidth(toRender), (double)(startSettingY + 3.0F), this.theme.getColor(1));
                           fr2.drawStringWithShadow(toRender, startX + 3.0F, startSettingY + 3.0F, (new Color(150, 150, 150)).getRGB());
                        }
                     } else if (settingHolder.getSetting() instanceof BooleanSetting) {
                        BooleanSetting setting = (BooleanSetting)settingHolder.getSetting();
                        if (!setting.isEnabled()) {
                           RenderUtils2.drawRoundedGradientOutlinedRectangle(endX - 20.5F, startSettingY + 2.0F, endX - 4.0F, startSettingY + 10.0F, 8.0F, Color.darkGray.getRGB(), Color.darkGray.getRGB(), Color.darkGray.getRGB());
                        } else {
                           RenderUtils2.drawRoundedGradientOutlinedRectangle(endX - 20.5F, startSettingY + 2.0F, endX - 4.0F, startSettingY + 10.0F, 8.0F, this.module.getColor((int)(endX - 12.0F + (float)y)), this.module.getColor((int)(endX - 12.0F + (float)y)), this.module.getColor((int)(endX - 12.0F + (float)y)));
                        }

                        if (setting.isEnabled()) {
                           RenderUtils2.drawRoundedGradientOutlinedRectangle(endX - 11.5F, startSettingY + 3.0F, endX - 5.5F, startSettingY + 9.0F, 6.5F, Color.lightGray.getRGB(), Color.lightGray.getRGB(), Color.lightGray.getRGB());
                        } else {
                           RenderUtils2.drawRoundedGradientOutlinedRectangle(endX - 19.5F, startSettingY + 3.0F, endX - 13.5F, startSettingY + 9.0F, 6.5F, Color.lightGray.getRGB(), Color.lightGray.getRGB(), Color.lightGray.getRGB());
                        }

                        fr2.drawStringWithShadow(setting.getName(), startX + 3.0F, startSettingY + 3.0F, (new Color(150, 150, 150)).getRGB());
                     } else if (settingHolder.getSetting() instanceof EnumModeSetting) {
                        EnumModeSetting setting = (EnumModeSetting)settingHolder.getSetting();
                        fr2.drawStringWithShadow(setting.getName() + "§f: §a" + setting.getMode().name(), startX + 3.0F, startSettingY + 3.0F, (new Color(150, 150, 150)).getRGB());
                     } else {
                        float endSettingX;
                        float length;
                        double numberX;
                        double finaalnumbe;
                        if (settingHolder.getSetting() instanceof DoubleSetting) {
                           DoubleSetting setting = (DoubleSetting)settingHolder.getSetting();
                           endSettingX = endX - 1.0F;
                           length = endSettingX - startX;
                           if (settingHolder.isHoldingMouse() && mouseX >= x && (float)mouseX <= startX + endSettingX && (float)mouseY > startSettingY && (float)mouseY < endSettingY) {
                              numberX = (double)((float)mouseX - startX);
                              finaalnumbe = numberX / (double)length;
                              setting.setValue(finaalnumbe * (setting.getMax() - setting.getMin()) + setting.getMin());
                           }

                           numberX = (double)startX + (setting.getValue() - setting.getMin()) * (double)length / (setting.getMax() - setting.getMin());
                           finaalnumbe = numberX < 102.0D ? numberX + 2.0D : numberX - 4.0D;
                           RenderUtils2.drawRoundedRectangle(startX + 2.0F, startSettingY + 10.0F, endSettingX - 4.0F, endSettingY - 2.0F, 4.0F, (new Color(40, 40, 40)).getRGB());
                           RenderUtils2.drawRoundedRectangle(startX + 2.0F, startSettingY + 10.0F, (float)finaalnumbe, endSettingY - 2.0F, finaalnumbe < 95.0D ? 0.0F : 4.0F, this.module.getColor(10));
                           fr2.drawStringWithShadow(setting.getName() + "", startX + 3.0F, startSettingY, (new Color(150, 150, 150)).getRGB());
                           fr2.drawStringWithShadow(setting.getStringValue(), (double)(startX + 3.0F) + fr2.getStringWidth(setting.getName() + " "), (double)startSettingY, this.theme.getColor(1));
                           fr2.drawStringWithShadow("", (double)(startX + 3.0F) + fr2.getStringWidth(setting.getName() + " "), (double)startSettingY, -1);
                        } else {
                           int value;
                           IntegerSetting setting;
                           float startSettingX;
                           if (settingHolder.getSetting() instanceof IntegerSetting) {
                              setting = (IntegerSetting)settingHolder.getSetting();
                              startSettingX = startX + 1.0F;
                              endSettingX = endX - 1.0F;
                              length = endSettingX - startSettingX;
                              if (settingHolder.isHoldingMouse() && mouseX >= x && (float)mouseX <= startSettingX + endSettingX && (float)mouseY > startSettingY && (float)mouseY < endSettingY) {
                                 numberX = (double)((float)mouseX - startSettingX);
                                 finaalnumbe = numberX / (double)length;
                                 value = (int)(finaalnumbe * (double)(setting.getMax() - setting.getMin()) + (double)setting.getMin());
                                 setting.setValue(value);
                              }

                              numberX = (double)(startSettingX + (float)(setting.getValue() - setting.getMin()) * length / (float)(setting.getMax() - setting.getMin()));
                              finaalnumbe = numberX < 102.0D ? numberX + 2.0D : numberX - 4.0D;
                              RenderUtils2.drawRoundedRectangle(startSettingX + 2.0F, startSettingY + 10.0F, endSettingX - 4.0F, endSettingY - 2.0F, 4.0F, (new Color(40, 40, 40)).getRGB());
                              RenderUtils2.drawRoundedRectangle(startSettingX + 2.0F, startSettingY + 10.0F, (float)finaalnumbe, endSettingY - 2.0F, finaalnumbe < 95.0D ? 0.0F : 4.0F, this.module.getColor(10));
                              fr2.drawStringWithShadow(setting.getName() + "§f ", startSettingX + 2.0F, startSettingY, (new Color(150, 150, 150)).getRGB());
                              fr2.drawStringWithShadow(setting.getValue() + "", (double)(startSettingX + 2.0F) + fr2.getStringWidth(setting.getName() + ":"), (double)startSettingY, this.theme.getColor(1));
                              fr2.drawStringWithShadow("", (double)(startSettingX + 3.0F) + fr2.getStringWidth(setting.getName() + " "), (double)startSettingY, -1);
                           } else if (settingHolder.getSetting() instanceof IntegerSetting) {
                              setting = (IntegerSetting)settingHolder.getSetting();
                              startSettingX = startX + 1.0F;
                              endSettingX = endX - 1.0F;
                              length = endSettingX - startSettingX;
                              if (settingHolder.isHoldingMouse() && mouseX >= x && (float)mouseX <= startSettingX + endSettingX && (float)mouseY > startSettingY && (float)mouseY < endSettingY) {
                                 numberX = (double)((float)mouseX - startSettingX);
                                 finaalnumbe = numberX / (double)length;
                                 value = (int)(finaalnumbe * (double)(setting.getMax() - setting.getMin()) + (double)setting.getMin());
                                 setting.setValue(value);
                              }

                              numberX = (double)(startSettingX + (float)(setting.getValue() - setting.getMin()) * length / (float)(setting.getMax() - setting.getMin()));
                              finaalnumbe = numberX < 102.0D ? numberX + 2.0D : numberX - 4.0D;
                              RenderUtils2.drawRoundedRectangle(startSettingX + 2.0F, startSettingY + 10.0F, endSettingX - 4.0F, endSettingY - 2.0F, 4.0F, (new Color(40, 40, 40)).getRGB());
                              RenderUtils2.drawRoundedRectangle(startSettingX + 2.0F, startSettingY + 10.0F, (float)finaalnumbe, endSettingY - 2.0F, finaalnumbe < 95.0D ? 0.0F : 4.0F, this.module.getColor(10));
                              fr2.drawStringWithShadow(setting.getName() + "", startSettingX + 3.0F, startSettingY, (new Color(150, 150, 150)).getRGB());
                              fr2.drawStringWithShadow(setting.getName(), (double)(startSettingX + 3.0F) + fr2.getStringWidth(setting.getName() + " "), (double)startSettingY, this.theme.getColor(1));
                              fr2.drawStringWithShadow("", (double)(startSettingX + 3.0F) + fr2.getStringWidth(setting.getName() + " "), (double)startSettingY, -1);
                           } else if (settingHolder.getSetting() instanceof DescriptionSettings) {
                              DescriptionSettings sett = (DescriptionSettings)settingHolder.getSetting();
                              settingColor = settingHolder.getSetting().getColor().getRGB();
                              endSettingX = startX + 1.0F;
                              length = endX - 1.0F;
                              fr3.drawStringWithShadow(sett.getName(), endSettingX + 2.0F, startSettingY, settingColor);
                           }
                        }
                     }

                     y += 16;
                  }
               }
            }
         }
      }

      GL11.glTranslatef(0.0F, (float)(-this.scrollY), 0.0F);
      this.lastMouseX = mouseX;
      this.lastMouseY = mouseY;
      if (((ClickGuiModule)Flap.instance.getModuleManager().getModule(ClickGuiModule.class)).style.getMode() == "Compact") {
         RenderUtils2.drawRect(0.0D, 0.0D, 3000.0D, 3000.0D, (new Color(0, 0, 0, 100)).getRGB());
         GaussianBlur.startBlur();
         RenderUtils2.drawBloomShadow(0.0F, 0.0F, 3000.0F, 3000.0F, 6, 4, -1, false);
         GaussianBlur.endBlur(8.0F, 2.0F);
         fr = Flap.instance.getFontManager().getProductSans();
         ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
         int width = 500;
         int height = 300;
         int xPos = sr.getScaledWidth() / 2 - width / 2;
         int yPos = sr.getScaledHeight() / 2 - height / 2;
         GL11.glEnable(3089);
         GL11.glScissor(xPos * sr.getScaleFactor(), (sr.getScaledHeight() - (yPos + height)) * sr.getScaleFactor(), width * sr.getScaleFactor(), height * sr.getScaleFactor());
         RenderUtils2.drawRoundOutline((double)xPos, (double)yPos, (double)width, (double)height, 10.5D, 0.5D, new Color(10, 10, 15, 220), new Color(35, 35, 45, 255));
         int initialOffset = 35;
         int accumulatedOffset = 0;
         Iterator var49 = this.categories.iterator();

         while(var49.hasNext()) {
            CategoryHolder category = (CategoryHolder)var49.next();
            String categoryName = category.getCategory().toString().toLowerCase();
            String capital = categoryName.substring(0, 1).toUpperCase();
            String rest = categoryName.substring(1);
            int btnwidth = 90;
            int btnheight = 25;
            int offsetbtn = 35;
            int btnxPos = sr.getScaledWidth() / 2 - width / 2 + 10;
            int btnyPos = sr.getScaledHeight() / 2 - height / 3 + initialOffset + accumulatedOffset;
            if (categoryName.equals(this.categoryselected.toLowerCase())) {
               RenderUtils2.drawRoundOutline((double)btnxPos, (double)btnyPos, (double)btnwidth, (double)btnheight, 5.0D, 0.0D, new Color(150, 0, 255, 255), new Color(35, 35, 45, 0));
               fr.drawStringWithShadow(capital + rest, (float)(btnxPos + 25), (float)(btnyPos + btnheight / 3), -1);
            } else {
               RenderUtils2.drawRoundOutline((double)btnxPos, (double)btnyPos, (double)btnwidth, (double)btnheight, 5.0D, 0.0D, new Color(10, 10, 15, 0), new Color(35, 35, 45, 0));
               fr.drawStringWithShadow(capital + rest, (float)(btnxPos + 25), (float)(btnyPos + btnheight / 3), Color.GRAY.getRGB());
            }

            DrawUtil.drawImage(new ResourceLocation("flap/imagens/clickgui/" + capital + rest + ".png"), btnxPos + 10, btnyPos + btnheight / 3, 10, 10);
            accumulatedOffset += offsetbtn;
            int initialOffsete = 35;
            int accumulatedOffsete = 0;
            int offsetmod = 35;
            Iterator var61 = category.getModules().iterator();

            while(var61.hasNext()) {
               ModuleHolder holder = (ModuleHolder)var61.next();
               String me = holder.getModule().getCategory().name();
               if (me.equals(this.categoryselected.toUpperCase())) {
                  GL11.glTranslatef(0.0F, (float)(-this.scrollY * 2), 0.0F);
                  int modwidth = 350;
                  int modheight = 30;
                  settingColor = sr.getScaledWidth() / 2 - modwidth / 2 + 45;
                  int modyPos = sr.getScaledHeight() / 2 - height / 2 - 20 + initialOffsete + accumulatedOffsete;
                  if (!holder.getModule().isEnabled()) {
                     RenderUtils2.drawRoundedRectangle((float)settingColor, (float)modyPos, (float)(modwidth + settingColor), (float)(modheight + modyPos), 8.0F, (new Color(10, 10, 15, 210)).getRGB());
                  } else {
                     RenderUtils2.drawRoundedRectangle((float)settingColor, (float)modyPos, (float)(modwidth + settingColor), (float)(modheight + modyPos), 8.0F, (new Color(150, 0, 255, 255)).getRGB());
                  }

                  if (!holder.getModule().isEnabled()) {
                     fr.drawString(holder.getModule().getName(), (float)(settingColor + offsetbtn / 4), (float)(modyPos + modheight / 3), (new Color(225, 225, 225)).getRGB());
                  } else {
                     fr.drawString(holder.getModule().getName(), (float)(settingColor + offsetbtn / 4), (float)(modyPos + modheight / 3), this.settingscolor.getRGB());
                  }

                  GL11.glTranslatef(0.0F, (float)(this.scrollY * 2), 0.0F);
                  accumulatedOffsete += offsetmod;
               }
            }
         }

         GL11.glDisable(3089);
         DrawUtil.drawImage(new ResourceLocation("flap/flap.png"), xPos + 30, yPos + 20, 40, 40);
      }

   }

   protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
      if (((ClickGuiModule)Flap.instance.getModuleManager().getModule(ClickGuiModule.class)).style.getMode() == "Drop Down") {
         VestigeFontRenderer fr = Flap.instance.getFontManager().getProductSans();
         if (mouseX > GuiScreen.width / 2 - 30 && mouseX < GuiScreen.width / 2 + 30 && mouseY > 2 && mouseY < 20 && button == 0) {
            this.mc.displayGuiScreen(new SettingsSave());
         }

         Iterator var5 = this.categories.iterator();

         label194:
         while(true) {
            CategoryHolder category;
            do {
               if (!var5.hasNext()) {
                  break label194;
               }

               category = (CategoryHolder)var5.next();
            } while(!category.isShown());

            int x = category.getX() + 50;
            int y = category.getY() + this.scrollY;
            if (mouseX > x && mouseX < x + 110 && mouseY > y && mouseY < y + 20) {
               category.setHolded(true);
            }

            float startX = (float)x;
            float endX = startX + 110.0F;
            y += 20;
            Iterator var11 = category.getModules().iterator();

            label192:
            while(true) {
               ModuleHolder holder;
               Module m;
               do {
                  if (!var11.hasNext()) {
                     continue label194;
                  }

                  holder = (ModuleHolder)var11.next();
                  m = holder.getModule();
                  float startY = (float)y;
                  float endY = startY + 17.0F;
                  if ((float)mouseX > startX && (float)mouseX < endX && (float)mouseY > startY && (float)mouseY < endY) {
                     if (button == 0) {
                        m.toggle();
                        holder.updateState();
                     } else if (button == 1) {
                        holder.setSettingsShown(!holder.isSettingsShown());
                     }
                  }

                  y += 17;
               } while(!holder.isSettingsShown());

               float startKeybindY = (float)y;
               float endKeybindY = (float)(y + 16);
               if (button == 0 && (float)mouseX > startX && (float)mouseX < endX && (float)mouseY > startKeybindY && (float)mouseY < endKeybindY) {
                  this.keyChangeModule = m;
               }

               y += 16;
               Iterator var18 = holder.getSettings().iterator();

               while(true) {
                  SettingHolder settingHolder;
                  do {
                     if (!var18.hasNext()) {
                        continue label192;
                     }

                     settingHolder = (SettingHolder)var18.next();
                  } while(!(Boolean)settingHolder.getSetting().getVisibility().get());

                  float startSettingY = (float)y;
                  float endSettingY = (float)(y + 16);
                  boolean hovering = (float)mouseX > startX && (float)mouseX < endX && (float)mouseY > startSettingY && (float)mouseY < endSettingY;
                  if (settingHolder.getSetting() instanceof BooleanSetting) {
                     BooleanSetting setting = (BooleanSetting)settingHolder.getSetting();
                     if (button == 0 && hovering) {
                        setting.setEnabled(!setting.isEnabled());
                     }
                  } else if (settingHolder.getSetting() instanceof ModeSetting) {
                     ModeSetting setting = (ModeSetting)settingHolder.getSetting();
                     String toRender = setting.getName() + " : " + setting.getMode();
                     if (fr.getStringWidth(toRender) > 107.0D) {
                        if ((float)mouseX > startX && (float)mouseX < endX && (float)mouseY > startSettingY && (float)mouseY < endSettingY + 6.0F) {
                           float realEndSettingY = endSettingY + 6.0F;
                           if (button == 0) {
                              setting.increment();
                           } else if (button == 1) {
                              setting.decrement();
                           }
                        }

                        y += 6;
                     } else if (hovering) {
                        if (button == 0) {
                           setting.increment();
                        } else if (button == 1) {
                           setting.decrement();
                        }
                     }
                  } else if (settingHolder.getSetting() instanceof EnumModeSetting) {
                     EnumModeSetting setting = (EnumModeSetting)settingHolder.getSetting();
                     if (hovering) {
                        if (button == 0) {
                           setting.increment();
                        } else if (button == 1) {
                           setting.decrement();
                        }
                     }
                  }

                  if (hovering && button == 0) {
                     settingHolder.setHoldingMouse(true);
                  }

                  y += 16;
               }
            }
         }
      }

      if (((ClickGuiModule)Flap.instance.getModuleManager().getModule(ClickGuiModule.class)).style.getMode() == "Compact") {
         Iterator var26 = this.categories.iterator();

         while(var26.hasNext()) {
            CategoryHolder category = (CategoryHolder)var26.next();
            if (category.isShown()) {
               String categoryName = category.getCategory().toString().toLowerCase();
               String capital = categoryName.substring(0, 1).toUpperCase();
               String rest = categoryName.substring(1);
               int x = category.getX() + GuiScreen.width / 6;
               int y = category.getY() + this.scrollY;
               if (mouseX > x && mouseX < x + 110 && mouseY > y && mouseY < y + 20) {
                  this.categoryselected = categoryName.toLowerCase();
               }

               float startX = (float)x;
               float endX = startX + 110.0F;
               y += 20;
            }
         }
      }

   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      this.categories.forEach((c) -> {
         c.setHolded(false);
         c.getModules().forEach((m) -> {
            m.getSettings().forEach((s) -> {
               s.setHoldingMouse(false);
            });
         });
      });
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      int i = Integer.signum(Mouse.getEventDWheel());
      this.scrollY -= i * 4;
      this.scrollY = Math.max(-500, Math.min(500, this.scrollY));
   }

   protected void keyTyped(char typedChar, int key) throws IOException {
      if (key == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
         if (this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }
      }

      if (this.keyChangeModule != null) {
         this.keyChangeModule.setKey(key == 14 ? 0 : key);
         this.keyChangeModule = null;
      }

   }

   public void onGuiClosed() {
      this.module.setEnabled(false);
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "color1";
         break;
      case 1:
         var10001[0] = "color2";
      }

      var10001[1] = "vestige/ui/click/dropdown/DropdownClickGUI";
      var10001[2] = "flap$interpolateColorC";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
