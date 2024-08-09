/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class JSONException
extends IOException {
    private final List<Entry> entries = Lists.newArrayList();
    private final String message;

    public JSONException(String string) {
        this.entries.add(new Entry());
        this.message = string;
    }

    public JSONException(String string, Throwable throwable) {
        super(throwable);
        this.entries.add(new Entry());
        this.message = string;
    }

    public void prependJsonKey(String string) {
        this.entries.get(0).addJsonKey(string);
    }

    public void setFilenameAndFlush(String string) {
        this.entries.get((int)0).filename = string;
        this.entries.add(0, new Entry());
    }

    @Override
    public String getMessage() {
        return "Invalid " + this.entries.get(this.entries.size() - 1) + ": " + this.message;
    }

    public static JSONException forException(Exception exception) {
        if (exception instanceof JSONException) {
            return (JSONException)exception;
        }
        String string = exception.getMessage();
        if (exception instanceof FileNotFoundException) {
            string = "File not found";
        }
        return new JSONException(string, exception);
    }

    public static class Entry {
        @Nullable
        private String filename;
        private final List<String> jsonKeys = Lists.newArrayList();

        private Entry() {
        }

        private void addJsonKey(String string) {
            this.jsonKeys.add(0, string);
        }

        public String getJsonKeys() {
            return StringUtils.join(this.jsonKeys, "->");
        }

        public String toString() {
            if (this.filename != null) {
                return this.jsonKeys.isEmpty() ? this.filename : this.filename + " " + this.getJsonKeys();
            }
            return this.jsonKeys.isEmpty() ? "(Unknown file)" : "(Unknown file) " + this.getJsonKeys();
        }
    }
}

