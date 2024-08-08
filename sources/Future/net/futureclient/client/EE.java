package net.futureclient.client;

import java.awt.Font;
import java.util.StringJoiner;
import java.awt.GraphicsEnvironment;

public class EE extends XB
{
    public EE() {
        super(new String[] { "Font", "SetFont", "CustomFont", "SetCustomFont", "Fond", "SetFond" });
    }
    
    @Override
    public String M(final String[] array) {
        final gD gd;
        if ((gd = (gD)pg.M().M().M((Class)gD.class)) == null) {
            return null;
        }
        final String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        if (array.length < 1) {
            return null;
        }
        final String lowerCase = array[0].toLowerCase();
        int n = -1;
        int n2 = 0;
        Label_0141: {
            switch (lowerCase.hashCode()) {
                case 3322014:
                    if (lowerCase.equals("list")) {
                        n2 = 0;
                        break Label_0141;
                    }
                    break;
                case 109780401:
                    if (lowerCase.equals("style")) {
                        n2 = 1;
                        break Label_0141;
                    }
                    break;
                case 3530753:
                    if (lowerCase.equals("size")) {
                        n = 2;
                        break;
                    }
                    break;
            }
            n2 = n;
        }
        switch (n2) {
            case 0: {
                final StringJoiner stringJoiner = new StringJoiner(", ");
                final String[] array2;
                final int length = (array2 = availableFontFamilyNames).length;
                int i = 0;
                int n3 = 0;
                while (i < length) {
                    final String s = array2[n3];
                    ++n3;
                    stringJoiner.add(s);
                    i = n3;
                }
                return String.format("Fonts (%s): %s.", availableFontFamilyNames.length, stringJoiner.toString());
            }
            case 1: {
                if (array.length != 2) {
                    return "No font style entered. Possible options: PLAIN, BOLD, ITALIC, or BOLD+ITALIC.";
                }
                final String upperCase;
                final String s2 = upperCase = array[1].toUpperCase();
                int n4 = -1;
                int n5 = 0;
                Label_0394: {
                    switch (upperCase.hashCode()) {
                        case 76210602:
                            if (upperCase.equals("PLAIN")) {
                                n5 = 0;
                                break Label_0394;
                            }
                            break;
                        case 2044549:
                            if (upperCase.equals("BOLD")) {
                                n5 = 1;
                                break Label_0394;
                            }
                            break;
                        case -2125451728:
                            if (upperCase.equals("ITALIC")) {
                                n5 = 2;
                                break Label_0394;
                            }
                            break;
                        case -977469450:
                            if (upperCase.equals("BOLD+ITALIC")) {
                                n4 = 3;
                                break;
                            }
                            break;
                    }
                    n5 = n4;
                }
                int n6 = 0;
                gD gd2 = null;
                switch (n5) {
                    case 0:
                        n6 = 0;
                        gd2 = gd;
                        break;
                    case 1:
                        n6 = 1;
                        gd2 = gd;
                        break;
                    case 2:
                        n6 = 2;
                        gd2 = gd;
                        break;
                    case 3:
                        n6 = 3;
                        gd2 = gd;
                        break;
                    default:
                        return "Invalid font style entered. Possible options: PLAIN, BOLD, ITALIC, or BOLD+ITALIC.";
                }
                gd2.p = new KH(new Font(gd.p.M().getName(), n6, gd.p.M().getSize()), true, true);
                return String.format("Font style been set to %s.", s2);
            }
            case 2: {
                if (array.length != 2) {
                    return "No font size entered.";
                }
                final String s3 = array[1];
                int int1;
                int n7;
                try {
                    n7 = (int1 = Integer.parseInt(s3));
                }
                catch (NumberFormatException ex) {
                    return String.format("Invalid font size entered, input must be a number: %s.", ex.getMessage());
                }
                if (int1 < 5 || n7 > 30) {
                    return String.format("Font size cannot be %s than %s.", (n7 < 5) ? "smaller" : "larger", (n7 < 5) ? 5 : 30);
                }
                gd.p = new KH(new Font(gd.p.M().getName(), gd.p.M().getStyle(), n7), true, true);
                return String.format("Font size has been set to %s.", n7);
            }
            default: {
                final StringBuilder sb = new StringBuilder();
                final int length2 = array.length;
                int j = 0;
                int n8 = 0;
                while (j < length2) {
                    sb.append(array[n8]);
                    final StringBuilder sb2 = sb;
                    ++n8;
                    sb2.append(" ");
                    j = n8;
                }
                final String s4 = (array.length == 1) ? array[0].toLowerCase() : sb.toString().trim().toLowerCase();
                final String[] array3;
                final int length3 = (array3 = availableFontFamilyNames).length;
                int k = 0;
                int n9 = 0;
                while (k < length3) {
                    final String s5;
                    if ((s5 = array3[n9]).toLowerCase().equals(s4)) {
                        final String s6 = "keC~\rbLy\rhHoC*^oY*Ye\r/^$";
                        gd.p = new KH(new Font(s5, gd.p.M().getStyle(), gd.p.M().getSize()), true, true);
                        return String.format(uh.M(s6), s5);
                    }
                    k = ++n9;
                }
                final String lowerCase2 = s4.replaceAll(" |_|-|'", "").toLowerCase();
                final String[] array4;
                final int length4 = (array4 = availableFontFamilyNames).length;
                int l = 0;
                int n10 = 0;
                while (l < length4) {
                    final String s7;
                    if ((s7 = array4[n10]).replaceAll(" |_|-|'", "").toLowerCase().contains(lowerCase2)) {
                        final String s8 = "e\u000bM\u0010\u0003\fB\u0017\u0003\u0006F\u0001MDP\u0001WDW\u000b\u0003APJ";
                        gd.p = new KH(new Font(s7, gd.p.M().getStyle(), gd.p.M().getSize()), true, true);
                        return String.format(XB.M(s8), s7);
                    }
                    l = ++n10;
                }
                return "That font doesn't exist.";
            }
        }
    }
    
    @Override
    public String M() {
        return "&e[font|style|size|list]";
    }
}
