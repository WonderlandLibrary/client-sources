/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommentStore {
    private final Map<String, List<String>> headers = new HashMap<String, List<String>>();
    private final String pathSeparator;
    private final String pathSeparatorQuoted;
    private final int indents;
    private List<String> mainHeader = new ArrayList<String>();

    public CommentStore(char c, int n) {
        this.pathSeparator = Character.toString(c);
        this.pathSeparatorQuoted = Pattern.quote(this.pathSeparator);
        this.indents = n;
    }

    public void mainHeader(String ... stringArray) {
        this.mainHeader = Arrays.asList(stringArray);
    }

    public List<String> mainHeader() {
        return this.mainHeader;
    }

    public void header(String string, String ... stringArray) {
        this.headers.put(string, Arrays.asList(stringArray));
    }

    public List<String> header(String string) {
        return this.headers.get(string);
    }

    public void storeComments(InputStream inputStream) throws IOException {
        String string;
        this.mainHeader.clear();
        this.headers.clear();
        try (Object object = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
            string = CharStreams.toString((Readable)object);
        }
        object = new ArrayList();
        boolean bl = true;
        boolean bl2 = false;
        int n = 0;
        String string2 = "";
        for (String string3 : string.split("\n")) {
            String[] stringArray;
            String string4 = string3.trim();
            if (string4.startsWith("#")) {
                object.add(string4);
                continue;
            }
            if (bl) {
                if (!object.isEmpty()) {
                    object.add("");
                    this.mainHeader.addAll((Collection<String>)object);
                    object.clear();
                }
                bl = false;
            }
            if (string4.isEmpty()) {
                object.add(string4);
                continue;
            }
            if (string4.startsWith("- |")) {
                bl2 = true;
                continue;
            }
            int n2 = this.getIndents(string3);
            int n3 = n2 / this.indents;
            if (bl2) {
                if (n3 > n) continue;
                bl2 = false;
            }
            if (n3 <= n) {
                int n4;
                stringArray = string2.split(this.pathSeparatorQuoted);
                int n5 = stringArray.length - (n4 = n - n3 + 1);
                string2 = n5 >= 0 ? this.join(stringArray, n5) : string2;
            }
            stringArray = string2.isEmpty() ? "" : this.pathSeparator;
            String string5 = string3.indexOf(58) != -1 ? string3.split(Pattern.quote(":"))[0] : string3;
            string2 = string2 + (String)stringArray + string5.substring(n2);
            n = n3;
            if (object.isEmpty()) continue;
            this.headers.put(string2, new ArrayList(object));
            object.clear();
        }
    }

    public void writeComments(String string, File file) throws IOException {
        String string22;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string22 : this.mainHeader) {
            stringBuilder.append(string22).append('\n');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        int n = 0;
        string22 = "";
        for (String string3 : string.split("\n")) {
            List<String> list;
            String[] stringArray;
            boolean bl;
            if (string3.isEmpty()) continue;
            int n2 = this.getIndents(string3);
            int n3 = n2 / this.indents;
            String string4 = string3.substring(n2);
            if (string4.trim().isEmpty() || string4.charAt(0) == '-') {
                bl = false;
            } else if (n3 <= n) {
                stringArray = string22.split(this.pathSeparatorQuoted);
                int n4 = n - n3 + 1;
                string22 = this.join(stringArray, stringArray.length - n4);
                bl = true;
            } else {
                boolean bl2 = bl = string3.indexOf(58) != -1;
            }
            if (!bl) {
                stringBuilder.append(string3).append('\n');
                continue;
            }
            stringArray = string4.split(Pattern.quote(":"))[0];
            if (!string22.isEmpty()) {
                string22 = string22 + this.pathSeparator;
            }
            if ((list = this.headers.get(string22 = string22 + (String)stringArray)) != null && !list.isEmpty()) {
                String string5 = n2 > 0 ? string3.substring(0, n2) : "";
                for (String string6 : list) {
                    if (string6.isEmpty()) {
                        stringBuilder.append('\n');
                        continue;
                    }
                    stringBuilder.append(string5).append(string6).append('\n');
                }
            }
            n = n3;
            stringBuilder.append(string3).append('\n');
        }
        Files.write(stringBuilder.toString(), file, StandardCharsets.UTF_8);
    }

    private int getIndents(String string) {
        int n = 0;
        for (int i = 0; i < string.length() && string.charAt(i) == ' '; ++i) {
            ++n;
        }
        return n;
    }

    private String join(String[] stringArray, int n) {
        CharSequence[] charSequenceArray = new String[n];
        System.arraycopy(stringArray, 0, charSequenceArray, 0, n);
        return String.join((CharSequence)this.pathSeparator, charSequenceArray);
    }
}

