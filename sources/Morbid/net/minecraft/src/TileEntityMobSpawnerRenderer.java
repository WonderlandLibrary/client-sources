package net.minecraft.src;

import org.lwjgl.opengl.*;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityMobSpawner(final TileEntityMobSpawner par1TileEntityMobSpawner, final double par2, final double par4, final double par6, final float par8) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2 + 0.5f, (float)par4, (float)par6 + 0.5f);
        func_98144_a(par1TileEntityMobSpawner.func_98049_a(), par2, par4, par6, par8);
        GL11.glPopMatrix();
    }
    
    public static void func_98144_a(final MobSpawnerBaseLogic par0MobSpawnerBaseLogic, final double par1, final double par3, final double par5, final float par7) {
        final Entity var8 = par0MobSpawnerBaseLogic.func_98281_h();
        if (var8 != null) {
            var8.setWorld(par0MobSpawnerBaseLogic.getSpawnerWorld());
            final float var9 = 0.4375f;
            GL11.glTranslatef(0.0f, 0.4f, 0.0f);
            GL11.glRotatef((float)(par0MobSpawnerBaseLogic.field_98284_d + (par0MobSpawnerBaseLogic.field_98287_c - par0MobSpawnerBaseLogic.field_98284_d) * par7) * 10.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-30.0f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(0.0f, -0.4f, 0.0f);
            GL11.glScalef(var9, var9, var9);
            var8.setLocationAndAngles(par1, par3, par5, 0.0f, 0.0f);
            RenderManager.instance.renderEntityWithPosYaw(var8, 0.0, 0.0, 0.0, 0.0f, par7);
        }
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity par1TileEntity, final double par2, final double par4, final double par6, final float par8) {
        this.renderTileEntityMobSpawner((TileEntityMobSpawner)par1TileEntity, par2, par4, par6, par8);
    }
}
