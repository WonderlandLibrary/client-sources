package net.futureclient.client.modules.render.chams;

import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;
import net.futureclient.client.modules.render.ESP;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Chams;
import net.futureclient.client.qF;
import net.futureclient.client.n;

public class Listener1 extends n<qF.EF>
{
    public final Chams k;
    
    public Listener1(final Chams k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((qF.EF)event);
    }
    
    @Override
    public void M(final qF.EF qf) {
        if (!ESP.F) {
            return;
        }
        if (!Chams.M(this.k, (Entity)qf.M())) {
            return;
        }
        qf.M(true);
        final boolean glIsEnabled = GL11.glIsEnabled(2896);
        final boolean glIsEnabled2 = GL11.glIsEnabled(3042);
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        if (!Chams.e(this.k).M()) {
            GL11.glDisable(3553);
        }
        if (glIsEnabled) {
            GL11.glDisable(2896);
        }
        if (!glIsEnabled2) {
            GL11.glEnable(3042);
        }
        GL11.glBlendFunc(770, 771);
        if (Chams.M(this.k).M()) {
            final float n = 0.0f;
            final int n2 = 0;
            final float n3 = 1.0f;
            GL11.glColor4f(n3, n, (float)n2, n3);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
            Chams.M(this.k, qf);
        }
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glEnable(2896);
        if (!Chams.e(this.k).M()) {
            GL11.glEnable(3553);
        }
        GL11.glEnable(3008);
        GL11.glPopAttrib();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        if (!Chams.e(this.k).M()) {
            GL11.glDisable(3553);
        }
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final float n4 = 1.0f;
        final float n5 = 0.0f;
        GL11.glColor4f(n5, n4, n5, 1.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
        final boolean b = glIsEnabled2;
        Chams.M(this.k, qf);
        if (!b) {
            GL11.glDisable(3042);
        }
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        if (glIsEnabled) {
            GL11.glEnable(2896);
        }
        if (!Chams.e(this.k).M()) {
            GL11.glEnable(3553);
        }
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
}
