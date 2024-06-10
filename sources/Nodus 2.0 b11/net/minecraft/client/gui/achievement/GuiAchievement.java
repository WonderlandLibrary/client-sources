/*   1:    */ package net.minecraft.client.gui.achievement;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.Minecraft;
/*   4:    */ import net.minecraft.client.gui.FontRenderer;
/*   5:    */ import net.minecraft.client.gui.Gui;
/*   6:    */ import net.minecraft.client.gui.ScaledResolution;
/*   7:    */ import net.minecraft.client.renderer.RenderHelper;
/*   8:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.client.resources.I18n;
/*  11:    */ import net.minecraft.stats.Achievement;
/*  12:    */ import net.minecraft.util.IChatComponent;
/*  13:    */ import net.minecraft.util.ResourceLocation;
/*  14:    */ import org.lwjgl.opengl.GL11;
/*  15:    */ 
/*  16:    */ public class GuiAchievement
/*  17:    */   extends Gui
/*  18:    */ {
/*  19: 16 */   private static final ResourceLocation field_146261_a = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*  20:    */   private Minecraft field_146259_f;
/*  21:    */   private int field_146260_g;
/*  22:    */   private int field_146267_h;
/*  23:    */   private String field_146268_i;
/*  24:    */   private String field_146265_j;
/*  25:    */   private Achievement field_146266_k;
/*  26:    */   private long field_146263_l;
/*  27:    */   private RenderItem field_146264_m;
/*  28:    */   private boolean field_146262_n;
/*  29:    */   private static final String __OBFID = "CL_00000721";
/*  30:    */   
/*  31:    */   public GuiAchievement(Minecraft par1Minecraft)
/*  32:    */   {
/*  33: 30 */     this.field_146259_f = par1Minecraft;
/*  34: 31 */     this.field_146264_m = new RenderItem();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void func_146256_a(Achievement p_146256_1_)
/*  38:    */   {
/*  39: 36 */     this.field_146268_i = I18n.format("achievement.get", new Object[0]);
/*  40: 37 */     this.field_146265_j = p_146256_1_.func_150951_e().getUnformattedText();
/*  41: 38 */     this.field_146263_l = Minecraft.getSystemTime();
/*  42: 39 */     this.field_146266_k = p_146256_1_;
/*  43: 40 */     this.field_146262_n = false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void func_146255_b(Achievement p_146255_1_)
/*  47:    */   {
/*  48: 45 */     this.field_146268_i = p_146255_1_.func_150951_e().getUnformattedText();
/*  49: 46 */     this.field_146265_j = p_146255_1_.getDescription();
/*  50: 47 */     this.field_146263_l = (Minecraft.getSystemTime() + 2500L);
/*  51: 48 */     this.field_146266_k = p_146255_1_;
/*  52: 49 */     this.field_146262_n = true;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private void func_146258_c()
/*  56:    */   {
/*  57: 54 */     GL11.glViewport(0, 0, this.field_146259_f.displayWidth, this.field_146259_f.displayHeight);
/*  58: 55 */     GL11.glMatrixMode(5889);
/*  59: 56 */     GL11.glLoadIdentity();
/*  60: 57 */     GL11.glMatrixMode(5888);
/*  61: 58 */     GL11.glLoadIdentity();
/*  62: 59 */     this.field_146260_g = this.field_146259_f.displayWidth;
/*  63: 60 */     this.field_146267_h = this.field_146259_f.displayHeight;
/*  64: 61 */     ScaledResolution var1 = new ScaledResolution(this.field_146259_f.gameSettings, this.field_146259_f.displayWidth, this.field_146259_f.displayHeight);
/*  65: 62 */     this.field_146260_g = var1.getScaledWidth();
/*  66: 63 */     this.field_146267_h = var1.getScaledHeight();
/*  67: 64 */     GL11.glClear(256);
/*  68: 65 */     GL11.glMatrixMode(5889);
/*  69: 66 */     GL11.glLoadIdentity();
/*  70: 67 */     GL11.glOrtho(0.0D, this.field_146260_g, this.field_146267_h, 0.0D, 1000.0D, 3000.0D);
/*  71: 68 */     GL11.glMatrixMode(5888);
/*  72: 69 */     GL11.glLoadIdentity();
/*  73: 70 */     GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void func_146254_a()
/*  77:    */   {
/*  78: 75 */     if ((this.field_146266_k != null) && (this.field_146263_l != 0L) && (Minecraft.getMinecraft().thePlayer != null))
/*  79:    */     {
/*  80: 77 */       double var1 = (Minecraft.getSystemTime() - this.field_146263_l) / 3000.0D;
/*  81: 79 */       if (!this.field_146262_n)
/*  82:    */       {
/*  83: 81 */         if ((var1 < 0.0D) || (var1 > 1.0D)) {
/*  84: 83 */           this.field_146263_l = 0L;
/*  85:    */         }
/*  86:    */       }
/*  87: 87 */       else if (var1 > 0.5D) {
/*  88: 89 */         var1 = 0.5D;
/*  89:    */       }
/*  90: 92 */       func_146258_c();
/*  91: 93 */       GL11.glDisable(2929);
/*  92: 94 */       GL11.glDepthMask(false);
/*  93: 95 */       double var3 = var1 * 2.0D;
/*  94: 97 */       if (var3 > 1.0D) {
/*  95: 99 */         var3 = 2.0D - var3;
/*  96:    */       }
/*  97:102 */       var3 *= 4.0D;
/*  98:103 */       var3 = 1.0D - var3;
/*  99:105 */       if (var3 < 0.0D) {
/* 100:107 */         var3 = 0.0D;
/* 101:    */       }
/* 102:110 */       var3 *= var3;
/* 103:111 */       var3 *= var3;
/* 104:112 */       int var5 = this.field_146260_g - 160;
/* 105:113 */       int var6 = 0 - (int)(var3 * 36.0D);
/* 106:114 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 107:115 */       GL11.glEnable(3553);
/* 108:116 */       this.field_146259_f.getTextureManager().bindTexture(field_146261_a);
/* 109:117 */       GL11.glDisable(2896);
/* 110:118 */       drawTexturedModalRect(var5, var6, 96, 202, 160, 32);
/* 111:120 */       if (this.field_146262_n)
/* 112:    */       {
/* 113:122 */         this.field_146259_f.fontRenderer.drawSplitString(this.field_146265_j, var5 + 30, var6 + 7, 120, -1);
/* 114:    */       }
/* 115:    */       else
/* 116:    */       {
/* 117:126 */         this.field_146259_f.fontRenderer.drawString(this.field_146268_i, var5 + 30, var6 + 7, -256);
/* 118:127 */         this.field_146259_f.fontRenderer.drawString(this.field_146265_j, var5 + 30, var6 + 18, -1);
/* 119:    */       }
/* 120:130 */       RenderHelper.enableGUIStandardItemLighting();
/* 121:131 */       GL11.glDisable(2896);
/* 122:132 */       GL11.glEnable(32826);
/* 123:133 */       GL11.glEnable(2903);
/* 124:134 */       GL11.glEnable(2896);
/* 125:135 */       this.field_146264_m.renderItemAndEffectIntoGUI(this.field_146259_f.fontRenderer, this.field_146259_f.getTextureManager(), this.field_146266_k.theItemStack, var5 + 8, var6 + 8);
/* 126:136 */       GL11.glDisable(2896);
/* 127:137 */       GL11.glDepthMask(true);
/* 128:138 */       GL11.glEnable(2929);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void func_146257_b()
/* 133:    */   {
/* 134:144 */     this.field_146266_k = null;
/* 135:145 */     this.field_146263_l = 0L;
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.achievement.GuiAchievement
 * JD-Core Version:    0.7.0.1
 */