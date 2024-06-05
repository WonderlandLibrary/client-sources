/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.handling;

import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;

public interface TabFactory<T> {
    public Tab<T> parse(TabHandler var1, T var2, Tab<?> var3, TabBlock var4, TabBlock var5);

    public Class<? extends T> getHandledType();
}

