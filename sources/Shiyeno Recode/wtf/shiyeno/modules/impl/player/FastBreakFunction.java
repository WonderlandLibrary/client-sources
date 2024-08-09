package wtf.shiyeno.modules.impl.player;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;

@FunctionAnnotation(
        name = "FastBreak",
        type = Type.Player
)
public class FastBreakFunction extends Function {
    public BooleanOption bypass = new BooleanOption("ћгновенное ломание", false);

    public FastBreakFunction() {
        this.addSettings(new Setting[]{this.bypass});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            mc.playerController.resetBlockRemoving();
            mc.playerController.blockHitDelay = 0;
            if (this.bypass.get() && mc.player.isOnGround()) {
                mc.playerController.curBlockDamageMP = 1.0F;
            }
        }

    }
}