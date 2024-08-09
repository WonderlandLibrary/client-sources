package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.DragValue;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.mojang.blaze3d.platform.GlStateManager;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

@ModuleInfo(name = "Motion Graph", description = "Отображает график вашего движения.", category = Category.RENDER)
public class MotionGraph extends Module implements IRenderAccess {
    private final DragValue position = new DragValue("Position", this, new Vector2d(100, 100));
    private final ArrayList<Double> height = new ArrayList<Double>();
    private double bps;
    private final Listener<MotionEvent> onMotion = event -> {
        double speed = Math.hypot(mc.player.motion.x, mc.player.motion.z) * 44.0D * mc.timer.getSpeed();
        this.bps = Interpolator.lerp(speed, this.bps, 0.5D);
        this.height.add(this.bps);

        if (this.height.size() >= position.size.x) {
            this.height.remove(0);
        }
    };

    private final Listener<Render2DEvent> onRender2D = event -> {

        double x, y, width, height;

        x = position.position.x;
        y = position.position.y;

        width = 93F;
        height = 23;

        position.getSize().set(width, height);

        if (mc.gameSettings.showDebugInfo) return;

        RenderUtil.renderClientRect(event.getMatrix(), (float) x, (float) y, (float) width, (float) height, false, height);

        GlStateManager.pushMatrix();

        RenderUtil.start();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(2);

        BUFFER.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
        int color1 = getTheme().getClientColorC(0).hashCode();
        int color2 = getTheme().getClientColorC(90).hashCode();
        int color3 = getTheme().getClientColorC(180).hashCode();
        int color4 = getTheme().getClientColorC(270).hashCode();
        for (int i = 0; i < this.height.size(); i++) {
            double posX = x + i + 1;
            double posY = y + height - 4 - Math.min(Math.max(0, height - 10), this.height.get(i));

            int overXCol1 = ColorUtil.overCol(color1, color2, (float) ((posX - x) / width));
            int overXCol2 = ColorUtil.overCol(color4, color3, (float) ((posX - x) / width));
            int vecColor = ColorUtil.overCol(overXCol1, overXCol2, (float) ((posY - y) / height));

            BUFFER.pos(posX, posY, 0).color(vecColor).endVertex();
        }

        TESSELLATOR.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderUtil.stop();

        GlStateManager.popMatrix();

    };
}
