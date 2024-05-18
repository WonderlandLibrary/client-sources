/*
 * Decompiled with CFR 0.143.
 */
package javassist.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javassist.bytecode.ConstPool;

abstract class ConstInfo {
    int index;

    public ConstInfo(int i) {
        this.index = i;
    }

    public abstract int getTag();

    public String getClassName(ConstPool cp) {
        return null;
    }

    public void renameClass(ConstPool cp, String oldName, String newName, HashMap cache) {
    }

    public void renameClass(ConstPool cp, Map classnames, HashMap cache) {
    }

    public abstract int copy(ConstPool var1, ConstPool var2, Map var3);

    public abstract void write(DataOutputStream var1) throws IOException;

    public abstract void print(PrintWriter var1);

    public String toString() {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bout);
        this.print(out);
        return bout.toString();
    }
}

