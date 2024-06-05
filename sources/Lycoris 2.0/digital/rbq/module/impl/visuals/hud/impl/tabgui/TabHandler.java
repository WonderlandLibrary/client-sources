/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.block.TabBlock;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.cursor.TabCursor;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.render.RenderUtils;

public class TabHandler {
    protected final Minecraft mc = Minecraft.getMinecraft();
    private final Map<Tab<?>, LinkedList<TabAction>> globalKeySubscriberRegistry = Maps.newHashMap();
    private final Map<Integer, LinkedList<TabAction>> specificKeySubscriberRegistry = Maps.newHashMap();
    private final Queue<Tab<?>> unsubscriptionQueue = Lists.newLinkedList();
    private final int renderPosX;
    private final int renderPosY;
    private final int defaultTabWidth;
    private final int defaultTabHeight;
    private final int horizontalTabSpacing;
    private final int verticalTabSpacing;
    private final Stopwatch scissorBoxStopwatch = new Stopwatch();
    private final TabCursor currentCursor;
    private boolean defaultKeyListening = true;
    private boolean wasListeningCancelled;
    private int scissorBoxWidth;
    private int totalHeight;
    private int treeDepth;
    private int previousTreeDepth;
    private ScissorBoxState scissorBoxState = ScissorBoxState.EXPANDING;
    private TabBlock currentBlock;

