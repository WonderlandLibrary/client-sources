/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.CannotCompileException;
import com.viaversion.viaversion.libs.javassist.ClassClassPath;
import com.viaversion.viaversion.libs.javassist.ClassPath;
import com.viaversion.viaversion.libs.javassist.ClassPathList;
import com.viaversion.viaversion.libs.javassist.DirClassPath;
import com.viaversion.viaversion.libs.javassist.JarClassPath;
import com.viaversion.viaversion.libs.javassist.JarDirClassPath;
import com.viaversion.viaversion.libs.javassist.LoaderClassPath;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import com.viaversion.viaversion.libs.javassist.bytecode.ClassFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

final class ClassPoolTail {
    protected ClassPathList pathList = null;

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[class path: ");
        ClassPathList list = this.pathList;
        while (true) {
            if (list == null) {
                buf.append(']');
                return buf.toString();
            }
            buf.append(list.path.toString());
            buf.append(File.pathSeparatorChar);
            list = list.next;
        }
    }

    public synchronized ClassPath insertClassPath(ClassPath cp) {
        this.pathList = new ClassPathList(cp, this.pathList);
        return cp;
    }

    public synchronized ClassPath appendClassPath(ClassPath cp) {
        ClassPathList tail = new ClassPathList(cp, null);
        ClassPathList list = this.pathList;
        if (list == null) {
            this.pathList = tail;
            return cp;
        }
        while (true) {
            if (list.next == null) {
                list.next = tail;
                return cp;
            }
            list = list.next;
        }
    }

    public synchronized void removeClassPath(ClassPath cp) {
        ClassPathList list = this.pathList;
        if (list == null) return;
        if (list.path == cp) {
            this.pathList = list.next;
            return;
        }
        while (list.next != null) {
            if (list.next.path == cp) {
                list.next = list.next.next;
                continue;
            }
            list = list.next;
        }
    }

    public ClassPath appendSystemPath() {
        if (ClassFile.MAJOR_VERSION < 53) {
            return this.appendClassPath(new ClassClassPath());
        }
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return this.appendClassPath(new LoaderClassPath(cl));
    }

    public ClassPath insertClassPath(String pathname) throws NotFoundException {
        return this.insertClassPath(ClassPoolTail.makePathObject(pathname));
    }

    public ClassPath appendClassPath(String pathname) throws NotFoundException {
        return this.appendClassPath(ClassPoolTail.makePathObject(pathname));
    }

    private static ClassPath makePathObject(String pathname) throws NotFoundException {
        String lower = pathname.toLowerCase();
        if (lower.endsWith(".jar")) return new JarClassPath(pathname);
        if (lower.endsWith(".zip")) {
            return new JarClassPath(pathname);
        }
        int len = pathname.length();
        if (len <= 2) return new DirClassPath(pathname);
        if (pathname.charAt(len - 1) != '*') return new DirClassPath(pathname);
        if (pathname.charAt(len - 2) != '/') {
            if (pathname.charAt(len - 2) != File.separatorChar) return new DirClassPath(pathname);
        }
        String dir = pathname.substring(0, len - 2);
        return new JarDirClassPath(dir);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void writeClassfile(String classname, OutputStream out) throws NotFoundException, IOException, CannotCompileException {
        InputStream fin = this.openClassfile(classname);
        if (fin == null) {
            throw new NotFoundException(classname);
        }
        try {
            ClassPoolTail.copyStream(fin, out);
            return;
        }
        finally {
            fin.close();
        }
    }

    InputStream openClassfile(String classname) throws NotFoundException {
        ClassPathList list = this.pathList;
        InputStream ins = null;
        NotFoundException error = null;
        while (true) {
            block4: {
                if (list == null) {
                    if (error == null) return null;
                    throw error;
                }
                try {
                    ins = list.path.openClassfile(classname);
                }
                catch (NotFoundException e) {
                    if (error != null) break block4;
                    error = e;
                }
            }
            if (ins != null) return ins;
            list = list.next;
        }
    }

    public URL find(String classname) {
        ClassPathList list = this.pathList;
        URL url = null;
        while (list != null) {
            url = list.path.find(classname);
            if (url != null) return url;
            list = list.next;
        }
        return null;
    }

    public static byte[] readStream(InputStream fin) throws IOException {
        int size;
        byte[][] bufs = new byte[8][];
        int bufsize = 4096;
        int i = 0;
        block0: while (true) {
            if (i >= 8) throw new IOException("too much data");
            bufs[i] = new byte[bufsize];
            size = 0;
            int len = 0;
            while ((len = fin.read(bufs[i], size, bufsize - size)) >= 0) {
                if ((size += len) < bufsize) continue;
                bufsize *= 2;
                ++i;
                continue block0;
            }
            break;
        }
        byte[] result = new byte[bufsize - 4096 + size];
        int s = 0;
        int j = 0;
        while (true) {
            if (j >= i) {
                System.arraycopy(bufs[i], 0, result, s, size);
                return result;
            }
            System.arraycopy(bufs[j], 0, result, s, s + 4096);
            s = s + s + 4096;
            ++j;
        }
    }

    public static void copyStream(InputStream fin, OutputStream fout) throws IOException {
        int bufsize = 4096;
        byte[] buf = null;
        int i = 0;
        while (i < 64) {
            if (i < 8) {
                buf = new byte[bufsize *= 2];
            }
            int size = 0;
            int len = 0;
            do {
                if ((len = fin.read(buf, size, bufsize - size)) >= 0) continue;
                fout.write(buf, 0, size);
                return;
            } while ((size += len) < bufsize);
            fout.write(buf);
            ++i;
        }
        throw new IOException("too much data");
    }
}

