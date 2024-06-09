/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.InputStream;
import java.net.URL;
import javassist.ClassPath;

public class ClassClassPath
implements ClassPath {
    private Class thisClass;

    public ClassClassPath(Class c) {
        this.thisClass = c;
    }

    ClassClassPath() {
        this(Object.class);
    }

    @Override
    public InputStream openClassfile(String classname) {
        String jarname = "/" + classname.replace('.', '/') + ".class";
        return this.thisClass.getResourceAsStream(jarname);
    }

    @Override
    public URL find(String classname) {
        String jarname = "/" + classname.replace('.', '/') + ".class";
        return this.thisClass.getResource(jarname);
    }

    @Override
    public void close() {
    }

    public String toString() {
        return this.thisClass.getName() + ".class";
    }
}

