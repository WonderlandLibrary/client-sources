package com.alan.clients.component.impl.render;

import com.alan.clients.component.Component;
import com.alan.clients.component.impl.render.espcomponent.api.ESP;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.UpdatePlayerAnglesEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.event.impl.render.Render3DEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ESPComponent extends Component {
    public static ConcurrentLinkedQueue<ESP> esps = new ConcurrentLinkedQueue<>();

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render2D);
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<UpdatePlayerAnglesEvent> onUpdatePlayerAngles = event -> {
        if (esps == null || esps.isEmpty()) {
            return;
        }

        for (ESP esp : esps) {
            esp.updatePlayerAngles(event.getEntityPlayer(), event.getModelBiped());
        }
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<Render3DEvent> onRender3D = event -> {
        if (esps == null || esps.isEmpty()) {
            return;
        }

        esps.forEach(ESP::render3D);
    };

    @EventLink(value = Priorities.VERY_HIGH)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        for (ESP esp1 : esps) {
            esp1.updateTargets();

            if (esp1.tick + 2 < mc.thePlayer.ticksExisted) {
                esps.remove(esp1);
            }
        }
    };

    public static void add(ESP esp) {
        boolean modified = false;
        for (ESP esp1 : esps) {
            if (esp.getClass().getSimpleName().equals(esp1.getClass().getSimpleName())) {
                esp1.espColor = esp.espColor;
                esp1.tick = mc.thePlayer.ticksExisted;
                modified = true;
            }
        }

        if (!modified) {
            esps.add(esp);
        }
    }

    @EventLink
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        esps.clear();
    };
}
