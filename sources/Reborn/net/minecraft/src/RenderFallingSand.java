package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderFallingSand extends Render
{
    private RenderBlocks sandRenderBlocks;
    
    public RenderFallingSand() {
        this.sandRenderBlocks = new RenderBlocks();
        this.shadowSize = 0.5f;
    }
    
    public void doRenderFallingSand(final EntityFallingSand par1EntityFallingSand, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final World var10 = par1EntityFallingSand.getWorld();
        final Block var11 = Block.blocksList[par1EntityFallingSand.blockID];
        if (var10.getBlockId(MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ)) != par1EntityFallingSand.blockID) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            this.loadTexture("/terrain.png");
            GL11.glDisable(2896);
            if (var11 instanceof BlockAnvil && var11.getRenderType() == 35) {
                this.sandRenderBlocks.blockAccess = var10;
                final Tessellator var12 = Tessellator.instance;
                var12.startDrawingQuads();
                var12.setTranslation(-MathHelper.floor_double(par1EntityFallingSand.posX) - 0.5f, -MathHelper.floor_double(par1EntityFallingSand.posY) - 0.5f, -MathHelper.floor_double(par1EntityFallingSand.posZ) - 0.5f);
                this.sandRenderBlocks.renderBlockAnvilMetadata((BlockAnvil)var11, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), par1EntityFallingSand.metadata);
                var12.setTranslation(0.0, 0.0, 0.0);
                var12.draw();
            }
            else if (var11.getRenderType() == 27) {
                this.sandRenderBlocks.blockAccess = var10;
                final Tessellator var12 = Tessellator.instance;
                var12.startDrawingQuads();
                var12.setTranslation(-MathHelper.floor_double(par1EntityFallingSand.posX) - 0.5f, -MathHelper.floor_double(par1EntityFallingSand.posY) - 0.5f, -MathHelper.floor_double(par1EntityFallingSand.posZ) - 0.5f);
                this.sandRenderBlocks.renderBlockDragonEgg((BlockDragonEgg)var11, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ));
                var12.setTranslation(0.0, 0.0, 0.0);
                var12.draw();
            }
            else if (var11 != null) {
                this.sandRenderBlocks.setRenderBoundsFromBlock(var11);
                this.sandRenderBlocks.renderBlockSandFalling(var11, var10, MathHelper.floor_double(par1EntityFallingSand.posX), MathHelper.floor_double(par1EntityFallingSand.posY), MathHelper.floor_double(par1EntityFallingSand.posZ), par1EntityFallingSand.metadata);
            }
            GL11.glEnable(2896);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderFallingSand((EntityFallingSand)par1Entity, par2, par4, par6, par8, par9);
    }
}
