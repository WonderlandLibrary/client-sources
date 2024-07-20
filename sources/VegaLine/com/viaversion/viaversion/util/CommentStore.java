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

    public CommentStore(char pathSeparator, int indents) {
        this.pathSeparator = Character.toString(pathSeparator);
        this.pathSeparatorQuoted = Pattern.quote(this.pathSeparator);
        this.indents = indents;
    }

    public void mainHeader(String ... header) {
        this.mainHeader = Arrays.asList(header);
    }

    public List<String> mainHeader() {
        return this.mainHeader;
    }

    public void header(String key, String ... header) {
        this.headers.put(key, Arrays.asList(header));
    }

    public List<String> header(String key) {
        return this.headers.get(key);
    }

    public void storeComments(InputStream inputStream) throws IOException {
        String data;
        this.mainHeader.clear();
        this.headers.clear();
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
            data = CharStreams.toString(reader);
        }
        ArrayList<String> currentComments = new ArrayList<String>();
        boolean header = true;
        boolean multiLineValue = false;
        int currentIndents = 0;
        String key = "";
        for (String line : data.split("\n")) {
            String s = line.trim();
            if (s.startsWith("#")) {
                currentComments.add(s);
                continue;
            }
            if (header) {
                if (!currentComments.isEmpty()) {
                    currentComments.add("");
                    this.mainHeader.addAll(currentComments);
                    currentComments.clear();
                }
                header = false;
            }
            if (s.isEmpty()) {
                currentComments.add(s);
                continue;
            }
            if (s.startsWith("- |")) {
                multiLineValue = true;
                continue;
            }
            int indent = this.getIndents(line);
            int indents = indent / this.indents;
            if (multiLineValue) {
                if (indents > currentIndents) continue;
                multiLineValue = false;
            }
            if (indents <= currentIndents) {
                int backspace;
                String[] array = key.split(this.pathSeparatorQuoted);
                int delta = array.length - (backspace = currentIndents - indents + 1);
                key = delta >= 0 ? this.join(array, delta) : key;
            }
            String separator = key.isEmpty() ? "" : this.pathSeparator;
            String lineKey = line.indexOf(58) != -1 ? line.split(Pattern.quote(":"))[0] : line;
            key = key + separator + lineKey.substring(indent);
            currentIndents = indents;
            if (currentComments.isEmpty()) continue;
            this.headers.put(key, new ArrayList(currentComments));
            currentComments.clear();
        }
    }

    public void writeComments(String rawYaml, File output) throws IOException {
        StringBuilder fileData = new StringBuilder();
        for (String mainHeaderLine : this.mainHeader) {
            fileData.append(mainHeaderLine).append('\n');
        }
        fileData.deleteCharAt(fileData.length() - 1);
        int currentKeyIndents = 0;
        String key = "";
        for (String line : rawYaml.split("\n")) {
            List<String> strings;
            boolean keyLine;
            if (line.isEmpty()) continue;
            int indent = this.getIndents(line);
            int indents = indent / this.indents;
            String substring = line.substring(indent);
            if (substring.trim().isEmpty() || substring.charAt(0) == '-') {
                keyLine = false;
            } else if (indents <= currentKeyIndents) {
                String[] array = key.split(this.pathSeparatorQuoted);
                int backspace = currentKeyIndents - indents + 1;
                key = this.join(array, array.length - backspace);
                keyLine = true;
            } else {
                boolean bl = keyLine = line.indexOf(58) != -1;
            }
            if (!keyLine) {
                fileData.append(line).append('\n');
                continue;
            }
            String newKey = substring.split(Pattern.quote(":"))[0];
            if (!key.isEmpty()) {
                key = key + this.pathSeparator;
            }
            if ((strings = this.headers.get(key = key + newKey)) != null && !strings.isEmpty()) {
                String indentText = indent > 0 ? line.substring(0, indent) : "";
                for (String comment : strings) {
                    if (comment.isEmpty()) {
                        fileData.append('\n');
                        continue;
                    }
                    fileData.append(indentText).append(comment).append('\n');
                }
            }
            currentKeyIndents = indents;
            fileData.append(line).append('\n');
        }
        Files.write(fileData.toString(), output, StandardCharsets.UTF_8);
    }

    private int getIndents(String line) {
        int count = 0;
        for (int i = 0; i < line.length() && line.charAt(i) == ' '; ++i) {
            ++count;
        }
        return count;
    }

    private String join(String[] array, int length) {
        CharSequence[] copy = new String[length];
        System.arraycopy(array, 0, copy, 0, length);
        return String.join((CharSequence)this.pathSeparator, copy);
    }
}

