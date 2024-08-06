package com.shroomclient.shroomclientnextgen.modules.impl.player;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import net.minecraft.block.FallingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EmptyBlockView;

@RegisterModule(
    name = "Fast Place",
    uniqueId = "fastplace",
    description = "Removes Placing Blocks Delay",
    category = ModuleCategory.Player
)
public class FastPlace extends Module {

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @ConfigOption(
        name = "Place Delay",
        description = "Delay For Block Place",
        max = 3,
        order = 1
    )
    public static Integer Speed = 1;

    public static int fastPlaceTicks() {
        if (
            C.p() != null &&
            C.p()
                    .getInventory()
                    .getStack(C.p().getInventory().selectedSlot)
                    .getItem() instanceof
                BlockItem h &&
            h
                .getBlock()
                .getDefaultState()
                .isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN) &&
            !(h.getBlock() instanceof FallingBlock)
        ) {
            return ModuleManager.isEnabled(FastPlace.class) ? Speed : 4;
        }
        return 4;
    }
}
