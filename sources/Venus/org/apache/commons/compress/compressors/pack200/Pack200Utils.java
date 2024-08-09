/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.zip.ZipFile;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Pack200Utils {
    private Pack200Utils() {
    }

    public static void normalize(File file) throws IOException {
        Pack200Utils.normalize(file, file, null);
    }

    public static void normalize(File file, Map<String, String> map) throws IOException {
        Pack200Utils.normalize(file, file, map);
    }

    public static void normalize(File file, File file2) throws IOException {
        Pack200Utils.normalize(file, file2, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void normalize(File file, File file2, Map<String, String> map) throws IOException {
        if (map == null) {
            map = new HashMap<String, String>();
        }
        map.put("pack.segment.limit", "-1");
        File file3 = File.createTempFile("commons-compress", "pack200normalize");
        file3.deleteOnExit();
        try {
            OutputStream outputStream = new FileOutputStream(file3);
            ZipFile zipFile = null;
            try {
                Pack200.Packer packer = Pack200.newPacker();
                packer.properties().putAll(map);
                zipFile = new JarFile(file);
                packer.pack((JarFile)zipFile, outputStream);
                zipFile = null;
                outputStream.close();
                outputStream = null;
                Pack200.Unpacker unpacker = Pack200.newUnpacker();
                outputStream = new JarOutputStream(new FileOutputStream(file2));
                unpacker.unpack(file3, (JarOutputStream)outputStream);
            } finally {
                if (zipFile != null) {
                    zipFile.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } finally {
            file3.delete();
        }
    }
}

