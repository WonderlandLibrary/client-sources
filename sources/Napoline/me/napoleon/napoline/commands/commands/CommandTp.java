package me.napoleon.napoline.commands.commands;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.utils.player.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class CommandTp extends Command {
    public CommandTp() {
        super("tp", "tp", "teleport");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 3) {
            PlayerUtil.sendMessage(".tp <x> <y> <z>");
            return;
        }
        int a = 0;
        while (a < 20) {
            Napoline.mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]),Napoline.mc.thePlayer.onGround));
            Napoline.mc.thePlayer.setPosition(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            a += 1;
        }
        PlayerUtil.sendMessage("Teleported");
        Napoline.mc.renderGlobal.loadRenderers();
    }
}
