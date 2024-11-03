package net.augustus.modules.render;

import net.augustus.events.EventClickGui;
import net.augustus.events.EventRender2D;
import net.augustus.events.EventShaderRender;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.rise5.RenderUtil;
import net.augustus.utils.skid.vestige.ColorUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MotionGraph extends Module {
    private double lastWidth;

    public MotionGraph() {
        super("MotionGraph", Color.CYAN, Categorys.RENDER);
    }
    private final java.util.ArrayList<Float> speeds = new java.util.ArrayList<>();
    private double lastVertices;
    private float biggestCock;
    private final BooleanValue rgb = new BooleanValue(136, "RGB", this, false);
    public DoubleValue width = new DoubleValue(3167, "Width", this, 180, 100, 300, 0);
    public DoubleValue height = new DoubleValue(2472, "Height", this, 5, 1, 20, 0);
    public DoubleValue y = new DoubleValue(2472, "YOffset", this, -210, -500, 500, 0);

    @EventTarget
    public void onChange(EventClickGui eventClickGui) {
        if (lastVertices != 100) {
            synchronized (speeds) {
                speeds.clear();
                biggestCock = 0;
            }
        }

        lastVertices = 100;
    }

    @EventTarget
    public void onTick(EventTick eventTick) {
        if (speeds.size() > 100 - 2) {
            speeds.remove(0);
        }

        speeds.add((float) MoveUtil.getSpeed() * mc.timer.timerSpeed);

        biggestCock = -1;
        for (final float f : speeds) {
            if (f > biggestCock) {
                biggestCock = f;
            }
        }
    }

    @EventTarget
    public void onShader(EventShaderRender eventShaderRender) {
        if(mm.shaders.isToggled() && mm.shaders.blur.getBoolean()) {
            render();
        }
    }

    @EventTarget
    public void onNotShader(EventRender2D eventShaderRender) {
        if(!(mm.shaders.isToggled() && mm.shaders.blur.getBoolean())) {
            render();
        }
    }

    public void render() {
        Color color;
        if(rgb.getBoolean()) {
            color = ColorUtils.getRainbow(4.0F, 0.5F, 1.0F);
        } else {
            color = Color.white;
        }
        final ScaledResolution sr = new ScaledResolution(mc);

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBegin(GL11.GL_LINES);

        if (speeds.size() > 3) {
            final float width = (float) (sr.getScaledWidth() / 2f - this.width.getValue() / 2f);

            for (int i = 0; i < speeds.size() - 1; i++) {
                RenderUtil.color(color);
                final float y = (float) (speeds.get(i) * 10 * height.getValue());
                final float y2 = (float) (speeds.get(i + 1) * 10 * height.getValue());
                final float length = (float) (this.width.getValue() / (speeds.size() - 1));

                GL11.glVertex2f(width + (i * length), (float) (sr.getScaledHeight() / 2F - Math.min(y, 50) - this.y.getValue()));
                GL11.glVertex2f(width + ((i + 1) * length), (float) (sr.getScaledHeight() / 2F - Math.min(y2, 50) - this.y.getValue()));
            }
        }
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        RenderUtil.color(Color.WHITE);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
    }
}
