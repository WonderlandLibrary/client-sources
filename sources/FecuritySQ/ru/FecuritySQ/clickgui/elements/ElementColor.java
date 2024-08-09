package ru.FecuritySQ.clickgui.elements;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.MathHelper;
import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.option.imp.OptionColor;
import ru.FecuritySQ.shader.StencilUtil;
import ru.FecuritySQ.utils.RenderUtil;

import java.awt.*;

public class ElementColor extends Element {

    private static final int COLOR_PICKER_HEIGHT = 80;
    public static Tessellator tessellator = Tessellator.getInstance();
    public static BufferBuilder buffer = tessellator.getBuffer();
    private ElementModule module;

    private OptionColor optionColor;

    public float hue;
    public float saturation;
    public float brightness;
    private float alpha;

    private boolean colorSelectorDragging;

    private boolean hueSelectorDragging;


    public boolean open;

    public ElementColor(ElementModule elementModule, OptionColor optionColor){
        this.module = elementModule;
        this.optionColor = optionColor;

        Color value = optionColor.get();
        float[] hsb = getHSBFromColor(value);
        this.hue = hsb[0];
        this.saturation = hsb[1];
        this.brightness = hsb[2];

        this.alpha = value.getAlpha() / 255.0F;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        int x = (int) getX() - 3;
        int y = (int) getY();
        int width = (int) getWidth();
        int height = (int) getHeight();

        Fonts.GREYCLIFF.drawString(stack, optionColor.getName(), x + 5, y + height / 2F - 7, Color.GRAY.getRGB());
        float left = x + width - 13;
        float top = y + height / 2.0F - 2;
        float right = x + width - 2;
        float bottom = y + height / 2.0F + 2;

        RenderUtil.drawRound((float) (x + this.width) - 10, y + 6, 8, 3, 1f, this.optionColor.get().getRGB());

        if (open) {
            this.height = COLOR_PICKER_HEIGHT;
            {
                // Box with gradient
                float cpLeft = x + 2 + 5 + 0;
                float cpTop = y + height + 2;
                float cpRight = x + COLOR_PICKER_HEIGHT - 2 + 15;
                float cpBottom = y + height + COLOR_PICKER_HEIGHT - 2 - 20;


                float colorSelectorX = saturation * (cpRight - cpLeft);
                float colorSelectorY = (1 - brightness) * (cpBottom - cpTop);


                if (colorSelectorDragging) {
                    float wWidth = cpRight - cpLeft;
                    float xDif = MathHelper.clamp(mouseX, cpLeft, cpRight) - cpLeft;
                    this.saturation = xDif / wWidth;
                    colorSelectorX = xDif;

                    float hHeight = cpBottom - cpTop;
                    float yDif = MathHelper.clamp(mouseY, cpTop, cpBottom) - cpTop;
                    this.brightness = 1 - (yDif / hHeight);
                    colorSelectorY = yDif;

                }

                drawColorPickerRect(cpLeft + 0.5F, cpTop + 0.5F, cpRight - 0.5F, cpBottom - 0.5F);
                // Selector
                float selectorWidth = 2;
                float outlineWidth = 0.5F;
                float half = selectorWidth / 2;

                float csLeft = cpLeft + colorSelectorX - half;
                float csTop = cpTop + colorSelectorY - half;
                float csRight = cpLeft + colorSelectorX + half;
                float csBottom = cpTop + colorSelectorY + half;

                StencilUtil.initStencilToWrite();
                drawColorPickerRect(cpLeft + 0.5F, cpTop + 0.5F, cpRight - 0.5F, cpBottom - 0.5F);
                StencilUtil.readStencilBuffer(1);
                RenderUtil.drawRoundCircle((csLeft + csRight) / 2, (csTop + csBottom) / 2, 2.5F, new Color(0xFF000000).getRGB());

                RenderUtil.drawRoundCircle((csLeft + csRight) / 2, (csTop + csBottom) / 2, 2F, new Color(Color.HSBtoRGB(hue, saturation, brightness)).getRGB());
                StencilUtil.uninitStencilBuffer();


            }

            // Hue Slider
            {
                float sLeft = x + COLOR_PICKER_HEIGHT - 1 + 15;
                float sTop = y + height + 2;
                float sRight = sLeft + 7;
                float sBottom = y + height + COLOR_PICKER_HEIGHT - 2 - 20;


                float hueSelectorY = this.hue * (sBottom - sTop);

                if (hueSelectorDragging) {
                    float hsHeight = sBottom - sTop;
                    float yDif = MathHelper.clamp(mouseY, sTop, sBottom) - sTop;
                    this.hue = yDif / hsHeight;
                    hueSelectorY = yDif;

                }

                // Outline
            //    RenderUtil.drawRect(sLeft, sTop, sRight, sBottom, 0xFF000000);
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
                    RenderUtil.drawGradientRect(sLeft + 0.5F, sY, sRight - 0.5F, sY + size, Color.HSBtoRGB(inc * i, 1.0F, 1.0F), Color.HSBtoRGB(inc * (i + 1), 1.0F, 1.0F));
                    if (!last)
                        sY += size;
                }

                float selectorHeight = 2;
                float outlineWidth = 0.5F;
                float half = selectorHeight / 2;

                float csTop = sTop + hueSelectorY - half;
                float csBottom = sTop + hueSelectorY + half;
            }


            if (!new Color(Color.getHSBColor(hue, saturation, brightness).getRed(), Color.getHSBColor(hue, saturation, brightness).getGreen(), Color.getHSBColor(hue, saturation, brightness).getBlue(), 255).equals(optionColor.get()) || colorSelectorDragging || hueSelectorDragging)
                updateColor(Color.getHSBColor(hue, saturation, brightness));

        }
    }

    private void updateColor(Color hex) {
        optionColor.set(new Color(hex.getRed(), hex.getGreen(), hex.getBlue(), (int) (this.alpha * 255)));
    }

    private void drawCheckeredBackground(float x, float y, float right, float bottom) {
        RenderUtil.drawRect(x, y, right, bottom, -1);

        for (boolean off = false; y < bottom; y++)
            for (float x1 = x + ((off = !off) ? 1 : 0); x1 < right; x1 += 2)
                RenderUtil.drawRect(x1, y, x1 + 1, y + 1, 0xFF808080);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

        if(collided(mouseX, mouseY) && button == 1){
            this.open = !open;
        }

        if (button == 0 && open) {
            if(collided(mouseX, mouseY, (double) getX(), getY() + 18, (float)this.width - 10, COLOR_PICKER_HEIGHT - 25)){
                colorSelectorDragging = true;
            }


            if(collided(mouseX, mouseY, (double) getX() + this.width - 10, getY() + 18, 10, COLOR_PICKER_HEIGHT - 25)){
                hueSelectorDragging = true;
            }

        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        hueSelectorDragging = false;
        colorSelectorDragging = false;
        super.mouseReleased(x, y, button);
    }

    public float[] getHSBFromColor(Color hex) {

        return Color.RGBtoHSB(hex.getRed(), hex.getGreen(), hex.getBlue(), null);
    }

    public void drawColorPickerRect(float left, float top, float right, float bottom) {
        int hueBasedColor = Color.HSBtoRGB(hue, 1, 1);
        RenderUtil.drawGradientRound(left + 0.5F, top + 0.5F, right - left - 1F, bottom - top - 1F, 2F, Color.white.getRGB(), Color.BLACK.getRGB(), new Color(hueBasedColor).getRGB(), Color.BLACK.getRGB());
    }

}
