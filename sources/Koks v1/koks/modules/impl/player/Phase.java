package koks.modules.impl.player;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 17:22
 */
public class Phase extends Module {

    public ModeValue<String> mode = new ModeValue<String>("Mode","Hive",new String[] {"Hive"},this);

    public Phase() {
        super("Phase", "You can walk through walls", Category.PLAYER);
        addValue(mode);
    }

    public void Hive() {
        double motionX = -(Math.sin(Math.toRadians(mc.thePlayer.rotationYaw)) * 0.7);
        double motionZ = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw)) * 0.7;
        mc.thePlayer.setPosition(mc.thePlayer.posX + motionX, mc.thePlayer.posY, mc.thePlayer.posZ + motionZ);

        mc.thePlayer.motionY = 0;
        mc.thePlayer.onGround = true;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());

            switch (mode.getSelectedMode()) {
                case "Hive":
                    Hive();
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
