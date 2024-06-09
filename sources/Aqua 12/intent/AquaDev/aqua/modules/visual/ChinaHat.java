// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import events.listeners.EventRender3D;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class ChinaHat extends Module
{
    public static long lastFrame;
    private int ticks;
    
    public ChinaHat() {
        super("ChinaHat", Type.Visual, "ChinaHat", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Blur", this, false));
        Aqua.setmgr.register(new Setting("Glow", this, false));
        Aqua.setmgr.register(new Setting("Colored", this, false));
        Aqua.setmgr.register(new Setting("Color", this));
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventRender3D) {
            if (ChinaHat.mc.gameSettings.thirdPersonView == 0) {
                return;
            }
            if (Aqua.setmgr.getSetting("ChinaHatGlow").isState()) {
                ShaderMultiplier.drawGlowESP(() -> this.render(), false);
            }
            if (Aqua.setmgr.getSetting("ChinaHatBlur").isState()) {
                Blur.drawBlurred(() -> this.render(), false);
            }
            if (Aqua.setmgr.getSetting("ChinaHatColored").isState()) {
                this.render();
            }
        }
    }
    
    public void render() {
        this.ticks += (int)(0.004 * (System.currentTimeMillis() - ChinaHat.lastFrame));
        ChinaHat.lastFrame = System.currentTimeMillis();
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glShadeModel(7425);
        GlStateManager.disableCull();
        GL11.glBegin(5);
        final double x = ChinaHat.mc.thePlayer.lastTickPosX + (ChinaHat.mc.thePlayer.posX - ChinaHat.mc.thePlayer.lastTickPosX) * ChinaHat.mc.timer.renderPartialTicks - ChinaHat.mc.getRenderManager().viewerPosX;
        final double y = ChinaHat.mc.thePlayer.lastTickPosY + (ChinaHat.mc.thePlayer.posY - ChinaHat.mc.thePlayer.lastTickPosY) * ChinaHat.mc.timer.renderPartialTicks - ChinaHat.mc.getRenderManager().viewerPosY + ChinaHat.mc.thePlayer.getEyeHeight() + 0.5;
        final double z = ChinaHat.mc.thePlayer.lastTickPosZ + (ChinaHat.mc.thePlayer.posZ - ChinaHat.mc.thePlayer.lastTickPosZ) * ChinaHat.mc.timer.renderPartialTicks - ChinaHat.mc.getRenderManager().viewerPosZ;
        final double rad = 0.6499999761581421;
        int q = 64;
        boolean increaseCount = false;
        q = 1024;
        increaseCount = true;
        for (float i = 0.0f; i < 6.283185307179586 + (increaseCount ? 0.01 : 0.0); i += (float)(12.566370614359172 / q)) {
            final double vecX = x + 0.6499999761581421 * Math.cos(i);
            final double vecZ = z + 0.6499999761581421 * Math.sin(i);
            final Color c;
            final Color color = c = new Color(Aqua.setmgr.getSetting("ChinaHatColor").getColor());
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 0.8f);
            GL11.glVertex3d(vecX, y - 0.25, vecZ);
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, 0.8f);
            GL11.glVertex3d(x, y, z);
        }
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GlStateManager.enableCull();
        GL11.glDisable(2848);
        GL11.glEnable(2832);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
        GL11.glColor3f(255.0f, 255.0f, 255.0f);
    }
    
    static {
        ChinaHat.lastFrame = 0L;
    }
}
