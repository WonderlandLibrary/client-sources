/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ItemRenderer {
/*  34 */   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
/*  35 */   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private ItemStack itemToRender;
/*     */   
/*     */   private float equippedProgress;
/*     */   
/*     */   private float prevEquippedProgress;
/*     */   
/*     */   private final RenderManager renderManager;
/*     */   
/*     */   private final RenderItem itemRenderer;
/*     */   
/*  50 */   private int equippedItemSlot = -1;
/*     */ 
/*     */   
/*     */   public ItemRenderer(Minecraft mcIn) {
/*  54 */     this.mc = mcIn;
/*  55 */     this.renderManager = mcIn.getRenderManager();
/*  56 */     this.itemRenderer = mcIn.getRenderItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform) {
/*  61 */     if (heldStack != null) {
/*     */       
/*  63 */       Item item = heldStack.getItem();
/*  64 */       Block block = Block.getBlockFromItem(item);
/*  65 */       GlStateManager.pushMatrix();
/*     */       
/*  67 */       if (this.itemRenderer.shouldRenderItemIn3D(heldStack)) {
/*     */         
/*  69 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*     */         
/*  71 */         if (isBlockTranslucent(block))
/*     */         {
/*  73 */           GlStateManager.depthMask(false);
/*     */         }
/*     */       } 
/*     */       
/*  77 */       this.itemRenderer.renderItemModelForEntity(heldStack, entityIn, transform);
/*     */       
/*  79 */       if (isBlockTranslucent(block))
/*     */       {
/*  81 */         GlStateManager.depthMask(true);
/*     */       }
/*     */       
/*  84 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockTranslucent(Block blockIn) {
/*  93 */     return (blockIn != null && blockIn.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178101_a(float angle, float p_178101_2_) {
/*  98 */     GlStateManager.pushMatrix();
/*  99 */     GlStateManager.rotate(angle, 1.0F, 0.0F, 0.0F);
/* 100 */     GlStateManager.rotate(p_178101_2_, 0.0F, 1.0F, 0.0F);
/* 101 */     RenderHelper.enableStandardItemLighting();
/* 102 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178109_a(AbstractClientPlayer clientPlayer) {
/* 107 */     int i = this.mc.theWorld.getCombinedLight(new BlockPos(clientPlayer.posX, clientPlayer.posY + clientPlayer.getEyeHeight(), clientPlayer.posZ), 0);
/* 108 */     float f = (i & 0xFFFF);
/* 109 */     float f1 = (i >> 16);
/* 110 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, f, f1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178110_a(EntityPlayerSP entityplayerspIn, float partialTicks) {
/* 115 */     float f = entityplayerspIn.prevRenderArmPitch + (entityplayerspIn.renderArmPitch - entityplayerspIn.prevRenderArmPitch) * partialTicks;
/* 116 */     float f1 = entityplayerspIn.prevRenderArmYaw + (entityplayerspIn.renderArmYaw - entityplayerspIn.prevRenderArmYaw) * partialTicks;
/* 117 */     GlStateManager.rotate((entityplayerspIn.rotationPitch - f) * 0.1F, 1.0F, 0.0F, 0.0F);
/* 118 */     GlStateManager.rotate((entityplayerspIn.rotationYaw - f1) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_178100_c(float p_178100_1_) {
/* 123 */     float f = 1.0F - p_178100_1_ / 45.0F + 0.1F;
/* 124 */     f = MathHelper.clamp_float(f, 0.0F, 1.0F);
/* 125 */     f = -MathHelper.cos(f * 3.1415927F) * 0.5F + 0.5F;
/* 126 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderRightArm(RenderPlayer renderPlayerIn) {
/* 131 */     GlStateManager.pushMatrix();
/* 132 */     GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
/* 133 */     GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
/* 134 */     GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
/* 135 */     GlStateManager.translate(0.25F, -0.85F, 0.75F);
/* 136 */     renderPlayerIn.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 137 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderLeftArm(RenderPlayer renderPlayerIn) {
/* 142 */     GlStateManager.pushMatrix();
/* 143 */     GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
/* 144 */     GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
/* 145 */     GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
/* 146 */     GlStateManager.translate(-0.3F, -1.1F, 0.45F);
/* 147 */     renderPlayerIn.renderLeftArm((AbstractClientPlayer)this.mc.thePlayer);
/* 148 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderPlayerArms(AbstractClientPlayer clientPlayer) {
/* 153 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 154 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 155 */     RenderPlayer renderplayer = (RenderPlayer)render;
/*     */     
/* 157 */     if (!clientPlayer.isInvisible()) {
/*     */       
/* 159 */       GlStateManager.disableCull();
/* 160 */       renderRightArm(renderplayer);
/* 161 */       renderLeftArm(renderplayer);
/* 162 */       GlStateManager.enableCull();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderItemMap(AbstractClientPlayer clientPlayer, float p_178097_2_, float p_178097_3_, float p_178097_4_) {
/* 168 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F);
/* 169 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F * 2.0F);
/* 170 */     float f2 = -0.2F * MathHelper.sin(p_178097_4_ * 3.1415927F);
/* 171 */     GlStateManager.translate(f, f1, f2);
/* 172 */     float f3 = func_178100_c(p_178097_2_);
/* 173 */     GlStateManager.translate(0.0F, 0.04F, -0.72F);
/* 174 */     GlStateManager.translate(0.0F, p_178097_3_ * -1.2F, 0.0F);
/* 175 */     GlStateManager.translate(0.0F, f3 * -0.5F, 0.0F);
/* 176 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 177 */     GlStateManager.rotate(f3 * -85.0F, 0.0F, 0.0F, 1.0F);
/* 178 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 179 */     renderPlayerArms(clientPlayer);
/* 180 */     float f4 = MathHelper.sin(p_178097_4_ * p_178097_4_ * 3.1415927F);
/* 181 */     float f5 = MathHelper.sin(MathHelper.sqrt_float(p_178097_4_) * 3.1415927F);
/* 182 */     GlStateManager.rotate(f4 * -20.0F, 0.0F, 1.0F, 0.0F);
/* 183 */     GlStateManager.rotate(f5 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 184 */     GlStateManager.rotate(f5 * -80.0F, 1.0F, 0.0F, 0.0F);
/* 185 */     GlStateManager.scale(0.38F, 0.38F, 0.38F);
/* 186 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 187 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 188 */     GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 189 */     GlStateManager.translate(-1.0F, -1.0F, 0.0F);
/* 190 */     GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
/* 191 */     this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
/* 192 */     Tessellator tessellator = Tessellator.getInstance();
/* 193 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 194 */     GL11.glNormal3f(0.0F, 0.0F, -1.0F);
/* 195 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 196 */     worldrenderer.pos(-7.0D, 135.0D, 0.0D).tex(0.0D, 1.0D).endVertex();
/* 197 */     worldrenderer.pos(135.0D, 135.0D, 0.0D).tex(1.0D, 1.0D).endVertex();
/* 198 */     worldrenderer.pos(135.0D, -7.0D, 0.0D).tex(1.0D, 0.0D).endVertex();
/* 199 */     worldrenderer.pos(-7.0D, -7.0D, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 200 */     tessellator.draw();
/* 201 */     MapData mapdata = Items.filled_map.getMapData(this.itemToRender, (World)this.mc.theWorld);
/*     */     
/* 203 */     if (mapdata != null)
/*     */     {
/* 205 */       this.mc.entityRenderer.getMapItemRenderer().renderMap(mapdata, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178095_a(AbstractClientPlayer clientPlayer, float p_178095_2_, float p_178095_3_) {
/* 211 */     float f = -0.3F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F);
/* 212 */     float f1 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F * 2.0F);
/* 213 */     float f2 = -0.4F * MathHelper.sin(p_178095_3_ * 3.1415927F);
/* 214 */     GlStateManager.translate(f, f1, f2);
/* 215 */     GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
/* 216 */     GlStateManager.translate(0.0F, p_178095_2_ * -0.6F, 0.0F);
/* 217 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 218 */     float f3 = MathHelper.sin(p_178095_3_ * p_178095_3_ * 3.1415927F);
/* 219 */     float f4 = MathHelper.sin(MathHelper.sqrt_float(p_178095_3_) * 3.1415927F);
/* 220 */     GlStateManager.rotate(f4 * 70.0F, 0.0F, 1.0F, 0.0F);
/* 221 */     GlStateManager.rotate(f3 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 222 */     this.mc.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
/* 223 */     GlStateManager.translate(-1.0F, 3.6F, 3.5F);
/* 224 */     GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
/* 225 */     GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
/* 226 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/* 227 */     GlStateManager.scale(1.0F, 1.0F, 1.0F);
/* 228 */     GlStateManager.translate(5.6F, 0.0F, 0.0F);
/* 229 */     Render<AbstractClientPlayer> render = this.renderManager.getEntityRenderObject((Entity)this.mc.thePlayer);
/* 230 */     GlStateManager.disableCull();
/* 231 */     RenderPlayer renderplayer = (RenderPlayer)render;
/* 232 */     renderplayer.renderRightArm((AbstractClientPlayer)this.mc.thePlayer);
/* 233 */     GlStateManager.enableCull();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178105_d(float p_178105_1_) {
/* 238 */     float f = -0.4F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927F);
/* 239 */     float f1 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(p_178105_1_) * 3.1415927F * 2.0F);
/* 240 */     float f2 = -0.2F * MathHelper.sin(p_178105_1_ * 3.1415927F);
/* 241 */     GlStateManager.translate(f, f1, f2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178104_a(AbstractClientPlayer clientPlayer, float p_178104_2_) {
/* 246 */     float f = clientPlayer.getItemInUseCount() - p_178104_2_ + 1.0F;
/* 247 */     float f1 = f / this.itemToRender.getMaxItemUseDuration();
/* 248 */     float f2 = MathHelper.abs(MathHelper.cos(f / 4.0F * 3.1415927F) * 0.1F);
/*     */     
/* 250 */     if (f1 >= 0.8F)
/*     */     {
/* 252 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 255 */     GlStateManager.translate(0.0F, f2, 0.0F);
/* 256 */     float f3 = 1.0F - (float)Math.pow(f1, 27.0D);
/* 257 */     GlStateManager.translate(f3 * 0.6F, f3 * -0.5F, f3 * 0.0F);
/* 258 */     GlStateManager.rotate(f3 * 90.0F, 0.0F, 1.0F, 0.0F);
/* 259 */     GlStateManager.rotate(f3 * 10.0F, 1.0F, 0.0F, 0.0F);
/* 260 */     GlStateManager.rotate(f3 * 30.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transformFirstPersonItem(float equipProgress, float swingProgress) {
/* 268 */     GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
/* 269 */     GlStateManager.translate(0.0F, equipProgress * -0.6F, 0.0F);
/* 270 */     GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 271 */     float f = MathHelper.sin(swingProgress * swingProgress * 3.1415927F);
/* 272 */     float f1 = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * 3.1415927F);
/* 273 */     GlStateManager.rotate(f * -20.0F, 0.0F, 1.0F, 0.0F);
/* 274 */     GlStateManager.rotate(f1 * -20.0F, 0.0F, 0.0F, 1.0F);
/* 275 */     GlStateManager.rotate(f1 * -80.0F, 1.0F, 0.0F, 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 279 */     if (Client.instance.getModuleManager().getModuleByName("BigItems").isEnabled()) {
/*     */       
/* 281 */       if (Client.instance.getSettingManager().getSettingByName("Holding").getBoolean())
/*     */       {
/* 283 */         float size = (float)Client.instance.getSettingManager().getSettingByName("Size").getValue() / 2.5F;
/*     */         
/* 285 */         GlStateManager.scale(size, size, size);
/*     */       }
/*     */       else
/*     */       {
/* 289 */         GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 295 */       GlStateManager.scale(0.4F, 0.4F, 0.4F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_178098_a(float p_178098_1_, AbstractClientPlayer clientPlayer) {
/* 302 */     GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
/* 303 */     GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
/* 304 */     GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
/* 305 */     GlStateManager.translate(-0.9F, 0.2F, 0.0F);
/* 306 */     float f = this.itemToRender.getMaxItemUseDuration() - clientPlayer.getItemInUseCount() - p_178098_1_ + 1.0F;
/* 307 */     float f1 = f / 20.0F;
/* 308 */     f1 = (f1 * f1 + f1 * 2.0F) / 3.0F;
/*     */     
/* 310 */     if (f1 > 1.0F)
/*     */     {
/* 312 */       f1 = 1.0F;
/*     */     }
/*     */     
/* 315 */     if (f1 > 0.1F) {
/*     */       
/* 317 */       float f2 = MathHelper.sin((f - 0.1F) * 1.3F);
/* 318 */       float f3 = f1 - 0.1F;
/* 319 */       float f4 = f2 * f3;
/* 320 */       GlStateManager.translate(f4 * 0.0F, f4 * 0.01F, f4 * 0.0F);
/*     */     } 
/*     */     
/* 323 */     GlStateManager.translate(f1 * 0.0F, f1 * 0.0F, f1 * 0.1F);
/* 324 */     GlStateManager.scale(1.0F, 1.0F, 1.0F + f1 * 0.2F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178103_d() {
/* 329 */     GlStateManager.translate(-0.5F, 0.2F, 0.0F);
/* 330 */     GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
/* 331 */     GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
/* 332 */     GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderItemInFirstPerson(float partialTicks) {
/* 340 */     float f = 1.0F - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/* 341 */     EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 342 */     float f1 = entityPlayerSP.getSwingProgress(partialTicks);
/* 343 */     float f2 = ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch + (((AbstractClientPlayer)entityPlayerSP).rotationPitch - ((AbstractClientPlayer)entityPlayerSP).prevRotationPitch) * partialTicks;
/* 344 */     float f3 = ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw + (((AbstractClientPlayer)entityPlayerSP).rotationYaw - ((AbstractClientPlayer)entityPlayerSP).prevRotationYaw) * partialTicks;
/* 345 */     func_178101_a(f2, f3);
/* 346 */     func_178109_a((AbstractClientPlayer)entityPlayerSP);
/* 347 */     func_178110_a(entityPlayerSP, partialTicks);
/* 348 */     GlStateManager.enableRescaleNormal();
/* 349 */     GlStateManager.pushMatrix();
/*     */ 
/*     */ 
/*     */     
/* 353 */     if (Client.instance.getModuleManager().getModuleByName("ItemRenderer").isEnabled())
/*     */     {
/*     */       
/* 356 */       f = getRealHeight() - this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
/*     */     }
/*     */ 
/*     */     
/* 360 */     if (this.itemToRender != null) {
/*     */       
/* 362 */       if (this.itemToRender.getItem() == Items.filled_map) {
/*     */         
/* 364 */         renderItemMap((AbstractClientPlayer)entityPlayerSP, f2, f, f1);
/*     */       }
/* 366 */       else if (entityPlayerSP.getItemInUseCount() > 0) {
/*     */         
/* 368 */         EnumAction enumaction = this.itemToRender.getItemUseAction();
/*     */         
/* 370 */         switch (enumaction) {
/*     */           
/*     */           case NONE:
/* 373 */             transformFirstPersonItem(f, 0.0F);
/*     */             break;
/*     */           
/*     */           case EAT:
/*     */           case DRINK:
/* 378 */             func_178104_a((AbstractClientPlayer)entityPlayerSP, partialTicks);
/* 379 */             transformFirstPersonItem(f, 0.0F);
/*     */             break;
/*     */ 
/*     */           
/*     */           case null:
/* 384 */             if (Client.instance.getModuleManager().getModuleByName("ItemRenderer").isEnabled())
/*     */             {
/* 386 */               if (Client.instance.getSettingManager().getSettingByName("BHAnimation").getBoolean()) {
/*     */                 
/* 388 */                 transformFirstPersonItem(f, f1);
/* 389 */                 func_178103_d();
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             }
/*     */             
/* 396 */             transformFirstPersonItem(f, 0.0F);
/* 397 */             func_178103_d();
/*     */             break;
/*     */           
/*     */           case BOW:
/* 401 */             transformFirstPersonItem(f, 0.0F);
/* 402 */             func_178098_a(partialTicks, (AbstractClientPlayer)entityPlayerSP);
/*     */             break;
/*     */         } 
/*     */       
/*     */       } else {
/* 407 */         func_178105_d(f1);
/* 408 */         transformFirstPersonItem(f, f1);
/*     */       } 
/*     */       
/* 411 */       renderItem((EntityLivingBase)entityPlayerSP, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
/*     */     }
/* 413 */     else if (!entityPlayerSP.isInvisible()) {
/*     */       
/* 415 */       func_178095_a((AbstractClientPlayer)entityPlayerSP, f, f1);
/*     */     } 
/*     */     
/* 418 */     GlStateManager.popMatrix();
/* 419 */     GlStateManager.disableRescaleNormal();
/* 420 */     RenderHelper.disableStandardItemLighting();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRealHeight() {
/* 425 */     float height = (float)Client.instance.getSettingManager().getSettingByName("ItemHeight").getValue();
/*     */     
/* 427 */     float realheight = 0.0F;
/*     */     
/* 429 */     if (height == 1.0F) {
/*     */       
/* 431 */       realheight = 1.3F;
/*     */     }
/* 433 */     else if (height == 2.0F) {
/*     */       
/* 435 */       realheight = 1.2F;
/*     */     }
/* 437 */     else if (height == 3.0F) {
/*     */       
/* 439 */       realheight = 1.1F;
/*     */     }
/* 441 */     else if (height == 4.0F) {
/*     */       
/* 443 */       realheight = 1.0F;
/*     */     }
/* 445 */     else if (height == 5.0F) {
/*     */       
/* 447 */       realheight = 0.9F;
/*     */     }
/* 449 */     else if (height == 6.0F) {
/*     */       
/* 451 */       realheight = 0.8F;
/*     */     }
/* 453 */     else if (height == 7.0F) {
/*     */       
/* 455 */       realheight = 0.7F;
/*     */     }
/* 457 */     else if (height == 8.0F) {
/*     */       
/* 459 */       realheight = 0.6F;
/*     */     }
/* 461 */     else if (height == 9.0F) {
/*     */       
/* 463 */       realheight = 0.5F;
/*     */     }
/* 465 */     else if (height == 10.0F) {
/*     */       
/* 467 */       realheight = 0.4F;
/*     */     } 
/*     */ 
/*     */     
/* 471 */     return realheight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderOverlays(float partialTicks) {
/* 480 */     GlStateManager.disableAlpha();
/*     */     
/* 482 */     if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
/*     */       
/* 484 */       IBlockState iblockstate = this.mc.theWorld.getBlockState(new BlockPos((Entity)this.mc.thePlayer));
/* 485 */       EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/*     */       
/* 487 */       for (int i = 0; i < 8; i++) {
/*     */         
/* 489 */         double d0 = ((EntityPlayer)entityPlayerSP).posX + ((((i >> 0) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 490 */         double d1 = ((EntityPlayer)entityPlayerSP).posY + ((((i >> 1) % 2) - 0.5F) * 0.1F);
/* 491 */         double d2 = ((EntityPlayer)entityPlayerSP).posZ + ((((i >> 2) % 2) - 0.5F) * ((EntityPlayer)entityPlayerSP).width * 0.8F);
/* 492 */         BlockPos blockpos = new BlockPos(d0, d1 + entityPlayerSP.getEyeHeight(), d2);
/* 493 */         IBlockState iblockstate1 = this.mc.theWorld.getBlockState(blockpos);
/*     */         
/* 495 */         if (iblockstate1.getBlock().isVisuallyOpaque())
/*     */         {
/* 497 */           iblockstate = iblockstate1;
/*     */         }
/*     */       } 
/*     */       
/* 501 */       if (iblockstate.getBlock().getRenderType() != -1)
/*     */       {
/* 503 */         func_178108_a(partialTicks, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(iblockstate));
/*     */       }
/*     */     } 
/*     */     
/* 507 */     if (!this.mc.thePlayer.isSpectator()) {
/*     */       
/* 509 */       if (this.mc.thePlayer.isInsideOfMaterial(Material.water))
/*     */       {
/* 511 */         renderWaterOverlayTexture(partialTicks);
/*     */       }
/*     */       
/* 514 */       if (this.mc.thePlayer.isBurning())
/*     */       {
/* 516 */         renderFireInFirstPerson(partialTicks);
/*     */       }
/*     */     } 
/*     */     
/* 520 */     GlStateManager.enableAlpha();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_178108_a(float p_178108_1_, TextureAtlasSprite p_178108_2_) {
/* 525 */     this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 526 */     Tessellator tessellator = Tessellator.getInstance();
/* 527 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 528 */     float f = 0.1F;
/* 529 */     GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
/* 530 */     GlStateManager.pushMatrix();
/* 531 */     float f1 = -1.0F;
/* 532 */     float f2 = 1.0F;
/* 533 */     float f3 = -1.0F;
/* 534 */     float f4 = 1.0F;
/* 535 */     float f5 = -0.5F;
/* 536 */     float f6 = p_178108_2_.getMinU();
/* 537 */     float f7 = p_178108_2_.getMaxU();
/* 538 */     float f8 = p_178108_2_.getMinV();
/* 539 */     float f9 = p_178108_2_.getMaxV();
/* 540 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 541 */     worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex(f7, f9).endVertex();
/* 542 */     worldrenderer.pos(1.0D, -1.0D, -0.5D).tex(f6, f9).endVertex();
/* 543 */     worldrenderer.pos(1.0D, 1.0D, -0.5D).tex(f6, f8).endVertex();
/* 544 */     worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex(f7, f8).endVertex();
/* 545 */     tessellator.draw();
/* 546 */     GlStateManager.popMatrix();
/* 547 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderWaterOverlayTexture(float p_78448_1_) {
/* 556 */     this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
/* 557 */     Tessellator tessellator = Tessellator.getInstance();
/* 558 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 559 */     float f = this.mc.thePlayer.getBrightness(p_78448_1_);
/* 560 */     GlStateManager.color(f, f, f, 0.5F);
/* 561 */     GlStateManager.enableBlend();
/* 562 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 563 */     GlStateManager.pushMatrix();
/* 564 */     float f1 = 4.0F;
/* 565 */     float f2 = -1.0F;
/* 566 */     float f3 = 1.0F;
/* 567 */     float f4 = -1.0F;
/* 568 */     float f5 = 1.0F;
/* 569 */     float f6 = -0.5F;
/* 570 */     float f7 = -this.mc.thePlayer.rotationYaw / 64.0F;
/* 571 */     float f8 = this.mc.thePlayer.rotationPitch / 64.0F;
/* 572 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 573 */     worldrenderer.pos(-1.0D, -1.0D, -0.5D).tex((4.0F + f7), (4.0F + f8)).endVertex();
/* 574 */     worldrenderer.pos(1.0D, -1.0D, -0.5D).tex((0.0F + f7), (4.0F + f8)).endVertex();
/* 575 */     worldrenderer.pos(1.0D, 1.0D, -0.5D).tex((0.0F + f7), (0.0F + f8)).endVertex();
/* 576 */     worldrenderer.pos(-1.0D, 1.0D, -0.5D).tex((4.0F + f7), (0.0F + f8)).endVertex();
/* 577 */     tessellator.draw();
/* 578 */     GlStateManager.popMatrix();
/* 579 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 580 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderFireInFirstPerson(float p_78442_1_) {
/* 588 */     Tessellator tessellator = Tessellator.getInstance();
/* 589 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 590 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
/* 591 */     GlStateManager.depthFunc(519);
/* 592 */     GlStateManager.depthMask(false);
/* 593 */     GlStateManager.enableBlend();
/* 594 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 595 */     float f = 1.0F;
/*     */     
/* 597 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 599 */       GlStateManager.pushMatrix();
/* 600 */       TextureAtlasSprite textureatlassprite = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
/* 601 */       this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 602 */       float f1 = textureatlassprite.getMinU();
/* 603 */       float f2 = textureatlassprite.getMaxU();
/* 604 */       float f3 = textureatlassprite.getMinV();
/* 605 */       float f4 = textureatlassprite.getMaxV();
/* 606 */       float f5 = (0.0F - f) / 2.0F;
/* 607 */       float f6 = f5 + f;
/* 608 */       float f7 = 0.0F - f / 2.0F;
/* 609 */       float f8 = f7 + f;
/* 610 */       float f9 = -0.5F;
/* 611 */       GlStateManager.translate(-(i * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 612 */       GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
/* 613 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 614 */       worldrenderer.pos(f5, f7, f9).tex(f2, f4).endVertex();
/* 615 */       worldrenderer.pos(f6, f7, f9).tex(f1, f4).endVertex();
/* 616 */       worldrenderer.pos(f6, f8, f9).tex(f1, f3).endVertex();
/* 617 */       worldrenderer.pos(f5, f8, f9).tex(f2, f3).endVertex();
/* 618 */       tessellator.draw();
/* 619 */       GlStateManager.popMatrix();
/*     */     } 
/*     */     
/* 622 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 623 */     GlStateManager.disableBlend();
/* 624 */     GlStateManager.depthMask(true);
/* 625 */     GlStateManager.depthFunc(515);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateEquippedItem() {
/* 630 */     this.prevEquippedProgress = this.equippedProgress;
/* 631 */     EntityPlayerSP entityPlayerSP = this.mc.thePlayer;
/* 632 */     ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventory.getCurrentItem();
/* 633 */     boolean flag = false;
/*     */     
/* 635 */     if (this.itemToRender != null && itemstack != null) {
/*     */       
/* 637 */       if (!this.itemToRender.getIsItemStackEqual(itemstack))
/*     */       {
/* 639 */         flag = true;
/*     */       }
/*     */     }
/* 642 */     else if (this.itemToRender == null && itemstack == null) {
/*     */       
/* 644 */       flag = false;
/*     */     }
/*     */     else {
/*     */       
/* 648 */       flag = true;
/*     */     } 
/*     */     
/* 651 */     float f = 0.4F;
/* 652 */     float f1 = flag ? 0.0F : 1.0F;
/* 653 */     float f2 = MathHelper.clamp_float(f1 - this.equippedProgress, -f, f);
/* 654 */     this.equippedProgress += f2;
/*     */     
/* 656 */     if (this.equippedProgress < 0.1F) {
/*     */       
/* 658 */       this.itemToRender = itemstack;
/* 659 */       this.equippedItemSlot = ((EntityPlayer)entityPlayerSP).inventory.currentItem;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress() {
/* 668 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetEquippedProgress2() {
/* 676 */     this.equippedProgress = 0.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\ItemRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */