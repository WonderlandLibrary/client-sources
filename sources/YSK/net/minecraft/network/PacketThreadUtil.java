package net.minecraft.network;

import net.minecraft.util.*;

public class PacketThreadUtil
{
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
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> packet, final T t, final IThreadListener threadListener) throws ThreadQuickExitException {
        if (!threadListener.isCallingFromMinecraftThread()) {
            threadListener.addScheduledTask(new Runnable(packet, t) {
                private final Packet val$p_180031_0_;
                private final INetHandler val$p_180031_1_;
                
                @Override
                public void run() {
                    this.val$p_180031_0_.processPacket(this.val$p_180031_1_);
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
                        if (2 < 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }
}
