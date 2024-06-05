package org.jsoup.nodes;

import org.jsoup.*;
import org.jsoup.helper.*;
import java.nio.charset.*;
import org.jsoup.parser.*;
import java.io.*;
import java.nio.*;
import java.util.*;

public class Entities
{
    private static final int empty = -1;
    private static final String emptyName = "";
    static final int codepointRadix = 36;
    private static final HashMap<String, String> multipoints;
    private static final char[] codeDelims;
    
    private Entities() {
    }
    
    public static boolean isNamedEntity(final String name) {
        return EscapeMode.extended.codepointForName(name) != -1;
    }
    
    public static boolean isBaseNamedEntity(final String name) {
        return EscapeMode.base.codepointForName(name) != -1;
    }
    
    @Deprecated
    public static Character getCharacterByName(final String name) {
        return (char)EscapeMode.extended.codepointForName(name);
    }
    
    public static String getByName(final String name) {
        final String val = Entities.multipoints.get(name);
        if (val != null) {
            return val;
        }
        final int codepoint = EscapeMode.extended.codepointForName(name);
        if (codepoint != -1) {
            return new String(new int[] { codepoint }, 0, 1);
        }
        return "";
    }
    
    public static int codepointsForName(final String name, final int[] codepoints) {
        final String val = Entities.multipoints.get(name);
        if (val != null) {
            codepoints[0] = val.codePointAt(0);
            codepoints[1] = val.codePointAt(1);
            return 2;
        }
        final int codepoint = EscapeMode.extended.codepointForName(name);
        if (codepoint != -1) {
            codepoints[0] = codepoint;
            return 1;
        }
        return 0;
    }
    
