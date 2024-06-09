package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;

public class BreadCrumbs extends ModuleContainer
{
    public static ArrayList<double[]> A;
    public static int B;
    public static final NumberValue width;
    
    public BreadCrumbs() {
        super(Category.RENDER, "BreadCrumbs", false, -1, "Draws Line behind player");
    }
    
    @SubscribeEvent
    public void D(final EventRenderWorld eventRenderWorld) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            if (this.k()) {
                ++BreadCrumbs.B;
                if (BreadCrumbs.B >= 50) {
                    BreadCrumbs.B = 0;
                    if (BreadCrumbs.A.size() > 5) {
                        BreadCrumbs.A.remove(0);
                    }
                }
                final Iterator<EntityPlayer> iterator = (Iterator<EntityPlayer>)Wrapper.mc.world.playerEntities.iterator();
                while (iterator.hasNext()) {
                    final EntityPlayer next;
                    if ((next = iterator.next()) instanceof EntityPlayer) {
                        final EntityPlayer entityPlayer;
                        final boolean b = (entityPlayer = next) == Wrapper.mc.player;
                        double n = renderPosY + 2.0;
                        if (Wrapper.mc.player.isElytraFlying()) {
                            n -= 1.5;
                        }
                        if (!b) {
                            continue;
                        }
                        BreadCrumbs.A.add(new double[] { renderPosX, n - entityPlayer.height, renderPosZ });
                    }
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @Override
    public void E() {
        BreadCrumbs.A.removeAll(BreadCrumbs.A);
    }
    
    static {
        width = ValueFactory.M("BreadCrumbs", "Width", "Line Thickness", 1.5, 0.5, 0.0, 10.0);
        BreadCrumbs.A = new ArrayList<double[]>();
        BreadCrumbs.B = 0;
    }
    
    public static double M(final double n) {
        if (n == 0.0) {
            return n;
        }
        if (n < 0.0) {
            return n * -1.0;
        }
        return n;
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        try {
            final double renderPosX = Wrapper.mc.getRenderManager().renderPosX;
            final double renderPosY = Wrapper.mc.getRenderManager().renderPosY;
            final double renderPosZ = Wrapper.mc.getRenderManager().renderPosZ;
            final float n = HUD.red.M() / 255.0f;
            final float n2 = HUD.green.M() / 255.0f;
            final float n3 = HUD.blue.M() / 255.0f;
            if (this.k()) {
                GL11.glPushMatrix();
                GL11.glLineWidth((float)BreadCrumbs.width.k());
                GL11.glBlendFunc(770, 771);
                GL11.glEnable(3042);
                GL11.glLineWidth((float)BreadCrumbs.width.k());
                GL11.glDisable(3553);
                GL11.glDisable(2929);
                GL11.glDepthMask(false);
                GL11.glBegin(3);
                final Iterator<double[]> iterator2;
                Iterator<double[]> iterator = iterator2 = BreadCrumbs.A.iterator();
                while (iterator.hasNext()) {
                    final double[] array;
                    final double m;
                    if ((m = M(Math.hypot((array = iterator2.next())[0] - Wrapper.mc.player.posX, array[1] - Wrapper.mc.player.posY))) > 100.0) {
                        iterator = iterator2;
                    }
                    else {
                        GL11.glColor4f(n, n2, n3, 1.0f - (float)(m / 100.0));
                        iterator = iterator2;
                        GL11.glVertex3d(array[0] - renderPosX, array[1] - renderPosY, array[2] - renderPosZ);
                    }
                }
                GL11.glEnd();
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
        catch (Exception ex) {}
    }
}
