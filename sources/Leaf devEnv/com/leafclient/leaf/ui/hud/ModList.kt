package com.leafclient.leaf.ui.hud

import com.leafclient.leaf.management.mod.Mod
import com.leafclient.leaf.management.ui.Component
import com.leafclient.leaf.mod.ModManager

class ModList: Component() {

    /**
     * Returns every available mods
     */
    val mods: List<Mod>
        get() = ModManager.mods

}