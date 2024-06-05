/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.lwjgl.input.Keyboard
 */
package digital.rbq.module.impl.visuals.hud.impl.tabgui.cursor.cursors;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Deque;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import digital.rbq.core.Autumn;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.cursor.TabCursor;
import digital.rbq.module.impl.visuals.hud.impl.tabgui.tab.Tab;
import digital.rbq.utils.Stopwatch;
import digital.rbq.utils.render.Palette;

public class QueuedTabCursor
implements TabCursor {
    private final Deque<RenderAction> renderQueue = Lists.newLinkedList();
    private float translationRateMultiplier = 1.0f;
    private final float translationRate = 3.5f;
    private static int currentColor;

    @Override
    public void renderOn(Tab<?> tab) {
        if (this.renderQueue.isEmpty() || this.renderQueue.getFirst().getTargetTab().getPosX() != tab.getPosX() || tab.getContainerTabBlock().sizeOf() == 1) {
            this.renderQueue.clear();
            this.renderQueue.push(new RenderAction(tab, tab, State.NEW));
            this.renderQueue.getLast().render();
        } else if (this.renderQueue.getFirst().getTargetTab() == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getFirst() && tab == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getLast() && Keyboard.getEventKey() != 208) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.CYCLEUP));
        } else if (this.renderQueue.getFirst().getTargetTab() == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getLast() && tab == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getFirst() && Keyboard.getEventKey() != 200) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.CYCLEDOWN));
        } else if (tab.getPosY() < this.renderQueue.getFirst().getTargetTab().getPosY()) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.UP));
        } else if (tab.getPosY() > this.renderQueue.getFirst().getTargetTab().getPosY()) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.DOWN));
        }
        this.translationRateMultiplier = 1.0f + Math.max(0.0f, (50.0f - (float)Minecraft.getDebugFPS()) / 10.0f);
        if (this.renderQueue.getLast().isComplete() && this.renderQueue.size() > 1) {
            this.renderQueue.removeLast();
        }
        if (this.renderQueue.getFirst().isComplete()) {
            this.renderStaticOn(tab);
        }
        if (!this.renderQueue.getLast().isComplete()) {
            this.renderQueue.getLast().render();
        }
    }

    @Override
    public void renderStaticOn(Tab<?> tab) {
        Color c = (Color)Autumn.MANAGER_REGISTRY.moduleManager.getModuleOrNull(HUDMod.class).color.getValue();
        currentColor = Palette.fade(c).getRGB();
        Gui.drawRect((float)tab.getPosX() + 0.5f, (float)tab.getPosY() + 0.5f, (float)(tab.getPosX() + tab.getWidth()) - 0.5f, (float)(tab.getPosY() + tab.getHeight()) - 0.5f, currentColor);
    }

    private static enum State {
        NEW,
        UP,
        DOWN,
        CYCLEUP,
        CYCLEDOWN;

    }

    private class RenderAction {
        private final Tab<?> previousTab;
        private final Tab<?> targetTab;
        private final Stopwatch animationStopwatch = new Stopwatch();
        private final State state;
        private boolean complete;
        private float animationOffset = 0.0f;

        public RenderAction(Tab<?> previousTab, Tab<?> targetTab, State state) {
            this.previousTab = previousTab;
            this.targetTab = targetTab;
            this.state = state;
        }

        public State getState() {
            return this.state;
        }

        public boolean isComplete() {
            return this.complete;
        }

        public Tab<?> getTargetTab() {
            return this.targetTab;
        }

        public void render() {
            if (this.state.equals((Object)State.NEW)) {
                this.complete = true;
            } else if (this.state.equals((Object)State.CYCLEUP)) {
                if (this.animationStopwatch.elapsed(15L)) {
                    this.animationOffset = Math.max((float)(-this.previousTab.getHeight()), this.animationOffset - 3.5f * QueuedTabCursor.this.translationRateMultiplier);
                    this.animationStopwatch.reset();
                }
                Gui.drawRect((float)this.previousTab.getPosX() + 0.5f, (float)this.previousTab.getPosY() + 0.5f, (float)(this.previousTab.getPosX() + this.previousTab.getWidth()) - 0.5f, (float)(this.previousTab.getPosY() + this.previousTab.getHeight()) + this.animationOffset - 0.5f, currentColor);
                Gui.drawRect((float)this.targetTab.getPosX() + 0.5f, (float)(this.targetTab.getPosY() + this.targetTab.getHeight()) + this.animationOffset + 0.5f, (float)(this.targetTab.getPosX() + this.targetTab.getWidth()) - 0.5f, (float)(this.targetTab.getPosY() + this.targetTab.getHeight()) - 0.5f, currentColor);
                if (-this.animationOffset == (float)this.targetTab.getHeight()) {
                    this.complete = true;
                }
            } else if (this.state.equals((Object)State.CYCLEDOWN)) {
                if (this.animationStopwatch.elapsed(15L)) {
                    this.animationOffset = Math.min((float)this.previousTab.getHeight(), this.animationOffset + 3.5f * QueuedTabCursor.this.translationRateMultiplier);
                    this.animationStopwatch.reset();
                }
                Gui.drawRect((float)this.previousTab.getPosX() + 0.5f, (float)this.previousTab.getPosY() + this.animationOffset + 0.5f, (float)(this.previousTab.getPosX() + this.previousTab.getWidth()) - 0.5f, (float)(this.previousTab.getPosY() + this.previousTab.getHeight()) - 0.5f, currentColor);
                Gui.drawRect((float)this.targetTab.getPosX() + 0.5f, (float)this.targetTab.getPosY() + 0.5f, (float)(this.targetTab.getPosX() + this.targetTab.getWidth()) - 0.5f, (float)this.targetTab.getPosY() + this.animationOffset - 0.5f, currentColor);
                if (this.animationOffset == (float)this.targetTab.getHeight()) {
                    this.complete = true;
                }
            } else if (this.state.equals((Object)State.UP)) {
                if (this.animationStopwatch.elapsed(15L)) {
                    this.animationOffset = Math.max((float)(-this.previousTab.getHeight()), this.animationOffset - 3.5f * QueuedTabCursor.this.translationRateMultiplier);
                    this.animationStopwatch.reset();
                }
                Gui.drawRect((float)this.previousTab.getPosX() + 0.5f, (float)this.previousTab.getPosY() + this.animationOffset + 0.5f, (float)(this.previousTab.getPosX() + this.previousTab.getWidth()) - 0.5f, (float)(this.previousTab.getPosY() + this.previousTab.getHeight()) + this.animationOffset - 0.5f, currentColor);
                if ((float)this.previousTab.getPosY() + this.animationOffset == (float)this.targetTab.getPosY()) {
                    this.complete = true;
                }
            } else if (this.state.equals((Object)State.DOWN)) {
                if (this.animationStopwatch.elapsed(15L)) {
                    this.animationOffset = Math.min((float)this.previousTab.getHeight(), this.animationOffset + 3.5f * QueuedTabCursor.this.translationRateMultiplier);
                    this.animationStopwatch.reset();
                }
                Gui.drawRect((float)this.previousTab.getPosX() + 0.5f, (float)this.previousTab.getPosY() + this.animationOffset + 0.5f, (float)(this.previousTab.getPosX() + this.previousTab.getWidth()) - 0.5f, (float)(this.previousTab.getPosY() + this.previousTab.getHeight()) + this.animationOffset - 0.5f, currentColor);
                if ((float)this.previousTab.getPosY() + this.animationOffset == (float)this.targetTab.getPosY()) {
                    this.complete = true;
                }
            }
        }
    }
}

