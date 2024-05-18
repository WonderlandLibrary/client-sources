package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderBoat extends Render
{
    protected ModelBase modelBoat;
    
    public RenderBoat() {
        this.shadowSize = 0.5f;
        this.modelBoat = new ModelBoat();
    }
    
    public void renderBoat(final EntityBoat par1EntityBoat, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glRotatef(180.0f - par8, 0.0f, 1.0f, 0.0f);
        final float var10 = par1EntityBoat.getTimeSinceHit() - par9;
        float var11 = par1EntityBoat.getDamageTaken() - par9;
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(var10) * var10 * var11 / 10.0f * par1EntityBoat.getForwardDirection(), 1.0f, 0.0f, 0.0f);
        }
        this.loadTexture("/terrain.png");
        final float var12 = 0.75f;
        GL11.glScalef(var12, var12, var12);
        GL11.glScalef(1.0f / var12, 1.0f / var12, 1.0f / var12);
        this.loadTexture("/item/boat.png");
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelBoat.render(par1EntityBoat, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderBoat((EntityBoat)par1Entity, par2, par4, par6, par8, par9);
    }
}
