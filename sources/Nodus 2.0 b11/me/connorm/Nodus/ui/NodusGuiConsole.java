/*   1:    */ package me.connorm.Nodus.ui;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.net.URI;
/*   5:    */ import me.connorm.Nodus.Nodus;
/*   6:    */ import me.connorm.Nodus.command.NodusCommand;
/*   7:    */ import me.connorm.Nodus.command.NodusCommandManager;
/*   8:    */ import me.connorm.Nodus.command.commands.Help;
/*   9:    */ import me.connorm.Nodus.ui.gui.theme.nodus.ColorUtils;
/*  10:    */ import me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils;
/*  11:    */ import net.minecraft.client.Minecraft;
/*  12:    */ import net.minecraft.client.gui.FontRenderer;
/*  13:    */ import net.minecraft.client.gui.GuiScreen;
/*  14:    */ import net.minecraft.client.gui.GuiTextField;
/*  15:    */ import org.lwjgl.input.Keyboard;
/*  16:    */ import org.lwjgl.input.Mouse;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public class NodusGuiConsole
/*  20:    */   extends GuiScreen
/*  21:    */ {
/*  22:    */   private boolean field_73897_d;
/*  23:    */   private boolean field_73905_m;
/*  24:    */   private int field_73903_n;
/*  25:    */   private URI clickedURI;
/*  26:    */   protected GuiTextField inputField;
/*  27: 25 */   private String defaultInputFieldText = "";
/*  28:    */   
/*  29:    */   public void initGui()
/*  30:    */   {
/*  31: 29 */     Keyboard.enableRepeatEvents(true);
/*  32: 30 */     this.inputField = new GuiTextField(Nodus.theNodus.getMinecraft().fontRenderer, 79, 18, GuiScreen.width - 75, 12);
/*  33: 31 */     this.inputField.setMaxStringLength(100);
/*  34: 32 */     this.inputField.setEnableBackgroundDrawing(false);
/*  35: 33 */     this.inputField.setFocused(true);
/*  36: 34 */     this.inputField.setText(this.defaultInputFieldText);
/*  37: 35 */     this.inputField.setCanLoseFocus(false);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void onGuiClosed()
/*  41:    */   {
/*  42: 40 */     Keyboard.enableRepeatEvents(false);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void updateScreen()
/*  46:    */   {
/*  47: 45 */     this.inputField.updateCursorCounter();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void keyTyped(char par1, int par2)
/*  51:    */   {
/*  52: 50 */     this.field_73905_m = false;
/*  53: 52 */     if (par2 != 15) {
/*  54: 54 */       this.field_73897_d = false;
/*  55:    */     }
/*  56: 57 */     if (par2 == 1)
/*  57:    */     {
/*  58: 59 */       this.mc.displayGuiScreen(null);
/*  59:    */     }
/*  60: 61 */     else if ((par2 != 28) && (par2 != 156))
/*  61:    */     {
/*  62: 63 */       this.inputField.textboxKeyTyped(par1, par2);
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66: 67 */       String var3 = this.inputField.getText().trim();
/*  67: 69 */       if (var3.length() > 0)
/*  68:    */       {
/*  69: 71 */         String commandRun = Character.toString(Nodus.theNodus.commandManager.commandPrefix) + var3;
/*  70: 72 */         Nodus.theNodus.commandManager.runCommands(commandRun);
/*  71:    */       }
/*  72: 75 */       this.mc.displayGuiScreen(null);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void func_146274_d()
/*  77:    */   {
/*  78: 81 */     super.handleMouseInput();
/*  79: 82 */     int var1 = Mouse.getEventDWheel();
/*  80: 84 */     if (var1 != 0)
/*  81:    */     {
/*  82: 86 */       if (var1 > 1) {
/*  83: 88 */         var1 = 1;
/*  84:    */       }
/*  85: 91 */       if (var1 < -1) {
/*  86: 93 */         var1 = -1;
/*  87:    */       }
/*  88: 96 */       if (!isShiftKeyDown()) {
/*  89: 98 */         var1 *= 7;
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void mouseClicked(int par1, int par2, int par3)
/*  95:    */   {
/*  96:105 */     this.inputField.mouseClicked(par1, par2, par3);
/*  97:106 */     super.mouseClicked(par1, par2, par3);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void confirmClicked(boolean par1, int par2)
/* 101:    */   {
/* 102:111 */     if (par2 == 0)
/* 103:    */     {
/* 104:113 */       if (par1) {
/* 105:115 */         func_73896_a(this.clickedURI);
/* 106:    */       }
/* 107:118 */       this.clickedURI = null;
/* 108:119 */       this.mc.displayGuiScreen(this);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void func_73896_a(URI par1URI)
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:127 */       Class var2 = Class.forName("java.awt.Desktop");
/* 117:128 */       Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 118:129 */       var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { par1URI });
/* 119:    */     }
/* 120:    */     catch (Throwable var4)
/* 121:    */     {
/* 122:133 */       var4.printStackTrace();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void completePlayerName() {}
/* 127:    */   
/* 128:    */   private void func_73893_a(String par1Str, String par2Str)
/* 129:    */   {
/* 130:143 */     if (par1Str.length() >= 1) {
/* 131:145 */       this.field_73905_m = true;
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void drawScreen(int par1, int par2, float par3)
/* 136:    */   {
/* 137:151 */     GL11.glPushMatrix();
/* 138:152 */     GL11.glDisable(2896);
/* 139:153 */     NodusUtils.drawTopNodusRect(75.0F, 14.0F, width - 75, 30.0F);
/* 140:154 */     this.inputField.drawTextBox();
/* 141:155 */     String typedCommand = this.inputField.getText();
/* 142:156 */     int theHeight = 35;
/* 143:158 */     for (NodusCommand nodusCommand : Nodus.theNodus.commandManager.getCommands())
/* 144:    */     {
/* 145:160 */       String isHelp = (nodusCommand instanceof Help) ? "" : " - ";
/* 146:161 */       String isKeybind = " | " + nodusCommand.getDescription();
/* 147:162 */       if ((nodusCommand.getCommandName().startsWith(typedCommand)) && (typedCommand.length() > 0))
/* 148:    */       {
/* 149:164 */         String toDisplay = nodusCommand.getConsoleDisplay() + isHelp + nodusCommand.getSyntax() + isKeybind;
/* 150:165 */         NodusUtils.drawSmallNodusRect(75.0F, theHeight - 3, width - 75, theHeight + 12);
/* 151:166 */         Nodus.theNodus.getMinecraft().fontRenderer.drawString(toDisplay, 79, theHeight, -1);
/* 152:167 */         if (ColorUtils.secondaryColor == -1) {
/* 153:168 */           Nodus.theNodus.getMinecraft().fontRenderer.drawString(typedCommand, 79, theHeight, -10101190);
/* 154:    */         } else {
/* 155:170 */           Nodus.theNodus.getMinecraft().fontRenderer.drawString(typedCommand, 79, theHeight, ColorUtils.secondaryColor);
/* 156:    */         }
/* 157:171 */         theHeight += 16;
/* 158:    */       }
/* 159:172 */       else if (typedCommand.startsWith(nodusCommand.getCommandName()))
/* 160:    */       {
/* 161:174 */         String toDisplay = nodusCommand.getConsoleDisplay() + isHelp + nodusCommand.getSyntax() + isKeybind;
/* 162:175 */         NodusUtils.drawSmallNodusRect(75.0F, theHeight - 3, width - 75, theHeight + 12);
/* 163:    */         
/* 164:177 */         Nodus.theNodus.getMinecraft().fontRenderer.drawString(toDisplay, 79, theHeight, -1);
/* 165:179 */         if (ColorUtils.secondaryColor == -1) {
/* 166:180 */           Nodus.theNodus.getMinecraft().fontRenderer.drawString(nodusCommand.getConsoleDisplay(), 79, theHeight, -10101190);
/* 167:    */         } else {
/* 168:182 */           Nodus.theNodus.getMinecraft().fontRenderer.drawString(nodusCommand.getConsoleDisplay(), 79, theHeight, ColorUtils.secondaryColor);
/* 169:    */         }
/* 170:184 */         theHeight += 16;
/* 171:    */       }
/* 172:    */     }
/* 173:188 */     if (typedCommand.length() == 0) {
/* 174:190 */       Nodus.theNodus.getMinecraft().fontRenderer.drawString("Type \"help\" to recieve help.", 79, 35, -1);
/* 175:    */     }
/* 176:192 */     GL11.glPopMatrix();
/* 177:193 */     super.drawScreen(par1, par2, par3);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean doesGuiPauseGame()
/* 181:    */   {
/* 182:198 */     return true;
/* 183:    */   }
/* 184:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.NodusGuiConsole
 * JD-Core Version:    0.7.0.1
 */