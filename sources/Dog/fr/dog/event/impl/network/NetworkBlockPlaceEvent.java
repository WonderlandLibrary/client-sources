package fr.dog.event.impl.network;

import fr.dog.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;


@AllArgsConstructor
@Getter
@Setter
public class NetworkBlockPlaceEvent extends Event {
    public BlockPos pos;
    public IBlockState state;
}
