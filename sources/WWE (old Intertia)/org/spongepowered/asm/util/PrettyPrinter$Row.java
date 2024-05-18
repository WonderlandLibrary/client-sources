package org.spongepowered.asm.util;

static class Row implements IVariableWidthEntry
{
    final Table table;
    final String[] args;
    
    public Row(final Table table, final Object... array) {
        super();
        this.table = table.grow(array.length);
        this.args = new String[array.length];
        for (int i = 0; i < array.length; ++i) {
            this.args[i] = array[i].toString();
            this.table.columns.get(i).setMinWidth(this.args[i].length());
        }
    }
    
    @Override
    public String toString() {
        final Object[] array = new Object[this.table.columns.size()];
        for (int i = 0; i < array.length; ++i) {
            final Column column = this.table.columns.get(i);
            if (i >= this.args.length) {
                array[i] = "";
            }
            else {
                array[i] = ((this.args[i].length() > column.getMaxWidth()) ? this.args[i].substring(0, column.getMaxWidth()) : this.args[i]);
            }
        }
        return String.format(this.table.format, array);
    }
    
    @Override
    public int getWidth() {
        return this.toString().length();
    }
}
