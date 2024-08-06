package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.Render3dEvent;
import com.shroomclient.shroomclientnextgen.listeners.BlockESP;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.util.ArrayList;
import net.minecraft.block.BedBlock;
import net.minecraft.util.math.BlockPos;

@RegisterModule(
    name = "Bed ESP",
    uniqueId = "bedesp",
    description = "Renders A Box Around Beds",
    category = ModuleCategory.Render,
    enabledByDefault = true
)
public class BedESP extends Module {

    @SubscribeEvent
    public void onRender3d(Render3dEvent e) {
        // beds is added to / removed in async, so we gotta clone!
        ArrayList<BlockPos> bedsClone = (ArrayList<
                BlockPos
            >) BlockESP.beds.clone();
        for (BlockPos bed : bedsClone) {
            if (
                !(BlockESP.blockAtPos(bed) instanceof BedBlock) ||
                BlockESP.blockAtPos(bed) == null
            ) {
                // bad for fps prob, make a chunk unload event somehow idk
                BlockESP.unloadedChunks.add(bed);
            } else {
                int xCoord = 0;
                int zCoord = 0;

                int xWid = 1;
                int zWid = 1;

                switch (
                    BedBlock.getOppositePartDirection(C.w().getBlockState(bed))
                ) {
                    case NORTH:
                        zCoord -= 1;
                        zWid += 1;
                        break;
                    case SOUTH:
                        zWid += 1;
                        break;
                    case EAST:
                        xWid += 1;
                        break;
                    case WEST:
                        xCoord -= 1;
                        xWid += 1;
                        break;
                }

                RenderUtil.drawBox2(
                    bed.getX() + xCoord,
                    bed.getY(),
                    bed.getZ() + zCoord,
                    xWid,
                    0.6,
                    zWid,
                    e.partialTicks,
                    e.matrixStack,
                    ThemeUtil.themeColors(
                        bed.getX() + xCoord,
                        bed.getZ() + zCoord,
                        50,
                        0.002F
                    )[0],
                    100
                );
            }
        }
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
