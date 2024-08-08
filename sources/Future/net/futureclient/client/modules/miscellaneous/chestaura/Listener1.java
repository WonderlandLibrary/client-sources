package net.futureclient.client.modules.miscellaneous.chestaura;

import net.minecraft.util.math.BlockPos;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import java.util.Iterator;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.futureclient.client.IG;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.ChestAura;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final ChestAura k;
    
    public Listener1(final ChestAura k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        ChestAura.M(this.k);
        if (ChestAura.M(this.k) > 0) {
            ChestAura.e(this.k);
            return;
        }
        if (!ChestAura.M(this.k)) {
            ChestAura.M(this.k).remove(ChestAura.M(this.k));
            ChestAura.M(this.k, true);
        }
        if (ChestAura.M(this.k).M() && !ChestAura.getMinecraft6().player.onGround) {
            return;
        }
        if (ChestAura.getMinecraft14().currentScreen != null) {
            return;
        }
        final Iterator<TileEntity> iterator = ChestAura.getMinecraft12().world.loadedTileEntityList.iterator();
        TileEntity tileEntity = null;
    Label_0183:
        do {
            Iterator<TileEntity> iterator2 = iterator;
            while (iterator2.hasNext()) {
                if (!((tileEntity = iterator.next()) instanceof TileEntityChest) && !(tileEntity instanceof TileEntityEnderChest)) {
                    iterator2 = iterator;
                }
                else {
                    if (!ChestAura.M(this.k).contains(tileEntity.getPos())) {
                        continue Label_0183;
                    }
                    iterator2 = iterator;
                }
            }
            return;
        } while (tileEntity.getDistanceSq(ChestAura.getMinecraft5().player.posX, ChestAura.getMinecraft1().player.posY + ChestAura.getMinecraft2().player.getEyeHeight(), ChestAura.getMinecraft3().player.posZ) > ChestAura.M(this.k).B().floatValue() * ChestAura.M(this.k).B().floatValue());
        IG.M(tileEntity.getPos());
        final PlayerControllerMP playerController = ChestAura.getMinecraft9().playerController;
        final EntityPlayerSP player = ChestAura.getMinecraft13().player;
        final WorldClient world = ChestAura.getMinecraft().world;
        final BlockPos pos = tileEntity.getPos();
        final EnumFacing up = EnumFacing.UP;
        final double n = 0.0;
        playerController.processRightClickBlock(player, world, pos, up, new Vec3d(n, n, n), EnumHand.MAIN_HAND);
        ChestAura.M(this.k, (ChestAura.getMinecraft11().getConnection() != null && ChestAura.getMinecraft4().getConnection().getPlayerInfo(ChestAura.getMinecraft7().player.getUniqueID()) != null) ? (ChestAura.getMinecraft8().getConnection().getPlayerInfo(ChestAura.getMinecraft10().player.getUniqueID()).getResponseTime() + 2) : 0);
        ChestAura.M(this.k, tileEntity.getPos());
        ChestAura.M(this.k, false);
        ChestAura.M(this.k).add(ChestAura.M(this.k));
    }
}
