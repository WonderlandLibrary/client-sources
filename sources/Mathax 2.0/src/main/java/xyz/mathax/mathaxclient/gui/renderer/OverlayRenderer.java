package xyz.mathax.mathaxclient.gui.renderer;

import net.minecraft.client.util.math.MatrixStack;
import xyz.mathax.mathaxclient.renderer.text.Section;
import xyz.mathax.mathaxclient.renderer.text.TextRenderer;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.render.color.Color;

import java.util.ArrayList;
import java.util.List;

public class OverlayRenderer {
    private final List<Runnable> postTasks = new ArrayList<>();

    private TextRenderer textRenderer;

    private boolean shadow;

    public double scale;
    public double delta;

    public void begin(double scale, double frameDelta, boolean scaleOnly, boolean shadow) {
        this.textRenderer = Systems.get(Themes.class).getTheme().textRenderer();
        this.textRenderer.begin(scale, scaleOnly, false, shadow);

        this.shadow = Systems.get(Themes.class).getTheme().fontShadow();
        this.scale = scale;
        this.delta = frameDelta;
    }

    public void begin(double scale, double frameDelta, boolean scaleOnly) {
        begin(scale, frameDelta, scaleOnly, shadow);
    }

    public void end(MatrixStack matrixStack) {
        textRenderer.end(matrixStack);

        for (Runnable runnable : postTasks) {
            runnable.run();
        }

        postTasks.clear();
    }

    public void end() {
        end(null);
    }

    public void text(String text, double x, double y, Color color, boolean shadow) {
        textRenderer.render(text, x, y, color, shadow);
    }

    public void text(String text, double x, double y, Color color) {
        text(text, x, y, color, shadow);
    }

    public void text(List<Section> sections, double x, double y) {
        textRenderer.render(sections, x, y);
    }

    public double textWidth(String text, boolean shadow) {
        return textRenderer.getWidth(text, shadow);
    }

    public double textWidth(String text) {
        return textWidth(text, shadow);
    }

    public double textHeight(boolean shadow) {
        return textRenderer.getHeight(shadow);
    }

    public double textHeight() {
        return textHeight(shadow);
    }

    public void addPostTask(Runnable runnable) {
        postTasks.add(runnable);
    }
}