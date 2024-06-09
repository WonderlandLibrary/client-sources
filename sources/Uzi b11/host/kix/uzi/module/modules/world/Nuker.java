package host.kix.uzi.module.modules.world;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Created by myche on 2/25/2017.
 */
public class Nuker extends Module {

    private int xPos, yPos, zPos;

    public Nuker() {
        super("Nuker", 0, Category.WORLD);
    }

    @SubscribeEvent
    public void update(UpdateEvent e) {
        if (this.isEnabled()) {
            for (int x = -4; x < 4; x++) {
                for (int y = 4; y > -4; y--) {
                    for (int z = -4; z < 4; z++) {
                        xPos = (int) mc.thePlayer.posX + x;
                        yPos = (int) mc.thePlayer.posY + y;
                        zPos = (int) mc.thePlayer.posZ + z;

                        BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                        Block block = mc.theWorld.getBlockState(blockPos).getBlock();

                        if (block.getMaterial() == Material.air) continue;
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    }
                }
            }
        }
    }

}
