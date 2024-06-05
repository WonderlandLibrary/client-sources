package net.minecraft.client.gui;

import java.io.IOException;
import java.util.Random;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public class GuiCreateWorld extends GuiScreen {
   private GuiButton field_146325_B;
   private GuiButton field_146321_E;
   private static final String[] field_146327_L = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
   private String field_175300_s;
   private GuiScreen field_146332_f;
   private String field_146330_J;
   private boolean field_146339_u;
   private boolean field_146337_w;
   private String field_146342_r = "survival";
   private String field_146323_G;
   private boolean field_146340_t;
   private GuiTextField field_146333_g;
   private GuiButton field_146320_D;
   private boolean field_146344_y;
   private boolean field_146345_x;
   private GuiTextField field_146335_h;
   private int field_146331_K;
   public String field_146334_a = "";
   private String field_146329_I;
   private boolean field_146341_s = true;
   private GuiButton field_146343_z;
   private boolean field_146338_v;
   private String field_146336_i;
   private GuiButton field_146322_F;
   private static final String __OBFID = "CL_00000689";
   private GuiButton field_146326_C;
   private GuiButton field_146324_A;
   private String field_146328_H;

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      super.mouseClicked(var1, var2, var3);
      if (this.field_146344_y) {
         this.field_146335_h.mouseClicked(var1, var2, var3);
      } else {
         this.field_146333_g.mouseClicked(var1, var2, var3);
      }

   }

   private void func_146315_i() {
      this.func_146316_a(!this.field_146344_y);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create")));
      this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel")));
      this.buttonList.add(this.field_146343_z = new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode")));
      this.buttonList.add(this.field_146324_A = new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions")));
      this.buttonList.add(this.field_146325_B = new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures")));
      this.field_146325_B.visible = false;
      this.buttonList.add(this.field_146326_C = new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems")));
      this.field_146326_C.visible = false;
      this.buttonList.add(this.field_146320_D = new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType")));
      this.field_146320_D.visible = false;
      this.buttonList.add(this.field_146321_E = new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands")));
      this.field_146321_E.visible = false;
      this.buttonList.add(this.field_146322_F = new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType")));
      this.field_146322_F.visible = false;
      this.field_146333_g = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.field_146333_g.setFocused(true);
      this.field_146333_g.setText(this.field_146330_J);
      this.field_146335_h = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.field_146335_h.setText(this.field_146329_I);
      this.func_146316_a(this.field_146344_y);
      this.func_146314_g();
      this.func_146319_h();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 1) {
            this.mc.displayGuiScreen(this.field_146332_f);
         } else if (var1.id == 0) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.field_146345_x) {
               return;
            }

            this.field_146345_x = true;
            long var2 = (new Random()).nextLong();
            String var4 = this.field_146335_h.getText();
            if (!StringUtils.isEmpty(var4)) {
               try {
                  long var5 = Long.parseLong(var4);
                  if (var5 != 0L) {
                     var2 = var5;
                  }
               } catch (NumberFormatException var7) {
                  var2 = (long)var4.hashCode();
               }
            }

            WorldSettings.GameType var8 = WorldSettings.GameType.getByName(this.field_146342_r);
            WorldSettings var6 = new WorldSettings(var2, var8, this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.field_146331_K]);
            var6.setWorldName(this.field_146334_a);
            if (this.field_146338_v && !this.field_146337_w) {
               var6.enableBonusChest();
            }

            if (this.field_146340_t && !this.field_146337_w) {
               var6.enableCommands();
            }

            this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), var6);
         } else if (var1.id == 3) {
            this.func_146315_i();
         } else if (var1.id == 2) {
            if (this.field_146342_r.equals("survival")) {
               if (!this.field_146339_u) {
                  this.field_146340_t = false;
               }

               this.field_146337_w = false;
               this.field_146342_r = "hardcore";
               this.field_146337_w = true;
               this.field_146321_E.enabled = false;
               this.field_146326_C.enabled = false;
               this.func_146319_h();
            } else if (this.field_146342_r.equals("hardcore")) {
               if (!this.field_146339_u) {
                  this.field_146340_t = true;
               }

               this.field_146337_w = false;
               this.field_146342_r = "creative";
               this.func_146319_h();
               this.field_146337_w = false;
               this.field_146321_E.enabled = true;
               this.field_146326_C.enabled = true;
            } else {
               if (!this.field_146339_u) {
                  this.field_146340_t = false;
               }

               this.field_146342_r = "survival";
               this.func_146319_h();
               this.field_146321_E.enabled = true;
               this.field_146326_C.enabled = true;
               this.field_146337_w = false;
            }

            this.func_146319_h();
         } else if (var1.id == 4) {
            this.field_146341_s = !this.field_146341_s;
            this.func_146319_h();
         } else if (var1.id == 7) {
            this.field_146338_v = !this.field_146338_v;
            this.func_146319_h();
         } else if (var1.id == 5) {
            ++this.field_146331_K;
            if (this.field_146331_K >= WorldType.worldTypes.length) {
               this.field_146331_K = 0;
            }

            while(!this.func_175299_g()) {
               ++this.field_146331_K;
               if (this.field_146331_K >= WorldType.worldTypes.length) {
                  this.field_146331_K = 0;
               }
            }

            this.field_146334_a = "";
            this.func_146319_h();
            this.func_146316_a(this.field_146344_y);
         } else if (var1.id == 6) {
            this.field_146339_u = true;
            this.field_146340_t = !this.field_146340_t;
            this.func_146319_h();
         } else if (var1.id == 8) {
            if (WorldType.worldTypes[this.field_146331_K] == WorldType.FLAT) {
               this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.field_146334_a));
            } else {
               this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.field_146334_a));
            }
         }
      }

   }

   private void func_146316_a(boolean var1) {
      this.field_146344_y = var1;
      if (WorldType.worldTypes[this.field_146331_K] == WorldType.DEBUG_WORLD) {
         this.field_146343_z.visible = !this.field_146344_y;
         this.field_146343_z.enabled = false;
         if (this.field_175300_s == null) {
            this.field_175300_s = this.field_146342_r;
         }

         this.field_146342_r = "spectator";
         this.field_146325_B.visible = false;
         this.field_146326_C.visible = false;
         this.field_146320_D.visible = this.field_146344_y;
         this.field_146321_E.visible = false;
         this.field_146322_F.visible = false;
      } else {
         this.field_146343_z.visible = !this.field_146344_y;
         this.field_146343_z.enabled = true;
         if (this.field_175300_s != null) {
            this.field_146342_r = this.field_175300_s;
            this.field_175300_s = null;
         }

         this.field_146325_B.visible = this.field_146344_y && WorldType.worldTypes[this.field_146331_K] != WorldType.CUSTOMIZED;
         this.field_146326_C.visible = this.field_146344_y;
         this.field_146320_D.visible = this.field_146344_y;
         this.field_146321_E.visible = this.field_146344_y;
         this.field_146322_F.visible = this.field_146344_y && (WorldType.worldTypes[this.field_146331_K] == WorldType.FLAT || WorldType.worldTypes[this.field_146331_K] == WorldType.CUSTOMIZED);
      }

      this.func_146319_h();
      if (this.field_146344_y) {
         this.field_146324_A.displayString = I18n.format("gui.done");
      } else {
         this.field_146324_A.displayString = I18n.format("selectWorld.moreWorldOptions");
      }

   }

   private void func_146319_h() {
      this.field_146343_z.displayString = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.gameMode")))).append(": ").append(I18n.format(String.valueOf((new StringBuilder("selectWorld.gameMode.")).append(this.field_146342_r)))));
      this.field_146323_G = I18n.format(String.valueOf((new StringBuilder("selectWorld.gameMode.")).append(this.field_146342_r).append(".line1")));
      this.field_146328_H = I18n.format(String.valueOf((new StringBuilder("selectWorld.gameMode.")).append(this.field_146342_r).append(".line2")));
      this.field_146325_B.displayString = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.mapFeatures")))).append(" "));
      if (this.field_146341_s) {
         this.field_146325_B.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146325_B.displayString))).append(I18n.format("options.on")));
      } else {
         this.field_146325_B.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146325_B.displayString))).append(I18n.format("options.off")));
      }

      this.field_146326_C.displayString = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.bonusItems")))).append(" "));
      if (this.field_146338_v && !this.field_146337_w) {
         this.field_146326_C.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146326_C.displayString))).append(I18n.format("options.on")));
      } else {
         this.field_146326_C.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146326_C.displayString))).append(I18n.format("options.off")));
      }

      this.field_146320_D.displayString = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.mapType")))).append(" ").append(I18n.format(WorldType.worldTypes[this.field_146331_K].getTranslateName())));
      this.field_146321_E.displayString = String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.allowCommands")))).append(" "));
      if (this.field_146340_t && !this.field_146337_w) {
         this.field_146321_E.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146321_E.displayString))).append(I18n.format("options.on")));
      } else {
         this.field_146321_E.displayString = String.valueOf((new StringBuilder(String.valueOf(this.field_146321_E.displayString))).append(I18n.format("options.off")));
      }

   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   private boolean func_175299_g() {
      WorldType var1 = WorldType.worldTypes[this.field_146331_K];
      return var1 != null && var1.getCanBeCreated() ? (var1 == WorldType.DEBUG_WORLD ? isShiftKeyDown() : true) : false;
   }

   public static String func_146317_a(ISaveFormat var0, String var1) {
      var1 = var1.replaceAll("[\\./\"]", "_");
      String[] var2 = field_146327_L;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         if (var1.equalsIgnoreCase(var5)) {
            var1 = String.valueOf((new StringBuilder("_")).append(var1).append("_"));
         }
      }

      while(var0.getWorldInfo(var1) != null) {
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append("-"));
      }

      return var1;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.field_146333_g.isFocused() && !this.field_146344_y) {
         this.field_146333_g.textboxKeyTyped(var1, var2);
         this.field_146330_J = this.field_146333_g.getText();
      } else if (this.field_146335_h.isFocused() && this.field_146344_y) {
         this.field_146335_h.textboxKeyTyped(var1, var2);
         this.field_146329_I = this.field_146335_h.getText();
      }

      if (var2 == 28 || var2 == 156) {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      ((GuiButton)this.buttonList.get(0)).enabled = this.field_146333_g.getText().length() > 0;
      this.func_146314_g();
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create"), this.width / 2, 20, -1);
      if (this.field_146344_y) {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo"), this.width / 2 - 100, 85, -6250336);
         if (this.field_146325_B.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info"), this.width / 2 - 150, 122, -6250336);
         }

         if (this.field_146321_E.visible) {
            this.drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info"), this.width / 2 - 150, 172, -6250336);
         }

         this.field_146335_h.drawTextBox();
         if (WorldType.worldTypes[this.field_146331_K].showWorldInfoNotice()) {
            this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.field_146331_K].func_151359_c()), this.field_146320_D.xPosition + 2, this.field_146320_D.yPosition + 22, this.field_146320_D.getButtonWidth(), 10526880);
         }
      } else {
         this.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.fontRendererObj, String.valueOf((new StringBuilder(String.valueOf(I18n.format("selectWorld.resultFolder")))).append(" ").append(this.field_146336_i)), this.width / 2 - 100, 85, -6250336);
         this.field_146333_g.drawTextBox();
         this.drawString(this.fontRendererObj, this.field_146323_G, this.width / 2 - 100, 137, -6250336);
         this.drawString(this.fontRendererObj, this.field_146328_H, this.width / 2 - 100, 149, -6250336);
      }

      super.drawScreen(var1, var2, var3);
   }

   private void func_146314_g() {
      this.field_146336_i = this.field_146333_g.getText().trim();
      char[] var1 = ChatAllowedCharacters.allowedCharactersArray;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1[var3];
         this.field_146336_i = this.field_146336_i.replace(var4, '_');
      }

      if (StringUtils.isEmpty(this.field_146336_i)) {
         this.field_146336_i = "World";
      }

      this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
   }

   public GuiCreateWorld(GuiScreen var1) {
      this.field_146332_f = var1;
      this.field_146329_I = "";
      this.field_146330_J = I18n.format("selectWorld.newWorld");
   }

   public void func_146318_a(WorldInfo var1) {
      this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", var1.getWorldName());
      this.field_146329_I = String.valueOf(new StringBuilder(String.valueOf(var1.getSeed())));
      this.field_146331_K = var1.getTerrainType().getWorldTypeID();
      this.field_146334_a = var1.getGeneratorOptions();
      this.field_146341_s = var1.isMapFeaturesEnabled();
      this.field_146340_t = var1.areCommandsAllowed();
      if (var1.isHardcoreModeEnabled()) {
         this.field_146342_r = "hardcore";
      } else if (var1.getGameType().isSurvivalOrAdventure()) {
         this.field_146342_r = "survival";
      } else if (var1.getGameType().isCreative()) {
         this.field_146342_r = "creative";
      }

   }

   public void updateScreen() {
      this.field_146333_g.updateCursorCounter();
      this.field_146335_h.updateCursorCounter();
   }
}
