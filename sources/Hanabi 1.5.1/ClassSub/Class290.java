package cn.Hanabi.modules.Player;

import java.util.*;

public class Class290
{
    final Spammer this$0;
    
    
    public Class290(final Spammer this$0) {
        ((Class290)this).this$0 = this$0;
    }
    
    public String getStringRandom(final int n) {
        String s = "";
        final Random random = new Random();
        for (int i = 0; i < n; ++i) {
            final String s2 = (random.nextInt(2) % 2 == 0) ? "char" : "num";
            if ("char".equalsIgnoreCase(s2)) {
                s += (char)(random.nextInt(26) + ((random.nextInt(2) % 2 == 0) ? 65 : 97));
            }
            else if ("num".equalsIgnoreCase(s2)) {
                s += String.valueOf(random.nextInt(10));
            }
        }
        return s;
    }
}
