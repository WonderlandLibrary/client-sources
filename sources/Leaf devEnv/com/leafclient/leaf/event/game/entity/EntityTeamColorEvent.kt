package com.leafclient.leaf.event.game.entity

import net.minecraft.entity.Entity
import java.awt.Color

/**
 * Event called to modify the color of specified entity when its rendered,
 * set the color to null lets the client use team colors
 */
class EntityTeamColorEvent @JvmOverloads constructor(val entity: Entity, var color: Color? = null)