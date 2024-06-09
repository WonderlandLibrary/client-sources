/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.vfs;

import com.google.common.collect.AbstractIterator;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import org.reflections.Reflections;
import org.reflections.vfs.Vfs;
import org.reflections.vfs.ZipFile;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ZipDir
implements Vfs.Dir {
    final java.util.zip.ZipFile jarFile;

    public ZipDir(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    public String getPath() {
        return this.jarFile.getName();
    }

    @Override
    public Iterable<Vfs.File> getFiles() {
        return new Iterable<Vfs.File>(){

            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>(){
                    final Enumeration<? extends ZipEntry> entries;
                    {
                        this.entries = ZipDir.this.jarFile.entries();
                    }

                    protected Vfs.File computeNext() {
                        while (this.entries.hasMoreElements()) {
                            ZipEntry entry = this.entries.nextElement();
                            if (entry.isDirectory()) continue;
                            return new ZipFile(ZipDir.this, entry);
                        }
                        return (Vfs.File)this.endOfData();
                    }
                };
            }

        };
    }

    @Override
    public void close() {
        block2 : {
            try {
                this.jarFile.close();
            }
            catch (IOException e) {
                if (Reflections.log == null) break block2;
                Reflections.log.warn("Could not close JarFile", (Throwable)e);
            }
        }
    }

    public String toString() {
        return this.jarFile.getName();
    }

}

