/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.util.ArrayList;
import java.util.List;
import joptsimple.internal.Columns;
import joptsimple.internal.Row;
import joptsimple.internal.Strings;

public class Rows {
    private final int overallWidth;
    private final int columnSeparatorWidth;
    private final List<Row> rows = new ArrayList<Row>();
    private int widthOfWidestOption;
    private int widthOfWidestDescription;

    public Rows(int n, int n2) {
        this.overallWidth = n;
        this.columnSeparatorWidth = n2;
    }

    public void add(String string, String string2) {
        this.add(new Row(string, string2));
    }

    private void add(Row row) {
        this.rows.add(row);
        this.widthOfWidestOption = Math.max(this.widthOfWidestOption, row.option.length());
        this.widthOfWidestDescription = Math.max(this.widthOfWidestDescription, row.description.length());
    }

    public void reset() {
        this.rows.clear();
        this.widthOfWidestOption = 0;
        this.widthOfWidestDescription = 0;
    }

    public void fitToWidth() {
        Columns columns = new Columns(this.optionWidth(), this.descriptionWidth());
        ArrayList<Row> arrayList = new ArrayList<Row>();
        for (Row row : this.rows) {
            arrayList.addAll(columns.fit(row));
        }
        this.reset();
        for (Row row : arrayList) {
            this.add(row);
        }
    }

    public String render() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Row row : this.rows) {
            this.pad(stringBuilder, row.option, this.optionWidth()).append(Strings.repeat(' ', this.columnSeparatorWidth));
            this.pad(stringBuilder, row.description, this.descriptionWidth()).append(Strings.LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }

    private int optionWidth() {
        return Math.min((this.overallWidth - this.columnSeparatorWidth) / 2, this.widthOfWidestOption);
    }

    private int descriptionWidth() {
        return Math.min(this.overallWidth - this.optionWidth() - this.columnSeparatorWidth, this.widthOfWidestDescription);
    }

    private StringBuilder pad(StringBuilder stringBuilder, String string, int n) {
        stringBuilder.append(string).append(Strings.repeat(' ', n - string.length()));
        return stringBuilder;
    }
}

