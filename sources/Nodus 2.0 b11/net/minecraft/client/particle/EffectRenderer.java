/*   1:    */ package net.minecraft.client.particle;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Random;
/*   6:    */ import java.util.concurrent.Callable;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.material.Material;
/*   9:    */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*  10:    */ import net.minecraft.client.renderer.Tessellator;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  12:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  13:    */ import net.minecraft.crash.CrashReport;
/*  14:    */ import net.minecraft.crash.CrashReportCategory;
/*  15:    */ import net.minecraft.entity.Entity;
/*  16:    */ import net.minecraft.util.MathHelper;
/*  17:    */ import net.minecraft.util.ReportedException;
/*  18:    */ import net.minecraft.util.ResourceLocation;
/*  19:    */ import net.minecraft.world.World;
/*  20:    */ import org.lwjgl.opengl.GL11;
/*  21:    */ 
/*  22:    */ public class EffectRenderer
/*  23:    */ {
/*  24: 24 */   private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
/*  25:    */   protected World worldObj;
/*  26: 28 */   private List[] fxLayers = new List[4];
/*  27:    */   private TextureManager renderer;
/*  28: 32 */   private Random rand = new Random();
/*  29:    */   private static final String __OBFID = "CL_00000915";
/*  30:    */   
/*  31:    */   public EffectRenderer(World par1World, TextureManager par2TextureManager)
/*  32:    */   {
/*  33: 37 */     if (par1World != null) {
/*  34: 39 */       this.worldObj = par1World;
/*  35:    */     }
/*  36: 42 */     this.renderer = par2TextureManager;
/*  37: 44 */     for (int var3 = 0; var3 < 4; var3++) {
/*  38: 46 */       this.fxLayers[var3] = new ArrayList();
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void addEffect(EntityFX par1EntityFX)
/*  43:    */   {
/*  44: 52 */     int var2 = par1EntityFX.getFXLayer();
/*  45: 54 */     if (this.fxLayers[var2].size() >= 4000) {
/*  46: 56 */       this.fxLayers[var2].remove(0);
/*  47:    */     }
/*  48: 59 */     this.fxLayers[var2].add(par1EntityFX);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void updateEffects()
/*  52:    */   {
/*  53: 64 */     for (int var11 = 0; var11 < 4; var11++)
/*  54:    */     {
/*  55: 66 */       final int var1 = var11;
/*  56: 68 */       for (int var2 = 0; var2 < this.fxLayers[var1].size(); var2++)
/*  57:    */       {
/*  58: 70 */         final EntityFX var3 = (EntityFX)this.fxLayers[var1].get(var2);
/*  59:    */         try
/*  60:    */         {
/*  61: 74 */           var3.onUpdate();
/*  62:    */         }
/*  63:    */         catch (Throwable var8)
/*  64:    */         {
/*  65: 78 */           CrashReport var5 = CrashReport.makeCrashReport(var8, "Ticking Particle");
/*  66: 79 */           CrashReportCategory var6 = var5.makeCategory("Particle being ticked");
/*  67: 80 */           var6.addCrashSectionCallable("Particle", new Callable()
/*  68:    */           {
/*  69:    */             private static final String __OBFID = "CL_00000916";
/*  70:    */             
/*  71:    */             public String call()
/*  72:    */             {
/*  73: 85 */               return var3.toString();
/*  74:    */             }
/*  75: 87 */           });
/*  76: 88 */           var6.addCrashSectionCallable("Particle Type", new Callable()
/*  77:    */           {
/*  78:    */             private static final String __OBFID = "CL_00000917";
/*  79:    */             
/*  80:    */             public String call()
/*  81:    */             {
/*  82: 93 */               return "Unknown - " + var1;
/*  83:    */             }
/*  84: 95 */           });
/*  85: 96 */           throw new ReportedException(var5);
/*  86:    */         }
/*  87: 99 */         if (var3.isDead) {
/*  88:101 */           this.fxLayers[var1].remove(var2--);
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void renderParticles(Entity par1Entity, float par2)
/*  95:    */   {
/*  96:112 */     float var3 = ActiveRenderInfo.rotationX;
/*  97:113 */     float var4 = ActiveRenderInfo.rotationZ;
/*  98:114 */     float var5 = ActiveRenderInfo.rotationYZ;
/*  99:115 */     float var6 = ActiveRenderInfo.rotationXY;
/* 100:116 */     float var7 = ActiveRenderInfo.rotationXZ;
/* 101:117 */     EntityFX.interpPosX = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par2;
/* 102:118 */     EntityFX.interpPosY = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par2;
/* 103:119 */     EntityFX.interpPosZ = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par2;
/* 104:121 */     for (int var88 = 0; var88 < 3; var88++)
/* 105:    */     {
/* 106:123 */       final int var8 = var88;
/* 107:125 */       if (!this.fxLayers[var8].isEmpty())
/* 108:    */       {
/* 109:127 */         switch (var8)
/* 110:    */         {
/* 111:    */         case 0: 
/* 112:    */         default: 
/* 113:131 */           this.renderer.bindTexture(particleTextures);
/* 114:132 */           break;
/* 115:    */         case 1: 
/* 116:135 */           this.renderer.bindTexture(TextureMap.locationBlocksTexture);
/* 117:136 */           break;
/* 118:    */         case 2: 
/* 119:139 */           this.renderer.bindTexture(TextureMap.locationItemsTexture);
/* 120:    */         }
/* 121:142 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 122:143 */         GL11.glDepthMask(false);
/* 123:144 */         GL11.glEnable(3042);
/* 124:145 */         GL11.glBlendFunc(770, 771);
/* 125:146 */         GL11.glAlphaFunc(516, 0.003921569F);
/* 126:147 */         Tessellator var9 = Tessellator.instance;
/* 127:148 */         var9.startDrawingQuads();
/* 128:150 */         for (int var10 = 0; var10 < this.fxLayers[var8].size(); var10++)
/* 129:    */         {
/* 130:152 */           final EntityFX var11 = (EntityFX)this.fxLayers[var8].get(var10);
/* 131:153 */           var9.setBrightness(var11.getBrightnessForRender(par2));
/* 132:    */           try
/* 133:    */           {
/* 134:157 */             var11.renderParticle(var9, par2, var3, var7, var4, var5, var6);
/* 135:    */           }
/* 136:    */           catch (Throwable var16)
/* 137:    */           {
/* 138:161 */             CrashReport var13 = CrashReport.makeCrashReport(var16, "Rendering Particle");
/* 139:162 */             CrashReportCategory var14 = var13.makeCategory("Particle being rendered");
/* 140:163 */             var14.addCrashSectionCallable("Particle", new Callable()
/* 141:    */             {
/* 142:    */               private static final String __OBFID = "CL_00000918";
/* 143:    */               
/* 144:    */               public String call()
/* 145:    */               {
/* 146:168 */                 return var11.toString();
/* 147:    */               }
/* 148:170 */             });
/* 149:171 */             var14.addCrashSectionCallable("Particle Type", new Callable()
/* 150:    */             {
/* 151:    */               private static final String __OBFID = "CL_00000919";
/* 152:    */               
/* 153:    */               public String call()
/* 154:    */               {
/* 155:176 */                 return "Unknown - " + var8;
/* 156:    */               }
/* 157:178 */             });
/* 158:179 */             throw new ReportedException(var13);
/* 159:    */           }
/* 160:    */         }
/* 161:183 */         var9.draw();
/* 162:184 */         GL11.glDisable(3042);
/* 163:185 */         GL11.glDepthMask(true);
/* 164:186 */         GL11.glAlphaFunc(516, 0.1F);
/* 165:    */       }
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void renderLitParticles(Entity par1Entity, float par2)
/* 170:    */   {
/* 171:193 */     float var3 = 0.01745329F;
/* 172:194 */     float var4 = MathHelper.cos(par1Entity.rotationYaw * 0.01745329F);
/* 173:195 */     float var5 = MathHelper.sin(par1Entity.rotationYaw * 0.01745329F);
/* 174:196 */     float var6 = -var5 * MathHelper.sin(par1Entity.rotationPitch * 0.01745329F);
/* 175:197 */     float var7 = var4 * MathHelper.sin(par1Entity.rotationPitch * 0.01745329F);
/* 176:198 */     float var8 = MathHelper.cos(par1Entity.rotationPitch * 0.01745329F);
/* 177:199 */     byte var9 = 3;
/* 178:200 */     List var10 = this.fxLayers[var9];
/* 179:202 */     if (!var10.isEmpty())
/* 180:    */     {
/* 181:204 */       Tessellator var11 = Tessellator.instance;
/* 182:206 */       for (int var12 = 0; var12 < var10.size(); var12++)
/* 183:    */       {
/* 184:208 */         EntityFX var13 = (EntityFX)var10.get(var12);
/* 185:209 */         var11.setBrightness(var13.getBrightnessForRender(par2));
/* 186:210 */         var13.renderParticle(var11, par2, var4, var8, var5, var6, var7);
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void clearEffects(World par1World)
/* 192:    */   {
/* 193:217 */     this.worldObj = par1World;
/* 194:219 */     for (int var2 = 0; var2 < 4; var2++) {
/* 195:221 */       this.fxLayers[var2].clear();
/* 196:    */     }
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void func_147215_a(int p_147215_1_, int p_147215_2_, int p_147215_3_, Block p_147215_4_, int p_147215_5_)
/* 200:    */   {
/* 201:227 */     if (p_147215_4_.getMaterial() != Material.air)
/* 202:    */     {
/* 203:229 */       byte var6 = 4;
/* 204:231 */       for (int var7 = 0; var7 < var6; var7++) {
/* 205:233 */         for (int var8 = 0; var8 < var6; var8++) {
/* 206:235 */           for (int var9 = 0; var9 < var6; var9++)
/* 207:    */           {
/* 208:237 */             double var10 = p_147215_1_ + (var7 + 0.5D) / var6;
/* 209:238 */             double var12 = p_147215_2_ + (var8 + 0.5D) / var6;
/* 210:239 */             double var14 = p_147215_3_ + (var9 + 0.5D) / var6;
/* 211:240 */             addEffect(new EntityDiggingFX(this.worldObj, var10, var12, var14, var10 - p_147215_1_ - 0.5D, var12 - p_147215_2_ - 0.5D, var14 - p_147215_3_ - 0.5D, p_147215_4_, p_147215_5_).applyColourMultiplier(p_147215_1_, p_147215_2_, p_147215_3_));
/* 212:    */           }
/* 213:    */         }
/* 214:    */       }
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void addBlockHitEffects(int par1, int par2, int par3, int par4)
/* 219:    */   {
/* 220:252 */     Block var5 = this.worldObj.getBlock(par1, par2, par3);
/* 221:254 */     if (var5.getMaterial() != Material.air)
/* 222:    */     {
/* 223:256 */       float var6 = 0.1F;
/* 224:257 */       double var7 = par1 + this.rand.nextDouble() * (var5.getBlockBoundsMaxX() - var5.getBlockBoundsMinX() - var6 * 2.0F) + var6 + var5.getBlockBoundsMinX();
/* 225:258 */       double var9 = par2 + this.rand.nextDouble() * (var5.getBlockBoundsMaxY() - var5.getBlockBoundsMinY() - var6 * 2.0F) + var6 + var5.getBlockBoundsMinY();
/* 226:259 */       double var11 = par3 + this.rand.nextDouble() * (var5.getBlockBoundsMaxZ() - var5.getBlockBoundsMinZ() - var6 * 2.0F) + var6 + var5.getBlockBoundsMinZ();
/* 227:261 */       if (par4 == 0) {
/* 228:263 */         var9 = par2 + var5.getBlockBoundsMinY() - var6;
/* 229:    */       }
/* 230:266 */       if (par4 == 1) {
/* 231:268 */         var9 = par2 + var5.getBlockBoundsMaxY() + var6;
/* 232:    */       }
/* 233:271 */       if (par4 == 2) {
/* 234:273 */         var11 = par3 + var5.getBlockBoundsMinZ() - var6;
/* 235:    */       }
/* 236:276 */       if (par4 == 3) {
/* 237:278 */         var11 = par3 + var5.getBlockBoundsMaxZ() + var6;
/* 238:    */       }
/* 239:281 */       if (par4 == 4) {
/* 240:283 */         var7 = par1 + var5.getBlockBoundsMinX() - var6;
/* 241:    */       }
/* 242:286 */       if (par4 == 5) {
/* 243:288 */         var7 = par1 + var5.getBlockBoundsMaxX() + var6;
/* 244:    */       }
/* 245:291 */       addEffect(new EntityDiggingFX(this.worldObj, var7, var9, var11, 0.0D, 0.0D, 0.0D, var5, this.worldObj.getBlockMetadata(par1, par2, par3)).applyColourMultiplier(par1, par2, par3).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
/* 246:    */     }
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getStatistics()
/* 250:    */   {
/* 251:297 */     return this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size();
/* 252:    */   }
/* 253:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.particle.EffectRenderer
 * JD-Core Version:    0.7.0.1
 */