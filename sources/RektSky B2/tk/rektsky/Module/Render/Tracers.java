package tk.rektsky.Module.Render;

import tk.rektsky.Module.*;
import tk.rektsky.Event.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;

public class Tracers extends Module
{
    public Tracers() {
        super("Tracers", "Draw lines to all players", 0, Category.RENDER);
    }
    
    @Override
    public void onEvent(final Event ev) {
        if (ev instanceof RenderEvent) {
            for (final Object o : this.mc.theWorld.loadedEntityList) {
                if (o instanceof Entity) {
                    final Entity e = (Entity)o;
                    if (!(e instanceof EntityPlayer) || e == this.mc.thePlayer) {
                        continue;
                    }
                    this.tracer(e, Color.cyan);
                }
            }
        }
    }
    
    private void tracer(final Entity e, final Color color) {
        GlStateManager.pushMatrix();
        GL11.glDisable(2929);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
        GL11.glLineWidth(1.0f);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0, this.mc.thePlayer.getEyeHeight(), 0.0);
        GL11.glVertex3d(e.posX - this.mc.thePlayer.posX, e.posY - this.mc.thePlayer.posY, e.posZ - this.mc.thePlayer.posZ);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GlStateManager.popMatrix();
    }
}
