/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.gui.GuiOptionButton;
/*   8:    */ import net.minecraft.client.gui.GuiOptionSlider;
/*   9:    */ import net.minecraft.client.gui.GuiScreen;
/*  10:    */ import net.minecraft.client.gui.GuiVideoSettings;
/*  11:    */ import net.minecraft.client.gui.ScaledResolution;
/*  12:    */ import net.minecraft.client.resources.I18n;
/*  13:    */ import net.minecraft.client.settings.GameSettings;
/*  14:    */ import net.minecraft.client.settings.GameSettings.Options;
/*  15:    */ 
/*  16:    */ public class GuiQualitySettingsOF
/*  17:    */   extends GuiScreen
/*  18:    */ {
/*  19:    */   private GuiScreen prevScreen;
/*  20: 15 */   protected String title = "Quality Settings";
/*  21:    */   private GameSettings settings;
/*  22: 17 */   private static GameSettings.Options[] enumOptions = { GameSettings.Options.MIPMAP_LEVELS, GameSettings.Options.MIPMAP_TYPE, GameSettings.Options.ANISOTROPIC_FILTERING, GameSettings.Options.AA_LEVEL, GameSettings.Options.CLEAR_WATER, GameSettings.Options.RANDOM_MOBS, GameSettings.Options.BETTER_GRASS, GameSettings.Options.BETTER_SNOW, GameSettings.Options.CUSTOM_FONTS, GameSettings.Options.CUSTOM_COLORS, GameSettings.Options.SWAMP_COLORS, GameSettings.Options.SMOOTH_BIOMES, GameSettings.Options.CONNECTED_TEXTURES, GameSettings.Options.NATURAL_TEXTURES, GameSettings.Options.CUSTOM_SKY };
/*  23: 18 */   private int lastMouseX = 0;
/*  24: 19 */   private int lastMouseY = 0;
/*  25: 20 */   private long mouseStillTime = 0L;
/*  26:    */   
/*  27:    */   public GuiQualitySettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
/*  28:    */   {
/*  29: 24 */     this.prevScreen = guiscreen;
/*  30: 25 */     this.settings = gamesettings;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void initGui()
/*  34:    */   {
/*  35: 33 */     int i = 0;
/*  36: 34 */     GameSettings.Options[] aenumoptions = enumOptions;
/*  37: 35 */     int j = aenumoptions.length;
/*  38: 37 */     for (int k = 0; k < j; k++)
/*  39:    */     {
/*  40: 39 */       GameSettings.Options enumoptions = aenumoptions[k];
/*  41: 40 */       int x = width / 2 - 155 + i % 2 * 160;
/*  42: 41 */       int y = height / 6 + 21 * (i / 2) - 10;
/*  43: 43 */       if (!enumoptions.getEnumFloat()) {
/*  44: 45 */         this.buttonList.add(new GuiOptionButton(enumoptions.returnEnumOrdinal(), x, y, enumoptions, this.settings.getKeyBinding(enumoptions)));
/*  45:    */       } else {
/*  46: 49 */         this.buttonList.add(new GuiOptionSlider(enumoptions.returnEnumOrdinal(), x, y, enumoptions));
/*  47:    */       }
/*  48: 52 */       i++;
/*  49:    */     }
/*  50: 55 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void actionPerformed(NodusGuiButton guibutton)
/*  54:    */   {
/*  55: 60 */     if (guibutton.enabled)
/*  56:    */     {
/*  57: 62 */       if ((guibutton.id < 100) && ((guibutton instanceof GuiOptionButton)))
/*  58:    */       {
/*  59: 64 */         this.settings.setOptionValue(((GuiOptionButton)guibutton).func_146136_c(), 1);
/*  60: 65 */         guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
/*  61:    */       }
/*  62: 68 */       if (guibutton.id == 200)
/*  63:    */       {
/*  64: 70 */         this.mc.gameSettings.saveOptions();
/*  65: 71 */         this.mc.displayGuiScreen(this.prevScreen);
/*  66:    */       }
/*  67: 74 */       if (guibutton.id != GameSettings.Options.CLOUD_HEIGHT.ordinal())
/*  68:    */       {
/*  69: 76 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  70: 77 */         int i = scaledresolution.getScaledWidth();
/*  71: 78 */         int j = scaledresolution.getScaledHeight();
/*  72: 79 */         setWorldAndResolution(this.mc, i, j);
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void drawScreen(int x, int y, float f)
/*  78:    */   {
/*  79: 89 */     drawDefaultBackground();
/*  80: 90 */     drawCenteredString(this.fontRendererObj, this.title, width / 2, 20, 16777215);
/*  81: 91 */     super.drawScreen(x, y, f);
/*  82: 93 */     if ((Math.abs(x - this.lastMouseX) <= 5) && (Math.abs(y - this.lastMouseY) <= 5))
/*  83:    */     {
/*  84: 95 */       short activateDelay = 700;
/*  85: 97 */       if (System.currentTimeMillis() >= this.mouseStillTime + activateDelay)
/*  86:    */       {
/*  87: 99 */         int x1 = width / 2 - 150;
/*  88:100 */         int y1 = height / 6 - 5;
/*  89:102 */         if (y <= y1 + 98) {
/*  90:104 */           y1 += 105;
/*  91:    */         }
/*  92:107 */         int x2 = x1 + 150 + 150;
/*  93:108 */         int y2 = y1 + 84 + 10;
/*  94:109 */         NodusGuiButton btn = getSelectedButton(x, y);
/*  95:111 */         if (btn != null)
/*  96:    */         {
/*  97:113 */           String s = getButtonName(btn.displayString);
/*  98:114 */           String[] lines = getTooltipLines(s);
/*  99:116 */           if (lines == null) {
/* 100:118 */             return;
/* 101:    */           }
/* 102:121 */           drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
/* 103:123 */           for (int i = 0; i < lines.length; i++)
/* 104:    */           {
/* 105:125 */             String line = lines[i];
/* 106:126 */             int col = 14540253;
/* 107:128 */             if (line.endsWith("!")) {
/* 108:130 */               col = 16719904;
/* 109:    */             }
/* 110:133 */             this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, col);
/* 111:    */           }
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:    */     else
/* 116:    */     {
/* 117:140 */       this.lastMouseX = x;
/* 118:141 */       this.lastMouseY = y;
/* 119:142 */       this.mouseStillTime = System.currentTimeMillis();
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private String[] getTooltipLines(String btnName)
/* 124:    */   {
/* 125:148 */     return btnName.equals("Custom Sky") ? new String[] { "Custom Sky", "  ON - custom sky textures (default), slow", "  OFF - default sky, faster", "The custom sky textures are supplied by the current", "texture pack" } : btnName.equals("Natural Textures") ? new String[] { "Natural Textures", "  OFF - no natural textures (default)", "  ON - use natural textures", "Natural textures remove the gridlike pattern", "created by repeating blocks of the same type.", "It uses rotated and flipped variants of the base", "block texture. The configuration for the natural", "textures is supplied by the current texture pack" } : btnName.equals("Far View") ? new String[] { "Far View", " OFF - (default) standard view distance", " ON - 3x view distance", "Far View is very resource demanding!", "3x view distance => 9x chunks to be loaded => FPS / 9", "Standard view distances: 32, 64, 128, 256", "Far view distances: 96, 192, 384, 512" } : btnName.equals("Connected Textures") ? new String[] { "Connected Textures", "  OFF - no connected textures (default)", "  Fast - fast connected textures", "  Fancy - fancy connected textures", "Connected textures joins the textures of glass,", "sandstone and bookshelves when placed next to", "each other. The connected textures are supplied", "by the current texture pack." } : btnName.equals("Show Capes") ? new String[] { "Show Capes", "  ON - show player capes (default)", "  OFF - do not show player capes" } : btnName.equals("Custom Colors") ? new String[] { "Custom Colors", "  ON - uses custom colors (default), slower", "  OFF - uses default colors, faster", "The custom colors are supplied by the current", "texture pack" } : btnName.equals("Custom Fonts") ? new String[] { "Custom Fonts", "  ON - uses custom fonts (default), slower", "  OFF - uses default font, faster", "The custom fonts are supplied by the current", "texture pack" } : btnName.equals("Smooth Biomes") ? new String[] { "Smooth Biomes", "  ON - smoothing of biome borders (default), slower", "  OFF - no smoothing of biome borders, faster", "The smoothing of biome borders is done by sampling and", "averaging the color of all surrounding blocks.", "Affected are grass, leaves, vines and water." } : btnName.equals("Swamp Colors") ? new String[] { "Swamp Colors", "  ON - use swamp colors (default), slower", "  OFF - do not use swamp colors, faster", "The swamp colors affect grass, leaves, vines and water." } : btnName.equals("Random Mobs") ? new String[] { "Random Mobs", "  OFF - no random mobs, faster", "  ON - random mobs, slower", "Random mobs uses random textures for the game creatures.", "It needs a texture pack which has multiple mob textures." } : btnName.equals("Better Snow") ? new String[] { "Better Snow", "  OFF - default snow, faster", "  ON - better snow, slower", "Shows snow under transparent blocks (fence, tall grass)", "when bordering with snow blocks" } : btnName.equals("Better Grass") ? new String[] { "Better Grass", "  OFF - default side grass texture, fastest", "  Fast - full side grass texture, slower", "  Fancy - dynamic side grass texture, slowest" } : btnName.equals("Clear Water") ? new String[] { "Clear Water", "  ON - clear, transparent water", "  OFF - default water" } : btnName.equals("Antialiasing") ? new String[] { "Antialiasing", " OFF - (default) no antialiasing (faster)", " 2-16 - antialiased lines and edges (slower)", "The Antialiasing smooths jagged lines and ", "sharp color transitions.", "Higher values may substantially decrease the FPS.", "Not all levels are supported by all graphics cards.", "Effective after a RESTART!" } : btnName.equals("Anisotropic Filtering") ? new String[] { "Anisotropic Filtering", " OFF - (default) standard texture detail (faster)", " 2-16 - finer details in mipmapped textures (slower)", "The Anisotropic Filtering restores details in mipmapped", "textures. Higher values may decrease the FPS." } : btnName.equals("Mipmap Type") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  Nearest - rough smoothing", "  Linear - fine smoothing", "This option usually does not affect the performance." } : btnName.equals("Mipmap Levels") ? new String[] { "Visual effect which makes distant objects look better", "by smoothing the texture details", "  OFF - no smoothing", "  1 - minimum smoothing", "  4 - maximum smoothing", "This option usually does not affect the performance." } : null;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private String getButtonName(String displayString)
/* 129:    */   {
/* 130:153 */     int pos = displayString.indexOf(':');
/* 131:154 */     return pos < 0 ? displayString : displayString.substring(0, pos);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private NodusGuiButton getSelectedButton(int i, int j)
/* 135:    */   {
/* 136:159 */     for (int k = 0; k < this.buttonList.size(); k++)
/* 137:    */     {
/* 138:161 */       NodusGuiButton btn = (NodusGuiButton)this.buttonList.get(k);
/* 139:162 */       int btnWidth = GuiVideoSettings.getButtonWidth(btn);
/* 140:163 */       int btnHeight = GuiVideoSettings.getButtonHeight(btn);
/* 141:164 */       boolean flag = (i >= btn.xPosition) && (j >= btn.yPosition) && (i < btn.xPosition + btnWidth) && (j < btn.yPosition + btnHeight);
/* 142:166 */       if (flag) {
/* 143:168 */         return btn;
/* 144:    */       }
/* 145:    */     }
/* 146:172 */     return null;
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.GuiQualitySettingsOF
 * JD-Core Version:    0.7.0.1
 */