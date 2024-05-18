package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

public class ModelGhast extends ModelBase
{
    ModelRenderer body;
    ModelRenderer[] tentacles;
    
    public ModelGhast() {
        this.tentacles = new ModelRenderer[9];
        final byte var1 = -16;
        (this.body = new ModelRenderer(this, 0, 0)).addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        final ModelRenderer body = this.body;
        body.rotationPointY += 24 + var1;
        final Random var2 = new Random(1660L);
        for (int var3 = 0; var3 < this.tentacles.length; ++var3) {
            this.tentacles[var3] = new ModelRenderer(this, 0, 0);
            final float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            final float var5 = (var3 / 3 / 2.0f * 2.0f - 1.0f) * 5.0f;
            final int var6 = var2.nextInt(7) + 8;
            this.tentacles[var3].addBox(-1.0f, 0.0f, -1.0f, 2, var6, 2);
            this.tentacles[var3].rotationPointX = var4;
            this.tentacles[var3].rotationPointZ = var5;
            this.tentacles[var3].rotationPointY = 31 + var1;
        }
    }
    
    @Override
    public void setRotationAngles(final float par1, final float par2, final float par3, final float par4, final float par5, final float par6, final Entity par7Entity) {
        for (int var8 = 0; var8 < this.tentacles.length; ++var8) {
            this.tentacles[var8].rotateAngleX = 0.2f * MathHelper.sin(par3 * 0.3f + var8) + 0.4f;
        }
    }
    
    @Override
    public void render(final Entity par1Entity, final float par2, final float par3, final float par4, final float par5, final float par6, final float par7) {
        this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.6f, 0.0f);
        this.body.render(par7);
        for (final ModelRenderer var11 : this.tentacles) {
            var11.render(par7);
        }
        GL11.glPopMatrix();
    }
}
