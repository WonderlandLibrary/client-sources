package us.dev.direkt.module.internal.render.esp.modes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.event.internal.events.game.EventGameTick;
import us.dev.direkt.event.internal.events.game.render.EventRender3D;
import us.dev.direkt.module.internal.render.esp.ESP;
import us.dev.direkt.module.internal.render.esp.ESPMode;
import us.dev.direkt.util.render.Box;
import us.dev.direkt.util.render.OGLRender;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.concurrent.Executors;

import org.lwjgl.opengl.GL11;

/**
 * @author Foundry
 */
public class BoxMode extends ESPMode {

    public BoxMode() {
        super("Box");
    }

    @Listener
    protected Link<EventRender3D> on3D = new Link<>(event -> {
        Wrapper.getWorld().getLoadedEntityList().stream()
        .filter(e -> Direkt.getInstance().getModuleManager().getModule(ESP.class).doesQualify(e))
                .forEach(e -> {
            		float red;
            		float green;
            		float blue;
                    GL11.glPushMatrix();
                    OGLRender.enableGL3D(1.5f);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);

                    final double[] pos = OGLRender.interpolate(e);
                    final double x = pos[0];
                    final double y = pos[1];
                    final double z = pos[2];

    				if (e instanceof EntityPlayer && Direkt.getInstance().getFriendManager().isFriend((EntityPlayer) e)) {
    					red = 0.27F;
    					green = 0.70F;
    					blue = 0.92F;
    				} else if (e instanceof EntityLivingBase && ((EntityLivingBase) e).hurtTime != 0) {
    					red = 0.9F;
    					green = 0.0F;
    					blue = 0.0F;
    				} else {
    					red = 0.0F;
    					green = 0.9F;
    					blue = 0.0F;
    				}
                    
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, z);
                    GL11.glColor4f(red, green, blue, 0.1001f);
                    OGLRender.drawBox(new Box(e.getEntityBoundingBox().minX - e.posX - 0.15, e.getEntityBoundingBox().minY - e.posY - 0.02, e.getEntityBoundingBox().minZ - e.posZ - 0.15, e.getEntityBoundingBox().maxX - e.posX + 0.15, e.getEntityBoundingBox().maxY - e.posY + .2, e.getEntityBoundingBox().maxZ - e.posZ + 0.15));
                    GL11.glColor4f(red, green, blue, 1.0f);
                    OGLRender.drawOutlinedBox(new Box(e.getEntityBoundingBox().minX - e.posX - 0.15, e.getEntityBoundingBox().minY - e.posY - 0.02, e.getEntityBoundingBox().minZ - e.posZ - 0.15, e.getEntityBoundingBox().maxX - e.posX + 0.15, e.getEntityBoundingBox().maxY - e.posY + .2, e.getEntityBoundingBox().maxZ - e.posZ + 0.15));
                    GL11.glPopMatrix();

                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDisable(GL11.GL_BLEND);
                    OGLRender.disableGL3D();
                    GL11.glPopMatrix();
                });
    });


}
