package com.leafclient.leaf.utils.render

import org.lwjgl.opengl.Display
import kotlin.math.ceil

/**
 * An utility class used to know at which size the client should be rendered, allowing
 * us to keep a good size when rendering the user interface.
 */
object Screen {

    private const val DEFAULT_WIDTH = 1920F
    private const val DEFAULT_HEIGHT = 1080F

    val width: Float
        get() = Display.getWidth() / screenScale / 2F

    val height: Float
        get() = Display.getHeight() / screenScale / 2F

    var screenScale: Float = 0.0F
        private set

    /**
     * Calculates a good screen size for rendering
     */
    fun calculateScreenScale() {
        val currentWidth = Display.getWidth().toFloat()
        val currentHeight = Display.getHeight().toFloat()

        screenScale = ceil(
            (if(currentWidth > currentHeight)
                currentHeight / DEFAULT_HEIGHT
            else
                currentWidth / DEFAULT_WIDTH) * 10.0F
        ) / 10.0F
    }

}