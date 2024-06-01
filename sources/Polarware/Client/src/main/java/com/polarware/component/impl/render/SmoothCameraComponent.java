package com.polarware.component.impl.render;

import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import util.time.StopWatch;

public class SmoothCameraComponent extends Component {

    public static double y;
    public static StopWatch stopWatch = new StopWatch();

    public static void setY(double y) {
        stopWatch.reset();
        SmoothCameraComponent.y = y;
    }

    public static void setY() {
        if (stopWatch.finished(80)) SmoothCameraComponent.y = mc.thePlayer.lastTickPosY;
        stopWatch.reset();
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (stopWatch.finished(80)) return;
        mc.thePlayer.cameraYaw = 0;
        mc.thePlayer.cameraPitch = 0;
    };
}
