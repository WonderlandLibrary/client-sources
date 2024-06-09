/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javassist.ClassPath;

public class ByteArrayClassPath
implements ClassPath {
    protected String classname;
    protected byte[] classfile;

    public ByteArrayClassPath(String name, byte[] classfile) {
        this.classname = name;
        this.classfile = classfile;
    }

    @Override
    public void close() {
    }

    public String toString() {
        return "byte[]:" + this.classname;
    }

    @Override
    public InputStream openClassfile(String classname) {
        if (this.classname.equals(classname)) {
            return new ByteArrayInputStream(this.classfile);
        }
        return null;
    }

    @Override
    public URL find(String classname) {
        if (this.classname.equals(classname)) {
            String cname = classname.replace('.', '/') + ".class";
            try {
                return new URL("file:/ByteArrayClassPath/" + cname);
            }
            catch (MalformedURLException e) {
                // empty catch block
            }
        }
        return null;
    }
}

