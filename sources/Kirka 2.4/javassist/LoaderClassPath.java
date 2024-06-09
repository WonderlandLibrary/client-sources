/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import javassist.ClassPath;

public class LoaderClassPath
implements ClassPath {
    private WeakReference clref;

    public LoaderClassPath(ClassLoader cl) {
        this.clref = new WeakReference<ClassLoader>(cl);
    }

    public String toString() {
        Object cl = null;
        if (this.clref != null) {
            cl = this.clref.get();
        }
        return cl == null ? "<null>" : cl.toString();
    }

    @Override
    public InputStream openClassfile(String classname) {
        String cname = classname.replace('.', '/') + ".class";
        ClassLoader cl = (ClassLoader)this.clref.get();
        if (cl == null) {
            return null;
        }
        return cl.getResourceAsStream(cname);
    }

    @Override
    public URL find(String classname) {
        String cname = classname.replace('.', '/') + ".class";
        ClassLoader cl = (ClassLoader)this.clref.get();
        if (cl == null) {
            return null;
        }
        return cl.getResource(cname);
    }

    @Override
    public void close() {
        this.clref = null;
    }
}

