package com.leafclient.leaf.mod.combat

import com.leafclient.leaf.event.game.entity.PlayerUpdateEvent
import com.leafclient.leaf.event.game.world.WorldEntityEvent
import com.leafclient.leaf.extension.isBot
import com.leafclient.leaf.management.mod.ToggleableMod
import com.leafclient.leaf.management.mod.category.Category
import com.leafclient.leaf.management.setting.setting
import com.leafclient.leaf.utils.name
import com.leafclient.leaf.utils.stripColor
import fr.shyrogan.publisher4k.subscription.Listener
import fr.shyrogan.publisher4k.subscription.Subscribe
import net.minecraft.entity.player.EntityPlayer
import java.util.*

class AntiBot: ToggleableMod("AntiBot", Category.FIGHT) {

    private var pingCheck by setting("Id", true)
    private var idCheck by setting("Ping", true)
    private var groundCheck by setting("Ground", "Checks whether the entity has touched the ground", false)
    private var derpCheck by setting("Derp", true)
    private var tabCheck by setting("Tab", true)

    private val groundList = mutableListOf<UUID>()

    /**
     * Method invoked every time we check whether [player] is a bot:
     * at update and enabling
     */
    @Suppress("UNNECESSARY_SAFE_CALL")
    private fun checkIfBot(player: EntityPlayer) {
        if(mc.player == null || player == mc.player || mc.player.ticksExisted < 10)
            return
        player.isBot = false

        //<editor-fold desc="Id">
        if(idCheck) {
            if(player.entityId !in 0..999999999) {
                player.isBot = true
                return
            }
        }
        //</editor-fold>
        //<editor-fold desc="Ping">
        if(pingCheck) {
            if(mc.player.connection.getPlayerInfo(player.uniqueID)?.responseTime ?: 0 < 0) {
                player.isBot = true
                return
            }
        }
        //</editor-fold>
        //<editor-fold desc="Derp">
        if(derpCheck) {
            if(player.rotationPitch !in -90F..90F) {
                player.isBot = true
                return
            }
        }
        //</editor-fold>
        //<editor-fold desc="Ground">
        if(groundCheck) {
            if (player.onGround)
                groundList.add(player.uniqueID)
            if (!groundList.contains(player.uniqueID)) {
                player.isBot = true
                return
            }
        }
        //</editor-fold>
        //<editor-fold desc="Tab">
        if(tabCheck) {
            val playerName = player.displayName.formattedText.stripColor
            if(mc.player.connection.playerInfoMap.none { playerName.contains(it.name.stripColor) }) {
                player.isBot = true
                return
            }
        }
        //</editor-fold>
    }

    /**
     * If the [player] is null:
     * Clears all the checks to make sure we don't keep objects in memory
     *
     * Otherwise:
     * Clears the checks for specified [player]
     */
    private fun clearChecks(player: EntityPlayer?) {
        if(player == null) {
            groundList.clear()
            mc.world.loadedEntityList
                .filterIsInstance<EntityPlayer>()
                .forEach { it.isBot = false }
        } else {
            groundList.remove(player.uniqueID)
        }
    }

    /**
     * Invokes the [checkIfBot] method to each player
     */
    @Subscribe
    val onUpdate = Listener<PlayerUpdateEvent.Pre> { e ->
        mc.world.loadedEntityList
            .filterIsInstance<EntityPlayer>()
            .forEach(this::checkIfBot)
    }

    /**
     * Check if the entity is a bot when spawned and also clear the checks
     * if the player changes of world
     */
    @Subscribe
    val onEntityAdd = Listener<WorldEntityEvent.Spawn> { e ->
        if(e.entity == mc.player) {
            clearChecks(null)
        } else {
            if (e.entity is EntityPlayer)
                checkIfBot(e.entity)
        }
    }

    /**
     * Clear the checks for the removed entity
     */
    @Subscribe
    val onEntityRemove = Listener<WorldEntityEvent.Despawn> { e ->
        val entity = e.entity
        if(entity is EntityPlayer)
            clearChecks(entity)
    }

    override fun onDisable()
        = clearChecks(null)

    init {
        isRunning = true
    }

}