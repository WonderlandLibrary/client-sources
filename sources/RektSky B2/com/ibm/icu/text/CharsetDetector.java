package com.ibm.icu.text;

import java.io.*;
import java.util.*;

public class CharsetDetector
{
    private static final int kBufSize = 8000;
    byte[] fInputBytes;
    int fInputLen;
    short[] fByteStats;
    boolean fC1Bytes;
    String fDeclaredEncoding;
    byte[] fRawInput;
    int fRawLength;
    InputStream fInputStream;
    private boolean fStripTags;
    private boolean[] fEnabledRecognizers;
    private static final List<CSRecognizerInfo> ALL_CS_RECOGNIZERS;
    
    public CharsetDetector() {
        this.fInputBytes = new byte[8000];
        this.fByteStats = new short[256];
        this.fC1Bytes = false;
        this.fStripTags = false;
    }
    
    public CharsetDetector setDeclaredEncoding(final String encoding) {
        this.fDeclaredEncoding = encoding;
        return this;
    }
    
    public CharsetDetector setText(final byte[] in) {
        this.fRawInput = in;
        this.fRawLength = in.length;
        return this;
    }
    
    public CharsetDetector setText(final InputStream in) throws IOException {
        (this.fInputStream = in).mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        int bytesRead;
        for (int remainingLength = 8000; remainingLength > 0; remainingLength -= bytesRead) {
            bytesRead = this.fInputStream.read(this.fRawInput, this.fRawLength, remainingLength);
            if (bytesRead <= 0) {
                break;
            }
            this.fRawLength += bytesRead;
        }
        this.fInputStream.reset();
        return this;
    }
    
    public CharsetMatch detect() {
        final CharsetMatch[] matches = this.detectAll();
        if (matches == null || matches.length == 0) {
            return null;
        }
        return matches[0];
    }
    
    public CharsetMatch[] detectAll() {
        final ArrayList<CharsetMatch> matches = new ArrayList<CharsetMatch>();
        this.MungeInput();
        for (int i = 0; i < CharsetDetector.ALL_CS_RECOGNIZERS.size(); ++i) {
            final CSRecognizerInfo rcinfo = CharsetDetector.ALL_CS_RECOGNIZERS.get(i);
            final boolean active = (this.fEnabledRecognizers != null) ? this.fEnabledRecognizers[i] : rcinfo.isDefaultEnabled;
            if (active) {
                final CharsetMatch m = rcinfo.recognizer.match(this);
                if (m != null) {
                    matches.add(m);
                }
            }
        }
        Collections.sort(matches);
        Collections.reverse(matches);
        CharsetMatch[] resultArray = new CharsetMatch[matches.size()];
        resultArray = matches.toArray(resultArray);
        return resultArray;
    }
    
