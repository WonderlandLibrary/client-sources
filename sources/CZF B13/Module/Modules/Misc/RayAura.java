package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class RayAura
        extends Module {

    private static final int radius = 5;
    private int xPos;
    private int yPos;
    private int zPos;
    private final Mode<Enum> mode = new Mode("Mode", "Mode", Break.values(), Break.Bed);

    public RayAura() {
        super("RayAura", new String[]{"rayaura", "fuck"}, ModuleType.World);
        this.addValues(this.mode);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        this.setSuffix(this.mode.getValue());
        int x = -radius;
        while (x < radius) {
            int y = radius;
            while (y > -radius) {
                int z = -radius;
                while (z < radius) {
                    this.xPos = (int) mc.thePlayer.posX + x;
                    this.yPos = (int) mc.thePlayer.posY + y;
                    this.zPos = (int) mc.thePlayer.posZ + z;
                    BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block.getBlockState().getBlock() == Block.getBlockById(92) && this.mode.getValue() == Break.Cake) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    } else if (block.getBlockState().getBlock() == Block.getBlockById(122) && this.mode.getValue() == Break.Egg) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    } else if (block.getBlockState().getBlock() == Block.getBlockById(26) && this.mode.getValue() == Break.Bed) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    }
                    ++z;
                }
                --y;
            }
            ++x;
        }
    }

    enum Break {
        Cake,
        Egg,
        Bed
    }
}


