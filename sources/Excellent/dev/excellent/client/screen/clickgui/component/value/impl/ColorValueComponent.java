package dev.excellent.client.screen.clickgui.component.value.impl;

import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.client.screen.clickgui.component.value.ValueComponent;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.font.Icon;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.glfw.Cursors;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.ColorValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ColorValueComponent extends ValueComponent implements IShader {
    private boolean colorPickerDown, huePickerDown;
    private float huePointer = 0;
    private float pickerWidth, pickerHeight;
    private Vector2f colorPointer = new Vector2f();
    private Color hueSelectorColor = Color.RED;

    public ColorValueComponent(Value<?> value, float width) {
        super(value, width - 10);
    }

    @Override
    public void init() {
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        ColorValue colorValue = (ColorValue) value;
        alphaAnimation.run(excellent.getClickGui().isExit() ? 0 : 1);

        float padding = 5F;
        float x = position.x;
        float y = position.y;

        pickerWidth = width - 4F;
        pickerHeight = (width / 2F);

        font12.draw(matrixStack, colorValue.getName(), x + padding, y + padding, ColorUtil.getColor(215, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        float xPicker = x + padding + 2F;
        float yPicker = y + padding + font12.getHeight() + padding;

        int rectAlpha = (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 255));

        if (isHover(mouseX, mouseY)) {
            if (isHover(mouseX, mouseY, xPicker, yPicker, pickerWidth, pickerHeight) || isHover(mouseX, mouseY, xPicker + 1, yPicker + pickerHeight + (padding / 1.5F) + 0.5F, pickerWidth - 2, 2F)) {
                if (!hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.HAND);
                    hovered = true;
                }
            } else {
                if (hovered) {
                    GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), Cursors.ARROW);
                    hovered = false;
                }
            }
        }

        RectUtil.drawRoundedRectShadowed(matrixStack, x + padding, yPicker - (padding / 2F), x + padding + width, yPicker - (padding / 2F) + pickerHeight + padding * 2F + (padding / 2F), 2F, 0.5F, ColorUtil.getColor(30, 30, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 100))), ColorUtil.getColor(30, 30, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 100))), ColorUtil.getColor(30, 30, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 100))), ColorUtil.getColor(30, 30, 40, (int) Mathf.clamp(0, 255, (alphaAnimation.getValue() * 100))), false, false, true, true);
        ROUNDED_GRADIENT.draw(xPicker, yPicker, pickerWidth, pickerHeight, 1, ColorUtil.getColor(0, 0, 0, rectAlpha), ColorUtil.getColor(255, 255, 255, rectAlpha), ColorUtil.getColor(0, 0, 0, rectAlpha), ColorUtil.replAlpha(hueSelectorColor.hashCode(), rectAlpha));
        rainbowRect(xPicker + 1, yPicker + pickerHeight + (padding / 1.5F) + 0.5F, pickerWidth - 2, 2F, (float) alphaAnimation.getValue());

        if (colorPickerDown) {
            colorPointer = new Vector2f((mouseX - xPicker), (mouseY - yPicker));
            colorPointer.x = MathHelper.clamp(colorPointer.x, 0f, pickerWidth);
            colorPointer.y = MathHelper.clamp(colorPointer.y, 0f, pickerHeight);
            Color color = ColorUtil.interpolate(Color.BLACK, ColorUtil.interpolate(hueSelectorColor, Color.WHITE, (colorPointer.x / pickerWidth)), (colorPointer.y / pickerHeight));
            colorValue.setValue(color);
        } else if (huePickerDown) {
            huePointer = (mouseX - xPicker);
            huePointer = (float) Mathf.clamp(0F + 1F, pickerWidth - 1F, huePointer);
            hueSelectorColor = Color.getHSBColor(((huePointer + 1F) / (pickerWidth - 1F)), 1F, 1F);
            Color color = ColorUtil.interpolate(Color.BLACK, ColorUtil.interpolate(hueSelectorColor, Color.WHITE, ((colorPointer.x + 1F) / (pickerWidth - 1F))), (colorPointer.y / pickerHeight));
            colorValue.setValue(color);

        }


        if (colorPointer.x != -1 && colorPointer.y != -1) {
            float colorwidth;
            colorwidth = 4F;
            RectUtil.drawRoundedRectShadowed(matrixStack, xPicker + colorPointer.x - (colorwidth / 2F), yPicker + colorPointer.y - (colorwidth / 2F), xPicker + colorPointer.x - (colorwidth / 2F) + colorwidth, yPicker + colorPointer.y - (colorwidth / 2F) + colorwidth, (colorwidth / 2F) - 0.5F, 0.5F, ColorUtil.getColor(0, 0, 0, rectAlpha), ColorUtil.getColor(0, 0, 0, rectAlpha), ColorUtil.getColor(0, 0, 0, rectAlpha), ColorUtil.getColor(0, 0, 0, rectAlpha), false, false, true, true);
            colorwidth = 3F;
            RectUtil.drawRoundedRectShadowed(matrixStack, xPicker + colorPointer.x - (colorwidth / 2F), yPicker + colorPointer.y - (colorwidth / 2F), xPicker + colorPointer.x - (colorwidth / 2F) + colorwidth, yPicker + colorPointer.y - (colorwidth / 2F) + colorwidth, (colorwidth / 2F) - 0.25F, 0.5F, ColorUtil.getColor(255, 255, 255, rectAlpha), ColorUtil.getColor(255, 255, 255, rectAlpha), ColorUtil.getColor(255, 255, 255, rectAlpha), ColorUtil.getColor(255, 255, 255, rectAlpha), false, false, true, true);
        }

        Fonts.ICON.get(10).drawCenter(matrixStack, Icon.DOWN.getCharacter(), xPicker + huePointer, yPicker + pickerHeight + (padding / 2F) - 1.5F - 2F, ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, (alphaAnimation.getValue() * 255))));

        height = padding + font12.getHeight() + padding + pickerHeight + padding + padding + padding;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (position == null) return;
        ColorValue colorValue = (ColorValue) value;

        float padding = 5;
        float x = position.x;
        float y = position.y;
        if (isLClick(button)) {
            float xPicker = x + padding + 2;
            float yPicker = y + padding + font12.getHeight() + padding;

            colorPickerDown = isHover(mouseX, mouseY, xPicker, yPicker, pickerWidth, pickerHeight);
            huePickerDown = isHover(mouseX, mouseY, x + padding, yPicker + pickerHeight, width, (padding + padding));
        }
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        colorPickerDown = huePickerDown = false;
    }

    @Override
    public void keyPressed(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }

    @Override
    public void onClose() {
        colorPickerDown = huePickerDown = false;
    }

    public void rainbowRect(double x, double y, double width, double height, float alpha) {
        RenderUtil.start();
        BUFFER.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        double step = 0.5;
        for (double position = x; position < x + width; position += step) {
            float colorHue = (float) ((position - x) / width);
            int color = Color.HSBtoRGB(colorHue, 1, 1);

            float r = ((color >> 16) & 0xFF) / 255.0f;
            float g = ((color >> 8) & 0xFF) / 255.0f;
            float b = (color & 0xFF) / 255.0f;

            BUFFER.pos(position, y, 0).color(r, g, b, alpha).endVertex();
            BUFFER.pos(position + step, y, 0).color(r, g, b, alpha).endVertex();
            BUFFER.pos(position + step, y + height, 0).color(r, g, b, alpha).endVertex();
            BUFFER.pos(position, y + height, 0).color(r, g, b, alpha).endVertex();
        }
        TESSELLATOR.draw();

        RenderUtil.stop();
    }
}
