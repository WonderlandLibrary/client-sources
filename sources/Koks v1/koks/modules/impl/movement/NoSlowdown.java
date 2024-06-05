package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.values.BooleanValue;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 23:31
 */
public class NoSlowdown extends Module {

    public NumberValue<Integer> speedInPercent = new NumberValue<>("Speed in Percent", 20, 100, 20, this);
    public BooleanValue<Boolean> sprint = new BooleanValue<>("Sprint", true, this);

    public NoSlowdown() {
        super("NoSlowdown", "You are not slow when you doing sachen", Category.MOVEMENT);
        addValue(speedInPercent);
        addValue(sprint);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(speedInPercent.getDefaultValue().toString() + (sprint.isToggled() ? ", Sprint" : ""));
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}