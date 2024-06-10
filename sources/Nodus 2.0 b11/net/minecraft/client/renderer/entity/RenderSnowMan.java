/*  1:   */ package net.minecraft.client.renderer.entity;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ import net.minecraft.client.model.ModelRenderer;
/*  5:   */ import net.minecraft.client.model.ModelSnowMan;
/*  6:   */ import net.minecraft.client.renderer.ItemRenderer;
/*  7:   */ import net.minecraft.client.renderer.RenderBlocks;
/*  8:   */ import net.minecraft.entity.Entity;
/*  9:   */ import net.minecraft.entity.EntityLivingBase;
/* 10:   */ import net.minecraft.entity.monster.EntitySnowman;
/* 11:   */ import net.minecraft.init.Blocks;
/* 12:   */ import net.minecraft.item.ItemBlock;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraft.util.ResourceLocation;
/* 15:   */ import org.lwjgl.opengl.GL11;
/* 16:   */ 
/* 17:   */ public class RenderSnowMan
/* 18:   */   extends RenderLiving
/* 19:   */ {
/* 20:17 */   private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");
/* 21:   */   private ModelSnowMan snowmanModel;
/* 22:   */   private static final String __OBFID = "CL_00001025";
/* 23:   */   
/* 24:   */   public RenderSnowMan()
/* 25:   */   {
/* 26:25 */     super(new ModelSnowMan(), 0.5F);
/* 27:26 */     this.snowmanModel = ((ModelSnowMan)this.mainModel);
/* 28:27 */     setRenderPassModel(this.snowmanModel);
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected void renderEquippedItems(EntitySnowman par1EntitySnowman, float par2)
/* 32:   */   {
/* 33:32 */     super.renderEquippedItems(par1EntitySnowman, par2);
/* 34:33 */     ItemStack var3 = new ItemStack(Blocks.pumpkin, 1);
/* 35:35 */     if ((var3.getItem() instanceof ItemBlock))
/* 36:   */     {
/* 37:37 */       GL11.glPushMatrix();
/* 38:38 */       this.snowmanModel.head.postRender(0.0625F);
/* 39:40 */       if (RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var3.getItem()).getRenderType()))
/* 40:   */       {
/* 41:42 */         float var4 = 0.625F;
/* 42:43 */         GL11.glTranslatef(0.0F, -0.34375F, 0.0F);
/* 43:44 */         GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
/* 44:45 */         GL11.glScalef(var4, -var4, var4);
/* 45:   */       }
/* 46:48 */       this.renderManager.itemRenderer.renderItem(par1EntitySnowman, var3, 0);
/* 47:49 */       GL11.glPopMatrix();
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected ResourceLocation getEntityTexture(EntitySnowman par1EntitySnowman)
/* 52:   */   {
/* 53:58 */     return snowManTextures;
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected void renderEquippedItems(EntityLivingBase par1EntityLivingBase, float par2)
/* 57:   */   {
/* 58:63 */     renderEquippedItems((EntitySnowman)par1EntityLivingBase, par2);
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected ResourceLocation getEntityTexture(Entity par1Entity)
/* 62:   */   {
/* 63:71 */     return getEntityTexture((EntitySnowman)par1Entity);
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.entity.RenderSnowMan
 * JD-Core Version:    0.7.0.1
 */