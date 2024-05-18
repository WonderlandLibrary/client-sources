package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.*;

public class RenderFallingBlock extends Render<EntityFallingBlock>
{
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderFallingBlock(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityFallingBlock)entity);
    }
    
    @Override
    public void doRender(final EntityFallingBlock entityFallingBlock, final double n, final double n2, final double n3, final float n4, final float n5) {
        if (entityFallingBlock.getBlock() != null) {
            this.bindTexture(TextureMap.locationBlocksTexture);
            final IBlockState block = entityFallingBlock.getBlock();
            final Block block2 = block.getBlock();
            final BlockPos blockPos = new BlockPos(entityFallingBlock);
            final World worldObj = entityFallingBlock.getWorldObj();
            if (block != worldObj.getBlockState(blockPos) && block2.getRenderType() != -" ".length() && block2.getRenderType() == "   ".length()) {
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)n, (float)n2, (float)n3);
                GlStateManager.disableLighting();
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.begin(0x3 ^ 0x4, DefaultVertexFormats.BLOCK);
                worldRenderer.setTranslation(-blockPos.getX() - 0.5f, -blockPos.getY(), -blockPos.getZ() - 0.5f);
                final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                blockRendererDispatcher.getBlockModelRenderer().renderModel(worldObj, blockRendererDispatcher.getModelFromBlockState(block, worldObj, null), block, blockPos, worldRenderer, "".length() != 0);
                worldRenderer.setTranslation(0.0, 0.0, 0.0);
                instance.draw();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entityFallingBlock, n, n2, n3, n4, n5);
            }
        }
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityFallingBlock)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityFallingBlock entityFallingBlock) {
        return TextureMap.locationBlocksTexture;
    }
}
