/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import java.awt.Color;
import skizzle.modules.ModuleManager;
import skizzle.util.Colors;

public class ColourUtil {
    public ColourUtil() {
        ColourUtil Nigga;
    }

    public static {
        throw throwable;
    }

    public static int getRainbow(float Nigga, float Nigga2, float Nigga3, long Nigga4) {
        if (ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\ude74\u71ce\ue52c\u7013\u8c99\u5bef\u8c2a\ub207"))) {
            Nigga4 = -Nigga4;
        }
        float Nigga5 = (float)((System.currentTimeMillis() + Nigga4) % (long)((int)(Nigga * Float.intBitsToFloat(9.9307469E8f ^ 0x7F4B1DFF)))) / (Nigga * Float.intBitsToFloat(9.90136E8f ^ 0x7F7E46A9));
        int Nigga6 = Color.HSBtoRGB(Nigga5, Nigga2, Nigga3);
        if (ModuleManager.hudModule.color.getMode().equals(Qprot0.0("\ude64\u718d\ue50d\u7056\u8cb9\u5bfd\u8c26\ub20d\u80f2\u27ce\ud1c6"))) {
            return Colors.getColor(new Color(Nigga6).getRed());
        }
        return Nigga6;
    }

    public static double[] cleanSwitch(double Nigga) {
        Nigga *= 510.0;
        double Nigga2 = 255.0;
        double Nigga3 = 255.0;
        if (Nigga < 255.0) {
            Nigga2 = 255.0;
            Nigga3 = Nigga;
        }
        if (Nigga > 255.0) {
            Nigga2 = 255.0 - (Nigga - 255.0);
            Nigga3 = 255.0;
        }
        return new double[]{Nigga2, Nigga3};
    }
}

