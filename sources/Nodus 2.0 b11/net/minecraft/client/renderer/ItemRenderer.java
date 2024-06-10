/*   1:    */ package net.minecraft.client.renderer;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.BlockFire;
/*   5:    */ import net.minecraft.block.material.Material;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   8:    */ import net.minecraft.client.entity.EntityPlayerSP;
/*   9:    */ import net.minecraft.client.gui.MapItemRenderer;
/*  10:    */ import net.minecraft.client.multiplayer.WorldClient;
/*  11:    */ import net.minecraft.client.renderer.entity.Render;
/*  12:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  13:    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*  17:    */ import net.minecraft.entity.EntityLivingBase;
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.init.Blocks;
/*  20:    */ import net.minecraft.init.Items;
/*  21:    */ import net.minecraft.item.EnumAction;
/*  22:    */ import net.minecraft.item.Item;
/*  23:    */ import net.minecraft.item.ItemBlock;
/*  24:    */ import net.minecraft.item.ItemCloth;
/*  25:    */ import net.minecraft.item.ItemMap;
/*  26:    */ import net.minecraft.item.ItemStack;
/*  27:    */ import net.minecraft.util.IIcon;
/*  28:    */ import net.minecraft.util.MathHelper;
/*  29:    */ import net.minecraft.util.ResourceLocation;
/*  30:    */ import net.minecraft.world.storage.MapData;
/*  31:    */ import org.lwjgl.opengl.GL11;
/*  32:    */ 
/*  33:    */ public class ItemRenderer
/*  34:    */ {
/*  35: 31 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  36: 32 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  37: 33 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*  38:    */   private Minecraft mc;
/*  39:    */   private ItemStack itemToRender;
/*  40:    */   private float equippedProgress;
/*  41:    */   private float prevEquippedProgress;
/*  42: 44 */   private RenderBlocks renderBlocksIr = new RenderBlocks();
/*  43: 47 */   private int equippedItemSlot = -1;
/*  44:    */   private static final String __OBFID = "CL_00000953";
/*  45:    */   
/*  46:    */   public ItemRenderer(Minecraft par1Minecraft)
/*  47:    */   {
/*  48: 52 */     this.mc = par1Minecraft;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3)
/*  52:    */   {
/*  53: 60 */     GL11.glPushMatrix();
/*  54: 61 */     TextureManager var4 = this.mc.getTextureManager();
/*  55: 62 */     Item var5 = par2ItemStack.getItem();
/*  56: 63 */     Block var6 = Block.getBlockFromItem(var5);
/*  57: 65 */     if ((par2ItemStack.getItemSpriteNumber() == 0) && ((var5 instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(var6.getRenderType())))
/*  58:    */     {
/*  59: 67 */       var4.bindTexture(var4.getResourceLocation(0));
/*  60: 69 */       if ((par2ItemStack != null) && ((par2ItemStack.getItem() instanceof ItemCloth)))
/*  61:    */       {
/*  62: 71 */         GL11.glEnable(3042);
/*  63: 72 */         GL11.glDepthMask(false);
/*  64: 73 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  65: 74 */         this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0F);
/*  66: 75 */         GL11.glDepthMask(true);
/*  67: 76 */         GL11.glDisable(3042);
/*  68:    */       }
/*  69:    */       else
/*  70:    */       {
/*  71: 80 */         this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0F);
/*  72:    */       }
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76: 85 */       IIcon var7 = par1EntityLivingBase.getItemIcon(par2ItemStack, par3);
/*  77: 87 */       if (var7 == null)
/*  78:    */       {
/*  79: 89 */         GL11.glPopMatrix();
/*  80: 90 */         return;
/*  81:    */       }
/*  82: 93 */       var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
/*  83: 94 */       TextureUtil.func_147950_a(false, false);
/*  84: 95 */       Tessellator var8 = Tessellator.instance;
/*  85: 96 */       float var9 = var7.getMinU();
/*  86: 97 */       float var10 = var7.getMaxU();
/*  87: 98 */       float var11 = var7.getMinV();
/*  88: 99 */       float var12 = var7.getMaxV();
/*  89:100 */       float var13 = 0.0F;
/*  90:101 */       float var14 = 0.3F;
/*  91:102 */       GL11.glEnable(32826);
/*  92:103 */       GL11.glTranslatef(-var13, -var14, 0.0F);
/*  93:104 */       float var15 = 1.5F;
/*  94:105 */       GL11.glScalef(var15, var15, var15);
/*  95:106 */       GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/*  96:107 */       GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
/*  97:108 */       GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
/*  98:109 */       renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625F);
/*  99:111 */       if ((par2ItemStack.hasEffect()) && (par3 == 0))
/* 100:    */       {
/* 101:113 */         GL11.glDepthFunc(514);
/* 102:114 */         GL11.glDisable(2896);
/* 103:115 */         var4.bindTexture(RES_ITEM_GLINT);
/* 104:116 */         GL11.glEnable(3042);
/* 105:117 */         OpenGlHelper.glBlendFunc(768, 1, 1, 0);
/* 106:118 */         float var16 = 0.76F;
/* 107:119 */         GL11.glColor4f(0.5F * var16, 0.25F * var16, 0.8F * var16, 1.0F);
/* 108:120 */         GL11.glMatrixMode(5890);
/* 109:121 */         GL11.glPushMatrix();
/* 110:122 */         float var17 = 0.125F;
/* 111:123 */         GL11.glScalef(var17, var17, var17);
/* 112:124 */         float var18 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
/* 113:125 */         GL11.glTranslatef(var18, 0.0F, 0.0F);
/* 114:126 */         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/* 115:127 */         renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
/* 116:128 */         GL11.glPopMatrix();
/* 117:129 */         GL11.glPushMatrix();
/* 118:130 */         GL11.glScalef(var17, var17, var17);
/* 119:131 */         var18 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
/* 120:132 */         GL11.glTranslatef(-var18, 0.0F, 0.0F);
/* 121:133 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 122:134 */         renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
/* 123:135 */         GL11.glPopMatrix();
/* 124:136 */         GL11.glMatrixMode(5888);
/* 125:137 */         GL11.glDisable(3042);
/* 126:138 */         GL11.glEnable(2896);
/* 127:139 */         GL11.glDepthFunc(515);
/* 128:    */       }
/* 129:142 */       GL11.glDisable(32826);
/* 130:143 */       var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
/* 131:144 */       TextureUtil.func_147945_b();
/* 132:    */     }
/* 133:147 */     GL11.glPopMatrix();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, int par5, int par6, float par7)
/* 137:    */   {
/* 138:155 */     par0Tessellator.startDrawingQuads();
/* 139:156 */     par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
/* 140:157 */     par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, par1, par4);
/* 141:158 */     par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, par3, par4);
/* 142:159 */     par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, par3, par2);
/* 143:160 */     par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, par1, par2);
/* 144:161 */     par0Tessellator.draw();
/* 145:162 */     par0Tessellator.startDrawingQuads();
/* 146:163 */     par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
/* 147:164 */     par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - par7, par1, par2);
/* 148:165 */     par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0F - par7, par3, par2);
/* 149:166 */     par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0F - par7, par3, par4);
/* 150:167 */     par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - par7, par1, par4);
/* 151:168 */     par0Tessellator.draw();
/* 152:169 */     float var8 = 0.5F * (par1 - par3) / par5;
/* 153:170 */     float var9 = 0.5F * (par4 - par2) / par6;
/* 154:171 */     par0Tessellator.startDrawingQuads();
/* 155:172 */     par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
/* 156:177 */     for (int var10 = 0; var10 < par5; var10++)
/* 157:    */     {
/* 158:179 */       float var11 = var10 / par5;
/* 159:180 */       float var12 = par1 + (par3 - par1) * var11 - var8;
/* 160:181 */       par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0F - par7, var12, par4);
/* 161:182 */       par0Tessellator.addVertexWithUV(var11, 0.0D, 0.0D, var12, par4);
/* 162:183 */       par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0D, var12, par2);
/* 163:184 */       par0Tessellator.addVertexWithUV(var11, 1.0D, 0.0F - par7, var12, par2);
/* 164:    */     }
/* 165:187 */     par0Tessellator.draw();
/* 166:188 */     par0Tessellator.startDrawingQuads();
/* 167:189 */     par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
/* 168:192 */     for (var10 = 0; var10 < par5; var10++)
/* 169:    */     {
/* 170:194 */       float var11 = var10 / par5;
/* 171:195 */       float var12 = par1 + (par3 - par1) * var11 - var8;
/* 172:196 */       float var13 = var11 + 1.0F / par5;
/* 173:197 */       par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0F - par7, var12, par2);
/* 174:198 */       par0Tessellator.addVertexWithUV(var13, 1.0D, 0.0D, var12, par2);
/* 175:199 */       par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0D, var12, par4);
/* 176:200 */       par0Tessellator.addVertexWithUV(var13, 0.0D, 0.0F - par7, var12, par4);
/* 177:    */     }
/* 178:203 */     par0Tessellator.draw();
/* 179:204 */     par0Tessellator.startDrawingQuads();
/* 180:205 */     par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);
/* 181:207 */     for (var10 = 0; var10 < par6; var10++)
/* 182:    */     {
/* 183:209 */       float var11 = var10 / par6;
/* 184:210 */       float var12 = par4 + (par2 - par4) * var11 - var9;
/* 185:211 */       float var13 = var11 + 1.0F / par6;
/* 186:212 */       par0Tessellator.addVertexWithUV(0.0D, var13, 0.0D, par1, var12);
/* 187:213 */       par0Tessellator.addVertexWithUV(1.0D, var13, 0.0D, par3, var12);
/* 188:214 */       par0Tessellator.addVertexWithUV(1.0D, var13, 0.0F - par7, par3, var12);
/* 189:215 */       par0Tessellator.addVertexWithUV(0.0D, var13, 0.0F - par7, par1, var12);
/* 190:    */     }
/* 191:218 */     par0Tessellator.draw();
/* 192:219 */     par0Tessellator.startDrawingQuads();
/* 193:220 */     par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);
/* 194:222 */     for (var10 = 0; var10 < par6; var10++)
/* 195:    */     {
/* 196:224 */       float var11 = var10 / par6;
/* 197:225 */       float var12 = par4 + (par2 - par4) * var11 - var9;
/* 198:226 */       par0Tessellator.addVertexWithUV(1.0D, var11, 0.0D, par3, var12);
/* 199:227 */       par0Tessellator.addVertexWithUV(0.0D, var11, 0.0D, par1, var12);
/* 200:228 */       par0Tessellator.addVertexWithUV(0.0D, var11, 0.0F - par7, par1, var12);
/* 201:229 */       par0Tessellator.addVertexWithUV(1.0D, var11, 0.0F - par7, par3, var12);
/* 202:    */     }
/* 203:232 */     par0Tessellator.draw();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void renderItemInFirstPerson(float par1)
/* 207:    */   {
/* 208:240 */     float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
/* 209:241 */     EntityClientPlayerMP var3 = this.mc.thePlayer;
/* 210:242 */     float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
/* 211:243 */     GL11.glPushMatrix();
/* 212:244 */     GL11.glRotatef(var4, 1.0F, 0.0F, 0.0F);
/* 213:245 */     GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0F, 1.0F, 0.0F);
/* 214:246 */     RenderHelper.enableStandardItemLighting();
/* 215:247 */     GL11.glPopMatrix();
/* 216:248 */     EntityPlayerSP var5 = var3;
/* 217:249 */     float var6 = var5.prevRenderArmPitch + (var5.renderArmPitch - var5.prevRenderArmPitch) * par1;
/* 218:250 */     float var7 = var5.prevRenderArmYaw + (var5.renderArmYaw - var5.prevRenderArmYaw) * par1;
/* 219:251 */     GL11.glRotatef((var3.rotationPitch - var6) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 220:252 */     GL11.glRotatef((var3.rotationYaw - var7) * 0.1F, 0.0F, 1.0F, 0.0F);
/* 221:253 */     ItemStack var8 = this.itemToRender;
/* 222:255 */     if ((var8 != null) && ((var8.getItem() instanceof ItemCloth)))
/* 223:    */     {
/* 224:257 */       GL11.glEnable(3042);
/* 225:258 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 226:    */     }
/* 227:261 */     int var9 = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
/* 228:262 */     int var10 = var9 % 65536;
/* 229:263 */     int var11 = var9 / 65536;
/* 230:264 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0F, var11 / 1.0F);
/* 231:265 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 232:270 */     if (var8 != null)
/* 233:    */     {
/* 234:272 */       int var12 = var8.getItem().getColorFromItemStack(var8, 0);
/* 235:273 */       float var13 = (var12 >> 16 & 0xFF) / 255.0F;
/* 236:274 */       float var14 = (var12 >> 8 & 0xFF) / 255.0F;
/* 237:275 */       float var15 = (var12 & 0xFF) / 255.0F;
/* 238:276 */       GL11.glColor4f(var13, var14, var15, 1.0F);
/* 239:    */     }
/* 240:    */     else
/* 241:    */     {
/* 242:280 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 243:    */     }
/* 244:290 */     if ((var8 != null) && (var8.getItem() == Items.filled_map))
/* 245:    */     {
/* 246:292 */       GL11.glPushMatrix();
/* 247:293 */       float var22 = 0.8F;
/* 248:294 */       float var13 = var3.getSwingProgress(par1);
/* 249:295 */       float var14 = MathHelper.sin(var13 * 3.141593F);
/* 250:296 */       float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F);
/* 251:297 */       GL11.glTranslatef(-var15 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F * 2.0F) * 0.2F, -var14 * 0.2F);
/* 252:298 */       var13 = 1.0F - var4 / 45.0F + 0.1F;
/* 253:300 */       if (var13 < 0.0F) {
/* 254:302 */         var13 = 0.0F;
/* 255:    */       }
/* 256:305 */       if (var13 > 1.0F) {
/* 257:307 */         var13 = 1.0F;
/* 258:    */       }
/* 259:310 */       var13 = -MathHelper.cos(var13 * 3.141593F) * 0.5F + 0.5F;
/* 260:311 */       GL11.glTranslatef(0.0F, 0.0F * var22 - (1.0F - var2) * 1.2F - var13 * 0.5F + 0.04F, -0.9F * var22);
/* 261:312 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 262:313 */       GL11.glRotatef(var13 * -85.0F, 0.0F, 0.0F, 1.0F);
/* 263:314 */       GL11.glEnable(32826);
/* 264:315 */       this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
/* 265:317 */       for (int var24 = 0; var24 < 2; var24++)
/* 266:    */       {
/* 267:319 */         int var28 = var24 * 2 - 1;
/* 268:320 */         GL11.glPushMatrix();
/* 269:321 */         GL11.glTranslatef(-0.0F, -0.6F, 1.1F * var28);
/* 270:322 */         GL11.glRotatef(-45 * var28, 1.0F, 0.0F, 0.0F);
/* 271:323 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 272:324 */         GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
/* 273:325 */         GL11.glRotatef(-65 * var28, 0.0F, 1.0F, 0.0F);
/* 274:326 */         Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
/* 275:327 */         RenderPlayer var25 = (RenderPlayer)var26;
/* 276:328 */         float var18 = 1.0F;
/* 277:329 */         GL11.glScalef(var18, var18, var18);
/* 278:330 */         var25.renderFirstPersonArm(this.mc.thePlayer);
/* 279:331 */         GL11.glPopMatrix();
/* 280:    */       }
/* 281:334 */       var14 = var3.getSwingProgress(par1);
/* 282:335 */       var15 = MathHelper.sin(var14 * var14 * 3.141593F);
/* 283:336 */       float var16 = MathHelper.sin(MathHelper.sqrt_float(var14) * 3.141593F);
/* 284:337 */       GL11.glRotatef(-var15 * 20.0F, 0.0F, 1.0F, 0.0F);
/* 285:338 */       GL11.glRotatef(-var16 * 20.0F, 0.0F, 0.0F, 1.0F);
/* 286:339 */       GL11.glRotatef(-var16 * 80.0F, 1.0F, 0.0F, 0.0F);
/* 287:340 */       float var17 = 0.38F;
/* 288:341 */       GL11.glScalef(var17, var17, var17);
/* 289:342 */       GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 290:343 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 291:344 */       GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
/* 292:345 */       float var18 = 0.015625F;
/* 293:346 */       GL11.glScalef(var18, var18, var18);
/* 294:347 */       this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 295:348 */       Tessellator var31 = Tessellator.instance;
/* 296:349 */       GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 297:350 */       var31.startDrawingQuads();
/* 298:351 */       byte var30 = 7;
/* 299:352 */       var31.addVertexWithUV(0 - var30, 128 + var30, 0.0D, 0.0D, 1.0D);
/* 300:353 */       var31.addVertexWithUV(128 + var30, 128 + var30, 0.0D, 1.0D, 1.0D);
/* 301:354 */       var31.addVertexWithUV(128 + var30, 0 - var30, 0.0D, 1.0D, 0.0D);
/* 302:355 */       var31.addVertexWithUV(0 - var30, 0 - var30, 0.0D, 0.0D, 0.0D);
/* 303:356 */       var31.draw();
/* 304:357 */       MapData var21 = Items.filled_map.getMapData(var8, this.mc.theWorld);
/* 305:359 */       if (var21 != null) {
/* 306:361 */         this.mc.entityRenderer.getMapItemRenderer().func_148250_a(var21, false);
/* 307:    */       }
/* 308:364 */       GL11.glPopMatrix();
/* 309:    */     }
/* 310:366 */     else if (var8 != null)
/* 311:    */     {
/* 312:368 */       GL11.glPushMatrix();
/* 313:369 */       float var22 = 0.8F;
/* 314:371 */       if (var3.getItemInUseCount() > 0)
/* 315:    */       {
/* 316:373 */         EnumAction var23 = var8.getItemUseAction();
/* 317:375 */         if ((var23 == EnumAction.eat) || (var23 == EnumAction.drink))
/* 318:    */         {
/* 319:377 */           float var14 = var3.getItemInUseCount() - par1 + 1.0F;
/* 320:378 */           float var15 = 1.0F - var14 / var8.getMaxItemUseDuration();
/* 321:379 */           float var16 = 1.0F - var15;
/* 322:380 */           var16 = var16 * var16 * var16;
/* 323:381 */           var16 = var16 * var16 * var16;
/* 324:382 */           var16 = var16 * var16 * var16;
/* 325:383 */           float var17 = 1.0F - var16;
/* 326:384 */           GL11.glTranslatef(0.0F, MathHelper.abs(MathHelper.cos(var14 / 4.0F * 3.141593F) * 0.1F) * (var15 > 0.2D ? 1 : 0), 0.0F);
/* 327:385 */           GL11.glTranslatef(var17 * 0.6F, -var17 * 0.5F, 0.0F);
/* 328:386 */           GL11.glRotatef(var17 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 329:387 */           GL11.glRotatef(var17 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 330:388 */           GL11.glRotatef(var17 * 30.0F, 0.0F, 0.0F, 1.0F);
/* 331:    */         }
/* 332:    */       }
/* 333:    */       else
/* 334:    */       {
/* 335:393 */         float var13 = var3.getSwingProgress(par1);
/* 336:394 */         float var14 = MathHelper.sin(var13 * 3.141593F);
/* 337:395 */         float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F);
/* 338:396 */         GL11.glTranslatef(-var15 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F * 2.0F) * 0.2F, -var14 * 0.2F);
/* 339:    */       }
/* 340:399 */       GL11.glTranslatef(0.7F * var22, -0.65F * var22 - (1.0F - var2) * 0.6F, -0.9F * var22);
/* 341:400 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 342:401 */       GL11.glEnable(32826);
/* 343:402 */       float var13 = var3.getSwingProgress(par1);
/* 344:403 */       float var14 = MathHelper.sin(var13 * var13 * 3.141593F);
/* 345:404 */       float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F);
/* 346:405 */       GL11.glRotatef(-var14 * 20.0F, 0.0F, 1.0F, 0.0F);
/* 347:406 */       GL11.glRotatef(-var15 * 20.0F, 0.0F, 0.0F, 1.0F);
/* 348:407 */       GL11.glRotatef(-var15 * 80.0F, 1.0F, 0.0F, 0.0F);
/* 349:408 */       float var16 = 0.4F;
/* 350:409 */       GL11.glScalef(var16, var16, var16);
/* 351:413 */       if (var3.getItemInUseCount() > 0)
/* 352:    */       {
/* 353:415 */         EnumAction var29 = var8.getItemUseAction();
/* 354:417 */         if (var29 == EnumAction.block)
/* 355:    */         {
/* 356:419 */           GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
/* 357:420 */           GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
/* 358:421 */           GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
/* 359:422 */           GL11.glRotatef(60.0F, 0.0F, 1.0F, 0.0F);
/* 360:    */         }
/* 361:424 */         else if (var29 == EnumAction.bow)
/* 362:    */         {
/* 363:426 */           GL11.glRotatef(-18.0F, 0.0F, 0.0F, 1.0F);
/* 364:427 */           GL11.glRotatef(-12.0F, 0.0F, 1.0F, 0.0F);
/* 365:428 */           GL11.glRotatef(-8.0F, 1.0F, 0.0F, 0.0F);
/* 366:429 */           GL11.glTranslatef(-0.9F, 0.2F, 0.0F);
/* 367:430 */           float var18 = var8.getMaxItemUseDuration() - (var3.getItemInUseCount() - par1 + 1.0F);
/* 368:431 */           float var19 = var18 / 20.0F;
/* 369:432 */           var19 = (var19 * var19 + var19 * 2.0F) / 3.0F;
/* 370:434 */           if (var19 > 1.0F) {
/* 371:436 */             var19 = 1.0F;
/* 372:    */           }
/* 373:439 */           if (var19 > 0.1F) {
/* 374:441 */             GL11.glTranslatef(0.0F, MathHelper.sin((var18 - 0.1F) * 1.3F) * 0.01F * (var19 - 0.1F), 0.0F);
/* 375:    */           }
/* 376:444 */           GL11.glTranslatef(0.0F, 0.0F, var19 * 0.1F);
/* 377:445 */           GL11.glRotatef(-335.0F, 0.0F, 0.0F, 1.0F);
/* 378:446 */           GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
/* 379:447 */           GL11.glTranslatef(0.0F, 0.5F, 0.0F);
/* 380:448 */           float var20 = 1.0F + var19 * 0.2F;
/* 381:449 */           GL11.glScalef(1.0F, 1.0F, var20);
/* 382:450 */           GL11.glTranslatef(0.0F, -0.5F, 0.0F);
/* 383:451 */           GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 384:452 */           GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
/* 385:    */         }
/* 386:    */       }
/* 387:456 */       if (var8.getItem().shouldRotateAroundWhenRendering()) {
/* 388:458 */         GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 389:    */       }
/* 390:461 */       if (var8.getItem().requiresMultipleRenderPasses())
/* 391:    */       {
/* 392:463 */         renderItem(var3, var8, 0);
/* 393:464 */         int var27 = var8.getItem().getColorFromItemStack(var8, 1);
/* 394:465 */         float var18 = (var27 >> 16 & 0xFF) / 255.0F;
/* 395:466 */         float var19 = (var27 >> 8 & 0xFF) / 255.0F;
/* 396:467 */         float var20 = (var27 & 0xFF) / 255.0F;
/* 397:468 */         GL11.glColor4f(1.0F * var18, 1.0F * var19, 1.0F * var20, 1.0F);
/* 398:469 */         renderItem(var3, var8, 1);
/* 399:    */       }
/* 400:    */       else
/* 401:    */       {
/* 402:473 */         renderItem(var3, var8, 0);
/* 403:    */       }
/* 404:476 */       GL11.glPopMatrix();
/* 405:    */     }
/* 406:478 */     else if (!var3.isInvisible())
/* 407:    */     {
/* 408:480 */       GL11.glPushMatrix();
/* 409:481 */       float var22 = 0.8F;
/* 410:482 */       float var13 = var3.getSwingProgress(par1);
/* 411:483 */       float var14 = MathHelper.sin(var13 * 3.141593F);
/* 412:484 */       float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F);
/* 413:485 */       GL11.glTranslatef(-var15 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F * 2.0F) * 0.4F, -var14 * 0.4F);
/* 414:486 */       GL11.glTranslatef(0.8F * var22, -0.75F * var22 - (1.0F - var2) * 0.6F, -0.9F * var22);
/* 415:487 */       GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 416:488 */       GL11.glEnable(32826);
/* 417:489 */       var13 = var3.getSwingProgress(par1);
/* 418:490 */       var14 = MathHelper.sin(var13 * var13 * 3.141593F);
/* 419:491 */       var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.141593F);
/* 420:492 */       GL11.glRotatef(var15 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 421:493 */       GL11.glRotatef(-var14 * 20.0F, 0.0F, 0.0F, 1.0F);
/* 422:494 */       this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
/* 423:495 */       GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
/* 424:496 */       GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
/* 425:497 */       GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
/* 426:498 */       GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
/* 427:499 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/* 428:500 */       GL11.glTranslatef(5.6F, 0.0F, 0.0F);
/* 429:501 */       Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
/* 430:502 */       RenderPlayer var25 = (RenderPlayer)var26;
/* 431:503 */       float var18 = 1.0F;
/* 432:504 */       GL11.glScalef(var18, var18, var18);
/* 433:505 */       var25.renderFirstPersonArm(this.mc.thePlayer);
/* 434:506 */       GL11.glPopMatrix();
/* 435:    */     }
/* 436:509 */     if ((var8 != null) && ((var8.getItem() instanceof ItemCloth))) {
/* 437:511 */       GL11.glDisable(3042);
/* 438:    */     }
/* 439:514 */     GL11.glDisable(32826);
/* 440:515 */     RenderHelper.disableStandardItemLighting();
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void renderOverlays(float par1)
/* 444:    */   {
/* 445:523 */     GL11.glDisable(3008);
/* 446:525 */     if (this.mc.thePlayer.isBurning()) {
/* 447:527 */       renderFireInFirstPerson(par1);
/* 448:    */     }
/* 449:530 */     if (this.mc.thePlayer.isEntityInsideOpaqueBlock())
/* 450:    */     {
/* 451:532 */       int var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
/* 452:533 */       int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
/* 453:534 */       int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
/* 454:535 */       Block var5 = this.mc.theWorld.getBlock(var2, var3, var4);
/* 455:537 */       if (this.mc.theWorld.getBlock(var2, var3, var4).isNormalCube()) {
/* 456:539 */         renderInsideOfBlock(par1, var5.getBlockTextureFromSide(2));
/* 457:    */       } else {
/* 458:543 */         for (int var6 = 0; var6 < 8; var6++)
/* 459:    */         {
/* 460:545 */           float var7 = ((var6 >> 0) % 2 - 0.5F) * this.mc.thePlayer.width * 0.9F;
/* 461:546 */           float var8 = ((var6 >> 1) % 2 - 0.5F) * this.mc.thePlayer.height * 0.2F;
/* 462:547 */           float var9 = ((var6 >> 2) % 2 - 0.5F) * this.mc.thePlayer.width * 0.9F;
/* 463:548 */           int var10 = MathHelper.floor_float(var2 + var7);
/* 464:549 */           int var11 = MathHelper.floor_float(var3 + var8);
/* 465:550 */           int var12 = MathHelper.floor_float(var4 + var9);
/* 466:552 */           if (this.mc.theWorld.getBlock(var10, var11, var12).isNormalCube()) {
/* 467:554 */             var5 = this.mc.theWorld.getBlock(var10, var11, var12);
/* 468:    */           }
/* 469:    */         }
/* 470:    */       }
/* 471:559 */       if (var5.getMaterial() != Material.air) {
/* 472:561 */         renderInsideOfBlock(par1, var5.getBlockTextureFromSide(2));
/* 473:    */       }
/* 474:    */     }
/* 475:565 */     if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
/* 476:567 */       renderWarpedTextureOverlay(par1);
/* 477:    */     }
/* 478:570 */     GL11.glEnable(3008);
/* 479:    */   }
/* 480:    */   
/* 481:    */   private void renderInsideOfBlock(float par1, IIcon par2Icon)
/* 482:    */   {
/* 483:578 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 484:579 */     Tessellator var3 = Tessellator.instance;
/* 485:580 */     float var4 = 0.1F;
/* 486:581 */     GL11.glColor4f(var4, var4, var4, 0.5F);
/* 487:582 */     GL11.glPushMatrix();
/* 488:583 */     float var5 = -1.0F;
/* 489:584 */     float var6 = 1.0F;
/* 490:585 */     float var7 = -1.0F;
/* 491:586 */     float var8 = 1.0F;
/* 492:587 */     float var9 = -0.5F;
/* 493:588 */     float var10 = par2Icon.getMinU();
/* 494:589 */     float var11 = par2Icon.getMaxU();
/* 495:590 */     float var12 = par2Icon.getMinV();
/* 496:591 */     float var13 = par2Icon.getMaxV();
/* 497:592 */     var3.startDrawingQuads();
/* 498:593 */     var3.addVertexWithUV(var5, var7, var9, var11, var13);
/* 499:594 */     var3.addVertexWithUV(var6, var7, var9, var10, var13);
/* 500:595 */     var3.addVertexWithUV(var6, var8, var9, var10, var12);
/* 501:596 */     var3.addVertexWithUV(var5, var8, var9, var11, var12);
/* 502:597 */     var3.draw();
/* 503:598 */     GL11.glPopMatrix();
/* 504:599 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 505:    */   }
/* 506:    */   
/* 507:    */   private void renderWarpedTextureOverlay(float par1)
/* 508:    */   {
/* 509:608 */     this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 510:609 */     Tessellator var2 = Tessellator.instance;
/* 511:610 */     float var3 = this.mc.thePlayer.getBrightness(par1);
/* 512:611 */     GL11.glColor4f(var3, var3, var3, 0.5F);
/* 513:612 */     GL11.glEnable(3042);
/* 514:613 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 515:614 */     GL11.glPushMatrix();
/* 516:615 */     float var4 = 4.0F;
/* 517:616 */     float var5 = -1.0F;
/* 518:617 */     float var6 = 1.0F;
/* 519:618 */     float var7 = -1.0F;
/* 520:619 */     float var8 = 1.0F;
/* 521:620 */     float var9 = -0.5F;
/* 522:621 */     float var10 = -this.mc.thePlayer.rotationYaw / 64.0F;
/* 523:622 */     float var11 = this.mc.thePlayer.rotationPitch / 64.0F;
/* 524:623 */     var2.startDrawingQuads();
/* 525:624 */     var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
/* 526:625 */     var2.addVertexWithUV(var6, var7, var9, 0.0F + var10, var4 + var11);
/* 527:626 */     var2.addVertexWithUV(var6, var8, var9, 0.0F + var10, 0.0F + var11);
/* 528:627 */     var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0F + var11);
/* 529:628 */     var2.draw();
/* 530:629 */     GL11.glPopMatrix();
/* 531:630 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 532:631 */     GL11.glDisable(3042);
/* 533:    */   }
/* 534:    */   
/* 535:    */   private void renderFireInFirstPerson(float par1)
/* 536:    */   {
/* 537:639 */     Tessellator var2 = Tessellator.instance;
/* 538:640 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.9F);
/* 539:641 */     GL11.glEnable(3042);
/* 540:642 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 541:643 */     float var3 = 1.0F;
/* 542:645 */     for (int var4 = 0; var4 < 2; var4++)
/* 543:    */     {
/* 544:647 */       GL11.glPushMatrix();
/* 545:648 */       IIcon var5 = Blocks.fire.func_149840_c(1);
/* 546:649 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 547:650 */       float var6 = var5.getMinU();
/* 548:651 */       float var7 = var5.getMaxU();
/* 549:652 */       float var8 = var5.getMinV();
/* 550:653 */       float var9 = var5.getMaxV();
/* 551:654 */       float var10 = (0.0F - var3) / 2.0F;
/* 552:655 */       float var11 = var10 + var3;
/* 553:656 */       float var12 = 0.0F - var3 / 2.0F;
/* 554:657 */       float var13 = var12 + var3;
/* 555:658 */       float var14 = -0.5F;
/* 556:659 */       GL11.glTranslatef(-(var4 * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 557:660 */       GL11.glRotatef((var4 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 558:661 */       var2.startDrawingQuads();
/* 559:662 */       var2.addVertexWithUV(var10, var12, var14, var7, var9);
/* 560:663 */       var2.addVertexWithUV(var11, var12, var14, var6, var9);
/* 561:664 */       var2.addVertexWithUV(var11, var13, var14, var6, var8);
/* 562:665 */       var2.addVertexWithUV(var10, var13, var14, var7, var8);
/* 563:666 */       var2.draw();
/* 564:667 */       GL11.glPopMatrix();
/* 565:    */     }
/* 566:670 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 567:671 */     GL11.glDisable(3042);
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void updateEquippedItem()
/* 571:    */   {
/* 572:676 */     this.prevEquippedProgress = this.equippedProgress;
/* 573:677 */     EntityClientPlayerMP var1 = this.mc.thePlayer;
/* 574:678 */     ItemStack var2 = var1.inventory.getCurrentItem();
/* 575:679 */     boolean var3 = (this.equippedItemSlot == var1.inventory.currentItem) && (var2 == this.itemToRender);
/* 576:681 */     if ((this.itemToRender == null) && (var2 == null)) {
/* 577:683 */       var3 = true;
/* 578:    */     }
/* 579:686 */     if ((var2 != null) && (this.itemToRender != null) && (var2 != this.itemToRender) && (var2.getItem() == this.itemToRender.getItem()) && (var2.getItemDamage() == this.itemToRender.getItemDamage()))
/* 580:    */     {
/* 581:688 */       this.itemToRender = var2;
/* 582:689 */       var3 = true;
/* 583:    */     }
/* 584:692 */     float var4 = 0.4F;
/* 585:693 */     float var5 = var3 ? 1.0F : 0.0F;
/* 586:694 */     float var6 = var5 - this.equippedProgress;
/* 587:696 */     if (var6 < -var4) {
/* 588:698 */       var6 = -var4;
/* 589:    */     }
/* 590:701 */     if (var6 > var4) {
/* 591:703 */       var6 = var4;
/* 592:    */     }
/* 593:706 */     this.equippedProgress += var6;
/* 594:708 */     if (this.equippedProgress < 0.1F)
/* 595:    */     {
/* 596:710 */       this.itemToRender = var2;
/* 597:711 */       this.equippedItemSlot = var1.inventory.currentItem;
/* 598:    */     }
/* 599:    */   }
/* 600:    */   
/* 601:    */   public void resetEquippedProgress()
/* 602:    */   {
/* 603:720 */     this.equippedProgress = 0.0F;
/* 604:    */   }
/* 605:    */   
/* 606:    */   public void resetEquippedProgress2()
/* 607:    */   {
/* 608:728 */     this.equippedProgress = 0.0F;
/* 609:    */   }
/* 610:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.ItemRenderer
 * JD-Core Version:    0.7.0.1
 */