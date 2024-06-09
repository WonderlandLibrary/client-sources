// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.component.property.impl;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import xyz.niggfaclient.utils.render.RenderUtils;
import org.lwjgl.opengl.GL11;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.gui.click.component.Component;
import xyz.niggfaclient.property.Property;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import xyz.niggfaclient.gui.click.component.impl.component.property.PropertyComponent;
import xyz.niggfaclient.gui.click.component.impl.ExpandableComponent;

public class ColorPropertyComponent extends ExpandableComponent implements PropertyComponent
{
    private static final int COLOR_PICKER_HEIGHT = 80;
    public static Tessellator tessellator;
    public static WorldRenderer worldrenderer;
    private final Property<Integer> colorProperty;
    private float hue;
    private float saturation;
    private float brightness;
    private float alpha;
    private boolean colorSelectorDragging;
    private boolean hueSelectorDragging;
    private boolean alphaSelectorDragging;
    
    public ColorPropertyComponent(final Component parent, final Property<Integer> colorProperty, final int x, final int y, final int width, final int height) {
        super(parent, colorProperty.getName(), x, y, width, height);
        final float[] hsb;
        (this.colorProperty = colorProperty).addValueChangeListener((oldValue, value) -> {
            hsb = this.getHSBFromColor(value);
            this.hue = hsb[0];
            this.saturation = hsb[1];
            this.brightness = hsb[2];
            this.alpha = (value >> 24 & 0xFF) / 255.0f;
        });
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        final int x = (int)(this.getX() - 0.7);
        final int y = this.getY();
        final int width = this.getWidth() + 2;
        final int height = this.getHeight();
        final int textColor = 16777215;
        Gui.drawRect(x, y, x + width, y + height, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        Fonts.sf19.drawStringWithShadow(this.getName(), (float)(x + 2), y + height / 2.0f - 3.0f, textColor);
        final float left = (float)(x + width - 13);
        final float top = y + height / 2.0f - 2.0f;
        final float right = (float)(x + width - 2);
        final float bottom = y + height / 2.0f + 2.0f;
        Gui.drawRect(left - 0.5f, top - 0.5f, right + 0.5f, bottom + 0.5f, -16777216);
        this.drawCheckeredBackground(left, top, right, bottom);
        Gui.drawRect(left, top, right, bottom, this.colorProperty.getValue());
        if (this.isExpanded()) {
            Gui.drawRect(x + 1, y + height, x + width - 1, y + this.getHeightWithExpand(), new Color(45, 45, 45).getRGB());
            final float cpLeft = (float)(x + 2);
            final float cpTop = (float)(y + height + 2);
            final float cpRight = (float)(x + 80 - 2);
            final float cpBottom = (float)(y + height + 80 - 2);
            if (mouseX <= cpLeft || mouseY <= cpTop || mouseX >= cpRight || mouseY >= cpBottom) {
                this.colorSelectorDragging = false;
            }
            float colorSelectorX = this.saturation * (cpRight - cpLeft);
            float colorSelectorY = (1.0f - this.brightness) * (cpBottom - cpTop);
            if (this.colorSelectorDragging) {
                final float wWidth = cpRight - cpLeft;
                final float xDif = mouseX - cpLeft;
                this.saturation = xDif / wWidth;
                colorSelectorX = xDif;
                final float hHeight = cpBottom - cpTop;
                final float yDif = mouseY - cpTop;
                this.brightness = 1.0f - yDif / hHeight;
                colorSelectorY = yDif;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            Gui.drawRect(cpLeft, cpTop, cpRight, cpBottom, -16777216);
            this.drawColorPickerRect(cpLeft + 0.5f, cpTop + 0.5f, cpRight - 0.5f, cpBottom - 0.5f);
            final float selectorWidth = 2.0f;
            final float outlineWidth = 0.5f;
            final float half = selectorWidth / 2.0f;
            final float csLeft = cpLeft + colorSelectorX - half;
            final float csTop = cpTop + colorSelectorY - half;
            final float csRight = cpLeft + colorSelectorX + half;
            final float csBottom = cpTop + colorSelectorY + half;
            Gui.drawRect(csLeft - outlineWidth, csTop - outlineWidth, csRight + outlineWidth, csBottom + outlineWidth, -16777216);
            Gui.drawRect(csLeft, csTop, csRight, csBottom, Color.HSBtoRGB(this.hue, this.saturation, this.brightness));
            float sLeft = (float)(x + 80 - 1);
            float sTop = (float)(y + height + 2);
            float sRight = sLeft + 5.0f;
            float sBottom = (float)(y + height + 80 - 2);
            if (mouseX <= sLeft || mouseY <= sTop || mouseX >= sRight || mouseY >= sBottom) {
                this.hueSelectorDragging = false;
            }
            float hueSelectorY = this.hue * (sBottom - sTop);
            if (this.hueSelectorDragging) {
                final float hsHeight = sBottom - sTop;
                final float yDif2 = mouseY - sTop;
                this.hue = yDif2 / hsHeight;
                hueSelectorY = yDif2;
                this.updateColor(Color.HSBtoRGB(this.hue, this.saturation, this.brightness), false);
            }
            Gui.drawRect(sLeft, sTop, sRight, sBottom, -16777216);
            final float inc = 0.2f;
            final float times = 1.0f / inc;
            final float sHeight = sBottom - sTop;
            float sY = sTop + 0.5f;
            float size = sHeight / times;
            for (int i = 0; i < times; ++i) {
                final boolean last = i == times - 1.0f;
                if (last) {
                    --size;
                }
                Gui.drawGradientRect(sLeft + 0.5f, sY, sRight - 0.5f, sY + size, Color.HSBtoRGB(inc * i, 1.0f, 1.0f), Color.HSBtoRGB(inc * (i + 1), 1.0f, 1.0f));
                if (!last) {
                    sY += size;
                }
            }
            final float selectorHeight = 2.0f;
            final float outlineWidth2 = 0.5f;
            final float half2 = selectorHeight / 2.0f;
            final float csTop2 = sTop + hueSelectorY - half2;
            final float csBottom2 = sTop + hueSelectorY + half2;
            Gui.drawRect(sLeft - outlineWidth2, csTop2 - outlineWidth2, sRight + outlineWidth2, csBottom2 + outlineWidth2, -16777216);
            Gui.drawRect(sLeft, csTop2, sRight, csBottom2, Color.HSBtoRGB(this.hue, 1.0f, 1.0f));
            sLeft = (float)(x + 80 + 6);
            sTop = (float)(y + height + 2);
            sRight = sLeft + 5.0f;
            sBottom = (float)(y + height + 80 - 2);
            if (mouseX <= sLeft || mouseY <= sTop || mouseX >= sRight || mouseY >= sBottom) {
                this.alphaSelectorDragging = false;
            }
            final int color = Color.HSBtoRGB(this.hue, this.saturation, this.brightness);
            final int r = color >> 16 & 0xFF;
            final int g = color >> 8 & 0xFF;
            final int b = color & 0xFF;
            float alphaSelectorY = this.alpha * (sBottom - sTop);
            if (this.alphaSelectorDragging) {
                final float hsHeight2 = sBottom - sTop;
                final float yDif3 = mouseY - sTop;
                this.alpha = yDif3 / hsHeight2;
                alphaSelectorY = yDif3;
                this.updateColor(new Color(r, g, b, (int)(this.alpha * 255.0f)).getRGB(), true);
            }
            Gui.drawRect(sLeft, sTop, sRight, sBottom, -16777216);
            this.drawCheckeredBackground(sLeft + 0.5f, sTop + 0.5f, sRight - 0.5f, sBottom - 0.5f);
            Gui.drawGradientRect(sLeft + 0.5f, sTop + 0.5f, sRight - 0.5f, sBottom - 0.5f, new Color(r, g, b, 0).getRGB(), new Color(r, g, b, 255).getRGB());
            final float selectorHeight2 = 2.0f;
            final float outlineWidth3 = 0.5f;
            final float half3 = selectorHeight2 / 2.0f;
            final float csTop3 = sTop + alphaSelectorY - half3;
            final float csBottom3 = sTop + alphaSelectorY + half3;
            final float bx = sRight + outlineWidth3;
            final float ay = csTop3 - outlineWidth3;
            final float by = csBottom3 + outlineWidth3;
            GL11.glDisable(3553);
            RenderUtils.color(-16777216);
            GL11.glBegin(2);
            GL11.glVertex2f(sLeft, ay);
            GL11.glVertex2f(sLeft, by);
            GL11.glVertex2f(bx, by);
            GL11.glVertex2f(bx, ay);
            GL11.glEnd();
            GL11.glEnable(3553);
        }
    }
    
    private void drawCheckeredBackground(final float x, float y, final float x2, final float y2) {
        Gui.drawRect(x, y, x2, y2, -1);
        boolean off = false;
        while (y < y2) {
            for (float x3 = x + ((off = !off) ? 1 : 0); x3 < x2; x3 += 2.0f) {
                Gui.drawRect(x3, y, x3 + 1.0f, y + 1.0f, -8355712);
            }
            ++y;
        }
    }
    
    private void updateColor(final int hex, final boolean hasAlpha) {
        if (hasAlpha) {
            this.colorProperty.setValue(hex);
        }
        else {
            this.colorProperty.setValue(new Color(hex >> 16 & 0xFF, hex >> 8 & 0xFF, hex & 0xFF, (int)(this.alpha * 255.0f)).getRGB());
        }
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (this.isExpanded() && button == 0) {
            final int x = this.getX();
            final int y = this.getY();
            final float cpLeft = (float)(x + 2);
            final float cpTop = (float)(y + this.getHeight() + 2);
            final float cpRight = (float)(x + 80 - 2);
            final float cpBottom = (float)(y + this.getHeight() + 80 - 2);
            final float sLeft = (float)(x + 80 - 1);
            final float sTop = (float)(y + this.getHeight() + 2);
            final float sRight = sLeft + 5.0f;
            final float sBottom = (float)(y + this.getHeight() + 80 - 2);
            final float asLeft = (float)(x + 80 + 6);
            final float asTop = (float)(y + this.getHeight() + 2);
            final float asRight = asLeft + 5.0f;
            final float asBottom = (float)(y + this.getHeight() + 80 - 2);
            this.colorSelectorDragging = (!this.colorSelectorDragging && mouseX > cpLeft && mouseY > cpTop && mouseX < cpRight && mouseY < cpBottom);
            this.hueSelectorDragging = (!this.hueSelectorDragging && mouseX > sLeft && mouseY > sTop && mouseX < sRight && mouseY < sBottom);
            this.alphaSelectorDragging = (!this.alphaSelectorDragging && mouseX > asLeft && mouseY > asTop && mouseX < asRight && mouseY < asBottom);
        }
    }
    
    @Override
    public void onMouseRelease(final int button) {
        if (this.hueSelectorDragging) {
            this.hueSelectorDragging = false;
        }
        else if (this.colorSelectorDragging) {
            this.colorSelectorDragging = false;
        }
        else if (this.alphaSelectorDragging) {
            this.alphaSelectorDragging = false;
        }
    }
    
    private float[] getHSBFromColor(final int hex) {
        final int r = hex >> 16 & 0xFF;
        final int g = hex >> 8 & 0xFF;
        final int b = hex & 0xFF;
        return Color.RGBtoHSB(r, g, b, null);
    }
    
    public void drawColorPickerRect(final float left, final float top, final float right, final float bottom) {
        final int hueBasedColor = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        GL11.glDisable(3553);
        RenderUtils.startBlending();
        GL11.glShadeModel(7425);
        ColorPropertyComponent.worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        ColorPropertyComponent.worldrenderer.pos(right, top, 0.0).color(hueBasedColor).endVertex();
        ColorPropertyComponent.worldrenderer.pos(left, top, 0.0).color(-1).endVertex();
        ColorPropertyComponent.worldrenderer.pos(left, bottom, 0.0).color(-1).endVertex();
        ColorPropertyComponent.worldrenderer.pos(right, bottom, 0.0).color(hueBasedColor).endVertex();
        ColorPropertyComponent.tessellator.draw();
        ColorPropertyComponent.worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        ColorPropertyComponent.worldrenderer.pos(right, top, 0.0).color(402653184).endVertex();
        ColorPropertyComponent.worldrenderer.pos(left, top, 0.0).color(402653184).endVertex();
        ColorPropertyComponent.worldrenderer.pos(left, bottom, 0.0).color(-16777216).endVertex();
        ColorPropertyComponent.worldrenderer.pos(right, bottom, 0.0).color(-16777216).endVertex();
        ColorPropertyComponent.tessellator.draw();
        RenderUtils.endBlending();
        GL11.glShadeModel(7424);
        GL11.glEnable(3553);
    }
    
    @Override
    public boolean canExpand() {
        return true;
    }
    
    @Override
    public int getHeightWithExpand() {
        return this.getHeight() + 80;
    }
    
    @Override
    public void onPress(final int mouseX, final int mouseY, final int button) {
    }
    
    @Override
    public Property<Integer> getProperty() {
        return this.colorProperty;
    }
    
    static {
        ColorPropertyComponent.tessellator = Tessellator.getInstance();
        ColorPropertyComponent.worldrenderer = ColorPropertyComponent.tessellator.getWorldRenderer();
    }
}