    public Reader getReader(final InputStream in, final String declaredEncoding) {
        this.fDeclaredEncoding = declaredEncoding;
        try {
            this.setText(in);
            final CharsetMatch match = this.detect();
            if (match == null) {
                return null;
            }
            return match.getReader();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    public String getString(final byte[] in, final String declaredEncoding) {
        this.fDeclaredEncoding = declaredEncoding;
        try {
            this.setText(in);
            final CharsetMatch match = this.detect();
            if (match == null) {
                return null;
            }
            return match.getString(-1);
        }
        catch (IOException e) {
            return null;
        }
    }
    
    public static String[] getAllDetectableCharsets() {
        final String[] allCharsetNames = new String[CharsetDetector.ALL_CS_RECOGNIZERS.size()];
        for (int i = 0; i < allCharsetNames.length; ++i) {
            allCharsetNames[i] = CharsetDetector.ALL_CS_RECOGNIZERS.get(i).recognizer.getName();
        }
        return allCharsetNames;
    }
    
    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }
    
    public boolean enableInputFilter(final boolean filter) {
        final boolean previous = this.fStripTags;
        this.fStripTags = filter;
        return previous;
    }
    
    private void MungeInput() {
        int srci = 0;
        int dsti = 0;
        boolean inMarkup = false;
        int openTags = 0;
        int badTags = 0;
        if (this.fStripTags) {
            for (srci = 0; srci < this.fRawLength && dsti < this.fInputBytes.length; ++srci) {
                final byte b = this.fRawInput[srci];
                if (b == 60) {
                    if (inMarkup) {
                        ++badTags;
                    }
                    inMarkup = true;
                    ++openTags;
                }
                if (!inMarkup) {
                    this.fInputBytes[dsti++] = b;
                }
                if (b == 62) {
                    inMarkup = false;
                }
            }
            this.fInputLen = dsti;
        }
        if (openTags < 5 || openTags / 5 < badTags || (this.fInputLen < 100 && this.fRawLength > 600)) {
            int limit = this.fRawLength;
            if (limit > 8000) {
                limit = 8000;
            }
            for (srci = 0; srci < limit; ++srci) {
                this.fInputBytes[srci] = this.fRawInput[srci];
            }
            this.fInputLen = srci;
        }
        Arrays.fill(this.fByteStats, (short)0);
        for (srci = 0; srci < this.fInputLen; ++srci) {
            final int val = this.fInputBytes[srci] & 0xFF;
            final short[] fByteStats = this.fByteStats;
            final int n = val;
            ++fByteStats[n];
        }
        this.fC1Bytes = false;
        for (int i = 128; i <= 159; ++i) {
            if (this.fByteStats[i] != 0) {
                this.fC1Bytes = true;
                break;
            }
        }
    }
    
    @Deprecated
    public String[] getDetectableCharsets() {
        final List<String> csnames = new ArrayList<String>(CharsetDetector.ALL_CS_RECOGNIZERS.size());
        for (int i = 0; i < CharsetDetector.ALL_CS_RECOGNIZERS.size(); ++i) {
            final CSRecognizerInfo rcinfo = CharsetDetector.ALL_CS_RECOGNIZERS.get(i);
            final boolean active = (this.fEnabledRecognizers == null) ? rcinfo.isDefaultEnabled : this.fEnabledRecognizers[i];
            if (active) {
                csnames.add(rcinfo.recognizer.getName());
            }
        }
        return csnames.toArray(new String[csnames.size()]);
    }
    
    @Deprecated
    public CharsetDetector setDetectableCharset(final String encoding, final boolean enabled) {
        int modIdx = -1;
        boolean isDefaultVal = false;
        for (int i = 0; i < CharsetDetector.ALL_CS_RECOGNIZERS.size(); ++i) {
            final CSRecognizerInfo csrinfo = CharsetDetector.ALL_CS_RECOGNIZERS.get(i);
            if (csrinfo.recognizer.getName().equals(encoding)) {
                modIdx = i;
                isDefaultVal = (csrinfo.isDefaultEnabled == enabled);
                break;
            }
        }
        if (modIdx < 0) {
            throw new IllegalArgumentException("Invalid encoding: \"" + encoding + "\"");
        }
        if (this.fEnabledRecognizers == null && !isDefaultVal) {
            this.fEnabledRecognizers = new boolean[CharsetDetector.ALL_CS_RECOGNIZERS.size()];
            for (int i = 0; i < CharsetDetector.ALL_CS_RECOGNIZERS.size(); ++i) {
                this.fEnabledRecognizers[i] = CharsetDetector.ALL_CS_RECOGNIZERS.get(i).isDefaultEnabled;
            }
        }
        if (this.fEnabledRecognizers != null) {
            this.fEnabledRecognizers[modIdx] = enabled;
        }
        return this;
    }
    
    static {
        final List<CSRecognizerInfo> list = new ArrayList<CSRecognizerInfo>();
        list.add(new CSRecognizerInfo(new CharsetRecog_UTF8(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022JP(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022CN(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022KR(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_big5(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
        list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
        ALL_CS_RECOGNIZERS = Collections.unmodifiableList((List<? extends CSRecognizerInfo>)list);
    }
    
    private static class CSRecognizerInfo
    {
        CharsetRecognizer recognizer;
        boolean isDefaultEnabled;
        
        CSRecognizerInfo(final CharsetRecognizer recognizer, final boolean isDefaultEnabled) {
            this.recognizer = recognizer;
            this.isDefaultEnabled = isDefaultEnabled;
        }
    }
}
