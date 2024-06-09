package exhibition.event;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.module.impl.other.Spotify;
import exhibition.util.MinecraftUtil;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class EventSubscription<T extends Event> {
    private final T event;
    private final List<EventListener> subscribed = new CopyOnWriteArrayList<EventListener>();

    public EventSubscription(T event) {
        this.event = event;
    }

    public void fire(Event event) {
        if (MinecraftUtil.mc.thePlayer == null) {
            return;
        }
        this.subscribed.forEach(listener -> {
            if (MinecraftUtil.mc.thePlayer != null || listener instanceof Spotify) {
                listener.onEvent(event);
            }
        });
    }

    public void add(EventListener listener) {
        this.subscribed.add(listener);
        this.subscribed.sort(Comparator.comparing(EventListener::getPriority));
    }

    public void remove(EventListener listener) {
        if (this.subscribed.contains(listener)) {
            this.subscribed.remove(listener);
        }
    }

    public List<EventListener> getSubscribed() {
        return this.subscribed;
    }

    public Event getEvent() {
        return this.event;
    }
}

