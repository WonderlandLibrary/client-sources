package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.GameEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(aliases = {"Bed Plates"}, description = "bedplates", category = Category.RENDER)
public class BedPlates extends Module {

    @EventLink
    private final Listener<GameEvent> onGameEvent = event -> {
        for (int x = 0; x < mc.theWorld.getActualHeight(); x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < mc.theWorld.getWorldBorder().getSize(); z++) {
                    final BlockPos pos = new BlockPos(x, y, z);

                    if (mc.theWorld.getBlockState(pos).getBlock() == Blocks.bed) {
                        ChatUtil.display("Found bed at: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
                    }
                    int posX = pos.getX();

                    for (int i = 0; i < 3; i++) {
                        posX++;
                    }
                }
            }
        }
    };
}
