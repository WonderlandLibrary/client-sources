// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.utilities.Logger;
import net.minecraft.util.StringUtils;
import net.andrewsnetwork.icarus.Icarus;
import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.events.MouseClicked;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class MidClick extends Module
{
    public MidClick() {
        super("MidClick", Category.MISC);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof MouseClicked) {
            Label_0047: {
                if (e instanceof EatMyAssYouFuckingDecompiler) {
                    OutputStreamWriter request = new OutputStreamWriter(System.out);
                    try {
                        request.flush();
                    }
                    catch (IOException ex) {
                        break Label_0047;
                    }
                    finally {
                        request = null;
                    }
                    request = null;
                }
            }
            final MouseClicked mouse = (MouseClicked)e;
            if (mouse.getButton() == 2 && MidClick.mc.objectMouseOver.entityHit != null && MidClick.mc.objectMouseOver.entityHit instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer)MidClick.mc.objectMouseOver.entityHit;
                if (!Icarus.getFriendManager().isFriend(StringUtils.stripControlCodes(player.getName()))) {
                    Icarus.getFriendManager().addFriend(player.getName(), player.getName());
                    Logger.writeChat("Friend " + player.getName() + " added with the alias of " + player.getName() + ".");
                    if (Icarus.getFileManager().getFileByName("friendsconfiguration") != null) {
                        Icarus.getFileManager().getFileByName("friendsconfiguration").saveFile();
                    }
                }
                else {
                    Icarus.getFriendManager().removeFriend(player.getName());
                    Logger.writeChat("Friend " + player.getName() + " removed.");
                    if (Icarus.getFileManager().getFileByName("friendsconfiguration") != null) {
                        Icarus.getFileManager().getFileByName("friendsconfiguration").saveFile();
                    }
                }
            }
        }
    }
}
