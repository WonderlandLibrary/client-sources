package net.augustus.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import net.augustus.Augustus;
import net.augustus.clickgui.buttons.CategoryButton;
import net.augustus.clickgui.buttons.ModuleButton;
import net.augustus.clickgui.buttons.SettingsButton;
import net.augustus.clickgui.screens.ConfigGui;
import net.augustus.events.EventClickGui;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.ui.widgets.CustomButton;
import net.augustus.utils.EventHandler;
import net.augustus.utils.GuiUtils;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.animation.Animate;
import net.augustus.utils.animation.Easing;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.interfaces.MM;
import net.augustus.utils.interfaces.SM;
import net.augustus.utils.skid.tenacity.RenderUtilTenacity;
import net.augustus.utils.skid.tenacity.animations.Animation;
import net.augustus.utils.skid.tenacity.animations.impl.DecelerateAnimation;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickGui extends GuiScreen implements MM, SM {
   private final ResourceLocation zeroTwo = new ResourceLocation("client/clickgui/zerotwo.png");
   Animate anim = new Animate();
   Easing easing = Easing.EXPO_OUT;
   private final ArrayList<CategoryButton> categoryButtons = new ArrayList<>();
   private final ArrayList<ModuleButton> moduleButtonsRandom = new ArrayList<>();
   private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();
   private final ArrayList<SettingsButton> settingsButtons = new ArrayList<>();
   private Categorys draggedCategory = null;
   private boolean keyPress;
   private final TimeHelper timeHelper = new TimeHelper();
   private double sizeCounter;
   private String currentSorting = null;
   private int heightBuffer = 0;

   public ClickGui(String title) {
      anim.setEase(easing);
      anim.setMin(0);
      anim.setMax(500);
      anim.setSpeed(200);
      anim.setReversed(false);
      int x = 10;
      int y = 10;
      int width = 100;
      int height = 18;
      int distancePanels = 20 + width;

      for(Categorys category : Categorys.values()) {
         String categoryName = Character.toUpperCase(category.name().toLowerCase().charAt(0)) + category.name().toLowerCase().substring(1);
         this.categoryButtons.add(new CategoryButton(1, x, y, width, height, categoryName, Color.white, category));
         int i = 1;
         int moduleX = x + 3;
         int moduleWidth = width - 6;
         int moduleHeight = 16;

         for(Module module : mm.getModules()) {
            if (module.getCategory() == category) {
               this.moduleButtons
                  .add(
                     new ModuleButton(2, moduleX, y + i * moduleHeight, moduleWidth, moduleHeight, module.getName(), new Color(0, 0, 0, 190), category, module)
                  );
               int j = 1;
               int settingHeight = 14;

               for(Setting setting : sm.getStgs()) {
                  if (setting.getParent() == module) {
                     this.settingsButtons
                        .add(
                           new SettingsButton(
                              3,
                              moduleX + moduleWidth + 1,
                              y + i * moduleHeight + j * settingHeight,
                              moduleWidth,
                              settingHeight,
                              setting.getName(),
                              new Color(0, 0, 0, 190),
                              module,
                              setting
                           )
                        );
                     ++j;
                  }
               }

               ++i;
            }
         }

         x += distancePanels;
      }

      this.moduleButtonsRandom.addAll(this.moduleButtons);
   }


   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 4) {
         this.mc.displayGuiScreen(new ConfigGui(this));
      }

      super.actionPerformed(button);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(mc);
      //anim.setMax((float) sr.getScaledWidth() / 2);
      anim.update();
      //RenderUtil.drawImage(zeroTwo, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, sr.getScaledWidth() / 4, sr.getScaledHeight() / 3);
