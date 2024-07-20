/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class FileUtils {
    private static final String PROTOCOL_FILE = "file";
    private static final String JBOSS_FILE = "vfsfile";
    private static final Logger LOGGER = StatusLogger.getLogger();

    private FileUtils() {
    }

    public static File fileFromUri(URI uri) {
        if (uri == null || uri.getScheme() != null && !PROTOCOL_FILE.equals(uri.getScheme()) && !JBOSS_FILE.equals(uri.getScheme())) {
            return null;
        }
        if (uri.getScheme() == null) {
            File file = new File(uri.toString());
            if (file.exists()) {
                return file;
            }
            try {
                String path = uri.getPath();
                file = new File(path);
                if (file.exists()) {
                    return file;
                }
                uri = new File(path).toURI();
            } catch (Exception ex) {
                LOGGER.warn("Invalid URI {}", (Object)uri);
                return null;
            }
        }
        String charsetName = StandardCharsets.UTF_8.name();
        try {
            String fileName = uri.toURL().getFile();
            if (new File(fileName).exists()) {
                return new File(fileName);
            }
            fileName = URLDecoder.decode(fileName, charsetName);
            return new File(fileName);
        } catch (MalformedURLException ex) {
            LOGGER.warn("Invalid URL {}", (Object)uri, (Object)ex);
        } catch (UnsupportedEncodingException uee) {
            LOGGER.warn("Invalid encoding: {}", (Object)charsetName, (Object)uee);
        }
        return null;
    }

    public static boolean isFile(URL url) {
        return url != null && (url.getProtocol().equals(PROTOCOL_FILE) || url.getProtocol().equals(JBOSS_FILE));
    }

    public static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    public static void mkdir(File dir, boolean createDirectoryIfNotExisting) throws IOException {
        if (!dir.exists()) {
            if (!createDirectoryIfNotExisting) {
                throw new IOException("The directory " + dir.getAbsolutePath() + " does not exist.");
            }
            if (!dir.mkdirs()) {
                throw new IOException("Could not create directory " + dir.getAbsolutePath());
            }
        }
        if (!dir.isDirectory()) {
            throw new IOException("File " + dir + " exists and is not a directory. Unable to create directory.");
        }
    }

    public static void makeParentDirs(File file) throws IOException {
        File parent = Objects.requireNonNull(file, PROTOCOL_FILE).getCanonicalFile().getParentFile();
        if (parent != null) {
            FileUtils.mkdir(parent, true);
        }
    }
}

