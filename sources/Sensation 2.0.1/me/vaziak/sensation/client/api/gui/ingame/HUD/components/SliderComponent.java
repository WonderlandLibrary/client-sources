package me.vaziak.sensation.client.api.gui.ingame.HUD.components;

import org.lwjgl.input.Keyboard;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;
import me.vaziak.sensation.utils.math.MathUtils;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author antja03
 */
public class SliderComponent extends ValueComponent {

    private DoubleProperty numberValue;

    private boolean mouseDragging;
    private boolean typing;
    private String typedChars;
    private double widthOfSlider;
    private DoubleProperty property;
    private double currentPosition;
    private ArrayList<Double> possibleValues = new ArrayList<>();

    public SliderComponent(Value value, int x, int y) {
        super(value, x, y);
        this.numberValue = (DoubleProperty) value;
        mouseDragging = false;
        typing = false;
        typedChars = "";
        widthOfSlider = 100;
        property = (DoubleProperty) value;
        currentPosition = (widthOfSlider / (property.getMaximum() - property.getMinimum())) * (property.getValue() - property.getMinimum());
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        {
            double alphaModifier = Sensation.instance.hudEditor.getAlphaModifer();

            //Identifier
            Fonts.f14.drawCenteredString(numberValue.getId(), posX, posY + 2, ColorCreator.create(220, 220, 220, (int) (220 * alphaModifier)));
            //PropertySlider BG
            Draw.drawRectangle(posX - 50, posY + 10, posX + 50, posY + 14, ColorCreator.create(50, 50, 50, (int) (255 * alphaModifier)));
            //PropertySlider Fill
            Draw.drawRectangle(posX - 50, posY + 10, posX - 50 + currentPosition, posY + 14, new Color(255, 75,60).getRGB());
            //Current
            if (typing) {
                Fonts.f12.drawCenteredString(typedChars + "_" + property.getNumType(), (posX + widthOfSlider / 2), posY + 17,
                        typing ? new Color(255, 75,60).getRGB() : ColorCreator.create(220, 220, 220, (int) (255 * alphaModifier)));
            } else {
                Fonts.f12.drawCenteredString(MathUtils.round(property.getValue(), 2) + property.getNumType(), posX - 50 + currentPosition, posY + 17,
                        typing ? new Color(255, 75,60).getRGB() : ColorCreator.create(220, 220, 220, (int) (255 * alphaModifier)));
            }
        }

        /*
         * Handling the property value
         */
        {
            if (typing) {
                mouseDragging = false;
            }

            if (mouseDragging) {
                double cursorPosOnBar = mouseX - posX + 50;
                currentPosition = MathUtils.clamp(cursorPosOnBar, 0, widthOfSlider);
                double exactValue = MathUtils.clamp(property.getMinimum() + ((property.getMaximum() - property.getMinimum()) * (cursorPosOnBar / widthOfSlider)), property.getMinimum(), property.getMaximum());

                if (possibleValues.isEmpty()) {
                    double current = property.getMinimum();
                    possibleValues.add(current);
                    while (current < property.getMaximum()) {
                        current += property.getIncrement();
                        possibleValues.add(current);
                    }
                }

                double bestValue = -1;
                for (Double value : possibleValues) {
                    if (bestValue == -1) {
                        bestValue = value;
                        continue;
                    } else {
                        if (MathUtils.getDifference(exactValue, value) < MathUtils.getDifference(exactValue, bestValue))
                            bestValue = value;
                    }
                }

                property.setValue(bestValue);
            } else {
                setPositionBasedOnValue();
                possibleValues.clear();
            }
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        if (mouseX >= posX - 50 && mouseY >= posY + 10 && mouseX <= posX + 50 && mouseY <= posY + 14) {
            if (button == 0) {
                mouseDragging = true;
                return true;
            } else if (button == 1) {
                typing = true;
                return true;
            } else {
                mouseDragging = false;
                typing = false;
            }
        }
        return false;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        mouseDragging = false;
    }

    public boolean keyTyped(char typedChar, int keyCode) {
        if (typing) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                typing = false; 
            }

            String allowedChars = "0123456789.";
            if (keyCode == Keyboard.KEY_BACK) {
                if (typedChars.length() > 1) {
                    typedChars = typedChars.substring(0, typedChars.length() - 1);
                } else if (typedChars.length() == 1) {
                    typedChars = "";
                }
            } else if (keyCode == Keyboard.KEY_RETURN) {
                typedChars = "";
                typing = false;
            } else if (allowedChars.contains(Character.toString(typedChar))) {
                if (Fonts.f14.getStringWidth(typedChars) < 100 - 1) {
                    typedChars += Character.toString(typedChar);
                }
            }
            property.setValue(typedChars);
        }
        return false;
    }

    public void onGuiClose() {
        mouseDragging = false;
        typing = false;
    }

    public void setPositionBasedOnValue() {
        currentPosition = (widthOfSlider / (property.getMaximum() - property.getMinimum())) * (property.getValue() - property.getMinimum());
    }

}