//      RenderUtil.drawImage(zeroTwo, anim.getValue(), (float) (sr.getScaledHeight() / 2), (float) (sr.getScaledWidth() / 5.5), (float) sr.getScaledHeight() / 2);
      EventClickGui eventClickGui = new EventClickGui();
      EventHandler.call(eventClickGui);
      if (this.currentSorting == null) {
         String height = mm.clickGUI.sorting.getSelected();
         switch(height) {
            case "Random":
               this.moduleButtons.clear();
               this.moduleButtons.addAll(this.moduleButtonsRandom);
               break;
            case "Alphabet":
               this.moduleButtons.sort(Comparator.comparing(categoryButton -> categoryButton.displayString));
               break;
            case "Length":
               this.moduleButtons
                  .sort(Comparator.comparingDouble(categoryButton -> (double)(1000 - this.mc.fontRendererObj.getStringWidth(categoryButton.displayString))));
         }
      } else if (!this.currentSorting.equals(mm.clickGUI.sorting.getSelected())) {
         String var25 = mm.clickGUI.sorting.getSelected();
         switch(var25) {
            case "Random":
               this.moduleButtons.clear();
               this.moduleButtons.addAll(this.moduleButtonsRandom);
               break;
            case "Alphabet":
               this.moduleButtons.sort(Comparator.comparing(categoryButton -> categoryButton.displayString));
               break;
            case "Length":
               this.moduleButtons
                  .sort(Comparator.comparingDouble(categoryButton -> (double)(1000 - this.mc.fontRendererObj.getStringWidth(categoryButton.displayString))));
         }
      }

      this.currentSorting = mm.clickGUI.sorting.getSelected();

      for(ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.setVisible(false);
      }

      for(SettingsButton settingsButton : this.settingsButtons) {
         settingsButton.setVisible(false);
      }

      for(CategoryButton categoryButton : this.categoryButtons) {
         if (categoryButton.isUnfolded()) {
            for(ModuleButton moduleButton : this.moduleButtons) {
               if (moduleButton.getParent() == categoryButton.getCategory()) {
                  moduleButton.setVisible(true);

                  for(SettingsButton settingsButton : this.settingsButtons) {
                     if (settingsButton.getModule() == moduleButton.getModule() && moduleButton.hasVisibleSetting() && settingsButton.getSetting().isVisible()) {
                        settingsButton.setVisible(true);
                     }
                  }
               }
            }
         }
      }

      int mouseDelta = (int)((float)Mouse.getDWheel() / 5.0F);
      heightBuffer += mouseDelta;
      int height = 18;

      for(CategoryButton categoryButton : this.categoryButtons) {
         if (this.draggedCategory != null && this.draggedCategory == categoryButton.getCategory()) {
            categoryButton.xPosition = (int)((double)mouseX - (categoryButton.getCm()[0] - categoryButton.getCm()[2]));
            categoryButton.yPosition = (int)((double)mouseY - (categoryButton.getCm()[1] - categoryButton.getCm()[3]));
         }
         if(mouseDelta != 0)
        	 categoryButton.yPosition += mouseDelta;

         categoryButton.drawButton(MC.mc, mouseX, mouseY);
         if (categoryButton.isUnfolded()) {
            int i = 0;
            int moduleX = categoryButton.xPosition + 3;
            int moduleHeight = 16;

            for(ModuleButton moduleButton : this.moduleButtons) {
               if (moduleButton.getParent() == categoryButton.getCategory()) {
                  moduleButton.xPosition = moduleX;
                  moduleButton.yPosition = height + i * moduleHeight + categoryButton.yPosition;
                  moduleButton.drawButton(MC.mc, mouseX, mouseY);
                  int settingsPerModule = 0;
                  int underSettingsPerModule = 0;

                  for(SettingsButton sb : this.settingsButtons) {
                     if (sb.getModule() == moduleButton.getModule() && moduleButton.hasVisibleSetting() && sb.getSetting().isVisible()) {
                        if (sb.isDropdownVisible()) {
                           if (sb.getSetting() instanceof StringValue) {
                              underSettingsPerModule += ((StringValue)sb.getSetting()).getStringList().length;
                           } else if (sb.getSetting() instanceof BooleansSetting) {
                              underSettingsPerModule += ((BooleansSetting)sb.getSetting()).getSettingList().length;
                           }
                        }

                        ++settingsPerModule;
                     }
                  }

                  int settingHeight = 14;
                  int j = 0;
                  int k = 0;
                  int buttonYPos = moduleButton.yPosition - heightBuffer;
                  int moduleButtonPosToBottomHeight = sr.getScaledHeight() - moduleButton.yPosition;
                  for(SettingsButton settingsButton : this.settingsButtons) {
                     if (settingsButton.getModule() == moduleButton.getModule() && moduleButton.hasVisibleSetting() && settingsButton.getSetting().isVisible()) {
                        settingsButton.xPosition = moduleX + moduleButton.getButtonWidth() + 1;
                        settingsButton.yPosition = buttonYPos + j * settingHeight + k * settingHeight + heightBuffer;
                        settingsButton.drawButton(MC.mc, mouseX, mouseY);
                        if (settingsButton.isDropdownVisible()) {
                           if (settingsButton.getSetting() instanceof StringValue) {
                              k += ((StringValue)settingsButton.getSetting()).getStringList().length;
                           } else if (settingsButton.getSetting() instanceof BooleansSetting) {
                              k += ((BooleansSetting)settingsButton.getSetting()).getSettingList().length;
                           }
                        }

                        ++j;
                     }
                  }

                  ++i;
               }
            }
         }
      }

      if (this.timeHelper.reached(70L)) {
         for(SettingsButton settingsButton : this.settingsButtons) {
            settingsButton.tick();
         }

         this.timeHelper.reset();
      }

      super.drawScreen(mouseX, mouseY, partialTicks);
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      for(CategoryButton categoryButton : this.categoryButtons) {
         categoryButton.click((double)mouseX, (double)mouseY, mouseButton);
      }

      for(ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.click1((double)mouseX, (double)mouseY, mouseButton);
      }

      for(SettingsButton settingsButton : this.settingsButtons) {
         settingsButton.click3((double)mouseX, (double)mouseY, mouseButton);
      }

      super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   @Override
   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
      for(CategoryButton categoryButton : this.categoryButtons) {
         if (this.mouseOver(
               (double)mouseX,
               (double)mouseY,
               (double)categoryButton.xPosition,
               (double)categoryButton.yPosition,
               (double)categoryButton.getButtonWidth(),
               (double)categoryButton.getHeight()
            )
            && clickedMouseButton == 0
            && this.draggedCategory == null) {
            for(Categorys categorys : Categorys.values()) {
               if (categorys == categoryButton.getCategory()) {
                  this.draggedCategory = categorys;
                  break;
               }
            }
         }
      }

      super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
   }

   @Override
   protected void mouseReleased(int mouseX, int mouseY, int state) {
      this.draggedCategory = null;

      for(SettingsButton settingsButton : this.settingsButtons) {
         settingsButton.mouseReleased();
      }
   }

   @Override
   public void initGui() {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.buttonList.add(new CustomButton(4, sr.getScaledWidth() - 70, sr.getScaledHeight() - 40, 50, 20, "Configs", Augustus.getInstance().getClientColor()));
      this.keyPress = false;
      this.setEventButton(-1);
      super.initGui();
   }

   @Override
   public void onGuiClosed() {
      anim = new Animate();
      anim.setEase(easing);
      anim.setMin(0);
      anim.setMax(500);
      anim.setSpeed(200);
      anim.setReversed(false);
//      Augustus.getInstance().getConverter().clickGuiSaver(this.categoryButtons);

      for(ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.onClosed();
      }
      super.onGuiClosed();
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
      for(SettingsButton settingsButton : this.settingsButtons) {
         settingsButton.onKey(keyCode);
      }

      for(ModuleButton moduleButton : this.moduleButtons) {
         moduleButton.onKey(keyCode);
      }
   }

   public boolean mouseOver(double mouseX, double mouseY, double posX, double posY, double width, double height) {
      return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
   }

   public ArrayList<CategoryButton> getCategoryButtons() {
      return this.categoryButtons;
   }
}
