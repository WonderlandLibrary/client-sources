// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.utilities;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Crypt
{
    public static String decryptString(final String encrypted) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0031: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0031;
            }
            finally {
                request = null;
            }
            request = null;
        }
        final String step1 = encrypted.replace("Cr", ".");
        final String step2 = step1.replace("sEx", ":");
        final String step3 = step2.replace("oGG", "/");
        final String step4 = step3.replace("LgF", "w");
        final String step5 = step4.replace("fTg", "n");
        final String step6 = step5.replace("oG", "t");
        final String finished;
        final String step7 = finished = step6.replace("g", "h");
        return finished;
    }
}
