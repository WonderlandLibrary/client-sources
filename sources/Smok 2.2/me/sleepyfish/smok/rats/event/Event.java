package me.sleepyfish.smok.rats.event;

import me.sleepyfish.smok.Smok;

// Class from SMok Client by SleepyFish
public abstract class Event {
    private boolean cancel;

    public Event call() {
        this.cancel = false;
        call(this);
        return this;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(boolean cancelled) {
        this.cancel = cancelled;
    }

    private static void call(Event event) {
        ArrayHelper<EventHelp> dataList = Smok.inst.eveManager.getEvent(event.getClass());
        if (dataList != null) {
            for (EventHelp data : dataList) {
                try {
                    data.meth.invoke(data.obj, event);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
