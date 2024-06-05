/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist;

import com.viaversion.viaversion.libs.javassist.ClassPath;
import com.viaversion.viaversion.libs.javassist.JarClassPath;
import com.viaversion.viaversion.libs.javassist.NotFoundException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;

final class JarDirClassPath
implements ClassPath {
    JarClassPath[] jars;

    JarDirClassPath(String dirName) throws NotFoundException {
        File[] files = new File(dirName).listFiles(new FilenameFilter(){

            @Override
            public boolean accept(File dir, String name) {
                if ((name = name.toLowerCase()).endsWith(".jar")) return true;
                if (name.endsWith(".zip")) return true;
                return false;
            }
        });
        if (files == null) return;
        this.jars = new JarClassPath[files.length];
        int i = 0;
        while (i < files.length) {
            this.jars[i] = new JarClassPath(files[i].getPath());
            ++i;
        }
    }

    @Override
    public InputStream openClassfile(String classname) throws NotFoundException {
        if (this.jars == null) return null;
        int i = 0;
        while (i < this.jars.length) {
            InputStream is = this.jars[i].openClassfile(classname);
            if (is != null) {
                return is;
            }
            ++i;
        }
        return null;
    }

    @Override
    public URL find(String classname) {
        if (this.jars == null) return null;
        int i = 0;
        while (i < this.jars.length) {
            URL url = this.jars[i].find(classname);
            if (url != null) {
                return url;
            }
            ++i;
        }
        return null;
    }
}

