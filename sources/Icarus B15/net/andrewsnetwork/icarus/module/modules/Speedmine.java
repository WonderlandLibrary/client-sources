// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import net.andrewsnetwork.icarus.utilities.BlockData;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.andrewsnetwork.icarus.event.events.BlockBreaking;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Speedmine extends Module
{
    private final Value<Boolean> autotool;
    public final Value<Boolean> instant;
    private final Value<Float> multiplier;
    private final Value<Integer> delay;
    private final TimeHelper time;
    
    public Speedmine() {
        super("Speedmine", -7820064, Category.WORLD);
        this.autotool = new Value<Boolean>("speedmine_Auto Tool", "autotool", true, this);
        this.instant = new Value<Boolean>("speedmine_Instant", "instant", true, this);
        this.multiplier = (Value<Float>)new ConstrainedValue("speedmine_Multiplier", "multiplier", 1.337f, 1.0f, 5.0f, this);
        this.delay = (Value<Integer>)new ConstrainedValue("speedmine_Delay", "delay", 3, 0, 5, this);
        this.time = new TimeHelper();
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        boolean shouldSend = true;
        if (event instanceof BlockBreaking) {
            final BlockBreaking bb = (BlockBreaking)event;
            if (bb.getState() == BlockBreaking.EnumBlock.CLICK) {
                this.time.reset();
                shouldSend = true;
                if (this.autotool.getValue()) {
                    BlockHelper.bestTool(bb.getPos().getX(), bb.getPos().getY(), bb.getPos().getZ());
                }
            }
            if (this.time.hasReached(55L)) {
                shouldSend = false;
            }
            if (this.instant.getValue()) {
                if (Speedmine.mc.gameSettings.keyBindAttack.getIsKeyPressed() && this.time.hasReached(25L) && shouldSend) {
                    Speedmine.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, bb.getPos(), bb.getSide()));
                }
            }
            else if (!this.instant.getValue()) {
                bb.setDelay(this.delay.getValue());
                bb.setMultiplier(this.multiplier.getValue());
            }
        }
    }
    
    public BlockData getBlockData(final BlockPos pos) {
        if (Speedmine.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
        }
        if (Speedmine.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (Speedmine.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (Speedmine.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (Speedmine.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
            return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
}
