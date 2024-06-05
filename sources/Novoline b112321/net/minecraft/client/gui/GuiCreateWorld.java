package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Random;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiCustomizeWorldScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
   private GuiScreen parentScreen;
   private GuiTextField field_146333_g;
   private GuiTextField field_146335_h;
   private String field_146336_i;
   private String gameMode = "survival";
   private String field_175300_s;
   private boolean field_146341_s = true;
   private boolean allowCheats;
   private boolean field_146339_u;
   private boolean field_146338_v;
   private boolean field_146337_w;
   private boolean field_146345_x;
   private boolean field_146344_y;
   private GuiButton btnGameMode;
   private GuiButton btnMoreOptions;
   private GuiButton btnMapFeatures;
   private GuiButton btnBonusItems;
   private GuiButton btnMapType;
   private GuiButton btnAllowCommands;
   private GuiButton btnCustomizeType;
   private String field_146323_G;
   private String field_146328_H;
   private String field_146329_I;
   private String field_146330_J;
   private int selectedIndex;
   public String chunkProviderSettingsJson = "";
   private static final String[] disallowedFilenames = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

   public GuiCreateWorld(GuiScreen var1) {
      this.parentScreen = var1;
      this.field_146329_I = "";
      this.field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
   }

   public void updateScreen() {
      this.field_146333_g.updateCursorCounter();
      this.field_146335_h.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
      this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
      this.buttonList.add(this.btnGameMode = new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
      this.buttonList.add(this.btnMoreOptions = new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
      this.buttonList.add(this.btnMapFeatures = new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
      this.btnMapFeatures.visible = false;
      this.buttonList.add(this.btnBonusItems = new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
      this.btnBonusItems.visible = false;
      this.buttonList.add(this.btnMapType = new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
      this.btnMapType.visible = false;
      this.buttonList.add(this.btnAllowCommands = new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
      this.btnAllowCommands.visible = false;
      this.buttonList.add(this.btnCustomizeType = new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
      this.btnCustomizeType.visible = false;
      this.field_146333_g = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.field_146333_g.setFocused(true);
      this.field_146333_g.setText(this.field_146330_J);
      this.field_146335_h = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.field_146335_h.setText(this.field_146329_I);
      this.func_146316_a(this.field_146344_y);
      this.func_146314_g();
      this.func_146319_h();
   }

   private void func_146314_g() {
      this.field_146336_i = this.field_146333_g.getText().trim();

      for(char var4 : ChatAllowedCharacters.allowedCharactersArray) {
         this.field_146336_i = this.field_146336_i.replace(var4, '_');
      }

      if(StringUtils.isEmpty(this.field_146336_i)) {
         this.field_146336_i = "World";
      }

      this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
   }

   private void func_146319_h() {
      this.btnGameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
      this.field_146323_G = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
      this.field_146328_H = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
      this.btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures", new Object[0]) + " ";
      if(this.field_146341_s) {
         this.btnMapFeatures.displayString = this.btnMapFeatures.displayString + I18n.format("options.on", new Object[0]);
      } else {
         this.btnMapFeatures.displayString = this.btnMapFeatures.displayString + I18n.format("options.off", new Object[0]);
      }

      this.btnBonusItems.displayString = I18n.format("selectWorld.bonusItems", new Object[0]) + " ";
      if(this.field_146338_v && !this.field_146337_w) {
         this.btnBonusItems.displayString = this.btnBonusItems.displayString + I18n.format("options.on", new Object[0]);
      } else {
         this.btnBonusItems.displayString = this.btnBonusItems.displayString + I18n.format("options.off", new Object[0]);
      }

      this.btnMapType.displayString = I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
      this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
      if(this.allowCheats && !this.field_146337_w) {
         this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.on", new Object[0]);
      } else {
         this.btnAllowCommands.displayString = this.btnAllowCommands.displayString + I18n.format("options.off", new Object[0]);
      }

   }

   public static String func_146317_a(ISaveFormat var0, String var1) {
      var1 = var1.replaceAll("[\\./\"]", "_");

      for(String var5 : disallowedFilenames) {
         if(var1.equalsIgnoreCase(var5)) {
            var1 = "_" + var1 + "_";
         }
      }

      while(var0.getWorldInfo(var1) != null) {
         var1 = var1 + "-";
      }

      return var1;
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(var1.id == 0) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if(this.field_146345_x) {
               return;
            }

            this.field_146345_x = true;
            long var2 = (new Random()).nextLong();
            String var4 = this.field_146335_h.getText();
            if(!StringUtils.isEmpty(var4)) {
               String var10000 = var4;

               try {
                  long var5 = Long.parseLong(var10000);
                  if(var5 != 0L) {
                     var2 = var5;
                  }
               } catch (NumberFormatException var7) {
                  var2 = (long)var4.hashCode();
               }
            }

            WorldSettings$GameType var8 = WorldSettings$GameType.getByName(this.gameMode);
            WorldSettings var6 = new WorldSettings(var2, var8, this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.selectedIndex]);
            var6.setWorldName(this.chunkProviderSettingsJson);
            if(this.field_146338_v && !this.field_146337_w) {
               var6.enableBonusChest();
            }

            if(this.allowCheats && !this.field_146337_w) {
               var6.enableCommands();
            }

            this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), var6);
         } else if(var1.id == 3) {
            this.func_146315_i();
         } else if(var1.id == 2) {
            if(this.gameMode.equals("survival")) {
               if(!this.field_146339_u) {
                  this.allowCheats = false;
               }

               this.field_146337_w = false;
               this.gameMode = "hardcore";
               this.field_146337_w = true;
               this.btnAllowCommands.enabled = false;
               this.btnBonusItems.enabled = false;
               this.func_146319_h();
            } else if(this.gameMode.equals("hardcore")) {
               if(!this.field_146339_u) {
                  this.allowCheats = true;
               }

               this.field_146337_w = false;
               this.gameMode = "creative";
               this.func_146319_h();
               this.field_146337_w = false;
               this.btnAllowCommands.enabled = true;
               this.btnBonusItems.enabled = true;
            } else {
               if(!this.field_146339_u) {
                  this.allowCheats = false;
               }

               this.gameMode = "survival";
               this.func_146319_h();
               this.btnAllowCommands.enabled = true;
               this.btnBonusItems.enabled = true;
               this.field_146337_w = false;
            }

            this.func_146319_h();
         } else if(var1.id == 4) {
            this.field_146341_s = !this.field_146341_s;
            this.func_146319_h();
         } else if(var1.id == 7) {
            this.field_146338_v = !this.field_146338_v;
            this.func_146319_h();
         } else if(var1.id == 5) {
            ++this.selectedIndex;
            if(this.selectedIndex >= WorldType.worldTypes.length) {
               this.selectedIndex = 0;
            }

            while(!this.func_175299_g()) {
               ++this.selectedIndex;
               if(this.selectedIndex >= WorldType.worldTypes.length) {
                  this.selectedIndex = 0;
               }
            }

            this.chunkProviderSettingsJson = "";
            this.func_146319_h();
            this.func_146316_a(this.field_146344_y);
         } else if(var1.id == 6) {
            this.field_146339_u = true;
            this.allowCheats = !this.allowCheats;
            this.func_146319_h();
         } else if(var1.id == 8) {
            if(WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
               this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
            } else {
               this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
            }
         }
      }

   }

   private boolean func_175299_g() {
      WorldType var1 = WorldType.worldTypes[this.selectedIndex];
      return var1.getCanBeCreated() && (var1 != WorldType.DEBUG_WORLD || isShiftKeyDown());
   }

   private void func_146315_i() {
      this.func_146316_a(!this.field_146344_y);
   }

   private void func_146316_a(boolean var1) {
      this.field_146344_y = var1;
      if(WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
         this.btnGameMode.visible = !this.field_146344_y;
         this.btnGameMode.enabled = false;
         if(this.field_175300_s == null) {
            this.field_175300_s = this.gameMode;
         }

         this.gameMode = "spectator";
         this.btnMapFeatures.visible = false;
         this.btnBonusItems.visible = false;
         this.btnMapType.visible = this.field_146344_y;
         this.btnAllowCommands.visible = false;
         this.btnCustomizeType.visible = false;
      } else {
         this.btnGameMode.visible = !this.field_146344_y;
         this.btnGameMode.enabled = true;
         if(this.field_175300_s != null) {
            this.gameMode = this.field_175300_s;
            this.field_175300_s = null;
         }

         this.btnMapFeatures.visible = this.field_146344_y && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED;
         this.btnBonusItems.visible = this.field_146344_y;
         this.btnMapType.visible = this.field_146344_y;
         this.btnAllowCommands.visible = this.field_146344_y;
         this.btnCustomizeType.visible = this.field_146344_y && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED);
      }

      this.func_146319_h();
      if(this.field_146344_y) {
         this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
      } else {
         this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
      }

   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if(this.field_146333_g.isFocused() && !this.field_146344_y) {
         this.field_146333_g.textboxKeyTyped(var1, var2);
         this.field_146330_J = this.field_146333_g.getText();
      } else if(this.field_146335_h.isFocused() && this.field_146344_y) {
         this.field_146335_h.textboxKeyTyped(var1, var2);
         this.field_146329_I = this.field_146335_h.getText();
      }

      if(var2 == 28 || var2 == 156) {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      ((GuiButton)this.buttonList.get(0)).enabled = !this.field_146333_g.getText().isEmpty();
      this.func_146314_g();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      if(this.field_146344_y) {
         this.field_146335_h.mouseClicked(var1, var2, var3);
      } else {
         this.field_146333_g.mouseClicked(var1, var2, var3);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), this.width / 2, 20, -1);
      if(this.field_146344_y) {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), this.width / 2 - 100, 85, -6250336);
         if(this.btnMapFeatures.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), this.width / 2 - 150, 122, -6250336);
         }

         if(this.btnAllowCommands.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), this.width / 2 - 150, 172, -6250336);
         }

         this.field_146335_h.drawTextBox();
         if(WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice()) {
            this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].func_151359_c(), new Object[0]), (int)this.btnMapType.xPosition + 2, (int)this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
         }
      } else {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + this.field_146336_i, this.width / 2 - 100, 85, -6250336);
         this.field_146333_g.drawTextBox();
         this.drawString(this.fontRendererObj, this.field_146323_G, this.width / 2 - 100, 137, -6250336);
         this.drawString(this.fontRendererObj, this.field_146328_H, this.width / 2 - 100, 149, -6250336);
      }

      super.drawScreen(var1, var2, var3);
   }

   public void func_146318_a(WorldInfo var1) {
      this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", new Object[]{var1.getWorldName()});
      this.field_146329_I = var1.getSeed() + "";
      this.selectedIndex = var1.getTerrainType().getWorldTypeID();
      this.chunkProviderSettingsJson = var1.getGeneratorOptions();
      this.field_146341_s = var1.isMapFeaturesEnabled();
      this.allowCheats = var1.areCommandsAllowed();
      if(var1.isHardcoreModeEnabled()) {
         this.gameMode = "hardcore";
      } else if(var1.getGameType().isSurvivalOrAdventure()) {
         this.gameMode = "survival";
      } else if(var1.getGameType().isCreative()) {
         this.gameMode = "creative";
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
