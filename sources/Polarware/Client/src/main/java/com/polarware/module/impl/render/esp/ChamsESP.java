package com.polarware.module.impl.render.esp;

import com.polarware.component.impl.render.ESPComponent;
import com.polarware.component.impl.render.espcomponent.api.ESPColor;
import com.polarware.component.impl.render.espcomponent.impl.PlayerChams;
import com.polarware.module.impl.render.ESPModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.value.Mode;

import java.awt.*;

public final class ChamsESP extends Mode<ESPModule> {

    public ChamsESP(String name, ESPModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Color color = getTheme().getFirstColor();
        ESPComponent.add(new PlayerChams(new ESPColor(color, color, color)));
    };
}