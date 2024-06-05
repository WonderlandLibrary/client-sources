package me.vaziak.sensation.client.api.gui.ingame.HUD.components;

import java.awt.*;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.property.impl.ColorProperty;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.MathUtils;

/**
 * @author antja03
 */
public class ColorPicker extends ValueComponent {

    private ColorProperty colorValue;
    private boolean extended;
    private boolean selectingHue;
    private boolean selectingSB;
    private boolean selectingAlpha;

    private boolean extraOptions;


    public ColorPicker(Value colorValue, int x, int y) {
        super(colorValue, x, y);
        if (colorValue instanceof ColorProperty) {
            this.colorValue = (ColorProperty) colorValue;
        }
        selectingHue = false;
        selectingSB = false;
        selectingAlpha = false;
    }

    @SuppressWarnings("unused")
    @Override
    public void drawComponent(int mouseX, int mouseY) {
        double alphaModifier = Sensation.instance.hudEditor.getAlphaModifer();
        Fonts.f14.drawCenteredString(colorValue.getId(), posX, posY + 1, ColorCreator.create(220, 220, 220, (int) (220 * alphaModifier)));
        if (!extended || Sensation.instance.hudEditor.isClosing() || extraOptions) {
            Draw.drawRectangle(posX - 6.5, posY + 8, posX + 6.5, posY + 20,
                    ColorCreator.create(colorValue.getValue().getRed(), colorValue.getValue().getGreen(), colorValue.getValue().getBlue(), (int) (colorValue.getValue().getAlpha() * alphaModifier)));

            selectingHue = false;
            selectingSB = false;
            selectingAlpha = false;

            if (extraOptions) {
                Draw.drawRectangle(posX + 6.5, posY + 20, posX + 46.5, posY + 36,
                        ColorCreator.create(40, 40, 40, (int) (255 * alphaModifier)));

                if (mouseX >= posX + 6.5 && mouseY >= posY + 20 && mouseX <= posX + 46.5 && mouseY <= posY + 28) {
                    Draw.drawRectangle(posX + 6.5, posY + 20, posX + 46.5, posY + 28,
                            ColorCreator.create(30, 30, 30, (int) (255 * alphaModifier)));
                }
                Fonts.f12.drawString("Copy", posX + 11, posY + 23, ColorCreator.create(220, 220, 220, (int) (255 * alphaModifier)));

                if (mouseX >= posX + 6.5 && mouseY >= posY + 28 && mouseX <= posX + 46.5 && mouseY <= posY + 36) {
                    Draw.drawRectangle(posX + 6.5, posY + 28, posX + 46.5, posY + 36,
                            ColorCreator.create(30, 30, 30, (int) (255 * alphaModifier)));
                }
                Fonts.f12.drawString("Paste", posX + 11, posY + 31, ColorCreator.create(220, 220, 220, (int) (255 * alphaModifier)));
            }
        } else {
            double boxPosX = posX - 57.5;
            double boxWidth = 115;
            double boxPosY = posY + 10;
            double boxHeight = extended ? 115 : 10;

            Draw.drawRectangle(boxPosX + 1, boxPosY + 1, boxPosX + boxWidth - 1, boxPosY + boxHeight - 1, new Color(24, 24, 24).getRGB());

            float inc = 1.0f / 100;

            SB:
            {
                float currentSaturation;
                float currentBrightness;

                for (int i = 0; i < 100; i++) {
                    currentSaturation = i * inc;
                    Draw.drawGradientRect(boxPosX + 2 + i, boxPosY + 2, boxPosX + 3 + i, boxPosY + boxHeight - 14, Color.getHSBColor(colorValue.getHue(), currentSaturation, 1.0f).getRGB(), ColorCreator.create(0, 0, 0));
                    for (int i2 = 0; i2 < 100; i2++) {
                        if (selectingSB) {
                            if (mouseX >= boxPosX + 2 + i && mouseY >= boxPosY + boxHeight - 15 - i2
                                    && mouseX <= boxPosX + 4 + i && mouseY <= boxPosY + boxHeight - 13 - i2) {
                                colorValue.setSaturation(i * inc);
                                colorValue.setBrightness(i2 * inc);
                            }
                        }
                    }
                }

                for (int i = 0; i < 100; i++) {
                    for (int i2 = 0; i2 < 100; i2++) {
                        currentSaturation = i * inc;
                        currentBrightness = i2 * inc;
                        if (Math.abs(currentSaturation - colorValue.getSaturation()) < 0.025 && Math.abs(currentBrightness - colorValue.getBrightness()) < 0.025) {
                            Draw.drawRectangle(boxPosX + 2 + i, boxPosY + boxHeight - 14 - i2, boxPosX + 3 + i, boxPosY + boxHeight - 15 - i2, ColorCreator.create(0, 0, 0, 200));
                        }
                    }
                }
            }


            Hue:
            {
                if (selectingHue) {
                    double barHeight = boxHeight - 17;
                    double mousePosOnBar = mouseY - boxPosY + 2;
                    if (mousePosOnBar < 0)
                        mousePosOnBar = 0;
                    else if (mousePosOnBar > barHeight) {
                        mousePosOnBar = barHeight;
                    }
                    colorValue.setHue((float) ((1.0f / barHeight) * mousePosOnBar));
                }

                for (int i = 0; i < 100; i++) {
                    Draw.drawRectangle(boxPosX + boxWidth - 11, boxPosY + 2 + i, boxPosX + boxWidth - 2, boxPosY + 3 + i, Color.getHSBColor(i * inc, 1.0f, 1.0f).getRGB());

                    if (Math.abs(i * inc - colorValue.getHue()) <= 0.025) {
                        Draw.drawRectangle(boxPosX + boxWidth - 11, boxPosY + 2 + i, boxPosX + boxWidth - 2, boxPosY + 3 + i, ColorCreator.create(0, 0, 0, 200));
                    }
                }
            }

            Alpha:
            {
                Draw.drawRectangle(boxPosX + 2, boxPosY + boxHeight - 12, boxPosX + boxWidth - 2, boxPosY + boxHeight - 2, ColorCreator.create(40, 40, 40, 255));
                Draw.drawRectangle(boxPosX + 2.5, boxPosY + boxHeight - 11.5, boxPosX + boxWidth - 2.5, boxPosY + boxHeight - 2.5, colorValue.value.getRGB());
                double barWidth = boxWidth - 4;
                if (selectingAlpha) {
                    double mousePosOnBar = mouseX - boxPosX + 2;
                    if (mousePosOnBar < 0)
                        mousePosOnBar = 0;
                    else if (mousePosOnBar > barWidth) {
                        mousePosOnBar = barWidth;
                    }
                    colorValue.setAlpha(MathUtils.clamp((255 / barWidth) * mousePosOnBar, 0, 255).intValue());
                }

                double pos = (double) colorValue.getAlpha() / 255 * barWidth;
                Draw.drawRectangle(boxPosX + pos + 1, boxPosY + boxHeight - 11.5, boxPosX + pos + 2, boxPosY + boxHeight - 2.5, ColorCreator.create(0, 0, 0, 200));
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        double boxPosX = posX - 57.5;
        double boxWidth = 115;
        double boxPosY = posY + 10;
        double boxHeight = extended ? 115 : 10;

        if (mouseButton == 0) {
            if (!extended) {
                if (mouseX >= posX - 6.5 && mouseY >= posY + 10 && mouseX <= posX + 6.5 && mouseY <= posY + 20) {
                    extended = true;
                    extraOptions = false;
                    return true;
                }

                if (extraOptions) {
                    if (mouseX >= posX + 6.5 && mouseY >= posY + 20 && mouseX <= posX + 46.5 && mouseY <= posY + 28) {
                        ColorProperty.copiedValue = colorValue.getValueAsString();
                        extraOptions = false;
                        return true;
                    }

                    if (mouseX >= posX + 6.5 && mouseY >= posY + 28 && mouseX <= posX + 46.5 && mouseY <= posY + 36) {
                        if (!ColorProperty.copiedValue.equals("")) {
                            colorValue.setValue(ColorProperty.copiedValue);
                        }
                        extraOptions = false;
                        return true;
                    }
                }
            } else {
                if (mouseX >= boxPosX + 2 && mouseY >= boxPosY + 2 && mouseX <= boxPosX + 102 && mouseY <= boxPosY + boxHeight - 15) {
                    selectingSB = true;
                    return true;
                } else if (mouseX >= boxPosX + boxWidth - 11 && mouseY >= boxPosY + 2 && mouseX <= boxPosX + boxWidth - 2 && mouseY <= boxPosY + 104) {
                    selectingHue = true;
                    return true;
                } else if (mouseX >= boxPosX + 2 && mouseY >= boxPosY + boxHeight - 15 && mouseX <= boxPosX + boxWidth - 2 && mouseY <= boxPosY + boxHeight - 2) {
                    selectingAlpha = true;
                    return true;
                }
            }
        } else if (mouseButton == 1) {
            if (mouseX >= posX - 8 && mouseY >= posY + 10 && mouseX <= posX + 8 && mouseY <= posY + 20) {
                extended = false;
                extraOptions = true;
                return true;
            }
        }

        extended = false;
        selectingHue = false;
        selectingSB = false;
        selectingAlpha = false;
        extraOptions = false;
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        selectingHue = false;
        selectingSB = false;
        selectingAlpha = false;
    }

    public void onGuiClose() {
        extended = false;
        selectingHue = false;
        selectingSB = false;
        selectingAlpha = false;
        extraOptions = false;
    }

    public boolean isExtended() {
        return extended;
    }

}
