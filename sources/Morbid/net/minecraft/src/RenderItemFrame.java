package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderItemFrame extends Render
{
    private final RenderBlocks renderBlocksInstance;
    private Icon field_94147_f;
    
    public RenderItemFrame() {
        this.renderBlocksInstance = new RenderBlocks();
    }
    
    @Override
    public void updateIcons(final IconRegister par1IconRegister) {
        this.field_94147_f = par1IconRegister.registerIcon("itemframe_back");
    }
    
    public void func_82404_a(final EntityItemFrame par1EntityItemFrame, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        final float var10 = (float)(par1EntityItemFrame.posX - par2) - 0.5f;
        final float var11 = (float)(par1EntityItemFrame.posY - par4) - 0.5f;
        final float var12 = (float)(par1EntityItemFrame.posZ - par6) - 0.5f;
        final int var13 = par1EntityItemFrame.xPosition + Direction.offsetX[par1EntityItemFrame.hangingDirection];
        final int var14 = par1EntityItemFrame.yPosition;
        final int var15 = par1EntityItemFrame.zPosition + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
        GL11.glTranslatef(var13 - var10, var14 - var11, var15 - var12);
        this.renderFrameItemAsBlock(par1EntityItemFrame);
        this.func_82402_b(par1EntityItemFrame);
        GL11.glPopMatrix();
    }
    
    private void renderFrameItemAsBlock(final EntityItemFrame par1EntityItemFrame) {
        GL11.glPushMatrix();
        this.renderManager.renderEngine.bindTexture("/terrain.png");
        GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0f, 1.0f, 0.0f);
        final Block var2 = Block.planks;
        final float var3 = 0.0625f;
        final float var4 = 0.75f;
        final float var5 = var4 / 2.0f;
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0, 0.5f - var5 + 0.0625f, 0.5f - var5 + 0.0625f, var3 * 0.5f, 0.5f + var5 - 0.0625f, 0.5f + var5 - 0.0625f);
        this.renderBlocksInstance.setOverrideBlockTexture(this.field_94147_f);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0f);
        this.renderBlocksInstance.clearOverrideBlockTexture();
        this.renderBlocksInstance.unlockBlockBounds();
        GL11.glPopMatrix();
        this.renderBlocksInstance.setOverrideBlockTexture(Block.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3 + 1.0E-4f, var3 + 0.5f - var5, 0.5f + var5);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0, 0.5f + var5 - var3, 0.5f - var5, var3 + 1.0E-4f, 0.5f + var5, 0.5f + var5);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0, 0.5f - var5, 0.5f - var5, var3, 0.5f + var5, var3 + 0.5f - var5);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0, 0.5f - var5, 0.5f + var5 - var3, var3, 0.5f + var5, 0.5f + var5);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0f);
        GL11.glPopMatrix();
        this.renderBlocksInstance.unlockBlockBounds();
        this.renderBlocksInstance.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }
    
    private void func_82402_b(final EntityItemFrame par1EntityItemFrame) {
        final ItemStack var2 = par1EntityItemFrame.getDisplayedItem();
        if (var2 != null) {
            final EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0, 0.0, 0.0, var2);
            var3.getEntityItem().stackSize = 1;
            var3.hoverStart = 0.0f;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.453125f * Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18f, -0.453125f * Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
            GL11.glRotatef(180.0f + par1EntityItemFrame.rotationYaw, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-90 * par1EntityItemFrame.getRotation(), 0.0f, 0.0f, 1.0f);
            switch (par1EntityItemFrame.getRotation()) {
                case 1: {
                    GL11.glTranslatef(-0.16f, -0.16f, 0.0f);
                    break;
                }
                case 2: {
                    GL11.glTranslatef(0.0f, -0.32f, 0.0f);
                    break;
                }
                case 3: {
                    GL11.glTranslatef(0.16f, -0.16f, 0.0f);
                    break;
                }
            }
            if (var3.getEntityItem().getItem() == Item.map) {
                this.renderManager.renderEngine.bindTexture("/misc/mapbg.png");
                final Tessellator var4 = Tessellator.instance;
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
                GL11.glScalef(0.00390625f, 0.00390625f, 0.00390625f);
                GL11.glTranslatef(-65.0f, -107.0f, -3.0f);
                GL11.glNormal3f(0.0f, 0.0f, -1.0f);
                var4.startDrawingQuads();
                final byte var5 = 7;
                var4.addVertexWithUV(0 - var5, 128 + var5, 0.0, 0.0, 1.0);
                var4.addVertexWithUV(128 + var5, 128 + var5, 0.0, 1.0, 1.0);
                var4.addVertexWithUV(128 + var5, 0 - var5, 0.0, 1.0, 0.0);
                var4.addVertexWithUV(0 - var5, 0 - var5, 0.0, 0.0, 0.0);
                var4.draw();
                final MapData var6 = Item.map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
                GL11.glTranslatef(0.0f, 0.0f, -1.0f);
                if (var6 != null) {
                    this.renderManager.itemRenderer.mapItemRenderer.renderMap(null, this.renderManager.renderEngine, var6);
                }
            }
            else {
                if (var3.getEntityItem().getItem() == Item.compass) {
                    final TextureCompass var7 = TextureCompass.compassTexture;
                    final double var8 = var7.currentAngle;
                    final double var9 = var7.angleDelta;
                    var7.currentAngle = 0.0;
                    var7.angleDelta = 0.0;
                    var7.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, MathHelper.wrapAngleTo180_float(180 + par1EntityItemFrame.hangingDirection * 90), false, true);
                    var7.currentAngle = var8;
                    var7.angleDelta = var9;
                }
                RenderItem.renderInFrame = true;
                RenderManager.instance.renderEntityWithPosYaw(var3, 0.0, 0.0, 0.0, 0.0f, 0.0f);
                RenderItem.renderInFrame = false;
                if (var3.getEntityItem().getItem() == Item.compass) {
                    final TextureCompass var7 = TextureCompass.compassTexture;
                    var7.updateAnimation();
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.func_82404_a((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
    }
}
