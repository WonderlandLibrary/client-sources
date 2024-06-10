/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.resources.I18n;
/*   7:    */ import net.minecraft.client.settings.GameSettings;
/*   8:    */ import net.minecraft.client.settings.GameSettings.Options;
/*   9:    */ import net.minecraft.src.GuiAnimationSettingsOF;
/*  10:    */ import net.minecraft.src.GuiDetailSettingsOF;
/*  11:    */ import net.minecraft.src.GuiOtherSettingsOF;
/*  12:    */ import net.minecraft.src.GuiPerformanceSettingsOF;
/*  13:    */ import net.minecraft.src.GuiQualitySettingsOF;
/*  14:    */ 
/*  15:    */ public class GuiVideoSettings
/*  16:    */   extends GuiScreen
/*  17:    */ {
/*  18:    */   private GuiScreen field_146498_f;
/*  19: 15 */   protected String field_146500_a = "Video Settings";
/*  20:    */   private GameSettings field_146499_g;
/*  21:    */   private boolean is64bit;
/*  22: 18 */   private static GameSettings.Options[] field_146502_i = { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.ADVANCED_OPENGL, GameSettings.Options.GAMMA, GameSettings.Options.CHUNK_LOADING, GameSettings.Options.FOG_FANCY, GameSettings.Options.FOG_START, GameSettings.Options.USE_SERVER_TEXTURES };
/*  23: 19 */   private int lastMouseX = 0;
/*  24: 20 */   private int lastMouseY = 0;
/*  25: 21 */   private long mouseStillTime = 0L;
/*  26:    */   private static final String __OBFID = "CL_00000718";
/*  27:    */   
/*  28:    */   public GuiVideoSettings(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
/*  29:    */   {
/*  30: 26 */     this.field_146498_f = par1GuiScreen;
/*  31: 27 */     this.field_146499_g = par2GameSettings;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void initGui()
/*  35:    */   {
/*  36: 35 */     this.field_146500_a = I18n.format("options.videoTitle", new Object[0]);
/*  37: 36 */     this.buttonList.clear();
/*  38: 37 */     this.is64bit = false;
/*  39: 38 */     String[] var1 = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*  40: 39 */     String[] var2 = var1;
/*  41: 40 */     int var3 = var1.length;
/*  42: 42 */     for (int var8 = 0; var8 < var3; var8++)
/*  43:    */     {
/*  44: 44 */       String var9 = var2[var8];
/*  45: 45 */       String var10 = System.getProperty(var9);
/*  46: 47 */       if ((var10 != null) && (var10.contains("64")))
/*  47:    */       {
/*  48: 49 */         this.is64bit = true;
/*  49: 50 */         break;
/*  50:    */       }
/*  51:    */     }
/*  52: 54 */     boolean var12 = false;
/*  53: 55 */     boolean var111 = !this.is64bit;
/*  54: 56 */     GameSettings.Options[] var13 = field_146502_i;
/*  55: 57 */     int var14 = var13.length;
/*  56: 58 */     boolean var11 = false;
/*  57: 62 */     for (int var15 = 0; var15 < var14; var15++)
/*  58:    */     {
/*  59: 64 */       GameSettings.Options y = var13[var15];
/*  60: 65 */       int x = width / 2 - 155 + var15 % 2 * 160;
/*  61: 66 */       int y1 = height / 6 + 21 * (var15 / 2) - 10;
/*  62: 68 */       if (y.getEnumFloat()) {
/*  63: 70 */         this.buttonList.add(new GuiOptionSlider(y.returnEnumOrdinal(), x, y1, y));
/*  64:    */       } else {
/*  65: 74 */         this.buttonList.add(new GuiOptionButton(y.returnEnumOrdinal(), x, y1, y, this.field_146499_g.getKeyBinding(y)));
/*  66:    */       }
/*  67:    */     }
/*  68: 78 */     int var16 = height / 6 + 21 * (var15 / 2) - 10;
/*  69: 79 */     boolean var17 = false;
/*  70: 80 */     int x = width / 2 - 155 + 160;
/*  71: 81 */     this.buttonList.add(new GuiOptionButton(102, x, var16, "Quality..."));
/*  72: 82 */     var16 += 21;
/*  73: 83 */     x = width / 2 - 155 + 0;
/*  74: 84 */     this.buttonList.add(new GuiOptionButton(101, x, var16, "Details..."));
/*  75: 85 */     x = width / 2 - 155 + 160;
/*  76: 86 */     this.buttonList.add(new GuiOptionButton(112, x, var16, "Performance..."));
/*  77: 87 */     var16 += 21;
/*  78: 88 */     x = width / 2 - 155 + 0;
/*  79: 89 */     this.buttonList.add(new GuiOptionButton(111, x, var16, "Animations..."));
/*  80: 90 */     x = width / 2 - 155 + 160;
/*  81: 91 */     this.buttonList.add(new GuiOptionButton(122, x, var16, "Other..."));
/*  82: 92 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void actionPerformed(NodusGuiButton par1GuiButton)
/*  86:    */   {
/*  87: 97 */     if (par1GuiButton.enabled)
/*  88:    */     {
/*  89: 99 */       int var2 = this.field_146499_g.guiScale;
/*  90:101 */       if ((par1GuiButton.id < 100) && ((par1GuiButton instanceof GuiOptionButton)))
/*  91:    */       {
/*  92:103 */         this.field_146499_g.setOptionValue(((GuiOptionButton)par1GuiButton).func_146136_c(), 1);
/*  93:104 */         par1GuiButton.displayString = this.field_146499_g.getKeyBinding(GameSettings.Options.getEnumOptions(par1GuiButton.id));
/*  94:    */       }
/*  95:107 */       if (par1GuiButton.id == 200)
/*  96:    */       {
/*  97:109 */         this.mc.gameSettings.saveOptions();
/*  98:110 */         this.mc.displayGuiScreen(this.field_146498_f);
/*  99:    */       }
/* 100:113 */       if (this.field_146499_g.guiScale != var2)
/* 101:    */       {
/* 102:115 */         ScaledResolution scr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/* 103:116 */         int var4 = scr.getScaledWidth();
/* 104:117 */         int var5 = scr.getScaledHeight();
/* 105:118 */         setWorldAndResolution(this.mc, var4, var5);
/* 106:    */       }
/* 107:121 */       if (par1GuiButton.id == 101)
/* 108:    */       {
/* 109:123 */         this.mc.gameSettings.saveOptions();
/* 110:124 */         GuiDetailSettingsOF scr1 = new GuiDetailSettingsOF(this, this.field_146499_g);
/* 111:125 */         this.mc.displayGuiScreen(scr1);
/* 112:    */       }
/* 113:128 */       if (par1GuiButton.id == 102)
/* 114:    */       {
/* 115:130 */         this.mc.gameSettings.saveOptions();
/* 116:131 */         GuiQualitySettingsOF scr2 = new GuiQualitySettingsOF(this, this.field_146499_g);
/* 117:132 */         this.mc.displayGuiScreen(scr2);
/* 118:    */       }
/* 119:135 */       if (par1GuiButton.id == 111)
/* 120:    */       {
/* 121:137 */         this.mc.gameSettings.saveOptions();
/* 122:138 */         GuiAnimationSettingsOF scr3 = new GuiAnimationSettingsOF(this, this.field_146499_g);
/* 123:139 */         this.mc.displayGuiScreen(scr3);
/* 124:    */       }
/* 125:142 */       if (par1GuiButton.id == 112)
/* 126:    */       {
/* 127:144 */         this.mc.gameSettings.saveOptions();
/* 128:145 */         GuiPerformanceSettingsOF scr4 = new GuiPerformanceSettingsOF(this, this.field_146499_g);
/* 129:146 */         this.mc.displayGuiScreen(scr4);
/* 130:    */       }
/* 131:149 */       if (par1GuiButton.id == 122)
/* 132:    */       {
/* 133:151 */         this.mc.gameSettings.saveOptions();
/* 134:152 */         GuiOtherSettingsOF scr5 = new GuiOtherSettingsOF(this, this.field_146499_g);
/* 135:153 */         this.mc.displayGuiScreen(scr5);
/* 136:    */       }
/* 137:156 */       if (par1GuiButton.id == GameSettings.Options.AO_LEVEL.ordinal()) {}
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void drawScreen(int x, int y, float z)
/* 142:    */   {
/* 143:168 */     drawDefaultBackground();
/* 144:169 */     drawCenteredString(this.fontRendererObj, this.field_146500_a, width / 2, this.is64bit ? 20 : 5, 16777215);
/* 145:171 */     if ((!this.is64bit) && (this.field_146499_g.renderDistanceChunks > 8)) {}
/* 146:176 */     super.drawScreen(x, y, z);
/* 147:178 */     if ((Math.abs(x - this.lastMouseX) <= 5) && (Math.abs(y - this.lastMouseY) <= 5))
/* 148:    */     {
/* 149:180 */       short activateDelay = 700;
/* 150:182 */       if (System.currentTimeMillis() >= this.mouseStillTime + activateDelay)
/* 151:    */       {
/* 152:184 */         int x1 = width / 2 - 150;
/* 153:185 */         int y1 = height / 6 - 5;
/* 154:187 */         if (y <= y1 + 98) {
/* 155:189 */           y1 += 105;
/* 156:    */         }
/* 157:192 */         int x2 = x1 + 150 + 150;
/* 158:193 */         int y2 = y1 + 84 + 10;
/* 159:194 */         NodusGuiButton btn = getSelectedButton(x, y);
/* 160:196 */         if (btn != null)
/* 161:    */         {
/* 162:198 */           String s = getButtonName(btn.displayString);
/* 163:199 */           String[] lines = getTooltipLines(s);
/* 164:201 */           if (lines == null) {
/* 165:203 */             return;
/* 166:    */           }
/* 167:206 */           drawGradientRect(x1, y1, x2, y2, -536870912, -536870912);
/* 168:208 */           for (int i = 0; i < lines.length; i++)
/* 169:    */           {
/* 170:210 */             String line = lines[i];
/* 171:211 */             this.fontRendererObj.drawStringWithShadow(line, x1 + 5, y1 + 5 + i * 11, 14540253);
/* 172:    */           }
/* 173:    */         }
/* 174:    */       }
/* 175:    */     }
/* 176:    */     else
/* 177:    */     {
/* 178:218 */       this.lastMouseX = x;
/* 179:219 */       this.lastMouseY = y;
/* 180:220 */       this.mouseStillTime = System.currentTimeMillis();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   private String[] getTooltipLines(String btnName)
/* 185:    */   {
/* 186:226 */     return btnName.equals("Chunk Loading") ? new String[] { "Chunk Loading", "  Default - unstable FPS when loading chunks", "  Smooth - stable FPS", "  Multi-Core - stable FPS, 3x faster world loading", "Smooth and Multi-Core remove the stuttering and ", "freezes caused by chunk loading.", "Multi-Core can speed up 3x the world loading and", "increase FPS by using a second CPU core." } : btnName.equals("Brightness") ? new String[] { "Increases the brightness of darker objects", "  OFF - standard brightness", "  100% - maximum brightness for darker objects", "This options does not change the brightness of ", "fully black objects" } : btnName.equals("Fog Start") ? new String[] { "Fog start", "  0.2 - the fog starts near the player", "  0.8 - the fog starts far from the player", "This option usually does not affect the performance." } : btnName.equals("Fog") ? new String[] { "Fog type", "  Fast - faster fog", "  Fancy - slower fog, looks better", "  OFF - no fog, fastest", "The fancy fog is available only if it is supported by the ", "graphic card." } : btnName.equals("Advanced OpenGL") ? new String[] { "Detect and render only visible geometry", "  OFF - all geometry is rendered (slower)", "  Fast - only visible geometry is rendered (fastest)", "  Fancy - conservative, avoids visual artifacts (faster)", "The option is available only if it is supported by the ", "graphic card." } : btnName.equals("Server Textures") ? new String[] { "Server textures", "Use the resource pack recommended by the server" } : btnName.equals("GUI Scale") ? new String[] { "GUI Scale", "Smaller GUI might be faster" } : btnName.equals("View Bobbing") ? new String[] { "More realistic movement.", "When using mipmaps set it to OFF for best results." } : btnName.equals("Max Framerate") ? new String[] { "Max framerate", "  VSync - limit to monitor framerate (60, 30, 20)", "  5-255 - variable", "  Unlimited - no limit (fastest)", "The framerate limit decreases the FPS even if", "the limit value is not reached." } : btnName.equals("Smooth Lighting Level") ? new String[] { "Smooth lighting level", "  OFF - no smooth lighting (faster)", "  1% - light smooth lighting (slower)", "  100% - dark smooth lighting (slower)" } : btnName.equals("Smooth Lighting") ? new String[] { "Smooth lighting", "  OFF - no smooth lighting (faster)", "  Minimum - simple smooth lighting (slower)", "  Maximum - complex smooth lighting (slowest)" } : btnName.equals("Render Distance") ? new String[] { "Visible distance", "  2 Tiny - 32m (fastest)", "  4 Short - 64m (faster)", "  8 Normal - 128m", "  16 Far - 256m (slower)", "  32 Extreme - 512m (slowest!)", "The Extreme view distance is very resource demanding!", "Values over 16 Far are only effective in local worlds." } : btnName.equals("Graphics") ? new String[] { "Visual quality", "  Fast  - lower quality, faster", "  Fancy - higher quality, slower", "Changes the appearance of clouds, leaves, water,", "shadows and grass sides." } : null;
/* 187:    */   }
/* 188:    */   
/* 189:    */   private String getButtonName(String displayString)
/* 190:    */   {
/* 191:231 */     int pos = displayString.indexOf(':');
/* 192:232 */     return pos < 0 ? displayString : displayString.substring(0, pos);
/* 193:    */   }
/* 194:    */   
/* 195:    */   private NodusGuiButton getSelectedButton(int i, int j)
/* 196:    */   {
/* 197:237 */     for (int k = 0; k < this.buttonList.size(); k++)
/* 198:    */     {
/* 199:239 */       NodusGuiButton btn = (NodusGuiButton)this.buttonList.get(k);
/* 200:240 */       boolean flag = (i >= btn.xPosition) && (j >= btn.yPosition) && (i < btn.xPosition + btn.width) && (j < btn.yPosition + btn.height);
/* 201:242 */       if (flag) {
/* 202:244 */         return btn;
/* 203:    */       }
/* 204:    */     }
/* 205:248 */     return null;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static int getButtonWidth(NodusGuiButton btn)
/* 209:    */   {
/* 210:253 */     return btn.width;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static int getButtonHeight(NodusGuiButton btn)
/* 214:    */   {
/* 215:258 */     return btn.height;
/* 216:    */   }
/* 217:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiVideoSettings
 * JD-Core Version:    0.7.0.1
 */