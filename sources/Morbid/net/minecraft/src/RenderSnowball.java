package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderSnowball extends Render
{
    private Item field_94151_a;
    private int field_94150_f;
    
    public RenderSnowball(final Item par1, final int par2) {
        this.field_94151_a = par1;
        this.field_94150_f = par2;
    }
    
    public RenderSnowball(final Item par1) {
        this(par1, 0);
    }
    
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        final Icon var10 = this.field_94151_a.getIconFromDamage(this.field_94150_f);
        if (var10 != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)par2, (float)par4, (float)par6);
            GL11.glEnable(32826);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.loadTexture("/gui/items.png");
            final Tessellator var11 = Tessellator.instance;
            if (var10 == ItemPotion.func_94589_d("potion_splash")) {
                final int var12 = PotionHelper.func_77915_a(((EntityPotion)par1Entity).getPotionDamage(), false);
                final float var13 = (var12 >> 16 & 0xFF) / 255.0f;
                final float var14 = (var12 >> 8 & 0xFF) / 255.0f;
                final float var15 = (var12 & 0xFF) / 255.0f;
                GL11.glColor3f(var13, var14, var15);
                GL11.glPushMatrix();
                this.func_77026_a(var11, ItemPotion.func_94589_d("potion_contents"));
                GL11.glPopMatrix();
                GL11.glColor3f(1.0f, 1.0f, 1.0f);
            }
            this.func_77026_a(var11, var10);
            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }
    
    private void func_77026_a(final Tessellator par1Tessellator, final Icon par2Icon) {
        final float var3 = par2Icon.getMinU();
        final float var4 = par2Icon.getMaxU();
        final float var5 = par2Icon.getMinV();
        final float var6 = par2Icon.getMaxV();
        final float var7 = 1.0f;
        final float var8 = 0.5f;
        final float var9 = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        par1Tessellator.startDrawingQuads();
        par1Tessellator.setNormal(0.0f, 1.0f, 0.0f);
        par1Tessellator.addVertexWithUV(0.0f - var8, 0.0f - var9, 0.0, var3, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, 0.0f - var9, 0.0, var4, var6);
        par1Tessellator.addVertexWithUV(var7 - var8, var7 - var9, 0.0, var4, var5);
        par1Tessellator.addVertexWithUV(0.0f - var8, var7 - var9, 0.0, var3, var5);
        par1Tessellator.draw();
    }
}
