package cn.Hanabi.modules.Player;

import java.util.*;
import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import com.darkmagician6.eventapi.*;
import net.minecraft.block.*;
import ClassSub.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;

public class Nuker extends Mod
{
    ArrayList positions;
    private Class205 timer2;
    private Class205 timer;
    private Value mode;
    private Value<Double> reach;
    private Value<Double> delay;
    
    
    public Nuker() {
        super("Nuker", Category.PLAYER);
        this.positions = null;
        this.timer2 = new Class205();
        this.timer = new Class205();
        this.mode = new Value("Nuker", "Mode", 0);
        this.reach = new Value<Double>("Nuker_Reach", 6.0, 1.0, 6.0, 0.1);
        this.delay = new Value<Double>("Nuker_Delay", 120.0, 0.0, 1000.0, 10.0);
        this.mode.mode.add("Bed");
        this.mode.mode.add("Egg");
        this.mode.mode.add("Cake");
    }
    
    @EventTarget
    public void onPre(final EventPreMotion eventPreMotion) {
        this.standartDestroyer(eventPreMotion);
    }
    
    private void standartDestroyer(final EventPreMotion eventPreMotion) {
        BlockPos blockPos;
        while ((blockPos = BlockPos.getAllInBox(Nuker.mc.thePlayer.getPosition().subtract(new Vec3i((double)this.reach.getValueState(), (double)this.reach.getValueState(), (double)this.reach.getValueState())), Nuker.mc.thePlayer.getPosition().add(new Vec3i((double)this.reach.getValueState(), (double)this.reach.getValueState(), (double)this.reach.getValueState()))).iterator().next()) != null && (!(Nuker.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockBed) || !this.mode.isCurrentMode("Bed")) && (!(Nuker.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockDragonEgg) || !this.mode.isCurrentMode("Egg"))) {
            if (Nuker.mc.theWorld.getBlockState(blockPos).getBlock() instanceof BlockCake) {
                if (!this.mode.isCurrentMode("Cake")) {
                    continue;
                }
                break;
            }
        }
        if (blockPos instanceof BlockPos) {
            final float[] array = Class339.getRotationsNeededBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            eventPreMotion.yaw = array[0];
            eventPreMotion.pitch = array[1];
            if (this.timer.isDelayComplete(this.delay.getValueState())) {
                Nuker.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                Nuker.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                Nuker.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.DOWN));
                Nuker.mc.thePlayer.swingItem();
                this.timer.reset();
            }
        }
    }
}
