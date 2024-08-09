/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import com.viaversion.viaversion.libs.kyori.adventure.nbt.CharBuffer;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.CompoundBinaryTag;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.StringTagParseException;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringReader;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.TagStringWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class TagStringIO {
    private static final TagStringIO INSTANCE = new TagStringIO(new Builder());
    private final boolean acceptLegacy;
    private final boolean emitLegacy;
    private final String indent;

    @NotNull
    public static TagStringIO get() {
        return INSTANCE;
    }

    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    private TagStringIO(@NotNull Builder builder) {
        this.acceptLegacy = Builder.access$000(builder);
        this.emitLegacy = Builder.access$100(builder);
        this.indent = Builder.access$200(builder);
    }

    public CompoundBinaryTag asCompound(String string) throws IOException {
        try {
            CharBuffer charBuffer = new CharBuffer(string);
            TagStringReader tagStringReader = new TagStringReader(charBuffer);
            tagStringReader.legacy(this.acceptLegacy);
            CompoundBinaryTag compoundBinaryTag = tagStringReader.compound();
            if (charBuffer.skipWhitespace().hasMore()) {
                throw new IOException("Document had trailing content after first CompoundTag");
            }
            return compoundBinaryTag;
        } catch (StringTagParseException stringTagParseException) {
            throw new IOException(stringTagParseException);
        }
    }

    public String asString(CompoundBinaryTag compoundBinaryTag) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (TagStringWriter tagStringWriter = new TagStringWriter(stringBuilder, this.indent);){
            tagStringWriter.legacy(this.emitLegacy);
            tagStringWriter.writeTag(compoundBinaryTag);
        }
        return stringBuilder.toString();
    }

    public void toWriter(CompoundBinaryTag compoundBinaryTag, Writer writer) throws IOException {
        try (TagStringWriter tagStringWriter = new TagStringWriter(writer, this.indent);){
            tagStringWriter.legacy(this.emitLegacy);
            tagStringWriter.writeTag(compoundBinaryTag);
        }
    }

    TagStringIO(Builder builder, 1 var2_2) {
        this(builder);
    }

    public static class Builder {
        private boolean acceptLegacy = true;
        private boolean emitLegacy = false;
        private String indent = "";

        Builder() {
        }

        @NotNull
        public Builder indent(int n) {
            if (n == 0) {
                this.indent = "";
            } else if (this.indent.length() > 0 && this.indent.charAt(0) != ' ' || n != this.indent.length()) {
                char[] cArray = new char[n];
                Arrays.fill(cArray, ' ');
                this.indent = String.copyValueOf(cArray);
            }
            return this;
        }

        @NotNull
        public Builder indentTab(int n) {
            if (n == 0) {
                this.indent = "";
            } else if (this.indent.length() > 0 && this.indent.charAt(0) != '\t' || n != this.indent.length()) {
                char[] cArray = new char[n];
                Arrays.fill(cArray, '\t');
                this.indent = String.copyValueOf(cArray);
            }
            return this;
        }

        @NotNull
        public Builder acceptLegacy(boolean bl) {
            this.acceptLegacy = bl;
            return this;
        }

        @NotNull
        public Builder emitLegacy(boolean bl) {
            this.emitLegacy = bl;
            return this;
        }

        @NotNull
        public TagStringIO build() {
            return new TagStringIO(this, null);
        }

        static boolean access$000(Builder builder) {
            return builder.acceptLegacy;
        }

        static boolean access$100(Builder builder) {
            return builder.emitLegacy;
        }

        static String access$200(Builder builder) {
            return builder.indent;
        }
    }
}

