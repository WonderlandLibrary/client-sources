/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import javassist.ClassPath;

final class ClassPathList {
    ClassPathList next;
    ClassPath path;

    ClassPathList(ClassPath p, ClassPathList n) {
        this.next = n;
        this.path = p;
    }
}

