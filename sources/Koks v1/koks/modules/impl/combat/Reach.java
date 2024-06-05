package koks.modules.impl.combat;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.MouseOverEvent;
import koks.modules.Module;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 11:09
 */
public class Reach extends Module {

    public NumberValue<Double> reach = new NumberValue<>("Extra Reach", 0.2D, 1D, 0D, this);

    public Reach() {
        super("Reach", "", Category.COMBAT);
        addValue(reach);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(reach.getDefaultValue().toString());
        }

        if (event instanceof MouseOverEvent) {
            ((MouseOverEvent) event).setReach(3 + reach.getDefaultValue());
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}