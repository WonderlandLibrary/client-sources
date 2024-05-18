package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSheep extends RenderLiving
{
    public RenderSheep(final ModelBase par1ModelBase, final ModelBase par2ModelBase, final float par3) {
        super(par1ModelBase, par3);
        this.setRenderPassModel(par2ModelBase);
    }
    
    protected int setWoolColorAndRender(final EntitySheep par1EntitySheep, final int par2, final float par3) {
        if (par2 == 0 && !par1EntitySheep.getSheared()) {
            this.loadTexture("/mob/sheep_fur.png");
            final float var4 = 1.0f;
            final int var5 = par1EntitySheep.getFleeceColor();
            GL11.glColor3f(var4 * EntitySheep.fleeceColorTable[var5][0], var4 * EntitySheep.fleeceColorTable[var5][1], var4 * EntitySheep.fleeceColorTable[var5][2]);
            return 1;
        }
        return -1;
    }
    
    @Override
    protected int shouldRenderPass(final EntityLiving par1EntityLiving, final int par2, final float par3) {
        return this.setWoolColorAndRender((EntitySheep)par1EntityLiving, par2, par3);
    }
}
