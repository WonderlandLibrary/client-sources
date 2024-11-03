package net.silentclient.client.gui.elements;

import net.minecraft.client.gui.Gui;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class HSBPicker extends Gui {
    public float[] color;
    private boolean pickingColor;
    private boolean pickingHue;
    private boolean pickingAlpha;
    private int pickerX, pickerY, pickerWidth, pickerHeight;
    private int hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight;
    private int alphaSliderX, alphaSliderY, alphaSliderWidth, alphaSliderHeight;
    private boolean rainbowState = false;
    private final int x, y, width, height;
    private int selectedColorFinal = -1;
    private Color selectedColorFinalAsColor;
    public int cursorX;
    public int cursorY;
    public boolean alphaSlider = true;

    public SimpleAnimation sx = new SimpleAnimation(0f);
    public SimpleAnimation sy = new SimpleAnimation(0f);

    public HSBPicker(int x, int y, int width, int height, boolean alphaSlider) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = new float[]{0.4f, 1.0f, 1.0f, 1.0f};
        this.pickingColor = false;
        this.alphaSlider = alphaSlider;
    }

    public void init() {
        this.pickerWidth = width;
        this.pickerHeight = height;
        this.pickerX = x;
        this.pickerY = y;
        this.hueSliderX = pickerX;
        this.hueSliderY = pickerY + pickerHeight + 6;
        this.hueSliderWidth = pickerWidth;
        this.hueSliderHeight = 6;
        this.alphaSliderX = pickerX + pickerWidth + 6;
        this.alphaSliderY = pickerY;
        this.alphaSliderWidth = 6;
        this.alphaSliderHeight = pickerHeight;
    }

    public void setPickerX(int pickerX) {
        this.pickerX = pickerX;
        this.hueSliderX = pickerX;
    }

    public void setPickerY(int pickerY) {
        this.pickerY = pickerY;
        this.hueSliderY = pickerY + pickerHeight + 6;
    }

    public void render(int mouseX, int mouseY) {
        if (this.rainbowState) {
            double rainbowState = Math.ceil((System.currentTimeMillis() + 200) / 20.0);
            rainbowState %= 360.0;
            this.color[0] = (float) (rainbowState / 360.0);
        }
        if (this.pickingHue) {
            if (this.hueSliderWidth > this.hueSliderHeight) {
                float restrictedX = (float) Math.min(Math.max(hueSliderX, mouseX), hueSliderX + hueSliderWidth);
                this.color[0] = (restrictedX - (float) hueSliderX) / hueSliderWidth;
            } else {
                float restrictedY = (float) Math.min(Math.max(hueSliderY, mouseY), hueSliderY + hueSliderHeight);
                this.color[0] = (restrictedY - (float) hueSliderY) / hueSliderHeight;
            }
        }
        if (this.pickingAlpha) {
            if (this.alphaSliderWidth > this.alphaSliderHeight) {
                float restrictedX = (float) Math.min(Math.max(alphaSliderX, mouseX), alphaSliderX + alphaSliderWidth);
                this.color[3] = 1 - (restrictedX - (float) alphaSliderX) / alphaSliderWidth;
            } else {
                float restrictedY = (float) Math.min(Math.max(alphaSliderY, mouseY), alphaSliderY + alphaSliderHeight);
                this.color[3] = 1 - (restrictedY - (float) alphaSliderY) / alphaSliderHeight;
            }
        }
        if (this.pickingColor) {
            float restrictedX = (float) Math.min(Math.max(pickerX, mouseX), pickerX + pickerWidth);
            float restrictedY = (float) Math.min(Math.max(pickerY, mouseY), pickerY + pickerHeight);
            this.color[1] = (restrictedX - (float) pickerX) / pickerWidth;
            this.color[2] = 1 - (restrictedY - (float) pickerY) / pickerHeight;
        }
        int selectedX = pickerX + pickerWidth + 6;
        int selectedY = pickerY + pickerHeight + 6;
        int selectedWidth = 6;
        int selectedHeight = 6;
        Gui.drawRect(pickerX - 1, pickerY - 1, pickerX + pickerWidth + 1, pickerY + pickerHeight + 1, new Color(0, 0, 0, 0).getRGB());
        Gui.drawRect(hueSliderX - 1, hueSliderY - 1, hueSliderX + hueSliderWidth + 1, hueSliderY + hueSliderHeight + 1, new Color(0, 0, 0, 0).getRGB());
        Gui.drawRect(alphaSliderX - 1, alphaSliderY - 1, alphaSliderX + alphaSliderWidth + 1, alphaSliderY + alphaSliderHeight + 1, new Color(0, 0, 0, 0).getRGB());
        int selectedColor = Color.HSBtoRGB(this.color[0], 1.0f, 1.0f);
        float selectedRed = (selectedColor >> 16 & 0xFF) / 255.0f;
        float selectedGreen = (selectedColor >> 8 & 0xFF) / 255.0f;
        float selectedBlue = (selectedColor & 0xFF) / 255.0f;

        this.drawPickerBase(pickerX, pickerY, pickerWidth, pickerHeight, selectedRed, selectedGreen, selectedBlue, 255);
        this.drawHueSlider(hueSliderX, hueSliderY, hueSliderWidth, hueSliderHeight, this.color[0]);
        if (alphaSlider) {
            this.drawAlphaSlider(alphaSliderX, alphaSliderY, alphaSliderWidth, alphaSliderHeight, selectedRed, selectedGreen, selectedBlue, this.color[3]);
        }

        selectedColorFinal = alpha(new Color(Color.HSBtoRGB(this.color[0], this.color[1], this.color[2])), this.color[3]);
        selectedColorFinalAsColor = color(new Color(Color.HSBtoRGB(this.color[0], this.color[1], this.color[2])), this.color[3]);

        Gui.drawRect(selectedX - 1, selectedY - 1, selectedX + selectedWidth + 1, selectedY + selectedHeight + 1, new Color(0, 0, 0, 0).getRGB());
        Gui.drawRect(selectedX, selectedY, selectedX + selectedWidth, selectedY + selectedHeight, selectedColorFinal);

        cursorX = (int) (pickerX + color[1] * pickerWidth);
        cursorY = (int) ((pickerY + pickerHeight) - color[2] * pickerHeight);

        sx.setAnimation(cursorX, 25f);
        sy.setAnimation(cursorY, 25f);

        RenderUtils.drawRect(sx.getValue() - 1, sy.getValue() - 1, 2, 2, Color.WHITE.getRGB());
    }

    final int alpha(Color color, float alpha) {
        final float red = (float) color.getRed() / 255;
        final float green = (float) color.getGreen() / 255;
        final float blue = (float) color.getBlue() / 255;
        return new Color(red, green, blue, alpha).getRGB();
    }

    final Color color(Color color, float alpha) {
        final float red = (float) color.getRed() / 255;
        final float green = (float) color.getGreen() / 255;
        final float blue = (float) color.getBlue() / 255;
        return new Color(red, green, blue, alpha);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.pickingColor = check(pickerX, pickerY, pickerX + pickerWidth, pickerY + pickerHeight, mouseX, mouseY);
        this.pickingHue = check(hueSliderX, hueSliderY, hueSliderX + hueSliderWidth, hueSliderY + hueSliderHeight, mouseX, mouseY);
        if (alphaSlider) {
            this.pickingAlpha = check(alphaSliderX, alphaSliderY, alphaSliderX + alphaSliderWidth, alphaSliderY + alphaSliderHeight, mouseX, mouseY);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        this.pickingColor = this.pickingHue = this.pickingAlpha = false;
    }

    private void drawHueSlider(int x, int y, int width, int height, float hue) {
        int step = 0;
        if (height > width) {
            Gui.drawRect(x, y, x + width, y + 4, 0xFFFF0000);
            y += 4;
            for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
                int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
                int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);
                this.drawGradientRect(x, y + step * (height / 6), x + width, y + (step + 1) * (height / 6), previousStep, nextStep);
                step++;
            }
            final int sliderMinY = (int) (y + (height * hue)) - 4;
            Gui.drawRect(x, sliderMinY - 1, x + width, sliderMinY + 1, -1);
        } else {
            for (int colorIndex = 0; colorIndex < 6; colorIndex++) {
                int previousStep = Color.HSBtoRGB((float) step / 6, 1.0f, 1.0f);
                int nextStep = Color.HSBtoRGB((float) (step + 1) / 6, 1.0f, 1.0f);
                this.gradient(x + step * (width / 6), y, x + (step + 1) * (width / 6), y + height, previousStep, nextStep, true);
                step++;
            }
            final int sliderMinX = (int) (x + (width * hue));
            Gui.drawRect(sliderMinX - 1, y, sliderMinX, y + height, -1);
        }
    }

    private void drawAlphaSlider(int x, int y, int width, int height, float red, float green, float blue, float alpha) {
        boolean left = true;
        int checkerBoardSquareSize = width / 2;
        for (int squareIndex = -checkerBoardSquareSize; squareIndex < height; squareIndex += checkerBoardSquareSize) {
            if (!left) {
                Gui.drawRect(x, y + squareIndex, x + width, y + squareIndex + checkerBoardSquareSize, 0xFFFFFFFF);
                Gui.drawRect(x + checkerBoardSquareSize, y + squareIndex, x + width, y + squareIndex + checkerBoardSquareSize, 0xFF909090);
                if (squareIndex < height - checkerBoardSquareSize) {
                    int minY = y + squareIndex + checkerBoardSquareSize;
                    int maxY = Math.min(y + height, y + squareIndex + checkerBoardSquareSize * 2);
                    Gui.drawRect(x, minY, x + width, maxY, 0xFF909090);
                    Gui.drawRect(x + checkerBoardSquareSize, minY, x + width, maxY, 0xFFFFFFFF);
                }
            }
            left = !left;
        }
        this.gradient(x, y, x + width, y + height, new Color((int)red, (int)green, (int)blue, 255).getRGB(), 0, false);
        final int sliderMinY = (int) (y + height - (height * alpha));
        Gui.drawRect(x, sliderMinY - 1, x + width, sliderMinY, -1);
    }

    private void drawPickerBase(int pickerX, int pickerY, int pickerWidth, int pickerHeight, float red, float green, float blue, float alpha) {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glVertex2f(pickerX, pickerY);
            GL11.glVertex2f(pickerX, pickerY + pickerHeight);
            GL11.glColor4f(red, green, blue, alpha);
            GL11.glVertex2f(pickerX + pickerWidth, pickerY + pickerHeight);
            GL11.glVertex2f(pickerX + pickerWidth, pickerY);
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBegin(GL11.GL_POLYGON);
        {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glVertex2f(pickerX, pickerY);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            GL11.glVertex2f(pickerX, pickerY + pickerHeight);
            GL11.glVertex2f(pickerX + pickerWidth, pickerY + pickerHeight);
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glVertex2f(pickerX + pickerWidth, pickerY);
        }
        GL11.glEnd();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    protected void gradient(int minX, int minY, int maxX, int maxY, int startColor, int endColor, boolean left) {
        if (left) {

            final float startA = (startColor >> 24 & 0xFF) / 255.0f;
            final float startR = (startColor >> 16 & 0xFF) / 255.0f;
            final float startG = (startColor >> 8 & 0xFF) / 255.0f;
            final float startB = (startColor & 0xFF) / 255.0f;

            final float endA = (endColor >> 24 & 0xFF) / 255.0f;
            final float endR = (endColor >> 16 & 0xFF) / 255.0f;
            final float endG = (endColor >> 8 & 0xFF) / 255.0f;
            final float endB = (endColor & 0xFF) / 255.0f;

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glBegin(GL11.GL_POLYGON);
            {
                GL11.glColor4f(startR, startG, startB, startA);
                GL11.glVertex2f(minX, minY);
                GL11.glVertex2f(minX, maxY);
                GL11.glColor4f(endR, endG, endB, endA);
                GL11.glVertex2f(maxX, maxY);
                GL11.glVertex2f(maxX, minY);
            }
            GL11.glEnd();
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        } else drawGradientRect(minX, minY, maxX, maxY, startColor, endColor);
    }

    protected boolean check(int minX, int minY, int maxX, int maxY, int curX, int curY) {
        return curX >= minX && curY >= minY && curX < maxX && curY < maxY;
    }

    public Color getSelectedColorFinal() {
        return new Color(selectedColorFinal);
    }

    public void setFinalColor(Color color) {
        float r = color.getRed() / 255.0F;
        float g = color.getGreen() / 255.0F;
        float b = color.getBlue() / 255.0F;
        float a = color.getAlpha() / 255.0F;
        this.color[0] = r;
        this.color[1] = g;
        this.color[2] = b;
        this.color[3] = a;
    }
}