package com.leafclient.leaf.ui.theme.tabgui.tab

import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.ui.hud.tabgui.tab.specific.TabToggleable
import com.leafclient.leaf.utils.client.keyboard.Key
import org.lwjgl.input.Keyboard
import java.awt.Color

class LeafTabToggleable: LeafTab<TabToggleable<*>>() {

    override fun render(c: TabToggleable<*>) {
        val font = if(c.isRunning) font_bold else font

        font.drawStringWithShadow(
            c.label,
            c.area.x + TEXT_MARGIN + TEXT_ANIMATION_MARGIN * textAnimation.value.toFloat(),
            c.area.y,
            Color.WHITE.rgb
        )
    }

    override fun onInput(c: TabToggleable<*>, e: Key, pressed: Boolean): Boolean {
        if(e.keyCode == Keyboard.KEY_RETURN) {
            c.value.toggle()
            return true
        }
        return false
    }

}