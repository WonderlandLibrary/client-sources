/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.data;

import com.ibm.icu.impl.ICUData;
import com.ibm.icu.impl.PatternProps;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceReader
implements Closeable {
    private BufferedReader reader = null;
    private String resourceName;
    private String encoding;
    private Class<?> root;
    private int lineNo;

    public ResourceReader(String string, String string2) throws UnsupportedEncodingException {
        this(ICUData.class, "data/" + string, string2);
    }

    public ResourceReader(String string) {
        this(ICUData.class, "data/" + string);
    }

    public ResourceReader(Class<?> clazz, String string, String string2) throws UnsupportedEncodingException {
        this.root = clazz;
        this.resourceName = string;
        this.encoding = string2;
        this.lineNo = -1;
        this._reset();
    }

    public ResourceReader(InputStream inputStream, String string, String string2) {
        this.root = null;
        this.resourceName = string;
        this.encoding = string2;
        this.lineNo = -1;
        try {
            InputStreamReader inputStreamReader = string2 == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, string2);
            this.reader = new BufferedReader(inputStreamReader);
            this.lineNo = 0;
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public ResourceReader(InputStream inputStream, String string) {
        this(inputStream, string, null);
    }

    public ResourceReader(Class<?> clazz, String string) {
        this.root = clazz;
        this.resourceName = string;
        this.encoding = null;
        this.lineNo = -1;
        try {
            this._reset();
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    public String readLine() throws IOException {
        if (this.lineNo == 0) {
            ++this.lineNo;
            String string = this.reader.readLine();
            if (string != null && (string.charAt(0) == '\uffef' || string.charAt(0) == '\ufeff')) {
                string = string.substring(1);
            }
            return string;
        }
        ++this.lineNo;
        return this.reader.readLine();
    }

    public String readLineSkippingComments(boolean bl) throws IOException {
        String string;
        int n;
        do {
            if ((string = this.readLine()) != null) continue;
            return string;
        } while ((n = PatternProps.skipWhiteSpace(string, 0)) == string.length() || string.charAt(n) == '#');
        if (bl) {
            string = string.substring(n);
        }
        return string;
    }

    public String readLineSkippingComments() throws IOException {
        return this.readLineSkippingComments(true);
    }

    public int getLineNumber() {
        return this.lineNo;
    }

    public String describePosition() {
        return this.resourceName + ':' + this.lineNo;
    }

    public void reset() {
        try {
            this._reset();
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
    }

    private void _reset() throws UnsupportedEncodingException {
        try {
            this.close();
        } catch (IOException iOException) {
            // empty catch block
        }
        if (this.lineNo == 0) {
            return;
        }
        InputStream inputStream = ICUData.getStream(this.root, this.resourceName);
        if (inputStream == null) {
            throw new IllegalArgumentException("Can't open " + this.resourceName);
        }
        InputStreamReader inputStreamReader = this.encoding == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, this.encoding);
        this.reader = new BufferedReader(inputStreamReader);
        this.lineNo = 0;
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
            this.reader = null;
        }
    }
}

