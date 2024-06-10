/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.settings.GameSettings;
/*   6:    */ import net.minecraft.client.settings.GameSettings.Options;
/*   7:    */ import org.lwjgl.opengl.GL11;
/*   8:    */ 
/*   9:    */ public class GuiOptionSlider
/*  10:    */   extends NodusGuiButton
/*  11:    */ {
/*  12:    */   private float field_146134_p;
/*  13:    */   public boolean field_146135_o;
/*  14:    */   private GameSettings.Options field_146133_q;
/*  15:    */   private final float field_146132_r;
/*  16:    */   private final float field_146131_s;
/*  17:    */   private static final String __OBFID = "CL_00000680";
/*  18:    */   
/*  19:    */   public GuiOptionSlider(int p_i45016_1_, int p_i45016_2_, int p_i45016_3_, GameSettings.Options p_i45016_4_)
/*  20:    */   {
/*  21: 20 */     this(p_i45016_1_, p_i45016_2_, p_i45016_3_, p_i45016_4_, 0.0F, 1.0F);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public GuiOptionSlider(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, GameSettings.Options p_i45017_4_, float p_i45017_5_, float p_i45017_6_)
/*  25:    */   {
/*  26: 25 */     super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
/*  27: 26 */     this.field_146134_p = 1.0F;
/*  28: 27 */     this.field_146133_q = p_i45017_4_;
/*  29: 28 */     this.field_146132_r = p_i45017_5_;
/*  30: 29 */     this.field_146131_s = p_i45017_6_;
/*  31: 30 */     Minecraft var7 = Minecraft.getMinecraft();
/*  32: 31 */     this.field_146134_p = p_i45017_4_.normalizeValue(var7.gameSettings.getOptionFloatValue(p_i45017_4_));
/*  33: 32 */     this.displayString = var7.gameSettings.getKeyBinding(p_i45017_4_);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected int getHoverState(boolean p_146114_1_)
/*  37:    */   {
/*  38: 37 */     return 0;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_)
/*  42:    */   {
/*  43: 45 */     if (this.field_146125_m)
/*  44:    */     {
/*  45: 47 */       if (this.field_146135_o)
/*  46:    */       {
/*  47: 49 */         this.field_146134_p = ((p_146119_2_ - (this.xPosition + 4)) / (this.width - 8));
/*  48: 51 */         if (this.field_146134_p < 0.0F) {
/*  49: 53 */           this.field_146134_p = 0.0F;
/*  50:    */         }
/*  51: 56 */         if (this.field_146134_p > 1.0F) {
/*  52: 58 */           this.field_146134_p = 1.0F;
/*  53:    */         }
/*  54: 61 */         float var4 = this.field_146133_q.denormalizeValue(this.field_146134_p);
/*  55: 62 */         p_146119_1_.gameSettings.setOptionFloatValue(this.field_146133_q, var4);
/*  56: 63 */         this.field_146134_p = this.field_146133_q.normalizeValue(var4);
/*  57: 64 */         this.displayString = p_146119_1_.gameSettings.getKeyBinding(this.field_146133_q);
/*  58:    */       }
/*  59: 67 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  60: 68 */       drawTexturedModalRect(this.xPosition + (int)(this.field_146134_p * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/*  61: 69 */       drawTexturedModalRect(this.xPosition + (int)(this.field_146134_p * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
/*  66:    */   {
/*  67: 79 */     if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_))
/*  68:    */     {
/*  69: 81 */       this.field_146134_p = ((p_146116_2_ - (this.xPosition + 4)) / (this.width - 8));
/*  70: 83 */       if (this.field_146134_p < 0.0F) {
/*  71: 85 */         this.field_146134_p = 0.0F;
/*  72:    */       }
/*  73: 88 */       if (this.field_146134_p > 1.0F) {
/*  74: 90 */         this.field_146134_p = 1.0F;
/*  75:    */       }
/*  76: 93 */       p_146116_1_.gameSettings.setOptionFloatValue(this.field_146133_q, this.field_146133_q.denormalizeValue(this.field_146134_p));
/*  77: 94 */       this.displayString = p_146116_1_.gameSettings.getKeyBinding(this.field_146133_q);
/*  78: 95 */       this.field_146135_o = true;
/*  79: 96 */       return true;
/*  80:    */     }
/*  81:100 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void mouseReleased(int p_146118_1_, int p_146118_2_)
/*  85:    */   {
/*  86:109 */     this.field_146135_o = false;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiOptionSlider
 * JD-Core Version:    0.7.0.1
 */