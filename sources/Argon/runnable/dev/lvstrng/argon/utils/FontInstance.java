// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import java.awt.*;
import java.io.IOException;

public final class FontInstance {
    public static FontUtil field633;

    static {
        try {
            field633 = FontUtil.method564("ok", (int) (0x272E6E7B4D128C7FL ^ 0x6D7C4D128C57L), false, false, false);
        } catch (IOException | FontFormatException e) {

        }
    }

    /*static {
        final char[] charArray = "*pmHC\u0005#*xsVC\u00159de{W_\u00171ve1]I\u001f$*wqUR_$qw".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0119:
            {
                if (n > 1) {
                    break Label_0119;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    charArray[length] = (char) (c ^ switch (n3 % 7) {
                        case 0 -> '\u0005';
                        case 1 -> '\u0011';
                        case 2 -> '\u001e';
                        case 3 -> ';';
                        case 4 -> '&';
                        case 5 -> 'q';
                        default -> 'P';
                    });
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                FontInstance.field633 =                 return;
            }
            continue;
        }
    }*/
}
