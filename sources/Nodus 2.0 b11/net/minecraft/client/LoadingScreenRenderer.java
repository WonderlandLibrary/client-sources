/*   1:    */ package net.minecraft.client;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.gui.FontRenderer;
/*   4:    */ import net.minecraft.client.gui.Gui;
/*   5:    */ import net.minecraft.client.gui.ScaledResolution;
/*   6:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   7:    */ import net.minecraft.client.renderer.Tessellator;
/*   8:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   9:    */ import net.minecraft.client.shader.Framebuffer;
/*  10:    */ import net.minecraft.util.IProgressUpdate;
/*  11:    */ import net.minecraft.util.MinecraftError;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ public class LoadingScreenRenderer
/*  15:    */   implements IProgressUpdate
/*  16:    */ {
/*  17: 14 */   private String field_73727_a = "";
/*  18:    */   private Minecraft mc;
/*  19: 22 */   private String currentlyDisplayedText = "";
/*  20: 23 */   private long field_73723_d = Minecraft.getSystemTime();
/*  21:    */   private boolean field_73724_e;
/*  22:    */   private ScaledResolution field_146587_f;
/*  23:    */   private Framebuffer field_146588_g;
/*  24:    */   private static final String __OBFID = "CL_00000655";
/*  25:    */   
/*  26:    */   public LoadingScreenRenderer(Minecraft par1Minecraft)
/*  27:    */   {
/*  28: 31 */     this.mc = par1Minecraft;
/*  29: 32 */     this.field_146587_f = new ScaledResolution(par1Minecraft.gameSettings, par1Minecraft.displayWidth, par1Minecraft.displayHeight);
/*  30: 33 */     this.field_146588_g = new Framebuffer(this.field_146587_f.getScaledWidth(), this.field_146587_f.getScaledHeight(), false);
/*  31: 34 */     this.field_146588_g.setFramebufferFilter(9728);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void resetProgressAndMessage(String par1Str)
/*  35:    */   {
/*  36: 43 */     this.field_73724_e = false;
/*  37: 44 */     func_73722_d(par1Str);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void displayProgressMessage(String par1Str)
/*  41:    */   {
/*  42: 52 */     this.field_73724_e = true;
/*  43: 53 */     func_73722_d(par1Str);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void func_73722_d(String par1Str)
/*  47:    */   {
/*  48: 58 */     this.currentlyDisplayedText = par1Str;
/*  49: 60 */     if (!this.mc.running)
/*  50:    */     {
/*  51: 62 */       if (!this.field_73724_e) {
/*  52: 64 */         throw new MinecraftError();
/*  53:    */       }
/*  54:    */     }
/*  55:    */     else
/*  56:    */     {
/*  57: 69 */       GL11.glClear(256);
/*  58: 70 */       GL11.glMatrixMode(5889);
/*  59: 71 */       GL11.glLoadIdentity();
/*  60: 73 */       if (OpenGlHelper.isFramebufferEnabled())
/*  61:    */       {
/*  62: 75 */         int var2 = this.field_146587_f.getScaleFactor();
/*  63: 76 */         GL11.glOrtho(0.0D, this.field_146587_f.getScaledWidth() * var2, this.field_146587_f.getScaledHeight() * var2, 0.0D, 100.0D, 300.0D);
/*  64:    */       }
/*  65:    */       else
/*  66:    */       {
/*  67: 80 */         ScaledResolution var3 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/*  68: 81 */         GL11.glOrtho(0.0D, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/*  69:    */       }
/*  70: 84 */       GL11.glMatrixMode(5888);
/*  71: 85 */       GL11.glLoadIdentity();
/*  72: 86 */       GL11.glTranslatef(0.0F, 0.0F, -200.0F);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void resetProgresAndWorkingMessage(String par1Str)
/*  77:    */   {
/*  78: 95 */     if (!this.mc.running)
/*  79:    */     {
/*  80: 97 */       if (!this.field_73724_e) {
/*  81: 99 */         throw new MinecraftError();
/*  82:    */       }
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:104 */       this.field_73723_d = 0L;
/*  87:105 */       this.field_73727_a = par1Str;
/*  88:106 */       setLoadingProgress(-1);
/*  89:107 */       this.field_73723_d = 0L;
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setLoadingProgress(int par1)
/*  94:    */   {
/*  95:116 */     if (!this.mc.running)
/*  96:    */     {
/*  97:118 */       if (!this.field_73724_e) {
/*  98:120 */         throw new MinecraftError();
/*  99:    */       }
/* 100:    */     }
/* 101:    */     else
/* 102:    */     {
/* 103:125 */       long var2 = Minecraft.getSystemTime();
/* 104:127 */       if (var2 - this.field_73723_d >= 100L)
/* 105:    */       {
/* 106:129 */         this.field_73723_d = var2;
/* 107:130 */         ScaledResolution var4 = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
/* 108:131 */         int var5 = var4.getScaleFactor();
/* 109:132 */         int var6 = var4.getScaledWidth();
/* 110:133 */         int var7 = var4.getScaledHeight();
/* 111:135 */         if (OpenGlHelper.isFramebufferEnabled()) {
/* 112:137 */           this.field_146588_g.framebufferClear();
/* 113:    */         } else {
/* 114:141 */           GL11.glClear(256);
/* 115:    */         }
/* 116:144 */         this.field_146588_g.bindFramebuffer(true);
/* 117:145 */         GL11.glMatrixMode(5889);
/* 118:146 */         GL11.glLoadIdentity();
/* 119:148 */         if (OpenGlHelper.isFramebufferEnabled()) {
/* 120:150 */           GL11.glOrtho(0.0D, var6, var7, 0.0D, 100.0D, 300.0D);
/* 121:    */         } else {
/* 122:154 */           GL11.glOrtho(0.0D, var4.getScaledWidth_double(), var4.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/* 123:    */         }
/* 124:157 */         GL11.glMatrixMode(5888);
/* 125:158 */         GL11.glLoadIdentity();
/* 126:159 */         GL11.glTranslatef(0.0F, 0.0F, -200.0F);
/* 127:161 */         if (!OpenGlHelper.isFramebufferEnabled()) {
/* 128:163 */           GL11.glClear(16640);
/* 129:    */         }
/* 130:166 */         Tessellator var8 = Tessellator.instance;
/* 131:167 */         this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 132:168 */         float var9 = 32.0F;
/* 133:169 */         var8.startDrawingQuads();
/* 134:170 */         var8.setColorOpaque_I(4210752);
/* 135:171 */         var8.addVertexWithUV(0.0D, var7, 0.0D, 0.0D, var7 / var9);
/* 136:172 */         var8.addVertexWithUV(var6, var7, 0.0D, var6 / var9, var7 / var9);
/* 137:173 */         var8.addVertexWithUV(var6, 0.0D, 0.0D, var6 / var9, 0.0D);
/* 138:174 */         var8.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/* 139:175 */         var8.draw();
/* 140:177 */         if (par1 >= 0)
/* 141:    */         {
/* 142:179 */           byte var10 = 100;
/* 143:180 */           byte var11 = 2;
/* 144:181 */           int var12 = var6 / 2 - var10 / 2;
/* 145:182 */           int var13 = var7 / 2 + 16;
/* 146:183 */           GL11.glDisable(3553);
/* 147:184 */           var8.startDrawingQuads();
/* 148:185 */           var8.setColorOpaque_I(8421504);
/* 149:186 */           var8.addVertex(var12, var13, 0.0D);
/* 150:187 */           var8.addVertex(var12, var13 + var11, 0.0D);
/* 151:188 */           var8.addVertex(var12 + var10, var13 + var11, 0.0D);
/* 152:189 */           var8.addVertex(var12 + var10, var13, 0.0D);
/* 153:190 */           var8.setColorOpaque_I(8454016);
/* 154:191 */           var8.addVertex(var12, var13, 0.0D);
/* 155:192 */           var8.addVertex(var12, var13 + var11, 0.0D);
/* 156:193 */           var8.addVertex(var12 + par1, var13 + var11, 0.0D);
/* 157:194 */           var8.addVertex(var12 + par1, var13, 0.0D);
/* 158:195 */           var8.draw();
/* 159:196 */           GL11.glEnable(3553);
/* 160:    */         }
/* 161:199 */         GL11.glEnable(3042);
/* 162:200 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 163:201 */         this.mc.fontRenderer.drawStringWithShadow(this.currentlyDisplayedText, (var6 - this.mc.fontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2, var7 / 2 - 4 - 16, 16777215);
/* 164:202 */         this.mc.fontRenderer.drawStringWithShadow(this.field_73727_a, (var6 - this.mc.fontRenderer.getStringWidth(this.field_73727_a)) / 2, var7 / 2 - 4 + 8, 16777215);
/* 165:203 */         this.field_146588_g.unbindFramebuffer();
/* 166:205 */         if (OpenGlHelper.isFramebufferEnabled()) {
/* 167:207 */           this.field_146588_g.framebufferRender(var6 * var5, var7 * var5);
/* 168:    */         }
/* 169:210 */         this.mc.func_147120_f();
/* 170:    */         try
/* 171:    */         {
/* 172:214 */           Thread.yield();
/* 173:    */         }
/* 174:    */         catch (Exception localException) {}
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void func_146586_a() {}
/* 180:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.LoadingScreenRenderer
 * JD-Core Version:    0.7.0.1
 */