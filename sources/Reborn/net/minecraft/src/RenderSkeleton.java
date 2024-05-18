package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSkeleton extends RenderBiped
{
    public RenderSkeleton() {
        super(new ModelSkeleton(), 0.5f);
    }
    
    protected void func_82438_a(final EntitySkeleton par1EntitySkeleton, final float par2) {
        if (par1EntitySkeleton.getSkeletonType() == 1) {
            GL11.glScalef(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    protected void func_82422_c() {
        GL11.glTranslatef(0.09375f, 0.1875f, 0.0f);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.func_82438_a((EntitySkeleton)par1EntityLiving, par2);
    }
}
