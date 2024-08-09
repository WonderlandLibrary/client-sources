/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math;

import java.util.function.Predicate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

public class RayTraceContext {
    private final Vector3d startVec;
    private final Vector3d endVec;
    private final BlockMode blockMode;
    private final FluidMode fluidMode;
    private final ISelectionContext context;

    public RayTraceContext(Vector3d vector3d, Vector3d vector3d2, BlockMode blockMode, FluidMode fluidMode, Entity entity2) {
        this.startVec = vector3d;
        this.endVec = vector3d2;
        this.blockMode = blockMode;
        this.fluidMode = fluidMode;
        this.context = ISelectionContext.forEntity(entity2);
    }

    public Vector3d getEndVec() {
        return this.endVec;
    }

    public Vector3d getStartVec() {
        return this.startVec;
    }

    public VoxelShape getBlockShape(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos) {
        return this.blockMode.get(blockState, iBlockReader, blockPos, this.context);
    }

    public VoxelShape getFluidShape(FluidState fluidState, IBlockReader iBlockReader, BlockPos blockPos) {
        return this.fluidMode.test(fluidState) ? fluidState.getShape(iBlockReader, blockPos) : VoxelShapes.empty();
    }

    public static enum BlockMode implements IVoxelProvider
    {
        COLLIDER(AbstractBlock.AbstractBlockState::getCollisionShape),
        OUTLINE(AbstractBlock.AbstractBlockState::getShape),
        VISUAL(AbstractBlock.AbstractBlockState::getRaytraceShape);

        private final IVoxelProvider provider;

        private BlockMode(IVoxelProvider iVoxelProvider) {
            this.provider = iVoxelProvider;
        }

        @Override
        public VoxelShape get(BlockState blockState, IBlockReader iBlockReader, BlockPos blockPos, ISelectionContext iSelectionContext) {
            return this.provider.get(blockState, iBlockReader, blockPos, iSelectionContext);
        }
    }

    public static enum FluidMode {
        NONE(FluidMode::lambda$static$0),
        SOURCE_ONLY(FluidState::isSource),
        ANY(FluidMode::lambda$static$1);

        private final Predicate<FluidState> fluidTest;

        private FluidMode(Predicate<FluidState> predicate) {
            this.fluidTest = predicate;
        }

        public boolean test(FluidState fluidState) {
            return this.fluidTest.test(fluidState);
        }

        private static boolean lambda$static$1(FluidState fluidState) {
            return !fluidState.isEmpty();
        }

        private static boolean lambda$static$0(FluidState fluidState) {
            return true;
        }
    }

    public static interface IVoxelProvider {
        public VoxelShape get(BlockState var1, IBlockReader var2, BlockPos var3, ISelectionContext var4);
    }
}

