/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BreakableBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SlimeBlock
extends BreakableBlock {
    public SlimeBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onFallenUpon(World world, BlockPos blockPos, Entity entity2, float f) {
        if (entity2.isSuppressingBounce()) {
            super.onFallenUpon(world, blockPos, entity2, f);
        } else {
            entity2.onLivingFall(f, 0.0f);
        }
    }

    @Override
    public void onLanded(IBlockReader iBlockReader, Entity entity2) {
        if (entity2.isSuppressingBounce()) {
            super.onLanded(iBlockReader, entity2);
        } else {
            this.bounceEntity(entity2);
        }
    }

    private void bounceEntity(Entity entity2) {
        Vector3d vector3d = entity2.getMotion();
        if (vector3d.y < 0.0) {
            double d = entity2 instanceof LivingEntity ? 1.0 : 0.8;
            entity2.setMotion(vector3d.x, -vector3d.y * d, vector3d.z);
        }
    }

    @Override
    public void onEntityWalk(World world, BlockPos blockPos, Entity entity2) {
        double d = Math.abs(entity2.getMotion().y);
        if (d < 0.1 && !entity2.isSteppingCarefully()) {
            double d2 = 0.4 + d * 0.2;
            entity2.setMotion(entity2.getMotion().mul(d2, 1.0, d2));
        }
        super.onEntityWalk(world, blockPos, entity2);
    }
}

