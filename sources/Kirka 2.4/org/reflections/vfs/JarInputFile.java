/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import org.reflections.vfs.JarInputDir;
import org.reflections.vfs.Vfs;

public class JarInputFile
implements Vfs.File {
    private final ZipEntry entry;
    private final JarInputDir jarInputDir;
    private final long fromIndex;
    private final long endIndex;

    public JarInputFile(ZipEntry entry, JarInputDir jarInputDir, long cursor, long nextCursor) {
        this.entry = entry;
        this.jarInputDir = jarInputDir;
        this.fromIndex = cursor;
        this.endIndex = nextCursor;
    }

    public String getName() {
        String name = this.entry.getName();
        return name.substring(name.lastIndexOf("/") + 1);
    }

    public String getRelativePath() {
        return this.entry.getName();
    }

    public InputStream openInputStream() throws IOException {
        return new InputStream(){

            public int read() throws IOException {
                if (JarInputFile.access$000((JarInputFile)JarInputFile.this).cursor >= JarInputFile.this.fromIndex && JarInputFile.access$000((JarInputFile)JarInputFile.this).cursor <= JarInputFile.this.endIndex) {
                    int read = JarInputFile.access$000((JarInputFile)JarInputFile.this).jarInputStream.read();
                    ++JarInputFile.access$000((JarInputFile)JarInputFile.this).cursor;
                    return read;
                }
                return -1;
            }
        };
    }

    static /* synthetic */ JarInputDir access$000(JarInputFile x0) {
        return x0.jarInputDir;
    }

}

