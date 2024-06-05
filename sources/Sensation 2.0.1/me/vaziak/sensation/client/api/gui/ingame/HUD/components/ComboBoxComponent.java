package me.vaziak.sensation.client.api.gui.ingame.HUD.components;

import java.awt.*;
import java.util.ArrayList;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

/**
 * @author antja03
 */
public class ComboBoxComponent extends ValueComponent {
    private StringsProperty comboValue;
    private boolean extended;

    public ComboBoxComponent(Value value, int x, int y) {
        super(value, x, y);
        this.comboValue = (StringsProperty) value;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        double alphaModifier = Sensation.instance.hudEditor.getAlphaModifer();

        Fonts.f14.drawCenteredString(comboValue.getId(), posX, posY + 2, ColorCreator.create(220, 220, 220, (int) (220 * alphaModifier)));

        int keyCount = comboValue.getValue().keySet().size();
        double boxHeight = 10;

        if (extended && !Sensation.instance.hudEditor.isClosing())
            boxHeight += 10 * (keyCount - 1);

        Draw.drawRectangle(posX - 30, posY + 10, posX + 30, posY + 10 + boxHeight, ColorCreator.create(24, 24, 24, (int) (255 * alphaModifier)));

        if (extended && !Sensation.instance.hudEditor.isClosing()) {
            ArrayList<String> options = getUnorderedList();
            for (String option : options) {
                double optionPosY = posY + 10 + options.indexOf(option) * 10;
                if (comboValue.isSelected(option))
                    Draw.drawRectangle(posX - 30, optionPosY, posX + 30, optionPosY + 10, new Color(19, 19, 19).getRGB());

                Fonts.f12.drawCenteredString(option, posX, optionPosY + 4, new Color(230, 230, 230, 230).getRGB());
            }
        } else {
            Fonts.f12.drawCenteredString(getBoxLabel(), posX, posY + 14, ColorCreator.create(255, 255, 255, (int) (255 * alphaModifier)));
        }
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        int keyCount = comboValue.getValue().keySet().size();
        if (!extended) {
            if (mouseX >= posX && mouseY >= posY + 10 && mouseX <= posX + 60 && mouseY <= posY + 20) {
                extended = true;
                return true;
            }
        } else {
            ArrayList<String> options = getUnorderedList();
            for (String string : options) {
                double optionPosY = posY + 10 + options.indexOf(string) * 10;
                if (mouseX >= posX - 30 && mouseY >= optionPosY && mouseX <= posX + 30 && mouseY <= optionPosY + 10) {
                    comboValue.setOption(string + ":" + !comboValue.getValue().get(string));
                    if (!comboValue.canMultiselect())
                        extended = false;
                    return true;
                }
            }
        }
        extended = false;
        return false;
    }

    public String getBoxLabel() {
        String finalString = "";
        if (comboValue.getSelectedStrings().isEmpty())
            return finalString;

        if (comboValue.canMultiselect()) {
            for (String string : comboValue.getSelectedStrings()) {
                finalString += string + ", ";
            }
            finalString = finalString.substring(0, finalString.length() - 2);
            ArrayList<String> strings = new ArrayList<>(Fonts.f14.wrapWords(finalString, 80));
            finalString = Fonts.f14.wrapWords(finalString, 80).get(0);
            if (strings.size() > 1) {
                finalString = finalString.substring(0, finalString.length() - 3);
                finalString += "...";
            }
        } else {
            finalString = comboValue.getSelectedStrings().get(0);
        }

        return finalString;
    }

    public ArrayList<String> getUnorderedList() {
        return new ArrayList<>(comboValue.getValue().keySet());
    }

    public ArrayList<String> getOrderedList() {
        ArrayList<String> orderedList = new ArrayList<>();
        if (!comboValue.canMultiselect()) {
            orderedList.add(comboValue.getSelectedStrings().get(0));
        }
        for (String string : comboValue.getValue().keySet()) {
            if ((!comboValue.canMultiselect() && !string.equalsIgnoreCase(orderedList.get(0))) || comboValue.canMultiselect()) {
                orderedList.add(string);
            }
        }
        return orderedList;
    }

}
