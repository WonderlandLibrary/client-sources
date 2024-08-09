/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CaseInsensitiveString;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TransliteratorIDParser {
    private static final char ID_DELIM = ';';
    private static final char TARGET_SEP = '-';
    private static final char VARIANT_SEP = '/';
    private static final char OPEN_REV = '(';
    private static final char CLOSE_REV = ')';
    private static final String ANY = "Any";
    private static final int FORWARD = 0;
    private static final int REVERSE = 1;
    private static final Map<CaseInsensitiveString, String> SPECIAL_INVERSES = Collections.synchronizedMap(new HashMap());

    TransliteratorIDParser() {
    }

    public static SingleID parseFilterID(String string, int[] nArray) {
        int n = nArray[0];
        Specs specs = TransliteratorIDParser.parseFilterID(string, nArray, true);
        if (specs == null) {
            nArray[0] = n;
            return null;
        }
        SingleID singleID = TransliteratorIDParser.specsToID(specs, 0);
        singleID.filter = specs.filter;
        return singleID;
    }

    public static SingleID parseSingleID(String string, int[] nArray, int n) {
        SingleID singleID;
        int n2 = nArray[0];
        Specs specs = null;
        Specs specs2 = null;
        boolean bl = false;
        for (int i = 1; i <= 2; ++i) {
            if (i == 2 && (specs = TransliteratorIDParser.parseFilterID(string, nArray, true)) == null) {
                nArray[0] = n2;
                return null;
            }
            if (!Utility.parseChar(string, nArray, '(')) continue;
            bl = true;
            if (Utility.parseChar(string, nArray, ')') || (specs2 = TransliteratorIDParser.parseFilterID(string, nArray, true)) != null && Utility.parseChar(string, nArray, ')')) break;
            nArray[0] = n2;
            return null;
        }
        if (bl) {
            if (n == 0) {
                singleID = TransliteratorIDParser.specsToID(specs, 0);
                singleID.canonID = singleID.canonID + '(' + TransliteratorIDParser.specsToID((Specs)specs2, (int)0).canonID + ')';
                if (specs != null) {
                    singleID.filter = specs.filter;
                }
            } else {
                singleID = TransliteratorIDParser.specsToID(specs2, 0);
                singleID.canonID = singleID.canonID + '(' + TransliteratorIDParser.specsToID((Specs)specs, (int)0).canonID + ')';
                if (specs2 != null) {
                    singleID.filter = specs2.filter;
                }
            }
        } else {
            if (n == 0) {
                singleID = TransliteratorIDParser.specsToID(specs, 0);
            } else {
                singleID = TransliteratorIDParser.specsToSpecialInverse(specs);
                if (singleID == null) {
                    singleID = TransliteratorIDParser.specsToID(specs, 1);
                }
            }
            singleID.filter = specs.filter;
        }
        return singleID;
    }

    public static UnicodeSet parseGlobalFilter(String string, int[] nArray, int n, int[] nArray2, StringBuffer stringBuffer) {
        UnicodeSet unicodeSet = null;
        int n2 = nArray[0];
        if (nArray2[0] == -1) {
            nArray2[0] = Utility.parseChar(string, nArray, '(') ? 1 : 0;
        } else if (nArray2[0] == 1 && !Utility.parseChar(string, nArray, '(')) {
            nArray[0] = n2;
            return null;
        }
        nArray[0] = PatternProps.skipWhiteSpace(string, nArray[0]);
        if (UnicodeSet.resemblesPattern(string, nArray[0])) {
            ParsePosition parsePosition = new ParsePosition(nArray[0]);
            try {
                unicodeSet = new UnicodeSet(string, parsePosition, null);
            } catch (IllegalArgumentException illegalArgumentException) {
                nArray[0] = n2;
                return null;
            }
            String string2 = string.substring(nArray[0], parsePosition.getIndex());
            nArray[0] = parsePosition.getIndex();
            if (nArray2[0] == 1 && !Utility.parseChar(string, nArray, ')')) {
                nArray[0] = n2;
                return null;
            }
            if (stringBuffer != null) {
                if (n == 0) {
                    if (nArray2[0] == 1) {
                        string2 = String.valueOf('(') + string2 + ')';
                    }
                    stringBuffer.append(string2 + ';');
                } else {
                    if (nArray2[0] == 0) {
                        string2 = String.valueOf('(') + string2 + ')';
                    }
                    stringBuffer.insert(0, string2 + ';');
                }
            }
        }
        return unicodeSet;
    }

    public static boolean parseCompoundID(String string, int n, StringBuffer stringBuffer, List<SingleID> list, UnicodeSet[] unicodeSetArray) {
        SingleID singleID;
        int[] nArray = new int[]{0};
        int[] nArray2 = new int[1];
        list.clear();
        unicodeSetArray[0] = null;
        stringBuffer.setLength(0);
        nArray2[0] = 0;
        UnicodeSet unicodeSet = TransliteratorIDParser.parseGlobalFilter(string, nArray, n, nArray2, stringBuffer);
        if (unicodeSet != null) {
            if (!Utility.parseChar(string, nArray, ';')) {
                stringBuffer.setLength(0);
                nArray[0] = 0;
            }
            if (n == 0) {
                unicodeSetArray[0] = unicodeSet;
            }
        }
        boolean bl = true;
        while ((singleID = TransliteratorIDParser.parseSingleID(string, nArray, n)) != null) {
            if (n == 0) {
                list.add(singleID);
            } else {
                list.add(0, singleID);
            }
            if (Utility.parseChar(string, nArray, ';')) continue;
            bl = false;
            break;
        }
        if (list.size() == 0) {
            return true;
        }
        for (int i = 0; i < list.size(); ++i) {
            SingleID singleID2 = list.get(i);
            stringBuffer.append(singleID2.canonID);
            if (i == list.size() - 1) continue;
            stringBuffer.append(';');
        }
        if (bl) {
            nArray2[0] = 1;
            unicodeSet = TransliteratorIDParser.parseGlobalFilter(string, nArray, n, nArray2, stringBuffer);
            if (unicodeSet != null) {
                Utility.parseChar(string, nArray, ';');
                if (n == 1) {
                    unicodeSetArray[0] = unicodeSet;
                }
            }
        }
        nArray[0] = PatternProps.skipWhiteSpace(string, nArray[0]);
        return nArray[0] != string.length();
    }

    static List<Transliterator> instantiateList(List<SingleID> list) {
        Transliterator transliterator;
        ArrayList<Transliterator> arrayList = new ArrayList<Transliterator>();
        for (SingleID singleID : list) {
            if (singleID.basicID.length() == 0) continue;
            transliterator = singleID.getInstance();
            if (transliterator == null) {
                throw new IllegalArgumentException("Illegal ID " + singleID.canonID);
            }
            arrayList.add(transliterator);
        }
        if (arrayList.size() == 0) {
            transliterator = Transliterator.getBasicInstance("Any-Null", null);
            if (transliterator == null) {
                throw new IllegalArgumentException("Internal error; cannot instantiate Any-Null");
            }
            arrayList.add(transliterator);
        }
        return arrayList;
    }

    public static String[] IDtoSTV(String string) {
        String string2 = ANY;
        String string3 = null;
        String string4 = "";
        int n = string.indexOf(45);
        int n2 = string.indexOf(47);
        if (n2 < 0) {
            n2 = string.length();
        }
        boolean bl = false;
        if (n < 0) {
            string3 = string.substring(0, n2);
            string4 = string.substring(n2);
        } else if (n < n2) {
            if (n > 0) {
                string2 = string.substring(0, n);
                bl = true;
            }
            string3 = string.substring(++n, n2);
            string4 = string.substring(n2);
        } else {
            if (n2 > 0) {
                string2 = string.substring(0, n2);
                bl = true;
            }
            string4 = string.substring(n2, n++);
            string3 = string.substring(n);
        }
        if (string4.length() > 0) {
            string4 = string4.substring(1);
        }
        return new String[]{string2, string3, string4, bl ? "" : null};
    }

    public static String STVtoID(String string, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder(string);
        if (stringBuilder.length() == 0) {
            stringBuilder.append(ANY);
        }
        stringBuilder.append('-').append(string2);
        if (string3 != null && string3.length() != 0) {
            stringBuilder.append('/').append(string3);
        }
        return stringBuilder.toString();
    }

    public static void registerSpecialInverse(String string, String string2, boolean bl) {
        SPECIAL_INVERSES.put(new CaseInsensitiveString(string), string2);
        if (bl && !string.equalsIgnoreCase(string2)) {
            SPECIAL_INVERSES.put(new CaseInsensitiveString(string2), string);
        }
    }

    private static Specs parseFilterID(String string, int[] nArray, boolean bl) {
        String string2 = null;
        String string3 = null;
        String string4 = null;
        String string5 = null;
        String string6 = null;
        int n = 0;
        int n2 = 0;
        int n3 = nArray[0];
        while (true) {
            String string7;
            char c;
            nArray[0] = PatternProps.skipWhiteSpace(string, nArray[0]);
            if (nArray[0] == string.length()) break;
            if (bl && string6 == null && UnicodeSet.resemblesPattern(string, nArray[0])) {
                ParsePosition parsePosition = new ParsePosition(nArray[0]);
                new UnicodeSet(string, parsePosition, null);
                string6 = string.substring(nArray[0], parsePosition.getIndex());
                nArray[0] = parsePosition.getIndex();
                continue;
            }
            if (n == 0 && ((c = string.charAt(nArray[0])) == '-' && string4 == null || c == '/' && string5 == null)) {
                n = c;
                nArray[0] = nArray[0] + 1;
                continue;
            }
            if (n == 0 && n2 > 0 || (string7 = Utility.parseUnicodeIdentifier(string, nArray)) == null) break;
            switch (n) {
                case 0: {
                    string2 = string7;
                    break;
                }
                case 45: {
                    string4 = string7;
                    break;
                }
                case 47: {
                    string5 = string7;
                }
            }
            ++n2;
            n = 0;
        }
        if (string2 != null) {
            if (string4 == null) {
                string4 = string2;
            } else {
                string3 = string2;
            }
        }
        if (string3 == null && string4 == null) {
            nArray[0] = n3;
            return null;
        }
        boolean bl2 = true;
        if (string3 == null) {
            string3 = ANY;
            bl2 = false;
        }
        if (string4 == null) {
            string4 = ANY;
        }
        return new Specs(string3, string4, string5, bl2, string6);
    }

    private static SingleID specsToID(Specs specs, int n) {
        String string = "";
        String string2 = "";
        String string3 = "";
        if (specs != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (n == 0) {
                if (specs.sawSource) {
                    stringBuilder.append(specs.source).append('-');
                } else {
                    string3 = specs.source + '-';
                }
                stringBuilder.append(specs.target);
            } else {
                stringBuilder.append(specs.target).append('-').append(specs.source);
            }
            if (specs.variant != null) {
                stringBuilder.append('/').append(specs.variant);
            }
            string2 = string3 + stringBuilder.toString();
            if (specs.filter != null) {
                stringBuilder.insert(0, specs.filter);
            }
            string = stringBuilder.toString();
        }
        return new SingleID(string, string2);
    }

    private static SingleID specsToSpecialInverse(Specs specs) {
        if (!specs.source.equalsIgnoreCase(ANY)) {
            return null;
        }
        String string = SPECIAL_INVERSES.get(new CaseInsensitiveString(specs.target));
        if (string != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (specs.filter != null) {
                stringBuilder.append(specs.filter);
            }
            if (specs.sawSource) {
                stringBuilder.append(ANY).append('-');
            }
            stringBuilder.append(string);
            String string2 = "Any-" + string;
            if (specs.variant != null) {
                stringBuilder.append('/').append(specs.variant);
                string2 = string2 + '/' + specs.variant;
            }
            return new SingleID(stringBuilder.toString(), string2);
        }
        return null;
    }

    static class SingleID {
        public String canonID;
        public String basicID;
        public String filter;

        SingleID(String string, String string2, String string3) {
            this.canonID = string;
            this.basicID = string2;
            this.filter = string3;
        }

        SingleID(String string, String string2) {
            this(string, string2, null);
        }

        Transliterator getInstance() {
            Transliterator transliterator = this.basicID == null || this.basicID.length() == 0 ? Transliterator.getBasicInstance("Any-Null", this.canonID) : Transliterator.getBasicInstance(this.basicID, this.canonID);
            if (transliterator != null && this.filter != null) {
                transliterator.setFilter(new UnicodeSet(this.filter));
            }
            return transliterator;
        }
    }

    private static class Specs {
        public String source;
        public String target;
        public String variant;
        public String filter;
        public boolean sawSource;

        Specs(String string, String string2, String string3, boolean bl, String string4) {
            this.source = string;
            this.target = string2;
            this.variant = string3;
            this.sawSource = bl;
            this.filter = string4;
        }
    }
}

