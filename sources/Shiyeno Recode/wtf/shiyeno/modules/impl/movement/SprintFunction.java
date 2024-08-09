package wtf.shiyeno.modules.impl.movement;

import wtf.shiyeno.modules.Function;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import net.minecraft.client.Minecraft;

@FunctionAnnotation(name = "Sprint", type = Type.Movement)
public class SprintFunction extends Function {

    public SprintFunction() {
        addSettings();
    }

    @Override
    public void onEvent(final Event event) {
        if (event instanceof EventUpdate) {
            if (!mc.player.isSneaking() && !mc.player.collidedHorizontally)
                mc.gameSettings.keyBindSprint.setPressed(!Minecraft.player.isSprinting());
        }
    }
}