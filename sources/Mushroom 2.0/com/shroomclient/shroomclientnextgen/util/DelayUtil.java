package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.annotations.RegisterListeners;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

// TODO This is probably horribly inefficient
@RegisterListeners
public class DelayUtil {

    private static ConcurrentHashMap<
        Integer,
        ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>>
    > tickQueue = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<
        Integer,
        ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>>
    > futureTickQueue = new ConcurrentHashMap<>();
    private static boolean isRunning = false;

    public static void queue(Consumer<MotionEvent.Pre> task, int ticksDelay) {
        if (!isRunning) {
            if (tickQueue.containsKey(ticksDelay)) {
                ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>> q =
                    tickQueue.get(ticksDelay);
                q.add(task);
                tickQueue.put(ticksDelay, q);
            } else {
                ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>> q =
                    new ConcurrentLinkedQueue<>();
                q.add(task);
                tickQueue.put(ticksDelay, q);
            }
        } else {
            if (futureTickQueue.containsKey(ticksDelay)) {
                ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>> q =
                    futureTickQueue.get(ticksDelay);
                q.add(task);
                futureTickQueue.put(ticksDelay, q);
            } else {
                ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>> q =
                    new ConcurrentLinkedQueue<>();
                q.add(task);
                futureTickQueue.put(ticksDelay, q);
            }
        }
    }

    private static void runQueue(MotionEvent.Pre e) {
        isRunning = true;

        if (tickQueue.containsKey(1)) {
            ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>> q = tickQueue.get(
                1
            );
            while (!q.isEmpty()) {
                q.poll().accept(e);
            }
        }

        ConcurrentHashMap<
            Integer,
            ConcurrentLinkedQueue<Consumer<MotionEvent.Pre>>
        > newQ = new ConcurrentHashMap<>();
        for (Integer key : tickQueue.keySet()) {
            if (key != 1) newQ.put(key - 1, tickQueue.get(key));
        }
        tickQueue = newQ;

        isRunning = false;

        for (
            Iterator<Integer> it = futureTickQueue.keys().asIterator();
            it.hasNext();
        ) {
            int k = it.next();
            for (Consumer<MotionEvent.Pre> task : futureTickQueue.get(k)) {
                queue(task, k);
            }
        }
        futureTickQueue.clear();
    }

    @SubscribeEvent
    public void onMotionPre(MotionEvent.Pre e) {
        runQueue(e);
    }
}
