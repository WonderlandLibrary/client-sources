package wtf.shiyeno.modules.impl.util;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.misc.TimerUtil;

@FunctionAnnotation(
        name = "AutoEvents",
        type = Type.Util
)
public class AutoEvents extends Function {
    private final TimerUtil timerUtil = new TimerUtil();
    private final SliderSetting timer = new SliderSetting("Скорость сообщения", 1.0F, 1.0F, 10000.0F, 1.0F);
    private final ModeSetting events = new ModeSetting("Авто команда", "boss", new String[]{"boss", "siege", "event delay"});

    public AutoEvents() {
        this.addSettings(new Setting[]{this.timer, this.events});
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            if (mc.player == null || mc.world == null) {
                return;
            }

            if (this.events.is("event delay") && this.timerUtil.hasTimeElapsed((long)this.timer.getValue().intValue())) {
                mc.player.sendChatMessage("/event delay");
                this.timerUtil.reset();
            }

            if (this.events.is("boss") && this.timerUtil.hasTimeElapsed((long)this.timer.getValue().intValue())) {
                mc.player.sendChatMessage("/boss join");
                this.timerUtil.reset();
            }

            if (this.events.is("siege") && this.timerUtil.hasTimeElapsed((long)this.timer.getValue().intValue())) {
                mc.player.sendChatMessage("/siege join");
                this.timerUtil.reset();
            }
        }
    }
}