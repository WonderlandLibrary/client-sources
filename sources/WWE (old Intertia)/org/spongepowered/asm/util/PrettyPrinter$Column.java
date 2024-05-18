package org.spongepowered.asm.util;

static class Column
{
    private final Table table;
    private Alignment align;
    private int minWidth;
    private int maxWidth;
    private int size;
    private String title;
    private String format;
    
    Column(final Table table) {
        super();
        this.align = Alignment.LEFT;
        this.minWidth = 1;
        this.maxWidth = 0;
        this.size = 0;
        this.title = "";
        this.format = "%s";
        this.table = table;
    }
    
    Column(final Table table, final String title) {
        this(table);
        this.title = title;
        this.minWidth = title.length();
        this.updateFormat();
    }
    
    Column(final Table table, final Alignment align, final int size, final String s) {
        this(table, s);
        this.align = align;
        this.size = size;
    }
    
    void setAlignment(final Alignment align) {
        this.align = align;
        this.updateFormat();
    }
    
    void setWidth(final int size) {
        if (size > this.size) {
            this.size = size;
            this.updateFormat();
        }
    }
    
    void setMinWidth(final int minWidth) {
        if (minWidth > this.minWidth) {
            this.minWidth = minWidth;
            this.updateFormat();
        }
    }
    
    void setMaxWidth(final int n) {
        this.size = Math.min(this.size, this.maxWidth);
        this.maxWidth = Math.max(1, n);
        this.updateFormat();
    }
    
    void setTitle(final String title) {
        this.title = title;
        this.setWidth(title.length());
    }
    
    private void updateFormat() {
        this.format = "%" + ((this.align == Alignment.RIGHT) ? "" : "-") + Math.min(this.maxWidth, (this.size == 0) ? this.minWidth : this.size) + "s";
        this.table.updateFormat();
    }
    
    int getMaxWidth() {
        return this.maxWidth;
    }
    
    String getTitle() {
        return this.title;
    }
    
    String getFormat() {
        return this.format;
    }
    
    @Override
    public String toString() {
        if (this.title.length() > this.maxWidth) {
            return this.title.substring(0, this.maxWidth);
        }
        return this.title;
    }
}
