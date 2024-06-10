/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.gui.GuiOptionButton;
/*  7:   */ import net.minecraft.client.gui.GuiOptionSlider;
/*  8:   */ import net.minecraft.client.gui.GuiScreen;
/*  9:   */ import net.minecraft.client.gui.ScaledResolution;
/* 10:   */ import net.minecraft.client.resources.I18n;
/* 11:   */ import net.minecraft.client.settings.GameSettings;
/* 12:   */ import net.minecraft.client.settings.GameSettings.Options;
/* 13:   */ 
/* 14:   */ public class GuiAnimationSettingsOF
/* 15:   */   extends GuiScreen
/* 16:   */ {
/* 17:   */   private GuiScreen prevScreen;
/* 18:14 */   protected String title = "Animation Settings";
/* 19:   */   private GameSettings settings;
/* 20:16 */   private static GameSettings.Options[] enumOptions = { GameSettings.Options.ANIMATED_WATER, GameSettings.Options.ANIMATED_LAVA, GameSettings.Options.ANIMATED_FIRE, GameSettings.Options.ANIMATED_PORTAL, GameSettings.Options.ANIMATED_REDSTONE, GameSettings.Options.ANIMATED_EXPLOSION, GameSettings.Options.ANIMATED_FLAME, GameSettings.Options.ANIMATED_SMOKE, GameSettings.Options.VOID_PARTICLES, GameSettings.Options.WATER_PARTICLES, GameSettings.Options.RAIN_SPLASH, GameSettings.Options.PORTAL_PARTICLES, GameSettings.Options.POTION_PARTICLES, GameSettings.Options.DRIPPING_WATER_LAVA, GameSettings.Options.ANIMATED_TERRAIN, GameSettings.Options.ANIMATED_ITEMS, GameSettings.Options.ANIMATED_TEXTURES, GameSettings.Options.PARTICLES };
/* 21:   */   
/* 22:   */   public GuiAnimationSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
/* 23:   */   {
/* 24:20 */     this.prevScreen = guiscreen;
/* 25:21 */     this.settings = gamesettings;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void initGui()
/* 29:   */   {
/* 30:29 */     int i = 0;
/* 31:30 */     GameSettings.Options[] aenumoptions = enumOptions;
/* 32:31 */     int j = aenumoptions.length;
/* 33:33 */     for (int k = 0; k < j; k++)
/* 34:   */     {
/* 35:35 */       GameSettings.Options enumoptions = aenumoptions[k];
/* 36:36 */       int x = width / 2 - 155 + i % 2 * 160;
/* 37:37 */       int y = height / 6 + 21 * (i / 2) - 10;
/* 38:39 */       if (!enumoptions.getEnumFloat()) {
/* 39:41 */         this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
/* 40:   */       } else {
/* 41:45 */         this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
/* 42:   */       }
/* 43:48 */       i++;
/* 44:   */     }
/* 45:51 */     this.buttonList.add(new NodusGuiButton(210, width / 2 - 155, height / 6 + 168 + 11, 70, 20, "All ON"));
/* 46:52 */     this.buttonList.add(new NodusGuiButton(211, width / 2 - 155 + 80, height / 6 + 168 + 11, 70, 20, "All OFF"));
/* 47:53 */     this.buttonList.add(new GuiOptionButton(200, width / 2 + 5, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected void actionPerformed(NodusGuiButton guibutton)
/* 51:   */   {
/* 52:58 */     if (guibutton.enabled)
/* 53:   */     {
/* 54:60 */       if ((guibutton.id < 100) && ((guibutton instanceof GuiOptionButton)))
/* 55:   */       {
/* 56:62 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).func_146136_c(), 1);
/* 57:63 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/* 58:   */       }
/* 59:66 */       if (guibutton.id == 200)
/* 60:   */       {
/* 61:68 */         this.mc.gameSettings.saveOptions();
/* 62:69 */         this.mc.displayGuiScreen(this.prevScreen);
/* 63:   */       }
/* 64:72 */       if (guibutton.id == 210) {
/* 65:74 */         this.mc.gameSettings.setAllAnimations(true);
/* 66:   */       }
/* 67:77 */       if (guibutton.id == 211) {
/* 68:79 */         this.mc.gameSettings.setAllAnimations(false);
/* 69:   */       }
/* 70:82 */       if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
/* 71:   */       {
/* 72:84 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/* 73:85 */         int i = scaledresolution.getScaledWidth();
/* 74:86 */         int j = scaledresolution.getScaledHeight();
/* 75:87 */         setWorldAndResolution(this.mc, i, j);
/* 76:   */       }
/* 77:   */     }
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void drawScreen(int i, int j, float f)
/* 81:   */   {
/* 82:97 */     drawDefaultBackground();
/* 83:98 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
/* 84:99 */     super.drawScreen(i, j, f);
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.GuiAnimationSettingsOF
 * JD-Core Version:    0.7.0.1
 */