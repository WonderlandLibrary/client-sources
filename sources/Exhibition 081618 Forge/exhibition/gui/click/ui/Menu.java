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
import exhibition.management.keybinding.Keybind;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.MathUtils;
import exhibition.util.RenderingUtil;
import exhibition.util.StringConversions;
import exhibition.util.render.Colors;
import exhibition.util.render.Depth;
import exhibition.util.render.TTFFontRenderer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatAllowedCharacters;

public class Menu extends UI {
   Minecraft mc = Minecraft.getMinecraft();
   private float hue;
   public Opacity opacity = new Opacity(0);
   public void mainConstructor(ClickGui p0) {
   }

   public void mainPanelDraw(MainPanel panel, int p0, int p1) {
       GlStateManager.pushMatrix();
       GlStateManager.popMatrix();
       RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) - 0.3, (double)(panel.y + panel.dragY) - 0.3, (double)(panel.x + 400.0f + panel.dragX) + 0.3, (double)(panel.y + 270.0f + panel.dragY) + 0.3, 0.5, Colors.getColor(60), Colors.getColor(10));
       RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 0.6, (double)(panel.y + panel.dragY) + 0.6, (double)(panel.x + 400.0f + panel.dragX) - 0.6, (double)(panel.y + 270.0f + panel.dragY) - 0.6, 1.3, Colors.getColor(60), Colors.getColor(40));
       RenderingUtil.rectangleBordered((double)(panel.x + panel.dragX) + 2.5, (double)(panel.y + panel.dragY) + 2.5, (double)(panel.x + 400.0f + panel.dragX) - 2.5, (double)(panel.y + 270.0f + panel.dragY) - 2.5, 0.5, Colors.getColor(20), Colors.getColor(10));
       if (this.hue > 255.0f) {
           this.hue = 0.0f;
       }
       float h = this.hue;
       float h2 = this.hue + 100.0f;
       float h3 = this.hue + 200.0f;
       if (h > 255.0f) {
           h = 0.0f;
       }
       if (h2 > 255.0f) {
           h2 -= 255.0f;
       }
       if (h3 > 255.0f) {
           h3 -= 255.0f;
       }
       java.awt.Color color33 = java.awt.Color.getHSBColor(h / 255.0f, 0.9f, 1.0f);
       java.awt.Color color332 = java.awt.Color.getHSBColor(h2 / 255.0f, 0.9f, 1.0f);
       java.awt.Color color333 = java.awt.Color.getHSBColor(h3 / 255.0f, 0.9f, 1.0f);
       int color = color33.getRGB();
       int color2 = color332.getRGB();
       int color3 = color333.getRGB();
       this.hue = (float)((double)this.hue + 0.15);
       RenderingUtil.drawGradientSideways(panel.x + panel.dragX + 3.0f, panel.y + panel.dragY + 3.0f, panel.x + 202.0f + panel.dragX - 3.0f, panel.dragY + panel.y + 4.0f, color, color2);
       RenderingUtil.drawGradientSideways(panel.x + panel.dragX + 199.0f, panel.y + panel.dragY + 3.0f, panel.x + 400.0f + panel.dragX - 3.0f, panel.dragY + panel.y + 4.0f, color2, color3);
       RenderingUtil.rectangle(panel.x + panel.dragX + 3.0f, (double)(panel.y + panel.dragY) + 3.3, panel.x + 400.0f + panel.dragX - 3.0f, panel.dragY + panel.y + 4.0f, Colors.getColor(0, 120));
       boolean isOff = false;
       for (float y = 5.0f; y < 268.0f; y += 4.0f) {
           int x;
           int n = x = isOff ? 6 : 4;
           while (x < 397) {
               RenderingUtil.rectangle(panel.x + panel.dragX + (float)x, panel.y + panel.dragY + y, (double)(panel.x + panel.dragX + (float)x) + 1.3, (double)(panel.y + panel.dragY + y) + 1.6, Colors.getColor(12));
               x += 4;
           }
           isOff = !isOff;
       }
       RenderingUtil.rectangleBordered(panel.x + panel.dragX + 57.0f, panel.y + panel.dragY + 16.0f, panel.x + 390.0f + panel.dragX, panel.y + 250.0f + panel.dragY, 0.5, Colors.getColor(46), Colors.getColor(10));
       RenderingUtil.rectangle(panel.x + panel.dragX + 58.0f, panel.y + panel.dragY + 17.0f, panel.x + 390.0f + panel.dragX - 1.0f, panel.y + 250.0f + panel.dragY - 1.0f, Colors.getColor(17));
       for (SLButton button : panel.slButtons) {
           button.draw(p0, p1);
       }
       for (CategoryButton button : panel.typeButton) {
           button.draw(p0, p1);
       }
       if (panel.dragging) {
           panel.dragX = (float)p0 - panel.lastDragX;
           panel.dragY = (float)p1 - panel.lastDragY;
       }
   }

   public void mainPanelKeyPress(MainPanel panel, int key) {
	      if (this.opacity.getOpacity() >= 10.0F) {
	         if (key == Keyboard.KEY_F12 || key == Keyboard.KEY_INSERT) {
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
      if ((float)x >= mainPanel.x + mainPanel.dragX && (float)y >= mainPanel.dragY + mainPanel.y && (float)x <= mainPanel.dragX + mainPanel.x + 400.0F && (float)y <= mainPanel.dragY + mainPanel.y + 12.0F && z == 0) {
         mainPanel.dragging = true;
         mainPanel.lastDragX = (float)x - mainPanel.dragX;
         mainPanel.lastDragY = (float)y - mainPanel.dragY;
      }

      Iterator var5 = mainPanel.typeButton.iterator();

      while(var5.hasNext()) {
         CategoryButton c = (CategoryButton)var5.next();
         c.mouseClicked(x, y, z);
         c.categoryPanel.mouseClicked(x, y, z);
      }

   }

   public void panelMouseMovedOrUp(MainPanel mainPanel, int x, int y, int z) {
      if (z == 0) {
         mainPanel.dragging = false;
      }

      Iterator var5 = mainPanel.typeButton.iterator();

      while(var5.hasNext()) {
         CategoryButton button = (CategoryButton)var5.next();
         button.mouseReleased(x, y, z);
      }

   }

   public void categoryButtonConstructor(CategoryButton p0, MainPanel p1) {
      p0.categoryPanel = new CategoryPanel(p0.name, p0, 0.0F, 0.0F);
   }

   public void categoryButtonMouseClicked(CategoryButton p0, MainPanel p1, int p2, int p3, int p4) {
       if ((float)p2 >= p0.x + p1.dragX && (float)p3 >= p1.dragY + p0.y && (float)p2 <= p1.dragX + p0.x + Client.f.getWidth(p0.name.toUpperCase()) + 5.0f && (float)p3 <= p1.dragY + p0.y + 12.0f && p4 == 0) {
           int i = 0;
           for (CategoryButton button : p1.typeButton) {
               if (button == p0) {
                   p0.enabled = true;
                   p0.categoryPanel.visible = true;
               } else {
                   button.enabled = false;
                   button.categoryPanel.visible = false;
               }
               ++i;
           }
       }
   }

   @Override
   public void categoryButtonDraw(CategoryButton p0, float p2, float p3) {
       int color;
       int n = color = p0.enabled ? -1 : Colors.getColor(75);
       if (p2 >= p0.x + p0.panel.dragX && p3 >= p0.y + p0.panel.dragY && p2 <= p0.x + p0.panel.dragX + Client.f.getWidth(p0.name.toUpperCase()) + 5.0f && p3 <= p0.y + p0.panel.dragY + 12.0f && !p0.enabled) {
           color = Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255);
       }
       GlStateManager.pushMatrix();
       Client.f.drawStringWithShadow(p0.name.toUpperCase(), p0.x + 4.0f + p0.panel.dragX, p0.y + p0.panel.dragY, color);
       GlStateManager.popMatrix();
       p0.categoryPanel.draw(p2, p3);
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
      float xOff = 62.0F + categoryButton.panel.x;
      float yOff = 20.0F + categoryButton.panel.y;
      float biggestY;
      Module[] var8;
      int var9;
      int var10;
      Module module;
      Iterator var12;
      Setting setting;
      if (categoryButton.name.equalsIgnoreCase("Combat")) {
         biggestY = yOff + 8.0F;
         var8 = (Module[])Client.getModuleManager().getArray();
         var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            module = var8[var10];
            if (module.getType() == ModuleData.Type.Combat) {
               categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff, module));
               y = 8.0F;
               if (this.getSettings(module) != null) {
                  var12 = this.getSettings(module).iterator();

                  while(var12.hasNext()) {
                     setting = (Setting)var12.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff, yOff + y, setting));
                        y += 8.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }

                     if (setting.getValue() instanceof Number) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff, yOff + y + 6.0F, setting));
                        y += 12.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }
                  }
               }

               xOff += 40.0F;
               if (xOff > 20.0F + categoryButton.panel.y + 360.0F) {
                  xOff = 62.0F + categoryButton.panel.x;
                  yOff = biggestY + 8.0F;
               }
            }
         }
      }

      if (categoryButton.name == "Player") {
         biggestY = yOff + 8.0F;
         var8 = (Module[])Client.getModuleManager().getArray();
         var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            module = var8[var10];
            if (module.getType() == ModuleData.Type.Player) {
               categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff, module));
               y = 8.0F;
               if (this.getSettings(module) != null) {
                  var12 = this.getSettings(module).iterator();

                  while(var12.hasNext()) {
                     setting = (Setting)var12.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff, yOff + y, setting));
                        y += 8.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }

                     if (setting.getValue() instanceof Number) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff, yOff + y + 6.0F, setting));
                        y += 12.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }
                  }
               }

               xOff += 45.0F;
               if (xOff > 20.0F + categoryButton.panel.y + 315.0F) {
                  xOff = 62.0F + categoryButton.panel.x;
                  yOff = biggestY + 8.0F;
               }
            }
         }
      }

      if (categoryButton.name == "Movement") {
         biggestY = yOff + 8.0F;
         var8 = (Module[])Client.getModuleManager().getArray();
         var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            module = var8[var10];
            if (module.getType() == ModuleData.Type.Movement) {
               categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff, module));
               y = 8.0F;
               if (this.getSettings(module) != null) {
                  var12 = this.getSettings(module).iterator();

                  while(var12.hasNext()) {
                     setting = (Setting)var12.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff, yOff + y, setting));
                        y += 8.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }

                     if (setting.getValue() instanceof Number) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff, yOff + y + 6.0F, setting));
                        y += 12.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }
                  }
               }

               xOff += 45.0F;
               if (xOff > 20.0F + categoryButton.panel.y + 315.0F) {
                  xOff = 62.0F + categoryButton.panel.x;
                  yOff = biggestY + 8.0F;
               }
            }
         }
      }

      if (categoryButton.name == "Visuals") {
         biggestY = yOff + 8.0F;
         int row = 0;
         Module[] var16 = (Module[])Client.getModuleManager().getArray();
         var10 = var16.length;

         for(int var17 = 0; var17 < var10; ++var17) {
            Module module1 = var16[var17];
            if (module1.getType() == ModuleData.Type.Visuals) {
               categoryPanel.buttons.add(new Button(categoryPanel, module1.getName(), xOff, yOff, module1));
               y = 8.0F;
               if (this.getSettings(module1) != null) {
                  Iterator var19 = this.getSettings(module1).iterator();

                  while(var19.hasNext()) {
                     Setting setting1 = (Setting)var19.next();
                     if (setting1.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting1.getName(), xOff, yOff + y, setting1));
                        y += 8.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }

                     if (setting1.getValue() instanceof Number) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff, yOff + y + 6.0F, setting1));
                        y += 12.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }
                  }
               }

               xOff += 40.0F;
               if (xOff > 20.0F + categoryButton.panel.y + 360.0F) {
                  ++row;
                  xOff = 62.0F + categoryButton.panel.x;
                  yOff = biggestY + 8.0F;
                  if (row == 2) {
                     yOff = 20.0F + categoryButton.panel.y + 24.0F + 80.0F;
                  }
               }
            }
         }
      }

      if (categoryButton.name == "Other") {
         biggestY = yOff + 8.0F;
         var8 = (Module[])Client.getModuleManager().getArray();
         var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            module = var8[var10];
            if (module.getType() == ModuleData.Type.Other) {
               categoryPanel.buttons.add(new Button(categoryPanel, module.getName(), xOff, yOff, module));
               y = 8.0F;
               if (this.getSettings(module) != null) {
                  var12 = this.getSettings(module).iterator();

                  while(var12.hasNext()) {
                     setting = (Setting)var12.next();
                     if (setting.getValue() instanceof Boolean) {
                        categoryPanel.checkboxes.add(new Checkbox(categoryPanel, setting.getName(), xOff, yOff + y, setting));
                        y += 8.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }

                     if (setting.getValue() instanceof Number) {
                        categoryPanel.sliders.add(new Slider(categoryPanel, xOff, yOff + y + 6.0F, setting));
                        y += 12.0F;
                        if (yOff + y >= biggestY) {
                           biggestY = yOff + y;
                        }
                     }
                  }
               }

               xOff += 40.0F;
               if (xOff > 20.0F + categoryButton.panel.y + 360.0F) {
                  xOff = 62.0F + categoryButton.panel.x;
                  yOff = biggestY + 8.0F;
               }
            }
         }
      }

   }

   public void categoryPanelMouseClicked(CategoryPanel categoryPanel, int p1, int p2, int p3) {
      Iterator var5 = categoryPanel.buttons.iterator();

      while(var5.hasNext()) {
         Button button = (Button)var5.next();
         button.mouseClicked(p1, p2, p3);
      }

      var5 = categoryPanel.checkboxes.iterator();

      while(var5.hasNext()) {
         Checkbox checkbox = (Checkbox)var5.next();
         checkbox.mouseClicked(p1, p2, p3);
      }

      var5 = categoryPanel.sliders.iterator();

      while(var5.hasNext()) {
         Slider slider = (Slider)var5.next();
         slider.mouseClicked(p1, p2, p3);
      }

      var5 = categoryPanel.dropdownBoxes.iterator();

      while(var5.hasNext()) {
         DropdownBox db = (DropdownBox)var5.next();
         db.mouseClicked(p1, p2, p3);
      }

   }

   public void categoryPanelDraw(CategoryPanel categoryPanel, float x, float y) {
	      Iterator var4 = categoryPanel.colorPreviews.iterator();

	      while(var4.hasNext()) {
	         ColorPreview cp = (ColorPreview)var4.next();
	         cp.draw(x, y);
	      }

	      var4 = categoryPanel.groupBoxes.iterator();

	      while(var4.hasNext()) {
	         GroupBox groupBox = (GroupBox)var4.next();
	         groupBox.draw(x, y);
	      }

	      var4 = categoryPanel.buttons.iterator();

	      while(var4.hasNext()) {
	         Button button = (Button)var4.next();
	         button.draw(x, y);
	      }

	      var4 = categoryPanel.checkboxes.iterator();

	      while(var4.hasNext()) {
	         Checkbox checkbox = (Checkbox)var4.next();
	         checkbox.draw(x, y);
	      }

	      var4 = categoryPanel.sliders.iterator();

	      while(var4.hasNext()) {
	         Slider slider = (Slider)var4.next();
	         slider.draw(x, y);
	      }

	      var4 = categoryPanel.dropdownBoxes.iterator();

	      DropdownBox db;
	      while(var4.hasNext()) {
	         db = (DropdownBox)var4.next();
	         db.draw(x, y);
	      }

	      var4 = categoryPanel.dropdownBoxes.iterator();

	      while(true) {
	         do {
	            if (!var4.hasNext()) {
	               return;
	            }

	            db = (DropdownBox)var4.next();
	         } while(!db.active);

	         Iterator var6 = db.buttons.iterator();

	         while(var6.hasNext()) {
	            DropdownButton b = (DropdownButton)var6.next();
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

   }

   public void groupBoxConstructor(GroupBox groupBox, float x, float y) {
   }

   public void groupBoxMouseClicked(GroupBox groupBox, int p1, int p2, int p3) {
   }

   public void groupBoxDraw(GroupBox groupBox, float x, float y) {
	      float xOff = groupBox.x + groupBox.categoryPanel.categoryButton.panel.dragX - 2.5F;
	      float yOff = groupBox.y + groupBox.categoryPanel.categoryButton.panel.dragY + 10.0F;
	      RenderingUtil.rectangleBordered((double)xOff, (double)(yOff - 6.0F), (double)(xOff + 90.0F), (double)(yOff + groupBox.ySize), 0.3D, Colors.getColor(48), Colors.getColor(10));
	      RenderingUtil.rectangle((double)(xOff + 1.0F), (double)(yOff - 5.0F), (double)(xOff + 89.0F), (double)(yOff + groupBox.ySize - 1.0F), Colors.getColor(17));
	      RenderingUtil.rectangle((double)(xOff + 5.0F), (double)(yOff - 6.0F), (double)(xOff + Client.fs.getWidth(groupBox.module.getName()) + 5.0F), (double)(yOff - 4.0F), Colors.getColor(17));
	   }

   public void groupBoxMouseMovedOrUp(GroupBox groupBox, int x, int y, int button) {
   }

   public void buttonContructor(Button p0, CategoryPanel panel) {
   }

   public void buttonMouseClicked(Button p0, int p2, int p3, int p4, CategoryPanel panel) {
      if (panel.categoryButton.enabled) {
         float xOff = panel.categoryButton.panel.dragX;
         float yOff = panel.categoryButton.panel.dragY;
         if ((float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + xOff + 6.0F && (float)p3 <= p0.y + yOff + 6.0F && p4 == 0) {
            p0.module.toggle();
            p0.enabled = p0.module.isEnabled();
         }
      }

   }

   public void buttonDraw(Button p0, float p2, float p3, CategoryPanel panel) {
	      if (panel.categoryButton.enabled) {
	         GlStateManager.pushMatrix();
	         float xOff = panel.categoryButton.panel.dragX;
	         float yOff = panel.categoryButton.panel.dragY;
	         RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6D, (double)(p0.y + yOff) + 0.6D, (double)(p0.x + 6.0F + xOff) + -0.6D, (double)(p0.y + 6.0F + yOff) + -0.6D, Colors.getColor(10));
	         RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + 6.0F + xOff + -1.0F), (double)(p0.y + 6.0F + yOff + -1.0F), Colors.getColor(76), Colors.getColor(51));
	         p0.enabled = p0.module.isEnabled();
	         boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0F + xOff && p3 <= p0.y + 6.0F + yOff;
	         GlStateManager.pushMatrix();
	         Client.fs.drawStringWithShadow(p0.module.getName(), p0.x + xOff + 3.0F, p0.y + 0.5F + yOff - 7.0F, Colors.getColor(220));
	         Client.fss.drawStringWithShadow("Enable", p0.x + 7.6F + xOff, p0.y + 1.0F + yOff, Colors.getColor(220));
	         String meme = !p0.module.getKeybind().getKeyStr().equalsIgnoreCase("None") ? "[" + p0.module.getKeybind().getKeyStr() + "]" : "[-]";
	         GlStateManager.pushMatrix();
	         GlStateManager.translate(p0.x + xOff + 29.0F, p0.y + 1.0F + yOff, 0.0F);
	         GlStateManager.scale(0.5D, 0.5D, 0.5D);
	         this.mc.fontRendererObj.drawStringWithShadow(meme, 0.0F, 0.0F, p0.isBinding ? Colors.getColor(216, 56, 56) : Colors.getColor(75));
	         GlStateManager.popMatrix();
	         GlStateManager.popMatrix();
	         if (p0.enabled) {
	            RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
	         }

	         if (hovering && !p0.enabled) {
	            RenderingUtil.rectangle((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(255, 40));
	         }

	         if (hovering) {
	            Client.fss.drawStringWithShadow(p0.module.getDescription() != null && !p0.module.getDescription().equalsIgnoreCase("") ? p0.module.getDescription() : "ERROR: No Description Found.", panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, Colors.getColor(220));
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
         if ((float)p2 >= p0.x + xOff && (float)p3 >= p0.y + yOff && (float)p2 <= p0.x + xOff + 6.0F && (float)p3 <= p0.y + yOff + 6.0F && p4 == 0) {
            boolean xd = ((Boolean)p0.setting.getValue()).booleanValue();
            p0.setting.setValue(!xd);
            //Module.saveSettings();
         }
      }

   }

   public void checkBoxDraw(Checkbox p0, float p2, float p3, CategoryPanel panel) {
	      if (panel.categoryButton.enabled) {
	         GlStateManager.pushMatrix();
	         float xOff = panel.categoryButton.panel.dragX;
	         float yOff = panel.categoryButton.panel.dragY;
	         RenderingUtil.rectangle((double)(p0.x + xOff) + 0.6D, (double)(p0.y + yOff) + 0.6D, (double)(p0.x + 6.0F + xOff) + -0.6D, (double)(p0.y + 6.0F + yOff) + -0.6D, Colors.getColor(10));
	         RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + 6.0F + xOff + -1.0F), (double)(p0.y + 6.0F + yOff + -1.0F), Colors.getColor(76), Colors.getColor(51));
	         p0.enabled = ((Boolean)p0.setting.getValue()).booleanValue();
	         boolean hovering = p2 >= p0.x + xOff && p3 >= p0.y + yOff && p2 <= p0.x + 35.0F + xOff && p3 <= p0.y + 6.0F + yOff;
	         GlStateManager.pushMatrix();
	         String xd = p0.setting.getName().charAt(0) + p0.setting.getName().toLowerCase().substring(1);
	         Client.fss.drawStringWithShadow(xd, p0.x + 7.5F + xOff, p0.y + 1.0F + yOff, Colors.getColor(220));
	         GlStateManager.popMatrix();
	         if (p0.enabled) {
	            RenderingUtil.drawGradient((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 120));
	         }

	         if (hovering && !p0.enabled) {
	            RenderingUtil.rectangle((double)(p0.x + xOff + 1.0F), (double)(p0.y + yOff + 1.0F), (double)(p0.x + xOff + 5.0F), (double)(p0.y + yOff + 5.0F), Colors.getColor(255, 40));
	         }


	         GlStateManager.popMatrix();
	      }

	   }

   public void dropDownContructor(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
   }

   public void dropDownMouseClicked(DropdownBox dropDown, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
      if (dropDown.active) {
         Iterator var6 = dropDown.buttons.iterator();

         while(var6.hasNext()) {
            DropdownButton b = (DropdownButton)var6.next();
            b.mouseClicked(mouseX, mouseY, mouse);
         }
      }

      if ((float)mouseX >= panel.categoryButton.panel.dragX + dropDown.x && (float)mouseY >= panel.categoryButton.panel.dragY + dropDown.y && (float)mouseX <= panel.categoryButton.panel.dragX + dropDown.x + 40.0F && (float)mouseY <= panel.categoryButton.panel.dragY + dropDown.y + 8.0F && mouse == 0) {
         dropDown.active = !dropDown.active;
      }

   }

   public void dropDownDraw(DropdownBox p0, float p2, float p3, CategoryPanel panel) {
	      float xOff = panel.categoryButton.panel.dragX;
	      float yOff = panel.categoryButton.panel.dragY;
	      boolean hovering = p2 >= panel.categoryButton.panel.dragX + p0.x && p3 >= panel.categoryButton.panel.dragY + p0.y && p2 <= panel.categoryButton.panel.dragX + p0.x + 40.0F && p3 <= panel.categoryButton.panel.dragY + p0.y + 9.0F;
	      RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F) + 0.3D, Colors.getColor(10));
	      RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), Colors.getColor(31), Colors.getColor(36));
	      if (hovering) {
	         RenderingUtil.rectangleBordered((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F), 0.3D, Colors.getColor(0, 0), Colors.getColor(90));
	      }

	      Client.fss.drawStringWithShadow(p0.option.getName(), p0.x + xOff + 1.0F, p0.y - 6.0F + yOff, Colors.getColor(220));
	      GlStateManager.pushMatrix();
	      GlStateManager.translate((double)(p0.x + xOff + 38.0F) - (p0.active ? 2.5D : 0.0D), (double)(p0.y + 4.0F + yOff), 0.0D);
	      GlStateManager.rotate(p0.active ? 270.0F : 90.0F, 0.0F, 0.0F, 90.0F);
	      RenderingUtil.rectangle(-1.0D, 0.0D, -0.5D, 2.5D, Colors.getColor(0));
	      RenderingUtil.rectangle(-0.5D, 0.0D, 0.0D, 2.5D, Colors.getColor(151));
	      RenderingUtil.rectangle(0.0D, 0.5D, 0.5D, 2.0D, Colors.getColor(151));
	      RenderingUtil.rectangle(0.5D, 1.0D, 1.0D, 1.5D, Colors.getColor(151));
	      GlStateManager.popMatrix();
	      Client.fss.drawString(p0.option.getSelected(), p0.x + 4.0F + xOff - 1.0F, p0.y + 3.0F + yOff, Colors.getColor(151));
	      if (p0.active) {
	         int i = p0.buttons.size();
	         RenderingUtil.rectangle((double)(p0.x + xOff) - 0.3D, (double)(p0.y + 10.0F + yOff) - 0.3D, (double)(p0.x + xOff + 40.0F) + 0.3D, (double)(p0.y + yOff + 9.0F + (float)(9 * i)) + 0.3D, Colors.getColor(10));
	         RenderingUtil.drawGradient((double)(p0.x + xOff), (double)(p0.y + yOff + 10.0F), (double)(p0.x + xOff + 40.0F), (double)(p0.y + yOff + 9.0F + (float)(9 * i)), Colors.getColor(31), Colors.getColor(36));
	      }

	      if (hovering) {
	         Client.fss.drawStringWithShadow("ERROR: No Description Found.", panel.categoryButton.panel.x + 2.0F + panel.categoryButton.panel.dragX + 55.0F, panel.categoryButton.panel.y + 9.0F + panel.categoryButton.panel.dragY, -1);
	      }

	   }

   public void dropDownButtonMouseClicked(DropdownButton p0, DropdownBox p1, int x, int y, int mouse) {
      if ((float)x >= p1.panel.categoryButton.panel.dragX + p0.x && (float)y >= p1.panel.categoryButton.panel.dragY + p0.y && (float)x <= p1.panel.categoryButton.panel.dragX + p0.x + 40.0F && (float)y <= p1.panel.categoryButton.panel.dragY + p0.y + 8.0F && mouse == 0) {
         ;
      }

   }

   public void dropDownButtonDraw(DropdownButton p0, DropdownBox p1, float x, float y) {
      float xOff = p1.panel.categoryButton.panel.dragX;
      float yOff = p1.panel.categoryButton.panel.dragY;
      RenderingUtil.rectangle((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + 40.0F + xOff), (double)(p0.y + 6.0F + yOff), Colors.getColor(28, 28, 28));
      boolean hovering = x >= xOff + p0.x && y >= yOff + p0.y && x <= xOff + p0.x + 40.0F && y <= yOff + p0.y + 6.0F;
      if (hovering) {
         RenderingUtil.rectangle((double)(p0.x + xOff), (double)(p0.y + yOff), (double)(p0.x + 40.0F + xOff), (double)(p0.y + 6.0F + yOff), Colors.getColor(255, 255, 255, 50));
      }

      GlStateManager.pushMatrix();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      int offSet = this.mc.fontRendererObj.getStringWidth(p0.name) / 2;
      this.mc.fontRendererObj.drawStringWithShadow(p0.name, (p0.x + 20.0F - (float)(offSet / 2) + xOff) * 2.0F, (p0.y + 1.0F + yOff) * 2.0F, -1);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public void SliderContructor(Slider p0, CategoryPanel panel) {
	      double percent = (((Number)p0.setting.getValue()).doubleValue() - p0.setting.getMin()) / (p0.setting.getMax() - p0.setting.getMin());
	      p0.dragX = 40.0D * percent;
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

   public void SliderMouseMovedOrUp(Slider slider, int mouseX, int mouseY, int mouse, CategoryPanel panel) {
      if (mouse == 0) {
         slider.dragging = false;
      }

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

   public void categoryButtonMouseReleased(CategoryButton categoryButton, int x, int y, int button) {
      categoryButton.categoryPanel.mouseReleased(x, y, button);
   }

   public void slButtonDraw(SLButton slButton, float x, float y, MainPanel panel) {
   }

   public void slButtonMouseClicked(SLButton slButton, float x, float y, int button, MainPanel panel) {
   }

   public void colorConstructor(ColorPreview colorPreview, float x, float y) {
   }

   public void colorPrewviewDraw(ColorPreview colorPreview, float x, float y) {
   }

   private String getDescription(Setting setting) {
	      return setting.getDesc() != null && !setting.getDesc().equalsIgnoreCase("") ? setting.getDesc() : "ERROR: No Description Found.";
   }
   
@Override
public void multiDropDownContructor(MultiDropdownBox var1, float var2, float var3, CategoryPanel var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void multiDropDownMouseClicked(MultiDropdownBox var1, int var2, int var3, int var4, CategoryPanel var5) {
	// TODO Auto-generated method stub
	
}

@Override
public void multiDropDownDraw(MultiDropdownBox var1, float var2, float var3, CategoryPanel var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void multiDropDownButtonMouseClicked(MultiDropdownButton var1, MultiDropdownBox var2, int var3, int var4,
		int var5) {
	// TODO Auto-generated method stub
	
}

@Override
public void multiDropDownButtonDraw(MultiDropdownButton var1, MultiDropdownBox var2, float var3, float var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void colorPickerConstructor(HSVColorPicker var1, float var2, float var3) {
	// TODO Auto-generated method stub
	
}

@Override
public void colorPickerDraw(HSVColorPicker var1, float var2, float var3) {
	// TODO Auto-generated method stub
	
}

@Override
public void colorPickerClick(HSVColorPicker var1, float var2, float var3, int var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void colorPickerMovedOrUp(HSVColorPicker var1, float var2, float var3, int var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void configButtonDraw(ConfigButton var1, float var2, float var3) {
	// TODO Auto-generated method stub
	
}

@Override
public void configButtonMouseClicked(ConfigButton var1, float var2, float var3, int var4) {
	// TODO Auto-generated method stub
	
}

@Override
public void configListDraw(ConfigList var1, float var2, float var3) {
	// TODO Auto-generated method stub
	
}

@Override
public void configListMouseClicked(ConfigList var1, float var2, float var3, int var4) {
	// TODO Auto-generated method stub
	
}

@Override
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

@Override
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

@Override
public void textBoxKeyPressed(TextBox textBox, int key) {
    if (key == 1 || key == Keyboard.KEY_F12 || key == Keyboard.KEY_INSERT) {
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
}
