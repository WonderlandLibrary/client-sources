package YSK;

import net.minecraft.network.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;

public class ChatMSG
{
    private static final String[] I;
    private static String clientname;
    
    public static void sendPacket(final Packet packet) {
        player().sendQueue.addToSendQueue(packet);
    }
    
    public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    public static void msg(String string, final boolean b) {
        String string2;
        if (b) {
            string2 = ChatMSG.I[" ".length()] + getClientName() + ChatMSG.I["  ".length()];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            string2 = ChatMSG.I["   ".length()];
        }
        string = String.valueOf(string2) + string;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(string.replace(ChatMSG.I[0x62 ^ 0x66], ChatMSG.I[0x28 ^ 0x2D]), new Object["".length()]));
    }
    
    public static EntityPlayerSP player() {
        return mc().thePlayer;
    }
    
    public static String getClientName() {
        return ChatMSG.clientname;
    }
    
    static {
        I();
        ChatMSG.clientname = ChatMSG.I["".length()];
    }
    
    private static void I() {
        (I = new String[0x79 ^ 0x7F])["".length()] = I("\r\u0000/", "TSdHr");
        ChatMSG.I[" ".length()] = I("~\u0016L^5~BL\u0005", "Xpjin");
        ChatMSG.I["  ".length()] = I("E\u0004p]\u0004CD$", "cbVjY");
        ChatMSG.I["   ".length()] = I("", "nIBwu");
        ChatMSG.I[0x59 ^ 0x5D] = I("d", "BXHkp");
        ChatMSG.I[0x4C ^ 0x49] = I("\u00d2", "ulhyz");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
