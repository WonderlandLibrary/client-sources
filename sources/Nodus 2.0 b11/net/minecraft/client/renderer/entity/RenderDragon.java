/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.client.model.ModelBase;
/*   5:    */ import net.minecraft.client.model.ModelDragon;
/*   6:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   7:    */ import net.minecraft.client.renderer.RenderHelper;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.entity.Entity;
/*  10:    */ import net.minecraft.entity.EntityLiving;
/*  11:    */ import net.minecraft.entity.EntityLivingBase;
/*  12:    */ import net.minecraft.entity.boss.BossStatus;
/*  13:    */ import net.minecraft.entity.boss.EntityDragon;
/*  14:    */ import net.minecraft.entity.item.EntityEnderCrystal;
/*  15:    */ import net.minecraft.util.MathHelper;
/*  16:    */ import net.minecraft.util.ResourceLocation;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public class RenderDragon
/*  20:    */   extends RenderLiving
/*  21:    */ {
/*  22: 19 */   private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
/*  23: 20 */   private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
/*  24: 21 */   private static final ResourceLocation enderDragonEyesTextures = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
/*  25: 22 */   private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
/*  26:    */   protected ModelDragon modelDragon;
/*  27:    */   private static final String __OBFID = "CL_00000988";
/*  28:    */   
/*  29:    */   public RenderDragon()
/*  30:    */   {
/*  31: 30 */     super(new ModelDragon(0.0F), 0.5F);
/*  32: 31 */     this.modelDragon = ((ModelDragon)this.mainModel);
/*  33: 32 */     setRenderPassModel(this.mainModel);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected void rotateCorpse(EntityDragon par1EntityDragon, float par2, float par3, float par4)
/*  37:    */   {
/*  38: 37 */     float var5 = (float)par1EntityDragon.getMovementOffsets(7, par4)[0];
/*  39: 38 */     float var6 = (float)(par1EntityDragon.getMovementOffsets(5, par4)[1] - par1EntityDragon.getMovementOffsets(10, par4)[1]);
/*  40: 39 */     GL11.glRotatef(-var5, 0.0F, 1.0F, 0.0F);
/*  41: 40 */     GL11.glRotatef(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
/*  42: 41 */     GL11.glTranslatef(0.0F, 0.0F, 1.0F);
/*  43: 43 */     if (par1EntityDragon.deathTime > 0)
/*  44:    */     {
/*  45: 45 */       float var7 = (par1EntityDragon.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
/*  46: 46 */       var7 = MathHelper.sqrt_float(var7);
/*  47: 48 */       if (var7 > 1.0F) {
/*  48: 50 */         var7 = 1.0F;
/*  49:    */       }
/*  50: 53 */       GL11.glRotatef(var7 * getDeathMaxRotation(par1EntityDragon), 0.0F, 0.0F, 1.0F);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void renderModel(EntityDragon par1EntityDragon, float par2, float par3, float par4, float par5, float par6, float par7)
/*  55:    */   {
/*  56: 62 */     if (par1EntityDragon.deathTicks > 0)
/*  57:    */     {
/*  58: 64 */       float var8 = par1EntityDragon.deathTicks / 200.0F;
/*  59: 65 */       GL11.glDepthFunc(515);
/*  60: 66 */       GL11.glEnable(3008);
/*  61: 67 */       GL11.glAlphaFunc(516, var8);
/*  62: 68 */       bindTexture(enderDragonExplodingTextures);
/*  63: 69 */       this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
/*  64: 70 */       GL11.glAlphaFunc(516, 0.1F);
/*  65: 71 */       GL11.glDepthFunc(514);
/*  66:    */     }
/*  67: 74 */     bindEntityTexture(par1EntityDragon);
/*  68: 75 */     this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
/*  69: 77 */     if (par1EntityDragon.hurtTime > 0)
/*  70:    */     {
/*  71: 79 */       GL11.glDepthFunc(514);
/*  72: 80 */       GL11.glDisable(3553);
/*  73: 81 */       GL11.glEnable(3042);
/*  74: 82 */       GL11.glBlendFunc(770, 771);
/*  75: 83 */       GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
/*  76: 84 */       this.mainModel.render(par1EntityDragon, par2, par3, par4, par5, par6, par7);
/*  77: 85 */       GL11.glEnable(3553);
/*  78: 86 */       GL11.glDisable(3042);
/*  79: 87 */       GL11.glDepthFunc(515);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void doRender(EntityDragon par1EntityDragon, double par2, double par4, double par6, float par8, float par9)
/*  84:    */   {
/*  85: 99 */     BossStatus.setBossStatus(par1EntityDragon, false);
/*  86:100 */     super.doRender(par1EntityDragon, par2, par4, par6, par8, par9);
/*  87:102 */     if (par1EntityDragon.healingEnderCrystal != null)
/*  88:    */     {
/*  89:104 */       float var10 = par1EntityDragon.healingEnderCrystal.innerRotation + par9;
/*  90:105 */       float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
/*  91:106 */       var11 = (var11 * var11 + var11) * 0.2F;
/*  92:107 */       float var12 = (float)(par1EntityDragon.healingEnderCrystal.posX - par1EntityDragon.posX - (par1EntityDragon.prevPosX - par1EntityDragon.posX) * (1.0F - par9));
/*  93:108 */       float var13 = (float)(var11 + par1EntityDragon.healingEnderCrystal.posY - 1.0D - par1EntityDragon.posY - (par1EntityDragon.prevPosY - par1EntityDragon.posY) * (1.0F - par9));
/*  94:109 */       float var14 = (float)(par1EntityDragon.healingEnderCrystal.posZ - par1EntityDragon.posZ - (par1EntityDragon.prevPosZ - par1EntityDragon.posZ) * (1.0F - par9));
/*  95:110 */       float var15 = MathHelper.sqrt_float(var12 * var12 + var14 * var14);
/*  96:111 */       float var16 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14);
/*  97:112 */       GL11.glPushMatrix();
/*  98:113 */       GL11.glTranslatef((float)par2, (float)par4 + 2.0F, (float)par6);
/*  99:114 */       GL11.glRotatef((float)-Math.atan2(var14, var12) * 180.0F / 3.141593F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 100:115 */       GL11.glRotatef((float)-Math.atan2(var15, var13) * 180.0F / 3.141593F - 90.0F, 1.0F, 0.0F, 0.0F);
/* 101:116 */       Tessellator var17 = Tessellator.instance;
/* 102:117 */       RenderHelper.disableStandardItemLighting();
/* 103:118 */       GL11.glDisable(2884);
/* 104:119 */       bindTexture(enderDragonCrystalBeamTextures);
/* 105:120 */       GL11.glShadeModel(7425);
/* 106:121 */       float var18 = 0.0F - (par1EntityDragon.ticksExisted + par9) * 0.01F;
/* 107:122 */       float var19 = MathHelper.sqrt_float(var12 * var12 + var13 * var13 + var14 * var14) / 32.0F - (par1EntityDragon.ticksExisted + par9) * 0.01F;
/* 108:123 */       var17.startDrawing(5);
/* 109:124 */       byte var20 = 8;
/* 110:126 */       for (int var21 = 0; var21 <= var20; var21++)
/* 111:    */       {
/* 112:128 */         float var22 = MathHelper.sin(var21 % var20 * 3.141593F * 2.0F / var20) * 0.75F;
/* 113:129 */         float var23 = MathHelper.cos(var21 % var20 * 3.141593F * 2.0F / var20) * 0.75F;
/* 114:130 */         float var24 = var21 % var20 * 1.0F / var20;
/* 115:131 */         var17.setColorOpaque_I(0);
/* 116:132 */         var17.addVertexWithUV(var22 * 0.2F, var23 * 0.2F, 0.0D, var24, var19);
/* 117:133 */         var17.setColorOpaque_I(16777215);
/* 118:134 */         var17.addVertexWithUV(var22, var23, var16, var24, var18);
/* 119:    */       }
/* 120:137 */       var17.draw();
/* 121:138 */       GL11.glEnable(2884);
/* 122:139 */       GL11.glShadeModel(7424);
/* 123:140 */       RenderHelper.enableStandardItemLighting();
/* 124:141 */       GL11.glPopMatrix();
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected ResourceLocation getEntityTexture(EntityDragon par1EntityDragon)
/* 129:    */   {
/* 130:150 */     return enderDragonTextures;
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected void renderEquippedItems(EntityDragon par1EntityDragon, float par2)
/* 134:    */   {
/* 135:155 */     super.renderEquippedItems(par1EntityDragon, par2);
/* 136:156 */     Tessellator var3 = Tessellator.instance;
/* 137:158 */     if (par1EntityDragon.deathTicks > 0)
/* 138:    */     {
/* 139:160 */       RenderHelper.disableStandardItemLighting();
/* 140:161 */       float var4 = (par1EntityDragon.deathTicks + par2) / 200.0F;
/* 141:162 */       float var5 = 0.0F;
/* 142:164 */       if (var4 > 0.8F) {
/* 143:166 */         var5 = (var4 - 0.8F) / 0.2F;
/* 144:    */       }
/* 145:169 */       Random var6 = new Random(432L);
/* 146:170 */       GL11.glDisable(3553);
/* 147:171 */       GL11.glShadeModel(7425);
/* 148:172 */       GL11.glEnable(3042);
/* 149:173 */       GL11.glBlendFunc(770, 1);
/* 150:174 */       GL11.glDisable(3008);
/* 151:175 */       GL11.glEnable(2884);
/* 152:176 */       GL11.glDepthMask(false);
/* 153:177 */       GL11.glPushMatrix();
/* 154:178 */       GL11.glTranslatef(0.0F, -1.0F, -2.0F);
/* 155:180 */       for (int var7 = 0; var7 < (var4 + var4 * var4) / 2.0F * 60.0F; var7++)
/* 156:    */       {
/* 157:182 */         GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 158:183 */         GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 159:184 */         GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
/* 160:185 */         GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
/* 161:186 */         GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
/* 162:187 */         GL11.glRotatef(var6.nextFloat() * 360.0F + var4 * 90.0F, 0.0F, 0.0F, 1.0F);
/* 163:188 */         var3.startDrawing(6);
/* 164:189 */         float var8 = var6.nextFloat() * 20.0F + 5.0F + var5 * 10.0F;
/* 165:190 */         float var9 = var6.nextFloat() * 2.0F + 1.0F + var5 * 2.0F;
/* 166:191 */         var3.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - var5)));
/* 167:192 */         var3.addVertex(0.0D, 0.0D, 0.0D);
/* 168:193 */         var3.setColorRGBA_I(16711935, 0);
/* 169:194 */         var3.addVertex(-0.866D * var9, var8, -0.5F * var9);
/* 170:195 */         var3.addVertex(0.866D * var9, var8, -0.5F * var9);
/* 171:196 */         var3.addVertex(0.0D, var8, 1.0F * var9);
/* 172:197 */         var3.addVertex(-0.866D * var9, var8, -0.5F * var9);
/* 173:198 */         var3.draw();
/* 174:    */       }
/* 175:201 */       GL11.glPopMatrix();
/* 176:202 */       GL11.glDepthMask(true);
/* 177:203 */       GL11.glDisable(2884);
/* 178:204 */       GL11.glDisable(3042);
/* 179:205 */       GL11.glShadeModel(7424);
/* 180:206 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 181:207 */       GL11.glEnable(3553);
/* 182:208 */       GL11.glEnable(3008);
/* 183:209 */       RenderHelper.enableStandardItemLighting();
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected int shouldRenderPass(EntityDragon par1EntityDragon, int par2, float par3)
/* 188:    */   {
/* 189:218 */     if (par2 == 1) {
/* 190:220 */       GL11.glDepthFunc(515);
/* 191:    */     }
/* 192:223 */     if (par2 != 0) {
/* 193:225 */       return -1;
/* 194:    */     }
/* 195:229 */     bindTexture(enderDragonEyesTextures);
/* 196:230 */     GL11.glEnable(3042);
/* 197:231 */     GL11.glDisable(3008);
/* 198:232 */     GL11.glBlendFunc(1, 1);
/* 199:233 */     GL11.glDisable(2896);
/* 200:234 */     GL11.glDepthFunc(514);
/* 201:235 */     char var4 = 61680;
/* 202:236 */     int var5 = var4 % 65536;
/* 203:237 */     int var6 = var4 / 65536;
/* 204:238 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0F, var6 / 1.0F);
/* 205:239 */     GL11.glEnable(2896);
/* 206:240 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 207:241 */     return 1;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
/* 211:    */   {
/* 212:253 */     doRender((EntityDragon)par1EntityLiving, par2, par4, par6, par8, par9);
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 216:    */   {
/* 217:261 */     return shouldRenderPass((EntityDragon)par1EntityLivingBase, par2, par3);
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 221:    */   {
/* 222:266 */     renderEquippedItems((EntityDragon)par1EntityLivingBase, par2);
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 226:    */   {
/* 227:271 */     rotateCorpse((EntityDragon)par1EntityLivingBase, par2, par3, par4);
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected void renderModel(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7)
/* 231:    */   {
/* 232:279 */     renderModel((EntityDragon)par1EntityLivingBase, par2, par3, par4, par5, par6, par7);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void doRender(EntityLivingBase par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 236:    */   {
/* 237:290 */     doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
/* 238:    */   }
/* 239:    */   
/* 240:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 241:    */   {
/* 242:298 */     return getEntityTexture((EntityDragon)par1Entity);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 246:    */   {
/* 247:309 */     doRender((EntityDragon)par1Entity, par2, par4, par6, par8, par9);
/* 248:    */   }
/* 249:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderDragon
 * JD-Core Version:    0.7.0.1
 */