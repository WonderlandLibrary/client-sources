// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;
import de.liquiddev.ircclient.api.SimpleIrcApi;

public class IrcChatListener extends SimpleIrcApi
{
    @Override
    public void addChat(final String message) {
        print(message.replace("", ""));
    }
    
    public static void print(final String msg) {
        try {
            final Minecraft mc = Minecraft.getMinecraft();
            if (mc.theWorld != null && msg != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText(msg));
            }
        }
        catch (Exception ex) {
            System.out.println("Error by printing message: " + msg);
        }
    }
}
