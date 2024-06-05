package io.github.nevalackin.radium.gui.click.component.impl.component.property.impl;

import io.github.nevalackin.radium.gui.click.ClickGui;
import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.ExpandableComponent;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.PropertyComponent;
import io.github.nevalackin.radium.gui.click.component.impl.panel.impl.CategoryPanel;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import io.github.nevalackin.radium.utils.render.RenderingUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ColorPropertyComponent extends ExpandableComponent implements PropertyComponent {

    private static final int COLOR_PICKER_HEIGHT = 80;
    public static Tessellator tessellator = Tessellator.getInstance();
    public static WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    private final Property<Integer> colorProperty;
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;

    private boolean colorSelectorDragging;

    private boolean hueSelectorDragging;

    private boolean alphaSelectorDragging;

    // TODO: Copy and paste colors
    public ColorPropertyComponent(Component parent, Property<Integer> colorProperty, int x, int y, int width, int height) {
        super(parent, colorProperty.getLabel(), x, y, width, height);

        this.colorProperty = colorProperty;

        this.colorProperty.addValueChangeListener(((oldValue, value) -> {
            float[] hsb = getHSBFromColor(value);
            this.hue = hsb[0];
            this.saturation = hsb[1];
            this.brightness = hsb[2];

            this.alpha = (value >> 24 & 0xFF) / 255.0F;
        }));
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();

        int textColor = 0xFFFFFF;
        int bgColor = getSecondaryBackgroundColor(isHovered(mouseX, mouseY));

        boolean hovered = isHovered(mouseX, mouseY);

        Gui.drawRect(x, y, x + width, y + height, bgColor);
        Wrapper.getFontRenderer().drawStringWithShadow(getName(), x + 2, y + height / 2.0F - 3, textColor);

        float left = x + width - 13;
        float top = y + height / 2.0F - 2;
        float right = x + width - 2;
        float bottom = y + height / 2.0F + 2;

        Gui.drawRect(left - 0.5F, top - 0.5F, right + 0.5F,
                bottom + 0.5F, 0xFF000000);
        drawCheckeredBackground(left, top, right, bottom);
        Gui.drawRect(left, top, right, bottom, colorProperty.getValue());

        if (isExpanded()) {
            // Draw Background
            Gui.drawRect(x + CategoryPanel.X_ITEM_OFFSET, y + height,
                    x + width - CategoryPanel.X_ITEM_OFFSET, y + getHeightWithExpand(),
                    ClickGui.getInstance().getPalette().getSecondaryBackgroundColor().getRGB());

            // Draw Color Picker
            {
                // Box with gradient
                float cpLeft = x + 2;
                float cpTop = y + height + 2;
                float cpRight = x + COLOR_PICKER_HEIGHT - 2;
                float cpBottom = y + height + COLOR_PICKER_HEIGHT - 2;

                if (mouseX <= cpLeft || mouseY <= cpTop || mouseX >= cpRight || mouseY >= cpBottom)
                    colorSelectorDragging = false;

                float colorSelectorX = saturation * (cpRight - cpLeft);
                float colorSelectorY = (1 - brightness) * (cpBottom - cpTop);

                if (colorSelectorDragging) {
                    float wWidth = cpRight - cpLeft;
                    float xDif = mouseX - cpLeft;
                    this.saturation = xDif / wWidth;
                    colorSelectorX = xDif;

                    float hHeight = cpBottom - cpTop;
                    float yDif = mouseY - cpTop;
                    this.brightness = 1 - (yDif / hHeight);
                    colorSelectorY = yDif;

                    updateColor(Color.HSBtoRGB(hue, saturation, brightness), false);
                }

                Gui.drawRect(cpLeft, cpTop, cpRight, cpBottom, 0xFF000000);
                drawColorPickerRect(cpLeft + 0.5F, cpTop + 0.5F, cpRight - 0.5F, cpBottom - 0.5F);
                // Selector
                float selectorWidth = 2;
                float outlineWidth = 0.5F;
                float half = selectorWidth / 2;

                float csLeft = cpLeft + colorSelectorX - half;
                float csTop = cpTop + colorSelectorY - half;
                float csRight = cpLeft + colorSelectorX + half;
                float csBottom = cpTop + colorSelectorY + half;

                Gui.drawRect(csLeft - outlineWidth,
                        csTop - outlineWidth,
                        csRight + outlineWidth,
                        csBottom + outlineWidth,
                        0xFF000000);
                Gui.drawRect(csLeft,
                        csTop,
                        csRight,
                        csBottom,
                        Color.HSBtoRGB(hue, saturation, brightness));
            }

            // Hue Slider
            {
                float sLeft = x + COLOR_PICKER_HEIGHT - 1;
                float sTop = y + height + 2;
                float sRight = sLeft + 5;
                float sBottom = y + height + COLOR_PICKER_HEIGHT - 2;

                if (mouseX <= sLeft || mouseY <= sTop || mouseX >= sRight || mouseY >= sBottom)
                    hueSelectorDragging = false;

                float hueSelectorY = this.hue * (sBottom - sTop);

                if (hueSelectorDragging) {
                    float hsHeight = sBottom - sTop;
                    float yDif = mouseY - sTop;
                    this.hue = yDif / hsHeight;
                    hueSelectorY = yDif;

                    updateColor(Color.HSBtoRGB(hue, saturation, brightness), false);
                }

                // Outline
                Gui.drawRect(sLeft, sTop, sRight, sBottom, 0xFF000000);
                float inc = 0.2F;
                float times = 1 / inc;
                float sHeight = sBottom - sTop;
                float sY = sTop + 0.5F;
                float size = sHeight / times;
                // Color
                for (int i = 0; i < times; i++) {
                    boolean last = i == times - 1;
                    if (last)
                        size--;
                    Gui.drawGradientRect(sLeft + 0.5F, sY, sRight - 0.5F,
                            sY + size,
                            Color.HSBtoRGB(inc * i, 1.0F, 1.0F),
                            Color.HSBtoRGB(inc * (i + 1), 1.0F, 1.0F));
                    if (!last)
                        sY += size;
                }

                float selectorHeight = 2;
                float outlineWidth = 0.5F;
                float half = selectorHeight / 2;

                float csTop = sTop + hueSelectorY - half;
                float csBottom = sTop + hueSelectorY + half;

                Gui.drawRect(sLeft - outlineWidth,
                        csTop - outlineWidth,
                        sRight + outlineWidth,
                        csBottom + outlineWidth,
                        0xFF000000);
                Gui.drawRect(sLeft,
                        csTop,
                        sRight,
                        csBottom,
                        Color.HSBtoRGB(hue, 1.0F, 1.0F));
            }

            // Alpha Slider
            {
                float sLeft = x + COLOR_PICKER_HEIGHT + 6;
                float sTop = y + height + 2;
                float sRight = sLeft + 5;
                float sBottom = y + height + COLOR_PICKER_HEIGHT - 2;

                if (mouseX <= sLeft || mouseY <= sTop || mouseX >= sRight || mouseY >= sBottom)
                    alphaSelectorDragging = false;

                int color = Color.HSBtoRGB(hue, saturation, brightness);

                int r = color >> 16 & 0xFF;
                int g = color >> 8 & 0xFF;
                int b = color & 0xFF;

                float alphaSelectorY = alpha * (sBottom - sTop);

                if (alphaSelectorDragging) {
                    float hsHeight = sBottom - sTop;
                    float yDif = mouseY - sTop;
                    this.alpha = yDif / hsHeight;
                    alphaSelectorY = yDif;

                    updateColor(new Color(r, g, b, (int) (alpha * 255)).getRGB(), true);
                }

                // Outline
                Gui.drawRect(sLeft, sTop, sRight, sBottom, 0xFF000000);
                // Background
                drawCheckeredBackground(sLeft + 0.5F, sTop + 0.5F, sRight - 0.5F, sBottom - 0.5F);
                // Colored bit
                Gui.drawGradientRect(sLeft + 0.5F, sTop + 0.5F, sRight - 0.5F,
                        sBottom - 0.5F,
                        new Color(r, g, b, 0).getRGB(),
                        new Color(r, g, b, 255).getRGB());

                float selectorHeight = 2;
                float outlineWidth = 0.5F;
                float half = selectorHeight / 2;

                float csTop = sTop + alphaSelectorY - half;
                float csBottom = sTop + alphaSelectorY + half;

                float bx = sRight + outlineWidth;
                float ay = csTop - outlineWidth;
                float by = csBottom + outlineWidth;
                // Selector thingy
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                RenderingUtils.color(0xFF000000);
                GL11.glBegin(GL11.GL_LINE_LOOP);
                GL11.glVertex2f(sLeft, ay);
                GL11.glVertex2f(sLeft, by);
                GL11.glVertex2f(bx, by);
                GL11.glVertex2f(bx, ay);
                GL11.glEnd();
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            // Color Preview Section
            {
                float xOff = COLOR_PICKER_HEIGHT + 13;
                float sLeft = x + xOff;
                float sTop = y + height + 2;
                float sRight = sLeft + width - xOff - 3;
                float sBottom = y + height + (COLOR_PICKER_HEIGHT / 2.0F) + 8;

                Gui.drawRect(sLeft, sTop, sRight, sBottom, 0xFF000000);

                drawCheckeredBackground(sLeft + 0.5F, sTop + 0.5F, sRight - 0.5F, sBottom - 0.5F);

                Gui.drawRect(sLeft + 4, sTop + 4, sRight - 4, sBottom - 4, colorProperty.getValue());
            }
        }
    }

    private void drawCheckeredBackground(float x, float y, float x2, float y2) {
        Gui.drawRect(x, y, x2, y2, -1);

        for (boolean off = false; y < y2; y++)
            for (float x1 = x + ((off = !off) ? 1 : 0); x1 < x2; x1 += 2)
                Gui.drawRect(x1, y, x1 + 1, y + 1, 0xFF808080);
    }

    private void updateColor(int hex, boolean hasAlpha) {
        if (hasAlpha)
            colorProperty.setValue(hex);
        else {
            colorProperty.setValue(new Color(
                    hex >> 16 & 0xFF,
                    hex >> 8 & 0xFF,
                    hex & 0xFF,
                    (int) (alpha * 255)).getRGB());
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);

        if (isExpanded()) {
            if (button == 0) {
                int x = getX();
                int y = getY();
                // Color Picker Dimensions
                float cpLeft = x + 2;
                float cpTop = y + getHeight() + 2;
                float cpRight = x + COLOR_PICKER_HEIGHT - 2;
                float cpBottom = y + getHeight() + COLOR_PICKER_HEIGHT - 2;
                // Hue Slider Dimensions
                float sLeft = x + COLOR_PICKER_HEIGHT - 1;
                float sTop = y + getHeight() + 2;
                float sRight = sLeft + 5;
                float sBottom = y + getHeight() + COLOR_PICKER_HEIGHT - 2;
                // Alpha Slider Dimensions
                float asLeft = x + COLOR_PICKER_HEIGHT + 6;
                float asTop = y + getHeight() + 2;
                float asRight = asLeft + 5;
                float asBottom = y + getHeight() + COLOR_PICKER_HEIGHT - 2;
                // If hovered over color picker
                colorSelectorDragging = !colorSelectorDragging && mouseX > cpLeft && mouseY > cpTop && mouseX < cpRight && mouseY < cpBottom;
                // If hovered over hue slider
                hueSelectorDragging = !hueSelectorDragging && mouseX > sLeft && mouseY > sTop && mouseX < sRight && mouseY < sBottom;
                // If hovered over alpha slider
                alphaSelectorDragging = !alphaSelectorDragging && mouseX > asLeft && mouseY > asTop && mouseX < asRight && mouseY < asBottom;
            }
        }
    }

    @Override
    public void onMouseRelease(int button) {
        if (hueSelectorDragging)
            hueSelectorDragging = false;
        else if (colorSelectorDragging)
            colorSelectorDragging = false;
        else if (alphaSelectorDragging)
            alphaSelectorDragging = false;
    }

    private float[] getHSBFromColor(int hex) {
        int r = hex >> 16 & 0xFF;
        int g = hex >> 8 & 0xFF;
        int b = hex & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }

    public void drawColorPickerRect(float left, float top, float right, float bottom) {
        int hueBasedColor = Color.HSBtoRGB(hue, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OGLUtils.startBlending();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0f)
                .color(hueBasedColor)
                .endVertex();
        worldrenderer.pos(left, top, 0.0f)
                .color(0xFFFFFFFF)
                .endVertex();
        worldrenderer.pos(left, bottom, 0.0f)
                .color(0xFFFFFFFF)
                .endVertex();
        worldrenderer.pos(right, bottom, 0.0f)
                .color(hueBasedColor)
                .endVertex();
        tessellator.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0f)
                .color(0x18000000)
                .endVertex();
        worldrenderer.pos(left, top, 0.0f)
                .color(0x18000000)
                .endVertex();
        worldrenderer.pos(left, bottom, 0.0f)
                .color(0xFF000000)
                .endVertex();
        worldrenderer.pos(right, bottom, 0.0f)
                .color(0xFF000000)
                .endVertex();
        tessellator.draw();
        OGLUtils.endBlending();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public boolean canExpand() {
        return true;
    }

    @Override
    public int getHeightWithExpand() {
        return getHeight() + COLOR_PICKER_HEIGHT;
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {

    }

    @Override
    public Property<Integer> getProperty() {
        return colorProperty;
    }
}
