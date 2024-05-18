package us.dev.direkt.gui.tab.tab;

import com.google.common.base.Preconditions;
import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.util.render.OGLRender;

import java.util.Optional;

/**
 * Created by Foundry on 12/27/2015.
 */
public abstract class AbstractTab<T> implements Tab<T> {
    protected final T stateObject;
    protected final TabHandler handler;
    protected final Tab<?> parent;
    protected final TabBlock container,
            children;
    private int posX = 0,
            posY = 0;
    private int height = -1,
            width = -1;

    public AbstractTab(TabHandler handler, T stateObject, TabBlock container) {
        this(handler, stateObject, null, null, container);
    }

    public AbstractTab(TabHandler handler, T stateObject, Tab<?> parent, TabBlock container) {
        this(handler, stateObject, parent, null, container);
    }

    public AbstractTab(TabHandler handler, T stateObject, TabBlock children, TabBlock container) {
        this(handler, stateObject, null, children, container);
    }

    public AbstractTab(TabHandler handler, T stateObject, Tab<?> parent, TabBlock children, TabBlock container) {
        this.handler = Preconditions.checkNotNull(handler);
        this.stateObject = stateObject;
        this.container = container;
        this.parent = parent;
        this.children = children;
    }

    @Override
    public void renderTabBack() {
        OGLRender.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, 0x95050505);
    }

    public T getStateObject() {
        return this.stateObject;
    }

    public Optional<Tab<?>> findParent() {
        return Optional.ofNullable(parent);
    }

    public Optional<TabBlock> findChildren() {
        return Optional.ofNullable(children);
    }

    public TabBlock getContainerTabBlock() {
        return container;
    }

    public TabHandler getHandler() {
        return handler;
    }

    public void addChild(Tab<?>... children) {
        for (Tab<?> tab : children)
            this.children.appendTab(tab);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setDimensionAndPos(int renderPosX, int renderPosY, int width, int height) {
        this.posX = renderPosX;
        this.posY = renderPosY;
        this.width = width;
        this.height = height;
    }
}
