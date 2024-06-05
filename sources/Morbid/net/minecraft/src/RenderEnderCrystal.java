package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderEnderCrystal extends Render
{
    private int field_76996_a;
    private ModelBase field_76995_b;
    
    public RenderEnderCrystal() {
        this.field_76996_a = -1;
        this.shadowSize = 0.5f;
    }
    
    public void doRenderEnderCrystal(final EntityEnderCrystal par1EntityEnderCrystal, final double par2, final double par4, final double par6, final float par8, final float par9) {
        if (this.field_76996_a != 1) {
            this.field_76995_b = new ModelEnderCrystal(0.0f, true);
            this.field_76996_a = 1;
        }
        final float var10 = par1EntityEnderCrystal.innerRotation + par9;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        this.loadTexture("/mob/enderdragon/crystal.png");
        float var11 = MathHelper.sin(var10 * 0.2f) / 2.0f + 0.5f;
        var11 += var11 * var11;
        this.field_76995_b.render(par1EntityEnderCrystal, 0.0f, var10 * 3.0f, var11 * 0.2f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        this.doRenderEnderCrystal((EntityEnderCrystal)par1Entity, par2, par4, par6, par8, par9);
    }
}
