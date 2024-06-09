package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * Created by myche on 2/5/2017.
 */
public class NoFall extends Module {

    public NoFall() {
        super("NoFall", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent e){
        if(getClosestBlockDistanceUnderPlayer(mc.thePlayer) > 3 && mc.thePlayer.fallDistance  > 0){
            mc.thePlayer.onGround = false;
            mc.thePlayer.motionY = 0;
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX +mc.thePlayer.motionX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + mc.thePlayer.motionX, -42069 + mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ, mc.thePlayer.rotationYaw , mc.thePlayer.rotationPitch, true));
        }
    }

    private static boolean getBlocks(Block b){
        return (b != Blocks.air) && (b != Blocks.tallgrass) && (b != Blocks.deadbush) && (b != Blocks.double_plant) && (b != Blocks.lava) && (b != Blocks.water) && (b != Blocks.ladder);
    }

    public int getClosestBlockDistanceUnderPlayer(EntityPlayer p){
        for(int i = MathHelper.floor_double(p.posY); i > 0; i--){
            BlockPos bp = new BlockPos(MathHelper.floor_double(p.posX), i, MathHelper.floor_double(p.posZ));
            if(getBlocks(mc.theWorld.getBlockState(bp).getBlock())){
                return MathHelper.floor_double(p.posY) - i - 1;
            }
        }
        return -1;
    }

}
