/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui;

import me.zane.basicbus.api.annotations.Listener;
import digital.rbq.events.game.KeyPressEvent;
import digital.rbq.module.impl.visuals.hud.impl.TabComponent;

public final class TabInputHandler {
    private final TabComponent component;

    public TabInputHandler(TabComponent component) {
        this.component = component;
    }

    @Listener(value=KeyPressEvent.class)
    public final void onKeyPress(KeyPressEvent event) {
        this.component.getHandler().doKeyInput(event.getKeyCode());
    }
}

