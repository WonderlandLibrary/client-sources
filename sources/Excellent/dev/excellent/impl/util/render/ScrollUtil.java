package dev.excellent.impl.util.render;

import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.api.interfaces.game.IWindow;
import dev.excellent.impl.util.math.Interpolator;
import dev.excellent.impl.util.math.Mathf;
import lombok.Data;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;

import java.awt.*;

@Data
public class ScrollUtil implements IMinecraft, IWindow {
    private double target, scroll, max;
    private double speed = 8F;
    private boolean enabled;
    private boolean autoReset;

    public ScrollUtil() {
        setEnabled(true);
        setAutoReset(true);
    }

    public void update() {
        if (enabled) {
            double wheel = mc.mouseHelper.getWheel() * (speed * 10F);
            double stretch = 0;
            target = Math.min(Math.max(target + wheel / 2, max - (wheel == 0 ? 0 : stretch)), (wheel == 0 ? 0 : stretch));
            resetWheel();
        }
        if (autoReset) resetWheel();
        scroll = Interpolator.lerp(scroll, target, 0.1F).floatValue();
    }

    private void resetWheel() {
        mc.mouseHelper.setWheel(0);
    }

    public void render(MatrixStack matrixStack, Vector2f position, float maxHeight) {
        render(matrixStack, position, maxHeight, 255);
    }

    public void render(MatrixStack matrixStack, Vector2f position, float maxHeight, float alpha) {
        double percentage = (getScroll() / getMax());
        double barHeight = maxHeight - ((getMax() / (getMax() - maxHeight)) * maxHeight);
        boolean allowed = (barHeight < maxHeight);
        if (!allowed) return;
        double scrollX = position.x;
        double scrollY = position.y + (maxHeight * percentage) - (barHeight * percentage);
        ScissorUtil.enable();
        ScissorUtil.scissor(mc.getMainWindow(), position.x, position.y, 0.5F, maxHeight);
        RectUtil.drawRect(matrixStack, (float) scrollX, (float) scrollY, (float) scrollX + 0.5F, (float) scrollY + (float) barHeight, new Color(255, 255, 255, (int) Mathf.clamp(0, 255, alpha)).hashCode());
        ScissorUtil.disable();
    }

    public void renderH(MatrixStack matrixStack, Vector2f position, float maxWidth) {
        renderH(matrixStack, position, maxWidth, 255);
    }

    public void renderH(MatrixStack matrixStack, Vector2f position, float maxWidth, float alpha) {
        double percentage = (getScroll() / getMax());
        double barWidth = maxWidth - ((getMax() / (getMax() - maxWidth)) * maxWidth);
        boolean allowed = (barWidth < maxWidth);
        if (!allowed) return;
        double scrollX = position.x + (maxWidth * percentage) - (barWidth * percentage);
        double scrollY = position.y;
        ScissorUtil.enable();
        ScissorUtil.scissor(mc.getMainWindow(), position.x, position.y, maxWidth, 0.5);
        RectUtil.drawRect(matrixStack, (float) scrollX, (float) scrollY, (float) (scrollX + barWidth), (float) (scrollY + 0.5), new Color(255, 255, 255, (int) Mathf.clamp(0, 255, alpha)).hashCode());
        ScissorUtil.disable();
    }

    public void reset() {
        this.scroll = 0F;
        this.target = 0F;
    }

    public void setMax(float max, float height) {
        this.max = -max + height;
    }
}