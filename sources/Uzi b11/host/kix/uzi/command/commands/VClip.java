package host.kix.uzi.command.commands;

import host.kix.uzi.command.Command;
import net.minecraft.client.Minecraft;

/**
 * Created by k1x on 4/23/17.
 */
public class VClip extends Command {

    public VClip() {
        super("VClip", "Allows the user to clip through roofs!", "vclip");
    }

    @Override
    public void dispatch(String message) {
        int distance = Integer.parseInt(message.split(" ")[1]);
        Minecraft.getMinecraft().thePlayer.setPositionAndUpdate(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + distance, Minecraft.getMinecraft().thePlayer.posZ);
    }
}
