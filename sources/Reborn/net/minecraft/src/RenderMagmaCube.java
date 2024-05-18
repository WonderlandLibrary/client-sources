package net.minecraft.src;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;

public class RenderMagmaCube extends RenderLiving
{
    private int field_77120_a;
    
    public RenderMagmaCube() {
        super(new ModelMagmaCube(), 0.25f);
        this.field_77120_a = ((ModelMagmaCube)this.mainModel).func_78107_a();
    }
    
    public void renderMagmaCube(final EntityMagmaCube par1EntityMagmaCube, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final int var10 = ((ModelMagmaCube)this.mainModel).func_78107_a();
        if (var10 != this.field_77120_a) {
            this.field_77120_a = var10;
            this.mainModel = new ModelMagmaCube();
            Minecraft.getMinecraft().getLogAgent().logInfo("Loaded new lava slime model");
        }
        super.doRenderLiving(par1EntityMagmaCube, par2, par4, par6, par8, par9);
    }
    
    protected void scaleMagmaCube(final EntityMagmaCube par1EntityMagmaCube, final float par2) {
        final int var3 = par1EntityMagmaCube.getSlimeSize();
        final float var4 = (par1EntityMagmaCube.field_70812_c + (par1EntityMagmaCube.field_70811_b - par1EntityMagmaCube.field_70812_c) * par2) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        final float var6 = var3;
        GL11.glScalef(var5 * var6, 1.0f / var5 * var6, var5 * var6);
    }
    
    @Override
    protected void preRenderCallback(final EntityLiving par1EntityLiving, final float par2) {
        this.scaleMagmaCube((EntityMagmaCube)par1EntityLiving, par2);
    }
    
    @Override
    public void doRenderLiving(final EntityLiving par1EntityLiving, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderMagmaCube((EntityMagmaCube)par1EntityLiving, par2, par4, par6, par8, par9);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.renderMagmaCube((EntityMagmaCube)par1Entity, par2, par4, par6, par8, par9);
    }
}
