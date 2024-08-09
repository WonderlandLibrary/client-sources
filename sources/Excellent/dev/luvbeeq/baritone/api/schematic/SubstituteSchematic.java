package dev.luvbeeq.baritone.api.schematic;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubstituteSchematic extends AbstractSchematic {

    private final ISchematic schematic;
    private final Map<Block, List<Block>> substitutions;
    private final Map<BlockState, Map<Block, BlockState>> blockStateCache = new HashMap<>();

    public SubstituteSchematic(ISchematic schematic, Map<Block, List<Block>> substitutions) {
        super(schematic.widthX(), schematic.heightY(), schematic.lengthZ());
        this.schematic = schematic;
        this.substitutions = substitutions;
    }

    @Override
    public boolean inSchematic(int x, int y, int z, BlockState currentState) {
        return schematic.inSchematic(x, y, z, currentState);
    }

    @Override
    public BlockState desiredState(int x, int y, int z, BlockState current, List<BlockState> approxPlaceable) {
        BlockState desired = schematic.desiredState(x, y, z, current, approxPlaceable);
        Block desiredBlock = desired.getBlock();
        if (!substitutions.containsKey(desiredBlock)) {
            return desired;
        }
        List<Block> substitutes = substitutions.get(desiredBlock);
        if (substitutes.contains(current.getBlock()) && !(current.getBlock() instanceof AirBlock)) {// don't preserve air, it's almost always there and almost never wanted
            return withBlock(desired, current.getBlock());
        }
        for (Block substitute : substitutes) {
            if (substitute instanceof AirBlock) {
                return current.getBlock() instanceof AirBlock ? current : Blocks.AIR.getDefaultState(); // can always "place" air
            }
            for (BlockState placeable : approxPlaceable) {
                if (substitute.equals(placeable.getBlock())) {
                    return withBlock(desired, placeable.getBlock());
                }
            }
        }
        return substitutes.get(0).getDefaultState();
    }

    private BlockState withBlock(BlockState state, Block block) {
        if (blockStateCache.containsKey(state) && blockStateCache.get(state).containsKey(block)) {
            return blockStateCache.get(state).get(block);
        }
        Collection<Property<?>> properties = state.getBlock().getStateContainer().getProperties();
        BlockState newState = block.getDefaultState();
        for (Property<?> property : properties) {
            try {
                newState = copySingleProp(state, newState, property);
            } catch(IllegalArgumentException e) { //property does not exist for target block
            }
        }
        blockStateCache.computeIfAbsent(state, s -> new HashMap<Block, BlockState>()).put(block, newState);
        return newState;
    }

    private <T extends Comparable<T>> BlockState copySingleProp(BlockState fromState, BlockState toState, Property<T> prop) {
        return toState.with(prop, fromState.get(prop));
    }
}
