package me.rich.helpers.command.impl;

import me.rich.Main;
import me.rich.helpers.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class Clip extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public Clip() {
        super("Clip", new String[]{"vclip", "hclip"});
    }

    @Override
    public void onCommand(String[] args) {
        if(args.length == 2 && args[0].equalsIgnoreCase("vclip")) {
            try {
                Main.msg("vclipped " + Double.valueOf(args[1]) + " blocks.", true);
                mc.player.setPosition(mc.player.posX, mc.player.posY + Double.valueOf(args[1]), mc.player.posZ);
                mc.getConnection().sendPacket(new CPacketPlayer(true));
            } catch(Exception formatException){
            	Main.msg("invalid message.", true);
                formatException.printStackTrace();
            }
        } else if(args.length == 2 && args[0].equalsIgnoreCase("hclip")){
            try {
            	Main.msg("hclipped " + Double.valueOf(args[1]) + " blocks.", true);
                float f = mc.player.rotationYaw * 0.017453292F;
                double speed = Double.valueOf(args[1]);
                double x = -(MathHelper.sin(f) * speed);
                double z = MathHelper.cos(f) * speed;
                mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
            } catch(Exception formatException){
            	Main.msg("invalid message.", true);
                formatException.printStackTrace();
            }
        }
    }
}