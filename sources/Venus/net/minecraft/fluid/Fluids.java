/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.fluid;

import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.registry.Registry;

public class Fluids {
    public static final Fluid EMPTY = Fluids.register("empty", new EmptyFluid());
    public static final FlowingFluid FLOWING_WATER = Fluids.register("flowing_water", new WaterFluid.Flowing());
    public static final FlowingFluid WATER = Fluids.register("water", new WaterFluid.Source());
    public static final FlowingFluid FLOWING_LAVA = Fluids.register("flowing_lava", new LavaFluid.Flowing());
    public static final FlowingFluid LAVA = Fluids.register("lava", new LavaFluid.Source());

    private static <T extends Fluid> T register(String string, T t) {
        return (T)Registry.register(Registry.FLUID, string, t);
    }

    static {
        for (Fluid fluid : Registry.FLUID) {
            for (FluidState fluidState : fluid.getStateContainer().getValidStates()) {
                Fluid.STATE_REGISTRY.add(fluidState);
            }
        }
    }
}

