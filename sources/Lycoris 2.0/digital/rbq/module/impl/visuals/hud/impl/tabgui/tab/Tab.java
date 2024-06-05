/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab;

import java.util.Optional;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;

public interface Tab<T> {
    public void doInvocation();

    public void renderTabBack();

    public void renderTabFront();

    public int getTabWidth();

    public T getStateObject();

    public Optional<Tab<?>> findParent();

    public Optional<TabBlock> findChildren();

    public TabBlock getContainerTabBlock();

    public TabHandler getHandler();

    public void addChild(Tab<?> ... var1);

    public int getPosX();

    public int getPosY();

    public int getWidth();

    public int getHeight();

    public void setPosX(int var1);

    public void setPosY(int var1);

    public void setDimensionAndPos(int var1, int var2, int var3, int var4);
}

