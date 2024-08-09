/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import joptsimple.internal.Row;
import joptsimple.internal.Strings;

class Columns {
    private static final int INDENT_WIDTH = 2;
    private final int optionWidth;
    private final int descriptionWidth;

    Columns(int n, int n2) {
        this.optionWidth = n;
        this.descriptionWidth = n2;
    }

    List<Row> fit(Row row) {
        List<String> list = this.piecesOf(row.option, this.optionWidth);
        List<String> list2 = this.piecesOf(row.description, this.descriptionWidth);
        ArrayList<Row> arrayList = new ArrayList<Row>();
        for (int i = 0; i < Math.max(list.size(), list2.size()); ++i) {
            arrayList.add(new Row(Columns.itemOrEmpty(list, i), Columns.itemOrEmpty(list2, i)));
        }
        return arrayList;
    }

    private static String itemOrEmpty(List<String> list, int n) {
        return n >= list.size() ? "" : list.get(n);
    }

    private List<String> piecesOf(String string, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String string2 : string.trim().split(Strings.LINE_SEPARATOR)) {
            arrayList.addAll(this.piecesOfEmbeddedLine(string2, n));
        }
        return arrayList;
    }

    private List<String> piecesOfEmbeddedLine(String string, int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        BreakIterator breakIterator = BreakIterator.getLineInstance();
        breakIterator.setText(string);
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = breakIterator.first();
        int n3 = breakIterator.next();
        while (n3 != -1) {
            stringBuilder = this.processNextWord(string, stringBuilder, n2, n3, n, arrayList);
            n2 = n3;
            n3 = breakIterator.next();
        }
        if (stringBuilder.length() > 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList;
    }

    private StringBuilder processNextWord(String string, StringBuilder stringBuilder, int n, int n2, int n3, List<String> list) {
        StringBuilder stringBuilder2 = stringBuilder;
        String string2 = string.substring(n, n2);
        if (stringBuilder2.length() + string2.length() > n3) {
            list.add(stringBuilder2.toString().replaceAll("\\s+$", ""));
            stringBuilder2 = new StringBuilder(Strings.repeat(' ', 2)).append(string2);
        } else {
            stringBuilder2.append(string2);
        }
        return stringBuilder2;
    }
}

