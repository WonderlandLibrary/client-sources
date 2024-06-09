package com.leafclient.leaf.mod.visual

import com.leafclient.leaf.event.client.ClientLoadedEvent
import com.leafclient.leaf.event.client.KeyboardEvent
import com.leafclient.leaf.event.game.render.ScreenRenderEvent
import com.leafclient.leaf.management.mod.Mod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.management.ui.Theme
import com.leafclient.leaf.ui.Themes
import com.leafclient.leaf.ui.hud.ModList
import com.leafclient.leaf.ui.hud.Watermark
import com.leafclient.leaf.ui.hud.tabgui.TabGui
import com.leafclient.leaf.utils.render.Screen
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11.*

class HUD: Mod("HUD", Category.VISUAL) {

    var theme by setting("Theme", Themes.LEAF)

    // The components
    private val components = arrayOf(
        Watermark(), ModList(), TabGui()
    )

    @Subscribe
    val onRenderOverlay = Listener<ScreenRenderEvent> { e ->
        Screen.calculateScreenScale()
        val gameScale = ScaledResolution(mc).scaleFactor
        val clientScale = Screen.screenScale

        if(mc.gameSettings.showDebugInfo)
            return@Listener

        glPushMatrix()

        glScaled(2.0 / gameScale, 2.0 / gameScale, 2.0)
        glScalef(clientScale, clientScale, 1.0F)

        components.forEach { c ->
            theme.rendererFor(c)?.let { r ->
                r.update(c)
                r.render(c)
            }
        }

        glPopMatrix()
    }

    @Subscribe
    val onKeyPress = Listener<KeyboardEvent.Press> {
        components.forEach { c ->
            theme.rendererFor(c)?.onInput(c, it.key, true)
        }
    }

    @Subscribe
    val onKeyRelease = Listener<KeyboardEvent.Release> {
        components.forEach { c ->
            theme.rendererFor(c)?.onInput(c, it.key, false)
        }
    }

    @Subscribe
    val onClientLoaded = Listener<ClientLoadedEvent> {
        Thread {
            components.forEach {
                theme.rendererFor(it) // loads the renderer async
            }
        }.run()
    }

}