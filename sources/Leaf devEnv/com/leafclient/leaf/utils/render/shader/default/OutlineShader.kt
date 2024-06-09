package com.leafclient.leaf.utils.render.shader.default

import com.leafclient.leaf.event.game.render.ScreenRenderEvent
import com.leafclient.leaf.extension.setupCamera
import com.leafclient.leaf.management.event.EventManager
import com.leafclient.leaf.utils.render.RenderUtils.interpolate
import com.leafclient.leaf.utils.render.RenderUtils.drawFramebuffer
import com.leafclient.leaf.utils.render.shader.Shader
import com.leafclient.leaf.utils.render.shader.ShaderProgram
import com.leafclient.leaf.utils.render.shader.framebuffer.QuickFramebuffer
import com.leafclient.leaf.utils.render.shader.uniform.uniform
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import fr.shyrogan.publisher4k.subscription.subscription
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.culling.Frustum
import org.lwjgl.opengl.GL11

/**
 * The outline shader used by the ESP/StorageESP
 */
object  OutlineShader: ShaderProgram(
        Shader.defaultVertex,
        Shader.fromFile("/assets/minecraft/shaders/fragment/outline.frag", Shader.Type.FRAGMENT)
)
{

    private val mc = Minecraft.getMinecraft()

    /**
     * The framebuffer used by this shader
     */
    private val framebuffer = QuickFramebuffer(mc.displayWidth, mc.displayHeight, true)

    /**
     * Allows you to enable/disable the shader in case if we don't need it anymore.
     */
    var isRunning = false

    /**
     * Frustum to check if the bb is inside
     */
    val frustum = Frustum()

    @Subscribe
    val subscription = subscription(Listener { e: ScreenRenderEvent ->
        if(!isRunning)
            return@Listener

        val mc = Minecraft.getMinecraft()
        val renderViewEntity = mc.renderViewEntity ?: return@Listener
        GlStateManager.pushMatrix()
        GlStateManager.pushAttrib()

        GlStateManager.enableAlpha()

        frustum.setPosition(
            interpolate(renderViewEntity.posX, renderViewEntity.prevPosX, e.partialTicks),
            interpolate(renderViewEntity.posY, renderViewEntity.prevPosY, e.partialTicks),
            interpolate(renderViewEntity.posZ, renderViewEntity.prevPosZ, e.partialTicks)
        )

        framebuffer.resizeIfRequired(mc.displayWidth, mc.displayHeight)
        framebuffer.framebufferClear()
        framebuffer.bindFramebuffer(false)

        mc.entityRenderer.setupCamera(e.partialTicks, 0)

        mc.renderManager.setRenderOutlines(true)
        EventManager.publish(RenderEvent(e.partialTicks))
        mc.renderManager.setRenderOutlines(false)

        GL11.glEnable(GL11.GL_BLEND)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
        mc.framebuffer.bindFramebuffer(true)

        pushAndUse()
            texelSize = Pair(
            1F / Minecraft.getMinecraft().displayWidth * radius,
            1F / Minecraft.getMinecraft().displayHeight * radius
        )

        mc.entityRenderer.setupOverlayRendering()
        drawFramebuffer(framebuffer)
        pop()

        GL11.glDisable(GL11.GL_BLEND)
        GL11.glDisable(GL11.GL_LINE_SMOOTH)

        GlStateManager.popMatrix()
        GlStateManager.popAttrib()
    })

    /**
     * The texture of this shader
     */
    var texture by uniform("texture", 0)

    /**
     * The radius of this radius
     */
    var radius by uniform("radius", 1F)

    /**
     * The texel of the texture
     */
    var texelSize by uniform("texelSize", Pair(
        1F / Minecraft.getMinecraft().displayWidth * radius * 4F,
        1F / Minecraft.getMinecraft().displayHeight * radius * 4F
    ))

    /**
     * The opacity of this shader
     */
    var opacity by uniform("opacity", 0.2F)

    /**
     * The filled value of this shader
     */
    var filled by uniform("filled", false)

    init {
        EventManager.register(this)
    }

    /**
     * Event published by the [OutlineShader] when its rendered.
     */
    class RenderEvent(val partialTicks: Float)

}