    private TabHandler(int renderPosX, int renderPosY, int defaultTabWidth, int defaultTabHeight, int horizontalTabSpacing, int verticalTabSpacing, TabCursor currentCursor) {
        this.renderPosX = renderPosX;
        this.renderPosY = renderPosY;
        this.defaultTabWidth = defaultTabWidth;
        this.defaultTabHeight = defaultTabHeight;
        this.horizontalTabSpacing = horizontalTabSpacing;
        this.verticalTabSpacing = verticalTabSpacing;
        this.currentCursor = currentCursor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void ascendTabTree() {
        this.currentBlock.restartIteration();
        this.currentBlock = this.currentBlock.getCurrent().findParent().get().getContainerTabBlock();
        this.scissorBoxState = ScissorBoxState.CONTRACTING;
        this.previousTreeDepth = this.treeDepth--;
    }

    public void descendTabTree() {
        this.currentBlock = this.currentBlock.getCurrent().findChildren().get();
        this.scissorBoxState = ScissorBoxState.EXPANDING;
        if (this.scissorBoxWidth > 0) {
            this.scissorBoxWidth = 0;
        }
        this.previousTreeDepth = this.treeDepth++;
    }

    public void shiftUpTab() {
        this.currentBlock.cycleToPrevious();
    }

    public void shiftDownTab() {
        this.currentBlock.cycleToNext();
    }

    public boolean canAscendTree() {
        return this.currentBlock.getCurrent().findParent().isPresent() && this.scissorBoxWidth >= this.defaultTabWidth;
    }

    public boolean canDescendTree() {
        return this.currentBlock.getCurrent().findChildren().isPresent() && this.currentBlock.getCurrent().findChildren().get().sizeOf() > 0;
    }

    public Tab<?> getCurrentTab() {
        return this.currentBlock.getCurrent();
    }

    public void setCurrentTabs(Collection<? extends Tab> tabs) {
        Optional<Object> found = Optional.empty();
        Iterator<Tab<?>> iterator = (Iterator<Tab<?>>) tabs.iterator();
        if (iterator.hasNext()) {
            Tab<?> tab1 = iterator.next();
            found = Optional.of(tab1);
        }
        this.currentBlock = ((Tab)found.get()).getContainerTabBlock();
        tabs.forEach(this.currentBlock::appendTab);
        this.assignTabPositions(this.currentBlock, this.renderPosX, this.renderPosY);
        this.currentBlock.forEach(tab -> this.totalHeight += tab.getHeight());
    }

    private void assignTabPositions(TabBlock block, int startPosX, int startPosY) {
        if (block.sizeOf() == 0) {
            return;
        }
        Iterator<TabBlock.DoublyLinkedTab> it = block.linkedIterator();
        while (it.hasNext()) {
            TabBlock.DoublyLinkedTab tab = it.next();
            Optional<Tab<?>> parent = tab.getTab().findParent();
            int width = Math.max(this.defaultTabWidth, this.getWidestOf(block));
            if (tab.getTab() == block.getFirst()) {
                tab.getTab().setDimensionAndPos(parent.map(value -> value.getPosX() + value.getWidth() + this.horizontalTabSpacing).orElse(startPosX), parent.map(Tab::getPosY).orElse(startPosY), width, this.defaultTabHeight);
            } else {
                tab.getTab().setDimensionAndPos(parent.map(value -> value.getPosX() + value.getWidth() + this.horizontalTabSpacing).orElse(startPosX), tab.previous.getTab().getPosY() + tab.previous.getTab().getHeight() + this.verticalTabSpacing, width, this.defaultTabHeight);
            }
            if (!tab.getTab().findChildren().isPresent()) continue;
            this.assignTabPositions(tab.getTab().findChildren().get(), this.renderPosX, this.renderPosY);
        }
    }

    public int getWidestOf(TabBlock block) {
        int widestTabWidth = 0;
        Iterator<TabBlock.DoublyLinkedTab> it = block.linkedIterator();
        while (it.hasNext()) {
            TabBlock.DoublyLinkedTab tab = it.next();
            int tabWidth = tab.getTab().getTabWidth();
            if (tabWidth <= widestTabWidth) continue;
            widestTabWidth = tabWidth;
        }
        return widestTabWidth;
    }

    private void doForCurrentTreeBranch(Consumer<Tab<?>> action, boolean oneAhead) {
        try {
            TabBlock workingBlock = this.currentBlock;
            if (oneAhead && workingBlock.getCurrent().findChildren().isPresent()) {
                workingBlock = workingBlock.getCurrent().findChildren().get();
            }
            while (true) {
                this.doForAllInBlock(workingBlock, action);
                if (workingBlock.getFirst().findParent().isPresent()) {
                    workingBlock = workingBlock.getFirst().findParent().get().getContainerTabBlock();
                    continue;
                }
                break;
            }
        }
        catch (RuntimeException runtimeException) {
            // empty catch block
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
        LinkedList<TabAction> specificActions = this.specificKeySubscriberRegistry.get(key);
        if (specificActions != null) {
            int specificActionsSize = specificActions.size();
            for (int i = 0; i < specificActionsSize; ++i) {
                TabAction tabAction = specificActions.get(i);
                tabAction.invoke(key);
            }
        }
        Collection<LinkedList<TabAction>> tabActionsRef = this.globalKeySubscriberRegistry.values();
        for (LinkedList<TabAction> linkedList : tabActionsRef) {
            int tabActionsSize = linkedList.size();
            for (int i = 0; i < tabActionsSize; ++i) {
                linkedList.get(i).invoke(key);
            }
        }
        for (Tab tab : this.unsubscriptionQueue) {
            this.globalKeySubscriberRegistry.remove(tab);
        }
        this.unsubscriptionQueue.clear();
        if (this.defaultKeyListening && !this.wasListeningCancelled) {
            if (key == 200) {
                this.shiftUpTab();
            } else if (key == 208) {
                this.shiftDownTab();
            } else if (key == 203) {
                if (this.canAscendTree()) {
                    this.ascendTabTree();
                }
            } else if (key == 205) {
                if (this.canDescendTree()) {
                    this.descendTabTree();
                }
            } else if (key == 28) {
                this.invokeCurrent();
            }
        }
        this.wasListeningCancelled = false;
    }

    private void handleScissorBoxing() {
        if (this.scissorBoxStopwatch.elapsed(15L)) {
            float translationRateMultiplier = 1.0f + Math.max(0.0f, (50.0f - (float)Minecraft.getDebugFPS()) / 10.0f);
            if (this.scissorBoxState == ScissorBoxState.EXPANDING) {
                int currentBlockWidth = Math.max(this.defaultTabWidth, this.getWidestOf(this.currentBlock));
                this.scissorBoxWidth = (int)Math.min((float)this.scissorBoxWidth + 8.0f * translationRateMultiplier, (float)currentBlockWidth);
            } else if (this.scissorBoxState == ScissorBoxState.CONTRACTING) {
                this.scissorBoxWidth = (int)Math.max((float)this.scissorBoxWidth - 10.0f * translationRateMultiplier, 0.0f);
            }
            this.scissorBoxStopwatch.reset();
        }
    }

    public void doTabRendering() {
        int currentBlockWidth = Math.max(this.defaultTabWidth, this.getWidestOf(this.currentBlock));
        if (this.scissorBoxState != ScissorBoxState.STATIC) {
            GL11.glEnable((int)3089);
            if (this.scissorBoxState == ScissorBoxState.EXPANDING) {
                RenderUtils.prepareScissorBox(this.renderPosX - 1, this.renderPosY, this.currentBlock.getLast().getPosX() + this.scissorBoxWidth + 1, this.mc.displayHeight - this.renderPosY);
            } else {
                RenderUtils.prepareScissorBox(this.renderPosX - 1, this.renderPosY, this.currentBlock.getCurrent().getPosX() + currentBlockWidth + this.scissorBoxWidth + 1, this.mc.displayHeight - this.renderPosY);
            }
            this.handleScissorBoxing();
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
                if (this.previousTreeDepth < this.treeDepth && this.scissorBoxWidth == currentBlockWidth) {
                    this.scissorBoxState = ScissorBoxState.STATIC;
                }
            } else if (this.scissorBoxState == ScissorBoxState.CONTRACTING) {
                if (this.previousTreeDepth < this.treeDepth && this.scissorBoxWidth == 0) {
                    this.scissorBoxState = ScissorBoxState.STATIC;
                } else if (this.previousTreeDepth > this.treeDepth && this.scissorBoxWidth == 0) {
                    this.scissorBoxWidth = currentBlockWidth;
                    this.scissorBoxState = ScissorBoxState.STATIC;
                }
            }
            GL11.glDisable((int)3089);
        }
    }

    public boolean isInDirectTree(Tab<?> tab) {
        Tab<?> workingTab = this.currentBlock.getCurrent();
        boolean isInTree = false;
        while (true) {
            if (workingTab == tab) {
                isInTree = true;
            }
            if (!workingTab.findParent().isPresent()) break;
            workingTab = workingTab.findParent().get();
        }
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

    public void subscribeActionToKey(Tab<?> tab, int key, BiConsumer<Integer, Tab<?>> action) {
        this.specificKeySubscriberRegistry.computeIfAbsent(key, list -> Lists.newLinkedList()).add(new TabAction(tab, action));
    }

    public void unsubscribeSpecificActions(Tab<?> tab) {
        for (LinkedList<TabAction> list : this.specificKeySubscriberRegistry.values()) {
            int listSize = list.size();
            for (int i = 0; i < listSize; ++i) {
                TabAction action = list.get(i);
                if (action.getTab() != tab) continue;
                list.remove(action);
            }
        }
    }

    private static class TabAction {
        private final Tab<?> tab;
        private final BiConsumer<Integer, Tab<?>> action;

        TabAction(Tab<?> tab, BiConsumer<Integer, Tab<?>> action) {
            this.tab = tab;
            this.action = action;
        }

        void invoke(int key) {
            this.action.accept(key, this.tab);
        }

        public Tab<?> getTab() {
            return this.tab;
        }
    }

    public static class Builder {
        private int renderPosX;
        private int renderPosY;
        private int defaultTabWidth;
        private int defaultTabHeight;
        private int horizontalTabSpacing;
        private int verticalTabSpacing;
        private TabCursor currentCursor;

        protected Builder() {
        }

        public Builder renderCoordinates(int renderPosX, int renderPosY) {
            this.renderPosX = renderPosX;
            this.renderPosY = renderPosY;
            return this;
        }

        public Builder tabDimensions(int defaultTabWidth, int defaultTabHeight) {
            this.defaultTabWidth = defaultTabWidth;
            this.defaultTabHeight = defaultTabHeight;
            return this;
        }

        public Builder tabSpacing(int horizontalTabSpacing, int verticalTabSpacing) {
            this.horizontalTabSpacing = horizontalTabSpacing;
            this.verticalTabSpacing = verticalTabSpacing;
            return this;
        }

        public Builder tabCursor(TabCursor cursor) {
            this.currentCursor = cursor;
            return this;
        }

        public TabHandler build() {
            return new TabHandler(this.renderPosX, this.renderPosY, this.defaultTabWidth, this.defaultTabHeight, this.horizontalTabSpacing, this.verticalTabSpacing, this.currentCursor);
        }
    }

    private static enum ScissorBoxState {
        EXPANDING,
        CONTRACTING,
        STATIC;

    }
}

