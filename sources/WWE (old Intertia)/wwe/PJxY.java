package wwe;

import java.text.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;

public class PJxY
{
    public static float Npqe;
    public static long nzjS;
    public static float[] ZQOL;
    public static DecimalFormat PNlO;
    
    public PJxY() {
        super();
    }
    
    public static void sAGw(final Packet packet) {
        if (!(packet instanceof SPacketTimeUpdate)) {
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        if (PJxY.nzjS == (0x80B69F3493D7C29L ^ 0xF7F4960CB6C283D6L)) {
            PJxY.nzjS = currentTimeMillis;
            return;
        }
        float n = (float)((currentTimeMillis - PJxY.nzjS) / (0xE413B6535E33F226L ^ 0xE413B6535E33F232L));
        if (n == 0.0f) {
            n = 50.0f;
        }
        float n2 = 1000.0f / n;
        if (n2 > 20.0f) {
            n2 = 20.0f;
        }
        int i = PJxY.ZQOL.length - 1;
        while (i > 0) {
            PJxY.ZQOL[i] = PJxY.ZQOL[i - 1];
            --i;
            if (PJxY.\u200c\u200c\u200c\u200d\u200c\u2003) {
                throw null;
            }
        }
        PJxY.ZQOL[0] = n2;
        double n3 = 0.0;
        final float[] zqol = PJxY.ZQOL;
        final int length = zqol.length;
        int j = 0;
        while (j < length) {
            n3 += zqol[j];
            ++j;
            if (PJxY.\u200c\u200c\u200c\u200d\u200c\u2003) {
                throw null;
            }
        }
        double n4 = n3 / PJxY.ZQOL.length;
        if (n4 > 20.0) {
            n4 = 20.0;
        }
        PJxY.Npqe = Float.parseFloat(PJxY.PNlO.format(n4));
        PJxY.nzjS = currentTimeMillis;
    }
    
    static {
        PJxY.Npqe = 20.0f;
        PJxY.nzjS = (0x238C1460F4EFDC2CL ^ 0xDC73EB9F0B1023D3L);
        PJxY.ZQOL = new float[10];
        PJxY.PNlO = new DecimalFormat("##.0#");
    }
}
