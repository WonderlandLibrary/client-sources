package net.minecraft.src;

import org.lwjgl.opengl.*;

public class ModelEnderCrystal extends ModelBase
{
    private ModelRenderer cube;
    private ModelRenderer glass;
    private ModelRenderer base;
    
    public ModelEnderCrystal(final float par1, final boolean par2) {
        this.glass = new ModelRenderer(this, "glass");
        this.glass.setTextureOffset(0, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0f, -4.0f, -4.0f, 8, 8, 8);
        if (par2) {
            this.base = new ModelRenderer(this, "base");
            this.base.setTextureOffset(0, 16).addBox(-6.0f, 0.0f, -6.0f, 12, 4, 12);
        }
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glTranslatef(0.0f, -0.5f, 0.0f);
        if (this.base != null) {
            this.base.render(par7);
        }
        GL11.glRotatef(par3, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.8f + par4, 0.0f);
        GL11.glRotatef(60.0f, 0.7071f, 0.0f, 0.7071f);
        this.glass.render(par7);
        final float var8 = 0.875f;
        GL11.glScalef(var8, var8, var8);
        GL11.glRotatef(60.0f, 0.7071f, 0.0f, 0.7071f);
        GL11.glRotatef(par3, 0.0f, 1.0f, 0.0f);
        this.glass.render(par7);
        GL11.glScalef(var8, var8, var8);
        GL11.glRotatef(60.0f, 0.7071f, 0.0f, 0.7071f);
        GL11.glRotatef(par3, 0.0f, 1.0f, 0.0f);
        this.cube.render(par7);
        GL11.glPopMatrix();
    }
}
