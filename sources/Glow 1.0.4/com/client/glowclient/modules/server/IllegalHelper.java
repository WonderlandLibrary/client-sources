package com.client.glowclient.modules.server;

import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.tileentity.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.*;
import java.util.*;
import com.client.glowclient.modules.*;

public class IllegalHelper extends ModuleContainer
{
    @SubscribeEvent
    public void M(final Cd cd) {
        if (cd.getPacket() instanceof CPacketCloseWindow) {
            cd.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void M(final EventRenderWorld eventRenderWorld) {
        if (this.k()) {
            for (final TileEntity tileEntity2 : Wrapper.mc.world.loadedTileEntityList) {
                final TileEntity tileEntity = tileEntity2;
                final double n = tileEntity.getPos().getX();
                final double n2 = tileEntity.getPos().getY();
                final double n3 = tileEntity.getPos().getZ();
                if (tileEntity instanceof TileEntityHopper) {
                    if (Wrapper.mc.player.getDistance(((TileEntityHopper)tileEntity2).getXPos(), ((TileEntityHopper)tileEntity2).getYPos(), ((TileEntityHopper)tileEntity2).getZPos()) >= 8.0) {
                        final float n4 = 1.0f;
                        final float n5 = 0.0f;
                        GL11.glColor4f(n4, n5, n5, 0.02f);
                    }
                    else {
                        GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.1f);
                    }
                    if (Wrapper.mc.player.getDistance(((TileEntityHopper)tileEntity2).getXPos(), ((TileEntityHopper)tileEntity2).getYPos(), ((TileEntityHopper)tileEntity2).getZPos()) > 17.0) {
                        continue;
                    }
                    final double n6 = n;
                    final double n7 = n2;
                    final double n8 = n3;
                    final float n9 = 8.5f;
                    final int n10 = 25;
                    Ma.M(n6, n7, n8, n9, n10, n10);
                }
            }
        }
    }
    
    public IllegalHelper() {
        super(Category.SERVER, "IllegalHelper", false, -1, "Help with the use of Illegal Items - 2b2t");
    }
}
