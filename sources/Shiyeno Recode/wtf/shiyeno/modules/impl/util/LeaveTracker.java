package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.BooleanOption;
import wtf.shiyeno.modules.settings.imp.SliderSetting;

@FunctionAnnotation(
        name = "LeaveTracker",
        type = Type.Util
)
public class LeaveTracker extends Function {
    public static SliderSetting distance = new SliderSetting("Искать с блоков от вас", 100.0F, 20.0F, 500.0F, 1.0F);
    public static BooleanOption gps = new BooleanOption("Автоматический GPS", true);

    public LeaveTracker() {
        this.addSettings(new Setting[]{distance, gps});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
        }
    }
}