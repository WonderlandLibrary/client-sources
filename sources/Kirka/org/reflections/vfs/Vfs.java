/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.vfs;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import javax.annotation.Nullable;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.Utils;
import org.reflections.vfs.JarInputDir;
import org.reflections.vfs.SystemDir;
import org.reflections.vfs.UrlTypeVFS;
import org.reflections.vfs.ZipDir;
import org.slf4j.Logger;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class Vfs {
    private static List<UrlType> defaultUrlTypes = Lists.newArrayList((Object[])DefaultUrlTypes.values());

    public static List<UrlType> getDefaultUrlTypes() {
        return defaultUrlTypes;
    }

    public static void setDefaultURLTypes(List<UrlType> urlTypes) {
        defaultUrlTypes = urlTypes;
    }

    public static void addDefaultURLTypes(UrlType urlType) {
        defaultUrlTypes.add(0, urlType);
    }

    public static Dir fromURL(URL url) {
        return Vfs.fromURL(url, defaultUrlTypes);
    }

    public static Dir fromURL(URL url, List<UrlType> urlTypes) {
        for (UrlType type : urlTypes) {
            try {
                Dir dir;
                if (!type.matches(url) || (dir = type.createDir(url)) == null) continue;
                return dir;
            }
            catch (Throwable e) {
                if (Reflections.log == null) continue;
                Reflections.log.warn("could not create Dir using " + type + " from url " + url.toExternalForm() + ". skipping.", e);
            }
        }
        throw new ReflectionsException("could not create Vfs.Dir from url, no matching UrlType was found [" + url.toExternalForm() + "]\n" + "either use fromURL(final URL url, final List<UrlType> urlTypes) or " + "use the static setDefaultURLTypes(final List<UrlType> urlTypes) or addDefaultURLTypes(UrlType urlType) " + "with your specialized UrlType.");
    }

    public static Dir fromURL(URL url, UrlType ... urlTypes) {
        return Vfs.fromURL(url, Lists.newArrayList((Object[])urlTypes));
    }

    public static Iterable<File> findFiles(Collection<URL> inUrls, final String packagePrefix, final Predicate<String> nameFilter) {
        Predicate<File> fileNamePredicate = new Predicate<File>(){

            public boolean apply(File file) {
                String path = file.getRelativePath();
                if (path.startsWith(packagePrefix)) {
                    String filename = path.substring(path.indexOf(packagePrefix) + packagePrefix.length());
                    return !Utils.isEmpty(filename) && nameFilter.apply((Object)filename.substring(1));
                }
                return false;
            }
        };
        return Vfs.findFiles(inUrls, fileNamePredicate);
    }

    public static Iterable<File> findFiles(Collection<URL> inUrls, Predicate<File> filePredicate) {
        Iterable<File> result = new ArrayList<File>();
        for (final URL url : inUrls) {
            try {
                result = Iterables.concat(result, (Iterable)Iterables.filter((Iterable)new Iterable<File>(){

                    @Override
                    public Iterator<File> iterator() {
                        return Vfs.fromURL(url).getFiles().iterator();
                    }
                }, filePredicate));
            }
            catch (Throwable e) {
                if (Reflections.log == null) continue;
                Reflections.log.error("could not findFiles for url. continuing. [" + url + "]", e);
            }
        }
        return result;
    }

    @Nullable
    public static java.io.File getFile(URL url) {
        String path;
        java.io.File file;
        try {
            path = url.toURI().getSchemeSpecificPart();
            file = new java.io.File(path);
            if (file.exists()) {
                return file;
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            // empty catch block
        }
        try {
            path = URLDecoder.decode(url.getPath(), "UTF-8");
            if (path.contains(".jar!")) {
                path = path.substring(0, path.lastIndexOf(".jar!") + ".jar".length());
            }
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        try {
            path = url.toExternalForm();
            if (path.startsWith("jar:")) {
                path = path.substring("jar:".length());
            }
            if (path.startsWith("wsjar:")) {
                path = path.substring("wsjar:".length());
            }
            if (path.startsWith("file:")) {
                path = path.substring("file:".length());
            }
            if (path.contains(".jar!")) {
                path = path.substring(0, path.indexOf(".jar!") + ".jar".length());
            }
            if ((file = new java.io.File(path)).exists()) {
                return file;
            }
            file = new java.io.File(path = path.replace("%20", " "));
            if (file.exists()) {
                return file;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum DefaultUrlTypes implements UrlType
    {
        jarFile{

            public boolean matches(URL url) {
                return url.getProtocol().equals("file") && url.toExternalForm().contains(".jar");
            }

            public Dir createDir(URL url) throws Exception {
                return new ZipDir(new JarFile(Vfs.getFile(url)));
            }
        }
        ,
        jarUrl{

            public boolean matches(URL url) {
                return "jar".equals(url.getProtocol()) || "zip".equals(url.getProtocol()) || "wsjar".equals(url.getProtocol());
            }

            public Dir createDir(URL url) throws Exception {
                try {
                    URLConnection urlConnection = url.openConnection();
                    if (urlConnection instanceof JarURLConnection) {
                        return new ZipDir(((JarURLConnection)urlConnection).getJarFile());
                    }
                }
                catch (Throwable urlConnection) {
                    // empty catch block
                }
                java.io.File file = Vfs.getFile(url);
                if (file != null) {
                    return new ZipDir(new JarFile(file));
                }
                return null;
            }
        }
        ,
        directory{

            public boolean matches(URL url) {
                return url.getProtocol().equals("file") && !url.toExternalForm().contains(".jar") && Vfs.getFile(url).isDirectory();
            }

            public Dir createDir(URL url) throws Exception {
                return new SystemDir(Vfs.getFile(url));
            }
        }
        ,
        jboss_vfs{

            public boolean matches(URL url) {
                return url.getProtocol().equals("vfs");
            }

            public Dir createDir(URL url) throws Exception {
                Object content = url.openConnection().getContent();
                Class<?> virtualFile = ClasspathHelper.contextClassLoader().loadClass("org.jboss.vfs.VirtualFile");
                java.io.File physicalFile = (java.io.File)virtualFile.getMethod("getPhysicalFile", new Class[0]).invoke(content, new Object[0]);
                String name = (String)virtualFile.getMethod("getName", new Class[0]).invoke(content, new Object[0]);
                java.io.File file = new java.io.File(physicalFile.getParentFile(), name);
                if (!file.exists() || !file.canRead()) {
                    file = physicalFile;
                }
                return file.isDirectory() ? new SystemDir(file) : new ZipDir(new JarFile(file));
            }
        }
        ,
        jboss_vfsfile{

            public boolean matches(URL url) throws Exception {
                return "vfszip".equals(url.getProtocol()) || "vfsfile".equals(url.getProtocol());
            }

            public Dir createDir(URL url) throws Exception {
                return new UrlTypeVFS().createDir(url);
            }
        }
        ,
        bundle{

            public boolean matches(URL url) throws Exception {
                return url.getProtocol().startsWith("bundle");
            }

            public Dir createDir(URL url) throws Exception {
                return Vfs.fromURL((URL)ClasspathHelper.contextClassLoader().loadClass("org.eclipse.core.runtime.FileLocator").getMethod("resolve", URL.class).invoke(null, url));
            }
        }
        ,
        jarInputStream{

            public boolean matches(URL url) throws Exception {
                return url.toExternalForm().contains(".jar");
            }

            public Dir createDir(URL url) throws Exception {
                return new JarInputDir(url);
            }
        };
        

        private DefaultUrlTypes() {
        }

    }

    public static interface UrlType {
        public boolean matches(URL var1) throws Exception;

        public Dir createDir(URL var1) throws Exception;
    }

    public static interface File {
        public String getName();

        public String getRelativePath();

        public InputStream openInputStream() throws IOException;
    }

    public static interface Dir {
        public String getPath();

        public Iterable<File> getFiles();

        public void close();
    }

}

