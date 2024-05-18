package de.labystudio.capes;

import de.labystudio.labymod.LabyMod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MineconRenderer
{
    private static ResourceLocation starTexture = new ResourceLocation("star.png");
    private static Random random = LabyMod.random;
    private long lastSpawn = 0L;
    private ArrayList<Star> stars;

    public void render(AbstractClientPlayer abstractClientPlayer)
    {
        if (this.stars == null)
        {
            this.stars = new ArrayList();
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        float f = LabyMod.getInstance().getPartialTicks();
        minecraft.getTextureManager().bindTexture(starTexture);

        if (this.lastSpawn < System.currentTimeMillis())
        {
            this.stars.add(new Star(random));
            this.lastSpawn = System.currentTimeMillis() + (long)random.nextInt(1000);
        }

        GlStateManager.pushMatrix();
        this.transform(abstractClientPlayer, f);
        Star star;

        for (Iterator<Star> iterator = this.stars.iterator(); iterator.hasNext(); star.draw())
        {
            star = (Star)iterator.next();

            if (star.isKilled())
            {
                iterator.remove();
            }
        }

        GlStateManager.popMatrix();
    }

    private void transform(AbstractClientPlayer entity, float partialTicks)
    {
        GlStateManager.translate(0.0F, 0.0F, 0.125F);
        double d0 = entity.prevChasingPosX + (entity.chasingPosX - entity.prevChasingPosX) * (double)partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks);
        double d1 = entity.prevChasingPosY + (entity.chasingPosY - entity.prevChasingPosY) * (double)partialTicks - (entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks);
        double d2 = entity.prevChasingPosZ + (entity.chasingPosZ - entity.prevChasingPosZ) * (double)partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks);
        float f = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * partialTicks;
        double d3 = (double)MathHelper.sin(f * (float)Math.PI / 180.0F);
        double d4 = (double)(-MathHelper.cos(f * (float)Math.PI / 180.0F));
        float f1 = (float)d1 * 10.0F;
        f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
        float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
        float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        float f4 = entity.prevCameraYaw + (entity.cameraYaw - entity.prevCameraYaw) * partialTicks;
        f1 = f1 + MathHelper.sin((entity.prevDistanceWalkedModified + (entity.distanceWalkedModified - entity.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

        if (entity.isSneaking())
        {
            f1 += 25.0F;
        }

        GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
    }
}
