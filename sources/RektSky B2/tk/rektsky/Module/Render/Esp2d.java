package tk.rektsky.Module.Render;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class Esp2d extends Module
{
    public static String highlighted;
    
    public Esp2d() {
        super("ESP", "Shows where players are", 0, Category.RENDER);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof RenderEvent) {
            for (final Object o : this.mc.theWorld.getLoadedEntityList()) {
                if (o instanceof EntityPlayer) {
                    if (o == this.mc.thePlayer) {
                        continue;
                    }
                    this.esp2d((Entity)o, Color.WHITE);
                }
            }
        }
    }
    
    public void esp2d(final Entity e, final Color color) {
        GlStateManager.pushMatrix();
        GL11.glTranslated(e.posX - this.mc.renderManager.viewerPosX, e.posY - this.mc.renderManager.viewerPosY, e.posZ - this.mc.renderManager.viewerPosZ);
        GL11.glRotatef(-this.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glTranslated(-(e.posX - this.mc.renderManager.viewerPosX), -(e.posY - this.mc.renderManager.viewerPosY), -(e.posZ - this.mc.renderManager.viewerPosZ));
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDisable(2912);
        GlStateManager.color(255.0f, 255.0f, 255.0f);
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), 255.0f);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(2);
        GL11.glVertex3d(e.posX - this.mc.renderManager.viewerPosX - e.width, e.posY - this.mc.renderManager.viewerPosY, e.posZ - this.mc.renderManager.viewerPosZ);
        GL11.glVertex3d(e.posX - this.mc.renderManager.viewerPosX + e.width, e.posY - this.mc.renderManager.viewerPosY, e.posZ - this.mc.renderManager.viewerPosZ);
        GL11.glVertex3d(e.posX - this.mc.renderManager.viewerPosX + e.width, e.posY - this.mc.renderManager.viewerPosY + e.height, e.posZ - this.mc.renderManager.viewerPosZ);
        GL11.glVertex3d(e.posX - this.mc.renderManager.viewerPosX - e.width, e.posY - this.mc.renderManager.viewerPosY + e.height, e.posZ - this.mc.renderManager.viewerPosZ);
        GL11.glEnd();
        GL11.glRotatef(-this.mc.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(this.mc.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glEnable(2929);
        GL11.glEnable(2912);
        GL11.glEnable(3553);
        GlStateManager.popMatrix();
    }
    
    static {
        Esp2d.highlighted = "";
    }
}
