/*   1:    */ package me.connorm.Nodus.ui;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.Nodus;
/*   4:    */ import me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*   7:    */ import net.minecraft.client.audio.SoundHandler;
/*   8:    */ import net.minecraft.client.gui.Gui;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.util.ResourceLocation;
/*  11:    */ import org.lwjgl.opengl.GL11;
/*  12:    */ 
/*  13:    */ public class NodusGuiButton
/*  14:    */   extends Gui
/*  15:    */ {
/*  16: 15 */   protected static final ResourceLocation field_146122_a = new ResourceLocation("textures/gui/widgets.png");
/*  17:    */   public int width;
/*  18:    */   public int height;
/*  19:    */   public int xPosition;
/*  20:    */   public int yPosition;
/*  21:    */   public String displayString;
/*  22:    */   public int id;
/*  23:    */   public boolean enabled;
/*  24:    */   public boolean field_146125_m;
/*  25:    */   protected boolean field_146123_n;
/*  26:    */   private static final String __OBFID = "CL_00000668";
/*  27:    */   
/*  28:    */   public NodusGuiButton(int par1, int par2, int par3, String par4Str)
/*  29:    */   {
/*  30: 31 */     this(par1, par2, par3, 200, 20, par4Str);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public NodusGuiButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
/*  34:    */   {
/*  35: 36 */     this.width = 200;
/*  36: 37 */     this.height = 20;
/*  37: 38 */     this.enabled = true;
/*  38: 39 */     this.field_146125_m = true;
/*  39: 40 */     this.id = par1;
/*  40: 41 */     this.xPosition = par2;
/*  41: 42 */     this.yPosition = par3;
/*  42: 43 */     this.width = par4;
/*  43: 44 */     this.height = par5;
/*  44: 45 */     this.displayString = par6Str;
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected int getHoverState(boolean p_146114_1_)
/*  48:    */   {
/*  49: 50 */     byte var2 = 1;
/*  50: 52 */     if (!this.enabled) {
/*  51: 54 */       var2 = 0;
/*  52: 56 */     } else if (p_146114_1_) {
/*  53: 58 */       var2 = 2;
/*  54:    */     }
/*  55: 61 */     return var2;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
/*  59:    */   {
/*  60: 69 */     if (this.field_146125_m)
/*  61:    */     {
/*  62: 71 */       p_146112_1_.getTextureManager().bindTexture(field_146122_a);
/*  63: 72 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  64: 73 */       boolean isOverButton = (p_146112_2_ >= this.xPosition) && (p_146112_3_ >= this.yPosition) && (p_146112_2_ < this.xPosition + this.width) && (p_146112_3_ < this.yPosition + this.height);
/*  65: 74 */       int var5 = getHoverState(this.field_146123_n);
/*  66: 76 */       if (isOverButton) {
/*  67: 78 */         NodusUtils.drawNodusButton(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, true);
/*  68:    */       } else {
/*  69: 81 */         NodusUtils.drawNodusButton(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, false);
/*  70:    */       }
/*  71: 84 */       mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
/*  72:    */       
/*  73: 86 */       drawCenteredString(Nodus.theNodus.getMinecraft().fontRenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, -1);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {}
/*  78:    */   
/*  79:    */   public void mouseReleased(int p_146118_1_, int p_146118_2_) {}
/*  80:    */   
/*  81:    */   public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
/*  82:    */   {
/*  83:106 */     return (this.enabled) && (this.field_146125_m) && (p_146116_2_ >= this.xPosition) && (p_146116_3_ >= this.yPosition) && (p_146116_2_ < this.xPosition + this.width) && (p_146116_3_ < this.yPosition + this.height);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean func_146115_a()
/*  87:    */   {
/*  88:111 */     return this.field_146123_n;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void func_146111_b(int p_146111_1_, int p_146111_2_) {}
/*  92:    */   
/*  93:    */   public void func_146113_a(SoundHandler p_146113_1_)
/*  94:    */   {
/*  95:118 */     p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/*  96:    */   }
/*  97:    */   
/*  98:    */   public int func_146117_b()
/*  99:    */   {
/* 100:123 */     return this.width;
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.NodusGuiButton
 * JD-Core Version:    0.7.0.1
 */