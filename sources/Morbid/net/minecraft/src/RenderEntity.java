package net.minecraft.src;

import org.lwjgl.opengl.*;

public class RenderEntity extends Render
{
    @Override
    public void doRender(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final float par9) {
        GL11.glPushMatrix();
        Render.renderOffsetAABB(par1Entity.boundingBox, par2 - par1Entity.lastTickPosX, par4 - par1Entity.lastTickPosY, par6 - par1Entity.lastTickPosZ);
        GL11.glPopMatrix();
    }
}
