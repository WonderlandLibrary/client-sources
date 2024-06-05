/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.tab;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.TabHandler;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;

public abstract class AbstractTab<T>
implements Tab<T> {
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final T stateObject;
    protected final TabHandler handler;
    protected final Tab<?> parent;
    protected final TabBlock container;
    protected final TabBlock children;
    private int posX = 0;
    private int posY = 0;
    private int height = -1;
    private int width = -1;

    public AbstractTab(TabHandler handler, T stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        this.handler = (TabHandler)Preconditions.checkNotNull((Object)handler);
        this.stateObject = stateObject;
        this.container = container;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public void renderTabBack() {
        Gui.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, new Color(13, 13, 13, 220).getRGB());
    }

    @Override
    public T getStateObject() {
        return this.stateObject;
    }

    @Override
    public Optional<Tab<?>> findParent() {
        return Optional.ofNullable(this.parent);
    }

    @Override
    public Optional<TabBlock> findChildren() {
        return Optional.ofNullable(this.children);
    }

    @Override
    public TabBlock getContainerTabBlock() {
        return this.container;
    }

    @Override
    public TabHandler getHandler() {
        return this.handler;
    }

    @Override
    public void addChild(Tab<?> ... children) {
        for (Tab<?> tab : children) {
            this.children.appendTab(tab);
        }
    }

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public void setDimensionAndPos(int renderPosX, int renderPosY, int width, int height) {
        this.posX = renderPosX;
        this.posY = renderPosY;
        this.width = width;
        this.height = height;
    }
}

