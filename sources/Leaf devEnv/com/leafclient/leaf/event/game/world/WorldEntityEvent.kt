package com.leafclient.leaf.event.game.world

import net.minecraft.entity.Entity

abstract class WorldEntityEvent(val entity: Entity) {

    class Spawn(entity: Entity): WorldEntityEvent(entity)

    class Despawn(entity: Entity): WorldEntityEvent(entity)

}