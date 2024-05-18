package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.BoundingBoxEvent;
import host.kix.uzi.events.SentPacketEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import org.lwjgl.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * Created by myche on 3/6/2017.
 */
public class Jesus extends Module {

    private Entity entity;

    public Jesus() {
        super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
    }


    @SubscribeEvent
    public void onBoundingBox(BoundingBoxEvent e){
        if(((e.getBlock() instanceof BlockLiquid)) && e.getEntity() == mc.thePlayer && (!this.isInLiquid())
                && (mc.thePlayer.fallDistance < 3.0F) && (!mc.thePlayer.isSneaking())){
            e.setBoundingBox(AxisAlignedBB.fromBounds(e.getLocation().getX(), e
                    .getLocation().getY(), e.getLocation().getZ(), e
                    .getLocation().getX() + 1, e.getLocation().getY() + 1, e
                    .getLocation().getZ() + 1));
        }
    }

    @SubscribeEvent
    public void onPacketSend(SentPacketEvent event){
        if(event.getPacket() instanceof S49PacketUpdateEntityNBT){
            S49PacketUpdateEntityNBT packet = (S49PacketUpdateEntityNBT) event.getPacket();
        }

        if((event.getPacket() instanceof C03PacketPlayer)){
            C03PacketPlayer player = (C03PacketPlayer) event.getPacket();

            if(this.isOnLiquid()){
                player.setY(mc.thePlayer.posY + (mc.thePlayer.ticksExisted % 2 == 0 ? 0.01 : -0.01));
            }

        }
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event){
        if(event.type == EventType.PRE && this.isInLiquid() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.pressed){
            mc.thePlayer.motionY = 0.1;
        }

        if(mc.thePlayer.isBurning()){
            mc.thePlayer.inWater = true;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
        }
    }

    public boolean isInLiquid(){
        if(mc.thePlayer == null)
            return false;
        boolean inLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.minY;
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++){
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
                    .floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++){
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z))
                        .getBlock();
                if((block != null) && (!(block instanceof BlockAir))){
                    if(!(block instanceof BlockLiquid))
                        return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

    public boolean isOnLiquid(){
        if(mc.thePlayer == null)
            return false;
        boolean onLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.offset(0.0D, -0.01D, 0.0D).minY;
        for(int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper
                .floor_double(mc.thePlayer.boundingBox.maxX) + 1; x++){
            for(int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper
                    .floor_double(mc.thePlayer.boundingBox.maxZ) + 1; z++){
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z))
                        .getBlock();
                if((block != null) && (!(block instanceof BlockAir))){
                    if(!(block instanceof BlockLiquid))
                        return false;
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }


}
