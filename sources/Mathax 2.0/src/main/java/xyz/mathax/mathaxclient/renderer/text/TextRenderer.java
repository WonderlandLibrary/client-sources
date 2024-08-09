package xyz.mathax.mathaxclient.renderer.text;

import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public interface TextRenderer {
    static TextRenderer get(TextRendererType rendererType) {
        switch (rendererType) {
            case Custom -> {
                return Fonts.RENDERER;
            }
            case Vanilla -> {
                return VanillaTextRenderer.INSTANCE;
            }
            default -> {
                return Systems.get(Themes.class).getTheme().customFont() ? Fonts.RENDERER : VanillaTextRenderer.INSTANCE;
            }
        }
    }

    static TextRenderer get() {
        return get(TextRendererType.Config);
    }

    double getScale();

    void setScale(double scale);

    double getAlpha();

    void setAlpha(double alpha);

    boolean getShadow();

    void setShadow(boolean shadow);

    void begin(double scale, boolean scaleOnly, boolean big, boolean shadow);

    default void begin(double scale, boolean scaleOnly, boolean big) {
        begin(scale, scaleOnly, big, false);
    }

    default void begin(double scale, boolean shadow) {
        begin(scale, false, false, shadow);
    }

    default void begin(double scale) {
        begin(scale, false, false, false);
    }

    default void begin() {
        begin(1, false, false, false);
    }

    default void beginBig() {
        begin(1, false, true);
    }

    double getWidth(String text, int length, boolean shadow);

    default double getWidth(String text, boolean shadow) {
        return getWidth(text, text.length(), shadow);
    }

    default double getWidth(String text) { return getWidth(text, text.length(), false); }

    double getHeight(boolean shadow);

    default double getHeight() {
        return getHeight(false);
    }

    double render(String text, double x, double y, Color color);

    double render(String text, double x, double y, Color color, boolean shadow);

    double render(List<Section> sections, double x, double y);

    boolean isBuilding();

    boolean isBuilt();

    void end(MatrixStack matrixStack);

    default void end() {
        end(null);
    }
}