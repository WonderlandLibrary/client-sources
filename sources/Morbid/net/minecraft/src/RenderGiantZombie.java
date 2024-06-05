package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderGiantZombie extends RenderLiving
{
    private float scale;
    
    public RenderGiantZombie(final ModelBase par1ModelBase, final float par2, final float par3) {
        super(par1ModelBase, par2 * par3);
        this.scale = par3;
    }
    
    protected void preRenderScale(final EntityGiantZombie par1EntityGiantZombie, final float par2) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.preRenderScale((EntityGiantZombie)par1EntityLiving, par2);
    }
}
