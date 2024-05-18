package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderMinecart extends Render
{
    protected ModelBase modelMinecart;
    protected final RenderBlocks field_94145_f;
    
    public RenderMinecart() {
        this.shadowSize = 0.5f;
        this.modelMinecart = new ModelMinecart();
        this.field_94145_f = new RenderBlocks();
    }
    
    public void renderTheMinecart(final EntityMinecart par1EntityMinecart, double par2, double par4, double par6, float par8, final float par9) {
        GL11.glPushMatrix();
        long var10 = par1EntityMinecart.entityId * 493286711L;
        var10 = var10 * var10 * 4392167121L + var10 * 98761L;
        final float var11 = (((var10 >> 16 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var12 = (((var10 >> 20 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var13 = (((var10 >> 24 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        GL11.glTranslatef(var11, var12, var13);
        final double var14 = par1EntityMinecart.lastTickPosX + (par1EntityMinecart.posX - par1EntityMinecart.lastTickPosX) * par9;
        final double var15 = par1EntityMinecart.lastTickPosY + (par1EntityMinecart.posY - par1EntityMinecart.lastTickPosY) * par9;
        final double var16 = par1EntityMinecart.lastTickPosZ + (par1EntityMinecart.posZ - par1EntityMinecart.lastTickPosZ) * par9;
        final double var17 = 0.30000001192092896;
        final Vec3 var18 = par1EntityMinecart.func_70489_a(var14, var15, var16);
        float var19 = par1EntityMinecart.prevRotationPitch + (par1EntityMinecart.rotationPitch - par1EntityMinecart.prevRotationPitch) * par9;
        if (var18 != null) {
            Vec3 var20 = par1EntityMinecart.func_70495_a(var14, var15, var16, var17);
            Vec3 var21 = par1EntityMinecart.func_70495_a(var14, var15, var16, -var17);
            if (var20 == null) {
                var20 = var18;
            }
            if (var21 == null) {
                var21 = var18;
            }
            par2 += var18.xCoord - var14;
            par4 += (var20.yCoord + var21.yCoord) / 2.0 - var15;
            par6 += var18.zCoord - var16;
            Vec3 var22 = var21.addVector(-var20.xCoord, -var20.yCoord, -var20.zCoord);
            if (var22.lengthVector() != 0.0) {
                var22 = var22.normalize();
                par8 = (float)(Math.atan2(var22.zCoord, var22.xCoord) * 180.0 / 3.141592653589793);
                var19 = (float)(Math.atan(var22.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180.0f - par8, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-var19, 0.0f, 0.0f, 1.0f);
        final float var23 = par1EntityMinecart.getRollingAmplitude() - par9;
        float var24 = par1EntityMinecart.getDamage() - par9;
        if (var24 < 0.0f) {
            var24 = 0.0f;
        }
        if (var23 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(var23) * var23 * var24 / 10.0f * par1EntityMinecart.getRollingDirection(), 1.0f, 0.0f, 0.0f);
        }
        final int var25 = par1EntityMinecart.getDisplayTileOffset();
        final Block var26 = par1EntityMinecart.getDisplayTile();
        final int var27 = par1EntityMinecart.getDisplayTileData();
        if (var26 != null) {
            GL11.glPushMatrix();
            this.loadTexture("/terrain.png");
            final float var28 = 0.75f;
            GL11.glScalef(var28, var28, var28);
            GL11.glTranslatef(0.0f, var25 / 16.0f, 0.0f);
            this.renderBlockInMinecart(par1EntityMinecart, par9, var26, var27);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.loadTexture("/item/cart.png");
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.render(par1EntityMinecart, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    protected void renderBlockInMinecart(final EntityMinecart par1EntityMinecart, final float par2, final Block par3Block, final int par4) {
        final float var5 = par1EntityMinecart.getBrightness(par2);
        GL11.glPushMatrix();
        this.field_94145_f.renderBlockAsItem(par3Block, par4, var5);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderTheMinecart((EntityMinecart)par1Entity, par2, par4, par6, par8, par9);
    }
}
