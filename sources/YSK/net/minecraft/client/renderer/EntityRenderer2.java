package net.minecraft.client.renderer;

import java.util.concurrent.*;
import net.minecraft.client.*;

class EntityRenderer2 implements Callable
{
    private static final String[] I;
    final EntityRenderer field_90025_c;
    private static final String __OBFID;
    
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public String call() throws Exception {
        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() throws Exception {
        return this.call();
    }
    
    EntityRenderer2(final EntityRenderer field_90025_c) {
        this.field_90025_c = field_90025_c;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0001-\u0010XIrQ\u007fQMz", "BaOhy");
    }
    
    static {
        I();
        __OBFID = EntityRenderer2.I["".length()];
    }
}
