/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class WaterAvoidingRandomFlyingGoal
extends WaterAvoidingRandomWalkingGoal {
    public WaterAvoidingRandomFlyingGoal(CreatureEntity creatureEntity, double d) {
        super(creatureEntity, d);
    }

    @Override
    @Nullable
    protected Vector3d getPosition() {
        Vector3d vector3d = null;
        if (this.creature.isInWater()) {
            vector3d = RandomPositionGenerator.getLandPos(this.creature, 15, 15);
        }
        if (this.creature.getRNG().nextFloat() >= this.probability) {
            vector3d = this.getTreePos();
        }
        return vector3d == null ? super.getPosition() : vector3d;
    }

    @Nullable
    private Vector3d getTreePos() {
        BlockPos blockPos = this.creature.getPosition();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        BlockPos.Mutable mutable2 = new BlockPos.Mutable();
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(MathHelper.floor(this.creature.getPosX() - 3.0), MathHelper.floor(this.creature.getPosY() - 6.0), MathHelper.floor(this.creature.getPosZ() - 3.0), MathHelper.floor(this.creature.getPosX() + 3.0), MathHelper.floor(this.creature.getPosY() + 6.0), MathHelper.floor(this.creature.getPosZ() + 3.0))) {
            Block block;
            boolean bl;
            if (blockPos.equals(blockPos2) || !(bl = (block = this.creature.world.getBlockState(mutable2.setAndMove(blockPos2, Direction.DOWN)).getBlock()) instanceof LeavesBlock || block.isIn(BlockTags.LOGS)) || !this.creature.world.isAirBlock(blockPos2) || !this.creature.world.isAirBlock(mutable.setAndMove(blockPos2, Direction.UP))) continue;
            return Vector3d.copyCenteredHorizontally(blockPos2);
        }
        return null;
    }
}

