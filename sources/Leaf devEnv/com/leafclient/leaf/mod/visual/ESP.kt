package com.leafclient.leaf.mod.visual

import com.leafclient.leaf.event.game.entity.EntityTeamColorEvent
import com.leafclient.leaf.extension.isBot
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.asSetting
import com.leafclient.leaf.management.setting.contraint.bound
import com.leafclient.leaf.management.setting.contraint.increment
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.isFriendly
import com.leafclient.leaf.utils.isHostile
import com.leafclient.leaf.utils.isPassive
import com.leafclient.leaf.utils.render.shader.default.OutlineShader
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import java.awt.Color

@Suppress("deprecation")
class ESP: ToggleableMod("ESP", Category.VISUAL) {

    private val radius by setting("Radius", "The outline radius", 1.0F) {
        bound(1.0F, 2.0F)
        increment(0.5F)
    }
    private val filled by setting("Filled", "Fills the inside of entities", false)
    private val opacity by setting("Opacity", "Opacity of the filling", 0.2F) {
        bound(0.0F, 1.0F)
        increment(0.2F)
    }

    private var players by setting("Players", true)
    private var playersColor by ::players.asSetting
        .setting("Color", Color(200, 0, 0))
    private var playersInvisible by ::players.asSetting
        .setting("Invisible", true)
    private var playersBot by ::players.asSetting
        .setting("Bot", false)

    private var hostiles by setting("Hostiles", true)
    private var hostilesColor by ::hostiles.asSetting
        .setting("Color", Color(200, 200, 0))
    private var hostilesInvisible by ::hostiles.asSetting
        .setting("Invisible", true)

    private var passives by setting("Passives", true)
    private var passivesColor by ::passives.asSetting
        .setting("Color", Color(0, 200, 0))
    private var passivesInvisible by ::passives.asSetting
        .setting("Invisible", true)

    private var friendlies by setting("Friendlies", true)
    private var friendliesColor by ::friendlies.asSetting
        .setting("Color", Color(0, 200, 200))

    private var items by setting("Items", true)
    private var itemsColor by ::items.asSetting
        .setting("Color", Color(180, 150, 180))


    /**
     * Draws the entities onto the screen when the [OutlineShader]
     * is drawn.
     */
    @Subscribe
    val onRenderScreen = Listener { e: OutlineShader.RenderEvent ->
        OutlineShader.radius = radius
        OutlineShader.opacity = opacity
        OutlineShader.filled = filled
        mc.world.loadedEntityList
            .filter {
                (mc.gameSettings.thirdPersonView != 0 || it != mc.renderViewEntity) &&
                        shouldRenderEntity(it)
            }
            .forEach {
                mc.renderManager.renderEntityStatic(it, e.partialTicks, true)
            }
    }

    /**
     * Colorizes the entities when rendered.
     */
    @Subscribe
    val onColorizeEntity = Listener { ev: EntityTeamColorEvent ->
        val e = ev.entity
        ev.color = getRenderColor(e)
    }

    /**
     * Initializes the shader and let it knows it should be ran
     */
    init {
        OutlineShader.isRunning = true
    }

    /**
     * Returns true whether [e] should be rendered by the ESP
     */
    private fun shouldRenderEntity(e: Entity): Boolean {
        return OutlineShader.frustum.isBoundingBoxInFrustum(e.entityBoundingBox) && when {
            e.isFriendly -> friendlies
            e is EntityPlayer -> if (e.isBot) playersBot else if (e.isInvisible) playersInvisible else players
            e.isHostile -> if (e.isInvisible) hostilesInvisible else hostiles
            e.isPassive -> if (e.isInvisible) passivesInvisible else passives
            e is EntityItem -> items
            else -> false
        }
    }

    /**
     * Returns the color of rendered entity
     */
    private fun getRenderColor(e: Entity): Color = when {
        e.isFriendly -> friendliesColor
        e is EntityPlayer -> playersColor
        e.isHostile -> hostilesColor
        e.isPassive -> passivesColor
        e is EntityItem -> itemsColor
        else -> Color.white
    }

    init {
        isRunning = true
    }

}