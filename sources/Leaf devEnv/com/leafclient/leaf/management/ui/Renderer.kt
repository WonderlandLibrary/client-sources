package com.leafclient.leaf.management.ui

import com.leafclient.leaf.utils.client.keyboard.Key

interface Renderer<T: Component> {

    /**
     * Method invoked by the theme when the [Renderer] needs to be rendered
     */
    fun render(c: T)

    /**
     * Updates the component (dimensions...), is called before rendering
     */
    fun update(c: T) {}

    /**
     * Event invoked when [e] is pressed/released
     */
    fun onInput(c: T, e: Key, pressed: Boolean): Boolean = false

}