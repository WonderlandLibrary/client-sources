/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.Nodus;
/*   4:    */ import me.connorm.Nodus.module.NodusModuleManager;
/*   5:    */ import me.connorm.Nodus.module.modules.NameTags;
/*   6:    */ import me.connorm.Nodus.ui.gui.theme.nodus.NodusUtils;
/*   7:    */ import net.minecraft.block.Block;
/*   8:    */ import net.minecraft.block.BlockFire;
/*   9:    */ import net.minecraft.block.material.Material;
/*  10:    */ import net.minecraft.client.Minecraft;
/*  11:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*  12:    */ import net.minecraft.client.gui.FontRenderer;
/*  13:    */ import net.minecraft.client.renderer.EntityRenderer;
/*  14:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  15:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  16:    */ import net.minecraft.client.renderer.Tessellator;
/*  17:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  18:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  19:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  20:    */ import net.minecraft.client.settings.GameSettings;
/*  21:    */ import net.minecraft.entity.Entity;
/*  22:    */ import net.minecraft.entity.EntityLiving;
/*  23:    */ import net.minecraft.entity.EntityLivingBase;
/*  24:    */ import net.minecraft.init.Blocks;
/*  25:    */ import net.minecraft.util.AxisAlignedBB;
/*  26:    */ import net.minecraft.util.IIcon;
/*  27:    */ import net.minecraft.util.MathHelper;
/*  28:    */ import net.minecraft.util.ResourceLocation;
/*  29:    */ import net.minecraft.world.World;
/*  30:    */ import org.lwjgl.opengl.GL11;
/*  31:    */ 
/*  32:    */ public abstract class Render
/*  33:    */ {
/*  34: 30 */   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
/*  35:    */   protected RenderManager renderManager;
/*  36: 32 */   protected RenderBlocks field_147909_c = new RenderBlocks();
/*  37:    */   protected float shadowSize;
/*  38: 38 */   protected float shadowOpaque = 1.0F;
/*  39: 39 */   private boolean field_147908_f = false;
/*  40:    */   private static final String __OBFID = "CL_00000992";
/*  41:    */   
/*  42:    */   public abstract void doRender(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
/*  43:    */   
/*  44:    */   protected abstract ResourceLocation getEntityTexture(Entity paramEntity);
/*  45:    */   
/*  46:    */   public boolean func_147905_a()
/*  47:    */   {
/*  48: 57 */     return this.field_147908_f;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected void bindEntityTexture(Entity par1Entity)
/*  52:    */   {
/*  53: 62 */     bindTexture(getEntityTexture(par1Entity));
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected void bindTexture(ResourceLocation par1ResourceLocation)
/*  57:    */   {
/*  58: 67 */     this.renderManager.renderEngine.bindTexture(par1ResourceLocation);
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void renderEntityOnFire(Entity par1Entity, double par2, double par4, double par6, float par8)
/*  62:    */   {
/*  63: 75 */     GL11.glDisable(2896);
/*  64: 76 */     IIcon var9 = Blocks.fire.func_149840_c(0);
/*  65: 77 */     IIcon var10 = Blocks.fire.func_149840_c(1);
/*  66: 78 */     GL11.glPushMatrix();
/*  67: 79 */     GL11.glTranslatef((float)par2, (float)par4, (float)par6);
/*  68: 80 */     float var11 = par1Entity.width * 1.4F;
/*  69: 81 */     GL11.glScalef(var11, var11, var11);
/*  70: 82 */     Tessellator var12 = Tessellator.instance;
/*  71: 83 */     float var13 = 0.5F;
/*  72: 84 */     float var14 = 0.0F;
/*  73: 85 */     float var15 = par1Entity.height / var11;
/*  74: 86 */     float var16 = (float)(par1Entity.posY - par1Entity.boundingBox.minY);
/*  75: 87 */     GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/*  76: 88 */     GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int)var15 * 0.02F);
/*  77: 89 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  78: 90 */     float var17 = 0.0F;
/*  79: 91 */     int var18 = 0;
/*  80: 92 */     var12.startDrawingQuads();
/*  81: 94 */     while (var15 > 0.0F)
/*  82:    */     {
/*  83: 96 */       IIcon var19 = var18 % 2 == 0 ? var9 : var10;
/*  84: 97 */       bindTexture(TextureMap.locationBlocksTexture);
/*  85: 98 */       float var20 = var19.getMinU();
/*  86: 99 */       float var21 = var19.getMinV();
/*  87:100 */       float var22 = var19.getMaxU();
/*  88:101 */       float var23 = var19.getMaxV();
/*  89:103 */       if (var18 / 2 % 2 == 0)
/*  90:    */       {
/*  91:105 */         float var24 = var22;
/*  92:106 */         var22 = var20;
/*  93:107 */         var20 = var24;
/*  94:    */       }
/*  95:110 */       var12.addVertexWithUV(var13 - var14, 0.0F - var16, var17, var22, var23);
/*  96:111 */       var12.addVertexWithUV(-var13 - var14, 0.0F - var16, var17, var20, var23);
/*  97:112 */       var12.addVertexWithUV(-var13 - var14, 1.4F - var16, var17, var20, var21);
/*  98:113 */       var12.addVertexWithUV(var13 - var14, 1.4F - var16, var17, var22, var21);
/*  99:114 */       var15 -= 0.45F;
/* 100:115 */       var16 -= 0.45F;
/* 101:116 */       var13 *= 0.9F;
/* 102:117 */       var17 += 0.03F;
/* 103:118 */       var18++;
/* 104:    */     }
/* 105:121 */     var12.draw();
/* 106:122 */     GL11.glPopMatrix();
/* 107:123 */     GL11.glEnable(2896);
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void renderShadow(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 111:    */   {
/* 112:132 */     GL11.glEnable(3042);
/* 113:133 */     GL11.glBlendFunc(770, 771);
/* 114:134 */     this.renderManager.renderEngine.bindTexture(shadowTextures);
/* 115:135 */     World var10 = getWorldFromRenderManager();
/* 116:136 */     GL11.glDepthMask(false);
/* 117:137 */     float var11 = this.shadowSize;
/* 118:139 */     if ((par1Entity instanceof EntityLiving))
/* 119:    */     {
/* 120:141 */       EntityLiving var12 = (EntityLiving)par1Entity;
/* 121:142 */       var11 *= var12.getRenderSizeModifier();
/* 122:144 */       if (var12.isChild()) {
/* 123:146 */         var11 *= 0.5F;
/* 124:    */       }
/* 125:    */     }
/* 126:150 */     double var35 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par9;
/* 127:151 */     double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par9 + par1Entity.getShadowSize();
/* 128:152 */     double var16 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par9;
/* 129:153 */     int var18 = MathHelper.floor_double(var35 - var11);
/* 130:154 */     int var19 = MathHelper.floor_double(var35 + var11);
/* 131:155 */     int var20 = MathHelper.floor_double(var14 - var11);
/* 132:156 */     int var21 = MathHelper.floor_double(var14);
/* 133:157 */     int var22 = MathHelper.floor_double(var16 - var11);
/* 134:158 */     int var23 = MathHelper.floor_double(var16 + var11);
/* 135:159 */     double var24 = par2 - var35;
/* 136:160 */     double var26 = par4 - var14;
/* 137:161 */     double var28 = par6 - var16;
/* 138:162 */     Tessellator var30 = Tessellator.instance;
/* 139:163 */     var30.startDrawingQuads();
/* 140:165 */     for (int var31 = var18; var31 <= var19; var31++) {
/* 141:167 */       for (int var32 = var20; var32 <= var21; var32++) {
/* 142:169 */         for (int var33 = var22; var33 <= var23; var33++)
/* 143:    */         {
/* 144:171 */           Block var34 = var10.getBlock(var31, var32 - 1, var33);
/* 145:173 */           if ((var34.getMaterial() != Material.air) && (var10.getBlockLightValue(var31, var32, var33) > 3)) {
/* 146:175 */             func_147907_a(var34, par2, par4 + par1Entity.getShadowSize(), par6, var31, var32, var33, par8, var11, var24, var26 + par1Entity.getShadowSize(), var28);
/* 147:    */           }
/* 148:    */         }
/* 149:    */       }
/* 150:    */     }
/* 151:181 */     var30.draw();
/* 152:182 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 153:183 */     GL11.glDisable(3042);
/* 154:184 */     GL11.glDepthMask(true);
/* 155:    */   }
/* 156:    */   
/* 157:    */   private World getWorldFromRenderManager()
/* 158:    */   {
/* 159:192 */     return this.renderManager.worldObj;
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void func_147907_a(Block p_147907_1_, double p_147907_2_, double p_147907_4_, double p_147907_6_, int p_147907_8_, int p_147907_9_, int p_147907_10_, float p_147907_11_, float p_147907_12_, double p_147907_13_, double p_147907_15_, double p_147907_17_)
/* 163:    */   {
/* 164:197 */     Tessellator var19 = Tessellator.instance;
/* 165:199 */     if (p_147907_1_.renderAsNormalBlock())
/* 166:    */     {
/* 167:201 */       double var20 = (p_147907_11_ - (p_147907_4_ - (p_147907_9_ + p_147907_15_)) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(p_147907_8_, p_147907_9_, p_147907_10_);
/* 168:203 */       if (var20 >= 0.0D)
/* 169:    */       {
/* 170:205 */         if (var20 > 1.0D) {
/* 171:207 */           var20 = 1.0D;
/* 172:    */         }
/* 173:210 */         var19.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float)var20);
/* 174:211 */         double var22 = p_147907_8_ + p_147907_1_.getBlockBoundsMinX() + p_147907_13_;
/* 175:212 */         double var24 = p_147907_8_ + p_147907_1_.getBlockBoundsMaxX() + p_147907_13_;
/* 176:213 */         double var26 = p_147907_9_ + p_147907_1_.getBlockBoundsMinY() + p_147907_15_ + 0.015625D;
/* 177:214 */         double var28 = p_147907_10_ + p_147907_1_.getBlockBoundsMinZ() + p_147907_17_;
/* 178:215 */         double var30 = p_147907_10_ + p_147907_1_.getBlockBoundsMaxZ() + p_147907_17_;
/* 179:216 */         float var32 = (float)((p_147907_2_ - var22) / 2.0D / p_147907_12_ + 0.5D);
/* 180:217 */         float var33 = (float)((p_147907_2_ - var24) / 2.0D / p_147907_12_ + 0.5D);
/* 181:218 */         float var34 = (float)((p_147907_6_ - var28) / 2.0D / p_147907_12_ + 0.5D);
/* 182:219 */         float var35 = (float)((p_147907_6_ - var30) / 2.0D / p_147907_12_ + 0.5D);
/* 183:220 */         var19.addVertexWithUV(var22, var26, var28, var32, var34);
/* 184:221 */         var19.addVertexWithUV(var22, var26, var30, var32, var35);
/* 185:222 */         var19.addVertexWithUV(var24, var26, var30, var33, var35);
/* 186:223 */         var19.addVertexWithUV(var24, var26, var28, var33, var34);
/* 187:    */       }
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static void renderOffsetAABB(AxisAlignedBB par0AxisAlignedBB, double par1, double par3, double par5)
/* 192:    */   {
/* 193:233 */     GL11.glDisable(3553);
/* 194:234 */     Tessellator var7 = Tessellator.instance;
/* 195:235 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 196:236 */     var7.startDrawingQuads();
/* 197:237 */     var7.setTranslation(par1, par3, par5);
/* 198:238 */     var7.setNormal(0.0F, 0.0F, -1.0F);
/* 199:239 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 200:240 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 201:241 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 202:242 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 203:243 */     var7.setNormal(0.0F, 0.0F, 1.0F);
/* 204:244 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 205:245 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 206:246 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 207:247 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 208:248 */     var7.setNormal(0.0F, -1.0F, 0.0F);
/* 209:249 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 210:250 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 211:251 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 212:252 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 213:253 */     var7.setNormal(0.0F, 1.0F, 0.0F);
/* 214:254 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 215:255 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 216:256 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 217:257 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 218:258 */     var7.setNormal(-1.0F, 0.0F, 0.0F);
/* 219:259 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 220:260 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 221:261 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 222:262 */     var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 223:263 */     var7.setNormal(1.0F, 0.0F, 0.0F);
/* 224:264 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 225:265 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 226:266 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 227:267 */     var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 228:268 */     var7.setTranslation(0.0D, 0.0D, 0.0D);
/* 229:269 */     var7.draw();
/* 230:270 */     GL11.glEnable(3553);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public static void renderAABB(AxisAlignedBB par0AxisAlignedBB)
/* 234:    */   {
/* 235:278 */     Tessellator var1 = Tessellator.instance;
/* 236:279 */     var1.startDrawingQuads();
/* 237:280 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 238:281 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 239:282 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 240:283 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 241:284 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 242:285 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 243:286 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 244:287 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 245:288 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 246:289 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 247:290 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 248:291 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 249:292 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 250:293 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 251:294 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 252:295 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 253:296 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 254:297 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 255:298 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 256:299 */     var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 257:300 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
/* 258:301 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
/* 259:302 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
/* 260:303 */     var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
/* 261:304 */     var1.draw();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setRenderManager(RenderManager par1RenderManager)
/* 265:    */   {
/* 266:312 */     this.renderManager = par1RenderManager;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 270:    */   {
/* 271:320 */     if ((this.renderManager.options.fancyGraphics) && (this.shadowSize > 0.0F) && (!par1Entity.isInvisible()))
/* 272:    */     {
/* 273:322 */       double var10 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
/* 274:323 */       float var12 = (float)((1.0D - var10 / 256.0D) * this.shadowOpaque);
/* 275:325 */       if (var12 > 0.0F) {
/* 276:327 */         renderShadow(par1Entity, par2, par4, par6, var12, par9);
/* 277:    */       }
/* 278:    */     }
/* 279:331 */     if (par1Entity.canRenderOnFire()) {
/* 280:333 */       renderEntityOnFire(par1Entity, par2, par4, par6, par9);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public FontRenderer getFontRendererFromRenderManager()
/* 285:    */   {
/* 286:342 */     return this.renderManager.getFontRenderer();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void updateIcons(IIconRegister par1IconRegister) {}
/* 290:    */   
/* 291:    */   protected void func_147906_a(EntityLivingBase p_147906_1_, String p_147906_2_, double p_147906_3_, double p_147906_5_, double p_147906_7_, int p_147906_9_)
/* 292:    */   {
/* 293:350 */     boolean shouldDrawNametags = Nodus.theNodus.moduleManager.nameTagsModule.isToggled();
/* 294:    */     
/* 295:352 */     double var100 = p_147906_1_.getDistance(Nodus.theNodus.getMinecraft().thePlayer.posX, Nodus.theNodus.getMinecraft().thePlayer.posY, Nodus.theNodus.getMinecraft().thePlayer.posZ);
/* 296:353 */     String nameTagName = p_147906_2_;
/* 297:    */     
/* 298:355 */     double var10 = p_147906_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 299:357 */     if (var10 <= p_147906_9_ * p_147906_9_)
/* 300:    */     {
/* 301:359 */       FontRenderer var12 = getFontRendererFromRenderManager();
/* 302:360 */       float var13 = 1.6F;
/* 303:    */       
/* 304:362 */       float var14 = shouldDrawNametags ? 0.01666667F * var13 * 2.0F : 0.01666667F * var13;
/* 305:363 */       GL11.glPushMatrix();
/* 306:365 */       if (shouldDrawNametags) {
/* 307:367 */         Nodus.theNodus.getMinecraft().entityRenderer.disableLightmap(1.0D);
/* 308:    */       }
/* 309:369 */       GL11.glTranslatef((float)p_147906_3_ + 0.0F, (float)p_147906_5_ + p_147906_1_.height + 0.5F, (float)p_147906_7_);
/* 310:370 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 311:371 */       GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 312:372 */       GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 313:373 */       GL11.glScalef(-var14, -var14, var14);
/* 314:374 */       GL11.glDisable(2896);
/* 315:375 */       GL11.glDepthMask(false);
/* 316:376 */       GL11.glDisable(2929);
/* 317:377 */       GL11.glEnable(3042);
/* 318:378 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 319:379 */       Tessellator var15 = Tessellator.instance;
/* 320:380 */       byte var16 = 0;
/* 321:382 */       if (p_147906_2_.equals("deadmau5")) {
/* 322:384 */         var16 = -10;
/* 323:    */       }
/* 324:388 */       int var17 = var12.getStringWidth(p_147906_2_) / 2;
/* 325:389 */       GL11.glDisable(3553);
/* 326:390 */       int nametagColor = p_147906_1_.isSneaking() ? -1716912128 : -1728053248;
/* 327:391 */       if (shouldDrawNametags) {
/* 328:392 */         NodusUtils.drawNodusNametag(-var17 - 3, var16 - 3, 3 + var17, var16 + 10, nametagColor);
/* 329:    */       }
/* 330:393 */       var15.startDrawingQuads();
/* 331:394 */       var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
/* 332:395 */       var15.addVertex(-var17 - 1, -1 + var16, 0.0D);
/* 333:396 */       var15.addVertex(-var17 - 1, 8 + var16, 0.0D);
/* 334:397 */       var15.addVertex(var17 + 1, 8 + var16, 0.0D);
/* 335:398 */       var15.addVertex(var17 + 1, -1 + var16, 0.0D);
/* 336:399 */       var15.draw();
/* 337:400 */       GL11.glEnable(3553);
/* 338:402 */       if (shouldDrawNametags) {
/* 339:404 */         var12.drawString(nameTagName, -var12.getStringWidth(nameTagName) / 2, var16, -4276546);
/* 340:    */       } else {
/* 341:407 */         var12.drawString(p_147906_2_, -var12.getStringWidth(p_147906_2_) / 2, var16, -1);
/* 342:    */       }
/* 343:409 */       GL11.glEnable(2929);
/* 344:410 */       GL11.glDepthMask(true);
/* 345:411 */       if (shouldDrawNametags) {
/* 346:413 */         var12.drawString(nameTagName, -var12.getStringWidth(nameTagName) / 2, var16, -4276546);
/* 347:    */       } else {
/* 348:416 */         var12.drawString(p_147906_2_, -var12.getStringWidth(p_147906_2_) / 2, var16, -1);
/* 349:    */       }
/* 350:418 */       GL11.glEnable(2896);
/* 351:419 */       GL11.glDisable(3042);
/* 352:420 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 353:421 */       GL11.glPopMatrix();
/* 354:    */     }
/* 355:    */   }
/* 356:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.Render
 * JD-Core Version:    0.7.0.1
 */