    static String escape(final String string, final Document.OutputSettings out) {
        final StringBuilder accum = new StringBuilder(string.length() * 2);
        try {
            escape(accum, string, out, false, false, false);
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
        return accum.toString();
    }
    
    static void escape(final Appendable accum, final String string, final Document.OutputSettings out, final boolean inAttribute, final boolean normaliseWhite, final boolean stripLeadingWhite) throws IOException {
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        final EscapeMode escapeMode = out.escapeMode();
        final CharsetEncoder encoder = out.encoder();
        final CoreCharset coreCharset = byName(encoder.charset().name());
        int codePoint;
        for (int length = string.length(), offset = 0; offset < length; offset += Character.charCount(codePoint)) {
            codePoint = string.codePointAt(offset);
            if (normaliseWhite) {
                if (StringUtil.isWhitespace(codePoint)) {
                    if (stripLeadingWhite && !reachedNonWhite) {
                        continue;
                    }
                    if (lastWasWhite) {
                        continue;
                    }
                    accum.append(' ');
                    lastWasWhite = true;
                    continue;
                }
                else {
                    lastWasWhite = false;
                    reachedNonWhite = true;
                }
            }
            if (codePoint < 65536) {
                final char c = (char)codePoint;
                switch (c) {
                    case '&': {
                        accum.append("&amp;");
                        break;
                    }
                    case ' ': {
                        if (escapeMode != EscapeMode.xhtml) {
                            accum.append("&nbsp;");
                            break;
                        }
                        accum.append("&#xa0;");
                        break;
                    }
                    case '<': {
                        if (!inAttribute || escapeMode == EscapeMode.xhtml) {
                            accum.append("&lt;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    case '>': {
                        if (!inAttribute) {
                            accum.append("&gt;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    case '\"': {
                        if (inAttribute) {
                            accum.append("&quot;");
                            break;
                        }
                        accum.append(c);
                        break;
                    }
                    default: {
                        if (canEncode(coreCharset, c, encoder)) {
                            accum.append(c);
                            break;
                        }
                        appendEncoded(accum, escapeMode, codePoint);
                        break;
                    }
                }
            }
            else {
                final String c2 = new String(Character.toChars(codePoint));
                if (encoder.canEncode(c2)) {
                    accum.append(c2);
                }
                else {
                    appendEncoded(accum, escapeMode, codePoint);
                }
            }
        }
    }
    
    private static void appendEncoded(final Appendable accum, final EscapeMode escapeMode, final int codePoint) throws IOException {
        final String name = escapeMode.nameForCodepoint(codePoint);
        if (name != "") {
            accum.append('&').append(name).append(';');
        }
        else {
            accum.append("&#x").append(Integer.toHexString(codePoint)).append(';');
        }
    }
    
    static String unescape(final String string) {
        return unescape(string, false);
    }
    
    static String unescape(final String string, final boolean strict) {
        return Parser.unescapeEntities(string, strict);
    }
    
    private static boolean canEncode(final CoreCharset charset, final char c, final CharsetEncoder fallback) {
        switch (charset) {
            case ascii: {
                return c < '\u0080';
            }
            case utf: {
                return true;
            }
            default: {
                return fallback.canEncode(c);
            }
        }
    }
    
    private static void load(final EscapeMode e, final String file, final int size) {
        e.nameKeys = new String[size];
        e.codeVals = new int[size];
        e.codeKeys = new int[size];
        e.nameVals = new String[size];
        final InputStream stream = Entities.class.getResourceAsStream(file);
        if (stream == null) {
            throw new IllegalStateException("Could not read resource " + file + ". Make sure you copy resources for " + Entities.class.getCanonicalName());
        }
        int i = 0;
        try {
            final ByteBuffer bytes = DataUtil.readToByteBuffer(stream, 0);
            final String contents = Charset.forName("ascii").decode(bytes).toString();
            final CharacterReader reader = new CharacterReader(contents);
            while (!reader.isEmpty()) {
                final String name = reader.consumeTo('=');
                reader.advance();
                final int cp1 = Integer.parseInt(reader.consumeToAny(Entities.codeDelims), 36);
                final char codeDelim = reader.current();
                reader.advance();
                int cp2;
                if (codeDelim == ',') {
                    cp2 = Integer.parseInt(reader.consumeTo(';'), 36);
                    reader.advance();
                }
                else {
                    cp2 = -1;
                }
                String indexS = reader.consumeTo('\n');
                if (indexS.charAt(indexS.length() - 1) == '\r') {
                    indexS = indexS.substring(0, indexS.length() - 1);
                }
                final int index = Integer.parseInt(indexS, 36);
                reader.advance();
                e.nameKeys[i] = name;
                e.codeVals[i] = cp1;
                e.codeKeys[index] = cp1;
                e.nameVals[index] = name;
                if (cp2 != -1) {
                    Entities.multipoints.put(name, new String(new int[] { cp1, cp2 }, 0, 2));
                }
                ++i;
            }
        }
        catch (IOException err) {
            throw new IllegalStateException("Error reading resource " + file);
        }
    }
    
    static {
        multipoints = new HashMap<String, String>();
        codeDelims = new char[] { ',', ';' };
    }
    
    public enum EscapeMode
    {
        xhtml("entities-xhtml.properties", 4), 
        base("entities-base.properties", 106), 
        extended("entities-full.properties", 2125);
        
        private String[] nameKeys;
        private int[] codeVals;
        private int[] codeKeys;
        private String[] nameVals;
        
        private EscapeMode(final String file, final int size) {
            load(this, file, size);
        }
        
        int codepointForName(final String name) {
            final int index = Arrays.binarySearch(this.nameKeys, name);
            return (index >= 0) ? this.codeVals[index] : -1;
        }
        
        String nameForCodepoint(final int codepoint) {
            final int index = Arrays.binarySearch(this.codeKeys, codepoint);
            if (index >= 0) {
                return (index < this.nameVals.length - 1 && this.codeKeys[index + 1] == codepoint) ? this.nameVals[index + 1] : this.nameVals[index];
            }
            return "";
        }
        
        private int size() {
            return this.nameKeys.length;
        }
    }
    
    private enum CoreCharset
    {
        ascii, 
        utf, 
        fallback;
        
        private static CoreCharset byName(final String name) {
            if (name.equals("US-ASCII")) {
                return CoreCharset.ascii;
            }
            if (name.startsWith("UTF-")) {
                return CoreCharset.utf;
            }
            return CoreCharset.fallback;
        }
    }
}
