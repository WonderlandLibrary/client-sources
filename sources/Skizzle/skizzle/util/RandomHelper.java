/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import java.util.Random;

public class RandomHelper {
    public static boolean randomBoolean() {
        return RandomHelper.randomInt(0, 1) == 1;
    }

    public static {
        throw throwable;
    }

    public static int randomInt(int Nigga, int Nigga2) {
        int Nigga3 = Nigga2 - Nigga;
        return (int)Math.round(Math.random() * (double)Nigga3 + (double)Nigga);
    }

    public static String randomString(int Nigga) {
        String Nigga2 = Qprot0.0("\ueb80\u71e9\ud0f6\uf706\u1515\u6e3d\u8c08\u87c4\u07ed\ube50\ue41d\uaf20\u691a\u22a5\u6126\u7794\u42d8\u5dfc\u47f5\u0a45\u1252\u01fc\ufbcb\ub892\ud2ae\u569c\u2f1f\u3e85\u1294\u9faa\u0a45\u8813\u6659\ufde4\u0ff4\uc995\uddb4\u8131\u1a3f\u31bb\ue8b2\u3c4d\uf379\u691d\uf5b0\u4d81\ua15b\ud84b\u40ff\u56a5\uc73c\uf444\u56df\u602f\u40d0\ucefc\u691e\ua744\ue520\ub023\u41b7\u09c2");
        StringBuilder Nigga3 = new StringBuilder();
        Random Nigga4 = new Random();
        while (Nigga3.length() < Nigga) {
            int Nigga5 = (int)(Nigga4.nextFloat() * (float)Nigga2.length());
            Nigga3.append(Nigga2.charAt(Nigga5));
        }
        String Nigga6 = Nigga3.toString();
        return Nigga6;
    }

    public static double randomDouble(double Nigga, double Nigga2) {
        double Nigga3 = Nigga2 - Nigga;
        return (double)((int)(Math.random() * Nigga3)) + Nigga;
    }

    public static boolean randomChance(double Nigga) {
        double Nigga2 = RandomHelper.randomDouble(0.0, 100.0) / 100.0;
        return Nigga2 <= Nigga;
    }

    public static String randomName() {
        String Nigga = "";
        if (RandomHelper.randomBoolean()) {
            String[] Nigga2 = new String[]{Qprot0.0("\ueba6\u71ca\ud0d8\u1c7d\ubde8"), Qprot0.0("\uebaa\u71c2\ud0d1"), Qprot0.0("\ueba3\u71c4\ud0cc"), Qprot0.0("\ueba4\u71cc\ud0dc\u1c6a\ubdf6"), Qprot0.0("\ueba4\u71c9\ud0da\u1c61"), Qprot0.0("\uebb3\u71ca\ud0db\u1c7c\ubdf5"), Qprot0.0("\ueba9\u71ca\ud0d6\u1c73\ubdff\u6e09"), Qprot0.0("\uebb2\u71c0\ud0dc\u1c62\ubde0\u6e17\u8c2a")};
            String Nigga3 = Nigga2[RandomHelper.randomInt(0, Nigga2.length - 1)];
            Nigga = String.valueOf(Nigga3) + RandomHelper.randomInt(0, 1000) + RandomHelper.randomString(3);
        } else {
            String[] Nigga4 = new String[]{Qprot0.0("\uebb3\u71ce\ud0d8"), Qprot0.0("\uebad\u71c4"), Qprot0.0("\ueba9\u71ca\ud0c5"), Qprot0.0("\uebab\u71c4\ud0de"), Qprot0.0("\ueba9\u71ce"), Qprot0.0("\ueba4\u71cc"), Qprot0.0("\uebac\u71ca\ud0dd"), Qprot0.0("\uebad\u71ca"), Qprot0.0("\uebb0\u71ce\ud0c1"), Qprot0.0("\ueba0\u71de\ud0c7\u1c6c"), Qprot0.0("\ueba0\u71c0\ud0c1\u1c6a\ubdef"), Qprot0.0("\ueba8\u71cf\ud0da\u1c79"), Qprot0.0("\ueba9\u71c6\ud0d4\u1c71"), Qprot0.0("\ueba5\u71c6\ud0d4\u1c71\ubde8\u6e15"), Qprot0.0("\ueba9\u71ce\ud0db")};
            String[] Nigga5 = new String[]{Qprot0.0("\uebae\u71c7\ud0cc"), Qprot0.0("\uebaf\u71c2\ud0c1\u1c61"), Qprot0.0("\ueba9\u71ce\ud0c5"), Qprot0.0("\uebaa\u71c4"), Qprot0.0("\ueba6\u71c4\ud0c1"), Qprot0.0("\uebac\u71ce\ud0de"), Qprot0.0("\uebad\u71c4\ud0d9\u1c79"), Qprot0.0("\ueba9\u71ce\ud0d4"), Qprot0.0("\ueba9\u71ca\ud0d8"), Qprot0.0("\ueba2\u71c4\ud0c2"), Qprot0.0("\ueba6\u71c6\ud0d4"), Qprot0.0("\ueba5\u71c5\ud0d4"), Qprot0.0("\uebb5\u71ca\ud0dc")};
            String Nigga6 = Nigga4[RandomHelper.randomInt(0, Nigga4.length - 1)];
            String Nigga7 = Nigga5[RandomHelper.randomInt(0, Nigga5.length - 1)];
            Nigga = String.valueOf(Nigga6) + Nigga7 + RandomHelper.randomInt(0, 1000) + RandomHelper.randomString(3);
        }
        if (Nigga.length() > 16) {
            return RandomHelper.randomName();
        }
        return Nigga;
    }

    public RandomHelper() {
        RandomHelper Nigga;
    }
}

