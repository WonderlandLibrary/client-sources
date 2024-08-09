package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.StyleManager;
import fun.ellant.utils.drag.Dragging;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class MotionGraphRenderer implements ElementRenderer {
    private final Dragging dragging;
    private final Minecraft mc = Minecraft.getInstance();
    private final ArrayList<Double> height = new ArrayList<>();
    private double bps;


    public MotionGraphRenderer(Dragging dragging) {
        this.dragging = dragging;

    }
    public void tick() {
        double speed = Math.hypot(mc.player.getMotion().x, mc.player.getMotion().z) * 22.0D;
        this.bps = interpolate(speed, this.bps, 0.5D);
        this.height.add(this.bps);

        if (this.height.size() >= 80) {
            this.height.remove(0);
        }
    }
    private double interpolate(double target, double current, double delta) {
        return current + (target - current) * delta;
    }

    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = this.dragging.getX();
        float posY = this.dragging.getY();

        renderMotionGraph(ms, posX, posY);
        Scissor.unset();
    }

    private void renderMotionGraph(MatrixStack ms, float posX, float posY) {
        int width = 61;
        int height = 20;

        int color = ColorUtils.rgba(0,0,0,190);
        DisplayUtils.drawRoundedRect((int) posX, (int) posY, width, height, 5, color);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(2F);

        GL11.glBegin(GL11.GL_LINE_STRIP);

        StyleManager styleManager = Ellant.getInstance().getStyleManager();
        int firstColor = styleManager.getCurrentStyle().getFirstColor().getRGB();
        float alpha = ((firstColor >> 24) & 0xFF) / 255.0f;
        float red = ((firstColor >> 16) & 0xFF) / 255.0f;
        float green = ((firstColor >> 8) & 0xFF) / 255.0f;
        float blue = (firstColor & 0xFF) / 255.0f;

        for (int i = 0; i < this.height.size(); i++) {
            GL11.glColor4f(red, green, blue, alpha);

            double x = posX + i / 1.3 + 0.5;
            double y = posY + height - 5 - Math.max(0, Math.min(height - 15, this.height.get(i)));

            GL11.glVertex2d(x, y);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        dragging.setWidth(16 * 4 + 2 * 3 + 15);
        dragging.setHeight(30);
    }
    public void update(EventUpdate e) {
    }
}