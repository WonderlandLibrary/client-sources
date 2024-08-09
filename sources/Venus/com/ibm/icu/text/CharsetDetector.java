/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.text.CharsetMatch;
import com.ibm.icu.text.CharsetRecog_2022;
import com.ibm.icu.text.CharsetRecog_UTF8;
import com.ibm.icu.text.CharsetRecog_Unicode;
import com.ibm.icu.text.CharsetRecog_mbcs;
import com.ibm.icu.text.CharsetRecog_sbcs;
import com.ibm.icu.text.CharsetRecognizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharsetDetector {
    private static final int kBufSize = 8000;
    byte[] fInputBytes = new byte[8000];
    int fInputLen;
    short[] fByteStats = new short[256];
    boolean fC1Bytes = false;
    String fDeclaredEncoding;
    byte[] fRawInput;
    int fRawLength;
    InputStream fInputStream;
    private boolean fStripTags = false;
    private boolean[] fEnabledRecognizers;
    private static final List<CSRecognizerInfo> ALL_CS_RECOGNIZERS;

    public CharsetDetector setDeclaredEncoding(String string) {
        this.fDeclaredEncoding = string;
        return this;
    }

    public CharsetDetector setText(byte[] byArray) {
        this.fRawInput = byArray;
        this.fRawLength = byArray.length;
        return this;
    }

    public CharsetDetector setText(InputStream inputStream) throws IOException {
        int n;
        this.fInputStream = inputStream;
        this.fInputStream.mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        for (int i = 8000; i > 0 && (n = this.fInputStream.read(this.fRawInput, this.fRawLength, i)) > 0; i -= n) {
            this.fRawLength += n;
        }
        this.fInputStream.reset();
        return this;
    }

    public CharsetMatch detect() {
        CharsetMatch[] charsetMatchArray = this.detectAll();
        if (charsetMatchArray == null || charsetMatchArray.length == 0) {
            return null;
        }
        return charsetMatchArray[0];
    }

    public CharsetMatch[] detectAll() {
        ArrayList<CharsetMatch> arrayList = new ArrayList<CharsetMatch>();
        this.MungeInput();
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            CharsetMatch charsetMatch;
            boolean bl;
            CSRecognizerInfo cSRecognizerInfo = ALL_CS_RECOGNIZERS.get(i);
            boolean bl2 = bl = this.fEnabledRecognizers != null ? this.fEnabledRecognizers[i] : cSRecognizerInfo.isDefaultEnabled;
            if (!bl || (charsetMatch = cSRecognizerInfo.recognizer.match(this)) == null) continue;
            arrayList.add(charsetMatch);
        }
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        CharsetMatch[] charsetMatchArray = new CharsetMatch[arrayList.size()];
        charsetMatchArray = arrayList.toArray(charsetMatchArray);
        return charsetMatchArray;
    }

    public Reader getReader(InputStream inputStream, String string) {
        this.fDeclaredEncoding = string;
        try {
            this.setText(inputStream);
            CharsetMatch charsetMatch = this.detect();
            if (charsetMatch == null) {
                return null;
            }
            return charsetMatch.getReader();
        } catch (IOException iOException) {
            return null;
        }
    }

    public String getString(byte[] byArray, String string) {
        this.fDeclaredEncoding = string;
        try {
            this.setText(byArray);
            CharsetMatch charsetMatch = this.detect();
            if (charsetMatch == null) {
                return null;
            }
            return charsetMatch.getString(-1);
        } catch (IOException iOException) {
            return null;
        }
    }

    public static String[] getAllDetectableCharsets() {
        String[] stringArray = new String[ALL_CS_RECOGNIZERS.size()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = CharsetDetector.ALL_CS_RECOGNIZERS.get((int)i).recognizer.getName();
        }
        return stringArray;
    }

    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }

    public boolean enableInputFilter(boolean bl) {
        boolean bl2 = this.fStripTags;
        this.fStripTags = bl;
        return bl2;
    }

    private void MungeInput() {
        int n;
        int n2 = 0;
        int n3 = 0;
        boolean bl = false;
        int n4 = 0;
        int n5 = 0;
        if (this.fStripTags) {
            for (n2 = 0; n2 < this.fRawLength && n3 < this.fInputBytes.length; ++n2) {
                byte by = this.fRawInput[n2];
                if (by == 60) {
                    if (bl) {
                        ++n5;
                    }
                    bl = true;
                    ++n4;
                }
                if (!bl) {
                    this.fInputBytes[n3++] = by;
                }
                if (by != 62) continue;
                bl = false;
            }
            this.fInputLen = n3;
        }
        if (n4 < 5 || n4 / 5 < n5 || this.fInputLen < 100 && this.fRawLength > 600) {
            n = this.fRawLength;
            if (n > 8000) {
                n = 8000;
            }
            for (n2 = 0; n2 < n; ++n2) {
                this.fInputBytes[n2] = this.fRawInput[n2];
            }
            this.fInputLen = n2;
        }
        Arrays.fill(this.fByteStats, (short)0);
        for (n2 = 0; n2 < this.fInputLen; ++n2) {
            int n6 = n = this.fInputBytes[n2] & 0xFF;
            this.fByteStats[n6] = (short)(this.fByteStats[n6] + 1);
        }
        this.fC1Bytes = false;
        for (n = 128; n <= 159; ++n) {
            if (this.fByteStats[n] == 0) continue;
            this.fC1Bytes = true;
            break;
        }
    }

    @Deprecated
    public String[] getDetectableCharsets() {
        ArrayList<String> arrayList = new ArrayList<String>(ALL_CS_RECOGNIZERS.size());
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            boolean bl;
            CSRecognizerInfo cSRecognizerInfo = ALL_CS_RECOGNIZERS.get(i);
            boolean bl2 = bl = this.fEnabledRecognizers == null ? cSRecognizerInfo.isDefaultEnabled : this.fEnabledRecognizers[i];
            if (!bl) continue;
            arrayList.add(cSRecognizerInfo.recognizer.getName());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    @Deprecated
    public CharsetDetector setDetectableCharset(String string, boolean bl) {
        int n;
        int n2 = -1;
        boolean bl2 = false;
        for (n = 0; n < ALL_CS_RECOGNIZERS.size(); ++n) {
            CSRecognizerInfo cSRecognizerInfo = ALL_CS_RECOGNIZERS.get(n);
            if (!cSRecognizerInfo.recognizer.getName().equals(string)) continue;
            n2 = n;
            bl2 = cSRecognizerInfo.isDefaultEnabled == bl;
            break;
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("Invalid encoding: \"" + string + "\"");
        }
        if (this.fEnabledRecognizers == null && !bl2) {
            this.fEnabledRecognizers = new boolean[ALL_CS_RECOGNIZERS.size()];
            for (n = 0; n < ALL_CS_RECOGNIZERS.size(); ++n) {
                this.fEnabledRecognizers[n] = CharsetDetector.ALL_CS_RECOGNIZERS.get((int)n).isDefaultEnabled;
            }
        }
        if (this.fEnabledRecognizers != null) {
            this.fEnabledRecognizers[n2] = bl;
        }
        return this;
    }

    static {
        ArrayList<CSRecognizerInfo> arrayList = new ArrayList<CSRecognizerInfo>();
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_UTF8(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022JP(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022CN(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022KR(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_big5(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
        ALL_CS_RECOGNIZERS = Collections.unmodifiableList(arrayList);
    }

    private static class CSRecognizerInfo {
        CharsetRecognizer recognizer;
        boolean isDefaultEnabled;

        CSRecognizerInfo(CharsetRecognizer charsetRecognizer, boolean bl) {
            this.recognizer = charsetRecognizer;
            this.isDefaultEnabled = bl;
        }
    }
}

