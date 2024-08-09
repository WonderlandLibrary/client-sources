/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.impl.RecordReader;
import com.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordReader
implements RecordReader {
    private Reader r;
    private List<String> nameStack;
    private boolean atTag;
    private String tag;

    public XMLRecordReader(Reader reader) {
        this.r = reader;
        this.nameStack = new ArrayList<String>();
        if (this.getTag().startsWith("?xml")) {
            this.advance();
        }
        if (this.getTag().startsWith("!--")) {
            this.advance();
        }
    }

    @Override
    public boolean open(String string) {
        if (this.getTag().equals(string)) {
            this.nameStack.add(string);
            this.advance();
            return false;
        }
        return true;
    }

    @Override
    public boolean close() {
        int n = this.nameStack.size() - 1;
        String string = this.nameStack.get(n);
        if (this.getTag().equals("/" + string)) {
            this.nameStack.remove(n);
            this.advance();
            return false;
        }
        return true;
    }

    @Override
    public boolean bool(String string) {
        String string2 = this.string(string);
        if (string2 != null) {
            return "true".equals(string2);
        }
        return true;
    }

    @Override
    public boolean[] boolArray(String string) {
        String[] stringArray = this.stringArray(string);
        if (stringArray != null) {
            boolean[] blArray = new boolean[stringArray.length];
            for (int i = 0; i < stringArray.length; ++i) {
                blArray[i] = "true".equals(stringArray[i]);
            }
            return blArray;
        }
        return null;
    }

    @Override
    public char character(String string) {
        String string2 = this.string(string);
        if (string2 != null) {
            return string2.charAt(0);
        }
        return '\u0000';
    }

    @Override
    public char[] characterArray(String string) {
        String[] stringArray = this.stringArray(string);
        if (stringArray != null) {
            char[] cArray = new char[stringArray.length];
            for (int i = 0; i < stringArray.length; ++i) {
                cArray[i] = stringArray[i].charAt(0);
            }
            return cArray;
        }
        return null;
    }

    @Override
    public byte namedIndex(String string, String[] stringArray) {
        String string2 = this.string(string);
        if (string2 != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                if (!string2.equals(stringArray[i])) continue;
                return (byte)i;
            }
        }
        return 1;
    }

    @Override
    public byte[] namedIndexArray(String string, String[] stringArray) {
        String[] stringArray2 = this.stringArray(string);
        if (stringArray2 != null) {
            byte[] byArray = new byte[stringArray2.length];
            block0: for (int i = 0; i < stringArray2.length; ++i) {
                String string2 = stringArray2[i];
                for (int j = 0; j < stringArray.length; ++j) {
                    if (!stringArray[j].equals(string2)) continue;
                    byArray[i] = (byte)j;
                    continue block0;
                }
                byArray[i] = -1;
            }
            return byArray;
        }
        return null;
    }

    @Override
    public String string(String string) {
        if (this.match(string)) {
            String string2 = this.readData();
            if (this.match("/" + string)) {
                return string2;
            }
        }
        return null;
    }

    @Override
    public String[] stringArray(String string) {
        if (this.match(string + "List")) {
            String string2;
            ArrayList<String> arrayList = new ArrayList<String>();
            while (null != (string2 = this.string(string))) {
                if ("Null".equals(string2)) {
                    string2 = null;
                }
                arrayList.add(string2);
            }
            if (this.match("/" + string + "List")) {
                return arrayList.toArray(new String[arrayList.size()]);
            }
        }
        return null;
    }

    @Override
    public String[][] stringTable(String string) {
        if (this.match(string + "Table")) {
            String[] stringArray;
            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            while (null != (stringArray = this.stringArray(string))) {
                arrayList.add(stringArray);
            }
            if (this.match("/" + string + "Table")) {
                return (String[][])arrayList.toArray((T[])new String[arrayList.size()][]);
            }
        }
        return null;
    }

    private boolean match(String string) {
        if (this.getTag().equals(string)) {
            this.advance();
            return false;
        }
        return true;
    }

    private String getTag() {
        if (this.tag == null) {
            this.tag = this.readNextTag();
        }
        return this.tag;
    }

    private void advance() {
        this.tag = null;
    }

    private String readData() {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        boolean bl = false;
        while (true) {
            if ((n = this.readChar()) == -1 || n == 60) break;
            if (n == 38) {
                StringBuilder stringBuilder2;
                n = this.readChar();
                if (n == 35) {
                    stringBuilder2 = new StringBuilder();
                    int n2 = 10;
                    n = this.readChar();
                    if (n == 120) {
                        n2 = 16;
                        n = this.readChar();
                    }
                    while (n != 59 && n != -1) {
                        stringBuilder2.append((char)n);
                        n = this.readChar();
                    }
                    try {
                        int n3 = Integer.parseInt(stringBuilder2.toString(), n2);
                        n = (char)n3;
                    } catch (NumberFormatException numberFormatException) {
                        System.err.println("numbuf: " + stringBuilder2.toString() + " radix: " + n2);
                        throw numberFormatException;
                    }
                } else {
                    stringBuilder2 = new StringBuilder();
                    while (n != 59 && n != -1) {
                        stringBuilder2.append((char)n);
                        n = this.readChar();
                    }
                    String string = stringBuilder2.toString();
                    if (string.equals("lt")) {
                        n = 60;
                    } else if (string.equals("gt")) {
                        n = 62;
                    } else if (string.equals("quot")) {
                        n = 34;
                    } else if (string.equals("apos")) {
                        n = 39;
                    } else if (string.equals("amp")) {
                        n = 38;
                    } else {
                        System.err.println("unrecognized character entity: '" + string + "'");
                        continue;
                    }
                }
            }
            if (UCharacter.isWhitespace(n)) {
                if (bl) continue;
                n = 32;
                bl = true;
            } else {
                bl = false;
            }
            stringBuilder.append((char)n);
        }
        this.atTag = n == 60;
        return stringBuilder.toString();
    }

    private String readNextTag() {
        int n = 0;
        while (!this.atTag) {
            n = this.readChar();
            if (n == 60 || n == -1) {
                if (n != 60) break;
                this.atTag = true;
                break;
            }
            if (UCharacter.isWhitespace(n)) continue;
            System.err.println("Unexpected non-whitespace character " + Integer.toHexString(n));
            break;
        }
        if (this.atTag) {
            this.atTag = false;
            StringBuilder stringBuilder = new StringBuilder();
            while ((n = this.readChar()) != 62 && n != -1) {
                stringBuilder.append((char)n);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    int readChar() {
        try {
            return this.r.read();
        } catch (IOException iOException) {
            return 1;
        }
    }
}

