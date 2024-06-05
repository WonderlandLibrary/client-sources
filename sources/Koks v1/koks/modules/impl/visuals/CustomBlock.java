package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EventItemRenderer;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 20:04
 */
public class CustomBlock extends Module {

    public ModeValue<String> blockAnimation = new ModeValue<>("Block Animation", "1.8", new String[]{"1.8", "1.7", "Eject", "Simple", "Power"}, this);

    public CustomBlock() {
        super("CustomBlock", "Its change the Block Hit Animation", Category.VISUALS);
        addValue(blockAnimation);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventItemRenderer) {
            EventItemRenderer e = (EventItemRenderer) event;
            switch (blockAnimation.getSelectedMode()) {
                case "1.8":
                    e.setFirstArg(e.getF());
                    e.setLastArg(0.0F);
                    break;
                case "1.7":
                    e.setFirstArg(e.getF());
                    e.setLastArg(e.getF1());
                    break;
                case "Eject":
                    e.setFirstArg(e.getF() - 0.25F);
                    e.setLastArg(e.getF1() * 0.3F - 0.02F);
                    break;
                case "Simple":
                    e.setFirstArg(e.getF());
                    e.setLastArg(e.getF1() - 1.0F);
                    break;
                case "Power":
                    e.setFirstArg(e.getF() - 0.25F);
                    e.setLastArg(e.getF1() * 0.1F - 0.9F);
                    break;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}