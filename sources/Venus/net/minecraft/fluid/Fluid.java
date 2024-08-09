/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class Fluid {
    public static final ObjectIntIdentityMap<FluidState> STATE_REGISTRY = new ObjectIntIdentityMap();
    protected final StateContainer<Fluid, FluidState> stateContainer;
    private FluidState defaultState;

    protected Fluid() {
        StateContainer.Builder<Fluid, FluidState> builder = new StateContainer.Builder<Fluid, FluidState>(this);
        this.fillStateContainer(builder);
        this.stateContainer = builder.func_235882_a_(Fluid::getDefaultState, FluidState::new);
        this.setDefaultState(this.stateContainer.getBaseState());
    }

    protected void fillStateContainer(StateContainer.Builder<Fluid, FluidState> builder) {
    }

    public StateContainer<Fluid, FluidState> getStateContainer() {
        return this.stateContainer;
    }

    protected final void setDefaultState(FluidState fluidState) {
        this.defaultState = fluidState;
    }

    public final FluidState getDefaultState() {
        return this.defaultState;
    }

    public abstract Item getFilledBucket();

    protected void animateTick(World world, BlockPos blockPos, FluidState fluidState, Random random2) {
    }

    protected void tick(World world, BlockPos blockPos, FluidState fluidState) {
    }

    protected void randomTick(World world, BlockPos blockPos, FluidState fluidState, Random random2) {
    }

    @Nullable
    protected IParticleData getDripParticleData() {
        return null;
    }

    protected abstract boolean canDisplace(FluidState var1, IBlockReader var2, BlockPos var3, Fluid var4, Direction var5);

    protected abstract Vector3d getFlow(IBlockReader var1, BlockPos var2, FluidState var3);

    public abstract int getTickRate(IWorldReader var1);

    protected boolean ticksRandomly() {
        return true;
    }

    protected boolean isEmpty() {
        return true;
    }

    protected abstract float getExplosionResistance();

    public abstract float getActualHeight(FluidState var1, IBlockReader var2, BlockPos var3);

    public abstract float getHeight(FluidState var1);

    protected abstract BlockState getBlockState(FluidState var1);

    public abstract boolean isSource(FluidState var1);

    public abstract int getLevel(FluidState var1);

    public boolean isEquivalentTo(Fluid fluid) {
        return fluid == this;
    }

    public boolean isIn(ITag<Fluid> iTag) {
        return iTag.contains(this);
    }

    public abstract VoxelShape func_215664_b(FluidState var1, IBlockReader var2, BlockPos var3);
}

