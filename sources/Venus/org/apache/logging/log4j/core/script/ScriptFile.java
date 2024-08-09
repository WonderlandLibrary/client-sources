/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.script;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.util.ExtensionLanguageMapping;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.IOUtils;
import org.apache.logging.log4j.core.util.NetUtils;

@Plugin(name="ScriptFile", category="Core", printObject=true)
public class ScriptFile
extends AbstractScript {
    private final Path filePath;
    private final boolean isWatched;

    public ScriptFile(String string, Path path, String string2, boolean bl, String string3) {
        super(string, string2, string3);
        this.filePath = path;
        this.isWatched = bl;
    }

    public Path getPath() {
        return this.filePath;
    }

    public boolean isWatched() {
        return this.isWatched;
    }

    @PluginFactory
    public static ScriptFile createScript(@PluginAttribute(value="name") String string, @PluginAttribute(value="language") String string2, @PluginAttribute(value="path") String string3, @PluginAttribute(value="isWatched") Boolean bl, @PluginAttribute(value="charset") Charset charset) {
        Object object;
        Object object2;
        Object object3;
        if (string3 == null) {
            LOGGER.error("No script path provided for ScriptFile");
            return null;
        }
        if (string == null) {
            string = string3;
        }
        URI uRI = NetUtils.toURI(string3);
        File file = FileUtils.fileFromUri(uRI);
        if (string2 == null && file != null && (object3 = FileUtils.getFileExtension(file)) != null && (object2 = ExtensionLanguageMapping.getByExtension((String)object3)) != null) {
            string2 = object2.getLanguage();
        }
        if (string2 == null) {
            LOGGER.info("No script language supplied, defaulting to {}", (Object)"JavaScript");
            string2 = "JavaScript";
        }
        object3 = charset == null ? Charset.defaultCharset() : charset;
        try {
            object = new InputStreamReader(file != null ? new FileInputStream(file) : uRI.toURL().openStream(), (Charset)object3);
            Throwable throwable = null;
            try {
                object2 = IOUtils.toString((Reader)object);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (object != null) {
                    if (throwable != null) {
                        try {
                            ((Reader)object).close();
                        } catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        ((Reader)object).close();
                    }
                }
            }
        } catch (IOException iOException) {
            LOGGER.error("{}: language={}, path={}, actualCharset={}", (Object)iOException.getClass().getSimpleName(), (Object)string2, (Object)string3, object3);
            return null;
        }
        Object object4 = object = file != null ? Paths.get(file.toURI()) : Paths.get(uRI);
        if (object == null) {
            LOGGER.error("Unable to convert {} to a Path", (Object)uRI.toString());
            return null;
        }
        return new ScriptFile(string, (Path)object, string2, bl == null ? Boolean.FALSE : bl, (String)object2);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (!this.getName().equals(this.filePath.toString())) {
            stringBuilder.append("name=").append(this.getName()).append(", ");
        }
        stringBuilder.append("path=").append(this.filePath);
        if (this.getLanguage() != null) {
            stringBuilder.append(", language=").append(this.getLanguage());
        }
        stringBuilder.append(", isWatched=").append(this.isWatched);
        return stringBuilder.toString();
    }
}

