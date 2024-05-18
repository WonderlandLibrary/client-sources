/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class RenderEntityItem
/*     */   extends Render<EntityItem>
/*     */ {
/*     */   private final RenderItem itemRenderer;
/*  21 */   private Random field_177079_e = new Random();
/*     */ 
/*     */   
/*     */   public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
/*  25 */     super(renderManagerIn);
/*  26 */     this.itemRenderer = p_i46167_2_;
/*  27 */     this.shadowSize = 0.15F;
/*  28 */     this.shadowOpaque = 0.75F;
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) {
/*  33 */     ItemStack itemstack = itemIn.getEntityItem();
/*  34 */     Item item = itemstack.getItem();
/*     */     
/*  36 */     float size = 1.0F;
/*     */     
/*  38 */     if (Client.instance.getModuleManager().getModuleByName("BigItems").isEnabled())
/*     */     {
/*  40 */       size = (float)Client.instance.getSettingManager().getSettingByName("Size").getValue();
/*     */     }
/*     */ 
/*     */     
/*  44 */     if (item == null)
/*     */     {
/*  46 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  50 */     boolean flag = p_177077_9_.isGui3d();
/*  51 */     int i = func_177078_a(itemstack);
/*  52 */     float f = 0.25F;
/*  53 */     float f1 = MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
/*  54 */     float f2 = (p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND)).scale.y;
/*     */ 
/*     */     
/*  57 */     if (Client.instance.getModuleManager().getModuleByName("BigItems").isEnabled()) {
/*     */       
/*  59 */       GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2 + size / 5.0F, (float)p_177077_6_);
/*     */     }
/*     */     else {
/*     */       
/*  63 */       GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
/*     */     } 
/*     */ 
/*     */     
/*  67 */     if (flag || this.renderManager.options != null) {
/*     */       
/*  69 */       float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
/*  70 */       GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */ 
/*     */       
/*  73 */       if (Client.instance.getModuleManager().getModuleByName("BigItems").isEnabled())
/*     */       {
/*  75 */         GlStateManager.scale(size, size, size);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  81 */     if (!flag) {
/*     */       
/*  83 */       float f6 = -0.0F * (i - 1) * 0.5F;
/*  84 */       float f4 = -0.0F * (i - 1) * 0.5F;
/*  85 */       float f5 = -0.046875F * (i - 1) * 0.5F;
/*  86 */       GlStateManager.translate(f6, f4, f5);
/*     */     } 
/*     */     
/*  89 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  90 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int func_177078_a(ItemStack stack) {
/*  96 */     int i = 1;
/*     */     
/*  98 */     if (stack.stackSize > 48) {
/*     */       
/* 100 */       i = 5;
/*     */     }
/* 102 */     else if (stack.stackSize > 32) {
/*     */       
/* 104 */       i = 4;
/*     */     }
/* 106 */     else if (stack.stackSize > 16) {
/*     */       
/* 108 */       i = 3;
/*     */     }
/* 110 */     else if (stack.stackSize > 1) {
/*     */       
/* 112 */       i = 2;
/*     */     } 
/*     */     
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 131 */     ItemStack itemstack = entity.getEntityItem();
/* 132 */     this.field_177079_e.setSeed(187L);
/* 133 */     boolean flag = false;
/*     */     
/* 135 */     if (bindEntityTexture(entity)) {
/*     */       
/* 137 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
/* 138 */       flag = true;
/*     */     } 
/*     */     
/* 141 */     GlStateManager.enableRescaleNormal();
/* 142 */     GlStateManager.alphaFunc(516, 0.1F);
/* 143 */     GlStateManager.enableBlend();
/* 144 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 145 */     GlStateManager.pushMatrix();
/* 146 */     IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
/* 147 */     int i = func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);
/*     */     
/* 149 */     for (int j = 0; j < i; j++) {
/*     */       
/* 151 */       if (ibakedmodel.isGui3d()) {
/*     */         
/* 153 */         GlStateManager.pushMatrix();
/*     */         
/* 155 */         if (j > 0) {
/*     */           
/* 157 */           float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 158 */           float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 159 */           float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 160 */           GlStateManager.translate(f, f1, f2);
/*     */         } 
/*     */         
/* 163 */         GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 164 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 165 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 166 */         GlStateManager.popMatrix();
/*     */       }
/*     */       else {
/*     */         
/* 170 */         GlStateManager.pushMatrix();
/* 171 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 172 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 173 */         GlStateManager.popMatrix();
/* 174 */         float f3 = (ibakedmodel.getItemCameraTransforms()).ground.scale.x;
/* 175 */         float f4 = (ibakedmodel.getItemCameraTransforms()).ground.scale.y;
/* 176 */         float f5 = (ibakedmodel.getItemCameraTransforms()).ground.scale.z;
/* 177 */         GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
/*     */       } 
/*     */     } 
/*     */     
/* 181 */     GlStateManager.popMatrix();
/* 182 */     GlStateManager.disableRescaleNormal();
/* 183 */     GlStateManager.disableBlend();
/* 184 */     bindEntityTexture(entity);
/*     */     
/* 186 */     if (flag)
/*     */     {
/* 188 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
/*     */     }
/*     */     
/* 191 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItem entity) {
/* 199 */     return TextureMap.locationBlocksTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\entity\RenderEntityItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */