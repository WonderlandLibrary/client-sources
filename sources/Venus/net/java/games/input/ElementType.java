/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

final class ElementType {
    private static final ElementType[] map = new ElementType[514];
    public static final ElementType INPUT_MISC = new ElementType(1);
    public static final ElementType INPUT_BUTTON = new ElementType(2);
    public static final ElementType INPUT_AXIS = new ElementType(3);
    public static final ElementType INPUT_SCANCODES = new ElementType(4);
    public static final ElementType OUTPUT = new ElementType(129);
    public static final ElementType FEATURE = new ElementType(257);
    public static final ElementType COLLECTION = new ElementType(513);
    private final int type_id;

    public static final ElementType map(int n) {
        if (n < 0 || n >= map.length) {
            return null;
        }
        return map[n];
    }

    private ElementType(int n) {
        ElementType.map[n] = this;
        this.type_id = n;
    }

    public final String toString() {
        return "ElementType (0x" + Integer.toHexString(this.type_id) + ")";
    }
}

