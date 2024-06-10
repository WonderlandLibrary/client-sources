package me.vene.skilled.modules.mods.utility;
 
 import me.vene.skilled.modules.Category;
 import me.vene.skilled.modules.Module;
 import me.vene.skilled.utilities.StringRegistry;
 import me.vene.skilled.values.NumberValue;
 import net.minecraft.client.Minecraft;
 import net.minecraft.init.Blocks;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.client.C07PacketPlayerDigging;
 import net.minecraft.util.BlockPos;
 import net.minecraft.util.EnumFacing;
 import net.minecraftforge.fml.common.gameevent.TickEvent;
 
public class BedNuker extends Module
{
    private BlockPos m;
    private final long per = 600L;
    private NumberValue range;
    private Minecraft mc;
    private static int ticks;
    
    public BedNuker() {
        super(StringRegistry.register("BedNuker"), 0, Category.U);
        this.range = new NumberValue("Range", 6.0, 1.0, 6.0);
        this.mc = Minecraft.getMinecraft();
        this.addValue(this.range);
    }
    
    @Override
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        ++BedNuker.ticks;
        if (BedNuker.ticks % 20 == 0 && this.mc.thePlayer != null && this.mc.theWorld != null) {
            BedNuker.ticks = 0;
            int ra;
            for (int y = ra = (int)this.range.getValue(); y >= -ra; --y) {
                for (int x = -ra; x <= ra; ++x) {
                    for (int z = -ra; z <= ra; ++z) {
                        final BlockPos p = new BlockPos(this.mc.thePlayer.posX + x, this.mc.thePlayer.posY + y, this.mc.thePlayer.posZ + z);
                        final boolean bed = this.mc.theWorld.getBlockState(p).getBlock() == Blocks.bed;
                        if (this.m == p) {
                            if (!bed) {
                                this.m = null;
                            }
                        }
                        else if (bed) {
                            this.breakBlock(p);
                            this.m = p;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    private void breakBlock(final BlockPos p) {
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, p, EnumFacing.NORTH));
        this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, p, EnumFacing.NORTH));
    }
}
