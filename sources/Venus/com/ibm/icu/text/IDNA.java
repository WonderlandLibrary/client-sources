/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.IDNA2003;
import com.ibm.icu.impl.UTS46;
import com.ibm.icu.text.StringPrepParseException;
import com.ibm.icu.text.UCharacterIterator;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract class IDNA {
    public static final int DEFAULT = 0;
    @Deprecated
    public static final int ALLOW_UNASSIGNED = 1;
    public static final int USE_STD3_RULES = 2;
    public static final int CHECK_BIDI = 4;
    public static final int CHECK_CONTEXTJ = 8;
    public static final int NONTRANSITIONAL_TO_ASCII = 16;
    public static final int NONTRANSITIONAL_TO_UNICODE = 32;
    public static final int CHECK_CONTEXTO = 64;

    public static IDNA getUTS46Instance(int n) {
        return new UTS46(n);
    }

    public abstract StringBuilder labelToASCII(CharSequence var1, StringBuilder var2, Info var3);

    public abstract StringBuilder labelToUnicode(CharSequence var1, StringBuilder var2, Info var3);

    public abstract StringBuilder nameToASCII(CharSequence var1, StringBuilder var2, Info var3);

    public abstract StringBuilder nameToUnicode(CharSequence var1, StringBuilder var2, Info var3);

    @Deprecated
    protected static void resetInfo(Info info) {
        Info.access$000(info);
    }

    @Deprecated
    protected static boolean hasCertainErrors(Info info, EnumSet<Error> enumSet) {
        return !Info.access$100(info).isEmpty() && !Collections.disjoint(Info.access$100(info), enumSet);
    }

    @Deprecated
    protected static boolean hasCertainLabelErrors(Info info, EnumSet<Error> enumSet) {
        return !Info.access$200(info).isEmpty() && !Collections.disjoint(Info.access$200(info), enumSet);
    }

    @Deprecated
    protected static void addLabelError(Info info, Error error2) {
        Info.access$200(info).add(error2);
    }

    @Deprecated
    protected static void promoteAndResetLabelErrors(Info info) {
        if (!Info.access$200(info).isEmpty()) {
            Info.access$100(info).addAll(Info.access$200(info));
            Info.access$200(info).clear();
        }
    }

    @Deprecated
    protected static void addError(Info info, Error error2) {
        Info.access$100(info).add(error2);
    }

    @Deprecated
    protected static void setTransitionalDifferent(Info info) {
        Info.access$302(info, true);
    }

    @Deprecated
    protected static void setBiDi(Info info) {
        Info.access$402(info, true);
    }

    @Deprecated
    protected static boolean isBiDi(Info info) {
        return Info.access$400(info);
    }

    @Deprecated
    protected static void setNotOkBiDi(Info info) {
        Info.access$502(info, false);
    }

    @Deprecated
    protected static boolean isOkBiDi(Info info) {
        return Info.access$500(info);
    }

    @Deprecated
    protected IDNA() {
    }

    @Deprecated
    public static StringBuffer convertToASCII(String string, int n) throws StringPrepParseException {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(string);
        return IDNA.convertToASCII(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertToASCII(StringBuffer stringBuffer, int n) throws StringPrepParseException {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(stringBuffer);
        return IDNA.convertToASCII(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertToASCII(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        return IDNA2003.convertToASCII(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertIDNToASCII(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        return IDNA.convertIDNToASCII(uCharacterIterator.getText(), n);
    }

    @Deprecated
    public static StringBuffer convertIDNToASCII(StringBuffer stringBuffer, int n) throws StringPrepParseException {
        return IDNA.convertIDNToASCII(stringBuffer.toString(), n);
    }

    @Deprecated
    public static StringBuffer convertIDNToASCII(String string, int n) throws StringPrepParseException {
        return IDNA2003.convertIDNToASCII(string, n);
    }

    @Deprecated
    public static StringBuffer convertToUnicode(String string, int n) throws StringPrepParseException {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(string);
        return IDNA.convertToUnicode(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertToUnicode(StringBuffer stringBuffer, int n) throws StringPrepParseException {
        UCharacterIterator uCharacterIterator = UCharacterIterator.getInstance(stringBuffer);
        return IDNA.convertToUnicode(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertToUnicode(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        return IDNA2003.convertToUnicode(uCharacterIterator, n);
    }

    @Deprecated
    public static StringBuffer convertIDNToUnicode(UCharacterIterator uCharacterIterator, int n) throws StringPrepParseException {
        return IDNA.convertIDNToUnicode(uCharacterIterator.getText(), n);
    }

    @Deprecated
    public static StringBuffer convertIDNToUnicode(StringBuffer stringBuffer, int n) throws StringPrepParseException {
        return IDNA.convertIDNToUnicode(stringBuffer.toString(), n);
    }

    @Deprecated
    public static StringBuffer convertIDNToUnicode(String string, int n) throws StringPrepParseException {
        return IDNA2003.convertIDNToUnicode(string, n);
    }

    @Deprecated
    public static int compare(StringBuffer stringBuffer, StringBuffer stringBuffer2, int n) throws StringPrepParseException {
        if (stringBuffer == null || stringBuffer2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(stringBuffer.toString(), stringBuffer2.toString(), n);
    }

    @Deprecated
    public static int compare(String string, String string2, int n) throws StringPrepParseException {
        if (string == null || string2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(string, string2, n);
    }

    @Deprecated
    public static int compare(UCharacterIterator uCharacterIterator, UCharacterIterator uCharacterIterator2, int n) throws StringPrepParseException {
        if (uCharacterIterator == null || uCharacterIterator2 == null) {
            throw new IllegalArgumentException("One of the source buffers is null");
        }
        return IDNA2003.compare(uCharacterIterator.getText(), uCharacterIterator2.getText(), n);
    }

    public static enum Error {
        EMPTY_LABEL,
        LABEL_TOO_LONG,
        DOMAIN_NAME_TOO_LONG,
        LEADING_HYPHEN,
        TRAILING_HYPHEN,
        HYPHEN_3_4,
        LEADING_COMBINING_MARK,
        DISALLOWED,
        PUNYCODE,
        LABEL_HAS_DOT,
        INVALID_ACE_LABEL,
        BIDI,
        CONTEXTJ,
        CONTEXTO_PUNCTUATION,
        CONTEXTO_DIGITS;

    }

    public static final class Info {
        private EnumSet<Error> errors = EnumSet.noneOf(Error.class);
        private EnumSet<Error> labelErrors = EnumSet.noneOf(Error.class);
        private boolean isTransDiff = false;
        private boolean isBiDi = false;
        private boolean isOkBiDi = true;

        public boolean hasErrors() {
            return !this.errors.isEmpty();
        }

        public Set<Error> getErrors() {
            return this.errors;
        }

        public boolean isTransitionalDifferent() {
            return this.isTransDiff;
        }

        private void reset() {
            this.errors.clear();
            this.labelErrors.clear();
            this.isTransDiff = false;
            this.isBiDi = false;
            this.isOkBiDi = true;
        }

        static void access$000(Info info) {
            info.reset();
        }

        static EnumSet access$100(Info info) {
            return info.errors;
        }

        static EnumSet access$200(Info info) {
            return info.labelErrors;
        }

        static boolean access$302(Info info, boolean bl) {
            info.isTransDiff = bl;
            return info.isTransDiff;
        }

        static boolean access$402(Info info, boolean bl) {
            info.isBiDi = bl;
            return info.isBiDi;
        }

        static boolean access$400(Info info) {
            return info.isBiDi;
        }

        static boolean access$502(Info info, boolean bl) {
            info.isOkBiDi = bl;
            return info.isOkBiDi;
        }

        static boolean access$500(Info info) {
            return info.isOkBiDi;
        }
    }
}

