/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.WORLD, color=-3092272, name="Fucker")
public class BedFucker
extends ToggleableModule {
    private int xOffset;
    private int zOffset;
    private int yOffset;

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (BedFucker.mc.theWorld == null) {
            return;
        }
        if (BedFucker.mc.thePlayer.isOnLadder()) {
            BedFucker.mc.thePlayer.motionY = 0.5;
        }
        this.xOffset = -5;
        while (this.xOffset < 6) {
            this.zOffset = -5;
            while (this.zOffset < 6) {
                this.yOffset = 5;
                while (this.yOffset > -5) {
                    double x = BedFucker.mc.thePlayer.posX + (double)this.xOffset;
                    double y = BedFucker.mc.thePlayer.posY + (double)this.yOffset;
                    double z = BedFucker.mc.thePlayer.posZ + (double)this.zOffset;
                    BlockPos pos = new BlockPos(x, y, z);
                    int id = Block.getIdFromBlock(BedFucker.mc.theWorld.getBlockState(pos).getBlock());
                    Block bed = Blocks.bed;
                    int idout = Block.getIdFromBlock(Blocks.bed);
                    if (id == 26 || id == 138) {
                        BedFucker.mc.thePlayer.swingItem();
                        this.smashBlock(pos);
                    }
                    --this.yOffset;
                }
                ++this.zOffset;
            }
            ++this.xOffset;
        }
    }

    public void smashBlock(BlockPos pos) {
        BedFucker.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        BedFucker.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
    }
}

