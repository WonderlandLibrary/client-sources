/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

class Row {
    final String option;
    final String description;

    Row(String string, String string2) {
        this.option = string;
        this.description = string2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null || !this.getClass().equals(object.getClass())) {
            return true;
        }
        Row row = (Row)object;
        return this.option.equals(row.option) && this.description.equals(row.description);
    }

    public int hashCode() {
        return this.option.hashCode() ^ this.description.hashCode();
    }
}

