package us.dev.direkt.gui.tab;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import us.dev.api.timing.Timer;
import us.dev.direkt.Wrapper;
import us.dev.direkt.gui.tab.block.TabBlock;
import us.dev.direkt.gui.tab.cursor.TabCursor;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.util.render.OGLRender;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Foundry on 12/29/2015.
 */
public  class TabHandler {

    private enum ScissorBoxState {
        EXPANDING, CONTRACTING, STATIC
    }

    private Map<Tab<?>, LinkedList<TabAction>> globalKeySubscriberRegistry = Maps.newHashMap();
    private Map<Integer, LinkedList<TabAction>> specificKeySubscriberRegistry = Maps.newHashMap();
    private Queue<Tab<?>> unsubscriptionQueue = Lists.newLinkedList(); //make global queue

    private boolean defaultKeyListening = true, wasListeningCancelled, wasPopulated;

    private int renderPosX, renderPosY,
            defaultTabWidth, defaultTabHeight,
            horizontalTabSpacing, verticalTabSpacing,
            scissorBoxWidth, scissorBoxHeight,
            totalHeight,
            treeDepth, previousTreeDepth;

    private ScissorBoxState scissorBoxState = ScissorBoxState.EXPANDING;
    private Timer scissorBoxTimer = new Timer();

    private TabBlock currentBlock;
    private TabCursor currentCursor;

    private TabHandler(int renderPosX, int renderPosY, int defaultTabWidth, int defaultTabHeight, int horizontalTabSpacing, int verticalTabSpacing, TabCursor currentCursor) {
        this.renderPosX = renderPosX;
        this.renderPosY = renderPosY;
        this.defaultTabWidth = defaultTabWidth;
        this.defaultTabHeight = defaultTabHeight;
        this.horizontalTabSpacing = horizontalTabSpacing;
        this.verticalTabSpacing = verticalTabSpacing;
        this.currentCursor = currentCursor;
    }

    public int getRenderPosX() {
        return this.renderPosX;
    }

    public int getRenderPosY() {
        return this.renderPosY;
    }

    public void ascendTabTree() {
        this.currentBlock.restartIteration();
        this.currentBlock = this.currentBlock.getCurrent().findParent().get().getContainerTabBlock();
        this.scissorBoxState = ScissorBoxState.CONTRACTING;
        this.previousTreeDepth = treeDepth--;
    }

    public void descendTabTree() {
        this.currentBlock = this.currentBlock.getCurrent().findChildren().get();
        this.scissorBoxState = ScissorBoxState.EXPANDING;
        if (this.scissorBoxWidth == this.defaultTabWidth) {
            this.scissorBoxWidth = 0;
        }
        this.previousTreeDepth = treeDepth++;
    }

    public void shiftUpTab() {
        this.currentBlock.cycleToPrevious();
    }

    public void shiftDownTab() {
        this.currentBlock.cycleToNext();
    }

    public boolean canAscendTree() {
        return this.currentBlock.getCurrent().findParent().isPresent();
    }

    public boolean canDescendTree() {
        return this.currentBlock.getCurrent().findChildren().isPresent()
                && this.currentBlock.getCurrent().findChildren().get().sizeOf() > 0;
    }

    public Tab<?> getCurrentTab() {
        return this.currentBlock.getCurrent();
    }

    public void setCurrentTabs(Collection<? extends Tab<?>> tabs) {
        this.currentBlock = tabs.stream().findFirst().get().getContainerTabBlock();
        tabs.forEach(this.currentBlock::appendTab);
        this.assignTabPositions(this.currentBlock, this.renderPosX, this.renderPosY);
        this.currentBlock.forEach(tab -> this.totalHeight += tab.getHeight());
        this.wasPopulated = true;
    }

    public void setCurrentTabs(TabBlock block) {
        this.currentBlock = block;
        this.assignTabPositions(this.currentBlock, this.renderPosX, this.renderPosY);
        this.currentBlock.forEach(tab -> this.totalHeight += tab.getHeight());
        this.wasPopulated = true;
    }

    private void assignTabPositions(TabBlock block, int startPosX, int startPosY) {
        if (block.sizeOf() == 0)
            return;
        for (Iterator<TabBlock.DoublyLinkedTab> it = block.linkedIterator(); it.hasNext();) {
            final TabBlock.DoublyLinkedTab tab = it.next();

            Optional<Tab<?>> parent = tab.getTab().findParent();
            if (tab.getTab() == block.getFirst()) {
                tab.getTab().setDimensionAndPos(
                        parent.isPresent()
                                ? parent.get().getPosX() + parent.get().getWidth() + this.horizontalTabSpacing
                                : startPosX,
                        parent.isPresent()
                                ? parent.get().getPosY()
                                : startPosY,
                        this.defaultTabWidth, this.defaultTabHeight);
            }
            else {
                tab.getTab().setDimensionAndPos(
                        parent.isPresent()
                                ? parent.get().getPosX() + parent.get().getWidth() + this.horizontalTabSpacing
                                : startPosX,
                        tab.previous.getTab().getPosY() + tab.previous.getTab().getHeight() + this.verticalTabSpacing,
                        this.defaultTabWidth, this.defaultTabHeight);
            }

            if (tab.getTab().findChildren().isPresent()) {
                this.assignTabPositions(tab.getTab().findChildren().get(), this.renderPosX, this.renderPosY);
            }
        }
    }

