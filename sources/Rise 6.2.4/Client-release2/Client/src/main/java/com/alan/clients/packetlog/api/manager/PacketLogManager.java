package com.alan.clients.packetlog.api.manager;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.packetlog.Check;
import com.alan.clients.packetlog.impl.HostsFileCheck;
import com.alan.clients.packetlog.impl.ProxyCheck;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.interfaces.ThreadAccess;
import lombok.Getter;
import rip.vantage.commons.util.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
@Getter
public final class PacketLogManager implements Accessor, ThreadAccess {

    private final List<Check> checkList = new ArrayList<>();

    private final StopWatch stopWatch = new StopWatch();

    public boolean packetLogging;

    public void init() {
        Client.INSTANCE.getEventBus().register(this);

        this.add(new HostsFileCheck());
        this.add(new ProxyCheck());
    }

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (stopWatch.finished(3000L)) {
            threadPool.execute(() -> {
                boolean detected = false;

                for (final Check check : this.checkList) {
                    if (check.run()) {
                        detected = true;
                        break;
                    }
                }

                this.packetLogging = detected;
            });
            stopWatch.reset();
        }
    };

    public void add(final Check check) {
        this.checkList.add(check);
    }
}