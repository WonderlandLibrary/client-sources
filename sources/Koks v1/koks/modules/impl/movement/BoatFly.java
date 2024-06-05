package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;
import net.minecraft.util.Vec3;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 23:28
 */
public class BoatFly extends Module {

    public ModeValue<String> mode = new ModeValue<>("Mode","AAC4",new String[] {"AAC4"}, this);

    public NumberValue<Integer> aac4boost = new NumberValue<>("AAC4-Boost", 8, 10,5,this);

    public BoatFly() {
        super("BoatFly", "You are very schneller boat flieger", Category.MOVEMENT);
        addValue(aac4boost);
        addValue(mode);
    }

    public boolean rided;

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            switch (mode.getSelectedMode()) {
                case "AAC4":
                    if(mc.thePlayer.isRiding())rided = true;

                    if(!mc.thePlayer.isRiding() && rided) {
                        Vec3 vec3 = mc.thePlayer.getLookVec();
                        int boost = aac4boost.getDefaultValue();
                        System.out.println(boost);
                        mc.thePlayer.motionX = vec3.xCoord * boost;
                        mc.thePlayer.motionY = 1;
                        mc.thePlayer.motionZ = vec3.zCoord * boost;
                        rided = false;
                    }
                    break;
            }
        }
    }

    @Override
    public void onEnable() {
        rided = false;
    }

    @Override
    public void onDisable() {

    }
}
