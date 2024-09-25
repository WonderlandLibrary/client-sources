/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import net.minecraft.client.Minecraft;
import skizzle.Client;

public class RotationUtil {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static float[] fixedSensitivity(float Nigga, float Nigga2, float Nigga3) {
        float Nigga4 = Nigga * Float.intBitsToFloat(1.10157325E9f ^ 0x7EB13525) + Float.intBitsToFloat(1.09418074E9f ^ 0x7F7B135F);
        float Nigga5 = Nigga4 * Nigga4 * Nigga4 * Float.intBitsToFloat(1.0827159E9f ^ 0x7F117774);
        float Nigga6 = Client.serverRotations[0];
        float Nigga7 = Client.serverRotations[1];
        float Nigga8 = Nigga2 - Nigga6;
        Nigga8 -= Nigga8 % Nigga5;
        float Nigga9 = Nigga6 + Nigga8;
        float Nigga10 = Nigga3 - Nigga7;
        Nigga10 -= Nigga10 % Nigga5;
        float Nigga11 = Nigga7 + Nigga10;
        return new float[]{Nigga9, Nigga11};
    }

    public RotationUtil() {
        RotationUtil Nigga;
    }
}

