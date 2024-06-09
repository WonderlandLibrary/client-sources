package host.kix.uzi.ui.click.impl.themes.darcula.components;

import com.ibm.icu.math.MathContext;
import host.kix.uzi.ui.click.api.theme.ComponentUI;
import host.kix.uzi.ui.click.api.theme.Theme;
import host.kix.uzi.ui.click.impl.component.ValueSlider;
import host.kix.uzi.utilities.minecraft.NahrFont;
import net.minecraft.client.gui.Gui;

import java.math.BigDecimal;

/**
 * @author Marc
 * @since 7/22/2016
 */
public class ValueSliderUI extends ComponentUI<ValueSlider> {

    /**
     * The font of the component.
     */

    private final NahrFont font, keyBinding;


    public ValueSliderUI(Theme theme) {
        super(theme);
        font = new NahrFont("Arial", 16F);
        keyBinding = new NahrFont("Arial", 12.5F);

    }

    @Override
    public void draw(ValueSlider component, int x, int y) {
        Gui.drawRect((int) (component.getX() + component.getCompletion()), component.getY(),
                (int) (component.getX() + component.getWidth()), component.getY() + component.getHeight(), 0xFF3F4041);
        Gui.drawRect(component.getX(), component.getY(), (int) (component.getX() + component.getCompletion()),
                component.getY() + component.getHeight(), 0xFF4B7EAF);

        if (component.isHovering(x, y))
            Gui.drawRect(component.getX(), component.getY(), component.getX() + component.getWidth(),
                    component.getY() + component.getHeight(), 0x26FFFFFF);

        font.drawString(component.getLabel(), component.getX() + 4, component.getY() + 1, NahrFont.FontType.SHADOW_THIN, -1);

        String sliderValue = component.getSliderValue() + "";

        if (component.getValue().getValue() instanceof Double || component.getValue().getValue() instanceof Float)
            sliderValue = String.format("%.1f", component.getSliderValue());

        font.drawString(sliderValue, component.getX() + component.getWidth() - font.getStringWidth(sliderValue) - 4,
                component.getY() + 1, NahrFont.FontType.SHADOW_THIN, -1);

    }

    public static double roundTo(float v, float r) {
        return Math.round(v / r) * r;
    }

    public double roundToPlace(double fuck, int places) {
        BigDecimal decimal = new BigDecimal(fuck);

        return decimal.setScale(places, MathContext.ROUND_HALF_UP).doubleValue();
    }

}
