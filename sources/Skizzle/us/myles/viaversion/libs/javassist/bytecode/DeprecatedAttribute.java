/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import us.myles.viaversion.libs.javassist.bytecode.AttributeInfo;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;

public class DeprecatedAttribute
extends AttributeInfo {
    public static final String tag = "Deprecated";

    DeprecatedAttribute(ConstPool cp, int n, DataInputStream in) throws IOException {
        super(cp, n, in);
    }

    public DeprecatedAttribute(ConstPool cp) {
        super(cp, tag, new byte[0]);
    }

    @Override
    public AttributeInfo copy(ConstPool newCp, Map<String, String> classnames) {
        return new DeprecatedAttribute(newCp);
    }
}

