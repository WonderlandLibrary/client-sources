package client.metaware.impl.module.player;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.PacketUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

@ModuleInfo(renderName = "Breaker", name = "Breaker", category = Category.MOVEMENT)
public class Breaker extends Module {
    public DoubleProperty reech = new DoubleProperty("Range", 4, 1, 5, 1);

    private BlockPos pos;
    private Block block;
    private float blockDamage;

    @EventHandler
    private Listener<UpdatePlayerEvent> updatePlayerEventListener = event -> {
        if (this.pos == null && this.block == null) {
            for (double x = this.mc.thePlayer.posX - reech.getValue(); x < this.mc.thePlayer.posX + reech.getValue(); x++) {
                double y;
                for (y = this.mc.thePlayer.posY - reech.getValue(); y < this.mc.thePlayer.posY + reech.getValue(); y++) {
                    double z;
                    for (z = this.mc.thePlayer.posZ - reech.getValue(); z < this.mc.thePlayer.posZ + reech.getValue(); z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        Block block = this.mc.theWorld.getBlockState(pos).getBlock();
                        if (block instanceof net.minecraft.block.BlockBed) {
                            this.pos = pos;
                            this.block = block;
                        }
                    }
                }
            }
        } else {
            if (this.blockDamage <= 0.0F)
                PacketUtil.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.pos, EnumFacing.DOWN));
            this.mc.thePlayer.swingItem();
            this.blockDamage += this.block.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.thePlayer, (World)this.mc.theWorld, this.pos);
            this.mc.theWorld.sendBlockBreakProgress(this.mc.thePlayer.getEntityId(), this.pos, (int)(this.blockDamage * 10.0F - 1.0F));
            if (this.blockDamage >= 1.0F) {
                PacketUtil.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.pos, EnumFacing.DOWN));
                this.mc.playerController.onPlayerDestroyBlock(this.pos, EnumFacing.DOWN);
                this.blockDamage = 0.0F;
                this.pos = null;
                this.block = null;
            }
        }
    };
}
