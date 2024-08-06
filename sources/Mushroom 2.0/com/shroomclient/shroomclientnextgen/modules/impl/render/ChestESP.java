package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.listeners.BlockESP;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.util.ArrayList;
import net.minecraft.block.ChestBlock;
import net.minecraft.util.math.BlockPos;

@RegisterModule(
    name = "Chest ESP",
    uniqueId = "chestesp",
    description = "Renders A Box Around Chests",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class ChestESP extends Module {

    @ConfigOption(
        name = "Filled Box",
        description = "Renders A Filled Box Around Chests",
        order = 2
    )
    public static Boolean filledBox = false;

    @ConfigOption(
        name = "Outline",
        description = "Renders An Outline Around Chests",
        order = 3
    )
    public static Boolean line = false;

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @SubscribeEvent
    public void render3D(Render3dEvent e) {
        ArrayList<BlockPos> chestsClone = (ArrayList<
                BlockPos
            >) BlockESP.chests.clone();

        try {
            for (BlockPos chest : chestsClone) {
                if (
                    !(BlockESP.blockAtPos(chest) instanceof ChestBlock) ||
                    BlockESP.blockAtPos(chest) == null
                ) BlockESP.unloadedChunks.add(chest); // bad for fps prob, make a chunk unload event somehow idk

                if (filledBox) RenderUtil.drawBox2(
                    chest.getX() + 0.1,
                    chest.getY(),
                    chest.getZ() + 0.1,
                    0.8,
                    0.85,
                    0.8,
                    e.partialTicks,
                    e.matrixStack,
                    ThemeUtil.themeColors(
                        chest.getX(),
                        chest.getZ(),
                        50,
                        0.002F
                    )[1],
                    100
                );
                if (line) RenderUtil.drawOutlinedBox(
                    chest.getX() + 0.1,
                    chest.getY(),
                    chest.getZ() + 0.1,
                    0.8,
                    0.85,
                    0.8,
                    e.partialTicks,
                    e.matrixStack,
                    ThemeUtil.themeColors(
                        chest.getX(),
                        chest.getZ(),
                        50,
                        0.002F
                    )[0]
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
