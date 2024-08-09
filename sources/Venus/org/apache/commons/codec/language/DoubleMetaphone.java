/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.util.Locale;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.StringUtils;

public class DoubleMetaphone
implements StringEncoder {
    private static final String VOWELS = "AEIOUY";
    private static final String[] SILENT_START = new String[]{"GN", "KN", "PN", "WR", "PS"};
    private static final String[] L_R_N_M_B_H_F_V_W_SPACE = new String[]{"L", "R", "N", "M", "B", "H", "F", "V", "W", " "};
    private static final String[] ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER = new String[]{"ES", "EP", "EB", "EL", "EY", "IB", "IL", "IN", "IE", "EI", "ER"};
    private static final String[] L_T_K_S_N_M_B_Z = new String[]{"L", "T", "K", "S", "N", "M", "B", "Z"};
    private int maxCodeLen = 4;

    public String doubleMetaphone(String string) {
        return this.doubleMetaphone(string, true);
    }

    public String doubleMetaphone(String string, boolean bl) {
        if ((string = this.cleanInput(string)) == null) {
            return null;
        }
        boolean bl2 = this.isSlavoGermanic(string);
        int n = this.isSilentStart(string) ? 1 : 0;
        DoubleMetaphoneResult doubleMetaphoneResult = new DoubleMetaphoneResult(this, this.getMaxCodeLen());
        block25: while (!doubleMetaphoneResult.isComplete() && n <= string.length() - 1) {
            switch (string.charAt(n)) {
                case 'A': 
                case 'E': 
                case 'I': 
                case 'O': 
                case 'U': 
                case 'Y': {
                    n = this.handleAEIOUY(doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'B': {
                    doubleMetaphoneResult.append('P');
                    n = this.charAt(string, n + 1) == 'B' ? n + 2 : n + 1;
                    continue block25;
                }
                case '\u00c7': {
                    doubleMetaphoneResult.append('S');
                    ++n;
                    continue block25;
                }
                case 'C': {
                    n = this.handleC(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'D': {
                    n = this.handleD(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'F': {
                    doubleMetaphoneResult.append('F');
                    n = this.charAt(string, n + 1) == 'F' ? n + 2 : n + 1;
                    continue block25;
                }
                case 'G': {
                    n = this.handleG(string, doubleMetaphoneResult, n, bl2);
                    continue block25;
                }
                case 'H': {
                    n = this.handleH(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'J': {
                    n = this.handleJ(string, doubleMetaphoneResult, n, bl2);
                    continue block25;
                }
                case 'K': {
                    doubleMetaphoneResult.append('K');
                    n = this.charAt(string, n + 1) == 'K' ? n + 2 : n + 1;
                    continue block25;
                }
                case 'L': {
                    n = this.handleL(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'M': {
                    doubleMetaphoneResult.append('M');
                    n = this.conditionM0(string, n) ? n + 2 : n + 1;
                    continue block25;
                }
                case 'N': {
                    doubleMetaphoneResult.append('N');
                    n = this.charAt(string, n + 1) == 'N' ? n + 2 : n + 1;
                    continue block25;
                }
                case '\u00d1': {
                    doubleMetaphoneResult.append('N');
                    ++n;
                    continue block25;
                }
                case 'P': {
                    n = this.handleP(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'Q': {
                    doubleMetaphoneResult.append('K');
                    n = this.charAt(string, n + 1) == 'Q' ? n + 2 : n + 1;
                    continue block25;
                }
                case 'R': {
                    n = this.handleR(string, doubleMetaphoneResult, n, bl2);
                    continue block25;
                }
                case 'S': {
                    n = this.handleS(string, doubleMetaphoneResult, n, bl2);
                    continue block25;
                }
                case 'T': {
                    n = this.handleT(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'V': {
                    doubleMetaphoneResult.append('F');
                    n = this.charAt(string, n + 1) == 'V' ? n + 2 : n + 1;
                    continue block25;
                }
                case 'W': {
                    n = this.handleW(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'X': {
                    n = this.handleX(string, doubleMetaphoneResult, n);
                    continue block25;
                }
                case 'Z': {
                    n = this.handleZ(string, doubleMetaphoneResult, n, bl2);
                    continue block25;
                }
            }
            ++n;
        }
        return bl ? doubleMetaphoneResult.getAlternate() : doubleMetaphoneResult.getPrimary();
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("DoubleMetaphone encode parameter is not of type String");
        }
        return this.doubleMetaphone((String)object);
    }

    @Override
    public String encode(String string) {
        return this.doubleMetaphone(string);
    }

    public boolean isDoubleMetaphoneEqual(String string, String string2) {
        return this.isDoubleMetaphoneEqual(string, string2, true);
    }

    public boolean isDoubleMetaphoneEqual(String string, String string2, boolean bl) {
        return StringUtils.equals(this.doubleMetaphone(string, bl), this.doubleMetaphone(string2, bl));
    }

    public int getMaxCodeLen() {
        return this.maxCodeLen;
    }

    public void setMaxCodeLen(int n) {
        this.maxCodeLen = n;
    }

    private int handleAEIOUY(DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('A');
        }
        return n + 1;
    }

    private int handleC(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.conditionC0(string, n)) {
            doubleMetaphoneResult.append('K');
            n += 2;
        } else if (n == 0 && DoubleMetaphone.contains(string, n, 6, "CAESAR")) {
            doubleMetaphoneResult.append('S');
            n += 2;
        } else if (DoubleMetaphone.contains(string, n, 2, "CH")) {
            n = this.handleCH(string, doubleMetaphoneResult, n);
        } else if (DoubleMetaphone.contains(string, n, 2, "CZ") && !DoubleMetaphone.contains(string, n - 2, 4, "WICZ")) {
            doubleMetaphoneResult.append('S', 'X');
            n += 2;
        } else if (DoubleMetaphone.contains(string, n + 1, 3, "CIA")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        } else {
            if (DoubleMetaphone.contains(string, n, 2, "CC") && (n != 1 || this.charAt(string, 0) != 'M')) {
                return this.handleCC(string, doubleMetaphoneResult, n);
            }
            if (DoubleMetaphone.contains(string, n, 2, "CK", "CG", "CQ")) {
                doubleMetaphoneResult.append('K');
                n += 2;
            } else if (DoubleMetaphone.contains(string, n, 2, "CI", "CE", "CY")) {
                if (DoubleMetaphone.contains(string, n, 3, "CIO", "CIE", "CIA")) {
                    doubleMetaphoneResult.append('S', 'X');
                } else {
                    doubleMetaphoneResult.append('S');
                }
                n += 2;
            } else {
                doubleMetaphoneResult.append('K');
                n = DoubleMetaphone.contains(string, n + 1, 2, " C", " Q", " G") ? (n += 3) : (DoubleMetaphone.contains(string, n + 1, 1, "C", "K", "Q") && !DoubleMetaphone.contains(string, n + 1, 2, "CE", "CI") ? (n += 2) : ++n);
            }
        }
        return n;
    }

    private int handleCC(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (DoubleMetaphone.contains(string, n + 2, 1, "I", "E", "H") && !DoubleMetaphone.contains(string, n + 2, 2, "HU")) {
            if (n == 1 && this.charAt(string, n - 1) == 'A' || DoubleMetaphone.contains(string, n - 1, 5, "UCCEE", "UCCES")) {
                doubleMetaphoneResult.append("KS");
            } else {
                doubleMetaphoneResult.append('X');
            }
            n += 3;
        } else {
            doubleMetaphoneResult.append('K');
            n += 2;
        }
        return n;
    }

    private int handleCH(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n > 0 && DoubleMetaphone.contains(string, n, 4, "CHAE")) {
            doubleMetaphoneResult.append('K', 'X');
            return n + 2;
        }
        if (this.conditionCH0(string, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (this.conditionCH1(string, n)) {
            doubleMetaphoneResult.append('K');
            return n + 2;
        }
        if (n > 0) {
            if (DoubleMetaphone.contains(string, 0, 2, "MC")) {
                doubleMetaphoneResult.append('K');
            } else {
                doubleMetaphoneResult.append('X', 'K');
            }
        } else {
            doubleMetaphoneResult.append('X');
        }
        return n + 2;
    }

    private int handleD(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (DoubleMetaphone.contains(string, n, 2, "DG")) {
            if (DoubleMetaphone.contains(string, n + 2, 1, "I", "E", "Y")) {
                doubleMetaphoneResult.append('J');
                n += 3;
            } else {
                doubleMetaphoneResult.append("TK");
                n += 2;
            }
        } else if (DoubleMetaphone.contains(string, n, 2, "DT", "DD")) {
            doubleMetaphoneResult.append('T');
            n += 2;
        } else {
            doubleMetaphoneResult.append('T');
            ++n;
        }
        return n;
    }

    private int handleG(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n, boolean bl) {
        if (this.charAt(string, n + 1) == 'H') {
            n = this.handleGH(string, doubleMetaphoneResult, n);
        } else if (this.charAt(string, n + 1) == 'N') {
            if (n == 1 && this.isVowel(this.charAt(string, 0)) && !bl) {
                doubleMetaphoneResult.append("KN", "N");
            } else if (!DoubleMetaphone.contains(string, n + 2, 2, "EY") && this.charAt(string, n + 1) != 'Y' && !bl) {
                doubleMetaphoneResult.append("N", "KN");
            } else {
                doubleMetaphoneResult.append("KN");
            }
            n += 2;
        } else if (DoubleMetaphone.contains(string, n + 1, 2, "LI") && !bl) {
            doubleMetaphoneResult.append("KL", "L");
            n += 2;
        } else if (n == 0 && (this.charAt(string, n + 1) == 'Y' || DoubleMetaphone.contains(string, n + 1, 2, ES_EP_EB_EL_EY_IB_IL_IN_IE_EI_ER))) {
            doubleMetaphoneResult.append('K', 'J');
            n += 2;
        } else if (!(!DoubleMetaphone.contains(string, n + 1, 2, "ER") && this.charAt(string, n + 1) != 'Y' || DoubleMetaphone.contains(string, 0, 6, "DANGER", "RANGER", "MANGER") || DoubleMetaphone.contains(string, n - 1, 1, "E", "I") || DoubleMetaphone.contains(string, n - 1, 3, "RGY", "OGY"))) {
            doubleMetaphoneResult.append('K', 'J');
            n += 2;
        } else if (DoubleMetaphone.contains(string, n + 1, 1, "E", "I", "Y") || DoubleMetaphone.contains(string, n - 1, 4, "AGGI", "OGGI")) {
            if (DoubleMetaphone.contains(string, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(string, 0, 3, "SCH") || DoubleMetaphone.contains(string, n + 1, 2, "ET")) {
                doubleMetaphoneResult.append('K');
            } else if (DoubleMetaphone.contains(string, n + 1, 3, "IER")) {
                doubleMetaphoneResult.append('J');
            } else {
                doubleMetaphoneResult.append('J', 'K');
            }
            n += 2;
        } else if (this.charAt(string, n + 1) == 'G') {
            n += 2;
            doubleMetaphoneResult.append('K');
        } else {
            ++n;
            doubleMetaphoneResult.append('K');
        }
        return n;
    }

    private int handleGH(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n > 0 && !this.isVowel(this.charAt(string, n - 1))) {
            doubleMetaphoneResult.append('K');
            n += 2;
        } else if (n == 0) {
            if (this.charAt(string, n + 2) == 'I') {
                doubleMetaphoneResult.append('J');
            } else {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        } else if (n > 1 && DoubleMetaphone.contains(string, n - 2, 1, "B", "H", "D") || n > 2 && DoubleMetaphone.contains(string, n - 3, 1, "B", "H", "D") || n > 3 && DoubleMetaphone.contains(string, n - 4, 1, "B", "H")) {
            n += 2;
        } else {
            if (n > 2 && this.charAt(string, n - 1) == 'U' && DoubleMetaphone.contains(string, n - 3, 1, "C", "G", "L", "R", "T")) {
                doubleMetaphoneResult.append('F');
            } else if (n > 0 && this.charAt(string, n - 1) != 'I') {
                doubleMetaphoneResult.append('K');
            }
            n += 2;
        }
        return n;
    }

    private int handleH(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if ((n == 0 || this.isVowel(this.charAt(string, n - 1))) && this.isVowel(this.charAt(string, n + 1))) {
            doubleMetaphoneResult.append('H');
            n += 2;
        } else {
            ++n;
        }
        return n;
    }

    private int handleJ(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n, boolean bl) {
        if (DoubleMetaphone.contains(string, n, 4, "JOSE") || DoubleMetaphone.contains(string, 0, 4, "SAN ")) {
            if (n == 0 && this.charAt(string, n + 4) == ' ' || string.length() == 4 || DoubleMetaphone.contains(string, 0, 4, "SAN ")) {
                doubleMetaphoneResult.append('H');
            } else {
                doubleMetaphoneResult.append('J', 'H');
            }
            ++n;
        } else {
            if (n == 0 && !DoubleMetaphone.contains(string, n, 4, "JOSE")) {
                doubleMetaphoneResult.append('J', 'A');
            } else if (this.isVowel(this.charAt(string, n - 1)) && !bl && (this.charAt(string, n + 1) == 'A' || this.charAt(string, n + 1) == 'O')) {
                doubleMetaphoneResult.append('J', 'H');
            } else if (n == string.length() - 1) {
                doubleMetaphoneResult.append('J', ' ');
            } else if (!DoubleMetaphone.contains(string, n + 1, 1, L_T_K_S_N_M_B_Z) && !DoubleMetaphone.contains(string, n - 1, 1, "S", "K", "L")) {
                doubleMetaphoneResult.append('J');
            }
            n = this.charAt(string, n + 1) == 'J' ? (n += 2) : ++n;
        }
        return n;
    }

    private int handleL(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(string, n + 1) == 'L') {
            if (this.conditionL0(string, n)) {
                doubleMetaphoneResult.appendPrimary('L');
            } else {
                doubleMetaphoneResult.append('L');
            }
            n += 2;
        } else {
            ++n;
            doubleMetaphoneResult.append('L');
        }
        return n;
    }

    private int handleP(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(string, n + 1) == 'H') {
            doubleMetaphoneResult.append('F');
            n += 2;
        } else {
            doubleMetaphoneResult.append('P');
            n = DoubleMetaphone.contains(string, n + 1, 1, "P", "B") ? n + 2 : n + 1;
        }
        return n;
    }

    private int handleR(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n, boolean bl) {
        if (n == string.length() - 1 && !bl && DoubleMetaphone.contains(string, n - 2, 2, "IE") && !DoubleMetaphone.contains(string, n - 4, 2, "ME", "MA")) {
            doubleMetaphoneResult.appendAlternate('R');
        } else {
            doubleMetaphoneResult.append('R');
        }
        return this.charAt(string, n + 1) == 'R' ? n + 2 : n + 1;
    }

    private int handleS(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n, boolean bl) {
        if (DoubleMetaphone.contains(string, n - 1, 3, "ISL", "YSL")) {
            ++n;
        } else if (n == 0 && DoubleMetaphone.contains(string, n, 5, "SUGAR")) {
            doubleMetaphoneResult.append('X', 'S');
            ++n;
        } else if (DoubleMetaphone.contains(string, n, 2, "SH")) {
            if (DoubleMetaphone.contains(string, n + 1, 4, "HEIM", "HOEK", "HOLM", "HOLZ")) {
                doubleMetaphoneResult.append('S');
            } else {
                doubleMetaphoneResult.append('X');
            }
            n += 2;
        } else if (DoubleMetaphone.contains(string, n, 3, "SIO", "SIA") || DoubleMetaphone.contains(string, n, 4, "SIAN")) {
            if (bl) {
                doubleMetaphoneResult.append('S');
            } else {
                doubleMetaphoneResult.append('S', 'X');
            }
            n += 3;
        } else if (n == 0 && DoubleMetaphone.contains(string, n + 1, 1, "M", "N", "L", "W") || DoubleMetaphone.contains(string, n + 1, 1, "Z")) {
            doubleMetaphoneResult.append('S', 'X');
            n = DoubleMetaphone.contains(string, n + 1, 1, "Z") ? n + 2 : n + 1;
        } else if (DoubleMetaphone.contains(string, n, 2, "SC")) {
            n = this.handleSC(string, doubleMetaphoneResult, n);
        } else {
            if (n == string.length() - 1 && DoubleMetaphone.contains(string, n - 2, 2, "AI", "OI")) {
                doubleMetaphoneResult.appendAlternate('S');
            } else {
                doubleMetaphoneResult.append('S');
            }
            n = DoubleMetaphone.contains(string, n + 1, 1, "S", "Z") ? n + 2 : n + 1;
        }
        return n;
    }

    private int handleSC(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (this.charAt(string, n + 2) == 'H') {
            if (DoubleMetaphone.contains(string, n + 3, 2, "OO", "ER", "EN", "UY", "ED", "EM")) {
                if (DoubleMetaphone.contains(string, n + 3, 2, "ER", "EN")) {
                    doubleMetaphoneResult.append("X", "SK");
                } else {
                    doubleMetaphoneResult.append("SK");
                }
            } else if (n == 0 && !this.isVowel(this.charAt(string, 3)) && this.charAt(string, 3) != 'W') {
                doubleMetaphoneResult.append('X', 'S');
            } else {
                doubleMetaphoneResult.append('X');
            }
        } else if (DoubleMetaphone.contains(string, n + 2, 1, "I", "E", "Y")) {
            doubleMetaphoneResult.append('S');
        } else {
            doubleMetaphoneResult.append("SK");
        }
        return n + 3;
    }

    private int handleT(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (DoubleMetaphone.contains(string, n, 4, "TION")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        } else if (DoubleMetaphone.contains(string, n, 3, "TIA", "TCH")) {
            doubleMetaphoneResult.append('X');
            n += 3;
        } else if (DoubleMetaphone.contains(string, n, 2, "TH") || DoubleMetaphone.contains(string, n, 3, "TTH")) {
            if (DoubleMetaphone.contains(string, n + 2, 2, "OM", "AM") || DoubleMetaphone.contains(string, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(string, 0, 3, "SCH")) {
                doubleMetaphoneResult.append('T');
            } else {
                doubleMetaphoneResult.append('0', 'T');
            }
            n += 2;
        } else {
            doubleMetaphoneResult.append('T');
            n = DoubleMetaphone.contains(string, n + 1, 1, "T", "D") ? n + 2 : n + 1;
        }
        return n;
    }

    private int handleW(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (DoubleMetaphone.contains(string, n, 2, "WR")) {
            doubleMetaphoneResult.append('R');
            n += 2;
        } else if (n == 0 && (this.isVowel(this.charAt(string, n + 1)) || DoubleMetaphone.contains(string, n, 2, "WH"))) {
            if (this.isVowel(this.charAt(string, n + 1))) {
                doubleMetaphoneResult.append('A', 'F');
            } else {
                doubleMetaphoneResult.append('A');
            }
            ++n;
        } else if (n == string.length() - 1 && this.isVowel(this.charAt(string, n - 1)) || DoubleMetaphone.contains(string, n - 1, 5, "EWSKI", "EWSKY", "OWSKI", "OWSKY") || DoubleMetaphone.contains(string, 0, 3, "SCH")) {
            doubleMetaphoneResult.appendAlternate('F');
            ++n;
        } else if (DoubleMetaphone.contains(string, n, 4, "WICZ", "WITZ")) {
            doubleMetaphoneResult.append("TS", "FX");
            n += 4;
        } else {
            ++n;
        }
        return n;
    }

    private int handleX(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n) {
        if (n == 0) {
            doubleMetaphoneResult.append('S');
            ++n;
        } else {
            if (n != string.length() - 1 || !DoubleMetaphone.contains(string, n - 3, 3, "IAU", "EAU") && !DoubleMetaphone.contains(string, n - 2, 2, "AU", "OU")) {
                doubleMetaphoneResult.append("KS");
            }
            n = DoubleMetaphone.contains(string, n + 1, 1, "C", "X") ? n + 2 : n + 1;
        }
        return n;
    }

    private int handleZ(String string, DoubleMetaphoneResult doubleMetaphoneResult, int n, boolean bl) {
        if (this.charAt(string, n + 1) == 'H') {
            doubleMetaphoneResult.append('J');
            n += 2;
        } else {
            if (DoubleMetaphone.contains(string, n + 1, 2, "ZO", "ZI", "ZA") || bl && n > 0 && this.charAt(string, n - 1) != 'T') {
                doubleMetaphoneResult.append("S", "TS");
            } else {
                doubleMetaphoneResult.append('S');
            }
            n = this.charAt(string, n + 1) == 'Z' ? n + 2 : n + 1;
        }
        return n;
    }

    private boolean conditionC0(String string, int n) {
        if (DoubleMetaphone.contains(string, n, 4, "CHIA")) {
            return false;
        }
        if (n <= 1) {
            return true;
        }
        if (this.isVowel(this.charAt(string, n - 2))) {
            return true;
        }
        if (!DoubleMetaphone.contains(string, n - 1, 3, "ACH")) {
            return true;
        }
        char c = this.charAt(string, n + 2);
        return c != 'I' && c != 'E' || DoubleMetaphone.contains(string, n - 2, 6, "BACHER", "MACHER");
    }

    private boolean conditionCH0(String string, int n) {
        if (n != 0) {
            return true;
        }
        if (!DoubleMetaphone.contains(string, n + 1, 5, "HARAC", "HARIS") && !DoubleMetaphone.contains(string, n + 1, 3, "HOR", "HYM", "HIA", "HEM")) {
            return true;
        }
        return DoubleMetaphone.contains(string, 0, 5, "CHORE");
    }

    private boolean conditionCH1(String string, int n) {
        return DoubleMetaphone.contains(string, 0, 4, "VAN ", "VON ") || DoubleMetaphone.contains(string, 0, 3, "SCH") || DoubleMetaphone.contains(string, n - 2, 6, "ORCHES", "ARCHIT", "ORCHID") || DoubleMetaphone.contains(string, n + 2, 1, "T", "S") || (DoubleMetaphone.contains(string, n - 1, 1, "A", "O", "U", "E") || n == 0) && (DoubleMetaphone.contains(string, n + 2, 1, L_R_N_M_B_H_F_V_W_SPACE) || n + 1 == string.length() - 1);
    }

    private boolean conditionL0(String string, int n) {
        if (n == string.length() - 3 && DoubleMetaphone.contains(string, n - 1, 4, "ILLO", "ILLA", "ALLE")) {
            return false;
        }
        return !DoubleMetaphone.contains(string, string.length() - 2, 2, "AS", "OS") && !DoubleMetaphone.contains(string, string.length() - 1, 1, "A", "O") || !DoubleMetaphone.contains(string, n - 1, 4, "ALLE");
    }

    private boolean conditionM0(String string, int n) {
        if (this.charAt(string, n + 1) == 'M') {
            return false;
        }
        return DoubleMetaphone.contains(string, n - 1, 3, "UMB") && (n + 1 == string.length() - 1 || DoubleMetaphone.contains(string, n + 2, 2, "ER"));
    }

    private boolean isSlavoGermanic(String string) {
        return string.indexOf(87) > -1 || string.indexOf(75) > -1 || string.indexOf("CZ") > -1 || string.indexOf("WITZ") > -1;
    }

    private boolean isVowel(char c) {
        return VOWELS.indexOf(c) != -1;
    }

    private boolean isSilentStart(String string) {
        boolean bl = false;
        for (String string2 : SILENT_START) {
            if (!string.startsWith(string2)) continue;
            bl = true;
            break;
        }
        return bl;
    }

    private String cleanInput(String string) {
        if (string == null) {
            return null;
        }
        if ((string = string.trim()).length() == 0) {
            return null;
        }
        return string.toUpperCase(Locale.ENGLISH);
    }

    protected char charAt(String string, int n) {
        if (n < 0 || n >= string.length()) {
            return '\u0001';
        }
        return string.charAt(n);
    }

    protected static boolean contains(String string, int n, int n2, String ... stringArray) {
        boolean bl = false;
        if (n >= 0 && n + n2 <= string.length()) {
            String string2 = string.substring(n, n + n2);
            for (String string3 : stringArray) {
                if (!string2.equals(string3)) continue;
                bl = true;
                break;
            }
        }
        return bl;
    }

    public class DoubleMetaphoneResult {
        private final StringBuilder primary;
        private final StringBuilder alternate;
        private final int maxLength;
        final DoubleMetaphone this$0;

        public DoubleMetaphoneResult(DoubleMetaphone doubleMetaphone, int n) {
            this.this$0 = doubleMetaphone;
            this.primary = new StringBuilder(this.this$0.getMaxCodeLen());
            this.alternate = new StringBuilder(this.this$0.getMaxCodeLen());
            this.maxLength = n;
        }

        public void append(char c) {
            this.appendPrimary(c);
            this.appendAlternate(c);
        }

        public void append(char c, char c2) {
            this.appendPrimary(c);
            this.appendAlternate(c2);
        }

        public void appendPrimary(char c) {
            if (this.primary.length() < this.maxLength) {
                this.primary.append(c);
            }
        }

        public void appendAlternate(char c) {
            if (this.alternate.length() < this.maxLength) {
                this.alternate.append(c);
            }
        }

        public void append(String string) {
            this.appendPrimary(string);
            this.appendAlternate(string);
        }

        public void append(String string, String string2) {
            this.appendPrimary(string);
            this.appendAlternate(string2);
        }

        public void appendPrimary(String string) {
            int n = this.maxLength - this.primary.length();
            if (string.length() <= n) {
                this.primary.append(string);
            } else {
                this.primary.append(string.substring(0, n));
            }
        }

        public void appendAlternate(String string) {
            int n = this.maxLength - this.alternate.length();
            if (string.length() <= n) {
                this.alternate.append(string);
            } else {
                this.alternate.append(string.substring(0, n));
            }
        }

        public String getPrimary() {
            return this.primary.toString();
        }

        public String getAlternate() {
            return this.alternate.toString();
        }

        public boolean isComplete() {
            return this.primary.length() >= this.maxLength && this.alternate.length() >= this.maxLength;
        }
    }
}

