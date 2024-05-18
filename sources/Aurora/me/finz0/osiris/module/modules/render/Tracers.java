package me.finz0.osiris.module.modules.render;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.event.events.RenderEvent;
import me.finz0.osiris.util.OsirisTessellator;
import me.finz0.osiris.util.Rainbow;
import me.finz0.osiris.friends.Friends;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Tracers extends Module {
    public Tracers() {
        super("Tracers", Category.RENDER, "Draw lines to players");
    }

    Setting opacity;

    public void setup(){
        AuroraMod.getInstance().settingsManager.rSetting(opacity = new Setting("Alpha", this, 1, 0, 1, false, "TracersAlpha"));
    }

    public void onWorldRender(RenderEvent event){
        OsirisTessellator.prepareGL();
        GlStateManager.pushMatrix();
        mc.world.loadedEntityList.forEach(e->{
            if(e instanceof EntityPlayer && e != mc.player){
                if(Friends.isFriend(e.getName())) {
                    Color c = Rainbow.getColor();
                    drawLineToEntity(e, c.getRed(), c.getGreen(), c.getBlue(), (float) opacity.getValDouble());
                }else {
                    drawLineToEntity(e, 1, 1, 1, (float) opacity.getValDouble());
                }
            }
        });
        OsirisTessellator.releaseGL();
        GlStateManager.popMatrix();
    }


    public static double interpolate(double now, double then) {
        return then + (now - then) * mc.getRenderPartialTicks();
    }

    public static double[] interpolate(Entity entity) {
        double posX = interpolate(entity.posX, entity.lastTickPosX) - mc.getRenderManager().renderPosX;
        double posY = interpolate(entity.posY, entity.lastTickPosY) - mc.getRenderManager().renderPosY;
        double posZ = interpolate(entity.posZ, entity.lastTickPosZ) - mc.getRenderManager().renderPosZ;
        return new double[] { posX, posY, posZ };
    }

    public static void drawLineToEntity(Entity e, float red, float green, float blue, float opacity){
        double[] xyz = interpolate(e);
        drawLine(xyz[0],xyz[1],xyz[2], e.height, red, green, blue, opacity);
    }

    public static void drawLine(double posx, double posy, double posz, double up, float red, float green, float blue, float opacity)
    {
        Vec3d eyes = new Vec3d(0, 0, 1)
                .rotatePitch(-(float)Math
                        .toRadians(Minecraft.getMinecraft().player.rotationPitch))
                .rotateYaw(-(float)Math
                        .toRadians(Minecraft.getMinecraft().player.rotationYaw));

        drawLineFromPosToPos(eyes.x, eyes.y + mc.player.getEyeHeight(), eyes.z, posx, posy, posz, up, red, green, blue, opacity);
    }

    public static void drawLineFromPosToPos(double posx, double posy, double posz, double posx2, double posy2, double posz2, double up, float red, float green, float blue, float opacity){
        GL11.glBlendFunc(770, 771);
        //GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, opacity);
        GlStateManager.disableLighting();
        GL11.glLoadIdentity();
        mc.entityRenderer.orientCamera(mc.getRenderPartialTicks());

        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex3d(posx, posy, posz);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2, posz2);
            GL11.glVertex3d(posx2, posy2+up, posz2);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        //GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor3d(1d,1d,1d);
        GlStateManager.enableLighting();
    }
}
