package argo.saj;

import java.io.*;
import java.util.*;

public final class SajParser
{
    public void parse(final Reader par1Reader, final JsonListener par2JsonListener) throws IOException, InvalidSyntaxException {
        final PositionTrackingPushbackReader var3 = new PositionTrackingPushbackReader(par1Reader);
        final char var4 = (char)var3.read();
        switch (var4) {
            case '[': {
                var3.unread(var4);
                par2JsonListener.startDocument();
                this.arrayString(var3, par2JsonListener);
                break;
            }
            case '{': {
                var3.unread(var4);
                par2JsonListener.startDocument();
                this.objectString(var3, par2JsonListener);
                break;
            }
            default: {
                throw new InvalidSyntaxException("Expected either [ or { but got [" + var4 + "].", var3);
            }
        }
        final int var5 = this.readNextNonWhitespaceChar(var3);
        if (var5 != -1) {
            throw new InvalidSyntaxException("Got unexpected trailing character [" + (char)var5 + "].", var3);
        }
        par2JsonListener.endDocument();
    }
    
    private void arrayString(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader, final JsonListener par2JsonListener) throws IOException, InvalidSyntaxException {
        final char var3 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        if (var3 != '[') {
            throw new InvalidSyntaxException("Expected object to start with [ but got [" + var3 + "].", par1PositionTrackingPushbackReader);
        }
        par2JsonListener.startArray();
        final char var4 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        par1PositionTrackingPushbackReader.unread(var4);
        if (var4 != ']') {
            this.aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
        }
        boolean var5 = false;
        while (!var5) {
            final char var6 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
            switch (var6) {
                case ',': {
                    this.aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
                    continue;
                }
                case ']': {
                    var5 = true;
                    continue;
                }
                default: {
                    throw new InvalidSyntaxException("Expected either , or ] but got [" + var6 + "].", par1PositionTrackingPushbackReader);
                }
            }
        }
        par2JsonListener.endArray();
    }
    
    private void objectString(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader, final JsonListener par2JsonListener) throws IOException, InvalidSyntaxException {
        final char var3 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        if (var3 != '{') {
            throw new InvalidSyntaxException("Expected object to start with { but got [" + var3 + "].", par1PositionTrackingPushbackReader);
        }
        par2JsonListener.startObject();
        final char var4 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        par1PositionTrackingPushbackReader.unread(var4);
        if (var4 != '}') {
            this.aFieldToken(par1PositionTrackingPushbackReader, par2JsonListener);
        }
        boolean var5 = false;
        while (!var5) {
            final char var6 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
            switch (var6) {
                case ',': {
                    this.aFieldToken(par1PositionTrackingPushbackReader, par2JsonListener);
                    continue;
                }
                case '}': {
                    var5 = true;
                    continue;
                }
                default: {
                    throw new InvalidSyntaxException("Expected either , or } but got [" + var6 + "].", par1PositionTrackingPushbackReader);
                }
            }
        }
        par2JsonListener.endObject();
    }
    
