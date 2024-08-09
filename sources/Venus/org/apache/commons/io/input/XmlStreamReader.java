/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.io.input.XmlStreamReaderException;

public class XmlStreamReader
extends Reader {
    private static final int BUFFER_SIZE = 4096;
    private static final String UTF_8 = "UTF-8";
    private static final String US_ASCII = "US-ASCII";
    private static final String UTF_16BE = "UTF-16BE";
    private static final String UTF_16LE = "UTF-16LE";
    private static final String UTF_32BE = "UTF-32BE";
    private static final String UTF_32LE = "UTF-32LE";
    private static final String UTF_16 = "UTF-16";
    private static final String UTF_32 = "UTF-32";
    private static final String EBCDIC = "CP1047";
    private static final ByteOrderMark[] BOMS = new ByteOrderMark[]{ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE};
    private static final ByteOrderMark[] XML_GUESS_BYTES = new ByteOrderMark[]{new ByteOrderMark("UTF-8", 60, 63, 120, 109), new ByteOrderMark("UTF-16BE", 0, 60, 0, 63), new ByteOrderMark("UTF-16LE", 60, 0, 63, 0), new ByteOrderMark("UTF-32BE", 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109), new ByteOrderMark("UTF-32LE", 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0), new ByteOrderMark("CP1047", 76, 111, 167, 148)};
    private final Reader reader;
    private final String encoding;
    private final String defaultEncoding;
    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
    public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);
    private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
    private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
    private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
    private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
    private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    public XmlStreamReader(File file) throws IOException {
        this(new FileInputStream(file));
    }

    public XmlStreamReader(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public XmlStreamReader(InputStream inputStream, boolean bl) throws IOException {
        this(inputStream, bl, null);
    }

    public XmlStreamReader(InputStream inputStream, boolean bl, String string) throws IOException {
        this.defaultEncoding = string;
        BOMInputStream bOMInputStream = new BOMInputStream((InputStream)new BufferedInputStream(inputStream, 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream((InputStream)bOMInputStream, true, XML_GUESS_BYTES);
        this.encoding = this.doRawStream(bOMInputStream, bOMInputStream2, bl);
        this.reader = new InputStreamReader((InputStream)bOMInputStream2, this.encoding);
    }

    public XmlStreamReader(URL uRL) throws IOException {
        this(uRL.openConnection(), null);
    }

    public XmlStreamReader(URLConnection uRLConnection, String string) throws IOException {
        this.defaultEncoding = string;
        boolean bl = true;
        String string2 = uRLConnection.getContentType();
        InputStream inputStream = uRLConnection.getInputStream();
        BOMInputStream bOMInputStream = new BOMInputStream((InputStream)new BufferedInputStream(inputStream, 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream((InputStream)bOMInputStream, true, XML_GUESS_BYTES);
        this.encoding = uRLConnection instanceof HttpURLConnection || string2 != null ? this.doHttpStream(bOMInputStream, bOMInputStream2, string2, true) : this.doRawStream(bOMInputStream, bOMInputStream2, true);
        this.reader = new InputStreamReader((InputStream)bOMInputStream2, this.encoding);
    }

    public XmlStreamReader(InputStream inputStream, String string) throws IOException {
        this(inputStream, string, true);
    }

    public XmlStreamReader(InputStream inputStream, String string, boolean bl, String string2) throws IOException {
        this.defaultEncoding = string2;
        BOMInputStream bOMInputStream = new BOMInputStream((InputStream)new BufferedInputStream(inputStream, 4096), false, BOMS);
        BOMInputStream bOMInputStream2 = new BOMInputStream((InputStream)bOMInputStream, true, XML_GUESS_BYTES);
        this.encoding = this.doHttpStream(bOMInputStream, bOMInputStream2, string, bl);
        this.reader = new InputStreamReader((InputStream)bOMInputStream2, this.encoding);
    }

    public XmlStreamReader(InputStream inputStream, String string, boolean bl) throws IOException {
        this(inputStream, string, bl, null);
    }

    public String getEncoding() {
        return this.encoding;
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        return this.reader.read(cArray, n, n2);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    private String doRawStream(BOMInputStream bOMInputStream, BOMInputStream bOMInputStream2, boolean bl) throws IOException {
        String string = bOMInputStream.getBOMCharsetName();
        String string2 = bOMInputStream2.getBOMCharsetName();
        String string3 = XmlStreamReader.getXmlProlog(bOMInputStream2, string2);
        try {
            return this.calculateRawEncoding(string, string2, string3);
        } catch (XmlStreamReaderException xmlStreamReaderException) {
            if (bl) {
                return this.doLenientDetection(null, xmlStreamReaderException);
            }
            throw xmlStreamReaderException;
        }
    }

    private String doHttpStream(BOMInputStream bOMInputStream, BOMInputStream bOMInputStream2, String string, boolean bl) throws IOException {
        String string2 = bOMInputStream.getBOMCharsetName();
        String string3 = bOMInputStream2.getBOMCharsetName();
        String string4 = XmlStreamReader.getXmlProlog(bOMInputStream2, string3);
        try {
            return this.calculateHttpEncoding(string, string2, string3, string4, bl);
        } catch (XmlStreamReaderException xmlStreamReaderException) {
            if (bl) {
                return this.doLenientDetection(string, xmlStreamReaderException);
            }
            throw xmlStreamReaderException;
        }
    }

    private String doLenientDetection(String string, XmlStreamReaderException xmlStreamReaderException) throws IOException {
        String string2;
        if (string != null && string.startsWith("text/html")) {
            string = string.substring(9);
            string = "text/xml" + string;
            try {
                return this.calculateHttpEncoding(string, xmlStreamReaderException.getBomEncoding(), xmlStreamReaderException.getXmlGuessEncoding(), xmlStreamReaderException.getXmlEncoding(), false);
            } catch (XmlStreamReaderException xmlStreamReaderException2) {
                xmlStreamReaderException = xmlStreamReaderException2;
            }
        }
        if ((string2 = xmlStreamReaderException.getXmlEncoding()) == null) {
            string2 = xmlStreamReaderException.getContentTypeEncoding();
        }
        if (string2 == null) {
            string2 = this.defaultEncoding == null ? UTF_8 : this.defaultEncoding;
        }
        return string2;
    }

    String calculateRawEncoding(String string, String string2, String string3) throws IOException {
        if (string == null) {
            if (string2 == null || string3 == null) {
                return this.defaultEncoding == null ? UTF_8 : this.defaultEncoding;
            }
            if (string3.equals(UTF_16) && (string2.equals(UTF_16BE) || string2.equals(UTF_16LE))) {
                return string2;
            }
            return string3;
        }
        if (string.equals(UTF_8)) {
            if (string2 != null && !string2.equals(UTF_8)) {
                String string4 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string4, string, string2, string3);
            }
            if (string3 != null && !string3.equals(UTF_8)) {
                String string5 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string5, string, string2, string3);
            }
            return string;
        }
        if (string.equals(UTF_16BE) || string.equals(UTF_16LE)) {
            if (string2 != null && !string2.equals(string)) {
                String string6 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string6, string, string2, string3);
            }
            if (string3 != null && !string3.equals(UTF_16) && !string3.equals(string)) {
                String string7 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string7, string, string2, string3);
            }
            return string;
        }
        if (string.equals(UTF_32BE) || string.equals(UTF_32LE)) {
            if (string2 != null && !string2.equals(string)) {
                String string8 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string8, string, string2, string3);
            }
            if (string3 != null && !string3.equals(UTF_32) && !string3.equals(string)) {
                String string9 = MessageFormat.format(RAW_EX_1, string, string2, string3);
                throw new XmlStreamReaderException(string9, string, string2, string3);
            }
            return string;
        }
        String string10 = MessageFormat.format(RAW_EX_2, string, string2, string3);
        throw new XmlStreamReaderException(string10, string, string2, string3);
    }

    String calculateHttpEncoding(String string, String string2, String string3, String string4, boolean bl) throws IOException {
        if (bl && string4 != null) {
            return string4;
        }
        String string5 = XmlStreamReader.getContentTypeMime(string);
        String string6 = XmlStreamReader.getContentTypeEncoding(string);
        boolean bl2 = XmlStreamReader.isAppXml(string5);
        boolean bl3 = XmlStreamReader.isTextXml(string5);
        if (!bl2 && !bl3) {
            String string7 = MessageFormat.format(HTTP_EX_3, string5, string6, string2, string3, string4);
            throw new XmlStreamReaderException(string7, string5, string6, string2, string3, string4);
        }
        if (string6 == null) {
            if (bl2) {
                return this.calculateRawEncoding(string2, string3, string4);
            }
            return this.defaultEncoding == null ? US_ASCII : this.defaultEncoding;
        }
        if (string6.equals(UTF_16BE) || string6.equals(UTF_16LE)) {
            if (string2 != null) {
                String string8 = MessageFormat.format(HTTP_EX_1, string5, string6, string2, string3, string4);
                throw new XmlStreamReaderException(string8, string5, string6, string2, string3, string4);
            }
            return string6;
        }
        if (string6.equals(UTF_16)) {
            if (string2 != null && string2.startsWith(UTF_16)) {
                return string2;
            }
            String string9 = MessageFormat.format(HTTP_EX_2, string5, string6, string2, string3, string4);
            throw new XmlStreamReaderException(string9, string5, string6, string2, string3, string4);
        }
        if (string6.equals(UTF_32BE) || string6.equals(UTF_32LE)) {
            if (string2 != null) {
                String string10 = MessageFormat.format(HTTP_EX_1, string5, string6, string2, string3, string4);
                throw new XmlStreamReaderException(string10, string5, string6, string2, string3, string4);
            }
            return string6;
        }
        if (string6.equals(UTF_32)) {
            if (string2 != null && string2.startsWith(UTF_32)) {
                return string2;
            }
            String string11 = MessageFormat.format(HTTP_EX_2, string5, string6, string2, string3, string4);
            throw new XmlStreamReaderException(string11, string5, string6, string2, string3, string4);
        }
        return string6;
    }

    static String getContentTypeMime(String string) {
        String string2 = null;
        if (string != null) {
            int n = string.indexOf(";");
            string2 = n >= 0 ? string.substring(0, n) : string;
            string2 = string2.trim();
        }
        return string2;
    }

    static String getContentTypeEncoding(String string) {
        int n;
        String string2 = null;
        if (string != null && (n = string.indexOf(";")) > -1) {
            String string3 = string.substring(n + 1);
            Matcher matcher = CHARSET_PATTERN.matcher(string3);
            string2 = matcher.find() ? matcher.group(1) : null;
            string2 = string2 != null ? string2.toUpperCase(Locale.US) : null;
        }
        return string2;
    }

    private static String getXmlProlog(InputStream inputStream, String string) throws IOException {
        String string2 = null;
        if (string != null) {
            byte[] byArray = new byte[4096];
            inputStream.mark(4096);
            int n = 0;
            int n2 = 4096;
            int n3 = inputStream.read(byArray, n, n2);
            int n4 = -1;
            String string3 = "";
            while (n3 != -1 && n4 == -1 && n < 4096) {
                n3 = inputStream.read(byArray, n += n3, n2 -= n3);
                string3 = new String(byArray, 0, n, string);
                n4 = string3.indexOf(62);
            }
            if (n4 == -1) {
                if (n3 == -1) {
                    throw new IOException("Unexpected end of XML stream");
                }
                throw new IOException("XML prolog or ROOT element not found on first " + n + " bytes");
            }
            int n5 = n;
            if (n5 > 0) {
                inputStream.reset();
                BufferedReader bufferedReader = new BufferedReader(new StringReader(string3.substring(0, n4 + 1)));
                StringBuffer stringBuffer = new StringBuffer();
                String string4 = bufferedReader.readLine();
                while (string4 != null) {
                    stringBuffer.append(string4);
                    string4 = bufferedReader.readLine();
                }
                Matcher matcher = ENCODING_PATTERN.matcher(stringBuffer);
                if (matcher.find()) {
                    string2 = matcher.group(1).toUpperCase();
                    string2 = string2.substring(1, string2.length() - 1);
                }
            }
        }
        return string2;
    }

    static boolean isAppXml(String string) {
        return string != null && (string.equals("application/xml") || string.equals("application/xml-dtd") || string.equals("application/xml-external-parsed-entity") || string.startsWith("application/") && string.endsWith("+xml"));
    }

    static boolean isTextXml(String string) {
        return string != null && (string.equals("text/xml") || string.equals("text/xml-external-parsed-entity") || string.startsWith("text/") && string.endsWith("+xml"));
    }
}

