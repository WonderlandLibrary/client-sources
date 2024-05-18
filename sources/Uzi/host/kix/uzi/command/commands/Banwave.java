package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by myche on 2/25/2017.
 */
public class Banwave extends Command {

    public Banwave() {
        super("banwave", "A basic command that bans every player on the server other than you.", "ban", "blacklist", "wave", "banall");
    }


    @Override
    public void dispatch(String message) {
        String abuse = "/ban";
        for (Object o : Minecraft.getMinecraft().theWorld.playerEntities) {
            if (o instanceof EntityPlayer) {
                EntityPlayer entity = (EntityPlayer) o;
                String playername = entity.getName();
                if (isUsernameValid(playername)) {
                    Minecraft.getMinecraft().thePlayer.sendChatMessage(String.format("%s %s", abuse, playername));
                }
            }
        }
    }

    private boolean isUsernameValid(String username) {
        if (username.equalsIgnoreCase(Minecraft.getMinecraft().getSession().getUsername()) || !Uzi.getInstance().getFriendManager().get(username).isPresent())
            return false;
        return true;
    }

}
