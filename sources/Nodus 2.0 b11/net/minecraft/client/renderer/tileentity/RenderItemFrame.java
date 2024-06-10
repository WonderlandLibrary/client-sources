/*   1:    */ package net.minecraft.client.renderer.tileentity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.gui.FontRenderer;
/*   6:    */ import net.minecraft.client.gui.MapItemRenderer;
/*   7:    */ import net.minecraft.client.renderer.EntityRenderer;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.client.renderer.entity.Render;
/*  11:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  12:    */ import net.minecraft.client.renderer.texture.IIconRegister;
/*  13:    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureCompass;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  17:    */ import net.minecraft.entity.Entity;
/*  18:    */ import net.minecraft.entity.item.EntityItem;
/*  19:    */ import net.minecraft.entity.item.EntityItemFrame;
/*  20:    */ import net.minecraft.init.Blocks;
/*  21:    */ import net.minecraft.init.Items;
/*  22:    */ import net.minecraft.item.Item;
/*  23:    */ import net.minecraft.item.ItemMap;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.util.IIcon;
/*  26:    */ import net.minecraft.util.MathHelper;
/*  27:    */ import net.minecraft.util.ResourceLocation;
/*  28:    */ import net.minecraft.world.storage.MapData;
/*  29:    */ import org.lwjgl.opengl.GL11;
/*  30:    */ 
/*  31:    */ public class RenderItemFrame
/*  32:    */   extends Render
/*  33:    */ {
/*  34: 32 */   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
/*  35: 33 */   private final RenderBlocks field_147916_f = new RenderBlocks();
/*  36: 34 */   private final Minecraft field_147917_g = Minecraft.getMinecraft();
/*  37:    */   private IIcon field_94147_f;
/*  38:    */   private static final String __OBFID = "CL_00001002";
/*  39:    */   
/*  40:    */   public void updateIcons(IIconRegister par1IconRegister)
/*  41:    */   {
/*  42: 40 */     this.field_94147_f = par1IconRegister.registerIcon("itemframe_background");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void doRender(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9)
/*  46:    */   {
/*  47: 51 */     GL11.glPushMatrix();
/*  48: 52 */     double var10 = par1EntityItemFrame.posX - par2 - 0.5D;
/*  49: 53 */     double var12 = par1EntityItemFrame.posY - par4 - 0.5D;
/*  50: 54 */     double var14 = par1EntityItemFrame.posZ - par6 - 0.5D;
/*  51: 55 */     int var16 = par1EntityItemFrame.field_146063_b + net.minecraft.util.Direction.offsetX[par1EntityItemFrame.hangingDirection];
/*  52: 56 */     int var17 = par1EntityItemFrame.field_146064_c;
/*  53: 57 */     int var18 = par1EntityItemFrame.field_146062_d + net.minecraft.util.Direction.offsetZ[par1EntityItemFrame.hangingDirection];
/*  54: 58 */     GL11.glTranslated(var16 - var10, var17 - var12, var18 - var14);
/*  55: 60 */     if ((par1EntityItemFrame.getDisplayedItem() != null) && (par1EntityItemFrame.getDisplayedItem().getItem() == Items.filled_map)) {
/*  56: 62 */       func_147915_b(par1EntityItemFrame);
/*  57:    */     } else {
/*  58: 66 */       renderFrameItemAsBlock(par1EntityItemFrame);
/*  59:    */     }
/*  60: 69 */     func_82402_b(par1EntityItemFrame);
/*  61: 70 */     GL11.glPopMatrix();
/*  62: 71 */     func_147914_a(par1EntityItemFrame, par2 + net.minecraft.util.Direction.offsetX[par1EntityItemFrame.hangingDirection] * 0.3F, par4 - 0.25D, par6 + net.minecraft.util.Direction.offsetZ[par1EntityItemFrame.hangingDirection] * 0.3F);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected ResourceLocation getEntityTexture(EntityItemFrame par1EntityItemFrame)
/*  66:    */   {
/*  67: 79 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private void func_147915_b(EntityItemFrame p_147915_1_)
/*  71:    */   {
/*  72: 84 */     GL11.glPushMatrix();
/*  73: 85 */     GL11.glRotatef(p_147915_1_.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  74: 86 */     this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/*  75: 87 */     Block var2 = Blocks.planks;
/*  76: 88 */     float var3 = 0.0625F;
/*  77: 89 */     float var4 = 1.0F;
/*  78: 90 */     float var5 = var4 / 2.0F;
/*  79: 91 */     GL11.glPushMatrix();
/*  80: 92 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5 + 0.0625F, 0.5F - var5 + 0.0625F, var3, 0.5F + var5 - 0.0625F, 0.5F + var5 - 0.0625F);
/*  81: 93 */     this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
/*  82: 94 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/*  83: 95 */     this.field_147916_f.clearOverrideBlockTexture();
/*  84: 96 */     this.field_147916_f.unlockBlockBounds();
/*  85: 97 */     GL11.glPopMatrix();
/*  86: 98 */     this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
/*  87: 99 */     GL11.glPushMatrix();
/*  88:100 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3 + 1.0E-004F, var3 + 0.5F - var5, 0.5F + var5);
/*  89:101 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/*  90:102 */     GL11.glPopMatrix();
/*  91:103 */     GL11.glPushMatrix();
/*  92:104 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F + var5 - var3, 0.5F - var5, var3 + 1.0E-004F, 0.5F + var5, 0.5F + var5);
/*  93:105 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/*  94:106 */     GL11.glPopMatrix();
/*  95:107 */     GL11.glPushMatrix();
/*  96:108 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3, 0.5F + var5, var3 + 0.5F - var5);
/*  97:109 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/*  98:110 */     GL11.glPopMatrix();
/*  99:111 */     GL11.glPushMatrix();
/* 100:112 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F + var5 - var3, var3, 0.5F + var5, 0.5F + var5);
/* 101:113 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 102:114 */     GL11.glPopMatrix();
/* 103:115 */     this.field_147916_f.unlockBlockBounds();
/* 104:116 */     this.field_147916_f.clearOverrideBlockTexture();
/* 105:117 */     GL11.glPopMatrix();
/* 106:    */   }
/* 107:    */   
/* 108:    */   private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame)
/* 109:    */   {
/* 110:125 */     GL11.glPushMatrix();
/* 111:126 */     GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 112:127 */     this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
/* 113:128 */     Block var2 = Blocks.planks;
/* 114:129 */     float var3 = 0.0625F;
/* 115:130 */     float var4 = 0.75F;
/* 116:131 */     float var5 = var4 / 2.0F;
/* 117:132 */     GL11.glPushMatrix();
/* 118:133 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5 + 0.0625F, 0.5F - var5 + 0.0625F, var3 * 0.5F, 0.5F + var5 - 0.0625F, 0.5F + var5 - 0.0625F);
/* 119:134 */     this.field_147916_f.setOverrideBlockTexture(this.field_94147_f);
/* 120:135 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 121:136 */     this.field_147916_f.clearOverrideBlockTexture();
/* 122:137 */     this.field_147916_f.unlockBlockBounds();
/* 123:138 */     GL11.glPopMatrix();
/* 124:139 */     this.field_147916_f.setOverrideBlockTexture(Blocks.planks.getIcon(1, 2));
/* 125:140 */     GL11.glPushMatrix();
/* 126:141 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3 + 1.0E-004F, var3 + 0.5F - var5, 0.5F + var5);
/* 127:142 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 128:143 */     GL11.glPopMatrix();
/* 129:144 */     GL11.glPushMatrix();
/* 130:145 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F + var5 - var3, 0.5F - var5, var3 + 1.0E-004F, 0.5F + var5, 0.5F + var5);
/* 131:146 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 132:147 */     GL11.glPopMatrix();
/* 133:148 */     GL11.glPushMatrix();
/* 134:149 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F - var5, var3, 0.5F + var5, var3 + 0.5F - var5);
/* 135:150 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 136:151 */     GL11.glPopMatrix();
/* 137:152 */     GL11.glPushMatrix();
/* 138:153 */     this.field_147916_f.overrideBlockBounds(0.0D, 0.5F - var5, 0.5F + var5 - var3, var3, 0.5F + var5, 0.5F + var5);
/* 139:154 */     this.field_147916_f.renderBlockAsItem(var2, 0, 1.0F);
/* 140:155 */     GL11.glPopMatrix();
/* 141:156 */     this.field_147916_f.unlockBlockBounds();
/* 142:157 */     this.field_147916_f.clearOverrideBlockTexture();
/* 143:158 */     GL11.glPopMatrix();
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void func_82402_b(EntityItemFrame par1EntityItemFrame)
/* 147:    */   {
/* 148:163 */     ItemStack var2 = par1EntityItemFrame.getDisplayedItem();
/* 149:165 */     if (var2 != null)
/* 150:    */     {
/* 151:167 */       EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, var2);
/* 152:168 */       Item var4 = var3.getEntityItem().getItem();
/* 153:169 */       var3.getEntityItem().stackSize = 1;
/* 154:170 */       var3.hoverStart = 0.0F;
/* 155:171 */       GL11.glPushMatrix();
/* 156:172 */       GL11.glTranslatef(-0.453125F * net.minecraft.util.Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18F, -0.453125F * net.minecraft.util.Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
/* 157:173 */       GL11.glRotatef(180.0F + par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 158:174 */       GL11.glRotatef(-90 * par1EntityItemFrame.getRotation(), 0.0F, 0.0F, 1.0F);
/* 159:176 */       switch (par1EntityItemFrame.getRotation())
/* 160:    */       {
/* 161:    */       case 1: 
/* 162:179 */         GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
/* 163:180 */         break;
/* 164:    */       case 2: 
/* 165:183 */         GL11.glTranslatef(0.0F, -0.32F, 0.0F);
/* 166:184 */         break;
/* 167:    */       case 3: 
/* 168:187 */         GL11.glTranslatef(0.16F, -0.16F, 0.0F);
/* 169:    */       }
/* 170:190 */       if (var4 == Items.filled_map)
/* 171:    */       {
/* 172:192 */         this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
/* 173:193 */         Tessellator var5 = Tessellator.instance;
/* 174:194 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 175:195 */         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 176:196 */         float var6 = 0.007813F;
/* 177:197 */         GL11.glScalef(var6, var6, var6);
/* 178:199 */         switch (par1EntityItemFrame.getRotation())
/* 179:    */         {
/* 180:    */         case 0: 
/* 181:202 */           GL11.glTranslatef(-64.0F, -87.0F, -1.5F);
/* 182:203 */           break;
/* 183:    */         case 1: 
/* 184:206 */           GL11.glTranslatef(-66.5F, -84.5F, -1.5F);
/* 185:207 */           break;
/* 186:    */         case 2: 
/* 187:210 */           GL11.glTranslatef(-64.0F, -82.0F, -1.5F);
/* 188:211 */           break;
/* 189:    */         case 3: 
/* 190:214 */           GL11.glTranslatef(-61.5F, -84.5F, -1.5F);
/* 191:    */         }
/* 192:217 */         GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 193:218 */         MapData var7 = Items.filled_map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
/* 194:219 */         GL11.glTranslatef(0.0F, 0.0F, -1.0F);
/* 195:221 */         if (var7 != null) {
/* 196:223 */           this.field_147917_g.entityRenderer.getMapItemRenderer().func_148250_a(var7, true);
/* 197:    */         }
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:228 */         if (var4 == Items.compass)
/* 202:    */         {
/* 203:230 */           TextureManager var12 = Minecraft.getMinecraft().getTextureManager();
/* 204:231 */           var12.bindTexture(TextureMap.locationItemsTexture);
/* 205:232 */           TextureAtlasSprite var14 = ((TextureMap)var12.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());
/* 206:234 */           if ((var14 instanceof TextureCompass))
/* 207:    */           {
/* 208:236 */             TextureCompass var15 = (TextureCompass)var14;
/* 209:237 */             double var8 = var15.currentAngle;
/* 210:238 */             double var10 = var15.angleDelta;
/* 211:239 */             var15.currentAngle = 0.0D;
/* 212:240 */             var15.angleDelta = 0.0D;
/* 213:241 */             var15.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, MathHelper.wrapAngleTo180_float(180 + par1EntityItemFrame.hangingDirection * 90), false, true);
/* 214:242 */             var15.currentAngle = var8;
/* 215:243 */             var15.angleDelta = var10;
/* 216:    */           }
/* 217:    */         }
/* 218:247 */         net.minecraft.client.renderer.entity.RenderItem.renderInFrame = true;
/* 219:248 */         RenderManager.instance.func_147940_a(var3, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
/* 220:249 */         net.minecraft.client.renderer.entity.RenderItem.renderInFrame = false;
/* 221:251 */         if (var4 == Items.compass)
/* 222:    */         {
/* 223:253 */           TextureAtlasSprite var13 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Items.compass.getIconIndex(var3.getEntityItem()).getIconName());
/* 224:255 */           if (var13.getFrameCount() > 0) {
/* 225:257 */             var13.updateAnimation();
/* 226:    */           }
/* 227:    */         }
/* 228:    */       }
/* 229:262 */       GL11.glPopMatrix();
/* 230:    */     }
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected void func_147914_a(EntityItemFrame p_147914_1_, double p_147914_2_, double p_147914_4_, double p_147914_6_)
/* 234:    */   {
/* 235:268 */     if ((Minecraft.isGuiEnabled()) && (p_147914_1_.getDisplayedItem() != null) && (p_147914_1_.getDisplayedItem().hasDisplayName()) && (this.renderManager.field_147941_i == p_147914_1_))
/* 236:    */     {
/* 237:270 */       float var8 = 1.6F;
/* 238:271 */       float var9 = 0.01666667F * var8;
/* 239:272 */       double var10 = p_147914_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
/* 240:273 */       float var12 = p_147914_1_.isSneaking() ? 32.0F : 64.0F;
/* 241:275 */       if (var10 < var12 * var12)
/* 242:    */       {
/* 243:277 */         String var13 = p_147914_1_.getDisplayedItem().getDisplayName();
/* 244:279 */         if (p_147914_1_.isSneaking())
/* 245:    */         {
/* 246:281 */           FontRenderer var14 = getFontRendererFromRenderManager();
/* 247:282 */           GL11.glPushMatrix();
/* 248:283 */           GL11.glTranslatef((float)p_147914_2_ + 0.0F, (float)p_147914_4_ + p_147914_1_.height + 0.5F, (float)p_147914_6_);
/* 249:284 */           GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/* 250:285 */           GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
/* 251:286 */           GL11.glRotatef(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
/* 252:287 */           GL11.glScalef(-var9, -var9, var9);
/* 253:288 */           GL11.glDisable(2896);
/* 254:289 */           GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
/* 255:290 */           GL11.glDepthMask(false);
/* 256:291 */           GL11.glEnable(3042);
/* 257:292 */           GL11.glBlendFunc(770, 771);
/* 258:293 */           Tessellator var15 = Tessellator.instance;
/* 259:294 */           GL11.glDisable(3553);
/* 260:295 */           var15.startDrawingQuads();
/* 261:296 */           int var16 = var14.getStringWidth(var13) / 2;
/* 262:297 */           var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
/* 263:298 */           var15.addVertex(-var16 - 1, -1.0D, 0.0D);
/* 264:299 */           var15.addVertex(-var16 - 1, 8.0D, 0.0D);
/* 265:300 */           var15.addVertex(var16 + 1, 8.0D, 0.0D);
/* 266:301 */           var15.addVertex(var16 + 1, -1.0D, 0.0D);
/* 267:302 */           var15.draw();
/* 268:303 */           GL11.glEnable(3553);
/* 269:304 */           GL11.glDepthMask(true);
/* 270:305 */           var14.drawString(var13, -var14.getStringWidth(var13) / 2, 0, 553648127);
/* 271:306 */           GL11.glEnable(2896);
/* 272:307 */           GL11.glDisable(3042);
/* 273:308 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 274:309 */           GL11.glPopMatrix();
/* 275:    */         }
/* 276:    */       }
/* 277:    */     }
/* 278:    */   }
/* 279:    */   
/* 280:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 281:    */   {
/* 282:324 */     return getEntityTexture((EntityItemFrame)par1Entity);
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 286:    */   {
/* 287:335 */     doRender((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
/* 288:    */   }
/* 289:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.tileentity.RenderItemFrame
 * JD-Core Version:    0.7.0.1
 */