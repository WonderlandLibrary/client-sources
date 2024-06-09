package com.leafclient.leaf.management.ui

import com.leafclient.leaf.management.utils.Labelable
import java.lang.RuntimeException
import java.util.*

/**
 * Represents a [Theme]
 */
class Theme(override val label: String, private val rendererMap: RendererMap): Labelable {

    /**
     * Creates an instance of [Theme] using specified pairs
     */
    constructor(
        label: String, vararg pairs: Pair<Class<out Component>, RendererProvider>
    ): this(label, mapOf(*pairs))

    private val rendererCache = WeakHashMap<Component, Renderer<*>>()

    /**
     * Returns a [Renderer] instance for [component]
     */
    @Suppress("unchecked_cast")
    fun <T: Component> rendererFor(component: T)
        = rendererCache.getOrPut(component) {
        rendererMap[component::class.java]?.invoke()
    } as Renderer<T>?

}

/**
 * A type alias used to recognized map that provides renderer
 */
typealias RendererProvider = () -> Renderer<*>

/**
 * A type alias used to recognized map that provides renderer
 */
typealias RendererMap = Map<Class<out Component>, RendererProvider>