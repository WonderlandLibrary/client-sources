package gq.vapu.czfclient.Command.Commands;

import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.MathUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.EnumChatFormatting;

public class VClip extends Command {
    private final TimerUtil timer = new TimerUtil();

    public VClip() {
        super("Vc", new String[]{"Vclip", "clip", "verticalclip", "clip"}, "", "Teleport down a specific ammount");
    }

    @Override
    public String execute(String[] args) {
        if (args.length > 0) {
            if (MathUtil.parsable(args[0], (byte) 4)) {
                float distance = Float.parseFloat(args[0]);
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(
                        new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX,
                                Minecraft.getMinecraft().thePlayer.posY + (double) distance,
                                Minecraft.getMinecraft().thePlayer.posZ, false));
                Minecraft.getMinecraft().thePlayer.setPosition(Minecraft.getMinecraft().thePlayer.posX,
                        Minecraft.getMinecraft().thePlayer.posY + (double) distance,
                        Minecraft.getMinecraft().thePlayer.posZ);
                Helper.sendMessage("Vclipped " + distance + " blocks");
            } else {
                Helper.sendMessage(EnumChatFormatting.GRAY + args[0] + " is not a valid number");
            }
        } else {
            Helper.sendMessage(EnumChatFormatting.GRAY + "Correct usage .vclip <number>");
        }
        return null;
    }
}
