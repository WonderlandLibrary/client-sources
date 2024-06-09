package com.leafclient.leaf.management.mod.category

import com.leafclient.leaf.management.utils.Labelable
import java.awt.Color

enum class Category(override val label: String, val color: Color): Labelable {

    FIGHT("Fight", Color(200, 40, 0)),
    VISUAL("Visual", Color(220, 220, 40)),
    MOVEMENT("Movement", Color(40, 120, 250)),
    WORLD("World", Color(40, 160, 40))

}