package com.leafclient.leaf.management.ui

import com.leafclient.leaf.utils.math.Area

/**
 * Represents a component of the user interface
 * TODO: Area, positioning..
 */
abstract class Component {

    /**
     * Returns the area of this [Component]
     */
    val area = Area()

}