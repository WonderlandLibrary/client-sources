package a.a.a;

import java.util.*;

public final class e
{
    static {
        new Random();
    }
    
    public static int a(final int n) {
        if (n < 0) {
            return 0;
        }
        if (n > 255) {
            return 255;
        }
        return n;
    }
}