    private void aFieldToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader, final JsonListener par2JsonListener) throws IOException, InvalidSyntaxException {
        final char var3 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        if ('\"' != var3) {
            throw new InvalidSyntaxException("Expected object identifier to begin with [\"] but got [" + var3 + "].", par1PositionTrackingPushbackReader);
        }
        par1PositionTrackingPushbackReader.unread(var3);
        par2JsonListener.startField(this.stringToken(par1PositionTrackingPushbackReader));
        final char var4 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        if (var4 != ':') {
            throw new InvalidSyntaxException("Expected object identifier to be followed by : but got [" + var4 + "].", par1PositionTrackingPushbackReader);
        }
        this.aJsonValue(par1PositionTrackingPushbackReader, par2JsonListener);
        par2JsonListener.endField();
    }
    
    private void aJsonValue(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader, final JsonListener par2JsonListener) throws IOException, InvalidSyntaxException {
        final char var3 = (char)this.readNextNonWhitespaceChar(par1PositionTrackingPushbackReader);
        switch (var3) {
            case '\"': {
                par1PositionTrackingPushbackReader.unread(var3);
                par2JsonListener.stringValue(this.stringToken(par1PositionTrackingPushbackReader));
                break;
            }
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                par1PositionTrackingPushbackReader.unread(var3);
                par2JsonListener.numberValue(this.numberToken(par1PositionTrackingPushbackReader));
                break;
            }
            case '[': {
                par1PositionTrackingPushbackReader.unread(var3);
                this.arrayString(par1PositionTrackingPushbackReader, par2JsonListener);
                break;
            }
            case 'f': {
                final char[] var4 = new char[4];
                final int var5 = par1PositionTrackingPushbackReader.read(var4);
                if (var5 != 4 || var4[0] != 'a' || var4[1] != 'l' || var4[2] != 's' || var4[3] != 'e') {
                    par1PositionTrackingPushbackReader.uncount(var4);
                    throw new InvalidSyntaxException("Expected 'f' to be followed by [[a, l, s, e]], but got [" + Arrays.toString(var4) + "].", par1PositionTrackingPushbackReader);
                }
                par2JsonListener.falseValue();
                break;
            }
            case 'n': {
                final char[] var6 = new char[3];
                final int var7 = par1PositionTrackingPushbackReader.read(var6);
                if (var7 != 3 || var6[0] != 'u' || var6[1] != 'l' || var6[2] != 'l') {
                    par1PositionTrackingPushbackReader.uncount(var6);
                    throw new InvalidSyntaxException("Expected 'n' to be followed by [[u, l, l]], but got [" + Arrays.toString(var6) + "].", par1PositionTrackingPushbackReader);
                }
                par2JsonListener.nullValue();
                break;
            }
            case 't': {
                final char[] var8 = new char[3];
                final int var9 = par1PositionTrackingPushbackReader.read(var8);
                if (var9 != 3 || var8[0] != 'r' || var8[1] != 'u' || var8[2] != 'e') {
                    par1PositionTrackingPushbackReader.uncount(var8);
                    throw new InvalidSyntaxException("Expected 't' to be followed by [[r, u, e]], but got [" + Arrays.toString(var8) + "].", par1PositionTrackingPushbackReader);
                }
                par2JsonListener.trueValue();
                break;
            }
            case '{': {
                par1PositionTrackingPushbackReader.unread(var3);
                this.objectString(par1PositionTrackingPushbackReader, par2JsonListener);
                break;
            }
            default: {
                throw new InvalidSyntaxException("Invalid character at start of value [" + var3 + "].", par1PositionTrackingPushbackReader);
            }
        }
    }
    
    private String numberToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if ('-' == var3) {
            var2.append('-');
        }
        else {
            par1PositionTrackingPushbackReader.unread(var3);
        }
        var2.append(this.nonNegativeNumberToken(par1PositionTrackingPushbackReader));
        return var2.toString();
    }
    
    private String nonNegativeNumberToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if ('0' == var3) {
            var2.append('0');
            var2.append(this.possibleFractionalComponent(par1PositionTrackingPushbackReader));
            var2.append(this.possibleExponent(par1PositionTrackingPushbackReader));
        }
        else {
            par1PositionTrackingPushbackReader.unread(var3);
            var2.append(this.nonZeroDigitToken(par1PositionTrackingPushbackReader));
            var2.append(this.digitString(par1PositionTrackingPushbackReader));
            var2.append(this.possibleFractionalComponent(par1PositionTrackingPushbackReader));
            var2.append(this.possibleExponent(par1PositionTrackingPushbackReader));
        }
        return var2.toString();
    }
    
    private char nonZeroDigitToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        switch (var3) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                return var3;
            }
            default: {
                throw new InvalidSyntaxException("Expected a digit 1 - 9 but got [" + var3 + "].", par1PositionTrackingPushbackReader);
            }
        }
    }
    
    private char digitToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        switch (var3) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9': {
                return var3;
            }
            default: {
                throw new InvalidSyntaxException("Expected a digit 1 - 9 but got [" + var3 + "].", par1PositionTrackingPushbackReader);
            }
        }
    }
    
    private String digitString(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException {
        final StringBuilder var2 = new StringBuilder();
        boolean var3 = false;
        while (!var3) {
            final char var4 = (char)par1PositionTrackingPushbackReader.read();
            switch (var4) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    var2.append(var4);
                    continue;
                }
                default: {
                    var3 = true;
                    par1PositionTrackingPushbackReader.unread(var4);
                    continue;
                }
            }
        }
        return var2.toString();
    }
    
    private String possibleFractionalComponent(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if (var3 == '.') {
            var2.append('.');
            var2.append(this.digitToken(par1PositionTrackingPushbackReader));
            var2.append(this.digitString(par1PositionTrackingPushbackReader));
        }
        else {
            par1PositionTrackingPushbackReader.unread(var3);
        }
        return var2.toString();
    }
    
    private String possibleExponent(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if (var3 != '.' && var3 != 'E') {
            par1PositionTrackingPushbackReader.unread(var3);
        }
        else {
            var2.append('E');
            var2.append(this.possibleSign(par1PositionTrackingPushbackReader));
            var2.append(this.digitToken(par1PositionTrackingPushbackReader));
            var2.append(this.digitString(par1PositionTrackingPushbackReader));
        }
        return var2.toString();
    }
    
    private String possibleSign(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if (var3 != '+' && var3 != '-') {
            par1PositionTrackingPushbackReader.unread(var3);
        }
        else {
            var2.append(var3);
        }
        return var2.toString();
    }
    
    private String stringToken(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final StringBuilder var2 = new StringBuilder();
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        if ('\"' != var3) {
            throw new InvalidSyntaxException("Expected [\"] but got [" + var3 + "].", par1PositionTrackingPushbackReader);
        }
        boolean var4 = false;
        while (!var4) {
            final char var5 = (char)par1PositionTrackingPushbackReader.read();
            switch (var5) {
                case '\"': {
                    var4 = true;
                    continue;
                }
                case '\\': {
                    final char var6 = this.escapedStringChar(par1PositionTrackingPushbackReader);
                    var2.append(var6);
                    continue;
                }
                default: {
                    var2.append(var5);
                    continue;
                }
            }
        }
        return var2.toString();
    }
    
    private char escapedStringChar(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final char var3 = (char)par1PositionTrackingPushbackReader.read();
        char var4 = '\0';
        switch (var3) {
            case '\"': {
                var4 = '\"';
                break;
            }
            case '/': {
                var4 = '/';
                break;
            }
            case '\\': {
                var4 = '\\';
                break;
            }
            case 'b': {
                var4 = '\b';
                break;
            }
            case 'f': {
                var4 = '\f';
                break;
            }
            case 'n': {
                var4 = '\n';
                break;
            }
            case 'r': {
                var4 = '\r';
                break;
            }
            case 't': {
                var4 = '\t';
                break;
            }
            case 'u': {
                var4 = (char)this.hexadecimalNumber(par1PositionTrackingPushbackReader);
                break;
            }
            default: {
                throw new InvalidSyntaxException("Unrecognised escape character [" + var3 + "].", par1PositionTrackingPushbackReader);
            }
        }
        return var4;
    }
    
    private int hexadecimalNumber(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException, InvalidSyntaxException {
        final char[] var2 = new char[4];
        final int var3 = par1PositionTrackingPushbackReader.read(var2);
        if (var3 != 4) {
            throw new InvalidSyntaxException("Expected a 4 digit hexidecimal number but got only [" + var3 + "], namely [" + String.valueOf(var2, 0, var3) + "].", par1PositionTrackingPushbackReader);
        }
        try {
            final int var4 = Integer.parseInt(String.valueOf(var2), 16);
            return var4;
        }
        catch (NumberFormatException var5) {
            par1PositionTrackingPushbackReader.uncount(var2);
            throw new InvalidSyntaxException("Unable to parse [" + String.valueOf(var2) + "] as a hexidecimal number.", var5, par1PositionTrackingPushbackReader);
        }
    }
    
    private int readNextNonWhitespaceChar(final PositionTrackingPushbackReader par1PositionTrackingPushbackReader) throws IOException {
        boolean var3 = false;
        int var4;
        do {
            var4 = par1PositionTrackingPushbackReader.read();
            switch (var4) {
                case 9:
                case 10:
                case 13:
                case 32: {
                    continue;
                }
                default: {
                    var3 = true;
                    continue;
                }
            }
        } while (!var3);
        return var4;
    }
}