    private void doForCurrentTreeBranch(Consumer<Tab<?>> action, boolean oneAhead) {
        try {
            TabBlock workingBlock = this.currentBlock;
            if (oneAhead && workingBlock.getCurrent().findChildren().isPresent()) {
                workingBlock = workingBlock.getCurrent().findChildren().get();
            }
            do {
                this.doForAllInBlock(workingBlock, action);
                if (workingBlock.getFirst().findParent().isPresent()) {
                    workingBlock = workingBlock.getFirst().findParent().get().getContainerTabBlock();
                } else {
                    break;
                }
            } while (true);
        } catch (RuntimeException e) {
            this.scissorBoxWidth = 0;
//            TODO: Implement an actual fix for this
        }
    }

    private void doForAllInBlock(TabBlock block, Consumer<Tab<?>> action) {
        for (Tab<?> tab : block) {
            action.accept(tab);
        }
    }

    public void invokeCurrent() {
        this.currentBlock.getCurrent().doInvocation();
    }

    public void doKeyInput(int key) {
        LinkedList<TabAction> specificActions;
        if ((specificActions = this.specificKeySubscriberRegistry.get(key)) != null)
            specificActions.forEach(action -> action.invoke(key));

        this.globalKeySubscriberRegistry.values().stream()
                .flatMap(Collection::stream)
                .forEach(action -> action.invoke(key));

        this.unsubscriptionQueue.forEach(this.globalKeySubscriberRegistry::remove);
        this.unsubscriptionQueue.clear();

        if (this.defaultKeyListening && !this.wasListeningCancelled) {
            if (key == Keyboard.KEY_UP) {
                this.shiftUpTab();
            }
            else if (key == Keyboard.KEY_DOWN) {
                this.shiftDownTab();
            }
            else if (key == Keyboard.KEY_LEFT) {
                if (this.canAscendTree())
                    this.ascendTabTree();
            }
            else if (key == Keyboard.KEY_RIGHT) {
                if (this.canDescendTree())
                    this.descendTabTree();
            }
            else if (key == Keyboard.KEY_RETURN)
                this.invokeCurrent();
        }
        this.wasListeningCancelled = false;
    }

    public void translateTabs(int posX, int posY) {
        Tab<?> workingTab = this.currentBlock.getCurrent();
        while (workingTab.findParent().isPresent()) {
            workingTab = workingTab.findParent().get();
        }
        this.assignTabPositions(workingTab.getContainerTabBlock(), posX, posY);
    }

    private void handleScissorBoxing() {
        if (scissorBoxTimer.hasReach(15)) {
            final float translationRateMultiplier = 1.0f + ((Math.max(0.0f, (50.0f - Minecraft.getDebugFPS()) / 10)));
            if (this.scissorBoxState == ScissorBoxState.EXPANDING) {
                this.scissorBoxWidth = (int) Math.min(this.scissorBoxWidth + (8 * translationRateMultiplier), this.defaultTabWidth);
            }
            else if (this.scissorBoxState == ScissorBoxState.CONTRACTING) {
                this.scissorBoxWidth = (int) Math.max(this.scissorBoxWidth - (10 * translationRateMultiplier), 0);
            }
            scissorBoxTimer.reset();
        }
    }

    public void doTabRendering() {
        if (this.scissorBoxState != ScissorBoxState.STATIC) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            if (this.scissorBoxState == ScissorBoxState.EXPANDING) {
                OGLRender.prepareScissorBox(this.renderPosX - 1,
                        this.renderPosY,
                        this.currentBlock.getLast().getPosX() + this.scissorBoxWidth + 1,
                        Wrapper.getMinecraft().displayHeight - this.renderPosY);
            }
            else {
                OGLRender.prepareScissorBox(this.renderPosX - 1,
                        this.renderPosY,
                        this.currentBlock.getCurrent().getPosX() + this.defaultTabWidth + this.scissorBoxWidth + 1,
                        Wrapper.getMinecraft().displayHeight - this.renderPosY);
            }
            handleScissorBoxing();
        }
        this.doForCurrentTreeBranch(Tab::renderTabBack, this.scissorBoxState == ScissorBoxState.CONTRACTING);
        this.currentCursor.renderOn(this.currentBlock.getCurrent());

        Tab<?> workingTab = this.currentBlock.getCurrent();
        while (workingTab.findParent().isPresent()) {
            workingTab = workingTab.findParent().get();
            this.currentCursor.renderStaticOn(workingTab);
        }

