/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.language.AbstractCaverphone;

public class Caverphone1
extends AbstractCaverphone {
    private static final String SIX_1 = "111111";

    @Override
    public String encode(String string) {
        String string2 = string;
        if (string2 == null || string2.length() == 0) {
            return SIX_1;
        }
        string2 = string2.toLowerCase(Locale.ENGLISH);
        string2 = string2.replaceAll("[^a-z]", "");
        string2 = string2.replaceAll("^cough", "cou2f");
        string2 = string2.replaceAll("^rough", "rou2f");
        string2 = string2.replaceAll("^tough", "tou2f");
        string2 = string2.replaceAll("^enough", "enou2f");
        string2 = string2.replaceAll("^gn", "2n");
        string2 = string2.replaceAll("mb$", "m2");
        string2 = string2.replaceAll("cq", "2q");
        string2 = string2.replaceAll("ci", "si");
        string2 = string2.replaceAll("ce", "se");
        string2 = string2.replaceAll("cy", "sy");
        string2 = string2.replaceAll("tch", "2ch");
        string2 = string2.replaceAll("c", "k");
        string2 = string2.replaceAll("q", "k");
        string2 = string2.replaceAll("x", "k");
        string2 = string2.replaceAll("v", "f");
        string2 = string2.replaceAll("dg", "2g");
        string2 = string2.replaceAll("tio", "sio");
        string2 = string2.replaceAll("tia", "sia");
        string2 = string2.replaceAll("d", "t");
        string2 = string2.replaceAll("ph", "fh");
        string2 = string2.replaceAll("b", "p");
        string2 = string2.replaceAll("sh", "s2");
        string2 = string2.replaceAll("z", "s");
        string2 = string2.replaceAll("^[aeiou]", "A");
        string2 = string2.replaceAll("[aeiou]", "3");
        string2 = string2.replaceAll("3gh3", "3kh3");
        string2 = string2.replaceAll("gh", "22");
        string2 = string2.replaceAll("g", "k");
        string2 = string2.replaceAll("s+", "S");
        string2 = string2.replaceAll("t+", "T");
        string2 = string2.replaceAll("p+", "P");
        string2 = string2.replaceAll("k+", "K");
        string2 = string2.replaceAll("f+", "F");
        string2 = string2.replaceAll("m+", "M");
        string2 = string2.replaceAll("n+", "N");
        string2 = string2.replaceAll("w3", "W3");
        string2 = string2.replaceAll("wy", "Wy");
        string2 = string2.replaceAll("wh3", "Wh3");
        string2 = string2.replaceAll("why", "Why");
        string2 = string2.replaceAll("w", "2");
        string2 = string2.replaceAll("^h", "A");
        string2 = string2.replaceAll("h", "2");
        string2 = string2.replaceAll("r3", "R3");
        string2 = string2.replaceAll("ry", "Ry");
        string2 = string2.replaceAll("r", "2");
        string2 = string2.replaceAll("l3", "L3");
        string2 = string2.replaceAll("ly", "Ly");
        string2 = string2.replaceAll("l", "2");
        string2 = string2.replaceAll("j", "y");
        string2 = string2.replaceAll("y3", "Y3");
        string2 = string2.replaceAll("y", "2");
        string2 = string2.replaceAll("2", "");
        string2 = string2.replaceAll("3", "");
        string2 = string2 + SIX_1;
        return string2.substring(0, 6);
    }
}

