package com.shroomclient.shroomclientnextgen.hooks;

import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.render.ChestESP;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.MatrixStack;

public class BlockRenderHook {

    public static void onRenderBlock(BlockState state, MatrixStack matrices) {
        Block b = state.getBlock();
        if (b == Blocks.CHEST && ModuleManager.isEnabled(ChestESP.class)) {
            //ModuleManager.getModule(ChestESP.class).onRender(matrices);
        }
    }
}
