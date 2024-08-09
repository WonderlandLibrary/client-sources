package xyz.mathax.mathaxclient.renderer.text;

import com.mojang.blaze3d.systems.RenderSystem;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import xyz.mathax.mathaxclient.MatHax;

import java.util.List;

public class VanillaTextRenderer implements TextRenderer {
    public static final VanillaTextRenderer INSTANCE = new VanillaTextRenderer();

    private final BufferBuilder buffer = new BufferBuilder(2048);
    private final VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(buffer);

    private final MatrixStack matrixStack = new MatrixStack();
    private final Matrix4f emptyMatrix4f = new Matrix4f();

    public double scale = 2;
    public boolean scaleIndividually;

    public boolean shadow;

    private boolean built;
    private boolean building;
    private double alpha = 1;

    private VanillaTextRenderer() {} // Use INSTANCE

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public double getAlpha() {
        return alpha;
    }

    @Override
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean getShadow() {
        return shadow;
    }

    @Override
    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    @Override
    public double getWidth(String text, int length, boolean shadow) {
        if (text.isEmpty()) {
            return 0;
        }

        if (length != text.length()) {
            text = text.substring(0, length);
        }

        return (MatHax.mc.textRenderer.getWidth(text) + (shadow ? 1 : 0)) * scale;
    }

    @Override
    public double getHeight(boolean shadow) {
        return (MatHax.mc.textRenderer.fontHeight + (shadow ? 1 : 0)) * scale;
    }

    @Override
    public void begin(double scale, boolean scaleOnly, boolean big, boolean shadow) {
        if (building) {
            throw new RuntimeException("VanillaTextRenderer.begin() called twice");
        }

        this.scale = scale * 2;
        this.shadow = shadow;
        built = false;
        building = true;
    }

    @Override
    public double render(String text, double x, double y, Color color, boolean shadow) {
        boolean wasBuilding = building;
        if (!wasBuilding) {
            begin();
        }

        x += 0.5 * scale;
        y += 0.5 * scale;

        int preA = color.a;
        color.a = (int) ((color.a / 255 * alpha) * 255);

        Matrix4f matrix4f = emptyMatrix4f;
        if (scaleIndividually) {
            matrixStack.push();
            matrixStack.scale((float) scale, (float) scale, 1);
            matrix4f = matrixStack.peek().getPositionMatrix();
        }

        double x2 = MatHax.mc.textRenderer.draw(text, (float) (x / scale), (float) (y / scale), color.getPacked(), shadow, matrix4f, immediate, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);

        if (scaleIndividually) {
            matrixStack.pop();
        }

        color.a = preA;

        if (!wasBuilding) {
            end();
        }

        return (x2 - 1) * scale;
    }

    @Override
    public double render(String text, double x, double y, Color color) {
        return render(text, x, y, color, shadow);
    }

    @Override
    public double render(List<Section> sections, double x, double y) {
        boolean wasBuilding = building;
        if (!wasBuilding) {
            begin();
        }

        double x2 = 0;
        boolean notFirst = false;
        for (Section section : sections) {
            boolean shadow;
            if (section.shadow == SectionShadow.Undefined) {
                shadow = this.shadow;
            } else {
                shadow = section.shadow == SectionShadow.Render;
            }

            x += 0.5 * scale;
            y += 0.5 * scale;

            int preA = section.color.a;
            section.color.a = (int) ((section.color.a / 255 * alpha) * 255);

            Matrix4f matrix4f = emptyMatrix4f;
            if (scaleIndividually) {
                matrixStack.push();
                matrixStack.scale((float) scale, (float) scale, 1);
                matrix4f = matrixStack.peek().getPositionMatrix();
            }

            x2 = MatHax.mc.textRenderer.draw(section.text, (notFirst ? 0.0F : (float) (x / scale)) + (float) x2 - (notFirst ? 2.0F : 0.0F), (float) (y / scale), section.color.getPacked(), shadow, matrix4f, immediate, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);

            if (scaleIndividually) {
                matrixStack.pop();
            }

            section.color.a = preA;

            notFirst = true;
        }

        if (!wasBuilding) {
            end();
        }

        return (x2 - 1) * scale;
    }

    @Override
    public boolean isBuilding() {
        return building;
    }

    @Override
    public boolean isBuilt() {
        return built;
    }

    @Override
    public void end(MatrixStack matrixStack) {
        if (!building) {
            throw new RuntimeException("VanillaTextRenderer.end() called without calling begin()");
        }

        MatrixStack matrixStack1 = RenderSystem.getModelViewStack();

        RenderSystem.disableDepthTest();
        matrixStack1.push();
        if (matrixStack != null) {
            matrixStack1.multiplyPositionMatrix(matrixStack.peek().getPositionMatrix());
        }

        if (!scaleIndividually) {
            matrixStack1.scale((float) scale, (float) scale, 1);
        }

        RenderSystem.applyModelViewMatrix();

        immediate.draw();

        matrixStack1.pop();
        RenderSystem.enableDepthTest();
        RenderSystem.applyModelViewMatrix();

        this.scale = 2;
        building = false;
        built = true;
    }
}