        this.doForCurrentTreeBranch(Tab::renderTabFront, this.scissorBoxState == ScissorBoxState.CONTRACTING);

        if (this.scissorBoxState != ScissorBoxState.STATIC) {
            if (this.scissorBoxState == ScissorBoxState.EXPANDING) {
                if (this.previousTreeDepth < this.treeDepth && this.scissorBoxWidth == this.defaultTabWidth) {
                    this.scissorBoxState = ScissorBoxState.STATIC;
                }
            }
            else if (this.scissorBoxState == ScissorBoxState.CONTRACTING) {
                if (this.previousTreeDepth < this.treeDepth && this.scissorBoxWidth == 0) {
                    this.scissorBoxState = ScissorBoxState.STATIC;
                }
                else if (this.previousTreeDepth > this.treeDepth && this.scissorBoxWidth == 0) {
                    this.scissorBoxWidth = this.defaultTabWidth;
                    this.scissorBoxState = ScissorBoxState.STATIC;
                }
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }
    }

    public boolean isInDirectTree(Tab<?> tab) {
        Tab<?> workingTab = this.currentBlock.getCurrent();
        boolean isInTree = false;
        do {
            if (workingTab == tab)
                isInTree = true;

            if (workingTab.findParent().isPresent()) {
                workingTab = workingTab.findParent().get();
            }
            else {
                break;
            }
        } while (true);
        return isInTree;
    }

    public void enableDefaultKeyListening() {
        if (!this.defaultKeyListening) {
            this.wasListeningCancelled = true;
        }
        this.defaultKeyListening = true;
    }

    public void disableDefaultKeyListening() {
        this.defaultKeyListening = false;
    }

    public void subscribeTabActionToAll(Tab<?> tab, BiConsumer<Integer, Tab<?>> action) {
        this.globalKeySubscriberRegistry.computeIfAbsent(tab, list -> Lists.newLinkedList()).add(new TabAction(tab, action));
    }

    public void unsubscribeTabActionFromAll(Tab<?> tab) {
        this.unsubscriptionQueue.add(tab);
    }

    public void subscribeActionToKey(Tab<?> tab, int key, BiConsumer<Integer, Tab<?>> action) {
        this.specificKeySubscriberRegistry.computeIfAbsent(key, list -> Lists.newLinkedList()).add(new TabAction(tab, action));
    }

    public void unsubscribeSpecificActions(int key) {
        this.specificKeySubscriberRegistry.remove(key);
    }

    public void unsubscribeSpecificActions(Tab<?> tab) {
        for (LinkedList<TabAction> list : this.specificKeySubscriberRegistry.values()) {
            for (TabAction action : list) {
                if (action.getTab() == tab) {
                    list.remove(action);
                }
            }
        }
    }

    public boolean wasPopulated() {
        return this.wasPopulated;
    }

    public int getGuiHeight() {
        return this.totalHeight;
    }

    public TabCursor getCurrentCursor() {
        return this.currentCursor;
    }

    public boolean isDefaultKeyListening() {
        return this.defaultKeyListening;
    }

    public void resetScissorBox() {
        this.scissorBoxState = ScissorBoxState.EXPANDING;
        this.scissorBoxWidth = 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int renderPosX,
                renderPosY,
                defaultTabWidth,
                defaultTabHeight,
                horizontalTabSpacing,
                verticalTabSpacing;
        private TabCursor currentCursor;

        protected Builder() {}

        public Builder renderCoordinates(int renderPosX,  int renderPosY) {
            this.renderPosX = renderPosX;
            this.renderPosY = renderPosY;
            return this;
        }

        public Builder tabDimensions(int defaultTabWidth,  int defaultTabHeight) {
            this.defaultTabWidth = defaultTabWidth;
            this.defaultTabHeight = defaultTabHeight;
            return this;
        }

        public Builder tabSpacing(int horizontalTabSpacing,  int verticalTabSpacing) {
            this.horizontalTabSpacing = horizontalTabSpacing;
            this.verticalTabSpacing = verticalTabSpacing;
            return this;
        }

        public Builder tabCursor(TabCursor cursor) {
            this.currentCursor = cursor;
            return this;
        }

        public TabHandler build() {
            return new TabHandler(renderPosX, renderPosY, defaultTabWidth, defaultTabHeight, horizontalTabSpacing, verticalTabSpacing, currentCursor);
        }

    }

    private static class TabAction {
        private Tab<?> tab;
        private BiConsumer<Integer, Tab<?>> action;

        TabAction(Tab<?> tab, BiConsumer<Integer, Tab<?>> action) {
            this.tab = tab;
            this.action = action;
        }

        void invoke( int key) {
            action.accept(key, tab);
        }

        public Tab<?> getTab() {
            return this.tab;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof TabAction))
                return false;
            if (this.tab != ((TabAction) o).getTab())
                return false;
            return false;
        }

    }

}
