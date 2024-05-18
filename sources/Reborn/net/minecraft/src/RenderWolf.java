package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderWolf extends RenderLiving
{
    public RenderWolf(final ModelBase par1ModelBase, final ModelBase par2ModelBase, final float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }
    
    protected float getTailRotation(final EntityWolf par1EntityWolf, final float par2) {
        return par1EntityWolf.getTailRotation();
    }
    
    protected int func_82447_a(final EntityWolf par1EntityWolf, final int par2, final float par3) {
        if (par2 == 0 && par1EntityWolf.getWolfShaking()) {
            final float var4 = par1EntityWolf.getBrightness(par3) * par1EntityWolf.getShadingWhileShaking(par3);
            this.loadTexture(par1EntityWolf.getTexture());
            GL11.glColor3f(var4, var4, var4);
            return 1;
        }
        if (par2 == 1 && par1EntityWolf.isTamed()) {
            this.loadTexture("/mob/wolf_collar.png");
            final float var4 = 1.0f;
            final int var5 = par1EntityWolf.getCollarColor();
            GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
            return 1;
        }
        return -1;
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.func_82447_a((EntityWolf)par1EntityLiving, par2, par3);
    }
    
    @Override
    protected float handleRotationFloat(final EntityLiving par1EntityLiving, final float par2) {
        return this.getTailRotation((EntityWolf)par1EntityLiving, par2);
    }
}
