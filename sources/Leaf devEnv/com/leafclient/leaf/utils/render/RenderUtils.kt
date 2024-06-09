package com.leafclient.leaf.utils.render

import com.leafclient.leaf.utils.math.Area
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.shader.Framebuffer
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import kotlin.math.roundToInt


object RenderUtils {

    private val constants = intArrayOf(GL_ALPHA_TEST, GL_BLEND, GL_DEPTH_TEST, GL_LINE_SMOOTH, GL_POINT_SMOOTH, GL_TEXTURE_2D)

    private val values = BooleanArray(constants.size)
    private val color = BufferUtils.createFloatBuffer(16)

    /**
     * Prepares a 2d render by modifying few gl [constants] and storing them
     */
    fun render2D(render: () -> Unit) {
        for (i in values.indices) {
            values[i] = glGetBoolean(constants[i])
        }
        glGetFloat(GL_CURRENT_COLOR, color)

        glPushMatrix()
        glEnable(GL_BLEND)
        glDisable(GL_TEXTURE_2D)
        glEnable(GL_LINE_SMOOTH)
        glEnable(GL_POINT_SMOOTH)
        glEnable(GL_ALPHA_TEST)

        render()

        glPopMatrix()

        glColor4f(color.get(0), color.get(1), color.get(2), color.get(3))
        for (i in values.indices.reversed()) {
            val value = values[i]
            if (value) {
                glEnable(constants[i])
            } else {
                glDisable(constants[i])
            }
        }
    }

    /**
     * Draws [area] rectangle
     */
    fun rect(area: Area, color: Color) {
        render2D {
            glColor4f(color.red / 255F, color.green / 255F, color.blue / 255F, color.alpha / 255F)
            glBegin(GL_QUADS)
            glVertex2d(area.x.toDouble(), area.y.toDouble())
            glVertex2d(area.x.toDouble(), area.y.toDouble() + area.height.toDouble())
            glVertex2d(area.x.toDouble() + area.width.toDouble(), area.y.toDouble() + area.height.toDouble())
            glVertex2d(area.x.toDouble() + area.width.toDouble(), area.y.toDouble())
            glEnd()
        }
    }


    /**
     * Draws the [framebuffer] onto the screen.
     */
    fun drawFramebuffer(framebuffer: Framebuffer) {
        val scaledResolution = ScaledResolution(Minecraft.getMinecraft())
        glBindTexture(GL_TEXTURE_2D, framebuffer.framebufferTexture)
        glBegin(GL_QUADS)
        glTexCoord2d(0.0, 1.0)
        glVertex2d(0.0, 0.0)
        glTexCoord2d(0.0, 0.0)
        glVertex2d(0.0, scaledResolution.scaledHeight.toDouble())
        glTexCoord2d(1.0, 0.0)
        glVertex2d(scaledResolution.scaledWidth.toDouble(), scaledResolution.scaledHeight.toDouble())
        glTexCoord2d(1.0, 1.0)
        glVertex2d(scaledResolution.scaledWidth.toDouble(), 0.0)
        glEnd()
    }

    /**
     * Scissors [render]
     */
    fun scissor(area: Area, stopsScissoring: Boolean = true, render: () -> Unit)
        = scissor(area.x, area.y, area.width, area.height, stopsScissoring, render)

    /**
     * Scissors [render]
     */
    fun scissor(x: Float, y: Float, width: Float, height: Float, stopsScissoring: Boolean = true, render: () -> Unit) {
        val scaleFactor = 2.0F * Screen.screenScale

        glEnable(GL_SCISSOR_TEST)
        glScissor(
            (x * scaleFactor).roundToInt(),
            (Minecraft.getMinecraft().displayHeight - (y + height) * scaleFactor).roundToInt(),
            (width * scaleFactor).roundToInt(),
            (height * scaleFactor).roundToInt()
        )
        render()
        if(stopsScissoring)
            glDisable(GL_SCISSOR_TEST)
    }

    /**
     * Interpolates the rendering
     */
    fun interpolate(now: Double, then: Double, ticks: Float): Double {
        return then + (now - then) * ticks
    }

}