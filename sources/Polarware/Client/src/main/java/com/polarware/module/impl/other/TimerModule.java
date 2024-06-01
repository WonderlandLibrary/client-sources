package com.polarware.module.impl.other;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.math.MathUtil;
import com.polarware.value.impl.BoundsNumberValue;

@ModuleInfo(name = "module.other.timer.name", description = "module.other.timer.description", category = Category.OTHER)
public final class TimerModule extends Module {

    private final BoundsNumberValue timer =
            new BoundsNumberValue("Timer", this, 1, 2, 0.1, 10, 0.1);

    @EventLink(value = Priority.MEDIUM)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        mc.timer.timerSpeed = (float) MathUtil.getRandom(timer.getValue().floatValue(), timer.getSecondValue().floatValue());
    };
}
