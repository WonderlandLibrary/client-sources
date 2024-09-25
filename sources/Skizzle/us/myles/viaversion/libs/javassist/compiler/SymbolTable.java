/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.compiler;

import java.util.HashMap;
import us.myles.viaversion.libs.javassist.compiler.ast.Declarator;

public final class SymbolTable
extends HashMap<String, Declarator> {
    private static final long serialVersionUID = 1L;
    private SymbolTable parent;

    public SymbolTable() {
        this((SymbolTable)null);
    }

    public SymbolTable(SymbolTable p) {
        this.parent = p;
    }

    public SymbolTable getParent() {
        return this.parent;
    }

    public Declarator lookup(String name) {
        Declarator found = (Declarator)this.get(name);
        if (found == null && this.parent != null) {
            return this.parent.lookup(name);
        }
        return found;
    }

    public void append(String name, Declarator value) {
        this.put(name, value);
    }
}

