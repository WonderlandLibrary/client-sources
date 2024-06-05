package org.jsoup.helper;

import org.jsoup.parser.*;
import java.nio.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.regex.*;
import java.nio.charset.*;
import java.util.*;

public final class DataUtil
{
    private static final Pattern charsetPattern;
    static final String defaultCharset = "UTF-8";
    private static final int bufferSize = 60000;
    private static final char[] mimeBoundaryChars;
    static final int boundaryLength = 32;
    
    private DataUtil() {
    }
    
    public static Document load(final File in, final String charsetName, final String baseUri) throws IOException {
        final ByteBuffer byteData = readFileToByteBuffer(in);
        return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
    }
    
    public static Document load(final InputStream in, final String charsetName, final String baseUri) throws IOException {
        final ByteBuffer byteData = readToByteBuffer(in);
        return parseByteData(byteData, charsetName, baseUri, Parser.htmlParser());
    }
    
    public static Document load(final InputStream in, final String charsetName, final String baseUri, final Parser parser) throws IOException {
        final ByteBuffer byteData = readToByteBuffer(in);
        return parseByteData(byteData, charsetName, baseUri, parser);
    }
    
    static void crossStreams(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buffer = new byte[60000];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }
    
    static Document parseByteData(final ByteBuffer byteData, String charsetName, final String baseUri, final Parser parser) {
        Document doc = null;
        charsetName = detectCharsetFromBom(byteData, charsetName);
        String docData;
        if (charsetName == null) {
            docData = Charset.forName("UTF-8").decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            final Elements metaElements = doc.select("meta[http-equiv=content-type], meta[charset]");
            String foundCharset = null;
            for (final Element meta : metaElements) {
                if (meta.hasAttr("http-equiv")) {
                    foundCharset = getCharsetFromContentType(meta.attr("content"));
                }
                if (foundCharset == null && meta.hasAttr("charset")) {
                    foundCharset = meta.attr("charset");
                }
                if (foundCharset != null) {
                    break;
                }
            }
            if (foundCharset == null && doc.childNodeSize() > 0 && doc.childNode(0) instanceof XmlDeclaration) {
                final XmlDeclaration prolog = (XmlDeclaration)doc.childNode(0);
                if (prolog.name().equals("xml")) {
                    foundCharset = prolog.attr("encoding");
                }
            }
            foundCharset = validateCharset(foundCharset);
            if (foundCharset != null && !foundCharset.equals("UTF-8")) {
                foundCharset = (charsetName = foundCharset.trim().replaceAll("[\"']", ""));
                byteData.rewind();
                docData = Charset.forName(foundCharset).decode(byteData).toString();
                doc = null;
            }
        }
        else {
            Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            docData = Charset.forName(charsetName).decode(byteData).toString();
        }
        if (doc == null) {
            doc = parser.parseInput(docData, baseUri);
            doc.outputSettings().charset(charsetName);
        }
        return doc;
    }
    
    public static ByteBuffer readToByteBuffer(final InputStream inStream, final int maxSize) throws IOException {
        Validate.isTrue(maxSize >= 0, "maxSize must be 0 (unlimited) or larger");
        final boolean capped = maxSize > 0;
        final byte[] buffer = new byte[(capped && maxSize < 60000) ? maxSize : 60000];
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream(capped ? maxSize : 60000);
        int remaining = maxSize;
        while (!Thread.interrupted()) {
            final int read = inStream.read(buffer);
            if (read == -1) {
                break;
            }
            if (capped) {
                if (read > remaining) {
                    outStream.write(buffer, 0, remaining);
                    break;
                }
                remaining -= read;
            }
            outStream.write(buffer, 0, read);
        }
        return ByteBuffer.wrap(outStream.toByteArray());
    }
    
    static ByteBuffer readToByteBuffer(final InputStream inStream) throws IOException {
        return readToByteBuffer(inStream, 0);
    }
    
    static ByteBuffer readFileToByteBuffer(final File file) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            final byte[] bytes = new byte[(int)randomAccessFile.length()];
            randomAccessFile.readFully(bytes);
            return ByteBuffer.wrap(bytes);
        }
        finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
    }
    
    static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }
    
    static String getCharsetFromContentType(final String contentType) {
        if (contentType == null) {
            return null;
        }
        final Matcher m = DataUtil.charsetPattern.matcher(contentType);
        if (m.find()) {
            String charset = m.group(1).trim();
            charset = charset.replace("charset=", "");
            return validateCharset(charset);
        }
        return null;
    }
    
    private static String validateCharset(String cs) {
        if (cs == null || cs.length() == 0) {
            return null;
        }
        cs = cs.trim().replaceAll("[\"']", "");
        try {
            if (Charset.isSupported(cs)) {
                return cs;
            }
            cs = cs.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(cs)) {
                return cs;
            }
        }
        catch (IllegalCharsetNameException ex) {}
        return null;
    }
    
    static String mimeBoundary() {
        final StringBuilder mime = new StringBuilder(32);
        final Random rand = new Random();
        for (int i = 0; i < 32; ++i) {
            mime.append(DataUtil.mimeBoundaryChars[rand.nextInt(DataUtil.mimeBoundaryChars.length)]);
        }
        return mime.toString();
    }
    
    private static String detectCharsetFromBom(final ByteBuffer byteData, String charsetName) {
        byteData.mark();
        final byte[] bom = new byte[4];
        if (byteData.remaining() >= bom.length) {
            byteData.get(bom);
            byteData.rewind();
        }
        if ((bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) || (bom[0] == -1 && bom[1] == -2 && bom[2] == 0 && bom[3] == 0)) {
            charsetName = "UTF-32";
        }
        else if ((bom[0] == -2 && bom[1] == -1) || (bom[0] == -1 && bom[1] == -2)) {
            charsetName = "UTF-16";
        }
        else if (bom[0] == -17 && bom[1] == -69 && bom[2] == -65) {
            charsetName = "UTF-8";
            byteData.position(3);
        }
        return charsetName;
    }
    
    static {
        charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
        mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    }
}
