package exhibition.gui.click.ui;

import exhibition.Client;
import exhibition.gui.click.ClickGui;
import exhibition.gui.click.components.Button;
import exhibition.gui.click.components.CategoryButton;
import exhibition.gui.click.components.CategoryPanel;
import exhibition.gui.click.components.Checkbox;
import exhibition.gui.click.components.ColorPreview;
import exhibition.gui.click.components.ConfigButton;
import exhibition.gui.click.components.ConfigList;
import exhibition.gui.click.components.DropdownBox;
import exhibition.gui.click.components.DropdownButton;
import exhibition.gui.click.components.GroupBox;
import exhibition.gui.click.components.HSVColorPicker;
import exhibition.gui.click.components.MainPanel;
import exhibition.gui.click.components.MultiDropdownBox;
import exhibition.gui.click.components.MultiDropdownButton;
import exhibition.gui.click.components.SLButton;
import exhibition.gui.click.components.Slider;
import exhibition.gui.click.components.TextBox;
import exhibition.management.ColorManager;
import exhibition.management.animate.Opacity;
import exhibition.management.command.Command;
import exhibition.management.command.impl.ColorCommand;
import exhibition.management.keybinding.Keybind;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.data.ModuleData;
import exhibition.module.data.MultiBool;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.StringConversions;
import exhibition.util.misc.ChatUtil;
import exhibition.util.render.Colors;
import exhibition.util.render.Depth;
import exhibition.util.render.TTFFontRenderer;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class SkeetMenu extends UI {
   public Opacity opacity = new Opacity(0);
   private Minecraft mc = Minecraft.getMinecraft();
   private ResourceLocation texture = new ResourceLocation("textures/skeetchainmail.png");

   public void mainConstructor(ClickGui p0) {
   }

   public void mainPanelDraw(MainPanel panel, int p0, int p1) {
      this.opacity.interpolate((float)panel.opacity);
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) - 0.3D, (double)(panel.y + panel.dragY) - 0.3D, (double)(panel.x + 340.0F + panel.dragX) + 0.5D, (double)(panel.y + 310.0F + panel.dragY) + 0.3D, 0.5D, Colors.getColor(0, 0), Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX), (double)(panel.y + panel.dragY), (double)(panel.x + 340.0F + panel.dragX), (double)(panel.y + 310.0F + panel.dragY), 0.5D, Colors.getColor(0, 0), Colors.getColor(60, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0F), (double)(panel.y + panel.dragY + 2.0F), (double)(panel.x + 340.0F + panel.dragX - 2.0F), (double)(panel.y + 310.0F + panel.dragY - 2.0F), 0.5D, Colors.getColor(0, 0), Colors.getColor(60, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 0.6D, (double)(panel.y + panel.dragY) + 0.6D, (double)(panel.x + 340.0F + panel.dragX) - 0.5D, (double)(panel.y + 310.0F + panel.dragY) - 0.6D, 1.3D, Colors.getColor(0, 0), Colors.getColor(40, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 2.5D, (double)(panel.y + panel.dragY) + 2.5D, (double)(panel.x + 340.0F + panel.dragX) - 2.5D, (double)(panel.y + 310.0F + panel.dragY) - 2.5D, 0.5D, Colors.getColor(22, (int)this.opacity.getOpacity()), Colors.getColor(22, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradientSideways((double)(panel.x + panel.dragX + 3.0F), (double)(panel.y + panel.dragY + 3.0F), (double)(panel.x + 178.0F + panel.dragX - 3.0F), (double)(panel.dragY + panel.y + 4.0F), Colors.getColor(55, 177, 218, (int)this.opacity.getOpacity()), Colors.getColor(204, 77, 198, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradientSideways((double)(panel.x + panel.dragX + 175.0F), (double)(panel.y + panel.dragY + 3.0F), (double)(panel.x + 340.0F + panel.dragX - 3.0F), (double)(panel.dragY + panel.y + 4.0F), Colors.getColor(204, 77, 198, (int)this.opacity.getOpacity()), Colors.getColor(204, 227, 53, (int)this.opacity.getOpacity()));
      int i11 = (int)this.opacity.getOpacity() - 145;
      if (i11 < 0) {
         i11 = 0;
      }

      RenderingUtil.rectangle((double)(panel.x + panel.dragX + 3.0F), (double)(panel.y + panel.dragY) + 3.3D, (double)(panel.x + 340.0F + panel.dragX - 3.0F), (double)(panel.dragY + panel.y + 4.0F), Colors.getColor(0, i11));
      RenderingUtil.drawGradientSideways(-1.0D, -1.0D, -1.0D, -1.0D, Colors.getColor(255, (int)this.opacity.getOpacity()), Colors.getColor(255, (int)this.opacity.getOpacity()));
      GlStateManager.pushMatrix();
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      this.mc.getTextureManager().bindTexture(this.texture);
      GlStateManager.translate((double)(panel.x + panel.dragX) + 2.5D, (double)(panel.dragY + panel.y + 3.0F), 0.0D);
      this.drawIcon(1.0D, 1.0D, 0.0F, 0.0F, 333.5D, 303.0D, 325.0F, 275.0F);
      GlStateManager.disableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.popMatrix();
      float y = 15.0F;

      for(int i = 0; i <= panel.typeButton.size(); ++i) {
         if (i <= panel.typeButton.size() - 1 && ((CategoryButton)panel.typeButton.get(i)).categoryPanel.visible && i > 0) {
            y = (float)(15 + i * 40);
         }
      }

      GlStateManager.pushMatrix();
      this.prepareScissorBox(panel.x + panel.dragX + 3.0F, panel.y + panel.dragY + 4.5F, panel.x + panel.dragX + 40.0F, panel.y + panel.dragY + y + 1.0F);
      GL11.glEnable(3089);
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0F), (double)(panel.y + panel.dragY + 3.0F), (double)(panel.x + panel.dragX + 40.0F), (double)(panel.y + panel.dragY + y), 0.5D, Colors.getColor(0, (int)this.opacity.getOpacity()), Colors.getColor(48, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(panel.x + panel.dragX + 3.0F), (double)(panel.y + panel.dragY + 4.0F), (double)(panel.x + panel.dragX + 39.0F), (double)(panel.y + panel.dragY + y - 1.0F), Colors.getColor(12, (int)this.opacity.getOpacity()));
      GL11.glDisable(3089);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.prepareScissorBox(panel.x + panel.dragX + 3.0F, panel.y + panel.dragY + y + 40.0F, panel.x + panel.dragX + 40.0F, panel.y + panel.dragY + 308.0F);
      GL11.glEnable(3089);
      RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX + 2.0F), (double)(panel.y + panel.dragY + y + 40.0F), (double)(panel.x + panel.dragX + 40.0F), (double)(panel.y + panel.dragY + 308.0F), 0.5D, Colors.getColor(0, (int)this.opacity.getOpacity()), Colors.getColor(48, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(panel.x + panel.dragX + 3.0F), (double)(panel.y + panel.dragY + y + 41.0F), (double)(panel.x + panel.dragX + 39.0F), (double)(panel.y + panel.dragY) + 307.5D, Colors.getColor(12, (int)this.opacity.getOpacity()));
      GL11.glDisable(3089);
      GlStateManager.popMatrix();
      if (this.opacity.getOpacity() != 0.0F) {
         Iterator var8 = panel.slButtons.iterator();

         while(var8.hasNext()) {
            SLButton button = (SLButton)var8.next();
            button.draw((float)p0, (float)p1);
         }

         var8 = panel.typeButton.iterator();

         while(var8.hasNext()) {
            CategoryButton button = (CategoryButton)var8.next();
            button.draw((float)p0, (float)p1);
         }

         ScaledResolution rs = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
         if (panel.dragging) {
            panel.dragX = (float)p0 - panel.lastDragX;
            panel.dragY = (float)p1 - panel.lastDragY;
         }

         if (panel.dragX > (float)(rs.getScaledWidth() - 402)) {
            panel.dragX = (float)(rs.getScaledWidth() - 402);
         }

         if (panel.dragX < -48.0F) {
            panel.dragX = -48.0F;
         }

         if (panel.dragY > (float)(rs.getScaledHeight() - 362)) {
            panel.dragY = (float)(rs.getScaledHeight() - 362);
         }

         if (panel.dragY < -48.0F) {
            panel.dragY = -48.0F;
         }
      }

   }

   private void drawIcon(double x, double y, float u, float v, double width, double height, float textureWidth, float textureHeight) {
      float var8 = 1.0F / textureWidth;
      float var9 = 1.0F / textureHeight;
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      var11.startDrawingQuads();
      var11.addVertexWithUV(x, y + height, 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV(x + width, y + height, 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV(x + width, y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
      var11.addVertexWithUV(x, y, 0.0D, (double)(u * var8), (double)(v * var9));
      var10.draw();
   }

   private void prepareScissorBox(float x, float y, float x2, float y2) {
      ScaledResolution scale = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }

   public void mainPanelKeyPress(MainPanel panel, int key) {
      if (this.opacity.getOpacity() >= 10.0F) {
         if (key == 210 || key == 211 || key == 54) {
            panel.typeButton.forEach((o) -> {
               o.categoryPanel.multiDropdownBoxes.forEach((b) -> {
                  b.active = false;
               });
            });
            panel.typeButton.forEach((o) -> {
               o.categoryPanel.dropdownBoxes.forEach((b) -> {
                  b.active = false;
               });
            });
            this.mc.displayGuiScreen((GuiScreen)null);
         }

         panel.typeButton.forEach((o) -> {
            o.categoryPanel.buttons.forEach((b) -> {
               b.keyPressed(key);
            });
         });
         panel.typeButton.forEach((o) -> {
            o.categoryPanel.textBoxes.forEach((t) -> {
               t.keyPressed(key);
            });
         });
      }
   }

   public void panelConstructor(MainPanel mainPanel, float x, float y) {
      int y1 = 15;
      ModuleData.Type[] var5 = ModuleData.Type.values();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ModuleData.Type types = var5[var7];
         mainPanel.typeButton.add(new CategoryButton(mainPanel, types.name(), x + 3.0F, y + (float)y1));
         y += 40.0F;
      }

      mainPanel.typeButton.add(new CategoryButton(mainPanel, "Colors", x + 3.0F, y + (float)y1));
      ((CategoryButton)mainPanel.typeButton.get(0)).enabled = true;
      ((CategoryButton)mainPanel.typeButton.get(0)).categoryPanel.visible = true;
   }

   public void panelMouseClicked(MainPanel mainPanel, int x, int y, int z) {
      if (this.opacity.getOpacity() >= 220.0F) {
         if ((float)x >= mainPanel.x + mainPanel.dragX && (float)y >= mainPanel.dragY + mainPanel.y && (float)x <= mainPanel.dragX + mainPanel.x + 400.0F && (float)y <= mainPanel.dragY + mainPanel.y + 12.0F && z == 0) {
            mainPanel.dragging = true;
            mainPanel.lastDragX = (float)x - mainPanel.dragX;
            mainPanel.lastDragY = (float)y - mainPanel.dragY;
         }

         mainPanel.typeButton.forEach((c) -> {
            c.mouseClicked(x, y, z);
            c.categoryPanel.mouseClicked(x, y, z);
         });
         mainPanel.slButtons.forEach((slButton) -> {
            slButton.mouseClicked(x, y, z);
         });
      }
   }

   public void panelMouseMovedOrUp(MainPanel mainPanel, int x, int y, int z) {
      if (this.opacity.getOpacity() >= 220.0F) {
         if (z == 0) {
            mainPanel.dragging = false;
         }

         Iterator var5 = mainPanel.typeButton.iterator();

         while(var5.hasNext()) {
            CategoryButton button = (CategoryButton)var5.next();
            button.mouseReleased(x, y, z);
         }

      }
   }

   public void categoryButtonConstructor(CategoryButton p0, MainPanel p1) {
      p0.categoryPanel = new CategoryPanel(p0.name, p0, 0.0F, 0.0F);
   }

   public void categoryButtonMouseClicked(CategoryButton p0, MainPanel p1, int p2, int p3, int p4) {
      if ((float)p2 >= p0.x + p1.dragX && (float)p3 >= p1.dragY + p0.y && (float)p2 <= p1.dragX + p0.x + 40.0F && (float)p3 <= p1.dragY + p0.y + 40.0F && p4 == 0) {
         Iterator var6 = p1.typeButton.iterator();

         while(var6.hasNext()) {
            CategoryButton button = (CategoryButton)var6.next();
            if (button == p0) {
               p0.enabled = true;
               p0.categoryPanel.visible = true;
            } else {
               button.enabled = false;
               button.categoryPanel.visible = false;
            }
         }
      }

   }

   public void categoryButtonDraw(CategoryButton p0, float p2, float p3) {
      int color = p0.enabled ? Colors.getColor(210, (int)this.opacity.getOpacity()) : Colors.getColor(91, (int)this.opacity.getOpacity());
      if (p2 >= p0.x + p0.panel.dragX && p3 >= p0.y + p0.panel.dragY && p2 <= p0.x + p0.panel.dragX + 40.0F && p3 <= p0.y + p0.panel.dragY + 40.0F && !p0.enabled) {
         color = Colors.getColor(165);
      }

      if (p0.name.equalsIgnoreCase("MSGO")) {
         Client.badCache.drawCenteredString("A", p0.x + 20.0F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Combat")) {
         Client.badCache.drawCenteredString("E", p0.x + 19.0F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Player")) {
         Client.badCache.drawCenteredString("F", p0.x + 18.0F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Movement")) {
         Client.badCache.drawCenteredString("J", p0.x + 19.0F + p0.panel.dragX, p0.y + 22.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Visuals")) {
         Client.badCache.drawCenteredString("C", p0.x + 18.0F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Colors")) {
         Client.badCache.drawCenteredString("H", p0.x + 18.5F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else if (p0.name.equalsIgnoreCase("Other")) {
         Client.badCache.drawCenteredString("I", p0.x + 19.0F + p0.panel.dragX, p0.y + 20.0F + p0.panel.dragY, color);
      } else {
         Client.f.drawStringWithShadow(Character.toString(p0.name.charAt(0)) + Character.toString(p0.name.charAt(1)), p0.x + 12.0F + p0.panel.dragX, p0.y + 13.0F + p0.panel.dragY, color);
      }

      if (p0.enabled) {
         p0.categoryPanel.draw(p2, p3);
      }

   }

   private List getSettings(Module mod) {
      List settings = new ArrayList();
      Iterator var3 = mod.getSettings().values().iterator();

      while(var3.hasNext()) {
         Setting set = (Setting)var3.next();
         settings.add(set);
      }

      return settings.isEmpty() ? null : settings;
   }

   public void categoryPanelConstructor(CategoryPanel categoryPanel, CategoryButton categoryButton, float x, float y) {
      float xOff = 50.0F + categoryButton.panel.x;
      float yOff = 15.0F + categoryButton.panel.y;
      float biggestY;
      float noSets;
      Module[] var9;
      int var10;
      int var11;
      Module module;
      if (categoryButton.name.equalsIgnoreCase("Combat")) {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.Combat) {
               if (module.getName().equalsIgnoreCase("AntiBot")) {
                  yOff -= 25.0F;
               }

               if (module.getName().equalsIgnoreCase("AntiVelocity")) {
                  yOff += 25.0F;
               }

               if (module.getName().equalsIgnoreCase("Killaura")) {
                  yOff -= 55.0F;
               }

               if (module.getName().equalsIgnoreCase("AutoSword")) {
                  yOff += 22.0F;
               }

               y = 20.0F;
               List<Setting> list = this.getSettings(module);
               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  float x1 = 0.5F;
                  Iterator var15 = list.iterator();

                  while(var15.hasNext()) {
                     Setting setting = (Setting)var15.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  int tY = 0;
                  List sliders = new ArrayList();
                  list.forEach((settingx) -> {
                     if (settingx.getValue() instanceof Number) {
                        sliders.add(settingx);
                     }

                  });
                  sliders.sort(Comparator.comparing(Setting::getName));
                  Iterator var17 = sliders.iterator();

                  Setting setting;
                  while(var17.hasNext()) {
                     setting = (Setting)var17.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var17 = this.getSettings(module).iterator();

                  label946:
                  while(true) {
                     do {
                        if (!var17.hasNext()) {
                           var17 = this.getSettings(module).iterator();

                           while(var17.hasNext()) {
                              setting = (Setting)var17.next();
                              if (setting.getValue() instanceof Options) {
                                 categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }

                              if (setting.getValue() instanceof MultiBool) {
                                 categoryPanel.multiDropdownBoxes.add(new MultiDropdownBox((MultiBool)setting.getValue(), xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           var17 = this.getSettings(module).iterator();

                           while(var17.hasNext()) {
                              setting = (Setting)var17.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 if (x1 == 44.5F) {
                                    y += 11.0F;
                                 }

                                 x1 = 0.5F;
                              }
                           }

                           var17 = this.getSettings(module).iterator();

                           while(var17.hasNext()) {
                              setting = (Setting)var17.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 16;
                                 x1 += 88.0F;
                                 if (x1 == 88.5F) {
                                    y = (float)((double)y + 15.5D);
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           y += (float)tY;
                           categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                           xOff += 95.0F;
                           if (y >= biggestY) {
                              biggestY = y;
                           }
                           break label946;
                        }

                        setting = (Setting)var17.next();
                     } while(!(setting.getValue() instanceof Options) && !(setting.getValue() instanceof MultiBool));

                     if (x1 == 44.5F) {
                        y += 14.0F;
                     }

                     x1 = 0.5F;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      float x1;
      Iterator var22;
      Setting setting;
      ArrayList sliders;
      Iterator var26;
      byte tY;
      if (categoryButton.name == "Player") {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.Player) {
               y = 20.0F;
               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  x1 = 0.5F;
                  var26 = this.getSettings(module).iterator();

                  while(var26.hasNext()) {
                     setting = (Setting)var26.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  tY = 0;
                  sliders = new ArrayList();
                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Number) {
                        sliders.add(setting);
                     }
                  }

                  sliders.sort(Comparator.comparing(Setting::getName));
                  var22 = sliders.iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  label878:
                  while(true) {
                     do {
                        if (!var22.hasNext()) {
                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue() instanceof Options) {
                                 categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }

                              if (setting.getValue() instanceof MultiBool) {
                                 categoryPanel.multiDropdownBoxes.add(new MultiDropdownBox((MultiBool)setting.getValue(), xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 if (x1 == 44.5F) {
                                    y += 11.0F;
                                 }

                                 x1 = 0.5F;
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 16;
                                 x1 += 88.0F;
                                 if (x1 == 88.5F) {
                                    y = (float)((double)y + 15.5D);
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           y += (float)tY;
                           categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                           xOff += 95.0F;
                           if (y >= biggestY) {
                              biggestY = y;
                           }
                           break label878;
                        }

                        setting = (Setting)var22.next();
                     } while(!(setting.getValue() instanceof Options) && !(setting.getValue() instanceof MultiBool));

                     if (x1 == 44.5F) {
                        y += 14.0F;
                     }

                     x1 = 0.5F;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      if (categoryButton.name == "Movement") {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.Movement) {
               y = 20.0F;
               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  x1 = 0.5F;
                  var26 = this.getSettings(module).iterator();

                  while(var26.hasNext()) {
                     setting = (Setting)var26.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  tY = 0;
                  sliders = new ArrayList();
                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Number) {
                        sliders.add(setting);
                     }
                  }

                  sliders.sort(Comparator.comparing(Setting::getName));
                  var22 = sliders.iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  label810:
                  while(true) {
                     do {
                        if (!var22.hasNext()) {
                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue() instanceof Options) {
                                 categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }

                              if (setting.getValue() instanceof MultiBool) {
                                 categoryPanel.multiDropdownBoxes.add(new MultiDropdownBox((MultiBool)setting.getValue(), xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 if (x1 == 44.5F) {
                                    y += 11.0F;
                                 }

                                 x1 = 0.5F;
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 16;
                                 x1 += 88.0F;
                                 if (x1 == 88.5F) {
                                    y = (float)((double)y + 15.5D);
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           y += (float)tY;
                           categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                           xOff += 95.0F;
                           if (y >= biggestY) {
                              biggestY = y;
                           }
                           break label810;
                        }

                        setting = (Setting)var22.next();
                     } while(!(setting.getValue() instanceof Options) && !(setting.getValue() instanceof MultiBool));

                     if (x1 == 44.5F) {
                        y += 14.0F;
                     }

                     x1 = 0.5F;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      if (categoryButton.name == "Visuals") {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.Visuals) {
               y = 20.0F;
               if (module.getName().equalsIgnoreCase("Weather")) {
                  yOff -= 32.0F;
                  xOff += 95.0F;
               }

               if (module.getName().equalsIgnoreCase("Radar")) {
                  yOff -= 18.0F;
               }

               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  x1 = 0.5F;
                  var26 = this.getSettings(module).iterator();

                  while(var26.hasNext()) {
                     setting = (Setting)var26.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  tY = 0;
                  sliders = new ArrayList();
                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Number) {
                        sliders.add(setting);
                     }
                  }

                  sliders.sort(Comparator.comparing(Setting::getName));
                  var22 = sliders.iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  label739:
                  while(true) {
                     do {
                        if (!var22.hasNext()) {
                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue() instanceof Options) {
                                 categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }

                              if (setting.getValue() instanceof MultiBool) {
                                 categoryPanel.multiDropdownBoxes.add(new MultiDropdownBox((MultiBool)setting.getValue(), xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 if (x1 == 44.5F) {
                                    y += 11.0F;
                                 }

                                 x1 = 0.5F;
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 16;
                                 x1 += 88.0F;
                                 if (x1 == 88.5F) {
                                    y = (float)((double)y + 15.5D);
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           y += (float)tY;
                           categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                           xOff += 95.0F;
                           if (y >= biggestY) {
                              biggestY = y;
                           }
                           break label739;
                        }

                        setting = (Setting)var22.next();
                     } while(!(setting.getValue() instanceof Options) && !(setting.getValue() instanceof MultiBool));

                     if (x1 == 44.5F) {
                        y += 14.0F;
                     }

                     x1 = 0.5F;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      if (categoryButton.name == "Other") {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.Other) {
               y = 20.0F;
               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  x1 = 0.5F;
                  var26 = this.getSettings(module).iterator();

                  while(var26.hasNext()) {
                     setting = (Setting)var26.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  tY = 0;
                  sliders = new ArrayList();
                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Number) {
                        sliders.add(setting);
                     }
                  }

                  sliders.sort(Comparator.comparing(Setting::getName));
                  var22 = sliders.iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Options) {
                        if (x1 == 44.5F) {
                           y += 14.0F;
                        }

                        x1 = 0.5F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Options) {
                        Options option = (Options)setting.getValue();
                        categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                        tY = 17;
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           y += 17.0F;
                           tY = 0;
                           x1 = 0.5F;
                        }
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue().getClass().equals(String.class)) {
                        if (x1 == 44.5F) {
                           y += 11.0F;
                        }

                        x1 = 0.5F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue().getClass().equals(String.class)) {
                        categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                        tY = 16;
                        x1 += 88.0F;
                        if (x1 == 88.5F) {
                           y = (float)((double)y + 15.5D);
                           tY = 0;
                           x1 = 0.5F;
                        }
                     }
                  }

                  y += (float)tY;
                  categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                  xOff += 95.0F;
                  if (y >= biggestY) {
                     biggestY = y;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      if (categoryButton.name == "MSGO") {
         biggestY = 34.0F;
         noSets = 0.0F;
         var9 = (Module[])Client.getModuleManager().getArray();
         var10 = var9.length;

         for(var11 = 0; var11 < var10; ++var11) {
            module = var9[var11];
            if (module.getType() == ModuleData.Type.MSGO) {
               y = 20.0F;
               if (this.getSettings(module) != null) {
                  categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff + 0.5F, yOff + 10.0F, module));
                  x1 = 0.5F;
                  var26 = this.getSettings(module).iterator();

                  while(var26.hasNext()) {
                     setting = (Setting)var26.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff + x1, yOff + y, setting));
                        x1 += 44.0F;
                        if (x1 == 88.5F) {
                           x1 = 0.5F;
                           y += 10.0F;
                        }
                     }
                  }

                  if (x1 == 44.5F) {
                     y += 10.0F;
                  }

                  x1 = 0.5F;
                  tY = 0;
                  sliders = new ArrayList();
                  var22 = this.getSettings(module).iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     if (setting.getValue() instanceof Number) {
                        sliders.add(setting);
                     }
                  }

                  sliders.sort(Comparator.comparing(Setting::getName));
                  var22 = sliders.iterator();

                  while(var22.hasNext()) {
                     setting = (Setting)var22.next();
                     categoryPanel.sliders.add(new Slider(categoryPanel, xOff + x1 + 1.0F, yOff + y + 4.0F, setting));
                     x1 += 44.0F;
                     tY = 12;
                     if (x1 == 88.5F) {
                        tY = 0;
                        x1 = 0.5F;
                        y += 12.0F;
                     }
                  }

                  var22 = this.getSettings(module).iterator();

                  label609:
                  while(true) {
                     do {
                        if (!var22.hasNext()) {
                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue() instanceof Options) {
                                 categoryPanel.dropdownBoxes.add(new DropdownBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }

                              if (setting.getValue() instanceof MultiBool) {
                                 categoryPanel.multiDropdownBoxes.add(new MultiDropdownBox((MultiBool)setting.getValue(), xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 17;
                                 x1 += 44.0F;
                                 if (x1 == 88.5F) {
                                    y += 17.0F;
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 if (x1 == 44.5F) {
                                    y += 11.0F;
                                 }

                                 x1 = 0.5F;
                              }
                           }

                           var22 = this.getSettings(module).iterator();

                           while(var22.hasNext()) {
                              setting = (Setting)var22.next();
                              if (setting.getValue().getClass().equals(String.class)) {
                                 categoryPanel.textBoxes.add(new TextBox(setting, xOff + x1, yOff + y + 4.0F, categoryPanel));
                                 tY = 16;
                                 x1 += 88.0F;
                                 if (x1 == 88.5F) {
                                    y = (float)((double)y + 15.5D);
                                    tY = 0;
                                    x1 = 0.5F;
                                 }
                              }
                           }

                           y += (float)tY;
                           categoryPanel.groupBoxes.add(new GroupBox(module, categoryPanel, xOff, yOff, y == 34.0F ? 40.0F : y - 11.0F));
                           xOff += 95.0F;
                           if (y >= biggestY) {
                              biggestY = y;
                           }
                           break label609;
                        }

                        setting = (Setting)var22.next();
                     } while(!(setting.getValue() instanceof Options) && !(setting.getValue() instanceof MultiBool));

                     if (x1 == 44.5F) {
                        y += 14.0F;
                     }

                     x1 = 0.5F;
                  }
               } else {
                  if (noSets >= 240.0F) {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets - 240.0F, 345.0F, module));
                  } else {
                     categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), 55.0F + categoryButton.panel.x + noSets, 330.0F, module));
                  }

                  noSets += 40.0F;
               }

               if (xOff > 20.0F + categoryButton.panel.y + 310.0F) {
                  xOff = 50.0F + categoryButton.panel.x;
                  yOff += y == 20.0F && biggestY == 20.0F ? 26.0F : biggestY;
               }
            }
         }
      }

      if (categoryButton.name == "Colors") {
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.fVis, "Friendly Visible", xOff + 77.5F, y, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.fInvis, "Friendly Invisible", xOff + 77.5F, y + 60.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.eVis, "Enemy Visible", xOff + 177.5F, y, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.eInvis, "Enemy Invisible", xOff + 177.5F, y + 60.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.fTeam, "Friendly Team", xOff + 77.5F, y + 120.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.eTeam, "Enemy Team", xOff + 177.5F, y + 120.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.glowColor, "Glow Color", xOff + 277.5F, y + 120.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(Client.cm.getHudColor(), "Hud Color", xOff + 277.5F, y, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.xhair, "Crosshair Color", xOff + 277.5F, y + 60.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.chamsVis, "Chams Visible", xOff + 77.5F, y + 180.0F, categoryButton));
         categoryPanel.colorPreviews.add(new ColorPreview(ColorManager.chamsInvis, "Chams Invisible", xOff + 177.5F, y + 180.0F, categoryButton));
      }

   }

   public void categoryPanelMouseClicked(CategoryPanel categoryPanel, int p1, int p2, int p3) {
      boolean active = false;
      Iterator var6 = categoryPanel.textBoxes.iterator();

      while(var6.hasNext()) {
         TextBox tb = (TextBox)var6.next();
         if (tb.isFocused || tb.isTyping) {
            tb.mouseClicked(p1, p2, p3);
            active = true;
            break;
         }
      }

      var6 = categoryPanel.dropdownBoxes.iterator();

      while(var6.hasNext()) {
         DropdownBox db = (DropdownBox)var6.next();
         if (db.active) {
            db.mouseClicked(p1, p2, p3);
            active = true;
            break;
         }
      }

      var6 = categoryPanel.multiDropdownBoxes.iterator();

      MultiDropdownBox db;
      while(var6.hasNext()) {
         db = (MultiDropdownBox)var6.next();
         if (db.active) {
            db.mouseClicked(p1, p2, p3);
            active = true;
            break;
         }
      }

      if (!active) {
         categoryPanel.textBoxes.forEach((o) -> {
            o.mouseClicked(p1, p2, p3);
         });
         categoryPanel.dropdownBoxes.forEach((o) -> {
            o.mouseClicked(p1, p2, p3);
         });
         var6 = categoryPanel.multiDropdownBoxes.iterator();

         while(var6.hasNext()) {
            db = (MultiDropdownBox)var6.next();
            db.mouseClicked(p1, p2, p3);
         }

         var6 = categoryPanel.buttons.iterator();

         while(var6.hasNext()) {
            Button button = (Button)var6.next();
            button.mouseClicked(p1, p2, p3);
         }

         var6 = categoryPanel.checkboxes.iterator();

         while(var6.hasNext()) {
            Checkbox checkbox = (Checkbox)var6.next();
            checkbox.mouseClicked(p1, p2, p3);
         }

         var6 = categoryPanel.sliders.iterator();

         while(var6.hasNext()) {
            Slider slider = (Slider)var6.next();
            slider.mouseClicked(p1, p2, p3);
         }

         var6 = categoryPanel.colorPreviews.iterator();

         while(var6.hasNext()) {
            ColorPreview cp = (ColorPreview)var6.next();
            Iterator var8 = cp.sliders.iterator();

            while(var8.hasNext()) {
               HSVColorPicker slider = (HSVColorPicker)var8.next();
               slider.mouseClicked(p1, p2, p3);
            }
         }
      }

   }

   public void categoryPanelDraw(CategoryPanel categoryPanel, float x, float y) {
	   for (ColorPreview cp : categoryPanel.colorPreviews) {
           cp.draw(x, y);
       }
       for (GroupBox groupBox : categoryPanel.groupBoxes) {
           groupBox.draw(x, y);
       }
       if (!categoryPanel.categoryButton.name.equalsIgnoreCase("Colors") && !categoryPanel.categoryButton.name.equalsIgnoreCase("MSGO")) {
           float xOff = 100.0f + categoryPanel.categoryButton.panel.dragX - 2.5f;
           float yOff = 322.0f + categoryPanel.categoryButton.panel.dragY;
           RenderingUtil.rectangleBordered(xOff, yOff - 6.0f, xOff + 280.0f, yOff + 33.0f, 0.5, Colors.getColor(0, 0), Colors.getColor(10, (int)this.opacity.getOpacity()));
           RenderingUtil.rectangleBordered((double)xOff + 0.5, (double)yOff - 5.5, (double)(xOff + 280.0f) - 0.5, (double)(yOff + 33.0f) - 0.5, 0.5, Colors.getColor(0, 0), Colors.getColor(48, (int)this.opacity.getOpacity()));
           RenderingUtil.rectangle(xOff + 1.0f, yOff - 5.0f, xOff + 279.0f, yOff + 33.0f - 1.0f, Colors.getColor(17, (int)this.opacity.getOpacity()));
           RenderingUtil.rectangle(xOff + 5.0f, yOff - 6.0f, xOff + Client.fs.getWidth("No Settings") + 5.0f, yOff - 4.0f, Colors.getColor(17, (int)this.opacity.getOpacity()));
           Client.fs.drawStringWithShadow("No Settings", xOff + 5.0f, yOff - 7.0f, Colors.getColor(220, (int)this.opacity.getOpacity()));
       }
       for (TextBox tb : categoryPanel.textBoxes) {
           if (!categoryPanel.visible) continue;
           tb.draw(x, y);
       }
       for (Button button : categoryPanel.buttons) {
           button.draw(x, y);
       }
       for (Checkbox checkbox : categoryPanel.checkboxes) {
           checkbox.draw(x, y);
       }
       for (Slider slider : categoryPanel.sliders) {
           slider.draw(x, y);
       }
       for (MultiDropdownBox db : categoryPanel.multiDropdownBoxes) {
           db.draw(x, y);
       }
       for (MultiDropdownBox db : categoryPanel.multiDropdownBoxes) {
           if (!db.active) continue;
           Iterator butt = db.buttons.iterator();
           while(butt.hasNext()) {
               MultiDropdownButton b = (MultiDropdownButton)butt.next();
               b.draw(x, y);
           }
       }
       ArrayList<DropdownBox> list = new ArrayList<DropdownBox>(categoryPanel.dropdownBoxes);
       Collections.reverse(list);
       for (DropdownBox db : list) {
           db.draw(x, y);
       }
       for (DropdownBox db : list) {
           if (!db.active) continue;
           Iterator butt = db.buttons.iterator();
           while(butt.hasNext()) {
        	   DropdownButton b = (DropdownButton)butt.next();
               b.draw(x, y);
           }
           
       }
   }

   public void categoryPanelMouseMovedOrUp(CategoryPanel categoryPanel, int x, int y, int button) {
      Iterator var5 = categoryPanel.sliders.iterator();

      while(var5.hasNext()) {
         Slider slider = (Slider)var5.next();
         slider.mouseReleased(x, y, button);
      }

      var5 = categoryPanel.colorPreviews.iterator();

      while(var5.hasNext()) {
         ColorPreview cp = (ColorPreview)var5.next();
         Iterator var7 = cp.sliders.iterator();

         while(var7.hasNext()) {
            HSVColorPicker slider = (HSVColorPicker)var7.next();
            slider.mouseReleased(x, y, button);
         }
      }

   }

   public void groupBoxConstructor(GroupBox groupBox, float x, float y) {
   }

   public void groupBoxMouseClicked(GroupBox groupBox, int p1, int p2, int p3) {
   }

   public void groupBoxDraw(GroupBox groupBox, float x, float y) {
      float xOff = groupBox.x + groupBox.categoryPanel.categoryButton.panel.dragX - 2.5F;
      float yOff = groupBox.y + groupBox.categoryPanel.categoryButton.panel.dragY + 10.0F;
      RenderingUtil.rectangleBordered((double)xOff, (double)(yOff - 6.0F), (double)(xOff + 90.0F), (double)(yOff + groupBox.ySize), 0.5D, Colors.getColor(0, 0), Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)xOff + 0.5D, (double)yOff - 5.5D, (double)(xOff + 90.0F) - 0.5D, (double)(yOff + groupBox.ySize) - 0.5D, 0.5D, Colors.getColor(0, 0), Colors.getColor(48, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(xOff + 1.0F), (double)(yOff - 5.0F), (double)(xOff + 89.0F), (double)(yOff + groupBox.ySize - 1.0F), Colors.getColor(17, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(xOff + 5.0F), (double)(yOff - 6.0F), (double)(xOff + Client.fs.getWidth(groupBox.module.getName()) + 5.0F), (double)(yOff - 4.0F), Colors.getColor(17, (int)this.opacity.getOpacity()));
   }

   public void groupBoxMouseMovedOrUp(GroupBox groupBox, int x, int y, int button) {
   }

   public void buttonContructor(Button p0, CategoryPanel panel) {
   }

   public void buttonMouseClicked(Button p0, int p2, int p3, int p4, CategoryPanel panel) {
      if (panel.categoryButton.enabled) {
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         boolean hovering = (float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + 35.0F + xOff && (float)p3 <= p0.y + 6.0F + yOff;
         if (hovering) {
            if (p4 == 0) {
               if (!p0.isBinding) {
                  p0.module.toggle();
                  p0.enabled = p0.module.isEnabled();
               } else {
                  p0.isBinding = false;
               }
            } else if (p4 == 1) {
               if (p0.isBinding) {
                  p0.module.setKeybind(new Keybind(p0.module, Keyboard.getKeyIndex("NONE")));
                  p0.isBinding = false;
               } else {
                  p0.isBinding = true;
               }
            }
         } else if (p0.isBinding) {
            p0.isBinding = false;
         }
      }

   }

   public void buttonDraw(Button p0, float p2, float p3, CategoryPanel panel) {
      if (panel.categoryButton.enabled) {
         GlStateManager.pushMatrix();
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6D, (double)(p0.y + yOff) + 0.6D, (double)(p0.x + 6.0F + xOff) + -0.6D, (double)(p0.y + 6.0F + yOff) + -0.6D, Colors.getColor(10, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + 6.0F + xOff + -1.0F), (double)(p0.y + 6.0F + yOff + -1.0F), Colors.getColor(76, (int)this.opacity.getOpacity()), Colors.getColor(51, (int)this.opacity.getOpacity()));
         p0.enabled = p0.module.isEnabled();
         boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0F + xOff && p3 <= p0.y + 6.0F + yOff;
         GlStateManager.pushMatrix();
         Client.fs.drawStringWithShadow(p0.module.getName(), p0.x + xOff + 3.0F, p0.y + 0.5F + yOff - 7.0F, Colors.getColor(220, (int)this.opacity.getOpacity()));
         Client.fss.drawStringWithShadow("Enable", p0.x + 7.6F + xOff, p0.y + 1.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
         String meme = !p0.module.getKeybind().getKeyStr().equalsIgnoreCase("None") ? "[" + p0.module.getKeybind().getKeyStr() + "]" : "[-]";
         GlStateManager.pushMatrix();
         GlStateManager.translate(p0.x + xOff + 29.0F, p0.y + 1.0F + yOff, 0.0F);
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         this.mc.fontRendererObj.drawStringWithShadow(meme, 0.0F, 0.0F, p0.isBinding ? Colors.getColor(216, 56, 56, (int)this.opacity.getOpacity()) : Colors.getColor(75, (int)this.opacity.getOpacity()));
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
         if (p0.enabled) {
            RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
         }

         if (hovering && !p0.enabled) {
            RenderingUtil.rectangle((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(255, 40));
         }

         if (hovering) {
            Client.fss.drawStringWithShadow(p0.module.getDescription() != null && !p0.module.getDescription().equalsIgnoreCase("") ? p0.module.getDescription() : "ERROR: No Description Found.", panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, Colors.getColor(220, (int)this.opacity.getOpacity()));
         }

         GlStateManager.popMatrix();
      }

   }

   public void buttonKeyPressed(Button button, int key) {
      if (button.isBinding && key != 0) {
         int keyToBind = key;
         if (key == 1 || key == 14) {
            keyToBind = Keyboard.getKeyIndex("NONE");
         }

         Keybind keybind = new Keybind(button.module, keyToBind);
         button.module.setKeybind(keybind);
         ModuleManager.saveStatus();
         button.isBinding = false;
      }

   }

   public void checkBoxMouseClicked(Checkbox p0, int p2, int p3, int p4, CategoryPanel panel) {
      if (panel.categoryButton.enabled) {
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         boolean hovering = (float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + 35.0F + xOff && (float)p3 <= p0.y + 6.0F + yOff;
         if (hovering && p4 == 0) {
            boolean xd = ((Boolean)p0.setting.getValue()).booleanValue();
            p0.setting.setValue(!xd);
            ModuleManager.saveSettings();
         }
      }

   }

   public void checkBoxDraw(Checkbox p0, float p2, float p3, CategoryPanel panel) {
      if (panel.categoryButton.enabled) {
         GlStateManager.pushMatrix();
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         GlStateManager.pushMatrix();
         String xd = p0.setting.getName().charAt(0) + p0.setting.getName().toLowerCase().substring(1);
         Client.fss.drawStringWithShadow(xd, p0.x + 7.5F + xOff, p0.y + 1.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
         GlStateManager.popMatrix();
         RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6D, (double)(p0.y + yOff) + 0.6D, (double)(p0.x + 6.0F + xOff) + -0.6D, (double)(p0.y + 6.0F + yOff) + -0.6D, Colors.getColor(10, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + 6.0F + xOff + -1.0F), (double)(p0.y + 6.0F + yOff + -1.0F), Colors.getColor(76), Colors.getColor(51, (int)this.opacity.getOpacity()));
         p0.enabled = ((Boolean)p0.setting.getValue()).booleanValue();
         boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0F + xOff && p3 <= p0.y + 6.0F + yOff;
         if (p0.enabled) {
            RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
         }

         if (hovering && !p0.enabled) {
            RenderingUtil.rectangle((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(255, 40));
         }

         if (hovering) {
            Client.fss.drawStringWithShadow(this.getDescription(p0.setting), panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, Colors.getColor(255, (int)this.opacity.getOpacity()));
         }

         GlStateManager.popMatrix();
      }

   }

   public void dropDownContructor(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
      int y = 10;
      String[] var6 = p0.option.getOptions();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String value = var6[var8];
         p0.buttons.add(new DropdownButton(value, p2, p3 + (float)y, p0));
         y += 9;
      }

   }

   public void dropDownMouseClicked(DropdownBox dropDown, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
      Iterator var6 = dropDown.buttons.iterator();

      while(var6.hasNext()) {
         DropdownButton db = (DropdownButton)var6.next();
         if (dropDown.active && dropDown.panel.visible) {
            db.mouseClicked(mouseX, mouseY, mouse);
         }
      }

      if ((float)mouseX >= panel.categoryButton.panel.dragX + dropDown.x && (float)mouseY >= panel.categoryButton.panel.dragY + dropDown.y && (float)mouseX <= panel.categoryButton.panel.dragX + dropDown.x + 40.0F && (float)mouseY <= panel.categoryButton.panel.dragY + dropDown.y + 8.0F && mouse == 0 && dropDown.panel.visible) {
         dropDown.active = !dropDown.active;
      } else {
         dropDown.active = false;
      }

   }

   public void dropDownDraw(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
      float xOff = panel.categoryButton.panel.dragX;
      float yOff = panel.categoryButton.panel.dragY;
      boolean hovering = p2 >= panel.categoryButton.panel.dragX + p0.x && p3 >= panel.categoryButton.panel.dragY + p0.y && p2 <= panel.categoryButton.panel.dragX + p0.x + 40.0F && p3 <= panel.categoryButton.panel.dragY + p0.y + 9.0F;
      RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F) + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), Colors.getColor(31, (int)this.opacity.getOpacity()), Colors.getColor(36, (int)this.opacity.getOpacity()));
      if (hovering) {
         RenderingUtil.rectangleBordered((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), 0.3D, Colors.getColor(0, 0), Colors.getColor(90, (int)this.opacity.getOpacity()));
      }

      Client.fss.drawStringWithShadow(p0.option.getName(), p0.x + xOff + 1.0F, p0.y - 6.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)(p0.x + xOff + 38.0F) - (p0.active ? 2.5D : 0.0D), (double)p0.y + 4.5D + (double)yOff, 0.0D);
      GlStateManager.rotate(p0.active ? 270.0F : 90.0F, 0.0F, 0.0F, 90.0F);
      RenderingUtil.rectangle(-1.0D, 0.0D, -0.5D, 2.5D, Colors.getColor(0));
      RenderingUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(151));
      RenderingUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(151));
      RenderingUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(151));
      GlStateManager.popMatrix();
      Client.fss.drawString(p0.option.getSelected(), p0.x + 4.0F + xOff - 1.0F, p0.y + 3.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
      if (p0.option.getSelected().contains("180")) {
         this.mc.fontRendererObj.drawString("树屋", p0.x + 3.0F + xOff + Client.fss.getWidth(p0.option.getSelected()), p0.y + yOff + 0.5F, Colors.getColor(151, (int)this.opacity.getOpacity()));
      }

      if (p0.active) {
         int i = p0.buttons.size();
         RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + 10.0F + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F + (float)(9 * i)) + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff + 10.0F), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F + (float)(9 * i)), Colors.getColor(31, (int)this.opacity.getOpacity()), Colors.getColor(36, (int)this.opacity.getOpacity()));
      }

      if (hovering) {
         Client.fss.drawStringWithShadow(this.getDescription(p0.setting), panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, -1);
      }

   }

   public void dropDownButtonMouseClicked(DropdownButton p0, DropdownBox p1, int x, int y, int mouse) {
      if ((float)x >= p1.panel.categoryButton.panel.dragX + p0.x && (float)y >= p1.panel.categoryButton.panel.dragY + p0.y && (float)x <= p1.panel.categoryButton.panel.dragX + p0.x + 40.0F && (double)y <= (double)(p1.panel.categoryButton.panel.dragY + p0.y) + 8.5D && mouse == 0) {
         p1.option.setSelected(p0.name);
         p1.active = false;
      }

   }

   public void dropDownButtonDraw(DropdownButton p0, DropdownBox p1, float x, float y) {
      float xOff = p1.panel.categoryButton.panel.dragX;
      float yOff = p1.panel.categoryButton.panel.dragY;
      boolean hovering = x >= xOff + p0.x && y >= yOff + p0.y && x <= xOff + p0.x + 40.0F && (double)y <= (double)(yOff + p0.y) + 8.5D;
      GlStateManager.pushMatrix();
      boolean active = p1.option.getSelected().equalsIgnoreCase(p0.name);
      TTFFontRenderer font = hovering ? Client.test2 : Client.test3;
      font.drawStringWithShadow((!hovering && !active ? "" : "§l") + p0.name, p0.x + 3.0F + xOff, p0.y + 2.0F + yOff, active ? Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()) : -1);
      if (p0.name.contains("180")) {
         this.mc.fontRendererObj.drawStringWithShadow("树屋", p0.x + 3.0F + xOff + font.getWidth(p0.name), p0.y + yOff - 1.0F, active ? Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()) : -1);
      }

      GlStateManager.popMatrix();
   }

   public void multiDropDownContructor(MultiDropdownBox p0, float x, float u, CategoryPanel panel) {
      int y = 10;

      for(Iterator var6 = p0.setting.getBooleans().iterator(); var6.hasNext(); y += 9) {
         Setting value = (Setting)var6.next();
         p0.buttons.add(new MultiDropdownButton(value.getName(), x, u + (float)y, p0, value));
      }

   }

   public void multiDropDownMouseClicked(MultiDropdownBox p0, int x, int u, int mouse, CategoryPanel panel) {
      Iterator var6 = p0.buttons.iterator();

      while(var6.hasNext()) {
         MultiDropdownButton db = (MultiDropdownButton)var6.next();
         if (p0.active && p0.panel.visible) {
            db.mouseClicked(x, u, mouse);
         }
      }

      if (mouse == 0) {
         if ((float)x >= panel.categoryButton.panel.dragX + p0.x && (float)u >= panel.categoryButton.panel.dragY + p0.y && (float)x <= panel.categoryButton.panel.dragX + p0.x + 40.0F && (float)u <= panel.categoryButton.panel.dragY + p0.y + 8.0F && mouse == 0 && p0.panel.visible) {
            p0.active = !p0.active;
         } else if ((float)x < panel.categoryButton.panel.dragX + p0.x || (float)u < panel.categoryButton.panel.dragY + p0.y + 8.0F || (float)x > panel.categoryButton.panel.dragX + p0.x + 40.0F || (float)u > panel.categoryButton.panel.dragY + p0.y + 8.0F + (float)(p0.buttons.size() * 9)) {
            p0.active = false;
         }
      }

   }

   public void multiDropDownDraw(MultiDropdownBox p0, float x, float y, CategoryPanel panel) {
      float xOff = panel.categoryButton.panel.dragX;
      float yOff = panel.categoryButton.panel.dragY;
      boolean hovering = x >= panel.categoryButton.panel.dragX + p0.x && y >= panel.categoryButton.panel.dragY + p0.y && x <= panel.categoryButton.panel.dragX + p0.x + 40.0F && y <= panel.categoryButton.panel.dragY + p0.y + 9.0F;
      RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F) + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), Colors.getColor(31, (int)this.opacity.getOpacity()), Colors.getColor(36, (int)this.opacity.getOpacity()));
      if (hovering) {
         RenderingUtil.rectangleBordered((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), 0.3D, Colors.getColor(0, 0), Colors.getColor(90, (int)this.opacity.getOpacity()));
      }

      Client.fss.drawStringWithShadow(p0.name, p0.x + xOff + 1.0F, p0.y - 6.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)(p0.x + xOff + 38.0F) - (p0.active ? 2.5D : 0.0D), (double)p0.y + 4.5D + (double)yOff, 0.0D);
      GlStateManager.rotate(p0.active ? 270.0F : 90.0F, 0.0F, 0.0F, 90.0F);
      RenderingUtil.rectangle(-1.0D, 0.0D, -0.5D, 2.5D, Colors.getColor(0));
      RenderingUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(151));
      RenderingUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(151));
      RenderingUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(151));
      GlStateManager.popMatrix();
      List enabled = new ArrayList();
      p0.setting.getBooleans().forEach((set) -> {
         if (((Boolean)((Setting) set).getValue()).booleanValue()) {
            enabled.add(((Setting) set).getName().charAt(0) + ((Setting) set).getName().toLowerCase().substring(1));
         }

      });
      GlStateManager.pushMatrix();
      this.prepareScissorBox(p0.x + xOff, p0.y + yOff, p0.x + xOff + 35.0F, p0.y + yOff + 9.0F);
      GL11.glEnable(3089);
      String str = enabled.isEmpty() ? "None" : enabled.toString().replace("[", "").replace("]", "");
      Client.fss.drawString(str, p0.x + 4.0F + xOff - 1.0F, p0.y + 3.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
      GL11.glDisable(3089);
      GlStateManager.popMatrix();
      if (p0.active) {
         int i = p0.buttons.size();
         RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + 10.0F + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F + (float)(9 * i)) + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff + 10.0F), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F + (float)(9 * i)), Colors.getColor(31, (int)this.opacity.getOpacity()), Colors.getColor(36, (int)this.opacity.getOpacity()));
      }

      if (hovering) {
         Client.fss.drawStringWithShadow("ERROR: No description found.", panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, -1);
      }

   }

   public void multiDropDownButtonMouseClicked(MultiDropdownButton p0, MultiDropdownBox p1, int x, int y, int mouse) {
      if ((float)x >= p1.panel.categoryButton.panel.dragX + p0.x && (float)y >= p1.panel.categoryButton.panel.dragY + p0.y && (float)x <= p1.panel.categoryButton.panel.dragX + p0.x + 40.0F && (double)y <= (double)(p1.panel.categoryButton.panel.dragY + p0.y) + 8.5D && mouse == 0) {
         p0.setting.setValue(!((Boolean)p0.setting.getValue()).booleanValue());
      }

   }

   public void multiDropDownButtonDraw(MultiDropdownButton p0, MultiDropdownBox p1, float x, float y) {
      float xOff = p1.panel.categoryButton.panel.dragX;
      float yOff = p1.panel.categoryButton.panel.dragY;
      boolean hovering = x >= xOff + p0.x && y >= yOff + p0.y && x <= xOff + p0.x + 40.0F && (double)y <= (double)(yOff + p0.y) + 8.5D;
      GlStateManager.pushMatrix();
      boolean active = ((Boolean)p0.setting.getValue()).booleanValue();
      TTFFontRenderer font = hovering ? Client.test2 : Client.test3;
      font.drawStringWithShadow((!hovering && !active ? "" : "§l") + p0.setting.getName().charAt(0) + p0.setting.getName().toLowerCase().substring(1), p0.x + 3.0F + xOff, p0.y + 2.0F + yOff, active ? Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()) : Colors.getColor(255, (int)this.opacity.getOpacity()));
      GlStateManager.popMatrix();
   }

   public void categoryButtonMouseReleased(CategoryButton categoryButton, int x, int y, int button) {
      categoryButton.categoryPanel.mouseReleased(x, y, button);
   }

   public void slButtonDraw(SLButton slButton, float x, float y, MainPanel panel) {
      float xOff = panel.dragX;
      float yOff = panel.dragY + 75.0F;
      boolean hovering = x >= 55.0F + slButton.x + xOff && y >= slButton.y + yOff - 2.0F && x <= 55.0F + slButton.x + xOff + 40.0F && y <= slButton.y + 8.0F + yOff + 2.0F;
      RenderingUtil.rectangleBordered((double)(slButton.x + xOff + 55.0F) - 0.3D, (double)(slButton.y + yOff) - 0.3D - 2.0D, (double)(slButton.x + xOff + 40.0F + 55.0F) + 0.3D, (double)(slButton.y + 8.0F + yOff) + 0.3D + 2.0D, 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()), Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradient((double)(slButton.x + xOff + 55.0F), (double)(slButton.y + yOff - 2.0F), (double)(slButton.x + xOff + 40.0F + 55.0F), (double)(slButton.y + 8.0F + yOff + 2.0F), Colors.getColor(46, (int)this.opacity.getOpacity()), Colors.getColor(27, (int)this.opacity.getOpacity()));
      if (hovering) {
         RenderingUtil.rectangleBordered((double)(slButton.x + xOff + 55.0F), (double)(slButton.y + yOff - 2.0F), (double)(slButton.x + xOff + 40.0F + 55.0F), (double)(slButton.y + 8.0F + yOff + 2.0F), 0.6D, Colors.getColor(0, 0), Colors.getColor(90, (int)this.opacity.getOpacity()));
      }

      float xOffset = Client.fs.getWidth(slButton.name) / 2.0F;
      Client.fs.drawStringWithShadow(slButton.name, xOff + 25.0F + 55.0F - xOffset, slButton.y + yOff + 1.5F, -1);
   }

   public void slButtonMouseClicked(SLButton slButton, float x, float y, int button, MainPanel panel) {
      float xOff = panel.dragX;
      float yOff = panel.dragY + 75.0F;
      if (button == 0 && x >= 55.0F + slButton.x + xOff && y >= slButton.y + yOff - 2.0F && x <= 55.0F + slButton.x + xOff + 40.0F && y <= slButton.y + 8.0F + yOff + 2.0F) {
         if (slButton.load) {
            ChatUtil.printChat("Settings have been loaded.");
            ModuleManager.loadSettings();
            ColorCommand.loadStatus();
            panel.typePanel.forEach((o) -> {
               o.sliders.forEach((slider) -> {
                  slider.dragX = slider.lastDragX = ((Number)slider.setting.getValue()).doubleValue() * 40.0D / slider.setting.getMax();
               });
            });
         } else {
            ChatUtil.printChat("Settings have been saved.");
            ColorCommand.saveStatus();
            ModuleManager.saveSettings();
            panel.typePanel.forEach((o) -> {
               o.sliders.forEach((slider) -> {
                  slider.dragX = slider.lastDragX = ((Number)slider.setting.getValue()).doubleValue() * 40.0D / slider.setting.getMax();
               });
            });
         }
      }

   }

   public void colorConstructor(ColorPreview colorPreview, float x, float y) {
      colorPreview.sliders.add(new HSVColorPicker(x + 10.0F, y, colorPreview, colorPreview.colorObject));
   }

   public void colorPrewviewDraw(ColorPreview colorPreview, float x, float y) {
      float xOff = colorPreview.x + colorPreview.categoryPanel.panel.dragX;
      float yOff = colorPreview.y + colorPreview.categoryPanel.panel.dragY + 75.0F;
      RenderingUtil.rectangleBordered((double)(xOff - 80.0F), (double)(yOff - 6.0F), (double)(xOff + 1.0F), (double)(yOff + 46.0F), 0.3D, Colors.getColor(48, (int)this.opacity.getOpacity()), Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(xOff - 79.0F), (double)(yOff - 5.0F), (double)xOff, (double)(yOff + 45.0F), Colors.getColor(17, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)(xOff - 74.0F), (double)(yOff - 6.0F), (double)(xOff - 73.0F + Client.fs.getWidth(colorPreview.colorName) + 1.0F), (double)(yOff - 4.0F), Colors.getColor(17, (int)this.opacity.getOpacity()));
      Client.fs.drawStringWithShadow(colorPreview.colorName, xOff - 73.0F, yOff - 8.0F, Colors.getColor(255, (int)this.opacity.getOpacity()));
      colorPreview.sliders.forEach((o) -> {
         ((HSVColorPicker) o).draw(x, y);
      });
   }

   public void colorPickerConstructor(HSVColorPicker picker, float x, float y) {
      Color color = new Color(picker.colorPreview.colorObject.getColorInt());
      picker.opacity = (float)picker.colorPreview.colorObject.getAlpha() / 255.0F;
      picker.hue = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[])null)[0];
      picker.saturation = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[])null)[1];
      picker.brightness = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[])null)[2];
   }

   public void colorPickerDraw(HSVColorPicker cp, float x, float y) {
      float xOff = cp.x + cp.colorPreview.categoryPanel.panel.dragX - 85.5F;
      float yOff = cp.y + cp.colorPreview.categoryPanel.panel.dragY + 74.0F;
      RenderingUtil.rectangle((double)xOff, (double)yOff, (double)(xOff + 43.0F), (double)(yOff + 43.0F), Colors.getColor(32, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangle((double)xOff + 0.5D, (double)yOff + 0.5D, (double)xOff + 42.5D, (double)yOff + 42.5D, Color.getHSBColor(cp.hue, 1.0F, 1.0F).getRGB());
      Depth.pre();
      Depth.mask();
      RenderingUtil.rectangle((double)xOff + 0.5D, (double)yOff + 0.5D, (double)xOff + 42.5D, (double)yOff + 42.5D, -1);
      Depth.render();
      RenderingUtil.drawGradientSideways((double)xOff + 0.5D, (double)yOff + 0.5D, (double)(xOff + 46.5F), (double)yOff + 42.5D, Colors.getColor(255, 255), Colors.getColor(255, 0));
      RenderingUtil.drawGradient((double)xOff + 0.5D, (double)(yOff - 4.0F), (double)xOff + 42.5D, (double)yOff + 42.5D, Colors.getColor(0, 0), Colors.getColor(0, 255));
      Depth.post();
      RenderingUtil.rectangleBordered((double)xOff + 42.5D * (double)cp.saturation - 1.0D, (double)yOff + 42.5D - 42.5D * (double)cp.brightness - 1.0D, (double)xOff + 42.5D * (double)cp.saturation + 1.0D, (double)yOff + 42.5D - 42.5D * (double)cp.brightness + 1.0D, 0.5D, Color.getHSBColor(cp.hue, cp.saturation, cp.brightness).getRGB(), Colors.getColor(0));
      RenderingUtil.rectangle((double)(xOff + 45.0F), (double)yOff, (double)(xOff + 48.0F), (double)(yOff + 43.0F), Colors.getColor(32, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 0.5F), (double)(xOff + 47.5F), (double)(yOff + 8.0F), Color.getHSBColor(0.0F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.2F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 8.0F), (double)(xOff + 47.5F), (double)(yOff + 13.0F), Color.getHSBColor(0.2F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.3F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 13.0F), (double)(xOff + 47.5F), (double)(yOff + 17.0F), Color.getHSBColor(0.3F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.4F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 17.0F), (double)(xOff + 47.5F), (double)(yOff + 22.0F), Color.getHSBColor(0.4F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.5F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 22.0F), (double)(xOff + 47.5F), (double)(yOff + 26.0F), Color.getHSBColor(0.5F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.6F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 26.0F), (double)(xOff + 47.5F), (double)(yOff + 30.0F), Color.getHSBColor(0.6F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.7F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 30.0F), (double)(xOff + 47.5F), (double)(yOff + 34.0F), Color.getHSBColor(0.7F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(0.8F, 1.0F, 1.0F).getRGB());
      RenderingUtil.drawGradient((double)(xOff + 45.5F), (double)(yOff + 34.0F), (double)(xOff + 47.5F), (double)yOff + 42.5D, Color.getHSBColor(0.8F, 1.0F, 1.0F).getRGB(), Color.getHSBColor(1.0F, 1.0F, 1.0F).getRGB());
      RenderingUtil.rectangleBordered((double)(xOff + 45.0F), (double)yOff + 42.5D * (double)cp.hue - 1.5D, (double)(xOff + 48.0F), (double)yOff + 42.5D * (double)cp.hue + 1.5D, 0.5D, Colors.getColor(0, 0), Colors.getColor(cp.selectingHue ? 255 : 200, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(xOff + 50.0F), (double)yOff, (double)(xOff + 53.0F), (double)(yOff + 43.0F), 0.5D, cp.color.getColorInt(), Colors.getColor(32, (int)this.opacity.getOpacity()));
      RenderingUtil.rectangleBordered((double)(xOff + 50.0F), (double)yOff + 42.5D * (double)cp.opacity - 1.5D, (double)(xOff + 53.0F), (double)yOff + 42.5D * (double)cp.opacity + 1.5D, 0.5D, Colors.getColor(0, 0), Colors.getColor(cp.selectingOpacity ? 255 : 200, (int)this.opacity.getOpacity()));
      boolean shouldUpdate = cp.selectingHue || cp.selectingColor || cp.selectingOpacity;
      if (shouldUpdate) {
         Color tcolor = Color.getHSBColor(1.0F - cp.hue, cp.saturation, cp.brightness);
         cp.color.updateColors(tcolor.getRed(), tcolor.getBlue(), tcolor.getGreen(), (int)(255.0F * cp.opacity));
      }

      float tempY;
      if (cp.selectingOpacity) {
         tempY = y;
         if (y > yOff + 42.0F) {
            tempY = yOff + 42.0F;
         } else if (y < yOff) {
            tempY = yOff;
         }

         tempY -= yOff;
         cp.opacity = tempY / 42.0F;
      }

      if (cp.selectingHue) {
         tempY = y;
         if (y > yOff + 42.0F) {
            tempY = yOff + 42.0F;
         } else if (y < yOff) {
            tempY = yOff;
         }

         tempY -= yOff;
         cp.hue = tempY / 42.0F;
      }

      if (cp.selectingColor) {
         tempY = y;
         float tempX = x;
         if (y > yOff + 43.0F) {
            tempY = yOff + 43.0F;
         } else if (y < yOff) {
            tempY = yOff;
         }

         tempY -= yOff;
         if (x > xOff + 43.0F) {
            tempX = xOff + 43.0F;
         } else if (x < xOff) {
            tempX = xOff;
         }

         tempX -= xOff;
         cp.brightness = 1.0F - tempY / 43.0F;
         cp.saturation = tempX / 43.0F;
      }

      RenderingUtil.rectangle((double)(xOff + 57.0F), (double)yOff, (double)(xOff + 72.0F), (double)(yOff + 30.0F), -1);
      boolean offset = false;

      for(int yThing = 0; yThing < 30; ++yThing) {
         for(int i = offset ? 0 : 1; i < 15; i += 2) {
            RenderingUtil.rectangle((double)(xOff + 57.0F + (float)i), (double)(yOff + (float)yThing), (double)(xOff + 57.0F + (float)i + 1.0F), (double)(yOff + (float)yThing + 1.0F), Colors.getColor(190));
         }

         offset = !offset;
      }

      RenderingUtil.rectangleBordered((double)(xOff + 59.0F), (double)(yOff + 2.0F), (double)(xOff + 70.0F), (double)(yOff + 28.0F), 0.5D, cp.colorPreview.colorObject.getColorInt(), Color.BLACK.getRGB());
      GlStateManager.pushMatrix();
      GlStateManager.translate(xOff + 65.0F, yOff + 33.0F, 0.0F);
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      this.mc.fontRendererObj.drawStringWithShadow("Copy", (float)(0 - this.mc.fontRendererObj.getStringWidth("Copy") / 2), 0.0F, -1);
      this.mc.fontRendererObj.drawStringWithShadow("Paste", (float)(0 - this.mc.fontRendererObj.getStringWidth("Paste") / 2), 12.0F, -1);
      GlStateManager.popMatrix();
   }

   private boolean hovering(float mouseX, float mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + height;
   }

   public static String copy() {
      String ret = "";
      Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable clipTf = sysClip.getContents((Object)null);
      if (clipTf != null && clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
         try {
            ret = (String)clipTf.getTransferData(DataFlavor.stringFlavor);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      return ret;
   }

   public static void paste(String writeMe) {
      Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
      Transferable tText = new StringSelection(writeMe);
      clip.setContents(tText, (ClipboardOwner)null);
   }

   public void colorPickerClick(HSVColorPicker cp, float x, float y, int mouse) {
      float xOff = cp.x + cp.colorPreview.categoryPanel.panel.dragX - 85.5F;
      float yOff = cp.y + cp.colorPreview.categoryPanel.panel.dragY + 74.0F;
      if (mouse == 0) {
         try {
            String hex;
            if (this.hovering(x, y, xOff + 59.0F, yOff + 33.0F, 12.0F, 4.0F)) {
               hex = String.format("#%02X%02X%02X", cp.color.getRed(), cp.color.getGreen(), cp.color.getBlue());
               paste(hex);
               DevNotifications.getManager().post("Copied to clipboard hex: " + hex);
            }

            if (this.hovering(x, y, xOff + 59.0F, yOff + 39.0F, 12.0F, 4.0F)) {
               hex = copy();
               if (!Objects.equals(hex, "")) {
                  if (!hex.startsWith("#")) {
                     hex = "#" + hex;
                  }

                  Color c = Color.decode(hex);
                  cp.opacity = (float)cp.colorPreview.colorObject.getAlpha() / 255.0F;
                  cp.hue = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), (float[])null)[0];
                  cp.saturation = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), (float[])null)[1];
                  cp.brightness = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), (float[])null)[2];
                  Color tcolor = Color.getHSBColor(1.0F - cp.hue, cp.saturation, cp.brightness);
                  cp.color.updateColors(tcolor.getRed(), tcolor.getBlue(), tcolor.getGreen(), (int)(255.0F * cp.opacity));
               }

               DevNotifications.getManager().post("Pasted color to " + cp.colorPreview.colorName);
            }
         } catch (Exception var10) {
            DevNotifications.getManager().post("§c" + var10.getMessage() + " exception!");
         }
      }

      if (!cp.selectingHue && !cp.selectingColor && !cp.selectingOpacity && mouse == 0) {
         if (this.hovering(x, y, xOff + 50.0F, yOff, 3.0F, 43.0F)) {
            cp.selectingOpacity = true;
         }

         if (this.hovering(x, y, xOff + 45.0F, yOff, 3.0F, 43.0F)) {
            cp.selectingHue = true;
         }

         if (this.hovering(x, y, xOff, yOff, 43.0F, 43.0F)) {
            cp.selectingColor = true;
         }
      }

   }

   public void colorPickerMovedOrUp(HSVColorPicker slider, float x, float y, int mouse) {
      if (mouse == 0 && (slider.selectingHue || slider.selectingColor || slider.selectingOpacity)) {
         ColorCommand.saveStatus();
         slider.selectingOpacity = false;
         slider.selectingColor = false;
         slider.selectingHue = false;
      }

   }

   public void SliderMouseMovedOrUp(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
      if (mouse == 0) {
         slider.dragging = false;
      }

   }

   public void SliderContructor(Slider p0, CategoryPanel panel) {
      double percent = (((Number)p0.setting.getValue()).doubleValue() - p0.setting.getMin()) / (p0.setting.getMax() - p0.setting.getMin());
      p0.dragX = 40.0D * percent;
   }

   public void SliderDraw(Slider slider, float x, float y, CategoryPanel panel) {
      if (panel.visible) {
         GlStateManager.pushMatrix();
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         double percent = slider.dragX / 38.0D;
         double value = MathUtils.getIncremental(percent * 100.0D * (slider.setting.getMax() - slider.setting.getMin()) / 100.0D + slider.setting.getMin(), slider.setting.getInc());
         float sliderX = (float)((((Number)slider.setting.getValue()).doubleValue() - slider.setting.getMin()) / (slider.setting.getMax() - slider.setting.getMin()) * 38.0D);
         RenderingUtil.rectangle((double)(slider.x + xOff) - 0.3D, (double)(slider.y + yOff) - 0.3D, (double)(slider.x + xOff + 38.0F) + 0.3D, (double)(slider.y + yOff) + 2.5D + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradient((double)(slider.x + xOff), (double)(slider.y + yOff), (double)(slider.x + xOff + 38.0F), (double)(slider.y + yOff) + 2.5D, Colors.getColor(46, (int)this.opacity.getOpacity()), Colors.getColor(27, (int)this.opacity.getOpacity()));
         if (slider.setting.getMin() < 0.0D && slider.setting.getMax() > 0.0D) {
            if (value > 0.0D) {
               RenderingUtil.drawGradient((double)(slider.x + xOff + 19.0F), (double)(slider.y + yOff), (double)(slider.x + xOff + sliderX), (double)(slider.y + yOff) + 2.5D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
            } else {
               RenderingUtil.drawGradient((double)(slider.x + xOff + sliderX), (double)(slider.y + yOff), (double)(slider.x + xOff + 19.0F), (double)(slider.y + yOff) + 2.5D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
            }
         } else {
            RenderingUtil.drawGradient((double)(slider.x + xOff), (double)(slider.y + yOff), (double)(slider.x + xOff + sliderX), (double)(slider.y + yOff) + 2.5D, Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, (int)this.opacity.getOpacity()), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
         }

         boolean hoverMinus = x >= panel.x + xOff + slider.x - 3.0F && y >= yOff + panel.y + slider.y && (double)x <= (double)(xOff + panel.x + slider.x) - 0.5D && y <= yOff + panel.y + slider.y + 2.0F;
         boolean hoverPlus = (double)x >= (double)(panel.x + xOff + slider.x) + 38.5D && y >= yOff + panel.y + slider.y && x <= xOff + panel.x + slider.x + 41.0F && y <= yOff + panel.y + slider.y + 2.0F;
         RenderingUtil.rectangle((double)(slider.x + xOff) - 2.5D, (double)(slider.y + yOff + 1.0F), (double)(slider.x + xOff - 1.0F), (double)(slider.y + yOff) + 1.5D, Colors.getColor(hoverMinus ? 220 : 120, (int)this.opacity.getOpacity()));
         RenderingUtil.rectangle((double)(slider.x + xOff + 39.0F), (double)(slider.y + yOff + 1.0F), (double)(slider.x + xOff) + 40.5D, (double)(slider.y + yOff) + 1.5D, Colors.getColor(hoverPlus ? 220 : 120, (int)this.opacity.getOpacity()));
         RenderingUtil.rectangle((double)(slider.x + xOff) + 39.5D, (double)(slider.y + yOff) + 0.5D, (double)(slider.x + xOff + 40.0F), (double)(slider.y + yOff + 2.0F), Colors.getColor(hoverPlus ? 220 : 120, (int)this.opacity.getOpacity()));
         String xd = slider.setting.getName().charAt(0) + slider.setting.getName().toLowerCase().substring(1);
         double setting = ((Number)slider.setting.getValue()).doubleValue();
         GlStateManager.pushMatrix();
         String valu2e = MathUtils.isInteger(setting) ? (setting + "").replace(".0", "") : setting + "";
         String a = slider.setting.getName().toLowerCase();
         if (a.contains("fov")) {
            valu2e = valu2e + "°";
         } else if (a.contains("delay") || a.contains("switch") && slider.setting.getInc() != 1.0D) {
            valu2e = valu2e + "ms";
         }

         if (a.equalsIgnoreCase("Mxaxaps")) {
            xd = "Maxaps";
         }

         float strWidth = Client.fs.getWidth(valu2e);
         Client.fsmallbold.drawBorderedString(valu2e, slider.x + xOff + 38.0F - strWidth, slider.y - 6.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
         GlStateManager.scale(1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         Client.fss.drawStringWithShadow(xd, slider.x + xOff, slider.y - 6.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
         Object newValue;
         if (slider.dragging) {
            slider.dragX = (double)(x - (slider.x + xOff));
            newValue = StringConversions.castNumber(Double.toString(value), slider.setting.getInc());
            slider.setting.setValue(newValue);
         }

         if (((Number)slider.setting.getValue()).doubleValue() <= slider.setting.getMin()) {
            newValue = StringConversions.castNumber(Double.toString(slider.setting.getMin()), slider.setting.getInc());
            slider.setting.setValue(newValue);
         }

         if (((Number)slider.setting.getValue()).doubleValue() >= slider.setting.getMax()) {
            newValue = StringConversions.castNumber(Double.toString(slider.setting.getMax()), slider.setting.getInc());
            slider.setting.setValue(newValue);
         }

         if (slider.dragX <= 0.0D) {
            slider.dragX = 0.0D;
         }

         if (slider.dragX >= 38.0D) {
            slider.dragX = 38.0D;
         }

         if (x >= xOff + slider.x && y >= yOff + slider.y - 6.0F && x <= xOff + slider.x + 38.0F && y <= yOff + slider.y + 3.0F || slider.dragging) {
            Client.fss.drawStringWithShadow(this.getDescription(slider.setting) + " Min: " + slider.setting.getMin() + " Max: " + slider.setting.getMax(), panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, -1);
         }

         GlStateManager.popMatrix();
      }

   }

   public void SliderMouseClicked(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
      float xOff = panel.categoryButton.panel.dragX;
      float yOff = panel.categoryButton.panel.dragY;
      if (panel.visible && (float)mouseX >= panel.x + xOff + slider.x && (float)mouseY >= yOff + panel.y + slider.y - 6.0F && (float)mouseX <= xOff + panel.x + slider.x + 38.0F && (float)mouseY <= yOff + panel.y + slider.y + 4.0F && mouse == 0) {
         slider.dragging = true;
         slider.lastDragX = (double)mouseX - slider.dragX;
      }

      Setting setting;
      double value;
      if (panel.visible && (float)mouseX >= panel.x + xOff + slider.x - 3.0F && (float)mouseY >= yOff + panel.y + slider.y && (double)mouseX <= (double)(xOff + panel.x + slider.x) - 0.5D && (float)mouseY <= yOff + panel.y + slider.y + 2.0F && mouse == 0) {
         setting = slider.setting;
         value = ((Number)setting.getValue()).doubleValue();
         if (value - setting.getInc() >= setting.getMin()) {
            setting.setValue(MathUtils.getIncremental(value - setting.getInc(), setting.getInc()));
         } else {
            setting.setValue(setting.getMin());
         }
      } else if (panel.visible && (double)mouseX >= (double)(panel.x + xOff + slider.x) + 38.5D && (float)mouseY >= yOff + panel.y + slider.y && (float)mouseX <= xOff + panel.x + slider.x + 41.0F && (float)mouseY <= yOff + panel.y + slider.y + 2.0F && mouse == 0) {
         setting = slider.setting;
         value = ((Number)setting.getValue()).doubleValue();
         if (value + setting.getInc() <= setting.getMax()) {
            setting.setValue(MathUtils.getIncremental(value + setting.getInc(), setting.getInc()));
         } else {
            setting.setValue(setting.getMax());
         }
      }

   }

   public void configButtonDraw(ConfigButton configButton, float x, float y) {
      float xOff = configButton.configList.categoryPanel.categoryButton.panel.dragX;
      float yOff = configButton.configList.categoryPanel.categoryButton.panel.dragY;
   }

   public void configButtonMouseClicked(ConfigButton configButton, float x, float y, int button) {
      float xOff = configButton.configList.categoryPanel.categoryButton.panel.dragX;
      float yOff = configButton.configList.categoryPanel.categoryButton.panel.dragY;
   }

   public void configListDraw(ConfigList configList, float x, float y) {
      float xOff = configList.categoryPanel.categoryButton.panel.dragX;
      float yOff = configList.categoryPanel.categoryButton.panel.dragY;
   }

   public void configListMouseClicked(ConfigList configList, float x, float y, int button) {
      float xOff = configList.categoryPanel.categoryButton.panel.dragX;
      float yOff = configList.categoryPanel.categoryButton.panel.dragY;
   }

   public void textBoxDraw(TextBox textBox, float x, float y) {
      CategoryPanel panel = textBox.panel;
      float xOff = panel.categoryButton.panel.dragX;
      float yOff = panel.categoryButton.panel.dragY;
      if (textBox.selectedChar > textBox.textString.length()) {
         textBox.selectedChar = textBox.textString.length();
      } else if (textBox.selectedChar < 0) {
         textBox.selectedChar = 0;
      }

      if (!textBox.isFocused && !textBox.isTyping && !textBox.textString.equals((String)textBox.setting.getValue())) {
         textBox.textString = (String)textBox.setting.getValue();
      }

      int selectedChar = textBox.selectedChar;
      boolean hovering = x >= xOff + textBox.x && y >= yOff + textBox.y && x <= xOff + textBox.x + 84.0F && y <= yOff + textBox.y + 9.0F;
      RenderingUtil.rectangle((double)(textBox.x + xOff) - 0.3D, (double)(textBox.y + yOff) - 0.3D, (double)(textBox.x + xOff + 84.0F) + 0.3D, (double)(textBox.y + yOff + 7.5F) + 0.3D, Colors.getColor(10, (int)this.opacity.getOpacity()));
      RenderingUtil.drawGradient((double)(textBox.x + xOff), (double)(textBox.y + yOff), (double)(textBox.x + xOff + 84.0F), (double)(textBox.y + yOff + 7.5F), Colors.getColor(31, (int)this.opacity.getOpacity()), Colors.getColor(36, (int)this.opacity.getOpacity()));
      if (hovering || textBox.isFocused) {
         RenderingUtil.rectangleBordered((double)(textBox.x + xOff), (double)(textBox.y + yOff), (double)(textBox.x + xOff + 84.0F), (double)(textBox.y + yOff + 7.5F), 0.3D, Colors.getColor(0, 0), textBox.isFocused ? Colors.getColor(130, (int)this.opacity.getOpacity()) : Colors.getColor(90, (int)this.opacity.getOpacity()));
      }

      String xd = textBox.setting.getName().charAt(0) + textBox.setting.getName().toLowerCase().substring(1);
      Client.fss.drawStringWithShadow(xd, textBox.x + xOff + 1.0F, textBox.y - 6.0F + yOff, Colors.getColor(220, (int)this.opacity.getOpacity()));
      if (textBox.offset > 0.0F && Client.fss.getWidth(textBox.textString) - textBox.offset <= 82.0F) {
         Depth.pre();
         Depth.mask();
         Client.fss.drawString(textBox.textString, textBox.x + 1.5F + xOff - textBox.offset, textBox.y + 2.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.render();
         RenderingUtil.drawGradientSideways((double)(textBox.x + 0.0F + xOff), (double)(textBox.y + 1.0F + yOff), (double)textBox.x + 4.5D + (double)xOff, (double)(textBox.y + 9.0F + yOff), Colors.getColor(0, 0), Colors.getColor(151, (int)this.opacity.getOpacity()));
         RenderingUtil.rectangle((double)(textBox.x + xOff) + 4.5D, (double)(textBox.y + yOff), (double)(textBox.x + xOff + 82.0F), (double)(textBox.y + yOff + 7.5F), Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.post();
      }

      if (textBox.offset > 0.0F && Client.fss.getWidth(textBox.textString) - textBox.offset > 82.0F) {
         Depth.pre();
         Depth.mask();
         Client.fss.drawString(textBox.textString, textBox.x + 1.5F + xOff - textBox.offset, textBox.y + 2.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.render();
         RenderingUtil.drawGradientSideways((double)(textBox.x + 0.0F + xOff), (double)(textBox.y + 1.0F + yOff), (double)textBox.x + 4.5D + (double)xOff, (double)(textBox.y + 9.0F + yOff), Colors.getColor(0, 0), Colors.getColor(151, (int)this.opacity.getOpacity()));
         RenderingUtil.drawGradientSideways((double)(textBox.x + 83.0F) - 4.5D + (double)xOff, (double)(textBox.y + 1.0F + yOff), (double)(textBox.x + 83.0F + xOff), (double)(textBox.y + 9.0F + yOff), Colors.getColor(151, (int)this.opacity.getOpacity()), Colors.getColor(0, 0));
         RenderingUtil.rectangle((double)(textBox.x + xOff) + 4.5D, (double)(textBox.y + yOff), (double)(textBox.x + xOff + 83.0F) - 4.5D, (double)(textBox.y + yOff + 7.5F), Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.post();
      } else if (Client.fss.getWidth(textBox.textString) - textBox.offset > 82.0F) {
         Depth.pre();
         Depth.mask();
         Client.fss.drawString(textBox.textString, textBox.x + 1.5F + xOff - textBox.offset, textBox.y + 2.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.render();
         RenderingUtil.drawGradientSideways((double)(textBox.x + 83.0F) - 4.5D + (double)xOff, (double)(textBox.y + 1.0F + yOff), (double)(textBox.x + 83.0F + xOff), (double)(textBox.y + 9.0F + yOff), Colors.getColor(151, (int)this.opacity.getOpacity()), Colors.getColor(0, 0));
         RenderingUtil.rectangle((double)(textBox.x + xOff + 1.0F), (double)(textBox.y + yOff), (double)(textBox.x + xOff + 83.0F) - 4.5D, (double)(textBox.y + yOff + 7.5F), Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.post();
      } else {
         Depth.pre();
         Depth.mask();
         RenderingUtil.rectangle((double)(textBox.x + xOff + 2.0F), (double)(textBox.y + yOff), (double)(textBox.x + xOff + 82.0F), (double)(textBox.y + yOff + 7.5F), Colors.getColor(90, (int)this.opacity.getOpacity()));
         Depth.render();
         Client.fss.drawString(textBox.textString, textBox.x + 1.5F + xOff - textBox.offset, textBox.y + 2.0F + yOff, Colors.getColor(151, (int)this.opacity.getOpacity()));
         Depth.post();
      }

      if (textBox.opacity.getOpacity() >= 255.0F) {
         textBox.backwards = true;
      } else if (textBox.opacity.getOpacity() <= 40.0F) {
         textBox.backwards = false;
      }

      textBox.opacity.interp(textBox.backwards ? 40.0F : 255.0F, 7);
      if (textBox.isFocused) {
         float width = Client.fss.getWidth(textBox.textString.substring(0, selectedChar));
         float posX = textBox.x + xOff + width - textBox.offset;
         RenderingUtil.rectangle((double)posX - 0.5D, (double)(textBox.y + yOff) + 1.5D, (double)posX, (double)(textBox.y + yOff + 6.0F), Colors.getColor(220, (int)textBox.opacity.getOpacity()));
      } else {
         textBox.opacity.setOpacity(255.0F);
      }

      if (hovering) {
         Client.fss.drawStringWithShadow(this.getDescription(textBox.setting), panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 7.5F + panel.categoryButton.panel.dragY, -1);
      }

   }

   public void textBoxMouseClicked(TextBox textBox, int x, int y, int mouseID) {
      CategoryPanel panel = textBox.panel;
      float xOff = panel.categoryButton.panel.dragX;
      float yOff = panel.categoryButton.panel.dragY;
      boolean hovering = (float)x >= xOff + textBox.x && (float)y >= yOff + textBox.y && (float)x <= xOff + textBox.x + 84.0F && (float)y <= yOff + textBox.y + 9.0F;
      if (hovering && mouseID == 0 && !textBox.isFocused) {
         textBox.isFocused = true;
         Keyboard.enableRepeatEvents(true);
      } else if (!hovering) {
         textBox.isFocused = false;
         textBox.isTyping = false;
         Keyboard.enableRepeatEvents(false);
         ModuleManager.saveSettings();
      }

   }

   public void textBoxKeyPressed(TextBox textBox, int key) {
      if (key == 1 || key == 211 || key == 210 || key == 54) {
         textBox.isFocused = false;
         textBox.isTyping = false;
      }

      char letter = Keyboard.getEventCharacter();
      if (letter == '\r') {
         textBox.isFocused = false;
         textBox.isTyping = false;
         textBox.setting.setValue(textBox.textString);
         ModuleManager.saveSettings();
      } else {
         TTFFontRenderer f;
         float newTextWidth;
         float oldTextWidth;
         float charWidth;
         String oldString;
         StringBuilder stringBuilder;
         if (textBox.isFocused) {
            float width;
            float barOffset;
            switch(key) {
            case 14:
               try {
                  if (textBox.textString.length() != 0) {
                     oldString = textBox.textString;
                     stringBuilder = new StringBuilder(oldString);
                     stringBuilder.charAt(textBox.selectedChar - 1);
                     stringBuilder.deleteCharAt(textBox.selectedChar - 1);
                     textBox.textString = ChatAllowedCharacters.filterAllowedCharacters(stringBuilder.toString());
                     --textBox.selectedChar;
                     if (Client.fss.getWidth(oldString) > 82.0F && textBox.offset > 0.0F) {
                        newTextWidth = Client.fss.getWidth(textBox.textString);
                        oldTextWidth = Client.fss.getWidth(oldString);
                        charWidth = newTextWidth - oldTextWidth;
                        if (newTextWidth <= 82.0F && oldTextWidth - 82.0F > charWidth) {
                           charWidth = 82.0F - oldTextWidth;
                        }

                        textBox.offset += charWidth;
                     }

                     if (textBox.selectedChar > textBox.textString.length()) {
                        textBox.selectedChar = textBox.textString.length();
                     }

                     textBox.setting.setValue(textBox.textString);
                  }
               } catch (Exception var10) {
                  var10.printStackTrace();
               }
               break;
            case 200:
               textBox.selectedChar = 0;
               textBox.offset = 0.0F;
               break;
            case 203:
               if (textBox.selectedChar > 0) {
                  --textBox.selectedChar;
               }

               width = Client.fss.getWidth(textBox.textString.substring(0, textBox.selectedChar));
               barOffset = width - textBox.offset;
               barOffset -= 2.0F;
               if (barOffset < 0.0F) {
                  textBox.offset += barOffset;
               }
               break;
            case 205:
               if (textBox.selectedChar < textBox.textString.length()) {
                  ++textBox.selectedChar;
               }

               width = Client.fss.getWidth(textBox.textString.substring(0, textBox.selectedChar));
               barOffset = width - textBox.offset;
               f = Client.fss;
               if (barOffset > 82.0F) {
                  textBox.offset += barOffset - 82.0F;
               }
               break;
            case 208:
               textBox.selectedChar = textBox.textString.length();
               width = Client.fss.getWidth(textBox.textString.substring(0, textBox.selectedChar));
               barOffset = width - textBox.offset;
               if (barOffset > 82.0F) {
                  textBox.offset += barOffset - 82.0F;
               }
            }
         }

         if (textBox.isFocused && ChatAllowedCharacters.isAllowedCharacter(letter)) {
            if (!textBox.isTyping) {
               textBox.isTyping = true;
            }

            oldString = textBox.textString;
            stringBuilder = new StringBuilder(oldString);
            stringBuilder.insert(textBox.selectedChar, letter);
            textBox.textString = ChatAllowedCharacters.filterAllowedCharacters(stringBuilder.toString());
            if (textBox.selectedChar > textBox.textString.length()) {
               textBox.selectedChar = textBox.textString.length();
            } else if (textBox.selectedChar == oldString.length() && textBox.textString.startsWith(oldString)) {
               textBox.selectedChar += textBox.textString.length() - oldString.length();
            } else {
               ++textBox.selectedChar;
               float width = Client.fss.getWidth(textBox.textString.substring(0, textBox.selectedChar));
               newTextWidth = width - textBox.offset;
               if (newTextWidth > 82.0F) {
                  textBox.offset += newTextWidth - 82.0F;
               }
            }

            f = Client.fss;
            newTextWidth = f.getWidth(textBox.textString);
            oldTextWidth = f.getWidth(oldString);
            if (newTextWidth > 82.0F) {
               if (oldTextWidth < 82.0F) {
                  oldTextWidth = 82.0F;
               }

               charWidth = newTextWidth - oldTextWidth;
               if (textBox.selectedChar == textBox.textString.length()) {
                  textBox.offset += charWidth;
               }
            }

            textBox.setting.setValue(textBox.textString);
         }
      }
   }

   private String getDescription(Setting setting) {
      return setting.getDesc() != null && !setting.getDesc().equalsIgnoreCase("") ? setting.getDesc() : "ERROR: No Description Found.";
   }
}
