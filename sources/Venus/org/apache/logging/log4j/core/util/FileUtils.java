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

    public static File fileFromUri(URI uRI) {
        String string;
        Object object;
        if (uRI == null || uRI.getScheme() != null && !PROTOCOL_FILE.equals(uRI.getScheme()) && !JBOSS_FILE.equals(uRI.getScheme())) {
            return null;
        }
        if (uRI.getScheme() == null) {
            object = new File(uRI.toString());
            if (((File)object).exists()) {
                return object;
            }
            try {
                string = uRI.getPath();
                object = new File(string);
                if (((File)object).exists()) {
                    return object;
                }
                uRI = new File(string).toURI();
            } catch (Exception exception) {
                LOGGER.warn("Invalid URI {}", (Object)uRI);
                return null;
            }
        }
        object = StandardCharsets.UTF_8.name();
        try {
            string = uRI.toURL().getFile();
            if (new File(string).exists()) {
                return new File(string);
            }
            string = URLDecoder.decode(string, (String)object);
            return new File(string);
        } catch (MalformedURLException malformedURLException) {
            LOGGER.warn("Invalid URL {}", (Object)uRI, (Object)malformedURLException);
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            LOGGER.warn("Invalid encoding: {}", object, (Object)unsupportedEncodingException);
        }
        return null;
    }

    public static boolean isFile(URL uRL) {
        return uRL != null && (uRL.getProtocol().equals(PROTOCOL_FILE) || uRL.getProtocol().equals(JBOSS_FILE));
    }

    public static String getFileExtension(File file) {
        String string = file.getName();
        if (string.lastIndexOf(".") != -1 && string.lastIndexOf(".") != 0) {
            return string.substring(string.lastIndexOf(".") + 1);
        }
        return null;
    }

    public static void mkdir(File file, boolean bl) throws IOException {
        if (!file.exists()) {
            if (!bl) {
                throw new IOException("The directory " + file.getAbsolutePath() + " does not exist.");
            }
            if (!file.mkdirs()) {
                throw new IOException("Could not create directory " + file.getAbsolutePath());
            }
        }
        if (!file.isDirectory()) {
            throw new IOException("File " + file + " exists and is not a directory. Unable to create directory.");
        }
    }

    public static void makeParentDirs(File file) throws IOException {
        File file2 = Objects.requireNonNull(file, PROTOCOL_FILE).getCanonicalFile().getParentFile();
        if (file2 != null) {
            FileUtils.mkdir(file2, true);
        }
    }
}

