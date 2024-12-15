package com.alan.clients.component.impl.player;

import com.alan.clients.component.Component;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.util.player.PingerCallable;
import rip.vantage.commons.util.time.StopWatch;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class PingComponent extends Component {
    private static long ping = 250;
    private static final Executor thread = Executors.newFixedThreadPool(1);
    private final static StopWatch lastPing = new StopWatch();
    private final static StopWatch lastGrab = new StopWatch();
    // Length between something calling getPing() before it auto disables
    private static final long TIMEOUT_AUTO_DISABLE = 120000;
    // Delay between pings
    private static final long DELAY = 10000;
    // Assumed ping if ping is not known yet
    private static final long DEFAULT_PING = 250;

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (lastPing.finished(DELAY) && !lastGrab.finished(TIMEOUT_AUTO_DISABLE)) {
            ping(false);
            lastPing.reset();
        }
    };

    public static long getPing() {
        if (lastGrab.finished(TIMEOUT_AUTO_DISABLE)) {
            ping(true);
            lastGrab.reset();
            return DEFAULT_PING;
        } else {
            lastGrab.reset();
            return ping;
        }
    }

    private static void ping(boolean notify) {
        if (mc.isIntegratedServerRunning()) {
            ping = 0;
            return;
        }

        thread.execute(() -> {
            lastPing.reset();
            if (notify) NotificationComponent.post("Ping", "Please wait whilst Rise analyses your ping.", 7000);
            PingerCallable pingerCallable = new PingerCallable(LastConnectionComponent.ip);

            ping = pingerCallable.call();
            if (notify)
                NotificationComponent.post("Success", "Successfully analysed ping, your features are ready to use.", 7000);
        });
    }
}