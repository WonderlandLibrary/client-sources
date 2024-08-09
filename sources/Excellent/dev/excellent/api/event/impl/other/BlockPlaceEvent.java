package dev.excellent.api.event.impl.other;

import dev.excellent.api.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@Getter
@AllArgsConstructor
public class BlockPlaceEvent extends CancellableEvent {
    public BlockPos position;
    public Block block;
    public ItemStack stack;
}