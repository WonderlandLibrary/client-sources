/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineBuffer
implements Iterable<String> {
    private ArrayList<String> lines = new ArrayList();

    public int size() {
        return this.lines.size();
    }

    public String get(int n) {
        return this.lines.get(n);
    }

    public void add(String string) {
        this.checkLine(string);
        this.lines.add(string);
    }

    public void add(String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            this.add(string);
        }
    }

    public void insert(int n, String string) {
        this.checkLine(string);
        this.lines.add(n, string);
    }

    public void insert(int n, String[] stringArray) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            this.checkLine(string);
        }
        this.lines.addAll(n, Arrays.asList(stringArray));
    }

    private void checkLine(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Line is null");
        }
        if (string.indexOf(10) >= 0) {
            throw new IllegalArgumentException("Line contains LF");
        }
        if (string.indexOf(13) >= 0) {
            throw new IllegalArgumentException("Line contains CR");
        }
    }

    public int indexMatch(Pattern pattern) {
        for (int i = 0; i < this.lines.size(); ++i) {
            String string = this.lines.get(i);
            Matcher matcher = pattern.matcher(string);
            if (!matcher.matches()) continue;
            return i;
        }
        return 1;
    }

    public static LineBuffer readAll(Reader reader) throws IOException {
        LineBuffer lineBuffer = new LineBuffer();
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            String string;
            if ((string = bufferedReader.readLine()) == null) {
                bufferedReader.close();
                return lineBuffer;
            }
            lineBuffer.add(string);
        }
    }

    public String[] getLines() {
        return this.lines.toArray(new String[this.lines.size()]);
    }

    @Override
    public Iterator<String> iterator() {
        return new Itr(this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.lines.size(); ++i) {
            String string = this.lines.get(i);
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public class Itr
    implements Iterator<String> {
        private int position;
        final LineBuffer this$0;

        public Itr(LineBuffer lineBuffer) {
            this.this$0 = lineBuffer;
        }

        @Override
        public boolean hasNext() {
            return this.position < this.this$0.lines.size();
        }

        @Override
        public String next() {
            String string = this.this$0.lines.get(this.position);
            ++this.position;
            return string;
        }

        @Override
        public Object next() {
            return this.next();
        }
    }
}

