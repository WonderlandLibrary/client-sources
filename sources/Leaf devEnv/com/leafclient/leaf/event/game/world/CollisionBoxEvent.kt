package com.leafclient.leaf.event.game.world

import fr.shyrogan.publisher4k.Cancellable
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos

class CollisionBoxEvent(val position: BlockPos, var boundingBox: AxisAlignedBB?): Cancellable()