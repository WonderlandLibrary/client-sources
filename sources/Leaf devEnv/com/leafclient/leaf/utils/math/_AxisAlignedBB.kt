package com.leafclient.leaf.utils.math

import net.minecraft.entity.Entity
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

data class BlockInteraction(val facing: EnumFacing, val position: BlockPos, val interactionBox: AxisAlignedBB)

/**
 * Returns every [EnumFacing] into a [BlockInteraction] object sorted by
 * their distance to [startPos].
 */
fun AxisAlignedBB.getClosestFacings(startPos: Vec3d, blockPosition: BlockPos)
    = listOf(
        BlockInteraction(EnumFacing.UP, blockPosition, AxisAlignedBB(minX, maxY, minZ, maxX, maxY, maxZ).offset(blockPosition)),
        BlockInteraction(EnumFacing.DOWN, blockPosition, AxisAlignedBB(minX, minY, minZ, maxX, minY, maxZ).offset(blockPosition)),
        BlockInteraction(EnumFacing.EAST, blockPosition, AxisAlignedBB(maxX, minY, minZ, maxX, maxY, maxZ).offset(blockPosition)),
        BlockInteraction(EnumFacing.SOUTH, blockPosition, AxisAlignedBB(minX, minY, maxZ, maxX, maxY, maxZ).offset(blockPosition)),
        BlockInteraction(EnumFacing.NORTH, blockPosition, AxisAlignedBB(minX, minY, minZ, maxX, maxY, minZ).offset(blockPosition)),
        BlockInteraction(EnumFacing.WEST, blockPosition, AxisAlignedBB(minX, minY, minZ, minX, maxY, maxZ).offset(blockPosition))
).sortedBy { it.interactionBox.center.distanceTo(startPos) }

/**
 * Checks whether this [AxisAlignedBB] is inside of a block or not.
 */
fun AxisAlignedBB.isInsideBlocks(entity: Entity, world: World): Boolean {
    return world.getCollisionBoxes(entity, this).isNotEmpty()
}
