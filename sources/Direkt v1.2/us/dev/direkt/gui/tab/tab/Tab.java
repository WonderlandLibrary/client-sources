package us.dev.direkt.gui.tab.tab;

import us.dev.direkt.gui.tab.TabHandler;
import us.dev.direkt.gui.tab.block.TabBlock;

import java.util.Optional;

/**
 * @author Foundry
 */
public interface Tab<T> {
    void doInvocation();

    void renderTabBack();

    void renderTabFront();

    T getStateObject();

    Optional<Tab<?>> findParent();

    Optional<TabBlock> findChildren();

    TabBlock getContainerTabBlock();

    TabHandler getHandler();

    void addChild(Tab<?>... children);

    int getPosX();

    int getPosY();

    int getWidth();

    int getHeight();

    void setPosX(int posX);

    void setPosY(int posY);

    void setDimensionAndPos(int renderPosX, int renderPosY, int width, int height);
}
