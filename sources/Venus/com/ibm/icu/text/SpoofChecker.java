/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpoofChecker {
    public static final UnicodeSet INCLUSION;
    public static final UnicodeSet RECOMMENDED;
    public static final int SINGLE_SCRIPT_CONFUSABLE = 1;
    public static final int MIXED_SCRIPT_CONFUSABLE = 2;
    public static final int WHOLE_SCRIPT_CONFUSABLE = 4;
    public static final int CONFUSABLE = 7;
    @Deprecated
    public static final int ANY_CASE = 8;
    public static final int RESTRICTION_LEVEL = 16;
    @Deprecated
    public static final int SINGLE_SCRIPT = 16;
    public static final int INVISIBLE = 32;
    public static final int CHAR_LIMIT = 64;
    public static final int MIXED_NUMBERS = 128;
    public static final int HIDDEN_OVERLAY = 256;
    public static final int ALL_CHECKS = -1;
    static final UnicodeSet ASCII;
    private int fChecks;
    private SpoofData fSpoofData;
    private Set<ULocale> fAllowedLocales;
    private UnicodeSet fAllowedCharsSet;
    private RestrictionLevel fRestrictionLevel;
    private static Normalizer2 nfdNormalizer;
    static final boolean $assertionsDisabled;

    private SpoofChecker() {
    }

    @Deprecated
    public RestrictionLevel getRestrictionLevel() {
        return this.fRestrictionLevel;
    }

    public int getChecks() {
        return this.fChecks;
    }

    public Set<ULocale> getAllowedLocales() {
        return Collections.unmodifiableSet(this.fAllowedLocales);
    }

    public Set<Locale> getAllowedJavaLocales() {
        HashSet<Locale> hashSet = new HashSet<Locale>(this.fAllowedLocales.size());
        for (ULocale uLocale : this.fAllowedLocales) {
            hashSet.add(uLocale.toLocale());
        }
        return hashSet;
    }

    public UnicodeSet getAllowedChars() {
        return this.fAllowedCharsSet;
    }

    public boolean failsChecks(String string, CheckResult checkResult) {
        int n;
        int n2;
        Object object;
        int n3 = string.length();
        int n4 = 0;
        if (checkResult != null) {
            checkResult.position = 0;
            checkResult.numerics = null;
            checkResult.restrictionLevel = null;
        }
        if (0 != (this.fChecks & 0x10)) {
            object = this.getRestrictionLevel(string);
            if (object.compareTo(this.fRestrictionLevel) > 0) {
                n4 |= 0x10;
            }
            if (checkResult != null) {
                checkResult.restrictionLevel = object;
            }
        }
        if (0 != (this.fChecks & 0x80)) {
            object = new UnicodeSet();
            this.getNumerics(string, (UnicodeSet)object);
            if (((UnicodeSet)object).size() > 1) {
                n4 |= 0x80;
            }
            if (checkResult != null) {
                checkResult.numerics = object;
            }
        }
        if (0 != (this.fChecks & 0x100) && (n2 = this.findHiddenOverlay(string)) != -1) {
            n4 |= 0x100;
        }
        if (0 != (this.fChecks & 0x40)) {
            int n5 = 0;
            while (n5 < n3) {
                n = Character.codePointAt(string, n5);
                n5 = Character.offsetByCodePoints(string, n5, 1);
                if (this.fAllowedCharsSet.contains(n)) continue;
                n4 |= 0x40;
                break;
            }
        }
        if (0 != (this.fChecks & 0x20)) {
            String string2 = nfdNormalizer.normalize(string);
            int n6 = 0;
            boolean bl = false;
            UnicodeSet unicodeSet = new UnicodeSet();
            n = 0;
            while (n < n3) {
                int n7 = Character.codePointAt(string2, n);
                n = Character.offsetByCodePoints(string2, n, 1);
                if (Character.getType(n7) != 6) {
                    n6 = 0;
                    if (!bl) continue;
                    unicodeSet.clear();
                    bl = false;
                    continue;
                }
                if (n6 == 0) {
                    n6 = n7;
                    continue;
                }
                if (!bl) {
                    unicodeSet.add(n6);
                    bl = true;
                }
                if (unicodeSet.contains(n7)) {
                    n4 |= 0x20;
                    break;
                }
                unicodeSet.add(n7);
            }
        }
        if (checkResult != null) {
            checkResult.checks = n4;
        }
        return 0 != n4;
    }

    public boolean failsChecks(String string) {
        return this.failsChecks(string, null);
    }

    public int areConfusable(String string, String string2) {
        String string3;
        if ((this.fChecks & 7) == 0) {
            throw new IllegalArgumentException("No confusable checks are enabled.");
        }
        String string4 = this.getSkeleton(string);
        if (!string4.equals(string3 = this.getSkeleton(string2))) {
            return 1;
        }
        ScriptSet scriptSet = new ScriptSet();
        this.getResolvedScriptSet(string, scriptSet);
        ScriptSet scriptSet2 = new ScriptSet();
        this.getResolvedScriptSet(string2, scriptSet2);
        int n = 0;
        if (scriptSet.intersects(scriptSet2)) {
            n |= 1;
        } else {
            n |= 2;
            if (!scriptSet.isEmpty() && !scriptSet2.isEmpty()) {
                n |= 4;
            }
        }
        return n &= this.fChecks;
    }

    public String getSkeleton(CharSequence charSequence) {
        int n;
        String string = nfdNormalizer.normalize(charSequence);
        int n2 = string.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n2; i += Character.charCount(n)) {
            n = Character.codePointAt(string, i);
            this.fSpoofData.confusableLookup(n, stringBuilder);
        }
        String string2 = stringBuilder.toString();
        string2 = nfdNormalizer.normalize(string2);
        return string2;
    }

    @Deprecated
    public String getSkeleton(int n, String string) {
        return this.getSkeleton(string);
    }

    public boolean equals(Object object) {
        if (!(object instanceof SpoofChecker)) {
            return true;
        }
        SpoofChecker spoofChecker = (SpoofChecker)object;
        if (this.fSpoofData != spoofChecker.fSpoofData && this.fSpoofData != null && !this.fSpoofData.equals(spoofChecker.fSpoofData)) {
            return true;
        }
        if (this.fChecks != spoofChecker.fChecks) {
            return true;
        }
        if (this.fAllowedLocales != spoofChecker.fAllowedLocales && this.fAllowedLocales != null && !this.fAllowedLocales.equals(spoofChecker.fAllowedLocales)) {
            return true;
        }
        if (this.fAllowedCharsSet != spoofChecker.fAllowedCharsSet && this.fAllowedCharsSet != null && !this.fAllowedCharsSet.equals(spoofChecker.fAllowedCharsSet)) {
            return true;
        }
        return this.fRestrictionLevel != spoofChecker.fRestrictionLevel;
    }

    public int hashCode() {
        return this.fChecks ^ this.fSpoofData.hashCode() ^ this.fAllowedLocales.hashCode() ^ this.fAllowedCharsSet.hashCode() ^ this.fRestrictionLevel.ordinal();
    }

    private static void getAugmentedScriptSet(int n, ScriptSet scriptSet) {
        scriptSet.clear();
        UScript.getScriptExtensions(n, scriptSet);
        if (scriptSet.get(0)) {
            scriptSet.set(172);
            scriptSet.set(105);
            scriptSet.set(119);
        }
        if (scriptSet.get(1)) {
            scriptSet.set(105);
        }
        if (scriptSet.get(1)) {
            scriptSet.set(105);
        }
        if (scriptSet.get(1)) {
            scriptSet.set(119);
        }
        if (scriptSet.get(0)) {
            scriptSet.set(172);
        }
        if (scriptSet.get(1) || scriptSet.get(0)) {
            scriptSet.setAll();
        }
    }

    private void getResolvedScriptSet(CharSequence charSequence, ScriptSet scriptSet) {
        this.getResolvedScriptSetWithout(charSequence, 193, scriptSet);
    }

    private void getResolvedScriptSetWithout(CharSequence charSequence, int n, ScriptSet scriptSet) {
        int n2;
        scriptSet.setAll();
        ScriptSet scriptSet2 = new ScriptSet();
        for (int i = 0; i < charSequence.length(); i += Character.charCount(n2)) {
            n2 = Character.codePointAt(charSequence, i);
            SpoofChecker.getAugmentedScriptSet(n2, scriptSet2);
            if (n != 193 && scriptSet2.get(n)) continue;
            scriptSet.and(scriptSet2);
        }
    }

    private void getNumerics(String string, UnicodeSet unicodeSet) {
        int n;
        unicodeSet.clear();
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = Character.codePointAt(string, i);
            if (UCharacter.getType(n) != 9) continue;
            unicodeSet.add(n - UCharacter.getNumericValue(n));
        }
    }

    private RestrictionLevel getRestrictionLevel(String string) {
        if (!this.fAllowedCharsSet.containsAll(string)) {
            return RestrictionLevel.UNRESTRICTIVE;
        }
        if (ASCII.containsAll(string)) {
            return RestrictionLevel.ASCII;
        }
        ScriptSet scriptSet = new ScriptSet();
        this.getResolvedScriptSet(string, scriptSet);
        if (!scriptSet.isEmpty()) {
            return RestrictionLevel.SINGLE_SCRIPT_RESTRICTIVE;
        }
        ScriptSet scriptSet2 = new ScriptSet();
        this.getResolvedScriptSetWithout(string, 25, scriptSet2);
        if (scriptSet2.get(1) || scriptSet2.get(0) || scriptSet2.get(0)) {
            return RestrictionLevel.HIGHLY_RESTRICTIVE;
        }
        if (!(scriptSet2.isEmpty() || scriptSet2.get(1) || scriptSet2.get(1) || scriptSet2.get(1))) {
            return RestrictionLevel.MODERATELY_RESTRICTIVE;
        }
        return RestrictionLevel.MINIMALLY_RESTRICTIVE;
    }

    int findHiddenOverlay(String string) {
        int n;
        boolean bl = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i += UCharacter.charCount(n)) {
            n = string.codePointAt(i);
            if (bl && n == 775) {
                return i;
            }
            int n2 = UCharacter.getCombiningClass(n);
            if (!$assertionsDisabled && UCharacter.getCombiningClass(775) != 230) {
                throw new AssertionError();
            }
            if (n2 != 0 && n2 != 230) continue;
            bl = this.isIllegalCombiningDotLeadCharacter(n, stringBuilder);
        }
        return 1;
    }

    boolean isIllegalCombiningDotLeadCharacterNoLookup(int n) {
        return n == 105 || n == 106 || n == 305 || n == 567 || n == 108 || UCharacter.hasBinaryProperty(n, 27);
    }

    boolean isIllegalCombiningDotLeadCharacter(int n, StringBuilder stringBuilder) {
        if (this.isIllegalCombiningDotLeadCharacterNoLookup(n)) {
            return false;
        }
        stringBuilder.setLength(0);
        this.fSpoofData.confusableLookup(n, stringBuilder);
        int n2 = UCharacter.codePointBefore(stringBuilder, stringBuilder.length());
        return n2 == n || !this.isIllegalCombiningDotLeadCharacterNoLookup(n2);
    }

    static int access$000(SpoofChecker spoofChecker) {
        return spoofChecker.fChecks;
    }

    static SpoofData access$100(SpoofChecker spoofChecker) {
        return spoofChecker.fSpoofData;
    }

    static UnicodeSet access$200(SpoofChecker spoofChecker) {
        return spoofChecker.fAllowedCharsSet;
    }

    static Set access$300(SpoofChecker spoofChecker) {
        return spoofChecker.fAllowedLocales;
    }

    static RestrictionLevel access$400(SpoofChecker spoofChecker) {
        return spoofChecker.fRestrictionLevel;
    }

    SpoofChecker(1 var1_1) {
        this();
    }

    static int access$002(SpoofChecker spoofChecker, int n) {
        spoofChecker.fChecks = n;
        return spoofChecker.fChecks;
    }

    static SpoofData access$102(SpoofChecker spoofChecker, SpoofData spoofData) {
        spoofChecker.fSpoofData = spoofData;
        return spoofChecker.fSpoofData;
    }

    static UnicodeSet access$202(SpoofChecker spoofChecker, UnicodeSet unicodeSet) {
        spoofChecker.fAllowedCharsSet = unicodeSet;
        return spoofChecker.fAllowedCharsSet;
    }

    static Set access$302(SpoofChecker spoofChecker, Set set) {
        spoofChecker.fAllowedLocales = set;
        return spoofChecker.fAllowedLocales;
    }

    static RestrictionLevel access$402(SpoofChecker spoofChecker, RestrictionLevel restrictionLevel) {
        spoofChecker.fRestrictionLevel = restrictionLevel;
        return spoofChecker.fRestrictionLevel;
    }

    static {
        $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();
        INCLUSION = new UnicodeSet("['\\-.\\:\\u00B7\\u0375\\u058A\\u05F3\\u05F4\\u06FD\\u06FE\\u0F0B\\u200C\\u200D\\u2010\\u2019\\u2027\\u30A0\\u30FB]").freeze();
        RECOMMENDED = new UnicodeSet("[0-9A-Z_a-z\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u0131\\u0134-\\u013E\\u0141-\\u0148\\u014A-\\u017E\\u018F\\u01A0\\u01A1\\u01AF\\u01B0\\u01CD-\\u01DC\\u01DE-\\u01E3\\u01E6-\\u01F0\\u01F4\\u01F5\\u01F8-\\u021B\\u021E\\u021F\\u0226-\\u0233\\u0259\\u02BB\\u02BC\\u02EC\\u0300-\\u0304\\u0306-\\u030C\\u030F-\\u0311\\u0313\\u0314\\u031B\\u0323-\\u0328\\u032D\\u032E\\u0330\\u0331\\u0335\\u0338\\u0339\\u0342\\u0345\\u037B-\\u037D\\u0386\\u0388-\\u038A\\u038C\\u038E-\\u03A1\\u03A3-\\u03CE\\u03FC-\\u045F\\u048A-\\u04FF\\u0510-\\u0529\\u052E\\u052F\\u0531-\\u0556\\u0559\\u0561-\\u0586\\u05B4\\u05D0-\\u05EA\\u05EF-\\u05F2\\u0620-\\u063F\\u0641-\\u0655\\u0660-\\u0669\\u0670-\\u0672\\u0674\\u0679-\\u068D\\u068F-\\u06A0\\u06A2-\\u06D3\\u06D5\\u06E5\\u06E6\\u06EE-\\u06FC\\u06FF\\u0750-\\u07B1\\u08A0-\\u08AC\\u08B2\\u08B6-\\u08C7\\u0901-\\u094D\\u094F\\u0950\\u0956\\u0957\\u0960-\\u0963\\u0966-\\u096F\\u0971-\\u0977\\u0979-\\u097F\\u0981-\\u0983\\u0985-\\u098C\\u098F\\u0990\\u0993-\\u09A8\\u09AA-\\u09B0\\u09B2\\u09B6-\\u09B9\\u09BC-\\u09C4\\u09C7\\u09C8\\u09CB-\\u09CE\\u09D7\\u09E0-\\u09E3\\u09E6-\\u09F1\\u09FE\\u0A01-\\u0A03\\u0A05-\\u0A0A\\u0A0F\\u0A10\\u0A13-\\u0A28\\u0A2A-\\u0A30\\u0A32\\u0A35\\u0A38\\u0A39\\u0A3C\\u0A3E-\\u0A42\\u0A47\\u0A48\\u0A4B-\\u0A4D\\u0A5C\\u0A66-\\u0A74\\u0A81-\\u0A83\\u0A85-\\u0A8D\\u0A8F-\\u0A91\\u0A93-\\u0AA8\\u0AAA-\\u0AB0\\u0AB2\\u0AB3\\u0AB5-\\u0AB9\\u0ABC-\\u0AC5\\u0AC7-\\u0AC9\\u0ACB-\\u0ACD\\u0AD0\\u0AE0-\\u0AE3\\u0AE6-\\u0AEF\\u0AFA-\\u0AFF\\u0B01-\\u0B03\\u0B05-\\u0B0C\\u0B0F\\u0B10\\u0B13-\\u0B28\\u0B2A-\\u0B30\\u0B32\\u0B33\\u0B35-\\u0B39\\u0B3C-\\u0B43\\u0B47\\u0B48\\u0B4B-\\u0B4D\\u0B55-\\u0B57\\u0B5F-\\u0B61\\u0B66-\\u0B6F\\u0B71\\u0B82\\u0B83\\u0B85-\\u0B8A\\u0B8E-\\u0B90\\u0B92-\\u0B95\\u0B99\\u0B9A\\u0B9C\\u0B9E\\u0B9F\\u0BA3\\u0BA4\\u0BA8-\\u0BAA\\u0BAE-\\u0BB9\\u0BBE-\\u0BC2\\u0BC6-\\u0BC8\\u0BCA-\\u0BCD\\u0BD0\\u0BD7\\u0BE6-\\u0BEF\\u0C01-\\u0C0C\\u0C0E-\\u0C10\\u0C12-\\u0C28\\u0C2A-\\u0C33\\u0C35-\\u0C39\\u0C3D-\\u0C44\\u0C46-\\u0C48\\u0C4A-\\u0C4D\\u0C55\\u0C56\\u0C60\\u0C61\\u0C66-\\u0C6F\\u0C80\\u0C82\\u0C83\\u0C85-\\u0C8C\\u0C8E-\\u0C90\\u0C92-\\u0CA8\\u0CAA-\\u0CB3\\u0CB5-\\u0CB9\\u0CBC-\\u0CC4\\u0CC6-\\u0CC8\\u0CCA-\\u0CCD\\u0CD5\\u0CD6\\u0CE0-\\u0CE3\\u0CE6-\\u0CEF\\u0CF1\\u0CF2\\u0D00\\u0D02\\u0D03\\u0D05-\\u0D0C\\u0D0E-\\u0D10\\u0D12-\\u0D3A\\u0D3D-\\u0D43\\u0D46-\\u0D48\\u0D4A-\\u0D4E\\u0D54-\\u0D57\\u0D60\\u0D61\\u0D66-\\u0D6F\\u0D7A-\\u0D7F\\u0D82\\u0D83\\u0D85-\\u0D8E\\u0D91-\\u0D96\\u0D9A-\\u0DA5\\u0DA7-\\u0DB1\\u0DB3-\\u0DBB\\u0DBD\\u0DC0-\\u0DC6\\u0DCA\\u0DCF-\\u0DD4\\u0DD6\\u0DD8-\\u0DDE\\u0DF2\\u0E01-\\u0E32\\u0E34-\\u0E3A\\u0E40-\\u0E4E\\u0E50-\\u0E59\\u0E81\\u0E82\\u0E84\\u0E86-\\u0E8A\\u0E8C-\\u0EA3\\u0EA5\\u0EA7-\\u0EB2\\u0EB4-\\u0EBD\\u0EC0-\\u0EC4\\u0EC6\\u0EC8-\\u0ECD\\u0ED0-\\u0ED9\\u0EDE\\u0EDF\\u0F00\\u0F20-\\u0F29\\u0F35\\u0F37\\u0F3E-\\u0F42\\u0F44-\\u0F47\\u0F49-\\u0F4C\\u0F4E-\\u0F51\\u0F53-\\u0F56\\u0F58-\\u0F5B\\u0F5D-\\u0F68\\u0F6A-\\u0F6C\\u0F71\\u0F72\\u0F74\\u0F7A-\\u0F80\\u0F82-\\u0F84\\u0F86-\\u0F92\\u0F94-\\u0F97\\u0F99-\\u0F9C\\u0F9E-\\u0FA1\\u0FA3-\\u0FA6\\u0FA8-\\u0FAB\\u0FAD-\\u0FB8\\u0FBA-\\u0FBC\\u0FC6\\u1000-\\u1049\\u1050-\\u109D\\u10C7\\u10CD\\u10D0-\\u10F0\\u10F7-\\u10FA\\u10FD-\\u10FF\\u1200-\\u1248\\u124A-\\u124D\\u1250-\\u1256\\u1258\\u125A-\\u125D\\u1260-\\u1288\\u128A-\\u128D\\u1290-\\u12B0\\u12B2-\\u12B5\\u12B8-\\u12BE\\u12C0\\u12C2-\\u12C5\\u12C8-\\u12D6\\u12D8-\\u1310\\u1312-\\u1315\\u1318-\\u135A\\u135D-\\u135F\\u1380-\\u138F\\u1780-\\u17A2\\u17A5-\\u17A7\\u17A9-\\u17B3\\u17B6-\\u17CA\\u17D2\\u17D7\\u17DC\\u17E0-\\u17E9\\u1C90-\\u1CBA\\u1CBD-\\u1CBF\\u1E00-\\u1E99\\u1E9E\\u1EA0-\\u1EF9\\u1F00-\\u1F15\\u1F18-\\u1F1D\\u1F20-\\u1F45\\u1F48-\\u1F4D\\u1F50-\\u1F57\\u1F59\\u1F5B\\u1F5D\\u1F5F-\\u1F70\\u1F72\\u1F74\\u1F76\\u1F78\\u1F7A\\u1F7C\\u1F80-\\u1FB4\\u1FB6-\\u1FBA\\u1FBC\\u1FC2-\\u1FC4\\u1FC6-\\u1FC8\\u1FCA\\u1FCC\\u1FD0-\\u1FD2\\u1FD6-\\u1FDA\\u1FE0-\\u1FE2\\u1FE4-\\u1FEA\\u1FEC\\u1FF2-\\u1FF4\\u1FF6-\\u1FF8\\u1FFA\\u1FFC\\u2D27\\u2D2D\\u2D80-\\u2D96\\u2DA0-\\u2DA6\\u2DA8-\\u2DAE\\u2DB0-\\u2DB6\\u2DB8-\\u2DBE\\u2DC0-\\u2DC6\\u2DC8-\\u2DCE\\u2DD0-\\u2DD6\\u2DD8-\\u2DDE\\u3005-\\u3007\\u3041-\\u3096\\u3099\\u309A\\u309D\\u309E\\u30A1-\\u30FA\\u30FC-\\u30FE\\u3105-\\u312D\\u312F\\u31A0-\\u31BF\\u3400-\\u4DBF\\u4E00-\\u9FFC\\uA67F\\uA717-\\uA71F\\uA788\\uA78D\\uA792\\uA793\\uA7AA\\uA7AE\\uA7B8\\uA7B9\\uA7C2-\\uA7CA\\uA9E7-\\uA9FE\\uAA60-\\uAA76\\uAA7A-\\uAA7F\\uAB01-\\uAB06\\uAB09-\\uAB0E\\uAB11-\\uAB16\\uAB20-\\uAB26\\uAB28-\\uAB2E\\uAB66\\uAB67\\uAC00-\\uD7A3\\uFA0E\\uFA0F\\uFA11\\uFA13\\uFA14\\uFA1F\\uFA21\\uFA23\\uFA24\\uFA27-\\uFA29\\U00011301\\U00011303\\U0001133B\\U0001133C\\U00016FF0\\U00016FF1\\U0001B150-\\U0001B152\\U0001B164-\\U0001B167\\U00020000-\\U0002A6DD\\U0002A700-\\U0002B734\\U0002B740-\\U0002B81D\\U0002B820-\\U0002CEA1\\U0002CEB0-\\U0002EBE0\\U00030000-\\U0003134A]").freeze();
        ASCII = new UnicodeSet(0, 127).freeze();
        nfdNormalizer = Normalizer2.getNFDInstance();
    }

    static class ScriptSet
    extends BitSet {
        private static final long serialVersionUID = 1L;

        ScriptSet() {
        }

        public void and(int n) {
            this.clear(0, n);
            this.clear(n + 1, 193);
        }

        public void setAll() {
            this.set(0, 193);
        }

        public boolean isFull() {
            return this.cardinality() == 193;
        }

        public void appendStringTo(StringBuilder stringBuilder) {
            stringBuilder.append("{ ");
            if (this.isEmpty()) {
                stringBuilder.append("- ");
            } else if (this.isFull()) {
                stringBuilder.append("* ");
            } else {
                for (int i = 0; i < 193; ++i) {
                    if (!this.get(i)) continue;
                    stringBuilder.append(UScript.getShortName(i));
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("}");
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<ScriptSet ");
            this.appendStringTo(stringBuilder);
            stringBuilder.append(">");
            return stringBuilder.toString();
        }
    }

    private static class SpoofData {
        int[] fCFUKeys;
        short[] fCFUValues;
        String fCFUStrings;
        private static final int DATA_FORMAT = 1130788128;
        private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable(null);

        public static SpoofData getDefault() {
            if (DefaultData.access$900() != null) {
                throw new MissingResourceException("Could not load default confusables data: " + DefaultData.access$900().getMessage(), "SpoofChecker", "");
            }
            return DefaultData.access$1000();
        }

        private SpoofData() {
        }

        private SpoofData(ByteBuffer byteBuffer) throws IOException {
            ICUBinary.readHeader(byteBuffer, 1130788128, IS_ACCEPTABLE);
            byteBuffer.mark();
            this.readData(byteBuffer);
        }

        public boolean equals(Object object) {
            if (!(object instanceof SpoofData)) {
                return true;
            }
            SpoofData spoofData = (SpoofData)object;
            if (!Arrays.equals(this.fCFUKeys, spoofData.fCFUKeys)) {
                return true;
            }
            if (!Arrays.equals(this.fCFUValues, spoofData.fCFUValues)) {
                return true;
            }
            return !Utility.sameObjects(this.fCFUStrings, spoofData.fCFUStrings) && this.fCFUStrings != null && !this.fCFUStrings.equals(spoofData.fCFUStrings);
        }

        public int hashCode() {
            return Arrays.hashCode(this.fCFUKeys) ^ Arrays.hashCode(this.fCFUValues) ^ this.fCFUStrings.hashCode();
        }

        private void readData(ByteBuffer byteBuffer) throws IOException {
            int n = byteBuffer.getInt();
            if (n != 944111087) {
                throw new IllegalArgumentException("Bad Spoof Check Data.");
            }
            int n2 = byteBuffer.getInt();
            int n3 = byteBuffer.getInt();
            int n4 = byteBuffer.getInt();
            int n5 = byteBuffer.getInt();
            int n6 = byteBuffer.getInt();
            int n7 = byteBuffer.getInt();
            int n8 = byteBuffer.getInt();
            int n9 = byteBuffer.getInt();
            byteBuffer.reset();
            ICUBinary.skipBytes(byteBuffer, n4);
            this.fCFUKeys = ICUBinary.getInts(byteBuffer, n5, 0);
            byteBuffer.reset();
            ICUBinary.skipBytes(byteBuffer, n6);
            this.fCFUValues = ICUBinary.getShorts(byteBuffer, n7, 0);
            byteBuffer.reset();
            ICUBinary.skipBytes(byteBuffer, n8);
            this.fCFUStrings = ICUBinary.getString(byteBuffer, n9, 0);
        }

        public void confusableLookup(int n, StringBuilder stringBuilder) {
            int n2 = 0;
            int n3 = this.length();
            do {
                int n4;
                if (this.codePointAt(n4 = (n2 + n3) / 2) > n) {
                    n3 = n4;
                    continue;
                }
                if (this.codePointAt(n4) < n) {
                    n2 = n4;
                    continue;
                }
                n2 = n4;
                break;
            } while (n3 - n2 > 1);
            if (this.codePointAt(n2) != n) {
                stringBuilder.appendCodePoint(n);
                return;
            }
            this.appendValueTo(n2, stringBuilder);
        }

        public int length() {
            return this.fCFUKeys.length;
        }

        public int codePointAt(int n) {
            return ConfusableDataUtils.keyToCodePoint(this.fCFUKeys[n]);
        }

        public void appendValueTo(int n, StringBuilder stringBuilder) {
            int n2 = ConfusableDataUtils.keyToLength(this.fCFUKeys[n]);
            short s = this.fCFUValues[n];
            if (n2 == 1) {
                stringBuilder.append((char)s);
            } else {
                stringBuilder.append(this.fCFUStrings, (int)s, s + n2);
            }
        }

        SpoofData(1 var1_1) {
            this();
        }

        SpoofData(ByteBuffer byteBuffer, 1 var2_2) throws IOException {
            this(byteBuffer);
        }

        private static final class DefaultData {
            private static SpoofData INSTANCE = null;
            private static IOException EXCEPTION = null;

            private DefaultData() {
            }

            static IOException access$900() {
                return EXCEPTION;
            }

            static SpoofData access$1000() {
                return INSTANCE;
            }

            static {
                try {
                    INSTANCE = new SpoofData(ICUBinary.getRequiredData("confusables.cfu"), null);
                } catch (IOException iOException) {
                    EXCEPTION = iOException;
                }
            }
        }

        private static final class IsAcceptable
        implements ICUBinary.Authenticate {
            private IsAcceptable() {
            }

            @Override
            public boolean isDataVersionAcceptable(byte[] byArray) {
                return byArray[0] == 2 || byArray[1] != 0 || byArray[2] != 0 || byArray[3] != 0;
            }

            IsAcceptable(1 var1_1) {
                this();
            }
        }
    }

    private static final class ConfusableDataUtils {
        public static final int FORMAT_VERSION = 2;
        static final boolean $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();

        private ConfusableDataUtils() {
        }

        public static final int keyToCodePoint(int n) {
            return n & 0xFFFFFF;
        }

        public static final int keyToLength(int n) {
            return ((n & 0xFF000000) >> 24) + 1;
        }

        public static final int codePointAndLengthToKey(int n, int n2) {
            if (!$assertionsDisabled && (n & 0xFFFFFF) != n) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n2 > 256) {
                throw new AssertionError();
            }
            return n | n2 - 1 << 24;
        }
    }

    public static class CheckResult {
        public int checks = 0;
        @Deprecated
        public int position = 0;
        public UnicodeSet numerics;
        public RestrictionLevel restrictionLevel;

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("checks:");
            if (this.checks == 0) {
                stringBuilder.append(" none");
            } else if (this.checks == -1) {
                stringBuilder.append(" all");
            } else {
                if ((this.checks & 1) != 0) {
                    stringBuilder.append(" SINGLE_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 2) != 0) {
                    stringBuilder.append(" MIXED_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 4) != 0) {
                    stringBuilder.append(" WHOLE_SCRIPT_CONFUSABLE");
                }
                if ((this.checks & 8) != 0) {
                    stringBuilder.append(" ANY_CASE");
                }
                if ((this.checks & 0x10) != 0) {
                    stringBuilder.append(" RESTRICTION_LEVEL");
                }
                if ((this.checks & 0x20) != 0) {
                    stringBuilder.append(" INVISIBLE");
                }
                if ((this.checks & 0x40) != 0) {
                    stringBuilder.append(" CHAR_LIMIT");
                }
                if ((this.checks & 0x80) != 0) {
                    stringBuilder.append(" MIXED_NUMBERS");
                }
            }
            stringBuilder.append(", numerics: ").append(this.numerics.toPattern(true));
            stringBuilder.append(", position: ").append(this.position);
            stringBuilder.append(", restrictionLevel: ").append((Object)this.restrictionLevel);
            return stringBuilder.toString();
        }
    }

    public static class Builder {
        int fChecks;
        SpoofData fSpoofData;
        final UnicodeSet fAllowedCharsSet = new UnicodeSet(0, 0x10FFFF);
        final Set<ULocale> fAllowedLocales = new LinkedHashSet<ULocale>();
        private RestrictionLevel fRestrictionLevel;

        public Builder() {
            this.fChecks = -1;
            this.fSpoofData = null;
            this.fRestrictionLevel = RestrictionLevel.HIGHLY_RESTRICTIVE;
        }

        public Builder(SpoofChecker spoofChecker) {
            this.fChecks = SpoofChecker.access$000(spoofChecker);
            this.fSpoofData = SpoofChecker.access$100(spoofChecker);
            this.fAllowedCharsSet.set(SpoofChecker.access$200(spoofChecker));
            this.fAllowedLocales.addAll(SpoofChecker.access$300(spoofChecker));
            this.fRestrictionLevel = SpoofChecker.access$400(spoofChecker);
        }

        public SpoofChecker build() {
            if (this.fSpoofData == null) {
                this.fSpoofData = SpoofData.getDefault();
            }
            SpoofChecker spoofChecker = new SpoofChecker(null);
            SpoofChecker.access$002(spoofChecker, this.fChecks);
            SpoofChecker.access$102(spoofChecker, this.fSpoofData);
            SpoofChecker.access$202(spoofChecker, (UnicodeSet)this.fAllowedCharsSet.clone());
            SpoofChecker.access$200(spoofChecker).freeze();
            SpoofChecker.access$302(spoofChecker, new HashSet<ULocale>(this.fAllowedLocales));
            SpoofChecker.access$402(spoofChecker, this.fRestrictionLevel);
            return spoofChecker;
        }

        public Builder setData(Reader reader) throws ParseException, IOException {
            this.fSpoofData = new SpoofData(null);
            ConfusabledataBuilder.buildConfusableData(reader, this.fSpoofData);
            return this;
        }

        @Deprecated
        public Builder setData(Reader reader, Reader reader2) throws ParseException, IOException {
            this.setData(reader);
            return this;
        }

        public Builder setChecks(int n) {
            if (0 != (n & 0)) {
                throw new IllegalArgumentException("Bad Spoof Checks value.");
            }
            this.fChecks = n & 0xFFFFFFFF;
            return this;
        }

        public Builder setAllowedLocales(Set<ULocale> set) {
            this.fAllowedCharsSet.clear();
            for (ULocale uLocale : set) {
                this.addScriptChars(uLocale, this.fAllowedCharsSet);
            }
            this.fAllowedLocales.clear();
            if (set.size() == 0) {
                this.fAllowedCharsSet.add(0, 0x10FFFF);
                this.fChecks &= 0xFFFFFFBF;
                return this;
            }
            UnicodeSet unicodeSet = new UnicodeSet();
            unicodeSet.applyIntPropertyValue(4106, 0);
            this.fAllowedCharsSet.addAll(unicodeSet);
            unicodeSet.applyIntPropertyValue(4106, 1);
            this.fAllowedCharsSet.addAll(unicodeSet);
            this.fAllowedLocales.clear();
            this.fAllowedLocales.addAll(set);
            this.fChecks |= 0x40;
            return this;
        }

        public Builder setAllowedJavaLocales(Set<Locale> set) {
            HashSet<ULocale> hashSet = new HashSet<ULocale>(set.size());
            for (Locale locale : set) {
                hashSet.add(ULocale.forLocale(locale));
            }
            return this.setAllowedLocales(hashSet);
        }

        private void addScriptChars(ULocale uLocale, UnicodeSet unicodeSet) {
            int[] nArray = UScript.getCode(uLocale);
            if (nArray != null) {
                UnicodeSet unicodeSet2 = new UnicodeSet();
                for (int i = 0; i < nArray.length; ++i) {
                    unicodeSet2.applyIntPropertyValue(4106, nArray[i]);
                    unicodeSet.addAll(unicodeSet2);
                }
            }
        }

        public Builder setAllowedChars(UnicodeSet unicodeSet) {
            this.fAllowedCharsSet.set(unicodeSet);
            this.fAllowedLocales.clear();
            this.fChecks |= 0x40;
            return this;
        }

        public Builder setRestrictionLevel(RestrictionLevel restrictionLevel) {
            this.fRestrictionLevel = restrictionLevel;
            this.fChecks |= 0x90;
            return this;
        }

        private static class ConfusabledataBuilder {
            private Hashtable<Integer, SPUString> fTable = new Hashtable();
            private UnicodeSet fKeySet = new UnicodeSet();
            private StringBuffer fStringTable;
            private ArrayList<Integer> fKeyVec = new ArrayList();
            private ArrayList<Integer> fValueVec = new ArrayList();
            private SPUStringPool stringPool = new SPUStringPool();
            private Pattern fParseLine;
            private Pattern fParseHexNum;
            private int fLineNum;
            static final boolean $assertionsDisabled = !SpoofChecker.class.desiredAssertionStatus();

            ConfusabledataBuilder() {
            }

            void build(Reader reader, SpoofData spoofData) throws ParseException, IOException {
                int n;
                int n2;
                Object object;
                int n3;
                Object object2;
                StringBuffer stringBuffer = new StringBuffer();
                LineNumberReader lineNumberReader = new LineNumberReader(reader);
                while ((object2 = lineNumberReader.readLine()) != null) {
                    stringBuffer.append((String)object2);
                    stringBuffer.append('\n');
                }
                this.fParseLine = Pattern.compile("(?m)^[ \\t]*([0-9A-Fa-f]+)[ \\t]+;[ \\t]*([0-9A-Fa-f]+(?:[ \\t]+[0-9A-Fa-f]+)*)[ \\t]*;\\s*(?:(SL)|(SA)|(ML)|(MA))[ \\t]*(?:#.*?)?$|^([ \\t]*(?:#.*?)?)$|^(.*?)$");
                this.fParseHexNum = Pattern.compile("\\s*([0-9A-F]+)");
                if (stringBuffer.charAt(0) == '\ufeff') {
                    stringBuffer.setCharAt(0, ' ');
                }
                object2 = this.fParseLine.matcher(stringBuffer);
                while (((Matcher)object2).find()) {
                    ++this.fLineNum;
                    if (((Matcher)object2).start(7) >= 0) continue;
                    if (((Matcher)object2).start(8) >= 0) {
                        throw new ParseException("Confusables, line " + this.fLineNum + ": Unrecognized Line: " + ((Matcher)object2).group(8), ((Matcher)object2).start(8));
                    }
                    n3 = Integer.parseInt(((Matcher)object2).group(1), 16);
                    if (n3 > 0x10FFFF) {
                        throw new ParseException("Confusables, line " + this.fLineNum + ": Bad code point: " + ((Matcher)object2).group(1), ((Matcher)object2).start(1));
                    }
                    Matcher matcher = this.fParseHexNum.matcher(((Matcher)object2).group(2));
                    object = new StringBuilder();
                    while (matcher.find()) {
                        int n4 = Integer.parseInt(matcher.group(1), 16);
                        if (n4 > 0x10FFFF) {
                            throw new ParseException("Confusables, line " + this.fLineNum + ": Bad code point: " + Integer.toString(n4, 16), ((Matcher)object2).start(2));
                        }
                        ((StringBuilder)object).appendCodePoint(n4);
                    }
                    if (!$assertionsDisabled && ((StringBuilder)object).length() < 1) {
                        throw new AssertionError();
                    }
                    SPUString sPUString = this.stringPool.addString(((StringBuilder)object).toString());
                    this.fTable.put(n3, sPUString);
                    this.fKeySet.add(n3);
                }
                this.stringPool.sort();
                this.fStringTable = new StringBuffer();
                n3 = this.stringPool.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    object = this.stringPool.getByIndex(n2);
                    int n5 = ((SPUString)object).fStr.length();
                    n = this.fStringTable.length();
                    if (n5 == 1) {
                        ((SPUString)object).fCharOrStrTableIndex = ((SPUString)object).fStr.charAt(0);
                        continue;
                    }
                    ((SPUString)object).fCharOrStrTableIndex = n;
                    this.fStringTable.append(((SPUString)object).fStr);
                }
                for (String string : this.fKeySet) {
                    n = string.codePointAt(0);
                    SPUString sPUString = this.fTable.get(n);
                    if (!$assertionsDisabled && sPUString == null) {
                        throw new AssertionError();
                    }
                    if (sPUString.fStr.length() > 256) {
                        throw new IllegalArgumentException("Confusable prototypes cannot be longer than 256 entries.");
                    }
                    int n6 = ConfusableDataUtils.codePointAndLengthToKey(n, sPUString.fStr.length());
                    int n7 = sPUString.fCharOrStrTableIndex;
                    this.fKeyVec.add(n6);
                    this.fValueVec.add(n7);
                }
                int n8 = this.fKeyVec.size();
                spoofData.fCFUKeys = new int[n8];
                int n9 = 0;
                for (n2 = 0; n2 < n8; ++n2) {
                    n = this.fKeyVec.get(n2);
                    int n10 = ConfusableDataUtils.keyToCodePoint(n);
                    if (!$assertionsDisabled && n10 <= n9) {
                        throw new AssertionError();
                    }
                    spoofData.fCFUKeys[n2] = n;
                    n9 = n10;
                }
                n = this.fValueVec.size();
                if (!$assertionsDisabled && n8 != n) {
                    throw new AssertionError();
                }
                spoofData.fCFUValues = new short[n];
                n2 = 0;
                for (int n6 : this.fValueVec) {
                    if (!$assertionsDisabled && n6 >= 65535) {
                        throw new AssertionError();
                    }
                    spoofData.fCFUValues[n2++] = (short)n6;
                }
                spoofData.fCFUStrings = this.fStringTable.toString();
            }

            public static void buildConfusableData(Reader reader, SpoofData spoofData) throws IOException, ParseException {
                ConfusabledataBuilder confusabledataBuilder = new ConfusabledataBuilder();
                confusabledataBuilder.build(reader, spoofData);
            }

            private static class SPUStringPool {
                private Vector<SPUString> fVec = new Vector();
                private Hashtable<String, SPUString> fHash = new Hashtable();

                public int size() {
                    return this.fVec.size();
                }

                public SPUString getByIndex(int n) {
                    SPUString sPUString = this.fVec.elementAt(n);
                    return sPUString;
                }

                public SPUString addString(String string) {
                    SPUString sPUString = this.fHash.get(string);
                    if (sPUString == null) {
                        sPUString = new SPUString(string);
                        this.fHash.put(string, sPUString);
                        this.fVec.addElement(sPUString);
                    }
                    return sPUString;
                }

                public void sort() {
                    Collections.sort(this.fVec, SPUStringComparator.INSTANCE);
                }
            }

            private static class SPUStringComparator
            implements Comparator<SPUString> {
                static final SPUStringComparator INSTANCE = new SPUStringComparator();

                private SPUStringComparator() {
                }

                @Override
                public int compare(SPUString sPUString, SPUString sPUString2) {
                    int n;
                    int n2 = sPUString.fStr.length();
                    if (n2 < (n = sPUString2.fStr.length())) {
                        return 1;
                    }
                    if (n2 > n) {
                        return 0;
                    }
                    return sPUString.fStr.compareTo(sPUString2.fStr);
                }

                @Override
                public int compare(Object object, Object object2) {
                    return this.compare((SPUString)object, (SPUString)object2);
                }
            }

            private static class SPUString {
                String fStr;
                int fCharOrStrTableIndex;

                SPUString(String string) {
                    this.fStr = string;
                    this.fCharOrStrTableIndex = 0;
                }
            }
        }
    }

    public static enum RestrictionLevel {
        ASCII,
        SINGLE_SCRIPT_RESTRICTIVE,
        HIGHLY_RESTRICTIVE,
        MODERATELY_RESTRICTIVE,
        MINIMALLY_RESTRICTIVE,
        UNRESTRICTIVE;

    }
}

