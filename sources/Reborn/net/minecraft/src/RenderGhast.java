package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderGhast extends RenderLiving
{
    public RenderGhast() {
        super(new ModelGhast(), 0.5f);
    }
    
    protected void preRenderGhast(final EntityGhast par1EntityGhast, final float par2) {
        float var4 = (par1EntityGhast.prevAttackCounter + (par1EntityGhast.attackCounter - par1EntityGhast.prevAttackCounter) * par2) / 20.0f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        var4 = 1.0f / (var4 * var4 * var4 * var4 * var4 * 2.0f + 1.0f);
        final float var5 = (8.0f + var4) / 2.0f;
        final float var6 = (8.0f + 1.0f / var4) / 2.0f;
        GL11.glScalef(var6, var5, var6);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.preRenderGhast((EntityGhast)par1EntityLiving, par2);
    }
}
