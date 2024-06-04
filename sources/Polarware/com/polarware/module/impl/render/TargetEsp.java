package com.polarware.module.impl.render;

import com.polarware.component.impl.render.ESPComponent;
import com.polarware.component.impl.render.espcomponent.api.ESPColor;
import com.polarware.component.impl.render.espcomponent.impl.AboveBox;
import com.polarware.component.impl.render.espcomponent.impl.FullBox;
import com.polarware.component.impl.render.espcomponent.impl.SigmaRing;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.SubMode;

import java.awt.*;

@ModuleInfo(name = "module.render.targetesp.name", description = "i wonder", category = Category.RENDER)
public class TargetEsp extends Module {
    private final ModeValue auraespMode = new ModeValue("Aura ESP Mode", this)
            .add(new SubMode("Ring"))
            .add(new SubMode("Box"))
            .add(new SubMode("None"))
            .setDefault("Ring");

    public final ModeValue backtrackespmode = new ModeValue("Backtrack ESP Mode", this)
            .add(new SubMode("Fake Player"))
            .add(new SubMode("Box"))
            .add(new SubMode("None"))
            .setDefault("Box");
    public final ModeValue boxMode = new ModeValue("Box Mode", this, () -> !(auraespMode.getValue()).getName().equals("Box"))
            .add(new SubMode("Above"))
            .add(new SubMode("Full"))
            .setDefault("Ring");

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        Color color = getTheme().getFirstColor();
        switch (auraespMode.getValue().getName()) {
            case "Ring":
                ESPComponent.add(new SigmaRing(new ESPColor(color, color, color)));
                break;
            case "Box":
                switch (boxMode.getValue().getName()) {
                    case "Full":
                        ESPComponent.add(new FullBox(new ESPColor(color, color, color)));
                        break;
                    case "Above":
                        ESPComponent.add(new AboveBox(new ESPColor(color, color, color)));
                        break;
                }
                break;

        }
    };
}
