package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

public class DelayUtil {

    private ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Consumer<EventTickPre>>> tickQueue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Consumer<EventTickPre>>> futureTickQueue = new ConcurrentHashMap<>();
    private boolean isRunning = false;

    public void queue(Consumer<EventTickPre> task, int ticksDelay) {
        ticksDelay = ticksDelay + 1;
        if (!isRunning) {
            if (tickQueue.containsKey(ticksDelay)) {
                ConcurrentLinkedQueue<Consumer<EventTickPre>> q = tickQueue.get(ticksDelay);
                q.add(task);
                tickQueue.put(ticksDelay, q);
            } else {
                ConcurrentLinkedQueue<Consumer<EventTickPre>> q = new ConcurrentLinkedQueue<>();
                q.add(task);
                tickQueue.put(ticksDelay, q);
            }
        } else {
            if (futureTickQueue.containsKey(ticksDelay)) {
                ConcurrentLinkedQueue<Consumer<EventTickPre>> q = futureTickQueue.get(ticksDelay);
                q.add(task);
                futureTickQueue.put(ticksDelay, q);
            } else {
                ConcurrentLinkedQueue<Consumer<EventTickPre>> q = new ConcurrentLinkedQueue<>();
                q.add(task);
                futureTickQueue.put(ticksDelay, q);
            }
        }
    }

    private void runQueue(EventTickPre e) {
        isRunning = true;

        if (tickQueue.containsKey(1)) {
            ConcurrentLinkedQueue<Consumer<EventTickPre>> q = tickQueue.get(1);
            while (!q.isEmpty()) {
                q.poll().accept(e);
            }
        }

        ConcurrentHashMap<Integer, ConcurrentLinkedQueue<Consumer<EventTickPre>>> newQ = new ConcurrentHashMap<>();
        for (Integer key : tickQueue.keySet()) {
            if (key != 1) newQ.put(key - 1, tickQueue.get(key));
        }
        tickQueue = newQ;

        isRunning = false;

        for (Iterator<Integer> it = futureTickQueue.keys().asIterator(); it.hasNext(); ) {
            int k = it.next();
            for (Consumer<EventTickPre> task : futureTickQueue.get(k)) {
                queue(task, k);
            }
        }
        futureTickQueue.clear();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = e -> {
        runQueue(e);
    };
}
