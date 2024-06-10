/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.renderer.ItemRenderer;
/*   7:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   8:    */ import net.minecraft.client.renderer.RenderBlocks;
/*   9:    */ import net.minecraft.client.renderer.Tessellator;
/*  10:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  11:    */ import net.minecraft.client.renderer.texture.TextureUtil;
/*  12:    */ import net.minecraft.entity.EntityLivingBase;
/*  13:    */ import net.minecraft.item.Item;
/*  14:    */ import net.minecraft.item.ItemBlock;
/*  15:    */ import net.minecraft.item.ItemCloth;
/*  16:    */ import net.minecraft.item.ItemStack;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.util.ResourceLocation;
/*  19:    */ import org.lwjgl.opengl.GL11;
/*  20:    */ 
/*  21:    */ public class ItemRendererOF
/*  22:    */   extends ItemRenderer
/*  23:    */ {
/*  24: 24 */   private Minecraft mc = null;
/*  25: 25 */   private RenderBlocks renderBlocksIr = null;
/*  26: 26 */   private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  27: 27 */   private static Field ItemRenderer_renderBlockInstance = Reflector.getFieldByType(ItemRenderer.class, RenderBlocks.class);
/*  28:    */   
/*  29:    */   public ItemRendererOF(Minecraft par1Minecraft)
/*  30:    */   {
/*  31: 31 */     super(par1Minecraft);
/*  32: 32 */     this.mc = par1Minecraft;
/*  33: 34 */     if (ItemRenderer_renderBlockInstance == null) {
/*  34: 36 */       Config.error("ItemRenderOF not initialized");
/*  35:    */     }
/*  36:    */     try
/*  37:    */     {
/*  38: 41 */       this.renderBlocksIr = ((RenderBlocks)ItemRenderer_renderBlockInstance.get(this));
/*  39:    */     }
/*  40:    */     catch (IllegalAccessException var3)
/*  41:    */     {
/*  42: 45 */       throw new RuntimeException(var3);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void renderItem(EntityLivingBase par1EntityLivingBase, ItemStack par2ItemStack, int par3)
/*  47:    */   {
/*  48: 54 */     GL11.glPushMatrix();
/*  49: 55 */     TextureManager var4 = this.mc.getTextureManager();
/*  50: 56 */     Item var5 = par2ItemStack.getItem();
/*  51: 57 */     Block var6 = Block.getBlockFromItem(var5);
/*  52: 58 */     Object type = null;
/*  53: 59 */     Object customRenderer = null;
/*  54: 61 */     if (Reflector.MinecraftForgeClient_getItemRenderer.exists())
/*  55:    */     {
/*  56: 63 */       type = Reflector.getFieldValue(Reflector.ItemRenderType_EQUIPPED);
/*  57: 64 */       customRenderer = Reflector.call(Reflector.MinecraftForgeClient_getItemRenderer, new Object[] { par2ItemStack, type });
/*  58:    */     }
/*  59: 67 */     if (customRenderer != null)
/*  60:    */     {
/*  61: 69 */       var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
/*  62: 70 */       Reflector.callVoid(Reflector.ForgeHooksClient_renderEquippedItem, new Object[] { type, customRenderer, this.renderBlocksIr, par1EntityLivingBase, par2ItemStack });
/*  63:    */     }
/*  64: 72 */     else if ((par2ItemStack.getItemSpriteNumber() == 0) && ((var5 instanceof ItemBlock)) && (RenderBlocks.renderItemIn3d(var6.getRenderType())))
/*  65:    */     {
/*  66: 74 */       var4.bindTexture(var4.getResourceLocation(0));
/*  67: 76 */       if ((par2ItemStack != null) && ((par2ItemStack.getItem() instanceof ItemCloth)))
/*  68:    */       {
/*  69: 78 */         GL11.glEnable(3042);
/*  70: 79 */         GL11.glDepthMask(false);
/*  71: 80 */         OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  72: 81 */         this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0F);
/*  73: 82 */         GL11.glDepthMask(true);
/*  74: 83 */         GL11.glDisable(3042);
/*  75:    */       }
/*  76:    */       else
/*  77:    */       {
/*  78: 87 */         this.renderBlocksIr.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0F);
/*  79:    */       }
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83: 92 */       IIcon var7 = par1EntityLivingBase.getItemIcon(par2ItemStack, par3);
/*  84: 94 */       if (var7 == null)
/*  85:    */       {
/*  86: 96 */         GL11.glPopMatrix();
/*  87: 97 */         return;
/*  88:    */       }
/*  89:100 */       var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
/*  90:101 */       TextureUtil.func_147950_a(false, false);
/*  91:102 */       Tessellator var8 = Tessellator.instance;
/*  92:103 */       float var9 = var7.getMinU();
/*  93:104 */       float var10 = var7.getMaxU();
/*  94:105 */       float var11 = var7.getMinV();
/*  95:106 */       float var12 = var7.getMaxV();
/*  96:107 */       float var13 = 0.0F;
/*  97:108 */       float var14 = 0.3F;
/*  98:109 */       GL11.glEnable(32826);
/*  99:110 */       GL11.glTranslatef(-var13, -var14, 0.0F);
/* 100:111 */       float var15 = 1.5F;
/* 101:112 */       GL11.glScalef(var15, var15, var15);
/* 102:113 */       GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
/* 103:114 */       GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
/* 104:115 */       GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
/* 105:116 */       renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625F);
/* 106:117 */       boolean renderEffect = false;
/* 107:119 */       if (Reflector.ForgeItemStack_hasEffect.exists()) {
/* 108:121 */         renderEffect = Reflector.callBoolean(par2ItemStack, Reflector.ForgeItemStack_hasEffect, new Object[] { Integer.valueOf(par3) });
/* 109:    */       } else {
/* 110:125 */         renderEffect = (par2ItemStack.hasEffect()) && (par3 == 0);
/* 111:    */       }
/* 112:128 */       if (renderEffect)
/* 113:    */       {
/* 114:130 */         GL11.glDepthFunc(514);
/* 115:131 */         GL11.glDisable(2896);
/* 116:132 */         var4.bindTexture(RES_ITEM_GLINT);
/* 117:133 */         GL11.glEnable(3042);
/* 118:134 */         OpenGlHelper.glBlendFunc(768, 1, 1, 0);
/* 119:135 */         float var16 = 0.76F;
/* 120:136 */         GL11.glColor4f(0.5F * var16, 0.25F * var16, 0.8F * var16, 1.0F);
/* 121:137 */         GL11.glMatrixMode(5890);
/* 122:138 */         GL11.glPushMatrix();
/* 123:139 */         float var17 = 0.125F;
/* 124:140 */         GL11.glScalef(var17, var17, var17);
/* 125:141 */         float var18 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
/* 126:142 */         GL11.glTranslatef(var18, 0.0F, 0.0F);
/* 127:143 */         GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
/* 128:144 */         renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 16, 16, 0.0625F);
/* 129:145 */         GL11.glPopMatrix();
/* 130:146 */         GL11.glPushMatrix();
/* 131:147 */         GL11.glScalef(var17, var17, var17);
/* 132:148 */         var18 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
/* 133:149 */         GL11.glTranslatef(-var18, 0.0F, 0.0F);
/* 134:150 */         GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
/* 135:151 */         renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 16, 16, 0.0625F);
/* 136:152 */         GL11.glPopMatrix();
/* 137:153 */         GL11.glMatrixMode(5888);
/* 138:154 */         GL11.glDisable(3042);
/* 139:155 */         GL11.glEnable(2896);
/* 140:156 */         GL11.glDepthFunc(515);
/* 141:    */       }
/* 142:159 */       GL11.glDisable(32826);
/* 143:160 */       var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
/* 144:161 */       TextureUtil.func_147945_b();
/* 145:    */     }
/* 146:164 */     GL11.glPopMatrix();
/* 147:    */   }
/* 148:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ItemRendererOF
 * JD-Core Version:    0.7.0.1
 */