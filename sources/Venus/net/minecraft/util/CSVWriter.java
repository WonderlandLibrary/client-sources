/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class CSVWriter {
    private final Writer field_225429_a;
    private final int field_225430_b;

    private CSVWriter(Writer writer, List<String> list) throws IOException {
        this.field_225429_a = writer;
        this.field_225430_b = list.size();
        this.func_225427_a(list.stream());
    }

    public static Builder func_225428_a() {
        return new Builder();
    }

    public void func_225426_a(Object ... objectArray) throws IOException {
        if (objectArray.length != this.field_225430_b) {
            throw new IllegalArgumentException("Invalid number of columns, expected " + this.field_225430_b + ", but got " + objectArray.length);
        }
        this.func_225427_a(Stream.of(objectArray));
    }

    private void func_225427_a(Stream<?> stream) throws IOException {
        this.field_225429_a.write(stream.map(CSVWriter::func_225425_a).collect(Collectors.joining(",")) + "\r\n");
    }

    private static String func_225425_a(@Nullable Object object) {
        return StringEscapeUtils.escapeCsv(object != null ? object.toString() : "[null]");
    }

    public static class Builder {
        private final List<String> field_225424_a = Lists.newArrayList();

        public Builder func_225423_a(String string) {
            this.field_225424_a.add(string);
            return this;
        }

        public CSVWriter func_225422_a(Writer writer) throws IOException {
            return new CSVWriter(writer, this.field_225424_a);
        }
    }
}

