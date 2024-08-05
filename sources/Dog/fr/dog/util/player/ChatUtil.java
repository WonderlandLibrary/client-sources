package fr.dog.util.player;

import fr.dog.Dog;
import fr.dog.util.InstanceAccess;
import lombok.experimental.UtilityClass;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;


@UtilityClass
public class ChatUtil implements InstanceAccess {
    public void display(final Object message) {
        mc.thePlayer.addChatMessage(new ChatComponentText("§f[" + Dog.getInstance().getThemeManager().getCurrentTheme().chatFormatting + "Dog Client§f]§8 > §7" + message));
    }
    public void ircAnnouncements(String mess){
        if(mc.thePlayer != null){
            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "IRC ANNOUNCEMENT " + EnumChatFormatting.GRAY + " > " + EnumChatFormatting.WHITE + mess));
        }

    }
    public void irc(String mess){

        if(mc.thePlayer != null) {
            if(mess.toLowerCase().contains("@" + Dog.getInstance().getUsername().toLowerCase())){

            }


            mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "IRC" + EnumChatFormatting.GRAY + " > " + EnumChatFormatting.WHITE + mess));
        }
    }

}
