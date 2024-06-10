// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.util.StringUtils;
import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.MouseClicked;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.Logger;

public class MidClick extends Module
{
    public MidClick() {
        super("MidClick", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof MouseClicked) {
            final MouseClicked mouse = (MouseClicked)e;
            if (mouse.getButton() == 2 && MidClick.mc.objectMouseOver.entityHit != null && MidClick.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)MidClick.mc.objectMouseOver.entityHit;
                if (!Client.getFriendManager().isFriend(StringUtils.stripControlCodes(player.getName()))) {
                    Client.getFriendManager().addFriend(player.getName(), player.getName());
                    Logger.writeChat("Friend " + player.getName() + " added with the alias of " + player.getName() + ".");
                    if (Client.getFileManager().getFileByName("friendsconfiguration") != null) {
                        Client.getFileManager().getFileByName("friendsconfiguration").saveFile();
                    }
                }
                else {
                    Client.getFriendManager().removeFriend(player.getName());
                    Logger.writeChat("Friend " + player.getName() + " removed.");
                    if (Client.getFileManager().getFileByName("friendsconfiguration") != null) {
                        Client.getFileManager().getFileByName("friendsconfiguration").saveFile();
                    }
                }
            }
        }
    }
}
