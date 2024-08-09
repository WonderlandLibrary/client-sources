/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.StructurePiece;

public abstract class ScatteredStructurePiece
extends StructurePiece {
    protected final int width;
    protected final int height;
    protected final int depth;
    protected int hPos = -1;

    protected ScatteredStructurePiece(IStructurePieceType iStructurePieceType, Random random2, int n, int n2, int n3, int n4, int n5, int n6) {
        super(iStructurePieceType, 0);
        this.width = n4;
        this.height = n5;
        this.depth = n6;
        this.setCoordBaseMode(Direction.Plane.HORIZONTAL.random(random2));
        this.boundingBox = this.getCoordBaseMode().getAxis() == Direction.Axis.Z ? new MutableBoundingBox(n, n2, n3, n + n4 - 1, n2 + n5 - 1, n3 + n6 - 1) : new MutableBoundingBox(n, n2, n3, n + n6 - 1, n2 + n5 - 1, n3 + n4 - 1);
    }

    protected ScatteredStructurePiece(IStructurePieceType iStructurePieceType, CompoundNBT compoundNBT) {
        super(iStructurePieceType, compoundNBT);
        this.width = compoundNBT.getInt("Width");
        this.height = compoundNBT.getInt("Height");
        this.depth = compoundNBT.getInt("Depth");
        this.hPos = compoundNBT.getInt("HPos");
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putInt("Width", this.width);
        compoundNBT.putInt("Height", this.height);
        compoundNBT.putInt("Depth", this.depth);
        compoundNBT.putInt("HPos", this.hPos);
    }

    protected boolean isInsideBounds(IWorld iWorld, MutableBoundingBox mutableBoundingBox, int n) {
        if (this.hPos >= 0) {
            return false;
        }
        int n2 = 0;
        int n3 = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = this.boundingBox.minZ; i <= this.boundingBox.maxZ; ++i) {
            for (int j = this.boundingBox.minX; j <= this.boundingBox.maxX; ++j) {
                mutable.setPos(j, 64, i);
                if (!mutableBoundingBox.isVecInside(mutable)) continue;
                n2 += iWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY();
                ++n3;
            }
        }
        if (n3 == 0) {
            return true;
        }
        this.hPos = n2 / n3;
        this.boundingBox.offset(0, this.hPos - this.boundingBox.minY + n, 0);
        return false;
    }
}

