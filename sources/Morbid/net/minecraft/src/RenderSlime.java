package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSlime extends RenderLiving
{
    private ModelBase scaleAmount;
    
    public RenderSlime(final ModelBase par1ModelBase, final ModelBase par2ModelBase, final float par3) {
        super(par1ModelBase, par3);
        this.scaleAmount = par2ModelBase;
    }
    
    protected int shouldSlimeRenderPass(final EntitySlime par1EntitySlime, final int par2, final float par3) {
        if (par1EntitySlime.isInvisible()) {
            return 0;
        }
        if (par2 == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return 1;
        }
        if (par2 == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return -1;
    }
    
    protected void scaleSlime(final EntitySlime par1EntitySlime, final float par2) {
        final float var3 = par1EntitySlime.getSlimeSize();
        final float var4 = (par1EntitySlime.field_70812_c + (par1EntitySlime.field_70811_b - par1EntitySlime.field_70812_c) * par2) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        GL11.glScalef(var5 * var3, 1.0f / var5 * var3, var5 * var3);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.scaleSlime((EntitySlime)par1EntityLiving, par2);
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.shouldSlimeRenderPass((EntitySlime)par1EntityLiving, par2, par3);
    }
}
