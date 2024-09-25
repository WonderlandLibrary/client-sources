/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist;

import us.myles.viaversion.libs.javassist.ClassPath;

final class ClassPathList {
    ClassPathList next;
    ClassPath path;

    ClassPathList(ClassPath p, ClassPathList n) {
        this.next = n;
        this.path = p;
    }
}

