/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.examination.string;

import com.viaversion.viaversion.libs.kyori.examination.AbstractExaminer;
import com.viaversion.viaversion.libs.kyori.examination.string.Strings;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StringExaminer
extends AbstractExaminer<String> {
    private static final Function<String, String> DEFAULT_ESCAPER = StringExaminer::lambda$static$0;
    private static final Collector<CharSequence, ?, String> COMMA_CURLY = Collectors.joining(", ", "{", "}");
    private static final Collector<CharSequence, ?, String> COMMA_SQUARE = Collectors.joining(", ", "[", "]");
    private final Function<String, String> escaper;

    @NotNull
    public static StringExaminer simpleEscaping() {
        return Instances.SIMPLE_ESCAPING;
    }

    public StringExaminer(@NotNull Function<String, String> function) {
        this.escaper = function;
    }

    @Override
    @NotNull
    protected <E> String array(E @NotNull [] EArray, @NotNull Stream<String> stream) {
        return stream.collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    protected <E> String collection(@NotNull Collection<E> collection, @NotNull Stream<String> stream) {
        return stream.collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    protected String examinable(@NotNull String string, @NotNull Stream<Map.Entry<String, String>> stream) {
        return string + stream.map(StringExaminer::lambda$examinable$1).collect(COMMA_CURLY);
    }

    @Override
    @NotNull
    protected <K, V> String map(@NotNull Map<K, V> map, @NotNull Stream<Map.Entry<String, String>> stream) {
        return stream.map(StringExaminer::lambda$map$2).collect(COMMA_CURLY);
    }

    @Override
    @NotNull
    protected String nil() {
        return "null";
    }

    @Override
    @NotNull
    protected String scalar(@NotNull Object object) {
        return String.valueOf(object);
    }

    @Override
    @NotNull
    public String examine(boolean bl) {
        return String.valueOf(bl);
    }

    @Override
    @NotNull
    public String examine(byte by) {
        return String.valueOf(by);
    }

    @Override
    @NotNull
    public String examine(char c) {
        return Strings.wrapIn(this.escaper.apply(String.valueOf(c)), '\'');
    }

    @Override
    @NotNull
    public String examine(double d) {
        return Strings.withSuffix(String.valueOf(d), 'd');
    }

    @Override
    @NotNull
    public String examine(float f) {
        return Strings.withSuffix(String.valueOf(f), 'f');
    }

    @Override
    @NotNull
    public String examine(int n) {
        return String.valueOf(n);
    }

    @Override
    @NotNull
    public String examine(long l) {
        return String.valueOf(l);
    }

    @Override
    @NotNull
    public String examine(short s) {
        return String.valueOf(s);
    }

    @Override
    @NotNull
    protected <T> String stream(@NotNull Stream<T> stream) {
        return stream.map(this::examine).collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    protected String stream(@NotNull DoubleStream doubleStream) {
        return doubleStream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    protected String stream(@NotNull IntStream intStream) {
        return intStream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    protected String stream(@NotNull LongStream longStream) {
        return longStream.mapToObj(this::examine).collect(COMMA_SQUARE);
    }

    @Override
    @NotNull
    public String examine(@Nullable String string) {
        if (string == null) {
            return this.nil();
        }
        return Strings.wrapIn(this.escaper.apply(string), '\"');
    }

    @Override
    @NotNull
    protected String array(int n, IntFunction<String> intFunction) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(intFunction.apply(i));
            if (i + 1 >= n) continue;
            stringBuilder.append(", ");
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    @NotNull
    protected Object array(int n, IntFunction intFunction) {
        return this.array(n, intFunction);
    }

    @Override
    @NotNull
    protected Object stream(@NotNull LongStream longStream) {
        return this.stream(longStream);
    }

    @Override
    @NotNull
    protected Object stream(@NotNull IntStream intStream) {
        return this.stream(intStream);
    }

    @Override
    @NotNull
    protected Object stream(@NotNull DoubleStream doubleStream) {
        return this.stream(doubleStream);
    }

    @Override
    @NotNull
    protected Object stream(@NotNull Stream stream) {
        return this.stream(stream);
    }

    @Override
    @NotNull
    protected Object scalar(@NotNull Object object) {
        return this.scalar(object);
    }

    @Override
    @NotNull
    protected Object nil() {
        return this.nil();
    }

    @Override
    @NotNull
    protected Object map(@NotNull Map map, @NotNull Stream stream) {
        return this.map(map, stream);
    }

    @Override
    @NotNull
    protected Object examinable(@NotNull String string, @NotNull Stream stream) {
        return this.examinable(string, stream);
    }

    @Override
    @NotNull
    protected Object collection(@NotNull Collection collection, @NotNull Stream stream) {
        return this.collection(collection, stream);
    }

    @Override
    @NotNull
    protected Object array(Object @NotNull [] objectArray, @NotNull Stream stream) {
        return this.array(objectArray, stream);
    }

    @Override
    @NotNull
    public Object examine(@Nullable String string) {
        return this.examine(string);
    }

    @Override
    @NotNull
    public Object examine(short s) {
        return this.examine(s);
    }

    @Override
    @NotNull
    public Object examine(long l) {
        return this.examine(l);
    }

    @Override
    @NotNull
    public Object examine(int n) {
        return this.examine(n);
    }

    @Override
    @NotNull
    public Object examine(float f) {
        return this.examine(f);
    }

    @Override
    @NotNull
    public Object examine(double d) {
        return this.examine(d);
    }

    @Override
    @NotNull
    public Object examine(char c) {
        return this.examine(c);
    }

    @Override
    @NotNull
    public Object examine(byte by) {
        return this.examine(by);
    }

    @Override
    @NotNull
    public Object examine(boolean bl) {
        return this.examine(bl);
    }

    private static String lambda$map$2(Map.Entry entry) {
        return (String)entry.getKey() + '=' + (String)entry.getValue();
    }

    private static String lambda$examinable$1(Map.Entry entry) {
        return (String)entry.getKey() + '=' + (String)entry.getValue();
    }

    private static String lambda$static$0(String string) {
        return string.replace("\"", "\\\"").replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    static Function access$000() {
        return DEFAULT_ESCAPER;
    }

    private static final class Instances {
        static final StringExaminer SIMPLE_ESCAPING = new StringExaminer(StringExaminer.access$000());

        private Instances() {
        }
    }
}

