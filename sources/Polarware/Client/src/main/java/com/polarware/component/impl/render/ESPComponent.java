package com.polarware.component.impl.render;

import com.polarware.component.Component;
import com.polarware.component.impl.render.espcomponent.api.ESP;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import com.polarware.event.impl.render.LimitedRender2DEvent;
import com.polarware.event.impl.render.Render3DEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ESPComponent extends Component {

    public static ConcurrentLinkedQueue<ESP> esps = new ConcurrentLinkedQueue<>();

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<LimitedRender2DEvent> onLimitedRender2D = event -> {

        if (esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render2D);
    };

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<Render3DEvent> onRender3D = event -> {

        if (esps == null || esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render3D);
    };

    @EventLink(value = Priority.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        threadPool.execute(() -> {
            for (ESP esp1 : esps) {
                esp1.updateTargets();

                if (esp1.tick + 2 < mc.thePlayer.ticksExisted) {
                    esps.remove(esp1);
                }
            }
        });
    };

    public static void add(ESP esp) {
        threadPool.execute(() -> {
            boolean modified = false;
            for (ESP esp1 : esps) {
                if (esp.getClass().getSimpleName().equals(esp1.getClass().getSimpleName())) {
                    esp1.espColor = esp.espColor;
                    esp1.tick = mc.thePlayer.ticksExisted;;
                    modified = true;
                }
            }

            if (!modified) {
                esps.add(esp);
            }
        });
    }

    @EventLink()
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        esps.clear();
    };
}
