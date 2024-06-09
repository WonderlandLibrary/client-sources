package com.leafclient.leaf.event.game.entity

import fr.shyrogan.publisher4k.Cancellable
import net.minecraft.entity.MoverType

class PlayerMoveEvent(val type: MoverType, var x: Double, var y: Double, var z: Double): Cancellable()