package fr.dog.util;


import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Getter
@Setter
public class PosFace {
    public BlockPos pos;
    public EnumFacing facing;

    public PosFace(BlockPos pos, EnumFacing facing) {
        this.pos = pos;
        this.facing = facing;
    }

}
