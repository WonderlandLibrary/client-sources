/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.awt.Toolkit;
/*   4:    */ import java.awt.datatransfer.Clipboard;
/*   5:    */ import java.awt.datatransfer.DataFlavor;
/*   6:    */ import java.awt.datatransfer.StringSelection;
/*   7:    */ import java.awt.datatransfer.Transferable;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.renderer.RenderHelper;
/*  15:    */ import net.minecraft.client.renderer.Tessellator;
/*  16:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  17:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  18:    */ import net.minecraft.client.settings.GameSettings;
/*  19:    */ import net.minecraft.item.EnumRarity;
/*  20:    */ import net.minecraft.item.ItemStack;
/*  21:    */ import net.minecraft.util.EnumChatFormatting;
/*  22:    */ import org.lwjgl.input.Keyboard;
/*  23:    */ import org.lwjgl.input.Mouse;
/*  24:    */ import org.lwjgl.opengl.GL11;
/*  25:    */ 
/*  26:    */ public class GuiScreen
/*  27:    */   extends Gui
/*  28:    */ {
/*  29: 31 */   protected static RenderItem itemRender = new RenderItem();
/*  30:    */   protected Minecraft mc;
/*  31:    */   public static int width;
/*  32:    */   public static int height;
/*  33: 43 */   protected List buttonList = new ArrayList();
/*  34: 46 */   protected List labelList = new ArrayList();
/*  35:    */   public boolean field_146291_p;
/*  36:    */   protected FontRenderer fontRendererObj;
/*  37:    */   private NodusGuiButton selectedButton;
/*  38:    */   private int eventButton;
/*  39:    */   private long lastMouseEvent;
/*  40:    */   private int field_146298_h;
/*  41:    */   private static final String __OBFID = "CL_00000710";
/*  42:    */   
/*  43:    */   public void drawScreen(int par1, int par2, float par3)
/*  44:    */   {
/*  45: 66 */     for (int var4 = 0; var4 < this.buttonList.size(); var4++) {
/*  46: 68 */       ((NodusGuiButton)this.buttonList.get(var4)).drawButton(this.mc, par1, par2);
/*  47:    */     }
/*  48: 71 */     for (var4 = 0; var4 < this.labelList.size(); var4++) {
/*  49: 73 */       ((GuiLabel)this.labelList.get(var4)).func_146159_a(this.mc, par1, par2);
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected void keyTyped(char par1, int par2)
/*  54:    */   {
/*  55: 82 */     if (par2 == 1)
/*  56:    */     {
/*  57: 84 */       this.mc.displayGuiScreen(null);
/*  58: 85 */       this.mc.setIngameFocus();
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String getClipboardString()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 96 */       Transferable var0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*  67: 98 */       if ((var0 != null) && (var0.isDataFlavorSupported(DataFlavor.stringFlavor))) {
/*  68:100 */         return (String)var0.getTransferData(DataFlavor.stringFlavor);
/*  69:    */       }
/*  70:    */     }
/*  71:    */     catch (Exception localException) {}
/*  72:108 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static void setClipboardString(String p_146275_0_)
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:118 */       StringSelection var1 = new StringSelection(p_146275_0_);
/*  80:119 */       Toolkit.getDefaultToolkit().getSystemClipboard().setContents(var1, null);
/*  81:    */     }
/*  82:    */     catch (Exception localException) {}
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected void func_146285_a(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_)
/*  86:    */   {
/*  87:129 */     List var4 = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*  88:131 */     for (int var5 = 0; var5 < var4.size(); var5++) {
/*  89:133 */       if (var5 == 0) {
/*  90:135 */         var4.set(var5, p_146285_1_.getRarity().rarityColor + (String)var4.get(var5));
/*  91:    */       } else {
/*  92:139 */         var4.set(var5, EnumChatFormatting.GRAY + (String)var4.get(var5));
/*  93:    */       }
/*  94:    */     }
/*  95:143 */     func_146283_a(var4, p_146285_2_, p_146285_3_);
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void func_146279_a(String p_146279_1_, int p_146279_2_, int p_146279_3_)
/*  99:    */   {
/* 100:148 */     func_146283_a(Arrays.asList(new String[] { p_146279_1_ }), p_146279_2_, p_146279_3_);
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected void func_146283_a(List p_146283_1_, int p_146283_2_, int p_146283_3_)
/* 104:    */   {
/* 105:153 */     if (!p_146283_1_.isEmpty())
/* 106:    */     {
/* 107:155 */       GL11.glDisable(32826);
/* 108:156 */       RenderHelper.disableStandardItemLighting();
/* 109:157 */       GL11.glDisable(2896);
/* 110:158 */       GL11.glDisable(2929);
/* 111:159 */       int var4 = 0;
/* 112:160 */       Iterator var5 = p_146283_1_.iterator();
/* 113:162 */       while (var5.hasNext())
/* 114:    */       {
/* 115:164 */         String var6 = (String)var5.next();
/* 116:165 */         int var7 = this.fontRendererObj.getStringWidth(var6);
/* 117:167 */         if (var7 > var4) {
/* 118:169 */           var4 = var7;
/* 119:    */         }
/* 120:    */       }
/* 121:173 */       int var14 = p_146283_2_ + 12;
/* 122:174 */       int var15 = p_146283_3_ - 12;
/* 123:175 */       int var8 = 8;
/* 124:177 */       if (p_146283_1_.size() > 1) {
/* 125:179 */         var8 += 2 + (p_146283_1_.size() - 1) * 10;
/* 126:    */       }
/* 127:182 */       if (var14 + var4 > width) {
/* 128:184 */         var14 -= 28 + var4;
/* 129:    */       }
/* 130:187 */       if (var15 + var8 + 6 > height) {
/* 131:189 */         var15 = height - var8 - 6;
/* 132:    */       }
/* 133:192 */       zLevel = 300.0F;
/* 134:193 */       itemRender.zLevel = 300.0F;
/* 135:194 */       int var9 = -267386864;
/* 136:195 */       drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
/* 137:196 */       drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
/* 138:197 */       drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
/* 139:198 */       drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
/* 140:199 */       drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
/* 141:200 */       int var10 = 1347420415;
/* 142:201 */       int var11 = (var10 & 0xFEFEFE) >> 1 | var10 & 0xFF000000;
/* 143:202 */       drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
/* 144:203 */       drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
/* 145:204 */       drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
/* 146:205 */       drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);
/* 147:207 */       for (int var12 = 0; var12 < p_146283_1_.size(); var12++)
/* 148:    */       {
/* 149:209 */         String var13 = (String)p_146283_1_.get(var12);
/* 150:210 */         this.fontRendererObj.drawStringWithShadow(var13, var14, var15, -1);
/* 151:212 */         if (var12 == 0) {
/* 152:214 */           var15 += 2;
/* 153:    */         }
/* 154:217 */         var15 += 10;
/* 155:    */       }
/* 156:220 */       zLevel = 0.0F;
/* 157:221 */       itemRender.zLevel = 0.0F;
/* 158:222 */       GL11.glEnable(2896);
/* 159:223 */       GL11.glEnable(2929);
/* 160:224 */       RenderHelper.enableStandardItemLighting();
/* 161:225 */       GL11.glEnable(32826);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 166:    */   {
/* 167:234 */     if (par3 == 0) {
/* 168:236 */       for (int var4 = 0; var4 < this.buttonList.size(); var4++)
/* 169:    */       {
/* 170:238 */         NodusGuiButton var5 = (NodusGuiButton)this.buttonList.get(var4);
/* 171:240 */         if (var5.mousePressed(this.mc, par1, par2))
/* 172:    */         {
/* 173:242 */           this.selectedButton = var5;
/* 174:243 */           var5.func_146113_a(this.mc.getSoundHandler());
/* 175:244 */           actionPerformed(var5);
/* 176:    */         }
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/* 182:    */   {
/* 183:252 */     if ((this.selectedButton != null) && (p_146286_3_ == 0))
/* 184:    */     {
/* 185:254 */       this.selectedButton.mouseReleased(p_146286_1_, p_146286_2_);
/* 186:255 */       this.selectedButton = null;
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_) {}
/* 191:    */   
/* 192:    */   protected void actionPerformed(NodusGuiButton p_146284_1_) {}
/* 193:    */   
/* 194:    */   public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_)
/* 195:    */   {
/* 196:269 */     this.mc = p_146280_1_;
/* 197:270 */     this.fontRendererObj = p_146280_1_.fontRenderer;
/* 198:271 */     width = p_146280_2_;
/* 199:272 */     height = p_146280_3_;
/* 200:273 */     this.buttonList.clear();
/* 201:274 */     initGui();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void initGui() {}
/* 205:    */   
/* 206:    */   public void handleInput()
/* 207:    */   {
/* 208:287 */     if (Mouse.isCreated()) {
/* 209:289 */       while (Mouse.next()) {
/* 210:291 */         handleMouseInput();
/* 211:    */       }
/* 212:    */     }
/* 213:295 */     if (Keyboard.isCreated()) {
/* 214:297 */       while (Keyboard.next()) {
/* 215:299 */         handleKeyboardInput();
/* 216:    */       }
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void handleMouseInput()
/* 221:    */   {
/* 222:309 */     int var1 = Mouse.getEventX() * width / this.mc.displayWidth;
/* 223:310 */     int var2 = height - Mouse.getEventY() * height / this.mc.displayHeight - 1;
/* 224:311 */     int var3 = Mouse.getEventButton();
/* 225:313 */     if ((Minecraft.isRunningOnMac) && (var3 == 0) && ((Keyboard.isKeyDown(29)) || (Keyboard.isKeyDown(157)))) {
/* 226:315 */       var3 = 1;
/* 227:    */     }
/* 228:318 */     if (Mouse.getEventButtonState())
/* 229:    */     {
/* 230:320 */       if ((this.mc.gameSettings.touchscreen) && (this.field_146298_h++ > 0)) {
/* 231:322 */         return;
/* 232:    */       }
/* 233:325 */       this.eventButton = var3;
/* 234:326 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 235:327 */       mouseClicked(var1, var2, this.eventButton);
/* 236:    */     }
/* 237:329 */     else if (var3 != -1)
/* 238:    */     {
/* 239:331 */       if ((this.mc.gameSettings.touchscreen) && (--this.field_146298_h > 0)) {
/* 240:333 */         return;
/* 241:    */       }
/* 242:336 */       this.eventButton = -1;
/* 243:337 */       mouseMovedOrUp(var1, var2, var3);
/* 244:    */     }
/* 245:339 */     else if ((this.eventButton != -1) && (this.lastMouseEvent > 0L))
/* 246:    */     {
/* 247:341 */       long var4 = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 248:342 */       mouseClickMove(var1, var2, this.eventButton, var4);
/* 249:    */     }
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void handleKeyboardInput()
/* 253:    */   {
/* 254:351 */     if (Keyboard.getEventKeyState())
/* 255:    */     {
/* 256:353 */       int var1 = Keyboard.getEventKey();
/* 257:354 */       char var2 = Keyboard.getEventCharacter();
/* 258:356 */       if (var1 == 87)
/* 259:    */       {
/* 260:358 */         this.mc.toggleFullscreen();
/* 261:359 */         return;
/* 262:    */       }
/* 263:362 */       keyTyped(var2, var1);
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   public void updateScreen() {}
/* 268:    */   
/* 269:    */   public void onGuiClosed() {}
/* 270:    */   
/* 271:    */   public void drawDefaultBackground()
/* 272:    */   {
/* 273:378 */     func_146270_b(0);
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void func_146270_b(int p_146270_1_)
/* 277:    */   {
/* 278:383 */     if (this.mc.theWorld != null) {
/* 279:385 */       drawGradientRect(0, 0, width, height, -1072689136, -804253680);
/* 280:    */     } else {
/* 281:389 */       func_146278_c(p_146270_1_);
/* 282:    */     }
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void func_146278_c(int p_146278_1_)
/* 286:    */   {
/* 287:395 */     GL11.glDisable(2896);
/* 288:396 */     GL11.glDisable(2912);
/* 289:397 */     Tessellator var2 = Tessellator.instance;
/* 290:398 */     this.mc.getTextureManager().bindTexture(optionsBackground);
/* 291:399 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 292:400 */     float var3 = 32.0F;
/* 293:401 */     var2.startDrawingQuads();
/* 294:402 */     var2.setColorOpaque_I(4210752);
/* 295:403 */     var2.addVertexWithUV(0.0D, height, 0.0D, 0.0D, height / var3 + p_146278_1_);
/* 296:404 */     var2.addVertexWithUV(width, height, 0.0D, width / var3, height / var3 + p_146278_1_);
/* 297:405 */     var2.addVertexWithUV(width, 0.0D, 0.0D, width / var3, p_146278_1_);
/* 298:406 */     var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, p_146278_1_);
/* 299:407 */     var2.draw();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public boolean doesGuiPauseGame()
/* 303:    */   {
/* 304:415 */     return true;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void confirmClicked(boolean par1, int par2) {}
/* 308:    */   
/* 309:    */   public static boolean isCtrlKeyDown()
/* 310:    */   {
/* 311:425 */     return (Keyboard.isKeyDown(219)) || (Keyboard.isKeyDown(220));
/* 312:    */   }
/* 313:    */   
/* 314:    */   public static boolean isShiftKeyDown()
/* 315:    */   {
/* 316:433 */     return (Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54));
/* 317:    */   }
/* 318:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreen
 * JD-Core Version:    0.7.0.1
 */