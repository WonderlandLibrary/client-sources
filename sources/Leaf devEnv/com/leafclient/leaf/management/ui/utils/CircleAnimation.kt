package com.leafclient.leaf.management.ui.utils

import com.leafclient.leaf.utils.render.RenderUtils.render2D
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


class CircleAnimation(
    val x: Float, val y: Float, val radius: Float,
    duration: Long, colorFadeDuration: Long = duration + 700L,
    transition: Transition = Transitions.LINEAR
) {

    private val expansionAnimation = Animation(duration, transition)
        .setForward(true)
        .setRunning(true)

    private val colorFadeAnimation = Animation(colorFadeDuration, transition)
        .setForward(false)
        .reset()
        .setRunning(true)
        .apply { this.transition = Transitions.LINEAR }

    val isOver: Boolean
        get() = colorFadeAnimation.isOver && expansionAnimation.isOver

    /**
     * Draws the circle animation
     */
    fun draw(color: Color) {
        render2D {
            val animation = expansionAnimation.value.toFloat()
            val animationColor = colorFadeAnimation.value

            val x = x * 2F
            val y = y * 2F

            glScalef(0.5F, 0.5F, 1.0F)
            glDisable(GL_ALPHA_TEST)
            glColor4d(
                color.red / 255.0,
                color.green / 255.0,
                color.blue / 255.0,
                color.alpha / 255.0 * animationColor
            )
            glBegin(GL_POLYGON)
            for(i in 0..360) {
                val angle = i * PI.toFloat() / 180F
                glVertex2f(
                    x + sin(angle) * radius * 2F * animation,
                    y + cos(angle) * radius * 2F * animation
                )
            }
            glEnd()
            glScalef(2F, 2F, 1.0F)
        }
    }

}