package com.leafclient.leaf.ui.theme

import com.leafclient.leaf.management.mod.Mod
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.ui.Renderer
import com.leafclient.leaf.management.ui.utils.Animation
import com.leafclient.leaf.management.ui.utils.Transitions
import com.leafclient.leaf.ui.hud.ModList
import com.leafclient.leaf.utils.render.Screen
import com.leafclient.leaf.utils.render.font.FontManager

class LeafModList: Renderer<ModList> {

    private val font = FontManager["SourceSansPro-Regular", 12F]
    private val animations = mutableListOf<Pair<Mod, Animation>>()

    override fun render(c: ModList) {
        var y = 1F
        val displayWidth = Screen.width - 1F

        animations
            .sortedByDescending { (mod, _) ->
                font.getWidth(mod.displayName)
            }
            .forEach { (mod, animation) ->
                val text = mod.displayName
                val textWidth = font.getWidth(text)
                val textHeight = font.getHeight(text)
                val animationValue = animation.value.toFloat()
                font.drawStringWithShadow(text, displayWidth - textWidth * animationValue, y, mod.category.color.rgb)
                y += textHeight * animationValue
            }
    }

    override fun update(c: ModList) {
        val mods = c.mods
        if(mods.isEmpty())
            return

        if(animations.isEmpty()) {
            animations.addAll(
                mods.map { it to Animation(250L, Transitions.SIGMOID_EXTREME) }
            )
        }

        animations
            .forEach { (mod, animation) ->
                if(!animation.isRunning)
                    animation.setRunning(true)

                val isForward = mod.isDisplayed
                if(animation.isForward != isForward) {
                    animation.setForward(isForward)
                }
            }

    }

    private val Mod.displayName: String
        get() = if(suffix.isNotEmpty())
            "$label §7$suffix"
        else
            label

    private val Mod.isDisplayed: Boolean
        get() = this !is ToggleableMod || (this.isRunning && this.visible)


}