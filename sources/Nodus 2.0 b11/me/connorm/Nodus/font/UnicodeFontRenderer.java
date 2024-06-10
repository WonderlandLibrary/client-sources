/*  1:   */ package me.connorm.Nodus.font;
/*  2:   */ 
/*  3:   */ import java.awt.Font;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.client.Minecraft;
/*  6:   */ import net.minecraft.client.gui.FontRenderer;
/*  7:   */ import net.minecraft.util.ResourceLocation;
/*  8:   */ import org.lwjgl.opengl.GL11;
/*  9:   */ import org.newdawn.slick.SlickException;
/* 10:   */ import org.newdawn.slick.UnicodeFont;
/* 11:   */ import org.newdawn.slick.font.effects.ColorEffect;
/* 12:   */ 
/* 13:   */ public class UnicodeFontRenderer
/* 14:   */   extends FontRenderer
/* 15:   */ {
/* 16:   */   private final UnicodeFont font;
/* 17:   */   
/* 18:   */   public UnicodeFontRenderer(Font awtFont)
/* 19:   */   {
/* 20:31 */     super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().renderEngine, false);
/* 21:   */     
/* 22:33 */     this.font = new UnicodeFont(awtFont);
/* 23:34 */     this.font.addAsciiGlyphs();
/* 24:35 */     this.font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
/* 25:   */     try
/* 26:   */     {
/* 27:38 */       this.font.loadGlyphs();
/* 28:   */     }
/* 29:   */     catch (SlickException exception)
/* 30:   */     {
/* 31:41 */       throw new RuntimeException(exception);
/* 32:   */     }
/* 33:43 */     String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
/* 34:44 */     this.FONT_HEIGHT = (this.font.getHeight(alphabet) / 2);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int drawString(String string, int x, int y, int color)
/* 38:   */   {
/* 39:49 */     if (string == null) {
/* 40:50 */       return 0;
/* 41:   */     }
/* 42:52 */     GL11.glPushMatrix();
/* 43:53 */     GL11.glScaled(0.5D, 0.5D, 0.5D);
/* 44:   */     
/* 45:55 */     boolean blend = GL11.glIsEnabled(3042);
/* 46:56 */     boolean lighting = GL11.glIsEnabled(2896);
/* 47:57 */     boolean texture = GL11.glIsEnabled(3553);
/* 48:58 */     if (!blend) {
/* 49:59 */       GL11.glEnable(3042);
/* 50:   */     }
/* 51:60 */     if (lighting) {
/* 52:61 */       GL11.glDisable(2896);
/* 53:   */     }
/* 54:62 */     if (texture) {
/* 55:63 */       GL11.glDisable(3553);
/* 56:   */     }
/* 57:64 */     x *= 2;
/* 58:65 */     y *= 2;
/* 59:   */     
/* 60:67 */     this.font.drawString(x, y, string, new org.newdawn.slick.Color(color));
/* 61:69 */     if (texture) {
/* 62:70 */       GL11.glEnable(3553);
/* 63:   */     }
/* 64:71 */     if (lighting) {
/* 65:72 */       GL11.glEnable(2896);
/* 66:   */     }
/* 67:73 */     if (!blend) {
/* 68:74 */       GL11.glDisable(3042);
/* 69:   */     }
/* 70:75 */     GL11.glPopMatrix();
/* 71:76 */     return x;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public int drawStringWithShadow(String string, int x, int y, int color)
/* 75:   */   {
/* 76:82 */     return drawString(string, x, y, color);
/* 77:   */   }
/* 78:   */   
/* 79:   */   public int getCharWidth(char c)
/* 80:   */   {
/* 81:88 */     return getStringWidth(Character.toString(c));
/* 82:   */   }
/* 83:   */   
/* 84:   */   public int getStringWidth(String string)
/* 85:   */   {
/* 86:94 */     return this.font.getWidth(string) / 2;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public int getStringHeight(String string)
/* 90:   */   {
/* 91:99 */     return this.font.getHeight(string) / 2;
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.font.UnicodeFontRenderer
 * JD-Core Version:    0.7.0.1
 */