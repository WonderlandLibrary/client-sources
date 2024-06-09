package com.leafclient.leaf.ui.theme.tabgui.selector

import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.management.ui.utils.Animation
import com.leafclient.leaf.management.ui.utils.CircleAnimation
import com.leafclient.leaf.management.ui.utils.Transitions
import com.leafclient.leaf.ui.Themes
import com.leafclient.leaf.ui.hud.tabgui.factory.TabFactories
import com.leafclient.leaf.ui.hud.tabgui.panel.TabPanel
import com.leafclient.leaf.ui.hud.tabgui.selector.TabSelector
import com.leafclient.leaf.ui.hud.tabgui.tab.TabContainer
import com.leafclient.leaf.utils.client.keyboard.Key
import com.leafclient.leaf.utils.math.Area
import com.leafclient.leaf.utils.render.RenderUtils.scissor
import com.leafclient.leaf.utils.render.RenderUtils.rect
import net.minecraft.util.math.Vec2f
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.sqrt

class LeafTabSelector: Renderer<TabSelector> {

    private val animation = Animation(50L, Transitions.CUBIC).setForward(true).setProgression(1.0)
    private var lastKnownArea = Area()
    private val circles = mutableListOf<CircleAnimation>()

    override fun render(c: TabSelector) {
        //<editor-fold desc="Area calculation">
        val progression = animation.value.toFloat()
        val diffX = (c.area.x - lastKnownArea.x) * progression
        val diffY = (c.area.y - lastKnownArea.y) * progression
        val diffHeight = (c.area.height - lastKnownArea.height) * progression

        val selectorArea = Area(
            lastKnownArea.x + diffX, lastKnownArea.y + diffY,
            c.area.width, lastKnownArea.height + diffHeight
        )
        //</editor-fold>

        scissor(selectorArea) {
            rect(selectorArea, Color(121, 186, 84))

            circles.removeIf {
                it.draw(Color(255, 255, 255, 100))

                it.isOver
            }
        }
    }

    override fun update(c: TabSelector) {
        if(lastKnownArea.width == 0F) {
            lastKnownArea = c.area.copy()
        }
        animation.setForward(true)
        if(lastKnownArea != c.area) {
            animation.setRunning(true)
            if(animation.isOver) {
                animation.reset()
                animation.setRunning(false)
                lastKnownArea = c.area.copy()
            }
        } else {
            if(animation.isOver)
                animation.setRunning(false)
        }
    }

    override fun onInput(c: TabSelector, e: Key, pressed: Boolean): Boolean {
        if(!pressed)
            return false

        val selectedTab = c.selectedTab
        val startPosition = Vec2f(selectedTab.area.x + 5F, selectedTab.area.y + selectedTab.area.height / 2F)
        val diffX = startPosition.x - selectedTab.area.x + c.panel.area.width
        val diffY = startPosition.y - selectedTab.area.y
        val radius = sqrt(diffX * diffX + diffY * diffY)
        if(e.keyCode == Keyboard.KEY_RETURN || e.keyCode == Keyboard.KEY_RIGHT)
            circles.add(CircleAnimation(startPosition.x, startPosition.y, radius, 250))

        val cancelled = Themes.LEAF.rendererFor(c.selectedTab)?.onInput(c.selectedTab, e, pressed) ?: false
        if(!cancelled) {
            when(e.keyCode) {
                Keyboard.KEY_UP -> c.move(TabSelector.Movement.UP)
                Keyboard.KEY_DOWN -> c.move(TabSelector.Movement.DOWN)
                Keyboard.KEY_RETURN, Keyboard.KEY_RIGHT -> {
                    if(c.selectedTab !is TabContainer<*>)
                        return false

                    c.panel.tabGui?.open(TabPanel(
                        TabFactories
                            .explore((c.selectedTab as TabContainer<*>).value)
                            .map {
                                TabFactories.tabFor(it)
                            }
                    ))
                }
                Keyboard.KEY_LEFT -> c.panel.tabGui?.close(c.panel)
            }
        }
        return false
    }

}