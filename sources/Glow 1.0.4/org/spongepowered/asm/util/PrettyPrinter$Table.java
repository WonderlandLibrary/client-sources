package org.spongepowered.asm.util;

import com.google.common.base.*;
import java.util.*;

static class Table implements IVariableWidthEntry
{
    final List<Column> columns;
    final List<Row> rows;
    String format;
    int colSpacing;
    boolean addHeader;
    
    Table() {
        super();
        this.columns = new ArrayList<Column>();
        this.rows = new ArrayList<Row>();
        this.format = "%s";
        this.colSpacing = 2;
        this.addHeader = true;
    }
    
    void headerAdded() {
        this.addHeader = false;
    }
    
    void setColSpacing(final int n) {
        this.colSpacing = Math.max(0, n);
        this.updateFormat();
    }
    
    Table grow(final int n) {
        while (this.columns.size() < n) {
            this.columns.add(new Column(this));
        }
        this.updateFormat();
        return this;
    }
    
    Column add(final Column column) {
        this.columns.add(column);
        return column;
    }
    
    Row add(final Row row) {
        this.rows.add(row);
        return row;
    }
    
    Column addColumn(final String s) {
        return this.add(new Column(this, s));
    }
    
    Column addColumn(final Alignment alignment, final int n, final String s) {
        return this.add(new Column(this, alignment, n, s));
    }
    
    Row addRow(final Object... array) {
        return this.add(new Row(this, array));
    }
    
    void updateFormat() {
        final String repeat = Strings.repeat(" ", this.colSpacing);
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (final Column column : this.columns) {
            if (n != 0) {
                sb.append(repeat);
            }
            n = 1;
            sb.append(column.getFormat());
        }
        this.format = sb.toString();
    }
    
    String getFormat() {
        return this.format;
    }
    
    Object[] getTitles() {
        final ArrayList<String> list = new ArrayList<String>();
        final Iterator<Column> iterator = this.columns.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next().getTitle());
        }
        return list.toArray();
    }
    
    @Override
    public String toString() {
        boolean b = false;
        final String[] array = new String[this.columns.size()];
        for (int i = 0; i < this.columns.size(); ++i) {
            array[i] = this.columns.get(i).toString();
            b |= !array[i].isEmpty();
        }
        return b ? String.format(this.format, (Object[])array) : null;
    }
    
    @Override
    public int getWidth() {
        final String string = this.toString();
        return (string != null) ? string.length() : 0;
    }
}
