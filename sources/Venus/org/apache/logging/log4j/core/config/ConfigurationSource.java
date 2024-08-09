/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

public class ConfigurationSource {
    public static final ConfigurationSource NULL_SOURCE = new ConfigurationSource(new byte[0]);
    private final File file;
    private final URL url;
    private final String location;
    private final InputStream stream;
    private final byte[] data;

    public ConfigurationSource(InputStream inputStream, File file) {
        this.stream = Objects.requireNonNull(inputStream, "stream is null");
        this.file = Objects.requireNonNull(file, "file is null");
        this.location = file.getAbsolutePath();
        this.url = null;
        this.data = null;
    }

    public ConfigurationSource(InputStream inputStream, URL uRL) {
        this.stream = Objects.requireNonNull(inputStream, "stream is null");
        this.url = Objects.requireNonNull(uRL, "URL is null");
        this.location = uRL.toString();
        this.file = null;
        this.data = null;
    }

    public ConfigurationSource(InputStream inputStream) throws IOException {
        this(ConfigurationSource.toByteArray(inputStream));
    }

    private ConfigurationSource(byte[] byArray) {
        this.data = Objects.requireNonNull(byArray, "data is null");
        this.stream = new ByteArrayInputStream(byArray);
        this.file = null;
        this.url = null;
        this.location = null;
    }

    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        int n = Math.max(4096, inputStream.available());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(n);
        byte[] byArray = new byte[n];
        int n2 = inputStream.read(byArray);
        while (n2 > 0) {
            byteArrayOutputStream.write(byArray, 0, n2);
            n2 = inputStream.read(byArray);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public File getFile() {
        return this.file;
    }

    public URL getURL() {
        return this.url;
    }

    public URI getURI() {
        URI uRI = null;
        if (this.url != null) {
            try {
                uRI = this.url.toURI();
            } catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        if (uRI == null && this.file != null) {
            uRI = this.file.toURI();
        }
        if (uRI == null && this.location != null) {
            try {
                uRI = new URI(this.location);
            } catch (URISyntaxException uRISyntaxException) {
                try {
                    uRI = new URI("file://" + this.location);
                } catch (URISyntaxException uRISyntaxException2) {
                    // empty catch block
                }
            }
        }
        return uRI;
    }

    public String getLocation() {
        return this.location;
    }

    public InputStream getInputStream() {
        return this.stream;
    }

    public ConfigurationSource resetInputStream() throws IOException {
        if (this.file != null) {
            return new ConfigurationSource((InputStream)new FileInputStream(this.file), this.file);
        }
        if (this.url != null) {
            return new ConfigurationSource(this.url.openStream(), this.url);
        }
        return new ConfigurationSource(this.data);
    }

    public String toString() {
        if (this.location != null) {
            return this.location;
        }
        if (this == NULL_SOURCE) {
            return "NULL_SOURCE";
        }
        int n = this.data == null ? -1 : this.data.length;
        return "stream (" + n + " bytes, unknown location)";
    }
}

