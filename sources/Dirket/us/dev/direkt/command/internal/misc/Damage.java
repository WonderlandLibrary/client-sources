package us.dev.direkt.command.internal.misc;

import net.minecraft.network.play.client.CPacketPlayer;
import us.dev.direkt.Direkt;
import us.dev.direkt.Wrapper;
import us.dev.direkt.command.Command;
import us.dev.direkt.command.handler.annotations.Executes;

/**
 * @author Foundry
 */
public class Damage extends Command {
    public Damage() {
        super(Direkt.getInstance().getCommandManager(), "damage", "dmg");
    }

    @Executes
    public void run() {
        Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY, Wrapper.getPlayer().posZ, false));
        for (int i = 0; i < 25; i++) {
            Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY - 0.0625, Wrapper.getPlayer().posZ, false));
            Wrapper.sendPacketDirect(new CPacketPlayer.Position(Wrapper.getPlayer().posX, Wrapper.getPlayer().posY + 0.0625, Wrapper.getPlayer().posZ, false));
        }
        Wrapper.sendPacketDirect(new CPacketPlayer(true));
    }

}
