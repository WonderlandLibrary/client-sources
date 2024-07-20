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

    public ConfigurationSource(InputStream stream, File file) {
        this.stream = Objects.requireNonNull(stream, "stream is null");
        this.file = Objects.requireNonNull(file, "file is null");
        this.location = file.getAbsolutePath();
        this.url = null;
        this.data = null;
    }

    public ConfigurationSource(InputStream stream, URL url) {
        this.stream = Objects.requireNonNull(stream, "stream is null");
        this.url = Objects.requireNonNull(url, "URL is null");
        this.location = url.toString();
        this.file = null;
        this.data = null;
    }

    public ConfigurationSource(InputStream stream) throws IOException {
        this(ConfigurationSource.toByteArray(stream));
    }

    private ConfigurationSource(byte[] data) {
        this.data = Objects.requireNonNull(data, "data is null");
        this.stream = new ByteArrayInputStream(data);
        this.file = null;
        this.url = null;
        this.location = null;
    }

    private static byte[] toByteArray(InputStream inputStream) throws IOException {
        int buffSize = Math.max(4096, inputStream.available());
        ByteArrayOutputStream contents = new ByteArrayOutputStream(buffSize);
        byte[] buff = new byte[buffSize];
        int length = inputStream.read(buff);
        while (length > 0) {
            contents.write(buff, 0, length);
            length = inputStream.read(buff);
        }
        return contents.toByteArray();
    }

    public File getFile() {
        return this.file;
    }

    public URL getURL() {
        return this.url;
    }

    public URI getURI() {
        URI sourceURI = null;
        if (this.url != null) {
            try {
                sourceURI = this.url.toURI();
            } catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        if (sourceURI == null && this.file != null) {
            sourceURI = this.file.toURI();
        }
        if (sourceURI == null && this.location != null) {
            try {
                sourceURI = new URI(this.location);
            } catch (URISyntaxException ex) {
                try {
                    sourceURI = new URI("file://" + this.location);
                } catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
            }
        }
        return sourceURI;
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
        int length = this.data == null ? -1 : this.data.length;
        return "stream (" + length + " bytes, unknown location)";
    }
}

