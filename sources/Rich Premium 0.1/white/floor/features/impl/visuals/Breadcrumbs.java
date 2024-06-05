package white.floor.features.impl.visuals;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event3D;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.BreadcrumbHelper;

import java.awt.*;
import java.util.ArrayList;

public class Breadcrumbs extends Feature {

    ArrayList<BreadcrumbHelper> bcs = new ArrayList<BreadcrumbHelper>();

    public Breadcrumbs() {
        super("Breadcrumbs", "Trail under foots.",0, Category.VISUALS);
    }

    @EventTarget
    public void bek(EventUpdate bek) {
        bcs.add(new BreadcrumbHelper(mc.player.getPositionVector()));
    }

    @EventTarget
    public void xoxol(Event3D xlol) {
        if (isToggled()) {
            GL11.glPushMatrix();
            DrawHelper.enableSmoothLine(1f);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_CULL_FACE);
            mc.entityRenderer.disableLightmap();

            GlStateManager.resetColor();
            double lastPosX = 114514.0;
            double lastPosY = 114514.0;
            double lastPosZ = 114514.0;
            for (int i = 0; i < bcs.size(); i++) {
                BreadcrumbHelper bc = bcs.get(i);

                if(bcs.size() > 16)
                    bcs.remove(bc);

                if (!(lastPosX == 114514.0 && lastPosY == 114514.0 && lastPosZ == 114514.0)) {
                    DrawHelper.glColor(Main.getClientColor(i-i + 1, i , 32), 160);
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glVertex3d(bc.getVector().xCoord - mc.renderManager.renderPosX, bc.getVector().yCoord - mc.renderManager.renderPosY, bc.getVector().zCoord - mc.renderManager.renderPosZ);
                    GL11.glVertex3d(lastPosX, lastPosY, lastPosZ);
                    GL11.glVertex3d(lastPosX, lastPosY + mc.player.height, lastPosZ);
                    GL11.glVertex3d(bc.getVector().xCoord - mc.renderManager.renderPosX, bc.getVector().yCoord - mc.renderManager.renderPosY + mc.player.height, bc.getVector().zCoord - mc.renderManager.renderPosZ);
                    GL11.glEnd();
                }
                lastPosX = bc.getVector().xCoord - mc.renderManager.renderPosX;
                lastPosY = bc.getVector().yCoord - mc.renderManager.renderPosY;
                lastPosZ = bc.getVector().zCoord - mc.renderManager.renderPosZ;
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_BLEND);
            DrawHelper.disableSmoothLine();
            GL11.glPopMatrix();
        }
    }
}