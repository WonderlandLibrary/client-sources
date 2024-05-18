/*
 * Decompiled with CFR 0.143.
 */
package javassist;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javassist.ClassPath;
import javassist.NotFoundException;

final class JarClassPath
implements ClassPath {
    JarFile jarfile;
    String jarfileURL;

    JarClassPath(String pathname) throws NotFoundException {
        try {
            this.jarfile = new JarFile(pathname);
            this.jarfileURL = new File(pathname).getCanonicalFile().toURI().toURL().toString();
            return;
        }
        catch (IOException e) {
            throw new NotFoundException(pathname);
        }
    }

    @Override
    public InputStream openClassfile(String classname) throws NotFoundException {
        try {
            String jarname = classname.replace('.', '/') + ".class";
            JarEntry je = this.jarfile.getJarEntry(jarname);
            if (je != null) {
                return this.jarfile.getInputStream(je);
            }
            return null;
        }
        catch (IOException e) {
            throw new NotFoundException("broken jar file?: " + this.jarfile.getName());
        }
    }

    @Override
    public URL find(String classname) {
        String jarname = classname.replace('.', '/') + ".class";
        JarEntry je = this.jarfile.getJarEntry(jarname);
        if (je != null) {
            try {
                return new URL("jar:" + this.jarfileURL + "!/" + jarname);
            }
            catch (MalformedURLException e) {
                // empty catch block
            }
        }
        return null;
    }

    @Override
    public void close() {
        try {
            this.jarfile.close();
            this.jarfile = null;
        }
        catch (IOException e) {
            // empty catch block
        }
    }

    public String toString() {
        return this.jarfile == null ? "<null>" : this.jarfile.toString();
    }
}

