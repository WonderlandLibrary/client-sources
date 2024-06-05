package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer {
   private static final String __OBFID = "CL_00002469";
   private final BlockRendererDispatcher field_178462_c = Minecraft.getMinecraft().getBlockRendererDispatcher();

   public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9) {
      this.func_178461_a((TileEntityPiston)var1, var2, var4, var6, var8, var9);
   }

   public void func_178461_a(TileEntityPiston var1, double var2, double var4, double var6, float var8, int var9) {
      BlockPos var10 = var1.getPos();
      IBlockState var11 = var1.func_174927_b();
      Block var12 = var11.getBlock();
      if (var12.getMaterial() != Material.air && var1.func_145860_a(var8) < 1.0F) {
         Tessellator var13 = Tessellator.getInstance();
         WorldRenderer var14 = var13.getWorldRenderer();
         this.bindTexture(TextureMap.locationBlocksTexture);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(7425);
         } else {
            GlStateManager.shadeModel(7424);
         }

         var14.startDrawingQuads();
         var14.setVertexFormat(DefaultVertexFormats.field_176600_a);
         var14.setTranslation((double)((float)var2 - (float)var10.getX() + var1.func_174929_b(var8)), (double)((float)var4 - (float)var10.getY() + var1.func_174928_c(var8)), (double)((float)var6 - (float)var10.getZ() + var1.func_174926_d(var8)));
         var14.func_178986_b(1.0F, 1.0F, 1.0F);
         World var15 = this.getWorld();
         if (var12 == Blocks.piston_head && var1.func_145860_a(var8) < 0.5F) {
            var11 = var11.withProperty(BlockPistonExtension.field_176327_M, true);
            this.field_178462_c.func_175019_b().renderBlockModel(var15, this.field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, true);
         } else if (var1.shouldPistonHeadBeRendered() && !var1.isExtending()) {
            BlockPistonExtension.EnumPistonType var16 = var12 == Blocks.sticky_piston ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
            IBlockState var17 = Blocks.piston_head.getDefaultState().withProperty(BlockPistonExtension.field_176325_b, var16).withProperty(BlockPistonExtension.field_176326_a, var11.getValue(BlockPistonBase.FACING));
            var17 = var17.withProperty(BlockPistonExtension.field_176327_M, var1.func_145860_a(var8) >= 0.5F);
            this.field_178462_c.func_175019_b().renderBlockModel(var15, this.field_178462_c.getModelFromBlockState(var17, var15, var10), var17, var10, var14, true);
            var14.setTranslation((double)((float)var2 - (float)var10.getX()), (double)((float)var4 - (float)var10.getY()), (double)((float)var6 - (float)var10.getZ()));
            var11.withProperty(BlockPistonBase.EXTENDED, true);
            this.field_178462_c.func_175019_b().renderBlockModel(var15, this.field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, true);
         } else {
            this.field_178462_c.func_175019_b().renderBlockModel(var15, this.field_178462_c.getModelFromBlockState(var11, var15, var10), var11, var10, var14, false);
         }

         var14.setTranslation(0.0D, 0.0D, 0.0D);
         var13.draw();
         RenderHelper.enableStandardItemLighting();
      }

   }
}
