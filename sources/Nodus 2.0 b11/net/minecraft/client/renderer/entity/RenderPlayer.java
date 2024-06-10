/*   1:    */ package net.minecraft.client.renderer.entity;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*   5:    */ import net.minecraft.client.entity.EntityPlayerSP;
/*   6:    */ import net.minecraft.client.gui.FontRenderer;
/*   7:    */ import net.minecraft.client.model.ModelBase;
/*   8:    */ import net.minecraft.client.model.ModelBiped;
/*   9:    */ import net.minecraft.client.model.ModelRenderer;
/*  10:    */ import net.minecraft.client.renderer.ItemRenderer;
/*  11:    */ import net.minecraft.client.renderer.RenderBlocks;
/*  12:    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*  13:    */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*  14:    */ import net.minecraft.entity.Entity;
/*  15:    */ import net.minecraft.entity.EntityLivingBase;
/*  16:    */ import net.minecraft.entity.player.EntityPlayer;
/*  17:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  18:    */ import net.minecraft.init.Items;
/*  19:    */ import net.minecraft.item.EnumAction;
/*  20:    */ import net.minecraft.item.Item;
/*  21:    */ import net.minecraft.item.ItemArmor;
/*  22:    */ import net.minecraft.item.ItemArmor.ArmorMaterial;
/*  23:    */ import net.minecraft.item.ItemBlock;
/*  24:    */ import net.minecraft.item.ItemStack;
/*  25:    */ import net.minecraft.nbt.NBTTagCompound;
/*  26:    */ import net.minecraft.scoreboard.Score;
/*  27:    */ import net.minecraft.scoreboard.ScoreObjective;
/*  28:    */ import net.minecraft.scoreboard.Scoreboard;
/*  29:    */ import net.minecraft.util.MathHelper;
/*  30:    */ import net.minecraft.util.ResourceLocation;
/*  31:    */ import org.lwjgl.opengl.GL11;
/*  32:    */ 
/*  33:    */ public class RenderPlayer
/*  34:    */   extends RendererLivingEntity
/*  35:    */ {
/*  36: 27 */   private static final ResourceLocation steveTextures = new ResourceLocation("textures/entity/steve.png");
/*  37:    */   private ModelBiped modelBipedMain;
/*  38:    */   private ModelBiped modelArmorChestplate;
/*  39:    */   private ModelBiped modelArmor;
/*  40:    */   private static final String __OBFID = "CL_00001020";
/*  41:    */   
/*  42:    */   public RenderPlayer()
/*  43:    */   {
/*  44: 35 */     super(new ModelBiped(0.0F), 0.5F);
/*  45: 36 */     this.modelBipedMain = ((ModelBiped)this.mainModel);
/*  46: 37 */     this.modelArmorChestplate = new ModelBiped(1.0F);
/*  47: 38 */     this.modelArmor = new ModelBiped(0.5F);
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected int shouldRenderPass(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
/*  51:    */   {
/*  52: 46 */     ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
/*  53: 48 */     if (var4 != null)
/*  54:    */     {
/*  55: 50 */       Item var5 = var4.getItem();
/*  56: 52 */       if ((var5 instanceof ItemArmor))
/*  57:    */       {
/*  58: 54 */         ItemArmor var6 = (ItemArmor)var5;
/*  59: 55 */         bindTexture(RenderBiped.func_110857_a(var6, par2));
/*  60: 56 */         ModelBiped var7 = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
/*  61: 57 */         var7.bipedHead.showModel = (par2 == 0);
/*  62: 58 */         var7.bipedHeadwear.showModel = (par2 == 0);
/*  63: 59 */         var7.bipedBody.showModel = ((par2 == 1) || (par2 == 2));
/*  64: 60 */         var7.bipedRightArm.showModel = (par2 == 1);
/*  65: 61 */         var7.bipedLeftArm.showModel = (par2 == 1);
/*  66: 62 */         var7.bipedRightLeg.showModel = ((par2 == 2) || (par2 == 3));
/*  67: 63 */         var7.bipedLeftLeg.showModel = ((par2 == 2) || (par2 == 3));
/*  68: 64 */         setRenderPassModel(var7);
/*  69: 65 */         var7.onGround = this.mainModel.onGround;
/*  70: 66 */         var7.isRiding = this.mainModel.isRiding;
/*  71: 67 */         var7.isChild = this.mainModel.isChild;
/*  72: 69 */         if (var6.getArmorMaterial() == ItemArmor.ArmorMaterial.CLOTH)
/*  73:    */         {
/*  74: 71 */           int var8 = var6.getColor(var4);
/*  75: 72 */           float var9 = (var8 >> 16 & 0xFF) / 255.0F;
/*  76: 73 */           float var10 = (var8 >> 8 & 0xFF) / 255.0F;
/*  77: 74 */           float var11 = (var8 & 0xFF) / 255.0F;
/*  78: 75 */           GL11.glColor3f(var9, var10, var11);
/*  79: 77 */           if (var4.isItemEnchanted()) {
/*  80: 79 */             return 31;
/*  81:    */           }
/*  82: 82 */           return 16;
/*  83:    */         }
/*  84: 85 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*  85: 87 */         if (var4.isItemEnchanted()) {
/*  86: 89 */           return 15;
/*  87:    */         }
/*  88: 92 */         return 1;
/*  89:    */       }
/*  90:    */     }
/*  91: 96 */     return -1;
/*  92:    */   }
/*  93:    */   
/*  94:    */   protected void func_82408_c(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
/*  95:    */   {
/*  96:101 */     ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - par2);
/*  97:103 */     if (var4 != null)
/*  98:    */     {
/*  99:105 */       Item var5 = var4.getItem();
/* 100:107 */       if ((var5 instanceof ItemArmor))
/* 101:    */       {
/* 102:109 */         bindTexture(RenderBiped.func_110858_a((ItemArmor)var5, par2, "overlay"));
/* 103:110 */         GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 104:    */       }
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void doRender(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9)
/* 109:    */   {
/* 110:123 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 111:124 */     ItemStack var10 = par1AbstractClientPlayer.inventory.getCurrentItem();
/* 112:125 */     this.modelArmorChestplate.heldItemRight = (this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = var10 != null ? 1 : 0);
/* 113:127 */     if ((var10 != null) && (par1AbstractClientPlayer.getItemInUseCount() > 0))
/* 114:    */     {
/* 115:129 */       EnumAction var11 = var10.getItemUseAction();
/* 116:131 */       if (var11 == EnumAction.block) {
/* 117:133 */         this.modelArmorChestplate.heldItemRight = (this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3);
/* 118:135 */       } else if (var11 == EnumAction.bow) {
/* 119:137 */         this.modelArmorChestplate.aimedBow = (this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = 1);
/* 120:    */       }
/* 121:    */     }
/* 122:141 */     this.modelArmorChestplate.isSneak = (this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking());
/* 123:142 */     double var13 = par4 - par1AbstractClientPlayer.yOffset;
/* 124:144 */     if ((par1AbstractClientPlayer.isSneaking()) && (!(par1AbstractClientPlayer instanceof EntityPlayerSP))) {
/* 125:146 */       var13 -= 0.125D;
/* 126:    */     }
/* 127:149 */     super.doRender(par1AbstractClientPlayer, par2, var13, par6, par8, par9);
/* 128:150 */     this.modelArmorChestplate.aimedBow = (this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = 0);
/* 129:151 */     this.modelArmorChestplate.isSneak = (this.modelArmor.isSneak = this.modelBipedMain.isSneak = 0);
/* 130:152 */     this.modelArmorChestplate.heldItemRight = (this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0);
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected ResourceLocation getEntityTexture(AbstractClientPlayer par1AbstractClientPlayer)
/* 134:    */   {
/* 135:160 */     return par1AbstractClientPlayer.getLocationSkin();
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void renderEquippedItems(AbstractClientPlayer par1AbstractClientPlayer, float par2)
/* 139:    */   {
/* 140:165 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 141:166 */     super.renderEquippedItems(par1AbstractClientPlayer, par2);
/* 142:167 */     super.renderArrowsStuckInEntity(par1AbstractClientPlayer, par2);
/* 143:168 */     ItemStack var3 = par1AbstractClientPlayer.inventory.armorItemInSlot(3);
/* 144:170 */     if (var3 != null)
/* 145:    */     {
/* 146:172 */       GL11.glPushMatrix();
/* 147:173 */       this.modelBipedMain.bipedHead.postRender(0.0625F);
/* 148:176 */       if ((var3.getItem() instanceof ItemBlock))
/* 149:    */       {
/* 150:178 */         if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType()))
/* 151:    */         {
/* 152:180 */           float var4 = 0.625F;
/* 153:181 */           GL11.glTranslatef(0.0F, -0.25F, 0.0F);
/* 154:182 */           GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 155:183 */           GL11.glScalef(var4, -var4, -var4);
/* 156:    */         }
/* 157:186 */         this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var3, 0);
/* 158:    */       }
/* 159:188 */       else if (var3.getItem() == Items.skull)
/* 160:    */       {
/* 161:190 */         float var4 = 1.0625F;
/* 162:191 */         GL11.glScalef(var4, -var4, -var4);
/* 163:192 */         String var5 = "";
/* 164:194 */         if ((var3.hasTagCompound()) && (var3.getTagCompound().func_150297_b("SkullOwner", 8))) {
/* 165:196 */           var5 = var3.getTagCompound().getString("SkullOwner");
/* 166:    */         }
/* 167:199 */         TileEntitySkullRenderer.field_147536_b.func_147530_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, var3.getItemDamage(), var5);
/* 168:    */       }
/* 169:202 */       GL11.glPopMatrix();
/* 170:    */     }
/* 171:207 */     if ((par1AbstractClientPlayer.getCommandSenderName().equals("deadmau5")) && (par1AbstractClientPlayer.getTextureSkin().isTextureUploaded()))
/* 172:    */     {
/* 173:209 */       bindTexture(par1AbstractClientPlayer.getLocationSkin());
/* 174:211 */       for (int var20 = 0; var20 < 2; var20++)
/* 175:    */       {
/* 176:213 */         float var22 = par1AbstractClientPlayer.prevRotationYaw + (par1AbstractClientPlayer.rotationYaw - par1AbstractClientPlayer.prevRotationYaw) * par2 - (par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2);
/* 177:214 */         float var6 = par1AbstractClientPlayer.prevRotationPitch + (par1AbstractClientPlayer.rotationPitch - par1AbstractClientPlayer.prevRotationPitch) * par2;
/* 178:215 */         GL11.glPushMatrix();
/* 179:216 */         GL11.glRotatef(var22, 0.0F, 1.0F, 0.0F);
/* 180:217 */         GL11.glRotatef(var6, 1.0F, 0.0F, 0.0F);
/* 181:218 */         GL11.glTranslatef(0.375F * (var20 * 2 - 1), 0.0F, 0.0F);
/* 182:219 */         GL11.glTranslatef(0.0F, -0.375F, 0.0F);
/* 183:220 */         GL11.glRotatef(-var6, 1.0F, 0.0F, 0.0F);
/* 184:221 */         GL11.glRotatef(-var22, 0.0F, 1.0F, 0.0F);
/* 185:222 */         float var7 = 1.333333F;
/* 186:223 */         GL11.glScalef(var7, var7, var7);
/* 187:224 */         this.modelBipedMain.renderEars(0.0625F);
/* 188:225 */         GL11.glPopMatrix();
/* 189:    */       }
/* 190:    */     }
/* 191:229 */     boolean var21 = par1AbstractClientPlayer.getTextureCape().isTextureUploaded();
/* 192:232 */     if ((var21) && (!par1AbstractClientPlayer.isInvisible()) && (!par1AbstractClientPlayer.getHideCape()))
/* 193:    */     {
/* 194:234 */       bindTexture(par1AbstractClientPlayer.getLocationCape());
/* 195:235 */       GL11.glPushMatrix();
/* 196:236 */       GL11.glTranslatef(0.0F, 0.0F, 0.125F);
/* 197:237 */       double var25 = par1AbstractClientPlayer.field_71091_bM + (par1AbstractClientPlayer.field_71094_bP - par1AbstractClientPlayer.field_71091_bM) * par2 - (par1AbstractClientPlayer.prevPosX + (par1AbstractClientPlayer.posX - par1AbstractClientPlayer.prevPosX) * par2);
/* 198:238 */       double var26 = par1AbstractClientPlayer.field_71096_bN + (par1AbstractClientPlayer.field_71095_bQ - par1AbstractClientPlayer.field_71096_bN) * par2 - (par1AbstractClientPlayer.prevPosY + (par1AbstractClientPlayer.posY - par1AbstractClientPlayer.prevPosY) * par2);
/* 199:239 */       double var9 = par1AbstractClientPlayer.field_71097_bO + (par1AbstractClientPlayer.field_71085_bR - par1AbstractClientPlayer.field_71097_bO) * par2 - (par1AbstractClientPlayer.prevPosZ + (par1AbstractClientPlayer.posZ - par1AbstractClientPlayer.prevPosZ) * par2);
/* 200:240 */       float var11 = par1AbstractClientPlayer.prevRenderYawOffset + (par1AbstractClientPlayer.renderYawOffset - par1AbstractClientPlayer.prevRenderYawOffset) * par2;
/* 201:241 */       double var12 = MathHelper.sin(var11 * 3.141593F / 180.0F);
/* 202:242 */       double var14 = -MathHelper.cos(var11 * 3.141593F / 180.0F);
/* 203:243 */       float var16 = (float)var26 * 10.0F;
/* 204:245 */       if (var16 < -6.0F) {
/* 205:247 */         var16 = -6.0F;
/* 206:    */       }
/* 207:250 */       if (var16 > 32.0F) {
/* 208:252 */         var16 = 32.0F;
/* 209:    */       }
/* 210:255 */       float var17 = (float)(var25 * var12 + var9 * var14) * 100.0F;
/* 211:256 */       float var18 = (float)(var25 * var14 - var9 * var12) * 100.0F;
/* 212:258 */       if (var17 < 0.0F) {
/* 213:260 */         var17 = 0.0F;
/* 214:    */       }
/* 215:263 */       float var19 = par1AbstractClientPlayer.prevCameraYaw + (par1AbstractClientPlayer.cameraYaw - par1AbstractClientPlayer.prevCameraYaw) * par2;
/* 216:264 */       var16 += MathHelper.sin((par1AbstractClientPlayer.prevDistanceWalkedModified + (par1AbstractClientPlayer.distanceWalkedModified - par1AbstractClientPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * var19;
/* 217:266 */       if (par1AbstractClientPlayer.isSneaking()) {
/* 218:268 */         var16 += 25.0F;
/* 219:    */       }
/* 220:271 */       GL11.glRotatef(6.0F + var17 / 2.0F + var16, 1.0F, 0.0F, 0.0F);
/* 221:272 */       GL11.glRotatef(var18 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 222:273 */       GL11.glRotatef(-var18 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 223:274 */       GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
/* 224:275 */       this.modelBipedMain.renderCloak(0.0625F);
/* 225:276 */       GL11.glPopMatrix();
/* 226:    */     }
/* 227:279 */     ItemStack var23 = par1AbstractClientPlayer.inventory.getCurrentItem();
/* 228:281 */     if (var23 != null)
/* 229:    */     {
/* 230:283 */       GL11.glPushMatrix();
/* 231:284 */       this.modelBipedMain.bipedRightArm.postRender(0.0625F);
/* 232:285 */       GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
/* 233:287 */       if (par1AbstractClientPlayer.fishEntity != null) {
/* 234:289 */         var23 = new ItemStack(Items.stick);
/* 235:    */       }
/* 236:292 */       EnumAction var24 = null;
/* 237:294 */       if (par1AbstractClientPlayer.getItemInUseCount() > 0) {
/* 238:296 */         var24 = var23.getItemUseAction();
/* 239:    */       }
/* 240:299 */       if (((var23.getItem() instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var23.getItem()).getRenderType())))
/* 241:    */       {
/* 242:301 */         float var7 = 0.5F;
/* 243:302 */         GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
/* 244:303 */         var7 *= 0.75F;
/* 245:304 */         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/* 246:305 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 247:306 */         GL11.glScalef(-var7, -var7, var7);
/* 248:    */       }
/* 249:308 */       else if (var23.getItem() == Items.bow)
/* 250:    */       {
/* 251:310 */         float var7 = 0.625F;
/* 252:311 */         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
/* 253:312 */         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/* 254:313 */         GL11.glScalef(var7, -var7, var7);
/* 255:314 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/* 256:315 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 257:    */       }
/* 258:317 */       else if (var23.getItem().isFull3D())
/* 259:    */       {
/* 260:319 */         float var7 = 0.625F;
/* 261:321 */         if (var23.getItem().shouldRotateAroundWhenRendering())
/* 262:    */         {
/* 263:323 */           GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/* 264:324 */           GL11.glTranslatef(0.0F, -0.125F, 0.0F);
/* 265:    */         }
/* 266:327 */         if ((par1AbstractClientPlayer.getItemInUseCount() > 0) && (var24 == EnumAction.block))
/* 267:    */         {
/* 268:329 */           GL11.glTranslatef(0.05F, 0.0F, -0.1F);
/* 269:330 */           GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
/* 270:331 */           GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
/* 271:332 */           GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
/* 272:    */         }
/* 273:335 */         GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
/* 274:336 */         GL11.glScalef(var7, -var7, var7);
/* 275:337 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/* 276:338 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 277:    */       }
/* 278:    */       else
/* 279:    */       {
/* 280:342 */         float var7 = 0.375F;
/* 281:343 */         GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
/* 282:344 */         GL11.glScalef(var7, var7, var7);
/* 283:345 */         GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
/* 284:346 */         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 285:347 */         GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/* 286:    */       }
/* 287:354 */       if (var23.getItem().requiresMultipleRenderPasses())
/* 288:    */       {
/* 289:356 */         for (int var28 = 0; var28 <= 1; var28++)
/* 290:    */         {
/* 291:358 */           int var8 = var23.getItem().getColorFromItemStack(var23, var28);
/* 292:359 */           float var29 = (var8 >> 16 & 0xFF) / 255.0F;
/* 293:360 */           float var10 = (var8 >> 8 & 0xFF) / 255.0F;
/* 294:361 */           float var11 = (var8 & 0xFF) / 255.0F;
/* 295:362 */           GL11.glColor4f(var29, var10, var11, 1.0F);
/* 296:363 */           this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, var28);
/* 297:    */         }
/* 298:    */       }
/* 299:    */       else
/* 300:    */       {
/* 301:368 */         int var28 = var23.getItem().getColorFromItemStack(var23, 0);
/* 302:369 */         float var27 = (var28 >> 16 & 0xFF) / 255.0F;
/* 303:370 */         float var29 = (var28 >> 8 & 0xFF) / 255.0F;
/* 304:371 */         float var10 = (var28 & 0xFF) / 255.0F;
/* 305:372 */         GL11.glColor4f(var27, var29, var10, 1.0F);
/* 306:373 */         this.renderManager.itemRenderer.renderItem(par1AbstractClientPlayer, var23, 0);
/* 307:    */       }
/* 308:376 */       GL11.glPopMatrix();
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected void preRenderCallback(AbstractClientPlayer par1AbstractClientPlayer, float par2)
/* 313:    */   {
/* 314:386 */     float var3 = 0.9375F;
/* 315:387 */     GL11.glScalef(var3, var3, var3);
/* 316:    */   }
/* 317:    */   
/* 318:    */   protected void func_96449_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10)
/* 319:    */   {
/* 320:392 */     if (par10 < 100.0D)
/* 321:    */     {
/* 322:394 */       Scoreboard var12 = par1AbstractClientPlayer.getWorldScoreboard();
/* 323:395 */       ScoreObjective var13 = var12.func_96539_a(2);
/* 324:397 */       if (var13 != null)
/* 325:    */       {
/* 326:399 */         Score var14 = var12.func_96529_a(par1AbstractClientPlayer.getCommandSenderName(), var13);
/* 327:401 */         if (par1AbstractClientPlayer.isPlayerSleeping()) {
/* 328:403 */           func_147906_a(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4 - 1.5D, par6, 64);
/* 329:    */         } else {
/* 330:407 */           func_147906_a(par1AbstractClientPlayer, var14.getScorePoints() + " " + var13.getDisplayName(), par2, par4, par6, 64);
/* 331:    */         }
/* 332:410 */         par4 += getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9;
/* 333:    */       }
/* 334:    */     }
/* 335:414 */     super.func_96449_a(par1AbstractClientPlayer, par2, par4, par6, par8Str, par9, par10);
/* 336:    */   }
/* 337:    */   
/* 338:    */   public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
/* 339:    */   {
/* 340:419 */     float var2 = 1.0F;
/* 341:420 */     GL11.glColor3f(var2, var2, var2);
/* 342:421 */     this.modelBipedMain.onGround = 0.0F;
/* 343:422 */     this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
/* 344:423 */     this.modelBipedMain.bipedRightArm.render(0.0625F);
/* 345:    */   }
/* 346:    */   
/* 347:    */   protected void renderLivingAt(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6)
/* 348:    */   {
/* 349:431 */     if ((par1AbstractClientPlayer.isEntityAlive()) && (par1AbstractClientPlayer.isPlayerSleeping())) {
/* 350:433 */       super.renderLivingAt(par1AbstractClientPlayer, par2 + par1AbstractClientPlayer.field_71079_bU, par4 + par1AbstractClientPlayer.field_71082_cx, par6 + par1AbstractClientPlayer.field_71089_bV);
/* 351:    */     } else {
/* 352:437 */       super.renderLivingAt(par1AbstractClientPlayer, par2, par4, par6);
/* 353:    */     }
/* 354:    */   }
/* 355:    */   
/* 356:    */   protected void rotateCorpse(AbstractClientPlayer par1AbstractClientPlayer, float par2, float par3, float par4)
/* 357:    */   {
/* 358:443 */     if ((par1AbstractClientPlayer.isEntityAlive()) && (par1AbstractClientPlayer.isPlayerSleeping()))
/* 359:    */     {
/* 360:445 */       GL11.glRotatef(par1AbstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
/* 361:446 */       GL11.glRotatef(getDeathMaxRotation(par1AbstractClientPlayer), 0.0F, 0.0F, 1.0F);
/* 362:447 */       GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
/* 363:    */     }
/* 364:    */     else
/* 365:    */     {
/* 366:451 */       super.rotateCorpse(par1AbstractClientPlayer, par2, par3, par4);
/* 367:    */     }
/* 368:    */   }
/* 369:    */   
/* 370:    */   protected void func_96449_a(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, String par8Str, float par9, double par10)
/* 371:    */   {
/* 372:457 */     func_96449_a((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8Str, par9, par10);
/* 373:    */   }
/* 374:    */   
/* 375:    */   protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
/* 376:    */   {
/* 377:466 */     preRenderCallback((AbstractClientPlayer)par1EntityLivingBase, par2);
/* 378:    */   }
/* 379:    */   
/* 380:    */   protected void func_82408_c(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 381:    */   {
/* 382:471 */     func_82408_c((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
/* 383:    */   }
/* 384:    */   
/* 385:    */   protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
/* 386:    */   {
/* 387:479 */     return shouldRenderPass((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
/* 388:    */   }
/* 389:    */   
/* 390:    */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 391:    */   {
/* 392:484 */     renderEquippedItems((AbstractClientPlayer)par1EntityLivingBase, par2);
/* 393:    */   }
/* 394:    */   
/* 395:    */   protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
/* 396:    */   {
/* 397:489 */     rotateCorpse((AbstractClientPlayer)par1EntityLivingBase, par2, par3, par4);
/* 398:    */   }
/* 399:    */   
/* 400:    */   protected void renderLivingAt(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6)
/* 401:    */   {
/* 402:497 */     renderLivingAt((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6);
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void doRender(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9)
/* 406:    */   {
/* 407:508 */     doRender((AbstractClientPlayer)par1EntityLivingBase, par2, par4, par6, par8, par9);
/* 408:    */   }
/* 409:    */   
/* 410:    */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 411:    */   {
/* 412:516 */     return getEntityTexture((AbstractClientPlayer)par1Entity);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
/* 416:    */   {
/* 417:527 */     doRender((AbstractClientPlayer)par1Entity, par2, par4, par6, par8, par9);
/* 418:    */   }
/* 419:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderPlayer
 * JD-Core Version:    0.7.0.1
 */