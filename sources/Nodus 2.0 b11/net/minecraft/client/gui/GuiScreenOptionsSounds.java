/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*   7:    */ import net.minecraft.client.audio.SoundCategory;
/*   8:    */ import net.minecraft.client.audio.SoundHandler;
/*   9:    */ import net.minecraft.client.resources.I18n;
/*  10:    */ import net.minecraft.client.settings.GameSettings;
/*  11:    */ import net.minecraft.util.ResourceLocation;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ public class GuiScreenOptionsSounds
/*  15:    */   extends GuiScreen
/*  16:    */ {
/*  17:    */   private final GuiScreen field_146505_f;
/*  18:    */   private final GameSettings field_146506_g;
/*  19: 18 */   protected String field_146507_a = "Options";
/*  20:    */   private String field_146508_h;
/*  21:    */   private static final String __OBFID = "CL_00000716";
/*  22:    */   
/*  23:    */   public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_)
/*  24:    */   {
/*  25: 24 */     this.field_146505_f = p_i45025_1_;
/*  26: 25 */     this.field_146506_g = p_i45025_2_;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void initGui()
/*  30:    */   {
/*  31: 33 */     byte var1 = 0;
/*  32: 34 */     this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
/*  33: 35 */     this.field_146508_h = I18n.format("options.off", new Object[0]);
/*  34: 36 */     this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), width / 2 - 155 + var1 % 2 * 160, height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
/*  35: 37 */     int var6 = var1 + 2;
/*  36: 38 */     SoundCategory[] var2 = SoundCategory.values();
/*  37: 39 */     int var3 = var2.length;
/*  38: 41 */     for (int var4 = 0; var4 < var3; var4++)
/*  39:    */     {
/*  40: 43 */       SoundCategory var5 = var2[var4];
/*  41: 45 */       if (var5 != SoundCategory.MASTER)
/*  42:    */       {
/*  43: 47 */         this.buttonList.add(new Button(var5.getCategoryId(), width / 2 - 155 + var6 % 2 * 160, height / 6 - 12 + 24 * (var6 >> 1), var5, false));
/*  44: 48 */         var6++;
/*  45:    */       }
/*  46:    */     }
/*  47: 52 */     this.buttonList.add(new NodusGuiButton(200, width / 2 - 100, height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  51:    */   {
/*  52: 57 */     if (p_146284_1_.enabled) {
/*  53: 59 */       if (p_146284_1_.id == 200)
/*  54:    */       {
/*  55: 61 */         this.mc.gameSettings.saveOptions();
/*  56: 62 */         this.mc.displayGuiScreen(this.field_146505_f);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void drawScreen(int par1, int par2, float par3)
/*  62:    */   {
/*  63: 72 */     drawDefaultBackground();
/*  64: 73 */     drawCenteredString(this.fontRendererObj, this.field_146507_a, width / 2, 15, 16777215);
/*  65: 74 */     super.drawScreen(par1, par2, par3);
/*  66:    */   }
/*  67:    */   
/*  68:    */   protected String func_146504_a(SoundCategory p_146504_1_)
/*  69:    */   {
/*  70: 79 */     float var2 = this.field_146506_g.getSoundLevel(p_146504_1_);
/*  71: 80 */     return (int)(var2 * 100.0F) + "%";
/*  72:    */   }
/*  73:    */   
/*  74:    */   class Button
/*  75:    */     extends NodusGuiButton
/*  76:    */   {
/*  77:    */     private final SoundCategory field_146153_r;
/*  78:    */     private final String field_146152_s;
/*  79: 87 */     public float field_146156_o = 1.0F;
/*  80:    */     public boolean field_146155_p;
/*  81:    */     private static final String __OBFID = "CL_00000717";
/*  82:    */     
/*  83:    */     public Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_)
/*  84:    */     {
/*  85: 93 */       super(p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
/*  86: 94 */       this.field_146153_r = p_i45024_5_;
/*  87: 95 */       this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
/*  88: 96 */       this.displayString = (this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(p_i45024_5_));
/*  89: 97 */       this.field_146156_o = GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(p_i45024_5_);
/*  90:    */     }
/*  91:    */     
/*  92:    */     protected int getHoverState(boolean p_146114_1_)
/*  93:    */     {
/*  94:102 */       return 0;
/*  95:    */     }
/*  96:    */     
/*  97:    */     protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_)
/*  98:    */     {
/*  99:107 */       if (this.field_146125_m)
/* 100:    */       {
/* 101:109 */         if (this.field_146155_p)
/* 102:    */         {
/* 103:111 */           this.field_146156_o = ((p_146119_2_ - (this.xPosition + 4)) / (this.width - 8));
/* 104:113 */           if (this.field_146156_o < 0.0F) {
/* 105:115 */             this.field_146156_o = 0.0F;
/* 106:    */           }
/* 107:118 */           if (this.field_146156_o > 1.0F) {
/* 108:120 */             this.field_146156_o = 1.0F;
/* 109:    */           }
/* 110:123 */           p_146119_1_.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 111:124 */           p_146119_1_.gameSettings.saveOptions();
/* 112:125 */           this.displayString = (this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.field_146153_r));
/* 113:    */         }
/* 114:128 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 115:129 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 116:130 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/* 117:    */       }
/* 118:    */     }
/* 119:    */     
/* 120:    */     public boolean mousePressed(Minecraft p_146116_1_, int p_146116_2_, int p_146116_3_)
/* 121:    */     {
/* 122:136 */       if (super.mousePressed(p_146116_1_, p_146116_2_, p_146116_3_))
/* 123:    */       {
/* 124:138 */         this.field_146156_o = ((p_146116_2_ - (this.xPosition + 4)) / (this.width - 8));
/* 125:140 */         if (this.field_146156_o < 0.0F) {
/* 126:142 */           this.field_146156_o = 0.0F;
/* 127:    */         }
/* 128:145 */         if (this.field_146156_o > 1.0F) {
/* 129:147 */           this.field_146156_o = 1.0F;
/* 130:    */         }
/* 131:150 */         p_146116_1_.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 132:151 */         p_146116_1_.gameSettings.saveOptions();
/* 133:152 */         this.displayString = (this.field_146152_s + ": " + GuiScreenOptionsSounds.this.func_146504_a(this.field_146153_r));
/* 134:153 */         this.field_146155_p = true;
/* 135:154 */         return true;
/* 136:    */       }
/* 137:158 */       return false;
/* 138:    */     }
/* 139:    */     
/* 140:    */     public void func_146113_a(SoundHandler p_146113_1_) {}
/* 141:    */     
/* 142:    */     public void mouseReleased(int p_146118_1_, int p_146118_2_)
/* 143:    */     {
/* 144:166 */       if (this.field_146155_p)
/* 145:    */       {
/* 146:168 */         if (this.field_146153_r == SoundCategory.MASTER) {
/* 147:170 */           float f = 1.0F;
/* 148:    */         } else {
/* 149:174 */           GuiScreenOptionsSounds.this.field_146506_g.getSoundLevel(this.field_146153_r);
/* 150:    */         }
/* 151:177 */         GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
/* 152:    */       }
/* 153:180 */       this.field_146155_p = false;
/* 154:    */     }
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenOptionsSounds
 * JD-Core Version:    0.7.0.1
 */