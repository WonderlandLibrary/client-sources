/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;

public class EntitySelectionContext
implements ISelectionContext {
    protected static final ISelectionContext DUMMY = new EntitySelectionContext(false, -1.7976931348623157E308, Items.AIR, EntitySelectionContext::lambda$static$0){

        @Override
        public boolean func_216378_a(VoxelShape voxelShape, BlockPos blockPos, boolean bl) {
            return bl;
        }
    };
    private final boolean sneaking;
    private final double posY;
    private final Item item;
    private final Predicate<Fluid> fluidPredicate;

    protected EntitySelectionContext(boolean bl, double d, Item item, Predicate<Fluid> predicate) {
        this.sneaking = bl;
        this.posY = d;
        this.item = item;
        this.fluidPredicate = predicate;
    }

    @Deprecated
    protected EntitySelectionContext(Entity entity2) {
        this(entity2.isDescending(), entity2.getPosY(), entity2 instanceof LivingEntity ? ((LivingEntity)entity2).getHeldItemMainhand().getItem() : Items.AIR, entity2 instanceof LivingEntity ? ((LivingEntity)entity2)::func_230285_a_ : EntitySelectionContext::lambda$new$1);
    }

    @Override
    public boolean hasItem(Item item) {
        return this.item == item;
    }

    @Override
    public boolean func_230426_a_(FluidState fluidState, FlowingFluid flowingFluid) {
        return this.fluidPredicate.test(flowingFluid) && !fluidState.getFluid().isEquivalentTo(flowingFluid);
    }

    @Override
    public boolean getPosY() {
        return this.sneaking;
    }

    @Override
    public boolean func_216378_a(VoxelShape voxelShape, BlockPos blockPos, boolean bl) {
        return this.posY > (double)blockPos.getY() + voxelShape.getEnd(Direction.Axis.Y) - (double)1.0E-5f;
    }

    private static boolean lambda$new$1(Fluid fluid) {
        return true;
    }

    private static boolean lambda$static$0(Fluid fluid) {
        return true;
    }
}

