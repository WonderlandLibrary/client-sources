/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.vfs;

import com.google.common.collect.AbstractIterator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import org.reflections.ReflectionsException;
import org.reflections.util.Utils;
import org.reflections.vfs.JarInputFile;
import org.reflections.vfs.Vfs;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class JarInputDir
implements Vfs.Dir {
    private final URL url;
    JarInputStream jarInputStream;
    long cursor = 0L;
    long nextCursor = 0L;

    public JarInputDir(URL url) {
        this.url = url;
    }

    @Override
    public String getPath() {
        return this.url.getPath();
    }

    @Override
    public Iterable<Vfs.File> getFiles() {
        return new Iterable<Vfs.File>(){

            @Override
            public Iterator<Vfs.File> iterator() {
                return new AbstractIterator<Vfs.File>(){
                    {
                        try {
                            JarInputDir.this.jarInputStream = new JarInputStream(JarInputDir.this.url.openConnection().getInputStream());
                        }
                        catch (Exception e) {
                            throw new ReflectionsException("Could not open url connection", e);
                        }
                    }

                    protected Vfs.File computeNext() {
                        try {
                            JarEntry entry;
                            do {
                                if ((entry = JarInputDir.this.jarInputStream.getNextJarEntry()) == null) {
                                    return (Vfs.File)this.endOfData();
                                }
                                long size = entry.getSize();
                                if (size < 0L) {
                                    size = 0xFFFFFFFFL + size;
                                }
                                JarInputDir.this.nextCursor += size;
                            } while (entry.isDirectory());
                            return new JarInputFile(entry, JarInputDir.this, JarInputDir.this.cursor, JarInputDir.this.nextCursor);
                        }
                        catch (IOException e) {
                            throw new ReflectionsException("could not get next zip entry", e);
                        }
                    }
                };
            }

        };
    }

    @Override
    public void close() {
        Utils.close(this.jarInputStream);
    }

}

