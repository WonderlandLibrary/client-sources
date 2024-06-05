package me.vaziak.sensation.client.api.gui.ingame.HUD.components;

import java.awt.Color;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.Value;
import me.vaziak.sensation.utils.anthony.ColorCreator;
import me.vaziak.sensation.utils.anthony.Draw;
import me.vaziak.sensation.utils.anthony.basicfont.Fonts;

/**
 * @author antja03
 */
public class CheckboxComponent extends ValueComponent {

    private BooleanProperty booleanValue;

    public CheckboxComponent(Value value, int posX, int posY) {
        super(value, posX, posY);
        this.booleanValue = (BooleanProperty) value;
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        double alphaModifier = Sensation.instance.hudEditor.getAlphaModifer();
        Fonts.f14.drawCenteredString(booleanValue.getId(), posX, posY + 1, ColorCreator.create(220, 220, 220, (int) (220 * alphaModifier)));
        Draw.drawRectangle(posX - 5, posY + 10, posX + 5, posY + 20, ColorCreator.create(50, 50, 50, (int) (255 * alphaModifier)));
        if (booleanValue.getValue())
            Draw.drawRectangle(posX - 4, posY + 11, posX + 4, posY + 19, new Color(255, 75,60).getRGB());

    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= posX - 5 && mouseY >= posY + 10 && mouseX <= posX + 5 && mouseY <= posY + 20) {
            booleanValue.setValue(!booleanValue.getValue());
            return true;
        }
        return false;
    }

}
