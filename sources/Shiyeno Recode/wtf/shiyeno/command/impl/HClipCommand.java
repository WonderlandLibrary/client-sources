package wtf.shiyeno.command.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import wtf.shiyeno.command.Command;
import wtf.shiyeno.command.CommandInfo;

@CommandInfo(name = "hclip", description = "Телепортирует вас вперед.")
public class HClipCommand extends Command {
    @Override
    public void run(String[] args) throws Exception {
        Vector3d tp = Minecraft.getInstance().player.getLook(1F).mul(Double.parseDouble(args[1]), 0, Double.parseDouble(args[1]));
        Minecraft.getInstance().player.setPosition(mc.player.getPosX() + tp.getX(), mc.player.getPosY(), mc.player.getPosZ() + tp.getZ());
    }

    @Override
    public void error() {

    }
}
