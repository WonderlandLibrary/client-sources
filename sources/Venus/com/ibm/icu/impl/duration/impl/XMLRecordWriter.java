/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.impl.RecordWriter;
import com.ibm.icu.lang.UCharacter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class XMLRecordWriter
implements RecordWriter {
    private Writer w;
    private List<String> nameStack;
    static final String NULL_NAME = "Null";
    private static final String INDENT = "    ";

    public XMLRecordWriter(Writer writer) {
        this.w = writer;
        this.nameStack = new ArrayList<String>();
    }

    @Override
    public boolean open(String string) {
        this.newline();
        this.writeString("<" + string + ">");
        this.nameStack.add(string);
        return false;
    }

    @Override
    public boolean close() {
        int n = this.nameStack.size() - 1;
        if (n >= 0) {
            String string = this.nameStack.remove(n);
            this.newline();
            this.writeString("</" + string + ">");
            return false;
        }
        return true;
    }

    public void flush() {
        try {
            this.w.flush();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void bool(String string, boolean bl) {
        this.internalString(string, String.valueOf(bl));
    }

    @Override
    public void boolArray(String string, boolean[] blArray) {
        if (blArray != null) {
            String[] stringArray = new String[blArray.length];
            for (int i = 0; i < blArray.length; ++i) {
                stringArray[i] = String.valueOf(blArray[i]);
            }
            this.stringArray(string, stringArray);
        }
    }

    private static String ctos(char c) {
        if (c == '<') {
            return "&lt;";
        }
        if (c == '&') {
            return "&amp;";
        }
        return String.valueOf(c);
    }

    @Override
    public void character(String string, char c) {
        if (c != '\uffff') {
            this.internalString(string, XMLRecordWriter.ctos(c));
        }
    }

    @Override
    public void characterArray(String string, char[] cArray) {
        if (cArray != null) {
            String[] stringArray = new String[cArray.length];
            for (int i = 0; i < cArray.length; ++i) {
                char c = cArray[i];
                stringArray[i] = c == '\uffff' ? NULL_NAME : XMLRecordWriter.ctos(c);
            }
            this.internalStringArray(string, stringArray);
        }
    }

    @Override
    public void namedIndex(String string, String[] stringArray, int n) {
        if (n >= 0) {
            this.internalString(string, stringArray[n]);
        }
    }

    @Override
    public void namedIndexArray(String string, String[] stringArray, byte[] byArray) {
        if (byArray != null) {
            String[] stringArray2 = new String[byArray.length];
            for (int i = 0; i < byArray.length; ++i) {
                byte by = byArray[i];
                stringArray2[i] = by < 0 ? NULL_NAME : stringArray[by];
            }
            this.internalStringArray(string, stringArray2);
        }
    }

    public static String normalize(String string) {
        if (string == null) {
            return null;
        }
        StringBuilder stringBuilder = null;
        boolean bl = false;
        char c = '\u0000';
        boolean bl2 = false;
        for (int i = 0; i < string.length(); ++i) {
            c = string.charAt(i);
            if (UCharacter.isWhitespace(c)) {
                if (stringBuilder == null && (bl || c != ' ')) {
                    stringBuilder = new StringBuilder(string.substring(0, i));
                }
                if (bl) continue;
                bl = true;
                bl2 = false;
                c = ' ';
            } else {
                bl = false;
                boolean bl3 = bl2 = c == '<' || c == '&';
                if (bl2 && stringBuilder == null) {
                    stringBuilder = new StringBuilder(string.substring(0, i));
                }
            }
            if (stringBuilder == null) continue;
            if (bl2) {
                stringBuilder.append(c == '<' ? "&lt;" : "&amp;");
                continue;
            }
            stringBuilder.append(c);
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        return string;
    }

    private void internalString(String string, String string2) {
        if (string2 != null) {
            this.newline();
            this.writeString("<" + string + ">" + string2 + "</" + string + ">");
        }
    }

    private void internalStringArray(String string, String[] stringArray) {
        if (stringArray != null) {
            this.push(string + "List");
            for (int i = 0; i < stringArray.length; ++i) {
                String string2 = stringArray[i];
                if (string2 == null) {
                    string2 = NULL_NAME;
                }
                this.string(string, string2);
            }
            this.pop();
        }
    }

    @Override
    public void string(String string, String string2) {
        this.internalString(string, XMLRecordWriter.normalize(string2));
    }

    @Override
    public void stringArray(String string, String[] stringArray) {
        if (stringArray != null) {
            this.push(string + "List");
            for (int i = 0; i < stringArray.length; ++i) {
                String string2 = XMLRecordWriter.normalize(stringArray[i]);
                if (string2 == null) {
                    string2 = NULL_NAME;
                }
                this.internalString(string, string2);
            }
            this.pop();
        }
    }

    @Override
    public void stringTable(String string, String[][] stringArray) {
        if (stringArray != null) {
            this.push(string + "Table");
            for (int i = 0; i < stringArray.length; ++i) {
                String[] stringArray2 = stringArray[i];
                if (stringArray2 == null) {
                    this.internalString(string + "List", NULL_NAME);
                    continue;
                }
                this.stringArray(string, stringArray2);
            }
            this.pop();
        }
    }

    private void push(String string) {
        this.newline();
        this.writeString("<" + string + ">");
        this.nameStack.add(string);
    }

    private void pop() {
        int n = this.nameStack.size() - 1;
        String string = this.nameStack.remove(n);
        this.newline();
        this.writeString("</" + string + ">");
    }

    private void newline() {
        this.writeString("\n");
        for (int i = 0; i < this.nameStack.size(); ++i) {
            this.writeString(INDENT);
        }
    }

    private void writeString(String string) {
        if (this.w != null) {
            try {
                this.w.write(string);
            } catch (IOException iOException) {
                System.err.println(iOException.getMessage());
                this.w = null;
            }
        }
    }
}

