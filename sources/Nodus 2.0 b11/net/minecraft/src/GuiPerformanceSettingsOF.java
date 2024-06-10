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
/*  16:    */ public class GuiPerformanceSettingsOF
/*  17:    */   extends GuiScreen
/*  18:    */ {
/*  19:    */   private GuiScreen prevScreen;
/*  20: 15 */   protected String title = "Performance Settings";
/*  21:    */   private GameSettings settings;
/*  22: 17 */   private static GameSettings.Options[] enumOptions = { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.LOAD_FAR, GameSettings.Options.PRELOADED_CHUNKS, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.FAST_MATH, GameSettings.Options.LAZY_CHUNK_LOADING, GameSettings.Options.FAST_RENDER };
/*  23: 18 */   private int lastMouseX = 0;
/*  24: 19 */   private int lastMouseY = 0;
/*  25: 20 */   private long mouseStillTime = 0L;
/*  26:    */   
/*  27:    */   public GuiPerformanceSettingsOF(GuiScreen guiscreen, GameSettings gamesettings)
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
/* 106:126 */             this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, 14540253);
/* 107:    */           }
/* 108:    */         }
/* 109:    */       }
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:133 */       this.lastMouseX = x;
/* 114:134 */       this.lastMouseY = y;
/* 115:135 */       this.mouseStillTime = System.currentTimeMillis();
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   private String[] getTooltipLines(String btnName)
/* 120:    */   {
/* 121:141 */     return btnName.equals("Fast Render") ? new String[] { "Fast Render", " OFF - standard rendering (default)", " ON - faster rendering", "Uses optimized rendering algorithm which decreases", "the GPU load and may substantionally increase the FPS.", "You can turn if OFF if you notice flickering textures", "on some blocks." } : btnName.equals("Fast Math") ? new String[] { "Fast Math", " OFF - standard math (default)", " ON - faster math", "Uses optimized sin() and cos() functions which can", "better utilize the CPU cache and increase the FPS." } : btnName.equals("Lazy Chunk Loading") ? new String[] { "Lazy Chunk Loading", " OFF - default server chunk loading", " ON - lazy server chunk loading (smoother)", "Smooths the integrated server chunk loading by", "distributing the chunks over several ticks.", "Turn it OFF if parts of the world do not load correctly.", "Effective only for local worlds and single-core CPU." } : btnName.equals("Dynamic Updates") ? new String[] { "Dynamic chunk updates", " OFF - (default) standard chunk updates per frame", " ON - more updates while the player is standing still", "Dynamic updates force more chunk updates while", "the player is standing still to load the world faster." } : btnName.equals("Chunk Updates") ? new String[] { "Chunk updates", " 1 - (default) slower world loading, higher FPS", " 3 - faster world loading, lower FPS", " 5 - fastest world loading, lowest FPS", "Number of chunk updates per rendered frame,", "higher values may destabilize the framerate." } : btnName.equals("Preloaded Chunks") ? new String[] { "Defines an area in which no chunks will be loaded", "  OFF - after 5m new chunks will be loaded", "  2 - after 32m  new chunks will be loaded", "  8 - after 128m new chunks will be loaded", "Higher values need more time to load all the chunks", "and may decrease the FPS." } : btnName.equals("Load Far") ? new String[] { "Loads the world chunks at distance Far.", "Switching the render distance does not cause all chunks ", "to be loaded again.", "  OFF - world chunks loaded up to render distance", "  ON - world chunks loaded at distance Far, allows", "       fast render distance switching" } : btnName.equals("Smooth World") ? new String[] { "Removes lag spikes caused by the internal server.", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "Stabilizes FPS by distributing the internal server load.", "Effective only for local worlds and single-core CPU." } : btnName.equals("Smooth FPS") ? new String[] { "Stabilizes FPS by flushing the graphic driver buffers", "  OFF - no stabilization, FPS may fluctuate", "  ON - FPS stabilization", "This option is graphics driver dependant and its effect", "is not always visible" } : null;
/* 122:    */   }
/* 123:    */   
/* 124:    */   private String getButtonName(String displayString)
/* 125:    */   {
/* 126:146 */     int pos = displayString.indexOf(':');
/* 127:147 */     return pos < 0 ? displayString : displayString.substring(0, pos);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private NodusGuiButton getSelectedButton(int i, int j)
/* 131:    */   {
/* 132:152 */     for (int k = 0; k < this.buttonList.size(); k++)
/* 133:    */     {
/* 134:154 */       NodusGuiButton btn = (NodusGuiButton)this.buttonList.get(k);
/* 135:155 */       int btnWidth = GuiVideoSettings.getButtonWidth(btn);
/* 136:156 */       int btnHeight = GuiVideoSettings.getButtonHeight(btn);
/* 137:157 */       boolean flag = (i >= btn.xPosition) && (j >= btn.yPosition) && (i < btn.xPosition + btnWidth) && (j < btn.yPosition + btnHeight);
/* 138:159 */       if (flag) {
/* 139:161 */         return btn;
/* 140:    */       }
/* 141:    */     }
/* 142:165 */     return null;
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.GuiPerformanceSettingsOF
 * JD-Core Version:    0.7.0.1
 */