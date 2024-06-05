package gq.vapu.czfclient.Module.Modules.Render;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.Render.EventRender3D;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Killaura;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Render.ColorUtils;
import gq.vapu.czfclient.Util.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ChineseHat extends Module {
    public Option<Boolean> forSelf = new Option<>("For Self", "forself", true);
    public Option<Boolean> forTarget = new Option<>("For Target", "fortarget", false);
    public Option<Boolean> rainbow = new Option<>("Rainbow", "rainbow", false);

    public ChineseHat() {
        super("ChineseHat", new String[]{"hat"}, ModuleType.Render);
        this.addValues(forSelf, forTarget, rainbow);
    }

    @EventHandler
    public void onRender(EventRender3D event) {
        if (forSelf.getValue())
            drawChineseHatFor(Helper.mc.thePlayer);
        if (forTarget.getValue()) {
            if (Killaura.curTarget != null) {
                drawChineseHatFor(Killaura.curTarget);
            }
        }
    }

    private void drawChineseHatFor(EntityLivingBase entity) {
        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTranslated(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - RenderManager.renderPosX,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - RenderManager.renderPosY + entity.height + 0f,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - RenderManager.renderPosZ);
        GL11.glRotatef((entity.ticksExisted + mc.timer.renderPartialTicks) * 2f, 0f, 1f, 0f);

        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex3d(0.0, 0.3, 0.0);
        float radius = 0.7F;
        for(int i=0;i<360;i+=5) {
            if (rainbow.getValue())
                RenderUtils.glColor(ColorUtils.getRainbow().getRGB());
            else
                RenderUtils.glColor(new Color(210, 132, 246).getRGB());
            GL11.glVertex3d(Math.cos(i * Math.PI / 180.0) * radius, 0.0, Math.sin(i * Math.PI / 180.0) * radius);
        }
        GL11.glVertex3d(0.0, 0.3, 0.0);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GlStateManager.resetColor();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
