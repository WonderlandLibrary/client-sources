package com.leafclient.leaf.ui.theme.tabgui.tab

import com.leafclient.leaf.ui.hud.tabgui.tab.Tab
import com.leafclient.leaf.utils.client.keyboard.Key
import org.lwjgl.input.Keyboard

open class LeafTabFocusable<T: Tab>: LeafTab<T>() {

    var isFocused = false

    override fun onInput(c: T, e: Key, pressed: Boolean): Boolean {
        if(e.keyCode == Keyboard.KEY_RETURN) {
            isFocused = !isFocused
        }
        return isFocused
    }

}