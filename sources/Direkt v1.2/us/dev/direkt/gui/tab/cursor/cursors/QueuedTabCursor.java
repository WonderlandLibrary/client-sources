package us.dev.direkt.gui.tab.cursor.cursors;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import us.dev.api.timing.Timer;
import us.dev.direkt.gui.color.ClientColors;
import us.dev.direkt.gui.tab.cursor.TabCursor;
import us.dev.direkt.gui.tab.tab.Tab;
import us.dev.direkt.util.render.OGLRender;

import java.util.Deque;

/**
 * Created by Foundry on 1/1/2016.
 */
public  class QueuedTabCursor implements TabCursor {
    private  Deque<RenderAction> renderQueue = Lists.newLinkedList();
    private float translationRateMultiplier = 1.0f,
        translationRate = 3.5f;
    private boolean slowdownDetected = false;

    private static Timer hueTimer = new Timer();
    private static float hueSwitch = 1f;
    private static boolean darken = false;
    private static int currentColor;

    @Override
    public void renderOn(Tab<?> tab) {
        if (this.renderQueue.isEmpty() || renderQueue.getFirst().getTargetTab().getPosX() != tab.getPosX() || tab.getContainerTabBlock().sizeOf() == 1) {
            this.renderQueue.clear();
            this.renderQueue.push(new RenderAction(tab, tab, State.NEW));
            this.renderQueue.getLast().render();
        }
        else if (this.renderQueue.getFirst().getTargetTab() == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getFirst()
                && tab == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getLast()
                && Keyboard.getEventKey() != Keyboard.KEY_DOWN) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.CYCLEUP));
        }
        else if (this.renderQueue.getFirst().getTargetTab() == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getLast()
                && tab == this.renderQueue.getFirst().getTargetTab().getContainerTabBlock().getFirst()
                && Keyboard.getEventKey() != Keyboard.KEY_UP) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.CYCLEDOWN));
        }
        else if (tab.getPosY() < renderQueue.getFirst().getTargetTab().getPosY()) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.UP));
        }
        else if (tab.getPosY() > renderQueue.getFirst().getTargetTab().getPosY()) {
            this.renderQueue.push(new RenderAction(this.renderQueue.getFirst().getTargetTab(), tab, State.DOWN));
        }
        this.translationRateMultiplier = 1.0f + ((Math.max(0.0f, (50.0f - Minecraft.getDebugFPS()) / 10)));
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
        currentColor = ClientColors.FADING_GREEN.getColor();
        OGLRender.drawRect(
                tab.getPosX() - 0.5f,
                tab.getPosY(),
                (tab.getPosX() + tab.getWidth() + 0.5f),
                (tab.getPosY() + tab.getHeight()),
                currentColor);
    }

    private class RenderAction {
        private Tab<?> previousTab, targetTab;
        private Timer animationTimer = new Timer();
        private State state;
        private boolean complete;
        private float animationOffset = 0;

        public RenderAction(Tab<?> previousTab,  Tab<?> targetTab,  State state) {
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

        public Tab<?> getPreviousTab() {
            return this.previousTab;
        }

        public Tab<?> getTargetTab() {
            return this.targetTab;
        }

        public void render() {
//            TODO: Add lag detection to increase animation speed
            if (this.state.equals(State.NEW)) {
                this.complete = true;
            }
            else if (this.state.equals(State.CYCLEUP)) {
                if (animationTimer.hasReach(15)) {
                    this.animationOffset = Math.max((float)-this.previousTab.getHeight(), this.animationOffset - translationRate * translationRateMultiplier);
                    this.animationTimer.reset();
                }
                OGLRender.drawRect(
                        previousTab.getPosX() - 0.5f,
                        previousTab.getPosY(),
                        (previousTab.getPosX() + previousTab.getWidth() + 0.5f),
                        (previousTab.getPosY() + previousTab.getHeight() + this.animationOffset),
                        currentColor);
                OGLRender.drawRect(
                        targetTab.getPosX() - 0.5f,
                        targetTab.getPosY() + targetTab.getHeight() + this.animationOffset,
                        (targetTab.getPosX() + targetTab.getWidth() + 0.5f),
                        (targetTab.getPosY() + targetTab.getHeight()),
                        currentColor);
                if (-this.animationOffset == this.targetTab.getHeight()) {
                    this.complete = true;
                }
            }
            else if (this.state.equals(State.CYCLEDOWN)) {
                if (animationTimer.hasReach(15)) {
                    this.animationOffset = Math.min((float)this.previousTab.getHeight(), this.animationOffset + translationRate * translationRateMultiplier);
                    this.animationTimer.reset();
                }
                OGLRender.drawRect(
                        previousTab.getPosX() - 0.5f,
                        previousTab.getPosY() + this.animationOffset,
                        (previousTab.getPosX() + previousTab.getWidth() + 0.5f),
                        (previousTab.getPosY() + previousTab.getHeight()),
                        currentColor);
                OGLRender.drawRect(
                        targetTab.getPosX() - 0.5f,
                        targetTab.getPosY(),
                        (targetTab.getPosX() + targetTab.getWidth() + 0.5f),
                        (targetTab.getPosY() + this.animationOffset),
                        currentColor);
                if (this.animationOffset == this.targetTab.getHeight()) {
                    this.complete = true;
                }
            }
            else if (this.state.equals(State.UP)) {
                if (animationTimer.hasReach(15)) {
                    this.animationOffset = Math.max((float)-this.previousTab.getHeight(), this.animationOffset - translationRate * translationRateMultiplier);
                    this.animationTimer.reset();
                }
                OGLRender.drawRect(
                        previousTab.getPosX() - 0.5f,
                        previousTab.getPosY() + this.animationOffset,
                        (previousTab.getPosX() + previousTab.getWidth() + 0.5f),
                        (previousTab.getPosY() + previousTab.getHeight() + this.animationOffset),
                        currentColor);
                if (this.previousTab.getPosY() + this.animationOffset == this.targetTab.getPosY()) {
                    this.complete = true;
                }
            }
            else if (this.state.equals(State.DOWN)) {
                if (animationTimer.hasReach(15)) {
                    this.animationOffset = Math.min((float)this.previousTab.getHeight(), this.animationOffset + translationRate * translationRateMultiplier);
                    this.animationTimer.reset();
                }
                OGLRender.drawRect(
                        previousTab.getPosX() - 0.5f,
                        previousTab.getPosY() + this.animationOffset,
                        (previousTab.getPosX() + previousTab.getWidth() + 0.5f),
                        (previousTab.getPosY() + previousTab.getHeight() + this.animationOffset),
                        currentColor);
                if (this.previousTab.getPosY() + this.animationOffset == this.targetTab.getPosY()) {
                    this.complete = true;
                }
            }
        }
    }

    private enum State {
        NEW, UP, DOWN, CYCLEUP, CYCLEDOWN
    }
}
