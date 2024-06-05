/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.world;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import digital.rbq.annotations.Label;
import digital.rbq.events.game.TickEvent;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.module.option.impl.EnumOption;

@Label(value="Destroyer")
@Category(value=ModuleCategory.WORLD)
public final class DestroyerMod
extends Module {
    private final EnumOption<Mode> mode = new EnumOption<Mode>("Mode", Mode.CAKE);
    public final DoubleOption radius = new DoubleOption("Radius", 5.0, 3.0, 6.0, 1.0);

    public DestroyerMod() {
        this.addOptions(this.mode, this.radius);
        this.setMode(this.mode);
    }

    @Listener(value=ReceivePacketEvent.class)
    public final void onReceivePacket(ReceivePacketEvent event) {
        S02PacketChat packetChat;
        String text;
        if (this.mode.getValue() == Mode.CAKE && event.getPacket() instanceof S02PacketChat && ((text = (packetChat = (S02PacketChat)event.getPacket()).getChatComponent().getUnformattedText()).contains("20 seconds") || text.contains("your own"))) {
            event.setCancelled();
        }
    }

    @Listener(value=TickEvent.class)
    public final void onTick(TickEvent event) {
        int range = ((Double)this.radius.getValue()).intValue();
        for (int x = -range; x < range; ++x) {
            for (int y = range; y > -range; --y) {
                for (int z = -range; z < range; ++z) {
                    int xPos = (int)DestroyerMod.mc.thePlayer.posX + x;
                    int yPos = (int)DestroyerMod.mc.thePlayer.posY + y;
                    int zPos = (int)DestroyerMod.mc.thePlayer.posZ + z;
                    BlockPos blockPos = new BlockPos(xPos, yPos, zPos);
                    Block block = DestroyerMod.mc.theWorld.getBlockState(blockPos).getBlock();
                    if (block.getBlockState().getBlock() != Block.getBlockById(this.getId())) continue;
                    mc.getNetHandler().addToSendQueueSilent(new C0APacketAnimation());
                    mc.getNetHandler().addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    mc.getNetHandler().addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                }
            }
        }
    }

    private final int getId() {
        switch ((Mode)((Object)this.mode.getValue())) {
            case CAKE: {
                return 92;
            }
            case BED: {
                return 26;
            }
        }
        return 0;
    }

    private static enum Mode {
        CAKE,
        BED;

    }
}

