package net.augustus.clickgui.screens;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import net.augustus.Augustus;
import net.augustus.ui.widgets.ConfigButton;
import net.augustus.ui.widgets.CustomButton;
import net.augustus.utils.PlayerUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class ConfigGui extends GuiScreen {
   private GuiTextField createTextField;
   private final GuiScreen parent;
   private ConfigButton selected;
   private ArrayList<ConfigButton> configButtons = new ArrayList<>();

   public ConfigGui(GuiScreen parent) {
      this.parent = parent;
   }

   @Override
   public void initGui() {
      super.initGui();
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.createTextField = new GuiTextField(1, this.fontRendererObj, sr.getScaledWidth() / 2 - 75, sr.getScaledHeight() - 130, 150, 20);
      this.createTextField.setMaxStringLength(1377);
      this.buttonList.add(new CustomButton(2, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 105, 100, 20, "Create", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(3, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 80, 100, 20, "Load", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(4, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 55, 100, 20, "Save", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(5, sr.getScaledWidth() / 2 - 50, sr.getScaledHeight() - 30, 100, 20, "Delete", Augustus.getInstance().getClientColor()));

      this.initConfigs();
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 2) {
         this.createConfig();
      } else if (button.id == 3) {
         this.loadConfig();
      } else if (button.id == 4) {
         this.saveConfigs();
      } else if (button.id == 5) {
         this.deleteConfigs();
      }

      super.actionPerformed(button);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      drawRect(sr.getScaledWidth() / 2 - 150, 0, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight(), new Color(0, 0, 0, 190).getRGB());
      boolean b = true;
      float mdw = (float)Mouse.getDWheel() / 10.0F;
      float y = 0.0F;
      if (this.configButtons.size() > 0) {
         if ((float)(this.configButtons.get(this.configButtons.size() - 1).yPosition + this.configButtons.get(this.configButtons.size() - 1).getHeight()) + mdw
               < (float)(sr.getScaledHeight() - 140)
            && mdw < 0.0F) {
            b = false;
            y = (float)(55 + (sr.getScaledHeight() - 140 - 55 - this.configButtons.size() * 36) + 2);
         } else if ((float)this.configButtons.get(0).yPosition + mdw > 55.0F && mdw > 0.0F) {
            b = false;
            y = 55.0F;
         }

         float yAdd = 0.0F;

         for(ConfigButton configButton : this.configButtons) {
            if (this.configButtons.size() > 1 && this.configButtons.size() * 36 > sr.getScaledHeight() - 140 - 55) {
               if (b) {
                  configButton.yPosition = (int)((float)configButton.yPosition + mdw);
               } else {
                  configButton.yPosition = (int)(y + yAdd);
               }
            }

            configButton.draw(this.mc, mouseX, mouseY);
            yAdd += 36.0F;
         }
      }

      drawRect(sr.getScaledWidth() / 2 - 150, 0, sr.getScaledWidth() / 2 + 150, 55, new Color(15, 15, 15, 255).getRGB());
      drawRect(
         sr.getScaledWidth() / 2 - 150, sr.getScaledHeight() - 140, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight(), new Color(15, 15, 15, 255).getRGB()
      );
      drawRect(sr.getScaledWidth() / 2 - 150, 55, sr.getScaledWidth() / 2 - 140, sr.getScaledHeight() - 140, new Color(15, 15, 15, 255).getRGB());
      drawRect(sr.getScaledWidth() / 2 + 140, 55, sr.getScaledWidth() / 2 + 150, sr.getScaledHeight() - 140, new Color(15, 15, 15, 255).getRGB());
      super.drawScreen(mouseX, mouseY, partialTicks);
      this.createTextField.drawTextBox();
      GlStateManager.pushMatrix();
      GlStateManager.scale(3.0F, 3.0F, 1.0F);
      this.fontRendererObj
         .drawStringWithShadow(
            "Configs", (float)sr.getScaledWidth() / 2.0F / 3.0F - (float)this.fontRendererObj.getStringWidth("Configs") / 2.0F, 5.0F, Color.gray.getRGB()
         );
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      this.createTextField.textboxKeyTyped(typedChar, keyCode);
      if (keyCode == 1) {
         this.mc.displayGuiScreen(this.parent);
      }
   }

   @Override
   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      this.createTextField.mouseClicked(mouseX, mouseY, mouseButton);
      super.mouseClicked(mouseX, mouseY, mouseButton);
      if (mouseButton == 1 || mouseButton == 0) {
         for(ConfigButton configButton : this.configButtons) {
            if (this.mouseOver(
               (double)mouseX,
               (double)mouseY,
               (double)configButton.xPosition,
               (double)configButton.yPosition,
               (double)configButton.getButtonWidth(),
               (double)configButton.getHeight()
            )) {
               this.selected = configButton;
               configButton.setSelected(true);
            } else {
               configButton.setSelected(false);
            }
         }
      }
   }

   @Override
   public void updateScreen() {
      this.createTextField.updateCursorCounter();
      super.updateScreen();
   }

   private void deleteConfigs() {
      if (this.selected != null) {
         File file = new File("gugustus/configs/" + this.selected.displayString + ".json");
         file.delete();
      }

      this.initConfigs();
   }

   private void createConfig() {
      if (!this.createTextField.getText().equals("")) {
         Augustus.getInstance().getConverter().configSaver(this.createTextField.getText());
         this.createTextField.setText("");
      }

      this.initConfigs();
   }

   private void saveConfigs() {
      if (this.selected != null) {
         Augustus.getInstance().getConverter().configSaver(this.selected.displayString);
      }
   }

   private void loadConfig() {
      if (this.selected != null) {
         Augustus.getInstance().getConverter().configLoader(this.selected.displayString);
      }
   }

   private void initConfigs() {
      ScaledResolution sr = new ScaledResolution(this.mc);
      this.configButtons = new ArrayList<>();
      try {
	      if (!Files.exists(Paths.get("gugustus/configs"))) {
	         try {
	            Files.createDirectories(Paths.get("gugustus/configs"));
	         } catch (IOException var11) {
	            var11.printStackTrace();
	         }
	      }
	
	      File f = new File("gugustus/configs");
	      File[] fileArray = f.listFiles();
	      int yAdd = 0;
	
	      assert fileArray != null;
	
	      for(File file : fileArray) {
	         if (file.getName().contains(".json")) {
	            String name = "";
	
	            for(int i = 0; i < file.getName().length() - 5; ++i) {
	               name = name + file.getName().charAt(i);
	            }
	
	            String[] s = Augustus.getInstance().getConverter().configReader(name);
	            this.configButtons.add(new ConfigButton(6, sr.getScaledWidth() / 2 - 140, 55 + yAdd, 280, 35, name, s[1], s[2], Color.gray));
	            yAdd += 36;
	         }
	      }
      }catch(Exception e) {
    	  e.printStackTrace();
      }
   }

   public boolean mouseOver(double mouseX, double mouseY, double posX, double posY, double width, double height) {
      return mouseX >= posX && mouseX <= posX + width && mouseY >= posY && mouseY <= posY + height;
   }
}
