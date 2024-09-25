/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.tools.reflect;

import us.myles.viaversion.libs.javassist.tools.reflect.ClassMetaobject;
import us.myles.viaversion.libs.javassist.tools.reflect.Metaobject;

public interface Metalevel {
    public ClassMetaobject _getClass();

    public Metaobject _getMetaobject();

    public void _setMetaobject(Metaobject var1);
}

