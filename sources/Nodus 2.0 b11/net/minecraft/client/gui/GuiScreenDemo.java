/*  1:   */ package net.minecraft.client.gui;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import java.net.URI;
/*  5:   */ import java.util.List;
/*  6:   */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  7:   */ import net.minecraft.client.Minecraft;
/*  8:   */ import net.minecraft.client.renderer.texture.TextureManager;
/*  9:   */ import net.minecraft.client.resources.I18n;
/* 10:   */ import net.minecraft.client.settings.GameSettings;
/* 11:   */ import net.minecraft.client.settings.KeyBinding;
/* 12:   */ import net.minecraft.util.ResourceLocation;
/* 13:   */ import org.apache.logging.log4j.LogManager;
/* 14:   */ import org.apache.logging.log4j.Logger;
/* 15:   */ import org.lwjgl.opengl.GL11;
/* 16:   */ 
/* 17:   */ public class GuiScreenDemo
/* 18:   */   extends GuiScreen
/* 19:   */ {
/* 20:16 */   private static final Logger logger = ;
/* 21:17 */   private static final ResourceLocation field_146348_f = new ResourceLocation("textures/gui/demo_background.png");
/* 22:   */   private static final String __OBFID = "CL_00000691";
/* 23:   */   
/* 24:   */   public void initGui()
/* 25:   */   {
/* 26:25 */     this.buttonList.clear();
/* 27:26 */     byte var1 = -16;
/* 28:27 */     this.buttonList.add(new NodusGuiButton(1, width / 2 - 116, height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.buy", new Object[0])));
/* 29:28 */     this.buttonList.add(new NodusGuiButton(2, width / 2 + 2, height / 2 + 62 + var1, 114, 20, I18n.format("demo.help.later", new Object[0])));
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 33:   */   {
/* 34:33 */     switch (p_146284_1_.id)
/* 35:   */     {
/* 36:   */     case 1: 
/* 37:36 */       p_146284_1_.enabled = false;
/* 38:   */       try
/* 39:   */       {
/* 40:40 */         Class var2 = Class.forName("java.awt.Desktop");
/* 41:41 */         Object var3 = var2.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 42:42 */         var2.getMethod("browse", new Class[] { URI.class }).invoke(var3, new Object[] { new URI("http://www.minecraft.net/store?source=demo") });
/* 43:   */       }
/* 44:   */       catch (Throwable var4)
/* 45:   */       {
/* 46:46 */         logger.error("Couldn't open link", var4);
/* 47:   */       }
/* 48:   */     case 2: 
/* 49:52 */       this.mc.displayGuiScreen(null);
/* 50:53 */       this.mc.setIngameFocus();
/* 51:   */     }
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void updateScreen()
/* 55:   */   {
/* 56:62 */     super.updateScreen();
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void drawDefaultBackground()
/* 60:   */   {
/* 61:67 */     super.drawDefaultBackground();
/* 62:68 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 63:69 */     this.mc.getTextureManager().bindTexture(field_146348_f);
/* 64:70 */     int var1 = (width - 248) / 2;
/* 65:71 */     int var2 = (height - 166) / 2;
/* 66:72 */     drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void drawScreen(int par1, int par2, float par3)
/* 70:   */   {
/* 71:80 */     drawDefaultBackground();
/* 72:81 */     int var4 = (width - 248) / 2 + 10;
/* 73:82 */     int var5 = (height - 166) / 2 + 8;
/* 74:83 */     this.fontRendererObj.drawString(I18n.format("demo.help.title", new Object[0]), var4, var5, 2039583);
/* 75:84 */     var5 += 12;
/* 76:85 */     GameSettings var6 = this.mc.gameSettings;
/* 77:86 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementShort", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode()) }), var4, var5, 5197647);
/* 78:87 */     this.fontRendererObj.drawString(I18n.format("demo.help.movementMouse", new Object[0]), var4, var5 + 12, 5197647);
/* 79:88 */     this.fontRendererObj.drawString(I18n.format("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode()) }), var4, var5 + 24, 5197647);
/* 80:89 */     this.fontRendererObj.drawString(I18n.format("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode()) }), var4, var5 + 36, 5197647);
/* 81:90 */     this.fontRendererObj.drawSplitString(I18n.format("demo.help.fullWrapped", new Object[0]), var4, var5 + 68, 218, 2039583);
/* 82:91 */     super.drawScreen(par1, par2, par3);
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenDemo
 * JD-Core Version:    0.7.0.1
 */