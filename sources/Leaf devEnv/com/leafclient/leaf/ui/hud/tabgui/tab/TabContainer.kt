package com.leafclient.leaf.ui.hud.tabgui.tab

import com.leafclient.leaf.management.utils.label

/**
 * Represents a [Tab] instance that contains an object
 */
open class TabContainer<T: Any>(
    val value: T,
    label: String = value.label ?: value.toString()
): Tab(label)